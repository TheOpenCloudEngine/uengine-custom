var com_abc_activitytype_DataInputActivity = function(objectId, className){

    this.object = mw3.objects[objectId];        //when the value comes from server.

}


com_abc_activitytype_DataInputActivity.prototype.getValue = function(){  //when the value goes to server.


    this.object.inputTable = $("#InputTable").val();

    return this.object;



}