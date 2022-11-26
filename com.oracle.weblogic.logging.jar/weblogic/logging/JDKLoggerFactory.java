package weblogic.logging;

import com.bea.logging.BaseLogRecord;
import com.bea.logging.BaseLogRecordFactory;
import com.bea.logging.BaseLogger;
import com.bea.logging.BaseLoggerFactory;
import com.bea.logging.DateFormatter;
import com.bea.logging.LogBufferHandler;
import com.bea.logging.LoggingService;
import com.bea.logging.StatsHandler;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.ServerMBean;

public class JDKLoggerFactory implements BaseLoggerFactory, BaseLogRecordFactory, BeanUpdateListener {
   protected static final String KERNEL_LOGGER = "com.bea.weblogic.kernel";
   protected static final String BRIDGE_LOGGER = "";
   private static final String LOG4J_ADAPTER_FACTORY = "weblogic.logging.log4j.JDKLog4jAdapterFactory";
   private static boolean log4jEnabled = false;
   public static final String DOMAIN_LOGGER = "com.bea.weblogic.domain";
   private static Logger bridgeLogger = Logger.getLogger("");

   public static JDKLoggerFactory getJDKLoggerFactory(LogMBean logMBean) {
      boolean useLog4jAdapter = logMBean.isLog4jLoggingEnabled();
      if (!useLog4jAdapter) {
         return new JDKLoggerFactory();
      } else {
         try {
            Class clz = Class.forName("weblogic.logging.log4j.JDKLog4jAdapterFactory", true, Thread.currentThread().getContextClassLoader());
            JDKLoggerFactory log4jLoggerFactory = (JDKLoggerFactory)clz.newInstance();
            log4jEnabled = true;
            return log4jLoggerFactory;
         } catch (Throwable var4) {
            LogMgmtLogger.logErrorInitializingLog4jLogging(logMBean.getName(), var4);
            return new JDKLoggerFactory();
         }
      }
   }

   public static boolean isLog4jEnabled() {
      return log4jEnabled;
   }

   static Logger getBridgeLogger() {
      return bridgeLogger;
   }

   public BaseLogger createBaseLogger(String name) {
      WLLogger wl = new WLLogger(name);
      wl.setUseParentHandlers(true);
      return wl;
   }

   public BaseLogRecord createBaseLogRecord(LogMessage logMessage) {
      return new WLLogRecord(logMessage);
   }

   public Logger createAndInitializeDomainLogger(LogMBean log) throws IOException {
      WLLogger domainLogger = new WLLogger("com.bea.weblogic.domain");
      domainLogger.setLevel(WLLevel.getLevel(Severities.severityStringToNum(log.getLoggerSeverity())));
      FileStreamHandler domainlog = new FileStreamHandler(log);
      LogFileFormatter lff = new LogFileFormatter(log);
      domainlog.setFormatter(lff);
      domainlog.setErrorManager(new WLErrorManager(domainlog));
      domainLogger.setUseParentHandlers(false);
      domainLogger.setLevel(Level.ALL);
      domainLogger.addHandler(domainlog);
      LogMgmtLogger.logInitializedDomainLogFile(log.getLogFilePath());
      return domainLogger;
   }

