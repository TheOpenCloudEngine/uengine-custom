

var com_abc_widget_TreeNode = function(objectId, className){

    this.objectId = objectId;
    this.className = className;
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv = $('#' + this.objectDivId);
    this.object = mw3.objects[objectId];

    this.objectDiv.draggable({
        appendTo: "body",
        helper: function( event ) {
            return $(this).clone();
        },
        zIndex: 100,
        start: function(event, ui) {
            isDroppable = true;

            var object = mw3.objects[this.id.split("_")[1]];

            if(object.object){
                var clipboard = mw3.getAutowiredObject("org.metaworks.widget.Clipboard");

                clipboard.content = this.object.object;

            }
        },
        drag: function() {
        },
        stop: function() {
        }
    });

    this.startLoading = function(){

    };

    this.endLoading = function(){

    };


}



