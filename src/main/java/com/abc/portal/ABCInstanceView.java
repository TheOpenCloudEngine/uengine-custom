package com.abc.portal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.InstanceView;
import org.uengine.codi.mw3.model.InstanceViewDetail;
import org.uengine.codi.mw3.model.InstanceViewThreadPanel;
import org.uengine.essencia.enactment.EssenceProcessDefinition;
import org.uengine.essencia.enactment.GameBoard;
import org.uengine.kernel.ProcessInstance;
import org.uengine.social.InstanceMonitorPanel;

/**
 * Created by jjy on 2016. 7. 19..
 */
@Component
@Scope("prototype")
public class ABCInstanceView extends InstanceView {

    @Override
    public InstanceViewDetail createInstanceViewDetail() {

        InstanceMonitorPanel instanceMonitorPanel = new InstanceMonitorPanel();
        try {
            instanceMonitorPanel.load(new Long(getInstanceId()), processManager);

            ABCInstanceViewDetail abcInstanceViewDetail = new ABCInstanceViewDetail();
            abcInstanceViewDetail.setInstanceMonitorPanel(instanceMonitorPanel);
            abcInstanceViewDetail.setInstanceId(getInstanceId());

            return abcInstanceViewDetail;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
