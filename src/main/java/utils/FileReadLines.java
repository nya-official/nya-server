/**
 * A model to read a large text file from disk, processing
 * each line at maximum speed.
 */

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Max Brito
 * @date 2015-01-10
 * @location Darmstadt, Germany
 */
public class FileReadLines {
  
    // internal variables
    private BufferedReader reader = null;
    private File fileInput = null;
    
    // mark the offset on disk
    private long 
            currentOffset = 0,
            currentLine = 0;
    
    
    public FileReadLines(final File textFileTarget){
        try {
            // get the file where we want to store or read the variables from
            fileInput = textFileTarget;
           
            // doublecheck if the file was really created
            if(fileInput.exists() == false){
                System.out.println("FRLS52 - Critical error, unable to create"
                        + " variable file: " + fileInput.getAbsolutePath());
                return;
            }
           
            // initialise the objects from where to read text
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(fileInput));
            reader = new BufferedReader(fileReader);
            
            
        } catch (Exception ex) {
            Logger.getLogger(FileReadLines.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        }
    }
      
    /**
     * Provides the next line on our text file
     * @return The next line that was available, or null when nothing was read
     * @throws java.io.IOException
     */
    public synchronized StringBuilder getNextLine() throws IOException{
           final String line = reader.readLine();           
            // increase the line count
           if(line != null){
                currentLine++;
                increaseCounterOffset(line.length() + 1);
                return new StringBuilder().append(line);
           }else{
               return null;
           }
    }

    private synchronized void increaseCounterOffset(final long value){
        currentOffset += value;
    }
    
    public synchronized long getCurrentOffset() {
        return currentOffset;
    }

    public long getCurrentLine() {
        return currentLine;
    }
    
    /**
     * Close all the files that were open
     */
    public void close(){
        try {
            // close the streams
            if(reader != null){
                reader.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileReadLines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
