/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Kmeans.Cluster;
import Kmeans.Kmeans;
import PSO.Swarm;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTextArea;
import preprocessing.parser.Document;
import preprocessing.parser.ParseDocument;
import preprocessing.parser.Parser;
import preprocessing.parser.Term;
import preprocessing.trie.DFS;
import preprocessing.trie.Trie;

/**
 *
 * @author caca
 */
public class Tester {

    public static final String DOCUMENT_PATH = "/home/caca/Documents/dataset";
    public static final String PATTERN  = "[\\P{Alpha}+]";

    public String main(DataInput input) throws IOException {
        if(input.algorithmName == "PSO"){
            Document[] docs = Document.processDocument(input.getPath());

            ParseDocument[] parseDoc = new ParseDocument[docs.length];
            Trie trie = new Trie<ArrayList<Term>>();

            for (int i = 0; i < docs.length; i++) {
                Parser parser = new Parser(docs[i], PATTERN);
                parseDoc[i] = new ParseDocument(docs[i].fileName, parser.parse());
                HashMap<String, Term> terms = parseDoc[i].getTerm();

                trie.putTerms(terms);
            }

            ArrayList bagOfTerm = getBagOfTerm(trie);

            float start = System.nanoTime();
            Swarm swarm = new Swarm(input, parseDoc, trie, bagOfTerm); 
            float end = System.nanoTime();

            System.out.println("Total time: " + ((end-start)) + " second");

            Pengujian pengujian = new Pengujian();
            pengujian.hasilPSO(input, swarm.getParticles(), swarm.getGBest());

            PrintOutput print = new PrintOutput();
            String solutions = print.PRINTPSOResult(input, swarm.getParticles(), swarm.getGBest(), swarm.getGBestPosition());

            return solutions;
        }
        else{
            Document[] docs = Document.processDocument(input.getPath());

            ParseDocument[] parseDoc = new ParseDocument[docs.length];
            Trie trie = new Trie<ArrayList<Term>>();

            for (int i = 0; i < docs.length; i++) {
                Parser parser = new Parser(docs[i], PATTERN);
                parseDoc[i] = new ParseDocument(docs[i].fileName, parser.parse());
                HashMap<String, Term> terms = parseDoc[i].getTerm();

                trie.putTerms(terms);
            }

            ArrayList bagOfTerm = getBagOfTerm(trie);

            float start = System.nanoTime();
            Kmeans kmeans = new Kmeans(input, parseDoc, trie, bagOfTerm);
            Cluster[] clusters = new Cluster[input.jumlahCentroid];
            clusters = kmeans.doIteration();
            float end = System.nanoTime();

            System.out.println("Total time: " + ((end-start) / 1000000000) + " second");
            
            Pengujian pengujian = new Pengujian();
            pengujian.hasilKmeans(input, clusters);
            
            PrintOutput print = new PrintOutput();
            String solutions = print.PRINTKmeansResult(input, clusters);

            return solutions;
        }
    }
    
    private ArrayList getBagOfTerm(Trie trie){
        DFS dfs = new DFS();
        ArrayList bagOfTerm = dfs.DFSProcess(trie.getRoot().children);
        return bagOfTerm;

    }
}
