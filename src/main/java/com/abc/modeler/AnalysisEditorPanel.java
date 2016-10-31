package com.abc.modeler;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.uengine.essencia.portal.EssenciaEditorPanel;
import org.uengine.modeling.resource.ResourceNavigator;
import org.uengine.processadmin.ProcessAdminEditorPanel;
import org.uengine.processadmin.ProcessAdminResourceNavigator;

/**
 * Created by jjy on 2016. 10. 23..
 */
@Component
@Scope("prototype")
@Order(4) //makes it used.
public class AnalysisEditorPanel extends EssenciaEditorPanel {
    @Override
    protected ResourceNavigator refreshNavigator(String appName) {

        if("codi".equals(appName)){
            return new ProcessAdminResourceNavigator();
        }else if("template".equals(appName)){
            return new TemplateExplorer();
        }

        return super.refreshNavigator(appName);
    }
}
