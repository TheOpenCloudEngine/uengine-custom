package com.abc.widget;

import java.util.List;

import org.metaworks.annotation.Children;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

@Face(ejsPathForArray="dwr/metaworks/genericfaces/ArrayFace_NoDiv.ejs")
public class TreeNodeSecond {

	public TreeNodeSecond() { }
	public TreeNodeSecond(String name) { this.setName(name); }
	
	private String         name;
	private List<TreeNodeThird> childNode;

	private TreeNode       parentNode;
	private String         type;
	private Object         object;

	private boolean        lastNode = false;;

	@Name
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@Children
	public List<TreeNodeThird> getChildNode() { return childNode; }
	public void setChildNode(List<TreeNodeThird> childNode) { this.childNode = childNode; }

	public TreeNode getParentNode() { return parentNode; }
	public void setParentNode(TreeNode parentNode) { this.parentNode = parentNode; }
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public Object getObject() { return object; }
	public void setObject(Object object) { this.object = object; }
	
	public boolean isLastNode() { return lastNode; }
	public void setLastNode(boolean lastNode) { this.lastNode = lastNode; }
	
//	@Face(displayName = "Data View")
//    @ServiceMethod(inContextMenu = true, callByContent = true, target = ServiceMethodContext.TARGET_POPUP)
//    public void dataView() throws Exception {
//
//		if (this.type.equals(NodeType.Table)) {
//		
//			TableResource tr = new TableResource(); 
//			tr.setName(this.name);
//			tr.setDbName(this.parentNode.getName());
//			
//	        ModalWindow popup = new ModalWindow(new DataViewList(tr), 700, 400);
//	        popup.getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
//	        popup.setTitle("Data View");
//	        MetaworksRemoteService.wrapReturn(popup);
//		}
//    }    
//
//    @ServiceMethod(callByContent = true, eventBinding = EventContext.EVENT_DBLCLICK, target = ServiceMethodContext.TARGET_SELF)
//    public void onNameClicked() {
//
//    	if (this.type.equals(NodeType.Table)) {
//
//        	List<Map<String, Object>> results = CommonFunction.getColumnList(DBType.Hive, this.name, "dw_archive");
//        	
//        	if (this.getChildNode() == null) this.setChildNode(new ArrayList<TreeNodeThird>());
//        	
//        	this.getChildNode().clear();
//        	
//        	for (int idx = 0; idx < results.size(); idx++) {
//            	
//        		Map<String, Object> result = results.get(idx);
//        	
//        		TreeNodeThird tn = new TreeNodeThird();
//        		tn.setName(result.get("COLUMN_NAME").toString());
//        		tn.setType(NodeType.Field);
//        		tn.setParentNode(this);
//
//        		if (idx == results.size() - 1) tn.setLastNode(true);
//        		
//        		this.getChildNode().add(tn);
//        	}
//    	}
//
//    }
	
}
