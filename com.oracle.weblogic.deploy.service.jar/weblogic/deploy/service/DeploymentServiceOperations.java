package weblogic.deploy.service;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;

@Contract
public interface DeploymentServiceOperations {
   void register(Version var1, DeploymentServiceCallbackHandler var2) throws RegistrationExistsException;

   DeploymentRequestTaskRuntimeMBean deploy(DeploymentRequest var1) throws RequiresTaskMediatedStartException;

   DeploymentRequestTaskRuntimeMBean startDeploy(DeploymentRequest var1);

   void cancel(DeploymentRequest var1);

   void unregister(String var1);
}
