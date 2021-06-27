/*
 * Description: Platform specific functions
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Date: 2020-08-25
 * Place: Zwingenberg, Germany
 * @author brito
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
