var com_abc_modeler_SubjectExplorer = function(objectId, className){

    this.objectId = objectId;
    this.className = className;
    console.log(this);
    //draw tree
    $('#objDiv_'+this.objectId).bind('click', function(e){

        console.log(e);
    });
   /*
    var selectedResource = {
        __className: 'org.uengine.modeling.DefaultResource',
        path: '/codi/....'
    }
*/



};