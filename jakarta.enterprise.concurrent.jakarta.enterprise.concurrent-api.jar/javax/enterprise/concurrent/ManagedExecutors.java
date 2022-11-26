package javax.enterprise.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ManagedExecutors {
   static final String NULL_TASK_ERROR_MSG = "Task cannot be null";

   private ManagedExecutors() {
   }

   public static boolean isCurrentThreadShutdown() {
      Thread currThread = Thread.currentThread();
      return currThread instanceof ManageableThread ? ((ManageableThread)currThread).isShutdown() : false;
   }

   public static Runnable managedTask(Runnable task, ManagedTaskListener taskListener) throws IllegalArgumentException {
      return managedTask((Runnable)task, (Map)null, taskListener);
   }

   public static Runnable managedTask(Runnable task, Map executionProperties, ManagedTaskListener taskListener) throws IllegalArgumentException {
      if (task == null) {
         throw new IllegalArgumentException("Task cannot be null");
      } else {
         return new RunnableAdapter(task, executionProperties, taskListener);
      }
   }

   public static Callable managedTask(Callable task, ManagedTaskListener taskListener) throws IllegalArgumentException {
      return managedTask((Callable)task, (Map)null, taskListener);
   }

   public static Callable managedTask(Callable task, Map executionProperties, ManagedTaskListener taskListener) throws IllegalArgumentException {
      if (task == null) {
         throw new IllegalArgumentException("Task cannot be null");
      } else {
         return new CallableAdapter(task, executionProperties, taskListener);
      }
   }

   static class Adapter implements ManagedTask {
      protected final ManagedTaskListener taskListener;
      protected final Map executionProperties;
      protected final ManagedTask managedTask;

      public Adapter(ManagedTaskListener taskListener, Map executionProperties, ManagedTask managedTask) {
         this.taskListener = taskListener;
         this.managedTask = managedTask;
         this.executionProperties = this.initExecutionProperties(managedTask == null ? null : managedTask.getExecutionProperties(), executionProperties);
      }

      public ManagedTaskListener getManagedTaskListener() {
         if (this.taskListener != null) {
            return this.taskListener;
         } else {
            return this.managedTask != null ? this.managedTask.getManagedTaskListener() : null;
         }
      }

      public Map getExecutionProperties() {
         return this.executionProperties != null ? this.executionProperties : null;
      }

      private Map initExecutionProperties(Map base, Map override) {
         if (base == null && override == null) {
            return null;
         } else {
            Map props = new HashMap();
            if (base != null) {
               props.putAll(base);
            }

            if (override != null) {
               props.putAll(override);
            }

            return props;
         }
      }
   }

   static final class CallableAdapter extends Adapter implements Callable {
      final Callable task;

      public CallableAdapter(Callable task, Map executionProperties, ManagedTaskListener taskListener) {
         super(taskListener, executionProperties, task instanceof ManagedTask ? (ManagedTask)task : null);
         this.task = task;
      }

      public Object call() throws Exception {
         return this.task.call();
      }
   }

   static final class RunnableAdapter extends Adapter implements Runnable {
      final Runnable task;

      public RunnableAdapter(Runnable task, Map executionProperties, ManagedTaskListener taskListener) {
         super(taskListener, executionProperties, task instanceof ManagedTask ? (ManagedTask)task : null);
         this.task = task;
      }

      public void run() {
         this.task.run();
      }
   }
}
