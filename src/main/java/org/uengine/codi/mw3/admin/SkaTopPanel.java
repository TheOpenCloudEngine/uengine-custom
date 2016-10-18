package org.uengine.codi.mw3.admin;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Available;
import org.metaworks.annotation.ServiceMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.Popup;

/**
 * Created by Administrator on 2016-10-17.
 */
@Component
@Scope("prototype")
public class SkaTopPanel extends TopPanel{

    @ServiceMethod(target= ServiceMethodContext.TARGET_STICK, onLoad = false)
    @Available(condition = "ux != 'phone'")
    public Popup showApps() throws Exception{
        return super.showApps();
    }
}
