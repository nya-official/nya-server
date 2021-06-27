/*
 * Description: List command (similar to UNIX ls)
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import console.Command;
import console.DataExchange;
import org2.beryx.textio.TextTerminal;
import user.User;
import user.UserItem;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandLs extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"ls"};
    }

    @Override
    public String getDescription() {
        return "List the items on the current folder";
    }

    
    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        
            UserItem folder = user.getFolderCurrent();
            for(UserItem item : folder.getTree()){
                terminal.println(item.getFilename());
            }
        
        return null;
    }

}
