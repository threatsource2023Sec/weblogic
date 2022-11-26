package weblogic.management.runtime;

import java.util.List;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.LibraryMBean;

public interface DeploymentProgressObjectMBean extends RuntimeMBean {
   int OPERATION_UNIMPLEMENTED = 0;
   int OPERATION_START = 1;
   int OPERATION_STOP = 2;
   int OPERATION_DEPLOY = 3;
   int OPERATION_REDEPLOY = 4;
   int OPERATION_UNDEPLOY = 5;
   String STATE_INITIALIZED = "STATE_INITIALIZED";
   String STATE_RUNNING = "STATE_RUNNING";
   String STATE_COMPLETED = "STATE_COMPLETED";
   String STATE_FAILED = "STATE_FAILED";
   String STATE_DEFERRED = "STATE_DEFERRED";

   String getId();

   int getOperationType();

   String getApplicationName();

   AppDeploymentMBean getAppDeploymentMBean();

   LibraryMBean getLibraryMBean();

   String getState();

   String[] getTargets();

   String[] getFailedTargets();

   void addMessages(List var1);

   String[] getMessages();

   RuntimeException[] getExceptions(String var1);

   RuntimeException[] getRootExceptions();

   void cancel() throws RuntimeException;

   long getBeginTime();

   long getEndTime();

   DeploymentTaskRuntimeMBean getDeploymentTaskRuntime();
}
