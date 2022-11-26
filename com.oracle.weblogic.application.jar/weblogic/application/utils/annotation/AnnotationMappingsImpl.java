package weblogic.application.utils.annotation;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;

public class AnnotationMappingsImpl implements AnnotationMappings {
   private final Map classMap = new HashMap();
   private final GenericClassLoader classLoader;
   private final String appLevelClassPathForAnnotationScanning;
   private ClassInfoFinder classInfosFinder = null;
   private boolean annotatedClassesLoaded = false;
   private static DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppAnnoScanVerbose");
   private final File srcLocation;
   private final File cacheLocationDir;
   private final boolean isPojoAnnotationEnabled;
   private final boolean useCache;
   private final List libraryClassInfoFinders;

   public AnnotationMappingsImpl(GenericClassLoader classLoader, boolean useCache, String appLevelClassPathForAnnotationScanning, File srcLocation, File cacheLocationDir, List libraryClassInfoFinders, boolean isPojoAnnotationEnabled) {
      this.classLoader = classLoader;
      String classpath;
      if (!useCache) {
         classpath = classLoader.getClassFinder().getClassPath();
      } else {
         classpath = appLevelClassPathForAnnotationScanning;
      }

      this.appLevelClassPathForAnnotationScanning = classpath == null ? "" : classpath;
      this.srcLocation = srcLocation;
      this.cacheLocationDir = cacheLocationDir;
      this.useCache = useCache;
      this.libraryClassInfoFinders = libraryClassInfoFinders;
      this.isPojoAnnotationEnabled = isPojoAnnotationEnabled;
   }

   public synchronized ClassInfoFinder getClassInfoFinder(Class[] annotationSupportAnnotations) {
      if (this.classInfosFinder == null) {
         ClassInfoFinder appClassInfoFinder = null;

         try {
            if (this.useCache) {
               appClassInfoFinder = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams((ClassFinder)(new ClasspathClassFinder2(this.appLevelClassPathForAnnotationScanning))).enableCaching(Cache.AppMetadataCache, this.srcLocation, this.cacheLocationDir));
            } else if (this.isPojoAnnotationEnabled) {
               appClassInfoFinder = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams((ClassFinder)(new ClasspathClassFinder2(this.appLevelClassPathForAnnotationScanning))));
            } else {
               appClassInfoFinder = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams((ClassFinder)(new ClasspathClassFinder2(this.appLevelClassPathForAnnotationScanning))).setComponentAnnotations(annotationSupportAnnotations));
            }
         } catch (AnnotationProcessingException var4) {
            var4.printStackTrace();
         }

         if (this.useCache) {
            this.classInfosFinder = ClassInfoFinderFactory.FACTORY.aggregateInstances(appClassInfoFinder, this.libraryClassInfoFinders);
         } else {
            this.classInfosFinder = appClassInfoFinder;
         }
      }

      return this.classInfosFinder;
   }

   public Set getAnnotatedClasses(Class... annotations) {
      Set result = new HashSet();
      Class[] var3 = annotations;
      int var4 = annotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class annotation = var3[var5];
         Set classes = (Set)this.classMap.get(annotation);
         if (classes != null) {
            result.addAll(classes);
         }
      }

      return result;
   }

   public void loadAnnotatedClasses(Class[] annotations) throws AnnotationProcessingException {
      if (this.annotatedClassesLoaded) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("AnnotationMappingsImpl.loadAnnotatedClasses has already been invoked");
         }
      } else {
         this.annotatedClassesLoaded = true;

         try {
            this.loadAnnotatedClasses(this.getClassInfoFinder(annotations), annotations);
         } catch (ClassNotFoundException var3) {
            throw new AnnotationProcessingException("Failed to load annotations for application", var3);
         }
      }

   }

   private void loadAnnotatedClasses(ClassInfoFinder classInfos, Class[] annotations) throws ClassNotFoundException {
      if (annotations != null) {
         Class[] var3 = annotations;
         int var4 = annotations.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class annotation = var3[var5];
            if (!this.classMap.containsKey(annotation)) {
               Set classes = new HashSet();
               Iterator var8 = classInfos.getClassNamesWithAnnotations(annotation.getName()).iterator();

               while(var8.hasNext()) {
                  String className = (String)var8.next();
                  classes.add(this.classLoader.loadClass(className));
                  this.classMap.put(annotation, classes);
               }
            }
         }
      }

   }

   public void freeClassInfoFinder() {
      this.classInfosFinder = null;
   }
}
