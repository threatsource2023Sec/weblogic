package weblogic.work.concurrent;

import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;
import weblogic.work.concurrent.utils.LogUtils;

public class ListenerWithContext implements ManagedTaskListener {
   private final ManagedTaskListener listener;
   private final TaskWrapper wrapper;
   private volatile boolean submitDone = false;

   public ListenerWithContext(ManagedTaskListener listener, TaskWrapper wrapper) {
      this.listener = listener;
      this.wrapper = wrapper;
   }

   public void taskAborted(Future future, ManagedExecutorService service, Object task, Throwable ex) {
      this.taskSubmitted(future, service, task);
      ContextHandleWrapper context = this.wrapper.setupContext();

      try {
         LogUtils.logListenerInvocation(this.listener, future, service, task, ex, "taskAborted");
         this.listener.taskAborted(future, service, task, ex);
      } catch (Throwable var10) {
         ConcurrencyLogger.logTaskListenerFail(this.wrapper.getTaskName(), "taskAborted", var10);
      } finally {
         if (context != null) {
            context.restore();
         }

      }

   }

   public void taskDone(Future future, ManagedExecutorService service, Object task, Throwable ex) {
      this.taskSubmitted(future, service, task);
      ContextHandleWrapper context = this.wrapper.setupContext();

      try {
         LogUtils.logListenerInvocation(this.listener, future, service, task, ex, "taskDone");
         this.listener.taskDone(future, service, task, ex);
      } catch (Throwable var10) {
         ConcurrencyLogger.logTaskListenerFail(this.wrapper.getTaskName(), "taskDone", var10);
      } finally {
         if (context != null) {
            context.restore();
         }

      }

   }

   public void taskStarting(Future future, ManagedExecutorService service, Object task) {
      this.taskSubmitted(future, service, task);
      ContextHandleWrapper context = this.wrapper.setupContext();

      try {
         LogUtils.logListenerInvocation(this.listener, future, service, task, (Throwable)null, "taskStarting");
         this.listener.taskStarting(future, service, task);
      } catch (Throwable var9) {
         ConcurrencyLogger.logTaskListenerFail(this.wrapper.getTaskName(), "taskStarting", var9);
      } finally {
         if (context != null) {
            context.restore();
         }

      }

   }

   public void taskSubmitted(Future future, ManagedExecutorService service, Object task) {
      if (!this.submitDone) {
         synchronized(this) {
            if (!this.submitDone) {
               ContextHandleWrapper context = this.wrapper.setupContext();

               try {
                  LogUtils.logListenerInvocation(this.listener, future, service, task, (Throwable)null, "taskSubmitted");
                  this.listener.taskSubmitted(future, service, task);
               } catch (Throwable var12) {
                  ConcurrencyLogger.logTaskListenerFail(this.wrapper.getTaskName(), "taskSubmitted", var12);
               } finally {
                  if (context != null) {
                     context.restore();
                  }

                  this.submitDone = true;
               }

            }
         }
      }
   }
}
