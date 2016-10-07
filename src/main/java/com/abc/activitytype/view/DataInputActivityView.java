package com.abc.activitytype.view;

import com.abc.activitytype.DataInputActivity;
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
import org.uengine.modeling.Canvas;
import org.uengine.modeling.IElement;
import org.uengine.social.InstanceMonitorPanel;

public class DataInputActivityView extends ActivityView {

    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_DBLCLICK, target = ServiceMethodContext.TARGET_POPUP)
    public Object showProperty(
                @AutowiredFromClient(payload = "elementViewList[__className=='com.abc.activitytype.view.DataInputActivityView' && toEdge==value.fromEdge].element.outValue.name") Canvas canvas,
                @AutowiredFromClient ProcessVariablePanel processVariablePanel,
                @AutowiredFromClient Session session
    ) throws Exception {

        DataInputActivity dia = (DataInputActivity) getElement();
        if(canvas.getElementViewList()!=null
                && canvas.getElementViewList().size() > 0
                && (dia.getOutValue()==null || dia.getOutValue().getName()==null)
                && ((DataInputActivity)canvas.getElementViewList().get(0).getElement()).getOutValue()!=null){

            dia.setOutValue(((DataInputActivity)canvas.getElementViewList().get(0).getElement()).getOutValue());
        }

        return showProperty();
    }

    @ServiceMethod(inContextMenu = true, target = ServiceMethod.TARGET_POPUP)//, where = "running")
    public void showDetails( @AutowiredFromClient(payload="instanceId") InstanceMonitorPanel instanceMonitorPanel, @Payload("element")IElement element){
        MetaworksRemoteService.wrapReturn(new ModalWindow(new Label("detail for " + getElement().getName() + " and the instanceId is " + instanceMonitorPanel.getInstanceId()), "title"));
    }

}
