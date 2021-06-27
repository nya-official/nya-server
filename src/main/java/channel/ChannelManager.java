/*
 * Description: Manages the available channels on this server
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import base.core;
import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import java.io.File;
import java.util.ArrayList;
import user.User;

/**
 * Date: 2021-06-23
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class ChannelManager {
    
    @Expose
    private final ArrayList<Channel> channels = new ArrayList();

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
        // save this list to disk
        saveToDisk();
    }

    /**
     * Tries to find a given channel
     * @param channelName
     * @return null in case it does not exist
     */
    public Channel find(String channelName) {
        // make sure it has some content
        if(Channel.isValidName(channelName) == false){
            return null;
        }
        
        // search for it
        for(Channel channel : this.getChannels()){
            // we don't care about lower or upper case. Reduce squatting
            if(channel.getId().equalsIgnoreCase(channelName)){
                return channel;
            }
        }
        // nothing found
        return null;
    }

    /**
     * Create the channel
     * @param user
     * @param channelName
     * @return 
     */
    public Channel create(User user, String channelName) {
        // make sure it has some content
        if(Channel.isValidName(channelName) == false){
            return null;
        }
        // create the channel
        Channel channel = new Channel();
        channel.setId(channelName);
        channel.setIdOwner(user.getIdSHA1());
        channel.setDateCreated(System.currentTimeMillis());
        channel.setDateLastModified(System.currentTimeMillis());
        channel.setFreshCreated(true);
        
        // add this channel to our list
        addChannel(channel);
        
        return channel;
    }

    /**
     * Save this object to disk
     */
    private void saveToDisk() {
        String text = this.jsonExport();
        File file = getFile();
        utils.files.SaveStringToFile(file, text);
    }
    
    public static File getFile(){
        return new File(core.folderDB, core.dbChannelsJSON);
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
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static ChannelManager jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static ChannelManager jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return gson.fromJson(textJSON, ChannelManager.class);
        }catch(JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Load from disk automatically
     * @return 
     */
    public static ChannelManager loadFromDisk() {
        // load one from disk
        ChannelManager chans = 
                ChannelManager.jsonImportFile(ChannelManager.getFile());
        // nothing was created? Create a new one then
        if(chans == null){
            chans = new ChannelManager();
        }
        return chans;
    }
    
}
