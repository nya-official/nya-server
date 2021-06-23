/*
 * Description: Notifications for the end user
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import java.util.ArrayList;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Message {

    private long 
        timeSent,   // when first dispatched
        timeRead;   // when first read by user or channel
    
    private String 
        idFrom,     // channel or person
        idTo,       // channel or person
        content;    // x-file.xyz data format
    
    private ArrayList<String> 
        tags = new ArrayList(); // message hidden/VIP other attributes

    private ArrayList<Reaction> 
        reactions = new ArrayList(); // reaction from users

    
    public long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(long timeSent) {
        this.timeSent = timeSent;
    }

    public long getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(long timeRead) {
        this.timeRead = timeRead;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String message) {
        this.content = message;
    }
    
    
}
