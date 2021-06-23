/*
 * Description: Show the current logged user
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import console.Command;
import console.DataExchange;
import org2.beryx.textio.TextTerminal;
import user.User;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandWhoAmI extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"whoami"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        terminal.println(user.getId());
        return null;
    }

    @Override
    public String getDescription() {
        return "Currently logged user";
    }

}
