package com.abc.activitytype;


import com.abc.activitytype.view.FilterInformationListFace;
import com.abc.activitytype.view.OutputColumnSelectorFace;
import com.abc.activitytype.view.TableSelector;
import com.abc.face.DynamicSelectBoxWithMultiFace;
import com.abc.face.DynamicSelectBoxWithSingleFace;
import com.abc.face.ParameterValueListFace;
import com.abc.face.ParameterVariable;
import com.abc.widget.ComputeTable;
import org.metaworks.EventContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.model.Session;
import org.uengine.kernel.*;
import org.uengine.kernel.bpmn.SequenceFlow;
import org.uengine.kernel.bpmn.face.ProcessVariableSelectorFace;
import org.uengine.util.UEngineUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jjy on 2016-08-08.
 */
public class DataInputActivity extends DefaultActivity {

    public DataInputActivity() {
        super();
        setName("Data Input");
        setComputeTable(new ComputeTable());
    }



    @Override
    public boolean isQueuingEnabled() {
        return false;
    }

    String inputTable;
//        @Face(
//                faceClass=TableSelector.class,
//                displayName = "인풋 테이블명"
//        )
        @Order(2)
        public String getInputTable() { return inputTable; }
        public void setInputTable(String inputTable) { this.inputTable = inputTable; }

    String outputTable;
//        @Face(
//                faceClass=TableSelector.class,
//                displayName = "아웃풋 테이블명"
//        )
        @Order(3)
        public String getOutputTable() { return outputTable; }
        public void setOutputTable(String outputTable) { this.outputTable = outputTable; }

    List<FilterInformation> filterInformation;
        @Face(
                faceClass=FilterInformationListFace.class,
                displayName = "필터 정보 리스트"
        )
        @Order(4)
        public List<FilterInformation> getFilterInformation() { return filterInformation; }
        public void setFilterInformation(List<FilterInformation> filterInformation) { this.filterInformation = filterInformation; }

    List<String> outputColumn;
        @Face(
                faceClass = OutputColumnSelectorFace.class,
                displayName = "아웃풋 컬럼 셀렉트박스"
        )
        @Order(5)
        public List<String> getOutputColumn() { return outputColumn; }
        public void setOutputColumn(List<String> outputColumn) { this.outputColumn = outputColumn; }

    /*
    String outputColumn;
        @Face(
                faceClass = OutputColumnSelectorFace.class,
                displayName = "아웃풋 컬럼 셀렉트박스"
        )
        @Order(5)
        public String getOutputColumn() { return outputColumn; }
        public void setOutputColumn(String outputColumn) { this.outputColumn = outputColumn; }
*/
    List<ParameterVariable> parameterValueList;
        @Face(
                faceClass = ParameterValueListFace.class,
                displayName = "파라미터 리스트"
        )
        @Order(6)
        public List<ParameterVariable> getParameterValueList() { return parameterValueList; }
        public void setParameterValueList(List<ParameterVariable> parameterValueList) { this.parameterValueList = parameterValueList; }

    boolean checkBox;
        @Face(
                displayName = "체크박스"
        )
        @Order(7)
        public boolean isCheckBox() { return checkBox; }
        public void setCheckBox(boolean checkBox) { this.checkBox = checkBox; }

    String radioValue;
        @Face(
                ejsPath = "dwr/metaworks/genericfaces/RadioButton.ejs",
                options = {"test1", "test2"},
                values = {"test1", "test2"},
                displayName = "라디오버튼"
        )
        @Order(8)
        public String getRadioValue() { return radioValue; }
        public void setRadioValue(String radioValue) { this.radioValue = radioValue; }

    Map<String, List<String>> dynamicSelectBoxWithMulti;
        @Face(
                displayName = "다이나믹 멀티셀렉트",
                faceClass = DynamicSelectBoxWithMultiFace.class
        )
        @Order(9)
        public Map<String, List<String>> getDynamicSelectBoxWithMulti() { return dynamicSelectBoxWithMulti; }
        public void setDynamicSelectBoxWithMulti(Map<String, List<String>> dynamicSelectBoxWithMulti) { this.dynamicSelectBoxWithMulti = dynamicSelectBoxWithMulti; }

    Map<String, List<String>> dynamicSelectBoxWithSingle;
        @Face(
                displayName = "다이나믹 싱글셀렉트",
                faceClass = DynamicSelectBoxWithSingleFace.class
        )
        @Order(10)
        public Map<String, List<String>> getDynamicSelectBoxWithSingle() { return dynamicSelectBoxWithSingle; }
        public void setDynamicSelectBoxWithSingle(Map<String, List<String>> dynamicSelectBoxWithSingle) { this.dynamicSelectBoxWithSingle = dynamicSelectBoxWithSingle; }


