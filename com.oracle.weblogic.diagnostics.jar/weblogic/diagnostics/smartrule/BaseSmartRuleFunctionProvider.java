package weblogic.diagnostics.smartrule;

import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.FixedExpressionEvaluator;
import java.lang.annotation.Annotation;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.GlobalServiceLocator;

public abstract class BaseSmartRuleFunctionProvider {
   public static final String TYPE_JVMRuntime = "com.bea:Type=JVMRuntime";
   public static final String HEAP_INSTANCE_EXPR = "com.bea:Type=JVMRuntime,*";
   public static final String HEAP_ATTRIBUTE = "HeapFreePercent";
   public static final String TYPE_OperatingSystem = "java.lang:type=OperatingSystem";
   public static final String OPERATING_SYSTEM_INSTANCE_EXPR = "java.lang:type=OperatingSystem,*";
   public static final String SYSLOADAVG_ATTRIBUTE = "SystemLoadAverage";
   public static final String PROCCPULOAD_ATTRIBUTE = "ProcessCpuLoad";
   public static final String SYSCPULOAD_ATTRIBUTE = "SystemCpuLoad";
   public static final String TYPE_ThreadPoolRuntime = "com.bea:Type=ThreadPoolRuntime";
   public static final String THREADPOOL_INSTANCE_EXPR = "com.bea:Type=ThreadPoolRuntime,*";
   public static final String IDLE_THREADCOUNT_ATTRIBUTE = "ExecuteThreadIdleCount";
   public static final String STUCK_THREADCOUNT_ATTRIBUTE = "StuckThreadCount";
   public static final String PENDING_REQUESTS_COUNT_ATTRIBUTE = "PendingUserRequestCount";
   public static final String QUEUELENGTH_ATTRIBUTE = "QueueLength";
   public static final String THROUGHPUT_ATTRIBUTE = "Throughput";
   public static final String ATTR_JVMRuntime_HeapFreePercent = "wls.runtime.serverRuntime.JVMRuntime.heapFreePercent";
   public static final String ATTR_OperatingSystem_SystemLoadAverage = "wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemLoadAverage')";
   public static final String ATTR_OperatingSystem_ProcessCPULoad = "wls.platform.query('java.lang:type=OperatingSystem,*', 'ProcessCpuLoad')";
   public static final String ATTR_OperatingSystem_SystemCPULoad = "wls.platform.query('java.lang:type=OperatingSystem,*', 'SystemCpuLoad')";
   public static final String ATTR_ThreadPoolRuntime_ExecuteThreadIdleCount = "wls.runtime.serverRuntime.threadPoolRuntime.executeThreadIdleCount";
   public static final String ATTR_ThreadPoolRuntime_StuckThreadCount = "wls.runtime.serverRuntime.threadPoolRuntime.stuckThreadCount";
   public static final String ATTR_ThreadPoolRuntime_PendingUserRequestCount = "wls.runtime.serverRuntime.threadPoolRuntime.pendingUserRequestCount";
   public static final String ATTR_ThreadPoolRuntime_QueueLength = "wls.runtime.serverRuntime.threadPoolRuntime.queueLength";
   public static final String ATTR_ThreadPoolRuntime_Throughput = "wls.runtime.serverRuntime.threadPoolRuntime.throughput";
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionFunctions");
   private static EvaluatorFactory factory;

   private static EvaluatorFactory getEvaluatorFactory() {
      if (factory == null) {
         factory = (EvaluatorFactory)GlobalServiceLocator.getServiceLocator().getService(EvaluatorFactory.class, new Annotation[0]);
      }

      return factory;
   }

   protected static boolean evaluateExpression(String expr) {
      boolean result = false;
      long t0 = System.nanoTime();
      long t1 = -1L;
      long t2 = -1L;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SmartRule-evaluateExpression: expr=" + expr);
      }

      try {
         EvaluatorFactory evalFactory = getEvaluatorFactory();
         FixedExpressionEvaluator evaluator = evalFactory.createEvaluator(expr, Boolean.class, new Annotation[0]);

         try {
            t1 = System.nanoTime();
            result = (Boolean)evaluator.evaluate();
         } finally {
            t2 = System.nanoTime();
            evalFactory.destroyEvaluator(evaluator);
         }
      } finally {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SmartRule-evaluateExpression: result=" + result + " dT1=" + (t1 > 0L ? t1 - t0 : -1L) + " dT2=" + (t2 > 0L ? t2 - t1 : -1L));
         }

      }

      return result;
   }

   protected static void validateOperation(String oper) {
      if (oper != null && oper.length() != 0) {
         switch (oper) {
            case "<":
            case "<=":
            case "==":
            case ">":
            case ">=":
               return;
            default:
               throw new IllegalArgumentException("Illegal smart rule operation type: " + oper);
         }
      } else {
         throw new IllegalArgumentException("Smart rule operation can not be empty or null");
      }
   }
}
