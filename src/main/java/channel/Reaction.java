/*
 * Description: Reaction to a message
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

import com.google.gson.annotations.Expose;

/**
 * Date: 2021-06-15
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Reaction {
    @Expose
    ReactionType reaction;  // kind of reaction
    
    @Expose
    String idUser;          // id for the user
    
    @Expose
    long timeStamp;         // when it was placed
}
