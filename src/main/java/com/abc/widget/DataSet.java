package com.abc.widget;

import java.util.List;

/**
 * Created by kimsh on 2016. 8. 25..
 */
public class DataSet {
//
//    label: "My First dataset",
//    backgroundColor: "rgba(179,181,198,0.2)",
//    borderColor: "rgba(179,181,198,1)",
//    pointBackgroundColor: "rgba(179,181,198,1)",
//    pointBorderColor: "#fff",
//    pointHoverBackgroundColor: "#fff",
//    pointHoverBorderColor: "rgba(179,181,198,1)",
//    data: [65, 59, 90, 81, 56, 55, 40]


    String label;

    List<Integer> data;


    String backgroundColor;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
