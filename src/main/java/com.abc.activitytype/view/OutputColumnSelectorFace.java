package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.component.MultiSelectBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016-08-08.
 */
public class OutputColumnSelectorFace extends MultiSelectBox implements Face<List<String>> {

    /**
     * 셀렉트박스에서 여러개를 생성하기 위해서는 다음과 같이 setMultiple()에 true로 세팅한다.
     * 생성자에 셀렉트 박스의 option과 value를 세팅한다.
     * getOptionNames에 option값을 add해서 세팅하고
     * option의 values를 세팅한 option으로 세팅한다. 이렇게 하면 다음과 같은 형식으로 UI가 생성된다.
     * <select><br>
     *     <opition="Col1">Col1</opition><br>
     *     <opition="Col2">Col2</opition><br>
     *     .<br>
     *     .<br>
     * </select>
     */
    public OutputColumnSelectorFace(){

        setMultiple(true);
        getOptionNames().add("Col1");
        getOptionNames().add("Col2");
        getOptionNames().add("Col3");
        getOptionNames().add("Col4");
        getOptionNames().add("Col5");
        setOptionValues(getOptionNames());

    }

    @Override
    public void setValueToFace(List<String> value) {
        if(value!=null && value.size() > 0){

            String valueStr = "";
            String sep = "";

            for(String valueItem : value){
                valueStr += sep + valueItem;
                sep = ", ";
            }

            setSelected(valueStr);

        }

    }

    @Override
    public List<String> createValueFromFace() {
        if(getSelected()==null) return null;

        String[] values = getSelected().split(", ");

        List<String> valuesInList = new ArrayList<String>();
        for(String value :  values){
            valuesInList.add(value);
        }

        return valuesInList;
    }
}
