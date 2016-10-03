package com.abc.portal;

import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.ModalWindow;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.InstanceViewDetail;
import org.uengine.social.InstanceMonitorPanel;
import org.uengine.social.SocialBPMInstanceTooltip;

/**
 * Created by jjy on 2016. 9. 21..
 */
@Component
@Order(1)
public class ABCInstanceToolbar extends SocialBPMInstanceTooltip {
    public ABCInstanceToolbar() throws Exception {
    }

    @ServiceMethod
    public void prcessView() throws Exception {

        ABCInstanceViewDetail instanceViewDetail = new ABCInstanceViewDetail();
        InstanceMonitorPanel instanceMonitorPanel = createInstanceMonitorPanel();

        instanceViewDetail.setInstanceId(String.valueOf(getInstanceId()));
        instanceViewDetail.setInstanceMonitorPanel(instanceMonitorPanel);


        MetaworksRemoteService.wrapReturn(instanceViewDetail);
    }
}
