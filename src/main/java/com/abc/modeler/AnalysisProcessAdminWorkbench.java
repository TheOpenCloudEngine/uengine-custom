package com.abc.modeler;

import com.abc.widget.RadarChart;
import org.uengine.processadmin.ProcessAdminWorkbench;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends ProcessAdminWorkbench {


    public AnalysisProcessAdminWorkbench(){

        super();

        setRadarChart(new RadarChart());

        //setResourceNavigator(new AnalysisResourceNavigator());

    }

    RadarChart radarChart;
        public RadarChart getRadarChart() {
            return radarChart;
        }
        public void setRadarChart(RadarChart radarChart) {
            this.radarChart = radarChart;
        }



//
//    DatabaseNavigator databaseNavigator;
//        public DatabaseNavigator getDatabaseNavigator() {
//            return databaseNavigator;
//        }
//        public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
//            this.databaseNavigator = databaseNavigator;
//        }

}
