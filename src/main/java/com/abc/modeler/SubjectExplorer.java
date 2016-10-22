package com.abc.modeler;

import org.metaworks.annotation.ServiceMethod;
import org.uengine.processadmin.ProcessAdminResourceNavigator;

/**
 * Created by Administrator on 2016-09-26.
 */
public class SubjectExplorer extends ProcessAdminResourceNavigator {

    @ServiceMethod
    public void refresh(){
        load();
    }




}
