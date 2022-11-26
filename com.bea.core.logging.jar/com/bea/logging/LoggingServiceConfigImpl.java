package com.bea.logging;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class LoggingServiceConfigImpl implements LoggingServiceConfig, Serializable {
   private static final boolean DEBUG = false;
   private LogFileConfig logFileConfig;
   private StdoutConfig stdoutConfig;
   private Properties loggerSeverities = new Properties();
   private LogFileConfig tempLogFileConfig = null;
   private StdoutConfig tempStdoutConfig = null;
   private String tempLoggerSeverity = "Info";
   private Properties tempProps = null;

   public String getLoggerSeverity() {
      return LoggingService.getInstance().getLoggerSeverity();
   }

   public void setLoggerSeverity(String severity) {
      LoggingConfigValidator.validateSeverity(severity);
      this.tempLoggerSeverity = severity;
   }

   public LogFileConfig getLogFileConfig() {
      return this.logFileConfig;
   }

   public void setLogFileConfig(LogFileConfig config) {
      this.tempLogFileConfig = config;
   }

   public StdoutConfig getStdoutConfig() {
      return this.stdoutConfig;
   }

   public void setStdoutConfig(StdoutConfig config) {
      this.tempStdoutConfig = config;
   }

   public Properties getLoggerSeverities() {
      return this.loggerSeverities;
   }

   public void setLoggerSeverities(Properties props) {
      LoggingConfigValidator.validateLoggerSeverityProperties(props);
      this.tempProps = props;
   }

   public void prepare() {
   }

   public void activate() {
      this.applyTempVariables();
   }

   private void applyTempVariables() {
      this.logFileConfig = this.tempLogFileConfig;
      this.stdoutConfig = this.tempStdoutConfig;
      LoggingService.getInstance().setLoggerSeverity(this.tempLoggerSeverity);
      LoggingService.getInstance().setLoggerSeverities(this.tempProps);
      this.loggerSeverities = this.tempProps;
   }

   public boolean prepareUpdate(List changes) {
      return true;
   }

   public void activateUpdate(List changes) {
      this.applyTempVariables();
   }

   public void rollbackUpdate(List changes) {
      this.initTempVariables();
   }

   private void initTempVariables() {
      this.tempLogFileConfig = null;
      this.tempStdoutConfig = null;
      this.tempLoggerSeverity = "Info";
      this.tempProps = null;
   }
}
