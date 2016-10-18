package com.abc.widget;

import java.util.List;

import org.metaworks.annotation.Children;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

@Face(ejsPathForArray="dwr/metaworks/genericfaces/ArrayFace_NoDiv.ejs")
public class TreeNode {

	public TreeNode() { }
	public TreeNode(String name) { this.setName(name); }
	
	private String         name;
	private List<TreeNodeSecond> childNode;
	
	private TreeNode       parentNode;
	private String         type;
	private Object         object;
	
	private boolean        lastNode = false;;
	
	@Name
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@Children
	public List<TreeNodeSecond> getChildNode() { return childNode; }
	public void setChildNode(List<TreeNodeSecond> childNode) { this.childNode = childNode; }

	public TreeNode getParentNode() { return this.parentNode; }
	public void setParentNode(TreeNode parentNode) { this.parentNode = parentNode; }
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public Object getObject() { return object; }
	public void setObject(Object object) { this.object = object; }
	
	public boolean isLastNode() { return lastNode; }
	public void setLastNode(boolean lastNode) { this.lastNode = lastNode; }

//    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_DBLCLICK, target = ServiceMethodContext.TARGET_SELF)
//    public void onNameClicked() {
//
//    	if (this.type.equals(NodeType.Database)) {
//    		
//        	List<Map<String, Object>> results = CommonFunction.getTableList(DBType.Hive, this.name);
//
//        	if (this.getChildNode() == null) this.setChildNode(new ArrayList<TreeNodeSecond>());
//        	
//        	this.getChildNode().clear();
//        	
//        	for (int idx = 0; idx < results.size(); idx++) {
//        	
//        		Map<String, Object> result = results.get(idx);
//        		
//        		TreeNodeSecond tn = new TreeNodeSecond();
//        		tn.setName(result.get("tab_name").toString());
//        		tn.setType(NodeType.Table);
//        		tn.setParentNode(this);
//        		
//        		if (idx == results.size() - 1) tn.setLastNode(true);
//        		
//        		this.getChildNode().add(tn);
//        	}
//    		
//    	}
//    }
    
	
}
