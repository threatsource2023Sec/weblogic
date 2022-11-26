package weblogic.deploy.service.internal.transport;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface MessageDispatcher {
   void dispatch(DeploymentServiceMessage var1);

   DeploymentServiceMessage blockingDispatch(DeploymentServiceMessage var1);
}
