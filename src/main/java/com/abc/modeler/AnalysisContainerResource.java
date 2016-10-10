package com.abc.modeler;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.ServiceMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.modeling.resource.DefaultResource;
import org.uengine.processadmin.ProcessAdminContainerResource;

/**
 * Created by jjy on 2016. 10. 10..
 */
@Component
@Scope("prototype")
public class AnalysisContainerResource extends ProcessAdminContainerResource{


    @ServiceMethod(inContextMenu = true, target = ServiceMethodContext.TARGET_POPUP)
    public void newModel() throws Exception {

        DefaultResource resource = new DefaultResource();
        resource.setPath(getPath() + "/" + "New Model.model"); //Extension name will be the resource class name mapping
        resource.setParent(this);

        resource.newOpen();
    }

    @ServiceMethod(target = ServiceMethod.TARGET_NONE, payload = {"path", "copyFromFolderPath"})
    public void copy(){

        System.out.println(getCopyFromFolderPath() + " will be copied to " + getPath() );

    }

    String copyFromFolderPath;
        public String getCopyFromFolderPath() {
            return copyFromFolderPath;
        }
        public void setCopyFromFolderPath(String copyFromFolderPath) {
            this.copyFromFolderPath = copyFromFolderPath;
        }

}
