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
     * ����Ʈ�ڽ����� �������� �����ϱ� ���ؼ��� ������ ���� setMultiple()�� true�� �����Ѵ�.
     * �����ڿ� ����Ʈ �ڽ��� option�� value�� �����Ѵ�.
     * getOptionNames�� option���� add�ؼ� �����ϰ�
     * option�� values�� ������ option���� �����Ѵ�. �̷��� �ϸ� ������ ���� �������� UI�� �����ȴ�.
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
