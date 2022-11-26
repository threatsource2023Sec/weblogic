package javax.enterprise.deploy.model;

import javax.enterprise.deploy.shared.ModuleType;

public interface DDBeanRoot extends DDBean {
   ModuleType getType();

   DeployableObject getDeployableObject();

   /** @deprecated */
   String getModuleDTDVersion();

   String getDDBeanRootVersion();

   String getXpath();

   String getFilename();
}
