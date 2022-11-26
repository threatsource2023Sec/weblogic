package weblogic.deploy.service;

import java.util.Iterator;
import java.util.Set;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface DeploymentRequest {
   long MINIMUM_TIMEOUT_INTERVAL = 120000L;

   long getId();

   void addDeployment(Deployment var1);

   Iterator getDeployments();

   Iterator getDeployments(String var1);

   DeploymentRequestTaskRuntimeMBean getTaskRuntime();

   boolean isConfigurationProviderCalledLast();

   void setCallConfigurationProviderLast();

   boolean isStartControlEnabled();

   void setStartControl(boolean var1);

   boolean isControlRequest();

   void setControlRequest(boolean var1);

   void setInitiator(AuthenticatedSubject var1);

   AuthenticatedSubject getInitiator();

   void registerFailureListener(DeploymentFailureHandler var1);

   Set getRegisteredFailureListeners();

   long getTimeoutInterval();

   void setTimeoutInterval(long var1);

   boolean isSinglePartitionDeploymentRequest();

   boolean concurrentAppPrepareEnabled();

   void setConfigCommitCalled();

   boolean getConfigCommitCalled();

   void enqueued();

   boolean isEnqueued();

   void deploymentFailedInConfigLayer();
}
