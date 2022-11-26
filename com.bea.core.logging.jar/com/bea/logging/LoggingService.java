package com.bea.logging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.Severities;

public final class LoggingService implements MessageLogger, BaseLoggerFactory, BaseLogRecordFactory, PropertyChangeListener {
   public static final String PRIMORDIAL_LOGGER_PROPERTY = "com.bea.logging.PrimordialLoggingService";
   public static final String FILENAME_PROPERTY = "com.bea.logging.BaseLogFileName";
   private static final boolean DEBUG = false;
   private Logger rootLogger;
   private Logger primordialLogger;
   private boolean primordialMode;
   private BaseLoggerFactory baseLoggerFactory;
   private BaseLogRecordFactory baseLogRecordFactory;
   private String loggerSeverity;
   private RotatingFileStreamHandler rotatingFileStreamHandler;
   private StdoutHandler stdoutHandler;
   private LoggingServiceManager logManager;
   private Logger debugDelegateLogger;
   private boolean streamHandlerAdded;
   private Set logFileChangeListeners;
   private Map partitionLogManagers;

   public static LoggingService getInstance() {
      return LoggingService.SingletonWrapper.SINGLETON;
   }

   private LoggingService() {
      this.primordialMode = false;
      this.loggerSeverity = "Info";
      this.logFileChangeListeners = new HashSet();
      this.partitionLogManagers = new ConcurrentHashMap();
      String severity = null;

      try {
         this.logManager = new LoggingServiceManager(this);
         this.baseLoggerFactory = this;
         this.baseLogRecordFactory = this;
         String loggingBootstrapClass = System.getProperty("com.bea.logging.PrimordialLoggingService");
         if (loggingBootstrapClass != null) {
            Class clz = Class.forName(loggingBootstrapClass);
            this.primordialLogger = (Logger)clz.getMethod("getJDKLogger").invoke((Object)null);
            Level level = this.primordialLogger.getLevel();
            if (level == null) {
               level = LogLevel.INFO;
            }

            severity = Severities.severityNumToString(LogLevel.getSeverity((Level)level));
            this.primordialMode = true;
            this.primordialLogger.addHandler(LogBufferHandler.getInstance());
         }

         this.initialize();
         if (severity != null) {
            this.setLoggerSeverity(severity);
         }
      } catch (Throwable var5) {
         System.err.println("Error initializing LoggingService");
         var5.printStackTrace();
      }

   }

   public boolean isPrimoridialMode() {
      return this.primordialMode;
   }

   public void stopUsingPrimordialLogger() {
      this.primordialMode = false;
      LogBufferHandler logBufferHandler = LogBufferHandler.getInstance();
      if (this.primordialLogger != null) {
         this.primordialLogger.removeHandler(logBufferHandler);
         this.primordialLogger = null;
      }

   }

   public String getLoggerSeverity() {
      return this.loggerSeverity;
   }

   public void setLoggerSeverity(String severity) {
      this.loggerSeverity = severity;
      int severityValue = this.severityStringToInt(severity);
      this.rootLogger.setLevel(LogLevel.getLevel(severityValue));
   }

   public BaseLogger createBaseLogger(String name) {
      return new BaseLogger(name);
   }

   public Logger getAnonymousLogger() {
      Logger l = this.baseLoggerFactory.createBaseLogger((String)null);
      l.setParent(this.rootLogger);
      l.setFilter((Filter)null);
      return l;
   }

   public Logger getLogger() {
      return this.primordialMode ? this.primordialLogger : this.rootLogger;
   }

   public Logger getLogger(String name) {
      return this.logManager.getLogger(name);
   }

   public synchronized LoggingServiceManager findOrCreatePartitionLogManager(String partitionName) {
      LoggingServiceManager lm = (LoggingServiceManager)this.partitionLogManagers.get(partitionName);
      if (lm == null) {
         lm = new LoggingServiceManager(this);
         this.partitionLogManagers.put(partitionName, lm);
      }

      return lm;
   }

   public synchronized LoggingServiceManager getPartitionLogManager(String partitionName) {
      return (LoggingServiceManager)this.partitionLogManagers.get(partitionName);
   }

   public synchronized void removePartitionLogManager(String partitionName) {
      LoggingServiceManager lm = (LoggingServiceManager)this.partitionLogManagers.remove(partitionName);
      if (lm != null) {
         lm.superReset();
      }

   }

   private void initialize() {
      this.rootLogger = this.getLogger("");
      this.rootLogger.setLevel(LogLevel.INFO);
      this.stdoutHandler = new StdoutHandler();
      this.stdoutHandler.setFormatter(new LogMessageFormatter());
      this.rootLogger.addHandler(this.stdoutHandler);
      this.rootLogger.addHandler(LogBufferHandler.getInstance());

      try {
         Method method;
         if (JDKLoggingVersionHelper.usePostJdk8()) {
            method = LogManager.class.getMethod("addConfigurationListener", Runnable.class);
            method.invoke(this.logManager, new Runnable() {
               public void run() {
                  LoggingService.this.clearLoggerCache();
               }
            });
         } else {
            method = LogManager.class.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
            method.invoke(this.logManager, this);
         }
      } catch (Exception var2) {
      }

   }

   private void initRotatingStreamHandler(LogFileConfigBean bean) {
      try {
         if (this.rotatingFileStreamHandler == null) {
            this.rotatingFileStreamHandler = new RotatingFileStreamHandler();
            this.rotatingFileStreamHandler.setFormatter(new LogMessageFormatter(LogMessageFormatter.LOG_FILE_FIELDS));
         }

         this.rotatingFileStreamHandler.initialize(bean);
         if (!this.streamHandlerAdded) {
            this.rootLogger.addHandler(this.rotatingFileStreamHandler);
            this.streamHandlerAdded = true;
         }
      } catch (IOException var3) {
         System.out.println("Error initializing RotatingFileStreamHandler");
         var3.printStackTrace();
      }

   }

