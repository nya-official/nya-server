/*
 * Description: <add description>
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package genesis;

import static base.core.dateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import java.io.File;

/**
 * Date: 2021-06-16
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Genesis {

    @Expose
    private String 
        genesis = null;
    
    @Expose
    public String 
        gateway = "ipfs.infura.io/tcp/5001/https";

    public String getGenesis() {
        return genesis;
    }

    public void setGenesis(String genesis) {
        this.genesis = genesis;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static Genesis jsonImportFile(File file){
        String text = utils.files.readAsString(file);
        return jsonImport(text);
    }
    
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static Genesis jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
            
            return  gson.fromJson(textJSON, Genesis.class);
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
