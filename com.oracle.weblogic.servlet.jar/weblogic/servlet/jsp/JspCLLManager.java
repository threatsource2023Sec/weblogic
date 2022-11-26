package weblogic.servlet.jsp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oracle.jsp.provider.JspResourceProvider;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.JspPropertyGroupBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.VirtualDirectoryMappingBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.jsp.compiler.Diagnostic;
import weblogic.jsp.compiler.DiagnosticList;
import weblogic.jsp.compiler.IApplication;
import weblogic.jsp.compiler.ICPL;
import weblogic.jsp.compiler.IClientContext;
import weblogic.jsp.compiler.IJavelin;
import weblogic.jsp.compiler.IJavelinFile;
import weblogic.jsp.compiler.ISourceFile;
import weblogic.jsp.compiler.IToken;
import weblogic.jsp.compiler.Diagnostic.Severity;
import weblogic.jsp.compiler.client.ClientUtils;
import weblogic.jsp.compiler.jsp.IJspCompilerOptions;
import weblogic.jsp.compiler.jsp.IJspConfigFeature;
import weblogic.jsp.compiler.jsp.IWebAppProjectFeature;
import weblogic.jsp.internal.css.CSSLanguage;
import weblogic.jsp.internal.html.HtmlLanguageX;
import weblogic.jsp.internal.jsp.JspLanguageX;
import weblogic.jsp.internal.jsp.config.ConfigurationParser;
import weblogic.jsp.internal.jsp.config.JspConfigFeatureX;
import weblogic.jsp.internal.jsp.config.JspPropertyGroup;
import weblogic.jsp.internal.jsp.config.JspPropertySet;
import weblogic.jsp.internal.jsp.config.URLPattern;
import weblogic.jsp.internal.jsp.options.JspCompilerOptionsX;
import weblogic.jsp.internal.jsp.utils.JavaTransformUtils;
import weblogic.jsp.languages.java.IJavaLanguage;
import weblogic.jsp.wlw.filesystem.IFile;
import weblogic.jsp.wlw.filesystem.IFileFilter;
import weblogic.jsp.wlw.util.filesystem.FS;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.Source;

public class JspCLLManager {
   static final IJavelin JAVELIN = ClientUtils.createCommandLineJavelin(false);
   private static boolean EXTRA_SAVE_USE_BEAN;
   private static boolean DISABLE_SMAP;
   private static final int MAX_DIAGNOSTIC_ERRORS = 100;
   private IJSPCompilerContext compilerContext;
   private IApplication application;
   private ICPL cll;
   private IFile workingDir;
   private boolean addTemporaryCPL;
   private boolean isClosed;
   private boolean keepGenerated;
   private Set _tagFiles;
   private Map _tagFilesModified;

   public static boolean compileJsps(List jspURIs, IJSPCompilerContext compilerContext) throws CompilationException {
      JspCLLManager manager = null;
      boolean success = true;

      try {
         manager = new JspCLLManager(compilerContext);
         JspConfig config = compilerContext.getJspConfig();
         Set files = new HashSet();
         Set builtFiles = new HashSet();
         StringWriter errWriter = null;
         Iterator i = jspURIs.iterator();

         while(i.hasNext()) {
            String jspURI = (String)i.next();
            Source s = compilerContext.getSource(jspURI);
            if (s == null) {
               compilerContext.sayError(jspURI, " Cannot find " + jspURI + " in the given docroot");
            } else {
               URI uri = JavelinxJSPStub.getAbsoluteJspURI(s, jspURI);
               if (uri != null) {
                  IFile fileToCompile = FS.getIFile(uri);
                  if (fileToCompile != null) {
                     compilerContext.say(jspURI);
                     files.add(fileToCompile);
                     manager.getCLL().addSourceFile(fileToCompile);
                  }
               }
            }
         }

         Set files = manager.getCLL().getSourceFiles();
         boolean hasErrors = false;

         try {
            hasErrors = !manager.build(files, (IFile)null, builtFiles);
         } catch (InterruptedException var16) {
         }

         if (hasErrors || manager.getCLL().getFilesWithDiagnostics().size() > 0) {
            if (errWriter == null) {
               errWriter = new StringWriter();
            }

            boolean errors = manager.addDiagnostics(true, errWriter, builtFiles);
            if (errors) {
               success = false;
               String errorString = errWriter.getBuffer().toString();
               if (!config.isPrecompileContinue()) {
                  throw new CompilationException(errorString, (String)null, "", (String)null, (Throwable)null);
               }

               compilerContext.sayError("jspURI", errorString);
               if (errWriter != null) {
                  errWriter.getBuffer().setLength(0);
               }

               builtFiles.clear();
               files.clear();
            }
         }
      } finally {
         if (manager != null) {
            manager.close();
         }

      }

      return success;
   }

