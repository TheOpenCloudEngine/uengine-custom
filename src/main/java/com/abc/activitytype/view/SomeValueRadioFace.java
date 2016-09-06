package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.common.ChoiceBox;

/**
 * Created by jjy on 2016. 7. 20..
 */
public class SomeValueRadioFace extends ChoiceBox implements Face<String> {

    public SomeValueRadioFace(){
        getOptionValues().add("a");
        getOptionValues().add("b");
        getOptionValues().add("c");

        setOptionNames(getOptionValues());
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
