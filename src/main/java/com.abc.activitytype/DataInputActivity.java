package com.abc.activitytype;

import com.abc.activitytype.view.FilterInformationListFace;
import com.abc.activitytype.view.OutputColumnSelectorFace;
import com.abc.activitytype.view.TableSelector;
import com.abc.face.DynamicSelectBoxFace;
import com.abc.face.ParameterValueListFace;
import com.abc.face.ParameterVariable;
import org.metaworks.EventContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ValidationContext;

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
    }

    String inputTable;
        @Face(
                faceClass=TableSelector.class,
                displayName = "인풋 테이블명"
        )
        @Order(2)
        public String getInputTable() { return inputTable; }
        public void setInputTable(String inputTable) { this.inputTable = inputTable; }

    String outputTable;
        @Face(
                faceClass=TableSelector.class,
                displayName = "아웃풋 테이블명"
        )
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

    Map<String, List<String>> dynamicSelectBox;
        @Face(
                displayName = "다이나믹 셀렉트",
                faceClass = DynamicSelectBoxFace.class
        )
        @Order(9)
        public Map<String, List<String>> getDynamicSelectBox() { return dynamicSelectBox; }
        public void setDynamicSelectBox(Map<String, List<String>> dynamicSelectBox) { this.dynamicSelectBox = dynamicSelectBox; }


    String inputValue;
        @Face(
                displayName = "인풋박스"
        )
        @Order(10)
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

        if(getInputValue()==null){
            validationContext.addWarning("Input Value must be set.");
        }

        return validationContext;
    }



    @Hidden
    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_CHANGE, bindingFor = "inputTable", bindingHidden = true, target = ServiceMethodContext.TARGET_SELF)
    public void onInputTableChanged() {

    }


    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {
        System.out.println("inputTable Value is " + getInputTable());
        System.out.println("outputTable Value is " + getOutputTable());
        System.out.println("================= outputColumn Values===============");
        int outputColumnIdx = 1;
        for(String outputColumn : getOutputColumn()) {
            System.out.println(outputColumnIdx+"번째 [outputColumn Value] : " + outputColumn);
            outputColumnIdx++;
        }
        System.out.println("================= FilterInformation End ===============");
        System.out.println("================= FilterInformation Values===============");
        int filterIdx = 1;
        for(FilterInformation filter : getFilterInformation()) {
            System.out.println(filterIdx+"번째 [FieldName] : " + filter.getFieldName() + ", [Operator] : " + filter.getOperator() + ", [Value] : "+filter.getValue());
            filterIdx++;
        }
        System.out.println("================= FilterInformation End ===============");
        System.out.println("================= ParameterVariable Values===============");
        int paramIdx = 1;
        for(ParameterVariable paramVar : getParameterValueList()) {
            System.out.println(paramIdx+"번째 [paramVar] : " + paramVar.getParameter());
            filterIdx++;
        }
        System.out.println("================= ParameterVariable Values===============");

        System.out.println("CheckBox is " + isCheckBox());
        System.out.println("radioValue is " + getRadioValue());
    }

}
