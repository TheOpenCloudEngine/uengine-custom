/**
 * Ajax를 이용해 Data를 조회한다. (동기 방식)
 * @param url
 * @param data
 * @returns {___anonymous58_66}
 */
function fncCallAjax(url, data, dataType) {
	
	var retObject = new Object();

	var pDataType = (typeof dataType == undefined || dataType == null) ? 'json' : dataType;
	
	$.ajax({
		type     :"post",
        url      : url,
        dataType : pDataType,
        data     : data,
        async    : false,
        success  : function(data) { retObject = data; },
        complete : function(data) { },
        error    : function(xhr, status, error) { retObject = "ERROR"; }
     });		

	return retObject;
}

/**
 * JsTree 특정 노드의 하위 노드를 지운다.
 * @param treeId
 * @param currentNode
 */
function deleteJsTreeChildNode(treeId, currentNode) {

	var tree = $(treeId).jstree(true);
	
	if (typeof currentNode.children != "undefined" && currentNode.children != null) { 
	
		for (var idx = currentNode.children.length - 1; idx >= 0; idx--) {
			
			var childNode = tree.get_node(currentNode.children[idx]);
			
			tree.delete_node(childNode);
		}
	}
}

/*
* jqGrid에 데이터를 바인딩 한다.
*/
function bindGrid(gridId, rowData, setSelectionIdx) {

    var dataSource = { rows : rowData };
    
    gridId = gridId.indexOf("#") != -1 ? gridId : "#" + gridId;
    
    $(gridId).jqGrid('setGridParam', {
        datastr    : JSON.stringify(dataSource),
        datatype   : "jsonstring",
        jsonReader : { repeatitems: false },
		height     : 'auto',
        gridview   : true,
	    loadComplete : function() { 
	
			$(gridId).jqGrid('setSelection', setSelectionIdx, false);
	     }
    }).trigger("reloadGrid");
}

/**
 * 로그인 사용자 ID를 가져온다.
 * 
 * @returns
 */
function getUserId() {

	return getCookie("codi.id");
	
}

/**
 * 쿠키에서 지정 키의 값을 가져온다.
 * 
 * @param key
 * @returns
 */
function getCookie(key) {

	var cookie      = document.cookie;
	var splitCookie = cookie.split("; ");

	for (var i = 0; i < splitCookie.length; i++) {
		
		var keyValues = splitCookie[i].split("=");

		if (keyValues[0] == key) {
			return unescape(keyValues[1]);
		}
	}
	
	return "";
}

/**
 * SKA.SKA_CODE_DEF 에 등록된 코드를 조회한다.
 * @param tree
 * @param parentNode
 */
function getCode(codeType) {

	var userNodes = fncCallAjax("ska/CommonController/getCode", { CODE_TYPE : codeType });
	
	return userNodes.result;
}

/**
 * Mail ID 를 받아 프로퍼티에 정의된 prefix + @ 앞부분 아이디를 합쳐 반환.
 * 
 * @param mailId
 */
function getUserIdAddPrefix(mailId) {
	
	if (mailId == null || typeof mailId == "undefined") mailId = getUserId();
	
	var results = fncCallAjax("ska/CommonController/getUserIdAddPrefix", { USERID : mailId } );
	
	return results.result;
}

/**
 * 스키마별 Prefix 를 비교하여 테이블명을 반환한다.
 * 
 * @param schema
 * @param tableName
 * @returns
 */
function makeTableName(schema, tableName) {
	
	var results = fncCallAjax("ska/CommonController/getTableNameBySchema", { SCHEMA : schema, TABLENAME : tableName } );
	
	return results.result;
}


/**
 * selectBox에 값을 Binding 한다.
 * 
 * @param selectId
 * @param data
 * @param codeColumn - 없을 경우 0번째 값을 넣음
 * @param displayColumn - Code컬럼이 없으면 0번째 값을 넣음. 
 */
function bindSelectBox(selectId, data, addBlank, codeColumn, displayColumn, selectValue) {

    $(selectId + " option").remove();
    
    if (addBlank) $(selectId).append('<option value=""></option>');

    $.each(data, function(index, item) {
		
    	// Code Column이 없을경우에 첫번째 값을 세팅
    	if (codeColumn == null || typeof codeColumn == "undefined") {
    		
    		var idx = 0;
    		for (var key in item) {
    			
    			$(selectId).append('<option value="'+ item[key] +'">' + item[key] + '</option>');
    			
    			if(idx++ != 0) continue;
    		}
    	}
    	else
    		$(selectId).append('<option value="'+ eval('item.' + codeColumn) +'">' + eval('item.' + displayColumn) + '</option>');
		
	});
    
    if (selectValue != null && typeof selectValue != "undefined") {
    
		var opt = document.getElementById(selectId.replace("#", ""));
	
		for (var idx = 0; idx < opt.length; idx++) {
			
			if (opt.options[idx].value == selectValue) {
				
				opt.options[idx].selected = true;
				break;
			}
		}
    }
}

/**
 * 등록된 문자열의 바인드 변수가 있는지 확인 한다. 
 * 
 * @param string
 * @returns {Boolean}
 */
function checkBindVariables(string) {
	
    var regex        = /({=[a-zA-Z0-9\s]+})/gi;
    var regexReplace = /[{=}]/gi;
    
    var matchArray;
    
    var bindVariableList = new Object();
    var jsonString = "";
    
    while ((matchArray = regex.exec(string)) != null) {
    	
    	var bindKey = matchArray[0];
    	var key     = bindKey.replace(regexReplace, "");
    	
    	if (jsonString.length == 0)
    	    jsonString += key + ": '" + key + "'";
    	else
    	    jsonString += "," + key + ": '" + key + "'";
    }
    
    eval("bindVariableList = { " + jsonString + " }");
    
    var processVariables = mw3.getAutowiredObject("org.uengine.kernel.bpmn.face.ProcessVariablePanel").processVariableList.face.elements;
    
    if (processVariables == null || typeof processVariables == "undefined") return false;

    // 전역으로 등록된 ProcessVariable 목록에 등록된 목록이 있는지 확인 한다.
    for (var key in bindVariableList) {
    	
		var filterVar = bindVariableList.filter(function(item) { return item.value.name === key; });

		if (filterVar == null || filterVar == "") return false;
    }
    	
    return true;
	
}

/**
 * 사용자 input 값중 숫자만 허용 하는 함수
 *
 * ex) onkeydown="onlyNumberKeyPress(event);"  style="ime-mode:disabled;" 
 * 
 */
function onlyNumberKeyPress(e)
{   
	var key;
	
	if (window.event)
		key = window.event.keyCode; // IE 
	else
		key = e.which // firefox
	var event;
	  
	if( (key == 0 || key == 8)|| key == 46 || key == 9){
		event = e || window.event;
		
		if ( typeof event.stopPropagation !="undefined"){
			event.stopPropagation();
		}else {
			event.cancelBubble = true; 
		}
		return;
	}
	
	if(key < 48 || (key > 57 && key < 96) || key > 105 || e.shiftKey){
		e.preventDefault ? e.preventDefault() : e.returnValue = false;
	}
} 



