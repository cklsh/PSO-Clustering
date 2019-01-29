/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing.trie;

import java.util.ArrayList;
import preprocessing.parser.Term;

/**
 *
 * @author caca
 */
public class TrieNode {

    public Object value;
    public TrieNode[] children;
    

    public TrieNode(Object termValue){
        this.value = termValue;
        children = new TrieNode[26];
    }
}

