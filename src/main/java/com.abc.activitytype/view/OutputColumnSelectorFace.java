package com.abc.activitytype.view;

<<<<<<< HEAD
import org.metaworks.Face;
import org.metaworks.component.MultiSelectBox;
import org.metaworks.component.SelectBox;
=======
import com.abc.activitytype.DataInputActivity;
import org.metaworks.EventContext;
import org.metaworks.Face;
import org.metaworks.MetaworksContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.component.MultiSelectBox;
>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557

import java.util.ArrayList;
import java.util.List;

/**
<<<<<<< HEAD
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

=======
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
    public OutputColumnSelectorFace() {
    }

    @ServiceMethod(onLoad = true, target = ServiceMethod.TARGET_SELF)
    public void onLoad(@AutowiredFromClient DataInputActivity dataInputActivity){

        setMultiple(true);

        setOptionNames(new ArrayList<String>());

        for(int i=0; i<3; i++)
            getOptionNames().add(dataInputActivity.getInputTable() + ".Col" + i);

        setOptionValues(getOptionNames());

        setMetaworksContext(new MetaworksContext());
        getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);

        if(dataInputActivity.getOutputColumn() != null && dataInputActivity.getOutputColumn().size()>0 ) {
            this.setValueToFace(dataInputActivity.getOutputColumn());
        }

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
>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557

    }

    @Override
    public List<String> createValueFromFace() {
<<<<<<< HEAD
=======
        if(getSelected()==null) return null;

>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
        String[] values = getSelected().split(", ");

        List<String> valuesInList = new ArrayList<String>();
        for(String value :  values){
            valuesInList.add(value);
        }

        return valuesInList;
    }
<<<<<<< HEAD
=======


>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
}
