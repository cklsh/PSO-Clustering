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
        return (dotProduct(d1, d2)/ jarak(d1, d2));
    }
    
    public double dotProduct(ParseDocument d1, ParseDocument d2){
        Term[] terms1 = d1.getTerm();
        Term[] terms2 = d2.getTerm();
        double result = 0;
        for (int i = 0; i < terms1.length; i++) {
            for (int j = 0; j < terms2.length; j++) {
                
                System.out.println("1 iteration... " + terms1[i].key + "    " + terms2[j].key );
                Term term1 = terms1[i];
                
                if((terms1[i].key.equals(terms2[j].key)) && (terms1[i].key.length() >0)){

                    double IDF = (double) this.idf.get(terms1[i].key);
                    result+= Math.pow((TFIDF(terms1[i].frec, IDF) * TFIDF(terms2[j].frec, IDF)),2);
                }
            }
        }
        return Math.sqrt(result);
    }
    
    public double jarak(ParseDocument d1, ParseDocument d2){
        return (d1.getPosition() * d2.getPosition());
    }
    
}

//merge sort dilakukan agar jika nilai 
//
//bikin cosine similarity jadi pakai merge sort
//kesulitannya di, sebelum dapat melakukan merge sort, harus dipikirin gimana caranya biar data terurut.
//begitu.