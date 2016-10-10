package org.uengine.modeling.resource.resources;

import com.abc.portal.FileRenamer;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.ModalWindow;
import org.uengine.modeling.resource.resources.ProcessResource;

/**
 * Created by jjy on 2016. 10. 10..
 */
public class ModelResource extends ProcessResource{

    @ServiceMethod(payload = {"path", "newName"}, target=ServiceMethod.TARGET_NONE)
    public void rename(){
        rename(getNewName());
    }

    String newName;
        public String getNewName() {
            return newName;
        }
        public void setNewName(String newName) {
            this.newName = newName;
        }

}
