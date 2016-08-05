package com.abc.monitor;

import com.abc.activitytype.CustomSQLActivity;
import com.abc.activitytype.ShellActivity;
import com.abc.activitytype.SyncActivity;
import com.abc.activitytype.view.AnalysisActivityView;
import com.abc.activitytype.view.CustomSQLActivityView;
import com.abc.activitytype.view.ShellActivityView;
import com.abc.activitytype.view.SyncActivityView;
import com.abc.portal.ABCInstanceView;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.IFrame;
import org.metaworks.widget.Label;
import org.metaworks.widget.ModalWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uengine.kernel.Activity;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.modeling.ElementView;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.social.ElementViewActionDelegateForInstanceMonitoring;

import java.rmi.RemoteException;

/**
 * Created by jjy on 2016. 7. 18..
 */

@Component
public class ABCElementViewActionDelegateForInstanceMonitoring extends ElementViewActionDelegateForInstanceMonitoring{


    @Override
    public void onDoubleClick(ElementView elementView) {

        if(elementView instanceof AnalysisActivityView){
            MetaworksRemoteService.wrapReturn(new ModalWindow(new IFrame("/data/ssh/" + getInstanceId()), elementView.getElement().getDescription()));

        }else if(elementView instanceof SyncActivityView){

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                MetaworksRemoteService.wrapReturn(new ModalWindow(new Console("testtesttest")));

                SyncActivity syncActivity = (SyncActivity) elementView.getElement();

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        } else if(elementView instanceof ShellActivityView) {

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
                ShellActivity shellActivity = (ShellActivity) elementView.getElement();

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                String log = (String) instance.getProperty(shellActivity.getTracingTag(), "log"); //for current log
                MetaworksRemoteService.wrapReturn(new ModalWindow(new ShellDetailView(new Console(log), getInstanceId(), shellActivity.getTracingTag() )));


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //super.onDoubleClick(elementView);
        } else if(elementView instanceof CustomSQLActivityView) {

            try {

                ProcessManagerRemote processManagerRemote = MetaworksRemoteService.getComponent(ProcessManagerRemote.class);
                CustomSQLActivity customSQLActivity = (CustomSQLActivity) elementView.getElement();

                ProcessInstance instance = processManagerRemote.getProcessInstance(getInstanceId());
                String log = (String) instance.getProperty(customSQLActivity.getTracingTag(), "resultSql"); //for current log
                MetaworksRemoteService.wrapReturn(new ModalWindow(new CustomSQLDetailView(new Console(log), getInstanceId(), customSQLActivity.getTracingTag())));


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //super.onDoubleClick(elementView);
        }

    }

    @Autowired
    public ProcessManagerRemote processManagerRemote;

}
