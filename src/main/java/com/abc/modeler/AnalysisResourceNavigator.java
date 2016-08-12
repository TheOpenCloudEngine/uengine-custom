package com.abc.modeler;

import com.abc.modeler.dbnavigator.DatabaseNavigator;
import org.uengine.modeling.resource.ResourceNavigator;
import org.uengine.processadmin.ProcessAdminResourceNavigator;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisResourceNavigator extends ResourceNavigator {

    public AnalysisResourceNavigator(){
        setDatabaseNavigator(new DatabaseNavigator());
        setProcessAdminResourceNavigator(new ProcessAdminResourceNavigator());
    }

    ProcessAdminResourceNavigator processAdminResourceNavigator;
        public ProcessAdminResourceNavigator getProcessAdminResourceNavigator() {
            return processAdminResourceNavigator;
        }
        public void setProcessAdminResourceNavigator(ProcessAdminResourceNavigator processAdminResourceNavigator) {
            this.processAdminResourceNavigator = processAdminResourceNavigator;
        }

    DatabaseNavigator databaseNavigator;
        public DatabaseNavigator getDatabaseNavigator() {
            return databaseNavigator;
        }
        public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
            this.databaseNavigator = databaseNavigator;
        }
}
