package weblogic.connector.external.impl;

import weblogic.connector.external.LoggingInfo;

public class LoggingInfoImpl implements LoggingInfo {
   private String logFilename;
   private boolean loggingEnabled;
   private String rotationType;
   private String rotationTime;
   private boolean numberOfFilesLimited;
   private int fileCount;
   private int fileSizeLimit;
   private int fileTimeSpan;
   private boolean rotateLogOnStartup;
   private String logFileRotationDir;

   public LoggingInfoImpl(String logFilename, boolean loggingEnabled, String rotationType, String rotationTime, boolean numberOfFilesLimited, int fileCount, int fileSizeLimit, int fileTimeSpan, boolean rotateLogOnStartup, String logFileRotationDir) {
      this.logFilename = logFilename;
      this.loggingEnabled = loggingEnabled;
      this.rotationType = rotationType;
      this.rotationTime = rotationTime;
      this.numberOfFilesLimited = numberOfFilesLimited;
      this.fileCount = fileCount;
      this.fileSizeLimit = fileSizeLimit;
      this.fileTimeSpan = fileTimeSpan;
      this.rotateLogOnStartup = rotateLogOnStartup;
      this.logFileRotationDir = logFileRotationDir;
   }

   public String getLogFilename() {
      return this.logFilename;
   }

   public boolean isLoggingEnabled() {
      return this.loggingEnabled;
   }

   public String getRotationType() {
      return this.rotationType;
   }

   public String getRotationTime() {
      return this.rotationTime;
   }

   public boolean isNumberOfFilesLimited() {
      return this.numberOfFilesLimited;
   }

   public int getFileCount() {
      return this.fileCount;
   }

   public int getFileSizeLimit() {
      return this.fileSizeLimit;
   }

   public int getFileTimeSpan() {
      return this.fileTimeSpan;
   }

   public boolean isRotateLogOnStartup() {
      return this.rotateLogOnStartup;
   }

   public String getLogFileRotationDir() {
      return this.logFileRotationDir;
   }
}
