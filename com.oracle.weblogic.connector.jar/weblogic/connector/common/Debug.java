package weblogic.connector.common;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.ProfileDataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;

public class Debug {
   public static final int INDENT_WIDTH = 2;
   private static final String WHITE_SPACE = "";
   private static final int MAX_SPACES = "".length();
   public static boolean verbose = getSystemProperty("weblogic.connector.Debug") != null && !getSystemProperty("weblogic.connector.Debug").equalsIgnoreCase("false");
   public static int indentLevel = 0;
   static DebugLogger logger = DebugLogger.createUnregisteredDebugLogger("ConnectorDebug", true);
   public static final DebugLogger concurrentlogger = DebugLogger.createUnregisteredDebugLogger("DebugConnectorConcurrent", Boolean.getBoolean("weblogic.connector.DebugConcurrent"));
   private static DebugLogger XAIN_LOGGER = null;
   private static DebugLogger CONNECTIONS_LOGGER = null;
   private static DebugLogger CONNEVENTS_LOGGER = null;
   private static DebugLogger DEPLOYMENT_LOGGER = null;
   private static DebugLogger LOCALOUT_LOGGER = null;
   private static DebugLogger PARSING_LOGGER = null;
   private static DebugLogger POOLING_LOGGER = null;
   private static DebugLogger POOLVERBOSE_LOGGER = null;
   private static DebugLogger RALIFECYCLE_LOGGER = null;
   private static DebugLogger SECURITYCTX_LOGGER = null;
   private static DebugLogger SERVICE_LOGGER = null;
   private static DebugLogger WORK_LOGGER = null;
   private static DebugLogger WORKEVENTS_LOGGER = null;
   private static DebugLogger XAOUT_LOGGER = null;
   private static DebugLogger XAWORK_LOGGER = null;
   private static DebugLogger RACLASSLOADING = null;

   public static final boolean isSecurityManagerEnabled() {
      return System.getSecurityManager() != null;
   }

   public static final String getSystemProperty(final String name) {
      return isSecurityManagerEnabled() ? (String)AccessController.doPrivileged(new PrivilegedAction() {
         public String run() {
            return System.getProperty(name);
         }
      }) : System.getProperty(name);
   }

