package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.Type;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataEntry;
import weblogic.application.metadatacache.MetadataType;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.classloaders.ClassFinder;

public abstract class SharedLibraryDefinition extends LibraryDefinition implements Library, ApplicationLibrary, WebAppHelper {
   protected boolean archived;
   protected final File extractDir;
   protected Map cacheEntries;
   protected boolean isAnnotationEnabled = false;
   protected Map handlesImplsMap;
   private AnnotationProcessStatus annotationProcessStatus;

   public SharedLibraryDefinition(LibraryData libData, Type type, File extractDir) {
      super(libData, type);
      this.annotationProcessStatus = SharedLibraryDefinition.AnnotationProcessStatus.NOT_START;
      this.archived = !this.getLocation().isDirectory();
      this.handlesImplsMap = new HashMap();
      if (extractDir == null) {
         throw new IllegalArgumentException("Extract Dir must not be null");
      } else {
         this.extractDir = extractDir;
      }
   }

   public boolean isArchived() {
      return this.archived;
   }

   public File getLibTempDir() {
      return this.extractDir;
   }

   abstract List getTldLocations() throws MetadataCacheException;

   protected abstract ClassFinder getClassFinder() throws IOException;

   public abstract void init() throws LibraryProcessingException;

   public Set getTagListeners(boolean isAnnotationOnly) {
      Set tagListeners = isAnnotationOnly ? (Set)this.getCachedObject(MetadataType.TAG_LISTENERS) : (Set)this.getTldInfo().get("listener-class");
      return tagListeners == null ? Collections.EMPTY_SET : tagListeners;
   }

   public Set getTagHandlers(boolean isAnnotationOnly) {
      Set tagHandlers = isAnnotationOnly ? (Set)this.getCachedObject(MetadataType.TAG_HANDLERS) : (Set)this.getTldInfo().get("tag-class");
      return tagHandlers == null ? Collections.EMPTY_SET : tagHandlers;
   }

   public Set getManagedBeanClasses(String jsfConfigFiles) {
      Set allManagedBeans = null;
      DescriptorCacheEntry.DescriptorCachableObject o = (DescriptorCacheEntry.DescriptorCachableObject)this.getCachedObject(MetadataType.FACE_BEANS);
      Set configedManagedBeans = FaceConfigCacheHelper.parseFacesConfigs(o.getResourceLocation(), (File)o.getCacheBaseDir(), this.getName());
      allManagedBeans = WarUtils.addAllIfNotEmpty((Set)allManagedBeans, configedManagedBeans);
      Set annotatedManagedBeans = this.getAnnotatedClasses("javax.faces.bean.ManagedBean");
      allManagedBeans = WarUtils.addAllIfNotEmpty((Set)allManagedBeans, annotatedManagedBeans);
      return allManagedBeans == null ? Collections.emptySet() : allManagedBeans;
   }

   public Set getAnnotatedClasses(String... annotation) {
      if (!this.isAnnotationEnabled) {
         return Collections.emptySet();
      } else {
         ClassInfoFinder classInfos = (ClassInfoFinder)this.getCachedObject(MetadataType.CLASSLEVEL_INFOS);
         return classInfos.getClassNamesWithAnnotations(annotation);
      }
   }

