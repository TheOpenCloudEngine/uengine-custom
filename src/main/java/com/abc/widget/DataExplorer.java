package com.abc.widget;

import java.util.List;

public class DataExplorer {
	private List<TreeNode> nodeList;
	public List<TreeNode> getNodeList() { return nodeList; }
	public void setNodeList(List<TreeNode> nodeList) { this.nodeList = nodeList; }
	
	public DataExplorer () { }
	
	/*
	private void SetData() {
	
		TreeNode tn = new TreeNode("dw_archive");
		tn.setType(NodeType.Database);
		tn.setChildNode(new ArrayList<TreeNodeSecond>());

		this.setNodeList(new ArrayList<TreeNode>());
		
		List<Map<String, Object>> results = CommonFunction.getTableList(DBType.Hive, tn.getName());
		
    	for (int idx = 0; idx < results.size(); idx++) {
		
    		Map<String, Object> result = results.get(idx);
    		
    		String tableName = result.get("TABLE_NAME").toString();
			
			TreeNodeSecond tnChild = new TreeNodeSecond(tableName);
			
			tnChild.setParentNode(tn);
			tnChild.setType(NodeType.Table);
			tnChild.setChildNode(new ArrayList<TreeNodeThird>());

			tn.getChildNode().add(tnChild);
		}

		this.getNodeList().add(tn);

		tn = new TreeNode("dw_active");
		tn.setType(NodeType.Database);
		tn.setChildNode(new ArrayList<TreeNodeSecond>());
		tn.setLastNode(true);

		results = CommonFunction.getTableList(DBType.PostgreSQL, tn.getName());
		
    	for (int idx = 0; idx < results.size(); idx++) {
		
    		Map<String, Object> result = results.get(idx);
    		
    		String tableName = result.get("TABLE_NAME").toString();
			
			TreeNodeSecond tnChild = new TreeNodeSecond(tableName);
			
			tnChild.setParentNode(tn);
			tnChild.setType(NodeType.Table);
			tnChild.setChildNode(new ArrayList<TreeNodeThird>());

			tn.getChildNode().add(tnChild);
		}

		this.getNodeList().add(tn);
	}
	*/
}
