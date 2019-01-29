/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CosineSimilarity;

import java.util.HashMap;
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
          
    public double TFIDF(double TF, double IDX){
        return (TF*IDX);
    }
    
    public double TF(int jumlahTerm){
        return Math.log(jumlahTerm);
    }
    
    public double IDF(int jumlahDoc, int jumlahDocTerm){
        return Math.log(1 + jumlahDoc / jumlahDocTerm);
    }
    
    public double calculate(ParseDocument d1, ParseDocument d2){
        Term[] terms1 = d1.getTerm();
        Term[] terms2 = d2.getTerm();
        double result = 0;
        for (int i = 0; i < terms1.length; i++) {
            for (int j = 0; j < terms2.length; j++) {
                Term term1 = terms1[i];
                if((terms1[i].key.equals(terms2[j].key)) && (terms1[i].key.length() >0)){
                    double IDF = (double) this.idf.get(terms1[i].key);
                    result+= Math.pow((TFIDF(terms1[i].TF, IDF) * TFIDF(terms2[j].TF, IDF)),2);
                }
            }
        }
        double hasil = (Math.sqrt(result)/ (d1.getPosition() * d2.getPosition()));
        return hasil;
    }
    
}