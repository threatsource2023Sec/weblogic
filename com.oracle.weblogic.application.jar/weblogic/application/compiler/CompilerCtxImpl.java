package weblogic.application.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.ApplicationFileManager;
import weblogic.application.Registry;
import weblogic.application.RegistryImpl;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.internal.library.LibraryManagerAggregate;
import weblogic.application.io.Ear;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProvider;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ModuleRegistryImpl;
import weblogic.application.utils.AppSupportDeclarations;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.annotation.AnnotationMappings;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.utils.Getopt2;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public final class CompilerCtxImpl implements CompilerCtx {
   private static final String DEFAULT_VERSION_ALGORITHM = "SHA-256";
   private Getopt2 opts;
   private VirtualJarFile vSource;
   private File sourceFile;
   private File outputDir;
   private String classpathArg;
   private String targetArchive;
   private boolean isSplitDir = false;
   private boolean verbose;
   private ToolsModule[] modules = null;
   private ToolsModule[] customModules = null;
   private final Map stateMap = new HashMap();
   private ApplicationDescriptor appDesc;
   private ApplicationBean appDD;
   private WeblogicApplicationBean wlappDD;
   private WeblogicExtensionBean wlExtDD;
   private Ear ear = null;
   private String planName;
   private File planFile;
   private DeploymentPlanBean plan;
   private File configDir;
   private Map factoryMap;
   private boolean readOnlyInvocation;
   private boolean verifyLibraryReferences;
   private boolean mergedDisabled;
   private File tempDir;
   private boolean unregisterLibrariesOnExit;
   private boolean basicView;
   private String partialOutputTarget;
   private Map registeries;
   private final Registry applicationRegistry;
   private boolean beanScaffoldingEnabled;
   private File generatedOutputDir;
   private File cacheDir;
   private boolean wlDirectoriesSet;
   private EditableDeployableObjectFactory objectFactory;
   private EditableDeployableObject deployableApplication;
   private boolean writeInferredDescriptors;
   private File manifestFile;
   private String lightWeightAppName;
   private boolean initialized;
   private String appId;
   private GenericClassLoader appClassLoader;
   private ApplicationFileManager afm;
   private final LibraryManagerAggregate libAggr;
   private final Map uriLinks;
   private Map contextRootOverrideMap;
   private SplitDirectoryInfo splitInfo;
   private ToolsExtension[] toolsExtensions;
   private ApplicationArchive archive;
   private AnnotationMappings annotationMappings;
   private boolean annotationProcessingComplete;
   private Object annotationProcessingLock;
   private List libraryClassInfoFinders;
   private String classpathForAppAnnotationScanning;
   private boolean generateVersion;
   private String versionAlgorithm;
   private String hashVersion;

   public CompilerCtxImpl() {
      this.factoryMap = Collections.EMPTY_MAP;
      this.readOnlyInvocation = false;
      this.verifyLibraryReferences = true;
      this.mergedDisabled = false;
      this.tempDir = null;
      this.unregisterLibrariesOnExit = true;
      this.basicView = false;
      this.partialOutputTarget = null;
      this.registeries = new HashMap();
      this.applicationRegistry = new RegistryImpl();
      this.beanScaffoldingEnabled = false;
      this.generatedOutputDir = null;
      this.cacheDir = null;
      this.wlDirectoriesSet = false;
      this.objectFactory = null;
      this.deployableApplication = null;
      this.writeInferredDescriptors = false;
      this.manifestFile = null;
      this.lightWeightAppName = null;
      this.initialized = false;
      this.libAggr = new LibraryManagerAggregate();
      this.uriLinks = new HashMap();
      this.annotationProcessingComplete = false;
      this.annotationProcessingLock = new Object();
      this.libraryClassInfoFinders = null;
      this.classpathForAppAnnotationScanning = null;
      this.generateVersion = false;
      this.versionAlgorithm = "SHA-256";
      this.libAggr.setPartitionName("DOMAIN");
   }

   public Getopt2 getOpts() {
      return this.opts;
   }

   public void setOpts(Getopt2 opts) {
      this.opts = opts;
   }

   public VirtualJarFile getVSource() {
      return this.vSource;
   }

   public void setVSource(VirtualJarFile vSource) {
      this.vSource = vSource;
   }

   public File getSourceFile() {
      return this.sourceFile;
   }

   public void setSourceFile(File sourceFile) {
      this.sourceFile = sourceFile;
   }

   public String getSourceName() {
      return this.sourceFile.getName();
   }

   public File getOutputDir() {
      return this.outputDir;
   }

   public void setOutputDir(File outputDir) {
      this.outputDir = outputDir;
   }

   public String getClasspathArg() {
      return this.classpathArg;
   }

   public void setClasspathArg(String classpathArg) {
      this.classpathArg = classpathArg;
   }

   public String getTargetArchive() {
      return this.targetArchive;
   }

   public void setTargetArchive(String targetArchive) {
      this.targetArchive = targetArchive;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public ApplicationBean getApplicationDD() {
      return this.appDD;
   }

   public WeblogicApplicationBean getWLApplicationDD() {
      return this.wlappDD;
   }

   public ApplicationDescriptor getApplicationDescriptor() {
      return this.appDesc;
   }

   public void setApplicationDescriptor(ApplicationDescriptor appDesc) throws IOException, XMLStreamException {
      this.appDesc = appDesc;
      this.appDD = appDesc.getApplicationDescriptor();
      this.wlappDD = appDesc.getWeblogicApplicationDescriptor();
      this.wlExtDD = appDesc.getWeblogicExtensionDescriptor();
   }

   public WeblogicExtensionBean getWLExtensionDD() {
      return this.wlExtDD;
   }

   public void init() {
      if (!this.initialized) {
         this.initialized = true;
         this.appId = this.getSourceName();
         this.appClassLoader = new GenericClassLoader(new MultiClassFinder(), Thread.currentThread().getContextClassLoader());
         this.appClassLoader.setAnnotation(new Annotation(this.appId));
      }

   }

   public void setModules(ToolsModule[] modules) {
      this.modules = modules;
      this.registeries.clear();
      ToolsModule[] var2 = this.modules;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ToolsModule m = var2[var4];
         this.registeries.put(m.getURI(), new ModuleRegistryImpl());
      }

   }

   public ToolsModule[] getModules() {
      return this.modules;
   }

   public boolean isSplitDir() {
      return this.isSplitDir;
   }

   public void setSplitDir() {
      this.isSplitDir = true;
   }

   public void setEar(Ear ear) {
      if (this.ear != null) {
         throw new AssertionError("An EAR can't be set twice on this Context");
      } else {
         this.ear = ear;
         this.addClassFinder(ear.getClassFinder());
      }
   }

   public Ear getEar() {
      return this.ear;
   }

   public void setPlanFile(File arg) {
      this.planFile = arg;
   }

   public File getPlanFile() {
      return this.planFile;
   }

   public void setPlanName(String arg) {
      this.planName = arg;
   }

   public String getPlanName() {
      return this.planName;
   }

   public void setPlanBean(DeploymentPlanBean bean) {
      this.plan = bean;
   }

   public DeploymentPlanBean getPlanBean() {
      return this.plan;
   }

   public void setConfigDir(File file) {
      this.configDir = file;
   }

   public File getConfigDir() {
      return this.configDir;
   }

   public Map getCustomModuleFactories() {
      return this.factoryMap;
   }

   public void setCustomModuleFactories(Map factoryMap) {
      this.factoryMap = factoryMap;
   }

   public ToolsModule[] getCustomModules() {
      return this.customModules;
   }

   public void setCustomModules(ToolsModule[] modules) {
      this.customModules = modules;
   }

   public EditableDeployableObjectFactory getObjectFactory() {
      return this.objectFactory;
   }

   public void setObjectFactory(EditableDeployableObjectFactory objectFactory) {
      this.objectFactory = objectFactory;
   }

   public boolean isReadOnlyInvocation() {
      return this.readOnlyInvocation;
   }

   public void setReadOnlyInvocation() {
      this.readOnlyInvocation = true;
   }

   public EditableDeployableObject getDeployableApplication() {
      return this.deployableApplication;
   }

   public void setDeployableApplication(EditableDeployableObject deployableApplication) {
      this.deployableApplication = deployableApplication;
   }

   public boolean verifyLibraryReferences() {
      return this.verifyLibraryReferences;
   }

   public void setVerifyLibraryReferences(boolean flag) {
      this.verifyLibraryReferences = flag;
   }

   public void disableLibraryMerge() {
      this.mergedDisabled = true;
   }

   public boolean isMergeDisabled() {
      return this.mergedDisabled;
   }

   public File getTempDir() {
      return this.tempDir;
   }

   public void setTempDir(File tempDir) {
      this.tempDir = tempDir;
   }

   public boolean isWriteInferredDescriptors() {
      return this.writeInferredDescriptors;
   }

   public void setWriteInferredDescriptors() {
      this.writeInferredDescriptors = true;
   }

   public void setManifestFile(File manifestFile) {
      this.manifestFile = manifestFile;
   }

   public File getManifestFile() {
      return this.manifestFile;
   }

   public void keepLibraryRegistrationOnExit() {
      this.unregisterLibrariesOnExit = false;
   }

   public boolean unregisterLibrariesOnExit() {
      return this.unregisterLibrariesOnExit;
   }

   public void setBasicView() {
      this.basicView = true;
   }

   public boolean isBasicView() {
      return this.basicView;
   }

   public void setLightWeightAppName(String app) {
      this.lightWeightAppName = app;
   }

   public String getLightWeightAppName() {
      return this.lightWeightAppName;
   }

   public String getPartialOutputTarget() {
      return this.partialOutputTarget;
   }

   public void setPartialOutputTarget(String target) {
      this.partialOutputTarget = target;
   }

   public void registerLink(File f) throws IOException {
      this.registerLink(f.getName(), f);
   }

   public void registerLink(String uri, File f) throws IOException {
      EarUtils.linkURI(this.getEar(), this.getApplicationFileManager(), uri, f);
      this.uriLinks.put(uri, f);
   }

   public File getURILink(String uri) {
      return (File)this.uriLinks.get(uri);
   }

   public boolean isLibraryURI(String uri) {
      return this.uriLinks.containsKey(uri);
   }

   public ClassFinder getClassFinder() {
      return this.getAppClassLoader().getClassFinder();
   }

   public void notifyDescriptorUpdate() throws LoggableLibraryProcessingException {
      LibraryUtils.resetAppDDs(this.getApplicationDescriptor(), this);
   }

   public GenericClassLoader getAppClassLoader() {
      return this.appClassLoader;
   }

   public void addClassFinder(ClassFinder in) {
      this.getAppClassLoader().addClassFinder(in);
   }

   public void addInstanceAppLibClassFinder(ClassFinder finder) {
   }

   public void addSharedAppLibClassFinder(ClassFinder finder) {
   }

   public ApplicationFileManager getApplicationFileManager() {
      return this.afm;
   }

   public void setApplicationFileManager(ApplicationFileManager afm) {
      this.afm = afm;
   }

   public void addLibraryManager(String moduleId, LibraryManager mgr) {
      this.libAggr.addLibraryManager(moduleId, mgr);
   }

   public LibraryManagerAggregate getLibraryManagerAggregate() {
      return this.libAggr;
   }

   public LibraryProvider getLibraryProvider(String moduleId) {
      return this.libAggr.getLibraryProvider(moduleId);
   }

   public Map getContextRootOverrideMap() {
      return this.contextRootOverrideMap;
   }

   public void setContextRootOverrideMap(Map map) {
      this.contextRootOverrideMap = map;
   }

   public void addLibraryClassInfoFinderFirst(ClassInfoFinder classInfoFinder) {
      if (this.libraryClassInfoFinders == null) {
         this.libraryClassInfoFinders = new ArrayList(1);
      }

      this.libraryClassInfoFinders.add(0, classInfoFinder);
   }

   public String getRefappName() {
      return ApplicationVersionUtils.getApplicationName(this.appId);
   }

   public String getRefappUri() {
      return this.getEar().getURI();
   }

   public String getApplicationId() {
      return this.appId;
   }

   public SplitDirectoryInfo getSplitDirectoryInfo() {
      return this.splitInfo;
   }

   public void setSplitDirectoryInfo(SplitDirectoryInfo splitInfo) {
      this.splitInfo = splitInfo;
   }

   public ModuleState createState(ToolsModule module) {
      return new ModuleState(module, this);
   }

   public void saveState(ToolsModule module, ModuleState state) {
      this.stateMap.put(module.getURI(), state);
   }

   public ModuleState getModuleState(ToolsModule module) {
      ToolsModule lookupModule = module;
      if (module instanceof ToolsModuleWrapper) {
         lookupModule = ((ToolsModuleWrapper)module).unwrap();
      }

      return lookupModule instanceof LibraryModule ? this.getModuleContext(((LibraryModule)lookupModule).getDelegate().getURI()) : this.getModuleContext(lookupModule.getURI());
   }

   public ModuleRegistry getModuleRegistry(String moduleUri) {
      return (ModuleRegistry)this.registeries.get(moduleUri);
   }

   public Registry getApplicationRegistry() {
      return this.applicationRegistry;
   }

   public void setToolsExtensions(ToolsExtension[] extensions) {
      this.toolsExtensions = extensions;
   }

   public ToolsExtension[] getToolsExtensions() {
      return this.toolsExtensions;
   }

   public ModuleState getModuleContext(String moduleUri) {
      return (ModuleState)this.stateMap.get(moduleUri);
   }

   private void processAnnotationMappings() throws AnnotationProcessingException {
      if (this.annotationMappings != null) {
         this.annotationMappings.loadAnnotatedClasses(this.findAnnotationsOfInterest());
      }
   }

   private Class[] findAnnotationsOfInterest() {
      if (this.modules != null && this.modules.length != 0) {
         Set allAnnotations = new HashSet();
         Set processedModuleTypes = new HashSet();
         ToolsModule[] var3 = this.modules;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ToolsModule module = var3[var5];
            ModuleType moduleType = module.getModuleType();
            if (!processedModuleTypes.contains(moduleType)) {
               processedModuleTypes.add(moduleType);
               Class[] annotations = AppSupportDeclarations.instance.getAnnotations(moduleType);
               if (annotations != null && annotations.length > 0) {
                  allAnnotations.addAll(Arrays.asList(annotations));
               }
            }
         }

         return allAnnotations.isEmpty() ? null : (Class[])allAnnotations.toArray(new Class[0]);
      } else {
         return null;
      }
   }

   public Set getAnnotatedClasses(Class... annos) throws AnnotationProcessingException {
      if (this.ear != null) {
         if (!this.annotationProcessingComplete) {
            synchronized(this.annotationProcessingLock) {
               if (!this.annotationProcessingComplete) {
                  this.processAnnotationMappings();
                  this.annotationProcessingComplete = true;
               }
            }
         }

         return this.annotationMappings == null ? Collections.emptySet() : this.annotationMappings.getAnnotatedClasses(annos);
      } else {
         return Collections.emptySet();
      }
   }

   public ApplicationArchive getApplicationArchive() {
      return this.archive;
   }

   public void setApplicationArchive(ApplicationArchive archive) {
      this.archive = archive;
   }

   public void setupApplicationFileManager(File outputDir) throws IOException {
      ApplicationArchive aa = this.getApplicationArchive();
      ApplicationFileManager afm;
      if (aa != null) {
         afm = ApplicationFileManager.newInstance(aa);
      } else {
         afm = ApplicationFileManager.newInstance(outputDir);
      }

      this.setApplicationFileManager(afm);
   }

   public void setAnnotationMappings(AnnotationMappings annotationMappings) {
      this.annotationMappings = annotationMappings;
   }

   public void enableBeanScaffolding() {
      this.beanScaffoldingEnabled = true;
   }

   public boolean isBeanScaffoldingEnabled() {
      return this.beanScaffoldingEnabled;
   }

   private void setupWLDirectories() {
      if (!this.wlDirectoriesSet) {
         File outputDir = this.getOutputDir();
         if (outputDir == null) {
            throw new IllegalStateException("Output directory not setup yet, cannot setup WL Internal directories");
         }

         this.generatedOutputDir = new File(outputDir, "META-INF/.WL_internal/generated/ear/");
         this.cacheDir = new File(outputDir, "META-INF/.WL_internal/cache/ear/");
         this.wlDirectoriesSet = true;
      }

   }

   public File getGeneratedOutputDir() {
      this.setupWLDirectories();
      return this.generatedOutputDir;
   }

   public File getCacheDir() {
      this.setupWLDirectories();
      return this.cacheDir;
   }

   public List getLibraryClassInfoFinders() {
      return this.libraryClassInfoFinders;
   }

   public void addAppAnnotationScanningClassPathFirst(String classpath) {
      if (classpath != null && classpath.length() > 0) {
         if (this.classpathForAppAnnotationScanning == null) {
            this.classpathForAppAnnotationScanning = classpath;
         } else {
            this.classpathForAppAnnotationScanning = classpath + PlatformConstants.PATH_SEP + this.classpathForAppAnnotationScanning;
         }
      }

   }

   public String getAppAnnotationScanningClassPath() {
      return this.classpathForAppAnnotationScanning;
   }

   public boolean isGenerateVersion() {
      return this.generateVersion;
   }

   public void setGenerateVersion(boolean generate) {
      this.generateVersion = generate;
   }

   public String getVersionGeneratorAlgorithm() {
      return this.versionAlgorithm;
   }

   public void setVersionGeneratorAlgorithm(String algorithm) {
      if (algorithm == null) {
         algorithm = "SHA-256";
      }

      this.versionAlgorithm = algorithm;
   }

   public String getApplicationVersion() {
      return this.hashVersion;
   }

   public void setApplicationVersion(String version) {
      this.hashVersion = version;
   }

   public String toString() {
      return "CompilerCtxImpl(" + System.identityHashCode(this) + ")";
   }
}
