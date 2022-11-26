package weblogic.servlet.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.descriptor.JspConfigDescriptor;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.JspPropertyGroupBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.TagLibBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.JspDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.jsp.ExpressionInterceptor;
import weblogic.servlet.jsp.JSPPrecompiler;
import weblogic.servlet.jsp.JspConfig;
import weblogic.servlet.jsp.JspFactoryImpl;
import weblogic.servlet.jsp.dd.JspConfigDescriptorImpl;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.http.HttpParsing;

public final class JSPManager {
   public static final String PAGE_CHECK_SECS = "PageCheckSeconds";
   public static final String JSP_VERBOSE = "Verbose";
   public static final String JSP_KEEP_GENERATED = "Keepgenerated";
   private final HashMap jspcArgs = new HashMap();
   private TagLibBean[] taglibs;
   private String userJSPWorkingDir;
   private String jspcPkgPrefix;
   private boolean jspExactMapping = false;
   private String jspServletClazz;
   private final ArrayList jspConfigs;
   private boolean jspPrecompileEnabled;
   private Set jspExtensions;
   private final BeanUpdateListener jspBeanListener;
   private String resourceProviderClass;
   private boolean isStrictStaleCheck;
   private boolean pageCheckSecondsSet;
   private boolean productionMode;
   private int pageCheckSeconds;
   private boolean el22BackwardCompatible;
   private JspConfigDescriptor jspConfigDescriptor;
   private WebAppBean webBean;
   private WeblogicWebAppBean wlWebBean;
   private boolean isRtexprvalueJspParamName;
   private boolean isJSPCompilerBackwardsCompatible;
   private String rootTempDirPath;
   private String logContext;
   static Map JSP_DESC_ELEMENTS_MAP = new HashMap();
   static final String DEPRECATED_JSP_ELEMENT = "warning";

   JSPManager(WebAppBean webBean, WeblogicWebAppBean wlWebBean, boolean isRtexprvalueJspParamName, boolean isJSPCompilerBackwardsCompatible, String rootTempDirPath, String logContext) throws ModuleException {
      this.jspServletClazz = JspConfig.DEFAULT_JSP_SERVLET;
      this.jspConfigs = new ArrayList();
      this.jspPrecompileEnabled = false;
      this.isStrictStaleCheck = true;
      this.pageCheckSecondsSet = false;
      this.productionMode = false;
      this.pageCheckSeconds = 1;
      this.el22BackwardCompatible = false;
      this.webBean = webBean;
      this.wlWebBean = wlWebBean;
      this.isRtexprvalueJspParamName = isRtexprvalueJspParamName;
      this.isJSPCompilerBackwardsCompatible = isJSPCompilerBackwardsCompatible;
      this.rootTempDirPath = rootTempDirPath;
      this.logContext = logContext;
      this.productionMode = WebServerRegistry.getInstance().isProductionMode();
      this.pageCheckSeconds = WebAppConfigManager.getDefaultReloadSecs();
      this.registerJspDescriptor();
      this.registerTagLibs();
      this.jspBeanListener = this.createBeanUpdateListener();
   }

   public boolean isJspExactMapping() {
      return this.jspExactMapping;
   }

   public TagLibBean[] getTagLibs() {
      return this.taglibs;
   }

   public Map getJspConfigArgs() {
      return this.jspcArgs;
   }

   public String getJspcPkgPrefix() {
      return this.jspcPkgPrefix;
   }

   public String getResourceProviderClass() {
      return this.resourceProviderClass;
   }

   public JspConfigDescriptor getJspConfigDescriptor() {
      return this.jspConfigDescriptor;
   }

   public String getJSPWorkingDir() {
      return this.userJSPWorkingDir != null ? this.userJSPWorkingDir : this.rootTempDirPath;
   }

