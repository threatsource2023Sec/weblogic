package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.internal.library.JarLibraryDefinition;
import weblogic.application.io.Archive;
import weblogic.application.io.ClasspathInfo;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.io.ManifestFinder;
import weblogic.application.io.ClasspathInfo.ArchiveType;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.application.utils.CompositeWebAppFinder;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.servlet.internal.tld.ExtensionTLD;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.ServletMapping;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MetadataAttachingFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.classloaders.NullSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.collections.SecondChanceCacheMap;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.jars.VirtualJarFile;

public class War implements WebAppHelper {
   public static final String WEB_INF = "WEB-INF";
   public static final String STANDARD_DD = "WEB-INF/web.xml";
   public static final String WEBLOGIC_DD = "WEB-INF/weblogic.xml";
   static final String WAR_EXTRACT_ROOT = "war";
   static final String ANNOTATION_MANAGEDBEAN = "javax.faces.bean.ManagedBean";
   private static final boolean DEBUG = false;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("WarExtraction");
   private Archive archive;
   private CompositeWebAppFinder classfinder = new CompositeWebAppFinder();
   private final ServletMapping virtualFinders = newServletMapping();
   private List libraries;
   private String uri;
   private File extractDir;
   private File warExtractDir;
   private StaleProber resourceReloadProber;
   private List tldURIs;
   private boolean findTldsCalled = false;
   private List facesConfigURIs = null;
   private ClassInfoFinder classInfos = null;
   private List extensions;
   private MultiClassFinder extensionsClassfinder;
   private Set virtualDirectories;
   private Map extensionTLDs;
   private String splitDirectoryClassPath;
   private File cacheDir;
   private boolean isArchiveReExtract;
   public static final ClasspathInfo WAR_CLASSPATH_INFO = new ClasspathInfo() {
      private final String[] WEB_INF_CLASSES;
      private final String[] WEB_INF_LIB;

      {
         this.WEB_INF_CLASSES = new String[]{"WEB-INF" + File.separator + "classes"};
         this.WEB_INF_LIB = new String[]{"WEB-INF" + File.separator + "lib"};
      }

      public String[] getClasspathURIs() {
         return this.WEB_INF_CLASSES;
      }

      public String[] getJarURIs() {
         return this.WEB_INF_LIB;
      }

      public ClasspathInfo.ArchiveType getArchiveType() {
         return ArchiveType.WAR;
      }
   };

   protected War() {
      this.extensionTLDs = Collections.EMPTY_MAP;
      this.isArchiveReExtract = false;
   }

   public War(String uri, File extractDir, VirtualJarFile vjf, boolean useOriginalJars, StaleProber resourceReloadProber, File cacheDir) throws IOException {
      this.extensionTLDs = Collections.EMPTY_MAP;
      this.isArchiveReExtract = false;
      this.uri = uri;
      this.extractDir = extractDir;
      this.resourceReloadProber = resourceReloadProber;
      this.cacheDir = cacheDir;
      if (vjf != null && extractDir != null) {
         this.initWithVirtualJarFile(vjf, useOriginalJars);
      } else {
         this.initNoOpWar();
      }
   }

   private void initWithVirtualJarFile(VirtualJarFile vjf, boolean useOriginalJars) throws IOException {
      this.debug("Creating War uri: " + this.uri + " extractDir :" + this.extractDir + " VJFDir: " + vjf.getDirectory());
      this.warExtractDir = new File(this.extractDir, "war");
      this.warExtractDir.mkdirs();
      File[] roots = vjf.getRootFiles();
      this.archive = this.makeExplodedJar(this.uri, this.warExtractDir, roots, useOriginalJars);
      this.classfinder.addFinder(this.archive.getClassFinder());

      for(int i = 0; i < roots.length; ++i) {
         if (roots[i] != null) {
            this.classfinder.addFinder(new ManifestFinder.ClassPathFinder(roots[i]));
         }
      }

      this.virtualFinders.put("/", new ResourceFinder(this.getURI() + "#", this.classfinder, this.resourceReloadProber));
   }

