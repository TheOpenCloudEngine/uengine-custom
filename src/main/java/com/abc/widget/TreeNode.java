package com.abc.widget;

import java.util.List;

import org.metaworks.annotation.Children;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Name;

@Face(ejsPathForArray="dwr/metaworks/genericfaces/CleanArrayFace.ejs")
public class TreeNode {

    public TreeNode() { }
    public TreeNode(String name) { this.setName(name); }

    private String name;
    private List<TreeNode> childNode;

    @Name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Children
    public List<TreeNode> getChildNode() { return childNode; }
    public void setChildNode(List<TreeNode> childNode) { this.childNode = childNode; }


    Object object;
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }


}
