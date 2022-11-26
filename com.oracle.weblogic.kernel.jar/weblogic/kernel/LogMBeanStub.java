package weblogic.kernel;

import java.io.File;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.configuration.LogMBean;

final class LogMBeanStub extends MBeanStub implements LogMBean {
   private static final String TRACE = "Trace";
   private static final String INFO = "Info";
   private String fname = null;
   private String rotationType = "none";
   private boolean filesLtd = false;
   private int fileCount = 7;
   private int timeSpan = 24;
   private int fileSize = 500;
   private String logRotationTime;
   private boolean rotateLogOnStartup = false;
   private String rotationDir = null;
   private boolean log4jLoggingEnabled = false;
   private int memoryBufferSize = 500;
   private String stdoutSeverty = "Info";
   private String logFileSeverity = "Trace";
   private String domainLogBroadcastSeverity;
   private String memoryBufferSeverity;
   private LogFilterMBean stdoutFilter;
   private LogFilterMBean logFileFilter;
   private LogFilterMBean domainLogBroadcastFilter;
   private LogFilterMBean memoryBufferFilter;
   private boolean redirectStdoutToServerLogEnabled = false;
   private boolean redirectStderrToServerLogEnabled = false;
   private String stdoutFormat = "standard";
   private boolean stdoutLogStack = true;
   private int stacktraceDepth = 5;
   private String dateFormatPattern;

   LogMBeanStub() {
      this.initializeFromSystemProperties("weblogic.log.");
   }

   public String getFileName() {
      return this.fname;
   }

   public void setFileName(String fileName) {
      this.fname = fileName;
   }

   public String getRotationType() {
      return this.rotationType;
   }

   public void setRotationType(String rotationType) {
      this.rotationType = rotationType;
   }

   public boolean isNumberOfFilesLimited() {
      return this.filesLtd;
   }

   public void setNumberOfFilesLimited(boolean value) {
      this.filesLtd = value;
   }

   public int getFileCount() {
      return this.fileCount;
   }

   public void setFileCount(int numberOfFiles) {
      this.fileCount = numberOfFiles;
   }

   public int getFileTimeSpan() {
      return this.timeSpan;
   }

   public void setFileTimeSpan(int hours) {
      this.timeSpan = hours;
   }

   public int getFileMinSize() {
      return this.fileSize;
   }

   public void setFileMinSize(int kBytes) {
      this.fileSize = kBytes;
   }

   public String getRotationTime() {
      return this.logRotationTime;
   }

   public void setRotationTime(String logRotationTime) {
      this.logRotationTime = logRotationTime;
   }

   public boolean getRotateLogOnStartup() {
      return this.rotateLogOnStartup;
   }

   public void setRotateLogOnStartup(boolean value) {
      this.rotateLogOnStartup = value;
   }

   public String getLogFileRotationDir() {
      return this.rotationDir;
   }

   public void setLogFileRotationDir(String dirPath) {
      this.rotationDir = dirPath;
   }

   public LogFilterMBean getLogFileFilter() {
      return this.logFileFilter;
   }

   public void setLogFileFilter(LogFilterMBean filter) {
      this.logFileFilter = filter;
   }

   public LogFilterMBean getStdoutFilter() {
      return this.stdoutFilter;
   }

   public void setStdoutFilter(LogFilterMBean filter) {
      this.stdoutFilter = filter;
   }

   public LogFilterMBean getMemoryBufferFilter() {
      return this.memoryBufferFilter;
   }

   public void setMemoryBufferFilter(LogFilterMBean filter) {
      this.memoryBufferFilter = filter;
   }

   public LogFilterMBean getDomainLogBroadcastFilter() {
      return this.domainLogBroadcastFilter;
   }

   public void setDomainLogBroadcastFilter(LogFilterMBean filter) {
      this.domainLogBroadcastFilter = filter;
   }

   public boolean isLog4jLoggingEnabled() {
      return this.log4jLoggingEnabled;
   }

   public void setLog4jLoggingEnabled(boolean enabled) {
      this.log4jLoggingEnabled = enabled;
   }

   public int getMemoryBufferSize() {
      return this.memoryBufferSize;
   }

   public void setMemoryBufferSize(int mem) {
      this.memoryBufferSize = mem;
   }

   public String getStdoutSeverity() {
      return this.stdoutSeverty;
   }

   public void setStdoutSeverity(String stdoutSeverity) {
      this.stdoutSeverty = stdoutSeverity;
   }

   public String getLogFileSeverity() {
      return this.logFileSeverity;
   }

