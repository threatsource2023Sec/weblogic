package weblogic.application.ondemand;

import weblogic.management.configuration.AppDeploymentMBean;

public interface DeploymentProvider {
   boolean claim(AppDeploymentMBean var1, Deployer var2);

   boolean unclaim(AppDeploymentMBean var1);
}
