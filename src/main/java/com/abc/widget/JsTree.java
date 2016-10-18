package com.abc.widget;

import java.util.ArrayList;
import java.util.List;

import com.abc.activitytype.view.AnalysisActivityView;
import org.metaworks.annotation.Face;


public class JsTree {

    public JsTree () {

        this.SetData();
    }

    private List<TreeNode> nodeList;

    public List<TreeNode> getNodeList() { return nodeList; }
    public void setNodeList(List<TreeNode> nodeList) { this.nodeList = nodeList; }

    private void SetData() {
/*
        TreeNode tn = new TreeNode("Hive");
        tn.setChildNode(new ArrayList<TreeNode>());

        for (int i = 1; i <= 3; i++) {

            TreeNode tnChild = new TreeNode("Child " + i);

            tn.getChildNode().add(tnChild);
        }

        TreeNode tn2 = new TreeNode("PostgreSQL");
        tn2.setChildNode(new ArrayList<TreeNode>());

        for (int i = 1; i <= 3; i++) {

            TreeNode tnChild = new TreeNode("Child " + i);

            tnChild.setObject((new AnalysisActivityView()).createSymbol());

            tn2.getChildNode().add(tnChild);
        }

        this.setNodeList(new ArrayList<TreeNode>());

        this.getNodeList().add(tn);
        this.getNodeList().add(tn2);
        */
    }

}
