package com.bea.logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.i18n.logging.Severities;

public class RotatingFileStreamHandler extends StreamHandler {
   private static final boolean DEBUG = false;
   protected RotatingFileOutputStream rotatingStream;
   private static final Map ATTRIBUTE_TYPES = buildAttrubuteTypes();

   public RotatingFileStreamHandler() throws IOException {
      this.rotatingStream = null;
      this.rotatingStream = new RotatingFileOutputStream();
      this.setOutputStream(this.rotatingStream);
      this.rotatingStream.setRotationMonitor(this);
      this.rotatingStream.setRotateImmediately(false);
      this.setFilter((Filter)null);
      this.setLevel(Level.ALL);
   }

   public RotatingFileStreamHandler(LogFileConfigBean logFileConfig) throws IOException {
      this();
      this.initialize(logFileConfig);
   }

   public RotatingFileOutputStream getRotatingFileOutputStream() {
      return this.rotatingStream;
   }

   public synchronized void publish(LogRecord rec) {
      try {
         if (this.rotatingStream == null || !this.rotatingStream.isStreamOpened()) {
            return;
         }

         super.publish(rec);
         super.flush();
         this.rotatingStream.ensureRotation();
      } catch (Exception var3) {
         this.reportError("Unknown exception writing to the log file " + this, var3, 1);
      }

   }

   public void close() {
      if (this.rotatingStream != null) {
         try {
            synchronized(this.rotatingStream.getRotationMonitor()) {
               this.rotatingStream.flush();
               this.rotatingStream.close();
            }
         } catch (IOException var4) {
            this.reportError(var4.getMessage(), var4, 3);
         }
      }

   }

   public void forceRotation() throws IOException {
      this.rotatingStream.forceRotation();
   }

   public String toString() {
      return this.rotatingStream.getCurrentLogFile();
   }

   public void setSeverity(String severity) {
      Level l = LogLevel.getLevel(Severities.severityStringToNum(severity));
      this.setLevel(l);
   }

   public long getLastTimerRunTime() {
      return this.rotatingStream.getLastTimerRunTime();
   }

   public void initialize(LogFileConfigBean logFileConfig) throws IOException {
      this.initialize(logFileConfig, false, false);
   }

   public void initialize(LogFileConfigBean logFileConfig, boolean reinitialize) throws IOException {
      this.initialize(logFileConfig, reinitialize, false);
   }

   public void initialize(LogFileConfigBean logFileConfig, boolean reinitialize, BeanUpdateEvent beanUpdateEvent) throws IOException {
      boolean reopenStream = false;
      boolean affectsPaths = false;
      BeanUpdateEvent.PropertyUpdate[] var6 = beanUpdateEvent.getUpdateList();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         BeanUpdateEvent.PropertyUpdate propUpdate = var6[var8];
         String propName = propUpdate.getPropertyName();
         AttributeType attrType = findAttributeType(propName);
         switch (attrType) {
            case AFFECTS_STREAM:
               reopenStream = true;
               break;
            case AFFECTS_PATHS:
               reinitialize = false;
               reopenStream = true;
               affectsPaths = true;
         }
      }

      String oldBaseFilePath = this.rotatingStream.getBaseLogFileName();
      String oldLogRotationDir = this.rotatingStream.getLogRotationDir();
      this.initialize(logFileConfig, reinitialize, reopenStream);
      if (affectsPaths) {
         String newBaseFilePath = logFileConfig.getBaseLogFilePath();
         String newLogRotationDir = logFileConfig.getLogFileRotationDir();
         LoggingService.getInstance().notifyLogFilePathChanges(oldBaseFilePath, oldLogRotationDir, newBaseFilePath, newLogRotationDir);
      }

   }

   private synchronized void initialize(LogFileConfigBean logFileConfig, boolean reinitialize, boolean reopenStream) throws IOException {
      this.setSeverity(logFileConfig.getLogFileSeverity());
      this.rotatingStream.initialize(logFileConfig, reinitialize, reopenStream);
   }

   private static AttributeType findAttributeType(String name) {
      AttributeType attrType = (AttributeType)ATTRIBUTE_TYPES.get(name);
      if (attrType == null) {
         attrType = RotatingFileStreamHandler.AttributeType.UNKNOWN;
      }

      return attrType;
   }

   private static Map buildAttrubuteTypes() {
      Map map = new HashMap();
      map.put("DateFormatPattern", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("RotationType", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("NumberOfFilesLimited", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("FileCount", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("FileTimeSpan", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("RotationTime", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("FileMinSize", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("RotateLogOnStartup", RotatingFileStreamHandler.AttributeType.FULLY_DYNAMIC);
      map.put("BufferSizeKB", RotatingFileStreamHandler.AttributeType.AFFECTS_STREAM);
      map.put("FileName", RotatingFileStreamHandler.AttributeType.AFFECTS_PATHS);
      map.put("LogFileRotationDir", RotatingFileStreamHandler.AttributeType.AFFECTS_PATHS);
      return map;
   }

   private static enum AttributeType {
      UNKNOWN,
      FULLY_DYNAMIC,
      AFFECTS_STREAM,
      AFFECTS_PATHS;
   }
}