   public Logger createAndInitializeServerLogger(ServerMBean smb) {
      LogMBean logMBean = smb.getLog();
      LoggingService ls = LoggingService.getInstance();
      SeverityChangeListener scl = null;
      ConsoleHandler consoleHandler = new ConsoleHandler(smb.getLog());
      consoleHandler.setFormatter(new ConsoleFormatter(smb.getLog()));
      DateFormatter df = ((ConsoleFormatter)consoleHandler.getFormatter()).getDateFormatter();
      scl = new SeverityChangeListener(logMBean, "StdoutSeverity", consoleHandler);
      scl.setLevel(logMBean.getStdoutSeverity());
      consoleHandler.setFilter(new LogFilter(logMBean, "StdoutFilter", logMBean.getStdoutFilter()));
      LogBufferHandler logBufferHandler = LogBufferHandler.getInstance();

      try {
         FileStreamHandler fileHandler = new FileStreamHandler(logMBean);
         LogFileFormatter lff = new LogFileFormatter(logMBean);
         fileHandler.setFormatter(lff);
         scl = new SeverityChangeListener(logMBean, "LogFileSeverity", fileHandler);
         scl.setLevel(logMBean.getLogFileSeverity());
         fileHandler.setFilter(new LogFilter(logMBean, "LogFileFilter", logMBean.getLogFileFilter()));
         synchronized(logBufferHandler) {
            logBufferHandler.setDelegateAndClose(fileHandler);
            ls.setBaseLoggerFactory(this);
            ls.setBaseLogRecordFactory(this);
            Logger logger = ls.getLogger("");
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            ls.stopUsingPrimordialLogger();
            MessageLoggerRegistry.registerMessageLogger("", ls);
         }

         this.updateLoggerSeverities(smb.getLog());
         smb.getLog().addBeanUpdateListener(this);
         logBufferHandler.dumpLogBuffer(fileHandler.getRotatingFileOutputStream(), lff);
         logBufferHandler.flush();
         LogMgmtLogger.logServerLogFileOpened(fileHandler.toString());
      } catch (IOException var15) {
         LogMgmtLogger.logErrorOpeningLogFile(logMBean.getLogFilePath());
      }

      Logger logger = ls.getLogger("");
      logger.addHandler(new StatsHandler());
      DomainLogBroadcastHandler dlbh = new DomainLogBroadcastHandler();
      scl = new SeverityChangeListener(logMBean, "DomainLogBroadcastSeverity", dlbh);
      scl.setLevel(logMBean.getDomainLogBroadcastSeverity());
      dlbh.setFilter(new LogFilter(logMBean, "DomainLogBroadcastFilter", logMBean.getDomainLogBroadcastFilter()));
      logger.addHandler(dlbh);

      try {
         logger.addHandler(new JMXBroadcastHandler());
      } catch (Exception var13) {
      }

      LogMgmtLogger.logDefaultServerLoggingInitialized();
      return logger;
   }

   public void initializeServerLoggingBridge(final LogMBean logMBean) {
      if (logMBean.isServerLoggingBridgeAtRootLoggerEnabled() && !ServerLoggingInitializer.isODLLoggingEnabled()) {
         addServerLoggingHandler(bridgeLogger, logMBean.isServerLoggingBridgeUseParentLoggersEnabled());
      }

      try {
         Method method = LogManager.class.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
         method.invoke(LogManager.getLogManager(), new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent arg0) {
               if (logMBean.isServerLoggingBridgeAtRootLoggerEnabled() && !ServerLoggingInitializer.isODLLoggingEnabled()) {
                  JDKLoggerFactory.addServerLoggingHandler(JDKLoggerFactory.bridgeLogger, logMBean.isServerLoggingBridgeUseParentLoggersEnabled());
               }

            }
         });
      } catch (Exception var3) {
      }

   }

   protected static void addServerLoggingHandler(Logger logger, boolean useParentHandlers) {
      Handler[] handlers = logger.getHandlers();
      if (handlers != null) {
         Handler[] var3 = handlers;
         int var4 = handlers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Handler h = var3[var5];
            if (h.getClass().getName().equals(ServerLoggingHandler.class.getName())) {
               return;
            }
         }
      }

      logger.setUseParentHandlers(useParentHandlers);
      Handler h = new ServerLoggingHandler();
      h.setLevel(Level.ALL);
      logger.addHandler(h);
   }

   public void activateUpdate(BeanUpdateEvent arg0) throws BeanUpdateFailedException {
      LogMBean log = (LogMBean)arg0.getSourceBean();
      this.updateLoggerSeverities(log);
   }

   public void prepareUpdate(BeanUpdateEvent arg0) throws BeanUpdateRejectedException {
   }

   public void rollbackUpdate(BeanUpdateEvent arg0) {
   }

   protected void updateLoggerSeverities(LogMBean logMBean) {
      LoggingService ls = LoggingService.getInstance();
      Logger logger = ls.getLogger("");
      logger.setLevel(WLLevel.getLevel(Severities.severityStringToNum(logMBean.getLoggerSeverity())));
      Properties loggerSeverities = logMBean.getLoggerSeverityProperties();
      LoggingService.getInstance().setLoggerSeverities(loggerSeverities);
   }
}
