/*
 * Description: A piece of text on a specific language
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Lang {
    private String 
            countryID, 
            text;

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
