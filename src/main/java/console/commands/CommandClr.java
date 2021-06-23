/*
 * Description: Clears the screen
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
public class CommandClr extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"clr", "cls", "clear"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        terminal.resetToBookmark("MAIN");
        return null;
    }

    @Override
    public String getDescription() {
        return "Clears the screen";
    }

}
