package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.component.MultiSelectBox;
import org.metaworks.component.SelectBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 8. 8..
 */
public class OutputColumnSelectorFace extends MultiSelectBox implements Face<List<String>> {

    public OutputColumnSelectorFace(){

        setMultiple(true);

        getOptionNames().add("Col1");
        getOptionNames().add("Col2");
        getOptionNames().add("Col3");
        getOptionNames().add("Col4");
        getOptionNames().add("Col5");
    }


    @Override
    public void setValueToFace(List<String> value) {


    }

    @Override
    public List<String> createValueFromFace() {
        String[] values = getSelected().split(", ");

        List<String> valuesInList = new ArrayList<String>();
        for(String value :  values){
            valuesInList.add(value);
        }

        return valuesInList;
    }
}