   public void setLogFileSeverity(String logFileSeverity) {
      this.logFileSeverity = logFileSeverity;
   }

   public String getMemoryBufferSeverity() {
      return this.memoryBufferSeverity;
   }

   public void setMemoryBufferSeverity(String severity) {
      this.memoryBufferSeverity = severity;
   }

   public String getDomainLogBroadcastSeverity() {
      return this.domainLogBroadcastSeverity;
   }

   public void setDomainLogBroadcastSeverity(String severity) {
      this.domainLogBroadcastSeverity = severity;
   }

   public boolean isRedirectStdoutToServerLogEnabled() {
      return this.redirectStdoutToServerLogEnabled;
   }

   public void setRedirectStdoutToServerLogEnabled(boolean captureStdout) {
      this.redirectStdoutToServerLogEnabled = captureStdout;
   }

   public boolean isRedirectStderrToServerLogEnabled() {
      return this.redirectStderrToServerLogEnabled;
   }

   public void setRedirectStderrToServerLogEnabled(boolean captureStderr) {
      this.redirectStderrToServerLogEnabled = captureStderr;
   }

   public int getDomainLogBroadcasterBufferSize() {
      return 0;
   }

   public void setDomainLogBroadcasterBufferSize(int bufferSize) {
   }

   public String computeLogFilePath() {
      return this.getLogFilePath();
   }

   public String getLogFilePath() {
      return (new File(this.getFileName())).getAbsolutePath();
   }

   public String getStdoutFormat() {
      return this.stdoutFormat;
   }

   public void setStdoutFormat(String format) {
      this.stdoutFormat = format;
   }

   public boolean isStdoutLogStack() {
      return this.stdoutLogStack;
   }

   public void setStdoutLogStack(boolean stack) {
      this.stdoutLogStack = stack;
   }

   public int getStacktraceDepth() {
      return this.stacktraceDepth;
   }

   public void setStacktraceDepth(int depth) {
      this.stacktraceDepth = depth;
   }

   public long getFileTimeSpanFactor() {
      return 3600000L;
   }

   public void setFileTimeSpanFactor(long factor) {
   }

   public OutputStream getOutputStream() {
      return null;
   }

   public void setOutputStream(OutputStream os) {
   }

   public int getBufferSizeKB() {
      return 8;
   }

   public void setBufferSizeKB(int size) {
   }

   public String getDateFormatPattern() {
      if (this.dateFormatPattern == null) {
         DateFormat df = DateFormat.getDateTimeInstance(2, 0);
         if (df instanceof SimpleDateFormat) {
            this.dateFormatPattern = ((SimpleDateFormat)df).toPattern();
         } else {
            this.dateFormatPattern = "MMM d, yyyy h:mm:ss a z";
         }
      }

      return this.dateFormatPattern;
   }

   public void setDateFormatPattern(String df) {
   }

   public String getLoggerSeverity() {
      return "Trace";
   }

   public void setLoggerSeverity(String severity) {
   }

   public Properties getLoggerSeverityProperties() {
      return null;
   }

   public void setLoggerSeverityProperties(Properties props) {
   }

   public boolean isServerLoggingBridgeUseParentLoggersEnabled() {
      return false;
   }

   public void setServerLoggingBridgeUseParentLoggersEnabled(boolean value) {
   }

   public Properties getPlatformLoggerLevels() {
      return null;
   }

   public void setPlatformLoggerLevels(Properties props) {
   }

   public boolean isServerLoggingBridgeAtRootLoggerEnabled() {
      return false;
   }

   public void setServerLoggingBridgeAtRootLoggerEnabled(boolean value) {
   }

   public String getLogRotationDirPath() {
      return this.getLogFileRotationDir();
   }

   public boolean isLogMonitoringEnabled() {
      return false;
   }

   public void setLogMonitoringEnabled(boolean enabled) {
   }

   public int getLogMonitoringIntervalSecs() {
      return 0;
   }

   public void setLogMonitoringIntervalSecs(int interval) {
   }

   public int getLogMonitoringThrottleThreshold() {
      return 0;
   }

   public void setLogMonitoringThrottleThreshold(int threshold) {
   }

   public int getLogMonitoringThrottleMessageLength() {
      return 0;
   }

   public void setLogMonitoringThrottleMessageLength(int length) {
   }

   public int getLogMonitoringMaxThrottleMessageSignatureCount() {
      return 0;
   }

   public void setLogMonitoringMaxThrottleMessageSignatureCount(int length) {
   }
}
