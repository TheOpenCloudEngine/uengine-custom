package com.abc.monitor;

import com.abc.activitytype.view.AnalysisActivityView;
import com.abc.portal.ABCInstanceView;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.IFrame;
import org.metaworks.widget.Label;
import org.metaworks.widget.ModalWindow;
import org.springframework.stereotype.Component;
import org.uengine.kernel.DefaultActivity;
import org.uengine.modeling.ElementView;
import org.uengine.social.ElementViewActionDelegateForInstanceMonitoring;

/**
 * Created by jjy on 2016. 7. 18..
 */

@Component
public class ABCElementViewActionDelegateForInstanceMonitoring extends ElementViewActionDelegateForInstanceMonitoring{


    @Override
    public void onDoubleClick(ElementView elementView) {

        if(elementView instanceof AnalysisActivityView){
            MetaworksRemoteService.wrapReturn(new ModalWindow(new IFrame("/data/ssh/" + getInstanceId()), elementView.getElement().getDescription()));

        }else{
            //super.onDoubleClick(elementView);
        }

    }

}
