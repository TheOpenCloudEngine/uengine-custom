package com.abc.widget;

import java.util.List;

/**
 * Created by Administrator on 2016-08-26.
 */
public class DataSet {

    String label;
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

    String backgroundColor;
        public String getBackgroundColor() { return backgroundColor; }
        public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    String borderColor;
        public String getBorderColor() { return borderColor; }
        public void setBorderColor(String borderColor) { this.borderColor = borderColor; }

    String pointBackgroundColor;
        public String getPointBackgroundColor() { return pointBackgroundColor; }
        public void setPointBackgroundColor(String pointBackgroundColor) { this.pointBackgroundColor = pointBackgroundColor; }

    String pointBorderColor;
        public String getPointBorderColor() { return pointBorderColor; }
        public void setPointBorderColor(String pointBorderColor) { this.pointBorderColor = pointBorderColor; }

    String pointHoverBackgroundColor;
        public String getPointHoverBackgroundColor() { return pointHoverBackgroundColor; }
        public void setPointHoverBackgroundColor(String pointHoverBackgroundColor) { this.pointHoverBackgroundColor = pointHoverBackgroundColor; }

    String pointHoverBorderColor;
        public String getPointHoverBorderColor() { return pointHoverBorderColor; }
        public void setPointHoverBorderColor(String pointHoverBorderColor) { this.pointHoverBorderColor = pointHoverBorderColor; }

    List<Integer> data;
        public List<Integer> getData() { return data; }
        public void setData(List<Integer> data) { this.data = data; }
}