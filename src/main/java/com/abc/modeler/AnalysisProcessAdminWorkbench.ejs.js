var com_abc_modeler_AnalysisProcessAdminWorkbench = function(objectId, className){
    this.objectId = objectId;
    this.className = className;
    this.object = mw3.objects[this.objectId];
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv = $('#' + this.objectDivId);

    if(this.object.defaultLoadingResourcePath){
        window.title = this.object.defaultLoadingResourcePath;
    }

    //var layoutOption = {west__size: '125', north__size: '40', togglerLength_open: 0, spacing_open: 0, spacing_closed: 0};
    var layoutOption = {north__size: '40', togglerLength_open: 0, spacing_open: 0, spacing_closed: 0};

    this.objectDiv.addClass('mw3_layout');
    this.layout = this.objectDiv.layout(layoutOption);

    this.destroy = function(){
        this.layout.destroy();
    }
}

