package weblogic.application.metadatacache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;

public abstract class PersistentMetadataEntry implements MetadataEntry {
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppMetadataCache");

   public Object readObject(File cacheFile) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = null;

      Object var3;
      try {
         ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(cacheFile)));
         var3 = ois.readObject();
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var10) {
               J2EELogger.logApplicationCacheFileObjectStreamClosureException(cacheFile.getAbsolutePath(), "input", var10.getMessage());
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Unable to close input stream", var10);
               }
            }
         }

      }

      return var3;
   }

   public void writeObject(File cacheFile, Object cacheObject) throws IOException {
      ObjectOutputStream oos = null;

      try {
         oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(cacheFile)));
         oos.writeObject(cacheObject);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var10) {
               J2EELogger.logApplicationCacheFileObjectStreamClosureException(cacheFile.getAbsolutePath(), "output", var10.getMessage());
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Unable to close output stream", var10);
               }
            }
         }

      }

   }
}
