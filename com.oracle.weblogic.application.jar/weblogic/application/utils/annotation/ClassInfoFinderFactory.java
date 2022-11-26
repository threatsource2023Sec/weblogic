package weblogic.application.utils.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.internal.Controls;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.ClassInfoFinderMetadataEntry;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataEntry;
import weblogic.application.metadatacache.SimpleMetadata;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public enum ClassInfoFinderFactory {
   FACTORY;

   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppAnnoScanVerbose");

   public Params createParams(ClassFinder classFinder) {
      return new Params(classFinder);
   }

   public Params createParams(VirtualJarFile virtualJarFile) {
      return new Params(virtualJarFile);
   }

   public ClassInfoFinder newInstance(Params params) throws AnnotationProcessingException {
      if (params.cache != null && params.srcFileForStaleCheck != null && params.cacheLocationDir != null) {
         ClassInfoFinderMetadataEntry metadataEntry = this.getMetadataEntry(params);
         SimpleMetadata metadata = new SimpleMetadata(new MetadataEntry[]{metadataEntry});

         try {
            Cache cacheToUse = params.cache;
            params.cache = null;
            cacheToUse.initCache(metadata);
            return (ClassInfoFinder)cacheToUse.lookupCachedObject(metadataEntry);
         } catch (MetadataCacheException var5) {
            throw new AnnotationProcessingException(var5);
         }
      } else {
         return this.newInstanceInternal(params);
      }
   }

   private ClassInfoFinder newInstanceInternal(Params params) throws AnnotationProcessingException {
      return new ClassfinderClassInfos(params);
   }

   public ClassInfoFinder aggregateInstances(ClassInfoFinder classInfoFinder, List classInfoFinders) {
      if (classInfoFinders != null && classInfoFinders.size() != 0) {
         ArrayList list = new ArrayList(classInfoFinders.size() + 1);
         list.add((ClassfinderClassInfos)classInfoFinder);
         Iterator var4 = classInfoFinders.iterator();

         while(var4.hasNext()) {
            ClassInfoFinder finder = (ClassInfoFinder)var4.next();
            list.add((ClassfinderClassInfos)finder);
         }

         return new AggregateClassInfoFinder(list);
      } else {
         return classInfoFinder;
      }
   }

   public void notifyRemoval(Params params) {
      if (params.cache != null && params.srcFileForStaleCheck != null && params.cacheLocationDir != null) {
         params.cache.clearCache(new SimpleMetadata(new MetadataEntry[]{this.getMetadataEntry(params)}));
      }

   }

   private ClassInfoFinderMetadataEntry getMetadataEntry(Params params) {
      return new ClassInfoFinderMetadataEntry(params.srcFileForStaleCheck, params.cacheLocationDir, params);
   }

   public static class Params {
      final ClassFinder classFinder;
      final VirtualJarFile virtualJarFile;
      ModuleType moduleType;
      boolean keepAnnotatedClassesOnly;
      Class[] componentAnnotations;
      File srcFileForStaleCheck;
      File cacheLocationDir;
      private Cache cache;

      private Params(ClassFinder classFinder) {
         this.keepAnnotatedClassesOnly = Controls.keepannotatedclassesonly.enabled;
         this.classFinder = classFinder;
         this.virtualJarFile = null;
      }

      private Params(VirtualJarFile virtualJarFile) {
         this.keepAnnotatedClassesOnly = Controls.keepannotatedclassesonly.enabled;
         this.classFinder = null;
         this.virtualJarFile = virtualJarFile;
      }

      public Params setModuleType(ModuleType moduleType) {
         this.moduleType = moduleType;
         return this;
      }

      public Params setKeepAnnotatedClassesOnly(boolean keepAnnotatedClassesOnly) {
         this.keepAnnotatedClassesOnly = keepAnnotatedClassesOnly;
         return this;
      }

      public Params setComponentAnnotations(Class... componentAnnotations) {
         this.componentAnnotations = componentAnnotations;
         return this;
      }

      public Params enableCaching(Cache cache, File srcFileForStaleCheck, File cacheLocationDir) {
         if (srcFileForStaleCheck != null && cacheLocationDir != null) {
            this.cache = cache;
            this.srcFileForStaleCheck = srcFileForStaleCheck;
            this.cacheLocationDir = cacheLocationDir;
         } else if (ClassInfoFinderFactory.debugger.isDebugEnabled()) {
            ClassInfoFinderFactory.debugger.debug("Caching can only be enabled if both srcFileForStaleCheck (" + srcFileForStaleCheck + ") and cacheLocationDir(" + cacheLocationDir + ") are non-null", new IllegalArgumentException());
         }

         return this;
      }

      public Params enableCaching(Cache cache, VirtualJarFile srcFileForStaleCheck, File cacheLocationDir) {
         File srcLocation = null;
         if (srcFileForStaleCheck != null) {
            File[] rootFiles = srcFileForStaleCheck.getRootFiles();
            if (rootFiles.length > 0) {
               srcLocation = rootFiles[0];
            }
         }

         if (srcLocation != null && cacheLocationDir != null) {
            this.cache = cache;
            this.srcFileForStaleCheck = srcLocation;
            this.cacheLocationDir = cacheLocationDir;
         } else if (ClassInfoFinderFactory.debugger.isDebugEnabled()) {
            ClassInfoFinderFactory.debugger.debug("Caching can only be enabled if both srcFileForStaleCheck (" + srcFileForStaleCheck + ") and cacheLocationDir(" + cacheLocationDir + ") are non-null", new IllegalArgumentException());
         }

         return this;
      }

      // $FF: synthetic method
      Params(ClassFinder x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      Params(VirtualJarFile x0, Object x1) {
         this(x0);
      }
   }
}
