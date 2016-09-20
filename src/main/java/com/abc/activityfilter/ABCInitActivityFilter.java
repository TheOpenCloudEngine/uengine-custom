/*
 * Created on 2004. 12. 19.
 */
package com.abc.activityfilter;

import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.*;

import java.io.Serializable;


/**
 * @author Jinyoung Jang
 */
public class ABCInitActivityFilter implements SensitiveActivityFilter, Serializable{

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public void afterExecute(Activity activity, final ProcessInstance instance)
		throws Exception {

	}

	public void afterComplete(Activity activity, ProcessInstance instance) throws Exception {

	}
	
	public void beforeExecute(Activity activity, ProcessInstance instance)
		throws Exception {

	}

	public void onDeploy(ProcessDefinition definition) throws Exception {
	}

	public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue) throws Exception {
	

	}

	@Override
	public void onEvent(Activity activity, ProcessInstance instance, String eventName, Object payload) throws Exception {

		if("instance.beforeStart".equals(eventName)){
			try{

				instance.setProperty("", "initUserId", MetaworksRemoteService.getComponent(Session.class).getUser().getUserId());
			}catch (Exception e){e.printStackTrace();}

		}
	}
}
