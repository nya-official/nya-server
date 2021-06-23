/*
 * Description: Join a channel
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
public class CommandJoin extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"join"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        
        terminal.println(utils.time.getDateTimeISO());
        return null;
    }

    @Override
    public String getDescription() {
        return "Join a channel";
    }

}
