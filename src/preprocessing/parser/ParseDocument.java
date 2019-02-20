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
public class ParseDocument {

    String fileName;
    HashMap<String, Term> terms = new HashMap();
    double position;
    
    
    public ParseDocument(String fileName, HashMap<String, Term> terms){
        this.fileName = fileName;
        this.terms = terms;
        this.position = calculatePosition(terms);
    }

    public String getFileName(){
        return this.fileName;
    }
    
    public HashMap<String, Term> getTerm(){
        return this.terms;
    }
    
    public double getPosition(){
        return this.position;
    }
    
    public double calculatePosition(HashMap<String, Term> terms){
        double result=0;
        for(Map.Entry<String, Term> entry : terms.entrySet()) {
            result+= Math.pow(entry.getValue().frec, 2);
	}
//        for (int i = 0; i < term.length; i++) {
//            result+= Math.pow(term[i].frec ,2);
//        }
        return Math.sqrt(result);
    }
}
