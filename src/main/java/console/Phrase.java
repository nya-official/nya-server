/*
 * Description: <add description>
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import java.util.HashMap;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Phrase {
    HashMap<String, String> list = new HashMap();
    
    /**
     * Adds a specific text for a given language
     * @param idCountry
     * @param text 
     */
    public void add(String idCountry, String text){
        list.put(idCountry, text);
    }
    
}
