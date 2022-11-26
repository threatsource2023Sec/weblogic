package weblogic.application.utils.annotation;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.zip.ZipEntry;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.internal.Controls;
import weblogic.application.metadatacache.ValidatingCacheObject;
import weblogic.application.utils.ParallelTaskManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.classloaders.ZipSource;
import weblogic.utils.classloaders.debug.ClasspathHelper;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class ClassfinderClassInfos implements Externalizable, ClassInfoFinder, ValidatingCacheObject {
   private static final long serialVersionUID = 6868039194768851530L;
   private static String separator = System.getProperty("path.separator");
   private boolean isParallelAnnotaionScan = Boolean.valueOf(System.getProperty("weblogic.application.ParallelAnnotationScan", "true"));
   protected Cache classInfos = new Cache();
   protected final Set handleTypes = new HashSet();
   protected Map computedDependentImplementations = new HashMap();
   protected Cache fieldAnnotations = new Cache();
   protected Cache methodAnnotations = new Cache();
   protected SourceInfo fileInfo = new SourceInfo();
   protected Path topPath = null;
   private boolean keepAnnotatedClassesOnly;
   private static final DebugLogger scanVerboseDebugger = DebugLogger.getDebugLogger("DebugAppAnnoScanVerbose");
   private static final DebugLogger queryVerboseDebugger = DebugLogger.getDebugLogger("DebugAppAnnoQueryVerbose");
   private static final DebugLogger queryDebugger = DebugLogger.getDebugLogger("DebugAppAnnoQuery");
   private static final DebugLogger timingDebugger = DebugLogger.getDebugLogger("DebugAppTiming");
   private static final DebugLogger annoScanDataDebugger = DebugLogger.getDebugLogger("DebugAppAnnoScanData");
   private static final DebugLogger lookupDebugger = DebugLogger.getDebugLogger("DebugAppAnnoLookup");
   private static final DebugLogger lookupVerboseDebugger = DebugLogger.getDebugLogger("DebugAppAnnoVerboseLookup");
   transient String debugDesc;

   ClassfinderClassInfos(ClassInfoFinderFactory.Params params) throws AnnotationProcessingException {
      this.keepAnnotatedClassesOnly = Controls.keepannotatedclassesonly.enabled;
      this.debugDesc = null;
      this.keepAnnotatedClassesOnly = params.keepAnnotatedClassesOnly;
      if (scanVerboseDebugger.isDebugEnabled() || timingDebugger.isDebugEnabled() || annoScanDataDebugger.isDebugEnabled()) {
         if (params.virtualJarFile != null) {
            this.debugDesc = "Path: " + params.virtualJarFile.getName();
         } else {
            StringBuilder builder = new StringBuilder();
            builder.append("ClassPath: ").append(params.classFinder.getClassPath()).append('\n').append("Formatted ClassPath: ").append('\n');
            ClasspathHelper.printFormattedClasspath(params.classFinder, builder);
            this.debugDesc = builder.toString();
         }
      }

      if (scanVerboseDebugger.isDebugEnabled()) {
         scanVerboseDebugger.debug("Instantiating ClassInfoFinder in app: " + ApplicationAccess.getApplicationAccess().getCurrentApplicationId() + " " + this.debugDesc);
      }

      this.topPath = topPathFromParams(params);
      long startTime = System.currentTimeMillis();
      boolean var20 = false;

      try {
         var20 = true;
         Set rootFilesUsed;
         Iterator var5;
         if (params.classFinder != null) {
            rootFilesUsed = this.populateClassInfos(params.classFinder, params.componentAnnotations);
            if (rootFilesUsed != null) {
               var5 = rootFilesUsed.iterator();

               label212:
               while(true) {
                  String path;
                  do {
                     if (!var5.hasNext()) {
                        var20 = false;
                        break label212;
                     }

                     ClassFinder finder = (ClassFinder)var5.next();
                     path = finder.getClassPath();
                  } while(path == null);

                  StringTokenizer st = new StringTokenizer(path, separator);

                  while(st.hasMoreTokens()) {
                     String entry = st.nextToken();
                     File e = new File(entry);

                     try {
                        e = new File(e.getCanonicalPath());
                     } catch (IOException var22) {
                     }

                     Path item = e.toPath();
                     if (this.topPath != null && item.startsWith(this.topPath)) {
                        this.fileInfo.add(this.topPath.relativize(item), e);
                     }
                  }
               }
            } else {
               var20 = false;
            }
         } else {
            if (params.virtualJarFile == null) {
               throw new IllegalArgumentException("One and only one of the class finderor virtual jar file must be non-null");
            }

            rootFilesUsed = this.populateClassInfos(params.virtualJarFile, params.componentAnnotations);
            if (rootFilesUsed != null) {
               var5 = rootFilesUsed.iterator();

               while(var5.hasNext()) {
                  File f = (File)var5.next();

                  try {
                     f = new File(f.getCanonicalPath());
                  } catch (IOException var21) {
                  }

                  Path item = f.toPath();
                  if (this.topPath != null && item.startsWith(this.topPath)) {
                     this.fileInfo.add(this.topPath.relativize(item), f);
                  }
               }

               var20 = false;
            } else {
               var20 = false;
            }
         }
      } finally {
         if (var20) {
            if (timingDebugger.isDebugEnabled()) {
               long currTime = System.currentTimeMillis();
               long elapsedTime = currTime - startTime;
               timingDebugger.debug("Annotation Scanning started at " + new Date(startTime) + " and finished working at " + new Date(currTime) + " taking a total of " + elapsedTime + " in milli-seconds. AppId: " + ApplicationAccess.getApplicationAccess().getCurrentApplicationId() + " with " + this.debugDesc, new Exception());
            }

            if (annoScanDataDebugger.isDebugEnabled()) {
               this.debugScanData(annoScanDataDebugger);
            }

         }
      }

      if (timingDebugger.isDebugEnabled()) {
         long currTime = System.currentTimeMillis();
         long elapsedTime = currTime - startTime;
         timingDebugger.debug("Annotation Scanning started at " + new Date(startTime) + " and finished working at " + new Date(currTime) + " taking a total of " + elapsedTime + " in milli-seconds. AppId: " + ApplicationAccess.getApplicationAccess().getCurrentApplicationId() + " with " + this.debugDesc, new Exception());
      }

      if (annoScanDataDebugger.isDebugEnabled()) {
         this.debugScanData(annoScanDataDebugger);
      }

   }

   private static Path topPathFromParams(ClassInfoFinderFactory.Params params) {
      File top = params.srcFileForStaleCheck;
      if (top != null) {
         try {
            top = new File(top.getCanonicalPath());
         } catch (IOException var3) {
         }
      }

      return top != null ? top.toPath() : null;
   }

   public ClassfinderClassInfos() {
      this.keepAnnotatedClassesOnly = Controls.keepannotatedclassesonly.enabled;
      this.debugDesc = null;
   }

   public Map getAnnotatedClasses(String... annotations) {
      Map results = this.categorizeAnnotatedClasses(annotations);
      if (lookupDebugger.isDebugEnabled()) {
         this.debugQuery(lookupDebugger, results, annotations, (ClassInfoFinder.Filter)null, false, (ClassLoader)null, false, (AnnotationAncestry)null, this.getClass(), "getAnnotatedClasses");
      }

      return results;
   }

   private Map categorizeAnnotatedClasses(String... annos) {
      Map annotatedClassesMap = new HashMap();
      String[] var3 = annos;
      int var4 = annos.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String annotation = var3[var5];
         Set set = this.getAnnotatedClasses(this.classInfos, annotation);
         if (set == null) {
            set = Collections.emptySet();
         }

         annotatedClassesMap.put(annotation, set);
      }

      return annotatedClassesMap;
   }

   public URL getClassSourceUrl(String className) {
      ClassInfo classInfo = (ClassInfo)this.classInfos.get(toInternalName(className));
      return classInfo == null ? null : classInfo.getUrl();
   }

   public Set getHandlesImpls(ClassLoader cl, String... handlesTypes) {
      if (handlesTypes != null && handlesTypes.length != 0) {
         Set handlesImpls = new HashSet();
         Map classInfosInClassPath = null;
         String[] var5 = handlesTypes;
         int var6 = handlesTypes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String handleType = var5[var7];
            String internalHandleType = toInternalName(handleType);
            ClassInfo handleTypeInfo = (ClassInfo)this.classInfos.get(internalHandleType);
            Iterator var12;
            if ((handleTypeInfo == null || ((ClassInfo)handleTypeInfo).getUrl() == null) && !this.handleTypes.contains(handleType)) {
               Class handleClass = this.loadClass(cl, handleType);
               var12 = this.classInfos.values().iterator();

               label100:
               while(true) {
                  while(true) {
                     ClassInfo classInfo;
                     Class clz;
                     do {
                        do {
                           do {
                              if (!var12.hasNext()) {
                                 this.handleTypes.add(handleType);
                                 break label100;
                              }

                              classInfo = (ClassInfo)var12.next();
                           } while(classInfo.getUrl() != null);
                        } while("java/lang/Object".equals(classInfo.getClassName()));

                        clz = this.loadClass(cl, toClassName(classInfo.getClassName()));
                     } while(clz == null);

                     if (classInfo == handleTypeInfo) {
                        List implClasses = classInfo.getImplementations();
                        List implClassInfos = new ArrayList();
                        Iterator var17 = implClasses.iterator();

                        while(var17.hasNext()) {
                           String implClass = (String)var17.next();
                           ClassInfo implClassInfo = (ClassInfo)this.classInfos.get(implClass);
                           if (implClassInfo != null) {
                              implClassInfos.add(implClassInfo);
                           }
                        }

                        var17 = implClassInfos.iterator();

                        while(var17.hasNext()) {
                           ClassInfo info = (ClassInfo)var17.next();
                           this.addAllImplementations((ClassInfo)handleTypeInfo, info);
                        }
                     } else if (handleClass != null && handleClass.isAssignableFrom(clz)) {
                        if (handleTypeInfo == null) {
                           handleTypeInfo = new ClassInfoImpl(internalHandleType);
                           if (classInfosInClassPath == null) {
                              classInfosInClassPath = new HashMap();
                           }

                           classInfosInClassPath.put(internalHandleType, handleTypeInfo);
                        }

                        this.addAllImplementations((ClassInfo)handleTypeInfo, classInfo);
                     }
                  }
               }
            }

            if (handleTypeInfo != null) {
               Iterator var20 = ((ClassInfo)handleTypeInfo).getAnnotatedClasses().iterator();

               while(var20.hasNext()) {
                  String internalName = (String)var20.next();
                  handlesImpls.add(toClassName(internalName));
               }

               Set computedImpls = (Set)this.computedDependentImplementations.get(internalHandleType);
               if (computedImpls == null) {
                  computedImpls = new HashSet();
                  var12 = ((ClassInfo)handleTypeInfo).getImplementations().iterator();

                  while(var12.hasNext()) {
                     String internalName = (String)var12.next();
                     ((Set)computedImpls).add(toClassName(internalName));
                     ((Set)computedImpls).addAll(this.computeDependentImpls(internalName));
                  }

                  this.computedDependentImplementations.put(internalHandleType, computedImpls);
               }

               handlesImpls.addAll((Collection)computedImpls);
            }
         }

         if (classInfosInClassPath != null) {
            this.classInfos.putAll(classInfosInClassPath);
         }

         return handlesImpls;
      } else {
         return Collections.emptySet();
      }
   }

   private Class loadClass(ClassLoader loader, String className) {
      try {
         return Class.forName(className, false, loader);
      } catch (Throwable var4) {
         J2EELogger.logClassLoadFailedWhenLookupHandleTypeImplementations(loader == null ? "Bootstrap ClassLoader" : loader.toString(), className, var4.getMessage());
         return null;
      }
   }

   private Set computeDependentImpls(String anImplementation) {
      Set dependentImplementations = new HashSet();
      Stack workingStack = new Stack();
      workingStack.push(anImplementation);

      while(true) {
         String currentImpl;
         do {
            if (workingStack.isEmpty()) {
               return dependentImplementations;
            }

            currentImpl = (String)workingStack.pop();
         } while(!this.classInfos.keySet().contains(currentImpl));

         ClassInfo ci = (ClassInfo)this.classInfos.get(currentImpl);
         synchronized(ci) {
            Iterator var7 = ci.getImplementations().iterator();

            while(var7.hasNext()) {
               String dependentImpl = (String)var7.next();
               dependentImplementations.add(toClassName(dependentImpl));
               workingStack.push(dependentImpl);
            }
         }
      }
   }

   private void addAllImplementations(ClassInfo superClassInfo, ClassInfo classInfo) {
      if (!superClassInfo.getClassName().equals(classInfo.getClassName())) {
         List implementations = classInfo.getImplementations();
         if (implementations != null && !implementations.isEmpty()) {
            Iterator var4 = implementations.iterator();

            while(var4.hasNext()) {
               String implClass = (String)var4.next();
               superClassInfo.addImplementation(implClass);
               ClassInfo implClassInfo = (ClassInfo)this.classInfos.get(implClass);
               if (implClassInfo != null) {
                  this.addAllImplementations(superClassInfo, implClassInfo);
               }
            }

         }
      }
   }

   private Set populateClassInfos(ClassFinder classFinder, Class... componentAnnotations) throws AnnotationProcessingException {
      ParallelTaskManager parallelTaskManager = ParallelTaskManager.create(this.getClass(), "finderscan", scanVerboseDebugger);
      Set findersUsed = new ConcurrentHashSet();
      this.populateClassInfos(findersUsed, classFinder, parallelTaskManager, componentAnnotations);
      ErrorCollectionException ece = parallelTaskManager.finishAndAwaitResults();
      if (ece != null) {
         throw new AnnotationProcessingException(ece);
      } else {
         return findersUsed;
      }
   }

   private void populateClassInfos(final Set findersUsed, final ClassFinder classFinder, ParallelTaskManager manager, final Class... componentAnnotations) throws AnnotationProcessingException {
      if (classFinder instanceof MultiClassFinder) {
         ClassFinder[] var5 = ((MultiClassFinder)classFinder).getClassFindersForAnnotationScan();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ClassFinder cf = var5[var7];
            this.populateClassInfos(findersUsed, cf, manager, componentAnnotations);
         }
      } else {
         if (scanVerboseDebugger.isDebugEnabled()) {
            scanVerboseDebugger.debug("Creating ClassInfos for ClassFinder for " + classFinder.getClassPath());
         }

         if (this.isParallelAnnotaionScan) {
            manager.schedule(new Callable() {
               public Void call() throws Exception {
                  ClassfinderClassInfos.this.populateClassInfos(findersUsed, classFinder, componentAnnotations);
                  return null;
               }
            });
         } else {
            this.populateClassInfos(findersUsed, classFinder, componentAnnotations);
         }
      }

   }

   private void populateClassInfos(Set findersUsed, ClassFinder classFinder, Class... componentAnnotations) throws AnnotationProcessingException {
      boolean finderWasUsed = false;
      Enumeration entries = classFinder.entries();

      while(entries.hasMoreElements()) {
         Source source = (Source)entries.nextElement();
         if (source == null) {
            throw new RuntimeException("The following classfinder returned a null source entry:" + classFinder.getClass().getName());
         }

         ClassInfo scannedInfo = this.getClassInfoFromSource(source, componentAnnotations);
         if (scannedInfo != null && this.polulateOneClassInfoIfValid(scannedInfo)) {
            finderWasUsed = true;
         }
      }

      if (finderWasUsed) {
         findersUsed.add(classFinder);
      }

   }

   private ClassInfo getClassInfoFromSource(Source source, Class... componentAnnotations) throws AnnotationProcessingException {
      URL url = source.getURL();
      if (url != null && url.getPath() != null && url.getPath().endsWith(".class")) {
         try {
            return new ClassInfoImpl(source.getBytes(), url, source.getCodeSourceURL(), componentAnnotations);
         } catch (IOException var7) {
            throw new AnnotationProcessingException("Failed to parse class file " + url + ".", var7);
         } catch (RuntimeException var8) {
            DebugLogger dbgLogger = DebugLogger.getDebugLogger("DebugAppAnnoScanVerbose");
            String stacktrace = dbgLogger.isDebugEnabled() ? Arrays.toString(var8.getStackTrace()) : "";
            J2EELogger.logUnableToParseClassFile(url.toString(), stacktrace);
            return null;
         }
      } else {
         return null;
      }
   }

   private Set populateClassInfos(VirtualJarFile vjf, Class... componentAnnotations) throws AnnotationProcessingException {
      Set rootFilesUsed = new HashSet();
      File[] var4 = vjf.getRootFiles();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File rootFile = var4[var6];
         boolean rootFileUsed = false;
         if (rootFile.exists()) {
            VirtualJarFile vjar = null;

            try {
               vjar = VirtualJarFactory.createVirtualJar(rootFile);
               Iterator it = vjf.entries();

               while(it.hasNext()) {
                  ZipEntry ze = (ZipEntry)it.next();
                  if (ze.getName().endsWith(".class")) {
                     Source source = getSource(vjar, ze.getName());
                     if (source != null) {
                        ClassInfo scannedInfo = this.getClassInfoFromSource(source, componentAnnotations);
                        if (scannedInfo != null && this.polulateOneClassInfoIfValid(scannedInfo)) {
                           rootFileUsed = true;
                        }
                     }
                  }
               }
            } catch (IOException var21) {
               throw new AnnotationProcessingException("Exception occured processing Annotations", var21);
            } finally {
               if (vjar != null) {
                  try {
                     vjar.close();
                  } catch (IOException var20) {
                  }
               }

            }

            if (rootFileUsed) {
               rootFilesUsed.add(rootFile);
            }
         }
      }

      return rootFilesUsed;
   }

   private static Source getSource(VirtualJarFile vjar, String relativePath) throws IOException {
      if (vjar.isDirectory()) {
         URL url = vjar.getResource(relativePath);
         if (url != null) {
            return new URLSource(url);
         }

         if (scanVerboseDebugger.isDebugEnabled()) {
            scanVerboseDebugger.debug("DIR CASE: url: " + url + ", vjar: " + vjar.getName() + ", relativePath:" + relativePath + ", vjar class" + vjar.getClass().getName());
         }
      } else {
         ZipEntry ze = vjar.getEntry(relativePath);
         if (ze != null) {
            return new ZipSource(vjar.getJarFile(), ze);
         }

         if (scanVerboseDebugger.isDebugEnabled()) {
            scanVerboseDebugger.debug("ZIP case: relativePath:" + relativePath + ", vjar: " + vjar.getName());
         }
      }

      return null;
   }

   private boolean polulateOneClassInfoIfValid(ClassInfo classInfo) throws AnnotationProcessingException {
      if (classInfo.shouldBePopulated()) {
         if (!this.keepAnnotatedClassesOnly) {
            this.polulateOneClassInfo(classInfo);
            return true;
         }

         if (classInfo.isAnnotated() || classInfo.isAnnotation()) {
            this.polulateOneClassInfo(classInfo);
            return true;
         }
      }

      return false;
   }

   private void polulateOneClassInfo(ClassInfo newClassInfo) throws AnnotationProcessingException {
      this.classInfos.putOrMerge(newClassInfo);
      this.processAnnotations(newClassInfo);
      this.processInterfaces(newClassInfo);
      this.processSuperClass(newClassInfo);
   }

   private void processAnnotations(ClassInfo classInfo) {
      String className = classInfo.getClassName();
      List annotations = classInfo.getClassLevelAnnotationNames();
      this.addAnnotationContributors(this.classInfos, className, annotations);
      this.addAnnotationContributors(this.fieldAnnotations, className, classInfo.getFieldAnnotations());
      this.addAnnotationContributors(this.methodAnnotations, className, classInfo.getMethodAnnotations());
   }

   private void addAnnotationContributors(Cache cache, String className, Iterable annotations) {
      Iterator var4 = annotations.iterator();

      while(var4.hasNext()) {
         String annotation = (String)var4.next();
         ClassInfo annotationInfo = cache.getOrCreateAnnotationInfo(annotation);
         synchronized(annotationInfo) {
            annotationInfo.addAnnotatedClass(className);
         }
      }

   }

   private void processInterfaces(ClassInfo classInfo) {
      List interfaces = classInfo.getInterfaceNames();
      String className = classInfo.getClassName();
      Iterator var4 = interfaces.iterator();

      while(var4.hasNext()) {
         String intf = (String)var4.next();
         this.processSuperType(className, intf);
      }

   }

   private void processSuperClass(ClassInfo classInfo) {
      String superClassName = classInfo.getSuperClassName();
      if (superClassName != null) {
         String className = classInfo.getClassName();
         this.processSuperType(className, superClassName);
      }
   }

   private void processSuperType(String className, String superClassName) {
      Object superInfo;
      if (!this.classInfos.containsKey(superClassName)) {
         superInfo = new ClassInfoImpl(superClassName);
         this.classInfos.put(superClassName, superInfo);
      } else {
         superInfo = (ClassInfo)this.classInfos.get(superClassName);
      }

      synchronized(superInfo) {
         ((ClassInfo)superInfo).addImplementation(className);
      }
   }

   private static String toInternalName(String className) {
      return className.replace('.', '/');
   }

   public static String toClassName(String internalName) {
      return internalName.replace('/', '.');
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      String[] classNamePool = this.toClassNamePool();
      out.writeObject(classNamePool);
      this.classInfos.writeInfos(out, classNamePool);
      this.fieldAnnotations.writeInfos(out, classNamePool);
      this.methodAnnotations.writeInfos(out, classNamePool);
      out.writeObject(this.fileInfo);
   }

   private String[] toClassNamePool() {
      HashSet cnpSet = new HashSet(this.classInfos.keySet());
      cnpSet.addAll(this.fieldAnnotations.keySet());
      cnpSet.addAll(this.methodAnnotations.keySet());
      String[] classNamePool = (String[])cnpSet.toArray(new String[0]);
      Arrays.sort(classNamePool);
      return classNamePool;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      String[] classNamePool = (String[])((String[])in.readObject());
      this.classInfos = (new Cache()).readInfos(in, classNamePool);
      this.fieldAnnotations = (new Cache()).readInfos(in, classNamePool);
      this.methodAnnotations = (new Cache()).readInfos(in, classNamePool);
      this.fileInfo = (SourceInfo)in.readObject();
   }

   public Set getClassNamesWithAnnotations(String... annotations) {
      Set results = this.getAnnotatedClasses(this.classInfos, annotations);
      if (lookupDebugger.isDebugEnabled()) {
         this.debugQuery(lookupDebugger, results, annotations, (ClassInfoFinder.Filter)null, false, (ClassLoader)null, false, (AnnotationAncestry)null, this.getClass(), "getClassNamesWithAnnotations");
      }

      return results;
   }

   private Set getAnnotatedClasses(Cache cache, String... annotations) {
      if (annotations == null) {
         return Collections.emptySet();
      } else {
         Set annotatedClasses = new LinkedHashSet();
         String[] var4 = annotations;
         int var5 = annotations.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String annotation = var4[var6];
            annotation = toInternalName(annotation);
            ClassInfo annotationInfo = (ClassInfo)cache.get(annotation);
            if (annotationInfo != null) {
               synchronized(annotatedClasses) {
                  Iterator var10 = annotationInfo.getAnnotatedClasses().iterator();

                  while(var10.hasNext()) {
                     String internalName = (String)var10.next();
                     annotatedClasses.add(toClassName(internalName));
                  }
               }
            }
         }

         return annotatedClasses;
      }
   }

   public Map getAnnotatedClassesByTargetsAndSources(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
      return this.getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader, false, getAnnotationAncestry(annotations, classLoader));
   }

   protected Map getAnnotatedClassesByTargetsAndSources(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader, boolean exitOnFirstMatch, AnnotationAncestry aa) {
      if (includeExtendedAnnotations && classLoader == null) {
         throw new IllegalArgumentException("Class loader must be provided when includeExtendedAnnotations is true");
      } else {
         Map results = Collections.emptyMap();
         ClassInfoFinder.Target[] var8 = ClassInfoFinder.Target.values();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ClassInfoFinder.Target target = var8[var10];
            if (filter == null || filter.accept(target)) {
               Cache cache = this.getCache(target);
               Set annotationsOfInterest = cache.getAnnotationsOfInterest(aa, annotations, includeExtendedAnnotations);
               if (queryVerboseDebugger.isDebugEnabled()) {
                  queryVerboseDebugger.debug("Annotations of interest: " + annotationsOfInterest);
               }

               Iterator var14 = annotationsOfInterest.iterator();

               while(var14.hasNext()) {
                  String annotation = (String)var14.next();
                  annotation = toInternalName(annotation);
                  Map annotatedClassesBySources = (Map)((Map)results).get(target);
                  Map newAnnotatedClassesBySources = this.getAnnotatedClasses(annotatedClassesBySources, filter, target, (ClassInfo)cache.get(annotation), exitOnFirstMatch);
                  if (annotatedClassesBySources != newAnnotatedClassesBySources) {
                     if (((Map)results).isEmpty()) {
                        results = new HashMap();
                     }

                     ((Map)results).put(target, newAnnotatedClassesBySources);
                     if (exitOnFirstMatch) {
                        if (queryDebugger.isDebugEnabled()) {
                           this.debugQuery(queryDebugger, results, annotations, filter, includeExtendedAnnotations, classLoader, exitOnFirstMatch, aa, this.getClass(), "getAnnotatedClassesByTargetsAndSources");
                        }

                        return (Map)results;
                     }
                  }
               }
            }
         }

         if (queryDebugger.isDebugEnabled()) {
            this.debugQuery(queryDebugger, results, annotations, filter, includeExtendedAnnotations, classLoader, exitOnFirstMatch, aa, this.getClass(), "getAnnotatedClassesByTargetsAndSources");
         }

         return (Map)results;
      }
   }

   static AnnotationAncestry getAnnotationAncestry(String[] annotations, ClassLoader classLoader) {
      return annotations != null && annotations.length > 0 ? new AnnotationAncestry(classLoader, annotations) : null;
   }

   public boolean hasAnnotatedClasses(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
      return !this.getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader, true, getAnnotationAncestry(annotations, classLoader)).isEmpty();
   }

   private Map getAnnotatedClasses(Map dest, ClassInfoFinder.Filter filter, ClassInfoFinder.Target target, ClassInfo annotationInfo, boolean exitOnFirstMatch) {
      if (annotationInfo != null) {
         synchronized(annotationInfo) {
            Iterator var7 = annotationInfo.getAnnotatedClasses().iterator();

            while(true) {
               String internalName;
               String annotatedClassName;
               URL codeSourceURL;
               do {
                  ClassInfo classInfo;
                  do {
                     do {
                        if (!var7.hasNext()) {
                           return (Map)dest;
                        }

                        internalName = (String)var7.next();
                        annotatedClassName = toClassName(internalName);
                        classInfo = (ClassInfo)this.classInfos.get(internalName);
                     } while(classInfo == null);
                  } while(classInfo.isAnnotation());

                  codeSourceURL = classInfo.getCodeSourceUrl();
               } while(filter != null && !filter.accept(target, codeSourceURL, annotationInfo.getClassName(), annotatedClassName));

               URI codeSourceURI = this.createCodeSourceURI(codeSourceURL);
               if (codeSourceURI != null) {
                  if (dest == null) {
                     dest = new HashMap();
                  }

                  Set annotatedClasses = (Set)((Map)dest).get(codeSourceURI);
                  if (annotatedClasses == null) {
                     annotatedClasses = new HashSet();
                     ((Map)dest).put(codeSourceURI, annotatedClasses);
                  }

                  ((Set)annotatedClasses).add(toClassName(internalName));
                  if (exitOnFirstMatch) {
                     return (Map)dest;
                  }
               }
            }
         }
      } else {
         return (Map)dest;
      }
   }

   private URI createCodeSourceURI(URL url) {
      try {
         return url.toURI();
      } catch (URISyntaxException var4) {
         if (scanVerboseDebugger.isDebugEnabled()) {
            scanVerboseDebugger.debug("URISyntaxException while invoking toURI() on URL :" + url, var4);
         }

         String path = url.getPath();
         if (path != null) {
            return (new File(path)).toURI();
         } else {
            if (scanVerboseDebugger.isDebugEnabled()) {
               scanVerboseDebugger.debug("getPath() found to be null for URL: " + url);
            }

            return null;
         }
      }
   }

   Cache getCache(ClassInfoFinder.Target target) {
      switch (target) {
         case CLASS:
            return this.classInfos;
         case FIELD:
            return this.fieldAnnotations;
         case METHOD:
            return this.methodAnnotations;
         default:
            throw new AssertionError("Only support the CLASS, FIELD and METHOD targets");
      }
   }

   public boolean isValid(Object validatingParams) {
      if (this.topPath == null && validatingParams instanceof ClassInfoFinderFactory.Params) {
         this.topPath = topPathFromParams((ClassInfoFinderFactory.Params)validatingParams);
      }

      if (this.topPath == null) {
         return this.fileInfo == null || this.fileInfo.isEmpty();
      } else {
         if (this.fileInfo != null) {
            Iterator var2 = this.fileInfo.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               String key = (String)entry.getKey();
               Path path = this.topPath.resolve(key);
               File file = path.toFile();
               if (!file.exists()) {
                  return false;
               }

               SourceEntry fie = (SourceEntry)entry.getValue();
               if (fie.lastModified > 0L && file.lastModified() != fie.lastModified) {
                  return false;
               }

               if (fie.length > 0L && file.length() != fie.length) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public Set getAllSubClassNames(String className) {
      ClassInfo ci = (ClassInfo)this.classInfos.get(toInternalName(className));
      if (ci == null) {
         return null;
      } else {
         Set allImplementations = new HashSet();
         this.collectAllImplementations(ci.getImplementations(), allImplementations);
         return allImplementations;
      }
   }

   private void collectAllImplementations(List implementations, Set allImplementations) {
      Iterator var3 = implementations.iterator();

      while(var3.hasNext()) {
         String implementation = (String)var3.next();
         String implClassName = toClassName(implementation);
         if (!allImplementations.contains(implClassName)) {
            allImplementations.add(implClassName);
            this.collectAllImplementations(((ClassInfo)this.classInfos.get(implementation)).getImplementations(), allImplementations);
         }
      }

   }

   protected void debugQuery(DebugLogger debugger, Object results, String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader, boolean exitOnFirstMatch, AnnotationAncestry aa, Class queryClass, String methodName) {
      Map debugParams = debugger.getDebugParameters();
      String annotationsList = (String)debugParams.get("Annotations");
      boolean printScanData;
      if (annotationsList != null && annotationsList.length() > 0) {
         Set searchedAnnotations = new LinkedHashSet(Arrays.asList(annotations));
         String[] filteredAnnotations = annotationsList.split(",");
         printScanData = false;

         for(int index = 0; index < filteredAnnotations.length && !printScanData; printScanData = searchedAnnotations.contains(filteredAnnotations[index++])) {
         }

         if (!printScanData) {
            return;
         }
      }

      String limitQueryClass = (String)debugParams.get("ClassName");
      if (limitQueryClass == null || limitQueryClass.length() <= 0 || queryClass.getName().equals(limitQueryClass)) {
         String limitMethodName = (String)debugParams.get("MethodName");
         if (limitMethodName == null || limitMethodName.length() <= 0 || limitMethodName.equals(methodName)) {
            printScanData = Boolean.parseBoolean((String)debugParams.get("PrintScanData"));
            boolean printStackTrace = Boolean.parseBoolean((String)debugParams.get("PrintStackTrace"));
            StringBuilder builder = new StringBuilder();
            builder.append("===========\n");
            builder.append("Query Debug: ").append(queryClass.getSimpleName()).append(".").append(methodName).append("\n");
            builder.append("===========\n");
            builder.append("Parameters\n");
            builder.append("===========\n");
            if (annotations != null) {
               builder.append("Annotations:\n");
               String[] var18 = annotations;
               int var19 = annotations.length;

               for(int var20 = 0; var20 < var19; ++var20) {
                  String annotation = var18[var20];
                  builder.append("  ").append(annotation).append("\n");
               }
            }

            builder.append("Filter: ").append(filter).append("\n");
            builder.append("IncludeExtendedAnnotations: ").append(includeExtendedAnnotations).append("\n");
            builder.append("ClassLoader: ").append(classLoader).append("\n");
            builder.append("ExitOnFirstMatch: ").append(exitOnFirstMatch).append("\n");
            builder.append("AnnotationAncestry: ").append(aa).append("\n");
            builder.append("===========\n");
            builder.append("Results\n");
            builder.append("===========\n");
            Set keySet;
            if (results instanceof Map) {
               keySet = ((Map)results).keySet();
               if (keySet.size() > 0) {
                  Object firstKey = keySet.iterator().next();
                  Map refinedResults;
                  Iterator var36;
                  if (firstKey instanceof ClassInfoFinder.Target) {
                     refinedResults = (Map)results;
                     var36 = refinedResults.keySet().iterator();

                     while(var36.hasNext()) {
                        ClassInfoFinder.Target target = (ClassInfoFinder.Target)var36.next();
                        builder.append("    ").append("Target: ").append(target).append("\n");
                        Map details = (Map)refinedResults.get(target);
                        Iterator var24 = details.keySet().iterator();

                        while(var24.hasNext()) {
                           URI uri = (URI)var24.next();
                           builder.append("        ").append("URI: ").append(uri).append("\n");
                           Set classNames = (Set)details.get(uri);
                           Iterator var27 = classNames.iterator();

                           while(var27.hasNext()) {
                              String className = (String)var27.next();
                              builder.append("            ").append(className).append("\n");
                           }
                        }
                     }
                  } else if (firstKey instanceof String) {
                     refinedResults = (Map)results;
                     var36 = refinedResults.keySet().iterator();

                     while(var36.hasNext()) {
                        String key = (String)var36.next();
                        builder.append("    ").append("Annotation: ").append(key).append("\n");
                        Iterator var39 = ((Set)refinedResults.get(key)).iterator();

                        while(var39.hasNext()) {
                           String className = (String)var39.next();
                           builder.append("      ").append(className).append("\n");
                        }
                     }
                  }
               }
            } else if (results instanceof Set) {
               keySet = (Set)results;
               Iterator var34 = keySet.iterator();

               while(var34.hasNext()) {
                  String className = (String)var34.next();
                  builder.append("    ").append(className).append("\n");
               }
            }

            builder.append("===========\n");
            if (printScanData) {
               this.debugScanData(builder);
            }

            if (printStackTrace) {
               debugger.debug(builder.toString(), new Exception("Debug Exception"));
            } else {
               debugger.debug(builder.toString());
            }

         }
      }
   }

   private void debugScanData(DebugLogger debugger) {
      StringBuilder builder = new StringBuilder();
      this.debugScanData(builder);
      debugger.debug(builder.toString());
   }

   void debugScanData(StringBuilder builder) {
      Collection classInfoCollection = this.classInfos.values();
      builder.append("\n==============\n");
      builder.append("Annotation Scanning Data\n");
      builder.append("Description: ").append(this.debugDesc != null ? this.debugDesc : "").append("\n");
      builder.append("Annotated Classes\n");
      builder.append("-----------------\n");
      Iterator var3 = classInfoCollection.iterator();

      ClassInfo classInfo;
      while(var3.hasNext()) {
         classInfo = (ClassInfo)var3.next();
         if (classInfo.isAnnotated()) {
            this.appendClassInfoData(builder, classInfo);
         }
      }

      builder.append("-----------------\n");
      builder.append("Annotation Classes\n");
      builder.append("-----------------\n");
      var3 = classInfoCollection.iterator();

      while(var3.hasNext()) {
         classInfo = (ClassInfo)var3.next();
         if (classInfo.isAnnotation()) {
            this.appendClassInfoData(builder, classInfo);
         }
      }

      builder.append("==============\n");
   }

   private void appendClassInfoData(StringBuilder builder, ClassInfo classInfo) {
      builder.append("Name: ").append(classInfo.getClassName()).append("\n");
      builder.append("Instance: ").append(classInfo).append("\n");
      this.appendAnnotationData(builder, "Class Level Annotations", "  ", classInfo.getClassLevelAnnotationNames());
      this.appendAnnotationData(builder, "Method Annotations", "  ", classInfo.getMethodAnnotations());
      this.appendAnnotationData(builder, "Field Annotations", "  ", classInfo.getFieldAnnotations());
      ((ClassInfoImpl)classInfo).appendScanData(builder, "  ");
   }

   private void appendAnnotationData(StringBuilder builder, String description, String indent, Collection annotationNames) {
      if (annotationNames.size() > 0) {
         builder.append(indent).append(description).append("\n");
         Iterator var5 = annotationNames.iterator();

         while(var5.hasNext()) {
            String classLevelAnnotationName = (String)var5.next();
            builder.append(indent).append(indent).append(classLevelAnnotationName).append("\n");
         }
      }

   }

   private class Cache extends ConcurrentSkipListMap {
      private Cache() {
      }

      private void writeInfos(ObjectOutput out, String[] classNamePool) throws IOException {
         out.writeInt(this.size());
         Iterator var3 = this.values().iterator();

         while(var3.hasNext()) {
            ClassInfo classInfo = (ClassInfo)var3.next();
            ClassInfoImpl info = (ClassInfoImpl)classInfo;
            info.writeExternal(out, classNamePool);
         }

      }

      private Cache readInfos(ObjectInput in, String[] classNamePool) throws IOException, ClassNotFoundException {
         int cacheSize = in.readInt();

         for(int i = 0; i < cacheSize; ++i) {
            ClassInfoImpl info = new ClassInfoImpl();
            info.readExternal(in, classNamePool);
            this.put(info.getClassName(), info);
         }

         return this;
      }

      private synchronized Set getAnnotationsOfInterest(AnnotationAncestry aa, String[] annotations, boolean includeExtendedAnnotations) {
         if (aa == null) {
            return this.keySet();
         } else {
            Set results = new HashSet();
            results.addAll(Arrays.asList(annotations));
            if (includeExtendedAnnotations) {
               Iterator var5 = this.keySet().iterator();

               while(var5.hasNext()) {
                  String annotationName = (String)var5.next();
                  if (aa.isExtendedAnnotation(annotationName, (ClassInfo)this.get(annotationName))) {
                     results.add(annotationName);
                  }
               }
            }

            return results;
         }
      }

      synchronized void putOrMerge(ClassInfo newClassInfo) {
         String className = newClassInfo.getClassName();
         ClassInfo existingClassInfo = (ClassInfo)this.get(className);
         if (existingClassInfo != null) {
            if (existingClassInfo.getUrl() != null) {
               if (newClassInfo.getUrl() != null) {
                  if (ClassfinderClassInfos.scanVerboseDebugger.isDebugEnabled()) {
                     ClassfinderClassInfos.scanVerboseDebugger.debug("Ignoring class from url " + newClassInfo.getUrl() + " since it has already been found and scanned at " + existingClassInfo.getUrl());
                  }
               } else {
                  existingClassInfo.merge(newClassInfo);
               }
            } else {
               newClassInfo.merge(existingClassInfo);
               this.put(className, newClassInfo);
            }
         } else {
            this.put(className, newClassInfo);
         }

      }

      private synchronized ClassInfo getOrCreateAnnotationInfo(String annotationName) {
         if (!this.containsKey(annotationName)) {
            ClassInfo annotationInfo = new ClassInfoImpl(annotationName);
            this.put(annotationName, annotationInfo);
            return annotationInfo;
         } else {
            return (ClassInfo)this.get(annotationName);
         }
      }

      // $FF: synthetic method
      Cache(Object x1) {
         this();
      }
   }

   private class SourceEntry implements Serializable {
      private final long lastModified;
      private final long length;

      public SourceEntry() {
         this(-1L, -1L);
      }

      public SourceEntry(long lastModified, long length) {
         this.lastModified = lastModified;
         this.length = length;
      }

      public SourceEntry(File file) {
         this(file.lastModified(), file.length());
      }
   }

   private class SourceInfo extends HashMap {
      private SourceInfo() {
      }

      public void add(Path relativePath, File file) {
         this.put(relativePath.toString(), ClassfinderClassInfos.this.new SourceEntry(file));
      }

      // $FF: synthetic method
      SourceInfo(Object x1) {
         this();
      }
   }
}
