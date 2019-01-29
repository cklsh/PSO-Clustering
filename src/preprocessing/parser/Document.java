package preprocessing.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author caca
 */
public class Document {
    
    public String fileName;
    String text;
    String docId;
    
    public Document(File file, String docId) throws IOException{
        this.fileName = file.getName();
        this.text = generateText(file);
        this.docId = docId;
    }
    
    public static Document[] processDocument(String path) throws FileNotFoundException, IOException{
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        Document[] doc = new Document[listOfFiles.length];
               
        try{
            for(int i = 0; i < listOfFiles.length; i++){
                Document myDoc = new Document(listOfFiles[i], listOfFiles[i].getName());
                doc[i] = myDoc;
            }
        }catch(Exception e){
            System.out.println("error");
        }
        
        return doc;
    }
    
    public String generateText(File file) throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new FileReader(file));
            
        String line;
        StringBuffer sb = new StringBuffer();
           
        try{
            while((line = in.readLine()) != null)
            {
                sb.append(line);
            }
            in.close();
        }catch(Exception e){
            System.out.println("err");
        }
        return sb.toString();
    }
    
}