package com.bea.wls.redef.debug;

import java.io.File;
import java.util.Collection;

public class BackingStoreFactory {
   private static File dir;
   private static String zipName;

   public static BackingStore make() {
      return (BackingStore)(dir != null ? new DefaultStore(dir) : new BackingStore() {
         public void write(Collection entries) {
         }

         public void write(StoreEntry entry) {
         }
      });
   }

   static {
      String theDir = System.getProperty("com.bea.wls.redef.debug.dir");
      if (theDir != null) {
         dir = new File(theDir);
      }

      zipName = System.getProperty("com.bea.wls.redef.debug.zipname");
   }
}
