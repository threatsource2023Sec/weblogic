package weblogic.deploy.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;

public class Debug {
   private static boolean ADD_THREAD_NAME = false;
   public static final DebugLogger serviceLogger = DebugLogger.getDebugLogger("DebugDeploymentServiceInternal");
   public static final DebugLogger serviceStatusLogger = DebugLogger.getDebugLogger("DebugDeploymentServiceStatusUpdates");
   public static final DebugLogger serviceTransportLogger = DebugLogger.getDebugLogger("DebugDeploymentServiceTransport");
   public static final DebugLogger serviceHttpLogger = DebugLogger.getDebugLogger("DebugDeploymentServiceTransportHttp");
   public static final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugDeployment");
   public static final DebugLogger deploymentConciseLogger = DebugLogger.getDebugLogger("DebugDeploymentConcise");
   public static final DebugLogger DEPLOY_STATE_DEBUGGER = DebugLogger.getDebugLogger("DeploymentState");
   public static final boolean isDeploymentPerformanceDebugEnabled = Boolean.getBoolean("weblogic.debug.DeploymentPerformance");

   public static final void serviceDebug(String message) {
      serviceLogger.debug(addThreadToMessage(message));
   }

   public static final boolean isServiceDebugEnabled() {
      return serviceLogger.isDebugEnabled();
   }

   public static final void serviceStatusDebug(String message) {
      serviceStatusLogger.debug(addThreadToMessage(message));
   }

   public static final boolean isServiceStatusDebugEnabled() {
      return serviceStatusLogger.isDebugEnabled();
   }

   public static final void serviceTransportDebug(String message) {
      serviceTransportLogger.debug(addThreadToMessage(message));
   }

   public static final boolean isServiceTransportDebugEnabled() {
      return serviceTransportLogger.isDebugEnabled();
   }

   public static final void serviceHttpDebug(String message) {
      serviceHttpLogger.debug(addThreadToMessage(message));
   }

   public static final boolean isServiceHttpDebugEnabled() {
      return serviceHttpLogger.isDebugEnabled();
   }

   public static final void deploymentDebug(String message) {
      deploymentLogger.debug(addThreadToMessage(message));
   }

   public static final void deploymentDebug(String message, Throwable t) {
      deploymentLogger.debug(addThreadToMessage(message), t);
   }

   public static final boolean isDeploymentDebugEnabled() {
      return deploymentLogger.isDebugEnabled();
   }

   public static final void deploymentDebugConcise(String message) {
      deploymentConciseLogger.debug(addThreadToMessage(message));
   }

   public static final void deploymentDebugConcise(String message, Throwable t) {
      deploymentConciseLogger.debug(addThreadToMessage(message), t);
   }

   public static final boolean isDeploymentDebugConciseEnabled() {
      return deploymentConciseLogger.isDebugEnabled();
   }

   private static final String addThreadToMessage(String message) {
      return ADD_THREAD_NAME ? "<" + Thread.currentThread().getName() + "> " + message : message;
   }

   public static final void logPerformanceData(String message) {
      System.out.println("<" + (new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss,SSS a z")).format(new Date()) + "> <Perf> " + message);
   }
}
