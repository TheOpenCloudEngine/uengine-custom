document.onmouseup = com_abc_modeler_AnalysisProcessAdminWorkbench_doMouseUp;

var com_abc_modeler_AnalysisProcessAdminWorkbench = function(objectId, className) {
	this.objectId = objectId;
    this.className = className;
    this.object = mw3.objects[this.objectId];
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv = $('#' + this.objectDivId);

    //var layoutOption = {west__size: '125', north__size: '40', togglerLength_open: 0, spacing_open: 0, spacing_closed: 0};
    var layoutOption = {west__size: '250', north__size: '67', togglerLength_open: 0, spacing_open: 0, spacing_closed: 0};

    this.objectDiv.addClass('mw3_layout');
    this.layout = this.objectDiv.layout(layoutOption);

    this.destroy = function() {
        this.layout.destroy();
    }
    
    window.setTimeout(function() {    	
    	com_abc_modeler_AnalysisProcessAdminWorkbench_setResizerStyle();
    }, 500);    
}

function com_abc_modeler_AnalysisProcessAdminWorkbench_doMouseUp() {	
	com_abc_modeler_AnalysisProcessAdminWorkbench_setResizerStyle();
}

function com_abc_modeler_AnalysisProcessAdminWorkbench_setResizerStyle() {
	$(".ui-layout-resizer").show();
	$(".ui-layout-resizer").css('width', '5px');
	$(".ui-layout-resizer").css('margin-top', '67px');
	$(".ui-layout-resizer").css('height', '100%');
	$(".ui-layout-resizer").css('background', '#f3f3f3');
	$(".ui-layout-resizer").css('border-width', '0 0px');
	$(".ui-layout-resizer").css('z-index', '0');
}