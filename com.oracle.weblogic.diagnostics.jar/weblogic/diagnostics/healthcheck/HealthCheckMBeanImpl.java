package weblogic.diagnostics.healthcheck;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSourceOutputStream;
import weblogic.diagnostics.utils.SecurityHelper;
import weblogic.logging.NonCatalogLogger;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.jars.ManifestManager;

public class HealthCheckMBeanImpl extends StandardMBean implements HealthCheckMBean {
   private static final String DEFAULT_HEALTH_CHECK_EXTENSION = ".txt";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static HealthCheckMBeanImpl instance;
   private ObjectName objName;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugHealthCheck");
   private static final NonCatalogLogger logger = new NonCatalogLogger("HealthCheckService");
   private Map healthChecks = new HashMap();
   private boolean discoveryComplete = false;
   private String absoluteImageDestinationPath;

   public static synchronized HealthCheckMBeanImpl getInstance() {
      if (instance == null) {
         RuntimeAccess runtime = (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);

         try {
            instance = new HealthCheckMBeanImpl(runtime.getServerRuntime().getName());
         } catch (Throwable var2) {
            logger.error("Unexpected error initializing the health check service MBean", var2);
         }
      }

      return instance;
   }

   HealthCheckMBeanImpl(String name) throws NotCompliantMBeanException, MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException {
      super(HealthCheckMBean.class);
   }

   public int execute(String fileName, Properties properties) {
      this.checkAccess();
      return this.executeDesiredHealthChecks(fileName, properties, this.getDesiredHealthChecks((String[])null));
   }

   public int execute(String fileName, String[] healthCheckNames, Properties properties) {
      this.checkAccess();
      Collection healthChecksToExecute = this.getDesiredHealthChecks(healthCheckNames);
      return this.executeDesiredHealthChecks(fileName, properties, healthChecksToExecute);
   }

   private Collection getDesiredHealthChecks(String[] healthCheckNames) {
      this.discoverHealthChecks();
      Object desiredSet;
      if (healthCheckNames != null) {
         desiredSet = new ArrayList();
         String[] var3 = healthCheckNames;
         int var4 = healthCheckNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String checkName = var3[var5];
            HealthCheck check = (HealthCheck)this.healthChecks.get(checkName);
            if (check != null) {
               ((Collection)desiredSet).add(check);
            } else {
               logger.warning("Requested health check " + checkName + " not found");
            }
         }
      } else {
         desiredSet = this.healthChecks.values();
      }

