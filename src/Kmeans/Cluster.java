/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kmeans;

import java.util.ArrayList;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Term;
import preprocessing.trie.Trie;

/**
 *
 * @author caca
 */
public class Cluster {
    private ArrayList<ParseDocument> objCluster;
    private ParseDocument centroid;
    private double mean;
    private ArrayList<String> bagOfTerm;
    private Trie trie;
    
    Cluster(Trie trie, ArrayList<String> bagOfTerm){
        this.objCluster = new ArrayList<ParseDocument>();
        this.mean = mean;
        this.centroid = centroid;
        
        this.trie = trie;
        this.bagOfTerm = bagOfTerm;
    }
    
    public void setCentroid(ParseDocument centroid){
        this.mean = 0;
        this.centroid = centroid;
    }
    
    public ParseDocument getCentroidPosition(){
        return this.centroid;
    }
    
    public void addObjectCluster(ParseDocument newObj, double similarity){
        this.mean += similarity;
        this.objCluster.add(newObj);
    }
     
    public ArrayList<ParseDocument> getObjCluster(){
        return this.objCluster;
    }
    
    public void removeObjCluster(ParseDocument obj){
        objCluster.remove(obj);
    }
    
    public int[] getMeanOfEveryTermInEveryObj(){
        int counterOfTermAppearanceInObj =1;
        int[] termsPositions = initializeTermPosition();
        
        for (int i = 0; i < this.bagOfTerm.size(); i++) {
            termsPositions[i] = calculateMeanOfEveryTerm(termsPositions[i], counterOfTermAppearanceInObj, i);
            counterOfTermAppearanceInObj = 1;
        }
        return termsPositions;
    }
    
    public int calculateMeanOfEveryTerm(int termsPosition, int counterOfTermAppearanceInObj, int i){

        String term = this.bagOfTerm.get(i);
        ArrayList<Term> trieNode = (ArrayList<Term>) trie.get(term);

        for (int j = 0; j < this.objCluster.size(); j++) {
            ParseDocument pd = this.objCluster.get(j);
            for (int k = 0; k < trieNode.size(); k++) {
                if(trieNode.get(k).docId.equals(pd.getFileName())){
                    counterOfTermAppearanceInObj++;
                    termsPosition += trieNode.get(k).frec;
                }
            }
        }
        return (termsPosition/ counterOfTermAppearanceInObj);
    }
    
    private int[] initializeTermPosition(){
        int[] termsPosition = new int[this.bagOfTerm.size()];
        for (int i = 0; i < termsPosition.length; i++) {
            termsPosition[i] = 1;
        }
        return termsPosition;
    }
    
}
