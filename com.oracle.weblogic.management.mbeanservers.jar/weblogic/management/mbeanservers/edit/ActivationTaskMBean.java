package weblogic.management.mbeanservers.edit;

import weblogic.management.mbeanservers.Service;

public interface ActivationTaskMBean extends Service {
   int STATE_NEW = 0;
   int STATE_DISTRIBUTING = 1;
   int STATE_DISTRIBUTED = 2;
   int STATE_PENDING = 3;
   int STATE_COMMITTED = 4;
   int STATE_FAILED = 5;
   int STATE_CANCELING = 6;
   int STATE_COMMIT_FAILING = 7;
   int STATE_DEFERRED = 8;

   int getState();

   ServerStatus[] getStatusByServer();

   String[] getSystemComponentsToRestart();

   Exception getError();

   long getStartTime();

   long getCompletionTime();

   String getUser();

   Change[] getChanges();

   String getDetails();

   String getEditSessionName();

   String getPartitionName();

   void waitForTaskCompletion();

   void waitForTaskCompletion(long var1);
}
