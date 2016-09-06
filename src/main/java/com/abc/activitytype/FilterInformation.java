package com.abc.activitytype;

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
}
