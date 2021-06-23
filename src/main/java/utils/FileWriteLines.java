/*
 * Makes it easier to handle portions of text being
 * written to a text file.
 */

package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Max Brito
 * @date 2015-01-05
 * @location Darmstadt, Germany
 */
public class FileWriteLines {

    private BufferedWriter out;
    private OutputStreamWriter fileWriter;
    
    // should we hold all data into memory or not?
    private boolean 
            isOpen = false;
    
    public FileWriteLines(final File resultFile){
    try {
        fileWriter = new OutputStreamWriter(new FileOutputStream(resultFile), "UTF-8");
        out = new BufferedWriter(fileWriter);
        isOpen = true;
        } catch (IOException e){
                System.err.println("Error: " + e.getMessage());
        }
    }
 
    /**
     * Creates a new object but appends new lines to the end of a text file
     * @param resultFile
     * @param append 
     */
    public FileWriteLines(final File resultFile, Boolean append){
    try {
        // does the folder for this file exists?
        if(resultFile.getParentFile().exists() == false){
            // create one
            utils.files.mkdirs(resultFile.getParentFile());
        }
        
        // try to create the file if it does not exist
        if(resultFile.exists() == false){
            utils.files.touch(resultFile);
        }
        
        fileWriter = new OutputStreamWriter(new FileOutputStream(resultFile, append), "UTF-8");
        out = new BufferedWriter(fileWriter);
        isOpen = true;
        } catch (IOException e){
                System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Physically write this text to disk
     * @param text 
     */
    private void saveToDisk(final String text){
        try {
            if(out != null && isOpen){
                out.write(text);
                //out.flush();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FileWriteLines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Adds a piece of text to our buffer
     * @param text 
     */
    public void write(final String text){
        saveToDisk(text);
    }

    public void flush(){
        try {
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileWriteLines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Close the buffer and write changes to disk
     */
    public void close(){
    try {
       
        isOpen = false;
        // flush everything from memory
        out.flush();
        // close the stream
        out.close();
        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }

}
