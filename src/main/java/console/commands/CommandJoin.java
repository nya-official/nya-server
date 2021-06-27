/*
 * Description: Join a channel
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import base.core;
import channel.Channel;
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
public class CommandJoin extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"join"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        if(commandText.startsWith("#") == false){
            Message.messageError(terminal, "Error, channels always start with \"#\" like #example");
            return null;
        }
        // try to first find the channel in case it exists
        Channel channel = core.channelManager.find(commandText);
        // does not exist? Create a new one
        if(channel == null){
            // setup the logged user as admin, even if anon
            channel = core.channelManager.create(user, commandText);
        }
        
        Message.message(terminal, "Joined " + commandText);
        return null;
    }

    @Override
    public String getDescription() {
        return "Join a channel";
    }

}
