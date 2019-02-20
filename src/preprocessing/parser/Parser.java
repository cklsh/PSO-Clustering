package preprocessing.parser;


import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author caca
 */
public class Parser {
    
    HashMap<String, Integer> map = new HashMap();
//    Term[] terms;
    Document document;
    String pattern;
    
    public Parser(Document document, String pattern){
        this.document = document;
        this.pattern = pattern;
    }
    
    public HashMap<String, Term> parse(){
        String[] tokens = this.document.text.split(this.pattern);
        
        for(String w : tokens){
            Integer n = map.get(w.toLowerCase());
            n = (n == null) ? 1 : n+1;
            if(w.length() >1) map.put(w.toLowerCase(), n);

        }
        
        HashMap<String, Term> terms= new HashMap();
//        terms = new Term[map.size()];
//        String[] key = new String[map.size()];
//        Integer[] frec = new Integer[map.size()];
//        map.keySet().toArray(key);
//        map.values().toArray(frec);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Term term = new Term(entry.getKey(), entry.getValue(), this.document.docId);
            terms.put(entry.getKey(), term);
	}
//        for (int i = 0; i < terms.length; i++) {
//            terms[i] = new Term(key[i], frec[i], this.document.docId);
//        }
        return terms;
    }
    
}
