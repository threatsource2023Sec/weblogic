package oracle.jrockit.jfr;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import oracle.jrockit.log.Logger;
import oracle.jrockit.log.MsgLevel;

public class Repository {
   private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
   private final File path;
   private final File lock;
   private final Logger logger;
   private final String pid;

   public Repository(JFR jfr, Options options, Logger logger) {
      this.logger = logger;
      this.pid = String.valueOf(jfr.getpid());
      String repo = options.repository();
      if (repo == null) {
         repo = System.getProperty("java.io.tmpdir");
      }

      String basename = this.repositoryName();
      String name = basename;
      int i = 0;

      while(true) {
         File f = new File(repo, name);
         if (this.tryToUseAsRepository(f)) {
            this.path = f;
            this.lock = new File(f, ".lock");
            this.logger.log(MsgLevel.INFO, "Using %s as Flight Recorder repository.", new Object[]{this.path.toString()});
            return;
         }

         name = basename + "_" + i++;
      }
   }

   private boolean tryToUseAsRepository(final File path) {
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Boolean run() {
            try {
               path.getCanonicalFile().getParentFile().mkdirs();
               if (path.exists()) {
                  return false;
               } else if (!path.mkdir()) {
                  return false;
               } else if (!path.exists()) {
                  return false;
               } else {
                  return (new File(path, ".lock")).exists() ? false : true;
               }
            } catch (Exception var2) {
               Repository.this.logger.log(MsgLevel.WARN, var2, "Could not open %s as repository.", new Object[]{path});
               return false;
            }
         }
      });
   }

   final void lock() throws IOException {
      while(!this.lock.createNewFile()) {
         try {
            Thread.sleep(1L);
         } catch (InterruptedException var2) {
         }
      }

   }

   final void unlock() {
      assert this.lock.exists();

      while(!this.lock.delete()) {
         this.logger.error("Could not unlock repository!");

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var2) {
         }
      }

   }

   final String filenameBase() {
      return this.sdf.format(new Date()) + "_" + this.pid;
   }

   public final File getPath() {
      return this.path;
   }

   private final String repositoryName() {
      return this.filenameBase();
   }

   public void destroy() {
      this.logger.info("Deleting Flight Recorder repository");
      if (!this.path.delete()) {
         this.logger.error("Repository could not be removed at shutdown: " + this.path.getAbsoluteFile());
      }

   }
}
