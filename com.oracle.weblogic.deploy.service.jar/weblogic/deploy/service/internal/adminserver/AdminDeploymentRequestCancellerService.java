package weblogic.deploy.service.internal.adminserver;

import org.jvnet.hk2.annotations.Contract;
import weblogic.deploy.service.DeploymentRequest;

@Contract
public interface AdminDeploymentRequestCancellerService {
   void deploymentRequestCancelledBeforeStart(DeploymentRequest var1);
}
