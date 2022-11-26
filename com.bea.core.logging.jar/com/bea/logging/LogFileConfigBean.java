package com.bea.logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LogFileConfigBean {
   private String baseLogFilePath;
   private String logFileRotationDir = null;
   private boolean rotateLogOnStartupEnabled = true;
   private String rotationType = "bySize";
   private int rotationSize = 500;
   private boolean numberOfFilesLimited = false;
   private int rotatedFileCount = 7;
   private String rotationTime = "00:00";
   private int rotationTimeSpan = 24;
   private long rotationTimeSpanFactor = 3600000L;
   private String logFileSeverity = "Trace";
   private int bufferSizeKB = 8;

   public String getBaseLogFilePath() {
      return this.baseLogFilePath;
   }

   public void setBaseLogFilePath(String baseLogFile) {
      this.baseLogFilePath = baseLogFile;
   }

   /** @deprecated */
   @Deprecated
   public String getBaseLogFileName() {
      return this.getBaseLogFilePath();
   }

   /** @deprecated */
   @Deprecated
   public void setBaseLogFileName(String baseLogFile) {
      this.setBaseLogFilePath(baseLogFile);
   }

   public String getLogFileRotationDir() {
      return this.logFileRotationDir;
   }

   public void setLogFileRotationDir(String logFileRotationDir) {
      this.logFileRotationDir = logFileRotationDir;
   }

   public String getLogFileSeverity() {
      return this.logFileSeverity;
   }

   public void setLogFileSeverity(String logFileSeverity) {
      validateSeverityString(logFileSeverity);
      this.logFileSeverity = logFileSeverity;
   }

   public boolean isNumberOfFilesLimited() {
      return this.numberOfFilesLimited;
   }

   public void setNumberOfFilesLimited(boolean numberOfFilesLimited) {
      this.numberOfFilesLimited = numberOfFilesLimited;
   }

   public int getRotatedFileCount() {
      return this.rotatedFileCount;
   }

   public void setRotatedFileCount(int rotatedFileCount) {
      this.rotatedFileCount = rotatedFileCount;
   }

   public boolean isRotateLogOnStartupEnabled() {
      return this.rotateLogOnStartupEnabled;
   }

   public void setRotateLogOnStartupEnabled(boolean rotateLogOnStartupEnabled) {
      this.rotateLogOnStartupEnabled = rotateLogOnStartupEnabled;
   }

   public int getRotationSize() {
      return this.rotationSize;
   }

   public void setRotationSize(int rotationSize) {
      this.rotationSize = rotationSize;
   }

   public String getRotationTime() {
      return this.rotationTime;
   }

   public void setRotationTime(String rotationTime) {
      validateLogTimeString(rotationTime);
      this.rotationTime = rotationTime;
   }

   public int getRotationTimeSpan() {
      return this.rotationTimeSpan;
   }

   public void setRotationTimeSpan(int rotationTimeSpan) {
      this.rotationTimeSpan = rotationTimeSpan;
   }

   public long getRotationTimeSpanFactor() {
      return this.rotationTimeSpanFactor;
   }

   public void setRotationTimeSpanFactor(long rotationTimeSpanFactor) {
      this.rotationTimeSpanFactor = rotationTimeSpanFactor;
   }

   public String getRotationType() {
      return this.rotationType;
   }

   public void setRotationType(String rotationType) {
      validateRotationType(rotationType);
      this.rotationType = rotationType;
   }

   public int getBufferSizeKB() {
      return this.bufferSizeKB;
   }

   public void setBufferSizeKB(int size) {
      this.bufferSizeKB = size;
   }

   private static boolean isLogStartTimeValid(String format, String time) {
      SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

      try {
         df.parse(time);
         return true;
      } catch (ParseException var4) {
         return false;
      }
   }

   private static void validateLogTimeString(String value) throws IllegalArgumentException {
      if (value != null && !value.equals("")) {
         if (!isLogStartTimeValid("k:mm", value)) {
            throw new IllegalArgumentException("Illegal time string: " + value);
         }
      } else {
         String msg = "LogTimeString can't be null or empty string";
         throw new IllegalArgumentException(msg);
      }
   }

   static void validateSeverityString(String sevstr) {
      LoggingConfigValidator.validateSeverity(sevstr);
   }

   private static void validateRotationType(String rotationType) {
      if (!rotationType.equalsIgnoreCase("bySize") && !rotationType.equalsIgnoreCase("byTime") && !rotationType.equalsIgnoreCase("none") && !rotationType.equalsIgnoreCase("bySizeOrTime")) {
         throw new IllegalArgumentException(rotationType);
      }
   }
}
