/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import PSO.Centroid;
import PSO.Particle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import preprocessing.parser.ParseDocument;

/**
 *
 * @author caca
 */
public class Pengujian {
    DataInput input;
    ArrayList<Particle> particles;
    double gBest;
    HashMap<String, Integer> data;
    
    Pengujian(DataInput input, ArrayList<Particle> particles, double gBest){
        this.input = input;
        this.particles = particles;
        this.gBest = gBest;
        this.data =  new HashMap<String, Integer>();
    }
    
    protected String hasil(){
        String hasil = "";
        for (int i = 0; i < input.jumlahParticle; i++) {
            Particle aParticle = particles.get(i);
            Centroid centroid[] = aParticle.getCentroid();
            System.out.println("================CLUSTER==============");
            System.out.println("");
            for (int j = 0; j < input.getJumlahCentroid(); j++) {
                 ArrayList<ParseDocument> apd = centroid[j].getObjCluster();
                for (int k = 0; k <apd.size(); k++) {
                   ParseDocument pd = apd.get(k);
                   String fileNameWithoutNumber = this.removeNumber(pd.getFileName());
                   int count = this.data.containsKey(fileNameWithoutNumber) ? this.data.get(fileNameWithoutNumber) : 0;
                   this.data.put(fileNameWithoutNumber, count + 1);
                }
                PRINT();
                this.data.clear();
            }

        }
  
        return hasil;
        
    } 
    
    public void PRINT(){
        System.out.println("------------centroid----------");
        for (Map.Entry<String, Integer> filename : this.data.entrySet()) {
            System.out.println("filename: " + filename.getKey());
            System.out.println("value: " + filename.getValue());

        }
    }
    
    private String removeNumber(String filename){
        return filename.replace(".txt", "").replaceAll("\\d*$", "");
    }
    
}
