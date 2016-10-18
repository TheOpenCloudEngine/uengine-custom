var treeIdTmp       = "";
var inputId      = "";
var selectedNode = null;
var prefixUserId = "";
var thisObjectId = null;
//var originObjectId = null;

var com_abc_modeler_TemplateExplorer = function(objectId, className) {
	treeIdTmp  			= '#jstreeTmp_' + objectId;
	inputId 			= '#searchIptSub_' + objectId;	
	prefixUserId 		= getUserIdAddPrefix(getUserId());		// prefix + userid 를 만든다. 	
//	console.log(">>>>> [ID Prefix] %s\n", prefixUserId);
	
	this.objectId    	= objectId;
	this.className   	= className;
	this.object      	= mw3.objects[this.objectId];
	this.objectDivId 	= mw3._getObjectDivId(this.objectId);
	this.objectDiv   	= $('#' + this.objectDivId);	
	this.objectDiv.css({'height': '100%'});
	
	
}