package weblogic.nodemanager.rest.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.rest.utils.CommonUtils;

public class AsyncJob {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   public static final String MANDATORY_ARGUMENTS_NULL;
   private final String id;
   private final String description;
   private final String domainName;
   private final CommonUtils.ComponentType componentType;
   private final String componentName;
   private final CommonUtils.OperationType operationType;
   private long intervalToPoll = 1000L;
   private final TaskWrapper taskExe;
   private final Future future;

   protected AsyncJob(String id, Future fTask, TaskWrapper taskWrapper) {
      if (id != null && fTask != null && taskWrapper != null) {
         this.id = id;
         this.description = taskWrapper.getDescription();
         this.domainName = taskWrapper.getDomainName();
         this.componentType = taskWrapper.getComponentType();
         this.componentName = taskWrapper.getComponentName();
         this.operationType = taskWrapper.getOperationType();
         this.future = fTask;
         this.taskExe = taskWrapper;
      } else {
         throw new IllegalArgumentException(MANDATORY_ARGUMENTS_NULL);
      }
   }

   public String getId() {
      return this.id;
   }

   public String getDescription() {
      return this.description;
   }

   public CommonUtils.ComponentType getComponentType() {
      return this.componentType;
   }

   public CommonUtils.OperationType getOperationType() {
      return this.operationType;
   }

   public long getIntervalToPoll() {
      return this.intervalToPoll;
   }

   public void setIntervalToPoll(long intervalToPoll) {
      this.intervalToPoll = intervalToPoll;
   }

   public boolean isComplete() {
      return this.future.isDone();
   }

   public Object getOutput() throws TaskNotCompleteException, TaskExecutionException {
      try {
         if (this.isComplete()) {
            return this.future.get();
         } else {
            throw new TaskNotCompleteException("use isComplete() to make task completed before invoking getOutput()");
         }
      } catch (ExecutionException var2) {
         throw new TaskExecutionException(var2.getCause());
      } catch (InterruptedException var3) {
         throw new TaskNotCompleteException("use isComplete() to make task completed before invoking getOutput()");
      }
   }

   public String getComponentName() {
      return this.componentName;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public long getStartTime() {
      return this.taskExe.getStartTime();
   }

   public long getEndTime() {
      return this.taskExe.getEndTime();
   }

   static {
      MANDATORY_ARGUMENTS_NULL = nmRestText.msgMandatoryArgumentsNull();
   }
}
