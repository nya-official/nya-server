/*
 * Description: Main configuration
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package base;

import static base.core.dateFormat;
import static base.core.fileGenesis;
import channel.ChannelManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import user.Users;
import java.io.File;


/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Config {

    // setup the coins and wallets
    @Expose
    private String
            idUsers,
            idCoins,
            idChannels;

    
    public String getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(String idUsers) {
        this.idUsers = idUsers;
    }

    public String getIdCoins() {
        return idCoins;
    }

    public void setIdCoins(String idCoins) {
        this.idCoins = idCoins;
    }

    public String getIdChannels() {
        return idChannels;
    }

    public void setIdChannels(String idChannels) {
        this.idChannels = idChannels;
    }
    
    /**
     * Setup the initial folders
     */
    public void setup(){
        // create the initial folders
        utils.files.mkdirs(core.folderRoot);
        utils.files.mkdirs(core.folderDB);
        
        // get the users
        Users users = Users.jsonImportFile(Users.getFileUsers());
        
        if(users != null){
            core.users = users;
        }else{
            core.users = new Users();
        }
        // get it started
        core.users.start();
        
        // get channels up and running
        core.channelManager = ChannelManager.loadFromDisk();
        
    }
    
    public void setupTest(){
        // call the initial setup
        setup();
        // create the initial folder
        utils.files.mkdirs(core.folderTest);
    }
    
    /**
     * Deletes the test folder completely
     */
    public void deleteTest(){
        utils.files.deleteDir(core.folderTest);
    }

     @SuppressWarnings("CallToPrintStackTrace")
    public static Config jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static Config jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return  gson.fromJson(textJSON, Config.class);
        }catch(JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
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

    /**
     * Save the config to IPFS and to the genesis block on disk
     */
    public void saveToIPFS() {
        String text = jsonExport();
        String idIPFS = core.ipfs.putContentAsText(text);
        core.genesis.setGenesis(idIPFS);
        String output = core.genesis.jsonExport();
        utils.files.SaveStringToFile(fileGenesis, output);
    }

    
    
}