   public JspCLLManager(IJSPCompilerContext context) {
      this(context, false);
   }

   public JspCLLManager(IJSPCompilerContext context, boolean temporaryCPL) {
      this._tagFilesModified = new HashMap();
      this.addTemporaryCPL = temporaryCPL;
      this.compilerContext = context;
      this.initialize();
   }

   private void initialize() {
      this.initCPL();
      this.initCompilerOptions();
      this.initConfigOptions();
      this.initPaths();
   }

   public ICPL getCLL() {
      return this.cll;
   }

   public boolean build(Set filesToBuild, IFile ifileToCompile, Set builtFiles) throws InterruptedException {
      Map filesWithDiags = new HashMap();
      HashMap props = new HashMap();
      if (this.keepGenerated) {
         props.put("-keepGenerated", "true");
         props.put("-s", this.workingDir.getFile().getAbsolutePath());
      }

      while(filesToBuild.size() > 0) {
         builtFiles.addAll(filesToBuild);
         filesWithDiags = null;
         filesWithDiags = ClientUtils.get(this.application).build(this.cll, filesToBuild, this.workingDir, props);
         if (ClientUtils.hasStopErrors((Map)filesWithDiags)) {
            return false;
         }

         filesToBuild.addAll(this.cll.getSourceFiles());
         filesToBuild.removeAll(builtFiles);
      }

      ((Map)filesWithDiags).putAll(ClientUtils.get(this.application).buildPrototypes(this.cll, this.workingDir, props));
      return !ClientUtils.hasErrors((Map)filesWithDiags);
   }

   public boolean addDiagnostics(boolean showText, Writer errors, Set builtFiles) {
      boolean hasErrors = false;
      Set files = new HashSet();
      Iterator it = builtFiles.iterator();

      while(it.hasNext()) {
         Object next = it.next();
         if (next instanceof ISourceFile) {
            ISourceFile sf = (ISourceFile)next;
            if (sf.getCreator() != null) {
               files.add(sf.getCreator().getIFile());
            } else {
               files.add(sf.getIFile());
            }
         } else {
            files.add(next);
         }
      }

      Map filesWithDiags = this.cll.getFilesWithDiagnostics();
      DiagnosticMessageLimitFilter filter = null;
      PrintWriter prErrorWriter = null;
      Iterator i = filesWithDiags.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         IJavelinFile file = (IJavelinFile)entry.getKey();
         if (!file.isBinary() && files.contains(file.getIFile())) {
            DiagnosticList diagnostics = (DiagnosticList)entry.getValue();
            if (diagnostics != null) {
               List errs = diagnostics.get(true, Severity.ERROR);
               int numErrors = errs.size();
               if (numErrors > 0) {
                  hasErrors = true;
                  if (prErrorWriter == null) {
                     prErrorWriter = new PrintWriter(errors);
                  }

                  if (filter == null) {
                     filter = new DiagnosticMessageLimitFilter(100);
                  }

                  diagnostics.print(true, JavaTransformUtils.isDiagnosticWithAbsolutePath ? file.getIFile().getDisplayPath() : file.getName(), prErrorWriter, showText, Severity.ERROR, filter);
                  if (filter.isMax()) {
                     break;
                  }
               }
            }
         }
      }

