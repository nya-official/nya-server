/*
 * Description: Defines a folder location on disk for our database
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */

package database;

import base.core;
import java.io.File;

/**
 * Date: 2020-06-12
 * Place: Coimbra, Portugal
 * @author brito
 */
public class FolderSpecific {

    /**
     * Provides a file from our database
     * @param folderBase
     * @param SHA1
     * @param filename
     * @return 
     */
    public static File getFile(String folderBase, String SHA1, String filename) {
        File folder = FolderSpecific.getFolder(folderBase, SHA1);
        if(folder == null){
            return null;
        }
        File file = new File(folder, filename);
        if(folder.exists() == false){
            utils.files.mkdirs(folder);
        }
        return file;
    }
    
    private final String
            id;
    
    private final File folder;

    public FolderSpecific(String folderBase, String SHA1) {
        this.id = SHA1;
        folder = getFolder(folderBase, id);
    }
    
    /**
     * Get the folder using a specific SHA1 signature
     * @param folderBase
     * @param SHA1
     * @return 
     */
    public static File getFolder(String folderBase, String SHA1) {
        if(SHA1 == null){
            return null;
        }
        String folder1 = SHA1.substring(0, 3);
        String folder2 = SHA1.substring(3, 6);
        File folderTemp1 = new File(core.folderDB, folderBase);
        File folderTemp2 = new File(folderTemp1, folder1);
        return new File(folderTemp2, folder2);
    }
    
    public File getFolder(){
        return folder;
    }

    public String getId() {
        return id;
    }

    public void create() {
        utils.files.mkdirs(folder);
    }

}
