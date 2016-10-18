package com.abc.modeler;

import java.util.List;

import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.uengine.modeling.resource.VersionManager;
import org.uengine.processadmin.ProcessAdminResourceNavigator;

import com.abc.widget.TreeNode;

/**
 * Created by Administrator on 2016-09-26.
 */
public class SubjectExplorer extends ProcessAdminResourceNavigator {
	private List<TreeNode> nodeList;
	public List<TreeNode> getNodeList() { return nodeList; }
	public void setNodeList(List<TreeNode> nodeList) { this.nodeList = nodeList; }
	
	public SubjectExplorer() {}
		
    @Override    
    @Hidden
    public VersionManager versionManager() throws Exception {    	
    	return super.versionManager();    	
    }
        
    @ServiceMethod
    public void refresh() throws Exception {
    	load();      
    }
}
