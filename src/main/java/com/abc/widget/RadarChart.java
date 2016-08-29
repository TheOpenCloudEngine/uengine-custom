package com.abc.widget;

import org.metaworks.annotation.ServiceMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 8. 25..
 */
public class RadarChart {


    public RadarChart(){

        String[] labelArray = {"Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"};
        setLabels(new ArrayList<String>());
        for(String label : labelArray) {
            getLabels().add(label);
        }

        setDatasets(new ArrayList<DataSet>());

        /*
        label: "My First dataset",
        backgroundColor: "rgba(179,181,198,0.2)",
        borderColor: "rgba(179,181,198,1)",
        pointBackgroundColor: "rgba(179,181,198,1)",
        pointBorderColor: "#fff",
        pointHoverBackgroundColor: "#fff",
        pointHoverBorderColor: "rgba(179,181,198,1)",
        data: [65, 59, 90, 81, 56, 55, 40]
        */

        DataSet dataSetOne = new DataSet();
        dataSetOne.setLabel("My First dataSet");
        dataSetOne.setBackgroundColor("rgba(179,181,198,0.2)");
        dataSetOne.setBorderColor("rgba(179,181,198,1)");
        dataSetOne.setPointBackgroundColor("rgba(179,181,198,1)");
        dataSetOne.setPointBorderColor("#fff");
        dataSetOne.setPointHoverBackgroundColor("#fff");
        dataSetOne.setPointHoverBorderColor("rgba(179,181,198,1)");
        dataSetOne.setData(new ArrayList<Integer>());
        Integer[] dataOneArray = {28, 48, 40, 19, 96, 27,100};
        for(Integer intValue: dataOneArray) {
            dataSetOne.getData().add(intValue);
        }

        /*
        label: "My Second dataset",
        backgroundColor: "rgba(255,99,132,0.2)",
        borderColor: "rgba(255,99,132,1)",
        pointBackgroundColor: "rgba(255,99,132,1)",
        pointBorderColor: "#fff",
        pointHoverBackgroundColor: "#fff",
        pointHoverBorderColor: "rgba(255,99,132,1)",
        data: [28, 48, 40, 19, 96, 27, 100]
         */
        DataSet dataSetTwo = new DataSet();
        dataSetTwo.setLabel("My Second dataSet");
        dataSetTwo.setBackgroundColor("rgba(255,99,132,0.2)");
        dataSetTwo.setBorderColor("rgba(255,99,132,1)");
        dataSetTwo.setPointBackgroundColor("rgba(255,99,132,1)");
        dataSetTwo.setPointBorderColor("#fff");
        dataSetTwo.setPointHoverBackgroundColor("#fff");
        dataSetTwo.setPointHoverBorderColor("rgba(255,99,132,1)");
        dataSetTwo.setData(new ArrayList<Integer>());
        Integer[] dataTwoArray = {28, 48, 40, 19, 96, 27, 100};
        for(Integer intValue: dataTwoArray) {
            dataSetTwo.getData().add(intValue);
        }

        getDatasets().add(dataSetOne);
        getDatasets().add(dataSetTwo);

    }


    List<String> labels;
    List<DataSet> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSet> datasets) {
        this.datasets = datasets;
    }


    @ServiceMethod(callByContent = true, target=ServiceMethod.TARGET_SELF, inContextMenu = true)
    public void refresh(){

        boolean plus = true;

        for(DataSet dataSet : getDatasets()){
            for(int i=0; i<dataSet.getData().size(); i++){
                int value = dataSet.getData().get(i);

                if(plus)
                    dataSet.getData().set(i, value + 10);
                else
                    dataSet.getData().set(i, value - 10);
            }
        }
    }
}
