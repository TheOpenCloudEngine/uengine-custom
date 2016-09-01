var com_abc_widget_JsTree = function (objectId, className) {

    var object    = mw3.objects[objectId];
    var dataList  = object.listGrid;
    var columns   = object.columnNames;
    var fieldName = eval(object.fieldName);
    var title     = object.title;

    $('#jstree_' + objectId).jstree();


}