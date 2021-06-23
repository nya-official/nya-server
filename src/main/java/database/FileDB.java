/*
 * Description: A file stored inside our disk database
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package database;

import java.io.File;

/**
 * Date: 2021-06-20
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class FileDB {

    /**
     * Puts a file on disk using a specific SHA1 signature
     * @param SHA1 the signature that will be used
     * @param text the content of the file
     * @param filename that will be used when creating the new file
     * @return true when all went well
     */
    public static boolean putFile(String SHA1, String text, String filename){
        Folder folder = new Folder(SHA1);
        folder.create();
        File file = new File(folder.getFolder(), filename);
        return utils.files.SaveStringToFile(file, text);
    }
    
}
