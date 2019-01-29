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
        for (int i = 0; i < input.getIterasi(); i++) {
           System.out.println("================================ Iterasi ke           " + i);
           
           calculate();
           if(currentGBestIsGreaterThanDataInputFitness(i)) break;
        }
   }

    private void initialize() {
        double fitness;
        createParticles();
        preventGBestHavingZeroValueAtTheFirstInitialization();
    }
    
    private void createParticles(){
        for (int i = 0; i < this.input.getJumlahParticle(); i++) {
            Particle newParticle = new Particle(this.input.getJumlahCentroid(), this.parseDoc, this.trie, this.bagOfTerm);
            this.particles.add(newParticle);
            this.particles.get(i).cluster();
            Centroid[] centroid = newParticle.getCentroid();
            newParticle.calculateFitness(centroid);
            
            compareNewParticleFitnessWithGBest(newParticle.calculateFitness(centroid), i);
            
            System.out.println("GLOBAL BEST================" + this.gBest);
        }
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
        }
        updateParticles();
    }

    
    private void updateParticles() {
        for (int i = 0; i < this.input.getJumlahParticle(); i++) {
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
        }
    }
    
    private int[][] calculateNewPosition(double[][] currVelocity, int[] currPosition, int[][] newPosition, int[] GBest, int[] PBest, int j, int k){
        currVelocity[j][k] = currVelocity[j][k] + (this.input.getR1() * (GBest[k] - currPosition[k])) + (this.input.getR2() * (PBest[k] - currPosition[k])) ;
        if(currVelocity[j][k] < 0){
            currVelocity[j][k] = 0;
        }
        else if(currVelocity[j][k] > this.input.getMaxVelocity()){
            currVelocity[j][k] = this.input.getMaxVelocity();
        }
        newPosition[j][k] = (int) currVelocity[j][k] + currPosition[k];
        return newPosition;
    }
    
    private int[][] initializePosition(Particle aParticle, double[][] currVelocity, int[][] newPosition, int j){
        int[] GBest = this.gBestPosition[j].getMeanOfEveryTermInEveryObj();
        int[] PBest = aParticle.getPBestPosition()[j].getMeanOfEveryTermInEveryObj();
        int[] currPosition = aParticle.getCentroid()[j].getMeanOfEveryTermInEveryObj();
        
        for (int k = 0; k < this.bagOfTerm.size(); k++) {
            newPosition = calculateNewPosition(currVelocity, currPosition, newPosition, GBest, PBest, j, k);
            
            //
            PrintOutput print = new PrintOutput();
            print.PRINTUpdate(bagOfTerm, currPosition, newPosition, currVelocity, GBest, PBest, j, k);
        }
        
        return newPosition;
    }
    
    private Centroid centroidFactory(int[][] newPosition, int i, int j){
        Term[] terms = new Term[this.bagOfTerm.size()];
        for (int n = 0; n < this.bagOfTerm.size(); n++) {
            terms[n] = new Term(this.bagOfTerm.get(n), newPosition[j][n], Integer.toString(i));
        }
        ParseDocument newParseDoc = new ParseDocument(Integer.toString(i), terms);
        Centroid newCentroid = new Centroid(newParseDoc, this.trie, this.bagOfTerm);
        return newCentroid;
    }
    
    private boolean currentGBestIsGreaterThanDataInputFitness(int i){
        if(this.gBest > input.getFitness()){
            System.out.println("break, jumlah iterasi= " + i);
            System.out.println("gBest: " + this.gBest);
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
