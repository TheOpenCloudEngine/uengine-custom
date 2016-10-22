var com_abc_modeler_SubjectExplorer = function(objectId, className){

    this.objectId = objectId;
    this.className = className;
    console.log(this);
    //draw tree
    $('#subject').bind('click', {objectId: this.objectId}, function(e){

        mw3.objects[objectId].__objectId = null;
        mw3.objects[objectId].refresh();

        console.log(e);
    });
   /*
    var selectedResource = {
        __className: 'org.uengine.modeling.DefaultResource',
        path: '/codi/....'
    }
*/



};