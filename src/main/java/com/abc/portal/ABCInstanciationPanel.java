package com.abc.portal;

import org.metaworks.MetaworksContext;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.model.RoleMappingPanel;
import org.uengine.essencia.enactment.EssenceProcessDefinition;
import org.uengine.essencia.model.Practice;
import org.uengine.essencia.modeling.modeler.MethodComposer;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessVariable;
import org.uengine.modeling.resource.VersionManager;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.social.ElementViewActionDelegateForInstanceMonitoring;
import org.uengine.uml.model.Attribute;
import org.uengine.uml.model.ClassDefinition;
import org.uengine.uml.model.ObjectInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 6. 13..
 */

@Component
@Scope("prototype")
public class ABCInstanciationPanel extends RoleMappingPanel{

    String practiceName;

        public String getPracticeName() {
            return practiceName;
        }

        public void setPracticeName(String practiceName) {
            this.practiceName = practiceName;
        }

    String practiceDescription;
        public String getPracticeDescription() {
            return practiceDescription;
        }

        public void setPracticeDescription(String practiceDescription) {
            this.practiceDescription = practiceDescription;
        }

    String practiceBriefDescription;

        public String getPracticeBriefDescription() {
            return practiceBriefDescription;
        }

        public void setPracticeBriefDescription(String practiceBriefDescription) {
            this.practiceBriefDescription = practiceBriefDescription;
        }


    ObjectInstance variableSettings;
        public ObjectInstance getVariableSettings() {
            return variableSettings;
        }

        public void setVariableSettings(ObjectInstance variableSettings) {
            this.variableSettings = variableSettings;
        }



    @Override
    public void load(String defId_) throws Exception {
        super.load(defId_);

        VersionManager versionManager = MetaworksRemoteService.getComponent(VersionManager.class);
        versionManager.setAppName("codi");

        defId_ = versionManager.getProductionResourcePath(defId_);

        ProcessDefinition processDefinition = (ProcessDefinition) processManager.getProcessDefinition(defId_);

        setPracticeName(processDefinition.getName());
        setPracticeBriefDescription(processDefinition.getShortDescription());


        ////// variable mapping table

        List<Attribute> fieldDescriptors = new ArrayList<Attribute>();

        setVariableSettings(new ObjectInstance());
        for(ProcessVariable processVariable : processDefinition.getProcessVariables()){

            Serializable value = processVariable.createNewValue();
            getVariableSettings().setBeanProperty(processVariable.getName(), value);

            Attribute attribute = new Attribute();
            attribute.setName(processVariable.getName());
            attribute.setClassName(value != null ? value.getClass().getName() :  Object.class.getName());
//
//            if(attribute.getClassName()==null){
//                attribute.setClassName(Object.class.getName());  //must be not null
//            }

            fieldDescriptors.add(attribute);
        }

        getVariableSettings().setClassDefinition(new ClassDefinition());

        Attribute[] dummy = new Attribute[fieldDescriptors.size()];
        fieldDescriptors.toArray(dummy);
        getVariableSettings().getClassDefinition().setFieldDescriptors(dummy);
        getVariableSettings().getClassDefinition().setName("Process Variables");

    }


    @Override //TODO: name must be changed to configure, and the classname must be changed to InstanciationPanel.
    public void putRoleMappings(ProcessManagerRemote processManager, String instId) throws Exception {
        super.putRoleMappings(processManager, instId);

        for(String variableName :  getVariableSettings().getValueMap().keySet()){
            processManager.setProcessVariable(instId, "", variableName, (Serializable) getVariableSettings().getValueMap().get(variableName));
        }

    }
}
