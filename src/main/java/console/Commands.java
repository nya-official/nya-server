/*
 * Description: List of commands accepted
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import console.commands.CommandCd;
import console.commands.CommandClr;
import console.commands.CommandEcho;
import console.commands.CommandHelp;
import console.commands.CommandJoin;
import console.commands.CommandLogin;
import console.commands.CommandLs;
import console.commands.CommandMkdir;
import console.commands.CommandRegister;
import console.commands.CommandTime;
import console.commands.CommandWhoAmI;
import java.util.ArrayList;
import org2.beryx.textio.TextTerminal;
import ratpack.handling.Context;
import user.User;

/**
 * Date: 2021-06-20
 * Place: Zwingenberg, Germany
 * @author brito
 */
public final class Commands {

    private final ArrayList<Command> 
            commandList = new ArrayList();

    public Commands() {
        addCommand(new CommandClr());
        addCommand(new CommandCd());
        addCommand(new CommandEcho());
        addCommand(new CommandJoin());
        addCommand(new CommandHelp());
        addCommand(new CommandLogin());
        addCommand(new CommandLs());
        addCommand(new CommandMkdir());
        addCommand(new CommandRegister());
        addCommand(new CommandTime());
        //addCommand(new CommandPing());
        addCommand(new CommandWhoAmI());
    }
    
    /**
     * Add a new command to our list
     * @param command 
     */
    public void addCommand(Command command){
        commandList.add(command);
    }

    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    /**
     * Runs a command when it is applicable
     * @param commandText
     * @param user
     * @param terminal 
     * @param context 
     * @return  
     */
    public DataExchange run(String commandText, User user,
            TextTerminal<?> terminal, Context context) {
        
        String commandTextLowerCase = commandText.toLowerCase();
        
        // go through all the available commands
        for(Command command : getCommandList()){
            if(command.isApplicable(commandTextLowerCase)){
                String keywordUsed = command.getApplicableKeyword(commandTextLowerCase);
                String commandLine = commandTextLowerCase.substring(keywordUsed.length()).trim();
                if(commandLine == null){
                    commandLine = "";
                }
                
                return command.actionInternal(commandLine, user, terminal, context);
            }
        }
        
        // not found, do nothing
        terminal.println("nya: Sorry, didn't understood this");
        return null;
    }
    
}
