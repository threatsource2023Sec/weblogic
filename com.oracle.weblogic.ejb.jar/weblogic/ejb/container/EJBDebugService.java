package weblogic.ejb.container;

import weblogic.diagnostics.debug.DebugLogger;

public final class EJBDebugService {
   public static final DebugLogger compilationLogger = DebugLogger.getDebugLogger("DebugEjbCompilation");
   public static final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugEjbDeployment");
   public static final DebugLogger mdbConnectionLogger = DebugLogger.getDebugLogger("DebugEjbMdbConnection");
   public static final DebugLogger cachingLogger = DebugLogger.getDebugLogger("DebugEjbCaching");
   public static final DebugLogger swappingLogger = DebugLogger.getDebugLogger("DebugEjbSwapping");
   public static final DebugLogger swappingLoggerVerbose = DebugLogger.getDebugLogger("DebugEjbSwappingVerbose");
   public static final DebugLogger lockingLogger = DebugLogger.getDebugLogger("DebugEjbLocking");
   public static final DebugLogger poolingLogger = DebugLogger.getDebugLogger("DebugEjbPooling");
   public static final DebugLogger timerLogger = DebugLogger.getDebugLogger("DebugEjbTimers");
   public static final DebugLogger invokeLogger = DebugLogger.getDebugLogger("DebugEjbInvoke");
   public static final DebugLogger securityLogger = DebugLogger.getDebugLogger("DebugEjbSecurity");
   public static final DebugLogger cmpDeploymentLogger = DebugLogger.getDebugLogger("DebugEjbCmpDeployment");
   public static final DebugLogger cmpRuntimeLogger = DebugLogger.getDebugLogger("DebugEjbCmpRuntime");
   public static final DebugLogger metadataLogger = DebugLogger.getDebugLogger("DebugEjbMetadata");
}
