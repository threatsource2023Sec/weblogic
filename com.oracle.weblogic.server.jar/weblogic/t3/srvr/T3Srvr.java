package weblogic.t3.srvr;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.ConfigurationUtilities;
import org.glassfish.hk2.extras.ExtrasUtilities;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelServiceUtilities;
import org.glassfish.hk2.runlevel.RunLevelController.ThreadingPolicy;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.xml.api.XmlServiceUtilities;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.T3ServicesDef;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.health.HealthMonitorService;
import weblogic.health.ServerFailureNotificationHandler;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.T3Srvr2Logger;
import weblogic.kernel.T3SrvrLogger;
import weblogic.logging.LogOutputStream;
import weblogic.management.ValidationServiceImpl;
import weblogic.management.configuration.OverloadProtectionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.InteractiveConfigurationException;
import weblogic.management.provider.CommandLine;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.AdminManagedValidatorService;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.SecurityInitializationException;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PluginUtils;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.ServerResource;
import weblogic.security.service.SubjectManagerImpl;
import weblogic.security.spi.Resource;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServerStates;
import weblogic.server.ServiceFailureException;
import weblogic.server.ShutdownParametersBean;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.filelock.FileLockService;
import weblogic.utils.io.oif.JreFilterSupport;
import weblogic.utils.io.oif.WebLogicObjectInputFilter;
import weblogic.utils.io.oif.WebLogicObjectInputFilter.Scope;

@Service
public final class T3Srvr implements WebLogicServer {
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private static final DebugLogger loggerSERVER_START_STATISTICS = DebugLogger.getDebugLogger("DebugServerStartStatistics");
   private static final DebugLogger loggerSERVER_SHUTDOWN_STATISTICS = DebugLogger.getDebugLogger("DebugServerShutdownStatistics");
   private static final DebugLogger loggerSERVER_SHUTDOWN_HANDLER = DebugLogger.getDebugLogger("DebugServerShutdownHandler");
   private static final DebugCategory debugExceptions = Debug.getCategory("weblogic.slc.exceptions.verbose");
   private static final ServiceFailureException STARTUP_ABORTED = new ServiceFailureException("Startup aborted");
   static final ServiceFailureException STARTUP_TIMED_OUT = new ServiceFailureException("Startup timed out");
   public static final int RESTARTABLE_EXIT_CODE = 1;
   public static final int NON_RESTARTABLE_EXIT_CODE = -1;
   public static final int PANIC_EXIT_CODE = 65;
   private static AuthenticatedSubject kernelId;
   private static WebLogicServer singleton;
   private final LogOutputStream log = new LogOutputStream("WebLogicServer");
   private final ThreadGroup tg = new ThreadGroup("WebLogicServer");
   private AuthorizationManager am;
   private ServerLockoutManager lockoutManager;
   private ServerRuntime serverRuntimeMBean;
   private long startTime;
   private long startupTime;
   private boolean abortStartupAfterAdminState;
   private boolean started;
   private volatile int srvrState = 0;
   private int exitCode = 0;
   private boolean preventShutdownHook = Boolean.getBoolean("weblogic.system.disableShutdownHook");
   private static final boolean oldHK2flag = "true".equals(System.getProperty("weblogic.hk2.enabled", "true"));
   private int fallbackState = 0;
   private boolean isShuttingDown = false;
   private boolean lifecycleExceptionThrown = false;
   private boolean isLifecycleCallstackEnabled = Boolean.getBoolean("weblogic.system.logLifecycleCallstack");
   @Inject
   private ServiceLocator serviceLocator;
   @Inject
   private Hk2LifeCycleInitialization hk2LifecycleInitialization;
   @Inject
   private Provider runLevelController;
   @Inject
   private Provider serverLifecycleListener;
   @Inject
   private FileLockService fileLockService;
   private FailureState failureState;
   private Map shutdownDirectives = new HashMap();
   private boolean svrStarting = true;
   private int shutWaitSecs = 0;
   @Inject
   private Provider svcs;
   private static final String PAUSE = "weblogic.sleepOnStartSecs";
   private boolean isSecurityManagerOnUserCodeDisabled = Boolean.getBoolean("weblogic.security.securityManagerOnUserCodeDisabled");

   public void setShutdownWaitSecs(int waitSecs) {
      this.shutWaitSecs = waitSecs;
   }

   /** @deprecated */
   @Deprecated
   public static WebLogicServer getT3Srvr() {
      if (singleton == null) {
         throw new IllegalStateException("Calling getT3Srvr() too early. This can happen when you have a static initializer or static variable pointing to T3Srvr.getT3Srvr() and your class is getting loaded prior to T3Srvr.");
      } else {
         return singleton;
      }
   }

   private T3Srvr() {
   }

   public T3ServicesDef getT3Services() {
      return (T3ServicesDef)this.svcs.get();
   }

   public ServerLockoutManager getLockoutManager() {
      return this.lockoutManager;
   }

