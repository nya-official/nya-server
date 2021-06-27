/*
 * Description: Creates a directory
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import console.Command;
import console.DataExchange;
import console.Message;
import org2.beryx.textio.TextTerminal;
import user.User;
import user.UserItem;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandMkdir extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"mkdir"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        
        String cleanText = utils.text.onlyValidChars(commandText);
        
        if(cleanText.length() != commandText.length()){
            Message.messageError(terminal, "Sorry! Please only simple characters inside folder names.");
            return null;
        }
        
        // get the current folder
        UserItem folderCurrent = user.getFolderCurrent();
        folderCurrent.createFolder(commandText);
        // save everything
        user.saveToDisk();
        return null;
    }

    @Override
    public String getDescription() {
        return "Makes a directory";
    }

}
