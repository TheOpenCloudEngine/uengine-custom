package com.abc.modeler;

import java.util.List;

import org.uengine.processadmin.ProcessAdminResourceNavigator;

import com.abc.widget.TreeNode;

public class TemplateExplorer extends ProcessAdminResourceNavigator {	
	private List<TreeNode> nodeList;
	public List<TreeNode> getNodeList() { return nodeList; }
	public void setNodeList(List<TreeNode> nodeList) { this.nodeList = nodeList; }

	public TemplateExplorer() {}
}
