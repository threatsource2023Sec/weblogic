package weblogic.application;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeploymentFinder {
   WorkDeployment findDeployment(String var1);
}