      return (Collection)desiredSet;
   }

   public String[] getHealthCheckNames() {
      this.checkAccess();
      this.discoverHealthChecks();
      Set typeSet = this.healthChecks.keySet();
      return (String[])typeSet.toArray(new String[typeSet.size()]);
   }

   private int executeDesiredHealthChecks(String fileName, Properties properties, Collection healthChecksToExecute) {
      int failures = 0;

      try {
         String zipFileName = this.getAbsoluteDestinationPath(fileName);
         logger.info("Executing " + healthChecksToExecute.size() + " health checks and recording to " + zipFileName);
         OutputStream healthCheckOutputStream = new BufferedOutputStream(new FileOutputStream(zipFileName));
         ZipOutputStream zipOutputStream = new ZipOutputStream(healthCheckOutputStream);

         try {
            this.recordPrologue(healthChecksToExecute, zipOutputStream);
            Iterator var8 = healthChecksToExecute.iterator();

            while(var8.hasNext()) {
               HealthCheck healthCheck = (HealthCheck)var8.next();
               if (!this.executeHealthCheck(healthCheck, properties, zipOutputStream)) {
                  ++failures;
               }
            }
         } finally {
            zipOutputStream.flush();
            zipOutputStream.close();
         }
      } catch (Exception var14) {
         logger.error("IOException occured executing health checks", var14);
         failures = -1;
      }

      return failures;
   }

   public void registerHealthCheck(HealthCheck check) {
      this.checkAccess();
      this.healthChecks.put(check.getType(), check);
      logger.info("Registered health check " + check.getType() + " successfully with health check service");
   }

   public void unregisterHealthCheck(HealthCheck check) {
      this.checkAccess();
      logger.info("Unregistering health check " + check.getType());
      if (this.healthChecks.remove(check.getType()) == null) {
         logger.warning("No health check of type " + check.getType() + " registered with service");
      }

   }

   private void recordPrologue(Collection healthChecksToExecute, ZipOutputStream zipOutputStream) throws IOException {
      ZipEntry summaryEntry = new ZipEntry("CheckSummary.txt");
      zipOutputStream.putNextEntry(summaryEntry);
      PrintWriter pw = new PrintWriter(zipOutputStream);
      if (healthChecksToExecute.size() > 0) {
         pw.println("Health checks to execute: " + Arrays.toString(this.buildNamesList(healthChecksToExecute)));
      } else {
         pw.println("No health checks to execute");
      }

      pw.flush();
   }

   private boolean executeHealthCheck(HealthCheck healthCheck, Properties properties, ZipOutputStream zipOutputStream) throws IOException {
      boolean success = true;
      String healthCheckType = healthCheck.getType();
      String desiredextension = healthCheck.getExtension();
      String entryExtension = desiredextension == null ? ".txt" : desiredextension;
      ZipEntry testEntry = new ZipEntry(healthCheckType + entryExtension);
      zipOutputStream.putNextEntry(testEntry);

      try {
         logger.info("Executing health check " + healthCheckType);
         success = healthCheck.execute(properties, new ImageSourceOutputStream(zipOutputStream));
      } catch (Throwable var13) {
         success = false;
         this.writeExceptionToStream(zipOutputStream, healthCheckType, var13);
      } finally {
         zipOutputStream.closeEntry();
         zipOutputStream.flush();
         logger.info("Completed health check " + healthCheckType);
      }

      return success;
   }

   private void writeExceptionToStream(ZipOutputStream zipOutputStream, String healthCheckType, Throwable t) {
      PrintWriter pw = new PrintWriter(zipOutputStream);
      pw.print("Exception occurred executing health check ");
      pw.println(healthCheckType);
      t.printStackTrace(pw);
      pw.flush();
   }

   private synchronized void discoverHealthChecks() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Discovering health check implementations");
      }

      if (!this.discoveryComplete) {
         ArrayList services = ManifestManager.getServices(HealthCheck.class);
         Iterator serviceIt = services.iterator();
         int totalChecksFound = 0;

         while(serviceIt.hasNext()) {
            HealthCheck healthCheck = (HealthCheck)serviceIt.next();
            ++totalChecksFound;
            logger.info("Discovered health check " + healthCheck.getType());
            this.healthChecks.put(healthCheck.getType(), healthCheck);
         }

         logger.info("Discovered " + totalChecksFound + " checks");
         Iterator allChecksIt = this.healthChecks.entrySet().iterator();

         while(allChecksIt.hasNext()) {
            Map.Entry healthCheckEntry = (Map.Entry)allChecksIt.next();
            HealthCheck healthCheck = (HealthCheck)healthCheckEntry.getValue();

            try {
               logger.info("Initializing health check " + healthCheck.getType());
               healthCheck.initialize();
            } catch (Throwable var8) {
               allChecksIt.remove();
               logger.error("Error initializing health check " + healthCheck.getType() + ", not included in active set of checks", var8);
            }
         }

         this.discoveryComplete = true;
      }

   }

   private String getAbsoluteDestinationPath(String fileName) throws Exception {
      String destinationPath = this.getAbsoluteImageDestinationPath();
      File file = new File(destinationPath);
      if (!file.exists() && !file.mkdirs()) {
         throw new Exception("Unable to create destination path: " + file.getAbsolutePath());
      } else {
         return destinationPath + File.separator + fileName + ".zip";
      }
   }

   private String getAbsoluteImageDestinationPath() {
      if (this.absoluteImageDestinationPath == null) {
         ImageManager imageManager = (ImageManager)LocatorUtilities.getService(ImageManager.class);
         this.absoluteImageDestinationPath = imageManager.getDestinationDirectory();
      }

      return this.absoluteImageDestinationPath;
   }

   private void checkAccess() {
      SecurityHelper.checkForAdminRole();
   }

   private String[] buildNamesList(Collection collection) {
      List healthCheckNames = new ArrayList();
      Iterator var3 = collection.iterator();

      while(var3.hasNext()) {
         HealthCheck healthCheck = (HealthCheck)var3.next();
         healthCheckNames.add(healthCheck.getType());
      }

      return (String[])healthCheckNames.toArray(new String[healthCheckNames.size()]);
   }
}