   public static Set getJspConfigPatterns(JspConfigBean[] jspConfig) {
      if (jspConfig != null && jspConfig.length != 0) {
         JspConfigBean config = jspConfig[0];
         JspPropertyGroupBean[] groups = config.getJspPropertyGroups();
         if (groups == null) {
            return null;
         } else {
            Set patternSet = null;

            for(int j = 0; j < groups.length; ++j) {
               String[] patterns = groups[j].getUrlPatterns();

               for(int i = 0; i < patterns.length; ++i) {
                  String pat = patterns[i];
                  if (!"*.jspx".equals(pat)) {
                     if (patternSet == null) {
                        patternSet = new HashSet();
                     }

                     if (!pat.startsWith("*.")) {
                        pat = HttpParsing.ensureStartingSlash(pat);
                     }

                     patternSet.add(pat);
                  }
               }
            }

            return patternSet;
         }
      } else {
         return null;
      }
   }

   public synchronized JspConfig createJspConfig() {
      JspConfig jspConfig = new JspConfig(this.jspcArgs);
      this.jspConfigs.add(jspConfig);
      return jspConfig;
   }

   public boolean isPageCheckSecondsSet() {
      return this.pageCheckSecondsSet;
   }

   public int getPageCheckSeconds() {
      return this.pageCheckSeconds;
   }

   public Set getJspExtensions() {
      return this.jspExtensions;
   }

   BeanUpdateListener getBeanUpdateListener() {
      return this.jspBeanListener;
   }

   boolean isStrictStaleCheck() {
      return this.isStrictStaleCheck;
   }

   boolean isJspPrecompileEnabled() {
      return this.jspPrecompileEnabled;
   }

   boolean isEL22BackwardCompatible() {
      return this.el22BackwardCompatible;
   }

   void setJspParam(String name, String value) throws DeploymentException {
      Iterator i = this.jspConfigs.iterator();

      while(i.hasNext()) {
         JspConfig jspConfig = (JspConfig)i.next();
         if (name.equals("workingDir")) {
            if (value == null) {
               throw new DeploymentException("JSP 'workingDir' cannot be null");
            }

            this.userJSPWorkingDir = value;
            if (this.userJSPWorkingDir != null) {
               this.userJSPWorkingDir = this.userJSPWorkingDir.replace('/', File.separatorChar);
               this.ensureWorkingDir(this.userJSPWorkingDir);
            }

            jspConfig.setWorkingDir(value);
         } else if (name.equals("pageCheckSeconds")) {
            jspConfig.setPageCheckSecs(Long.parseLong(value));
         } else if (name.equals("compileCommand")) {
            jspConfig.setCompileCommand(value);
         } else if (name.equals("compilerclass")) {
            jspConfig.setCompilerClass(value);
         } else if (name.equals("compileFlags")) {
            jspConfig.setCompileFlagsString(value);
         } else if (name.equals("verbose")) {
            jspConfig.setVerbose("true".equalsIgnoreCase(value));
         } else if (name.equals("keepgenerated")) {
            jspConfig.setKeepGenerated("true".equalsIgnoreCase(value));
         } else if (name.equals("precompileContinue")) {
            jspConfig.setPrecompileContinue("true".equalsIgnoreCase(value));
         } else if (name.equals("encoding")) {
            jspConfig.setEncoding(value);
         } else if (name.equals("compilerSupportsEncoding")) {
            jspConfig.setCompilerSupportsEncoding("true".equalsIgnoreCase(value));
         } else if (name.equals("noTryBlocks")) {
            jspConfig.setNoTryBlocks("true".equalsIgnoreCase(value));
         } else if (name.equals("exactMapping")) {
            jspConfig.setExactMapping("true".equalsIgnoreCase(value));
         } else if (name.equals("debug")) {
            jspConfig.setDebugEnabled("true".equalsIgnoreCase(value));
         } else if (name.equals("compressHtmlTemplate")) {
            jspConfig.setCompressHtmlTemplate("true".equalsIgnoreCase(value));
         } else if (name.equals("optimizeJavaExpression")) {
            jspConfig.setOptimizeJavaExpression("true".equalsIgnoreCase(value));
         } else {
            if (!name.equals("strictJspDocumentValidation")) {
               throw new DeploymentException("Unrecognized JSP param: " + name);
            }

            jspConfig.setStrictJspDocumentValidation("true".equalsIgnoreCase(value));
         }
      }

      this.setArg(this.jspcArgs, name, value);
   }