   private void initNoOpWar() {
      this.archive = new NoOpArchive();
      this.virtualFinders.put("/", new ResourceFinder(this.getURI() + "#", this.classfinder, this.resourceReloadProber));
   }

   public ClassFinder getClassFinder() {
      return this.classfinder;
   }

   public void remove() {
      if (this.extensions != null) {
         Iterator var1 = this.extensions.iterator();

         while(var1.hasNext()) {
            WarExtension extension = (WarExtension)var1.next();
            extension.remove();
         }

         this.extensions.clear();
      }

      this.archive.remove();
   }

   public String getURI() {
      return this.uri;
   }

   public File getExtractDir() {
      return this.extractDir;
   }

   private List getWebTLDLocations() {
      if (!this.findTldsCalled) {
         ClassFinder rf = this.getWebResourceFinder();
         List tldUris = new ArrayList();
         WarUtils.findTlds((ClassFinder)rf, (List)tldUris, (ClassFinder)this.classfinder.getWebappFinder());
         this.tldURIs = tldUris;
         this.findTldsCalled = true;
      }

      return this.tldURIs;
   }

   private List getWebFacesLocations(String jsfConfigFiles) {
      if (this.facesConfigURIs == null) {
         ClassFinder rf = this.getWebResourceFinder();
         this.facesConfigURIs = WarUtils.findFacesConfigs(jsfConfigFiles, rf, this.classfinder.getWebappFinder());
      }

      return this.facesConfigURIs;
   }

   private ClassFinder getWebResourceFinder() {
      return ((ResourceFinder)this.getResourceFinder("")).getWebResourceFinder();
   }

   public synchronized void addLibrary(Library library) throws IOException {
      this.addIfWarLibrary(library);
      this.addIfJarLibrary(library);
   }

   private void addIfJarLibrary(Library library) throws IOException {
      if (library instanceof JarLibraryDefinition) {
         JarLibraryDefinition original = (JarLibraryDefinition)library;
         if (this.libraries == null) {
            this.libraries = new ArrayList();
         }

         SharedJarLibraryDefinition jld = new SharedJarLibraryDefinition(original.getLibData(), original.getTemporaryDirectory());

         try {
            jld.init();
            this.libraries.add(jld);
            ClassFinder jarFinder = new JarClassFinder(jld.getLocation());
            ClassFinder libFinder = new MetadataAttachingFinder(jarFinder, new LibrarySourceMetadata(jld.isArchived()));
            this.classfinder.addLibraryFinder(libFinder);
         } catch (LibraryProcessingException var6) {
            throw new IOException(var6);
         }
      }
   }

   private void addIfWarLibrary(Library library) {
      if (library instanceof WarLibraryDefinition) {
         if (this.libraries == null) {
            this.libraries = new ArrayList();
         }

         WarLibraryDefinition wld = (WarLibraryDefinition)library;
         this.libraries.add(wld);
         String uri = this.getURI();
         ClassFinder libFinder = new MetadataAttachingFinder(wld.getClassFinder(uri), new LibrarySourceMetadata(wld.isArchived()));
         this.classfinder.addLibraryFinder(libFinder);
      }
   }

   public Source[] getLibResourcesAsSources(String path) {
      path = HttpParsing.ensureStartingSlash(path);
      List sources = new ArrayList();
      if (this.extensionsClassfinder != null) {
         ResourceFinder extResourceFinder = new ResourceFinder(this.uri + "#", this.extensionsClassfinder);
         sources.addAll(Collections.list(extResourceFinder.getSources(path)));
      }

      ClassFinder libClassFinder = this.classfinder.getLibraryFinder();
      if (libClassFinder != null) {
         ResourceFinder libResourceFinder = new ResourceFinder(this.uri + "#", this.classfinder.getLibraryFinder());
         sources.addAll(Collections.list(libResourceFinder.getSources(path)));
      }

      return sources.isEmpty() ? null : (Source[])sources.toArray(new Source[sources.size()]);
   }

