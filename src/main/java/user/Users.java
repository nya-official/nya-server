/*
 * Description: List of users inside the platform
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package user;

import static base.Log.log;
import base.core;
import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import database.Folder;
import java.io.File;
import java.util.HashSet;

/**
 * Date: 2021-06-15
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Users {
    
    @Expose
    // SHA1 and IPFS signatures
    private final HashSet<UserId> 
            listId = new HashSet<UserId>();
    
    
    /**
     * Creates an anon user
     * @return 
     */
    public static User getAnon() {
        User user = new User();
        user.setId("anon");
        user.setPassword("anon");
        user.setDescription("Anonymous day. Anonymous time.");
        user.setUserStatus(UserStatus.active);
        user.setUserType(UserType.anon);
        user.setPicProfileIPFS("QmPWeodGn1ofjvf9NLHv2fBTYc27wsvqtJUS3gdNXbLNxN");
        return user;
    }


    public Users() {
    }

    /**
     * Reads the genesis block for a list of users. When this list does not
     * exist, then create a new root user and get it available
     */
    public void start() {
        //addUsers();
        log("Users available: " + this.listId.size());
    }

    public User createRootUser() {
        // create the root admin user
        User user = new User();
        user.setId("lain");
        user.setPassword("verytops3cretpasswordth1stime!");
        user.setDescription("Present day. Present time.");
        user.setUserStatus(UserStatus.active);
        user.setPicProfileIPFS("QmPWeodGn1ofjvf9NLHv2fBTYc27wsvqtJUS3gdNXbLNxN");
        return user;
    }

    /**
     * Map SHA1 of id and IPFS of the whole JSON
     * @return 
     */
    public HashSet<UserId> getListId() {
        return listId;
    }
    
    public void addUser(User user) {
        UserId userId = new UserId(user.getIdSHA1(), user.getIdIPFS());
        listId.add(userId);
        user.saveToDisk();
        // save this list on disk
        saveToDisk();
    }

//    public void addUser(String SHA1, String idIPFS) {
//        listId.put(SHA1, idIPFS);
//        
//        // save this list on disk
//        saveToDisk();
//    }

//    /**
//     * Add users based on the genesis block
//     */
//    private void addUsers() {
//        String idIPFS = core.config.getIdUsers();
//        if(idIPFS == null){
//            // create a new one
//            log("No users found, adding root user");
//            User root = createRootUser();
//            root.addToIPFS();
//            this.addUser(root.getIdSHA1(), root.getIdIPFS());
//            this.saveToIPFS();
//            return;
//        }
//        // add all the users from the id list
//        String text = core.ipfs.getContentAsString(idIPFS);
//        Users users = Users.jsonImport(text);
//        if(users == null){
//            log("ERROR Users-78: Invalid data for users");
//            System.exit(-1);
//        }
//        log("Adding users");
//        // add all users
//        this.listId = users.getListId();
//    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static Users jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static Users jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return  gson.fromJson(textJSON, Users.class);
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
    
//    /**
//     * Save the current state of users to IPFS
//     */
//    private void saveToIPFS() {
//        String text = this.jsonExport();
//        String idIPFS = core.ipfs.putContentAsText(text);
//        core.config.setIdUsers(idIPFS);
//        core.config.saveToIPFS();
//    }

    /**
     * Get an user from our archive
     * @param username
     * @param password
     * @return 
     */
    public User get(String username, String password) {
        String sha1Username = utils.SHA1.calculateFromText(username);
        String sha1Password = utils.SHA1.calculateFromText(password);
                
        User userFound = User.getUserSHA1(sha1Username);
        if(userFound.getUserType() == UserType.anon){
            // just return an anon
            return userFound;
        }
        
        // check the password, needs to match
        if(userFound.getPassword().equalsIgnoreCase(sha1Password)){
            return userFound;
        }else{
            return Users.getAnon();
        }
    }

    /**
     * Checks if a given user already exists
     * @param username
     * @return 
     */
    public boolean exists(String username) {
        String sha1 = utils.SHA1.calculateFromText(username);
        for(UserId userId : listId){
            if(userId.getIdSHA1().equals(sha1)){
                return true;
            }
        }
        return false;
    }

    private void saveToDisk() {
        String text = this.jsonExport();
        utils.files.SaveStringToFile(getFileUsers(), text);
    }
    
    public static File getFileUsers(){
        return new File(core.folderDB, core.dbUsersJSON);
    }
    
    

}
