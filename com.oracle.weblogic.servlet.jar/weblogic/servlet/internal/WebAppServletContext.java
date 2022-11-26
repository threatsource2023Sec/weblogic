package weblogic.servlet.internal;

import com.oracle.injection.InjectionContainer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.annotation.ManagedBean;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.naming.Context;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.WebConnection;
import javax.servlet.jsp.JspApplicationContext;
import oracle.jsp.provider.JspResourceProvider;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.library.LibraryManager;
import weblogic.application.naming.Environment;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.JASPICProviderBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.ServletDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.logging.Loggable;
import weblogic.logging.j2ee.ServletContextLogger;
import weblogic.managedbean.ManagedBeanCreator;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.platform.JDK;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.servlet.AsyncInitServlet;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.JSPServlet;
import weblogic.servlet.WebLogicServletContext;
import weblogic.servlet.internal.async.AsyncContextImpl;
import weblogic.servlet.internal.async.AsyncContextTimer;
import weblogic.servlet.internal.fragment.WebFragmentManager;
import weblogic.servlet.internal.session.SessionConfigManager;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionCookieConfigImpl;
import weblogic.servlet.internal.session.SharedSessionData;
import weblogic.servlet.jsp.InjectionBasedJspApplicationContextImpl;
import weblogic.servlet.jsp.JspApplicationContextImpl;
import weblogic.servlet.jsp.JspFileNotFoundException;
import weblogic.servlet.jsp.JspStub;
import weblogic.servlet.jsp.StaleChecker;
import weblogic.servlet.jsp.TagFileHelper;
import weblogic.servlet.security.internal.ExternalRoleCheckerManager;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.ServletObjectsFacade;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.security.internal.SessionSecurityData;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.BeanELResolverCachePurger;
import weblogic.servlet.utils.ContainerInitializerConfiguration;
import weblogic.servlet.utils.FastSwapFilter;
import weblogic.servlet.utils.ServiceLoader;
import weblogic.servlet.utils.ServletAccessorHelper;
import weblogic.servlet.utils.ServletMapping;
import weblogic.servlet.utils.URLMapping;
import weblogic.servlet.utils.URLMappingFactory;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.Debug;
import weblogic.utils.NestedRuntimeException;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.http.MaxRequestParameterExceedException;
import weblogic.utils.io.FilenameEncoder;
import weblogic.work.WorkManagerFactory;

