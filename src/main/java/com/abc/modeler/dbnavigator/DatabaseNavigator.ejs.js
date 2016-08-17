var com_abc_modeler_dbnavigator_DatabaseNavigator = function (objectId, className) {
    this.objectId = objectId;
    this.className = className;
    this.object = mw3.objects[this.objectId];
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv = $('#' + this.objectDivId);

    this.objectDiv.css({'height': '100%'});
    var child = this.objectDiv.parent().parent().children();
    var interval = setInterval(function(){
        child.css({'height': '45%'});
    }, 1000);
    this.objectDiv.on("remove", function () {
        clearInterval(interval);
    });
}