   public LogOutputStream getLog() {
      return this.log;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public long getStartupTime() {
      return this.startupTime;
   }

   public ThreadGroup getStartupThreadGroup() {
      return this.tg;
   }

   public synchronized void setState(int newState) {
      if (!StateChangeValidator.validate(this.srvrState, newState)) {
         String errorMessage = "trying to set illegal state, present state " + this.getState() + ", new state " + ServerStates.SERVERSTATES[newState];
         throw new IllegalStateException(errorMessage);
      } else {
         this.srvrState = newState;
         if (this.serverRuntimeMBean != null) {
            this.serverRuntimeMBean.updateRunState(newState);
         }

         T3SrvrLogger.logServerStateChange(this.getState());
      }
   }

   public synchronized void failed(String reason) {
      if (this.srvrState != 8) {
         int savedState = this.srvrState;
         if (reason != null) {
            T3SrvrLogger.logServerHealthFailed(reason);
         }

         this.setState(8);
         SrvrUtilities.logThreadDump();

         try {
            ServerMBean smb = ManagementService.getRuntimeAccess(kernelId).getServer();
            OverloadProtectionMBean olp = smb.getOverloadProtection();
            if (!smb.getAutoKillIfFailed() && !"force-shutdown".equals(olp.getFailureAction())) {
               if (isServerBeyondAdminState(savedState) && "admin-state".equals(olp.getFailureAction())) {
                  T3SrvrLogger.logSuspendingOnFailure();
                  this.suspend(false, true);
               }
            } else {
               T3SrvrLogger.logShuttingDownOnFailure();
               this.exitCode = -1;
               this.shutdown(false, true);
            }
         } catch (ServerLifecycleException var5) {
            var5.printStackTrace();
         }

      }
   }

   public void failedForceShutdown(String reason) {
      if (reason != null) {
         T3SrvrLogger.logServerHealthFailed(reason);
      }

      this.setState(8);
      SrvrUtilities.logThreadDump();
      T3SrvrLogger.logShuttingDownOnFailure();
      this.exitCode = -1;

      try {
         this.shutdown(false, true);
      } catch (ServerLifecycleException var3) {
         this.exitImmediately(var3);
      }

   }

   public boolean isShutdownDueToFailure() {
      return this.exitCode == -1;
   }

   public boolean isStarted() {
      return this.started;
   }

   public boolean isShuttingDown() {
      return this.isShuttingDown;
   }

   public synchronized void setFailedStateFromCallback(Throwable th, boolean logStackTrace) {
      this.failureState = new FailureState(th, logStackTrace);
   }

   public synchronized void setFailedState(Throwable th, boolean logStackTrace) {
      this.setFailedState(th, logStackTrace, false);
   }

   public void setFailedState(Throwable th, boolean logStackTrace, boolean fromCallback) {
      if (th != STARTUP_ABORTED) {
         if (th == STARTUP_TIMED_OUT) {
            if (this.fallbackState != 17) {
               this.exitCode = -1;
            }

            SrvrUtilities.logThreadDump();
         }

         if (th == null || !logStackTrace && !debugExceptions.isEnabled()) {
            T3SrvrLogger.logServerSubsystemFailed(th.getMessage());
         } else {
            T3SrvrLogger.logServerSubsystemFailedWithTrace(th);
         }

         this.setState(8);
         if (!fromCallback) {
            try {
               if (this.fallbackState == 17) {
                  if (th != null) {
                     this.failureState = new FailureState(th, logStackTrace);
                  }

                  T3SrvrLogger.logSuspendingOnFailure();
                  this.suspend(false, true);
                  return;
               }

               if (this.fallbackState == 0) {
                  this.exitCode = -1;
                  T3SrvrLogger.logShuttingDownOnFailure();
                  this.shutdown(false, true);
               }
            } catch (ServerLifecycleException var5) {
               var5.printStackTrace();
            }

         }
      }
   }

   public void exitImmediately(Throwable th) {
      try {
         signalCriticalFailure("There is a panic condition in the server. The server is configured to exit on panic", th);
         this.setPreventShutdownHook();
      } finally {
         System.exit(65);
      }

   }

   public void haltImmediately() {
      Runtime.getRuntime().halt(65);
   }

   public boolean isLifecycleExceptionThrown() {
      return this.lifecycleExceptionThrown;
   }

   public void setLifecycleExceptionThrown(boolean lifecycleExceptionThrown) {
      this.lifecycleExceptionThrown = lifecycleExceptionThrown;
   }

   public int getRunState() {
      return this.srvrState;
   }

   public String getState() {
      return ServerStates.SERVERSTATES[this.srvrState];
   }

   public int getStableState() {
      int state = this.srvrState;
      switch (this.srvrState) {
         case 1:
            try {
               String startupMode = this.getStartupMode();
               if (!this.abortStartupAfterAdminState && (startupMode == null || !"ADMIN".equalsIgnoreCase(startupMode))) {
                  state = 2;
               } else {
                  state = 17;
               }
            } catch (ServerLifecycleException var3) {
               state = 9;
            }
            break;
         case 2:
         case 6:
            state = 2;
         case 3:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            break;
         case 4:
         case 5:
         case 17:
            if (this.isShuttingDown()) {
               state = 9;
            } else {
               state = 17;
            }
            break;
         case 7:
         case 11:
         case 18:
            state = 9;
      }

      return state;
   }

   private void initializeHK2ServerUtilities() {
      ExtrasUtilities.enableDefaultInterceptorServiceImplementation(this.serviceLocator);
      RunLevelServiceUtilities.enableRunLevelService(this.serviceLocator);
      ServiceLocatorUtilities.addClasses(this.serviceLocator, new Class[]{AdminManagedValidatorService.class});
      XmlServiceUtilities.enableDomXmlService(this.serviceLocator);
      ConfigurationUtilities.enableConfigurationSystem(this.serviceLocator);
   }

   public T3ServerFuture run(String[] args) {
      pauseBeforeStartup();
      this.doStartupChecks();
      this.setDefaultUncaughtExceptionHandler();

      try {
         WebLogicObjectInputFilter.initialize();
         if (WebLogicObjectInputFilter.isFilterLoggingEnabled()) {
            if (!WebLogicObjectInputFilter.isFilterAvailable()) {
               T3Srvr2Logger.logFilterNotEnabled();
            } else {
               T3Srvr2Logger.logFilterMode(WebLogicObjectInputFilter.getFilterMode());
               T3Srvr2Logger.logFilterScope(WebLogicObjectInputFilter.getFilterScope());
               WebLogicObjectInputFilter.Scope[] scopes = new WebLogicObjectInputFilter.Scope[]{Scope.WEBLOGIC, Scope.GLOBAL};
               WebLogicObjectInputFilter.Scope[] var3 = scopes;
               int var4 = scopes.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  WebLogicObjectInputFilter.Scope scope = var3[var5];
                  if (WebLogicObjectInputFilter.isGlobalFilterScope() || scope != Scope.GLOBAL) {
                     ArrayList limits = WebLogicObjectInputFilter.getFilterLimits(scope);
                     ArrayList packages = WebLogicObjectInputFilter.getFilterBlacklistPackages(scope);
                     ArrayList classes = WebLogicObjectInputFilter.getFilterBlacklistClasses(scope);
                     ArrayList whitelists = WebLogicObjectInputFilter.getFilterWhitelists(scope);
                     Iterator var11 = limits.iterator();

                     String s;
                     while(var11.hasNext()) {
                        s = (String)var11.next();
                        T3Srvr2Logger.logFilterLimit(scope.toString(), s);
                     }

                     var11 = packages.iterator();

                     while(var11.hasNext()) {
                        s = (String)var11.next();
                        T3Srvr2Logger.logFilterBlackListPackage(scope.toString(), s);
                     }

                     var11 = classes.iterator();

                     while(var11.hasNext()) {
                        s = (String)var11.next();
                        T3Srvr2Logger.logFilterBlackListClass(scope.toString(), s);
                     }

                     var11 = whitelists.iterator();

                     while(var11.hasNext()) {
                        s = (String)var11.next();
                        T3Srvr2Logger.logFilterWhiteList(scope.toString(), s);
                     }
                  }
               }
            }
         }

         if (!KernelStatus.isEmbedded()) {
            initSubjectManager();
         }

         SecurityServiceManager.initJava2Security();
         this.initializeHK2ServerUtilities();
         initKernelId();
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            ServiceLocatorUtilities.addClasses(this.serviceLocator, new Class[]{ValidationServiceImpl.class});
            SecurityManager bootsm = new SecurityManager() {
               public void checkRead(String file) {
               }
            };
            System.setSecurityManager(bootsm);
            if (!this.isSecurityManagerOnUserCodeDisabled) {
               SecurityServiceManager.setSecurityManagersForBoot(kernelId, bootsm, sm);
            }
         }

         initSingleton(this);
         SecurityServiceManager.pushSubject(kernelId, kernelId);
         this.addShutdownHook();
         this.startup();
         if (sm != null) {
            if (!this.isSecurityManagerOnUserCodeDisabled) {
               SecurityServiceManager.clearSecurityManagersForBoot(kernelId);
            }

            System.setSecurityManager(sm);
         }
      } catch (ServerLifecycleException var13) {
         this.preventShutdownHook = true;
         return new T3ServerFuture(-1);
      } catch (Throwable var14) {
         this.preventShutdownHook = true;
         handleFatalInitializationException(var14);
         this.setState(18);
         return new T3ServerFuture(-1);
      }

