package weblogic.management.utils.jmsdlb;

import java.util.Set;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.utils.GenericManagedDeployment;

public interface DLClusterRegistration {
   DLMigratableGroupConfigImpl getGroupConfigMBean(String var1, ServerMBean var2, String var3);

   Set registerDeploymentBeans(DynamicDeploymentMBean... var1);

   void registerGMD(String var1, GenericManagedDeployment var2);

   GenericManagedDeployment getGMD(String var1);

   boolean isMigratedLocally(String var1);
}
