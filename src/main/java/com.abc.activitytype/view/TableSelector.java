package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.component.SelectBox;

/**
 * Created by jjy on 2016. 8. 8..
 */
public class TableSelector extends SelectBox implements Face<String> {

    public TableSelector(){

        super();

        getOptionNames().add("Table1");
        getOptionNames().add("Table2");
        getOptionNames().add("Table3");
        getOptionNames().add("Table4");

    }


    @Override
    public void setValueToFace(String value) {
        setSelected(value);
    }

    @Override
    public String createValueFromFace() {
        return getSelected();
    }
}
