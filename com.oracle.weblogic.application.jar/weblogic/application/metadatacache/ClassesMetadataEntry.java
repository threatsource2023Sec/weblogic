package weblogic.application.metadatacache;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.application.utils.PathUtils;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.Source;

public abstract class ClassesMetadataEntry extends PersistentMetadataEntry {
   private final File srcLocation;
   private final File cacheLocationDir;
   private static final Object MARKER = new Object();
   private static final Map checked = new ConcurrentHashMap();

   public ClassesMetadataEntry(File srcLocation, File cacheLocationDir) {
      this.srcLocation = srcLocation;
      this.cacheLocationDir = cacheLocationDir;
   }

   public boolean hasStaleChecked() {
      return false;
   }

   public File getLocation() {
      return this.cacheLocationDir;
   }

   protected Object getValidatingParams() {
      return null;
   }

   public boolean isValid(Object cachedObject) {
      return cachedObject instanceof ValidatingCacheObject ? ((ValidatingCacheObject)cachedObject).isValid(this.getValidatingParams()) : true;
   }

   public boolean contains(File a, File b) {
      if (a != null && b != null) {
         while(!a.equals(b)) {
            b = b.getParentFile();
            if (b == null) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static void clearCheckedStaleCache() {
      synchronized(checked) {
         checked.clear();
      }
   }

   public boolean isStale(File cacheFile) {
      boolean result = true;
      boolean shortCircuit = false;
      Map s = null;

      try {
         Object var29;
         if (this.hasStaleChecked()) {
            shortCircuit = true;
            result = false;
            var29 = result;
            return (boolean)var29;
         } else {
            s = (Map)checked.get(cacheFile);
            if (s == null) {
               synchronized(checked) {
                  Map curVal = (Map)checked.get(cacheFile);
                  if (curVal != null) {
                     s = curVal;
                  } else {
                     s = new ConcurrentHashMap();
                     checked.put(cacheFile, s);
                  }
               }
            }

            if (((Map)s).containsKey(this.srcLocation)) {
               shortCircuit = true;
               result = false;
               var29 = result;
               return (boolean)var29;
            } else {
               long cacheTimestamp = cacheFile.lastModified();
               File appTempDir;
               if (!this.srcLocation.isDirectory()) {
                  result = this.srcLocation.lastModified() != cacheTimestamp;
                  appTempDir = (File)result;
                  return (boolean)appTempDir;
               } else {
                  appTempDir = PathUtils.getTempDir();
                  JarClassFinder finder;
                  if (this.contains(appTempDir, this.srcLocation)) {
                     if (this.contains(this.srcLocation, cacheFile)) {
                        result = this.srcLocation.lastModified() > cacheTimestamp;
                     } else {
                        result = this.srcLocation.lastModified() != cacheTimestamp;
                     }

                     finder = (JarClassFinder)result;
                     return (boolean)finder;
                  } else {
                     finder = null;

                     try {
                        finder = new JarClassFinder(this.srcLocation);
                        Enumeration entries = finder.entries();

                        Source source;
                        String sourceUrlPath;
                        do {
                           if (!entries.hasMoreElements()) {
                              result = false;
                              source = (Source)result;
                              return (boolean)source;
                           }

                           source = (Source)entries.nextElement();
                           String libraryName = this.srcLocation.getName();
                           if (source == null) {
                              throw new RuntimeException("Found NULL source for " + libraryName);
                           }

                           URL sourceUrl = source.getURL();
                           if (sourceUrl == null) {
                              throw new RuntimeException("Found NULL source URL for source " + source + " in " + libraryName);
                           }

                           sourceUrlPath = sourceUrl.getPath();
                           if (sourceUrlPath == null) {
                              throw new RuntimeException("Found NULL path for source URL " + sourceUrl + " in " + libraryName);
                           }
                        } while(!sourceUrlPath.endsWith(".class") && !sourceUrlPath.endsWith("_wl_cls_gen.jar") || !this.isStale(source, cacheTimestamp));

                        boolean var14 = true;
                        return var14;
                     } catch (IOException var26) {
                     } finally {
                        if (finder != null) {
                           finder.close();
                        }

                     }

                     boolean var30 = true;
                     return var30;
                  }
               }
            }
         }
      } finally {
         if (!shortCircuit && result == false && s != null) {
            ((Map)s).put(this.srcLocation, MARKER);
         }

      }
   }

   private boolean isStale(Source source, long timestamp) {
      return source.lastModified() > timestamp;
   }

   public void writeObject(File cacheFile, Object cacheObject) throws IOException {
      super.writeObject(cacheFile, cacheObject);
      if (!this.srcLocation.isDirectory()) {
         cacheFile.setLastModified(this.srcLocation.lastModified());
      }

   }

   public void updateTimestamp(File cacheFile) {
      if (!this.srcLocation.isDirectory()) {
         cacheFile.setLastModified(this.srcLocation.lastModified());
      }

   }
}
