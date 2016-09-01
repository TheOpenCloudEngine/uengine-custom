package com.abc.portal;

import com.abc.widget.Accordion;
import com.abc.widget.RadarChart;
import com.abc.widget.SlaveRadarChart;

/**
 * Created by kimsh on 2016. 8. 25..
 */
public class MultiRadarTest {

    RadarChart left;
    RadarChart right;

    Accordion accordion;

    public RadarChart getLeft() {
        return left;
    }

    public void setLeft(RadarChart left) {
        this.left = left;
    }

    public RadarChart getRight() {
        return right;
    }

    public void setRight(RadarChart right) {
        this.right = right;
    }

    public Accordion getAccordion() {
        return accordion;
    }

    public void setAccordion(Accordion accordion) {
        this.accordion = accordion;
    }



    public MultiRadarTest(){
        left = new RadarChart();
        right = new SlaveRadarChart();
        accordion = new Accordion();
    }
}
