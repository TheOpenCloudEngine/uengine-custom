package com.abc.face;

import org.metaworks.EventContext;
import org.metaworks.Face;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.component.MultiSelectBox;
import org.metaworks.component.SelectBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uEngineYBS on 2016-08-09.
 */
public class DynamicSelectBoxWithSingleFace extends MainSelectBoxFace implements Face<Map<String, List<String>>> {

    private transient SelectBox subSelectBox;
        public SelectBox getSubSelectBox() {
            return subSelectBox;
        }
        public void setSubSelectBox(SelectBox subSelectBox) {
            this.subSelectBox = subSelectBox;
        }

    public DynamicSelectBoxWithSingleFace() {
        super();
        getMainSelectBox().getOptionNames().add("==== choose ====");
        getMainSelectBox().getOptionNames().add("test1");
        getMainSelectBox().getOptionNames().add("test2");
        getMainSelectBox().getOptionNames().add("test3");
        getMainSelectBox().setOptionValues(getMainSelectBox().getOptionNames());
        setSubSelectBox(new SelectBox());
    }

    @Hidden
    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_CHANGE, bindingFor = "mainSelectBox", bindingHidden = true, target = ServiceMethodContext.TARGET_SELF)
    public void selectBoxChanged() {
        setSubSelectBox(new SelectBox());
        getSubSelectBox().add("=== choose ===", "-1");
        if("test1".equals(getMainSelectBox().getSelected())) {
            for(int i=1; i<4 ; i++) {
                getSubSelectBox().add("TEST" + i, "TEST" + i);
            }
        } else if("test2".equals(getMainSelectBox().getSelected())) {
            for(int k=4; k<7 ; k++) {
                getSubSelectBox().add("TEST" + k, "TEST" + k);
            }
        } else if("test3".equals(getMainSelectBox().getSelected())) {
            for(int t=7; t<10 ; t++) {
                getSubSelectBox().add("TEST"+t, "TEST"+t);
            }
        }
    }

    @Override
    public void setValueToFace(Map<String, List<String>> value) {

        if(value != null){

            List<String> mainSelectedValueList = value.get("mainSelectBox");
            List<String> subSelectedValueList = value.get("subSelectBox");

            if(mainSelectedValueList !=null && mainSelectedValueList.size()>0) {
                getMainSelectBox().setSelected(mainSelectedValueList.get(0));
            }

            if(subSelectedValueList !=null && subSelectedValueList.size()>0) {
                setSubSelectBox(new SelectBox());
                getSubSelectBox().add("=== choose ===", "-1");
                if("test1".equals(mainSelectedValueList.get(0))) {
                    for(int i=1; i<4 ; i++) {
                        getSubSelectBox().add("TEST" + i, "TEST" + i);
                    }
                } else if("test2".equals(mainSelectedValueList.get(0))) {
                    for(int k=4; k<7 ; k++) {
                        getSubSelectBox().add("TEST" + k, "TEST" + k);
                    }
                } else if("test3".equals(mainSelectedValueList.get(0))) {
                    for(int t=7; t<10 ; t++) {
                        getSubSelectBox().add("TEST"+t, "TEST"+t);
                    }
                }
                getSubSelectBox().setSelected(subSelectedValueList.get(0));
            }
        }

    }

    @Override
    public Map<String, List<String>> createValueFromFace() {

        Map<String, List<String>> createValueMap = new HashMap<String, List<String>>();
        List<String> mainSelectedValueList = new ArrayList<String>();
        mainSelectedValueList.add(getMainSelectBox().getSelected());
        createValueMap.put("mainSelectBox", mainSelectedValueList);

        List<String> subValuesInList = new ArrayList<String>();
        subValuesInList.add(getSubSelectBox().getSelected());
        createValueMap.put("subSelectBox", subValuesInList);

        return createValueMap;
    }
}
