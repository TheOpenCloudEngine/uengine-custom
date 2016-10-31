package com.abc.modeler;

import com.abc.modeler.dbnavigator.DatabaseNavigator;
import com.abc.widget.Accordion;
import org.metaworks.MetaworksContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Available;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dao.TransactionContext;
import org.metaworks.dwr.MetaworksRemoteService;
import org.springframework.stereotype.Service;
import org.uengine.modeling.resource.*;
import org.uengine.processadmin.ProcessAdminResourceNavigator;
import org.uengine.processadmin.RecentEditedResourcesPanel;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends Workbench {


    public AnalysisProcessAdminWorkbench() throws Exception {

   //     super(new SubjectExplorer());
        super(new ProcessAdminResourceNavigator());

        setTemplateExplorer(new TemplateExplorer());

        ((DefaultResource)getResourceNavigator().getRoot()).setDisplayName("SK 하이닉스");

        String defaultLoadingResourcePath = (String) TransactionContext.getThreadLocalInstance().getSharedContext("defaultLoadingResourcePath");

        if(defaultLoadingResourcePath!=null){
            setDefaultLoadingResource(defaultLoadingResourcePath);
            //setEditorPanel(defaultLoadingResource._newAndOpen(false));
        }else{
            try {

                // 로그인후 바로 새로운 프로세스 생성하는 캔버스 화면으로 이동하게...
                AnalysisContainerResource analysisContainerResource = new AnalysisContainerResource();
                analysisContainerResource.setPath("codi/New Process.process");
                analysisContainerResource.setParent(analysisContainerResource);


                EditorPanel editorPanel = (EditorPanel) MetaworksRemoteService.getComponent(EditorPanel.class);
                editorPanel.setResourcePath(analysisContainerResource.getPath());
                editorPanel.setMetaworksContext(new MetaworksContext());
                editorPanel.getMetaworksContext().setWhen("edit");
                editorPanel.setNew(true);
                String type = analysisContainerResource.getType();
                String classNamePrefix = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
                Class editorClass = Thread.currentThread().getContextClassLoader().loadClass("org.uengine.modeling.resource.editor." + classNamePrefix + "Editor");
                IEditor editor = (IEditor)editorClass.newInstance();
                editor.setEditingObject(editor.newObject(analysisContainerResource));
                editorPanel.setNew(true);
                editorPanel.setEditor(editor);

                setEditorPanel(editorPanel);

                //setEditorPanel(new RecentEditedResourcesPanel(getResourceNavigator()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    DatabaseNavigator databaseNavigator;
    public DatabaseNavigator getDatabaseNavigator() {
        return new DatabaseNavigator();
    }
    public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
        this.databaseNavigator = databaseNavigator;
    }

    TemplateExplorer templateExplorer;
        public TemplateExplorer getTemplateExplorer() {
            return templateExplorer;
        }
        public void setTemplateExplorer(TemplateExplorer templateExplorer) {
            this.templateExplorer = templateExplorer;
        }


    String defaultLoadingResource;
        public String getDefaultLoadingResource() {
            return defaultLoadingResource;
        }
        public void setDefaultLoadingResource(String defaultLoadingResource) {
            this.defaultLoadingResource = defaultLoadingResource;
        }


    @ServiceMethod(onLoad = true, target= ServiceMethodContext.TARGET_POPUP, payload = "defaultLoadingResource")
    @Available(condition = "defaultLoadingResource != null")
    public void loadDefaultResource() throws Exception {
        DefaultResource defaultLoadingResource = (DefaultResource) DefaultResource.createResource(getDefaultLoadingResource());

        defaultLoadingResource._newAndOpen(false);
    }
//
//    DatabaseNavigator databaseNavigator;
//        public DatabaseNavigator getDatabaseNavigator() {
//            return databaseNavigator;
//        }
//        public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
//            this.databaseNavigator = databaseNavigator;
//        }

}
