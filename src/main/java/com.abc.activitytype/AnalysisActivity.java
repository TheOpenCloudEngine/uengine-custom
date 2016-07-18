package com.abc.activitytype;

import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ReceiveActivity;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class AnalysisActivity extends ReceiveActivity{

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    String parameter1;

    String parameter2;

    @Override
    protected void executeActivity(ProcessInstance instance) throws Exception {

        //logic here
        System.out.println(getParameter1());
        System.out.println(getParameter2());

        super.executeActivity(instance);
    }
}
