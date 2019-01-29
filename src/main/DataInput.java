/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author caca
 */
public class DataInput {
    String path;
    int jumlahParticle, jumlahCentroid, iterasi;
    double velocity, fitness, r1, r2;
    String algorithmName;
    
    public DataInput(){}
    
    public void setPath(String path){
        this.path = path;
    }
    
    public String getPath(){
        return this.path;
    }
    
    public void setJumlahParticle(int jumlahParticle){
        this.jumlahParticle = jumlahParticle;
    }
    
    public int getJumlahParticle(){
        return this.jumlahParticle;
    }
    
    public void setJumlahCentroid(int jumlahCentroid){
        this.jumlahCentroid = jumlahCentroid;
    }
    
    public int getJumlahCentroid(){
        return this.jumlahCentroid;
    }
    
    public void setIterasi(int iterasi){
        this.iterasi = iterasi;
    }
    
    public int getIterasi(){
        return this.iterasi;
    }
    
    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public double getMaxVelocity(){
        return this.velocity;
    }
    
    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    
    public double getFitness(){
        return this.fitness;
    }
    
    public void setR1(double r1){
        this.r1 = r1;
    }
    
    public double getR1(){
        return this.r1;
    }
    
    public void setR2(double r2){
        this.r2 = r2;
    }
    
    public double getR2(){
        return this.r2;
    }
    
    public void setAlgorithm(String algorithm){
        this.algorithmName = algorithm;
    }
    
    public String getAlgorithm(){
        return this.algorithmName;
    }
            
}