      return new T3ServerFuture(this);
   }

   private void doStartupChecks() {
      if ((!JreFilterSupport.isEnabled() || !JreFilterSupport.isGlobalFilterSupported()) && JreFilterSupport.hasKnownSupportedVersion()) {
         this.recommendHigherJre();
      }

   }

   private void recommendHigherJre() {
      T3SrvrLogger.logJreLacksJep290(JreFilterSupport.getCurrentVersion(), JreFilterSupport.getLowestSupportedVersionForGlobalFilter());
   }

   private void startup() throws ServerLifecycleException {
      long startBeginTime = ManagementFactory.getRuntimeMXBean().getStartTime();
      VersionInfoFactory.initialize(true);
      if (!oldHK2flag) {
         throw new ServerLifecycleException("Booting WebLogic without hk2 is no longer supported");
      } else {
         this.startTime = startBeginTime;
         boolean var19 = false;

         label179: {
            try {
               var19 = true;
               if (debugSLCWLDF.isDebugEnabled()) {
                  ServiceLocatorUtilities.addClasses(this.serviceLocator, new Class[]{Hk2ServerServiceDebugger.class});
               }

               RunLevelFuture runLevelFuture = this.hk2LifecycleInitialization.progressServer();

               try {
                  runLevelFuture.get();
               } catch (ExecutionException var24) {
                  Throwable reason = var24.getCause();
                  if (reason != null) {
                     throw reason;
                  }

                  throw var24;
               }

               String startupMode = this.getStartupMode();
               if (!this.abortStartupAfterAdminState) {
                  if (startupMode == null) {
                     var19 = false;
                     break label179;
                  }

                  if (!"ADMIN".equalsIgnoreCase(startupMode)) {
                     var19 = false;
                     break label179;
                  }
               }

               ServerServiceProgressMeter sspm = (ServerServiceProgressMeter)LocatorUtilities.getService(ServerServiceProgressMeter.class);
               sspm.meter.finished();
               var19 = false;
               break label179;
            } catch (Throwable var25) {
               FailureState localFailureState;
               synchronized(this) {
                  localFailureState = this.failureState;
               }

               if (localFailureState == null) {
                  throw new ServerLifecycleException(var25);
               }

               this.setFailedState(localFailureState.th, localFailureState.stack);
               var19 = false;
            } finally {
               if (var19) {
                  synchronized(this) {
                     this.failureState = null;
                  }
               }
            }

            synchronized(this) {
               this.failureState = null;
               return;
            }
         }

         synchronized(this) {
            this.failureState = null;
         }

         this.startTime = System.currentTimeMillis();
      }
   }

   private long getElapsedStartTime(long startBeginTime) throws ServerLifecycleException {
      long endTime = System.currentTimeMillis();
      long elapsed = endTime - startBeginTime;
      String timeLog = System.getProperty("launch.time.log");
      if (timeLog != null) {
         System.out.println("Startup completed in " + elapsed + "ms.");

         try {
            String logEntry = elapsed + "\n";
            File log = new File(timeLog);
            if (!log.exists() || log.length() == 0L) {
               logEntry = System.getProperty("java.vm.name") + " " + System.getProperty("java.runtime.version") + " " + (new Date()).toString() + System.getProperty("line.separator") + logEntry;
            }

            FileOutputStream out = new FileOutputStream(log, true);
            out.write(logEntry.getBytes());
            out.close();
            System.out.println("Logged to " + log.getAbsolutePath());
         } catch (Exception var11) {
            System.out.println("Failed to log: " + var11);
         }

         this.shutdown(false, true);
      }

      return elapsed;
   }

   public void logStartupStatistics() {
      this.logStatistics(loggerSERVER_START_STATISTICS);
   }

   private void logShutdownStatistics() {
      this.logStatistics(loggerSERVER_SHUTDOWN_STATISTICS);
   }

   private void logStatistics(DebugLogger logger) {
      if (logger.isDebugEnabled()) {
         MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
         memoryBean.gc();
         MemoryUsage heap = memoryBean.getHeapMemoryUsage();
         MemoryUsage nonHeap = null;

         try {
            nonHeap = memoryBean.getNonHeapMemoryUsage();
         } catch (Exception var14) {
         }

         List poolBeans = ManagementFactory.getMemoryPoolMXBeans();
         long totalHeapPeakUsed = 0L;
         long totalNonheapPeakUsed = 0L;
         Iterator var10 = poolBeans.iterator();

         while(var10.hasNext()) {
            MemoryPoolMXBean pool = (MemoryPoolMXBean)var10.next();
            MemoryUsage currentUsage = pool.getUsage();
            MemoryUsage peakUsage = pool.getPeakUsage();
            logger.debug("MemoryPool(name,type,current,peak):" + pool.getName() + "," + pool.getType() + "," + currentUsage.getUsed() + "," + peakUsage.getUsed());
            if (pool.getType() == MemoryType.HEAP) {
               totalHeapPeakUsed += peakUsage.getUsed();
            } else {
               totalNonheapPeakUsed += peakUsage.getUsed();
            }
         }

         logger.debug("Statistics(time,heap,peakheap,nonheap,peaknonheap):" + this.startupTime + "," + (heap != null ? "" + heap.getUsed() : "NA") + "," + totalHeapPeakUsed + "," + (nonHeap != null ? "" + nonHeap.getUsed() : "NA") + "," + totalNonheapPeakUsed);
      }
   }

   public String getStartupMode() throws ServerLifecycleException {
      String startupMode = ManagementService.getRuntimeAccess(kernelId).getServer().getStartupMode();

      try {
         if ("STANDBY".equalsIgnoreCase(startupMode)) {
            ensureAdminChannel();
         }

         return startupMode;
      } catch (ServerLifecycleException var3) {
         this.setFailedState(var3, false);
         throw var3;
      }
   }

   private static void ensureAdminChannel() throws ServerLifecycleException {
      if (!isAdminChannelEnabled()) {
         T3SrvrTextTextFormatter fmt = new T3SrvrTextTextFormatter();
         throw new ServerLifecycleException(fmt.getStartupWithoutAdminChannel());
      }
   }

   private static boolean isAdminChannelEnabled() {
      return ChannelHelper.isLocalAdminChannelEnabled();
   }

   private static void pauseBeforeStartup() {
      try {
         int sleepTimeSeconds = 30;
         if (System.getProperty("weblogic.sleepOnStartSecs") != null) {
            try {
               sleepTimeSeconds = Integer.parseInt(System.getProperty("weblogic.sleepOnStartSecs"));
            } catch (Exception var2) {
               System.out.println("Server Failed parse time, using default of: '" + sleepTimeSeconds + "'");
            }

            System.out.println("Server Sleeping for: '" + sleepTimeSeconds + "' seconds");
            Thread.sleep((long)(sleepTimeSeconds * 1000));
            System.out.println("Server Waking");
         }
      } catch (AccessControlException var3) {
      } catch (Exception var4) {
         System.out.println("Server Failed to sleep");
      }

   }

   public static void initSubjectManager() {
      Object customSubjectManager = PluginUtils.createPlugin(SubjectManager.class, CommandLine.getCommandLine().getSecurityFWSubjectManagerClassNameProp());
      if (customSubjectManager != null) {
         SubjectManager.setSubjectManager((SubjectManager)customSubjectManager);
      } else {
         SubjectManager.setSubjectManager(new SubjectManagerImpl());
      }

   }

   private static void initKernelId() {
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static void initSingleton(WebLogicServer me) {
      singleton = me;
      HealthMonitorService.setServerFailureNotificationHandler(new ServerFailureNotificationHandler() {
         public void failed(String message) {
            T3Srvr.getT3Srvr().failed(message);
         }

         public void failedForceShutdown(String message) {
            T3Srvr.getT3Srvr().failedForceShutdown(message);
         }

         public void exitImmediately(Throwable th) {
            T3Srvr.getT3Srvr().exitImmediately(th);
         }

         public void haltImmediately() {
            T3Srvr.getT3Srvr().haltImmediately();
         }
      });
   }

   private static void handleFatalInitializationException(Throwable t) {
      String message = t.getMessage();
      Throwable cause = t.getCause();
      if (!(t instanceof Error) && !(cause instanceof InteractiveConfigurationException) && !(cause instanceof SecurityInitializationException) && !(cause instanceof AccessControlException) && !(t instanceof SecurityInitializationException)) {
         StringBuffer messageBuffer;
         if (message == null) {
            messageBuffer = new StringBuffer();
         } else {
            messageBuffer = new StringBuffer(message);
         }

         while(cause != null) {
            String causeMessage = cause.getMessage();
            if (message != null) {
               if (causeMessage != null && message.indexOf(causeMessage) == -1) {
                  messageBuffer.append(causeMessage);
               }
            } else if (causeMessage != null) {
               messageBuffer.append(causeMessage);
            }

            t = cause;
            cause = cause.getCause();
         }

         try {
            if (Kernel.isInitialized()) {
               T3SrvrLogger.logNotInitialized(messageBuffer.toString());
            }

            signalCriticalFailure(messageBuffer.toString(), t);
         } catch (Throwable var5) {
            System.err.println("Unable to dump log: '" + var5.getMessage() + "'");
         }

      } else {
         signalCriticalFailure(message, (Throwable)null);
      }
   }

   private static void signalCriticalFailure(String s, Throwable t) {
      System.out.flush();
      System.err.println("***************************************************************************");
      System.err.println("The WebLogic Server encountered a critical failure");
      if (t != null) {
         if (!(t instanceof RuntimeException) && !(t instanceof Error) && !(t instanceof ServiceFailureException)) {
            System.err.println("Exception raised: '" + t + "'");
         } else {
            t.printStackTrace();
         }
      }

      if (s != null && s.length() > 0) {
         System.err.println("Reason: " + s);
      }

      if (t instanceof AccessControlException) {
         System.err.println("Check you have both java.security.manager and java.security.policy defined");
      }

      System.err.println("***************************************************************************");
   }

   public void initializeServerRuntime(ServerRuntime serverRuntime) {
      this.serverRuntimeMBean = serverRuntime;
      this.setState(1);
   }

   public void resume() throws ServerLifecycleException {
      long timeout = (long)(ManagementService.getRuntimeAccess(kernelId).getServer().getStartupTimeout() * 1000);
      this.checkPrivileges("unlock");
      this.resume(timeout);
   }

   private void resume(long timeout) throws ServerLifecycleException {
      boolean exceptionHandledElsewhere = false;

      try {
         if (this.srvrState == 2) {
            return;
         }

         int prevState = this.srvrState;
         this.setState(6);
         this.failureState = null;
         LifecycleListener.setSvrStarting(true);
         ServerStartupTimer.startTimeBomb(SrvrUtilities.getStartupTimeout(), SrvrUtilities.getStartupTimeoutNumOfThreadDump(), SrvrUtilities.getStartupTimeoutThreadDumpInterval());
         this.hk2LifecycleInitialization.setThreadPolicy((RunLevelController)this.runLevelController.get());
         ((RunLevelController)this.runLevelController.get()).proceedTo(20);
         if (((RunLevelController)this.runLevelController.get()).getCurrentRunLevel() != 20 && this.failureState != null) {
            exceptionHandledElsewhere = true;
            throw this.failureState.th;
         }

         this.startTime = System.currentTimeMillis();
         if (prevState == 8) {
            this.updateResumeToRunningServerState();
         }
      } catch (ServiceFailureException var10) {
         if (this.failureState != null) {
            if (!exceptionHandledElsewhere) {
               this.setFailedState(this.failureState.th, this.failureState.stack);
            }

            throw new ServerLifecycleException(this.failureState.th);
         }

         this.setFailedState(var10, false);
         throw new ServerLifecycleException(var10);
      } catch (IllegalStateException var11) {
         throw new ServerLifecycleException(var11);
      } catch (Throwable var12) {
         if (this.failureState != null) {
            if (!exceptionHandledElsewhere) {
               this.setFailedState(this.failureState.th, this.failureState.stack);
            }

            throw new ServerLifecycleException(this.failureState.th);
         }

         this.setFailedState(var12, true);
         throw new ServerLifecycleException(var12);
      } finally {
         this.failureState = null;
      }

   }

   private void updateResumeToRunningServerState() {
      this.setState(2);
      SrvrUtilities.invokeRunningStateListeners();
      this.fallbackState = -1;
   }

   void waitForDeath() {
      try {
         this.waitForDeath(Long.MAX_VALUE);
      } catch (TimeoutException var2) {
         throw new RuntimeException(var2);
      }
   }

   void waitForDeath(long waitTime) throws TimeoutException {
      do {
         if (this.isConsoleInputEnabled()) {
            T3SrvrConsole console = new T3SrvrConsole();
            console.processCommands();
         } else {
            synchronized(this) {
               while(waitTime > 0L && this.isWaitingToDie()) {
                  try {
                     long elapsedTime = System.currentTimeMillis();
                     this.wait(waitTime);
                     elapsedTime = System.currentTimeMillis() - elapsedTime;
                     waitTime -= elapsedTime;
                  } catch (InterruptedException var7) {
                  }
               }

               if (this.isWaitingToDie() && waitTime <= 0L) {
                  throw new TimeoutException();
               }
            }
         }
      } while(this.isWaitingToDie());

   }

   private boolean isWaitingToDie() {
      return this.srvrState == 2 || this.srvrState == 3 || this.srvrState == 17 || this.srvrState == 0 || this.srvrState == 1;
   }

   public void gracefulShutdown(boolean ignoreSessions) throws ServerLifecycleException {
      this.gracefulShutdown(ignoreSessions, false);
   }

   public void gracefulShutdown(boolean ignoreSessions, boolean waitForAllSessions) throws ServerLifecycleException {
      this.checkShutdownPrivileges();
      this.shutdown(true, ignoreSessions, waitForAllSessions);
   }

   private void waitForExistingProcedure(boolean hard) {
      RunLevelFuture future = ((RunLevelController)this.runLevelController.get()).getCurrentProceeding();
      if (future == null) {
         if (debugSLCWLDF.isDebugEnabled()) {
            debugSLCWLDF.debug("No current proceeding to wait for");
         }

      } else {
         if (hard) {
            if (debugSLCWLDF.isDebugEnabled()) {
               debugSLCWLDF.debug("Hard cancelling future " + future);
            }

            future.cancel(true);
         }

         try {
            if (debugSLCWLDF.isDebugEnabled()) {
               debugSLCWLDF.debug("Waiting for " + future + " to complete");
            }

            future.get();
         } catch (ExecutionException var8) {
         } catch (InterruptedException var9) {
         } finally {
            if (debugSLCWLDF.isDebugEnabled()) {
               debugSLCWLDF.debug("Finished waiting for " + future);
            }

         }

      }
   }

   public void forceShutdown() throws ServerLifecycleException {
      this.checkShutdownPrivileges();
      this.shutdown(false, true);
   }

   private void addShutdownHook() {
      final boolean dumpThreadAtShutdownHandler = loggerSERVER_SHUTDOWN_HANDLER.isDebugEnabled();
      Runtime.getRuntime().addShutdownHook(new Thread() {
         public void run() {
            if (T3Srvr.this.setPreventShutdownHook()) {
               T3SrvrLogger.logShutdownHookCalled();
               if (dumpThreadAtShutdownHandler) {
                  SrvrUtilities.logThreadDump();
               }

               try {
                  T3Srvr.this.forceShutdown();
               } catch (ServerLifecycleException var2) {
                  var2.printStackTrace();
                  T3Srvr.this.fileLockService.removeLockFiles();
                  Runtime.getRuntime().halt(-1);
               }

            }
         }
      });
   }

   private void setDefaultUncaughtExceptionHandler() {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
         private boolean OOM_caught = false;

         public void uncaughtException(Thread t, Throwable e) {
            if (T3Srvr.debugSLCWLDF.isDebugEnabled()) {
               T3Srvr.debugSLCWLDF.debug("Uncaught Exception Handler for Thread = " + t + " Exception Thrown = " + e + " Message = " + e.getMessage());
            }

            if (e instanceof OutOfMemoryError) {
               synchronized(this) {
                  if (!this.OOM_caught) {
                     this.OOM_caught = true;
                  }
               }

               HealthMonitorService.panic(e);
            }

         }
      });
   }

   public synchronized boolean setPreventShutdownHook() {
      if (this.preventShutdownHook) {
         return false;
      } else {
         this.preventShutdownHook = true;
         return true;
      }
   }

   public void requestShutdownFromConsole() throws SecurityException {
      this.setShutdownWaitSecs(0);

      try {
         this.forceShutdown();
      } catch (ServerLifecycleException var2) {
         throw new SecurityException(var2.toString());
      }
   }

   public void gracefulSuspend(boolean ignoreSessions) throws ServerLifecycleException {
      this.checkPrivileges("lock");
      this.suspend(true, ignoreSessions);
   }

   public void forceSuspend() throws ServerLifecycleException {
      this.checkPrivileges("lock");
      this.suspend(false, true);
   }

   private void populateShutdownDirectives(boolean graceful, boolean ignoreSessions) {
      this.shutdownDirectives.put("Graceful", new Boolean(graceful));
      this.shutdownDirectives.put("ignore.sessions", ignoreSessions);
      ShutdownParametersBean.getInstance().setShutdownDirectives(this.shutdownDirectives);
   }

   private void populateShutdownDirectives(boolean graceful, boolean ignoreSessions, boolean waitForAllSessions) {
      this.shutdownDirectives.put("Graceful", new Boolean(graceful));
      this.shutdownDirectives.put("ignore.sessions", ignoreSessions);
      this.shutdownDirectives.put("WaitForAllSessions", waitForAllSessions);
      ShutdownParametersBean.getInstance().setShutdownDirectives(this.shutdownDirectives);
   }

   private void shutdown(boolean graceful, boolean ignoreSessions) throws ServerLifecycleException {
      this.shutdown(graceful, ignoreSessions, false);
   }

   private void shutdown(boolean graceful, boolean ignoreSessions, boolean waitForAllSessions) throws ServerLifecycleException {
      try {
         this.startupTime = this.getElapsedStartTime(this.getStartTime());
         this.logShutdownStatistics();
         LifecycleListener.setSvrStarting(false);
         ((ServerLifecycleListener)this.serverLifecycleListener.get()).setGracefulShutdown(graceful);
         if (!graceful) {
            this.prepareForForceShutdown();
         }

         this.populateShutdownDirectives(graceful, ignoreSessions, waitForAllSessions);
         if (this.shutWaitSecs > 0) {
            this.setState(11);
            if (this.isShutdownCancelled()) {
               return;
            }
         }

         this.isShuttingDown = true;
         if (isServerBeyondAdminState(this.srvrState)) {
            this.suspend(graceful, ignoreSessions);
         }

         this.setFallbackState(-1);
         if (graceful) {
            if (ServerLifeCycleTimerThread.isTimeBombStarted()) {
               throw new ServerLifecycleException("forceful suspend/shutdown has been started during graceful shutdown process");
            }

            this.setState(7);
         } else {
            this.setState(18);
         }

         this.waitForExistingProcedure(!graceful);
         ((RunLevelController)this.runLevelController.get()).setThreadingPolicy(ThreadingPolicy.USE_NO_THREADS);
         ((RunLevelController)this.runLevelController.get()).proceedTo(0);
         ((ServerLifecycleListener)this.serverLifecycleListener.get()).checkGracefulShutdownFailure();
      } catch (IllegalStateException var5) {
         throw new ServerLifecycleException(var5);
      }
   }

   private void prepareForForceShutdown() {
      this.setPreventShutdownHook();
      ServerLifeCycleTimerThread.startTimeBomb();
   }

   private void suspend(boolean graceful, boolean ignoreSessions) throws ServerLifecycleException {
      try {
         LifecycleListener.setSvrStarting(false);
         this.populateShutdownDirectives(graceful, ignoreSessions);
         if (this.srvrState != 17) {
            if (graceful) {
               this.setState(4);
            } else {
               synchronized(this) {
                  if (isServerBeyondAdminState(this.srvrState)) {
                     this.setState(5);
                  } else if (isServerBelowAdminState(this.srvrState)) {
                     return;
                  }
               }
            }

            this.waitForExistingProcedure(!graceful);
            if (graceful && ServerLifeCycleTimerThread.isTimeBombStarted()) {
               throw new ServerLifecycleException("forceful suspend/shutdown has been started during graceful shutdown process");
            } else {
               if (((RunLevelController)this.runLevelController.get()).getCurrentRunLevel() > 15) {
                  ((RunLevelController)this.runLevelController.get()).setThreadingPolicy(ThreadingPolicy.USE_NO_THREADS);
                  ((RunLevelController)this.runLevelController.get()).proceedTo(15);
                  ((ServerLifecycleListener)this.serverLifecycleListener.get()).checkGracefulShutdownFailure();
               } else {
                  ((ServerLifecycleListener)this.serverLifecycleListener.get()).onAdminLifecycleShutdown((ChangeableRunLevelFuture)null);
               }

            }
         }
      } catch (IllegalStateException var6) {
         throw new ServerLifecycleException(var6);
      }
   }

   private static synchronized boolean isServerBeyondAdminState(int currentState) {
      return currentState == 2 || currentState == 6 || currentState == 4;
   }

   private static boolean isServerBelowAdminState(int currentState) {
      return currentState == 17 || currentState == 7 || currentState == 18;
   }

   public void setLockoutManager() {
      this.setLockoutManager(new ServerLockoutManager());
   }

   void setLockoutManager(ServerLockoutManager lockoutManager) {
      if (this.lockoutManager == null) {
         this.lockoutManager = lockoutManager;
      }

   }

   public String cancelShutdown() {
      this.checkShutdownPrivileges();
      synchronized(this) {
         if (this.srvrState != 10) {
            if (this.srvrState == 11) {
               return T3SrvrLogger.logNoCancelShutdownTooLate();
            }

            return T3SrvrLogger.logNoCancelShutdownAlreadyNotShutting();
         }

         this.srvrState = 2;
      }

      this.setShutdownWaitSecs(0);
      T3SrvrLogger.logCancelShutdownInitiated();
      synchronized(this) {
         this.notifyAll();
      }

      return T3SrvrLogger.logCancelShutdownHappened();
   }

   private boolean isShutdownCancelled() {
      if (this.shutWaitSecs > 0) {
         T3SrvrLogger.logWaitingForShutdown(this.shutWaitSecs);
         synchronized(this) {
            this.srvrState = 10;
         }

         synchronized(this) {
            try {
               int waitTime = this.shutWaitSecs;
               this.setShutdownWaitSecs(0);
               this.wait((long)(waitTime * 1000));
            } catch (InterruptedException var4) {
            }

            if (this.srvrState == 10) {
               this.srvrState = 11;
            }
         }
      }

      if (this.srvrState == 11) {
         T3SrvrLogger.logNotWaitingForShutdown();
         return false;
      } else {
         return true;
      }
   }

   private void checkShutdownPrivileges() throws SecurityException {
      if (ManagementService.isRuntimeAccessInitialized()) {
         if (ManagementService.getRuntimeAccess(kernelId) != null && ManagementService.getRuntimeAccess(kernelId).getServer() != null && ManagementService.getRuntimeAccess(kernelId).getServer().isConsoleInputEnabled()) {
            T3SrvrLogger.logShutdownFromCommandLineOnly();
            throw new SecurityException("shutdown from command line only when weblogic.ConsoleInputEnabled=true");
         } else {
            this.checkPrivileges("shutdown");
         }
      }
   }

   private void checkPrivileges(String operation) throws SecurityException {
      String subjectName = null;
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      if (subject != null && (subjectName = SubjectUtils.getUsername(subject)) != null && subjectName.trim().length() != 0) {
         Throwable t = null;
         if (this.isLifecycleCallstackEnabled) {
            t = new Throwable("for dumping lifecycle callstack");
         }

         T3SrvrLogger.logOperationRequested(operation, subjectName, t);
         if (ManagementService.isRuntimeAccessInitialized()) {
            Resource resource = new ServerResource((String)null, ManagementService.getRuntimeAccess(kernelId).getServerName(), operation);
            if (this.am != null && !this.am.isAccessAllowed(subject, resource, new ResourceIDDContextWrapper(true))) {
               throw new SecurityException("User: '" + subjectName + "' does not have permission to " + operation + " server");
            }
         }
      } else {
         throw new SecurityException("Cannot " + operation + " the server, the request was from a nameless user (Principal)");
      }
   }

   public synchronized void abortStartupAfterAdminState() throws ServerLifecycleException {
      if (this.srvrState != 2 && this.srvrState != 6) {
         this.abortStartupAfterAdminState = true;
      } else {
         throw new ServerLifecycleException("cannot abort startup in admin state as current state is " + this.getState());
      }
   }

   public boolean isAbortStartupAfterAdminState() {
      return this.abortStartupAfterAdminState;
   }

   boolean isConsoleInputEnabled() {
      boolean consoleInputEnabled = false;
      if (ManagementService.getRuntimeAccess(kernelId) == null) {
         return consoleInputEnabled;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         return server != null ? server.isConsoleInputEnabled() : consoleInputEnabled;
      }
   }

   public boolean isSvrStarting() {
      return this.svrStarting;
   }

   public void setSvrStarting(boolean svrStarting) {
      this.svrStarting = svrStarting;
   }

   public void setStartupTime(long startupTime) {
      this.startupTime = startupTime;
   }

   public void setStarted(boolean started) {
      this.started = started;
   }

   public synchronized void setFallbackState(int fallbackState) {
      this.fallbackState = fallbackState;
   }

   public String toString() {
      return "T3Srvr(" + System.identityHashCode(this) + ")";
   }

   private static class FailureState {
      private final Throwable th;
      private final boolean stack;

      private FailureState(Throwable th, boolean stack) {
         this.th = th;
         this.stack = stack;
      }

      // $FF: synthetic method
      FailureState(Throwable x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }
}
