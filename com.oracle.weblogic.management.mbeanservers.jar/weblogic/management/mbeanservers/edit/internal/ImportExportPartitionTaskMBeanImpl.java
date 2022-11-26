package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.internal.ServiceImpl;

public class ImportExportPartitionTaskMBeanImpl extends ServiceImpl implements ImportExportPartitionTaskMBean {
   private final ImportExportPartitionTaskImpl task;

   public ImportExportPartitionTaskMBeanImpl(ImportExportPartitionTaskImpl _task) {
      super(Long.toString((long)_task.hashCode()), ImportExportPartitionTaskMBean.class.getName(), (Service)null);
      this.task = _task;
   }

   public int getState() {
      return this.task == null ? -1 : this.task.getState();
   }

   public Exception getError() {
      return this.task.getError();
   }
}
