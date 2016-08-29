package com.abc.widget;

import org.metaworks.annotation.ServiceMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 8. 25..
 */
public class RadarChart {


    public RadarChart(){

        setLabels(new ArrayList<String>());
        getLabels().add("Eating");
        getLabels().add("Drinking");
        getLabels().add("Sleeping");
        getLabels().add("Designing");
        getLabels().add("Coding");
        getLabels().add("Cycling");
        getLabels().add("Running");

        setDatasets(new ArrayList<DataSet>());

        DataSet dataSet = new DataSet();
        dataSet.setLabel("My First dataset");
        dataSet.setData(new ArrayList<Integer>());
        dataSet.getData().add(10);
        dataSet.getData().add(59);
        dataSet.getData().add(45);
        dataSet.getData().add(30);
        dataSet.getData().add(80);
        dataSet.getData().add(20);
        dataSet.getData().add(44);

        getDatasets().add(dataSet);
        getDatasets().add(dataSet);

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