      return hasErrors;
   }

   public synchronized void close() {
      try {
         if (!this.isClosed) {
            ClientUtils.get(this.application).close();
         }

         this.cll = null;
         this.application = null;
         this.isClosed = true;
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public boolean isClosed() {
      return this.isClosed;
   }

   private void initCPL() {
      String appName = this.compilerContext.getName();
      if (this.addTemporaryCPL) {
         appName = appName + System.currentTimeMillis();
      }

      try {
         this.application = JAVELIN.createApplication(appName);
      } catch (IllegalArgumentException var3) {
         var3.printStackTrace();
         this.application = JAVELIN.createApplication(appName + System.currentTimeMillis());
      }

      this.cll = this.application.createCPL("CPL");
   }

   private void initCompilerOptions() {
      IJspCompilerOptions compilerOptions = JspCompilerOptionsX.createFeature(this.cll);
      JspConfig jsps = this.compilerContext.getJspConfig();
      String workDir = jsps.getWorkingDir();
      IJavaLanguage javaLanguage = JAVELIN.getJavaLanguage();
      if (DISABLE_SMAP) {
         javaLanguage.setEmitDebug(0);
      } else if (jsps.isDebugEnabled()) {
         javaLanguage.setEmitDebug(7);
      }

      this.workingDir = FS.getIFile(new File(workDir));
      this.keepGenerated = jsps.isKeepGenerated();
      setUpCompilerOptions(compilerOptions, jsps);
      this.cll.setClassLoader(this.compilerContext.getClassLoader());
   }

   static void setUpCompilerOptions(IJspCompilerOptions compilerOptions, JspConfig jsps) {
      String superclass = jsps.getSuperClassName();
      if (superclass == null || "".equals(superclass)) {
         superclass = "weblogic.servlet.jsp.JspBase";
      }

      String pkgPrefix = jsps.getPackagePrefix();
      if (pkgPrefix == null) {
         pkgPrefix = "jsp_servlet";
      }

      compilerOptions.setCompilerOption("disableEnsureAppDeployment", "true");
      compilerOptions.setCompilerOption("packagePrefix", pkgPrefix);
      compilerOptions.setCompilerOption("superclass", superclass);
      compilerOptions.setCompilerOption("keepgenerated", Boolean.toString(jsps.isKeepGenerated()));
      compilerOptions.setCompilerOption("printNulls", Boolean.toString(jsps.isPrintNulls()));
      compilerOptions.setCompilerOption("compilerSupportsEncoding", Boolean.toString(jsps.isCompilerSupportsEncoding()));
      compilerOptions.setCompilerOption("backwardCompatible", Boolean.toString(jsps.isBackwardCompatible()));
      compilerOptions.setCompilerOption("rtexprvalueJspParamName", Boolean.toString(jsps.isRtexprvalueJspParamName()));
      compilerOptions.setCompilerOption("debug", Boolean.toString(jsps.isDebugEnabled()));
      compilerOptions.setCompilerOption("compressHtmlTemplate", Boolean.toString(jsps.isCompressHtmlTemplate()));
      compilerOptions.setCompilerOption("optimizeJavaExpression", Boolean.toString(jsps.isOptimizeJavaExpression()));
      compilerOptions.setCompilerOption("strictJspDocumentValidation", Boolean.toString(jsps.isStrictJspDocumentValidation()));
      compilerOptions.setCompilerOption("useByteBuffer", Boolean.toString(jsps.useByteBuffer()));
      if (jsps.getCompiler() != null) {
         compilerOptions.setCompilerOption("compiler", jsps.getCompiler());
      }

      if (jsps.source != null) {
         compilerOptions.setCompilerOption("source", jsps.source);
      }

      if (jsps.target != null) {
         compilerOptions.setCompilerOption("target", jsps.target);
      }

      if (jsps.getEncoding() != null) {
         compilerOptions.setCompilerOption("jspEncoding", jsps.getEncoding());
      }

      if (EXTRA_SAVE_USE_BEAN) {
         compilerOptions.setCompilerOption("extraSaveBeanToContext", "true");
      }

   }

   private void initConfigOptions() {
      WebAppBean descriptorBean = this.compilerContext.getWebAppBean();
      makeConfigFeature(descriptorBean, this.cll, this.compilerContext.getAdditionalExtensions());
   }

   private void initPaths() {
      List roots = new ArrayList();
      this.addPaths(roots, this.compilerContext.getClasspath());
      this.cll.initializeBinaryPaths(roots);
      this._tagFiles = new HashSet();
      String[] sourcePaths = this.compilerContext.getSourcePaths();
      List docRoots = new ArrayList();
      if (sourcePaths != null && sourcePaths.length > 0) {
         IFile[] paths = new IFile[sourcePaths.length];

         for(int i = 0; i < sourcePaths.length; ++i) {
            paths[i] = FS.getIFile(new File(sourcePaths[i]));
            if (paths[i] != null) {
               docRoots.add(paths[i].getCanonicalIFile().getURI());
            }
         }
      }

      this.cll.setFeature(IWebAppProjectFeature.class, new WebAppProjectFeatureImpl(this.cll, this.compilerContext.getContextPath(), (URI[])((URI[])docRoots.toArray(new URI[docRoots.size()])), getWebAppVersion(this.compilerContext.getWebAppBean()), getVirtualRootsInfo(this.compilerContext.getWlWebAppBean()), this.compilerContext.getSplitDirectoryJars()));
   }

   private static boolean isWebAppVersionGE2dot4(WebAppBean webappBean) {
      boolean result = false;
      String version = getWebAppVersion(webappBean);

      try {
         result = Double.parseDouble(version) >= 2.4;
      } catch (Exception var4) {
      }

      return result;
   }

   private static String getWebAppVersion(WebAppBean webappBean) {
      return webappBean == null ? "4.0" : ((DescriptorBean)webappBean).getDescriptor().getOriginalVersionInfo();
   }

   private boolean isTagFileStale(IFile tagFile) {
      Object modified = this._tagFilesModified.get(tagFile);
      if (modified != null && (Long)modified >= tagFile.lastModified()) {
         return false;
      } else {
         this._tagFilesModified.put(tagFile, new Long(tagFile.lastModified()));
         return true;
      }
   }

   static IJspConfigFeature makeConfigFeature(WebAppBean webAppBean, ICPL cll, Set extraExtensions) {
      List groups = new ArrayList();
      if (webAppBean != null) {
         JspConfigBean[] config = webAppBean.getJspConfigs();
         if (config != null && config.length > 0) {
            JspConfigBean bean = config[0];
            if (bean != null) {
               JspPropertyGroupBean[] propertyGroups = bean.getJspPropertyGroups();
               if (propertyGroups != null) {
                  for(int i = 0; i < propertyGroups.length; ++i) {
                     fillPropertyGroups(propertyGroups[i], groups, cll);
                  }
               }
            }
         }
      }

      List additionalGroups = fillDefaultGroups(extraExtensions, webAppBean);
      if (additionalGroups != null) {
         groups.addAll(additionalGroups);
      }

      return JspConfigFeatureX.createFeature(cll, groups, isWebAppVersionGE2dot4(webAppBean));
   }

   private static void fillPropertyGroups(JspPropertyGroupBean group, List propertyGroups, ICPL cpl) {
      if (group != null && group.getUrlPatterns() != null) {
         String[] urlPatterns = group.getUrlPatterns();
         Boolean ignoreEL = group.isElIgnored() ? Boolean.TRUE : Boolean.FALSE;
         Boolean scriptingInvalid = group.isScriptingInvalid() ? Boolean.TRUE : Boolean.FALSE;
         String pageEncoding = group.getPageEncoding();
         List preludes = null;
         if (group.getIncludePreludes() != null) {
            preludes = Arrays.asList(group.getIncludePreludes());
         }

         List codas = null;
         if (group.getIncludeCodas() != null) {
            codas = Arrays.asList(group.getIncludeCodas());
         }

         Boolean isXml = group.isIsXmlSet() ? (group.isIsXml() ? Boolean.TRUE : Boolean.FALSE) : null;
         Boolean deferredSyntaxAllowedAsLiteral = group.isDeferredSyntaxAllowedAsLiteral() ? Boolean.TRUE : Boolean.FALSE;
         Boolean trimDirectiveWhitespaces = group.isTrimDirectiveWhitespaces() ? Boolean.TRUE : Boolean.FALSE;
         String defaultContentType = group.getDefaultContentType();
         String buffer = group.getBuffer();
         Boolean errorOnUndeclaredNamespace = group.isErrorOnUndeclaredNamespace() ? Boolean.TRUE : Boolean.FALSE;
         ConfigurationParser parser = new ConfigurationParser();
         int bufferSize = parser.parseBuffer((IToken)null, buffer, false, JspLanguageX.isCompatible(cpl));
         JspPropertySet propSet = new JspPropertySet(ignoreEL, scriptingInvalid, pageEncoding, preludes, codas, isXml, deferredSyntaxAllowedAsLiteral, trimDirectiveWhitespaces, defaultContentType, bufferSize, errorOnUndeclaredNamespace);

         for(int i = 0; i < urlPatterns.length; ++i) {
            try {
               URLPattern pattern = new URLPattern(urlPatterns[i], true);
               propertyGroups.add(new JspPropertyGroup(pattern, propSet));
            } catch (IllegalArgumentException var21) {
            }
         }

      }
   }

   private static List fillDefaultGroups(Set extensions, WebAppBean webAppBean) {
      if (extensions == null) {
         return null;
      } else {
         List propertyGroups = null;
         Boolean ignoreEL = !isWebAppVersionGE2dot4(webAppBean);
         Boolean scriptingInvalid = Boolean.FALSE;
         Boolean deferredSyntaxAllowedAsLiteral = Boolean.FALSE;
         Boolean trimDirectiveWhitespaces = Boolean.FALSE;
         Boolean errorOnUndeclaredNamespace = Boolean.FALSE;
         JspPropertySet propSet = new JspPropertySet(ignoreEL, scriptingInvalid, (String)null, (List)null, (List)null, (Boolean)null, deferredSyntaxAllowedAsLiteral, trimDirectiveWhitespaces, (String)null, -1, errorOnUndeclaredNamespace);
         Iterator i = extensions.iterator();

         while(i.hasNext()) {
            String extension = (String)i.next();
            if (propertyGroups == null) {
               propertyGroups = new ArrayList(extensions.size());
            }

            URLPattern pattern = new URLPattern(extension, true);
            propertyGroups.add(new JspPropertyGroup(pattern, propSet));
         }

         return propertyGroups;
      }
   }

   private void addPaths(List roots, String path) {
      String[] parts = StringUtils.splitCompletely(path, File.pathSeparator);

      for(int i = 0; i < parts.length; ++i) {
         File file = new File(parts[i]);
         if (file.exists()) {
            roots.add(FS.getIFile(file));
         }
      }

   }

   private void addLooseClassFiles(Set files, IFile root) {
      if (root != null && root.exists()) {
         IFile[] children = root.listIFiles(new IFileFilter() {
            public boolean accept(IFile file) {
               if ("class".equals(file.getNameExtension(false))) {
                  return true;
               } else {
                  return file.isDirectory();
               }
            }
         });
         if (children != null) {
            for(int i = 0; i < children.length; ++i) {
               if (children[i].isDirectory()) {
                  this.addLooseClassFiles(files, children[i]);
               } else {
                  files.add(children[i]);
               }
            }
         }
      }

   }

   private static void getTagFiles(IFile root, Set files) {
      if (root != null && root.exists()) {
         IFile[] children = root.listIFiles();

         for(int i = 0; i < children.length; ++i) {
            if (!children[i].getNameExtension(false).equals("tag") && !children[i].getNameExtension(false).equals("tagx")) {
               if (children[i].isDirectory()) {
                  getTagFiles(children[i], files);
               }
            } else {
               files.add(children[i]);
            }
         }
      }

   }

   public static void setWebAppProjectFeature(ICPL cpl, String contextPath, URI[] docRoots, String version, VirtualRootInfo[] vRoots, JspResourceProvider resourceProvider, String[] splitDirectoryJars) {
      cpl.setFeature(IWebAppProjectFeature.class, new WebAppProjectFeatureImpl(cpl, contextPath, docRoots, version, vRoots, splitDirectoryJars, resourceProvider));
   }

   static VirtualRootInfo[] getVirtualRootsInfo(WeblogicWebAppBean wlDescriptorBean) {
      if (wlDescriptorBean == null) {
         return null;
      } else {
         VirtualDirectoryMappingBean[] vDirMapping = wlDescriptorBean.getVirtualDirectoryMappings();
         if (vDirMapping != null && vDirMapping.length != 0) {
            VirtualRootInfo[] vRootInfo = new VirtualRootInfo[vDirMapping.length];

            for(int i = 0; i < vDirMapping.length; ++i) {
               String[] urlPatterns = vDirMapping[i].getUrlPatterns();
               if (urlPatterns == null || urlPatterns.length == 0) {
                  urlPatterns = new String[]{WebAppSecurity.fixupURLPattern("*")};
               }

               try {
                  File srcPath = (new File(vDirMapping[i].getLocalPath())).getCanonicalFile();
                  IFile vpath = FS.getIFile(srcPath);
                  if (vpath != null) {
                     vRootInfo[i] = new VirtualRootInfo(vpath.getURI(), urlPatterns);
                  } else {
                     vRootInfo[i] = null;
                  }
               } catch (IOException var7) {
                  vRootInfo[i] = null;
               }
            }

            return vRootInfo;
         } else {
            return null;
         }
      }
   }

   static {
      FS.initialize();
      JAVELIN.addLanguage(new JspLanguageX(JAVELIN));
      JAVELIN.addLanguage(new HtmlLanguageX(JAVELIN));
      JAVELIN.addLanguage(new CSSLanguage(JAVELIN));
      EXTRA_SAVE_USE_BEAN = Boolean.getBoolean("weblogic.jsp.extraSaveUseBean");
      DISABLE_SMAP = Boolean.getBoolean("weblogic.jsp.disableSMAP");
   }

   private static class DiagnosticMessageLimitFilter implements DiagnosticList.Filter {
      private final int max;
      private int count;

      DiagnosticMessageLimitFilter(int max) {
         this.max = max;
         this.count = 0;
      }

      public boolean accept(Diagnostic diagnostic) {
         if (!this.isMax() && diagnostic != null) {
            ++this.count;
            return true;
         } else {
            return false;
         }
      }

      boolean isMax() {
         return this.count >= this.max;
      }
   }

   public static class JspClientContext implements IClientContext {
      private boolean hasException = false;
      private Throwable exception;
      private String path;

      JspClientContext() {
      }

      public void addFileListener(IFile file) {
      }

      public void exceptionEncountered(Throwable t, IFile file) {
         this.path = file.getDisplayPath();
         this.exception = t;
         this.hasException = true;
      }

      public void removeFileListener(IFile file) {
      }

      public void synchronizeFileListeners() {
      }

      public boolean hasException() {
         return this.hasException;
      }

      public Throwable getException() {
         return this.exception;
      }

      public String getPath() {
         return this.path;
      }
   }

   static class VirtualRootInfo {
      private URI virtualRoot;
      private String[] urlPatterns;

      public VirtualRootInfo(URI virtualRoot, String[] urlPatterns) {
         this.virtualRoot = virtualRoot;
         this.urlPatterns = urlPatterns;
      }
   }

   private static class WebAppProjectFeatureImpl implements IWebAppProjectFeature {
      private ICPL _cpl;
      private String _contextPath;
      private URI[] _roots;
      private VirtualRootInfo[] _vroots;
      private String _version;
      private JspResourceProvider _resourceProvider;
      private String[] splitDirectoryJars;

      public WebAppProjectFeatureImpl(ICPL cpl, String contextPath, URI[] roots, String version, VirtualRootInfo[] vRoots, String[] splitDirectoryJars) {
         this._resourceProvider = null;
         this._cpl = cpl;
         this._contextPath = contextPath;
         this._roots = roots;
         this._vroots = vRoots;
         this._version = version;
         this.splitDirectoryJars = splitDirectoryJars;
      }

      public WebAppProjectFeatureImpl(ICPL cpl, String contextPath, URI[] roots, String version, VirtualRootInfo[] vRoots, String[] splitDirectoryJars, JspResourceProvider resourceProvider) {
         this(cpl, contextPath, roots, version, vRoots, splitDirectoryJars);
         this._resourceProvider = resourceProvider;
      }

      public String getContextPath() {
         return this._contextPath;
      }

      public ICPL getCPL() {
         return this._cpl;
      }

      public URI[] getRoots() {
         return this._roots;
      }

      public URI findRoot(String resource) {
         try {
            URI res = new URI(resource);

            for(int i = 0; i < this._roots.length; ++i) {
               URI relative = this._roots[i].relativize(res);
               if (!relative.equals(res)) {
                  return this._roots[i];
               }
            }
         } catch (URISyntaxException var5) {
         }

         for(int i = 0; i < this._roots.length; ++i) {
            IFile res = FS.getIFile(FS.getIFile(this._roots[i]), resource);
            if (res != null && res.exists()) {
               return this._roots[i];
            }
         }

         return null;
      }

      public URI findResource(String path) {
         if (path == null) {
            return null;
         } else {
            for(int i = 0; i < this._roots.length; ++i) {
               IFile res = FS.getIFile(FS.getIFile(this._roots[i]), path);
               if (res != null && res.exists()) {
                  String _file = res.getURI().normalize().toString();
                  String root = this._roots[i].normalize().toString();
                  if (_file.startsWith(root)) {
                     return res.getURI();
                  }
               }
            }

            if (this._vroots != null && this._vroots.length != 0) {
               URLPattern uriPattern = new URLPattern(path, false);

               for(int i = 0; i < this._vroots.length; ++i) {
                  if (this._vroots[i] != null) {
                     for(int j = 0; j < this._vroots[i].urlPatterns.length; ++j) {
                        if (this.matchPattern(this._vroots[i].urlPatterns[j], uriPattern)) {
                           IFile res = FS.getIFile(FS.getIFile(this._vroots[i].virtualRoot), path);
                           if (res != null) {
                              String _file = null;
                              if (res.isInArchive()) {
                                 _file = res.getURI().normalize().getSchemeSpecificPart();
                              } else {
                                 _file = res.getURI().normalize().toString();
                              }

                              String root = this._vroots[i].virtualRoot.normalize().toString();
                              if (_file.startsWith(root)) {
                                 return res.getURI();
                              }
                           }
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         }
      }

      private boolean matchPattern(String pattern, URLPattern uriPattern) {
         URLPattern urlPattern = new URLPattern(pattern, true);
         if (urlPattern.getExtension() == null) {
            if (urlPattern.getPath() == null || uriPattern.getPath() == null) {
               return false;
            }

            if (uriPattern.getPath().startsWith(urlPattern.getPath())) {
               return true;
            }
         } else {
            if (urlPattern.getExtension().equals("*") || urlPattern.getPath() != null && urlPattern.getPath().equals("/")) {
               return true;
            }

            if (uriPattern.getExtension() != null) {
               if (urlPattern.getExtension().equals(uriPattern.getExtension())) {
                  return true;
               }
            } else {
               if (urlPattern.getPath() == null || uriPattern.getPath() == null) {
                  return false;
               }

               if (uriPattern.getPath().startsWith(urlPattern.getPath())) {
                  return true;
               }
            }
         }

         return false;
      }

      public URI[] getVirtualRoots() {
         if (this._vroots != null && this._vroots.length != 0) {
            URI[] virtualRoots = new URI[this._vroots.length];

            for(int i = 0; i < this._vroots.length; ++i) {
               virtualRoots[i] = this._vroots[i].virtualRoot;
            }

            return virtualRoots;
         } else {
            return null;
         }
      }

      public String getVersion() {
         return this._version;
      }

      public IWebAppProjectFeature[] getWebAppProjects() {
         return new IWebAppProjectFeature[]{this};
      }

      public JspResourceProvider getResourceProvider() {
         return this._resourceProvider;
      }

      public URI[] getExtraLibs() {
         if (this.splitDirectoryJars == null) {
            return null;
         } else {
            URI[] uris = new URI[this.splitDirectoryJars.length];

            for(int i = 0; i < this.splitDirectoryJars.length; ++i) {
               uris[i] = (new File(this.splitDirectoryJars[i])).toURI();
            }

            return uris;
         }
      }
   }

   public interface IJSPCompilerContext {
      ClassLoader getClassLoader();

      Source getSource(String var1);

      String getClasspath();

      JspConfig getJspConfig();

      String[] getSourcePaths();

      WebAppBean getWebAppBean();

      WeblogicWebAppBean getWlWebAppBean();

      String getName();

      ServletWorkContext getServletContext();

      String getContextPath();

      void say(String var1);

      void sayError(String var1, String var2);

      Set getAdditionalExtensions();

      String[] getSplitDirectoryJars();
   }
}
