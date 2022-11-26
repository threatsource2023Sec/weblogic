package weblogic.application.metadatacache;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.FileUtils;

public enum Cache {
   LibMetadataCache(true),
   AppMetadataCache(false);

   public static final String CACHE_FILE = ".cache.ser";
   private final Map cacheObjectMap = Collections.synchronizedMap(new HashMap());
   private final Map weakCacheObjectMap = Collections.synchronizedMap(new WeakHashMap() {
      public Object put(Object key, Object value) {
         return this.unwrap(super.put(key, new WeakReference(value)));
      }

      public Object get(Object key) {
         return this.unwrap(super.get(key));
      }

      private Object unwrap(Object ref) {
         return ref == null ? null : ((WeakReference)ref).get();
      }
   });
   private final boolean disableAllowed;
   private volatile boolean useCache = true;
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppMetadataCache");

   private Cache(boolean disableAllowed) {
      this.disableAllowed = disableAllowed;
   }

   public void disable() {
      if (!this.disableAllowed) {
         throw new UnsupportedOperationException("disable() not allowed");
      } else {
         this.useCache = false;
      }
   }

   private Map selectCacheObjectMap(MetadataEntry entry) {
      return entry.getType().isWeakRef() ? this.weakCacheObjectMap : this.cacheObjectMap;
   }

   public void initCache(Metadata metadata) throws MetadataCacheException {
      if (this.useCache) {
         MetadataEntry[] entries = metadata.findAllCachableEntry();

         for(int i = 0; i < entries.length; ++i) {
            this.lookupCachedObject(entries[i]);
         }

      }
   }

   public void clearCache(Metadata metadata) {
      if (this.useCache) {
         MetadataEntry[] entries = metadata.findAllCachableEntry();

         for(int i = 0; i < entries.length; ++i) {
            File cacheFile = this.getCacheFile(entries[i], false);
            this.selectCacheObjectMap(entries[i]).remove(cacheFile);
            this.remove(entries[i]);
         }

      }
   }

   public Object lookupCachedObject(MetadataEntry entry) throws MetadataCacheException {
      if (entry == null) {
         return null;
      } else {
         String stub = null;
         if (debugger.isDebugEnabled()) {
            stub = "MetadataEntry[Type=" + entry.getType() + "][Location=" + entry.getLocation() + "] ";
         }

         if (!this.useCache) {
            Object obj = null;

            try {
               obj = entry.getCachableObject();
            } finally {
               if (debugger.isDebugEnabled()) {
                  debugger.debug(stub + "Cache disabled, obtaining cacheable object from entry " + entry + ": " + obj);
               }

            }

            return obj;
         } else {
            File cacheFile = this.getCacheFile(entry, true);
            if (debugger.isDebugEnabled()) {
               debugger.debug(stub + "Obtained cache file " + cacheFile);
            }

            Map localCacheObjectMap = this.selectCacheObjectMap(entry);
            if (debugger.isDebugEnabled()) {
               debugger.debug(stub + "Chose map " + localCacheObjectMap.getClass());
            }

            Object cacheObject;
            if (!this.isStale(cacheFile, entry)) {
               if (debugger.isDebugEnabled()) {
                  debugger.debug(stub + "Entry " + entry + " not found to be stale in file " + cacheFile);
               }

               cacheObject = localCacheObjectMap.get(cacheFile);
               if (cacheObject != null) {
                  if (debugger.isDebugEnabled()) {
                     debugger.debug(stub + "Obtained cacheable object from local map: " + cacheObject);
                  }

                  if (entry.isValid(cacheObject)) {
                     return cacheObject;
                  }

                  if (debugger.isDebugEnabled()) {
                     debugger.debug(stub + "Cacheable found to no longer be valid, will regenerate: " + cacheObject);
                  }
               } else {
                  try {
                     Object cacheObject = entry.readObject(cacheFile);
                     if (debugger.isDebugEnabled()) {
                        debugger.debug(stub + "Read cacheable object from file: " + cacheFile + ": " + cacheObject);
                     }

                     if (entry.isValid(cacheObject)) {
                        if (debugger.isDebugEnabled()) {
                           debugger.debug(stub + "Cacheable found to be valid, saving in local map: " + cacheObject);
                        }

                        localCacheObjectMap.put(cacheFile, cacheObject);
                        return cacheObject;
                     }
                  } catch (Exception var12) {
                     J2EELogger.logApplicationCacheFileReadingException(cacheFile.getAbsolutePath(), var12.getMessage(), var12.getClass().getName());
                     if (debugger.isDebugEnabled()) {
                        debugger.debug(stub + "Unable to read from " + cacheFile, var12);
                     }
                  }
               }
            } else if (debugger.isDebugEnabled()) {
               debugger.debug(stub + "Entry " + entry + " found to be stale in file " + cacheFile);
            }

            if (cacheFile.exists()) {
               if (debugger.isDebugEnabled()) {
                  debugger.debug(stub + "Deleting cache file: " + cacheFile);
               }

               cacheFile.delete();
            }

            cacheObject = entry.getCachableObject();
            if (debugger.isDebugEnabled()) {
               debugger.debug(stub + "Obtained cacheable object from entry " + entry + ": " + cacheObject);
            }

            try {
               if (debugger.isDebugEnabled()) {
                  debugger.debug(stub + "Writing cacheable object " + cacheObject + " to file " + cacheFile);
               }

               entry.writeObject(cacheFile, cacheObject);
            } catch (IOException var11) {
               J2EELogger.logApplicationCacheFileWritingException(cacheFile.getAbsolutePath(), var11.getMessage());
               if (debugger.isDebugEnabled()) {
                  debugger.debug(stub + "Unable to write " + cacheFile, var11);
               }

               cacheFile.delete();
            }

            if (debugger.isDebugEnabled()) {
               debugger.debug(stub + "Saving cacheable object to local map: " + cacheObject);
            }

            localCacheObjectMap.put(cacheFile, cacheObject);
            return cacheObject;
         }
      }
   }

   private void remove(MetadataEntry entry) {
      File cacheDir = this.getCacheDir(entry);
      if (cacheDir.exists()) {
         FileUtils.remove(cacheDir);
      }
   }

   public File getCacheDir(MetadataEntry entry) {
      if (entry.getLocation() != null && entry.getLocation().getPath().length() != 0) {
         return new File(entry.getLocation(), entry.getType().getName());
      } else {
         throw new IllegalStateException("Cache Entry location must be available");
      }
   }

   private File getCacheFile(MetadataEntry entry, boolean createIfNotExists) {
      File cacheDir = this.getCacheDir(entry);
      if (!cacheDir.exists() && createIfNotExists) {
         cacheDir.mkdirs();
      }

      return new File(cacheDir, ".cache.ser");
   }

   private boolean isStale(File cacheFile, MetadataEntry entry) {
      return !cacheFile.exists() || entry.isStale(cacheFile);
   }
}
