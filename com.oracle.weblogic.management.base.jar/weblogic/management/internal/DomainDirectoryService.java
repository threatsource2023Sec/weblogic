package weblogic.management.internal;

import java.io.File;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public final class DomainDirectoryService extends AbstractServerService {
   @Inject
   @Named("PropertyService")
   private ServerService dependencyOnPropertyService;
   @Inject
   @Named("PreConfigBootService")
   private ServerService dependencyOnPreConfigBootService;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final boolean nodeManagerBoot = Boolean.getBoolean("weblogic.system.NodeManagerBoot");
   private static final String startmode = System.getProperty("weblogic.management.startmode");
   private static ManagementTextTextFormatter mgmtTextFormatter = ManagementTextTextFormatter.getInstance();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      try {
         if (isCreateNeeded()) {
            if (nodeManagerBoot || "WinSvc".equalsIgnoreCase(startmode)) {
               File configDir = new File(DomainDir.getConfigDir());
               File configFile = new File(configDir, "config.xml");
               String configFilePath = configFile.getAbsolutePath();
               String msg = mgmtTextFormatter.failedToLocateConfigFile(configFilePath);
               throw new ConfigurationException(msg);
            }

            DomainGenerator domainGenerator = new CIEDomainGenerator();
            domainGenerator.generateDefaultDomain(true);
         }

         ServerLocks.getServerLock();
      } catch (ManagementException var5) {
         throw new ServiceFailureException(var5.getMessage(), var5);
      } catch (Exception var6) {
         throw new ServiceFailureException(var6.getMessage());
      }
   }

   public void stop() throws ServiceFailureException {
      ServerLocks.releaseServerLock();
   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   public static boolean isCreateNeeded() throws ManagementException {
      return isCreateNeeded(new File(DomainDir.getRootDir()));
   }

   public static boolean isCreateNeeded(File rootDir) throws ManagementException {
      if (!ManagementService.getPropertyService(kernelId).isAdminServer()) {
         return false;
      } else {
         File configDir = new File(rootDir, "config");
         File rootCfg = new File(rootDir, BootStrap.getConfigFileName());
         File configCfg = new File(configDir, "config.xml");
         File parentCfg = findParentConfig(rootDir);
         if (!rootCfg.exists() && !configCfg.exists()) {
            if (parentCfg != null && parentCfg.exists()) {
               ConfigLogger.logConfigXMLFoundInParentDir(parentCfg.getAbsolutePath());
            }

            return true;
         } else {
            return false;
         }
      }
   }

   private static File findParentConfig(File dir) {
      File result = null;
      String parentDir = dir.getParent();
      if (parentDir != null) {
         result = new File(parentDir, "config.xml");
      }

      return result;
   }
}
