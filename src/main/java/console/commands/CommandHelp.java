/*
 * Description: Clears the screen
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import base.core;
import console.Command;
import console.DataExchange;
import org2.beryx.textio.TextTerminal;
import user.User;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandHelp extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"help"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        terminal.println();
        for(Command command : core.commands.getCommandList()){
            terminal.println(
                    command.getKeywords(user).toUpperCase()
                    + ": " 
                    + command.getDescription(user));
        }
        terminal.println();
        return null;
    }

    @Override
    public String getDescription() {
        return "Provides a list of commands";
    }

}
