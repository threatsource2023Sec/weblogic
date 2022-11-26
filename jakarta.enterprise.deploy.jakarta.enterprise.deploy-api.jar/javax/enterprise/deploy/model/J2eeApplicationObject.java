package javax.enterprise.deploy.model;

import javax.enterprise.deploy.shared.ModuleType;

public interface J2eeApplicationObject extends DeployableObject {
   DeployableObject getDeployableObject(String var1);

   DeployableObject[] getDeployableObjects(ModuleType var1);

   DeployableObject[] getDeployableObjects();

   String[] getModuleUris(ModuleType var1);

   String[] getModuleUris();

   DDBean[] getChildBean(ModuleType var1, String var2);

   String[] getText(ModuleType var1, String var2);

   void addXpathListener(ModuleType var1, String var2, XpathListener var3);

   void removeXpathListener(ModuleType var1, String var2, XpathListener var3);
}
