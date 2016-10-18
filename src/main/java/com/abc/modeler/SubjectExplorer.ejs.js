var treeIdSub       = "";
var inputId      = "";
var selectedNode = null;
var prefixUserId = "";
var thisObjectId = null;
var originObjectId = null;

var com_abc_modeler_SubjectExplorer = function(objectId, className) {
//	debugger;

    // TODO: [ysJeong] 확인 필요 ///////////////////////////////////////////////////////////
    if (thisObjectId == null) {
        thisObjectId = objectId;
        originObjectId = objectId;
    }
//	thisObjectId = objectId;
    ////////////////////////////////////////////////////////////////////////////////////
//	console.log("#1[%s, %s, %s]", objectId, thisObjectId, originObjectId);	
//	treeIdSub  			= '#jstreeSub_' + objectId;
    treeIdSub  			= '#jstreeSub_' + originObjectId;
//	inputId 			= '#searchIptSub_' + objectId;;	
    inputId 			= '#searchIptSub_' + originObjectId;
    prefixUserId 		= "prefix";//getUserIdAddPrefix(getUserId());		// prefix + userid 를 만든다.
//	console.log(">>>>> [ID Prefix] %s\n", prefixUserId);	
//	console.log("#2[%s, %s, %s]", objectId, thisObjectId, originObjectId);

    this.objectId    	= objectId;
    this.className   	= className;
    this.object      	= mw3.objects[this.objectId];
    this.objectDivId 	= mw3._getObjectDivId(this.objectId);
    this.objectDiv   	= $('#' + this.objectDivId);
    this.objectDiv.css({'height': '100%'});

//	var child    = this.objectDiv.parent().parent().children();
//	var interval = setInterval(function() { child.css({'height': '45%'}); }, 1000);
//	this.objectDiv.on("remove", function () { clearInterval(interval); });

//	if ($(treeIdSub).jstree(true)) {
//		$(treeIdSub).jstree(true).destroy();
//	}

    $(treeIdSub).jstree({
        core 		: 	{
            "open_parents"   : true,
            "load_open"      : true,
            "animation"      : 1,
            "check_callback" : true/*,
             "themes"         : { "stripes" : true }*/
        },
        plugins 	: 	[ "contextmenu", "search", "state", "types", "wholerow" ],
        types 		: 	{ 'file' : { 'icon': 'jstree-icon jstree-file' } },
        search  	: 	{
            "case_insensitive": true,
            "show_only_matches" : true
        },
        contextmenu :	{ items : createSubjectExplorerContextMenu }
    })
        .bind("select_node.jstree", function(evt, data) { selectedNode = data.node; })
        .bind("dblclick.jstree",    function(evt)       { if (selectedNode.original.nodetype == "process") com_abc_modeler_SubjectExplorer_Open(selectedNode); });

    $(inputId).val("");

    $(inputId).keyup(function() {
        var searchString = $(this).val();
//		console.log(searchString);		
        $(treeIdSub).jstree('search', searchString);
    });

    com_abc_modeler_SubjectExplorer_createJsTree(this.object);
}

function com_abc_modeler_SubjectExplorer_createJsTree(obj) {
    $(treeIdSub).jstree(true).refresh();

    var node = $(treeIdSub).jstree(true).create_node("#", { nodetype : obj.root.type, path : obj.root.path, text : "SK 하이닉스" }, 'last');

    var children =  obj.root.children;
    for (var idx in children) {
        com_abc_modeler_SubjectExplorer_addFolderNode(node, children[idx]);
    }

    $(treeIdSub).jstree(true).open_all(node);
}

function com_abc_modeler_SubjectExplorer_addFolderNode(node, data) {
    if (data.type == "folder") {
        var p_node = $(treeIdSub).jstree(true).create_node(node, { nodetype : data.type, path : data.path, text : data.displayName }, 'last');

        var child = data.children;
        for (var idx in child) {
            if (child[idx].type == "folder") {
                com_abc_modeler_SubjectExplorer_addFolderNode(p_node, child[idx]);
            } else {
                com_abc_modeler_SubjectExplorer_addFileNode(p_node, child[idx]);
            }
        }
    } else {
        com_abc_modeler_SubjectExplorer_addFileNode(node, data);
    }
}

function com_abc_modeler_SubjectExplorer_addFileNode(node, data) {
//	debugger;
    $(treeIdSub).jstree(true).create_node(node, { nodetype : data.type, path : data.path, text : data.displayName, type : "file" }, 'last');
    var temp = $("#subjectNodeList").val();
    $("#subjectNodeList").val(temp + "::" + data.path);
}

