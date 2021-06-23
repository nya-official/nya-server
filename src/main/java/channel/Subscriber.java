/*
 * Description: User subscribes to a specific channel
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package channel;

/**
 * Date: 2021-06-17
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class Subscriber {

    private String id; // id of the user
    private SubscriberRole role = SubscriberRole.participant;
    private NotificationType notificationType = NotificationType.normal;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubscriberRole getRole() {
        return role;
    }

    public void setRole(SubscriberRole role) {
        this.role = role;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
    
}
