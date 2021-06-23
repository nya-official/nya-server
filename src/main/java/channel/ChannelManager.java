/*
 * Description: Manages the available channels on this server
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import java.util.ArrayList;

/**
 * Date: 2021-06-23
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class ChannelManager {
    
    private ArrayList<Channel> channels = new ArrayList();

    /**
     * Provides the list of available channels
     * @return 
     */
    public ArrayList<Channel> getChannels() {
        return channels;
    }
    
    /**
     * Add a new channel
     * @param channelToAdd 
     */
    public void addChannel(Channel channelToAdd){
        for(Channel channel : channels){
            if(channel.getId().equals(channelToAdd.getId())){
                return;
            }
        }
        // add it up
        channels.add(channelToAdd);
    }
    
}