   public void addVirtualDirectory(String localPath, String pattern) {
      if (this.virtualDirectories == null) {
         this.virtualDirectories = new HashSet();
      }

      this.virtualDirectories.add(localPath);
      ClassFinder virtualDirFinder = new DescriptorFinder(this.getURI(), new ClasspathClassFinder2(localPath));
      MultiClassFinder finder = new MultiClassFinder(virtualDirFinder);
      finder.addFinder(this.classfinder);
      this.virtualFinders.put(pattern, new ResourceFinder(this.getURI() + "#", finder, this.resourceReloadProber));
   }

   public boolean isVirtualMappingUri(String uri) {
      if (!this.virtualDirectories.isEmpty() && uri != null) {
         uri = FileUtils.normalize(uri);

         while(uri.length() > 0) {
            boolean value = this.virtualDirectories.contains(uri);
            if (value) {
               return true;
            }

            int index = uri.lastIndexOf(File.separatorChar);
            if (index > 0) {
               uri = uri.substring(0, index);
            } else {
               uri = "";
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public ClassFinder getResourceFinder(String path) {
      path = HttpParsing.ensureStartingSlash(path);
      return (ClassFinder)this.virtualFinders.get(path);
   }

   public WarSource getResourceAsSource(String path) {
      return this.getResourceAsSource(path, false);
   }

   WarSource getResourceAsSource(String path, boolean fromDisk) {
      ResourceFinder resourceFinder = (ResourceFinder)this.getResourceFinder(path);
      return fromDisk ? resourceFinder.getSourceFromDisk(path) : (WarSource)resourceFinder.getSource(path);
   }

   void getResourcePaths(String path, Set set) {
      Enumeration e = this.getResourceFinder(path).getSources(path);

      while(true) {
         WarSource[] sources;
         do {
            if (!e.hasMoreElements()) {
               return;
            }

            Source source = (Source)e.nextElement();
            WarSource warSource = new WarSource(source);
            sources = warSource.listSources();
         } while(sources == null);

         for(int i = 0; i < sources.length; ++i) {
            String name = path + sources[i].getName();
            if (sources[i].isDirectory()) {
               name = name + "/";
            }

            set.add(name);
         }
      }
   }

   synchronized void addClassPath(String cp) {
      this.classfinder.addFinder(new ClasspathClassFinder2(cp));
   }

   boolean isArchiveReExtract() {
      return this.isArchiveReExtract;
   }

   public void closeAllFinders() {
      this.findTldsCalled = false;
      Object[] finders = this.virtualFinders.values();

      for(int i = 0; i < finders.length; ++i) {
         ((ClassFinder)finders[i]).close();
      }

      if (this.classfinder != null) {
         this.classfinder.close();
      }

   }

   protected void debug(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(msg);
      }

   }

   public Set getAnnotatedClasses(String... annotations) {
      this.initializeClassInfosIfNecessary();
      Set annotatedClasses = null;
      Set webAnnotatedClasses = this.getClassInfoFinder().getClassNamesWithAnnotations(annotations);
      annotatedClasses = WarUtils.addAllIfNotEmpty((Set)annotatedClasses, webAnnotatedClasses);
      WebAppHelper lib;
      if (this.libraries != null) {
         for(Iterator var4 = this.libraries.iterator(); var4.hasNext(); annotatedClasses = WarUtils.addAllIfNotEmpty((Set)annotatedClasses, lib.getAnnotatedClasses(annotations))) {
            lib = (WebAppHelper)var4.next();
         }
      }

      return annotatedClasses == null ? Collections.emptySet() : annotatedClasses;
   }

   public URL getClassSourceUrl(String className) {
      this.initializeClassInfosIfNecessary();
      URL url = this.classInfos.getClassSourceUrl(className);
      if (url != null) {
         return url;
      } else {
         if (this.libraries != null) {
            Iterator var3 = this.libraries.iterator();

            while(var3.hasNext()) {
               WebAppHelper lib = (WebAppHelper)var3.next();
               url = lib.getClassSourceUrl(className);
               if (url != null) {
                  return url;
               }
            }
         }

         return null;
      }
   }

   public Set getHandlesImpls(ClassLoader cl, String... handlesTypes) {
      if (handlesTypes != null && handlesTypes.length != 0) {
         this.initializeClassInfosIfNecessary();
         Set handlesImpls = null;
         handlesImpls = WarUtils.addAllIfNotEmpty((Set)handlesImpls, this.classInfos.getHandlesImpls(cl, handlesTypes));
         WebAppHelper lib;
         if (this.libraries != null) {
            for(Iterator var4 = this.libraries.iterator(); var4.hasNext(); handlesImpls = WarUtils.addAllIfNotEmpty((Set)handlesImpls, lib.getHandlesImpls(cl, handlesTypes))) {
               lib = (WebAppHelper)var4.next();
            }
         }

         return handlesImpls == null ? Collections.emptySet() : handlesImpls;
      } else {
         return Collections.emptySet();
      }
   }

   public Collection getWebFragments() {
      String uri = this.getURI();
      ClassFinder cf = this.getWebClassFinder();
      ClassFinder rf = this.getWebResourceFinder();
      Set fragments = null;
      fragments = WarUtils.addAllIfNotEmpty((Set)fragments, WarUtils.getWebFragments(uri, cf, rf));
      WebAppHelper lib;
      if (this.libraries != null) {
         for(Iterator var5 = this.libraries.iterator(); var5.hasNext(); fragments = WarUtils.addAllIfNotEmpty(fragments, lib.getWebFragments())) {
            lib = (WebAppHelper)var5.next();
         }
      }

      return fragments == null ? Collections.emptySet() : fragments;
   }

   public ClassInfoFinder getClassInfoFinder() {
      this.initializeClassInfosIfNecessary();
      return (ClassInfoFinder)(this.libraries == null ? this.classInfos : new CompositeClassInfoFinder(this));
   }

   private void initializeClassInfosIfNecessary() {
      if (this.classInfos == null) {
         try {
            this.classInfos = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(this.classfinder.getWebappFinder()).setKeepAnnotatedClassesOnly(false).setModuleType(ModuleType.WAR).enableCaching(Cache.AppMetadataCache, this.warExtractDir, this.cacheDir));
         } catch (AnnotationProcessingException var2) {
            var2.printStackTrace();
         }
      }

   }

   void cleanupClassInfos() {
      this.classInfos = null;
   }

   public Set getTagListeners(boolean isAnnotationOnly) {
      return this.getTagClasses(isAnnotationOnly, "listener-class");
   }

   public Set getTagHandlers(boolean isAnnotationOnly) {
      return this.getTagClasses(isAnnotationOnly, "tag-class");
   }

   public void addExtensionTLD(ExtensionTLD tld) {
      if (this.extensionTLDs == Collections.EMPTY_MAP) {
         this.extensionTLDs = new HashMap();
      }

      this.extensionTLDs.put(tld.getName(), tld);
   }

   public void removeExtensionTLD(String name) {
      this.extensionTLDs.remove(name);
   }

   private ClassFinder getWebClassFinder() {
      ClassFinder cf = this.classfinder.getWebappFinder();
      return cf;
   }

   private Set getTagClasses(boolean isAnnotationOnly, String type) {
      Set tagClasses = null;
      Map webTldInfo = this.getWebTldInfo();
      ClassFinder webappFinder = this.getWebClassFinder();
      Collection webTagClasses = WarUtils.getTagClasses(webappFinder, webTldInfo, isAnnotationOnly, type);
      tagClasses = WarUtils.addAllIfNotEmpty((Set)tagClasses, webTagClasses);
      Iterator var7;
      if (this.libraries != null) {
         var7 = this.libraries.iterator();

         while(var7.hasNext()) {
            WebAppHelper lib = (WebAppHelper)var7.next();
            Set libTagListeners;
            if ("tag-class".equals(type)) {
               libTagListeners = lib.getTagHandlers(isAnnotationOnly);
               tagClasses = WarUtils.addAllIfNotEmpty((Set)tagClasses, libTagListeners);
            } else if ("listener-class".equals(type)) {
               libTagListeners = lib.getTagListeners(isAnnotationOnly);
               tagClasses = WarUtils.addAllIfNotEmpty((Set)tagClasses, libTagListeners);
            }
         }
      }

      ExtensionTLD tld;
      for(var7 = this.extensionTLDs.values().iterator(); var7.hasNext(); tagClasses = WarUtils.addAllIfNotEmpty((Set)tagClasses, tld.getTagClass(type))) {
         tld = (ExtensionTLD)var7.next();
      }

      return tagClasses == null ? Collections.EMPTY_SET : tagClasses;
   }

   private Map getWebTldInfo() {
      Collection tldLocations = this.getWebTLDLocations();
      Map webTldInfo = TldCacheHelper.parseTagLibraries(tldLocations, (File)this.getExtractDir(), this.uri);
      return webTldInfo;
   }

   public Set getManagedBeanClasses(String jsfConfigFiles) {
      Set managedBeans = null;
      Collection configs = this.getWebFacesLocations(jsfConfigFiles);
      Set webManagedBeans = FaceConfigCacheHelper.parseFacesConfigs(configs, (File)this.getExtractDir(), this.uri);
      managedBeans = WarUtils.addAllIfNotEmpty((Set)managedBeans, webManagedBeans);
      Set webAnnotatedManagedBeans = this.getAnnotatedClasses("javax.faces.bean.ManagedBean");
      managedBeans = WarUtils.addAllIfNotEmpty((Set)managedBeans, webAnnotatedManagedBeans);
      Set libManagedBeans;
      if (this.libraries != null) {
         for(Iterator var6 = this.libraries.iterator(); var6.hasNext(); managedBeans = WarUtils.addAllIfNotEmpty((Set)managedBeans, libManagedBeans)) {
            WebAppHelper lib = (WebAppHelper)var6.next();
            libManagedBeans = lib.getManagedBeanClasses(jsfConfigFiles);
         }
      }

      return managedBeans == null ? Collections.EMPTY_SET : managedBeans;
   }

   public void registerExtension(WarExtension extension) throws IOException {
      ClassFinder cf = extension.getClassFinder();
      if (this.extensionsClassfinder == null) {
         this.extensionsClassfinder = new MultiClassFinder();
         this.classfinder.addFinderFirst(this.extensionsClassfinder);
      }

      this.extensionsClassfinder.addFinder(cf);
      if (this.extensions == null) {
         this.extensions = new ArrayList();
      }

      this.extensions.add(extension);
   }

   public List getExtensionRoots() {
      if (this.extensions == null) {
         return null;
      } else {
         List extensionRoots = new ArrayList();
         Iterator var2 = this.extensions.iterator();

         while(var2.hasNext()) {
            WarExtension extension = (WarExtension)var2.next();
            extensionRoots.addAll(Arrays.asList(extension.getRoots()));
         }

         return extensionRoots;
      }
   }

   private ExplodedJar makeExplodedJar(String uri, File extractDir, File[] roots, boolean useOriginalJars) throws IOException {
      long t = 0L;

      try {
         if (roots.length == 1 && !roots[0].isDirectory()) {
            ArchivedWar aw = new ArchivedWar(uri, extractDir, roots[0], WAR_CLASSPATH_INFO);
            this.isArchiveReExtract = aw.isReExtract();
            return aw;
         } else {
            CaseAwareExplodedJar var7 = new CaseAwareExplodedJar(uri, extractDir, roots, WAR_CLASSPATH_INFO, useOriginalJars ? JarCopyFilter.NOCOPY_FILTER : JarCopyFilter.DEFAULT_FILTER);
            return var7;
         }
      } finally {
         ;
      }
   }

   private static ServletMapping newServletMapping() {
      return !KernelStatus.isServer() ? new ServletMapping() : new ServletMapping(WebAppConfigManager.isCaseInsensitive(), WebServerRegistry.getInstance().getSecurityProvider().getEnforceStrictURLPattern());
   }

   void setSplitDirectoryClasspath(String splitDirectoryClassPath) {
      this.splitDirectoryClassPath = splitDirectoryClassPath;
   }

   public String[] getSplitDirectoryJars() {
      if (this.splitDirectoryClassPath == null) {
         return null;
      } else {
         String[] paths = StringUtils.splitCompletely(this.splitDirectoryClassPath, File.pathSeparator);
         List jars = new ArrayList();
         String[] var3 = paths;
         int var4 = paths.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String path = var3[var5];
            if (path.toLowerCase().endsWith("jar") && (new File(path)).exists()) {
               jars.add(path);
            }
         }

         return (String[])jars.toArray(new String[jars.size()]);
      }
   }

   public void startAnnotationProcess() {
      if (this.libraries != null) {
         Iterator var1 = this.libraries.iterator();

         while(var1.hasNext()) {
            WebAppHelper lib = (WebAppHelper)var1.next();
            lib.startAnnotationProcess();
         }
      }

   }

   public void completeAnnotationProcess() {
      if (this.libraries != null) {
         Iterator var1 = this.libraries.iterator();

         while(var1.hasNext()) {
            WebAppHelper lib = (WebAppHelper)var1.next();
            lib.completeAnnotationProcess();
         }
      }

   }

   private static class CompositeClassInfoFinder implements ClassInfoFinder {
      private War war;

      private CompositeClassInfoFinder(War war) {
         this.war = war;
      }

      public Map getAnnotatedClassesByTargetsAndSources(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
         Map classes = new HashMap();
         classes.putAll(this.war.classInfos.getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader));
         Iterator var6 = this.war.libraries.iterator();

         label66:
         while(true) {
            Map libClasses;
            do {
               do {
                  if (!var6.hasNext()) {
                     return (Map)(classes.isEmpty() ? Collections.emptyMap() : classes);
                  }

                  WebAppHelper library = (WebAppHelper)var6.next();
                  libClasses = library.getClassInfoFinder().getAnnotatedClassesByTargetsAndSources(annotations, filter, includeExtendedAnnotations, classLoader);
               } while(libClasses == null);
            } while(libClasses.isEmpty());

            Iterator var9 = libClasses.keySet().iterator();

            while(true) {
               Map urlMap;
               Map libUrlMap;
               do {
                  do {
                     while(true) {
                        if (!var9.hasNext()) {
                           continue label66;
                        }

                        ClassInfoFinder.Target target = (ClassInfoFinder.Target)var9.next();
                        urlMap = (Map)classes.get(target);
                        if (urlMap != null && !urlMap.isEmpty()) {
                           libUrlMap = (Map)libClasses.get(target);
                           break;
                        }

                        classes.put(target, libClasses.get(target));
                     }
                  } while(libUrlMap == null);
               } while(libUrlMap.isEmpty());

               Iterator var13 = libUrlMap.keySet().iterator();

               while(var13.hasNext()) {
                  URI uri = (URI)var13.next();
                  if (urlMap.containsKey(uri)) {
                     ((Set)urlMap.get(uri)).addAll((Collection)libUrlMap.get(uri));
                  } else {
                     urlMap.put(uri, libUrlMap.get(uri));
                  }
               }
            }
         }
      }

      public boolean hasAnnotatedClasses(String[] annotations, ClassInfoFinder.Filter filter, boolean includeExtendedAnnotations, ClassLoader classLoader) {
         if (this.war.classInfos.hasAnnotatedClasses(annotations, filter, includeExtendedAnnotations, classLoader)) {
            return true;
         } else {
            Iterator var5 = this.war.libraries.iterator();

            WebAppHelper library;
            do {
               if (!var5.hasNext()) {
                  return false;
               }

               library = (WebAppHelper)var5.next();
            } while(!library.getClassInfoFinder().hasAnnotatedClasses(annotations, filter, includeExtendedAnnotations, classLoader));

            return true;
         }
      }

      public Set getClassNamesWithAnnotations(String... annotations) {
         Set classNames = null;
         classNames = WarUtils.addAllIfNotEmpty((Set)classNames, this.war.classInfos.getClassNamesWithAnnotations(annotations));

         WebAppHelper lib;
         for(Iterator var3 = this.war.libraries.iterator(); var3.hasNext(); classNames = WarUtils.addAllIfNotEmpty((Set)classNames, lib.getClassInfoFinder().getClassNamesWithAnnotations(annotations))) {
            lib = (WebAppHelper)var3.next();
         }

         return classNames;
      }

      public Map getAnnotatedClasses(String... annotations) {
         Map map = this.war.classInfos.getAnnotatedClasses(annotations);
         Iterator var3 = this.war.libraries.iterator();

         while(var3.hasNext()) {
            WebAppHelper lib = (WebAppHelper)var3.next();
            Map libMap = lib.getClassInfoFinder().getAnnotatedClasses(annotations);
            Iterator var6 = libMap.keySet().iterator();

            while(var6.hasNext()) {
               String className = (String)var6.next();
               if (map.containsKey(className)) {
                  ((Set)map.get(className)).addAll((Collection)libMap.get(className));
               } else {
                  map.put(className, libMap.get(className));
               }
            }
         }

         return map;
      }

      public URL getClassSourceUrl(String className) {
         URL url = this.war.classInfos.getClassSourceUrl(className);
         if (url != null) {
            return url;
         } else {
            Iterator var3 = this.war.libraries.iterator();

            do {
               if (!var3.hasNext()) {
                  return null;
               }

               WebAppHelper library = (WebAppHelper)var3.next();
               url = library.getClassInfoFinder().getClassSourceUrl(className);
            } while(url == null);

            return url;
         }
      }

      public Set getHandlesImpls(ClassLoader cl, String... handlesTypes) {
         Set handlesImpls = null;
         handlesImpls = WarUtils.addAllIfNotEmpty((Set)handlesImpls, this.war.classInfos.getHandlesImpls(cl, handlesTypes));

         WebAppHelper lib;
         for(Iterator var4 = this.war.libraries.iterator(); var4.hasNext(); handlesImpls = WarUtils.addAllIfNotEmpty((Set)handlesImpls, lib.getClassInfoFinder().getHandlesImpls(cl, handlesTypes))) {
            lib = (WebAppHelper)var4.next();
         }

         return handlesImpls;
      }

      public Set getAllSubClassNames(String className) {
         Set allImplementations = null;
         allImplementations = WarUtils.addAllIfNotEmpty((Set)allImplementations, this.war.classInfos.getAllSubClassNames(className));

         WebAppHelper lib;
         for(Iterator var3 = this.war.libraries.iterator(); var3.hasNext(); allImplementations = WarUtils.addAllIfNotEmpty((Set)allImplementations, lib.getClassInfoFinder().getAllSubClassNames(className))) {
            lib = (WebAppHelper)var3.next();
         }

         return allImplementations;
      }

      // $FF: synthetic method
      CompositeClassInfoFinder(War x0, Object x1) {
         this(x0);
      }
   }

