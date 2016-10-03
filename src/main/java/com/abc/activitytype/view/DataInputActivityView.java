package com.abc.activitytype.view;

import org.metaworks.EventContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Payload;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.Label;
import org.metaworks.widget.ModalWindow;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.bpmn.face.ProcessVariablePanel;
import org.uengine.kernel.view.ActivityView;
import org.uengine.modeling.IElement;
import org.uengine.social.InstanceMonitorPanel;

public class DataInputActivityView extends ActivityView {

    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_DBLCLICK, target = ServiceMethodContext.TARGET_POPUP)
    public Object showProperty(
                @AutowiredFromClient ProcessVariablePanel processVariablePanel,
                @AutowiredFromClient Session session
    ) throws Exception {

        return showProperty();
    }

    @ServiceMethod(inContextMenu = true, target = ServiceMethod.TARGET_POPUP)//, where = "running")
    public void showDetails(@AutowiredFromClient(payload="instanceId") InstanceMonitorPanel instanceMonitorPanel, @Payload("element")IElement element){
        MetaworksRemoteService.wrapReturn(new ModalWindow(new Label("detail for " + getElement().getName() + " and the instanceId is " + instanceMonitorPanel.getInstanceId()), "title"));
    }

}
