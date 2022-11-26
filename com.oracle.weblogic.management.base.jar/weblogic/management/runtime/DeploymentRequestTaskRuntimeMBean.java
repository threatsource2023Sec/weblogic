package weblogic.management.runtime;

import java.util.Map;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentRequestSubTask;
import weblogic.management.ManagementException;

public interface DeploymentRequestTaskRuntimeMBean extends TaskRuntimeMBean {
   int STATE_INITIALIZING = 0;
   int STATE_INPROGRESS = 1;
   int STATE_SUCCESS = 2;
   int STATE_FAILED = 3;
   int STATE_CANCEL_SCHEDULED = 4;
   int STATE_CANCEL_INPROGRESS = 5;
   int STATE_CANCEL_COMPLETED = 6;
   int STATE_CANCEL_FAILED = 7;
   int STATE_COMMIT_FAILED = 8;
   int STATE_COMMIT_SUCCEEDED = 8;

   long getTaskId();

   int getState();

   Map getFailedTargets();

   DeploymentRequest getDeploymentRequest();

   void addDeploymentRequestSubTask(DeploymentRequestSubTask var1, String var2);

   void start() throws ManagementException;

   boolean isComplete();

   String[] getServersToBeRestarted();
}
