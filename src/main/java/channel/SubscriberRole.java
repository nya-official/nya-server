/*
 * Description: What kind of role the user has inside the channel
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

/**
 * Date: 2021-06-17
 * Place: Zwingenberg, Germany
 * @author brito
 */
public enum SubscriberRole {
    mod,
    participant,
    viewer,
    suspended,
    blocked
}
