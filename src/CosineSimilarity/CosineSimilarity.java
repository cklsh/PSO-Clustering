/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CosineSimilarity;

import java.util.HashMap;
import java.util.Map;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Term;

/**
 *
 * @author caca
 */
public class CosineSimilarity {
    HashMap idf;

    public CosineSimilarity(){}
    
    public CosineSimilarity(HashMap idf){
        this.idf= idf;
    }
          
    public double TFIDF(int frec, double IDX){
        return (TF(frec)*IDX);
    }
    
    public double TF(int jumlahTerm){
        return Math.log(jumlahTerm);
    }
    
    public double IDF(int jumlahDoc, int jumlahDocTerm){
        return Math.log(1 + jumlahDoc / jumlahDocTerm);
    }
    
    public double calculate(ParseDocument d1, ParseDocument d2){
        return (dotProduct(d1.getTerm(), d2.getTerm())/ jarak(d1, d2));
    }
    
    public double dotProduct(HashMap<String, Term> terms1, HashMap<String, Term> terms2){
        double result = 0;
        for (Map.Entry<String, Term> entry : terms1.entrySet()) {
            if(terms2.containsKey(entry.getKey())){
                double IDF = (double) this.idf.get(entry.getKey());
                double TFIDF1 = TFIDF(entry.getValue().frec, IDF);
                double TFIDF2 = TFIDF(terms2.get(entry.getKey()).frec, IDF);
                
                result+= Math.pow((TFIDF1 * TFIDF2), 2);
            }
	}
        return Math.sqrt(result);
    }
    
    public double jarak(ParseDocument d1, ParseDocument d2){
        return (d1.getPosition() * d2.getPosition());
    }
    
}
