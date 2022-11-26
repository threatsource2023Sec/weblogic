package weblogic.deploy.service;

import java.io.Serializable;

public interface DeploymentServiceCallbackHandler extends CallbackHandler, DeploymentFailureHandler {
   Deployment[] getDeployments(Version var1, Version var2, String var3, String var4);

   void deploySucceeded(long var1, FailureDescription[] var3);

   void commitFailed(long var1, FailureDescription[] var3);

   void commitSucceeded(long var1);

   void receivedStatusFrom(long var1, Serializable var3, String var4);
}
