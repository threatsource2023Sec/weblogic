package weblogic.application.compiler.deploymentview;

import weblogic.deploy.api.model.sca.ScaApplicationObject;

public interface EditableScaApplicationObject extends ScaApplicationObject, EditableDeployableObject {
   void addDeployableObject(EditableDeployableObject var1);
}
