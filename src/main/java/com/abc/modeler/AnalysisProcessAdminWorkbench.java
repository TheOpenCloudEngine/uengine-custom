package com.abc.modeler;

import org.uengine.processadmin.ProcessAdminWorkbench;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends ProcessAdminWorkbench {


    public AnalysisProcessAdminWorkbench(){

        super();

        setResourceNavigator(new AnalysisResourceNavigator());

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