   private void registerTagLibs() {
      if (this.webBean != null) {
         JspConfigBean[] jspConfigBeans = this.webBean.getJspConfigs();
         if (jspConfigBeans != null && jspConfigBeans.length != 0) {
            this.taglibs = jspConfigBeans[0].getTagLibs();
         }
      }
   }

   void registerExpressionInterceptor(WebAppServletContext context) throws DeploymentException {
      String interceptorName = null;
      if (this.wlWebBean != null) {
         JspDescriptorBean jspDescriptor = (JspDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(this.wlWebBean, this.wlWebBean.getJspDescriptors(), "JspDescriptor");
         if (jspDescriptor != null) {
            interceptorName = jspDescriptor.getExpressionInterceptor();
         }
      }

      if (interceptorName == null) {
         ParamValueBean[] contextParams = this.webBean.getContextParams();
         if (contextParams != null) {
            ParamValueBean[] var4 = contextParams;
            int var5 = contextParams.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ParamValueBean contextParam = var4[var6];
               if (contextParam.getParamName().equals(ExpressionInterceptor.class.getName())) {
                  interceptorName = contextParam.getParamValue();
                  break;
               }
            }
         }
      }

      if (interceptorName != null) {
         try {
            ExpressionInterceptor interceptor = (ExpressionInterceptor)context.createInstance(interceptorName);
            context.setAttribute(ExpressionInterceptor.class.getName(), interceptor);
         } catch (Exception var8) {
            throw new DeploymentException("Failed to create intance of the provided ExpressionIntercetpor implemenation: " + interceptorName, var8);
         }
      }

   }

   void precompileJSPs(WebAppServletContext context) throws DeploymentException {
      if (this.jspPrecompileEnabled) {
         Thread thread = Thread.currentThread();
         ClassLoader cl = thread.getContextClassLoader();

         try {
            Loggable l;
            try {
               JspConfig precompiler;
               if (HTTPDebugLogger.isEnabled()) {
                  precompiler = new JspConfig(this.jspcArgs);
                  l = HTTPLogger.logPrecompilingJSPsLoggable(context.getAppDisplayName(), context.getModuleName(), precompiler.toString());
                  HTTPDebugLogger.debug(l.getMessage());
               }

               precompiler = null;
               Class precompilerClazz = Class.forName(JspConfig.getJspPrecompilerClass());
               JSPPrecompiler precompiler = (JSPPrecompiler)precompilerClazz.newInstance();
               File dr = new File(context.getDocroot());
               thread.setContextClassLoader(context.getServletClassLoader());
               precompiler.compile(context, dr);
            } catch (RuntimeException var11) {
               throw var11;
            } catch (Exception var12) {
               l = HTTPLogger.logFailureCompilingJSPsLoggable(context.getAppDisplayName(), context.getModuleName(), var12);
               l.log();
               throw new DeploymentException(var12);
            }
         } finally {
            thread.setContextClassLoader(cl);
         }

      }
   }

   private void initJspConfigDescriptor() {
      JspConfigBean[] beans = this.webBean.getJspConfigs();
      if (beans != null && beans.length != 0) {
         this.jspConfigDescriptor = new JspConfigDescriptorImpl(this.webBean.getJspConfigs()[0]);
      } else {
         this.jspConfigDescriptor = null;
      }

   }

   void registerJspServlet(WebAppServletContext context, boolean isImplicitServletMappingDisabled) {
      ServletStubImpl jspStub = context.getJspServletStub();
      ServletStubImpl jspxStub = context.getJspxServletStub();
      if (!isImplicitServletMappingDisabled) {
         if (jspStub == null) {
            context.registerServlet("JspServlet", "*.jsp", this.jspServletClazz);
         }

         if (jspxStub == null) {
            context.registerServlet("JspServlet", "*.jspx", this.jspServletClazz);
         }
      }

      ServletStubImpl jspConfigStub = jspStub != null ? jspStub : jspxStub;
      if (jspConfigStub == null) {
         jspConfigStub = context.getJspServletStub();
      }

      this.registerJspServletMappings(context, jspConfigStub);
   }

