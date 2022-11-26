package weblogic.logging.j2ee;

import java.io.File;
import java.io.OutputStream;
import java.util.Properties;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.kernel.MBeanStub;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.configuration.LogMBean;

public final class LoggingBeanAdapter extends MBeanStub implements LogMBean {
   private LoggingBean delegate = null;
   private OutputStream outputStream;

   public LoggingBeanAdapter(LoggingBean source) {
      this.delegate = source;
   }

   public String getFileName() {
      return this.delegate.getLogFilename();
   }

   public void setFileName(String fileName) {
      this.delegate.setLogFilename(fileName);
   }

   public String getRotationType() {
      return this.delegate.getRotationType();
   }

   public void setRotationType(String rotationType) {
      this.delegate.setRotationType(rotationType);
   }

   public boolean isNumberOfFilesLimited() {
      return this.delegate.isNumberOfFilesLimited();
   }

   public void setNumberOfFilesLimited(boolean value) {
      this.delegate.setNumberOfFilesLimited(value);
   }

   public int getFileCount() {
      return this.delegate.getFileCount();
   }

   public void setFileCount(int fileCount) {
      this.delegate.setFileCount(fileCount);
   }

   public int getFileTimeSpan() {
      return this.delegate.getFileTimeSpan();
   }

   public void setFileTimeSpan(int hours) {
      this.delegate.setFileTimeSpan(hours);
   }

   public int getFileMinSize() {
      return this.delegate.getFileSizeLimit();
   }

   public void setFileMinSize(int kBytes) {
      this.delegate.setFileSizeLimit(kBytes);
   }

   public String getRotationTime() {
      return this.delegate.getRotationTime();
   }

   public void setRotationTime(String logRotationTime) {
      this.delegate.setRotationTime(logRotationTime);
   }

   public boolean getRotateLogOnStartup() {
      return this.delegate.isRotateLogOnStartup();
   }

   public void setRotateLogOnStartup(boolean value) {
      this.delegate.setRotateLogOnStartup(value);
   }

   public String getLogFileRotationDir() {
      return this.delegate.getLogFileRotationDir();
   }

   public void setLogFileRotationDir(String dirPath) {
      this.delegate.setLogFileRotationDir(dirPath);
   }

   public String getLogFileSeverity() {
      return null;
   }

   public void setLogFileSeverity(String severity) {
   }

   public LogFilterMBean getLogFileFilter() {
      return null;
   }

   public void setLogFileFilter(LogFilterMBean filter) {
   }

   public String getStdoutSeverity() {
      return null;
   }

   public void setStdoutSeverity(String severity) {
   }

   public LogFilterMBean getStdoutFilter() {
      return null;
   }

   public void setStdoutFilter(LogFilterMBean filter) {
   }

   public String getDomainLogBroadcastSeverity() {
      return null;
   }

   public void setDomainLogBroadcastSeverity(String severity) {
   }

   public LogFilterMBean getDomainLogBroadcastFilter() {
      return null;
   }

   public void setDomainLogBroadcastFilter(LogFilterMBean filter) {
   }

   public String getMemoryBufferSeverity() {
      return null;
   }

   public void setMemoryBufferSeverity(String severity) {
   }

   public LogFilterMBean getMemoryBufferFilter() {
      return null;
   }

   public void setMemoryBufferFilter(LogFilterMBean filter) {
   }

   public int getMemoryBufferSize() {
      return 0;
   }

   public void setMemoryBufferSize(int size) {
   }

   public boolean isLog4jLoggingEnabled() {
      return false;
   }

   public void setLog4jLoggingEnabled(boolean enableLog4j) {
   }

   public boolean isRedirectStdoutToServerLogEnabled() {
      return false;
   }

   public void setRedirectStdoutToServerLogEnabled(boolean captureStdout) {
   }

   public boolean isRedirectStderrToServerLogEnabled() {
      return false;
   }

   public void setRedirectStderrToServerLogEnabled(boolean captureStderr) {
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
      return "standard";
   }

   public void setStdoutFormat(String format) {
   }

   public boolean isStdoutLogStack() {
      return true;
   }

   public void setStdoutLogStack(boolean stack) {
   }

   public int getStacktraceDepth() {
      return -1;
   }

   public void setStacktraceDepth(int depth) {
   }

   public long getFileTimeSpanFactor() {
      return 3600000L;
   }

   public void setFileTimeSpanFactor(long factor) {
   }

   public OutputStream getOutputStream() {
      return this.outputStream;
   }

   public void setOutputStream(OutputStream os) {
      this.outputStream = os;
   }

   public int getBufferSizeKB() {
      return 8;
   }

   public void setBufferSizeKB(int size) {
   }

   public String getDateFormatPattern() {
      return this.delegate.getDateFormatPattern();
   }

   public void setDateFormatPattern(String dateFormat) {
      this.delegate.setDateFormatPattern(dateFormat);
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
      return this.delegate.getLogFileRotationDir();
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

   public boolean isLogMonitoringEnabled() {
      return false;
   }

   public void setLogMonitoringEnabled(boolean enabled) {
   }

   public int getLogMonitoringMaxThrottleMessageSignatureCount() {
      return 0;
   }

   public void setLogMonitoringMaxThrottleMessageSignatureCount(int length) {
   }
}
