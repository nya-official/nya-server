/*
 * Description: <add description>
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import java.util.ArrayList;

/**
 * Date: 2021-06-15
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Channel {

    private String 
            id,             // short id
            description,    // one-line description
            idOwner;        // who can add/delete things
  
    
    // those who have subscribed to the channel
    private final ArrayList<Subscriber> 
            subscribers = new ArrayList();
    
    private ChannelType 
            channelType = ChannelType.chat;

    private final ArrayList<Message> 
            messages = new ArrayList();
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }
    
    public void addMessage(Message message){
        messages.add(message);
    }
    
    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    
    
        
}