   public boolean isSeverityEnabled(String subSystem, int messageSeverity) {
      Logger logger = this.getLogger(subSystem);
      Level level = LogLevel.getLevel(messageSeverity);
      boolean loggable = logger.isLoggable(level);
      return loggable;
   }

   public void log(String subsystem, int severityLevel, String message) {
      this.log(subsystem, severityLevel, message, (Throwable)null);
   }

   public void log(String subsystem, int severityLevel, String message, Throwable throwable) {
      this.log(new LogMessage((String)null, (String)null, subsystem, severityLevel, message, throwable));
   }

   public void log(LogMessage logMessage) {
      BaseLogRecord rec = this.baseLogRecordFactory.createBaseLogRecord(logMessage);
      Logger logger = this.primordialMode ? this.primordialLogger : this.getLogger(logMessage.getSubsystem());
      logger.log(rec);
   }

   public BaseLoggerFactory getBaseLoggerFactory() {
      return this.baseLoggerFactory;
   }

   public void setBaseLoggerFactory(BaseLoggerFactory baseLoggerFactory) {
      this.baseLoggerFactory = baseLoggerFactory;
      this.logManager.superReset();
      this.logManager = new LoggingServiceManager(baseLoggerFactory);
      this.rootLogger = this.getLogger("");
   }

   public BaseLogRecordFactory getBaseLogRecordFactory() {
      return this.baseLogRecordFactory;
   }

   public void setBaseLogRecordFactory(BaseLogRecordFactory baseLogRecordFactory) {
      this.baseLogRecordFactory = baseLogRecordFactory;
   }

   public BaseLogRecord createBaseLogRecord(LogMessage logMessage) {
      return new BaseLogRecord(logMessage);
   }

   public RotatingFileStreamHandler getRotatingFileStreamHandler() {
      return this.rotatingFileStreamHandler;
   }

   public StdoutHandler getStdoutHandler() {
      return this.stdoutHandler;
   }

   public void setLoggerSeverities(Properties props) {
      this.resetLogLevels();
      if (props != null) {
         if (!props.isEmpty()) {
            Set entries = props.keySet();
            SortedSet loggerNames = new TreeSet(entries);
            Iterator iter = loggerNames.iterator();

            while(iter.hasNext()) {
               String key = (String)iter.next();
               String severity = props.getProperty(key);
               if (severity != null && severity.length() > 0) {
                  int severityValue = Severities.severityStringToNum(severity);
                  Level level = LogLevel.getLevel(severityValue);
                  Logger l = getInstance().getLogger(key);
                  l.setLevel(level);
               }
            }
         }

      }
   }

   private void resetLogLevels() {
      Enumeration e = this.logManager.getLoggerNames();

      while(e.hasMoreElements()) {
         String loggerName = (String)e.nextElement();
         if (loggerName != null && !loggerName.equals("com.oracle.logging.SystemLogger") && !loggerName.equals("")) {
            this.getLogger(loggerName).setLevel((Level)null);
         }
      }

   }

   void dumpLogBuffer() throws IOException {
      LogBufferHandler logBufferHandler = LogBufferHandler.getInstance();
      if (logBufferHandler != null) {
         logBufferHandler.dumpLogBuffer(this.rotatingFileStreamHandler.getRotatingFileOutputStream(), this.rotatingFileStreamHandler.getFormatter());
         logBufferHandler.flush();
         logBufferHandler.close();
         this.rootLogger.removeHandler(logBufferHandler);
      }

   }

   void configureLogFile(LogFileConfigBean configBean) throws IOException {
      this.initRotatingStreamHandler(configBean);
      this.dumpLogBuffer();
   }

   private int severityStringToInt(String sevstr) {
      LogFileConfigBean.validateSeverityString(sevstr);
      return Severities.severityStringToNum(sevstr);
   }

   public Logger getDebugDelegateLogger() {
      if (this.debugDelegateLogger == null) {
         this.debugDelegateLogger = this.getLogger("com.oracle.logging.SystemLogger");
         this.debugDelegateLogger.setLevel(Level.ALL);
      }

      return this.debugDelegateLogger;
   }

   public MessageDispatcher getMessageDispatcher(String name) {
      return new LoggerWrapper(name);
   }

   public void propertyChange(PropertyChangeEvent evt) {
      this.clearLoggerCache();
   }

   private void clearLoggerCache() {
      this.logManager.clearLoggerCache();
   }

   public void registerLogFileChangeListener(LogFileChangeListener listener) {
      synchronized(this.logFileChangeListeners) {
         this.logFileChangeListeners.add(listener);
      }
   }

   public void unregisterLogFileChangeListener(LogFileChangeListener listener) {
      synchronized(this.logFileChangeListeners) {
         this.logFileChangeListeners.remove(listener);
      }
   }

   void notifyLogFilePathChanges(String oldFilePath, String oldRotationDir, String newFilePath, String newRotationDir) {
      LogFileChangeListener[] listeners = new LogFileChangeListener[0];
      synchronized(this.logFileChangeListeners) {
         listeners = (LogFileChangeListener[])this.logFileChangeListeners.toArray(listeners);
      }

      LogFileChangeListener[] var6 = listeners;
      int var7 = listeners.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         LogFileChangeListener listener = var6[var8];
         listener.logFilePathsChanged(oldFilePath, oldRotationDir, newFilePath, newRotationDir);
      }

   }

   // $FF: synthetic method
   LoggingService(Object x0) {
      this();
   }

   private static class SingletonWrapper {
      private static LoggingService SINGLETON = new LoggingService();
   }
}
