package weblogic.management.mbeans.custom;

import java.text.ParseException;
import javax.management.InvalidAttributeValueException;
import weblogic.common.internal.VersionInfo;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WebServerLogMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.servlet.logging.LogMigrationProcessor;

public class WebServer extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = -1873044084609391595L;
   private static final boolean DEBUG = false;
   private boolean loggingEnabled = true;
   private String logFileFormat = "MM-dd-yyyy-k:mm:ss";
   private boolean logTimeInGMT = false;
   private String logFileName = "logs/access.log";
   private String logFileRotationType = "size";
   private int logRotationPeriodMins = 1440;
   private String logRotationTimeBegin;
   private boolean logFileLimitEnabled = true;
   private int logFileCount = 7;
   private static final VersionInfo diabloVersion = new VersionInfo("9.0.0.0");

   public WebServer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setLoggingEnabled(boolean enable) {
      this.loggingEnabled = enable;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (enable == log.isLoggingEnabled()) {
            return;
         }

         log.setLoggingEnabled(enable);
      }

   }

   public boolean isLoggingEnabled() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().isLoggingEnabled() : this.loggingEnabled;
   }

   public String getLogFileFormat() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().getLogFileFormat() : this.logFileFormat;
   }

   public void setLogFileFormat(String format) throws InvalidAttributeValueException, DistributedManagementException {
      this.logFileFormat = format;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (format != null && format.equals(log.getLogFileFormat())) {
            return;
         }

         log.setLogFileFormat(format);
      }

   }

   public boolean getLogTimeInGMT() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().isLogTimeInGMT() : this.logTimeInGMT;
   }

   public void setLogTimeInGMT(boolean useGMT) {
      this.logTimeInGMT = useGMT;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (useGMT == log.isLogTimeInGMT()) {
            return;
         }

         log.setLogTimeInGMT(useGMT);
      }

   }

   public String getLogFileName() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().getFileName() : this.logFileName;
   }

   public void setLogFileName(String fileName) throws InvalidAttributeValueException {
      if (fileName != null && fileName.length() != 0) {
         this.logFileName = fileName;
         if (!this.logFileName.equals("logs/access.log")) {
            if (this.isDelegateModeEnabled()) {
               WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
               log.setFileName(this.logFileName);
            }

         }
      }
   }

   public String getLogRotationType() {
      if (!this.isDelegateModeEnabled()) {
         return this.logFileRotationType;
      } else {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         String type = log.getRotationType();
         if (type != null && !type.equals("bySize")) {
            if (type.equals("byTime")) {
               type = "date";
            }
         } else {
            type = "size";
         }

         return type;
      }
   }

   public void setLogRotationType(String type) throws InvalidAttributeValueException {
      this.logFileRotationType = type;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (type != null && !type.equalsIgnoreCase("size")) {
            if (type.equalsIgnoreCase("date")) {
               type = "byTime";
            }
         } else {
            type = "bySize";
         }

         if (type != null && type.equals(log.getRotationType())) {
            return;
         }

         log.setRotationType(type);
      }

   }

   public int getLogRotationPeriodMins() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().getFileTimeSpan() * 60 : this.logRotationPeriodMins;
   }

   public void setLogRotationPeriodMins(int mins) throws InvalidAttributeValueException, DistributedManagementException {
      this.logRotationPeriodMins = mins;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (mins / 60 == log.getFileTimeSpan()) {
            return;
         }

         log.setFileTimeSpan(mins / 60);
      }

   }

   public String getLogRotationTimeBegin() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().getRotationTime() : this.logRotationTimeBegin;
   }

   public void setLogRotationTimeBegin(String when) throws InvalidAttributeValueException {
      this.logRotationTimeBegin = when;
      if (this.isDelegateModeEnabled()) {
         if (this.logRotationTimeBegin == null || this.logRotationTimeBegin.length() == 0) {
            return;
         }

         try {
            String newFormat = LogMigrationProcessor.convertLogRotationTimeBegin(when);
            ((WebServerMBean)this.getMbean()).getWebServerLog().setRotationTime(newFormat);
         } catch (ParseException var4) {
            String msg = ManagementTextTextFormatter.getInstance().getTimeFormatErrorMessage("MM-dd-yyyy-k:mm:ss", when);
            throw new IllegalArgumentException(msg);
         }
      }

   }

   public boolean isLogFileLimitEnabled() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().isNumberOfFilesLimited() : this.logFileLimitEnabled;
   }

   public void setLogFileLimitEnabled(boolean value) throws InvalidAttributeValueException {
      this.logFileLimitEnabled = value;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (value == log.isNumberOfFilesLimited()) {
            return;
         }

         try {
            log.setNumberOfFilesLimited(value);
         } catch (DistributedManagementException var4) {
            throw new IllegalArgumentException(var4.getMessage());
         }
      }

   }

   public int getLogFileCount() {
      return this.isDelegateModeEnabled() ? ((WebServerMBean)this.getMbean()).getWebServerLog().getFileCount() : this.logFileCount;
   }

   public void setLogFileCount(int value) throws InvalidAttributeValueException {
      this.logFileCount = value;
      if (this.isDelegateModeEnabled()) {
         WebServerLogMBean log = ((WebServerMBean)this.getMbean()).getWebServerLog();
         if (log == null) {
            return;
         }

         if (value == log.getFileCount()) {
            return;
         }

         try {
            log.setFileCount(value);
         } catch (DistributedManagementException var4) {
            throw new IllegalArgumentException(var4.getMessage());
         }
      }

   }

   protected boolean isDelegateModeEnabled() {
      DomainMBean root = (DomainMBean)this.getMbean().getDescriptor().getRootBean();
      String configurationVersionString = root.getConfigurationVersion();
      if (configurationVersionString == null) {
         return false;
      } else {
         VersionInfo configurationVersion = new VersionInfo(configurationVersionString);
         return !configurationVersion.earlierThan(diabloVersion);
      }
   }

   public void setMaxRequestParamterCount(int limit) throws InvalidAttributeValueException {
      if (!((WebServerMBean)this.getMbean()).isMaxRequestParameterCountSet() && limit != 10000) {
         ((WebServerMBean)this.getMbean()).setMaxRequestParameterCount(limit);
         if (((WebServerMBean)this.getMbean()).isMaxRequestParamterCountSet()) {
            ((WebServerMBean)this.getMbean()).unSet("MaxRequestParamterCount");
         }
      }

   }

   public int getMaxRequestParamterCount() {
      return ((WebServerMBean)this.getMbean()).getMaxRequestParameterCount();
   }
}
