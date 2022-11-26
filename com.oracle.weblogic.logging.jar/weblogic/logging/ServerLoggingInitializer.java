package weblogic.logging;

import com.bea.logging.LogFileRotator;
import com.bea.logging.LogMessageFormatter;
import com.bea.logging.MsgIdPrefixConverter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.image.ImageManager;
import weblogic.kernel.KernelLogManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.logging.ServerLogRuntimeMBeanGenerator;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerLogRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;

public class ServerLoggingInitializer {
   private static final String ODL_CONFIG_CLASS_NAME = "oracle.core.ojdl.weblogic.ODLConfiguration";
   private static final boolean DEBUG = false;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static JDKLoggerFactory loggerFactory;
   private static Set userConfiguredPlatformLoggers = new HashSet();
   private static Set ootbConfiguredPlatformLoggers = new HashSet();

   public static JDKLoggerFactory getLoggerFactory() {
      return loggerFactory;
   }

   public static void initializeServerLogging() throws ManagementException {
      RuntimeAccess runtimeAccess = getRuntimeAccess();
      boolean logFormatCompatibilityEnabled = runtimeAccess.getDomain().isLogFormatCompatibilityEnabled();
      LogMessageFormatter.setLogFormatCompatibilityEnabled(logFormatCompatibilityEnabled);
      final ServerMBean config = runtimeAccess.getServer();
      if (config != null) {
         LogFileRotator.getLogRotationLogger();
         loggerFactory = JDKLoggerFactory.getJDKLoggerFactory(config.getLog());
         Logger rootLogger = loggerFactory.createAndInitializeServerLogger(config);
         KernelLogManager.setLogger(rootLogger);
         Properties ootbLoggerLevels = new Properties();

         try {
            ootbLoggerLevels.load(ServerLoggingInitializer.class.getResourceAsStream("loglevel.properties"));
            SortedMap sorted = sortLoggingConfiguration(ootbLoggerLevels);
            Iterator var6 = sorted.keySet().iterator();

            while(var6.hasNext()) {
               String loggerName = (String)var6.next();
               loggerName = loggerName.trim();
               String levelName = (String)sorted.get(loggerName);
               Level level = Level.parse(levelName);
               Logger logger = Logger.getLogger(loggerName);
               if (logger.getLevel() == null) {
                  logger.setLevel(level);
                  ootbConfiguredPlatformLoggers.add(logger);
               }
            }
         } catch (Exception var11) {
            throw new ManagementException(var11);
         }

         initializePlatformLoggers(config.getLog().getPlatformLoggerLevels());
         config.getLog().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
               if ("PlatformLoggerLevels".equals(evt.getPropertyName())) {
                  ServerLoggingInitializer.initializePlatformLoggers(config.getLog().getPlatformLoggerLevels());
               }

            }
         });
         config.getLog().addPropertyChangeListener(StdoutSeverityListener.getStdoutSeverityListener(config));
         if (config.getLog().isRedirectStdoutToServerLogEnabled()) {
            System.setOut(new LoggingPrintStream(new LoggingOutputStream("Stdout", WLLevel.NOTICE, true)));
         }

         if (config.getLog().isRedirectStderrToServerLogEnabled()) {
            System.setErr(new LoggingPrintStream(new LoggingOutputStream("StdErr", WLLevel.NOTICE, true)));
         }

         ImageManager imgMgr = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         imgMgr.registerImageSource("Logging", LoggingImageSource.getInstance());
         ServerRuntimeMBean serverRuntime = runtimeAccess.getServerRuntime();
         ServerLogRuntimeMBeanGenerator generator = (ServerLogRuntimeMBeanGenerator)LocatorUtilities.getService(ServerLogRuntimeMBeanGenerator.class);
         ServerLogRuntimeMBean logRuntime = generator.createServerLogRuntimeMBean(config.getLog(), serverRuntime);
         serverRuntime.setServerLogRuntime(logRuntime);
         LogEntryInitializer.setServerInitialized(true);
         boolean msgIdConfig = runtimeAccess.getDomain().isMsgIdPrefixCompatibilityEnabled();
         initializeMsgIdPrefixConverter(msgIdConfig);
         runtimeAccess.getDomain().addBeanUpdateListener(new BeanUpdateListener() {
            public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
               DomainMBean bean = (DomainMBean)event.getProposedBean();
               ServerLoggingInitializer.initializeMsgIdPrefixConverter(bean.isMsgIdPrefixCompatibilityEnabled());
            }

            public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
            }

            public void rollbackUpdate(BeanUpdateEvent event) {
            }
         });
      }
   }

   private static void initializeMsgIdPrefixConverter(boolean enabled) {
      MsgIdPrefixConverter.setCompatibilityModeEnabled(enabled);
   }

   static RuntimeAccess getRuntimeAccess() {
      return ManagementService.getRuntimeAccess(KERNEL_ID);
   }

   private static void initializePlatformLoggers(Properties props) {
      resetUserConfiguredPlatformLoggerLevels();
      if (props != null && !props.isEmpty() && !isODLLoggingEnabled()) {
         SortedMap sorted = sortLoggingConfiguration(props);
         Iterator var2 = sorted.keySet().iterator();

         while(var2.hasNext()) {
            String loggerName = (String)var2.next();
            loggerName = loggerName.trim();
            String levelName = (String)sorted.get(loggerName);
            Level level = Level.parse(levelName);
            Logger logger = Logger.getLogger(loggerName);
            logger.setLevel(level);
            userConfiguredPlatformLoggers.add(logger);
         }

      }
   }

   private static SortedMap sortLoggingConfiguration(Properties props) {
      SortedMap sorted = new TreeMap();
      Iterator var2 = props.keySet().iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         if (key == null) {
            key = "";
         }

         Object value = props.get(key);
         String loggerName = (String)key;
         String levelName = (String)value;
         sorted.put(loggerName, levelName);
      }

      return sorted;
   }

   private static void resetUserConfiguredPlatformLoggerLevels() {
      Iterator var0 = userConfiguredPlatformLoggers.iterator();

      while(var0.hasNext()) {
         Logger l = (Logger)var0.next();
         l.setLevel((Level)null);
      }

      userConfiguredPlatformLoggers.clear();
   }

   public static boolean isODLLoggingEnabled() {
      try {
         Class.forName("oracle.core.ojdl.weblogic.ODLConfiguration");
         boolean result = isODLConfigClassDeployed();
         if (result) {
            LogMgmtLogger.logDetectedODLConfiguration();
         }

         return result;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   private static boolean isODLConfigClassDeployed() {
      String serverName = getRuntimeAccess().getServerName();
      StartupClassMBean[] startupClasses = getRuntimeAccess().getDomain().getStartupClasses();
      if (startupClasses != null) {
         StartupClassMBean[] var2 = startupClasses;
         int var3 = startupClasses.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            StartupClassMBean startupClass = var2[var4];
            String className = startupClass.getClassName();
            if (className != null && className.equals("oracle.core.ojdl.weblogic.ODLConfiguration")) {
               TargetMBean[] targets = startupClass.getTargets();
               if (targets != null) {
                  TargetMBean[] var8 = targets;
                  int var9 = targets.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     TargetMBean target = var8[var10];
                     Set serverNames = target.getServerNames();
                     if (serverNames == null) {
                        serverNames = new HashSet();
                     }

                     if (target.getName().equals(serverName) || ((Set)serverNames).contains(serverName)) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public static void initializeDomainLogging() {
      RuntimeAccess runtimeAccess = getRuntimeAccess();
      if (runtimeAccess.isAdminServer()) {
         LogMBean log = runtimeAccess.getDomain().getLog();
         Logger domainLogger = null;

         try {
            domainLogger = loggerFactory.createAndInitializeDomainLogger(log);
         } catch (IOException var4) {
            LogMgmtLogger.logCannotOpenDomainLogfile(log.getLogFilePath(), var4);
            domainLogger = null;
         }

         LoggingHelper.initDomainLogger(domainLogger);
      }
   }
}
