package weblogic.servlet.logging;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.WebServerLogMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public final class LogMigrationProcessor implements ConfigurationProcessor {
   private static final SimpleDateFormat OLD_FORMAT = new SimpleDateFormat("MM-dd-yyyy-k:mm:ss");
   private static final SimpleDateFormat NEW_FORMAT = new SimpleDateFormat("H:mm");

   public void updateConfiguration(DomainMBean domain) throws UpdateException {
      ServerMBean[] servers = domain.getServers();
      if (servers != null && servers.length > 0) {
         for(int i = 0; i < servers.length; ++i) {
            try {
               this.upgradeConfig(servers[i].getWebServer());
            } catch (InvalidAttributeValueException var8) {
               throw new UpdateException(var8);
            } catch (DistributedManagementException var9) {
               throw new UpdateException(var9);
            }
         }
      }

      VirtualHostMBean[] vhs = domain.getVirtualHosts();
      if (vhs != null && vhs.length > 0) {
         for(int i = 0; i < vhs.length; ++i) {
            try {
               this.upgradeConfig(vhs[i]);
            } catch (InvalidAttributeValueException var6) {
               throw new UpdateException(var6);
            } catch (DistributedManagementException var7) {
               throw new UpdateException(var7);
            }
         }
      }

   }

   private void upgradeConfig(WebServerMBean ws) throws InvalidAttributeValueException, DistributedManagementException {
      WebServerLogMBean log = ws.getWebServerLog();
      if (ws.isSet("LoggingEnabled")) {
         log.setLoggingEnabled(ws.isLoggingEnabled());
      }

      if (ws.isSet("LogFileFormat")) {
         log.setLogFileFormat(ws.getLogFileFormat());
      }

      if (ws.isSet("LogTimeInGMT")) {
         log.setLogTimeInGMT(ws.getLogTimeInGMT());
      }

      if (ws.isSet("LogFileName")) {
         log.setFileName(ws.getLogFileName());
      }

      String oldTime;
      if (ws.isSet("LogRotationType")) {
         oldTime = ws.getLogRotationType();
         if (oldTime != null && !oldTime.equalsIgnoreCase("size")) {
            if (oldTime.equalsIgnoreCase("date")) {
               oldTime = "byTime";
            }
         } else {
            oldTime = "bySize";
         }

         log.setRotationType(oldTime);
      }

      if (ws.isSet("LogRotationTimeBegin")) {
         try {
            oldTime = ws.getLogRotationTimeBegin();
            if (oldTime != null && oldTime.length() > 0) {
               String newTime = convertLogRotationTimeBegin(oldTime);
               log.setRotationTime(newTime);
            }
         } catch (ParseException var6) {
         }
      }

      if (ws.isSet("LogRotationPeriodMins")) {
         log.setFileTimeSpan(ws.getLogRotationPeriodMins() / 60);
      }

      if (ws.isSet("LogFileLimitEnabled")) {
         log.setNumberOfFilesLimited(ws.isLogFileLimitEnabled());
      }

      if (ws.isSet("LogFileCount")) {
         log.setFileCount(ws.getLogFileCount());
      }

      if (ws.isSet("MaxLogFileSizeKBytes")) {
         if (ws.getMaxLogFileSizeKBytes() == 0) {
            log.setRotationType("none");
         } else {
            log.setFileMinSize(ws.getMaxLogFileSizeKBytes());
         }
      }

      if (log.getFileMinSize() < 1) {
         log.setRotationType("none");
      }

      try {
         File accessLogFile = new File(DomainDir.getRootDir() + File.separatorChar + ws.getLogFileName());
         if (!ws.isSet("LogFileName")) {
            accessLogFile = new File(DomainDir.getRootDir() + File.separatorChar + ws.getName() + File.separatorChar + "access.log");
            if (!accessLogFile.exists()) {
               accessLogFile = new File(DomainDir.getRootDir() + File.separatorChar + "access.log");
            }
         }

         if ("extended".equals(ws.getLogFileFormat()) && accessLogFile.exists()) {
            String[] headers = ELFLogger.readELFFields(accessLogFile.getPath());
            if (headers != null && headers[1] != null) {
               log.setELFFields(headers[1]);
            }
         }
      } catch (Exception var5) {
      }

   }

   public static final String convertLogRotationTimeBegin(String time) throws ParseException {
      Date d = OLD_FORMAT.parse(time);
      return NEW_FORMAT.format(d);
   }
}
