package com.abc.portal;

import com.abc.monitor.ConsolePanel;
import org.uengine.codi.mw3.model.InstanceViewDetail;
import org.uengine.social.InstanceMonitorPanel;

/**
 * Created by jjy on 2016. 7. 19..
 */
public class ABCInstanceViewDetail extends InstanceViewDetail {

    public ABCInstanceViewDetail(){
        setConsolePanel(new ConsolePanel());
    }

    ConsolePanel consolePanel;
        public ConsolePanel getConsolePanel() {
            return consolePanel;
        }

        public void setConsolePanel(ConsolePanel consolePanel) {
            this.consolePanel = consolePanel;
        }


    InstanceMonitorPanel instanceMonitorPanel;
        public InstanceMonitorPanel getInstanceMonitorPanel() {
            return instanceMonitorPanel;
        }

        public void setInstanceMonitorPanel(InstanceMonitorPanel instanceMonitorPanel) {
            this.instanceMonitorPanel = instanceMonitorPanel;
        }

}
