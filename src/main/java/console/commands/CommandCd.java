/*
 * Description: Change directory
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
public class CommandCd extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"cd"};
    }

    @Override
    public String getDescription() {
        return "Change directory";
    }

    
    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
            // get the current folder
            UserItem folderCurrent = user.getFolderCurrent();
            
            // go down one folder
            if(commandText.equals("..")){
                changeToLowerDirectory(folderCurrent, user);
                return null;
            }
            
            UserItem folderToChange = folderCurrent.getFolder(commandText);
            // when null, just means we did not found it
            if(folderToChange == null){
                Message.messageError(terminal, "Sorry sir, didn't found that");
                return null;
            }
            
            // change to that folder
            user.setFolderCurrent(folderToChange);
            //folderToChange.setFolderParent(folderCurrent);
            
        return null;
    }

    private void changeToLowerDirectory(UserItem folderCurrent, User user) {
        
        String pathCurrent = folderCurrent.getPath();
        
        // already on the root, can't go down
        if(pathCurrent.equals("/")){
            return;
        }
        
        // go below
        if(utils.text.countMatches("/", pathCurrent) == 1){
            user.setFolderCurrent(user.getFilesystem());
            return;
        }
        
        // get the corresponding folder below
        int pos = folderCurrent.getPath().lastIndexOf("/");
        String pathBelow = folderCurrent.getPath().substring(0, pos);
        UserItem folder = user.findFolder(pathBelow);
        // go to the next one down
        user.setFolderCurrent(folder);
    }

}
