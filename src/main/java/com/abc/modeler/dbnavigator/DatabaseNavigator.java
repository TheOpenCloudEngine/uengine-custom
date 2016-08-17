package com.abc.modeler.dbnavigator;

import com.abc.modeler.dbnavigator.DatabaseResource;
import com.abc.modeler.dbnavigator.Field;
import com.abc.modeler.dbnavigator.TableResource;
import org.metaworks.annotation.ServiceMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class DatabaseNavigator {

    List<DatabaseResource> databaseResourceList;

    public List<DatabaseResource> getDatabaseResourceList() {
        return databaseResourceList;
    }

    public void setDatabaseResourceList(List<DatabaseResource> databaseResourceList) {
        this.databaseResourceList = databaseResourceList;
    }

    public DatabaseNavigator() {
        onLoad();
    }

    @ServiceMethod(onLoad = true, target = ServiceMethod.TARGET_SELF)
    public void onLoad() {
        setDatabaseResourceList(new ArrayList<DatabaseResource>());



        ArrayList<Field> fields = new ArrayList<Field>();
        for (int i = 0; i < 5; i++) {
            Field field = new Field();
            field.setName("field" + i);
            fields.add(field);
        }
        ArrayList<TableResource> tableResources = new ArrayList<TableResource>();
        for (int i = 0; i < 3; i++) {
            TableResource tableResource = new TableResource();
            tableResource.setName("table" + i);
            tableResource.setFieldList(fields);
            tableResources.add(tableResource);
        }

        DatabaseResource db1 = new DatabaseResource();
        DatabaseResource db2 = new DatabaseResource();
        db1.setTableResourceList(tableResources);
        db2.setTableResourceList(tableResources);
        db1.setName("DB1");
        db2.setName("DB2");

        getDatabaseResourceList().add(db1);
        getDatabaseResourceList().add(db2);
    }


}
