/*
 * Description: List command (similar to UNIX ls)
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
public class CommandLs extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"ls"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        if(core.channelManager.getChannels().isEmpty()){
            Message.messageError(terminal, "Sorry, no channels yet");
            return null;
        }
        for(Channel channel : core.channelManager.getChannels()){
            listChannel(channel, terminal);
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "List the items on the current folder";
    }

    private void listChannel(Channel channel, TextTerminal<?> terminal) {
        Message.message(terminal, channel.getId() + " | " + channel.getDescription());
    }

}
