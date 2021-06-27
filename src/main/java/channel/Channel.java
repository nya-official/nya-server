/*
 * Description: <add description>
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import base.core;
import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import utils.text;

/**
 * Date: 2021-06-15
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Channel {

    @Expose
    private String 
            id,             // short id
            idSHA1,         // SHA1 identification of the id
            description,    // one-line description
            idOwner;        // who can add/delete things
    
    @Expose
    // those who have subscribed to the channel
    private final ArrayList<Subscriber> 
            subscribers = new ArrayList();
    
    @Expose
    private ChannelType 
            channelType = ChannelType.chat;

    @Expose
    private final ArrayList<String> 
            messagesIds = new ArrayList();
    
    @Expose
    private int messageLimit = core.maxMessagesPerDefault;
    
    @Expose
    private boolean freshCreated = true;
    
    @Expose
    private long 
            dateCreated,
            dateLastModified;

    public String getIdSHA1() {
        return idSHA1;
    }

    public int getMessageLimit() {
        return messageLimit;
    }

    public void setMessageLimit(int messageLimit) {
        this.messageLimit = messageLimit;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.idSHA1 = utils.SHA1.calculateFromText(id);
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
        //messages.add(message);
    }
    
    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }

    public ArrayList<String> getMessagesIds() {
        return messagesIds;
    }

    public boolean isFreshCreated() {
        return freshCreated;
    }

    public void setFreshCreated(boolean freshCreated) {
        this.freshCreated = freshCreated;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(long dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
    
    
    /**
     * Verifies if a name for a channel is valid or not
     * @param channelName
     * @return 
     */
    public static boolean isValidName(String channelName){
         // make sure it has some content
        if(utils.text.isEmpty(channelName) 
                || channelName.contains(" ")
                || channelName.length() > core.lenghtMaxChannelName
                || channelName.length() < 2){
            return false;
        }
        
        // needs to start with a #
        if(channelName.startsWith("#") == false){
            return false;
        }
        // remove the #
        channelName = channelName.substring(1);
        
        // make sure it follows our rules
        String result = text.onlyValidChars(channelName);
        // final test
        return result.length() == channelName.length();
    }
    
     /**
     * Convert this object to a JSON representation
     * @return 
     */
    public String jsonExport() {
        Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                //.excludeFieldsWithoutExposeAnnotation()
                //.enableComplexMapKeySerialization()
                //.setLenient()
                .setPrettyPrinting()
                .create();
        
        return gson.toJson(this);
    }
    
        
}
