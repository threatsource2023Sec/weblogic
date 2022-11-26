package weblogic.deploy.api.model.sca;

import weblogic.application.compiler.deploymentview.EditableDeployableObject;

public interface EditableScaApplicationObject extends ScaApplicationObject, EditableDeployableObject {
   void addDeployableObject(EditableDeployableObject var1);
}
