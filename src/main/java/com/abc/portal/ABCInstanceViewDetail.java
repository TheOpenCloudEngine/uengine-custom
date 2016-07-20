package com.abc.portal;

import org.uengine.codi.mw3.model.InstanceViewDetail;
import org.uengine.social.InstanceMonitorPanel;

/**
 * Created by jjy on 2016. 7. 19..
 */
public class ABCInstanceViewDetail extends InstanceViewDetail {

    public InstanceMonitorPanel getInstanceMonitorPanel() {
        return instanceMonitorPanel;
    }

    public void setInstanceMonitorPanel(InstanceMonitorPanel instanceMonitorPanel) {
        this.instanceMonitorPanel = instanceMonitorPanel;
    }

    InstanceMonitorPanel instanceMonitorPanel;
}