   public static final void setSystemProperty(final String name, final String value) {
      if (isSecurityManagerEnabled()) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               System.setProperty(name, value);
               return null;
            }
         });
      } else {
         System.setProperty(name, value);
      }

   }

   public static final Properties getSystemProperties() {
      return isSecurityManagerEnabled() ? (Properties)AccessController.doPrivileged(new PrivilegedAction() {
         public Properties run() {
            return System.getProperties();
         }
      }) : System.getProperties();
   }

   public static DebugLogger xain() {
      if (XAIN_LOGGER == null) {
         XAIN_LOGGER = DebugLogger.getDebugLogger("DebugRAXAin");
      }

      return XAIN_LOGGER;
   }

   public static DebugLogger connections() {
      if (CONNECTIONS_LOGGER == null) {
         CONNECTIONS_LOGGER = DebugLogger.getDebugLogger("DebugRAConnections");
      }

      return CONNECTIONS_LOGGER;
   }

   public static DebugLogger connevents() {
      if (CONNEVENTS_LOGGER == null) {
         CONNEVENTS_LOGGER = DebugLogger.getDebugLogger("DebugRAConnEvents");
      }

      return CONNEVENTS_LOGGER;
   }

   public static DebugLogger deployment() {
      if (DEPLOYMENT_LOGGER == null) {
         DEPLOYMENT_LOGGER = DebugLogger.getDebugLogger("DebugRADeployment");
      }

      return DEPLOYMENT_LOGGER;
   }

   public static DebugLogger localout() {
      if (LOCALOUT_LOGGER == null) {
         LOCALOUT_LOGGER = DebugLogger.getDebugLogger("DebugRALocalOut");
      }

      return LOCALOUT_LOGGER;
   }

   public static DebugLogger parsing() {
      if (PARSING_LOGGER == null) {
         PARSING_LOGGER = DebugLogger.getDebugLogger("DebugRAParsing");
      }

      return PARSING_LOGGER;
   }

   public static DebugLogger pooling() {
      if (POOLING_LOGGER == null) {
         POOLING_LOGGER = DebugLogger.getDebugLogger("DebugRAPooling");
      }

      return POOLING_LOGGER;
   }

   public static DebugLogger poolverbose() {
      if (POOLVERBOSE_LOGGER == null) {
         POOLVERBOSE_LOGGER = DebugLogger.getDebugLogger("DebugRAPoolVerbose");
      }

      return POOLVERBOSE_LOGGER;
   }

   public static DebugLogger ralifecycle() {
      if (RALIFECYCLE_LOGGER == null) {
         RALIFECYCLE_LOGGER = DebugLogger.getDebugLogger("DebugRALifecycle");
      }

      return RALIFECYCLE_LOGGER;
   }

   public static DebugLogger securityctx() {
      if (SECURITYCTX_LOGGER == null) {
         SECURITYCTX_LOGGER = DebugLogger.getDebugLogger("DebugRASecurityCtx");
      }

      return SECURITYCTX_LOGGER;
   }

   public static DebugLogger service() {
      if (SERVICE_LOGGER == null) {
         SERVICE_LOGGER = DebugLogger.getDebugLogger("DebugConnectorService");
      }

      return SERVICE_LOGGER;
   }

   public static DebugLogger work() {
      if (WORK_LOGGER == null) {
         WORK_LOGGER = DebugLogger.getDebugLogger("DebugRAWork");
      }

      return WORK_LOGGER;
   }

   public static DebugLogger workevents() {
      if (WORKEVENTS_LOGGER == null) {
         WORKEVENTS_LOGGER = DebugLogger.getDebugLogger("DebugRAWorkEvents");
      }

      return WORKEVENTS_LOGGER;
   }

   public static DebugLogger xaout() {
      if (XAOUT_LOGGER == null) {
         XAOUT_LOGGER = DebugLogger.getDebugLogger("DebugRAXAout");
      }

      return XAOUT_LOGGER;
   }

   public static DebugLogger xawork() {
      if (XAWORK_LOGGER == null) {
         XAWORK_LOGGER = DebugLogger.getDebugLogger("DebugRAXAwork");
      }

      return XAWORK_LOGGER;
   }

   public static DebugLogger classloading() {
      if (RACLASSLOADING == null) {
         RACLASSLOADING = DebugLogger.getDebugLogger("DebugRAClassloader");
      }

      return RACLASSLOADING;
   }

   public static void connEvent(String msg) {
      connevents().debug(msg);
   }

   public static void connEvent(String msg, Throwable t) {
      connevents().debug(msg, t);
   }

   public static void connections(String msg) {
      connections().debug(msg);
   }

   public static void connections(String msg, Throwable t) {
      connections().debug(msg, t);
   }

   public static void deployment(String msg) {
      deployment().debug(msg);
   }

   public static void deployment(String msg, Throwable t) {
      deployment().debug(msg, t);
   }

   public static void localOut(String msg) {
      localout().debug(msg);
   }

   public static void localOut(String msg, Throwable t) {
      localout().debug(msg, t);
   }

   public static void parsing(String msg) {
      parsing().debug(msg);
   }

   public static void parsing(String msg, Throwable t) {
      parsing().debug(msg, t);
   }

   public static void poolVerbose(String msg) {
      poolverbose().debug(msg);
   }

   public static void poolVerbose(String msg, Throwable t) {
      poolverbose().debug(msg, t);
   }

   public static void pooling(String msg) {
      pooling().debug(msg);
   }

   public static void pooling(String msg, Throwable t) {
      pooling().debug(msg, t);
   }

   public static void raLifecycle(String msg) {
      ralifecycle().debug(msg);
   }

   public static void raLifecycle(String msg, Throwable t) {
      ralifecycle().debug(msg, t);
   }

   public static void securityCtx(String msg) {
      securityctx().debug(msg);
   }

   public static void securityCtx(String msg, Throwable t) {
      securityctx().debug(msg, t);
   }

   public static void service(String msg) {
      service().debug(msg);
   }

   public static void service(String msg, Throwable t) {
      service().debug(msg, t);
   }

   public static void work(String msg) {
      work().debug(msg);
   }

   public static void work(String msg, Throwable t) {
      work().debug(msg, t);
   }

   public static void workEvent(String msg) {
      workevents().debug(msg);
   }

   public static void workEvent(String msg, Throwable t) {
      workevents().debug(msg, t);
   }

   public static void xaIn(String msg) {
      xain().debug(msg);
   }

   public static void xaIn(String msg, Throwable t) {
      xain().debug(msg, t);
   }

   public static void xaOut(String msg) {
      xaout().debug(msg);
   }

   public static void xaOut(String msg, Throwable t) {
      xaout().debug(msg, t);
   }

   public static void xaWork(String msg) {
      xawork().debug(msg);
   }

   public static void xaWork(String msg, Throwable t) {
      xawork().debug(msg, t);
   }

   public static void classloading(String msg) {
      classloading().debug(msg);
   }

   public static void classloading(String msg, Throwable t) {
      classloading().debug(msg, t);
   }

   public static void localOut(ConnectionPool pool, String msg) {
      if (isLocalOutEnabled()) {
         localOut("For pool '" + (pool != null ? pool.getName() : "<null>") + "' " + msg);
      }

   }

   public static void xaOut(ConnectionPool pool, String msg) {
      if (isXAoutEnabled()) {
         xaOut("For pool '" + (pool != null ? pool.getName() : "<null>") + "' " + msg);
      }

   }

   public static void enter(Object o, String s) {
      if (verbose || getVerbose(o)) {
         logger.debug(spaces() + getClassName(o) + "." + s + " entered: " + (o != null ? o.toString() : ""));
         ++indentLevel;
      }

   }

   public static void enter(String s) {
      if (verbose) {
         logger.debug(spaces() + s + "() entered. ");
         ++indentLevel;
      }

   }

   public static void exit(Object o, String s) {
      if (verbose || getVerbose(o)) {
         --indentLevel;
         logger.debug(spaces() + getClassName(o) + "." + s + " exiting: " + (o != null ? o.toString() : ""));
      }

   }

   public static void exit(String s) {
      if (verbose) {
         --indentLevel;
         logger.debug(spaces() + s + "() exiting. ");
      }

   }

   public static void println(Object obj, String str) {
      if (verbose || getVerbose(obj)) {
         logger.debug(spaces() + getClassName(obj) + str);
      }

   }

   public static void println(String str) {
      if (verbose) {
         logger.debug(spaces() + str);
      }

   }

   public static void println(String str, Throwable t) {
      if (verbose) {
         logger.debug(spaces() + str, t);
      }

   }

   public static void printHashtable(Object o, String msg, String htName, Hashtable ht) {
      if (verbose || getVerbose(o)) {
         logger.debug(hashtableToString(o, msg, htName, ht));
      }

   }

   public static String hashtableToString(Object o, String msg, String htName, Hashtable ht) {
      String nameAndMsg = "";
      if (o != null) {
         nameAndMsg = getClassName(o) + msg;
      } else {
         nameAndMsg = msg;
      }

      String header = nameAndMsg + " Hashtable " + htName + " =\n";
      if (ht == null) {
         return header + nameAndMsg + " Hashtable " + htName + " is null ";
      } else {
         return ht.isEmpty() ? header + nameAndMsg + " Hashtable " + htName + " is EMPTY " : header + nameAndMsg + " Hashtable " + htName + " has " + ht.size() + " entries.\n" + nameAndMsg + " Hashtable " + htName + " = " + ht.toString();
      }
   }

   public static void showClassLoaders(Object caller, Object obj) {
      if (verbose) {
         String objString = null;
         if (obj != null) {
            try {
               objString = obj.toString();
            } catch (Throwable var4) {
               objString = "obj.toString() threw " + var4;
            }
         }

         println("Classloaders for object " + objString + ":");
         showClassLoaderTree("\t", obj.getClass().getClassLoader());
         if (caller != null) {
            showClassLoaderTree("\tCaller's classloader = ", caller.getClass().getClassLoader());
         }

         showClassLoaderTree("\tSystem classloader = ", ClassLoader.getSystemClassLoader());
         showClassLoaderTree("\tThread context classloader = ", Thread.currentThread().getContextClassLoader());
      }

   }

   public static void showClassLoaderTree(String msg, ClassLoader cl) {
      String tabs = "";
      String parent = "";
      println(msg);

      while(cl != null) {
         println(tabs + parent + cl);
         tabs = tabs + "\t";
         parent = "parent: ";
         cl = cl.getParent();
      }

   }

   public static void assertion(boolean condition, String message) {
      if (!condition) {
         throwAssertionError(message);
      }

   }

   public static String logJarFileProcessingError(Exception ex) {
      return ConnectorLogger.logJarFileProcessingError(ex);
   }

   public static String logStackTrace(String msgId, Throwable t) {
      return ConnectorLogger.logStackTrace(msgId, t);
   }

   public static String logStackTraceString(String msgId, String stackTrace) {
      return ConnectorLogger.logStackTraceString(msgId, stackTrace);
   }

   public static String logConnectorServiceShutdownError(String exceptionMsg) {
      return ConnectorLogger.logConnectorServiceShutdownError(exceptionMsg);
   }

   public static String logConnectorServiceInitializing() {
      return ConnectorLogger.logConnectorServiceInitializing();
   }

   public static String logConnectorServiceInitError(String exceptionMsg) {
      return ConnectorLogger.logConnectorServiceInitError(exceptionMsg);
   }

   public static String logConnectorServiceInitialized() {
      return ConnectorLogger.logConnectorServiceInitialized();
   }

   public static String logDeprecationReplacedWarning(String oldElement, String newElement) {
      return ConnectorLogger.logDeprecationReplacedWarning(oldElement, newElement);
   }

   public static String logDeprecationNotUsedWarning(String oldElement) {
      return ConnectorLogger.logDeprecationNotUsedWarning(oldElement);
   }

   public static String logDeprecatedLinkref(String a) {
      return ConnectorLogger.logDeprecatedLinkref(a);
   }

   public static String logJNDINameAlreadyExists(String a) {
      return ConnectorLogger.logJNDINameAlreadyExists(a);
   }

   public static String logRarMarkedForLateDeployment(String a) {
      return ConnectorLogger.logRarMarkedForLateDeployment(a);
   }

   public static String logCreateCFforMCFError(String a, ResourceException e) {
      return ConnectorLogger.logCreateCFforMCFError(a, e);
   }

   public static String logCreateInitialConnectionsError(String poolName, String errString) {
      return ConnectorLogger.logCreateInitialConnectionsError(poolName, errString);
   }

   public static String logInitConnRTMBeanError(String poolName, String exceptionMsg) {
      return ConnectorLogger.logInitConnRTMBeanError(poolName, exceptionMsg);
   }

   public static String logUnregisterConnRTMBeanError(String poolName, String exceptionString) {
      return ConnectorLogger.logUnregisterConnRTMBeanError(poolName, exceptionString);
   }

   public static String logInitCPRTMBeanError(String poolName, String exceptionString) {
      return ConnectorLogger.logInitCPRTMBeanError(poolName, exceptionString);
   }

   public static String logUnregisterCPRTMBeanError(String a, String b) {
      return ConnectorLogger.logUnregisterCPRTMBeanError(a, b);
   }

   public static String logProxyTestError(String a, Throwable t) {
      return ConnectorLogger.logProxyTestError(a, t);
   }

   public static String logProxyTestFailureInfo(String a, String b) {
      return ConnectorLogger.logProxyTestFailureInfo(a, b);
   }

   public static String logProxyTestStarted(String a) {
      return ConnectorLogger.logProxyTestStarted(a);
   }

   public static String logProxyTestSuccess(String a) {
      return ConnectorLogger.logProxyTestSuccess(a);
   }

   public static String logReReleasingResource(String poolName) {
      return ConnectorLogger.logReReleasingResource(poolName);
   }

   public static String logSetLogWriterError(String a) {
      return ConnectorLogger.logSetLogWriterError(a);
   }

   public static String logSetLogWriterErrorWithCause(String a, String exMsg, String cause) {
      return ConnectorLogger.logSetLogWriterErrorWithCause(a, exMsg, cause);
   }

   public static String logFindLogWriterError(String a, String b) {
      return ConnectorLogger.logFindLogWriterError(a, b);
   }

   public static String logCreateManagedConnectionException(String a, String b) {
      return ConnectorLogger.logCreateManagedConnectionException(a, b);
   }

   public static String logCreateManagedConnectionError(String poolName) {
      return ConnectorLogger.logCreateManagedConnectionError(poolName);
   }

   public static String logCloseConnectionError(String a, ConnectionInfo connectionInfo, String action, Throwable t) {
      return ConnectorLogger.logCloseConnectionError(a, connectionInfo == null ? "[null]" : connectionInfo.toString(), action, t);
   }

   public static String logCloseNotFoundOnHandle(String a) {
      return ConnectorLogger.logCloseNotFoundOnHandle(a);
   }

   public static String logConnectionAlreadyClosed(String a) {
      return ConnectorLogger.logConnectionAlreadyClosed(a);
   }

   public static String logAccessDeniedWarning(String a, String b, String c, String d) {
      return ConnectorLogger.logAccessDeniedWarning(a, b, c, d);
   }

   public static String logNoConnectionRequestInfo() {
      return ConnectorLogger.logNoConnectionRequestInfo();
   }

   public static String logNoResourcePrincipalFound() {
      return ConnectorLogger.logNoResourcePrincipalFound();
   }

   public static String logRequestedSecurityType(String a, String b) {
      return ConnectorLogger.logRequestedSecurityType(a, b);
   }

   public static String logContextProcessingError(NamingException e) {
      return ConnectorLogger.logContextProcessingError(e);
   }

   public static String logMCFNotFoundForJNDIName(String a) {
      return ConnectorLogger.logMCFNotFoundForJNDIName(a);
   }

   public static String logCreateCFReturnedNull(String a) {
      return ConnectorLogger.logCreateCFReturnedNull(a);
   }

   public static String logGetLocalTransactionError(String exString, String jndiName) {
      return ConnectorLogger.logGetLocalTransactionError(exString, jndiName);
   }

   public static String logRegisterNonXAResourceError(String a, String b) {
      return ConnectorLogger.logRegisterNonXAResourceError(a, b);
   }

   public static String logGetXAResourceError(String a, String b) {
      return ConnectorLogger.logGetXAResourceError(a, b);
   }

   public static String logRegisterXAResourceError(String a, String b) {
      return ConnectorLogger.logRegisterXAResourceError(a, b);
   }

   public static String logInvokeMethodError(String a, String b, String c) {
      return ConnectorLogger.logInvokeMethodError(a, b, c);
   }

   public static String logDiagImageUnregisterFailure(Throwable t) {
      return ConnectorLogger.logDiagImageUnregisterFailure(t);
   }

   public static String logDiagImageRegisterFailure(Throwable t) {
      return ConnectorLogger.logDiagImageRegisterFailure(t);
   }

   public static String logConfigPropWarning(String className, String raName, String errMsgs) {
      return ConnectorLogger.logConfigPropWarning(className, raName, errMsgs);
   }

   public static String logGetAnonymousSubjectFailed() {
      return ConnectorLogger.logGetAnonymousSubjectFailed();
   }

   public static String logFailedToFindModuleRuntimeMBean(String message) {
      return ConnectorLogger.logFailedToFindModuleRuntimeMBean(message);
   }

   public static String logFailedToUnregisterModuleRuntimeMBean(String ex) {
      return ConnectorLogger.logFailedToUnregisterModuleRuntimeMBean(ex);
   }

   public static String logInitJndiSubcontextsFailed(String appId, String ex) {
      return ConnectorLogger.logInitJndiSubcontextsFailed(appId, ex);
   }

   public static String logExtractingNativeLib(String fileName, String nativeLibDir) {
      return ConnectorLogger.logExtractingNativeLib(fileName, nativeLibDir);
   }

   public static String logTimerWarning() {
      return ConnectorLogger.logTimerWarning();
   }

   public static String logInvalidDye(String moduleName, String ex) {
      return ConnectorLogger.logInvalidDye(moduleName, ex);
   }

   public static String logRegisterForXARecoveryFailed(String ex) {
      return ConnectorLogger.logRegisterForXARecoveryFailed(ex);
   }

   public static String logUnregisterForXARecoveryFailed(String ex) {
      return ConnectorLogger.logUnregisterForXARecoveryFailed(ex);
   }

   public static String logFailedToApplyPoolChanges(String ex) {
      return ConnectorLogger.logFailedToApplyPoolChanges(ex);
   }

   public static String logMCFNotImplementResourceAdapterAssociation(String mcf) {
      return ConnectorLogger.logMCFNotImplementResourceAdapterAssociation(mcf);
   }

   public static String logInvalidRecoveryEvent(String msg) {
      return ConnectorLogger.logInvalidRecoveryEvent(msg);
   }

   public static String logCleanupFailure(String ex) {
      return ConnectorLogger.logCleanupFailure(ex);
   }

   public static String logConnectionError(String error) {
      return ConnectorLogger.logConnectionError(error);
   }

   public static String logDestroyFailed(String ex) {
      return ConnectorLogger.logDestroyFailed(ex);
   }

   public static String logNullXAResource() {
      return ConnectorLogger.logNullXAResource();
   }

   public static String logDissociateHandlesFailed(String jndiName, String ex) {
      return ConnectorLogger.logDissociateHandlesFailed(jndiName, ex);
   }

   public static String logLazyEnlistNullMC() {
      return ConnectorLogger.logLazyEnlistNullMC();
   }

   public static String logRequestedSharingScope(String jndiName, String sharingScope) {
      return ConnectorLogger.logRequestedSharingScope(jndiName, sharingScope);
   }

   public static String logFailedToDeployLinkRef(String moduleName, String baseName, String ex) {
      return ConnectorLogger.logFailedToDeployLinkRef(moduleName, baseName, ex);
   }

   public static String logAssertionError(String msg, Throwable ex) {
      return ConnectorLogger.logAssertionError(msg, ex);
   }

   public static String logPoolProfilingRecord(ProfileDataRecord record) {
      return ConnectorLogger.logPoolProfilingRecord(record.getPoolName(), record.getType(), record.getTimestamp(), record.getPropertiesString());
   }

   public static String logPropertyVetoWarning(String object, String propName, String propType, String propVal, String vetoMsg) {
      return ConnectorLogger.logPropertyVetoWarning(object, propName, propType, propVal, vetoMsg);
   }

   public static String logNoAdapterJNDInameSetForInboundRA(String moduleName, String appName) {
      return ConnectorLogger.logNoAdapterJNDInameSetForInboundRA(moduleName, appName);
   }

   public static String logDiagnosticImageTimedOut() {
      return ConnectorLogger.logDiagnosticImageTimedOut();
   }

   public static String logBuildOutboundFailed(String xpath) {
      return ConnectorLogger.logBuildOutboundFailed(xpath);
   }

   public static String logCreateInboundRuntimeMBeanFailed(String name, String err) {
      return ConnectorLogger.logCreateInboundRuntimeMBeanFailed(name, err);
   }

   public static String logFailedToCloseLog(String name, String err) {
      return ConnectorLogger.logFailedToCloseLog(name, err);
   }

   public static String logFailedToCreateLogStream(String name, String err) {
      return ConnectorLogger.logFailedToCreateLogStream(name, err);
   }

   public static String logSecurityPrincipalMapNotAllowed() {
      return ConnectorLogger.logSecurityPrincipalMapNotAllowed();
   }

   public static String logComplianceRAConfigurationException(String msg) {
      return ConnectorLogger.logComplianceRAConfigurationException(msg);
   }

   public static String logComplianceWLRAConfigurationException(String msg) {
      return ConnectorLogger.logComplianceWLRAConfigurationException(msg);
   }

   public static String logNoComplianceErrors(String raName) {
      return ConnectorLogger.logNoComplianceErrors(raName);
   }

   public static String logComplianceWarnings(String raName, String warnings) {
      return ConnectorLogger.logComplianceWarnings(raName, warnings);
   }

   public static String logComplianceIsLinkRef(String linkRefName) {
      return ConnectorLogger.logComplianceIsLinkRef(linkRefName);
   }

   public static String getStringAnonymousUser() {
      return ConnectorLogger.getStringAnonymousUserLoggable().getMessageText();
   }

   public static String getStringCloseCount() {
      return ConnectorLogger.getStringCloseCountLoggable().getMessageText();
   }

   public static String getStringCreateCount() {
      return ConnectorLogger.getStringCreateCountLoggable().getMessageText();
   }

   public static String getStringFreePoolSize() {
      return ConnectorLogger.getStringFreePoolSizeLoggable().getMessageText();
   }

   public static String getStringPoolSize() {
      return ConnectorLogger.getStringPoolSizeLoggable().getMessageText();
   }

   public static String getStringWaitingThreadCount() {
      return ConnectorLogger.getStringWaitingThreadCountLoggable().getMessageText();
   }

   public static String getStringCloseCountDescription() {
      return ConnectorLogger.getStringCloseCountLoggable().getMessageText();
   }

   public static String getStringCreateCountDescription() {
      return ConnectorLogger.getStringCreateCountDescriptionLoggable().getMessageText();
   }

   public static String getStringFreePoolSizeDescription() {
      return ConnectorLogger.getStringFreePoolSizeDescriptionLoggable().getMessageText();
   }

   public static String getStringPoolSizeDescription() {
      return ConnectorLogger.getStringPoolSizeDescriptionLoggable().getMessageText();
   }

   public static String getStringWaitingThreadCountDescription() {
      return ConnectorLogger.getStringWaitingThreadCountDescriptionLoggable().getMessageText();
   }

   public static String getStringNever() {
      return ConnectorLogger.getStringNeverLoggable().getMessageText();
   }

   public static String getStringUnavailable() {
      return ConnectorLogger.getStringUnavailableLoggable().getMessageText();
   }

   public static String getStringRunning() {
      return ConnectorLogger.getStringRunningLoggable().getMessageText();
   }

   public static String getStringSuspended() {
      return ManagementLogger.getStringSuspendedLoggable().getMessageText();
   }

   public static String getStringNew() {
      return ManagementLogger.getStringNewLoggable().getMessageText();
   }

   public static String getStringInitialized() {
      return ManagementLogger.getStringInitializedLoggable().getMessageText();
   }

   public static String getStringPrepared() {
      return ManagementLogger.getStringPreparedLoggable().getMessageText();
   }

   public static String getStringActivated() {
      return ManagementLogger.getStringActivatedLoggable().getMessageText();
   }

   public static String getStringUnknown() {
      return ConnectorLogger.getStringUnknownLoggable().getMessageText();
   }

   public static String getExceptionRANewInstanceFailed(String className, String ex) {
      return ConnectorLogger.getExceptionRANewInstanceFailedLoggable(className, ex).getMessageText();
   }

   public static String getExceptionImageSourceCreation(String ex) {
      return ConnectorLogger.getExceptionImageSourceCreationLoggable(ex).getMessageText();
   }

   public static String getExceptionPrepareUninitializedRA() {
      return ConnectorLogger.getExceptionPrepareUninitializedRALoggable().getMessageText();
   }

   public static String getExceptionActivateUnpreparedRA(String state) {
      return ConnectorLogger.getExceptionActivateUnpreparedRALoggable(state).getMessageText();
   }

   public static String getExceptionActivateSuspendedRA(String state) {
      return ConnectorLogger.getExceptionActivateSuspendedRALoggable(state).getMessageText();
   }

   public static String getExceptionRollbackActivatedRA() {
      return ConnectorLogger.getExceptionRollbackActivatedRALoggable().getMessageText();
   }

   public static String getExceptionCreateNativeLib() {
      return ConnectorLogger.getExceptionCreateNativeLibLoggable().getMessageText();
   }

   public static String getExceptionBadRAClassSpec(String raClassName, String raErrors) {
      return ConnectorLogger.getExceptionBadRAClassSpecLoggable(raClassName, raErrors).getMessageText();
   }

   public static String getExceptionBadMCFClassSpec(String mcfClassName, String mcfErrors) {
      return ConnectorLogger.getExceptionBadMCFClassSpecLoggable(mcfClassName, mcfErrors).getMessageText();
   }

   public static String getExceptionAdapterNotVersionable() {
      return ConnectorLogger.getExceptionAdapterNotVersionableLoggable().getMessageText();
   }

   public static String getExceptionPopulateWorkManager() {
      return ConnectorLogger.getExceptionPopulateWorkManagerLoggable().getMessageText();
   }

   public static String getExceptionStartRA(String moduleName, String msg) {
      return ConnectorLogger.getExceptionStartRALoggable(moduleName, msg).getMessageText();
   }

   public static String getExceptionCreateBootstrap(String moduleName, String msg) {
      return ConnectorLogger.getExceptionCreateBootstrapLoggable(moduleName, msg).getMessageText();
   }

   public static String getExceptionVersionRA() {
      return ConnectorLogger.getExceptionVersionRALoggable().getMessageText();
   }

   public static String getExceptionWorkRuntimer() {
      return ConnectorLogger.getExceptionWorkRuntimerLoggable().getMessageText();
   }

   public static String getExceptionIntrospectProperties(String obj) {
      return ConnectorLogger.getExceptionIntrospectPropertiesLoggable(obj).getMessageText();
   }

   public static String getExceptionSetterNotFound(String keyName) {
      return ConnectorLogger.getExceptionSetterNotFoundLoggable(keyName).getMessageText();
   }

   public static String getExceptionInvokeSetter(String keyName) {
      return ConnectorLogger.getExceptionInvokeSetterLoggable(keyName).getMessageText();
   }

   public static String getExceptionBadPropertyType(String type) {
      return ConnectorLogger.getExceptionBadPropertyTypeLoggable(type).getMessageText();
   }

   public static String getExceptionPropertyValueTypeMismatch(String propName, String configType, String configValue, String ex) {
      return ConnectorLogger.getExceptionPropertyValueTypeMismatchLoggable(propName, configType, configValue, ex).getMessageText();
   }

   public static String getExceptionLoginException(String username, String ex, String elementName) {
      return ConnectorLogger.getExceptionLoginExceptionLoggable(username, ex, elementName).getMessageText();
   }

   public static String getExceptionInitialCapacityMustBePositive() {
      return ConnectorLogger.getExceptionInitialCapacityMustBePositiveLoggable().getMessageText();
   }

   public static String getExceptionMaxCapacityZero() {
      return ConnectorLogger.getExceptionMaxCapacityZeroLoggable().getMessageText();
   }

   public static String getExceptionMaxCapacityNegative() {
      return ConnectorLogger.getExceptionMaxCapacityNegativeLoggable().getMessageText();
   }

   public static String getExceptionMaxCapacityLessThanInitialCapacity(String jndiName) {
      return ConnectorLogger.getExceptionMaxCapacityLessThanInitialCapacityLoggable(jndiName).getMessageText();
   }

   public static String getExceptionMaxCapacityIncrementMustBePositive() {
      return ConnectorLogger.getExceptionMaxCapacityIncrementMustBePositiveLoggable().getMessageText();
   }

   public static String getExceptionMaxCapacityTooHigh(String jndiName) {
      return ConnectorLogger.getExceptionMaxCapacityTooHighLoggable(jndiName).getMessageText();
   }

   public static String getExceptionShrinkFrequencySecondsMustBePositive() {
      return ConnectorLogger.getExceptionShrinkFrequencySecondsMustBePositiveLoggable().getMessageText();
   }

   public static String getExceptionInactiveConnectionTimeoutSecondsNegative() {
      return ConnectorLogger.getExceptionInactiveConnectionTimeoutSecondsNegativeLoggable().getMessageText();
   }

   public static String getExceptionNoDescriptorOrAltDD() {
      return ConnectorLogger.getExceptionNoDescriptorOrAltDDLoggable().getMessageText();
   }

   public static String getExceptionNoDescriptor() {
      return ConnectorLogger.getExceptionNoDescriptorLoggable().getMessageText();
   }

   public static String getExceptionMissingSchema() {
      return ConnectorLogger.getExceptionMissingSchemaLoggable().getMessageText();
   }

   public static String getExceptionNoComponents(String displayName) {
      return ConnectorLogger.getExceptionNoComponentsLoggable(displayName).getMessageText();
   }

   public static String getExceptionMoreThanOneComponent(String displayName) {
      return ConnectorLogger.getExceptionMoreThanOneComponentLoggable(displayName).getMessageText();
   }

   public static String getExceptionRollbackModuleFailed(String msg) {
      return ConnectorLogger.getExceptionRollbackModuleFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionCreateRuntimeMBeanForConnectorModuleFailed(String moduleName, String msg) {
      return ConnectorLogger.getExceptionCreateRuntimeMBeanForConnectorModuleFailedLoggable(moduleName, msg).getMessageText();
   }

   public static String getExceptionCloseVJarFailed(String jarFile, String msg) {
      return ConnectorLogger.getExceptionCloseVJarFailedLoggable(jarFile, msg).getMessageText();
   }

   public static String getExceptionCreateVJarFailed(String uri, String msg) {
      return ConnectorLogger.getExceptionCreateVJarFailedLoggable(uri, msg).getMessageText();
   }

   public static String getExceptionInitializeJndiSubcontextsFailed(String appId, String msg) {
      return ConnectorLogger.getExceptionInitializeJndiSubcontextsFailedLoggable(appId, msg).getMessageText();
   }

   public static String getExceptionPrepareUpdateFailed(String uri, String msg) {
      return ConnectorLogger.getExceptionPrepareUpdateFailedLoggable(uri, msg).getMessageText();
   }

   public static String getExceptionJndiNameNull() {
      return ConnectorLogger.getExceptionJndiNameNullLoggable().getMessageText();
   }

   public static String getExceptionNoInitialContextForJndi() {
      return ConnectorLogger.getExceptionNoInitialContextForJndiLoggable().getMessageText();
   }

   public static String getExceptionJndiNameAlreadyBound(String jndiName) {
      return ConnectorLogger.getExceptionAlreadyDeployedLoggable(jndiName).getMessageText();
   }

   public static String getExceptionAlreadyDeployed(String jndiName) {
      return ConnectorLogger.getExceptionAlreadyDeployedLoggable(jndiName).getMessageText();
   }

   public static String getExceptionBindingFailed(String jndiName, String msg) {
      return ConnectorLogger.getExceptionBindingFailedLoggable(jndiName, msg).getMessageText();
   }

   public static String getExceptionUnbindFailed(String jndiName, String msg) {
      return ConnectorLogger.getExceptionUnbindFailedLoggable(jndiName, msg).getMessageText();
   }

   public static String getExceptionNoInitialContextForUnbind(String msg) {
      return ConnectorLogger.getExceptionNoInitialContextForUnbindLoggable(msg).getMessageText();
   }

   public static String getExceptionUnbindAdminObjectFailed(String msg) {
      return ConnectorLogger.getExceptionUnbindAdminObjectFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionGetConnectionFactoryFailed(String key) {
      return ConnectorLogger.getExceptionGetConnectionFactoryFailedLoggable(key).getMessageText();
   }

   public static String getExceptionRANotDeployed(String jndiName) {
      return ConnectorLogger.getExceptionRANotDeployedLoggable(jndiName).getMessageText();
   }

   public static String getExceptionInitializeActivationSpecFailed(String clsName) {
      return ConnectorLogger.getExceptionInitializeActivationSpecFailedLoggable(clsName).getMessageText();
   }

   public static String getExceptionInstantiateClassFailed(String clsName, String msg) {
      return ConnectorLogger.getExceptionInstantiateClassFailedLoggable(clsName, msg).getMessageText();
   }

   public static String getExceptionBadValue(String methodName, String argName, String argValue) {
      return ConnectorLogger.getExceptionBadValueLoggable(methodName, argName, argValue).getMessageText();
   }

   public static String getExceptionRANotActive(String jndiName) {
      return ConnectorLogger.getExceptionRANotActiveLoggable(jndiName).getMessageText();
   }

   public static String getExceptionRANotFound(String jndiName) {
      return ConnectorLogger.getExceptionRANotFoundLoggable(jndiName).getMessageText();
   }

   public static String getExceptionNoMessageListener(String jndiName, String messageListenerType) {
      return ConnectorLogger.getExceptionNoMessageListenerLoggable(jndiName, messageListenerType).getMessageText();
   }

   public static String getExceptionMissingRequiredProperty(String missing) {
      return ConnectorLogger.getExceptionMissingRequiredPropertyLoggable(missing).getMessageText();
   }

   public static String getExceptionNoInboundRAElement() {
      return ConnectorLogger.getExceptionNoInboundRAElementLoggable().getMessageText();
   }

   public static String getExceptionNoMessageAdapterElement() {
      return ConnectorLogger.getExceptionNoMessageAdapterElementLoggable().getMessageText();
   }

   public static String getExceptionNoMessageListenerElement() {
      return ConnectorLogger.getExceptionNoMessageListenerElementLoggable().getMessageText();
   }

   public static String getExceptionAssertionError(String msg) {
      return ConnectorLogger.getExceptionAssertionErrorLoggable(msg).getMessageText();
   }

   public static String getExceptionSetDyeBitsFailedDiagCtxNotEnabled() {
      return ConnectorLogger.getExceptionSetDyeBitsFailedDiagCtxNotEnabledLoggable().getMessageText();
   }

   public static String getExceptionInvalidDyeValue(String bits) {
      return ConnectorLogger.getExceptionInvalidDyeValueLoggable(bits).getMessageText();
   }

   public static String getExceptionFailedToGetDiagCtx(String moduleName) {
      return ConnectorLogger.getExceptionFailedToGetDiagCtxLoggable(moduleName).getMessageText();
   }

   public static String getExceptionGetDyeBitsFailedDiagCtxNotEnabled() {
      return ConnectorLogger.getExceptionGetDyeBitsFailedDiagCtxNotEnabledLoggable().getMessageText();
   }

   public static String getExceptionInvalidDye(String moduleName, String msg) {
      return ConnectorLogger.getExceptionInvalidDyeLoggable(moduleName, msg).getMessageText();
   }

   public static String getExceptionCannotDeleteConnection() {
      return ConnectorLogger.getExceptionCannotDeleteConnectionLoggable().getMessageText();
   }

   public static String getExceptionEnlistmentFailed(String key, String msg) {
      return ConnectorLogger.getExceptionEnlistmentFailedLoggable(key, msg).getMessageText();
   }

   public static String getExceptionMCGetXAResourceReturnedNull() {
      return ConnectorLogger.getExceptionMCGetXAResourceReturnedNullLoggable().getMessageText();
   }

   public static String getExceptionMCGetXAResourceThrewNonResourceException(String msg) {
      return ConnectorLogger.getExceptionMCGetXAResourceThrewNonResourceExceptionLoggable(msg).getMessageText();
   }

   public static String getExceptionMCFCreateManagedConnectionReturnedNull() {
      return ConnectorLogger.getExceptionMCFCreateManagedConnectionReturnedNullLoggable().getMessageText();
   }

   public static String getExceptionInitializeForRecoveryFailed(String msg) {
      return ConnectorLogger.getExceptionInitializeForRecoveryFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionEnlistResourceIllegalType() {
      return ConnectorLogger.getExceptionEnlistResourceIllegalTypeLoggable().getMessageText();
   }

   public static String getExceptionRegisterResourceIllegalType(String clsName) {
      return ConnectorLogger.getExceptionRegisterResourceIllegalTypeLoggable(clsName).getMessageText();
   }

   public static String getExceptionXAStartInLocalTxIllegal() {
      return ConnectorLogger.getExceptionXAStartInLocalTxIllegalLoggable().getMessageText();
   }

   public static String getExceptionMCGetLocalTransactionThrewNonResourceException(String key, String msg) {
      return ConnectorLogger.getExceptionMCGetLocalTransactionThrewNonResourceExceptionLoggable(key, msg).getMessageText();
   }

   public static String getExceptionMCGetLocalTransactionReturnedNull(String key) {
      return ConnectorLogger.getExceptionMCGetLocalTransactionReturnedNullLoggable(key).getMessageText();
   }

   public static String getExceptionRegisterNonXAFailed(String msg) {
      return ConnectorLogger.getExceptionRegisterNonXAFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionCommitFailed(String msg, String stack) {
      return ConnectorLogger.getExceptionCommitFailedLoggable(msg, stack).getMessageText();
   }

   public static String getExceptionRollbackFailed(String msg, String stack) {
      return ConnectorLogger.getExceptionRollbackFailedLoggable(msg, stack).getMessageText();
   }

   public static String getExceptionCreateMCFailed(String msg) {
      return ConnectorLogger.getExceptionCreateMCFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionFailedMCSetup() {
      return ConnectorLogger.getExceptionFailedMCSetupLoggable().getMessageText();
   }

   public static String getExceptionObjectIdNull() {
      return ConnectorLogger.getExceptionObjectIdNullLoggable().getMessageText();
   }

   public static String getExceptionMCGetConnectionReturnedNull(String mcCls) {
      return ConnectorLogger.getExceptionMCGetConnectionReturnedNullLoggable(mcCls).getMessageText();
   }

   public static String getExceptionDuplicateHandle() {
      return ConnectorLogger.getExceptionDuplicateHandleLoggable().getMessageText();
   }

   public static String getExceptionTestResourceException(String msg) {
      return ConnectorLogger.getExceptionTestResourceExceptionLoggable(msg).getMessageText();
   }

   public static String getExceptionTestNonResourceException(String msg) {
      return ConnectorLogger.getExceptionTestNonResourceExceptionLoggable(msg).getMessageText();
   }

   public static String getExceptionMCFNotImplementValidatingMCF() {
      return ConnectorLogger.getExceptionMCFNotImplementValidatingMCFLoggable().getMessageText();
   }

   public static String getLazyEnlistNullMC(String pool) {
      return ConnectorLogger.getLazyEnlistNullMCLoggable(pool).getMessageText();
   }

   public static String getExceptionRAAccessDenied(String pool) {
      return ConnectorLogger.getExceptionRAAccessDeniedLoggable(pool).getMessageText();
   }

   public static String getExceptionGetConnectionFailed(String pool, String msg) {
      return ConnectorLogger.getExceptionGetConnectionFailedLoggable(pool, msg).getMessageText();
   }

   public static String getExceptionPoolDisabled(String poolName) {
      return ConnectorLogger.getExceptionPoolDisabledLoggable(poolName).getMessageText();
   }

   public static String getExceptionMCFCreateCFReturnedNull() {
      return ConnectorLogger.getExceptionMCFCreateCFReturnedNullLoggable().getMessageText();
   }

   public static String getExceptionStackTrace() {
      return ConnectorLogger.getExceptionStackTraceLoggable().getMessageText();
   }

   public static String getExceptionLocalTxNotSupported() {
      return ConnectorLogger.getExceptionLocalTxNotSupportedLoggable().getMessageText();
   }

   public static String getExceptionHandleNotSet() {
      return ConnectorLogger.getExceptionHandleNotSetLoggable().getMessageText();
   }

   public static String getExceptionOutboundPrepareFailed(String pool, String msg) {
      return ConnectorLogger.getExceptionOutboundPrepareFailedLoggable(pool, msg).getMessageText();
   }

   public static String getExceptionResumePoolFailed(String msg) {
      return ConnectorLogger.getExceptionResumePoolFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionActivatePoolFailed(String msg) {
      return ConnectorLogger.getExceptionActivatePoolFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionJndiBindFailed(String pool, String msg) {
      return ConnectorLogger.getExceptionJndiBindFailedLoggable(pool, msg).getMessageText();
   }

   public static String getExceptionDeactivateException(String poolKey, String msg, String exMsg) {
      return ConnectorLogger.getExceptionDeactivateExceptionLoggable(poolKey, msg, exMsg).getMessageText();
   }

   public static String getExceptionShutdownException(String poolKey, String msg, String exMsg) {
      return ConnectorLogger.getExceptionShutdownExceptionLoggable(poolKey, msg, exMsg).getMessageText();
   }

   public static String getExceptionCFJndiNameDuplicate(String jndiName) {
      return ConnectorLogger.getExceptionCFJndiNameDuplicateLoggable(jndiName).getMessageText();
   }

   public static String getExceptionJndiVerifyFailed(String jndiName, String msg) {
      return ConnectorLogger.getExceptionJndiVerifyFailedLoggable(jndiName, msg).getMessageText();
   }

   public static String getExceptionMCFNoImplementResourceAdapterAssociation(String mcfClassName) {
      return ConnectorLogger.getExceptionMCFNoImplementResourceAdapterAssociationLoggable(mcfClassName).getMessageText();
   }

   public static String getExceptionSetRAClassFailed(String mcfClassName, String msg) {
      return ConnectorLogger.getExceptionSetRAClassFailedLoggable(mcfClassName, msg).getMessageText();
   }

   public static String getExceptionMCFUnexpectedException(String mcfClassName, String msg) {
      return ConnectorLogger.getExceptionMCFUnexpectedExceptionLoggable(mcfClassName, msg).getMessageText();
   }

   public static String getExceptionMCFClassNotFound(String mcfClassName, String msg) {
      return ConnectorLogger.getExceptionMCFClassNotFoundLoggable(mcfClassName, msg).getMessageText();
   }

   public static String getExceptionInstantiateMCFFailed(String mcfClassName, String msg) {
      return ConnectorLogger.getExceptionInstantiateMCFFailedLoggable(mcfClassName, msg).getMessageText();
   }

   public static String getExceptionAccessMCFFailed(String mcfClassName, String msg) {
      return ConnectorLogger.getExceptionAccessMCFFailedLoggable(mcfClassName, msg).getMessageText();
   }

   public static String getExceptionGetConnectionFactoryFailedInternalError(String jndiName) {
      return ConnectorLogger.getExceptionGetConnectionFactoryFailedInternalErrorLoggable(jndiName).getMessageText();
   }

   public static String getExceptionFailedAccessOutsideApp() {
      return ConnectorLogger.getExceptionFailedAccessOutsideAppLoggable().getMessageText();
   }

   public static String getExceptionNotImplemented(String raName, String msg) {
      return ConnectorLogger.getExceptionNotImplementedLoggable(raName, msg).getMessageText();
   }

   public static String getExceptionMustBeLinkRef() {
      return ConnectorLogger.getExceptionMustBeLinkRefLoggable().getMessageText();
   }

   public static String getExceptionNeedsRAXML() {
      return ConnectorLogger.getExceptionNeedsRAXMLLoggable().getMessageText();
   }

   public static String getExceptionErrorCreatingNativeLibDir(String path) {
      return ConnectorLogger.getExceptionErrorCreatingNativeLibDirLoggable(path).getMessageText();
   }

   public static String getExceptionFileNotFoundForNativeLibDir(String displayName) {
      return ConnectorLogger.getExceptionFileNotFoundForNativeLibDirLoggable(displayName).getMessageText();
   }

   public static String getExceptionExceptionCreatingNativeLibDir(String displayName, String msg) {
      return ConnectorLogger.getExceptionExceptionCreatingNativeLibDirLoggable(displayName, msg).getMessageText();
   }

   public static String getExceptionStartPoolFailed(String msg) {
      return ConnectorLogger.getExceptionStartPoolFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionTestFrequencyNonZero() {
      return ConnectorLogger.getExceptionTestFrequencyNonZeroLoggable().getMessageText();
   }

   public static String getExceptionInvalidTestingConfig() {
      return ConnectorLogger.getExceptionInvalidTestingConfigLoggable().getMessageText();
   }

   public static String getExceptionWorkIsNull() {
      return ConnectorLogger.getExceptionWorkIsNullLoggable().getMessageText();
   }

   public static String getExceptionDoWorkNotAccepted() {
      return ConnectorLogger.getExceptionDoWorkNotAcceptedLoggable().getMessageText();
   }

   public static String getExceptionWorkManagerSuspended() {
      return ConnectorLogger.getExceptionWorkManagerSuspendedLoggable().getMessageText();
   }

   public static String getExceptionSetExecutionContextFailed(String msg) {
      return ConnectorLogger.getExceptionSetExecutionContextFailedLoggable(msg).getMessageText();
   }

   public static String getExceptionInvalidGid(String gid) {
      return ConnectorLogger.getExceptionInvalidGidLoggable(gid).getMessageText();
   }

   public static String getExceptionGidNotRegistered(String gid) {
      return ConnectorLogger.getExceptionGidNotRegisteredLoggable(gid).getMessageText();
   }

   public static String getExceptionSecurityPrincipalMapNotSupported() {
      return ConnectorLogger.getExceptionSecurityPrincipalMapNotSupportedLoggable().getMessageText();
   }

   public static String getExceptionImportedTxAlreadyActive(String gid) {
      return ConnectorLogger.getExceptionImportedTxAlreadyActiveLoggable(gid).getMessageText();
   }

   public static String getExceptionStartTimeout() {
      return ConnectorLogger.getExceptionStartTimeoutLoggable().getMessageText();
   }

   public static String getTestConnectionsOnCreateTrue() {
      return ConnectorLogger.getTestConnectionsOnCreateTrue();
   }

   public static String getTestConnectionsOnReleaseTrue() {
      return ConnectorLogger.getTestConnectionsOnReleaseTrue();
   }

   public static String getTestConnectionsOnReserveTrue() {
      return ConnectorLogger.getTestConnectionsOnReserveTrue();
   }

   public static String getFailedToForceLogRotation(String poolKey) {
      return ConnectorLogger.getFailedToForceLogRotation(poolKey);
   }

   public static String getFailedToGetCF(String key, String err) {
      return ConnectorLogger.getFailedToGetCF(key, err);
   }

   public static String getDeploySecurityBumpUpFailed(String action, String configuredUser, String currentUser) {
      return ConnectorLogger.getDeploySecurityBumpUpFailed(action, configuredUser, currentUser);
   }

   private static String spaces() {
      return indentLevel > 0 ? "".substring(0, Math.min(indentLevel * 2, MAX_SPACES)) : new String();
   }

   public static void setVerbose(boolean val) {
      verbose = true;
      String newVal = val ? "true" : "off";
      println("___Debug=" + newVal);
      verbose = val;
      setSystemProperty("Debug", newVal);
   }

   public static void setVerbose(String objName, boolean verbose) {
      boolean orig_verbose = Debug.verbose;
      String val = verbose ? "true" : "false";
      Properties props = getSystemProperties();
      props.put("Debug" + objName, val);
      System.setProperties(props);
      Debug.verbose = true;
      println("___Debug" + objName + "=" + val);
      Debug.verbose = orig_verbose;
   }

   public static boolean getVerbose(Object o) {
      String packageName = null;
      String className = null;
      String s;
      if (o instanceof String) {
         s = (String)o;
         int lastDot = s.lastIndexOf(46);
         if (lastDot == -1) {
            className = s;
         } else {
            packageName = s.substring(0, lastDot);
            className = s.substring(lastDot + 1);
         }
      } else {
         className = o.getClass().getName();
         Package thePackage = o.getClass().getPackage();
         if (thePackage != null) {
            packageName = thePackage.getName();
         }
      }

      s = "Debug" + className;
      if (packageName != null) {
         String pkg = "Debug" + packageName;
         return verbose || getSystemProperty(pkg) != null && !getSystemProperty(pkg).equalsIgnoreCase("false") || getSystemProperty(s) != null && !getSystemProperty(s).equalsIgnoreCase("false");
      } else {
         return verbose || getSystemProperty(s) != null && !getSystemProperty(s).equalsIgnoreCase("false");
      }
   }

   public static boolean getVerbose() {
      return verbose;
   }

   public static boolean isRALifecycleEnabled() {
      return ralifecycle().isDebugEnabled();
   }

   public static boolean isPoolVerboseEnabled() {
      return poolverbose().isDebugEnabled();
   }

   public static boolean isXAinEnabled() {
      return xain().isDebugEnabled();
   }

   public static boolean isXAoutEnabled() {
      return xaout().isDebugEnabled();
   }

   public static boolean isXAworkEnabled() {
      return xawork().isDebugEnabled();
   }

   public static boolean isLocalOutEnabled() {
      return localout().isDebugEnabled();
   }

   public static boolean isConnectorServiceEnabled() {
      return service().isDebugEnabled();
   }

   public static boolean isDeploymentEnabled() {
      return deployment().isDebugEnabled();
   }

   public static boolean isSecurityCtxEnabled() {
      return securityctx().isDebugEnabled();
   }

   public static boolean isParsingEnabled() {
      return parsing().isDebugEnabled();
   }

   public static boolean isPoolingEnabled() {
      return pooling().isDebugEnabled();
   }

   public static boolean isConnectionsEnabled() {
      return connections().isDebugEnabled();
   }

   public static boolean isConnEventsEnabled() {
      return connevents().isDebugEnabled();
   }

   public static boolean isWorkEnabled() {
      return work().isDebugEnabled();
   }

   public static boolean isWorkEventsEnabled() {
      return workevents().isDebugEnabled();
   }

   public static boolean isClassLoadingEnabled() {
      return classloading().isDebugEnabled();
   }

   public static void throwAssertionError(String msg) {
      String assertMsg = getExceptionAssertionError(msg);
      AssertionError assertErr = new AssertionError(assertMsg);
      logAssertionError(msg, assertErr);
      throw assertErr;
   }

   public static void throwAssertionError(String msg, Throwable ex) {
      String assertMsg = getExceptionAssertionError(msg);
      AssertionError assertErr = new AssertionError(assertMsg);
      assertErr.initCause(ex);
      logAssertionError(msg, assertErr);
      throw assertErr;
   }

   private static String getClassName(Object obj) {
      if (obj == null) {
         return null;
      } else {
         String fullName = null;
         if (obj instanceof String) {
            fullName = (String)obj;
         } else {
            fullName = obj.getClass().getName();
         }

         int dotLoc = fullName.lastIndexOf(46);
         return dotLoc > -1 ? fullName.substring(dotLoc + 1) : fullName;
      }
   }
}
