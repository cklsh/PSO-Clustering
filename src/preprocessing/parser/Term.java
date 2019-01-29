package preprocessing.parser;

import CosineSimilarity.CosineSimilarity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  
 * @author caca
 */
public class Term {

    public String key; //kata yang disimpan
    public int frec; //Jumlah kata pada dokumen
    public String docId; //idDokumen
    public double TF; //termFrekuensi
    CosineSimilarity cs = new CosineSimilarity();
    
    public Term(String key, int frec, String docId){
        this.key = key;
        this.docId = docId;
        this.frec = frec;
        this.TF = cs.TF(frec);
    }
}
