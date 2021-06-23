/*
 * Description: Log messages to disk and console
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package base;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Log {

    public static void log(String message){
        String timeText = utils.time.getDateTimeISO();
        System.out.println(timeText + " | " + message);
    }
    
}
