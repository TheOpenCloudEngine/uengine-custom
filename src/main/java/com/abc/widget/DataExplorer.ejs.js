var treeId       = "";
var inputId      = "";
var selectedNode = null;

var prefixUserId = "";

/**
 * $(document).ready(); 랑 같음
 */
var com_abc_widget_DataExplorer = function (objectId, className) {

	treeId  = '#jstree_' + objectId;
	inputId = '#searchIptData_' + objectId;	
	// prefix + userid 를 만든다.
	prefixUserId = getUserIdAddPrefix(getUserId()); 
	
	console.log(" -> ID Prefix : " + prefixUserId);
	
    this.objectId    = objectId;
    this.className   = className;
    this.object      = mw3.objects[this.objectId];
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv   = $('#' + this.objectDivId);
    
    this.objectDiv.css({'height': '100%'});
    
    var child    = this.objectDiv.parent().parent().children();
    
    //kimsh. 2016.10.12 퍼블리셔 요청으로 아래 두줄 제거    
    //var interval = setInterval(function() { child.css({'height': '45%'}); }, 1000);
    //this.objectDiv.on("remove", function () { clearInterval(interval); });	
	
    // Tree 초기화
	$(treeId).jstree({		
		core    	: 	{
						  "open_parents"   : true,
						  "load_open"      : true, 
						  "animation"      : 1,
						  "check_callback" : true/*,
				          "themes"         : { "stripes" : true }*/ 
		           		},
		plugins 	: 	[ "contextmenu", "search", "state", "types", "wholerow" ],
        types   	: 	{ 'file' : { 'icon': 'jstree-icon jstree-file' } },
        search  	: 	{ 
        				  "case_insensitive": true,
        				  "show_only_matches" : true 
        				},
		contextmenu : 	{ items : createDataExplorerContextMenu }
	})
	.bind("select_node.jstree", function(evt, data) { selectedNode = data.node; })
	.bind("dblclick.jstree",    function(evt)       { if (selectedNode.original.nodetype == "Table") com_abc_widget_DataExplorer_Refresh(selectedNode); });
	
//	console.log("JsTree...");	
			
	$(inputId).val("");
	
	$(inputId).keyup(function() {
		var searchString = $(this).val();
//		console.log(searchString);		
		$(treeId).jstree('search', searchString);
	});
	
	var tree = $(treeId).jstree(true);
	
	// PostgreSQL에 등록된 SKA_CODE_DEF 테이블에서 코드를 조회한다.
	var rootNodes = getCode("storage_type");
	
	for (idx in rootNodes) {	
		var node = tree.create_node("#", { nodetype : 'root', dbtype : rootNodes[idx].code, text : rootNodes[idx].disp_code }, 'last');		
		com_abc_widget_DataExplorer_GetUserStorage(tree, node);
	}    	
}

/**
 * Context Menu를 생성한다.
 * @param $node
 * @returns {___anonymous1592_2410}
 */
function createDataExplorerContextMenu($node) {
	var tree = $(treeId).jstree(true);
	
	// TODO: [ysJeong] 다국어 적용 필요
	var items = { 
		Refresh  : { separator_before: false, separator_after: false, label: "새로 고침",	action: function (obj) { com_abc_widget_DataExplorer_Refresh($node); } },
		DataView : { separator_before: false, separator_after: false, label: "자료 조회",	action: function (obj) { com_abc_widget_DataExplorer_ShowDataView($node.original.dbtype, $node.original.schema, $node.original.text); } },
		Create   : { separator_before: false, separator_after: false, label: "생성",   	action: function (obj) { com_abc_widget_DataExplorer_ShowCreateUserStorage($node.original.dbtype); } },
		Remove   : { separator_before: false, separator_after: false, label: "삭제",   	action: function (obj) { com_abc_widget_DataExplorer_ShowDeletePopup($node); } }
	};

	// NodeType 별 컨텍스트메뉴를 설정한다.
	if (typeof $node.original != "undefined" && $node.original != null) {
		
		switch ($node.original.nodetype) {	  
			case "root":			  
				delete items.DataView;
				delete items.Remove;
				break;
			  
			case "Database":
				delete items.Create;
				delete items.DataView;
				break;
			  
			case "Table":
				delete items.Create;				
				// 본인 계정 폴더가 아닐경우 삭제 메뉴를 제거한다.
				if ($node.original.schema != prefixUserId) delete items.Remove;				
				break;
			  
			case "Field":
				delete items.Refresh;
				delete items.DataView;
				delete items.Create;
				delete items.Remove;
				break;
		}
	}

	return items;
}