    ComputeTable computeTable;
        public ComputeTable getComputeTable() {
            return computeTable;
        }
        public void setComputeTable(ComputeTable computeTable) {
            this.computeTable = computeTable;
        }


    ProcessVariable outValue = new ProcessVariable();
    @Face(faceClass = ProcessVariableSelectorFace.class)
        public ProcessVariable getOutValue() {
            return outValue;
        }
        public void setOutValue(ProcessVariable outValue) {
            this.outValue = outValue;
        }


    String inputValue;
        @Order(11)
        public String getInputValue() { return inputValue; }
        public void setInputValue(String inputValue) { this.inputValue = inputValue; }
    /*

    String otherRadioValue;
        @Face(
                displayName = "라디오버튼2",
                faceClass = SomeValueRadioFace.class,
                ejsPath = "dwr/metaworks/genericfaces/RadioButton.ejs"
        )
        @Order(9)
        public String getOtherRadioValue() {return otherRadioValue; }
        public void setOtherRadioValue(String otherRadioValue) { this.otherRadioValue = otherRadioValue; }
*/

    @Override
    public ValidationContext validate(Map options) {


        ValidationContext validationContext =  super.validate(options);

        if(!UEngineUtil.isNotEmpty(getInputValue())){
            validationContext.addWarning("입력 값은 필수값입니다.");
        }

        return validationContext;
    }



    @Hidden
    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_CHANGE, bindingFor = "inputTable", bindingHidden = true, target = ServiceMethodContext.TARGET_SELF)
    public void onInputTableChanged() {

    }


    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {

       // if(true) throw new Exception("test");

        for (SequenceFlow sequenceFlow : getIncomingSequenceFlows()){
            Activity prevActivity = sequenceFlow.getSourceActivity();

            if(prevActivity instanceof DataInputActivity){
                System.out.println(((DataInputActivity) prevActivity).getOutValue());
            }
        }


        System.out.println("User info is " + instance.getProperty("", "initUserId"));


        System.out.println("inputTable Value is " + evaluateContent(instance, getInputTable()));
        System.out.println("outputTable Value is " + evaluateContent(instance, getInputTable()));
        System.out.println("================= outputColumn Values===============");
        int outputColumnIdx = 1;
        if(getOutputColumn()!=null)
        for(String outputColumn : getOutputColumn()) {
            System.out.println(outputColumnIdx+"번째 [outputColumn Value] : " + outputColumn);
            outputColumnIdx++;
        }
        System.out.println("================= FilterInformation End ===============");
        System.out.println("================= FilterInformation Values===============");
        int filterIdx = 1;

        if(getFilterInformation()!=null)
        for(FilterInformation filter : getFilterInformation()) {
            System.out.println(filterIdx+"번째 [FieldName] : " + filter.getFieldName() + ", [Operator] : " + filter.getOperator() + ", [Value] : "+filter.getValue());
            filterIdx++;
        }
        System.out.println("================= FilterInformation End ===============");
        System.out.println("================= ParameterVariable Values===============");
        int paramIdx = 1;

        if(getParameterValueList()!=null)
        for(ParameterVariable paramVar : getParameterValueList()) {
            System.out.println(paramIdx+"번째 [paramVar] : " + paramVar.getParameter());
            filterIdx++;
        }
        System.out.println("================= ParameterVariable Values End===============");

        System.out.println("CheckBox is " + isCheckBox());
        System.out.println("radioValue is " + getRadioValue());

        System.out.println("================= Dynamic Multi Select Values===============");
        Map<String, List<String>> dmSelectMap = getDynamicSelectBoxWithMulti();
        System.out.println("Main Selected Value is "+dmSelectMap.get("mainSelectBox"));
        System.out.println("Sub Multi Selected Value is " +dmSelectMap.get("subSelectBox"));
        System.out.println("================= Dynamic Multi Select Values End===============");


        System.out.println("================= Dynamic Single Select Values===============");
        Map<String, List<String>> dsSelectMap = getDynamicSelectBoxWithSingle();
        System.out.println("Main Selected Value is "+dsSelectMap.get("mainSelectBox"));
        System.out.println("Sub Single Selected Value is " +dsSelectMap.get("subSelectBox"));
        System.out.println("================= Dynamic Single Select Values End===============");


        getOutValue().set(instance, "", "output of " + instance.getName() + ":" + getInputValue());

        super.executeActivity(instance);
    }


    @Override
    public void suspend(ProcessInstance instance) throws Exception {
        stop(instance);
    }

    @Override
    public void stop(ProcessInstance instance, String status) throws Exception {
        super.stop(instance, status);


        //logic for signaling stop to the job
        // blah blah....


    }
}
