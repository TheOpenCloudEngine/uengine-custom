package com.abc.face;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.annotation.Face;
import org.metaworks.component.SelectBox;
import org.uengine.essencia.util.ContextUtil;

/**
 * Created by uEngineYBS on 2016-08-09.
 */
@Face(ejsPath = "dwr/metaworks/org/uengine/essencia/model/ChildProperty.ejs")
public abstract class MainSelectBoxFace implements ContextAware {

    private transient MetaworksContext metaworksContext;
        @Override
        public MetaworksContext getMetaworksContext() {
        return this.metaworksContext;
        }
        @Override
        public void setMetaworksContext(MetaworksContext context) {
            this.metaworksContext = context;
        }

    private transient SelectBox mainSelectBox;
        public SelectBox getMainSelectBox() {
            return this.mainSelectBox;
        }
        public void setMainSelectBox(SelectBox mainSelectBox) {
        this.mainSelectBox = mainSelectBox;
    }

    public MainSelectBoxFace() {
        ContextUtil.setWhen(this, MetaworksContext.WHEN_EDIT);
        this.setMainSelectBox(new SelectBox());
    }
}
