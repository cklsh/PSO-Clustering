/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kmeans;

import CosineSimilarity.CosineSimilarity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import main.DataInput;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Term;
import preprocessing.trie.DFS;
import preprocessing.trie.Trie;

/**
 *
 * @author caca
 */
public class Kmeans {
        
    private Random r = new Random();

    Trie trie;
    ParseDocument[] parseDoc;
    ArrayList<String> bagOfTerm;
    
    Cluster[] clusters;
    int counter = 10;
    
    DataInput input;
    

   public Kmeans(DataInput input, ParseDocument[] parseDoc, Trie trie, ArrayList<String> bagOfTerm){
       this.parseDoc = parseDoc;
       this.trie = trie;
       this.bagOfTerm = bagOfTerm;
       this.input = input;
       this.clusters = new Cluster[input.getJumlahCentroid()];
       this.counter = counter;
       
       initialize();
    }

    public Cluster[] doIteration(){
        for (int i = 0; i < input.getIterasi(); i++) {
            cluster();
            updateClusterCentroid();
            
            if(this.counter == 0){
                return clusters;
            }
        }
        return clusters;
    }

    private void initialize() {
        int min = 0;
        int max = parseDoc.length;
        int rand;
        for (int i = 0; i < this.clusters.length; i++) {
            rand = r.nextInt((max - min) + 1) + min;
            Cluster aCluster = new Cluster(this.trie, this.bagOfTerm);
            aCluster.setCentroid(parseDoc[rand]);
            this.clusters[i] = aCluster;
        }
    }
    
    public void cluster() {
        DFS dfs = new DFS(parseDoc.length);
        HashMap mapIDF = dfs.mapIDF(trie.getRoot().children);
        CosineSimilarity cs= new CosineSimilarity(mapIDF);
        double maxSimilarity = 0;
        double similarity;
        int bestCentroid = 0;
        this.counter = 0;

        for (int i = 0; i < parseDoc.length; i++) {

            for (int j =  0; j < this.clusters.length; j++) {
                similarity = cs.calculate(this.parseDoc[i], this.clusters[j].getCentroidPosition());
                if(similarity > maxSimilarity){
                    maxSimilarity = similarity;
                    bestCentroid = j;
                }
            }
            
            if(!this.clusters[bestCentroid].getObjCluster().contains(parseDoc[i])){
                for (int j = 0; j < this.clusters.length; j++) {
                    if(this.clusters[j].getObjCluster().contains(parseDoc[i])){
                        this.clusters[j].removeObjCluster(parseDoc[i]);
                    }
                }
                this.clusters[bestCentroid].addObjectCluster(parseDoc[i], maxSimilarity);              
                this.counter++;
            }
            maxSimilarity = 0;  

        }
       
    }
    
    public void updateClusterCentroid(){
        int[][] newPosition = new int[this.input.getJumlahCentroid()][this.bagOfTerm.size()]; 
        Term term;

        for (int i = 0; i < this.clusters.length; i++) {
            int[] meanTerms = this.clusters[i].getMeanOfEveryTermInEveryObj();
            HashMap<String, Term> terms = new HashMap();
            for (int j = 0; j < this.bagOfTerm.size(); j++) {
                newPosition[i][j] = meanTerms[j];
                term = new Term(this.bagOfTerm.get(j), newPosition[i][j], Integer.toString(i));
                terms.put(this.bagOfTerm.get(j), term);
            }
            ParseDocument newParseDoc = new ParseDocument(Integer.toString(i), terms);
            this.clusters[i].setCentroid(newParseDoc);
        }
    }
}
