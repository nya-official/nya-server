/*
 * Description: Provide basic data exchange compatible between browser and command line
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import java.util.ArrayList;
import java.util.HashMap;
import ratpack.handling.Context;
import user.User;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class DataExchange {

    // cookies when used for a browser, or system preferences when command line
    private final ArrayList<CookieInternal> cookies = new ArrayList();
    private final long dateCreated;
    private User user = null;
    private Context context;
            
    
    
    public DataExchange() {
        this.dateCreated = System.currentTimeMillis();
    }
    
    public ArrayList<CookieInternal> getCookies() {
        return cookies;
    }
    
    public String getSessionId(){
        return context.getRequest().oneCookie("JSESSIONID");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addCookie(String key, String value) {
        CookieInternal cookie = new CookieInternal(key, value);
        cookies.add(cookie);
    }
    
}
