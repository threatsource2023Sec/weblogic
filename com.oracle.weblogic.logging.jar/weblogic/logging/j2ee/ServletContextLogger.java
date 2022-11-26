package weblogic.logging.j2ee;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileStreamHandler;
import java.util.logging.Logger;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.kernel.KernelLogManager;
import weblogic.logging.LogFileConfigUtil;
import weblogic.logging.LogFileFormatter;
import weblogic.logging.LogMgmtLogger;
import weblogic.logging.WLErrorManager;
import weblogic.logging.WLLevel;
import weblogic.logging.WLLogRecord;
import weblogic.logging.WLLogger;

public final class ServletContextLogger {
   private String contextName;
   private LoggingBeanAdapter logAdapter = null;
   private Logger logger = KernelLogManager.getLogger();
   private String logFilePath;
   private String logRotationDirPath;

   public ServletContextLogger(String ctxName, LoggingBean loggingBean) {
      this.contextName = ctxName;
      if (loggingBean != null) {
         String logFileName = loggingBean.getLogFilename();
         if (logFileName != null && logFileName.length() > 0) {
            WLLogger wlLogger = new WLLogger(this.contextName);
            this.logAdapter = new LoggingBeanAdapter(loggingBean);
            LogFileConfigBean logFileConfig = LogFileConfigUtil.getLogFileConfig(this.logAdapter);
            this.logFilePath = this.getLogPath(this.logAdapter.getFileName());
            logFileConfig.setBaseLogFileName(this.logFilePath);
            String rotationDir = this.logAdapter.getLogFileRotationDir();
            if (rotationDir != null && !rotationDir.isEmpty()) {
               this.logRotationDirPath = this.getLogPath(rotationDir);
               logFileConfig.setLogFileRotationDir(this.logRotationDirPath);
            }

            try {
               RotatingFileStreamHandler fsh = new RotatingFileStreamHandler(logFileConfig);
               fsh.setFormatter(new LogFileFormatter(this.logAdapter));
               fsh.setErrorManager(new WLErrorManager(fsh));
               wlLogger.addHandler(fsh);
               this.logger = wlLogger;
            } catch (Exception var8) {
               LogMgmtLogger.logErrorInitializingServletContextLogger(this.contextName, var8);
            }
         }

      }
   }

   private String getLogPath(String path) {
      return LogFileConfigUtil.computePathRelativeServersLogsDir(path);
   }

   public LoggingBeanAdapter getLogAdapter() {
      return this.logAdapter;
   }

   public void log(String msg) {
      WLLogRecord rec = new WLLogRecord(WLLevel.INFO, msg);
      rec.setLoggerName(this.contextName);
      this.logger.log(rec);
   }

   public void logError(String msg) {
      WLLogRecord rec = new WLLogRecord(WLLevel.ERROR, msg);
      rec.setLoggerName(this.contextName);
      this.logger.log(rec);
   }

   public void log(String msg, Throwable throwable) {
      WLLogRecord rec = new WLLogRecord(WLLevel.ERROR, msg, throwable);
      rec.setLoggerName(this.contextName);
      this.logger.log(rec);
   }

   public String getLogFilePath() {
      return this.logFilePath;
   }

   public String getLogRotationDirPath() {
      return this.logRotationDirPath;
   }
}
