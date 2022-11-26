package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.mbeanservers.Service;

public interface ImportExportPartitionTaskMBean extends Service {
   int STATE_NOT_STARTED = -1;
   int STATE_STARTED = 1;
   int STATE_FINISHED = 2;
   int STATE_FAILED = 3;

   int getState();

   Exception getError();
}