   public static class LibrarySourceMetadata {
      private final boolean fromArchive;

      public LibrarySourceMetadata(boolean fromArchive) {
         this.fromArchive = fromArchive;
      }

      public boolean isFromArchive() {
         return this.fromArchive;
      }
   }

   public static final class ResourceFinder extends AbstractClassFinder {
      private static final NullSource NULL = new NullSource();
      private final SecondChanceCacheMap cache;
      private final ClassFinder delegate;
      private ResourceFinder webappResourceFinder;
      private final String prefix;
      private String classpath;
      private final StaleProber reloadProber;

      public ResourceFinder(String prefix, ClassFinder delegate) {
         this(prefix, delegate, (StaleProber)null);
      }

      ResourceFinder(String prefix, ClassFinder delegate, StaleProber reloadProber) {
         this.cache = new SecondChanceCacheMap(317);
         this.prefix = prefix;
         this.delegate = delegate;
         this.reloadProber = reloadProber;
      }

      public ResourceFinder getWebResourceFinder() {
         if (this.delegate instanceof CompositeWebAppFinder) {
            synchronized(this) {
               if (this.webappResourceFinder == null) {
                  this.webappResourceFinder = new ResourceFinder(this.prefix, ((CompositeWebAppFinder)this.delegate).getWebappFinder(), this.reloadProber);
               }

               return this.webappResourceFinder;
            }
         } else {
            return this;
         }
      }

