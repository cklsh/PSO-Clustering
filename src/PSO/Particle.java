/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO;

import CosineSimilarity.CosineSimilarity;
import preprocessing.trie.DFS;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import preprocessing.parser.Document;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Term;
import preprocessing.trie.Trie;
import preprocessing.trie.TrieNode;

/**
 *
 * @author caca
 */
public class Particle {
    private int jumlahCentroid;
    private double[][] velocity;
    private Centroid[] pBestPosition;
    private double pBest;
    private ParseDocument[] parseDoc;
    private Trie trie;
    private DFS dfs;
    private Centroid[] centroids;
    public ArrayList<String> bagOfTerm;
    Random r = new Random();
   
    
    Particle(int k, ParseDocument[] parseDoc, Trie trie, ArrayList<String> bagOfTerm){
        this.jumlahCentroid = k;
        this.pBest = 0;
        this.parseDoc = parseDoc;
        this.trie = trie;
        this.dfs = new DFS(parseDoc.length);
        this.centroids = new Centroid[this.jumlahCentroid];
        this.bagOfTerm = bagOfTerm;
        this.velocity = new double[this.jumlahCentroid][this.bagOfTerm.size()];

        initialize();
    }

    public void initialize() {
        initCentroids();
        handlePBestInitialization();
    }
    
    private void initCentroids(){
        int min = 0;
        int max = this.jumlahCentroid;
        
        for (int i = 0; i < this.jumlahCentroid; i++) {
            int rand = r.nextInt((max - min) + 1) + min;
            Centroid aCentroid = new Centroid(parseDoc[rand],trie, bagOfTerm);
            this.centroids[i] = aCentroid;
            
            for (int j = 0; j < this.bagOfTerm.size(); j++) {
                this.velocity[i][j] = 0;
            }
        }
    }
    
    private void handlePBestInitialization(){
        double fitness = calculateFitness(this.centroids);
        if(fitness > 0){
            setPBest(this.centroids, fitness);
        }else{
            setPBest(this.centroids, 0.0001);

        }
    }
    
    //use cosine similarity
    public void cluster() {
        long start = System.nanoTime();

        HashMap idf = dfs.findIDF(trie.getRoot().children);
        CosineSimilarity cs= new CosineSimilarity(idf);
        double maxSimilarity = 0;
        double similarity;
        int bestCentroid = 0;
        //parseDoc
        for (int i = 0; i < parseDoc.length; i++) {
            //centroid
            for (int j =  0; j < this.jumlahCentroid; j++) {
                similarity = cs.calculate(this.parseDoc[i], this.centroids[j].getPosition());
                if(similarity > maxSimilarity){
                    maxSimilarity = similarity;
                    bestCentroid = j;
                }
                break;
            }
            centroids[bestCentroid].addObjectCluster(parseDoc[i], maxSimilarity);
            maxSimilarity = 0;
        }
        
       updatePBest();
       
        long end = System.nanoTime();
        System.out.println("do cluster (one particle): " + (end-start));
    }
  
    public double[][] getVelocity(){
        return this.velocity;
    }
    
    public void setVelocity(double[][] velocity){
        this.velocity = velocity;
    }
    
    public Centroid[] getCentroid(){
        return this.centroids;
    }
    
    private void updatePBest(){
        double fitness = calculateFitness(this.centroids);
        if(fitness > pBest){
            setPBest(this.centroids, fitness);
        }
    }

    public void updateCentroid(Centroid[] centroids){
        this.centroids = centroids;
        cluster();
    }
    
   
    public void comparePBest(){
        double currentFitness = calculateFitness(this.centroids);
        if(currentFitness > this.pBest){
            setPBest(this.centroids, currentFitness);
        }
    }
    
    public double calculateFitness(Centroid[] centroid){
        double fitness = 0.00001;
        for (int i = 0; i < this.jumlahCentroid; i++) {
            fitness += centroids[i].getMean();
        }
        return fitness;
    }
    
    public double getPBest(){
        return this.pBest;
    }
    
    public Centroid[] getPBestPosition(){
        return this.pBestPosition;
    }
    
    public void setPBest(Centroid[] newPBest, double newPBestValue){
        this.pBestPosition = newPBest;
        this.pBest = newPBestValue;
    }
   
}
