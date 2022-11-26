package weblogic.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.descriptor.MimeMappingBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WelcomeFileListBean;
import weblogic.j2ee.descriptor.wl.AsyncDescriptorBean;
import weblogic.j2ee.descriptor.wl.CharsetMappingBean;
import weblogic.j2ee.descriptor.wl.CharsetParamsBean;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.FastSwapBean;
import weblogic.j2ee.descriptor.wl.GzipCompressionBean;
import weblogic.j2ee.descriptor.wl.InputCharsetBean;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.j2ee.descriptor.wl.VirtualDirectoryMappingBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.logging.j2ee.ServletContextLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.security.internal.RefererValidationType;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.http.HttpConstants;
import weblogic.utils.http.HttpParsing;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class WebAppConfigManager implements RealmNameAware, StaleProber {
   public static final boolean WIN_32;
   public static final char FSC;
   private static final String WL_USE_VM_ENCODING = "webapp.encoding.usevmdefault";
   private static final String WL_DEFAULT_ENCODING = "webapp.encoding.default";
   private static final String WL_RP_COMPAT_SWITCH = "webapp.getrealpath.accept_context_path";
   private static final String JSF_RI_DI_SPI = "com.sun.faces.injectionProvider";
   private static final String WL_JSF_RI_DI_IMPL = "com.bea.faces.WeblogicInjectionProvider";
   private static final long DEFAULT_MIN_COMPRESSION_CONTENT_LENGTH = 2048L;
   private static final String[] DEFAULT_INCLUDE_COMPRESSION_CONTENT_TYPE;
   private static final WebServerRegistry registry;
   static final HttpServerManager httpServerManager;
   private static final ManagementProvider mgmtProvider;
   private static final SecurityProvider securityProvider;
   private static final ServerMBean servermbean;
   private static final DomainMBean domainmbean;
   private static final WebAppContainerMBean webAppContainer;
   private static final boolean useExtendedSessionFormat;
   private static final boolean logInternalAppAccess;
   private static Set redefinableWebApps;
   private final ConcurrentHashMap mimeMapping = new ConcurrentHashMap();
   private WebAppComponentMBean compMBean = null;
   private boolean checkAuthOnForwardEnabled;
   private boolean filterDispatchedRequestsEnabled = true;
   private boolean preferWebInfClasses;
   private boolean clientCertProxyEnabled;
   private boolean redirectWithAbsoluteURLEnabled = true;
   private boolean sendPermanentRedirects = false;
   private boolean encodingEnabled;
   private int singleThreadedServletPoolSize = 5;
   private String authRealmName = "WebLogic Server";
   private String defaultMimeType;
   private String defaultEncoding = "ISO-8859-1";
   private WorkManager workManager;
   private int asyncTimeoutSecs = 30;
   private int asyncTimeoutCheckIntervalSecs = 10;
   private WorkManager asyncWorkManager;
   private boolean reloginEnabled;
   private boolean allowAllRoles;
   private boolean nativeIOEnabled;
   private boolean rtexprvalueJspParamName;
   private boolean isJSPCompilerBackwardsCompatible;
   private boolean failDeployOnFilterInitError = true;
   private final HashMap inputEncodingMap = new HashMap();
   private LocaleEncodingMap localeEncodingMap = new LocaleEncodingMap();
   private int servletReloadCheckSecs = getDefaultReloadSecs();
   private int resourceReloadCheckSecs = getDefaultReloadSecs();
   private boolean servletReloadCheckSecsSet = false;
   private boolean optimisticSerialization;
   private boolean retainOriginalURL;
   private boolean servletAuthFromURL;
   private boolean indexDirectoryEnabled = false;
   private boolean indexDirectoryEnabledSet = false;
   private String indexDirectorySortBy = "NAME";
   private long minimumNativeFileSize = 4096L;
   private boolean showArchivedRealPathEnabledSet = false;
   private boolean showArchivedRealPathEnabled = false;
   private boolean accessLoggingDisabled = false;
   private boolean preferForwardQueryStringSet = false;
   private boolean preferForwardQueryString = false;
   private String tempDir = null;
   private boolean disableImplicitServletMappings = false;
   private boolean requireAdminTraffic = false;
   private String docroot;
   private final Map contextParams = new HashMap();
   private Map virtualDirectoryMappings = new HashMap();
   private ArrayList indexFiles = new ArrayList();
   private Map charsetMap = new HashMap();
   private ServletContextLogger servletContextLogger;
   private boolean saveSessionsEnabled;
   private boolean fastSwapEnabled;
   private boolean gzipCompressionEnabled;
   private boolean gzipCompressionEnabledSet = false;
   private long minCompressionContentLength;
   private boolean minCompressionContentLengthSet = false;
   private String[] includeCompressionContentType;
   private boolean includeCompressionContentTypeSet = false;
   private RefererValidationType refererValidationType;
   private boolean registerFastSwapFilter;
   private int fastSwapRefreshInterval;
   private int fastSwapRedefinitionTaskLimit;
   private final BeanUpdateListener containerBeanListener;
   private WebAppModule module;

   WebAppConfigManager(WebAppModule module, WebAppComponentMBean compMBean) {
      this.refererValidationType = RefererValidationType.LENIENT;
      this.module = module;
      this.compMBean = compMBean;
      this.containerBeanListener = this.createBeanUpdateListener();
   }

   void init() throws ModuleException {
      this.initMimeMapping();
      this.initEncoding();
      this.initPluginSecurity();
      this.initSwitches();
      this.initDocRoot();
      this.initFromWlsDescriptors();
   }

   void prepare() {
      this.initWorkManager();
      this.initAsyncWorkManager();
      this.initFromWebAppDescriptors();
   }

   private void initFromWlsDescriptors() {
      this.registerContainerDescriptors();
      this.registerFastSwapDescriptor();
      this.registerRefererValidationTypeDescritpror();
      this.registerCharsetMap();
      this.registerInputEncodings();
      this.registerServletContextLogger();
      this.registerVirtualDirectoryMappings();
   }

   private void initFromWebAppDescriptors() {
      this.registerMimeMappings();
      this.registerLocaleEncodingMap();
      this.registerContextParams();
      this.registerWelcomeFiles();
   }

   BeanUpdateListener getBeanUpdateListener() {
      return this.containerBeanListener;
   }

   String getLogContext() {
      return this.module.getLogContext();
   }

   boolean isAcceptContextPathInGetRealPath(HttpServer httpServer) {
      String flag = this.module.getApplicationContext().getApplicationParameter("webapp.getrealpath.accept_context_path");
      return flag != null && flag.equalsIgnoreCase("true") ? true : httpServer.isAcceptContextPathInGetRealPath();
   }

   private void registerCharsetMap() {
      CharsetMappingBean[] mappings = this.getCharsetParamsBeans();
      if (mappings != null) {
         CharsetMappingBean[] var2 = mappings;
         int var3 = mappings.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CharsetMappingBean mapping = var2[var4];
            String ianaName = mapping.getIanaCharsetName();
            String javaName = mapping.getJavaCharsetName();
            this.charsetMap.put(ianaName, javaName);
         }
      }

   }

   boolean isOldDescriptor() {
      if (this.module == null) {
         return false;
      } else {
         WebAppBean bean = this.module.getWebAppBean();
         if (bean == null) {
            return false;
         } else {
            String version = ((DescriptorBean)bean).getDescriptor().getOriginalVersionInfo();
            return "DTD".equals(version);
         }
      }
   }

   public String getDefaultContextPath() {
      String[] dcp = this.module.getWebAppBean().getDefaultContextPaths();
      return dcp != null && dcp.length > 0 && !dcp[0].endsWith("/") ? dcp[0] : null;
   }

   Map getContextParams() {
      return new HashMap(this.contextParams);
   }

   private void initSwitches() {
      this.reloginEnabled = webAppContainer.isReloginEnabled();
      this.allowAllRoles = webAppContainer.isAllowAllRoles();
      if (webAppContainer.isSet("FilterDispatchedRequestsEnabled")) {
         this.filterDispatchedRequestsEnabled = webAppContainer.isFilterDispatchedRequestsEnabled();
      } else {
         this.filterDispatchedRequestsEnabled = this.isOldDescriptor();
      }

      this.optimisticSerialization = Boolean.getBoolean("weblogic.servlet.optimisticSerialization") || webAppContainer.isOptimisticSerialization();
      this.retainOriginalURL = webAppContainer.isRetainOriginalURL();
      this.servletAuthFromURL = webAppContainer.isServletAuthenticationFormURL();
      this.rtexprvalueJspParamName = webAppContainer.isRtexprvalueJspParamName();
      this.isJSPCompilerBackwardsCompatible = webAppContainer.isJSPCompilerBackwardsCompatible();
      if (this.compMBean != null) {
         this.singleThreadedServletPoolSize = this.compMBean.getSingleThreadedServletPoolSize();
         this.preferWebInfClasses = this.compMBean.isPreferWebInfClasses();
         this.authRealmName = this.compMBean.getAuthRealmName();
      }

      this.fastSwapEnabled = redefinableWebApps.contains(this.module.getId());
   }

   private void registerContextParams() {
      if (this.module.isJsfApplication()) {
         this.contextParams.put("com.sun.faces.injectionProvider", "com.bea.faces.WeblogicInjectionProvider");
      }

      if (this.module.getWebAppBean() != null) {
         ParamValueBean[] params = this.module.getWebAppBean().getContextParams();
         if (params != null) {
            for(int i = 0; i < params.length; ++i) {
               this.contextParams.put(params[i].getParamName(), params[i].getParamValue());
            }
         }
      }

   }

   private void initPluginSecurity() {
      ClusterMBean clustermbean = servermbean.getCluster();
      this.clientCertProxyEnabled = webAppContainer.isClientCertProxyEnabled();
      if (clustermbean != null && clustermbean.isSet("ClientCertProxyEnabled")) {
         this.clientCertProxyEnabled = clustermbean.isClientCertProxyEnabled();
      }

      if (servermbean.isSet("ClientCertProxyEnabled")) {
         this.clientCertProxyEnabled = servermbean.isClientCertProxyEnabled();
      }

   }

   private void initEncoding() {
      String enc = null;
      ApplicationContextInternal appCtx = this.module.getApplicationContext();
      if ("true".equalsIgnoreCase(appCtx.getApplicationParameter("webapp.encoding.usevmdefault"))) {
         enc = System.getProperty("file.encoding");
      } else {
         enc = appCtx.getApplicationParameter("webapp.encoding.default");
      }

      if (enc != null) {
         if (Charset.isSupported(enc)) {
            this.defaultEncoding = enc;
            this.encodingEnabled = true;
         } else {
            HTTPLogger.logUnsupportedEncoding(this.getLogContext(), enc, new UnsupportedEncodingException("Unsupported encoding " + enc));
         }

      }
   }

   private void registerServletContextLogger() {
      LoggingBean logging = null;
      if (this.module.getWlWebAppBean() != null && this.module.getWlWebAppBean().getLoggings().length > 0) {
         logging = this.module.getWlWebAppBean().getLoggings()[0];
      }

      if (logging != null && !logging.isLoggingEnabled()) {
         this.servletContextLogger = null;
      } else {
         this.servletContextLogger = new ServletContextLogger("ServletContext-" + this.module.getContextPath(), logging);
      }

   }

   private void addInputEncoding(String url, String enc) {
      url = HttpParsing.ensureStartingSlash(url);
      url = StringUtils.endsWith(url, '*') ? url.substring(0, url.length() - 1) : url;
      url = StringUtils.endsWith(url, '/') ? url.substring(0, url.length() - 1) : url;
      this.inputEncodingMap.put(url, enc);
   }

   private void registerLocaleEncodingMap() {
      this.localeEncodingMap.registerLocaleEncodingMap(this.module.getWebAppBean().getLocaleEncodingMappingLists());
   }

   private void registerVirtualDirectoryMappings() {
      WeblogicWebAppBean wlWebBean = this.module.getWlWebAppBean();
      if (wlWebBean != null) {
         VirtualDirectoryMappingBean[] vdm = wlWebBean.getVirtualDirectoryMappings();
         if (vdm != null && vdm.length >= 1) {
            for(int i = 0; i < vdm.length; ++i) {
               String localPath = vdm[i].getLocalPath();
               if (localPath != null && localPath.length() >= 1) {
                  if (!isAbsoluteFilePath(localPath)) {
                     File f = new File(this.getDocRoot() + FSC + localPath);
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPDebugLogger.debug(this.getLogContext() + ":The localPath is :" + localPath);
                        HTTPDebugLogger.debug(this.getLogContext() + ":docRoot is :" + this.getDocRoot());
                     }

                     try {
                        String warLocalPath = f.getCanonicalPath();
                        f = new File(warLocalPath);
                        if (f.exists()) {
                           localPath = warLocalPath;
                        } else {
                           f = new File(localPath);
                           localPath = f.getCanonicalPath();
                           if (HTTPDebugLogger.isEnabled()) {
                              HTTPDebugLogger.debug(this.getLogContext() + ": local path is relative to the rootDir. It is :" + f);
                           }
                        }
                     } catch (IOException var11) {
                        HTTPLogger.logInvalidVirtualDirectoryPath(this.getLogContext(), localPath, this.getDocRoot(), var11);
                        continue;
                     }
                  }

                  String[] patterns = vdm[i].getUrlPatterns();
                  if (patterns != null && patterns.length >= 1) {
                     Object patternList;
                     if (this.virtualDirectoryMappings.get(localPath) != null) {
                        patternList = (List)this.virtualDirectoryMappings.get(localPath);
                     } else {
                        patternList = new ArrayList();
                     }

                     String[] var7 = patterns;
                     int var8 = patterns.length;

                     for(int var9 = 0; var9 < var8; ++var9) {
                        String pattern = var7[var9];
                        if (pattern != null) {
                           pattern = WebAppSecurity.fixupURLPattern(pattern);
                           ((List)patternList).add(pattern);
                           this.virtualDirectoryMappings.put(localPath, patternList);
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private void registerWelcomeFiles() {
      WelcomeFileListBean[] wfb = this.module.getWebAppBean().getWelcomeFileLists();
      ArrayList list = new ArrayList();
      if (wfb != null && wfb.length > 0) {
         for(int i = 0; i < wfb.length; ++i) {
            String[] wfl = wfb[i].getWelcomeFiles();
            if (wfl != null) {
               for(int k = 0; k < wfl.length; ++k) {
                  list.add(wfl[k]);
               }
            }
         }
      } else if (this.compMBean != null) {
         String[] wfFromMBean = this.compMBean.getIndexFiles();
         if (wfFromMBean != null) {
            for(int i = 0; i < wfFromMBean.length; ++i) {
               list.add(wfFromMBean[i]);
            }
         }
      }

      ArrayList newIndexFiles = new ArrayList();

      String item;
      for(Iterator var9 = list.iterator(); var9.hasNext(); newIndexFiles.add(item)) {
         item = (String)var9.next();
         if (item.length() > 0 && item.charAt(0) == '/') {
            item = item.substring(1);
         }
      }

      this.indexFiles = newIndexFiles;
   }

   private void registerFastSwapDescriptor() {
      FastSwapBean fsBean = null;
      WeblogicWebAppBean weblogicWebAppBean = this.module.getWlWebAppBean();
      if (weblogicWebAppBean != null) {
         fsBean = weblogicWebAppBean.getFastSwap();
      }

      WeblogicApplicationBean wlAppBean = this.module.getApplicationContext().getWLApplicationDD();
      if (fsBean == null && wlAppBean != null) {
         fsBean = wlAppBean.getFastSwap();
      }

      if (fsBean != null && fsBean.isEnabled()) {
         this.fastSwapEnabled = true;
         this.registerFastSwapFilter = true;
         this.fastSwapRefreshInterval = fsBean.getRefreshInterval();
         this.fastSwapRedefinitionTaskLimit = fsBean.getRedefinitionTaskLimit();
      }

   }

   private void registerGzipCompressionDescriptor(ContainerDescriptorBean cd) {
      GzipCompressionBean gzipBean = null;
      if (cd != null) {
         gzipBean = cd.getGzipCompression();
      }

      if (gzipBean != null) {
         if (gzipBean.isEnabledSet()) {
            this.gzipCompressionEnabledSet = true;
            this.gzipCompressionEnabled = gzipBean.isEnabled();
         }

         if (gzipBean.isMinContentLengthSet()) {
            this.minCompressionContentLengthSet = true;
            this.minCompressionContentLength = gzipBean.getMinContentLength();
         }

         if (gzipBean.isContentTypeSet()) {
            this.includeCompressionContentTypeSet = true;
            this.includeCompressionContentType = gzipBean.getContentType();
         }
      }

   }

   private void registerRefererValidationTypeDescritpror() {
      WeblogicWebAppBean weblogicWebAppBean = this.module.getWlWebAppBean();
      if (weblogicWebAppBean != null) {
         ContainerDescriptorBean cd = (ContainerDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(weblogicWebAppBean, weblogicWebAppBean.getContainerDescriptors(), "ContainerDescriptor");
         this.refererValidationType = RefererValidationType.valueOf(cd.getRefererValidation());
      }
   }

   private void registerContainerDescriptors() {
      WeblogicWebAppBean weblogicWebAppBean = this.module.getWlWebAppBean();
      this.accessLoggingDisabled = !logInternalAppAccess && this.module.isInternalApp();
      if (weblogicWebAppBean != null) {
         ContainerDescriptorBean cd = (ContainerDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(weblogicWebAppBean, weblogicWebAppBean.getContainerDescriptors(), "ContainerDescriptor");
         this.checkAuthOnForwardEnabled = cd.getCheckAuthOnForward() != null;
         this.redirectWithAbsoluteURLEnabled = cd.isRedirectWithAbsoluteUrl();
         this.nativeIOEnabled = cd.isNativeIOEnabled();
         this.sendPermanentRedirects = cd.isSendPermanentRedirects();
         DescriptorBean db = (DescriptorBean)cd;
         if (db.isSet("FilterDispatchedRequestsEnabled")) {
            this.filterDispatchedRequestsEnabled = cd.isFilterDispatchedRequestsEnabled();
         }

         if (db.isSet("PreferWebInfClasses")) {
            this.preferWebInfClasses = cd.isPreferWebInfClasses();
         }

         if (db.isSet("SingleThreadedServletPoolSize")) {
            this.singleThreadedServletPoolSize = cd.getSingleThreadedServletPoolSize();
         }

         if (db.isSet("ClientCertProxyEnabled")) {
            this.clientCertProxyEnabled = cd.isClientCertProxyEnabled();
         }

         if (db.isSet("ReloginEnabled")) {
            this.reloginEnabled = cd.isReloginEnabled();
         }

         if (db.isSet("AllowAllRoles")) {
            this.allowAllRoles = cd.isAllowAllRoles();
         }

         if (db.isSet("OptimisticSerialization")) {
            this.optimisticSerialization = cd.isOptimisticSerialization();
         }

         if (db.isSet("RetainOriginalURL")) {
            this.retainOriginalURL = cd.isRetainOriginalURL();
         }

         if (cd.getDefaultMimeType() != null && !cd.getDefaultMimeType().equals("")) {
            this.defaultMimeType = cd.getDefaultMimeType();
         }

         if (cd.isServletReloadCheckSecsSet()) {
            this.servletReloadCheckSecsSet = true;
            this.servletReloadCheckSecs = cd.getServletReloadCheckSecs();
         }

         this.resourceReloadCheckSecs = cd.getResourceReloadCheckSecs();
         if (cd.isIndexDirectoryEnabledSet()) {
            this.indexDirectoryEnabledSet = true;
            this.indexDirectoryEnabled = cd.isIndexDirectoryEnabled();
         }

         this.indexDirectorySortBy = cd.getIndexDirectorySortBy();
         if (cd.isShowArchivedRealPathEnabledSet()) {
            this.showArchivedRealPathEnabledSet = true;
            this.showArchivedRealPathEnabled = cd.isShowArchivedRealPathEnabled();
         }

         if (cd.isAccessLoggingDisabledSet()) {
            this.accessLoggingDisabled = cd.isAccessLoggingDisabled();
         }

         this.registerGzipCompressionDescriptor(cd);
         this.preferForwardQueryString = cd.isPreferForwardQueryString();
         this.saveSessionsEnabled = cd.isSaveSessionsEnabled();
         this.minimumNativeFileSize = cd.getMinimumNativeFileSize();
         this.tempDir = cd.getTempDir();
         this.disableImplicitServletMappings = cd.isDisableImplicitServletMappings();
         this.requireAdminTraffic = cd.isRequireAdminTraffic();
         this.failDeployOnFilterInitError = cd.getFailDeployOnFilterInitError();
      }
   }

   private CharsetMappingBean[] getCharsetParamsBeans() {
      CharsetParamsBean cpmb = this.getCharsetParamsBean();
      return cpmb == null ? null : cpmb.getCharsetMappings();
   }

   private void registerInputEncodings() {
      CharsetParamsBean cpmb = this.getCharsetParamsBean();
      if (cpmb != null) {
         InputCharsetBean[] icdmb = cpmb.getInputCharsets();
         if (icdmb != null) {
            for(int i = 0; i < icdmb.length; ++i) {
               String p = icdmb[i].getResourcePath();
               String c = icdmb[i].getJavaCharsetName();
               if (p != null && c != null) {
                  this.addInputEncoding(p, c);
               }
            }

         }
      }
   }

   private CharsetParamsBean getCharsetParamsBean() {
      WeblogicWebAppBean wlWebAppBean = this.module.getWlWebAppBean();
      return wlWebAppBean == null ? null : (CharsetParamsBean)DescriptorUtils.getFirstChildOrDefaultBean(wlWebAppBean, wlWebAppBean.getCharsetParams(), "CharsetParams");
   }

   public LocaleEncodingMap getLocaleEncodingMap() {
      return this.localeEncodingMap;
   }

   public String getRequestCharacterEncoding() {
      String[] encodings = this.module.getWebAppBean().getRequestCharacterEncodings();
      return encodings != null && encodings.length > 0 ? encodings[0] : null;
   }

   public String getResponseCharacterEncoding() {
      String[] encodings = this.module.getWebAppBean().getResponseCharacterEncodings();
      return encodings != null && encodings.length > 0 ? encodings[0] : null;
   }

   public HashMap getInputEncodings() {
      return this.inputEncodingMap;
   }

   public String getDefaultEncoding() {
      return this.defaultEncoding;
   }

   public boolean useDefaultEncoding() {
      return this.encodingEnabled;
   }

   public boolean isRedirectWithAbsoluteURLEnabled() {
      return this.redirectWithAbsoluteURLEnabled;
   }

   public boolean isSendPermanentRedirects() {
      return this.sendPermanentRedirects;
   }

   public boolean isPreferWebInfClasses() {
      return this.preferWebInfClasses;
   }

   public boolean isShowArchivedRealPathEnabled() {
      if (this.showArchivedRealPathEnabledSet) {
         return this.showArchivedRealPathEnabled;
      } else {
         return webAppContainer != null ? webAppContainer.isShowArchivedRealPathEnabled() : false;
      }
   }

   public boolean isCheckAuthOnForwardEnabled() {
      return this.checkAuthOnForwardEnabled;
   }

   public boolean isIndexDirectoryEnabled() {
      if (this.indexDirectoryEnabledSet) {
         return this.indexDirectoryEnabled;
      } else {
         return this.compMBean != null ? this.compMBean.isIndexDirectoryEnabled() : false;
      }
   }

   public String getIndexDirectorySortBy() {
      return this.indexDirectorySortBy;
   }

   public boolean isReloginEnabled() {
      return this.reloginEnabled;
   }

   public boolean isAllowAllRoles() {
      return this.allowAllRoles;
   }

   public boolean isNativeIOEnabled() {
      return this.nativeIOEnabled;
   }

   public boolean isOptimisticSerialization() {
      return this.optimisticSerialization;
   }

   public boolean isRetainOriginalURL() {
      return this.retainOriginalURL;
   }

   public boolean isServletAuthFromURL() {
      return this.servletAuthFromURL;
   }

   public boolean isRtexprvalueJspParamName() {
      return this.rtexprvalueJspParamName;
   }

   public boolean isJSPCompilerBackwardsCompatible() {
      return this.isJSPCompilerBackwardsCompatible;
   }

   public boolean isFailDeployOnFilterInitError() {
      return this.failDeployOnFilterInitError;
   }

   public boolean isSaveSessionsEnabled() {
      return this.saveSessionsEnabled;
   }

   public boolean isAccessLoggingDisabled() {
      return this.accessLoggingDisabled;
   }

   public List getWelcomeFiles() {
      return this.indexFiles;
   }

   public Map getCharsetMap() {
      return this.charsetMap;
   }

   public String getDisplayName() {
      String[] ds = this.module.getWebAppBean().getDisplayNames();
      return ds != null && ds.length > 0 ? ds[0] : null;
   }

   public String getAuthFilter() {
      WeblogicWebAppBean wlwab = this.module.getWlWebAppBean();
      return wlwab != null && wlwab.getAuthFilters().length != 0 ? wlwab.getAuthFilters()[0] : null;
   }

   public static boolean useExtendedSessionFormat() {
      return useExtendedSessionFormat;
   }

   public long getMinimumNativeFileSize() {
      return this.minimumNativeFileSize;
   }

   public boolean isPreferForwardQueryString() {
      return this.preferForwardQueryString;
   }

   public boolean isRequireAdminTraffic() {
      return this.requireAdminTraffic;
   }

   public boolean isFilterDispatchedRequestsEnabled() {
      return this.filterDispatchedRequestsEnabled;
   }

   public int getServletReloadCheckSecs() {
      if (!this.module.isServletReloadAllowed()) {
         return -1;
      } else if (this.servletReloadCheckSecsSet) {
         return this.servletReloadCheckSecs;
      } else {
         return webAppContainer.isServletReloadCheckSecsSet() ? webAppContainer.getServletReloadCheckSecs() : getDefaultReloadSecs();
      }
   }

   public int getResourceReloadCheckSecs(String name) {
      int reloadSecs = getDefaultReloadSecs();
      if (this.isResourceJSP(name)) {
         if (this.module.getJspManager().isPageCheckSecondsSet()) {
            reloadSecs = this.module.getJspManager().getPageCheckSeconds();
         }
      } else {
         reloadSecs = this.resourceReloadCheckSecs;
      }

      return reloadSecs;
   }

   public int getSingleThreadedServletPoolSize() {
      return this.singleThreadedServletPoolSize;
   }

   private boolean isResourceJSP(String name) {
      int index = name.indexOf(46);
      if (index == -1) {
         return false;
      } else {
         String suffix = name.substring(index + 1, name.length());
         return suffix.equalsIgnoreCase("jsp") || suffix.equalsIgnoreCase("jspx");
      }
   }

   private void initDocRoot() throws ModuleException {
      String docrootName = this.module.getWarPath();
      if (docrootName == null) {
         Loggable l = HTTPLogger.logNullDocumentRootLoggable(this.getLogContext());
         l.log();
         throw new ModuleException(l.toString());
      } else {
         File root = new File(docrootName);
         String path = docrootName;
         if (!root.isAbsolute() && !root.exists()) {
            ServerMBean servermbean = registry.getServerMBean();
            ClusterMBean cluster = servermbean.getCluster();
            if (cluster != null) {
               path = docrootName + cluster.getName();
            }

            if (path == null) {
               path = servermbean.getName();
            }

            path = servermbean.getRootDirectory() + File.separator + path + File.separator + docrootName;
            root = new File(path);
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug(this.getLogContext() + ": document root doesn't exist for " + this.getLogContext() + " looking under relative path: " + root.getAbsolutePath());
            }
         }

         if (!root.exists()) {
            Loggable l = HTTPLogger.logNoDocRootLoggable(this.getLogContext(), path);
            l.log();
            throw new ModuleException(l.getMessage());
         } else {
            try {
               this.docroot = root.getCanonicalPath();
            } catch (IOException var6) {
               Loggable l = HTTPLogger.logErrorSettingDocumentRootLoggable(this.getLogContext(), docrootName, var6);
               l.log();
               throw new ModuleException(l.getMessage(), var6);
            }
         }
      }
   }

   public String getDocRoot() {
      return this.docroot;
   }

   public String getDefaultMimeType() {
      return this.defaultMimeType;
   }

   public String getAuthRealmName() {
      return this.authRealmName;
   }

   public void setAuthRealmName(String s) {
      this.authRealmName = s;
   }

   public boolean isClientCertProxyEnabled() {
      return this.clientCertProxyEnabled;
   }

   public int getAsyncTimeoutSecs() {
      return this.asyncTimeoutSecs;
   }

   public int getAsyncTimeoutCheckIntervalSecs() {
      return this.asyncTimeoutCheckIntervalSecs;
   }

   public WorkManager getAsyncWorkManager() {
      return this.asyncWorkManager;
   }

   Map getVirtualDirectoryMappings() {
      return this.virtualDirectoryMappings;
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   File getPublicTempDir() {
      File publicTempDir = null;
      if (this.tempDir == null) {
         publicTempDir = new File(this.getInternalTempDir(), "public");
      } else if (isAbsoluteFilePath(this.tempDir)) {
         publicTempDir = new File(this.tempDir);
      } else {
         publicTempDir = new File(this.getInternalTempDir(), this.tempDir);
      }

      if (!publicTempDir.exists() && !publicTempDir.mkdirs()) {
         HTTPLogger.logUnableToMakeDirectory(this.getLogContext(), publicTempDir.getAbsolutePath());
      }

      return publicTempDir;
   }

   File getInternalTempDir() {
      return this.module.getRootTempDir();
   }

   String getTempPath() {
      return this.module.getTempPath();
   }

   private void initAsyncWorkManager() {
      String wmName = null;
      WeblogicWebAppBean wlbean = this.module.getWlWebAppBean();
      if (wlbean != null) {
         AsyncDescriptorBean[] adbs = wlbean.getAsyncDescriptors();
         if (adbs != null && adbs.length > 0) {
            this.asyncTimeoutSecs = adbs[0].getTimeoutSecs();
            this.asyncTimeoutCheckIntervalSecs = adbs[0].getTimeoutCheckIntervalSecs();
            wmName = adbs[0].getAsyncWorkManager();
         }
      }

      if (wmName != null) {
         this.asyncWorkManager = WorkManagerFactory.getInstance().find(wmName, this.module.getApplicationContext().getApplicationId(), this.module.getId());
      }

      if (this.asyncWorkManager == null) {
         this.asyncWorkManager = this.workManager;
      }

      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": Async Descriptor: timeout=" + this.asyncTimeoutSecs + "s, check-interval=" + this.asyncTimeoutCheckIntervalSecs + "s.");
         if (this.asyncWorkManager != this.workManager) {
            HTTPDebugLogger.debug(this.getLogContext() + ": Use named work manager \"" + wmName + "\" for asynchronous jobs.");
         } else if (wmName != null && wmName.length() != 0) {
            HTTPDebugLogger.debug(this.getLogContext() + ": Can not find the  named work manager \"" + wmName + "\" for asynchronous jobs. Use the system default work manager.");
         } else {
            HTTPDebugLogger.debug(this.getLogContext() + ": No work manager is specified for asychronous jobs. Use the system default work manger.");
         }
      }

   }

   private void initWorkManager() {
      WeblogicWebAppBean wlBean = this.module.getWlWebAppBean();
      String dispatchPolicy = "weblogic.kernel.Default";
      if (wlBean != null && wlBean.getWlDispatchPolicies() != null && wlBean.getWlDispatchPolicies().length > 0) {
         dispatchPolicy = wlBean.getWlDispatchPolicies()[0];
      }

      this.workManager = WorkManagerFactory.getInstance().find(dispatchPolicy, this.module.getApplicationContext().getApplicationId(), this.module.getId());
   }

   boolean isImplicitServletMappingDisabled() {
      return this.disableImplicitServletMappings;
   }

   private void initMimeMapping() {
      for(int i = 0; i < HttpConstants.DEFAULT_MIME_MAPPINGS.length; ++i) {
         this.addMimeMapping(HttpConstants.DEFAULT_MIME_MAPPINGS[i][0], HttpConstants.DEFAULT_MIME_MAPPINGS[i][1]);
      }

      if (webAppContainer.getMimeMappingFile() != null) {
         String name = webAppContainer.getMimeMappingFile();
         name = name.replace('\\', File.separatorChar);
         name = name.replace('/', File.separatorChar);
         if (!isAbsoluteFilePath(name)) {
            name = mgmtProvider.getDomainRootDir() + File.separatorChar + name;
         }

         this.addMimeMappings(new File(name));
      }

      if (this.compMBean != null) {
         Map map = this.compMBean.getMimeTypes();
         if (map != null) {
            Iterator i = map.keySet().iterator();

            while(i.hasNext()) {
               String ext = (String)i.next();
               String type = (String)map.get(ext);
               this.addMimeMapping(ext, type);
            }
         }
      }

   }

   private void registerMimeMappings() {
      if (this.module != null && this.module.getWebAppBean() != null) {
         MimeMappingBean[] mm = this.module.getWebAppBean().getMimeMappings();
         if (mm != null) {
            for(int i = 0; i < mm.length; ++i) {
               MimeMappingBean mb = mm[i];
               this.addMimeMapping(mb.getExtension(), mb.getMimeType());
            }
         }
      }

   }

   private void addMimeMappings(File file) {
      if (file.exists()) {
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(file);
            Properties p = new Properties();
            p.load(fis);
            Enumeration e = p.propertyNames();

            while(e.hasMoreElements()) {
               String extn = (String)e.nextElement();
               String type = p.getProperty(extn);
               if (extn != null && extn.length() > 0) {
                  this.addMimeMapping(extn, type);
               }
            }
         } catch (IOException var15) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Failed to load mime-mappings from domain", var15);
            }
         } finally {
            try {
               if (fis != null) {
                  fis.close();
               }
            } catch (IOException var14) {
            }

         }

      }
   }

   void addMimeMapping(String extension, String mimeType) {
      extension = extension.trim().toLowerCase();
      if (mimeType == null) {
         this.mimeMapping.remove(extension);
      } else {
         mimeType = mimeType.trim();
         if (mimeType.length() == 0) {
            this.mimeMapping.remove(extension);
         } else {
            this.mimeMapping.put(extension, mimeType);
         }
      }
   }

   String getRootTempDirPath() {
      File tmpDir = this.module.getRootTempDir();
      String tmpPath = tmpDir.getAbsolutePath();
      if (tmpPath == null) {
         tmpPath = ".";
      }

      return tmpPath;
   }

   String getMimeType(String file) {
      int lastDot = file.lastIndexOf(46);
      String ext;
      if (lastDot != -1) {
         ext = file.substring(lastDot + 1).toLowerCase();
      } else {
         ext = file;
      }

      String type = (String)this.mimeMapping.get(ext);
      return type == null ? this.getDefaultMimeType() : type;
   }

   static int getDefaultReloadSecs() {
      return registry.isProductionMode() ? -1 : 1;
   }

   public static boolean isCaseInsensitive() {
      return securityProvider.areWebAppFilesCaseInsensitive() && WIN_32;
   }

   public static String getRealmAuthMethods() {
      return securityProvider.getRealmAuthMethods();
   }

   public boolean shouldRegisterFastSwapFilter() {
      return this.registerFastSwapFilter;
   }

   public int getFastSwapRefreshInterval() {
      return this.fastSwapRefreshInterval;
   }

   public boolean isFastSwapEnabled() {
      return this.fastSwapEnabled;
   }

   public int getFastSwapRedefinitionTaskLimit() {
      return this.fastSwapRedefinitionTaskLimit;
   }

   public boolean isGzipCompressionEnabled() {
      if (this.gzipCompressionEnabledSet) {
         return this.gzipCompressionEnabled;
      } else {
         return webAppContainer != null ? webAppContainer.getGzipCompression().isGzipCompressionEnabled() : false;
      }
   }

   public long getMinCompressionContentLength() {
      if (this.minCompressionContentLengthSet) {
         return this.minCompressionContentLength;
      } else {
         return webAppContainer != null ? webAppContainer.getGzipCompression().getGzipCompressionMinContentLength() : 2048L;
      }
   }

   private String[] getIncludeCompressionContentTypes() {
      if (this.includeCompressionContentTypeSet) {
         return this.includeCompressionContentType;
      } else {
         return webAppContainer != null ? webAppContainer.getGzipCompression().getGzipCompressionContentType() : DEFAULT_INCLUDE_COMPRESSION_CONTENT_TYPE;
      }
   }

   public boolean isContentTypeCompressable(String contentType) {
      if (contentType == null) {
         return false;
      } else {
         String[] includeContentTypes = this.getIncludeCompressionContentTypes();
         if (includeContentTypes != null) {
            for(int i = 0; i < includeContentTypes.length; ++i) {
               if (contentType.startsWith(includeContentTypes[i])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public RefererValidationType getRefererValidationType() {
      return this.refererValidationType;
   }

   public ServletContextLogger getServletContextLogger() {
      return this.servletContextLogger;
   }

   private static Set initRedefinableWebApps() {
      String webapps = System.getProperty("weblogic.class.redef.webapps");
      return (Set)(webapps == null ? Collections.emptySet() : new HashSet(Arrays.asList(webapps.split(","))));
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new WebComponentBeanUpdateListener() {
         protected void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate prop) {
            String propertyName = prop.getPropertyName();
            if ("ServletReloadCheckSecs".equals(propertyName)) {
               WebAppConfigManager.this.servletReloadCheckSecs = WebAppConfigManager.getDefaultReloadSecs();
               WebAppConfigManager.this.servletReloadCheckSecsSet = false;
            } else if ("ResourceReloadCheckSecs".equals(propertyName)) {
               WebAppConfigManager.this.resourceReloadCheckSecs = WebAppConfigManager.getDefaultReloadSecs();
            } else if ("IndexDirectoryEnabled".equals(propertyName)) {
               WebAppConfigManager.this.indexDirectoryEnabledSet = false;
               WebAppConfigManager.this.indexDirectoryEnabled = false;
            } else if ("IndexDirectorySortBy".equals(propertyName)) {
               WebAppConfigManager.this.indexDirectorySortBy = "NAME";
            } else if ("ShowArchivedRealPathEnabled".equals(propertyName)) {
               WebAppConfigManager.this.showArchivedRealPathEnabledSet = false;
               WebAppConfigManager.this.showArchivedRealPathEnabled = false;
            } else if ("MinimumNativeFileSize".equals(propertyName)) {
               WebAppConfigManager.this.minimumNativeFileSize = 4096L;
            } else if ("TempDir".equals(propertyName)) {
               WebAppConfigManager.this.tempDir = null;
            } else if ("DisableImplicitServletMappings".equals(propertyName)) {
               WebAppConfigManager.this.disableImplicitServletMappings = false;
            } else if ("RequireAdminTraffic".equals(propertyName)) {
               WebAppConfigManager.this.requireAdminTraffic = false;
            } else if ("AccessLoggingDisabled".equals(propertyName)) {
               WebAppConfigManager.this.accessLoggingDisabled = WebAppConfigManager.this.module.isInternalApp();
            } else if ("GzipCompressionEnabled".equals(propertyName)) {
               WebAppConfigManager.this.gzipCompressionEnabledSet = false;
               WebAppConfigManager.this.gzipCompressionEnabled = false;
            } else if ("MinCompressionContentLength".equals(propertyName)) {
               WebAppConfigManager.this.minCompressionContentLengthSet = false;
               WebAppConfigManager.this.minCompressionContentLength = 2048L;
            } else if ("IncludeCompressionContentType".equals(propertyName)) {
               WebAppConfigManager.this.includeCompressionContentTypeSet = false;
               WebAppConfigManager.this.includeCompressionContentType = WebAppConfigManager.DEFAULT_INCLUDE_COMPRESSION_CONTENT_TYPE;
            }

         }

         protected void handlePropertyChange(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            ContainerDescriptorBean cdb = (ContainerDescriptorBean)newBean;
            String propertyName = prop.getPropertyName();
            if ("ServletReloadCheckSecs".equals(propertyName)) {
               WebAppConfigManager.this.servletReloadCheckSecs = cdb.getServletReloadCheckSecs();
               WebAppConfigManager.this.servletReloadCheckSecsSet = true;
            } else if ("ResourceReloadCheckSecs".equals(propertyName)) {
               WebAppConfigManager.this.resourceReloadCheckSecs = cdb.getResourceReloadCheckSecs();
            } else if ("IndexDirectoryEnabled".equals(propertyName)) {
               WebAppConfigManager.this.indexDirectoryEnabledSet = true;
               WebAppConfigManager.this.indexDirectoryEnabled = cdb.isIndexDirectoryEnabled();
            } else if ("IndexDirectorySortBy".equals(propertyName)) {
               WebAppConfigManager.this.indexDirectorySortBy = cdb.getIndexDirectorySortBy();
            } else if ("ShowArchivedRealPathEnabled".equals(propertyName)) {
               WebAppConfigManager.this.showArchivedRealPathEnabledSet = true;
               WebAppConfigManager.this.showArchivedRealPathEnabled = cdb.isShowArchivedRealPathEnabled();
            } else if ("MinimumNativeFileSize".equals(propertyName)) {
               WebAppConfigManager.this.minimumNativeFileSize = cdb.getMinimumNativeFileSize();
            } else if ("TempDir".equals(propertyName)) {
               WebAppConfigManager.this.tempDir = cdb.getTempDir();
            } else if ("DisableImplicitServletMappings".equals(propertyName)) {
               WebAppConfigManager.this.disableImplicitServletMappings = cdb.isDisableImplicitServletMappings();
            } else if ("RequireAdminTraffic".equals(propertyName)) {
               WebAppConfigManager.this.requireAdminTraffic = cdb.isRequireAdminTraffic();
            } else if ("AccessLoggingDisabled".equals(propertyName)) {
               WebAppConfigManager.this.accessLoggingDisabled = cdb.isAccessLoggingDisabled();
            } else if ("GzipCompressionEnabled".equals(propertyName)) {
               WebAppConfigManager.this.gzipCompressionEnabledSet = true;
               WebAppConfigManager.this.gzipCompressionEnabled = cdb.getGzipCompression().isEnabled();
            } else if ("MinCompressionContentLength".equals(propertyName)) {
               WebAppConfigManager.this.minCompressionContentLengthSet = true;
               WebAppConfigManager.this.minCompressionContentLength = cdb.getGzipCompression().getMinContentLength();
            } else if ("IncludeCompressionContentType".equals(propertyName)) {
               WebAppConfigManager.this.includeCompressionContentTypeSet = true;
               WebAppConfigManager.this.includeCompressionContentType = cdb.getGzipCompression().getContentType();
            }

         }

         protected void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) throws BeanUpdateRejectedException {
            if (newBean instanceof ContainerDescriptorBean) {
               WeblogicWebAppBean wlBean = WebAppConfigManager.this.module.getWlWebAppBean();
               ContainerDescriptorBean cur = (ContainerDescriptorBean)newBean;
               ContainerDescriptorBean prev = (ContainerDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(wlBean, wlBean.getContainerDescriptors(), "ContainerDescriptor");
               List changedNames = new ArrayList();
               computeChange("check-auth-on-forward", cur.getCheckAuthOnForward(), prev.getCheckAuthOnForward(), changedNames);
               computeChange("filter-dispatched-requests-enabled", cur.isFilterDispatchedRequestsEnabled(), prev.isFilterDispatchedRequestsEnabled(), changedNames);
               computeChange("redirect-content-type", cur.getRedirectContentType(), prev.getRedirectContentType(), changedNames);
               computeChange("redirect-content", cur.getRedirectContent(), prev.getRedirectContent(), changedNames);
               computeChange("redirect-with-absolute-url", cur.isRedirectWithAbsoluteUrl(), prev.isRedirectWithAbsoluteUrl(), changedNames);
               computeChange("single-threaded-servlet-pool-size", cur.getSingleThreadedServletPoolSize(), prev.getSingleThreadedServletPoolSize(), changedNames);
               computeChange("save-sessions-enabled", cur.isSaveSessionsEnabled(), prev.isSaveSessionsEnabled(), changedNames);
               computeChange("prefer-web-inf-classes", cur.isPreferWebInfClasses(), prev.isPreferWebInfClasses(), changedNames);
               computeChange("default-mime-type", cur.getDefaultMimeType(), prev.getDefaultMimeType(), changedNames);
               computeChange("relogin-enabled", cur.isReloginEnabled(), prev.isReloginEnabled(), changedNames);
               computeChange("allow-all-roles", cur.isAllowAllRoles(), prev.isAllowAllRoles(), changedNames);
               computeChange("client-cert-proxy-enabled", cur.isClientCertProxyEnabled(), prev.isClientCertProxyEnabled(), changedNames);
               computeChange("native-io-enabled", cur.isNativeIOEnabled(), prev.isNativeIOEnabled(), changedNames);
               computeChange("disable-implicit-servlet-mappings", cur.isDisableImplicitServletMappings(), prev.isDisableImplicitServletMappings(), changedNames);
               computeChange("temp-dir", cur.getTempDir(), prev.getTempDir(), changedNames);
               computeChange("optimistic-serialization", cur.isOptimisticSerialization(), prev.isOptimisticSerialization(), changedNames);
               computeChange("retain-original-url", cur.isRetainOriginalURL(), prev.isRetainOriginalURL(), changedNames);
               computeChange("fail-deploy-on-filter-init-error", cur.getFailDeployOnFilterInitError(), prev.getFailDeployOnFilterInitError(), changedNames);
               if (!changedNames.isEmpty()) {
                  throw new BeanUpdateRejectedException("Non-Dynamic property in \"container-descriptor\" is/are specified in deployment plan: '" + getChangedPropertyNames(changedNames) + "'");
               }
            }
         }

         protected void handleBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            if (newBean instanceof ContainerDescriptorBean && prop != null && "ContainerDescriptors".equals(prop.getPropertyName())) {
               ContainerDescriptorBean cdb = (ContainerDescriptorBean)newBean;
               ((DescriptorBean)cdb).addBeanUpdateListener(this);
               if (cdb.isServletReloadCheckSecsSet()) {
                  WebAppConfigManager.this.servletReloadCheckSecs = cdb.getServletReloadCheckSecs();
                  WebAppConfigManager.this.servletReloadCheckSecsSet = true;
               }

               WebAppConfigManager.this.resourceReloadCheckSecs = cdb.getResourceReloadCheckSecs();
               if (cdb.isIndexDirectoryEnabledSet()) {
                  WebAppConfigManager.this.indexDirectoryEnabledSet = true;
                  WebAppConfigManager.this.indexDirectoryEnabled = cdb.isIndexDirectoryEnabled();
               }

               WebAppConfigManager.this.indexDirectorySortBy = cdb.getIndexDirectorySortBy();
               if (cdb.isShowArchivedRealPathEnabledSet()) {
                  WebAppConfigManager.this.showArchivedRealPathEnabledSet = true;
                  WebAppConfigManager.this.showArchivedRealPathEnabled = cdb.isShowArchivedRealPathEnabled();
               }

               WebAppConfigManager.this.minimumNativeFileSize = cdb.getMinimumNativeFileSize();
               WebAppConfigManager.this.tempDir = cdb.getTempDir();
               WebAppConfigManager.this.disableImplicitServletMappings = cdb.isDisableImplicitServletMappings();
               WebAppConfigManager.this.requireAdminTraffic = cdb.isRequireAdminTraffic();
               if (cdb.isAccessLoggingDisabledSet()) {
                  WebAppConfigManager.this.accessLoggingDisabled = cdb.isAccessLoggingDisabled();
               }

               if (cdb.getGzipCompression().isEnabledSet()) {
                  WebAppConfigManager.this.gzipCompressionEnabledSet = true;
                  WebAppConfigManager.this.gzipCompressionEnabled = cdb.getGzipCompression().isEnabled();
               }

               if (cdb.getGzipCompression().isMinContentLengthSet()) {
                  WebAppConfigManager.this.minCompressionContentLengthSet = true;
                  WebAppConfigManager.this.minCompressionContentLength = cdb.getGzipCompression().getMinContentLength();
               }

               if (cdb.getGzipCompression().isContentTypeSet()) {
                  WebAppConfigManager.this.includeCompressionContentTypeSet = true;
                  WebAppConfigManager.this.includeCompressionContentType = cdb.getGzipCompression().getContentType();
               }

            }
         }

         protected void handleBeanRemove(BeanUpdateEvent.PropertyUpdate prop) {
            if (prop != null && "ContainerDescriptors".equals(prop.getPropertyName()) && prop.getRemovedObject() instanceof ContainerDescriptorBean) {
               ContainerDescriptorBean cdb = (ContainerDescriptorBean)prop.getRemovedObject();
               ((DescriptorBean)cdb).removeBeanUpdateListener(this);
               WebAppConfigManager.this.servletReloadCheckSecs = WebAppConfigManager.getDefaultReloadSecs();
               WebAppConfigManager.this.servletReloadCheckSecsSet = false;
               WebAppConfigManager.this.resourceReloadCheckSecs = WebAppConfigManager.getDefaultReloadSecs();
               WebAppConfigManager.this.indexDirectoryEnabledSet = false;
               WebAppConfigManager.this.indexDirectoryEnabled = false;
               WebAppConfigManager.this.indexDirectorySortBy = "NAME";
               WebAppConfigManager.this.showArchivedRealPathEnabledSet = false;
               WebAppConfigManager.this.showArchivedRealPathEnabled = false;
               WebAppConfigManager.this.minimumNativeFileSize = 4096L;
               WebAppConfigManager.this.tempDir = null;
               WebAppConfigManager.this.disableImplicitServletMappings = false;
               WebAppConfigManager.this.requireAdminTraffic = false;
               WebAppConfigManager.this.accessLoggingDisabled = WebAppConfigManager.this.module.isInternalApp();
               WebAppConfigManager.this.gzipCompressionEnabledSet = false;
               WebAppConfigManager.this.gzipCompressionEnabled = false;
               WebAppConfigManager.this.minCompressionContentLengthSet = false;
               WebAppConfigManager.this.minCompressionContentLength = 2048L;
               WebAppConfigManager.this.includeCompressionContentTypeSet = false;
               WebAppConfigManager.this.includeCompressionContentType = WebAppConfigManager.DEFAULT_INCLUDE_COMPRESSION_CONTENT_TYPE;
            }
         }
      };
   }

   public boolean shouldReloadResource(long lastChecked, String name) {
      if (this.isResourceStaleCheckDisabled(name)) {
         return false;
      } else {
         int reloadSecs = this.getResourceReloadCheckSecs(name);
         return this.shouldReload(lastChecked, reloadSecs);
      }
   }

   public boolean isResourceStaleCheckDisabled(String name) {
      int reloadSecs = this.getResourceReloadCheckSecs(name);
      return reloadSecs < 0;
   }

   public boolean shouldReloadServlet(long lastChecked) {
      if (this.isServletStaleCheckDisabled()) {
         return false;
      } else {
         int reloadSecs = this.getServletReloadCheckSecs();
         return this.shouldReload(lastChecked, reloadSecs);
      }
   }

   public boolean isServletStaleCheckDisabled() {
      int reloadSecs = this.getServletReloadCheckSecs();
      return reloadSecs < 0;
   }

   private boolean shouldReload(long lastChecked, int reloadSecs) {
      return System.currentTimeMillis() - (long)(reloadSecs * 1000) > lastChecked;
   }

   static boolean isAbsoluteFilePath(String s) {
      if (WIN_32 && s.length() > 2 && Character.isLetter(s.charAt(0)) && s.charAt(1) == ':') {
         return true;
      } else {
         return s.length() > 0 && s.charAt(0) == FSC;
      }
   }

   boolean isWebAppSuspended() {
      return isServerSuspended() && !this.module.isInternalApp();
   }

   boolean isWebAppSuspending() {
      return isServerSuspending() && !this.module.isInternalApp();
   }

   static boolean isServerSuspending() {
      return WebAppShutdownService.isSuspending();
   }

   static boolean isServerSuspended() {
      return WebAppShutdownService.isSuspended();
   }

   static boolean isServerShutDown() {
      return isServerSuspended() || isServerSuspending();
   }

   static {
      WIN_32 = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH).indexOf("windows") >= 0;
      FSC = File.separatorChar;
      DEFAULT_INCLUDE_COMPRESSION_CONTENT_TYPE = new String[]{"text/html", "text/xml", "text/plain"};
      registry = WebServerRegistry.getInstance();
      redefinableWebApps = initRedefinableWebApps();
      httpServerManager = registry.getHttpServerManager();
      mgmtProvider = registry.getManagementProvider();
      securityProvider = registry.getSecurityProvider();
      domainmbean = registry.getDomainMBean();
      servermbean = registry.getServerMBean();
      webAppContainer = registry.getWebAppContainerMBean();
      useExtendedSessionFormat = Boolean.getBoolean("weblogic.servlet.useExtendedSessionFormat");
      logInternalAppAccess = Boolean.getBoolean("weblogic.servlet.logging.LogInternalAppAccess");
   }
}
