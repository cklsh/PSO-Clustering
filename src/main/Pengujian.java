    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Kmeans.Cluster;
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
    
    Pengujian(){
        this.data =  new HashMap<String, Integer>();
    }
    
    protected void hasilPSO(DataInput input, ArrayList<Particle> particles, double gBest){
        String hasil = "";
        System.out.println("PSO RESULT");
        System.out.println("");

        for (int i = 0; i < input.jumlahParticle; i++) {
            Particle aParticle = particles.get(i);
            
            
            double fitness = aParticle.getPBest();
            if(fitness>input.getFitness()){
                Centroid centroid[] = aParticle.getCentroid();
                System.out.println("================SWARM==============");
                System.out.println("");
                System.out.println("solution: " + fitness);
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

        }
  
    } 
    
        
    protected void hasilKmeans(DataInput input, Cluster[] clusters){
        String hasil = "";
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("KMEANS RESULT");
        System.out.println("");
        for (int i = 0; i < clusters.length; i++) {
            ArrayList<ParseDocument> objCluster = clusters[i].getObjCluster();
            System.out.println("Cluster");
            for (int j = 0; j < objCluster.size(); j++) {
                ParseDocument pd = objCluster.get(j);
                String fileNameWithoutNumber = this.removeNumber(pd.getFileName());
                int count = this.data.containsKey(fileNameWithoutNumber) ? this.data.get(fileNameWithoutNumber) : 0;
                this.data.put(fileNameWithoutNumber, count + 1);
            }
            PRINT();
            this.data.clear();
        }
        
    } 
    
    public void PRINT(){
        System.out.println("------------CLUSTER----------");
        for (Map.Entry<String, Integer> filename : this.data.entrySet()) {
            System.out.println("filename: " + filename.getKey());
            System.out.println("value: " + filename.getValue());

        }
    }
    
    private String removeNumber(String filename){
        return filename.replace(".txt", "").replaceAll("\\d*$", "");
    }
    
}
