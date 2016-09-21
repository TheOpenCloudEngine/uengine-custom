package com.abc.portal;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.uengine.social.SocialBPMInstanceTooltip;

/**
 * Created by jjy on 2016. 9. 21..
 */
@Component
@Order(1)
public class ABCInstanceToolbar extends SocialBPMInstanceTooltip {
    public ABCInstanceToolbar() throws Exception {
    }
}
