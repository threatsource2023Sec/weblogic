package weblogic.management.provider.internal;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.FileOwnerFixer;
import weblogic.utils.jars.JarFileUtils;

public class ConfigBackup {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final File configDir = new File(DomainDir.getConfigDir());
   private static final File archiveDir = new File(DomainDir.getRootDir(), "configArchive");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String CONFIG_BASE = "config-";

   static void saveBooted() throws IOException {
      File target = new File(DomainDir.getRootDir(), "config-booted.jar");
      createJarFileFromConfig(target);
   }

   static void saveOriginal() throws IOException {
      File target = new File(DomainDir.getRootDir(), "config-original.jar");
      FileOwnerFixer.addPathJDK6(target);
      createJarFileFromConfig(target);
   }

   static void saveVersioned() throws IOException {
      int maxVersion = 3;
      if (KernelStatus.isServer()) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         if (domain == null) {
            return;
         }

         maxVersion = domain.getArchiveConfigurationCount();
      }

      if (maxVersion > 0) {
         if (!archiveDir.exists()) {
            archiveDir.mkdir();
         }

         if (!archiveDir.isDirectory()) {
            throw new IOException(archiveDir.getCanonicalPath() + " is not a directory");
         } else {
            File[] archiveFiles = archiveDir.listFiles();
            if (archiveFiles != null && archiveFiles.length != 0) {
               int minVer = -1;
               int maxVer = -1;
               int count = 0;

               int currVer;
               for(currVer = 0; currVer < archiveFiles.length; ++currVer) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("saveVersioned: existing archive file = " + archiveFiles[currVer]);
                  }

                  int val = getVersion(archiveFiles[currVer]);
                  if (val >= 0) {
                     ++count;
                     if (minVer == -1 || minVer > val) {
                        minVer = val;
                     }

                     if (maxVer == -1 || maxVer < val) {
                        maxVer = val;
                     }
                  }
               }

               if (count > 0) {
                  currVer = maxVer + 1;
                  File minFile;
                  if (count >= maxVersion) {
                     minFile = getVersionedArchiveName(minVer);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("saveVersioned: delete existing archive file = " + minFile);
                     }

                     boolean gone = !minFile.delete();
                     if (gone) {
                        String msg = "Unable to delete old archive " + minFile.getCanonicalPath();
                        throw new IOException(msg);
                     }
                  }

                  minFile = getVersionedArchiveName(currVer);
                  createJarFileFromConfig(minFile);
               }
            } else {
               File target = getVersionedArchiveName(1);
               createJarFileFromConfig(target);
            }

         }
      }
   }

   private static int getVersion(File f) {
      if (f == null) {
         return -1;
      } else {
         String filename = f.getName();
         if (filename.startsWith("config-") && filename.endsWith(".jar")) {
            int start = filename.indexOf("config-") + "config-".length();
            String tmp = filename.substring(start);
            int end = tmp.indexOf(".jar");
            tmp = tmp.substring(0, end);

            try {
               return Integer.parseInt(tmp);
            } catch (NumberFormatException var6) {
               return -1;
            }
         } else {
            return -1;
         }
      }
   }

   private static File getVersionedArchiveName(int ver) {
      return new File(archiveDir, "config-" + ver + ".jar");
   }

   private static void createJarFileFromConfig(File jarFile) throws IOException {
      createJarFileFromConfig(jarFile.getPath());
   }

   private static void createJarFileFromConfig(String jarFileName) throws IOException {
      String fullpath = (new File(jarFileName)).getAbsolutePath();
      if (!configDir.isDirectory()) {
         IOException ioe = new IOException(configDir + " is not a directory");
         ManagementLogger.logCouldNotBackupConfiguration(fullpath, ioe);
         throw ioe;
      } else {
         ManagementLogger.logBackingUpConfiguration(fullpath);

         try {
            JarFileUtils.createJarFileFromDirectory(jarFileName, configDir);
         } catch (IOException var3) {
            ManagementLogger.logCouldNotBackupConfiguration(fullpath, var3);
            throw var3;
         }
      }
   }

   public static void main(String[] args) throws Exception {
      saveBooted();
      saveOriginal();
      saveVersioned();
   }
}
