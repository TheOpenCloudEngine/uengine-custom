package org.uengine.components.activityfilters;

import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinyoung Jang
 */
public class InitiatorSettingActivityFilter implements ActivityFilter, Serializable {

    private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

    @Override
    public void beforeExecute(Activity activity, ProcessInstance instance) throws Exception {

        if(instance.getRoleMapping("UserInfo") == null) {

            ProcessVariableValue pvv = new ProcessVariableValue();
            Map<String, String> map = new HashMap<String, String>();
            pvv.setName("UserInfo");
            map.put("userId",MetaworksRemoteService.getComponent(Session.class).getUser().getUserId());
            map.put("userName",MetaworksRemoteService.getComponent(Session.class).getUser().getName());
            map.put("userEmail",MetaworksRemoteService.getComponent(Session.class).getEmployee().getEmail());
            pvv.setValue(map);
            pvv.moveToAdd();
            pvv.beforeFirst();
            instance.set("", pvv);

            RoleMapping roleMapping = RoleMapping.create();
            roleMapping.setName("Initiator");
            roleMapping.setEndpoint(MetaworksRemoteService.getComponent(Session.class).getUser().getUserId());
            roleMapping.setResourceName(MetaworksRemoteService.getComponent(Session.class).getUser().getName());
            roleMapping.setEmailAddress(MetaworksRemoteService.getComponent(Session.class).getEmployee().getEmail());
            instance.putRoleMapping(roleMapping);

        }
    }

    @Override
    public void afterExecute(Activity activity, final ProcessInstance instance) throws Exception {}

    @Override
    public void afterComplete(Activity activity, ProcessInstance instance) throws Exception{};

    @Override
    public void onPropertyChange(Activity activity, ProcessInstance instance, String propertyName, Object changedValue) throws Exception{};

    @Override
    public void onDeploy(ProcessDefinition processDefinition) throws Exception{};

}

