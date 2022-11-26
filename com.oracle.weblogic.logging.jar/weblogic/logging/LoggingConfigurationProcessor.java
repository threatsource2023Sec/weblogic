package weblogic.logging;

import java.io.File;
import java.io.IOException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;
import weblogic.utils.PlatformConstants;

public class LoggingConfigurationProcessor implements ConfigurationProcessor {
   private static String FILE_NAME_ATTR = "FileName";
   private static final boolean DEBUG = false;

   public void updateConfiguration(DomainMBean root) throws UpdateException {
      ServerMBean[] servers = root.getServers();

      for(int i = 0; i < servers.length; ++i) {
         this.upgradeServerLogConfiguration(root, servers[i]);
      }

      LogMBean log = root.getLog();
      if (isLogFileNameDefaulted(log)) {
         unsetLogFileName(log);
      }

   }

   public void upgradeServerLogConfiguration(DomainMBean root, ServerMBean smb) {
      LogMBean log = smb.getLog();
      String severity = getNormalizedStdoutSeverity(smb.isStdoutEnabled(), smb.getStdoutSeverityLevel(), smb.isStdoutDebugEnabled());
      if (log.getStdoutSeverity() == null || log.getStdoutSeverity().equals("Notice") && !severity.equals("Notice")) {
         log.setStdoutSeverity(severity);
      }

      if (smb.isSet("StdoutFormat")) {
         log.setStdoutFormat(smb.getStdoutFormat());
      }

      if (smb.isSet("StdoutLogStack")) {
         log.setStdoutLogStack(smb.isStdoutLogStack());
      }

      if (isLogFileNameDefaulted(log)) {
         unsetLogFileName(log);
      }

   }

   private static void unsetLogFileName(LogMBean log) {
      log.unSet(FILE_NAME_ATTR);
   }

   private static boolean isLogFileNameDefaulted(LogMBean log) {
      String fileName = log.getFileName();
      String defaultName = log.getName() + ".log";
      if (fileName.equals(defaultName)) {
         return true;
      } else {
         File logFile = new File(log.getFileName());
         if (logFile.isAbsolute()) {
            return false;
         } else {
            File parentDir = null;
            boolean isServerLog = log.getParent() instanceof ServerMBean;
            if (isServerLog) {
               parentDir = new File(DomainDir.getRootDir() + PlatformConstants.FILE_SEP + log.getName());
            } else {
               parentDir = new File(DomainDir.getRootDir());
            }

            File oldDefault = new File(parentDir, log.getName() + ".log");

            try {
               boolean canonicalCompare = oldDefault.getCanonicalFile().equals(logFile.getCanonicalFile());
               return canonicalCompare;
            } catch (IOException var8) {
               return oldDefault.equals(logFile);
            }
         }
      }
   }

   public static String convertOldAttrsToFilterExpression(String[] userIds, String[] subsystemNames) {
      String filterExpr = "";
      if (userIds != null && userIds.length > 0) {
         filterExpr = "USERID IN " + convertToQueryParams(userIds);
      }

      if (subsystemNames != null && subsystemNames.length > 0) {
         String condition = "SUBSYSTEM IN " + convertToQueryParams(subsystemNames);
         if (filterExpr.length() > 0) {
            filterExpr = filterExpr + " AND " + condition;
         } else {
            filterExpr = condition;
         }
      }

      return filterExpr;
   }

   private static String convertToQueryParams(String[] args) {
      StringBuilder buffer = new StringBuilder();
      buffer.append("('");
      buffer.append(args[0]);
      buffer.append("'");

      for(int i = 1; i < args.length; ++i) {
         buffer.append(",'");
         buffer.append(args[i]);
         buffer.append("'");
      }

      buffer.append(")");
      return buffer.toString();
   }

   public static String getNormalizedStdoutSeverity(boolean stdoutEnabled, int stdoutSeverityLevel, boolean stdoutDebugEnabled) {
      stdoutSeverityLevel = Math.max(stdoutSeverityLevel, stdoutDebugEnabled ? 128 : stdoutSeverityLevel);
      if (!stdoutEnabled) {
         stdoutSeverityLevel = 0;
      }

      return Severities.severityNumToString(stdoutSeverityLevel);
   }
}
