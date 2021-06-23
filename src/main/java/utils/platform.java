/*
 * Platform specific functions
 *
 * Copyright (c) TripleCheck
 * License: Proprietary
 * http://triplecheck.tech
 */

package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Max Brito
 * @date 2020-08-25
 * @location Zwingenberg, Germany
 */
public class platform {

    @SuppressWarnings("CallToPrintStackTrace")
    public static String executeCommand(final String... command) {
		
        StringBuilder output = new StringBuilder();
 
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            InputStreamReader i = new InputStreamReader(p.getInputStream());
            BufferedReader reader = 
                new BufferedReader(i);

            String line;			
            while ((line = reader.readLine())!= null) {
                    output.append(line).append("\n");
                    //System.out.println(line);
            }

            i.close();
            reader.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return output.toString();
    }
    
    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
    
}
