package com.abc.portal;


import com.abc.modeler.AnalysisProcessAdminWorkbench;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.uengine.codi.mw3.model.Application;

@Face(
        ejsPath = "dwr/metaworks/genericfaces/CleanObjectFace.ejs"
)
public class AnalysisProcessAdminApplication extends Application {
    AnalysisProcessAdminWorkbench processAdminWorkbench = new AnalysisProcessAdminWorkbench();

    @Hidden
    public String getTopCenterPanelType() {
        return super.getTopCenterPanelType();
    }

    public AnalysisProcessAdminApplication() throws Exception {
    }

    public AnalysisProcessAdminWorkbench getProcessAdminWorkbench() {
        return this.processAdminWorkbench;
    }

    public void setProcessAdminWorkbench(AnalysisProcessAdminWorkbench processAdminWorkbench) {
        this.processAdminWorkbench = processAdminWorkbench;
    }
}