   public Set getHandlesImpls(ClassLoader loader, String... handlesTypes) {
      if (handlesTypes != null && handlesTypes.length != 0) {
         Set result = new HashSet();
         ClassInfoFinder classInfos = null;
         String[] var5 = handlesTypes;
         int var6 = handlesTypes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String handleType = var5[var7];
            Set handleImps = (Set)this.handlesImplsMap.get(handleType);
            if (handleImps == null) {
               if (classInfos == null) {
                  classInfos = (ClassInfoFinder)this.getCachedObject(MetadataType.CLASSLEVEL_INFOS);
               }

               synchronized(this) {
                  handleImps = classInfos.getHandlesImpls(loader, new String[]{handleType});
               }

               if (handleImps == null) {
                  handleImps = Collections.emptySet();
               }

               this.handlesImplsMap.put(handleType, handleImps);
            }

            result.addAll(handleImps);
         }

         return result;
      } else {
         return Collections.emptySet();
      }
   }

   public Collection getWebFragments() {
      return Collections.emptySet();
   }

   public URL getClassSourceUrl(String className) {
      ClassInfoFinder classInfos = (ClassInfoFinder)this.getCachedObject(MetadataType.CLASSLEVEL_INFOS);
      return classInfos.getClassSourceUrl(className);
   }

   protected abstract List getFacesConfigLocations() throws MetadataCacheException;

   protected Set getAnnotatedTagClasses(String type) throws MetadataCacheException {
      if (!this.isAnnotationEnabled) {
         return Collections.EMPTY_SET;
      } else {
         ClassFinder cf = null;

         Set var4;
         try {
            cf = this.getClassFinder();
            Map tldInfo = this.getTldInfo();
            var4 = WarUtils.getTagClasses(cf, tldInfo, true, type);
         } catch (IOException var8) {
            throw new MetadataCacheException(var8);
         } finally {
            if (cf != null) {
               cf.close();
            }

         }

         return var4;
      }
   }

   protected ClassInfoFinder getClassLevelInfos() throws MetadataCacheException {
      ClassFinder finder = null;

      ClassInfoFinder var2;
      try {
         finder = this.getClassFinder();
         var2 = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(finder).setKeepAnnotatedClassesOnly(false));
      } catch (Exception var6) {
         throw new MetadataCacheException(var6);
      } finally {
         if (finder != null) {
            finder.close();
         }

      }

      return var2;
   }

   protected Map getTldInfo() {
      DescriptorCacheEntry.DescriptorCachableObject o = (DescriptorCacheEntry.DescriptorCachableObject)this.getCachedObject(MetadataType.TLD);
      return TldCacheHelper.parseTagLibraries(o.getResourceLocation(), (File)o.getCacheBaseDir(), this.getName());
   }

   Object getCachedObject(MetadataType cacheType) {
      MetadataEntry entry = this.getCachableEntry(cacheType);

      try {
         return Cache.LibMetadataCache.lookupCachedObject(entry);
      } catch (MetadataCacheException var4) {
         return null;
      }
   }

   public void remove() throws LibraryProcessingException {
      Cache.LibMetadataCache.clearCache(this);
      this.cacheEntries.clear();
   }

   public MetadataEntry[] findAllCachableEntry() {
      return (MetadataEntry[])((MetadataEntry[])this.cacheEntries.values().toArray(new MetadataEntry[this.cacheEntries.size()]));
   }

   public MetadataEntry getCachableEntry(MetadataType type) {
      return (MetadataEntry)this.cacheEntries.get(type);
   }

   public ClassInfoFinder getClassInfoFinder() {
      return (ClassInfoFinder)this.getCachedObject(MetadataType.CLASSLEVEL_INFOS);
   }

   public void startAnnotationProcess() {
      this.annotationProcessStatus = SharedLibraryDefinition.AnnotationProcessStatus.START;
   }

   public void completeAnnotationProcess() {
      this.annotationProcessStatus = SharedLibraryDefinition.AnnotationProcessStatus.NOT_START;
   }

   protected boolean hasStaleChecked() {
      switch (this.annotationProcessStatus) {
         case START:
            this.annotationProcessStatus = SharedLibraryDefinition.AnnotationProcessStatus.PROCESSED;
            return false;
         case PROCESSED:
            return true;
         default:
            return false;
      }
   }

   protected void initializeCacheEntries() {
      this.cacheEntries = new HashMap();
      this.cacheEntries.put(MetadataType.TLD, new TldCacheEntry(this));
      this.cacheEntries.put(MetadataType.TAG_HANDLERS, new AnnotatedTagHandlersCacheEntry(this));
      this.cacheEntries.put(MetadataType.TAG_LISTENERS, new AnnotatedTagListenersCacheEntry(this));
      this.cacheEntries.put(MetadataType.FACE_BEANS, new FaceConfigCacheEntry(this));
      this.cacheEntries.put(MetadataType.CLASSLEVEL_INFOS, new ClassLevelClassInfos(this));
   }

   private static enum AnnotationProcessStatus {
      NOT_START,
      START,
      PROCESSED;
   }
}
