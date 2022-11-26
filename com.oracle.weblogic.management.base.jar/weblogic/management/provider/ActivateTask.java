package weblogic.management.provider;

import java.util.Iterator;
import java.util.Map;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;

public interface ActivateTask {
   int STATE_NEW = 0;
   int STATE_DISTRIBUTING = 1;
   int STATE_DISTRIBUTED = 2;
   int STATE_PENDING = 3;
   int STATE_COMMITTED = 4;
   int STATE_FAILED = 5;
   int STATE_CANCELING = 6;
   int STATE_COMMIT_FAILING = 7;
   int STATE_DEFERRED = 8;

   long getTaskId();

   int getState();

   Map getStateOnServers();

   boolean updateServerState(String var1, int var2);

   Iterator getChanges();

   String getUser();

   Map getFailedServers();

   void addFailedServer(String var1, Exception var2);

   void waitForTaskCompletion();

   void waitForTaskCompletion(long var1);

   DeploymentRequestTaskRuntimeMBean getDeploymentRequestTaskRuntimeMBean();

   long getBeginTime();

   long getEndTime();

   Exception getError();

   void addDeferredServer(String var1, Exception var2);

   String getPartitionName();

   String getEditSessionName();

   Map getSystemComponentRestartRequired();
}
