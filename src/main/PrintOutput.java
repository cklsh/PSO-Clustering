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
import preprocessing.parser.ParseDocument;

/**
 *
 * @author caca
 */
public class PrintOutput {
            
    public PrintOutput(){}
    
    public void PRINTUpdate(ArrayList<String> bagOfTerm, int[] currPosition, int[][] newPosition, double[][] currVelocity, int[] GBest, int[] PBest, int j, int k){
        System.out.println("current velocity        " + currVelocity[j][k]);
        System.out.println("gbest /term             " + GBest[k]);
        System.out.println("pbest /term             " + PBest[k]);
        System.out.println("current position        " + currPosition[k]);
        System.out.println("next velocity           " + currVelocity[j][k]);
        System.out.println(bagOfTerm.get(k));
        System.out.println("new position            " + newPosition[j][k]);
    }
    
    public String PRINTPSOResult(DataInput input, ArrayList<Particle> particles, double gBest, Centroid[] gBestPosition){
        String result="";
        result+= "Best Particle \n\n";
        for (int i = 0; i < gBestPosition.length; i++) {
            result+= "Cluster " + "\n\n";
            
            ArrayList<ParseDocument> objCluster = gBestPosition[i].getObjCluster();
            for (int j = 0; j < objCluster.size(); j++) {
                result+= objCluster.get(j).getFileName() + "\n";
            }
        }
        
        result+= "==============================" + "\n";
        
        
           
        
        
        for (int i = 0; i < input.getJumlahParticle(); i++) {
            
            Particle aParticle = particles.get(i);
            Centroid centroid[] = aParticle.getCentroid();
            result+=("particle " + i + "\n" + "=======================" + "\n");

            for (int j = 0; j < input.getJumlahCentroid(); j++) {
                result+=("cluster" + "\n");

                ArrayList<ParseDocument> apd = centroid[j].getObjCluster();
                for (int k = 0; k <apd.size(); k++) {
                   ParseDocument pd = apd.get(k);
                   result+=(pd.getFileName() + "\n");
                }
                result+=("=========" +"\n");

            }
            double fitness = aParticle.getPBest();
        }
        
        result+=("solution" + "\n");
        int ct = 0;
        for (int i = 0; i < input.getJumlahParticle(); i++) {
              
            Particle aParticle = particles.get(i);
            Centroid centroid[] = aParticle.getCentroid();
            double fitness = aParticle.getPBest();
            for (int j = 0; j < input.getJumlahCentroid(); j++) {
                if(fitness > input.getFitness()){
                    ct++;
                    System.out.println("solution" + "\n");
                    result+=("new cluster" + "\n");
                    ArrayList<ParseDocument> apd = centroid[j].getObjCluster();
                    for (int k = 0; k <apd.size(); k++) {
                        ParseDocument pd = apd.get(k);
                        result+=(pd.getFileName() + "\n");
                        //System.out.println(pd.getFileName());  
                    }
                    result+=("\n");
                }
                if(fitness > input.getFitness()){
                      result+=("=========" +"\n");
                }
            }
        }
        result+= "gBest" + gBest + "\n";
        if(ct==0) {
            return result + "\n" + "\n" + "no solution found";
        }
        else{
            return result;
        }
    }
    

    public String PRINTKmeansResult(DataInput input, Cluster[] clusters){
        String result = "";
        for (int i = 0; i < clusters.length; i++) {
            ArrayList<ParseDocument> apd = clusters[i].getObjCluster();
            result+=("Cluster" + "\n");
            for (int j = 0; j < apd.size(); j++) {
               ParseDocument pd = apd.get(j);
               result+=(pd.getFileName() + "\n");
            }
            result+=("=========" +"\n");
        }
        return result;
    }
       
       
}