   private void registerJspServletMappings(WebAppServletContext context, ServletStubImpl jspServletStub) {
      if (this.webBean != null && jspServletStub != null) {
         JspConfigBean[] jspConfigs = this.webBean.getJspConfigs();
         Set patterns = getJspConfigPatterns(jspConfigs);
         if (patterns != null) {
            Iterator var5 = patterns.iterator();

            while(var5.hasNext()) {
               String pattern = (String)var5.next();
               context.registerServletMap(jspServletStub.getServletName(), pattern, jspServletStub);
            }
         }

      }
   }

   private void setArg(HashMap args, String key, String value) {
      if (value != null) {
         args.put(key, value);
      }

   }

   private void registerJspDescriptor() throws ModuleException {
      this.addJspExtensions(this.webBean);
      this.jspcArgs.put("workingDir", this.getJSPWorkingDir());
      this.jspcArgs.put("verbose", this.productionMode ? "false" : "true");
      this.jspExactMapping = true;
      this.jspcArgs.put("rtexprvalueJspParamName", "" + this.isRtexprvalueJspParamName);
      this.jspcArgs.put("backwardCompatible", "" + this.isJSPCompilerBackwardsCompatible);
      if (this.wlWebBean != null) {
         this.initialize((JspDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(this.wlWebBean, this.wlWebBean.getJspDescriptors(), "JspDescriptor"));
      }

      if (!this.productionMode && JspConfig.checkCompilerClass() && this.jspcArgs != null && this.jspcArgs.get("compilerClass") == null) {
         String command = (String)this.jspcArgs.get("compileCommand");
         if (command == null) {
            this.setArg(this.jspcArgs, "compilerClass", "com.sun.tools.javac.Main");
         }
      }

      if (this.jspcPkgPrefix == null) {
         this.jspcPkgPrefix = "jsp_servlet";
      }

      this.initJspConfigDescriptor();
   }

   private void initialize(JspDescriptorBean jd) throws ModuleException {
      if (jd != null) {
         this.userJSPWorkingDir = jd.getWorkingDir();
         if (this.userJSPWorkingDir != null) {
            this.userJSPWorkingDir = this.userJSPWorkingDir.replace('/', File.separatorChar);
            this.ensureWorkingDir(this.userJSPWorkingDir);
         }

         this.jspPrecompileEnabled = jd.isPrecompile();
         this.jspcPkgPrefix = jd.getPackagePrefix();
         this.jspExactMapping = jd.isExactMapping();
         this.resourceProviderClass = jd.getResourceProviderClass();
         this.isStrictStaleCheck = jd.isStrictStaleCheck();
         this.el22BackwardCompatible = jd.isEL22BackwardCompatible();
         if (jd.getEncoding() != null) {
            HTTPLogger.logDeprecatedEncodingPropertyUsed(this.logContext);
         }

         DescriptorBean bean = (DescriptorBean)jd;
         if (bean.isSet("RtexprvalueJspParamName")) {
            this.setArg(this.jspcArgs, "rtexprvalueJspParamName", "" + jd.isRtexprvalueJspParamName());
         }

         if (bean.isSet("BackwardCompatible")) {
            this.setArg(this.jspcArgs, "backwardCompatible", "" + jd.isBackwardCompatible());
         }

         if (jd.isPageCheckSecondsSet()) {
            this.pageCheckSecondsSet = true;
            this.pageCheckSeconds = jd.getPageCheckSeconds();
         }

         this.setArg(this.jspcArgs, "encoding", jd.getEncoding());
         this.setArg(this.jspcArgs, "keepgenerated", "" + jd.isKeepgenerated());
         this.setArg(this.jspcArgs, "superclass", jd.getSuperClass());
         this.setArg(this.jspcArgs, "pageCheckSeconds", "" + jd.getPageCheckSeconds());
         this.setArg(this.jspcArgs, "index.jsp", jd.getDefaultFileName());
         this.setArg(this.jspcArgs, "workingDir", jd.getWorkingDir());
         this.setArg(this.jspcArgs, "packagePrefix", jd.getPackagePrefix());
         this.setArg(this.jspcArgs, "printNulls", "" + jd.isPrintNulls());
         this.setArg(this.jspcArgs, "exactMapping", "" + jd.isExactMapping());
         this.setArg(this.jspcArgs, "verbose", "" + jd.isVerbose());
         this.setArg(this.jspcArgs, "debug", "" + jd.isDebug());
         this.setArg(this.jspcArgs, "compressHtmlTemplate", "" + jd.isCompressHtmlTemplate());
         this.setArg(this.jspcArgs, "optimizeJavaExpression", "" + jd.isOptimizeJavaExpression());
         if (this.jspPrecompileEnabled) {
            this.setArg(this.jspcArgs, "precompileContinue", "" + jd.isPrecompileContinue());
         }

         if (jd.getResourceProviderClass() != null) {
            this.setArg(this.jspcArgs, "resourceProviderClass", jd.getResourceProviderClass());
         }

         this.setArg(this.jspcArgs, "strictJspDocumentValidation", "" + jd.isStrictJspDocumentValidation());
         if (jd.getCompilerSourceVM() != null && jd.getCompilerSourceVM().length() > 0) {
            this.setArg(this.jspcArgs, "source", jd.getCompilerSourceVM());
         }

         if (jd.getCompilerTargetVM() != null && jd.getCompilerTargetVM().length() > 0) {
            this.setArg(this.jspcArgs, "target", jd.getCompilerTargetVM());
         }

      }
   }

   private void ensureWorkingDir(String workingDir) throws ModuleException {
      File wd = new File(workingDir);
      if (!wd.exists()) {
         if (!wd.mkdirs()) {
            if (!wd.exists()) {
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  WebAppModule.DEBUG.debug("*** Debug: WORKINGDIR:" + wd.getAbsolutePath());
               }

               throw new ModuleException("Working directory: " + workingDir + " was not found and could not be created");
            }
         }
      }
   }

