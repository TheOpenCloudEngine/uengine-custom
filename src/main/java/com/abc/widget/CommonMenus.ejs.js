var com_abc_widget_CommonMenus = function(objectId, className) {        
    this.objectId = objectId;
    this.className = className;
    this.object = mw3.objects[this.objectId];
    this.objectDivId = mw3._getObjectDivId(this.objectId);
    this.objectDiv = $('#' + this.objectDivId);
            
    $("#login_user").text(mw3.getAutowiredObject("org.uengine.codi.mw3.admin.TopPanel").loginUser.name);
                
    $('#menuSave_' + objectId).click(function(e) {    	
    	//debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").save();
    		com_abc_modeler_SubjectExplorer_Refresh();
    	}    	
    });
    
    $('#menuSaveAs_' + objectId).click(function(e) {
    	//debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").saveAs();
    	}
    });
    
    $('#menuRename_' + objectId).click(function(e) {
    	//debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").rename();
    	}
    });
    
    $('#menuDelete_' + objectId).click(function(e) {    	
    	//debugger;
    	
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		var msg = mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").resourceName + ".process";	
        	msg += "를 삭제 하시겠습니까?";
        	
        	var ret = confirm(msg);
        	
        	if (ret) {
	    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").delete();
	    	}
    	}
    });
        
    $('#menuValidate_' + objectId).click(function(e) {
    	//debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").validate();
    	}
    });
    
    $('#menuRun_' + objectId).click(function(e) {
    	//debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").simulate();
    	}
    });
    
    $('#menuDebug_' + objectId).click(function(e) {
    	//debugger;
    });
    
    $('#menuStop_' + objectId).click(function(e) {
    	//debugger;
    });
    
    $('#menuPublish_' + objectId).click(function(e) {
    	//debugger;
    });
            
    $('#menuTemplate_' + objectId).click(function(e) {
    	//debugger;
    });
    
    $('#menuDownload_' + objectId).click(function(e) {
//    	debugger;
    	if (mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel") != null) {
    		mw3.getAutowiredObject("org.uengine.essencia.portal.EssenciaEditorPanel").download();
    	}
    });
    
    $('#menuLogout_' + objectId).click(function(e) {    
//    	debugger;
    	mw3.getAutowiredObject("org.uengine.codi.mw3.admin.TopPanel").loginUser.logout();
//    	mw3.getAutowiredObject("org.uengine.codi.mw3.admin.TopPanel").loginUser.detail();
    	thisObjectId = null;    	
    });
}