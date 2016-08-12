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

    public DatabaseNavigator(){
        onLoad();
    }

    @ServiceMethod(onLoad = true, target = ServiceMethod.TARGET_SELF)
    public void onLoad(){
        setDatabaseResourceList(new ArrayList<DatabaseResource>());

        DatabaseResource databaseResource = new DatabaseResource();{
            databaseResource.setName("DB1");

            TableResource tableResource = new TableResource();{
                tableResource.setName("table1");
                tableResource.setFieldList(new ArrayList<Field>());

                Field field = new Field();{
                    field.setName("field1");
                }

                tableResource.getFieldList().add(field);
            }

            databaseResource.setTableResourceList(new ArrayList<TableResource>());
            databaseResource.getTableResourceList().add(tableResource);

            getDatabaseResourceList().add(databaseResource);

        }

    }


}