function createSubjectExplorerContextMenu($node) {
//	debugger;	
    var tree = $(treeIdSub).jstree(true);

    // TODO: [ysJeong] 다국어 적용 필요
    var items = {
        Refresh			: { separator_before: false, separator_after: false, label: "Refresh",		action: function (obj) { com_abc_modeler_SubjectExplorer_Refresh(); } },
        Open			: { separator_before: false, separator_after: false, label: "Open",   		action: function (obj) { com_abc_modeler_SubjectExplorer_Open($node); } },
        Import			: { separator_before: false, separator_after: false, label: "Import",   	action: function (obj) { com_abc_modeler_SubjectExplorer_Import($node); } },
        NewFolder		: { separator_before: false, separator_after: false, label: "New Subject",  action: function (obj) { com_abc_modeler_SubjectExplorer_NewDir($node); } },
        NewProcess		: { separator_before: false, separator_after: false, label: "New Model",   	action: function (obj) { com_abc_modeler_SubjectExplorer_NewProc($node); } },
        CopyAs			: { separator_before: false, separator_after: false, label: "Copy",   		action: function (obj) { com_abc_modeler_SubjectExplorer_CopyAs($node); } },
        Rename			: { separator_before: false, separator_after: false, label: "Rename", 		action: function (obj) { com_abc_modeler_SubjectExplorer_Rename($node); } },
        Delete			: { separator_before: false, separator_after: false, label: "Delete",  		action: function (obj) { com_abc_modeler_SubjectExplorer_Delete($node); } },
        Publish			: { separator_before: false, separator_after: false, label: "Publish",   	action: function (obj) { com_abc_modeler_SubjectExplorer_Publish($node); }  },
        Restore			: { separator_before: false, separator_after: false, label: "Restore",   	action: function (obj) { com_abc_modeler_SubjectExplorer_Restore($node); } },
        Share			: { separator_before: false, separator_after: false, label: "Share",   		action: function (obj) { com_abc_modeler_SubjectExplorer_Share($node); } },
        Simulate		: { separator_before: false, separator_after: false, label: "Run",   		action: function (obj) { com_abc_modeler_SubjectExplorer_Simulate($node); } },
        VersionManager	: { separator_before: false, separator_after: false, label: "Version",   	action: function (obj) { com_abc_modeler_SubjectExplorer_VersionManager($node); } }
    };

    // TODO: [ysJeong] node type에 따른 메뉴 항목 정리 필요
    if (typeof $node.original != "undefined" && $node.original != null) {
        switch ($node.original.nodetype) {
            case "root":
            case "folder":
                delete items.Simulate;
                delete items.Open;
                break;

            case "process":
//				delete items.Refresh;
                delete items.Import;
                delete items.NewProcess;
                delete items.NewFolder;
//				delete items.VersionManager;
                break;
        }
    }

    return items;
}

function com_abc_modeler_SubjectExplorer_Refresh() {
//	console.log("refresh [%s]", thisObjectId);
    mw3.objects[thisObjectId].refresh();
    $("#subjectNodeList").val("");
}

function com_abc_modeler_SubjectExplorer_Open(node) {
//	debugger;	
    if (node.original.nodetype == "process") {
        var resource = new MetaworksObject({
            __className : "org.uengine.modeling.resource.resources.ProcessResource",
            path: node.original.path
        }, 'body');

        resource.open();
//		$(".header_box").text(node.original.text.substring(0, node.original.text.indexOf(".")));
    }
}

function com_abc_modeler_SubjectExplorer_Import(node) {
//	debugger;
    if (node.original.nodetype == "root" || node.original.nodetype == "folder") {
        var resource = new MetaworksObject({
            __className : "org.uengine.essencia.portal.EssenciaProcessAdminContainerResource",
            path: node.original.path
        }, 'body');

        resource.importResource();
    }
}

function com_abc_modeler_SubjectExplorer_NewProc(node) {
//	debugger;
    if (node.original.nodetype == "root" || node.original.nodetype == "folder") {
        var resource = new MetaworksObject({
            __className : "org.uengine.essencia.portal.EssenciaProcessAdminContainerResource",
            path: node.original.path
        }, 'body');

        resource.newProcess();
    }
}

function com_abc_modeler_SubjectExplorer_NewDir(node) {
//	debugger;
    if (node.original.nodetype == "root" || node.original.nodetype == "folder") {
        var resource = new MetaworksObject({
            __className : "org.uengine.essencia.portal.EssenciaProcessAdminContainerResource",
            path: node.original.path
        }, 'body');

        resource.openNewFolderPopup();
    }
}

function com_abc_modeler_SubjectExplorer_CopyAs(node) {
    //debugger;

    if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
        mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").saveAs();
    }
}

function com_abc_modeler_SubjectExplorer_Rename(node) {
    //debugger;
    if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
        mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").rename();
    }
//	else {		
//		if (node.original.nodetype == "process") {
//			var resource = new MetaworksObject({
//				__className : "org.uengine.modeling.resource.resources.ProcessResource",			
//				path: node.original.path
//			}, 'body');
//				
//			resource.rename();		
//		}		
//	}	
}

function com_abc_modeler_SubjectExplorer_Delete(node) {
//	debugger;

    // TODO: [ysJeong] 다국어 확인 필요
    var msg = node.original.text;
    msg += (node.original.nodetype == "folder") ?
        "의 하위 폴더 및 파일이 모두 삭제됩니다. 삭제 하시겠습니까?" : "를 삭제 하시겠습니까?";

    var ret = confirm(msg);

    if (ret) {
        if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
            mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").delete();
        } else {
            var resource = new MetaworksObject({
                __className : "org.uengine.essencia.portal.EssenciaProcessAdminContainerResource",
                path: node.original.path
            }, 'body');

            resource.delete();
        }

        com_abc_modeler_SubjectExplorer_Refresh();
    }
}

function com_abc_modeler_SubjectExplorer_Publish(node) {

}

function com_abc_modeler_SubjectExplorer_Restore(node) {

}

function com_abc_modeler_SubjectExplorer_Share(node) {

}

// 공통메뉴쪽으로 빼야 하는가?
function com_abc_modeler_SubjectExplorer_Simulate(node) {
    //debugger;

    if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
        mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").simulate();
    } else {
        if (node.original.nodetype == "process") {
            var resource = new MetaworksObject({
                __className : "org.uengine.modeling.resource.resources.ProcessResource",
                path: node.original.path
            }, 'body');

            resource.simulate();
        }
    }
}

function com_abc_modeler_SubjectExplorer_VersionManager(node) {
    if (node.original.nodetype == "root" || node.original.nodetype == "folder") {
        var resource = new MetaworksObject({
            __className : "org.uengine.essencia.portal.EssenciaProcessAdminContainerResource",
            path: node.original.path
        }, 'body');

        resource.versionManager();
    }
}