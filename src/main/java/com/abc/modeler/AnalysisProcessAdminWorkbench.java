package com.abc.modeler;

import java.util.List;

import org.metaworks.MetaworksContext;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.modeling.resource.DefaultResource;
import org.uengine.modeling.resource.EditorPanel;
import org.uengine.modeling.resource.IEditor;
import org.uengine.modeling.resource.IResource;
import org.uengine.modeling.resource.Workbench;
import org.uengine.modeling.resource.resources.ProcessResource;

import com.abc.widget.CommonMenus;
import com.abc.widget.DataExplorer;

/**
 * Created by jjy on 2016. 8. 12..
 */
public class AnalysisProcessAdminWorkbench extends Workbench {
    DataExplorer dataExplorer;
    SubjectExplorer subjectExplorer;
    TemplateExplorer templateExplorer;
    
	CommonMenus commonMenus;    

	List<IResource> childList = null;
        
    public SubjectExplorer getSubjectExplorer() { 							
    	return new SubjectExplorer(); 						
    }
    
    public void setSubjectExplorer(SubjectExplorer subjectExplorer) { 
    	this.subjectExplorer = subjectExplorer; 
    }

	public DataExplorer getDataExplorer() { 
		return new DataExplorer(); 							
	}
	
	public void setDataExplorer(DataExplorer dataExplorer) { 
		this.dataExplorer = dataExplorer; 
	}
	
	public TemplateExplorer getTemplateExplorer() {
		return new TemplateExplorer();
	}

	public void setTemplateExplorer(TemplateExplorer templateExplorer) {
		this.templateExplorer = templateExplorer;
	}
	
	public CommonMenus getCommonMenus() {
		return new CommonMenus();
	}

	public void setCommonMenus(CommonMenus commonMenus) {
		this.commonMenus = commonMenus;
	}
		   
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AnalysisProcessAdminWorkbench() {
    	super(new SubjectExplorer());	
//    	super(new ProcessAdminResourceNavigator());               
        ((DefaultResource)getResourceNavigator().getRoot()).setDisplayName("SK 하이닉스");        
        try {        	 
            ProcessResource processResource = new ProcessResource();
            processResource.setPath("codi/New Process.process");

            EditorPanel editorPanel = (EditorPanel) MetaworksRemoteService.getComponent(EditorPanel.class);
            editorPanel.setResourcePath(processResource.getPath());
            editorPanel.setMetaworksContext(new MetaworksContext());
            editorPanel.getMetaworksContext().setWhen("edit");
            editorPanel.setNew(true);
            String type = processResource.getType();
            String classNamePrefix = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
			Class editorClass = Thread.currentThread().getContextClassLoader().loadClass("org.uengine.modeling.resource.editor." + classNamePrefix + "Editor");
            IEditor editor = (IEditor)editorClass.newInstance();
            editor.setEditingObject(editor.newObject(processResource));
            editorPanel.setNew(true);
            editorPanel.setEditor(editor);
            
            setEditorPanel(editorPanel);

            //setEditorPanel(new RecentEditedResourcesPanel(getResourceNavigator()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
