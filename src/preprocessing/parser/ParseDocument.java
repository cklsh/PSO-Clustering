package preprocessing.parser;

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
    Term[] term;
    double position;
    
    
    public ParseDocument(String fileName, Term[] term){
        this.fileName = fileName;
        this.term = term;
        this.position = calculatePosition(term);
    }

    public String getFileName(){
        return this.fileName;
    }
    
    public Term[] getTerm(){
        return this.term;
    }
    
    public double getPosition(){
        return this.position;
    }
    
    public double calculatePosition(Term[] term){
        double result=0;
        for (int i = 0; i < term.length; i++) {
            result+= Math.pow(term[i].frec ,2);
        }
        return Math.sqrt(result);
    }
}
