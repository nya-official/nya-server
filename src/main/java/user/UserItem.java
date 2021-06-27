/*
 * Description: Defines a file or folder belonging to the user
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package user;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 * Date: 2021-06-25
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class UserItem {

    @Expose
    private String 
            filename,       // file name without path
            path,     // path location without file name
            idIPFS, // IPFS location of the file
            description,    // short description of the file
            folderIdIPFS; // in which folder is placed
    
    @Expose
    private ItemType itemType;
    
    @Expose
    private long 
        dateCreated,
        dateLastModified;

    @Expose
    private final ArrayList<UserItem> 
        tree = new ArrayList();
    
    public UserItem() {
        //this.user = user;
        dateCreated = System.currentTimeMillis();
        dateLastModified = System.currentTimeMillis();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String folderPath) {
        this.path = folderPath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdIPFS() {
        return idIPFS;
    }

    public void setIdIPFS(String idIPFS) {
        this.idIPFS = idIPFS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolderIdIPFS() {
        return folderIdIPFS;
    }

    public void setFolderIdIPFS(String folderIdIPFS) {
        this.folderIdIPFS = folderIdIPFS;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
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
     * Create a root folder
     * @return 
     */
    public static UserItem getRootFolder() {
        UserItem folder = new UserItem();
        folder.setItemType(ItemType.folder);
        folder.setFilename("/");
        folder.setPath("");
        return folder;
    }

    /**
     * Create a new folder
     * @param folderName 
     */
    public void createFolder(String folderName) {
        UserItem folder = new UserItem();
        folder.setItemType(ItemType.folder);
        folder.setFilename(folderName);
        String pathNew = path + "/" + folderName ;
        folder.setPath(pathNew);
        addFolder(folder);
    }

    /**
     * Add a new folder to our tree
     * @param folder 
     */
    private void addFolder(UserItem folder) {
        // check if we don't have another match
        for(UserItem folderExisting : tree){
            if(folderExisting.getFilename().equalsIgnoreCase(folder.getFilename())){
                return;
            }
        }
        // all good, add it up
        tree.add(folder);
    }

    public ArrayList<UserItem> getTree() {
        return tree;
    }

    /**
     * Checks if a given folder on the same flat level exists
     * @param folderName
     * @return null when something was not found
     */
    public UserItem getFolder(String folderName) {
            for(UserItem item : tree){
                // needs to be a folder
                if(item.getItemType() != ItemType.folder){
                    continue;
                }
                // try to get a folder match
                if(item.getFilename().equalsIgnoreCase(folderName)){
                    return item;
                }
            }
        return null;
    }


}
