package weblogic.servlet.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ParentModule;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.application.io.Ear;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.PathUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorHelper;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.JspDescriptorBean;
import weblogic.j2ee.descriptor.wl.SessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.persistence.PersistenceUnitViewer;
import weblogic.servlet.internal.AnnotationProcessingManager;
import weblogic.servlet.internal.StaleProber;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WarDefinition;
import weblogic.servlet.internal.WebAnnotationProcessor;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.servlet.internal.fragment.MergeException;
import weblogic.servlet.internal.fragment.WebFragmentManager;
import weblogic.servlet.jsp.JspcInvoker;
import weblogic.servlet.utils.WarUtils;
import weblogic.servlet.utils.WebAppLibraryUtils;
import weblogic.utils.BadOptionException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public class WARModule implements ToolsModule, ParentModule, Extensible {
   private static final String WL_EXT_URI = "WEB-INF/weblogic-extension.xml";
   protected static final boolean debug = false;
   private GenericClassLoader wcl;
   private GenericClassLoader temporaryClassLoader;
   private WebAppBean webBean;
   private WeblogicWebAppBean wlBean;
   private File moduleExtractDir = null;
   private String contextRoot;
   private PersistenceUnitViewer perViewer;
   private ModuleContext state;
   private final String altDD;
   private final String moduleUri;
   private ToolsContext ctx;
   private VirtualJarFile moduleVjf;
   private War war;
   private WebFragmentManager webFragmentManager;
   private boolean referenceLibraries;
   private ModuleExtensionContext extContext;
   private Map descriptors;

   public WARModule(String uri, String altDD, String contextRoot) {
      this.moduleUri = uri;
      this.altDD = altDD;
      this.contextRoot = contextRoot;
   }

   protected ModuleContext getState() {
      return this.state;
   }

   protected ToolsContext getCtx() {
      return this.ctx;
   }

   protected VirtualJarFile getModuleVjf() {
      return this.moduleVjf;
   }

   public String getAltDD() {
      return this.altDD;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public String getWLExtensionDirectory() {
      return "WEB-INF";
   }

   public String[] getApplicationNameXPath() {
      return new String[]{"web-app", "module-name"};
   }

   public ClassFinder getResourceFinder() {
      return this.war.getResourceFinder("/");
   }

   private File getExtractDir(ToolsContext ctx) throws ToolFailureException {
      Ear ear = ctx.getEar();
      String appName;
      if (ear == null) {
         appName = this.getURI();
      } else {
         appName = ear.getURI();
      }

      String moduleName = this.getURI();
      File baseDir = ctx.getTempDir();
      File appArchive = new File(baseDir, appName);
      if (appArchive.exists() && !appArchive.isDirectory()) {
         appArchive.delete();
      }

      File extDir = new File(baseDir, PathUtils.generateTempPath((String)null, appName, moduleName));
      boolean extDirWritable = false;
      if (extDir.exists()) {
         extDirWritable = extDir.canWrite();
      } else {
         extDirWritable = extDir.mkdirs();
      }

      if (!extDirWritable) {
         throw new ToolFailureException("Unable to generate temporary directory for module: " + extDir.getAbsolutePath());
      } else {
         return extDir;
      }
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_WAR;
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      if (this.extContext == null) {
         this.extContext = WARModuleExtensionContext.getInstance(this, this.war);
      }

      return this.extContext;
   }

   public Descriptor getStandardDescriptor() {
      return this.webBean != null ? ((DescriptorBean)this.webBean).getDescriptor() : null;
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      this.ctx = ctx;

      try {
         SplitDirectoryInfo info = ctx.getSplitDirectoryInfo();
         String[] splitClasses = info != null ? info.getWebAppClasses(this.getURI()) : null;
         this.moduleVjf = this.getModuleVirtualJarFile(state);
         WarDefinition definition = new WarDefinition(this.getURI(), this.moduleVjf, false, splitClasses, state.getCacheDir());
         this.war = definition.extract(this.moduleExtractDir, (StaleProber)null);
         return this.war.getClassFinder();
      } catch (IOException var7) {
         throw new ToolFailureException("Unable to load web module at uri " + this.getURI(), var7);
      }
   }

   private VirtualJarFile getModuleVirtualJarFile(ModuleContext state) throws ToolFailureException, IOException {
      this.moduleExtractDir = this.getExtractDir(this.ctx);
      VirtualJarFile moduleVjf = state.getVirtualJarFile();
      if (state.isArchive()) {
         if (state.getOutputDir().isFile()) {
            AppcUtils.expandJarFileIntoDirectory(moduleVjf, this.moduleExtractDir);
            moduleVjf = AppcUtils.getVirtualJarFile(this.moduleExtractDir);
         } else {
            moduleVjf = AppcUtils.getVirtualJarFile(state.getOutputDir());
         }
      }

      return moduleVjf;
   }

   public boolean needsClassLoader() {
      return true;
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      JspcInvoker jspc = new JspcInvoker(this.ctx.getOpts());

      try {
         jspc.checkCompliance(cl, this.state.getVirtualJarFile(), this.state.getAltDDFile(), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.state.getValidationInfo(), this.ctx.isVerbose());
      } catch (ErrorCollectionException var4) {
         throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.state.getVirtualJarFile().getName(), var4.toString()).getMessage(), var4);
      }
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      this.wcl = cl;
      this.wcl.addClassFinder(this.war.getClassFinder());
      WebAppDescriptor webDescriptor = this.getWebAppParser();
      this.loadDescriptors(webDescriptor);
      this.processLibraries(this.ctx);
      this.mergeDescriptors(webDescriptor);
      this.configureFCL(this.ctx.getAppClassLoader());
      this.configureFCL(this.wcl);
      File outputDir = this.state.getOutputDir();
      ModuleValidationInfo mvi = this.state.getValidationInfo();
      this.compileWAR(this.ctx, this.wcl, this.moduleVjf, outputDir, mvi);
      if (this.shouldBackUp()) {
         this.backupDescriptors(this.moduleVjf);
         this.initWebFragmentManager();
         this.processAnnotations(this.ctx);
         this.writeDescriptors(this.ctx);
      }

      return Collections.emptyMap();
   }

   protected GenericClassLoader getClassLoader() {
      return this.wcl;
   }

   protected GenericClassLoader getTemporaryClassLoader() {
      if (this.temporaryClassLoader != null) {
         return this.temporaryClassLoader;
      } else {
         this.temporaryClassLoader = new GenericClassLoader(this.wcl.getClassFinder(), this.wcl.getParent());
         return this.temporaryClassLoader;
      }
   }

   private void configureFCL(GenericClassLoader classloader) throws ToolFailureException {
      if (WarUtils.configureFCL(this.wlBean, classloader, this.ctx.getEar() == null)) {
         J2EELogger.logFilteringConfigurationIgnored(this.ctx.getApplicationId(), this.moduleUri);
      }

   }

   protected boolean shouldBackUp() {
      return this.ctx.isWriteInferredDescriptors() && (this.webBean == null || !this.webBean.isMetadataComplete());
   }

   public void cleanup() {
      this.temporaryClassLoader = null;
      if (this.wcl != null) {
         this.wcl.close();
      }

      this.war.closeAllFinders();
      this.war.remove();
      IOUtils.forceClose(this.moduleVjf);
   }

   protected void writeDescriptors(ToolsContext ctx) throws ToolFailureException {
      SettableBean bean = (SettableBean)this.webBean;
      boolean isMetadataCompleteSet = bean.isSet("MetadataComplete");
      boolean metadataComplete = this.webBean.isMetadataComplete();
      if (ctx.isWriteInferredDescriptors()) {
         this.webBean.setMetadataComplete(true);
      }

      this.writeDescriptor("WEB-INF/web.xml", (DescriptorBean)this.webBean);
      this.writeDescriptor("WEB-INF/weblogic.xml", (DescriptorBean)this.wlBean);
      if (this.descriptors != null) {
         Iterator var5 = this.descriptors.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            if ((DescriptorBean)entry.getValue() != null) {
               AppcUtils.writeDescriptor(this.state.getOutputDir(), (String)entry.getKey(), (DescriptorBean)entry.getValue());
            }
         }
      }

      if (!isMetadataCompleteSet) {
         bean.unSet("MetadataComplete");
      } else {
         this.webBean.setMetadataComplete(metadataComplete);
      }

   }

   protected void writeDescriptor(String uri, DescriptorBean bean) throws ToolFailureException {
      AppcUtils.writeDescriptor(this.state.getOutputDir(), uri, bean);
   }

   protected Map processDDs(ToolsContext ctx) {
      Map descriptors = new HashMap();
      if (this.webBean != null) {
         descriptors.put("WEB-INF/web.xml", (DescriptorBean)this.webBean);
      }

      if (this.wlBean != null) {
         descriptors.put("WEB-INF/weblogic.xml", (DescriptorBean)this.wlBean);
      }

      DescriptorBean diagDD = WLDFDescriptorHelper.getDiagnosticDescriptor(this.getURI(), this.getModuleType().toString(), this.state.getVirtualJarFile(), ctx.getPlanBean(), ctx.getConfigDir(), ctx.getEar() == null);
      if (diagDD != null) {
         descriptors.put("META-INF/weblogic-diagnostics.xml", diagDD);
      }

      Iterator uris = this.perViewer.getDescriptorURIs();

      while(uris.hasNext()) {
         String uri = (String)uris.next();
         Descriptor desc = this.perViewer.getDescriptor(uri);
         descriptors.put(uri, desc.getRootBean());
      }

      return descriptors;
   }

   private void mergeDescriptors(WebAppDescriptor webDescriptor) throws ToolFailureException {
      try {
         Source[] sources = this.war.getLibResourcesAsSources("WEB-INF/web.xml");
         if (sources != null && sources.length > 0) {
            this.webBean = (WebAppBean)webDescriptor.mergeLibaryDescriptors(sources, "WEB-INF/web.xml");
         }

         sources = this.war.getLibResourcesAsSources("WEB-INF/weblogic.xml");
         if (sources != null && sources.length > 0) {
            this.wlBean = (WeblogicWebAppBean)webDescriptor.mergeLibaryDescriptors(sources, "WEB-INF/weblogic.xml");
         }

      } catch (IOException var3) {
         throw new ToolFailureException("Unable to parse or merge standard web module descriptors", var3);
      } catch (XMLStreamException var4) {
         throw new ToolFailureException("Unable to parse or merge standard web module descriptors", var4);
      }
   }

   private void initWebAppLibraryManager(ToolsContext ctx, LibraryManager mgr, WeblogicWebAppBean wlBean, String uri) throws ToolFailureException {
      if (wlBean != null) {
         if (wlBean.getLibraryRefs() != null) {
            LibraryReference[] ref = WebAppLibraryUtils.getWebLibRefs(wlBean, uri);
            mgr.lookup(ref);
            if (mgr.hasUnresolvedReferences()) {
               String error = mgr.getUnresolvedReferencesError();
               if (ctx.verifyLibraryReferences()) {
                  throw new ToolFailureException(error);
               }

               J2EELogger.logUnresolvedLibraryReferencesWarningLoggable(error).log();
            }

         }
      }
   }

   private WebAppDescriptor getWebAppParser() {
      WebAppDescriptor webDescriptor = WarUtils.getWebAppDescriptor(this.state.getAltDDFile(), this.moduleVjf, this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.getURI());
      return webDescriptor;
   }

   private void loadDescriptors(WebAppDescriptor webDescriptor) throws ToolFailureException {
      this.wlBean = WarUtils.getWlWebAppBean(webDescriptor);
      this.webBean = WarUtils.getWebAppBean(webDescriptor);
      this.crateScaffoldingBean();
   }

   private void crateScaffoldingBean() {
      if (this.ctx.isBeanScaffoldingEnabled() && this.wlBean != null) {
         ContainerDescriptorBean[] cdBeans = this.wlBean.getContainerDescriptors();
         if (cdBeans == null || cdBeans.length == 0) {
            this.wlBean.createContainerDescriptor();
         }

         JspDescriptorBean[] jdBeans = this.wlBean.getJspDescriptors();
         if (jdBeans == null || jdBeans.length == 0) {
            this.wlBean.createJspDescriptor();
         }

         SessionDescriptorBean[] sdBeans = this.wlBean.getSessionDescriptors();
         if (sdBeans == null || sdBeans.length == 0) {
            this.wlBean.createSessionDescriptor();
         }

      }
   }

   private void processLibraries(ToolsContext ctx) throws ToolFailureException {
      LibraryManager libraryManager = new LibraryManager(WebAppLibraryUtils.getLibraryReferencer(this.getURI()), "DOMAIN");
      this.initWebAppLibraryManager(ctx, libraryManager, this.wlBean, this.getURI());
      ctx.addLibraryManager(this.getURI(), libraryManager);
      if (libraryManager.hasReferencedLibraries()) {
         this.referenceLibraries = true;

         try {
            WebAppLibraryUtils.addWebAppLibraries(libraryManager, this.war);
         } catch (IOException var4) {
            throw new ToolFailureException("Unable to process libraries", var4);
         }
      }

   }

   protected void processAnnotations() throws ClassNotFoundException, ErrorCollectionException, ToolFailureException {
      if (WarUtils.isAnnotationEnabled(this.webBean)) {
         AnnotationProcessingManager apm = new AnnotationProcessingManager(this.war, new WebAnnotationProcessor(), this.webFragmentManager);

         try {
            apm.processAnnotations(this.state.getClassLoader(), this.webBean, (WeblogicWebAppBean)this.wlBean);
         } catch (MergeException var3) {
            throw new ToolFailureException(var3.getMessage());
         }
      }
   }

   private void initWebFragmentManager() throws ToolFailureException {
      try {
         this.webFragmentManager = new WebFragmentManager(this.war, this.webBean);
      } catch (Exception var2) {
         throw new ToolFailureException("Unable to create web fragment manager", var2);
      }
   }

   public Map merge() throws ToolFailureException {
      this.descriptors = new HashMap();
      WebAppDescriptor webDescriptor = this.getWebAppParser();
      this.loadDescriptors(webDescriptor);
      this.processLibraries(this.ctx);
      this.mergeDescriptors(webDescriptor);
      this.configureFCL(this.ctx.getAppClassLoader());
      EarUtils.handleUnsetContextRoot(this.getURI(), (String)null, this.ctx.getApplicationDD(), this.wlBean, this.webBean);
      this.perViewer = new PersistenceUnitViewer.ResourceViewer(this.state.getClassLoader(), this.getURI(), this.ctx.getConfigDir(), this.ctx.getPlanBean());
      this.perViewer.loadDescriptors();
      Iterator uris = this.perViewer.getDescriptorURIs();

      while(uris.hasNext()) {
         String uri = (String)uris.next();
         this.descriptors.put(uri, this.perViewer.getDescriptor(uri).getRootBean());
      }

      if (this.shouldBackUp()) {
         this.backupDescriptors(this.state.getVirtualJarFile());
      }

      this.initWebFragmentManager();
      this.processAnnotations(this.ctx);
      return this.processDDs(this.ctx);
   }

   public void write() throws ToolFailureException {
      try {
         if (this.referenceLibraries) {
            WebAppLibraryUtils.writeWar(this.war, this.ctx.getLibraryProvider(this.getURI()).getReferencedLibraries(), this.state.getOutputDir());
         }

         this.writeDescriptors(this.ctx);
      } catch (IOException var2) {
         throw new ToolFailureException("Unable to write out web module", var2);
      }
   }

   public ModuleType getModuleType() {
      return ModuleType.WAR;
   }

   protected void backupDescriptors(VirtualJarFile moduleVjf) throws ToolFailureException {
      if (moduleVjf.getEntry("WEB-INF/weblogic.xml") != null) {
         this.writeDescriptor("WEB-INF/weblogic.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)this.wlBean);
      }

      if (moduleVjf.getEntry("WEB-INF/web.xml") != null) {
         this.writeDescriptor("WEB-INF/web.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)this.webBean);
      }

   }

   public String getContextRoot() {
      return this.contextRoot;
   }

   public void setContextRoot(String contextRoot) {
      this.contextRoot = contextRoot;
   }

   private final void processAnnotations(ToolsContext ctx) throws ToolFailureException {
      if (!ctx.isBasicView() && (ctx.isReadOnlyInvocation() || ctx.isWriteInferredDescriptors())) {
         try {
            this.processAnnotations();
         } catch (ClassNotFoundException var3) {
            this.throwAnnotationProcessingException(ctx, var3);
         } catch (NoClassDefFoundError var4) {
            this.throwAnnotationProcessingException(ctx, var4);
         } catch (ErrorCollectionException var5) {
            this.throwAnnotationProcessingException(ctx, var5);
         }
      }

   }

   private void throwAnnotationProcessingException(ToolsContext ctx, Throwable e) throws ToolFailureException {
      if (ctx.verifyLibraryReferences()) {
         e.printStackTrace();
         throw new ToolFailureException("Unable to process annotations for module " + this.getURI(), e);
      } else {
         e.printStackTrace();
      }
   }

   private void compileWAR(ToolsContext ctx, GenericClassLoader cl, VirtualJarFile vjf, File outDir, ModuleValidationInfo mvi) throws ToolFailureException {
      Getopt2 opts = this.prepareJspcOptions(outDir, ctx);
      JspcInvoker jspc = new JspcInvoker(opts);

      try {
         jspc.compile(vjf, this.webBean, this.wlBean, cl, this.war.getResourceFinder("/"), mvi, this.war.getSplitDirectoryJars());
      } catch (ErrorCollectionException var9) {
         throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(vjf.getName(), var9.toString()).getMessage(), var9);
      }
   }

   private Getopt2 prepareJspcOptions(File outDir, ToolsContext ctx) {
      Getopt2 opts = ctx.getOpts();

      try {
         opts.setOption("classpath", this.wcl.getClassPath());
         if (ctx.getPartialOutputTarget() != null) {
            opts.setOption("d", ctx.getPartialOutputTarget());
         } else {
            opts.setOption("d", outDir.getPath() + AppcUtils.WEBINF_CLASSES);
         }

         return opts;
      } catch (BadOptionException var5) {
         throw new AssertionError(var5);
      }
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
      if (this.getResourceFinder() != null) {
         deployableObject.setResourceFinder(this.getResourceFinder());
      }

      deployableObject.setContextRoot(this.getContextRoot());
   }

   public String toString() {
      return this.getURI();
   }

   public String getStandardDescriptorURI() {
      return "WEB-INF/web.xml";
   }

   public boolean isDeployableObject() {
      return true;
   }
}
