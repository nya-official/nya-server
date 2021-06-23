/*
 * Description: A command accepted by the processor
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import org2.beryx.textio.TextTerminal;
import ratpack.handling.Context;
import user.User;

/**
 * Date: 2021-06-20
 * Place: Zwingenberg, Germany
 * @author brito
 */
public abstract class Command {

    // one-line description for this command
    public abstract String getDescription();
    
    // get the syntax for a specific command
    //public abstract String getSyntax();
    
    // keywords need to be lower case
    public abstract String[] getKeywords();
    
    public DataExchange actionInternal(String commandText, User user, 
            TextTerminal<?> terminal, Context context){
        // run the normal action as expected
        DataExchange data = this.action(commandText, user, terminal);
        // process the data output
//        processResult(data, context, user);
        return data;
    }
    public abstract DataExchange action(String commandText, User user, TextTerminal<?> terminal);
    
    /**
     * Checks if this command is applicable
     * @param commandLowercase
     * @return 
     */
    public boolean isApplicable(String commandLowercase){
        for(String commandApplicable : getKeywords()){
            if(commandLowercase.startsWith(commandApplicable)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Provides the keyword that was used
     * @param commandLowercase
     * @return 
     */
    public String getApplicableKeyword(String commandLowercase){
        for(String commandApplicable : getKeywords()){
            if(commandLowercase.startsWith(commandApplicable)){
                return commandLowercase;
            }
        }
        return null;
    }


    public String getKeywords(User user) {
        return utils.text.arrayToString(this.getKeywords(), " | ");
    }

    /**
     * Provide a description for this command
     * @param user
     * @return 
     */
    public String getDescription(User user) {
        return getDescription();
    }

//    /**
//     * Post-processing for the DataExchange object
//     * @param data
//     * @param context
//     * @param user 
//     */
//    private void processResult(DataExchange data, Context context, User user) {
//        // no need to continue when this is null
//        if(data == null || context == null){
//            return;
//        }
//        // check the data
//        if(data.getData().isEmpty()){
//            return;
//        }
//        // write this data to the cookies
//        for(String key : data.getData().keySet()){
//            String value = data.getData().get(key);
//            context.getResponse().cookie(key, value);
//        }
//        context.getResponse().send();
//    }
    
}
