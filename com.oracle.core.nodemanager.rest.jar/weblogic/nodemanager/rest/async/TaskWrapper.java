package weblogic.nodemanager.rest.async;

import java.util.concurrent.Callable;
import weblogic.nodemanager.rest.utils.CommonUtils;

public class TaskWrapper implements Callable {
   private long startTime;
   private long endTime;
   Task task;

   TaskWrapper(Task task) {
      this.task = task;
   }

   public Object call() throws Exception {
      this.preTaskExecution();

      Object var2;
      try {
         Object taskResult = this.task.execute();
         var2 = taskResult;
      } finally {
         this.postTaskExecution();
      }

      return var2;
   }

   protected void preTaskExecution() {
      this.startTime = System.currentTimeMillis();
   }

   protected void postTaskExecution() {
      this.endTime = System.currentTimeMillis();
   }

   public long getStartTime() {
      return this.startTime;
   }

   public long getEndTime() {
      return this.endTime;
   }

   public String getDescription() {
      return this.task.getDescription();
   }

   public String getDomainName() {
      return this.task.getDomainName();
   }

   public CommonUtils.ComponentType getComponentType() {
      return this.task.getComponentType();
   }

   public String getComponentName() {
      return this.task.getComponentName();
   }

   public CommonUtils.OperationType getOperationType() {
      return this.task.getOperationType();
   }
}
