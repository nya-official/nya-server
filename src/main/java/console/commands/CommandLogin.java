/*
 * Description: Login into an account
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
import user.UserType;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandLogin extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"login"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        if(commandText.length() < (5)){
            Message.messageError(terminal, "Needs to be a bit bigger, thanks");
            return null;
        }
        // login brito 123456
        String text = commandText;//.substring("login ".length());
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
        User userFound = core.users.get(username, password);
        if(userFound.getUserType() == UserType.anon){
            Message.messageError(terminal, "Nope, that didn't work. Sorry! (⌣́_⌣̀)");
            return null;
        }
        
        // repeated login
        if(userFound.getIdSHA1().equals(user.getIdSHA1())){
            Message.message(terminal, "Hey, trying to log as yourself again? ^_^");
            return null;
        }
        
        // got a user
        Message.message(terminal, "Hey, welcome back " + userFound.getId() + "! ^_^");
        
        DataExchange data = new DataExchange();
        data.addCookie(core.dbUserKey, userFound.getIdSHA1());
        data.addCookie(core.dbUserPassword, userFound.getPassword());
        // set the new user logged in
        data.setUser(userFound);
        
        // provide the data back to be written as a cookie
        return data;
    }

    @Override
    public String getDescription() {
        return "Log into the system";
    }

}
