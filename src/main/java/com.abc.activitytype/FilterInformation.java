package com.abc.activitytype;

<<<<<<< HEAD
/**
 * Created by jjy on 2016. 8. 8..
 */
public class FilterInformation {

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String fieldName;

    String operator;

    String value;
=======
import org.metaworks.ContextAware;

import java.io.Serializable;

/**
 * Created by jjy on 2016-08-08.
 */
public class FilterInformation implements Serializable {

    String fieldName;
        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName;}

    String operator;
        public String getOperator() { return operator; }
        public void setOperator(String operator) { this.operator = operator; }

    String value;
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
>>>>>>> 8b834f34eb5d1f5fe843cdd899d68dd6caa20557
}
