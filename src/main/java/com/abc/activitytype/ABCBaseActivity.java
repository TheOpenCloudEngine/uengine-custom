package com.abc.activitytype;

import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;

import java.util.Map;

/**
 * Created by jjy on 2016. 10. 17..
 */
public class ABCBaseActivity extends DefaultActivity{

    public static Map<String, String> getUserInfo(ProcessInstance instance) throws Exception{
        return (Map<String, String>) instance.getRootProcessInstance().getProperty("", "UserInfo");
    }

}
