package com.abc.widget;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

@Face(ejsPathForArray="dwr/metaworks/genericfaces/ArrayFace_NoDiv.ejs")
public class TreeNodeThird {

	public TreeNodeThird() { }
	public TreeNodeThird(String name) { this.setName(name); }
	
	private String         name;
	
	private TreeNodeSecond parentNode;
	private String         type;
	private Object         object;

	private boolean        lastNode = false;;

	@Name
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public TreeNodeSecond getParentNode() { return parentNode; }
	public void setParentNode(TreeNodeSecond parentNode) { this.parentNode = parentNode; }
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public Object getObject() { return object; }
	public void setObject(Object object) { this.object = object; }
	
	public boolean isLastNode() { return lastNode; }
	public void setLastNode(boolean lastNode) { this.lastNode = lastNode; }
	
	
}
