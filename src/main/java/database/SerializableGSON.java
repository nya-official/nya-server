/*
 * Description: Makes classes serializable by GSON
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package database;

import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.File;

/**
 * Date: 2021-06-16
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class SerializableGSON {

    @SuppressWarnings("CallToPrintStackTrace")
    public static SerializableGSON jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static SerializableGSON jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return  gson.fromJson(textJSON, SerializableGSON.class);
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
    
}