      public Source getSource(String name) {
         WarSource w = (WarSource)this.cache.get(this.prefix + name);
         if (w == null || this.reloadProber != null && this.reloadProber.shouldReloadResource(w.getLastChecked(), name)) {
            w = this.getSourceFromDisk(name);
         }

         return w.delegate == NULL ? null : w;
      }

      public Enumeration getSources(String s) {
         Enumeration e = this.delegate.getSources(this.prefix + s);
         List l = null;
         if (e != null) {
            l = new ArrayList();

            while(e.hasMoreElements()) {
               l.add(new WarSource((Source)e.nextElement()));
            }
         }

         return (Enumeration)(l == null ? new EmptyEnumerator() : new IteratorEnumerator(l.iterator()));
      }

      public Source getClassSource(String c) {
         return this.delegate.getClassSource(c);
      }

      public String getClassPath() {
         synchronized(this) {
            if (this.classpath != null) {
               return this.classpath;
            } else {
               String cp = null;
               Enumeration e = this.delegate.getSources(this.prefix + "/");

               while(e.hasMoreElements()) {
                  Source s = (Source)e.nextElement();
                  URL u = s.getURL();
                  if (u != null) {
                     if (cp == null) {
                        cp = u.getPath() + File.pathSeparator;
                     } else {
                        cp = cp + u.getPath() + File.pathSeparator;
                     }
                  }
               }

               this.classpath = cp;
               return this.classpath;
            }
         }
      }

      public ClassFinder getManifestFinder() {
         return this.delegate.getManifestFinder();
      }

      public Enumeration entries() {
         return this.delegate.entries();
      }

      public void clearCache(String entry) {
         if (!entry.startsWith("/")) {
            entry = "/" + entry;
         }

         this.cache.remove(this.prefix + entry);
      }

      public void close() {
         synchronized(this) {
            this.classpath = null;
         }

         this.delegate.close();
      }

      private WarSource getSourceFromDisk(String name) {
         Source s = this.delegate.getSource(this.prefix + name);
         if (s == null) {
            s = NULL;
         }

         WarSource w = new WarSource((Source)s);
         this.cache.put(this.prefix + name, w);
         return w;
      }
   }

   private static class NoOpArchive extends Archive {
      private NoOpArchive() {
      }

      public ClassFinder getClassFinder() throws IOException {
         return NullClassFinder.NULL_FINDER;
      }

      public void remove() {
      }

      // $FF: synthetic method
      NoOpArchive(Object x0) {
         this();
      }
   }
}
