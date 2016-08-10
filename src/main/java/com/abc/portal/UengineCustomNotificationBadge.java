package com.abc.portal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.NotificationBadge;

/**
 * Created by uEngineYBS on 2016-08-03.
 */
@Component
@Scope("prototype")
public class UengineCustomNotificationBadge extends NotificationBadge {
    public UengineCustomNotificationBadge() {
        this.setAppName("SK Analytics");
    }
}
