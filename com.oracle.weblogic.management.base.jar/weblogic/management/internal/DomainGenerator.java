package weblogic.management.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class DomainGenerator {
   protected static final ManagementTextTextFormatter mgmtTextFormatter = ManagementTextTextFormatter.getInstance();
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected final String domainName;
   protected final String serverName;
   protected final String listenAddress;
   protected final String listenPort;
   protected final File configFile;

   public DomainGenerator() {
      String dn = BootStrap.getDomainName();
      this.domainName = dn == null ? "mydomain" : dn;
      String sn = BootStrap.getServerName();
      this.serverName = sn == null ? "myserver" : sn;
      this.configFile = new File(DomainDir.getConfigDir());
      this.listenAddress = System.getProperty("weblogic.ListenAddress");
      this.listenPort = System.getProperty("weblogic.ListenPort");
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("configFile: " + this.configFile);
         debugLogger.debug("domainName: " + this.domainName);
         debugLogger.debug("serverName: " + this.serverName);
         debugLogger.debug("PropertyService: " + ManagementService.getPropertyService(kernelId));
      }

   }

   public final void generateDefaultDomain() throws ManagementException {
      this.generateDefaultDomain(true);
   }

   public void generateDefaultDomain(boolean prompt) throws ManagementException {
      String configFilePath = this.configFile.getAbsolutePath();
      if (prompt) {
         this.validateGeneration();
      }

      this.logNoConfig(configFilePath);

      try {
         this.validateConfigFramework();
         ManagementService.getPropertyService(kernelId).initializeSecurityProperties(true);
         String ts1 = ManagementService.getPropertyService(kernelId).getTimestamp1();
         String ts2 = ManagementService.getPropertyService(kernelId).getTimestamp2();
         String root = DomainDir.getRootDir();
         if (root == null) {
            root = (new File(".")).getAbsolutePath();
         } else {
            root = (new File(root)).getAbsolutePath();
         }

         ManagementLogger.logGeneratingDomainDirectory(root);
         long t0 = System.currentTimeMillis();
         this.generateDefaultDomain(root, ts1, ts2);
         long t1 = System.currentTimeMillis();
         ManagementLogger.logDomainDirectoryGenerationComplete(t1 - t0);
      } catch (Exception var10) {
         throw new ManagementException("Failure during domain creation", var10);
      }
   }

   public abstract void validateConfigFramework() throws ManagementException;

   public abstract void generateDefaultDomain(String var1, String var2, String var3) throws Exception;

   private void logNoConfig(String configFilePath) {
      String msg = mgmtTextFormatter.failedToLocateConfigFile(configFilePath);
      ManagementLogger.logFailedToFindConfig(msg);
   }

   private void validateGeneration() throws InteractiveConfigurationException {
      boolean willGenerate = willGenerateConfigBasedOnProps() || willGenerateConfigInteractively(this.configFile);
      if (!willGenerate) {
         String noCfgMsg = mgmtTextFormatter.failedToLocateConfigFile(this.configFile.getAbsolutePath());
         String noGenMsg = mgmtTextFormatter.noConfigFileWillNotGenerate(noCfgMsg, "weblogic.management.GenerateDefaultConfig");
         throw new InteractiveConfigurationException(noGenMsg);
      }
   }

   private static boolean willGenerateConfigInteractively(File nonExistantConfigFile) {
      if (willGenerateConfigBasedOnProps()) {
         return true;
      } else {
         String affirmitave = mgmtTextFormatter.getAffirmitaveGenerateConfigText();
         String negative = mgmtTextFormatter.getNegativeGenerateConfigText();
         int attempts = 1;

         String configFilePath;
         try {
            configFilePath = nonExistantConfigFile.getCanonicalPath();
         } catch (IOException var8) {
            configFilePath = nonExistantConfigFile.getAbsolutePath();
         }

         System.out.println("\n" + mgmtTextFormatter.failedToLocateConfigFile(configFilePath));

         while(attempts < 4) {
            String answer = null;

            try {
               BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
               System.out.print("\n" + mgmtTextFormatter.getGenerateDefaultConfigInteractively(affirmitave, negative) + ": ");
               answer = reader.readLine();
               if (answer != null && answer.equalsIgnoreCase(affirmitave)) {
                  return true;
               }

               if (answer != null && answer.equalsIgnoreCase(negative)) {
                  return false;
               }

               System.out.println("\n" + mgmtTextFormatter.getPleaseConfirmDeny(affirmitave, negative));
               ++attempts;
            } catch (Exception var7) {
               ++attempts;
               debugLogger.debug("Unexpected Exception: " + var7, var7);
            }
         }

         return false;
      }
   }

   private static boolean willGenerateConfigBasedOnProps() {
      if (System.getProperty("gdc") != null) {
         return true;
      } else {
         return System.getProperty("weblogic.management.GenerateDefaultConfig") != null;
      }
   }
}
