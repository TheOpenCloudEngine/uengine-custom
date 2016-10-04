package com.abc.activitytype;


import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Order;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.*;
import org.uengine.kernel.bpmn.face.ProcessVariablePanel;
import org.uengine.util.UEngineUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by jjy on 2016-08-08.
 */
public class ProcVarListActivity extends DefaultActivity {

    public ProcVarListActivity() {
        super();
        setName("ProcVarList");
    }

    String inputValue;
        @Order(2)
        public String getInputValue() { return inputValue; }
        public void setInputValue(String inputValue) { this.inputValue = inputValue; }

    String loginUser;
        @Order(3)
        public String getLoginUser() { return loginUser; }
        public void setLoginUser(String loginUser) { this.loginUser = loginUser; }

    @Override
    public ValidationContext validate(Map options) {

        ValidationContext validationContext =  super.validate(options);

        if(!UEngineUtil.isNotEmpty(getInputValue())){
            validationContext.addWarning("입력 값은 필수값입니다.");
        }

        return validationContext;
    }

    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {


        instance.setProperty(this.getTracingTag(), "outTable", getInputValue());

        Vector<Activity> pActivities = instance.getProcessDefinition().getPreviousActivities();
        Activity previousActivity = pActivities.lastElement();
        String previousActivtytrcTag = previousActivity.getTracingTag();

        String previousOutTableValue = (String)instance.getProperty(previousActivtytrcTag, "outTable");




        System.out.println("User info is " + MetaworksRemoteService.getComponent(Session.class));

        ProcessVariableValue pvv = new ProcessVariableValue();

        if(instance.get("", "testVal") == null) {

            pvv.setName("testVal");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("test1", "test1");
            pvv.setValue(map1);

            System.out.println(pvv.getValue());

            pvv.moveToAdd();
            Map<String, Object> map2 = new HashMap<>();
            map2.put("test2", "test2");
            pvv.setValue(map2);
            pvv.moveToAdd();
            Map<String, Object> map3 = new HashMap<>();
            map3.put("test3", "test3");
            pvv.setValue(map3);
            pvv.moveToAdd();
            Map<String, Object> map4 = new HashMap<>();
            map4.put("test4", "test4");
            pvv.setValue(map4);

            //GlobalContext.serialize(pvv, System.out, ProcessVariableValue.class);

            pvv.beforeFirst();

            instance.set("", pvv);

        } else {
            pvv = instance.getMultiple("", "testVal");

            do {
                System.out.println(" value = " + pvv.getValue());
            } while (pvv.next());

            pvv.beforeFirst();
            pvv.remove();
            do {
                System.out.println(" value = " + pvv.getValue());
            } while (pvv.next());
        }
        super.executeActivity(instance);
    }



}
