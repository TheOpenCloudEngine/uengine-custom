package com.abc.activitytype.view;

import org.metaworks.Face;
import org.metaworks.component.SelectBox;

/**
<<<<<<< HEAD
 * Created by jjy on 2016. 8. 8..
 */
public class TableSelector extends SelectBox implements Face<String> {

    public TableSelector(){

        super();

=======
 * Created by jjy on 2016-08-08.
 */
public class TableSelector extends SelectBox implements Face<String> {

    /**
     * �����ڿ� ����Ʈ �ڽ��� option�� value�� �����Ѵ�.
     * getOptionNames�� option���� add�ؼ� �����ϰ�
     * option�� values�� ������ option���� �����Ѵ�. �̷��� �ϸ� ������ ���� �������� UI�� �����ȴ�.
     * <select><br>
     *     <opition="Tables1">Table1</opition><br>
     *     <opition="Tables2">Table2</opition><br>
     *     .<br>
     *     .<br>
     * </select>
     */
    public TableSelector(){

        super();
>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
        getOptionNames().add("Table1");
        getOptionNames().add("Table2");
        getOptionNames().add("Table3");
        getOptionNames().add("Table4");
<<<<<<< HEAD

    }


=======
        setOptionValues(getOptionNames());
    }

>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
    @Override
    public void setValueToFace(String value) {
        setSelected(value);
    }

    @Override
    public String createValueFromFace() {
        return getSelected();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
