package weblogic.deploy.api.model.sca;

import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.ModuleType;

public interface ScaApplicationObject extends DeployableObject {
   DeployableObject getDeployableObject(String var1);

   DeployableObject[] getDeployableObjects(ModuleType var1);

   DeployableObject[] getDeployableObjects();

   String[] getModuleUris(ModuleType var1);

   String[] getModuleUris();
}