/**
 * Node를 재조회 한다.
 * 
 * @param node
 */
function refreshJsTree() {
//kimsh. 주석제거
	com_abc_widget_DataExplorer_Refresh(selectedNode);
}

function com_abc_widget_DataExplorer_Refresh(node) {	
	
	$(inputId).val("");

	switch (node.original.nodetype) {
		
		case "root":     com_abc_widget_DataExplorer_GetUserStorage($(treeId).jstree(true), node); break;
		case "Database": com_abc_widget_DataExplorer_GetTableList(node,  node.original.dbtype, node.original.schema); break;
		case "Table":    com_abc_widget_DataExplorer_GetColumnList(node, node.original.dbtype, node.original.schema, node.original.text); break;
	}

	$(treeId).jstree(true).open_node(node);
}

function com_abc_widget_DataExplorer_GetSetPrivateStorage(tree) {
	
}

/**
 * Form Loading 시 사용자 ID에 묶인 설정을 조회한다.
 * @param tree
 * @param parentNode
 */
function com_abc_widget_DataExplorer_GetUserStorage(tree, parentNode) {

	// 기존에 등록된 node를 삭제한다.
	deleteJsTreeChildNode(treeId, parentNode);
	
	// 사용자별 등록된 Storage 목록을 조회
	var results = fncCallAjax("ska/DataExplorerController/getUserStorage", { USERID : getUserId() });
	var userNodes = results.result;
	
	// 없을 경우 Pass
	if (typeof userNodes != "undefined" && userNodes != null) {
	
		// Parent node ID로 실 Node Object를 가져온다.
		var parentObject = $(treeId).jstree(true).get_node(parentNode);
		
		// dbtype이 같은 항목만 필터한다.
		var filterNodes = userNodes.filter(function(item) { 
			return item.dbtype === parentObject.original.dbtype;
		});
		
		// 갯수 만큼 애드 후 하위노트(테이블목록) 조회
		for (var idx in filterNodes) {
			
			var node = tree.create_node(parentNode, { nodetype : 'Database', 
				                                      dbtype   : filterNodes[idx].dbtype, 
				                                      schema   : filterNodes[idx].schema, 
				                                      text     : filterNodes[idx].schemaname}, 'last');
	
			com_abc_widget_DataExplorer_GetTableList(node, filterNodes[idx].dbtype, filterNodes[idx].schema);
		}
		
		// 사용자의 전용 Schema를 추가한다. ( Hive일 경우만 )
		if (parentObject.original.dbtype == "Hive") {
			
			// dbtype이 같은 항목만 필터한다.
			filterNodes = userNodes.filter(function(item) { 
				return item.dbtype === parentObject.original.dbtype && item.schema == prefixUserId;
			});

			// 기존에 등록된 Node가 없을경우 ska_user_storage 에 넣어준다.
			if (filterNodes == null || typeof filterNodes == "undefined" || filterNodes.length == 0) {
				
		        var param = {
		                
	                USERID       : getUserId(),
	                STORAGE_TYPE : parentObject.original.dbtype,
	                SCHEMA_NAME  : prefixUserId,
	                DISP_NAME    : "SandBox"
		        };
		        
		        // insert
		        fncCallAjax("ska/DataExplorerController/createUserStorage", param);
				
		        // Node 추가
				var node = tree.create_node(parentNode, { nodetype : 'Database', 
	                                                      dbtype   : param.STORAGE_TYPE, 
	                                                      schema   : param.SCHEMA_NAME, 
	                                                      text     : param.DISP_NAME  }, 'last');
	
				// Table 목록 조회
				com_abc_widget_DataExplorer_GetTableList(node, filterNodes[idx].dbtype, filterNodes[idx].schema);
			
			}
		}

		
		// root/Database 까지는 노드를 Open 한다.
		tree.open_node(parentNode);
	}
}