public final class WebAppServletContext implements StaleChecker, WebLogicServletContext, ServletInvocationContext {
   private static final AuthenticatedSubject KERNEL_ID;
   private static final boolean NO_VERSION_CHECK;
   private static final Map NON_BLOCKING_DISPATCH;
   private static final Map DIRECT_DISPATCH;
   private static final String TEMPDIR_ATTRIBUTE = "javax.servlet.context.tempdir";
   private static final String JSF_RI_SUNJSFJS = "com.sun.faces.sunJsfJs";
   private static final String JSF_BEAN_VALIDATOR_FACTORY = "javax.faces.validator.beanValidator.ValidatorFactory";
   private static final String WL_HTTPD = "weblogic.httpd.";
   private static final String WL_CLASSPATH = "servlet.classpath";
   private static final String WL_DEFAULTSERVLET = "defaultServlet";
   private static final String WELD_IGNORE_FWD = "org.jboss.weld.context.ignore.forward";
   private static final String WELD_IGNORE_INC = "org.jboss.weld.context.ignore.include";
   static final long DEFAULT_UNAVAILABLE_SECONDS = 60L;
   public static final DebugLogger DEBUG_URL_RES;
   private static int maxConcurrentRequestsAllowed;
   private static boolean doNotSendContinueHeader;
   private static Method wldfDyeInjectionMethod;
   private static WebServerRegistry registry;
   private Map contextCreatedServlets = null;
   private Map servletRegisrations = null;
   private ServletSecurityContext securityContext = new ServletSecurityContextImpl(this);
   private final WebAppModule module;
   private final HttpServer httpServer;
   private ContextVersionManager contextManager;
   private ClassLoader classLoader;
   private final TagFileHelper tagFileHelper = new TagFileHelper(this);
   private final WebAppComponentMBean compMBean;
   private final ApplicationContextInternal appCtx;
   private WebAppRuntimeMBeanImpl runtime;
   private CompEnv compEnv;
   private final String contextName;
   private String contextPath;
   private String configuredContextPath;
   private final String versionId;
   private final String fullCtxName;
   private boolean adminMode = false;
   private String displayName;
   private String docroot = null;
   private final AttributesMap attributes = new AttributesMap("servlet-context");
   private Map contextParams;
   private final EventsManager eventsManager = new EventsManager(this);
   private SessionContext sessionContext;
   private WebAppSecurity securityManager;
   private final ErrorManager errorManager = new ErrorManager(this);
   private final FilterManager filterManager = new FilterManager(this);
   private final AsyncContextTimer asyncContextTimer;
   private boolean defaultContext;
   private final boolean onDemandDisplayRefresh;
   private boolean isArchived = false;
   private URLMatchHelper defaultURLMatchHelper = null;
   private String defaultServletName = null;
   private War war;
   private LibraryManager libraryManager = null;
   private WebComponentCreator componentCreator;
   private WebAppConfigManager configManager;
   private JSPManager jspManager;
   private SessionConfigManager sessionConfigManager;
   private SessionCookieConfig sessionCookieConfig;
   private CharsetMap charsetMap;
   private String requestCharacterEncoding = null;
   private String responseCharacterEncoding = null;
   private LocaleGenerator localeGenerator;
   private final ConcurrentHashMap servletStubs = new ConcurrentHashMap();
   private URLMapping servletMapping = new ServletMapping(WebAppConfigManager.isCaseInsensitive(), WebAppSecurity.getProvider().getEnforceStrictURLPattern());
   private final TreeMap servletLoadSequences = new TreeMap();
   private final Map preloadServlets = new HashMap();
   private boolean startedServletLoadSequences = false;
   private ServletContextLogger servletContextLogger;
   private String logContext;
   private boolean asyncInitsStillRunning = false;
   private final List asyncInitServlets = new ArrayList();
   private JspResourceProvider jspResourceProvider;
   private MDSClassFinder mdsFinder;
   private ServletStubImpl jspServletStub;
   private ServletStubImpl jspxServletStub;
   private JspApplicationContextImpl jacImpl = null;
   private final Object reloadServletClassLoaderLock = new Object();
   private WebFragmentManager webFragmentManager = null;
   private InjectionContainer m_injectionContainer = null;
   private String partitionName = null;
   private String partitionId = null;
   private ComponentInvocationContext invocationContext = null;
   private ContextPhase phase;
   static final long serialVersionUID = 8565865363773502204L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.WebAppServletContext");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Context_Execute_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Context_Handle_Throwable_Around_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   WebAppServletContext(HttpServer server, WebAppComponentMBean mbean, WebAppModule module) throws DeploymentException {
      this.phase = WebAppServletContext.ContextPhase.INIT;
      this.httpServer = server;
      this.compMBean = mbean;
      this.module = module;
      this.appCtx = module.getApplicationContext();
      this.onDemandDisplayRefresh = this.appCtx.getAppDeploymentMBean().isOnDemandDisplayRefresh();
      this.versionId = this.appCtx.getAppDeploymentMBean().getVersionIdentifier();
      this.contextName = module.getName();
      this.fullCtxName = this.versionId == null ? this.contextName : this.contextName + "#" + this.versionId;
      this.war = module.getWarInstance();
      this.isArchived = module.isArchived();
      this.configManager = module.getWebAppConfigManager();
      this.jspManager = module.getJspManager();
      this.sessionConfigManager = module.getSessionConfigManager();
      this.sessionCookieConfig = new SessionCookieConfigImpl(this, this.sessionConfigManager);
      this.webFragmentManager = module.getWebFragmentManager();
      this.docroot = this.configManager.getDocRoot();
      this.contextParams = this.configManager.getContextParams();
      this.classLoader = module.getClassLoader();
      this.logContext = this.toString();
      this.servletContextLogger = this.configManager.getServletContextLogger();
      this.charsetMap = new CharsetMap(this.httpServer.getCharsets());
      this.libraryManager = module.getLibraryManager();
      this.invocationContext = this.createComponentInvocationContext();
      this.processInitParams();
      this.setContextPath(module.getContextPath());
      this.initTagFileClassLoader();
      this.initSessionContext();
      this.initLocaleGenerator();
      this.asyncContextTimer = new AsyncContextTimer(this);
      this.compEnv = new CompEnv(this);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Initialized servlet context: " + this.getLogContext());
      }

   }

   WebAppServletContext(HttpServer server, WebAppComponentMBean compMBean, WebAppModule module, ApplicationContextInternal appCtx, CompEnv compEnv) {
      this.module = module;
      this.httpServer = server;
      this.compMBean = compMBean;
      this.appCtx = appCtx;
      this.compEnv = compEnv;
      this.contextName = null;
      this.versionId = null;
      this.fullCtxName = null;
      this.asyncContextTimer = null;
      this.onDemandDisplayRefresh = false;
   }

   private void initLocaleGenerator() {
      try {
         this.localeGenerator = (LocaleGenerator)Class.forName("weblogic.servlet.internal.JDK7LocaleGenerator").newInstance();
         this.localeGenerator.setLangtagFallbackEnabled(this.isLangtagFallbackEnabled());
      } catch (Exception var2) {
      }

   }

   private boolean isLangtagFallbackEnabled() {
      String webAppLangtagVersion = this.getWebAppLangtagVersion();
      if (webAppLangtagVersion != null && "3066".equals(webAppLangtagVersion)) {
         return true;
      } else if (webAppLangtagVersion != null && "5646".equals(webAppLangtagVersion)) {
         return false;
      } else {
         LocaleGenerator var10000 = this.localeGenerator;
         if (LocaleGenerator.SYSTEM_LANGTAG_REVISION != null) {
            LocaleGenerator var10001 = this.localeGenerator;
            if ("3066".equals(LocaleGenerator.SYSTEM_LANGTAG_REVISION)) {
               return true;
            }
         }

         return false;
      }
   }

   private String getWebAppLangtagVersion() {
      return this.module != null && this.module.getWlWebAppBean() != null && this.module.getWlWebAppBean().getContainerDescriptors() != null && this.module.getWlWebAppBean().getContainerDescriptors().length != 0 ? this.module.getWlWebAppBean().getContainerDescriptors()[0].getLangtagRevision() : null;
   }

   public static boolean isJDK6() {
      return JDK.getJDK().getMajorVersion() == 1 && JDK.getJDK().getMinorVersion() < 7;
   }

   private void processInitParams() {
      if (this.contextParams != null && this.contextParams.size() != 0) {
         Iterator it = this.contextParams.keySet().iterator();

         while(it.hasNext()) {
            this.processInitParameter((String)it.next());
         }

      }
   }

   private void processInitParameter(String name) {
      if (name != null && name.startsWith("weblogic.httpd.")) {
         String value = (String)this.contextParams.get(name);
         String param = name.substring("weblogic.httpd.".length());
         if ("defaultServlet".equalsIgnoreCase(param)) {
            HTTPLogger.logDeprecatedContextParamDefaultServlet();
            this.setDefaultServlet(value);
         } else if ("servlet.classpath".equalsIgnoreCase(param)) {
            HTTPLogger.logDeprecatedContextParamClasspath();
            this.addClassPath(value);
         }

      }
   }

   private void setDefaultServlet(String name) {
      if (name != null) {
         if (this.servletStubs != null) {
            ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
            if (sstub != null) {
               this.defaultURLMatchHelper = new URLMatchHelper("/", sstub);
            }
         }

         this.defaultServletName = name;
      }
   }

   private void initContextAttributes() {
      this.setAttribute("javax.servlet.context.tempdir", this.configManager.getPublicTempDir());
      if (this.compMBean != null) {
         this.attributes.put("weblogic.servlet.WebAppComponentMBean", this.compMBean);
      }

      List libs = this.webFragmentManager.getOrderedLibs();
      if (libs != null && libs.size() > 0) {
         this.attributes.put("javax.servlet.context.orderedLibs", libs);
      }

      this.attributes.put("weblogic.servlet.WebAppComponentRuntimeMBean", this.runtime);
      if (this.httpServer.getUriPath() != null) {
         this.attributes.put("weblogic.servlet.partition.uriPrefix", this.httpServer.getUriPath());
      }

   }

   public Object getAttribute(String name) {
      if (name == null) {
         throw new NullPointerException("Name should not be null when getAttribute");
      } else {
         return this.attributes.get(name, this);
      }
   }

   public Enumeration getAttributeNames() {
      return (Enumeration)(this.attributes.isEmpty() ? new EmptyEnumerator() : new IteratorEnumerator(this.attributes.keys()));
   }

   public void setAttribute(String name, Object value) {
      if (name == null) {
         throw new NullPointerException("Name should not be null when setAttribute");
      } else if (value == null) {
         this.removeAttribute(name);
      } else {
         Object prev = this.attributes.put(name, value, this);
         this.eventsManager.notifyContextAttributeChange(name, value, prev);
      }
   }

   public void removeAttribute(String name) {
      Object prev = this.attributes.remove(name);
      this.eventsManager.notifyContextAttributeChange(name, (Object)null, prev);
   }

   public ServletContext getContext(String uripath) {
      if (uripath == null) {
         return null;
      } else {
         uripath = HttpParsing.ensureStartingSlash(uripath);
         String uriPrefix = this.httpServer.getUriPath();
         if (uriPrefix != null && uriPrefix.length() > 0 && !uripath.startsWith(uriPrefix)) {
            uripath = uriPrefix + uripath;
         }

         ContextVersionManager mgr = this.httpServer.getServletContextManager().lookupVersionManager(uripath);
         return mgr == null ? null : mgr.getCurrentOrActiveContext(this.isAdminMode());
      }
   }

   public String getInitParameter(String name) {
      if (name == null) {
         throw new NullPointerException("Name should not be null when getInitParameter");
      } else {
         return (String)this.contextParams.get(name);
      }
   }

   public boolean setInitParameter(String name, String value) {
      if (name == null) {
         throw new NullPointerException("Name should not be null when setInitParameter");
      } else if (this.contextParams.containsKey(name)) {
         return false;
      } else {
         this.contextParams.put(name, value);
         return true;
      }
   }

   public Enumeration getInitParameterNames() {
      return (Enumeration)(this.contextParams.isEmpty() ? new EmptyEnumerator() : new IteratorEnumerator(this.contextParams.keySet().iterator()));
   }

   public int getMajorVersion() {
      return 4;
   }

   public int getMinorVersion() {
      return 0;
   }

   public String getMimeType(String file) {
      return this.configManager.getMimeType(file);
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      return this.getRequestDispatcher(path, -1);
   }

   RequestDispatcher getRequestDispatcher(String path, int mode) {
      if (path != null && path.length() != 0 && path.charAt(0) == '/') {
         String queryString = null;
         int queryStringPos = path.indexOf(63);
         if (queryStringPos > 0 && queryStringPos < path.length() - 1) {
            queryString = path.substring(queryStringPos + 1);
            path = path.substring(0, queryStringPos);
         }

         path = FilenameEncoder.resolveRelativeURIPath(path, true);
         return path == null ? null : new RequestDispatcherImpl(path, queryString, this, mode);
      } else {
         return null;
      }
   }

   public RequestDispatcher getNamedDispatcher(String name) {
      return this.getNamedDispatcher(name, -1);
   }

   public RequestDispatcher getNamedDispatcher(String name, int mode) {
      if (name == null) {
         return null;
      } else {
         ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
         return sstub == null ? null : new RequestDispatcherImpl(sstub, this, mode);
      }
   }

   public String getRealPath(String path) {
      if (this.docroot == null) {
         HTTPLogger.logNullDocRoot(this.getLogContext(), "getRealPath()");
         return null;
      } else if (path == null) {
         return null;
      } else {
         String vpath = path.replace('/', WebAppConfigManager.FSC);
         if (this.isArchived) {
            if (this.configManager.isShowArchivedRealPathEnabled()) {
               try {
                  File f = new File(this.getRootTempDir(), "war");
                  f = FilenameEncoder.getSafeFile(f.getPath(), vpath);
                  return f.getCanonicalPath();
               } catch (FilenameEncoder.UnsafeFilenameException var7) {
                  HTTPLogger.logUnsafePath(this.getLogContext(), "getRealPath()", path, var7);
                  return null;
               } catch (IOException var8) {
                  HTTPLogger.logUnsafePath(this.getLogContext(), "getRealPath()", path, var8);
                  return null;
               }
            } else {
               return null;
            }
         } else {
            if (this.configManager.isAcceptContextPathInGetRealPath(this.httpServer) && !this.isDefaultContext()) {
               String cpath = WebAppConfigManager.FSC + this.contextName;
               if (vpath.startsWith(cpath)) {
                  vpath = vpath.substring(cpath.length());
               }
            }

            try {
               ApplicationFileManager afMgr = this.appCtx.getApplicationFileManager();
               File[] dirs = afMgr.getVirtualJarFile(this.getURI()).getRootFiles();
               if (dirs.length == 0) {
                  throw new AssertionError("Could not determine the docroot in getRealPath");
               } else {
                  File f = null;

                  for(int i = 0; i < dirs.length; ++i) {
                     f = FilenameEncoder.getSafeFile(dirs[i].getPath(), vpath);
                     if (f.exists()) {
                        break;
                     }
                  }

                  return f.getCanonicalPath();
               }
            } catch (FilenameEncoder.UnsafeFilenameException var9) {
               HTTPLogger.logUnsafePath(this.getLogContext(), "getRealPath()", path, var9);
               return null;
            } catch (IOException var10) {
               HTTPLogger.logUnsafePath(this.getLogContext(), "getRealPath()", path, var10);
               return null;
            }
         }
      }
   }

   public URL getResource(String path) throws MalformedURLException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + " getResource() invoked for : " + path);
      }

      if (path != null && path.length() >= 1 && path.charAt(0) == '/') {
         Source src = this.getResourceAsSource(path);
         return src == null ? null : src.getURL();
      } else {
         throw new MalformedURLException("The path for getResource() must begin with a '/'");
      }
   }

   public URL[] getResources(String path) throws MalformedURLException {
      if (path != null && path.length() >= 1 && path.charAt(0) == '/') {
         ClassFinder resourceFinder = this.war.getResourceFinder(path);
         ArrayList list = new ArrayList();
         Enumeration e = resourceFinder.getSources(path);

         while(e.hasMoreElements()) {
            list.add(((Source)e.nextElement()).getURL());
         }

         return (URL[])list.toArray(new URL[list.size()]);
      } else {
         throw new MalformedURLException("The path for getResources() must begin with a '/'");
      }
   }

   public InputStream getResourceAsStream(String path) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + " getResourceAsStream() invoked for : " + path);
      }

      Source src = this.getResourceAsSource(path);

      try {
         return src == null ? null : src.getInputStream();
      } catch (IOException var4) {
         HTTPLogger.logUnableToGetStream(this.getLogContext(), path, var4);
         return null;
      }
   }

   public String getServerInfo() {
      return HttpServer.SERVER_INFO;
   }

   public HttpServer getServer() {
      return this.httpServer;
   }

   public HttpServer getHttpServer() {
      return this.httpServer;
   }

   protected LocaleGenerator getLocaleGenerator() {
      if (this.localeGenerator == null) {
         this.initLocaleGenerator();
      }

      return this.localeGenerator;
   }

   public void log(String s, Throwable e) {
      if (this.servletContextLogger != null) {
         this.servletContextLogger.log(s, e);
      }
   }

   public void logError(String s) {
      if (this.servletContextLogger != null) {
         this.servletContextLogger.logError(s);
      }
   }

   public void log(String s) {
      if (this.servletContextLogger != null) {
         this.servletContextLogger.log(s);
      }
   }

   public Set getResourcePaths(String path) {
      if (path == null) {
         return null;
      } else {
         path = HttpParsing.ensureEndingSlash(path);
         Set set = new HashSet();
         this.war.getResourcePaths(path, set);
         return set.isEmpty() ? null : set;
      }
   }

   public String getServletContextName() {
      return this.displayName;
   }

   public Enumeration getServletNames() {
      return new IteratorEnumerator(this.servletStubs.keySet().iterator());
   }

   /** @deprecated */
   @Deprecated
   public Servlet getServlet(String name) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Enumeration getServlets() {
      return new EmptyEnumerator();
   }

   /** @deprecated */
   @Deprecated
   public void log(Exception e, String s) {
      this.log((String)s, (Throwable)e);
   }

   public ContextVersionManager getContextManager() {
      if (this.contextManager == null || this.contextManager.isOld()) {
         this.contextManager = this.httpServer.getServletContextManager().lookupVersionManagerForContextPath(this.getContextPath());
      }

      return this.contextManager;
   }

   public void addSession(String sessionId) {
      ContextVersionManager cvm = this.getContextManager();
      if (cvm != null) {
         cvm.putContextForSession(sessionId, this);
      }

   }

   public void removeSession(String sessionId) {
      ContextVersionManager cvm = this.getContextManager();
      if (cvm != null) {
         cvm.removeContextForSession(sessionId);
      }

   }

   public void enteringContext(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
      if (!(session instanceof SharedSessionData)) {
         this.sessionContext.enter(req, res, session);
      }

   }

   public void exitingContext(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
      if (!(session instanceof SharedSessionData)) {
         this.sessionContext.exit(req, res, session);
      }

   }

   CharsetMap getCharsetMap() {
      return this.charsetMap;
   }

   private void registerDefaultServlet() {
      this.registerServlet("FileServlet", "/", "weblogic.servlet.FileServlet");
   }

   void prepare() throws DeploymentException {
      this.initializeSecurity();
      this.initWebappRuntimeMBean();
      this.registerDefaultServlet();
      this.registerFastSwapFilter();
      this.prepareFromDescriptors();
      this.initResourceProvider();
      this.initContextAttributes();
      this.initContextListeners();
      this.compEnv.prepare();
   }

   private void registerFastSwapFilter() throws DeploymentException {
      if (this.configManager.shouldRegisterFastSwapFilter()) {
         FastSwapFilter.registerFastSwapFilter(this);
      }

   }

   private void initWebappRuntimeMBean() throws DeploymentException {
      AppDeploymentMBean app = this.appCtx.getAppDeploymentMBean();
      RuntimeMBean parent = this.appCtx.getRuntime();
      String name = this.getRuntimeMBeanName();

      try {
         this.runtime = new WebAppRuntimeMBeanImpl(name, this.contextPath, this, parent, app.getApplicationIdentifier());
      } catch (ManagementException var5) {
         throw new DeploymentException(var5);
      }

      if (this.libraryManager != null && !this.libraryManager.hasUnresolvedReferences()) {
         this.runtime.setLibraryRuntimes(this.libraryManager.getReferencedLibraryRuntimes());
         this.libraryManager.getReferencer().setReferencerRuntime(this.runtime);
      }

   }

   private String getRuntimeMBeanName() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.httpServer.getName());
      builder.append("_").append(this.getContextPath());
      builder.append(this.getVersionId() == null ? "" : "_" + this.getVersionId());
      return builder.toString();
   }

   private void initSessionContext() throws DeploymentException {
      this.sessionContext = registry.getSessionContextFactory().createSessionContext(this);
   }

   private void prepareFromDescriptors() throws DeploymentException {
      this.displayName = this.configManager.getDisplayName();
      this.charsetMap.addMapping(this.configManager.getCharsetMap());
      this.filterManager.registerServletFilters(this.module.getWebAppBean());
      this.jspManager.registerExpressionInterceptor(this);
      this.registerURLMatchMapper();
      this.registerServlets();
      this.registerServletMappings();
      this.jspManager.registerJspServlet(this, this.configManager.isImplicitServletMappingDisabled());
      if (this.configManager.isSaveSessionsEnabled()) {
         this.sessionContext.getConfigMgr().setSaveSessionsOnRedeployEnabled(true);
      }

      this.registerSecurityRoles();
      this.registerSecurityConstraint();
      this.jspManager.precompileJSPs(this);
   }

   private void registerSecurityConstraint() throws DeploymentException {
      WebAppBean webAppBean = this.module.getWebAppBean();
      SecurityConstraintBean[] secCons = webAppBean.getSecurityConstraints();
      this.securityManager.registerSecurityConstraints(webAppBean, secCons);
   }

   private void activateFromDescriptors() throws DeploymentException {
      this.errorManager.registerErrorPages(this.module.getWebAppBean());
   }

   private void initializeComponentCreator() throws DeploymentException {
      Thread thread = Thread.currentThread();
      ClassLoader oldClassLoader = this.pushEnvironment(thread);

      try {
         Thread.currentThread().setContextClassLoader(this.getServletClassLoader());
         String componentFactoryClassName = null;
         if (this.getWebAppModule().getWlWebAppBean() != null && this.getWebAppModule().getWlWebAppBean().getComponentFactoryClassName().length > 0) {
            componentFactoryClassName = this.getWebAppModule().getWlWebAppBean().getComponentFactoryClassName()[0];
         }

         if (componentFactoryClassName == null && this.appCtx != null && this.appCtx.getWLApplicationDD() != null) {
            componentFactoryClassName = this.appCtx.getWLApplicationDD().getComponentFactoryClassName();
         }

         PitchforkContext pitchforkContext = new PitchforkContext(componentFactoryClassName);
         ModuleContext moduleContext = this.appCtx.getModuleContext(this.getWebAppModule().getId());
         InjectionContainer injectionContainer = this.getInjectionContainer(moduleContext);
         if (injectionContainer != null) {
            this.m_injectionContainer = injectionContainer;
            this.componentCreator = new InjectionBasedWebComponentCreator(this.m_injectionContainer, this.getWebAppModule(), pitchforkContext, moduleContext);
            this.contextParams.put("org.jboss.weld.context.ignore.forward", Boolean.FALSE.toString());
            this.contextParams.put("org.jboss.weld.context.ignore.include", Boolean.FALSE.toString());
         } else {
            this.componentCreator = new WebComponentContributor(pitchforkContext);
         }

         this.componentCreator.initialize(this);
         this.jacImpl = this.createJspApplicationContext();
      } finally {
         popEnvironment(thread, oldClassLoader);
      }

   }

   private InjectionContainer getInjectionContainer(ModuleContext moduleContext) {
      if (moduleContext != null && moduleContext.getRegistry() != null) {
         ModuleRegistry moduleRegistry = moduleContext.getRegistry();
         return (InjectionContainer)moduleRegistry.get(InjectionContainer.class.getName());
      } else {
         return null;
      }
   }

   private void registerDenyUncoveredMethodsSet() {
      this.securityManager.setDenyUncoveredMethodsSet(this.module.webappBean.isDenyUncoveredHttpMethods());
   }

   private void registerSecurityRoles() throws DeploymentException {
      this.securityManager.registerSecurityRoles(this.module.getWebAppBean(), this.module.getWlWebAppBean());
      if (this.isJSR375Application()) {
         this.securityManager.registerDefaultRoleMappingForJSR375(this.module.getWebAppBean().getSecurityRoles());
      }

   }

   private void deployPolicyAndRole() throws DeploymentException {
      this.securityManager.deployPolicyAndRole();
   }

   private void registerLoginConfig() throws DeploymentException {
      registerLoginConfig(this.getSecurityManager(), this.module.getWebAppBean(), this.configManager, this.getLogContext());
   }

   static void registerLoginConfig(WebAppSecurity securityManager, WebAppBean webAppBean, RealmNameAware configManager, String logContext) throws DeploymentException {
      LoginConfigBean[] login = webAppBean.getLoginConfigs();
      if (login != null && login.length >= 1) {
         Loggable l;
         if (login.length > 1) {
            l = HTTPLogger.logMultipleOccurrencesNotAllowedLoggable("<login-config>", "web.xml");
            logAndThrowDeploymentException(l);
         }

         try {
            securityManager.setLoginConfig(login[0]);
         } catch (IllegalArgumentException var7) {
            weblogic.i18n.logging.Loggable l = HTTPLogger.logInvalidAuthMethodLoggable(logContext, var7.getMessage());
            logAndThrowDeploymentException(l);
         }

         if (securityManager != null && "FORM".equals(securityManager.getAuthMethod())) {
            if (securityManager.getLoginPage() == null || securityManager.getLoginPage().length() < 1) {
               l = HTTPLogger.logLoginOrErrorPageMissingLoggable(logContext, "form-login-page");
               logAndThrowDeploymentException(l);
            }

            if (securityManager.getErrorPage() == null || securityManager.getErrorPage().length() < 1) {
               l = HTTPLogger.logLoginOrErrorPageMissingLoggable(logContext, "form-error-page");
               logAndThrowDeploymentException(l);
            }
         }

         String rName = login[0].getRealmName();
         if (rName != null) {
            configManager.setAuthRealmName(rName);
            securityManager.setAuthRealmName(rName);
         }

      } else {
         securityManager.createDelegateModule();
      }
   }

   private static void logAndThrowDeploymentException(weblogic.i18n.logging.Loggable l) throws DeploymentException {
      l.log();
      throw new DeploymentException(l.getMessage());
   }

   private void registerJaspicProvider() throws DeploymentException {
      registerJaspicProvider(this.getSecurityManager(), this.module.getWlWebAppBean(), registry.getDomainMBean());
   }

   static void registerJaspicProvider(WebAppSecurity securityManager, WeblogicWebAppBean wlWebAppBean, DomainMBean domain) throws DeploymentException {
      securityManager.registerJaspicProvider(domain.getSecurityConfiguration().getJASPIC(), wlWebAppBean == null ? null : wlWebAppBean.getJASPICProvider());
   }

   private void initContainerInitializers() throws DeploymentException {
      this.phase = WebAppServletContext.ContextPhase.INITIALIZER_STARTUP;
      if (!this.isInternalApp() && !this.isInternalSAMLApp() && !this.isInternalUtilitiesWebApp() && !this.isInternalWSATApp()) {
         Set initializers = null;
         ContainerInitializerConfiguration enabledExplicitly = WarUtils.isContainerInitializerEnabledExplicitly(this.module.getWlWebAppBean());
         if (enabledExplicitly != ContainerInitializerConfiguration.NONE) {
            if (enabledExplicitly == ContainerInitializerConfiguration.ENABLED) {
               initializers = this.lookupContainerInitializers();
            }
         } else {
            float version = WarUtils.getWebappVersion(this.module.getWebAppBean());
            if ((double)version >= 3.0 || (double)version == 2.5 && this.forcedContainerInitializersLookup()) {
               initializers = this.lookupContainerInitializers();
            }
         }

         this.initContainerInitializers(initializers);
         this.phase = WebAppServletContext.ContextPhase.INITIALIZER_STARTED;
      }
   }

   private boolean forcedContainerInitializersLookup() {
      return this.module.forcedContainerInitializersLookup() || this.isJsfApplication() || this.isJSR375Application();
   }

   private Set lookupContainerInitializers() {
      return ServiceLoader.getInstance().findService(ServletContainerInitializer.class, this.war.getClassFinder(), this.getResourcePatternsFromFCL());
   }

   private void initContainerInitializers(Set initializers) throws DeploymentException {
      if (initializers != null) {
         Iterator var2 = initializers.iterator();

         while(var2.hasNext()) {
            String className = (String)var2.next();
            if (!this.isClassFromExcludedWebFragments(className) && !this.skipSamInitializerIfJSR375NotEnabled(className)) {
               this.initContainerInitializer(className);
            }
         }

      }
   }

   private List getResourcePatternsFromFCL() {
      List result = Collections.emptyList();

      for(ClassLoader current = this.classLoader; current != null && current instanceof GenericClassLoader; current = current.getParent()) {
         if (((List)result).isEmpty()) {
            result = new ArrayList();
         }

         ((List)result).addAll(((GenericClassLoader)current).getResourcePatterns());
      }

      return (List)result;
   }

   private boolean isClassFromExcludedWebFragments(String className) {
      String resourceName = className.replace('.', '/') + ".class";
      URL resourceURL = this.classLoader.getResource(resourceName);
      return this.webFragmentManager.isClassFromExcludedWebFragments(resourceURL);
   }

   private void initContainerInitializer(String className) throws DeploymentException {
      try {
         Class clazz = this.classLoader.loadClass(className);
         ServletContainerInitializer initializer = (ServletContainerInitializer)clazz.newInstance();
         HandlesTypes handlesTypesAnnotation = (HandlesTypes)initializer.getClass().getAnnotation(HandlesTypes.class);
         Class[] handlesTypesClasses = null;
         if (handlesTypesAnnotation != null) {
            handlesTypesClasses = handlesTypesAnnotation.value();
         }

         if (handlesTypesClasses != null && handlesTypesClasses.length != 0) {
            int len = handlesTypesClasses.length;
            String[] handlesTypes = new String[len];

            for(int i = 0; i < len; ++i) {
               handlesTypes[i] = handlesTypesClasses[i].getName();
            }

            Set handlesImpls = this.getHelper().getHandlesImpls(this.module.getTemporaryClassLoader(), handlesTypes);
            Set handlesImplsClasses = new HashSet();
            Iterator var10 = handlesImpls.iterator();

            while(var10.hasNext()) {
               String handlesImpl = (String)var10.next();
               if (!this.isClassFromExcludedWebFragments(handlesImpl)) {
                  Class handlesImplClass = this.classLoader.loadClass(handlesImpl);
                  handlesImplsClasses.add(handlesImplClass);
               }
            }

            if (this.shouldHandleWebSocketConcernsForJSF(className, handlesImplsClasses)) {
               this.handleWebSocketConcernsForJSF(handlesImplsClasses);
            }

            if (handlesImplsClasses.size() == 0) {
               handlesImplsClasses = null;
            }

            initializer.onStartup(handlesImplsClasses, this);
         } else {
            initializer.onStartup((Set)null, this);
         }

      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ServletException var13) {
         throw new DeploymentException("Failed to initialize ServletContainerInitializer: " + className, var13);
      }
   }

   private boolean shouldHandleWebSocketConcernsForJSF(String initializerClassName, Set handlesImplsClasses) {
      if (!"weblogic.websocket.tyrus.TyrusServletContainerInitializer".equals(initializerClassName)) {
         return false;
      } else if (!this.isJsfApplication()) {
         return false;
      } else {
         return !Boolean.valueOf(this.getInitParameter("javax.faces.ENABLE_WEBSOCKET_ENDPOINT")) ? false : handlesImplsClasses.isEmpty();
      }
   }

   private void handleWebSocketConcernsForJSF(Set handlesImplsClasses) throws ClassNotFoundException {
      Class tyrusConfigClass = this.classLoader.loadClass("org.glassfish.tyrus.server.TyrusServerConfiguration");
      handlesImplsClasses.add(tyrusConfigClass);
   }

   public WebComponentCreator getComponentCreator() {
      return this.componentCreator;
   }

   protected ManagedBeanCreator getManagedBeanCreator() {
      ModuleContext modCtx = this.appCtx.getModuleContext(this.module.getId());
      if (modCtx != null) {
         ModuleRegistry registry = modCtx.getRegistry();
         if (registry != null) {
            return (ManagedBeanCreator)registry.get(ManagedBeanCreator.class.getName());
         }
      }

      return null;
   }

   public boolean isCDIWebApplication() {
      return this.m_injectionContainer != null;
   }

   private JspApplicationContextImpl createJspApplicationContext() {
      return (JspApplicationContextImpl)(this.isCDIWebApplication() ? new InjectionBasedJspApplicationContextImpl(this, this.jspManager.isEL22BackwardCompatible(), this.m_injectionContainer, this.getWebAppModule().getClassLoader(), this.getWebAppModule().getId()) : new JspApplicationContextImpl(this, this.jspManager.isEL22BackwardCompatible()));
   }

   private void registerAuthFilter() {
      String authFilter = this.configManager.getAuthFilter();
      if (authFilter != null) {
         this.getSecurityManager().setAuthFilter(authFilter);
      }

   }

   private void registerServlets() throws DeploymentException {
      ServletBean[] servlets = this.module.getWebAppBean().getServlets();
      if (servlets != null) {
         this.registerServlets(servlets);
      }

   }

   private void registerServletMappings() throws DeploymentException {
      ServletMappingBean[] servletMappings = this.module.getWebAppBean().getServletMappings();
      if (servletMappings != null) {
         this.registerServletMapping(servletMappings);
      }

   }

   private void registerServlets(ServletBean[] servlets) throws DeploymentException {
      HashMap sdMap = new HashMap();
      int var5;
      if (this.module.getWlWebAppBean() != null) {
         ServletDescriptorBean[] servletDescriptors = this.module.getWlWebAppBean().getServletDescriptors();
         if (servletDescriptors != null && servletDescriptors.length > 0) {
            ServletDescriptorBean[] var4 = servletDescriptors;
            var5 = servletDescriptors.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ServletDescriptorBean servletDescriptor = var4[var6];
               sdMap.put(servletDescriptor.getServletName(), servletDescriptor);
            }
         }
      }

      ServletBean[] var10 = servlets;
      int var11 = servlets.length;

      for(var5 = 0; var5 < var11; ++var5) {
         ServletBean sb = var10[var5];
         if (sb.isEnabled()) {
            String name = sb.getServletName();
            ServletDescriptorBean sd = (ServletDescriptorBean)sdMap.get(name);
            if ("weblogic.servlet.ServletServlet".equals(sb.getServletClass())) {
               SecureModeMBean secMode = registry.getDomainMBean().getSecurityConfiguration().getSecureMode();
               if (secMode.isSecureModeEnabled() && secMode.isWarnOnInsecureApplications()) {
                  HTTPLogger.logNoServletServletInSecureMode(name);
               }
            }

            ServletStubImpl stub = ServletStubFactory.getInstance(this, sb, sd);
            if (stub != null) {
               this.registerServletLoadSequence(name, sb.getLoadOnStartup());
            }
         }
      }

   }

   private void registerServletLoadSequence(String servletName, String sequence) throws DeploymentException {
      int loadOnStartup = -1;

      try {
         loadOnStartup = Integer.parseInt(sequence);
      } catch (NumberFormatException var5) {
      }

      this.registerServletLoadSequence(servletName, loadOnStartup);
   }

   private void verifyServletStubs() throws DeploymentException {
      Iterator var1 = this.servletStubs.values().iterator();

      while(var1.hasNext()) {
         ServletStubImpl stub = (ServletStubImpl)var1.next();
         if (stub.isPreliminary()) {
            weblogic.i18n.logging.Loggable l = HTTPLogger.logPreliminaryServletStubLoggable(stub.getServletName());
            l.log();
            this.removeServletStub(stub, false);
            if (!this.configManager.isOldDescriptor()) {
               throw new DeploymentException(l.getMessage());
            }
         }
      }

   }

   private void verifyServletMappings() throws DeploymentException {
      ServletMappingBean[] servletMappings = this.module.getWebAppBean().getServletMappings();
      if (servletMappings != null) {
         ServletMappingBean[] var2 = servletMappings;
         int var3 = servletMappings.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ServletMappingBean servletMapping = var2[var4];
            String name = servletMapping.getServletName();
            if (this.servletStubs.get(name) == null) {
               ServletBean servlet = this.module.getWebAppBean().lookupServlet(name);
               if (servlet == null || servlet.isEnabled()) {
                  weblogic.i18n.logging.Loggable l = HTTPLogger.logServletNotFoundForPatternLoggable(name, StringUtils.join(servletMapping.getUrlPatterns(), ", "));
                  l.log();
                  if (!this.configManager.isOldDescriptor()) {
                     throw new DeploymentException(l.getMessage());
                  }
               }
            }
         }

      }
   }

   private void registerServletMapping(ServletMappingBean[] servletMappings) throws DeploymentException {
      JspConfigBean[] jspConfigs = this.module.getWebAppBean().getJspConfigs();
      Set jspPatterns = JSPManager.getJspConfigPatterns(jspConfigs);

      for(int i = 0; i < servletMappings.length; ++i) {
         ServletMappingBean sm = servletMappings[i];
         if (sm != null) {
            String name = sm.getServletName();
            ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
            String[] patterns = sm.getUrlPatterns();
            Debug.assertion(patterns != null && patterns.length > 0, "web-app schema requires atleast one url-pattern");
            if (sstub != null) {
               String servletClass = null;
               if (sstub != null) {
                  servletClass = sstub.getClassName();
               }

               boolean isProxyServlet = "weblogic.servlet.proxy.HttpClusterServlet".equals(servletClass) || "weblogic.servlet.proxy.HttpProxyServlet".equals(servletClass);
               boolean isJSPServlet = "weblogic.servlet.JSPServlet".equals(servletClass) || "weblogic.servlet.JSPClassServlet".equals(servletClass);
               boolean isOracleJSPServlet = "oracle.jsp.runtimev2.JspServlet".equals(servletClass);

               for(int j = 0; j < patterns.length; ++j) {
                  if (patterns[j] != null && patterns[j].equals("*.jsp") && !isProxyServlet && !isOracleJSPServlet) {
                     HTTPLogger.logFoundStarJspUrlPattern(name);
                  }

                  if (jspPatterns == null || !jspPatterns.contains(patterns[j]) || isJSPServlet || isProxyServlet || isOracleJSPServlet) {
                     if (this.isUrlRegisteredToMultiServlets(patterns[j], sstub)) {
                        weblogic.i18n.logging.Loggable l = HTTPLogger.logUrlPatternMappedToMultipleServletsLoggable(patterns[j], this.contextName);
                        logAndThrowDeploymentException(l);
                     }

                     this.registerServletMap(name, patterns[j], sstub);
                  }
               }
            }
         }
      }

   }

   public String getUrlMatchMap() {
      WeblogicWebAppBean wlwab = this.module.getWlWebAppBean();
      if (wlwab == null) {
         return null;
      } else {
         return wlwab.getUrlMatchMaps().length > 0 ? wlwab.getUrlMatchMaps()[0] : null;
      }
   }

   private void registerURLMatchMapper() {
      String mapClassName = this.getUrlMatchMap();
      if (mapClassName != null) {
         URLMapping newMapping = URLMappingFactory.createCustomURLMapping(mapClassName, this.getServletClassLoader(), WebAppConfigManager.isCaseInsensitive());
         if (newMapping != null) {
            Object[] values = this.servletMapping.values();
            if (values != null) {
               for(int i = 0; i < values.length; ++i) {
                  URLMatchHelper umh = (URLMatchHelper)values[i];
                  if (umh != null) {
                     newMapping.put(umh.getPattern(), umh);
                  }
               }
            }

            this.servletMapping = newMapping;
         }
      }
   }

   public void registerFilter(String name, String filterClassName, String[] urlPatterns, String[] servletNames, Map initParams) throws DeploymentException {
      this.filterManager.registerFilter(name, filterClassName, urlPatterns, servletNames, initParams, (String[])null);
   }

   public void registerFilter(String name, String filterClassName, String[] urlPatterns, String[] servletNames, Map initParams, String[] dispatcher) throws DeploymentException {
      this.filterManager.registerFilter(name, filterClassName, urlPatterns, servletNames, initParams, dispatcher);
   }

   public boolean isFilterRegistered(String filterClassName) {
      return this.filterManager.isFilterRegistered(filterClassName);
   }

   public void registerListener(String listenerClassName) throws DeploymentException {
      this.addListener(listenerClassName);
   }

   public boolean isListenerRegistered(String listenerClassName) {
      return this.eventsManager.isListenerRegistered(listenerClassName);
   }

   public void registerServlet(String servletName, String servletClassName, String[] urlPatterns, Map initParams, int loadOnStartup) throws DeploymentException {
      ServletStubImpl sstub = ServletStubFactory.getInstance(this, servletName, servletClassName, initParams);
      if (urlPatterns != null) {
         for(int i = 0; i < urlPatterns.length; ++i) {
            this.registerServletMap(servletName, urlPatterns[i], sstub);
         }
      }

      this.registerServletLoadSequence(servletName, loadOnStartup);
   }

   public boolean isServletRegistered(String urlPattern) {
      if (!"*.jsp".equals(urlPattern) && !"*.jspx".equals(urlPattern)) {
         Object[] values = this.servletMapping.values();
         if (values != null && values.length >= 1) {
            String fixedUrlPattern = WebAppSecurity.fixupURLPattern(urlPattern);

            for(int i = 0; i < values.length; ++i) {
               URLMatchHelper umh = (URLMatchHelper)values[i];
               if (umh.getPattern().equals(fixedUrlPattern)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isUrlRegisteredToMultiServlets(String urlPattern, ServletStub sstub) {
      if (!"*.jsp".equals(urlPattern) && !"*.jspx".equals(urlPattern)) {
         Object[] values = this.servletMapping.values();
         if (values != null && values.length >= 1) {
            String fixedUrlPattern = WebAppSecurity.fixupURLPattern(urlPattern);

            for(int i = 0; i < values.length; ++i) {
               URLMatchHelper umh = (URLMatchHelper)values[i];
               if (umh.getPattern().equals(fixedUrlPattern) && !umh.getServletStub().equals(sstub)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void bindResourceRef(String jndiRefName, String resTypeClassName, String resAuth, String jndiName, boolean sharable, String description) throws DeploymentException {
      if (!this.isStarted()) {
         throw new DeploymentException("ResourceRef can be NOT bound when the application is NOT started :'" + jndiRefName + "'");
      } else {
         this.compEnv.bindResourceRef(jndiRefName, resTypeClassName, resAuth, jndiName, sharable, description);
      }
   }

   public void bindEjbRef(String name, String ejbRefType, String homeClassName, String remoteClassName, String ejbLink, String jndiName, String desc) throws DeploymentException {
      if (!this.isStarted()) {
         throw new DeploymentException("EjbRef can be NOT bound when the application is NOT started :'" + name + "'");
      } else {
         this.compEnv.bindEjbRef(name, ejbRefType, homeClassName, remoteClassName, ejbLink, jndiName, desc, false);
      }
   }

   public void bindEjbLocalRef(String name, String ejbRefType, String localHomeClassName, String localClassName, String ejbLink, String jndiName, String desc) throws DeploymentException {
      if (!this.isStarted()) {
         throw new DeploymentException("EjbLocalRef can NOT be bound when the application is NOT started :'" + name + "'");
      } else {
         this.compEnv.bindEjbRef(name, ejbRefType, localHomeClassName, localClassName, ejbLink, jndiName, desc, true);
      }
   }

   public boolean isResourceBound(String jndiRefName) {
      return this.compEnv.isResourceBound(jndiRefName);
   }

   public void setJspParam(String name, String value) throws DeploymentException {
      this.jspManager.setJspParam(name, value);
   }

   void registerServlet(String name, String pattern, String className) {
      ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
      if (sstub == null) {
         sstub = ServletStubFactory.getInstance(this, name, className, (Map)null);
      }

      this.registerServletMap(name, pattern, sstub);
   }

   private ServletStubImpl registerServletDefinition(String name, String className, Class servletClass, Servlet servlet) {
      ServletDescriptorBean servletDescriptor = null;
      if (this.module.getWlWebAppBean() != null) {
         servletDescriptor = this.module.getWlWebAppBean().lookupServletDescriptor(name);
      }

      ServletMappingBean[] servletMapping = this.findServletMappingBean(name);
      return ServletStubFactory.getInstance(this, name, className, servletClass, servlet, servletDescriptor, servletMapping);
   }

   private ServletMappingBean[] findServletMappingBean(String name) {
      ServletMappingBean[] servletMappings = this.module.getWebAppBean().getServletMappings();
      if (servletMappings == null) {
         return null;
      } else {
         List result = new ArrayList();
         ServletMappingBean[] var4 = servletMappings;
         int var5 = servletMappings.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServletMappingBean servletMapping = var4[var6];
            if (name.equals(servletMapping.getServletName())) {
               result.add(servletMapping);
            }
         }

         return (ServletMappingBean[])result.toArray(new ServletMappingBean[0]);
      }
   }

   public synchronized void registerServletMap(String name, String urlPattern) {
      ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
      this.registerServletMap(name, urlPattern, sstub);
   }

   synchronized void registerServletMap(String name, String urlPattern, ServletStubImpl servletStub) {
      if (urlPattern == null) {
         weblogic.i18n.logging.Loggable l = HTTPLogger.logServletNameIsNullLoggable(this.toString(), urlPattern);
         throw new IllegalArgumentException(l.getMessage());
      } else {
         if (urlPattern.length() == 0) {
            urlPattern = "/\"\"";
         }

         boolean exactDefault = urlPattern.equals("/");
         urlPattern = WebAppSecurity.fixupURLPattern(urlPattern);
         if (!urlPattern.equals("/") && !urlPattern.equals("/*")) {
            if (this.defaultServletName != null && name.equals(this.defaultServletName)) {
               this.defaultURLMatchHelper = new URLMatchHelper("/", servletStub);
            }

            URLMapping newMapping = (URLMapping)((URLMapping)this.servletMapping.clone());
            URLMatchHelper umh = new URLMatchHelper(urlPattern, servletStub);
            newMapping.put(urlPattern, umh);
            this.servletMapping = newMapping;
            if (urlPattern.equals("*.jsp")) {
               this.jspServletStub = servletStub;
            } else if (urlPattern.equals("*.jspx")) {
               this.jspxServletStub = servletStub;
            }
         } else {
            this.defaultURLMatchHelper = new URLMatchHelper(exactDefault ? "/" : "/*", servletStub);
         }

      }
   }

   synchronized void registerServletLoadSequence(String servletName, int seq) throws DeploymentException {
      Integer oldSeq = (Integer)this.preloadServlets.get(servletName);
      if (oldSeq != null && oldSeq != seq) {
         this.preloadServlets.remove(servletName);
         ((ArrayList)this.servletLoadSequences.get(oldSeq)).remove(servletName);
      }

      if (seq >= 0) {
         if (oldSeq == null || oldSeq != seq) {
            this.preloadServlets.put(servletName, seq);
            ArrayList o = (ArrayList)this.servletLoadSequences.get(seq);
            if (o == null) {
               o = new ArrayList();
               this.servletLoadSequences.put(seq, o);
            }

            o.add(servletName);
         }

         if (this.isStarted() || this.startedServletLoadSequences) {
            Thread thread = Thread.currentThread();
            ClassLoader cl = this.pushEnvironment(thread);

            try {
               this.preloadServlet(servletName);
            } finally {
               popEnvironment(thread, cl);
            }
         }

      }
   }

   private void initContextListeners() throws DeploymentException {
      this.eventsManager.registerPreparePhaseListeners();
      this.eventsManager.notifyContextPreparedEvent();
   }

   public synchronized void preloadResources() throws DeploymentException {
      Thread thread = Thread.currentThread();
      ClassLoader cl = this.pushEnvironment(thread);

      try {
         this.initContainerInitializers();
         this.eventsManager.registerEventListeners();
         this.eventsManager.notifyContextCreatedEvent();
         this.phase = WebAppServletContext.ContextPhase.AFTER_INITIALIZER_NOTIFY_LISTENER;
         this.jacImpl.setContextStarted(true);
         this.registerSecurityConfigurations();
         this.filterManager.preloadFilters();
         this.loadServletsOnStartup();
      } finally {
         popEnvironment(thread, cl);
      }

      this.scheduleAsyncInitServlets();
   }

   private void registerSecurityConfigurations() throws DeploymentException {
      this.registerDenyUncoveredMethodsSet();
      this.registerSecurityRoles();
      this.deployServletSecurityConstraints();
      this.registerSecurityConstraint();
      this.deployPolicyAndRole();
      this.registerJaspicProvider();
      this.registerLoginConfig();
      this.registerAuthFilter();
      this.registerSecurityRoleRef();
   }

   private void scheduleAsyncInitServlets() {
      if (this.asyncInitServlets.isEmpty()) {
         this.asyncInitsStillRunning = false;
      } else {
         this.asyncInitsStillRunning = true;
         WorkManagerFactory.getInstance().getDefault().schedule(new AsyncInitRequest(this.asyncInitServlets));
      }

   }

   private void registerSecurityRoleRef() throws DeploymentException {
      ServletStubImpl stub;
      for(Iterator var1 = this.servletStubs.entrySet().iterator(); var1.hasNext(); stub.getSecurityHelper().resolveSubjects(this.securityManager)) {
         Map.Entry entry = (Map.Entry)var1.next();
         stub = (ServletStubImpl)entry.getValue();
         this.securityManager.registerRoleRefs(stub);
         String servletName = stub.getServletName();
         ServletBean servlet = this.module.getWebAppBean().lookupServlet(servletName);
         if (servlet != null) {
            this.securityManager.registerSecurityRoleRef(stub, servlet.getSecurityRoleRefs());
         }
      }

   }

   public static final Throwable getRootCause(ServletException se) {
      for(int depth = 0; se.getRootCause() instanceof ServletException && depth < 10; ++depth) {
         Throwable e = se.getRootCause();
         if (se == e) {
            break;
         }

         se = (ServletException)e;
      }

      return (Throwable)(se.getRootCause() == null ? se : se.getRootCause());
   }

   public ServletSecurityContext getSecurityContext() {
      return this.securityContext;
   }

   private void loadServletsOnStartup() throws DeploymentException {
      this.startedServletLoadSequences = true;

      try {
         Collection c = this.servletLoadSequences.tailMap(new Integer(0)).values();
         Iterator var2 = c.iterator();

         while(var2.hasNext()) {
            ArrayList l = (ArrayList)var2.next();
            String[] var4 = (String[])l.toArray(new String[l.size()]);
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String s = var4[var6];
               this.preloadServlet(s);
            }
         }
      } finally {
         this.startedServletLoadSequences = false;
      }

   }

   private void preloadServlet(String name) throws DeploymentException {
      ServletStubImpl stub = (ServletStubImpl)this.servletStubs.get(name);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": loading servlet on startup: " + name);
      }

      try {
         String uri = stub.getFilePath();
         RequestCallback rc = new ContextRequestCallback(this, uri);
         stub.prepareServlet(rc);
      } catch (ServletException var6) {
         Throwable e = getRootCause(var6);
         weblogic.i18n.logging.Loggable l = HTTPLogger.logServletFailedToPreloadOnStartupLoggable(name, this.contextName, e);
         l.log();
         if (!(e instanceof UnavailableException)) {
            throw new DeploymentException(l.getMessage(), e);
         }
      } catch (Exception var7) {
         weblogic.i18n.logging.Loggable l = HTTPLogger.logServletFailedToPreloadOnStartupLoggable(name, this.contextName, var7);
         l.log();
         throw new DeploymentException(l.getMessage(), var7);
      }

   }

   public void addAsyncInitServlet(AsyncInitServlet s) {
      this.asyncInitServlets.add(s);
   }

   public void addMimeMapping(String extension, String mimeType) {
      this.configManager.addMimeMapping(extension, mimeType);
   }

   public boolean isSSLRequired(String relativeURI, String method) {
      relativeURI = HttpParsing.ensureStartingSlash(relativeURI);
      return this.getSecurityManager().isSSLRequired(relativeURI, method);
   }

   public ServletStubImpl getServletStub(String urlPattern) {
      ServletStubImpl sstub = null;
      URLMatchHelper umh = (URLMatchHelper)this.servletMapping.get(urlPattern);
      if (umh != null) {
         sstub = umh.getServletStub();
      }

      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": servlet " + (sstub == null ? "not found" : "found") + " for the url-pattern: " + urlPattern);
      }

      return sstub;
   }

   synchronized void removeServletStub(ServletStubImpl stub, boolean makeUnavailable) {
      String name = stub.getServletName();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": removing servlet stub with name: " + name);
      }

      ServletStubImpl currentStub = (ServletStubImpl)this.servletStubs.get(name);
      if (currentStub != stub) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(this.getLogContext() + ": the stub is different, not destroying it");
         }

      } else {
         this.servletStubs.remove(name);
         Integer seq = (Integer)this.preloadServlets.remove(name);
         if (seq != null) {
            ((ArrayList)this.servletLoadSequences.get(seq)).remove(name);
         }

         stub.destroy();
         URLMapping newMapping = (URLMapping)((URLMapping)this.servletMapping.clone());
         Object[] values = newMapping.values();

         for(int i = 0; values != null && i < values.length; ++i) {
            URLMatchHelper umh = (URLMatchHelper)values[i];
            if (name.equals(umh.getServletStub().getServletName())) {
               if (makeUnavailable) {
                  newMapping.put(umh.getPattern(), new URLMatchHelper(umh.getPattern(), ServletStubImpl.getUnavailableStub(stub)));
               } else {
                  newMapping.remove(umh.getPattern());
               }
            }
         }

         this.servletMapping = newMapping;
      }
   }

   void registerServletStub(String name, ServletStubImpl sstub) {
      ServletStubImpl oldStub = (ServletStubImpl)this.servletStubs.put(name, sstub);
      if (oldStub != null) {
         oldStub.destroy();
      }

   }

   public boolean webflowCheckAccess(String uri, ServletRequestImpl req, ServletResponseImpl rsp) {
      uri = HttpParsing.ensureStartingSlash(uri);
      req.setAttribute("webflow_resource", uri);

      boolean status;
      try {
         status = this.securityManager.checkAccess(req, rsp, false, false);
      } catch (ServletException var6) {
         return false;
      } catch (SocketException var7) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(this.getLogContext(), var7);
         }

         status = false;
      } catch (IOException var8) {
         HTTPLogger.logIOException(this.getLogContext(), var8);
         status = false;
      }

      req.removeAttribute("webflow_resource");
      return status;
   }

   private String prependContextPath(String uri) {
      uri = HttpParsing.ensureStartingSlash(uri);
      if (this.contextPath.length() != 0) {
         uri = this.getContextPath() + uri;
      }

      return uri;
   }

   void execute(ServletRequestImpl req, ServletResponseImpl rsp) throws IOException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[3];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = rsp;
         }

         InstrumentationSupport.createDynamicJoinPoint(var8);
         InstrumentationSupport.preProcess(var8);
         var8.resetPostBegin();
      }

      label508: {
         label509: {
            label510: {
               label511: {
                  label512: {
                     label513: {
                        label514: {
                           try {
                              if (HTTPDebugLogger.isEnabled()) {
                                 HTTPDebugLogger.debug(this.getLogContext() + ": invoking servlet for : " + req);
                              }

                              Thread thread = Thread.currentThread();
                              ClassLoader cl = this.pushEnvironment(thread);

                              try {
                                 label493: {
                                    if (StringUtils.indexOfIgnoreCase(req.getRelativeUri(), "/WEB-INF") != 0 && StringUtils.indexOfIgnoreCase(req.getRelativeUri(), "/META-INF") != 0) {
                                       if (req.getSendRedirect()) {
                                          rsp.sendRedirect(rsp.encodeRedirectURL(req.getRedirectURI()));
                                          break label513;
                                       }

                                       if (!this.isStarted()) {
                                          rsp.sendError(503);
                                          break label514;
                                       }

                                       if (rsp.getStatus() != 200) {
                                          rsp.sendError(rsp.getStatus());
                                          break label493;
                                       }

                                       ServletStubImpl ss = req.getServletStub();
                                       if (ss == null) {
                                          rsp.sendError(404);
                                          break label508;
                                       }

                                       if (this.configManager.isWebAppSuspended()) {
                                          weblogic.i18n.logging.Loggable l = HTTPLogger.logServerSuspendedLoggable(this.toString(), registry.getServerMBean().getName());
                                          throw new UnavailableException(l.getMessage());
                                       }

                                       if (!this.checkPermissionOnPort(req, rsp)) {
                                          break label509;
                                       }

                                       if (this.asyncInitsStillRunning) {
                                          rsp.sendError(503);
                                          break label510;
                                       }

                                       this.securedExecute(req, rsp, true);
                                       break label511;
                                    }

                                    rsp.sendError(404);
                                    break label512;
                                 }
                              } catch (JspFileNotFoundException var19) {
                                 rsp.sendError(404);
                                 break label511;
                              } catch (MaxRequestParameterExceedException var20) {
                                 this.logError("Rejecting request since max request parameter limit exceeded " + this.getServer().getMaxRequestParameterCount());
                                 rsp.sendError(500);
                                 break label511;
                              } catch (Throwable var21) {
                                 this.handleThrowableFromInvocation(var21, req, rsp);
                                 break label511;
                              } finally {
                                 popEnvironment(thread, cl);
                              }
                           } catch (Throwable var23) {
                              if (var8 != null) {
                                 var8.th = var23;
                                 InstrumentationSupport.postProcess(var8);
                              }

                              throw var23;
                           }

                           if (var8 != null) {
                              InstrumentationSupport.postProcess(var8);
                           }

                           return;
                        }

                        if (var8 != null) {
                           InstrumentationSupport.postProcess(var8);
                        }

                        return;
                     }

                     if (var8 != null) {
                        InstrumentationSupport.postProcess(var8);
                     }

                     return;
                  }

                  if (var8 != null) {
                     InstrumentationSupport.postProcess(var8);
                  }

                  return;
               }

               if (var8 != null) {
                  InstrumentationSupport.postProcess(var8);
               }

               return;
            }

            if (var8 != null) {
               InstrumentationSupport.postProcess(var8);
            }

            return;
         }

         if (var8 != null) {
            InstrumentationSupport.postProcess(var8);
         }

         return;
      }

      if (var8 != null) {
         InstrumentationSupport.postProcess(var8);
      }

   }

   public void securedExecute(HttpServletRequest req, HttpServletResponse rsp, boolean applyAuthFilters) throws Throwable {
      doSecuredExecute(this, req, rsp, applyAuthFilters, this.configManager.isWebAppSuspending(), this.isSuspending());
   }

   private static boolean isResourceGroupSuspending(ServletInvocationContext context) {
      return context instanceof WebAppServletContext ? WebAppResourceGroupManagerInterceptor.isResourceGroupSuspending(((WebAppServletContext)context).getApplicationContext()) : false;
   }

   static void doSecuredExecute(ServletInvocationContext context, HttpServletRequest req, HttpServletResponse rsp, boolean applyAuthFilters, boolean suspending, boolean isContextSuspending) throws Throwable {
      HttpSession s = req.getSession(false);
      if (!isContainerUnavailable(context, req, rsp, suspending, isContextSuspending, s)) {
         if (context instanceof WebAppServletContext && ((WebAppServletContext)context).hasRequestListeners()) {
            ((WebAppServletContext)context).getEventsManager().notifyCDIRequestLifetimeEvent(req, true);
         }

         try {
            processSecuredExecute(context, req, rsp, applyAuthFilters, suspending, isContextSuspending, s);
         } finally {
            if (context instanceof WebAppServletContext && ((WebAppServletContext)context).hasRequestListeners()) {
               ((WebAppServletContext)context).getEventsManager().notifyCDIRequestLifetimeEvent(req, false);
            }

         }

      }
   }

   private static boolean isContainerUnavailable(ServletInvocationContext context, HttpServletRequest req, HttpServletResponse rsp, boolean suspending, boolean isContextSuspending, HttpSession s) throws Throwable {
      String failoverServerList;
      if ((suspending || WebAppPartitionManagerInterceptor.isPartitionSuspending() || isResourceGroupSuspending(context)) && s == null) {
         ClusterProvider clusterProvider = registry.getClusterProvider();
         if (clusterProvider != null && clusterProvider.shouldDetectSessionCompatiblity()) {
            failoverServerList = clusterProvider.getCompatibleFailoverServerList(((ServletRequestImpl)req).getServerChannel(), ((WebAppServletContext)context).getServer().getServerName());
            if (failoverServerList != null) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Receive new request when server is suspending. Send 503 with header X-WebLogic-Cluster-FailoverGroup-List:" + failoverServerList);
               }

               rsp.setHeader("X-WebLogic-Cluster-FailoverGroup-List", failoverServerList);
            }
         }

         weblogic.i18n.logging.Loggable l = HTTPLogger.logServerSuspendedLoggable(context.toString(), registry.getServerMBean().getName());
         throw new UnavailableException(l.getMessage());
      } else if (isContextSuspending && s == null) {
         rsp.sendError(503);
         return true;
      } else {
         if (s != null && registry.getClusterProvider() != null && registry.getClusterProvider().shouldDetectSessionCompatiblity()) {
            try {
               if (context instanceof WebAppServletContext) {
                  WebAppServletContext servletCtx = (WebAppServletContext)context;
                  servletCtx.getSessionContext().detectSessionCompatiblity(s, req);
               }
            } catch (IncompatibleSessionSerializationException var8) {
               failoverServerList = registry.getClusterProvider().getCompatibleFailoverServerList(((ServletRequestImpl)req).getServerChannel(), ((WebAppServletContext)context).getServer().getServerName());
               if (failoverServerList != null) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Found incompatible session for request: ");
                     HTTPDebugLogger.debug(req.toString());
                     HTTPDebugLogger.debug("Send 503 with header X-WebLogic-Cluster-FailoverGroup-List:" + failoverServerList);
                  }

                  rsp.setHeader("X-WebLogic-Cluster-FailoverGroup-List", failoverServerList);
                  throw new UnavailableException("Found incompatible session");
               }
            }
         }

         return false;
      }
   }

   private static void processSecuredExecute(ServletInvocationContext context, HttpServletRequest req, HttpServletResponse rsp, boolean applyAuthFilters, boolean suspending, boolean isContextSuspending, HttpSession s) throws Throwable {
      if (context.getSecurityManager().checkAccess(req, rsp, applyAuthFilters, false)) {
         if (s != null) {
            int count = ((SessionSecurityData)s).getConcurrentRequestCount();
            if (maxConcurrentRequestsAllowed != -1 && count > maxConcurrentRequestsAllowed) {
               context.logError("Rejecting request since concurrent requests allowable limit exceeded :" + maxConcurrentRequestsAllowed);
               rsp.sendError(500);
               return;
            }
         }

         ServletObjectsFacade requestFacade = context.getSecurityContext().getRequestFacade();
         if (!doNotSendContinueHeader && "HTTP/1.1".equals(requestFacade.getProtocol(req)) && context.getSecurityManager().getAuthMethod() != null && !context.getSecurityManager().isFormAuth() && !requestFacade.isInternalDispatch(req) && "100-continue".equalsIgnoreCase(requestFacade.getExpectHeader(req))) {
            requestFacade.send100ContinueResponse(req);
         }

         SubjectHandle subject = SecurityModule.getCurrentUser(context.getSecurityContext(), req);
         if (subject == null) {
            subject = WebAppSecurity.getProvider().getAnonymousSubject();
         } else {
            requestFacade.getHttpAccountingInfo(req).setRemoteUser(subject.getUsername());
         }

         WebAppSecurity securityManager = context.getSecurityManager();
         HttpServletRequest wrappedReq = securityManager.getWrappedRequest(req);
         if (wrappedReq != null) {
            req = wrappedReq;
         }

         HttpServletResponse wrappedRsp = securityManager.getWrappedResponse(req, rsp);
         if (wrappedRsp != null) {
            rsp = wrappedRsp;
         }

         PrivilegedAction action = new ServletInvocationAction(req, rsp, context, requestFacade.getServletStub(req));
         Throwable e = (Throwable)subject.run((PrivilegedAction)action);
         if (e != null) {
            throw e;
         } else {
            context.getSecurityManager().postInvoke(req, rsp, subject);
         }
      }
   }

   public void handleThrowableFromInvocation(Throwable e, ServletRequestImpl req, ServletResponseImpl rsp) throws IOException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[4];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = e;
            var10000[2] = req;
            var10000[3] = rsp;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      label299: {
         try {
            if (this.configManager.isWebAppSuspended()) {
               rsp.sendError(503);
               break label299;
            }

            Throwable ee = e;
            Throwable nested;
            if (!(e instanceof ServletException)) {
               if (e instanceof SocketException) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logException(this.getLogContext(), e);
                  }
               } else if (e instanceof IOException) {
                  if (HTTPDebugLogger.shouldLogIOException((IOException)e)) {
                     HTTPLogger.logIOException(this.getLogContext(), e);
                  }
               } else if (e instanceof NestedRuntimeException) {
                  nested = ((NestedRuntimeException)e).getNestedException();
                  if (nested instanceof SocketException) {
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPLogger.logException(this.getLogContext(), e);
                     }
                  } else if (nested instanceof IOException && HTTPDebugLogger.shouldLogIOException((IOException)nested)) {
                     HTTPLogger.logIOException(this.getLogContext(), e);
                  }
               } else if (e instanceof ServletNestedRuntimeException) {
                  nested = e.getCause();
                  if (nested instanceof SocketException) {
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPLogger.logException(this.getLogContext(), e);
                     }
                  } else if (nested instanceof IOException) {
                     if (HTTPDebugLogger.shouldLogIOException((IOException)nested)) {
                        HTTPLogger.logIOException(this.getLogContext(), e);
                     }
                  } else {
                     HTTPLogger.logException(this.getLogContext(), e);
                  }
               } else {
                  HTTPLogger.logException(this.getLogContext(), e);
               }
            } else {
               e = getRootCause((ServletException)e);
               registry.getManagementProvider().handleOutOfMemory(e);
               if (this.configManager.isWebAppSuspending() && e instanceof UnavailableException || e instanceof UnavailableException && registry.getClusterProvider() != null && registry.getClusterProvider().shouldDetectSessionCompatiblity()) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logRootCause(addUpRequestInfo(req, this.getLogContext()), e);
                  }
               } else if (e instanceof SocketException) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logRootCause(addUpRequestInfo(req, this.getLogContext()), e);
                  }
               } else if (e instanceof IOException) {
                  if (HTTPDebugLogger.shouldLogIOException((IOException)e)) {
                     HTTPLogger.logRootCause(addUpRequestInfo(req, this.getLogContext()), e);
                  }
               } else {
                  HTTPLogger.logRootCause(addUpRequestInfo(req, this.getLogContext()), e);
               }

               nested = ((ServletException)ee).getRootCause();
               req.setAttribute("javax.servlet.error.exception", nested == null ? ee : nested);
            }

            if (!rsp.isCommitted()) {
               try {
                  this.errorManager.handleException(req, rsp, ee);
               } catch (SocketException var10) {
                  if (HTTPDebugLogger.isEnabled()) {
                     throw var10;
                  }
               } catch (IOException var11) {
                  if (HTTPDebugLogger.shouldLogIOException((IOException)e)) {
                     throw var11;
                  }
               }
            } else if (!rsp.isFlushOk() && e instanceof IOException) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("WebAppServletContext.handleThrowableFromInvocation() called for commited response but the underlying output stream not flushed ok due to an IOException.");
               }

               rsp.syncSession();
               rsp.disableKeepAlive();
               rsp.cleanupRequest(e);
            }
         } catch (Throwable var12) {
            if (var6 != null) {
               var6.th = var12;
               InstrumentationSupport.postProcess(var6);
            }

            throw var12;
         }

         if (var6 != null) {
            InstrumentationSupport.postProcess(var6);
         }

         return;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   private boolean checkPermissionOnPort(ServletRequestImpl req, ServletResponseImpl rsp) throws IOException {
      return req.getConnection().isInternalDispatch() ? true : registry.getContainerSupportProvider().hasPermissionOnChannel(req, rsp);
   }

   static boolean isAbsoluteURL(String url) {
      String[] pair = StringUtils.split(url, ':');
      if (pair[0] != null && pair[1] != null) {
         return pair[0].equals("mailto") || pair[1].startsWith("//");
      } else {
         return false;
      }
   }

   ServletStubImpl resolveDirectRequest(ServletRequestImpl req) {
      String relUri = req.getRelativeUri();
      URLMatchHelper umh = this.resolveRequest(relUri);
      if (umh.isDefaultServlet()) {
         req.setCheckIndexFile(true);
      }

      req.setServletPathAndPathInfo(relUri, umh.getServletPath(relUri));
      req.setHttpServletMapping(umh.getHttpServletMapping(relUri));
      ServletStubImpl sstub = umh.getServletStub();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Servlet resource: " + sstub + " is mapped to request: " + req.toStringSimple());
      }

      return sstub;
   }

   public ServletStubImpl resolveForwardedRequest(ServletRequestImpl req, ServletRequest wrapper) {
      String relUri = req.getRelativeUri();
      URLMatchHelper umh = this.resolveRequest(relUri);
      if (umh.isDefaultServlet()) {
         ServletStubImpl servletStub = this.getIndexServletStub(relUri, req, wrapper);
         if (servletStub != null) {
            return servletStub;
         }
      }

      req.setServletPathAndPathInfo(relUri, umh.getServletPath(relUri));
      req.setHttpServletMapping(umh.getHttpServletMapping(relUri));
      return umh.getServletStub();
   }

   String resolveForwardServletPath(ServletRequestImpl req) {
      String relUri = req.getRelativeUri();
      URLMatchHelper umh = this.resolveRequest(relUri);
      return umh.getServletPath(relUri);
   }

   ServletStubImpl resolveIncludedRequest(ServletRequestImpl req, ServletRequest wrapper) {
      String relUri = (String)wrapper.getAttribute("javax.servlet.include.request_uri");
      if (relUri != null && (this.httpServer.getUriPath() != null && this.httpServer.getUriPath().length() > 1 || !this.isDefaultContext()) && relUri.startsWith(req.getContext().getContextPath())) {
         relUri = relUri.substring(req.getContext().getContextPath().length());
      }

      URLMatchHelper umh = this.resolveRequest(relUri);
      wrapper.setAttribute("javax.servlet.include.mapping", umh.getHttpServletMapping(relUri));
      this.setIncludeServletPath(relUri, wrapper, umh.getServletPath(relUri));
      return umh.getServletStub();
   }

   private void setIncludeServletPath(String relUri, ServletRequest wrapper, String servletPath) {
      String pathInfo = ServletRequestImpl.computePathInfo(relUri, servletPath);
      wrapper.setAttribute("javax.servlet.include.servlet_path", servletPath);
      wrapper.setAttribute("javax.servlet.include.path_info", pathInfo);
   }

   ServletStubImpl getIndexServletStub(String URI, ServletRequestImpl req, ServletRequest wrapper) {
      String indexURI = this.findIndexFile(URI);
      if (indexURI == null) {
         return null;
      } else if ((!this.isDefaultContext() || URI.length() != 0) && !StringUtils.endsWith(URI, '/')) {
         String reqUri = req.getRequestURI();
         int paramsIndex = reqUri.indexOf(59);
         StringBuffer redirectURI = new StringBuffer();
         if (paramsIndex != -1) {
            redirectURI.append(HttpParsing.ensureEndingSlash(reqUri.substring(0, paramsIndex)));
            redirectURI.append(reqUri.substring(paramsIndex));
         } else {
            redirectURI.append(HttpParsing.ensureEndingSlash(reqUri));
         }

         String queryString = req.getQueryString();
         if (queryString != null) {
            redirectURI.append('?').append(queryString);
         }

         if (DEBUG_URL_RES.isDebugEnabled()) {
            DEBUG_URL_RES.debug(this.getLogContext() + ": redirecting " + req + " to :" + redirectURI.toString());
         }

         req.setRedirectURI(redirectURI.toString());
         return null;
      } else {
         req.initFromRequestURI(this.prependContextPath(indexURI));
         ServletStubImpl servletStub = this.resolveDirectRequest(req);
         if (servletStub.getClassName().equals("weblogic.servlet.proxy.HttpProxyServlet") || servletStub.getClassName().equals("weblogic.servlet.proxy.HttpClusterServlet")) {
            req.initFromRequestURI(this.prependContextPath(URI));
            servletStub = this.resolveDirectRequest(req);
         }

         return servletStub;
      }
   }

   WebAppHelper getHelper() {
      return this.war;
   }

   private URLMatchHelper resolveRequest(String relUri) {
      if (DEBUG_URL_RES.isDebugEnabled()) {
         DEBUG_URL_RES.debug(this.getLogContext() + ": resolving request with relUri: " + relUri);
      }

      URLMatchHelper umh = null;
      if (relUri.isEmpty()) {
         umh = (URLMatchHelper)this.servletMapping.get("/\"\"");
      } else {
         umh = (URLMatchHelper)this.servletMapping.get(relUri);
      }

      if (umh == null) {
         int jwspos;
         if (!WebAppConfigManager.isCaseInsensitive()) {
            jwspos = relUri.indexOf(".jws/");
         } else {
            jwspos = StringUtils.indexOfIgnoreCase(relUri, ".jws/");
         }

         if (jwspos != -1) {
            String jwsServletPath = relUri.substring(0, jwspos + 4);
            umh = (URLMatchHelper)this.servletMapping.get(jwsServletPath);
         }
      }

      if (umh == null) {
         umh = this.defaultURLMatchHelper;
      }

      return umh;
   }

   boolean isServedByDefaultServlet(String relUri) {
      return this.resolveRequest(relUri) == this.defaultURLMatchHelper;
   }

   private String findIndexFile(String URI) {
      List localIndexFiles = this.configManager.getWelcomeFiles();
      if (localIndexFiles.isEmpty()) {
         return null;
      } else {
         URI = HttpParsing.ensureEndingSlash(URI);
         WarSource s = this.getResourceAsSource(URI);
         if (s == null) {
            return null;
         } else {
            if (s.isDirectory()) {
               Iterator var4 = localIndexFiles.iterator();

               while(var4.hasNext()) {
                  String indexFile = (String)var4.next();
                  String indexURI = URI + indexFile;
                  Source indexSource = this.getResourceAsSource(indexURI);
                  if (indexSource != null) {
                     return indexURI;
                  }

                  URLMatchHelper umh = (URLMatchHelper)this.servletMapping.get(indexURI);
                  if (umh != null && umh.isIndexServlet()) {
                     return indexURI;
                  }
               }
            }

            return null;
         }
      }
   }

   public String getClasspath() {
      return this.war.getClassFinder().getClassPath();
   }

   public String getFullClasspath() {
      GenericClassLoader gcl = this.getTagFileHelper().getTagFileClassLoader();
      String appClasspath = gcl.getClassPath();
      return FilenameEncoder.cleanClasspath(appClasspath);
   }

   public String getLogContext() {
      return this.logContext == null ? this.toString() : this.logContext;
   }

   public WebAppModule getWebAppModule() {
      return this.module;
   }

   public String getModuleName() {
      return this.module.getModuleName();
   }

   public String getAppName() {
      if (this.appCtx == null) {
         return "";
      } else {
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         return dmb == null ? "" : dmb.getApplicationName();
      }
   }

   public String getAppDisplayName() {
      if (this.appCtx == null) {
         return "";
      } else {
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         return dmb == null ? null : ApplicationVersionUtils.getDisplayName(dmb);
      }
   }

   public SessionContext getSessionContext() {
      return this.sessionContext;
   }

   public JspResourceProvider getJspResourceProvider() {
      return this.jspResourceProvider;
   }

   public AsyncContextTimer getAsyncContextTimer() {
      return this.asyncContextTimer;
   }

   private void initializeSecurity() throws DeploymentException {
      this.securityManager = WebAppSecurity.createWebAppSecurity(this.appCtx, this.getSecurityContext(), this.getApplicationId(), this.contextPath, this.getDocroot(), new ExternalRoleCheckerManager(this.module));
      this.registerDenyUncoveredMethodsSet();
      WeblogicWebAppBean wlwebapp = this.module.getWlWebAppBean();
      if (wlwebapp != null) {
         JASPICProviderBean jaspicProviderBean = wlwebapp.getJASPICProvider();
         if (jaspicProviderBean != null) {
            ((DescriptorBean)jaspicProviderBean).addBeanUpdateListener(this.securityManager.getBeanUpdateListener());
         }
      }

   }

   public File getRootTempDir() {
      return this.configManager.getInternalTempDir();
   }

   public String getTempPath() {
      return this.configManager.getTempPath();
   }

   public String getDocroot() {
      return this.docroot;
   }

   private void setContextPath(String path) {
      if (this.httpServer.getDefaultWebAppContextRoot() != null && this.httpServer.getDefaultWebAppContextRoot().equals(path)) {
         path = "";
      }

      this.defaultContext = path.equals("/") || path.equals("");
      if (this.defaultContext) {
         this.contextPath = "";
      } else {
         if (!path.startsWith("/")) {
            path = "/" + path;
         }

         if (path.length() > 2 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
         }

         this.contextPath = path;
      }

      this.configuredContextPath = this.contextPath;
      String partitionUriPath = this.httpServer.getUriPath();
      if (partitionUriPath != null && !partitionUriPath.equals("/")) {
         this.contextPath = partitionUriPath + this.contextPath;
      }

   }

   void precompileJspsOnUpdate(String jspFile) {
      ((War.ResourceFinder)this.getResourceFinder(this.docroot)).clearCache(jspFile);
      if (this.getJSPManager().createJspConfig().getPageCheckSecs() == -1L) {
         Thread thread = Thread.currentThread();
         ClassLoader cl = this.pushEnvironment(thread);

         try {
            if (!jspFile.startsWith("/")) {
               jspFile = "/" + jspFile;
            }

            String jspclassname = JSPServlet.uri2classname(this.getJSPManager().getJspcPkgPrefix(), jspFile);
            Iterator var5 = this.servletStubs.values().iterator();

            while(true) {
               JspStub jspStub;
               do {
                  ServletStubImpl stubImpl;
                  do {
                     if (!var5.hasNext()) {
                        return;
                     }

                     stubImpl = (ServletStubImpl)var5.next();
                  } while(!(stubImpl instanceof JspStub));

                  jspStub = (JspStub)stubImpl;
               } while(!jspStub.getClassName().equals(jspclassname));

               try {
                  RequestCallback rc = new ContextRequestCallback(this, jspFile);
                  jspStub.reloadJSPOnUpdate(rc);
                  return;
               } catch (Exception var13) {
                  weblogic.i18n.logging.Loggable l = HTTPLogger.logFailureInCompilingJSPLoggable(this.getAppName(), this.getModuleName(), jspFile, var13);
                  l.log();
               }
            }
         } finally {
            popEnvironment(thread, cl);
         }
      }
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public String getConfiguredContextPath() {
      return this.configuredContextPath;
   }

   public String getFullCtxName() {
      return this.fullCtxName;
   }

   public String getVersionId() {
      return this.versionId;
   }

   public boolean isAdminMode() {
      return this.adminMode;
   }

   public void setAdminMode(boolean adminMode) {
      this.adminMode = adminMode;
   }

   public ApplicationContextInternal getApplicationContext() {
      return this.appCtx;
   }

   public String getApplicationName() {
      return this.module.getApplicationName();
   }

   public String getApplicationId() {
      return this.module.getApplicationId();
   }

   public String getName() {
      return this.contextName;
   }

   public String getId() {
      return this.module.getId();
   }

   public String getURI() {
      return this.module.getModuleURI();
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.invocationContext;
   }

   private ComponentInvocationContext createComponentInvocationContext() {
      return ComponentInvocationContextManager.getInstance().createComponentInvocationContext(this.appCtx.getPartitionName(), this.module.getApplicationName(), ApplicationVersionUtils.getVersionId(this.getApplicationId()), this.module.getModuleName(), this.module.getModuleName());
   }

   public String getSecurityRealmName() {
      String name = null;
      if (this.appCtx != null) {
         name = this.appCtx.getApplicationSecurityRealmName();
      }

      return name == null ? WebAppSecurity.getProvider().getDefaultRealmName() : name;
   }

   public War getWarInstance() {
      return this.war;
   }

   public WebAppComponentMBean getMBean() {
      return this.compMBean;
   }

   public WebAppRuntimeMBeanImpl getRuntimeMBean() {
      return this.runtime;
   }

   public synchronized ServletRuntimeMBean[] getServletRuntimeMBeans() {
      List l = new ArrayList();
      Iterator var2 = this.servletStubs.values().iterator();

      while(var2.hasNext()) {
         ServletStubImpl sstub = (ServletStubImpl)var2.next();
         if (sstub.getRuntimeMBean() != null) {
            l.add(sstub.getRuntimeMBean());
         }
      }

      return (ServletRuntimeMBean[])l.toArray(new ServletRuntimeMBean[l.size()]);
   }

   private void bounceClassLoader() {
      this.classLoader = this.module.bounceClassLoader();
      this.initTagFileClassLoader();
   }

   private void initTagFileClassLoader() {
      this.tagFileHelper.initClassLoader(this.war.getClassFinder(), this.classLoader);
   }

   public final ClassLoader getServletClassLoader() {
      return this.classLoader;
   }

   ClassLoader reloadServletClassLoader() {
      synchronized(this.reloadServletClassLoaderLock) {
         ClassLoader oldWebAppCL = Thread.currentThread().getContextClassLoader();
         if (oldWebAppCL != this.getServletClassLoader()) {
            return this.classLoader;
         } else {
            AuthenticatedSubject deploymentInitiator = this.appCtx.getDeploymentInitiator();

            try {
               this.cleanupContainerBeforeReload();
               this.cleanupExtensionsBeforeReload();
               this.bounceClassLoader();
               this.initContainerAfterReload();
               this.prePrepareExtensionsAfterReload();
               this.prepareContainerAfterReload();
               this.postPrepareExtensionsAfterReload();
               this.activateContainerAfterReload();
               this.postActivateExtensionsAfterReload();
               this.startExtensionsAfterReload();
               this.startContainerAfterReload(oldWebAppCL);
            } catch (DeploymentException var10) {
            } finally {
               this.appCtx.setDeploymentInitiator(deploymentInitiator);
            }

            return this.classLoader;
         }
      }
   }

   private void startContainerAfterReload(ClassLoader oldWebAppCL) {
      this.removeTransientAttributes(oldWebAppCL);
      this.sessionContext.forceToConvertAllSessionAttributes();
      Thread thread = Thread.currentThread();
      oldWebAppCL = this.pushEnvironment(thread);

      try {
         this.initContainerInitializers();
         this.eventsManager.registerEventListeners();
         this.eventsManager.notifyContextCreatedEvent();
      } catch (DeploymentException var7) {
      } finally {
         popEnvironment(thread, oldWebAppCL);
      }

      this.module.clearTemporaryClassLoader();
      this.jacImpl.setContextStarted(true);
      this.phase = WebAppServletContext.ContextPhase.START;
      this.war.cleanupClassInfos();
   }

   private void startExtensionsAfterReload() throws ModuleException {
      this.module.startModuleExtensionsForReload();
   }

   private void postActivateExtensionsAfterReload() throws DeploymentException {
      this.module.postActivateModuleExtensionsForReload();
      this.module.activateAppDeploymentPostprocessorForReload();
   }

   private void activateContainerAfterReload() throws DeploymentException {
      try {
         this.module.processAnnotations(true);
         this.initializeComponentCreator();
      } catch (DeploymentException var2) {
         HTTPLogger.logAnnotationProcessingFailed(this.getDocroot(), var2.getMessage(), var2);
      }

      this.compEnv.activate();
   }

   private void postPrepareExtensionsAfterReload() throws DeploymentException {
      this.module.postPrepareModuleExtensionsForReload();
      this.module.prepareAppDeploymentPostprocessorForReload();
      this.module.activateAppDeploymentPreprocessorForReload();
      this.module.preActivateModuleExtensionsForReload();
   }

   private void prepareContainerAfterReload() throws DeploymentException {
      this.compEnv.prepare();
      ModuleExtensionContext moduleExtensionContext = this.module.getModuleExtensionContext();
      if (moduleExtensionContext != null) {
         PojoEnvironmentBean envBean = moduleExtensionContext.getPojoEnvironmentBean();
         if (envBean != null) {
            Iterator var3 = moduleExtensionContext.getEnvironments().iterator();

            while(var3.hasNext()) {
               Environment env = (Environment)var3.next();
               env.contributeEnvEntries(envBean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
            }
         }
      }

   }

   private void prePrepareExtensionsAfterReload() throws DeploymentException {
      this.module.prepareAppDeploymentPreprocessorForReload();
      this.module.prePrepareModuleExtensionsForReload();
   }

   private void initContainerAfterReload() throws DeploymentException {
      this.phase = WebAppServletContext.ContextPhase.RELOAD;
      this.compEnv.cleanup();
      this.compEnv.destroy();
      this.compEnv = new CompEnv(this);
      this.appCtx.setDeploymentInitiator(SecurityManager.getCurrentSubject(KERNEL_ID));
   }

   private void cleanupExtensionsBeforeReload() {
      this.module.deactivateModuleExtensionsForReload();
      this.module.deactivateAppDeploymentExtensionsForReload();
      this.module.unprepareModuleExtensionsForReload();
      this.module.unprepareAppDeploymentExtensionsForReload();
   }

   private void cleanupContainerBeforeReload() {
      this.eventsManager.notifyContextDestroyedEvent();
      this.attributes.remove("com.sun.faces.sunJsfJs");
      BeanELResolverCachePurger.purgeCache(this.classLoader);
      ResourceBundle.clearCache(this.classLoader);
   }

   public final TagFileHelper getTagFileHelper() {
      return this.tagFileHelper;
   }

   public synchronized void addClassPath(String cp) {
      this.war.addClassPath(cp);
   }

   public Context getEnvironmentContext() {
      return this.compEnv.getEnvironmentContext();
   }

   public WebAppSecurity getSecurityManager() {
      SecurityServiceManager.checkKernelPermission();
      return this.securityManager;
   }

   WebAppSecurity getSecurityManagerWithPrivilege() {
      return (WebAppSecurity)AccessController.doPrivileged(new PrivilegedAction() {
         public WebAppSecurity run() {
            return WebAppServletContext.this.getSecurityManager();
         }
      });
   }

   void activate() throws DeploymentException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": activating ...");
      }

      this.initializeComponentCreator();
      this.activateFromDescriptors();
      this.compEnv.activate();
      this.sessionContext.initialize(this);
   }

   void start() throws DeploymentException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": starting ...");
      }

      ComponentInvocationContextManager cicManager = WebServerRegistry.getInstance().getContainerSupportProvider().getComponentInvocationContextManager();
      ComponentInvocationContext cic = this.getComponentInvocationContext();
      ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
      Throwable var4 = null;

      try {
         this.securityManager.startDeployment();
         this.preloadResources();
         this.securityManager.endDeployment();
         this.verifyServletStubs();
         this.verifyServletMappings();
         this.sessionContext.startTimers();
         this.phase = WebAppServletContext.ContextPhase.START;
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   void stop() {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + ": stopping ...");
      }

      this.phase = WebAppServletContext.ContextPhase.STOP;
   }

   public void setContextPhase(ContextPhase contextPhase) {
      this.phase = contextPhase;
   }

   public boolean isSuspending() {
      return this.phase == WebAppServletContext.ContextPhase.SUSPENDING;
   }

   public boolean isStarted() {
      return this.phase == WebAppServletContext.ContextPhase.START || this.isSuspending();
   }

   public boolean isStopped() {
      return this.phase == WebAppServletContext.ContextPhase.STOP;
   }

   public boolean isInternalApp() {
      return this.module.isInternalApp();
   }

   public boolean isInternalUtilitiesWebApp() {
      return this.module.isInternalUtilitiesWebApp();
   }

   public boolean isInternalUtilitiesWebSvcs() {
      return this.module.isInternalUtilitiesWebSvcs();
   }

   public boolean isInternalWSATApp() {
      return this.module.isInternalWSATApp();
   }

   public boolean isInternalSAMLApp() {
      return this.module.isInternalSAMLApp();
   }

   public boolean isOnDemandDisplayRefresh() {
      return this.onDemandDisplayRefresh;
   }

   public synchronized void destroy() {
      if (!this.isInternalApp() || WebAppConfigManager.isServerShutDown() || !this.invocationContext.isGlobalRuntime()) {
         this.asyncInitsStillRunning = false;
         Thread thread = Thread.currentThread();
         ClassLoader cl = this.pushEnvironment(thread);

         try {
            this.getSessionContext().destroy(WebAppConfigManager.isServerShutDown());
            this.destroyServlets();
            this.jspServletStub = null;
            this.jspxServletStub = null;
            this.removeBeanUpdateListeners();
            this.filterManager.destroyFilters();
            this.eventsManager.notifyContextDestroyedEvent();
            this.compEnv.cleanup();
            BeanELResolverCachePurger.purgeCache(this.classLoader);
         } finally {
            popEnvironment(thread, cl);
         }

         try {
            if (this.runtime != null) {
               this.removeAttribute("weblogic.servlet.WebAppComponentRuntimeMBean");
               this.runtime.unregister();
               this.runtime = null;
            }
         } catch (ManagementException var8) {
            HTTPLogger.logErrorUnregisteringWebAppRuntime(this.runtime.getObjectName(), var8);
         }

         try {
            String partitionNameToUse = this.appCtx.getPartitionName();
            if ("DOMAIN".equals(partitionNameToUse)) {
               partitionNameToUse = null;
            }

            ServletAccessorHelper.removeAccessor(registry.getManagementProvider(), partitionNameToUse, this.httpServer.getName(), this.contextPath);
         } catch (ManagementException var7) {
            HTTPLogger.logErrorRemoveWLDFDataAccessRuntimeMBean(this.getLogContext(), var7);
         }

         this.getSecurityManager().undeploy();
         if (!this.module.hasEjbInWar()) {
            this.destroyCompEnv();
         }

         this.asyncContextTimer.destroy();
         this.tagFileHelper.close();
         this.classLoader = null;
         this.contextManager = null;
      }
   }

   void destroyCompEnv() {
      this.compEnv.destroy();
   }

   private void removeBeanUpdateListeners() {
      WeblogicWebAppBean wlwebapp = this.module.getWlWebAppBean();
      if (wlwebapp != null) {
         JASPICProviderBean jaspicProviderBean = wlwebapp.getJASPICProvider();
         if (jaspicProviderBean != null) {
            ((DescriptorBean)jaspicProviderBean).removeBeanUpdateListener(this.securityManager.getBeanUpdateListener());
         }
      }

   }

   private void destroyServlets() {
      Collection c = this.servletLoadSequences.tailMap(new Integer(0)).values();
      ArrayList[] elements = (ArrayList[])c.toArray(new ArrayList[c.size()]);

      for(int i = elements.length - 1; i >= 0; --i) {
         ArrayList l = elements[i];
         Iterator var5 = l.iterator();

         while(var5.hasNext()) {
            String name = (String)var5.next();
            ServletStubImpl stub = (ServletStubImpl)this.servletStubs.remove(name);
            if (stub != null) {
               stub.destroy();
            }
         }
      }

      this.servletLoadSequences.clear();
      this.preloadServlets.clear();
      if (!this.servletStubs.isEmpty()) {
         Iterator i = this.servletStubs.values().iterator();

         while(i.hasNext()) {
            ServletStubImpl stub = (ServletStubImpl)i.next();
            i.remove();
            stub.destroy();
         }
      }

      this.servletStubs.clear();
   }

   public boolean isResourceStale(String resource, long sinceWhen, String releaseBuildVersion, String buildTimeZone) {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[5];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = resource;
            var10000[2] = InstrumentationSupport.convertToObject(sinceWhen);
            var10000[3] = releaseBuildVersion;
            var10000[4] = buildTimeZone;
         }

         InstrumentationSupport.createDynamicJoinPoint(var8);
         InstrumentationSupport.preProcess(var8);
         var8.resetPostBegin();
      }

      boolean var14;
      label319: {
         label320: {
            label321: {
               label322: {
                  label323: {
                     label324: {
                        label325: {
                           label326: {
                              label327: {
                                 try {
                                    String version = registry.getContainerSupportProvider().getReleaseBuildVersion();
                                    if (HTTPDebugLogger.isEnabled()) {
                                       weblogic.i18n.logging.Loggable l = HTTPLogger.logCompareVersionLoggable(this.getLogContext(), releaseBuildVersion, version);
                                       HTTPDebugLogger.debug(l.getMessage());
                                    }

                                    if (!NO_VERSION_CHECK && !version.equals(releaseBuildVersion)) {
                                       HTTPLogger.logServerVersionMismatchForJSPisStale(this.getLogContext(), resource, releaseBuildVersion, version);
                                       var14 = true;
                                       break label322;
                                    }

                                    if (this.jspManager.getPageCheckSeconds() < 0 && !this.jspManager.isJspPrecompileEnabled()) {
                                       var14 = false;
                                       break label323;
                                    }

                                    if (this.jspManager.getResourceProviderClass() != null) {
                                       if (this.jspResourceProvider == null) {
                                          var14 = true;
                                          break label324;
                                       }

                                       try {
                                          var14 = sinceWhen < this.jspResourceProvider.getLastModified(resource);
                                          break label325;
                                       } catch (Exception var11) {
                                          var14 = true;
                                          break label321;
                                       }
                                    }

                                    WarSource s = this.getResourceAsSource(resource, true);
                                    if (s == null) {
                                       var14 = true;
                                       break label326;
                                    }

                                    if (s.isFromArchive() || !s.isFromLibrary() && this.isArchived) {
                                       if (this.isResourceTimeStale(s, sinceWhen, buildTimeZone)) {
                                          HTTPLogger.logJSPisStale(this.getLogContext(), resource);
                                          var14 = true;
                                          break label319;
                                       }

                                       var14 = false;
                                       break label320;
                                    }

                                    if (!this.jspManager.isStrictStaleCheck()) {
                                       var14 = sinceWhen != s.lastModified() + 2000L;
                                       break label327;
                                    }

                                    var14 = sinceWhen < s.lastModified();
                                 } catch (Throwable var12) {
                                    if (var8 != null) {
                                       var8.th = var12;
                                       var8.ret = InstrumentationSupport.convertToObject(false);
                                       InstrumentationSupport.createDynamicJoinPoint(var8);
                                       InstrumentationSupport.postProcess(var8);
                                    }

                                    throw var12;
                                 }

                                 if (var8 != null) {
                                    var8.ret = InstrumentationSupport.convertToObject(var14);
                                    InstrumentationSupport.createDynamicJoinPoint(var8);
                                    InstrumentationSupport.postProcess(var8);
                                 }

                                 return var14;
                              }

                              if (var8 != null) {
                                 var8.ret = InstrumentationSupport.convertToObject(var14);
                                 InstrumentationSupport.createDynamicJoinPoint(var8);
                                 InstrumentationSupport.postProcess(var8);
                              }

                              return var14;
                           }

                           if (var8 != null) {
                              var8.ret = InstrumentationSupport.convertToObject(var14);
                              InstrumentationSupport.createDynamicJoinPoint(var8);
                              InstrumentationSupport.postProcess(var8);
                           }

                           return var14;
                        }

                        if (var8 != null) {
                           var8.ret = InstrumentationSupport.convertToObject(var14);
                           InstrumentationSupport.createDynamicJoinPoint(var8);
                           InstrumentationSupport.postProcess(var8);
                        }

                        return var14;
                     }

                     if (var8 != null) {
                        var8.ret = InstrumentationSupport.convertToObject(var14);
                        InstrumentationSupport.createDynamicJoinPoint(var8);
                        InstrumentationSupport.postProcess(var8);
                     }

                     return var14;
                  }

                  if (var8 != null) {
                     var8.ret = InstrumentationSupport.convertToObject(var14);
                     InstrumentationSupport.createDynamicJoinPoint(var8);
                     InstrumentationSupport.postProcess(var8);
                  }

                  return var14;
               }

               if (var8 != null) {
                  var8.ret = InstrumentationSupport.convertToObject(var14);
                  InstrumentationSupport.createDynamicJoinPoint(var8);
                  InstrumentationSupport.postProcess(var8);
               }

               return var14;
            }

            if (var8 != null) {
               var8.ret = InstrumentationSupport.convertToObject(var14);
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.postProcess(var8);
            }

            return var14;
         }

         if (var8 != null) {
            var8.ret = InstrumentationSupport.convertToObject(var14);
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.postProcess(var8);
         }

         return var14;
      }

      if (var8 != null) {
         var8.ret = InstrumentationSupport.convertToObject(var14);
         InstrumentationSupport.createDynamicJoinPoint(var8);
         InstrumentationSupport.postProcess(var8);
      }

      return var14;
   }

   public boolean isResourceTimeStale(Source s, long sinceWhen, String buildTimeZone) {
      if (s == null) {
         return true;
      } else {
         long buildTime = s.lastModified();
         if (buildTimeZone != null) {
            String runTimeZone = TimeZone.getDefault().getID();
            if (!buildTimeZone.equals(runTimeZone)) {
               GregorianCalendar buildTimeCal = new GregorianCalendar(TimeZone.getTimeZone(buildTimeZone));
               GregorianCalendar runTimeCal = new GregorianCalendar(TimeZone.getTimeZone(runTimeZone));
               buildTimeCal.setTime(new Date(buildTime));
               runTimeCal.setTime(new Date(buildTime));
               long buildOffset = (long)(buildTimeCal.get(15) + buildTimeCal.get(16));
               long runOffset = (long)(runTimeCal.get(15) + runTimeCal.get(16));
               buildTime -= buildOffset - runOffset;
            }
         }

         if (!this.jspManager.isStrictStaleCheck()) {
            return sinceWhen != buildTime + 2000L;
         } else {
            return sinceWhen < buildTime;
         }
      }
   }

   public ClassFinder getResourceFinder(String uri) {
      return this.war.getResourceFinder(uri);
   }

   public WarSource getResourceAsSource(String s) {
      return this.getResourceAsSource(s, false);
   }

   private WarSource getResourceAsSource(String s, boolean fromDisk) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.getLogContext() + " getResourceAsSource() invoked for : " + s);
      }

      WarSource rawSource = this.war.getResourceAsSource(s, fromDisk);
      if ((HTTPDebugLogger.isEnabled() || DEBUG_URL_RES.isDebugEnabled()) && rawSource == null) {
         DEBUG_URL_RES.debug(this.getLogContext() + ": getResourceAsSource() couldn't find source for : " + s);
      }

      return rawSource;
   }

   public WarSource getResourceAsSourceWithMDS(String uri) {
      if (this.mdsFinder != null) {
         Source source = this.mdsFinder.getSource(uri);
         if (source != null) {
            return new WarSource(source);
         } else {
            if (HTTPDebugLogger.isEnabled() || DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug(this.getLogContext() + ": getResourceAsSourceWithMDS() couldn't find source for : " + uri);
            }

            return null;
         }
      } else {
         return this.getResourceAsSource(uri);
      }
   }

   void setDefaultContext() {
      ContextVersionManager cvm = this.getContextManager();
      if (cvm != null) {
         cvm.setDefaultContext();
      }

      this.setContextPath("");
   }

   boolean isDefaultContext() {
      return this.defaultContext;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("ServletContext@");
      buf.append(super.hashCode()).append("[").append("app:").append(this.getAppName()).append(" module:").append(this.getName()).append(" path:").append(this.getContextPath());
      if (this.module.getWebAppBean() != null) {
         buf.append(" spec-version:").append(this.module.getWebAppBean().getVersion());
      }

      if (this.getVersionId() != null) {
         buf.append(" version:").append(this.getVersionId());
      }

      return buf.append("]").toString();
   }

   public final ClassLoader pushEnvironment(Thread thr) {
      Context envCtx = this.getEnvironmentContext();

      try {
         registry.getJNDIProvider().pushContext(envCtx);
      } catch (Exception var4) {
         HTTPLogger.logNoJNDIContext(this.logContext, var4.toString());
      }

      ClassLoader oldCL = thr.getContextClassLoader();
      thr.setContextClassLoader(this.getServletClassLoader());
      return oldCL;
   }

   public static final void popEnvironment(Thread thr, ClassLoader oldCL) {
      try {
         registry.getJNDIProvider().popContext();
      } catch (Exception var3) {
         HTTPLogger.logNoJNDIContext("<no context information>", var3.toString());
      }

      thr.setContextClassLoader(oldCL);
   }

   public void swapServlet(String name, String servletClassName, Map initParams) throws ServletException {
      ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
      if (sstub != null) {
         this.servletStubs.remove(name);
         sstub.destroy();
         if (initParams == null) {
            initParams = new HashMap();
         }

         Map map = sstub.getInitParametersMap();
         if (map != null) {
            ((Map)initParams).putAll(map);
         }

         ServletStubImpl newStub = ServletStubFactory.getInstance(this, name, servletClassName, (Map)initParams);
         this.swapServletStubs(sstub, newStub);
      } else {
         throw new ServletException("There is no sevlet \"" + name + "\" defined for web service.");
      }
   }

   private void swapServletStubs(ServletStubImpl oldStub, ServletStubImpl newStub) throws ServletException {
      if (this.defaultURLMatchHelper != null && this.defaultURLMatchHelper.getServletStub() == oldStub) {
         this.defaultURLMatchHelper = new URLMatchHelper(this.defaultURLMatchHelper.getPattern(), newStub);
      }

      Object[] values = this.servletMapping.values();
      if (values != null && values.length >= 1) {
         for(int i = 0; i < values.length; ++i) {
            URLMatchHelper umh = (URLMatchHelper)values[i];
            if (umh.getServletStub() == oldStub) {
               this.servletMapping.put(umh.getPattern(), new URLMatchHelper(umh.getPattern(), newStub));
            }
         }

         StubSecurityHelper oldStubHelper = oldStub.getSecurityHelper();
         StubSecurityHelper newStubHelper = newStub.getSecurityHelper();
         String oldRoleName = oldStubHelper.getRunAsRoleName();
         if (oldRoleName != null) {
            newStubHelper.setRunAsRoleName(oldRoleName);
            newStubHelper.setRunAsIdentity(oldStubHelper.getRunAsIdentity());
         }

         Iterator roleNames = oldStubHelper.getRoleNames();
         if (roleNames != null) {
            while(roleNames.hasNext()) {
               String roleName = (String)roleNames.next();
               newStubHelper.addRoleLink(roleName, oldStubHelper.getRoleLink(roleName));
            }
         }

      }
   }

   private void initResourceProvider() throws DeploymentException {
      String resourceProviderClassName = this.jspManager.getResourceProviderClass();
      if (resourceProviderClassName != null) {
         try {
            Class resourceProviderClass = this.classLoader.loadClass(resourceProviderClassName);
            this.jspResourceProvider = (JspResourceProvider)resourceProviderClass.newInstance();
            this.jspResourceProvider.init((String)null, this.getContextPath(), "/", this, (HttpServletRequest)null, (Hashtable)null);
            this.mdsFinder = new MDSClassFinder(this.jspResourceProvider);
         } catch (ClassNotFoundException var3) {
            throw new DeploymentException("Couldn't load jsp resource provider class: " + resourceProviderClassName);
         } catch (InstantiationException var4) {
            throw new DeploymentException("Couldn't instantiate jsp resource provider class: " + resourceProviderClassName);
         } catch (IllegalAccessException var5) {
            throw new DeploymentException("Couldn't instantiate jsp resource provider class: " + resourceProviderClassName);
         }
      }

   }

   URLMapping getServletMapping() {
      return this.servletMapping;
   }

   URLMatchHelper getDefaultMapping() {
      return this.defaultURLMatchHelper;
   }

   public EventsManager getEventsManager() {
      return this.eventsManager;
   }

   public WebAppConfigManager getConfigManager() {
      return this.configManager;
   }

   public JSPManager getJSPManager() {
      return this.jspManager;
   }

   public ErrorManager getErrorManager() {
      return this.errorManager;
   }

   public FilterManager getFilterManager() {
      return this.filterManager;
   }

   public SessionConfigManager getSessionConfigManager() {
      return this.sessionConfigManager;
   }

   public void dump(PrintStream p) {
      println(p, "==================== Internal Context Information ==================");
      println(p, "contextName: " + this.getName());
      println(p, "contextPath: " + this.getContextPath());
      println(p, "classpath: " + this.getClasspath());
      println(p, "indexFiles: " + this.configManager.getWelcomeFiles());
      println(p, "docroot: " + this.docroot);
      println(p, "isArchived: " + this.isArchived);
      println(p, "reloadCheckSeconds: " + this.configManager.getServletReloadCheckSecs());
      println(p, "classLoader: " + this.getServletClassLoader());
      println(p, "environmentCtx: " + this.getEnvironmentContext());
      println(p, "statusErrors: " + Arrays.toString(this.errorManager.getStatusErrors()));
      println(p, "attributes: " + this.attributes);
      println(p, "taglibs: " + Arrays.toString(this.jspManager.getTagLibs()));
      println(p, "defaultMimeType: " + this.configManager.getDefaultMimeType());
      println(p, "initParams: " + this.contextParams);
      println(p, "classFinder: " + this.war.getClassFinder());
      println(p, "httpServer.isDebugHttp: " + HTTPDebugLogger.isEnabled());
      println(p, "caseSensitive: " + !WebAppConfigManager.isCaseInsensitive());
      println(p, "servletStubs: " + this.servletStubs);
      println(p, "servletMapping: " + this.servletMapping);
      println(p, "authRealmName: " + this.configManager.getAuthRealmName());
      println(p, "securityManager: " + this.getSecurityManager());
      println(p, "webAppSecurity: " + this.getSecurityManager());
      println(p, "exceptionMap: " + Arrays.toString(this.errorManager.getStatusErrors()));
   }

   private static void println(PrintStream p, String s) {
      p.println(s + "<br>");
   }

   private static final String addUpRequestInfo(ServletRequestImpl req, String msg) {
      return registry.isProductionMode() ? msg : msg + ", request: " + req;
   }

   public static void enableWLDFDyeInjection(Boolean enabled) throws Exception {
      if (enabled) {
         Class clz = Class.forName("weblogic.diagnostics.instrumentation.support.DyeInjectionMonitorSupport");
         Class[] argTypes = new Class[]{Object.class};
         wldfDyeInjectionMethod = clz.getMethod("dyeWebAppRequest", argTypes);
      } else {
         wldfDyeInjectionMethod = null;
      }

   }

   public void initOrRestoreThreadContext(HttpServletRequest req) throws IOException {
      this.httpServer.getWorkContextManager().initOrRestoreThreadContexts(this, ServletRequestImpl.getOriginalRequest(req));
   }

   public boolean hasFilters() {
      return this.getFilterManager().hasFilters();
   }

   public boolean hasRequestListeners() {
      return this.getEventsManager().hasRequestListeners();
   }

   public FilterChainImpl getFilterChain(ServletStub stub, HttpServletRequest request, HttpServletResponse response) throws ServletException {
      return this.getFilterManager().getFilterChain(stub, request, response, this.hasRequestListeners(), 0);
   }

   public void invalidateSession(HttpSession session) {
      Thread thread = Thread.currentThread();
      ClassLoader cl = this.pushEnvironment(thread);

      try {
         session.invalidate();
      } finally {
         popEnvironment(thread, cl);
      }

   }

   void removeTransientAttributes(ClassLoader oldWebAppCL) {
      this.attributes.removeTransientAttributes(oldWebAppCL, this);
   }

   public HttpServletRequest cloneRequest(HttpServletRequest request) {
      ServletRequestImpl orig = ServletRequestImpl.getOriginalRequest(request);
      return orig == null ? null : orig.copy();
   }

   public JspApplicationContext getJspApplicationContext() {
      return this.jacImpl;
   }

   boolean isJsfApplication() {
      return this.module.isJsfApplication();
   }

   public boolean isJSR375Application() {
      return this.module.isJSR375Application();
   }

   boolean skipSamInitializerIfJSR375NotEnabled(String className) {
      return "org.glassfish.soteria.servlet.SamRegistrationInstaller".equals(className) && !this.module.isJSR375Application();
   }

   public Environment getEnvironmentBuilder() {
      return this.compEnv.getEnvironmentBuilder();
   }

   private FilterRegistration.Dynamic addFilter(String filterName, String className, Class filterClass, Filter filter) {
      this.checkContextStarted("addFilter");
      this.checkNotifyDynamicContext();
      if (filterName != null && !filterName.isEmpty()) {
         return this.filterManager.addFilter(filterName, className, filterClass, filter);
      } else {
         weblogic.i18n.logging.Loggable logger = HTTPLogger.logFilterNameIsNullOrEmptyStringLoggable();
         throw new IllegalArgumentException(logger.getMessage());
      }
   }

   public FilterRegistration.Dynamic addFilter(String filterName, String className) {
      return this.addFilter(filterName, className, (Class)null, (Filter)null);
   }

   public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
      return this.addFilter(filterName, (String)null, (Class)null, filter);
   }

   public FilterRegistration.Dynamic addFilter(String filterName, Class filterClass) {
      return this.addFilter(filterName, (String)null, filterClass, (Filter)null);
   }

   public void addListener(String className) {
      EventListener listener = this.eventsManager.createListener(className);
      this.addListener(listener);
   }

   public void addListener(EventListener t) {
      this.checkContextStarted("addListener");
      this.checkNotifyDynamicContext();
      if (t instanceof ServletContextListener) {
         if (this.phase != WebAppServletContext.ContextPhase.INITIALIZER_STARTUP) {
            weblogic.i18n.logging.Loggable logger = HTTPLogger.logCannotAddServletContextListenerLoggable();
            logger.log();
            throw new IllegalArgumentException(logger.getMessage());
         }

         this.eventsManager.registerDynamicContextListener((ServletContextListener)t);
      } else {
         this.eventsManager.addEventListener(t);
      }

   }

   public void addListener(Class listenerClass) {
      EventListener listener = null;

      try {
         listener = this.eventsManager.createListener(listenerClass);
      } catch (ReflectiveOperationException var4) {
         throw new IllegalArgumentException(var4);
      }

      this.addListener(listener);
   }

   private void checkSingleThreadModel(Class servletClass, Servlet servlet) {
      if (servletClass != null && SingleThreadModel.class.isAssignableFrom(servletClass)) {
         throw new IllegalArgumentException("Servlet class can't be SingleThreadModel");
      } else if (servlet != null && SingleThreadModel.class.isAssignableFrom(servlet.getClass())) {
         throw new IllegalArgumentException("Servlet class can't be SingleThreadModel");
      }
   }

   private ServletRegistration.Dynamic addServlet(String servletName, String className, Class servletClass, Servlet servlet) {
      this.checkContextStarted("addServlet");
      this.checkNotifyDynamicContext();
      this.checkSingleThreadModel(servletClass, servlet);
      if (servletName != null && !servletName.isEmpty()) {
         ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(servletName);
         if (sstub == null || sstub.isPreliminary() || sstub.isContextClassLoaderChanged()) {
            if (sstub == null) {
               sstub = this.registerServletDefinition(servletName, className, servletClass, servlet);
            } else {
               sstub.setServlet(className, servlet, servletClass);
            }

            if (sstub != null) {
               ServletRegistrationImpl servletRegistration = null;
               if (servlet == null) {
                  Class clz = servletClass;
                  if (servletClass == null) {
                     try {
                        clz = this.classLoader.loadClass(className);
                     } catch (ClassNotFoundException var9) {
                     }
                  }

                  servletRegistration = new ServletRegistrationImpl(this, sstub);
                  this.processServletAnnotationElements(clz, servletRegistration);
               } else {
                  if (this.contextCreatedServlets != null) {
                     servletRegistration = (ServletRegistrationImpl)this.contextCreatedServlets.get(servlet);
                  }

                  if (servletRegistration != null) {
                     servletRegistration.stub = sstub;
                  } else {
                     servletRegistration = new ServletRegistrationImpl(this, sstub);
                  }
               }

               if (sstub.isContextClassLoaderChanged()) {
                  return servletRegistration;
               }

               if (this.servletRegisrations == null) {
                  this.servletRegisrations = new HashMap();
               }

               this.servletRegisrations.put(servletName, servletRegistration);
               return servletRegistration;
            }
         }

         return null;
      } else {
         weblogic.i18n.logging.Loggable logger = HTTPLogger.logServletNameIsNullOrEmptyStringLoggable();
         throw new IllegalArgumentException(logger.getMessage());
      }
   }

   private void processServletAnnotationElements(Class clz, ServletRegistrationImpl servletRegistration) {
      if (clz != null) {
         ServletSecurity annotation = (ServletSecurity)clz.getAnnotation(ServletSecurity.class);
         if (annotation != null) {
            ServletSecurityElement sse = new ServletSecurityElement(annotation);
            servletRegistration.setServletSecurityElement(sse);
         }

         MultipartConfig multipartAnnotation = (MultipartConfig)clz.getAnnotation(MultipartConfig.class);
         if (multipartAnnotation != null) {
            MultipartConfigElement mce = new MultipartConfigElement(multipartAnnotation);
            servletRegistration.setMultipartConfig(mce);
         }

         DeclareRoles declareRoleAnnotation = (DeclareRoles)clz.getAnnotation(DeclareRoles.class);
         if (declareRoleAnnotation != null) {
            this.declareRoles(declareRoleAnnotation.value());
         }

         RunAs runAsAnnotation = (RunAs)clz.getAnnotation(RunAs.class);
         if (runAsAnnotation != null) {
            servletRegistration.setRunAsRole(runAsAnnotation.value());
         }

      }
   }

   private void deployServletSecurityConstraints() {
      if (this.servletRegisrations != null && this.servletRegisrations.size() != 0) {
         Iterator itor = this.servletRegisrations.keySet().iterator();

         while(itor.hasNext()) {
            String servletName = (String)itor.next();
            ServletRegistrationImpl servletRegistration = (ServletRegistrationImpl)this.servletRegisrations.get(servletName);
            if (servletRegistration != null) {
               servletRegistration.deployServletSecurity();
            }
         }

         this.contextCreatedServlets = null;
         this.servletRegisrations = null;
      }
   }

   public ServletRegistration.Dynamic addServlet(String servletName, String className) {
      return this.addServlet(servletName, className, (Class)null, (Servlet)null);
   }

   public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
      return this.addServlet(servletName, (String)null, (Class)null, servlet);
   }

   public ServletRegistration.Dynamic addServlet(String servletName, Class servletClass) {
      return this.addServlet(servletName, (String)null, servletClass, (Servlet)null);
   }

   public Filter createFilter(Class clazz) throws ServletException {
      this.checkNotifyDynamicContext();

      try {
         return (Filter)this.createInstance(clazz);
      } catch (IllegalAccessException var3) {
         throw new ServletException(var3);
      } catch (ClassNotFoundException var4) {
         throw new ServletException(var4);
      } catch (InstantiationException var5) {
         throw new ServletException(var5);
      }
   }

   public EventListener createListener(Class clazz) throws ServletException {
      this.checkNotifyDynamicContext();

      try {
         return this.eventsManager.createListener(clazz);
      } catch (ReflectiveOperationException var3) {
         throw new ServletException(var3);
      }
   }

   public Servlet createServlet(Class clazz) throws ServletException {
      this.checkNotifyDynamicContext();
      ServletRegistrationImpl servletRegistration = new ServletRegistrationImpl(this, (ServletStubImpl)null);
      this.processServletAnnotationElements(clazz, servletRegistration);

      try {
         Servlet instance = (Servlet)this.createInstance(clazz);
         if (this.contextCreatedServlets == null) {
            this.contextCreatedServlets = new HashMap();
         }

         this.contextCreatedServlets.put(instance, servletRegistration);
         return instance;
      } catch (IllegalAccessException var4) {
         throw new ServletException(var4);
      } catch (ClassNotFoundException var5) {
         throw new ServletException(var5);
      } catch (InstantiationException var6) {
         throw new ServletException(var6);
      }
   }

   public void declareRoles(String... roleNames) {
      this.checkNotifyDynamicContext();
      this.checkContextStarted("declareRoles");
      if (roleNames != null && roleNames.length > 0) {
         String[] var2 = roleNames;
         int var3 = roleNames.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String roleName = var2[var4];
            if (roleName == null || roleName.length() == 0) {
               throw new IllegalArgumentException("role name can't be null or empty string");
            }
         }

         this.securityManager.declareRoles(roleNames);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ClassLoader getClassLoader() {
      this.checkNotifyDynamicContext();
      MySecurityManager mySecurityManager = new MySecurityManager();
      mySecurityManager.checkGetClassLoaderPermission(this.classLoader);
      return this.classLoader;
   }

   public int getEffectiveMajorVersion() {
      this.checkNotifyDynamicContext();

      try {
         String version = ((DescriptorBean)this.module.getWebAppBean()).getDescriptor().getOriginalVersionInfo();
         String[] numbers = version.split("\\.");
         return Integer.parseInt(numbers[0]);
      } catch (Exception var3) {
         return 2;
      }
   }

   public int getEffectiveMinorVersion() {
      this.checkNotifyDynamicContext();

      try {
         String version = ((DescriptorBean)this.module.getWebAppBean()).getDescriptor().getOriginalVersionInfo();
         String[] numbers = version.split("\\.");
         return numbers.length < 2 ? 0 : Integer.parseInt(numbers[1]);
      } catch (Exception var3) {
         return 3;
      }
   }

   public String getModuleId() {
      return this.module.getId();
   }

   public FilterRegistration getFilterRegistration(String filterName) {
      this.checkNotifyDynamicContext();
      return this.filterManager.getFilterRegistration(filterName);
   }

   public Map getFilterRegistrations() {
      this.checkNotifyDynamicContext();
      return this.filterManager.getFilterRegistrations();
   }

   public JspConfigDescriptor getJspConfigDescriptor() {
      return this.jspManager.getJspConfigDescriptor();
   }

   public ServletRegistration getServletRegistration(String servletName) {
      this.checkNotifyDynamicContext();
      ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(servletName);
      return sstub == null ? null : new ServletRegistrationImpl(this, sstub);
   }

   public Map getServletRegistrations() {
      this.checkNotifyDynamicContext();
      HashMap ret = new HashMap();
      Iterator keys = this.servletStubs.keySet().iterator();

      while(keys.hasNext()) {
         String name = (String)keys.next();
         ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(name);
         if (sstub != null) {
            ServletRegistrationImpl sri = new ServletRegistrationImpl(this, sstub);
            ret.put(name, sri);
         }
      }

      return ret;
   }

   public SessionCookieConfig getSessionCookieConfig() {
      this.checkNotifyDynamicContext();
      return this.sessionCookieConfig;
   }

   public Set getDefaultSessionTrackingModes() {
      this.checkNotifyDynamicContext();
      return this.sessionConfigManager.getDefaultSessionTrackingModes();
   }

   public Set getEffectiveSessionTrackingModes() {
      this.checkNotifyDynamicContext();
      return this.sessionConfigManager.getEffectiveSessionTrackingModes();
   }

   public void setSessionTrackingModes(Set sessionTrackingModes) {
      this.checkContextStarted("setSessionTrackingModes");
      this.checkNotifyDynamicContext();
      Set supportedModes = this.getDefaultSessionTrackingModes();
      Iterator it = sessionTrackingModes.iterator();

      SessionTrackingMode sessionTrackingMode;
      do {
         if (!it.hasNext()) {
            if (sessionTrackingModes.size() > 1 && sessionTrackingModes.contains(SessionTrackingMode.SSL)) {
               throw new IllegalArgumentException("SSL mode could not combinate with othe mode");
            }

            it = sessionTrackingModes.iterator();

            while(it.hasNext()) {
               sessionTrackingMode = (SessionTrackingMode)it.next();
               if (sessionTrackingMode == SessionTrackingMode.COOKIE) {
                  this.sessionConfigManager.setSessionCookiesEnabled(true);
               }

               if (sessionTrackingMode == SessionTrackingMode.URL) {
                  this.sessionConfigManager.setUrlRewritingEnabled(true);
               }

               if (sessionTrackingMode == SessionTrackingMode.SSL) {
                  this.sessionConfigManager.setSSLTrackingEnabled(true);
               }
            }

            this.sessionConfigManager.hasEffectiveSessionTrackingModes(true);
            return;
         }

         sessionTrackingMode = (SessionTrackingMode)it.next();
      } while(supportedModes.contains(sessionTrackingMode));

      throw new IllegalArgumentException(sessionTrackingMode + " is unsupported Session tracking mode");
   }

   public String getVirtualServerName() {
      this.checkNotifyDynamicContext();
      return this.httpServer.getName();
   }

   Object createInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      Class clazz = this.classLoader.loadClass(className);
      return this.createInstance(clazz);
   }

   public Object createInstance(Class clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      ManagedBeanCreator creator = this.getManagedBeanCreator();
      if (clazz.isAnnotationPresent(ManagedBean.class) && creator != null) {
         Object instance = creator.createInstance(clazz);
         creator.notifyPostConstruct(clazz.getName(), instance);
         return instance;
      } else {
         return this.componentCreator != null ? this.componentCreator.createInstance(clazz) : clazz.newInstance();
      }
   }

   void prepareNotifyDynamicContextListener() {
      this.phase = WebAppServletContext.ContextPhase.INITIALIZER_NOTIFY_LISTENER;
   }

   private void checkContextStarted(String caller) {
      if (this.phase == WebAppServletContext.ContextPhase.START) {
         weblogic.i18n.logging.Loggable logger = HTTPLogger.logContextAlreadyStartLoggable(caller);
         logger.log();
         throw new IllegalStateException(logger.getMessage());
      }
   }

   private void checkNotifyDynamicContext() {
      if (this.phase == WebAppServletContext.ContextPhase.INITIALIZER_NOTIFY_LISTENER) {
         weblogic.i18n.logging.Loggable logger = HTTPLogger.logInvalidServletContextListenerLoggable();
         logger.log();
         throw new UnsupportedOperationException(logger.getMessage());
      }
   }

   ServletStubImpl getJspServletStub() {
      return this.jspServletStub;
   }

   ServletStubImpl getJspxServletStub() {
      return this.jspxServletStub;
   }

   public ServletContextLogger getServletContextLogger() {
      return this.servletContextLogger;
   }

   public int getSessionTimeout() {
      this.checkNotifyDynamicContext();
      return this.sessionConfigManager.getSessionTimeoutSecs() / 60;
   }

   public void setSessionTimeout(int sessionTimeout) {
      this.checkContextStarted("setSessionTimeout");
      this.checkNotifyDynamicContext();
      this.sessionConfigManager.setSessionTimeoutSecs(sessionTimeout * 60);
   }

   public String getRequestCharacterEncoding() {
      this.checkNotifyDynamicContext();
      return this.requestCharacterEncoding != null ? this.requestCharacterEncoding : this.configManager.getRequestCharacterEncoding();
   }

   public void setRequestCharacterEncoding(String encoding) {
      this.checkContextStarted("setRequestCharacterEncoding");
      this.checkNotifyDynamicContext();
      this.requestCharacterEncoding = encoding;
   }

   public String getResponseCharacterEncoding() {
      this.checkNotifyDynamicContext();
      return this.responseCharacterEncoding != null ? this.responseCharacterEncoding : this.configManager.getResponseCharacterEncoding();
   }

   public void setResponseCharacterEncoding(String encoding) {
      this.checkContextStarted("setRequestCharacterEncoding");
      this.checkNotifyDynamicContext();
      this.responseCharacterEncoding = encoding;
   }

   public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
      this.checkContextStarted("addJspFile");
      this.checkNotifyDynamicContext();
      if (servletName != null && !servletName.isEmpty()) {
         if (jspFile != null && jspFile.startsWith("/")) {
            ServletStubImpl sstub = (ServletStubImpl)this.servletStubs.get(servletName);
            if (sstub == null || sstub.isPreliminary() || sstub.isContextClassLoaderChanged()) {
               if (sstub == null) {
                  sstub = ServletStubFactory.getInstance(this, servletName, jspFile);
               }

               if (sstub != null) {
                  ServletRegistrationImpl servletRegistration = this.servletRegisrations != null ? (ServletRegistrationImpl)this.servletRegisrations.get(servletName) : null;
                  if (servletRegistration != null) {
                     servletRegistration.stub = sstub;
                  } else {
                     servletRegistration = new ServletRegistrationImpl(this, sstub);
                  }

                  if (sstub.isContextClassLoaderChanged()) {
                     return servletRegistration;
                  }

                  if (this.servletRegisrations == null) {
                     this.servletRegisrations = new HashMap();
                  }

                  this.servletRegisrations.put(servletName, servletRegistration);
                  return servletRegistration;
               }
            }

            return null;
         } else {
            throw new IllegalArgumentException(HTTPLogger.logJspFileIsNullOrNotStartWithSlashLoggable().getMessage());
         }
      } else {
         throw new IllegalArgumentException(HTTPLogger.logServletNameIsNullOrEmptyStringLoggable().getMessage());
      }
   }

   static {
      _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Stale_Resource_Around_Medium");
      _WLDF$INST_FLD_Servlet_Context_Execute_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Context_Execute_Around_Medium");
      _WLDF$INST_FLD_Servlet_Context_Handle_Throwable_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Context_Handle_Throwable_Around_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WebAppServletContext.java", "weblogic.servlet.internal.WebAppServletContext", "execute", "(Lweblogic/servlet/internal/ServletRequestImpl;Lweblogic/servlet/internal/ServletResponseImpl;)V", 2257, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Context_Execute_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Context_Execute_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WebAppServletContext.java", "weblogic.servlet.internal.WebAppServletContext", "handleThrowableFromInvocation", "(Ljava/lang/Throwable;Lweblogic/servlet/internal/ServletRequestImpl;Lweblogic/servlet/internal/ServletResponseImpl;)V", 2517, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Context_Handle_Throwable_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("exc", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Context_Handle_Throwable_Around_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WebAppServletContext.java", "weblogic.servlet.internal.WebAppServletContext", "isResourceStale", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;)Z", 3421, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Stale_Resource_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("res", (String)null, false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium};
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      NO_VERSION_CHECK = Boolean.getBoolean("weblogic.jspc.skipVersionCheck");
      NON_BLOCKING_DISPATCH = new HashMap();
      DIRECT_DISPATCH = new HashMap();
      NON_BLOCKING_DISPATCH.put("wl-dispatch-policy", "weblogic.kernel.Non-Blocking");
      DIRECT_DISPATCH.put("wl-dispatch-policy", "direct");
      DEBUG_URL_RES = DebugLogger.getDebugLogger("DebugURLResolution");
      maxConcurrentRequestsAllowed = -1;
      String val = System.getProperty("weblogic.http.session.maxConcurrentRequest");

      try {
         if (val != null) {
            maxConcurrentRequestsAllowed = Integer.parseInt(val);
         }

         if (maxConcurrentRequestsAllowed < 1) {
            maxConcurrentRequestsAllowed = -1;
         }
      } catch (NumberFormatException var2) {
      }

      doNotSendContinueHeader = false;
      doNotSendContinueHeader = Boolean.getBoolean("doNotSendContinueHeader");
      wldfDyeInjectionMethod = null;
      registry = WebServerRegistry.getInstance();
   }

   private static class MySecurityManager extends java.lang.SecurityManager {
      private MySecurityManager() {
      }

      boolean isAncestor(ClassLoader start, ClassLoader cl) {
         ClassLoader acl = start;

         do {
            acl = acl.getParent();
            if (cl == acl) {
               return true;
            }
         } while(acl != null);

         return false;
      }

      void checkGetClassLoaderPermission(ClassLoader webappLoader) {
         java.lang.SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            Class[] classContext = this.getClassContext();
            ClassLoader ccl = classContext[2].getClassLoader();
            if (ccl != null && ccl != webappLoader && !this.isAncestor(webappLoader, ccl)) {
               sm.checkPermission(new RuntimePermission("getClassLoader"));
            }

         }
      }

      // $FF: synthetic method
      MySecurityManager(Object x0) {
         this();
      }
   }

   private static final class ServletInvocationAction implements PrivilegedAction {
      private final HttpServletRequest req;
      private final HttpServletResponse rsp;
      private final ServletInvocationContext context;
      private final ServletStub stub;
      static final long serialVersionUID = 800369587119951937L;
      static final String _WLDF$INST_VERSION = "9.0.0";
      // $FF: synthetic field
      static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction");
      static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Invocation_Around_Low;
      static final JoinPoint _WLDF$INST_JPFLD_0;
      static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

      ServletInvocationAction(HttpServletRequest rq, HttpServletResponse rp, ServletInvocationContext context, ServletStub s) {
         this.req = rq;
         this.rsp = rp;
         this.context = context;
         this.stub = s;
      }

      public Object run() {
         return this.wrapRun(this.stub, this.req, this.rsp);
      }

      private Object wrapRun(ServletStub var1, HttpServletRequest var2, HttpServletResponse var3) {
         LocalHolder var8;
         Object[] var10000;
         if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
            if (var8.argsCapture) {
               var8.args = new Object[4];
               var10000 = var8.args;
               var10000[0] = this;
               var10000[1] = var1;
               var10000[2] = var2;
               var10000[3] = var3;
            }

            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
            var8.resetPostBegin();
         }

         label210: {
            IOException var24;
            label211: {
               Throwable var23;
               try {
                  label203: {
                     try {
                        ServletInvocationContext invocationContext = this.context;
                        invocationContext.initOrRestoreThreadContext(this.req);
                        if (WebAppServletContext.wldfDyeInjectionMethod != null) {
                           try {
                              Object[] args = new Object[]{this.req};
                              WebAppServletContext.wldfDyeInjectionMethod.invoke((Object)null, args);
                           } catch (Throwable var14) {
                           }
                        }

                        if (this.rsp instanceof ServletResponseImpl) {
                           ((ServletResponseImpl)this.rsp).setCorrelationResponse();
                        }

                        if (!invocationContext.hasFilters() && !invocationContext.hasRequestListeners()) {
                           this.stub.execute(this.req, this.rsp);
                        } else {
                           FilterChainImpl fc = invocationContext.getFilterChain(this.stub, this.req, this.rsp);
                           if (fc == null) {
                              this.stub.execute(this.req, this.rsp);
                           } else {
                              fc.doFilter(this.req, this.rsp);
                           }
                        }

                        ServletRequestImpl requestImpl = null;
                        if (this.req instanceof ServletRequestImpl) {
                           requestImpl = (ServletRequestImpl)this.req;
                        } else if (this.req instanceof ServletRequestWrapper) {
                           requestImpl = ServletRequestImpl.getOriginalRequest(this.req);
                        }

                        if (requestImpl != null && !requestImpl.isFutureResponseEnabled() && requestImpl.isUpgrade()) {
                           ServletResponseImpl responseImpl = requestImpl.getResponse();
                           WebConnection wc = new WebConnectionImpl((ServletInputStreamImpl)this.req.getInputStream(), (ServletOutputStreamImpl)responseImpl.getOutputStreamNoCheck(), requestImpl.getHttpUpgradeHandler());
                           ((ServletOutputStreamImpl)responseImpl.getOutputStreamNoCheck()).setContext(responseImpl.getContext());
                           ((ServletOutputStreamImpl)wc.getOutputStream()).setUpgradeMode(true);
                           requestImpl.getHttpUpgradeHandler().init(wc);
                        }

                        this.postProcessForAsync();
                     } catch (UnavailableException var16) {
                        UnavailableException ue = var16;

                        try {
                           if (ue.isPermanent()) {
                              this.rsp.sendError(404);
                           } else {
                              long retryAfter = System.currentTimeMillis() + (ue.getUnavailableSeconds() > 0 ? (long)ue.getUnavailableSeconds() : 60L) * 1000L;
                              this.rsp.addHeader("Retry-After", (new Date(retryAfter)).toString());
                              this.rsp.sendError(503);
                           }
                        } catch (IOException var15) {
                           var24 = var15;
                           break label211;
                        }
                     } catch (Throwable var17) {
                        var23 = var17;
                        break label203;
                     }

                     var10000 = null;
                     break label210;
                  }
               } catch (Throwable var18) {
                  if (var8 != null) {
                     var8.th = var18;
                     var8.ret = null;
                     InstrumentationSupport.postProcess(var8);
                  }

                  throw var18;
               }

               if (var8 != null) {
                  var8.ret = var23;
                  InstrumentationSupport.postProcess(var8);
               }

               return var23;
            }

            if (var8 != null) {
               var8.ret = var24;
               InstrumentationSupport.postProcess(var8);
            }

            return var24;
         }

         if (var8 != null) {
            var8.ret = var10000;
            InstrumentationSupport.postProcess(var8);
         }

         return var10000;
      }

      private void postProcessForAsync() {
         ServletObjectsFacade request = this.context.getSecurityContext().getRequestFacade();
         if (request.isAsyncMode(this.req)) {
            AsyncContextImpl async = request.getAsyncContext(this.req);
            async.returnToContainer();
         }
      }

      static {
         _WLDF$INST_FLD_Servlet_Invocation_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Invocation_Around_Low");
         _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WebAppServletContext.java", "weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction", "wrapRun", "(Lweblogic/servlet/internal/ServletStub;Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)Ljava/lang/Object;", 3835, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Invocation_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("stub", "weblogic.diagnostics.instrumentation.gathering.ServletStubRenderer", false, true), InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
         _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Invocation_Around_Low};
      }
   }

   protected static final class ContextRequestCallback implements RequestCallback {
      private final String uri;
      private final WebAppServletContext ctx;

      ContextRequestCallback(WebAppServletContext ctx, String uri) {
         this.ctx = ctx;
         this.uri = uri;
      }

      public final String getIncludeURI() {
         return this.uri;
      }

      public final void reportJSPTranslationFailure(String simpleMsg, String htmlMsg) {
         this.ctx.logError(simpleMsg);
      }

      public final void reportJSPCompilationFailure(String simpleMsg, String htmlMsg) {
         this.ctx.logError(simpleMsg);
      }
   }

   private class AsyncInitRequest implements Runnable {
      private final List asyncInitServlets;

      AsyncInitRequest(List asyncInitServlets) {
         this.asyncInitServlets = asyncInitServlets;
      }

      public void run() {
         Thread thread = Thread.currentThread();
         ClassLoader cl = WebAppServletContext.this.pushEnvironment(thread);

         try {
            Iterator var3 = this.asyncInitServlets.iterator();

            while(var3.hasNext()) {
               AsyncInitServlet s = (AsyncInitServlet)var3.next();

               try {
                  s.initDelegate();
               } catch (ServletException var9) {
                  if (!WebAppServletContext.this.asyncInitsStillRunning) {
                     return;
                  }

                  HTTPLogger.logAsyncInitFailed(s.getClass().getName(), (Throwable)(var9.getRootCause() != null ? var9.getRootCause() : var9));
               }
            }

         } finally {
            WebAppServletContext.popEnvironment(thread, cl);
            WebAppServletContext.this.asyncInitsStillRunning = false;
            this.asyncInitServlets.clear();
         }
      }
   }

   public static enum ContextPhase {
      INIT,
      INITIALIZER_STARTUP,
      INITIALIZER_STARTED,
      INITIALIZER_NOTIFY_LISTENER,
      AFTER_INITIALIZER_NOTIFY_LISTENER,
      START,
      SUSPENDING,
      STOP,
      RELOAD;
   }
}
