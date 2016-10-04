var com_abc_modeler_SubjectExplorer = function(objectId, className){

    this.objectId = objectId;
    this.className = className;
    console.log(this);
    //draw tree

    var object = mw3.objects[objectId];


    $('#objDiv_'+this.objectId).bind('click', function(){
        var resource = new MetaworksObject({
            __className : "org.uengine.modeling.resource.resources.ProcessResource",
            path: "codi/MainProcess.process"
        }, 'body');


        resource.open();
    });
   /*
    var selectedResource = {
        __className: 'org.uengine.modeling.DefaultResource',
        path: '/codi/....'
    }
*/



};