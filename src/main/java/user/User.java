/*
 * Description: The user of the exchange
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package user;

import base.core;
import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import database.FolderSpecific;
import java.io.File;
import java.util.TreeSet;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class User {

        @Expose
        private String 
            id,
            idSHA1,
            password,           // never the clear-text password, only SHA1
            description,
            picProfileIPFS; // IPFS id for profile pic
        
        @Expose
        private UserType
            userType = UserType.normal;
        
        @Expose
        private long
            dateCreation,
            dateLastAccess;
        
        private String
            idIPFS = null;

        @Expose
        private final TreeSet<String> 
            languages = new TreeSet(), // preferred conversation languages
            tags = new TreeSet(), // skills, attributes
            xpasswords = new TreeSet(); // to unlock x-file.xyz content
        
        @Expose
        private UserStatus 
            userStatus = UserStatus.active;

    public User() {
        // add the time creation
        dateCreation = System.currentTimeMillis();
        dateLastAccess = System.currentTimeMillis();
        // use english as default
        languages.add("en");
    }
        
        
        
        
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
        
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(utils.text.isEmpty(id)){
            return;
        }
        this.id = id;
        idSHA1 = utils.SHA1.calculateFromText(id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
        
    public String getPicProfileIPFS() {
        return picProfileIPFS;
    }

    public void setPicProfileIPFS(String picProfileIPFS) {
        this.picProfileIPFS = picProfileIPFS;
    }
    
     @SuppressWarnings("CallToPrintStackTrace")
    public static User jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static User jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return  gson.fromJson(textJSON, User.class);
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

    public TreeSet<String> getLanguages() {
        return languages;
    }

    public TreeSet<String> getTags() {
        return tags;
    }

    public TreeSet<String> getXPasswords() {
        return xpasswords;
    }

    public String getIdIPFS() {
        return idIPFS;
    }

    public void setIdIPFS(String idIPFS) {
        this.idIPFS = idIPFS;
    }
    
    /**
     * Adds this user to IPFS
     * @return 
     */
    public String addToIPFS(){
        String text = this.jsonExport();
        this.idIPFS = core.ipfs.putContentAsText(text);
        return this.idIPFS;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public long getDateLastAccess() {
        return dateLastAccess;
    }

    public void setDateLastAccess(long dateLastAccess) {
        this.dateLastAccess = dateLastAccess;
    }
    
    /**
     * Imports a user without doing any verification checks
     * @param SHA1
     * @return anon when nothing was found
     */
    public static User getUserSHA1(String SHA1) {
        File file = FolderSpecific.getFile(core.dbFolderUsers, SHA1, core.dbUserJSON);
        if(utils.files.isValidFile(file) == false){
            return Users.getAnon();
        }
        // try to read the user
        User user = User.jsonImportFile(file);
        if(user == null){
            user = Users.getAnon();
        }
        
        return user;
    }
    
    /**
     * Convert an idIPFS into a user
     * @param idIPFS
     * @return 
     */
    public static User getUserIPFS(String idIPFS){
        String text = core.ipfs.getContentAsString(idIPFS);
        return User.jsonImport(text);
    }

    public String getIdSHA1() {
        return this.idSHA1;
    }
    
    /**
     * Save this user profile to disk
     */
    public void saveToDisk(){
        String text = this.jsonExport();
        File file = FolderSpecific.getFile(core.dbFolderUsers, idSHA1, core.dbUserJSON);
        utils.files.SaveStringToFile(file, text);
    }
        
}
