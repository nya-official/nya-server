/*
 * What we receive from a web request
 *
 * Copyright (c) TripleCheck
 * License: Proprietary
 * http://triplecheck.tech
 */

package utils.www;

/**
 * @author Max Brito
 * @date 2019-07-21
 * @location Darmstadt, Germany
 */
public class WebResponse {
    
    // -1 means no connection
    private final int replyCode;
    private final String text;

    public WebResponse(int replyCode, String text) {
        this.replyCode = replyCode;
        this.text = text;
    }

    public int getReplyCode() {
        return replyCode;
    }

    public String getText() {
        return text;
    }
    
}
