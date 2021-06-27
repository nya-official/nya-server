/*
 * Description: Register an account
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import base.core;
import console.Command;
import console.DataExchange;
import console.Message;
import org2.beryx.textio.TextTerminal;
import user.User;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandRegister extends Command{

    private final String keyword = "register ";
    
    @Override
    public String[] getKeywords() {
        return new String[]{keyword};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        if(commandText.length() < (6)){
            Message.messageError(terminal, "Needs to be a bit bigger, thanks!");
            return null;
        }
        // login brito 123456
        String text = commandText;//.substring(keyword.length());
        // break into two portions
        String[] item = text.split(" ");
        if(item.length != 2){
            Message.messageError(terminal, "Hey, this needs two phrases. One for username another for password. (no spaces please) ^_^");
            return null;
        }
        
        String username = item[0];
        String password = item[1];
        
        //Message.message(terminal, "user: " + username + " | " + password);
        
        // try to find a user like this
        if(core.users.exists(username)){
            Message.messageError(terminal, "Nope, that one already exists. Sorry! (⌣́_⌣̀)");
            return null;
        }
        // check the password quality
        if(utils.text.isEmpty(password) || password.length() < 6){
            Message.messageError(terminal, "Please choose a better password (⌣́_⌣̀)");
            return null;
        }
        
        // create a new user
        User userNew = new User();
        userNew.setId(username);
        userNew.setPassword(utils.SHA1.calculateFromText(password));
        
        // add it to the users
        core.users.addUser(userNew);
        
        // got registered
        Message.message(terminal, "Registered with succcess! ^_^");
        
        DataExchange data = new DataExchange();
        data.setUser(userNew);
        data.addCookie(core.dbUserKey, userNew.getIdSHA1());
        data.addCookie(core.dbUserPassword, userNew.getPassword());
        
        // provide the data back to be written as a cookie
        return data;
    }

    @Override
    public String getDescription() {
        return "Log into the system";
    }

}
