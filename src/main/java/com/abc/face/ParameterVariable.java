package com.abc.face;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.uengine.contexts.ComplexType;
import org.uengine.kernel.NeedArrangementToSerialize;
import org.uengine.kernel.ProcessInstance;
import org.uengine.util.UEngineUtil;

import java.io.Serializable;

/**
 * Created by uEngineYBS on 2016-08-04.
 */
public class ParameterVariable implements Serializable, ContextAware {

    transient MetaworksContext metaworksContext;

    String parameter;
        public String getParameter() { return parameter; }
        public void setParameter(String parameter) { this.parameter = parameter; }

    public MetaworksContext getMetaworksContext() {
        return this.metaworksContext;
    }
    public void setMetaworksContext(MetaworksContext metaworksContext) {
        this.metaworksContext = metaworksContext;
    }
}
