/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO;

import java.util.ArrayList;
import java.util.HashMap;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Term;
import preprocessing.trie.Trie;
import preprocessing.trie.TrieNode;

/**
 *
 * @author caca
 */
public class Centroid {
    private ArrayList<ParseDocument> objCluster;
    private ParseDocument position;
    private double mean;
    private ArrayList<String> bagOfTerm;
    private Trie trie;
    double[][] currVelocity;
    
    Centroid(ParseDocument position, Trie trie, ArrayList<String> bagOfTerm){
        this.position = position;
        this.objCluster = new ArrayList<ParseDocument>();
        this.trie = trie;
        this.mean = 0;
        this.bagOfTerm = bagOfTerm;
    }
    

    public void addObjectCluster(ParseDocument newObj, double similarity){
        this.mean += similarity;
        this.objCluster.add(newObj);
    }
    
    public ArrayList<ParseDocument> getObjCluster(){
        return objCluster;
    }
    
    public ParseDocument getPosition(){
        return position;
    }
   
    public double getMean(){
        if(this.objCluster.size() == 0) return handleMeanCalculationWhenThereIsNoObjectInCluster();
        return (this.mean/(this.objCluster.size() + 1));
    }
    
    public double handleMeanCalculationWhenThereIsNoObjectInCluster(){
        return this.mean/1;
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
