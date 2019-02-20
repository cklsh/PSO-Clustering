 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import preprocessing.parser.Term;

/**
 *
 * @author caca
 */
public class Trie<U> {
    TrieNode root;

    public Trie(){
        this.root = new TrieNode(null);
    }
    
    public void putTerms(HashMap<String, Term> terms){
        for (Map.Entry<String, Term> entry : terms.entrySet()) {
            put(entry.getKey(), entry.getValue());
	}
//        for (Term term : terms) {
//            put(term.key, term);
//        }
    }

    public void put(String termValue, Object obj){
        TrieNode node = this.root;
        termValue = termValue.toLowerCase();

        for (int i = 0; i < termValue.length(); i++) {
            String c =  termValue.substring(i, i+1);
            if(node.children[Character.getNumericValue(c.charAt(0))-10] == null){
                node.children[Character.getNumericValue(c.charAt(0))-10] = new TrieNode(null);
                node = node.children[Character.getNumericValue(c.charAt(0))-10];
            }
            else{  
                node = node.children[Character.getNumericValue(c.charAt(0))-10];
            }
        }
        ArrayList listOfObject = (ArrayList) get(termValue);
        ArrayList arr = new ArrayList();
        
        if(listOfObject == null){
            arr.add(obj);
        }else {
            arr = listOfObject;
            arr.add(obj);
        }
        node.value = arr;
    } 
    
    public Object get(String termValue){
        TrieNode node = this.root;
        termValue = termValue.toLowerCase();

        for (int i = 0; i < termValue.length(); i++) {
            String c = termValue.substring(i, i+1);
            if(node.children[Character.getNumericValue(c.charAt(0))-10] == null){
                return null;
            }
            else{
                node = node.children[Character.getNumericValue(c.charAt(0))-10];
            }
        }
        return node.value;
    }

    public TrieNode getRoot(){
        return this.root;
    }
    
   
}
