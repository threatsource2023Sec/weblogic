package weblogic.logging;

import com.bea.logging.LogFileConfigBean;
import java.io.File;
import java.security.AccessController;
import weblogic.management.DomainDir;
import weblogic.management.RuntimeDir;
import weblogic.management.RuntimeDir.Current;
import weblogic.management.configuration.CommonLogMBean;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LogFileConfigUtil {
   private static final String DEFAULT_ROTATION_TIME = "00:00";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static LogFileConfigBean getLogFileConfig(LogFileMBean logFileMBean) {
      LogFileConfigBean logFileConfig = new LogFileConfigBean();
      logFileConfig.setBaseLogFileName(logFileMBean.getLogFilePath());
      logFileConfig.setRotateLogOnStartupEnabled(logFileMBean.getRotateLogOnStartup());
      String logFileRotationDir;
      if (logFileMBean instanceof CommonLogMBean) {
         logFileRotationDir = ((CommonLogMBean)logFileMBean).getLogFileSeverity();
         if (logFileRotationDir != null) {
            logFileConfig.setLogFileSeverity(logFileRotationDir);
         }
      }

      logFileConfig.setRotatedFileCount(logFileMBean.getFileCount());
      logFileConfig.setRotationSize(logFileMBean.getFileMinSize());
      logFileRotationDir = logFileMBean.getLogRotationDirPath();
      if (logFileRotationDir != null && logFileRotationDir.length() > 0) {
         logFileConfig.setLogFileRotationDir(logFileRotationDir);
      }

      logFileConfig.setNumberOfFilesLimited(logFileMBean.isNumberOfFilesLimited());
      String rotationTime = logFileMBean.getRotationTime();
      if (rotationTime == null || rotationTime.equals("")) {
         rotationTime = "00:00";
      }

      logFileConfig.setRotationTime(rotationTime);
      String rotationType = logFileMBean.getRotationType();
      if (rotationType == null || rotationType.equals("")) {
         rotationType = "bySize";
      }

      logFileConfig.setRotationType(rotationType);
      logFileConfig.setRotationTimeSpan(logFileMBean.getFileTimeSpan());
      logFileConfig.setRotationTimeSpanFactor(logFileMBean.getFileTimeSpanFactor());
      logFileConfig.setBufferSizeKB(logFileMBean.getBufferSizeKB());
      return logFileConfig;
   }

   public static String computePathRelativeServersLogsDir(String fileName) {
      File f = new File(fileName);
      String path = f.getAbsolutePath();
      RuntimeDir runtimeDir = Current.get();
      if (f.isAbsolute() && runtimeDir == DomainDir.getInstance()) {
         path = f.getAbsolutePath();
      } else {
         String serverName = ManagementService.getPropertyService(kernelId).getServerName();
         path = runtimeDir.getPathRelativeServersLogsDir(serverName, fileName);
      }

      return path;
   }
}
