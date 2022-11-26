package weblogic.management.patching;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import weblogic.management.DomainDir;
import weblogic.management.patching.commands.PatchingLogger;

public class ElasticitySyncCounter {
   private Map counter = null;
   private static ElasticitySyncCounter INSTANCE = null;

   private ElasticitySyncCounter() {
   }

   public static synchronized ElasticitySyncCounter getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ElasticitySyncCounter();
         INSTANCE.loadCounterFromDisk();
      }

      return INSTANCE;
   }

   public synchronized void increment(String clusterName) throws Exception {
      if (!this.counter.containsKey(clusterName)) {
         this.counter.put(clusterName, new Integer(0));
      }

      int counterValBefore = this.getCounterValue(clusterName);
      this.counter.put(clusterName, new Integer(counterValBefore + 1));
      int counterValAfter = this.getCounterValue(clusterName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Counter for Cluster: " + clusterName + " incremented from: " + counterValBefore + " to: " + counterValAfter);
      }

      this.persistCounterToDisk();
   }

   public synchronized void decrement(String clusterName) throws Exception {
      if (!this.counter.containsKey(clusterName)) {
         this.counter.put(clusterName, new Integer(0));
      }

      int counterValBefore = this.getCounterValue(clusterName);
      this.counter.put(clusterName, new Integer(counterValBefore - 1));
      int counterValAfter = this.getCounterValue(clusterName);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Counter for Cluster: " + clusterName + " decremented from: " + counterValBefore + " to: " + counterValAfter);
      }

      this.persistCounterToDisk();
   }

   public synchronized int getCounterValue(String clusterName) {
      if (!this.counter.containsKey(clusterName)) {
         this.counter.put(clusterName, new Integer(0));
      }

      return (Integer)this.counter.get(clusterName);
   }

   public synchronized void setCounterValue(int val, String clusterName) throws Exception {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Counter for Cluster: " + clusterName + " set to: " + val);
      }

      this.counter.put(clusterName, new Integer(val));
      this.persistCounterToDisk();
   }

   public synchronized void loadCounterFromDisk() {
      Path saveDir = Paths.get(DomainDir.getOrchestrationWorkflowDir());
      Path counterStoreLocation = saveDir.resolve("CONCURRENT_ROLLOUT_COUNTER");
      if (counterStoreLocation.toFile().exists()) {
         try {
            FileInputStream fis = new FileInputStream(counterStoreLocation.toAbsolutePath().toString());
            Throwable var4 = null;

            try {
               ObjectInputStream ois = new ObjectInputStream(fis);
               Throwable var6 = null;

               try {
                  this.counter = (HashMap)ois.readObject();
               } catch (Throwable var44) {
                  var6 = var44;
                  throw var44;
               } finally {
                  if (ois != null) {
                     if (var6 != null) {
                        try {
                           ois.close();
                        } catch (Throwable var43) {
                           var6.addSuppressed(var43);
                        }
                     } else {
                        ois.close();
                     }
                  }

               }
            } catch (Throwable var46) {
               var4 = var46;
               throw var46;
            } finally {
               if (fis != null) {
                  if (var4 != null) {
                     try {
                        fis.close();
                     } catch (Throwable var42) {
                        var4.addSuppressed(var42);
                     }
                  } else {
                     fis.close();
                  }
               }

            }
         } catch (ClassNotFoundException | IOException var48) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("ElasticitySyncCounter.loadCounterFromDisk caught an exception while trying to load counter from disk: " + counterStoreLocation, var48);
            }

            PatchingLogger.logElasticitySyncCounterLoadError(counterStoreLocation.toString(), var48.getMessage());
         } finally {
            if (this.counter == null) {
               this.counter = new HashMap();
            }

         }
      } else {
         this.counter = new HashMap();
      }

   }

   public synchronized void persistCounterToDisk() throws Exception {
      Path saveDir = Paths.get(DomainDir.getOrchestrationWorkflowDir());
      Path counterStoreLocation = saveDir.resolve("CONCURRENT_ROLLOUT_COUNTER");

      try {
         FileOutputStream fos = new FileOutputStream(counterStoreLocation.toAbsolutePath().toString());
         Throwable var4 = null;

         try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Throwable var6 = null;

            try {
               oos.writeObject(this.counter);
            } catch (Throwable var31) {
               var6 = var31;
               throw var31;
            } finally {
               if (oos != null) {
                  if (var6 != null) {
                     try {
                        oos.close();
                     } catch (Throwable var30) {
                        var6.addSuppressed(var30);
                     }
                  } else {
                     oos.close();
                  }
               }

            }
         } catch (Throwable var33) {
            var4 = var33;
            throw var33;
         } finally {
            if (fos != null) {
               if (var4 != null) {
                  try {
                     fos.close();
                  } catch (Throwable var29) {
                     var4.addSuppressed(var29);
                  }
               } else {
                  fos.close();
               }
            }

         }

      } catch (IOException var35) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ElasticitySyncCounter.persistCounterToDisk caught an exception while trying to write counter to disk: " + var35);
         }

         throw var35;
      }
   }
}