   private void addJspExtensions(WebAppBean bean) {
      if (bean != null) {
         ServletBean[] servlets = bean.getServlets();
         List servletNames = null;
         if (servlets != null) {
            for(int i = 0; i < servlets.length; ++i) {
               if (JspConfig.DEFAULT_JSP_SERVLET.equals(servlets[i].getServletClass())) {
                  if (servletNames == null) {
                     servletNames = new ArrayList();
                  }

                  servletNames.add(servlets[i].getServletName());
               }
            }

            if (servletNames != null && servletNames.size() > 0) {
               ServletMappingBean[] mappings = bean.getServletMappings();
               if (mappings == null) {
                  return;
               }

               for(int j = 0; j < mappings.length; ++j) {
                  String name = mappings[j].getServletName();
                  if (servletNames.contains(name)) {
                     if (this.jspExtensions == null) {
                        this.jspExtensions = new HashSet();
                     }

                     if (mappings[j].getUrlPatterns() != null && mappings[j].getUrlPatterns().length > 0) {
                        this.jspExtensions.addAll(Arrays.asList(mappings[j].getUrlPatterns()));
                     }
                  }
               }
            }
         }

      }
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new WebComponentBeanUpdateListener() {
         protected void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate prop) {
            String propertyName = prop.getPropertyName();
            if ("PageCheckSeconds".equals(propertyName)) {
               JSPManager.this.pageCheckSecondsSet = false;
               JSPManager.this.pageCheckSeconds = JSPManager.this.productionMode ? -1 : 1;
            }

            Iterator i = JSPManager.this.jspConfigs.iterator();

            while(i.hasNext()) {
               JspConfig jspConfig = (JspConfig)i.next();
               if ("Verbose".equals(propertyName)) {
                  jspConfig.setVerbose(true);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "verbose", "ture");
               }

               if ("Keepgenerated".equals(propertyName)) {
                  jspConfig.setKeepGenerated(false);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "keepgenerated", "false");
               }

               if ("PageCheckSeconds".equals(propertyName)) {
                  jspConfig.setPageCheckSecs((long)JSPManager.this.pageCheckSeconds);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "pageCheckSeconds", "" + JSPManager.this.pageCheckSeconds);
               }
            }

         }

         protected void handlePropertyChange(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            JspDescriptorBean jdb = (JspDescriptorBean)newBean;
            String propertyName = prop.getPropertyName();
            if (propertyName.equals("PageCheckSeconds") && jdb != null) {
               JSPManager.this.pageCheckSecondsSet = true;
               JSPManager.this.pageCheckSeconds = jdb.getPageCheckSeconds();
            }

            Iterator i = JSPManager.this.jspConfigs.iterator();

            while(i.hasNext()) {
               JspConfig jspConfig = (JspConfig)i.next();
               if (propertyName.equals("Verbose")) {
                  jspConfig.setVerbose(jdb.isVerbose());
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "verbose", "" + jdb.isVerbose());
               }

               if (propertyName.equals("Keepgenerated")) {
                  jspConfig.setKeepGenerated(jdb.isKeepgenerated());
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "keepgenerated", "" + jdb.isKeepgenerated());
               }

               if (propertyName.equals("PageCheckSeconds")) {
                  jspConfig.setPageCheckSecs((long)JSPManager.this.pageCheckSeconds);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "pageCheckSeconds", "" + JSPManager.this.pageCheckSeconds);
               }
            }

         }

         protected void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) throws BeanUpdateRejectedException {
            if (newBean instanceof JspDescriptorBean) {
               JspDescriptorBean cur = (JspDescriptorBean)newBean;
               JspDescriptorBean prev = (JspDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(JSPManager.this.wlWebBean, JSPManager.this.wlWebBean.getContainerDescriptors(), "JspDescriptor");
               List changedNames = new ArrayList();
               computeChange("package-prefix", cur.getPackagePrefix(), prev.getPackagePrefix(), changedNames);
               computeChange("super-class", cur.getSuperClass(), prev.getSuperClass(), changedNames);
               computeChange("precompile", cur.isPrecompile(), prev.isPrecompile(), changedNames);
               computeChange("precompile-continue", cur.isPrecompileContinue(), prev.isPrecompileContinue(), changedNames);
               computeChange("working-dir", cur.getWorkingDir(), prev.getWorkingDir(), changedNames);
               computeChange("print-nulls", cur.isPrintNulls(), prev.isPrintNulls(), changedNames);
               computeChange("backward-compatible", cur.isBackwardCompatible(), prev.isBackwardCompatible(), changedNames);
               computeChange("encoding", cur.getEncoding(), prev.getEncoding(), changedNames);
               computeChange("exact-mapping", cur.isExactMapping(), prev.isExactMapping(), changedNames);
               computeChange("default-file-name", cur.getDefaultFileName(), prev.getDefaultFileName(), changedNames);
               computeChange("rtexprvalue-jsp-param-name", cur.isRtexprvalueJspParamName(), prev.isRtexprvalueJspParamName(), changedNames);
               computeChange("debug", cur.isDebug(), prev.isDebug(), changedNames);
               computeChange("compress-html-template", cur.isCompressHtmlTemplate(), prev.isCompressHtmlTemplate(), changedNames);
               computeChange("optimize-java-expression", cur.isOptimizeJavaExpression(), prev.isOptimizeJavaExpression(), changedNames);
               computeChange("resource-provider-class", cur.getResourceProviderClass(), prev.getResourceProviderClass(), changedNames);
               if (!changedNames.isEmpty()) {
                  throw new BeanUpdateRejectedException("Non-Dynamic property in \"jsp-descriptor\" is/are specified in deployment plan: '" + getChangedPropertyNames(changedNames) + "'");
               }
            }
         }

         protected void handleBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            if (newBean instanceof JspDescriptorBean && "JspDescriptors".equals(prop.getPropertyName())) {
               JspDescriptorBean jdb = (JspDescriptorBean)newBean;
               ((DescriptorBean)jdb).addBeanUpdateListener(this);
               if (jdb.isPageCheckSecondsSet()) {
                  JSPManager.this.pageCheckSecondsSet = true;
                  JSPManager.this.pageCheckSeconds = jdb.getPageCheckSeconds();
               }

               Iterator i = JSPManager.this.jspConfigs.iterator();

               while(i.hasNext()) {
                  JspConfig jspConfig = (JspConfig)i.next();
                  jspConfig.setVerbose(jdb.isVerbose());
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "verbose", "" + jdb.isVerbose());
                  jspConfig.setKeepGenerated(jdb.isKeepgenerated());
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "keepgenerated", "" + jdb.isKeepgenerated());
                  jspConfig.setPageCheckSecs((long)jdb.getPageCheckSeconds());
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "pageCheckSeconds", "" + jdb.getPageCheckSeconds());
               }

            }
         }

         protected void handleBeanRemove(BeanUpdateEvent.PropertyUpdate prop) {
            if ("JspDescriptors".equals(prop.getPropertyName()) && prop.getRemovedObject() instanceof JspDescriptorBean) {
               JspDescriptorBean cdb = (JspDescriptorBean)prop.getRemovedObject();
               ((DescriptorBean)cdb).removeBeanUpdateListener(this);
               JSPManager.this.pageCheckSecondsSet = false;
               JSPManager.this.pageCheckSeconds = JSPManager.this.productionMode ? -1 : 1;
               Iterator i = JSPManager.this.jspConfigs.iterator();

               while(i.hasNext()) {
                  JspConfig jspConfig = (JspConfig)i.next();
                  jspConfig.setVerbose(true);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "verbose", "true");
                  jspConfig.setKeepGenerated(false);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "keepgenerated", "false");
                  jspConfig.setPageCheckSecs((long)JSPManager.this.pageCheckSeconds);
                  JSPManager.this.setArg(JSPManager.this.jspcArgs, "pageCheckSeconds", "" + JSPManager.this.pageCheckSeconds);
               }

            }
         }
      };
   }

   static {
      JspFactoryImpl.init();
      JSP_DESC_ELEMENTS_MAP.put("backwardCompatible".toLowerCase(), "backward-compatible");
      JSP_DESC_ELEMENTS_MAP.put("encoding".toLowerCase(), "encoding");
      JSP_DESC_ELEMENTS_MAP.put("exactMapping".toLowerCase(), "exact-mapping");
      JSP_DESC_ELEMENTS_MAP.put("keepgenerated".toLowerCase(), "keepgenerated");
      JSP_DESC_ELEMENTS_MAP.put("packagePrefix".toLowerCase(), "package-prefix");
      JSP_DESC_ELEMENTS_MAP.put("pageCheckSeconds".toLowerCase(), "page-check-seconds");
      JSP_DESC_ELEMENTS_MAP.put("precompile".toLowerCase(), "precompile");
      JSP_DESC_ELEMENTS_MAP.put("precompileContinue".toLowerCase(), "precompile-continue");
      JSP_DESC_ELEMENTS_MAP.put("printNulls".toLowerCase(), "print-nulls");
      JSP_DESC_ELEMENTS_MAP.put("superclass".toLowerCase(), "super-class");
      JSP_DESC_ELEMENTS_MAP.put("verbose".toLowerCase(), "verbose");
      JSP_DESC_ELEMENTS_MAP.put("workingDir".toLowerCase(), "working-dir");
      JSP_DESC_ELEMENTS_MAP.put("compileCommand".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("compilerClass".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("compileFlags".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("compilerSupportsEncoding".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("defaultFileName".toLowerCase(), "default-file-name");
      JSP_DESC_ELEMENTS_MAP.put("noTryBlocks".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("jspServlet".toLowerCase(), "warning");
      JSP_DESC_ELEMENTS_MAP.put("jspPrecompiler".toLowerCase(), "warning");
   }
}
