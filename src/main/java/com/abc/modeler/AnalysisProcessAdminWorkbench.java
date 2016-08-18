package com.abc.modeler;

import com.abc.modeler.dbnavigator.DatabaseNavigator;
import org.uengine.modeling.resource.Workbench;
import org.uengine.processadmin.ProcessAdminResourceNavigator;
import org.uengine.processadmin.RecentEditedResourcesPanel;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends Workbench {


    public AnalysisProcessAdminWorkbench() {

        super(new ProcessAdminResourceNavigator());

        try {
            setEditorPanel(new RecentEditedResourcesPanel(getResourceNavigator()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    DatabaseNavigator databaseNavigator;
    public DatabaseNavigator getDatabaseNavigator() {
        return new DatabaseNavigator();
    }
    public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
        this.databaseNavigator = databaseNavigator;
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
