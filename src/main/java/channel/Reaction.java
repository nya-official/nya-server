/*
 * Description: Reaction to a message
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

/**
 * Date: 2021-06-15
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Reaction {
    ReactionType reaction;  // kind of reaction
    String idUser;          // id for the user
    long timeStamp;         // when it was placed
}
