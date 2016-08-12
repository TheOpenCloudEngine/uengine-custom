package com.abc.modeler.dbnavigator;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

/**
 * Created by jjy on 2016. 8. 12..
 */
@Face(ejsPath="dwr/metaworks/genericfaces/TreeFace.ejs")
public class Field {

    String name;
    @Name
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

}
