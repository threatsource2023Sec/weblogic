package weblogic.servlet.internal;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.PersistentMetadataEntry;

public abstract class DescriptorCacheEntry extends PersistentMetadataEntry {
   protected SharedLibraryDefinition library;

   public DescriptorCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      this.library = sharedLibraryDefinition;
   }

   public File getLocation() {
      return this.library.getLibTempDir();
   }

   public Object getCachableObject() throws MetadataCacheException {
      File baseDir = Cache.LibMetadataCache.getCacheDir(this);
      List resLocations = this.getResourceLocations();
      return new DescriptorCachableObject(baseDir, resLocations);
   }

   protected abstract List getResourceLocations() throws MetadataCacheException;

   public boolean isStale(File cache) {
      return !this.library.isArchived();
   }

   public boolean isValid(Object cachedObject) {
      DescriptorCachableObject o = (DescriptorCachableObject)cachedObject;
      File currBaseDir = Cache.LibMetadataCache.getCacheDir(this);
      File cachedBaseDir = o.getCacheBaseDir();
      return currBaseDir.equals(cachedBaseDir);
   }

   static class DescriptorCachableObject implements Serializable {
      private List resLocations;
      private File cacheBaseDir;

      public DescriptorCachableObject(File cacheBaseDir, List resLocations) {
         this.cacheBaseDir = cacheBaseDir;
         this.resLocations = resLocations;
      }

      public File getCacheBaseDir() {
         return this.cacheBaseDir;
      }

      public List getResourceLocation() {
         return this.resLocations;
      }
   }
}
