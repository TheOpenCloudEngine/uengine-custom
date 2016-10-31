package com.abc.portal;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dao.TransactionContext;
import org.uengine.codi.mw3.StartCodi;

/**
 * Created by jjy on 2016. 10. 24..
 */
public class ABCStarter extends StartCodi{

    String defaultLoadingResourcePath;

        public String getDefaultLoadingResourcePath() {
            return defaultLoadingResourcePath;
        }

        public void setDefaultLoadingResourcePath(String defaultLoadingResourcePath) {
            this.defaultLoadingResourcePath = defaultLoadingResourcePath;
        }


    @Override
    @ServiceMethod(payload={"key","lastVisitPage", "lastVisitValue", "ssoService", "defaultLoadingResourcePath"}, target= ServiceMethodContext.TARGET_APPEND)
    public Object load() throws Exception {
        if(getDefaultLoadingResourcePath()!=null){
            TransactionContext.getThreadLocalInstance().setSharedContext("defaultLoadingResourcePath", getDefaultLoadingResourcePath());
        }

        return super.load();
    }

    @Override
    public Object[] login() throws Exception {
        if(getDefaultLoadingResourcePath()!=null){
            TransactionContext.getThreadLocalInstance().setSharedContext("defaultLoadingResourcePath", getDefaultLoadingResourcePath());
        }

        return super.login();
    }
}
