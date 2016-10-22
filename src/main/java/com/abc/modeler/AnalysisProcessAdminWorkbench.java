package com.abc.modeler;

import com.abc.modeler.dbnavigator.DatabaseNavigator;
import com.abc.widget.Accordion;
import org.metaworks.MetaworksContext;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.modeling.resource.DefaultResource;
import org.uengine.modeling.resource.EditorPanel;
import org.uengine.modeling.resource.IEditor;
import org.uengine.modeling.resource.Workbench;
import org.uengine.processadmin.ProcessAdminResourceNavigator;
import org.uengine.processadmin.RecentEditedResourcesPanel;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends Workbench {


    public AnalysisProcessAdminWorkbench() {

   //     super(new SubjectExplorer());
        super(new ProcessAdminResourceNavigator());

        ((DefaultResource)getResourceNavigator().getRoot()).setDisplayName("SK 하이닉스");

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

    DatabaseNavigator databaseNavigator;
    public DatabaseNavigator getDatabaseNavigator() {
        return new DatabaseNavigator();
    }
    public void setDatabaseNavigator(DatabaseNavigator databaseNavigator) {
        this.databaseNavigator = databaseNavigator;
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
