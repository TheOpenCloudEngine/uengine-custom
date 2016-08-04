package com.abc.activitytype;

import com.abc.monitor.Console;
import org.metaworks.ToAppend;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.admin.WebEditorFace;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.ValidationContext;
import org.uengine.kernel.bpmn.face.ProcessVariableSelectorFace;

import java.util.Map;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class SyncActivity extends DefaultActivity{

    public SyncActivity() {
        super();
        setName("Synchronous Activity");
    }

    String parameter1;
    @Order(3)
        public String getParameter1() {
        return parameter1;
        }
        public void setParameter1(String parameter1) {
            this.parameter1 = parameter1;
        }

    int intParameter;
    @Order(2)
        public int getIntParameter() {
            return intParameter;
        }
        public void setIntParameter(int intParameter) {
            this.intParameter = intParameter;
        }

    String optionParameter;
    @Range(options={"a", "b", "c"}, values={"A", "B", "C"})
    @Order(4)
        public String getOptionParameter() {
            return optionParameter;
        }
        public void setOptionParameter(String optionParameter) {
            this.optionParameter = optionParameter;
        }


    String parameter2;
    @Face(faceClass = WebEditorFace.class)
    @Order(3)
        public String getParameter2() {
            return parameter2;
        }
        public void setParameter2(String parameter2) {
            this.parameter2 = parameter2;
        }


    ProcessVariable variableToStoreResult;
    @Face(faceClass = ProcessVariableSelectorFace.class)
    @Order(5)
    @Group(name = "Result")
        public ProcessVariable getVariableToStoreResult() {
            return variableToStoreResult;
        }
        public void setVariableToStoreResult(ProcessVariable variableToStoreResult) {
            this.variableToStoreResult = variableToStoreResult;
        }


    @Override
    public ValidationContext validate(Map options) {


        ValidationContext validationContext =  super.validate(options);

        if(getParameter2()==null){
            validationContext.addWarning("parameter2 must be set.");
        }

        return validationContext;
    }

    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {

        //logic here
        System.out.println(getParameter1());
        final String log = "log from " + getClass().getName() + " :   evaluation for parameter2 is " + evaluateContent(instance, getParameter2());

        //set result value to variable
        if(getVariableToStoreResult()!=null)
            getVariableToStoreResult().set(instance, "", log);


        new Thread(){
            @Override
            public void run() {

                try {
                    for(int i=0; i<getIntParameter(); i++){
                        Thread.sleep(3000);
                        System.out.println(log);
                        instance.addDebugInfo(log);
                        MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new Console(), new Console(log))});


                    }

                    SyncActivity.this.fireComplete(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
