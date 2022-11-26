package weblogic.management.runtime;

import java.util.List;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.TargetStatus;

/** @deprecated */
@Deprecated
public interface DeploymentTaskRuntimeMBean extends TaskRuntimeMBean {
   String LIFECYCLE_TASKID = "__Lifecycle_taskid__";
   int DEPLOY_TASK_ACTIVATE = 1;
   int DEPLOY_TASK_PREPARE = 2;
   int DEPLOY_TASK_DEACTIVATE = 3;
   int DEPLOY_TASK_REMOVE = 4;
   int DEPLOY_TASK_UNPREPARE = 5;
   int DEPLOY_TASK_DISTRIBUTE = 6;
   int DEPLOY_TASK_START = 7;
   int DEPLOY_TASK_STOP = 8;
   int DEPLOY_TASK_REDEPLOY = 9;
   int DEPLOY_TASK_UPDATE = 10;
   int DEPLOY_TASK_DEPLOY = 11;
   int DEPLOY_TASK_UNDEPLOY = 12;
   int DEPLOY_TASK_RETIRE = 13;
   int DEPLOY_TASK_EXTEND_LOADER = 14;
   int NONE_NOTIFICATION_LEVEL = 0;
   int APP_NOTIFICATION_LEVEL = 1;
   int MODULE_NOTIFICATION_LEVEL = 2;
   int DEFAULT_NOTIFICATION_LEVEL = 1;
   int MIN_NOTIFICATION_LEVEL = 0;
   int MAX_NOTIFICATION_LEVEL = 2;
   int STATE_INITIALIZED = 0;
   int STATE_RUNNING = 1;
   int STATE_COMPLETED = 2;
   int STATE_FAILED = 3;
   /** @deprecated */
   @Deprecated
   int STATE_DEFERRED = 4;
   int CANCEL_STATE_NONE = 0;
   int CANCEL_STATE_STARTED = 2;
   int CANCEL_STATE_FAILED = 4;
   int CANCEL_STATE_COMPLETED = 8;

   int getTask();

   TargetStatus[] getTargets();

   TargetStatus findTarget(String var1);

   void updateTargetStatus(String var1, int var2, Exception var3);

   ApplicationMBean getDeploymentObject();

   DeploymentData getDeploymentData();

   String getId();

   void cancel() throws Exception;

   void start() throws ManagementException;

   boolean isNewSource();

   Map getVersionTargetStatusMap();

   boolean isInUse();

   String getSource();

   String getApplicationName();

   String getApplicationId();

   int getState();

   void setState(int var1);

   int getNotificationLevel();

   void setNotificationLevel(int var1);

   boolean isTaskFailedAsTargetNotUp();

   void setTaskFailedAsTargetNotUp(boolean var1);

   int getCancelState();

   void setCancelState(int var1);

   List getTaskMessages();

   void addUnreachableTarget(String var1);

   void waitForTaskCompletion(long var1);

   BasicDeploymentMBean getDeploymentMBean();

   String getApplicationVersionIdentifier();

   boolean isRetired();

   void setRetired();

   boolean isPendingActivation();
}
