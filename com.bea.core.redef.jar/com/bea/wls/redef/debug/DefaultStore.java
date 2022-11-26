package com.bea.wls.redef.debug;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.diagnostics.debug.DebugLogger;

public class DefaultStore implements BackingStore {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DefaultStore");
   private static AtomicInteger txId;
   private static final String DIR_PREFIX = "Session-";
   private final File root;

   public DefaultStore(File dir) {
      this.root = dir;
   }

   public void write(Collection entries) {
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         StoreEntry e = (StoreEntry)var2.next();
         this.write(e);
      }

   }

   public void write(StoreEntry entry) {
      int tx = txId.incrementAndGet();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("REDEFINE-SESSION-Id:" + tx);
         Iterator var3 = entry.getEntries().keySet().iterator();

         while(var3.hasNext()) {
            String x = (String)var3.next();
            DEBUG.debug("[DefaultStore:Writing]:" + tx + " : " + x);
         }
      }

      File dir = this.getDir(this.root, entry.getAnnotation(), tx);

      try {
         this.writeEntries(entry.getEntries(), dir);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      entry.reset();
   }

   private void writeEntries(Map entires, File dir) throws IOException {
      Iterator var3 = entires.keySet().iterator();

      while(var3.hasNext()) {
         String clazz = (String)var3.next();
         File toWrite = new File(dir, clazz + ".class");
         BufferedOutputStream bos = null;

         try {
            bos = new BufferedOutputStream(new FileOutputStream(toWrite));
            byte[] clazzBytes = (byte[])entires.get(clazz);
            bos.write(clazzBytes, 0, clazzBytes.length);
            bos.flush();
         } finally {
            bos.close();
         }
      }

   }

   private File getDir(File root, String annotation, int tx) {
      if (annotation == null || "".equals(annotation)) {
         annotation = "default";
      }

      File dir = new File(root, annotation);
      dir.mkdirs();
      File d = new File(dir, "Session-" + tx);
      d.mkdirs();
      return d;
   }

   static {
      int rdm = (int)(Math.random() * 100.0 % 256.0);
      txId = new AtomicInteger(rdm);
   }
}
