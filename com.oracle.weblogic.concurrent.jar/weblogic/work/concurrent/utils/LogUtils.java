package weblogic.work.concurrent.utils;

import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.future.AbstractFutureImpl;
import weblogic.work.concurrent.future.TaskState;

public class LogUtils {
   public static final String DEBUG_CONCURRENT = "DebugConcurrent";
   public static final String DEBUG_CONTEXT = "DebugConcurrentContext";
   public static final String DEBUG_MES = "DebugConcurrentMES";
   public static final String DEBUG_MSES = "DebugConcurrentMSES";
   public static final String DEBUG_MTF = "DebugConcurrentMTF";
   public static final String DEBUG_TRANS = "DebugConcurrentTransaction";

   public static void logWrongTaskState(String taskName, TaskState state, DebugLogger logger) {
      IllegalStateException ex = new IllegalStateException(String.format("task %s is in a wrong state %s", taskName, state.toString()));
      if (logger.isDebugEnabled()) {
         logger.debug(ex.getMessage(), ex);
      }

      throw ex;
   }

   public static void assertNotNull(Object o, String taskName, String variable, DebugLogger logger) {
      if (o == null) {
         NullPointerException ex = new NullPointerException(String.format("variable %s is null in task %s", variable, taskName));
         if (logger.isDebugEnabled()) {
            logger.debug(ex.getMessage(), ex);
         }

         throw ex;
      }
   }

   public static void checkThread(String taskName, Thread expected, Thread actual, DebugLogger logger) {
      if (expected != actual) {
         IllegalStateException ex = new IllegalStateException(String.format("task %s is running in wrong thread %s, expect %s", taskName, actual.getName(), expected.getName()));
         if (logger.isDebugEnabled()) {
            logger.debug(ex.getMessage(), ex);
         }

         throw ex;
      }
   }

   public static void logStateTransition(String taskName, TaskState from, TaskState to, DebugLogger debugLogger) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(String.format("Concurrent task %s change state from  %s to %s", taskName, from.toString(), to.toString()));
      }
   }

   public static void logListenerInvocation(ManagedTaskListener listener, Future future, ManagedExecutorService service, Object task, Throwable ex, String method) {
      if (future instanceof AbstractFutureImpl) {
         DebugLogger debugLogger = ((AbstractFutureImpl)future).getDebugLogger();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(String.format("Invoking %s of %s for concurrent task %s belonging to %s with exception %s", method, listener, task, service.toString(), ex == null ? "" : ex.toString()));
         }
      }
   }

   public static String getMessageLiftcycleNotSupported() {
      return ConcurrencyLogger.logLifecycleNotsupportedLoggable().getMessage();
   }

   public static String getMessageEmptyTaskList() {
      return ConcurrencyLogger.logEmptyTaskListLoggable().getMessage();
   }

   public static String getMessageRejectForStop(String name) {
      return ConcurrencyLogger.logRejectForStopLoggable(name).getMessage();
   }

   public static String getMessageCancelForStop(String name) {
      return ConcurrencyLogger.logCancelForStopLoggable(name).getMessage();
   }

   public static String getMessageWrongParaemter(String paramName, long value) {
      return ConcurrencyLogger.logWrongParaemterLoggable(paramName, String.valueOf(value)).getMessage();
   }

   public static String getMessageDefaultConcurrentObjectOverrideNotAllowed(String name) {
      return ConcurrencyLogger.logDefaultConcurrentObjectOverrideNotAllowedLoggable(name).getMessage();
   }

   public static String getMessageWrongConcurrentObjectJNDI(String name, String jndi) {
      return ConcurrencyLogger.logWrongConcurrentObjectJNDILoggable(name, jndi).getMessage();
   }

   public static void warnSkipClassloaderCheck(String objName, String taskName) {
      ConcurrencyLogger.logSkipClassloaderCheck(objName, taskName);
   }
}
