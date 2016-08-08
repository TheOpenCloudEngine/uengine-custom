package com.abc.activitytype;

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
}
