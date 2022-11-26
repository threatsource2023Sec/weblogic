package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.ResourceGroupMigrationTask;

public class ResourceGroupMigrationTaskMBeanImpl extends ServiceImpl implements ResourceGroupMigrationTaskMBean {
   private final ResourceGroupMigrationTask task;

   public ResourceGroupMigrationTaskMBeanImpl(ResourceGroupMigrationTask _task) {
      super(Long.toString((long)_task.hashCode()), ResourceGroupMigrationTaskMBean.class.getName(), (Service)null);
      this.task = _task;
   }

   public int getState() {
      return this.task == null ? -1 : this.task.getState();
   }

   public Exception getError() {
      return this.task.getError();
   }
}
