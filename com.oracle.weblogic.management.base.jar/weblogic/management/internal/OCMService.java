package weblogic.management.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.Home;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(20)
public final class OCMService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;
   @Inject
   private ManagementFileLockService fileLockService;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static boolean DEBUG;
   private static final String OCM_DIR;
   private static final String FILE_NAME = "domainlocation.properties";
   private static final String DOMAIN_SEPARATOR = ",";
   private static final long LOCK_TIMEOUT_MILLIS = 30000L;

   public void start() {
      DomainMBean domainMBean = this.runtimeAccess.getDomain();
      if (!domainMBean.isOCMEnabled()) {
         if (DEBUG) {
            debug("OCM is disabled since domainMBean.isOCMEnabled()=" + domainMBean.isOCMEnabled());
         }

      } else {
         if (DEBUG) {
            debug("started");
         }

         if (!this.runtimeAccess.isAdminServer()) {
            if (DEBUG) {
               debug("Production mode but not an admin server. quit");
            }

         } else {
            WorkManager wm = WorkManagerFactory.getInstance().getDefault();
            if (DEBUG) {
               debug("scheduling the OCMRunnable ...");
            }

            wm.schedule(new OCMRunnable());
         }
      }
   }

   private void recordDomainRoot(File ocmFile, String machineName, String currentDomainRoot) throws IOException {
      FileLockHandle lock = null;
      Properties ocmProperties = new Properties();
      RandomAccessFile raf = null;

      try {
         raf = new RandomAccessFile(ocmFile, "rws");
         lock = this.lock(ocmFile, raf);
         if (lock == null) {
            if (DEBUG) {
               debug("unable to get lock on " + ocmFile.getAbsolutePath());
            }

            return;
         }

         if (DEBUG) {
            debug(ocmFile + " is locked with " + lock);
         }

         load(ocmProperties, raf);
         if (DEBUG) {
            debug("loaded properties [" + ocmProperties + "]");
         }

         if (DEBUG) {
            debug("machineName=" + machineName + ", currentDomainRoot=" + currentDomainRoot);
         }

         if (machineName == null || currentDomainRoot == null) {
            return;
         }

         String domainRoots = ocmProperties.getProperty(machineName);
         if (DEBUG) {
            debug("domainRoots=" + domainRoots);
         }

         if (!isPresent(domainRoots, currentDomainRoot)) {
            if (domainRoots == null) {
               domainRoots = currentDomainRoot;
            } else {
               domainRoots = domainRoots + "," + currentDomainRoot;
            }

            ocmProperties.setProperty(machineName, domainRoots);
            if (DEBUG) {
               debug("storing updated domainRoots=" + domainRoots);
            }

            store(ocmProperties, raf);
            return;
         }
      } finally {
         try {
            if (lock != null) {
               lock.close();
            }

            if (raf != null) {
               raf.close();
            }
         } catch (IOException var17) {
            if (DEBUG) {
               var17.printStackTrace();
            }
         }

      }

   }

   private static boolean isPresent(String domainRoots, String currentDomainRoot) {
      if (currentDomainRoot != null && domainRoots != null) {
         currentDomainRoot = currentDomainRoot.trim();
         StringTokenizer st = new StringTokenizer(domainRoots, ",");

         String root;
         do {
            if (!st.hasMoreElements()) {
               if (DEBUG) {
                  debug("found no match!");
               }

               return false;
            }

            root = (String)st.nextElement();
            if (root != null) {
               root = root.trim();
            }

            if (DEBUG) {
               debug("comparing root=" + root + " with " + currentDomainRoot + " with result " + currentDomainRoot.equalsIgnoreCase(root));
            }
         } while(!currentDomainRoot.equalsIgnoreCase(root));

         return true;
      } else {
         return false;
      }
   }

   private FileLockHandle lock(File f, RandomAccessFile raf) throws IOException {
      if (DEBUG) {
         debug("attempting to lock " + f);
      }

      return this.fileLockService.getFileLock(f, 30000L);
   }

   private static void store(Properties ocmProperties, RandomAccessFile raf) throws IOException {
      raf.seek(0L);
      RandomAccessFileOutputStream rafos = new RandomAccessFileOutputStream(raf);

      try {
         ocmProperties.store(rafos, "");
      } finally {
         if (rafos != null) {
            try {
               rafos.close();
            } catch (IOException var9) {
            }
         }

      }

   }

   private static void load(Properties prop, RandomAccessFile raf) throws IOException {
      RandomAccessFileInputStream rafis = new RandomAccessFileInputStream(raf);

      try {
         prop.load(rafis);
      } finally {
         if (rafis != null) {
            try {
               rafis.close();
            } catch (IOException var9) {
            }
         }

      }

   }

   private static boolean create(File ocmFile) {
      try {
         if (ocmFile.createNewFile()) {
            return true;
         }
      } catch (IOException var2) {
      }

      return ocmFile.exists();
   }

   private static void debug(String s) {
      debugLogger.debug("[OCMService] " + s);
   }

   static {
      DEBUG = debugLogger.isDebugEnabled();
      OCM_DIR = "oracle_common" + File.separator + "ccr";
   }

   private static class RandomAccessFileOutputStream extends OutputStream {
      RandomAccessFile raf;

      RandomAccessFileOutputStream(RandomAccessFile raf) {
         this.raf = raf;
      }

      public void write(int val) throws IOException {
         this.raf.write(val);
      }
   }

   private static class RandomAccessFileInputStream extends InputStream {
      RandomAccessFile raf;

      RandomAccessFileInputStream(RandomAccessFile raf) {
         this.raf = raf;
      }

      public int read() throws IOException {
         return this.raf.read();
      }
   }

   private class OCMRunnable implements Runnable {
      private OCMRunnable() {
      }

      public void run() {
         if (OCMService.DEBUG) {
            OCMService.debug("OCMRunnable has started");
         }

         String home = Home.getMiddlewareHomePath();
         if (OCMService.DEBUG) {
            OCMService.debug("home=" + home);
         }

         if (home != null) {
            home = home + File.separator + OCMService.OCM_DIR;
         }

         File ocmDir = new File(home);
         if (!ocmDir.exists()) {
            if (OCMService.DEBUG) {
               OCMService.debug(ocmDir.getAbsolutePath() + " does not exist!");
            }

         } else {
            File ocmFile = new File(ocmDir, "domainlocation.properties");
            if (!ocmFile.exists() && !OCMService.create(ocmFile)) {
               if (OCMService.DEBUG) {
                  OCMService.debug(ocmFile.getAbsolutePath() + " failed to create !");
               }

            } else {
               try {
                  String machineName = InetAddress.getLocalHost().getHostName();
                  String currentDomainRoot = System.getenv("LONG_DOMAIN_HOME");
                  if (currentDomainRoot == null) {
                     currentDomainRoot = DomainDir.getRootDir();
                  }

                  OCMService.this.recordDomainRoot(ocmFile, machineName, currentDomainRoot);
               } catch (IOException var6) {
                  if (OCMService.DEBUG) {
                     OCMService.debug("failed to record current domain root! abort!");
                     var6.printStackTrace();
                  }
               }

            }
         }
      }

      // $FF: synthetic method
      OCMRunnable(Object x1) {
         this();
      }
   }
}
