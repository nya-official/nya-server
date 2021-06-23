/*
 * Description: Show the current time
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.commands;

import console.Command;
import console.DataExchange;
import console.Message;
import org2.beryx.textio.TextTerminal;
import user.User;
import utils.internet;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CommandPing extends Command{

    @Override
    public String[] getKeywords() {
        return new String[]{"ping"};
    }

    @Override
    public DataExchange action(String commandText, User user, TextTerminal<?> terminal) {
        String target = commandText.substring("ping ".length());
        boolean canConnect = internet.sendPingRequest(target);
        if(canConnect){
            Message.message(terminal, "Success! Can reach there! ^_^");
        }else{
            Message.message(terminal, "Not able to reach there! (~_~;)");
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Ping another machine elsewhere";
    }

}
