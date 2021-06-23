/*
 * Description: Messages from the console
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import org2.beryx.textio.TextTerminal;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Message {
    
    private static String 
            messageDefaultError = "Sorry, didn't understood this",
            header = "nya: ";

    public static String getMessageDefaultError() {
        return messageDefaultError;
    }

    public static void setMessageDefaultError(String messageDefaultError) {
        Message.messageDefaultError = messageDefaultError;
    }

    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        Message.header = header;
    }

    public static void messageError(TextTerminal<?> terminal, String message){
        terminal.println(header + message);
    }
    
    public static void messageErrorDefault(TextTerminal<?> terminal){
        terminal.println(header + messageDefaultError);
    }

    public static void message(TextTerminal<?> terminal, String message) {
        terminal.println(header + message);
    }
    
    
    
}
