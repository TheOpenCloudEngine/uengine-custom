package com.abc.portal;


import com.abc.modeler.AnalysisProcessAdminWorkbench;
import org.metaworks.Refresh;
import org.metaworks.ToEvent;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.EssenciaAllAppList;
import org.uengine.codi.mw3.model.Popup;
import org.uengine.codi.mw3.model.SNS;
import org.uengine.essencia.dashboard.Benchmark;
import org.uengine.social.ProcessAdminApplication;
import org.uengine.social.SocialBPMAppList;

@Component
@Order(5)
public class ABCAppLauncher extends SocialBPMAppList {
    public ABCAppLauncher() {
    }

    @Override
    @ServiceMethod(
            target = "append"
    )
    public void goProcessAdmin() throws Exception {
        AnalysisProcessAdminApplication application = new AnalysisProcessAdminApplication();
        this.topPanel.setTopCenterTitle("Workflow Modeler");
        MetaworksRemoteService.wrapReturn(new Refresh(application), new Refresh(this.topPanel), new ToEvent("self", "close"));

    }
}
