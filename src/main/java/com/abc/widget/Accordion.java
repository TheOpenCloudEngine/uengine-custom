package com.abc.widget;

import com.abc.activitytype.view.AnalysisActivityView;
import org.metaworks.widget.Label;

public class Accordion {

    Object item1;
        public Object getItem1() {
            return item1;
        }

        public void setItem1(Object item1) {
            this.item1 = item1;
        }


    Object item2;
        public Object getItem2() {
            return item2;
        }

        public void setItem2(Object item2) {
            this.item2 = item2;
        }


    Object item3;
        public Object getItem3() {
            return item3;
        }

        public void setItem3(Object item3) {
            this.item3 = item3;
        }



    public Accordion(){

        setItem3(new Label("<h1>Hello world</h1>"));
        setItem2(new RadarChart());
        setItem1((new AnalysisActivityView()).createSymbol());

    }


}
