/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing.trie;

import CosineSimilarity.CosineSimilarity;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import preprocessing.parser.Term;
import preprocessing.trie.TrieNode;

/**
 *
 * @author caca
 */
public class DFS {
    
    int jumlahDoc;
    HashMap<String, Double> idf;
    ArrayList<String> bagOfTerm;
    public DFS(){
        this.bagOfTerm = new ArrayList<String>();

    }
    public DFS(int jumlahDoc){
        this.jumlahDoc = jumlahDoc;
        this.idf = new HashMap<String, Double>();
    }
    
    public ArrayList<String> DFSProcess(TrieNode[] trieNode){
        for (int i = 0; i < 26; i++) {
            if(trieNode[i] !=null) {
                if(trieNode[i].value != null){
                    ArrayList newArr = (ArrayList) trieNode[i].value;
                    Term term  = (Term) newArr.get(0);
                    this.bagOfTerm.add(term.key);
                }
                DFSProcess(trieNode[i].children);
            }
        }
        return this.bagOfTerm;
    }
    
    public HashMap<String, Double> mapIDF(TrieNode[] trieNode){
        for (int i = 0; i < 26; i++) {
            if(trieNode[i] !=null) {
                if(trieNode[i].value != null){
                    ArrayList newArr = (ArrayList) trieNode[i].value;
                    Term term  = (Term) newArr.get(0);
                    CosineSimilarity cs = new CosineSimilarity();
                    double countIDF = cs.IDF(jumlahDoc, newArr.size());
                    this.idf.put(term.key, countIDF);
                }
                mapIDF(trieNode[i].children);
            }
        }
        return idf;
    }
}