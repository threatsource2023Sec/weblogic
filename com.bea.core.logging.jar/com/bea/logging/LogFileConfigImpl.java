package com.bea.logging;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Handler;
import weblogic.utils.PropertyHelper;

public class LogFileConfigImpl implements LogFileConfig, Serializable {
   private static final boolean DEBUG = false;
   private static final String DEFAULT_BASE_LOG_FILE_NAME = "server.log";
   private static LogFileConfigBean configBean = new LogFileConfigBean();
   private static boolean useDefaultLogFile = false;
   private static Map configBeanSetters = new HashMap();
   private boolean baseLogFileNameOverridden = false;
   private LogFilterExpressionConfig logFileFilter;
   private String logFileFilterName;

   public LogFileConfigImpl() {
      String logFileName = PropertyHelper.getProperty("com.bea.logging.BaseLogFileName", (String)null);
      if (logFileName != null) {
         this.baseLogFileNameOverridden = true;
      } else if (useDefaultLogFile) {
         logFileName = "server.log";
      }

      String baseLogFileName = configBean.getBaseLogFileName();
      if (baseLogFileName == null || baseLogFileName.length() == 0) {
         configBean.setBaseLogFileName(logFileName);
      }

   }

   public String getBaseLogFileName() {
      return configBean.getBaseLogFileName();
   }

   public void setBaseLogFileName(String baseLogFileName) {
      if (!this.baseLogFileNameOverridden) {
         configBean.setBaseLogFileName(baseLogFileName);
      }

   }

   public String getLogFileRotationDir() {
      return configBean.getLogFileRotationDir();
   }

   public void setLogFileRotationDir(String logFileRotationDir) {
      configBean.setLogFileRotationDir(logFileRotationDir);
   }

   public String getLogFileSeverity() {
      return configBean.getLogFileSeverity();
   }

   public void setLogFileSeverity(String logFileSeverity) {
      configBean.setLogFileSeverity(logFileSeverity);
   }

   public boolean isNumberOfFilesLimited() {
      return configBean.isNumberOfFilesLimited();
   }

   public void setNumberOfFilesLimited(boolean numberOfFilesLimited) {
      configBean.setNumberOfFilesLimited(numberOfFilesLimited);
   }

   public int getRotatedFileCount() {
      return configBean.getRotatedFileCount();
   }

   public void setRotatedFileCount(int rotatedFileCount) {
      configBean.setRotatedFileCount(rotatedFileCount);
   }

   public boolean isRotateLogOnStartupEnabled() {
      return configBean.isRotateLogOnStartupEnabled();
   }

   public void setRotateLogOnStartupEnabled(boolean rotateLogOnStartupEnabled) {
      configBean.setRotateLogOnStartupEnabled(rotateLogOnStartupEnabled);
   }

   public int getRotationSize() {
      return configBean.getRotationSize();
   }

   public void setRotationSize(int rotationSize) {
      configBean.setRotationSize(rotationSize);
   }

   public String getRotationTime() {
      return configBean.getRotationTime();
   }

   public void setRotationTime(String rotationTime) {
      configBean.setRotationTime(rotationTime);
   }

   public int getRotationTimeSpan() {
      return configBean.getRotationTimeSpan();
   }

   public void setRotationTimeSpan(int rotationTimeSpan) {
      configBean.setRotationTimeSpan(rotationTimeSpan);
   }

   public long getRotationTimeSpanFactor() {
      return configBean.getRotationTimeSpanFactor();
   }

   public void setRotationTimeSpanFactor(long rotationTimeSpanFactor) {
      configBean.setRotationTimeSpanFactor(rotationTimeSpanFactor);
   }

   public String getRotationType() {
      return configBean.getRotationType();
   }

   public void setRotationType(String rotationType) {
      configBean.setRotationType(rotationType);
   }

   public void prepare() {
   }

   public void activate() {
      try {
         LoggingService.getInstance().configureLogFile(configBean);
         this.activateFilterConfig();
      } catch (IOException var2) {
      }

   }

   public void prepareUpdate(List changes) {
   }

   public void activateUpdate(List changes) {
      if (configBeanSetters.isEmpty()) {
         this.initConfigBeanSetters();
      }

      Iterator i = changes.iterator();

      while(i.hasNext()) {
         PropertyChangeEvent e = (PropertyChangeEvent)i.next();
         String prop = e.getPropertyName();
         Method m = (Method)configBeanSetters.get(prop);

         try {
            if (m != null) {
               m.invoke(configBean, e.getNewValue());
            }
         } catch (Throwable var7) {
         }
      }

      this.activate();
   }

   private void initConfigBeanSetters() {
      Method[] methods = LogFileConfigBean.class.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         String n = m.getName();
         if (n.startsWith("set") && n.length() > 3) {
            String propName = n.substring(3);
            configBeanSetters.put(propName, m);
         }
      }

   }

   public void rollbackUpdate(List changes) {
   }

   static boolean isUseDefaultLogFile() {
      return useDefaultLogFile;
   }

   static void setUseDefaultLogFile(boolean useDefaultLogFile) {
      LogFileConfigImpl.useDefaultLogFile = useDefaultLogFile;
   }

   public LogFilterExpressionConfig getLogFileFilter() {
      return this.logFileFilter;
   }

   public void setLogFileFilter(LogFilterExpressionConfig logFileFilter) {
      this.logFileFilter = logFileFilter;
   }

   private void activateFilterConfig() {
      LoggingService ls = LoggingService.getInstance();
      Handler fh = ls.getRotatingFileStreamHandler();
      Filter f = null;
      if (this.logFileFilter != null) {
         String expr = this.logFileFilter.getLogFilterExpression();
         LogFilterFactory ff = LogFilterFactory.getInstance();
         if (ff != null) {
            f = ff.createFilter(expr);
         }
      }

      fh.setFilter(f);
   }

   public String getLogFileFilterName() {
      return this.logFileFilterName;
   }

   public void setLogFileFilterName(String s) {
      this.logFileFilterName = s;
   }
}
