var com_abc_modeler_AnalysisEditorPanel = function(objectId, className){
    org_uengine_essencia_portal_EssenciaEditorPanel.apply(this, new Array(objectId, className)); //equals to super(objectId, className) in java

    var object = mw3.objects[objectId];

    if(object.resourcePath){
//        document.title = "[" + object.resourcePath + "]";
        document.title = "[" + object.resourceName + "] - Codi";
    }

}
extend(com_abc_modeler_AnalysisEditorPanel, "org_uengine_essencia_portal_EssenciaEditorPanel");  //equals to extends in java
