package weblogic.application.compiler.deploymentview;

import javax.enterprise.deploy.model.J2eeApplicationObject;

public interface EditableJ2eeApplicationObject extends J2eeApplicationObject, EditableDeployableObject {
   void addDeployableObject(EditableDeployableObject var1);
}
