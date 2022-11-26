package weblogic.management.mbeans.custom;

import java.io.File;
import java.security.AccessController;
import weblogic.management.DomainDir;
import weblogic.management.RuntimeDir;
import weblogic.management.RuntimeDir.Current;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LogFile extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = 7988658427199784266L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public LogFile() {
      this((ConfigurationMBeanCustomized)null);
   }

   public LogFile(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public String getLogFilePath() {
      String logFilePath = null;
      if (ManagementService.isRuntimeAccessInitialized()) {
         LogFileMBean logFile = (LogFileMBean)this.getMbean();
         String fileName = logFile.getFileName();
         if (fileName == null) {
            fileName = "logs/" + logFile.getName() + ".log";
         }

         logFilePath = computePathRelativeServerDir(fileName);
      }

      return logFilePath;
   }

   public String computeLogFilePath() {
      return this.getLogFilePath();
   }

   public String getLogRotationDirPath() {
      String logRotationDirPath = null;
      if (ManagementService.isRuntimeAccessInitialized()) {
         LogFileMBean logFile = (LogFileMBean)this.getMbean();
         String configuredValue = logFile.getLogFileRotationDir();
         if (configuredValue != null && !configuredValue.isEmpty()) {
            logRotationDirPath = computePathRelativeServerDir(configuredValue);
         } else {
            logRotationDirPath = (new File(this.getLogFilePath())).getParent();
         }
      }

      return logRotationDirPath;
   }

   private static String computePathRelativeServerDir(String fileName) {
      File f = new File(fileName);
      RuntimeDir runtimeDir = Current.get();
      if (f.isAbsolute() && runtimeDir == DomainDir.getInstance()) {
         return f.getAbsolutePath();
      } else {
         String serverName = ManagementService.getPropertyService(kernelId).getServerName();
         return runtimeDir.getPathRelativeServerDir(serverName, fileName);
      }
   }
}
