package weblogic.application.ondemand;

import weblogic.management.DeploymentException;

public interface Deployer {
   void deploy(String var1, boolean var2) throws DeploymentException;
}
