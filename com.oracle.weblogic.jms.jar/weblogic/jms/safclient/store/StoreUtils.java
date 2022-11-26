package weblogic.jms.safclient.store;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import javax.jms.JMSException;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.FileStoreIO;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.store.xa.internal.PersistentStoreXAImpl;

public final class StoreUtils {
   private static final String STORE_PFX = "SAFSTORE";
   private static final String STORE_APX = "V";
   private static int storeNum;
   private static Object lock = new Object();
   private static HashMap stores = new HashMap();

   private static PersistentStoreXA makeStore(String name, String dirName) throws JMSException {
      if (name != null && dirName != null) {
         String resourceName = name + "_RESOURCE";

         try {
            PersistentStoreIO io = new FileStoreIO(name, dirName, true);
            return new PersistentStoreXAImpl(name, io, resourceName);
         } catch (PersistentStoreException var4) {
            throw new JMSException(var4.getMessage());
         }
      } else {
         throw new JMSException("Name and directory name may not be null");
      }
   }

   private static int getStoreNumber(File storeDirectory) {
      if (storeDirectory.exists() && storeDirectory.isDirectory()) {
         String[] dataFiles = storeDirectory.list(new FileFilter());
         if (dataFiles != null && dataFiles.length > 0) {
            int lcv = 0;

            Integer storeNumObj;
            while(true) {
               if (lcv >= dataFiles.length) {
                  synchronized(lock) {
                     return storeNum++;
                  }
               }

               String dataFileName = dataFiles[lcv];
               int Vindex = dataFileName.indexOf(86);
               if (Vindex > "SAFSTORE".length()) {
                  String storeNumString = dataFileName.substring("SAFSTORE".length(), "SAFSTORE".length() + (Vindex - "SAFSTORE".length()));

                  try {
                     storeNumObj = new Integer(storeNumString);
                     break;
                  } catch (NumberFormatException var15) {
                  }
               }

               ++lcv;
            }

            int retVal = storeNumObj;
            synchronized(lock) {
               if (storeNum <= retVal) {
                  storeNum = retVal + 1;
               }

               return retVal;
            }
         } else {
            synchronized(lock) {
               return storeNum++;
            }
         }
      } else {
         synchronized(lock) {
            return storeNum++;
         }
      }
   }

   public static void initStores(File rootDirectory, File storeDirectory, String writePolicy) throws JMSException {
      String retVal = "SAFSTORE" + getStoreNumber(storeDirectory) + "V";
      PersistentStoreXA realStore = makeStore(retVal, storeDirectory.toString());

      try {
         HashMap config = new HashMap();
         config.put("SynchronousWritePolicy", StoreWritePolicy.getPolicy(writePolicy));
         realStore.open(config);
      } catch (PersistentStoreException var8) {
         throw new weblogic.jms.common.JMSException(var8);
      }

      synchronized(stores) {
         stores.put(rootDirectory, realStore);
      }
   }

   public static PersistentStoreXA getStore(File rootDirectory) {
      synchronized(stores) {
         return (PersistentStoreXA)stores.get(rootDirectory);
      }
   }

   public static void removeStore(File rootDirectory) {
      synchronized(stores) {
         stores.remove(rootDirectory);
      }
   }

   private static class FileFilter implements FilenameFilter {
      private FileFilter() {
      }

      public boolean accept(File arg0, String arg1) {
         if (arg1 == null) {
            return false;
         } else {
            return arg1.startsWith("SAFSTORE");
         }
      }

      // $FF: synthetic method
      FileFilter(Object x0) {
         this();
      }
   }
}
