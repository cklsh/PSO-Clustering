/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import main.DataInput;
import main.PrintOutput;
import preprocessing.parser.Document;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Parser;
import preprocessing.parser.Term;
import preprocessing.trie.Trie;

/**
 *
 * @author caca
 */
public class Swarm {
    
    private double gBest;
    private Centroid[] gBestPosition;
    private Random rand;

    private ArrayList<Particle> particles;
    private ArrayList<Particle> solutions;

    Trie trie;
    ParseDocument[] parseDoc;
    ArrayList<String> bagOfTerm;
    
    DataInput input;

   public Swarm(DataInput input, ParseDocument[] parseDoc, Trie trie, ArrayList<String> bagOfTerm){
       this.particles = new ArrayList();
       this.solutions = new ArrayList();
       this.parseDoc = parseDoc;
       this.trie = trie;
       this.gBest = 0;
       this.gBestPosition = null;
       this.bagOfTerm = bagOfTerm;
       this.input = input;
       
       initialize();
       doIteration();
   }
   
   private void doIteration(){
        long start = System.nanoTime();
        for (int i = 0; i < input.getIterasi(); i++) {
//           System.out.println("================================ Iterasi ke           " + i);
           
           calculate();
           if(currentGBestIsGreaterThanDataInputFitness(i)) break;
           break;
        }
        long end = System.nanoTime();
//        System.out.println("iteration: " + (end-start));
   }

    private void initialize() {
        double fitness;
        createParticles();
        preventGBestHavingZeroValueAtTheFirstInitialization();
    }
    
    private void createParticles(){
        long start = System.nanoTime();
        for (int i = 0; i < this.input.getJumlahParticle(); i++) {
            Particle newParticle = new Particle(this.input.getJumlahCentroid(), this.parseDoc, this.trie, this.bagOfTerm);
            this.particles.add(newParticle);
            this.particles.get(i).cluster();
            Centroid[] centroid = newParticle.getCentroid();
            newParticle.calculateFitness(centroid);
            
            compareNewParticleFitnessWithGBest(newParticle.calculateFitness(centroid), i);
            
//          System.out.println("GLOBAL BEST================" + this.gBest);
            break;
        }
        long end = System.nanoTime();
        System.out.println("createParticles: " + (end-start));
    }
    
    private void preventGBestHavingZeroValueAtTheFirstInitialization(){
       if(this.gBest==0){
            this.gBest = this.particles.get(0).calculateFitness(gBestPosition);
            this.gBestPosition = this.particles.get(0).getCentroid();
        }
    }
    
    private void compareNewParticleFitnessWithGBest(double fitness, int i){
        if(fitness > this.gBest){
            this.gBest = fitness;
            this.gBestPosition = this.particles.get(i).getCentroid();
        }
    }
    
    private void calculate() {
        long start = System.nanoTime();
        Particle aParticle = null;
        double pBest = 0;
        for (int j = 0; j < this.input.getJumlahParticle(); j++) {
           aParticle = this.particles.get(j);
          // aParticle.findNewCentroid(centroid);
           pBest = aParticle.getPBest();
           if(pBest > this.gBest){
               this.gBest = pBest;
               this.gBestPosition = aParticle.getPBestPosition();
           }
           break;
        }
        updateParticles();
        long end = System.nanoTime();
        System.out.println("swarm calculate: " + (end-start));
    }

    private void updateParticles() {
        long start = System.nanoTime();
        for (int i = 0; i < this.input.getJumlahParticle(); i++) {
            long start2 = System.nanoTime();
            Particle aParticle = this.particles.get(i);
            double[][] currVelocity = aParticle.getVelocity();
            int[][] newPosition = new int[this.input.getJumlahCentroid()][this.bagOfTerm.size()]; 
            Centroid[] newCentroid  = new Centroid[this.input.getJumlahCentroid()];
            
            for (int j = 0; j < this.input.getJumlahCentroid(); j++) {
                newPosition = initializePosition(aParticle, currVelocity, newPosition, j);
                newCentroid[j] = centroidFactory(newPosition, i, j);
            }
            aParticle.setVelocity(currVelocity);
            aParticle.updateCentroid(newCentroid);
            long end2 = System.nanoTime();
            System.out.println("update one Particles: " + (end2-start2));
            break;
        }
        long end = System.nanoTime();
        System.out.println("update all Particles: " + (end-start));
    }
    
    private int[][] calculateNewPosition(double[][] currVelocity, int[] currPosition, int[][] newPosition, int[] GBest, int[] PBest, int j, int k){
        long start = System.nanoTime();
        currVelocity[j][k] = currVelocity[j][k] + (this.input.getR1() * (GBest[k] - currPosition[k])) + (this.input.getR2() * (PBest[k] - currPosition[k])) ;
        if(currVelocity[j][k] < 0){
            currVelocity[j][k] = 0;
        }
        else if(currVelocity[j][k] > this.input.getMaxVelocity()){
            currVelocity[j][k] = this.input.getMaxVelocity();
        }
        newPosition[j][k] = (int) currVelocity[j][k] + currPosition[k];
        long end = System.nanoTime();
//        System.out.println("calculate new position: " + (end-start));
        return newPosition;
    }
    
    private int[][] initializePosition(Particle aParticle, double[][] currVelocity, int[][] newPosition, int j){
        long start = System.nanoTime();
        int[] GBest = this.gBestPosition[j].getMeanOfEveryTermInEveryObj();
        int[] PBest = aParticle.getPBestPosition()[j].getMeanOfEveryTermInEveryObj();
        int[] currPosition = aParticle.getCentroid()[j].getMeanOfEveryTermInEveryObj();
        
        for (int k = 0; k < this.bagOfTerm.size(); k++) {
            newPosition = calculateNewPosition(currVelocity, currPosition, newPosition, GBest, PBest, j, k);
            
//            
//            PrintOutput print = new PrintOutput();
//            print.PRINTUpdate(bagOfTerm, currPosition, newPosition, currVelocity, GBest, PBest, j, k);
        }
        long end = System.nanoTime();
        System.out.println("initialize position (inside swarm's update particle) " + (end-start));
        return newPosition;
    }
    
    private Centroid centroidFactory(int[][] newPosition, int i, int j){
        long start = System.nanoTime();
        Term[] terms = new Term[this.bagOfTerm.size()];
        for (int n = 0; n < this.bagOfTerm.size(); n++) {
            terms[n] = new Term(this.bagOfTerm.get(n), newPosition[j][n], Integer.toString(i));
        }
        ParseDocument newParseDoc = new ParseDocument(Integer.toString(i), terms);
        Centroid newCentroid = new Centroid(newParseDoc, this.trie, this.bagOfTerm);
        
        long end = System.nanoTime();
        System.out.println("centroid factory: " + (end-start));
        
        return newCentroid;
    }
    
    private boolean currentGBestIsGreaterThanDataInputFitness(int i){
        if(this.gBest > input.getFitness()){
//            System.out.println("break, jumlah iterasi= " + i);
//            System.out.println("gBest: " + this.gBest);
            return true;
        }
        else return false;
    }
    
    public ArrayList<Particle> getParticles(){
        return this.particles;
    }
    
    public double getGBest(){
        return this.gBest;
    }
    
}
