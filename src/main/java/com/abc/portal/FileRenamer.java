package com.abc.portal;

import org.metaworks.annotation.ServiceMethod;
import org.uengine.modeling.resource.DefaultResource;
import org.uengine.modeling.resource.resources.ModelResource;

/**
 * Created by jjy on 2016. 10. 10..
 */
public class FileRenamer {

    public FileRenamer() {
    }

    public FileRenamer(ModelResource modelResource) {
        setResourcePath(modelResource.getPath());
    }

    String resourcePath;
        public String getResourcePath() {
            return resourcePath;
        }
        public void setResourcePath(String resourcePath) {
            this.resourcePath = resourcePath;
        }

    String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }


    @ServiceMethod
    public void rename(){
        DefaultResource resource = new DefaultResource(getResourcePath());
        resource.rename(getName());
    }
}
