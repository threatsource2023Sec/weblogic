package weblogic.upgrade.domain;

import java.util.Properties;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.version;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.LoggingHelper;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.upgrade.ConfigFileHelper;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.upgrade.Upgrade;
import weblogic.upgrade.UpgradeContext;

@Service
@Named
@RunLevel(5)
public class DomainUpgradeServerService extends AbstractServerService {
   @Inject
   @Named("DomainDirectoryService")
   private ServerService dependencyOnDomainDirectoryService;
   @Inject
   private Provider propertyService;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDomainUpgradeServerService");

   public void start() throws ServiceFailureException {
      try {
         if (ConfigFileHelper.isUpgradeNeeded()) {
            ManagementLogger.logDomainUpgrading(version.getReleaseBuildVersion());
            this.doUpgrade();
         }

      } catch (ManagementException var2) {
         throw new ServiceFailureException(var2.getMessage(), var2);
      }
   }

   public void doUpgrade() throws ServiceFailureException {
      try {
         if (!Boolean.getBoolean("weblogic.ProductionModeEnabled")) {
            SerializedSystemIni.getEncryptionService();
         }

         Logger serverLog = LoggingHelper.getServerLogger();
         UpgradeContext upgradeContext = new ServerUpgradeContext(serverLog);
         this.propertyService.get();
         Upgrade.upgradeDomain(DomainDir.getRootDir(), upgradeContext, (Properties)null);
      } catch (Exception var3) {
         throw new ServiceFailureException(var3);
      }
   }

   public void halt() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }

   private class ServerUpgradeContext implements UpgradeContext {
      private Logger logger;

      public ServerUpgradeContext(Logger log) {
         this.logger = log;
      }

      public Logger getLogger() {
         return this.logger;
      }
   }
}
