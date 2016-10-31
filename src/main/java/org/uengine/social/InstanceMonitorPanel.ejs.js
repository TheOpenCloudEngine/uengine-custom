var org_uengine_social_InstanceMonitorPanel_timer = null;

var org_uengine_social_InstanceMonitorPanel = function(objectId, className){

    this.destroy();

    //org_uengine_social_InstanceMonitorPanel_timer =
    //
    //    setTimeout(function(){
    //            var object = mw3.objects[objectId];
    //            object.load();
    //    }, 5000);

};

org_uengine_social_InstanceMonitorPanel.prototype = {

    startLoading : function(){
            //don't show loading effect.
    },
    endLoading: function(){
            //do nothing about loading effect.
    }

    ,
    destroy: function(){
        if(org_uengine_social_InstanceMonitorPanel_timer) {
            clearTimeout(org_uengine_social_InstanceMonitorPanel_timer);
            org_uengine_social_InstanceMonitorPanel_timer = null;
        }
    }



};

