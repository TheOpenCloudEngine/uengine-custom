package com.abc.modeler.dbnavigator;

import org.metaworks.annotation.Children;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

import java.util.List;

/**
 * Created by jjy on 2016. 8. 12..
 */
@Face(ejsPath="dwr/metaworks/genericfaces/TreeFace.ejs")
public class DatabaseResource {

    String name;
    @Name
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    List<TableResource> tableResourceList;
    @Children
        public List<TableResource> getTableResourceList() {
            return tableResourceList;
        }
        public void setTableResourceList(List<TableResource> tableResourceList) {
            this.tableResourceList = tableResourceList;
        }
}
