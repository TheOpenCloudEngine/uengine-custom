package com.abc.activitytype;

import com.abc.activitytype.view.FilterInformationListFace;
import com.abc.activitytype.view.OutputColumnSelectorFace;
import com.abc.activitytype.view.TableSelector;
import org.metaworks.annotation.Face;
import org.uengine.kernel.DefaultActivity;

import java.util.List;

/**
 * Created by jjy on 2016. 8. 8..
 */
public class DataInputActivity extends DefaultActivity {

    String inputTable;

    String outputTable;

    List<FilterInformation> filterInformation;

    List<String> outputColumn;


    @Face(faceClass=TableSelector.class)
    public String getInputTable() {
        return inputTable;
    }
    public void setInputTable(String inputTable) {
        this.inputTable = inputTable;
    }

    @Face(faceClass=TableSelector.class)
    public String getOutputTable() {
        return outputTable;
    }
    public void setOutputTable(String outputTable) {
        this.outputTable = outputTable;
    }

    @Face(faceClass=FilterInformationListFace.class)
    public List<FilterInformation> getFilterInformation() {
        return filterInformation;
    }

    public void setFilterInformation(List<FilterInformation> filterInformation) {
        this.filterInformation = filterInformation;
    }

    @Face(faceClass = OutputColumnSelectorFace.class)
    public List<String> getOutputColumn() {
        return outputColumn;
    }

    public void setOutputColumn(List<String> outputColumn) {
        this.outputColumn = outputColumn;
    }
}
