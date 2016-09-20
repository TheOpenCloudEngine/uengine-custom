package com.abc.activitytype.view;

import org.metaworks.EventContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.ServiceMethod;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.bpmn.face.ProcessVariablePanel;
import org.uengine.kernel.view.ActivityView;

public class DataInputActivityView extends ActivityView {

    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_DBLCLICK, target = ServiceMethodContext.TARGET_POPUP)
    public Object showProperty(
                @AutowiredFromClient ProcessVariablePanel processVariablePanel,
                @AutowiredFromClient Session session
    ) throws Exception {

        return showProperty();
    }


}
