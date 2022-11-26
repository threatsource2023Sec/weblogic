package weblogic.deploy.service;

import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeploymentProviderManager {
   void registerDeploymentProvider(DeploymentProvider var1);

   void unregisterDeploymentProvider(DeploymentProvider var1);

   Set getRegisteredDeploymentProviders();
}