/**
 * Callbak 모달 팝업창을 띄운다.
 * 
 * @param node
 */

function com_abc_widget_DataExplorer_ShowDeletePopup(node) {

	 console.log(JSON.stringify(node));

//	  var results = fncCallAjax("ska/CommonController/getCode", { USERID : "D:\aaa"  });
//    console.log(JSON.stringify(results));
	
	//kimsh. 아래로 변경	
	selectedNode = node;
	
	//showMessageModalConfirm("delete ? ", "deleteUserStorage");
	showMessageModalConfirm("정말로 삭제하시겠습니까? ", "com_abc_widget_DataExplorer_DeleteUserStorage");
	
}

/**
 * 선택한 노드를 삭제한다.
 * @param nodeId
 */
function com_abc_widget_DataExplorer_DeleteUserStorage() {

	var authType = selectedNode.original.schema == prefixUserId && selectedNode.original.nodetype == "Table" ? "Private" : "Public";
	
	var results = fncCallAjax("ska/DataExplorerController/deleteUserStorage", { USERID       : getUserId(),
		                                                                        STORAGE_TYPE : selectedNode.original.dbtype, 
		                                                                        SCHEMA_NAME  : selectedNode.original.schema,
		                                                                        TABLE_NAME   : selectedNode.original.text,
		                                                                        AUTH_TYPE    : authType });
	if (results.successYn == "Y") {
	
		$(treeId).jstree(true).delete_node(selectedNode);
		selectedNode = null;
	}
}

/**
 * 테이블 목록을 조회한다.
 * @param node
 * @param dbType
 * @param tableSchema
 */
function com_abc_widget_DataExplorer_GetTableList(node, dbType, tableSchema) {

	var results = fncCallAjax("ska/CommonController/getTableList", { DBTYPE : dbType, TABLESCHEMA : tableSchema });

    if (results.successYn == "Y") {

		deleteJsTreeChildNode(treeId, node);

		for(var idx in results.result) {
			
			$(treeId).jstree(true).create_node(node, { nodetype : "Table", dbtype : dbType, schema : tableSchema, text : results.result[idx].TABLE_NAME } , 'last');
		}
	}		
}


/**
 * 컬럼 목록을 조회한다.
 * @param node
 * @param dbType
 * @param tableSchema
 * @param tableName
 */
function com_abc_widget_DataExplorer_GetColumnList(node, dbType, tableSchema, tableName) {

	var results = fncCallAjax("ska/CommonController/getTableColumnList", { DBTYPE : dbType, TABLESCHEMA : tableSchema, TABLENAME : tableName });

    if (results.successYn == "Y") {

		deleteJsTreeChildNode(treeId, node);

		for(var idx in results.result) {
			
			$(treeId).jstree(true).create_node(node, { nodetype : "Field", dbtype : dbType, schema : tableSchema, text : results.result[idx].COLUMN_NAME, type  : "file" } , 'last');
		}
	}		
}

/**
 * 팝업을 띄운다.(Create)
 * 
 * @param dbType
 * @param tableSchema
 * @param tableName
 */
function com_abc_widget_DataExplorer_ShowCreateUserStorage(dbType) { 

	var paramData = new Object();
	
	paramData.TARGETMODALID = "openMCommonView";
	paramData.PAGENAME      = "popup/dataExplorerCreate"
	paramData.DBTYPE        = dbType;
	
	showAjaxHtmlModal(paramData.TARGETMODALID, "ska/CommonController/LinkModal.do", "json", paramData);
	
	$("#openMCommonView").draggable();	
}


/**
 * 팝업을 띄운다.(Data View)
 * 
 * @param dbType
 * @param tableSchema
 * @param tableName
 */
function com_abc_widget_DataExplorer_ShowDataView(dbType, tableSchema, tableName) { 

	var paramData = new Object();
	
	paramData.TARGETMODALID = "openMCommonView";
	paramData.PAGENAME      = "popup/dataExplorerDataView"
	paramData.DBTYPE        = dbType;
	paramData.TABLESCHEMA   = tableSchema;
	paramData.TABLENAME     = tableName;
	
	showAjaxHtmlModal(paramData.TARGETMODALID, "ska/CommonController/LinkModal.do", "json", paramData);
	
	$("#openMCommonView").draggable();
}
