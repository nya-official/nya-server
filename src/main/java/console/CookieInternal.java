/*
 * Description: Internal cookie
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

/**
 * Date: 2021-06-22
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CookieInternal {
    
    private final String key, value;

    public CookieInternal(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
    

}
