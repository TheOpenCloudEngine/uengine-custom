package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.component.SelectBox;

/**
 * Created by jjy on 2016-08-08.
 */
public class TableSelector extends SelectBox implements Face<String> {

    /**
     * 생성자에 셀렉트 박스의 option과 value를 세팅한다.
     * getOptionNames에 option값을 add해서 세팅하고
     * option의 values를 세팅한 option으로 세팅한다. 이렇게 하면 다음과 같은 형식으로 UI가 생성된다.
     * <select><br>
     *     <opition="Tables1">Table1</opition><br>
     *     <opition="Tables2">Table2</opition><br>
     *     .<br>
     *     .<br>
     * </select>
     */
    public TableSelector(){

        super();
        getOptionNames().add("Table1");
        getOptionNames().add("Table2");
        getOptionNames().add("Table3");
        getOptionNames().add("Table4");
        setOptionValues(getOptionNames());
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