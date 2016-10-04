package com.defaultcompany.organization;

import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.RoleMapping;

import java.util.Map;

public class DefaultCompanyRoleMapping extends RoleMapping {
	
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	final static String EXT_PROP_KEY_NateOnMessengerId = "EXT_PROP_KEY_NATEON_ID";

	public void fill(ProcessInstance instance) throws Exception {
		if(GlobalContext.isDesignTime()) return;

		ProcessVariableValue pvv = new ProcessVariableValue();
		pvv = instance.getMultiple("", "UserInfo");

		do{
			Map<String, String> userInfoMap = (Map<String, String>) pvv.getValue();
			setEndpoint(userInfoMap.get("userId"));
			setResourceName(userInfoMap.get("userName"));
			setEmailAddress(userInfoMap.get("userEmail"));
		}while(pvv.next());

		pvv.beforeFirst();

	}
	
}