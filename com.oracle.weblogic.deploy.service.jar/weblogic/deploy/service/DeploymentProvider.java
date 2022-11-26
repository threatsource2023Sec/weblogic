package weblogic.deploy.service;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeploymentProvider {
   String getIdentity();

   /** @deprecated */
   @Deprecated
   String getPartitionName();

   String getEditSessionName();

   void addDeploymentsTo(DeploymentRequest var1, ConfigurationContext var2);
}
