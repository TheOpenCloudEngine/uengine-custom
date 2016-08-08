package com.abc.activitytype;

import com.abc.activitytype.view.FilterInformationListFace;
import com.abc.activitytype.view.OutputColumnSelectorFace;
import com.abc.activitytype.view.TableSelector;
import com.abc.face.ParameterValueListFace;
import com.abc.face.ParameterVariable;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Order;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016-08-08.
 */
public class DataInputActivity extends DefaultActivity {

    public DataInputActivity() {
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


   /* List<String> outputColumn;
        @Face(
                faceClass = OutputColumnSelectorFace.class,
                displayName = "아웃풋 컬럼 셀렉트박스"
        )
        @Order(5)
        public List<String> getOutputColumn() { return outputColumn; }
        public void setOutputColumn(List<String> outputColumn) { this.outputColumn = outputColumn; }*/

    String outputColumn;
        @Face(
                faceClass = OutputColumnSelectorFace.class,
                displayName = "아웃풋 컬럼 셀렉트박스"
        )
        @Order(5)
        public String getOutputColumn() { return outputColumn; }
        public void setOutputColumn(String outputColumn) { this.outputColumn = outputColumn; }

    List<ParameterVariable> parameterValueList;
        @Face(
                faceClass = ParameterValueListFace.class,
                displayName = "파라미터 리스트"
        )
        @Order(6)
        public List<ParameterVariable> getParameterValueList() { return parameterValueList; }
        public void setParameterValueList(List<ParameterVariable> parameterValueList) { this.parameterValueList = parameterValueList; }

    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {
        System.out.println("inputTable Value is " + getInputTable());
        System.out.println("outputTable Value is " + getOutputTable());
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
    }

}
