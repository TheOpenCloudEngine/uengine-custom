var com_abc_activitytype_view_DataInputActivityView = function(objectId, className){

    org_uengine_kernel_view_ActivityView.apply(this, new Array(objectId, className));

    $(this.element).mouseup({objectId: this.objectId}, function (event, ui) {
        var view = mw3.objects[event.data.objectId];
        var activity = view.element;

        console.log(activity.name);
        console.log(activity.inputValue);
        console.log(activity.outValue);
    });

}
extend(com_abc_activitytype_view_DataInputActivityView, "org_uengine_kernel_view_ActivityView");

