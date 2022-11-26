package weblogic.servlet.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.jsp.AddToMapException;
import weblogic.servlet.jsp.CompilationException;
import weblogic.servlet.jsp.JspFileNotFoundException;
import weblogic.servlet.jsp.JspStub;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.ChangeAwareClassLoader;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class ServletStubImpl implements ServletConfig, ServletStub {
   static final String DISPATCH_POLICY = "wl-dispatch-policy";
   private static final String CLASSPATH_SERVLET_NAME = "weblogic.servlet.ClasspathServlet";
   private static final String FILE_SERVLET_NAME = "weblogic.servlet.FileServlet";
   private static final String HTTP_CLUSTER_SERVLET_NAME = "weblogic.servlet.proxy.HttpClusterServlet";
   private static final String HTTP_PROXY_SERVLET_NAME = "weblogic.servlet.proxy.HttpProxyServlet";
   private static final String PUBSUB_CONTROLLER_SERVLET_NAME = "com.bea.httppubsub.servlet.ControllerServlet";
   private final String name;
   protected String className;
   private final WebAppServletContext context;
   private ClassLoader contextLoader;
   private Map initParams;
   private final StubSecurityHelper securityHelper;
   private WorkManager workManager;
   private StubLifecycleHelper lifecycleHelper;
   private WorkManager sessionFetchingWorkManager;
   private boolean filtersInvoked;
   private boolean isInternalServlet;
   private boolean dynamicallyGenerated;
   private boolean asyncSupported;
   private MultipartConfigElement multipartConfig;
   private ServletRuntimeMBeanImpl runtime;
   private ConcurrentMap annotatedMethodsCache;
   private Class servletClass;
   private Servlet servlet;
   static final long serialVersionUID = -7594251273162572835L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.ServletStubImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Execute_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public ServletStubImpl(String name, String className, WebAppServletContext context) {
      this(context, name, className, (Servlet)null, (Class)null);
   }

   ServletStubImpl(String name, Class servletClass, WebAppServletContext context) {
      this(context, name, servletClass.getName(), (Servlet)null, servletClass);
   }

   ServletStubImpl(String name, Servlet servlet, WebAppServletContext context) {
      this(context, name, servlet.getClass().getName(), servlet, (Class)null);
   }

   private ServletStubImpl(WebAppServletContext context, String name, String className, Servlet servlet, Class servletClass) {
      this.filtersInvoked = false;
      this.isInternalServlet = false;
      this.dynamicallyGenerated = false;
      this.asyncSupported = false;
      this.annotatedMethodsCache = new ConcurrentHashMap();
      this.context = context;
      this.contextLoader = context.getServletClassLoader();
      this.name = name;
      this.setServlet(className, servlet, servletClass);
      this.setWorkManagerForSessionFetching();
      this.securityHelper = new StubSecurityHelper(this);
   }

   void setServlet(String className, Servlet servlet, Class servletClass) {
      this.className = className;
      this.servletClass = servletClass;
      this.servlet = servlet;
   }

   protected boolean isPreliminary() {
      return this.servlet == null && this.servletClass == null && this.className == null;
   }

   public final void initRuntime() throws ManagementException {
      Debug.assertion(this.context.getRuntimeMBean() != null);
      if (this.runtime == null) {
         this.runtime = ServletRuntimeMBeanImpl.newInstance(this);
      }
   }

   public final void destroyRuntime() {
      if (this.runtime != null) {
         this.runtime.destroy();
         this.runtime = null;
      }
   }

   final void setDispatchPolicy(String policy) {
      if (policy != null) {
         this.workManager = WorkManagerFactory.getInstance().find(policy, this.context.getApplicationId(), this.context.getId());
      }

   }

   private void setWorkManagerForSessionFetching() {
      String workManagerName = this.context.getServer().getMBean().getWorkManagerForRemoteSessionFetching();
      if (workManagerName != null) {
         this.sessionFetchingWorkManager = WorkManagerFactory.getInstance().find(workManagerName, this.context.getApplicationId(), this.context.getId());
      }

   }

   final WorkManager getWorkManagerForSessionFetching() {
      return this.sessionFetchingWorkManager;
   }

   final WorkManager getWorkManager() {
      return this.workManager != null ? this.workManager : this.context.getConfigManager().getWorkManager();
   }

   protected String getDefaultContentType() {
      return null;
   }

   final ServletRuntimeMBean getRuntimeMBean() {
      return this.runtime;
   }

   final boolean isSingleThreadModel() {
      return this.lifecycleHelper == null ? false : this.lifecycleHelper.isSingleThreadModel();
   }

   final boolean isFutureResponseServlet() {
      return this.lifecycleHelper == null ? false : this.lifecycleHelper.isFutureResponseServlet();
   }

   public final boolean isProxyServlet() {
      return "weblogic.servlet.proxy.HttpClusterServlet".equals(this.className) || "weblogic.servlet.proxy.HttpProxyServlet".equals(this.className);
   }

   public boolean isAsyncSupported() {
      return this.asyncSupported;
   }

   final void setAsyncSupported(boolean isAsyncSupported) {
      this.asyncSupported = isAsyncSupported;
   }

   MultipartConfigElement getMultipartConfig() {
      return this.multipartConfig;
   }

   void setMultipartConfig(MultipartConfigElement multipartConfig) {
      this.multipartConfig = multipartConfig;
   }

   boolean isMultipartConfigPresent() {
      return this.multipartConfig != null;
   }

   final boolean isInternalServlet() {
      return this.isInternalServlet;
   }

   final void setInternalServlet(boolean internal) {
      this.isInternalServlet = internal;
   }

   public final String getClassName() {
      if (this.className != null) {
         return this.className;
      } else if (this.context.isStarted() && this.servlet != null) {
         return this.servlet.getClass().getName();
      } else {
         return this.context.isStarted() && this.servletClass != null ? this.servletClass.getName() : null;
      }
   }

   public final boolean isClasspathServlet() {
      return "weblogic.servlet.ClasspathServlet".equals(this.className);
   }

   public final boolean isFileServlet() {
      return "weblogic.servlet.FileServlet".equals(this.className);
   }

   public final boolean isPubSubControllerServlet() {
      return "com.bea.httppubsub.servlet.ControllerServlet".equals(this.className);
   }

   public final void execute(ServletRequest req, ServletResponse rsp) throws ServletException, IOException {
      this.execute(req, rsp, (FilterChainImpl)null);
   }

   public void execute(ServletRequest req, ServletResponse rsp, FilterChainImpl chain) throws ServletException, IOException {
      LocalHolder var15;
      if ((var15 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var15.argsCapture) {
            var15.args = new Object[4];
            Object[] var10000 = var15.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = rsp;
            var10000[3] = chain;
         }

         InstrumentationSupport.createDynamicJoinPoint(var15);
         InstrumentationSupport.preProcess(var15);
         var15.resetPostBegin();
      }

      label454: {
         label455: {
            try {
               ServletRequestImpl reqi;
               ServletResponseImpl rspi;
               if (chain == null) {
                  reqi = ServletRequestImpl.getOriginalRequest(req);
                  rspi = reqi.getResponse();
               } else {
                  if (!this.filtersInvoked) {
                     this.filtersInvoked = true;
                  }

                  reqi = chain.getOrigRequest();
                  rspi = chain.getOrigResponse();
               }

               Object httpReq;
               if (req instanceof HttpServletRequest) {
                  httpReq = (HttpServletRequest)req;
               } else {
                  httpReq = reqi;
               }

               Object httpRes;
               if (rsp instanceof HttpServletResponse) {
                  httpRes = (HttpServletResponse)rsp;
               } else {
                  httpRes = rspi;
               }

               if (this.getDefaultContentType() != null) {
                  rsp.setContentType(this.getDefaultContentType());
               }

               RequestCallback rc = new RequestCallbackImpl((HttpServletRequest)httpReq, (HttpServletResponse)httpRes, rspi);

               Servlet s;
               try {
                  s = this.getServlet(rc);
               } catch (CompilationException var22) {
                  ((HttpServletRequest)httpReq).setAttribute("javax.servlet.error.exception", var22);
                  this.context.getErrorManager().setErrorAttributes((HttpServletRequest)httpReq, var22.getJavaFileName(), var22);
                  throw new ServletException(var22);
               }

               if (s instanceof HttpJspPage) {
                  String val = reqi.getRequestParameters().peekParameter("jsp_precompile");
                  if (val != null) {
                     if ("".equals(val) || "true".equals(val)) {
                        break label455;
                     }

                     if (!"false".equals(val)) {
                        rspi.sendError(500);
                        break label454;
                     }
                  }
               }

               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Start to execute servlet: " + this.toString());
               }

               long start = System.currentTimeMillis();

               try {
                  Throwable e = this.securityHelper.invokeServlet(req, (HttpServletRequest)httpReq, reqi, rsp, (HttpServletResponse)httpRes, s);
                  if (e != null) {
                     if (!(e instanceof AddToMapException)) {
                        if (e instanceof UnavailableException) {
                           UnavailableException ue = (UnavailableException)e;
                           if (ue.isPermanent()) {
                              this.context.removeServletStub(this, true);
                           } else {
                              this.lifecycleHelper.makeUnavailable(ue);
                           }

                           throw ue;
                        }

                        if (e instanceof ServletException) {
                           throw (ServletException)e;
                        }

                        if (e instanceof IOException) {
                           throw (IOException)e;
                        }

                        if (e instanceof RuntimeException) {
                           throw (RuntimeException)e;
                        }

                        throw new ServletException(e);
                     }

                     this.onAddToMapException(e, reqi, req, rsp, chain);
                  }
               } finally {
                  this.recordInvoke(start);
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Servlet execution completed: " + this.toString());
                  }

                  if (this.lifecycleHelper != null) {
                     this.lifecycleHelper.returnServlet(s);
                  }

               }
            } catch (Throwable var24) {
               if (var15 != null) {
                  var15.th = var24;
                  InstrumentationSupport.postProcess(var15);
               }

               throw var24;
            }

            if (var15 != null) {
               InstrumentationSupport.postProcess(var15);
            }

            return;
         }

         if (var15 != null) {
            InstrumentationSupport.postProcess(var15);
         }

         return;
      }

      if (var15 != null) {
         InstrumentationSupport.postProcess(var15);
      }

   }

   private Servlet getServlet(RequestCallback rc) throws ServletException, IOException {
      try {
         if (this.lifecycleHelper != null) {
            this.checkForReload(rc);
         } else {
            synchronized(this) {
               if (this.lifecycleHelper == null) {
                  this.prepareServlet(rc);
               }
            }
         }

         return this.lifecycleHelper.getServlet();
      } catch (JspFileNotFoundException var5) {
         this.context.removeServletStub(this, false);
         throw var5;
      } catch (CompilationException var6) {
         this.context.removeServletStub(this, false);
         throw var6;
      }
   }

   private void onAddToMapException(Throwable e, ServletRequestImpl reqi, ServletRequest req, ServletResponse rsp, FilterChainImpl chain) throws ServletException, IOException {
      AddToMapException atme = (AddToMapException)e;
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(this.context.getLogContext() + ": registering servlet stub dynamically for the JSP : " + reqi.getRequestURI());
      }

      ServletStubImpl sstub;
      synchronized(this.context) {
         sstub = this.context.getServletStub(atme.pattern);
         if (sstub != null) {
            if (sstub.getClassName() != null && sstub.getClassName().equals(this.getClassName())) {
               sstub = null;
            } else if (sstub != atme.sstub) {
               if (sstub instanceof JspStub && atme.sstub instanceof JspStub) {
                  atme.sstub.destroy();
               } else {
                  sstub = null;
               }
            }
         }

         if (sstub == null) {
            String pattern = atme.pattern;
            if (!this.context.getJSPManager().isJspExactMapping()) {
               pattern = pattern + "/*";
            }

            this.context.registerServletStub(pattern, atme.sstub);
            this.context.registerServletMap(pattern, pattern, atme.sstub);
            sstub = atme.sstub;

            try {
               sstub.initRuntime();
            } catch (ManagementException var12) {
               throw new ServletException(var12);
            }
         }
      }

      if (reqi.getServletStub() == this) {
         reqi.setServletStub(sstub);
      }

      sstub.dynamicallyGenerated = true;
      sstub.execute(req, rsp, chain);
   }

   private void recordInvoke(long start) {
      if (this.runtime != null) {
         this.runtime.incInvocationCount();
         long delta = System.currentTimeMillis() - start;
         if (delta >= 0L) {
            this.runtime.setExecutionTimeHighLow(delta);
            this.runtime.addExecutionTimeTotal(delta);
         }

      }
   }

   protected final synchronized void destroy() {
      if (this.lifecycleHelper != null) {
         this.lifecycleHelper.destroy();
         this.lifecycleHelper = null;
      }

      if (this.runtime != null) {
         this.runtime.destroy();
         this.runtime = null;
      }

   }

   protected void checkForReload(RequestCallback rc) throws ServletException, UnavailableException, IOException {
      StaleProber staleProber = this.context.getConfigManager();
      if (!staleProber.isServletStaleCheckDisabled()) {
         if (this.isContextClassLoaderChanged()) {
            synchronized(this) {
               if (this.isContextClassLoaderChanged()) {
                  this.reloadWhenClassloaderChanged(rc);
               }
            }
         } else if (!this.filtersInvoked && this.checkReloadTimeout(staleProber)) {
            synchronized(this) {
               if (this.isContextClassLoaderChanged()) {
                  this.reloadWhenClassloaderChanged(rc);
               } else if (this.checkReloadTimeout(staleProber) && this.needToReload()) {
                  this.lifecycleHelper.destroy();
                  this.context.reloadServletClassLoader();
                  this.prepareServlet(rc);
               }
            }
         }

      }
   }

   boolean isContextClassLoaderChanged() {
      return this.contextLoader != this.context.getServletClassLoader();
   }

   private void reloadWhenClassloaderChanged(RequestCallback rc) throws ServletException, IOException {
      this.lifecycleHelper.destroy();
      this.context.removeTransientAttributes(this.lifecycleHelper.getContextLoader());
      this.prepareServlet(rc);
   }

   private boolean checkReloadTimeout(StaleProber staleProber) {
      if (this.lifecycleHelper == null) {
         return true;
      } else {
         ClassLoader cl = this.lifecycleHelper.getContextLoader();
         if (!(cl instanceof ChangeAwareClassLoader)) {
            return false;
         } else {
            ChangeAwareClassLoader cacl = (ChangeAwareClassLoader)cl;
            return staleProber.shouldReloadServlet(cacl.getLastChecked());
         }
      }
   }

   private boolean needToReload() {
      if (this.lifecycleHelper == null) {
         return false;
      } else {
         ClassLoader cl = this.lifecycleHelper.getContextLoader();
         if (!(cl instanceof ChangeAwareClassLoader)) {
            return false;
         } else {
            ChangeAwareClassLoader cacl = (ChangeAwareClassLoader)cl;
            return !cacl.upToDate();
         }
      }
   }

   protected void prepareServlet(RequestCallback rc) throws ServletException, UnavailableException, IOException {
      if (!this.isPreliminary()) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(this.context.getLogContext() + ": Preparing servlet: " + this.name);
         }

         this.annotatedMethodsCache.clear();

         String cp;
         String lc;
         Loggable l;
         Loggable l;
         try {
            if (this.runtime != null) {
               this.runtime.incReloadCount();
            }

            this.initStubLifecycleHelper();
            this.contextLoader = this.context.getServletClassLoader();
         } catch (ClassNotFoundException var7) {
            cp = this.context.getClasspath();
            lc = this.context.getLogContext();
            Throwable ex = var7.getException();
            if (ex == null) {
               ex = var7;
            }

            weblogic.i18n.logging.Loggable l = HTTPLogger.logServletClassNotFoundLoggable(lc, this.name, this.className, cp, (Throwable)ex);
            l.log();
            throw new ServletException(l.getMessage().replace(cp, ""));
         } catch (NoClassDefFoundError var8) {
            cp = this.context.getClasspath();
            lc = this.context.getLogContext();
            l = HTTPLogger.logServletClassDefNotFoundLoggable(lc, this.name, this.className, cp, var8);
            l.log();
            throw new ServletException(l.getMessage().replace(cp, ""));
         } catch (UnsatisfiedLinkError var9) {
            cp = this.context.getClasspath();
            lc = this.context.getLogContext();
            l = HTTPLogger.logServletUnsatisfiedLinkLoggable(lc, this.name, this.className, cp, var9);
            l.log();
            throw new ServletException(l.getMessage().replace(cp, ""));
         } catch (VerifyError var10) {
            cp = this.context.getLogContext();
            l = HTTPLogger.logServletVerifyErrorLoggable(cp, this.name, this.className, var10);
            l.log();
            throw new ServletException(l.getMessage());
         } catch (ClassFormatError var11) {
            cp = this.context.getLogContext();
            l = HTTPLogger.logServletClassFormatErrorLoggable(cp, this.name, this.className, var11);
            l.log();
            throw new ServletException(l.getMessage());
         } catch (LinkageError var12) {
            cp = this.context.getClasspath();
            lc = this.context.getLogContext();
            l = HTTPLogger.logServletLinkageErrorLoggable(lc, this.name, this.className, cp, var12);
            l.log();
            throw new ServletException(l.getMessage().replace(cp, ""));
         }
      }
   }

   private void initStubLifecycleHelper() throws ServletException, ClassNotFoundException {
      if (this.servlet != null) {
         this.lifecycleHelper = new StubLifecycleHelper(this, this.servlet, this.context.getServletClassLoader());
      } else if (this.servletClass != null) {
         this.lifecycleHelper = new StubLifecycleHelper(this, this.servletClass, this.context.getServletClassLoader());
      } else {
         ClassLoader cl = this.getClassLoader();
         this.lifecycleHelper = new StubLifecycleHelper(this, cl.loadClass(this.className), this.context.getServletClassLoader());
      }

   }

   public String getFilePath() {
      return null;
   }

   Object[] invokeAnnotatedMethods(Class annotationToMatch, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Servlet s = null;
      Thread thread = Thread.currentThread();
      ClassLoader oldCL = this.context.pushEnvironment(thread);

      Object[] var7;
      try {
         try {
            s = this.getServlet(new WebAppServletContext.ContextRequestCallback(this.context, this.getFilePath()));
         } catch (Exception var11) {
            throw new InvocationTargetException(var11, "Failed to get Servlet instance");
         }

         List methods = this.getAnnotatedMethods(s, annotationToMatch);
         var7 = this.securityHelper.invokeAnnotatedMethods(s, methods, arguments);
      } finally {
         if (s != null && this.lifecycleHelper != null) {
            this.lifecycleHelper.returnServlet(s);
         }

         WebAppServletContext.popEnvironment(thread, oldCL);
      }

      return var7;
   }

   private List getAnnotatedMethods(Servlet s, Class annotationToMatch) {
      List methods = (List)this.annotatedMethodsCache.get(annotationToMatch);
      if (methods == null) {
         methods = new ArrayList();
         Method[] ms = s.getClass().getMethods();
         Method[] var5 = ms;
         int var6 = ms.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            if (m.isAnnotationPresent(annotationToMatch)) {
               ((List)methods).add(m);
            }
         }

         this.annotatedMethodsCache.put(annotationToMatch, methods);
      }

      return (List)methods;
   }

   protected ClassLoader getClassLoader() {
      return this.context.getServletClassLoader();
   }

   public final ServletContext getServletContext() {
      return this.context;
   }

   public final String getServletName() {
      return this.name;
   }

   Map getInitParametersMap() {
      Map retVal;
      if (this.initParams == null) {
         retVal = Collections.emptyMap();
      } else {
         retVal = this.initParams;
      }

      return retVal;
   }

   boolean setInitParameter(String name, String value) {
      if (this.initParams == null) {
         this.initParams = new HashMap();
      }

      if (this.initParams.containsKey(name)) {
         return false;
      } else {
         this.initParams.put(name, value);
         if ("wl-dispatch-policy".equals(name)) {
            this.setDispatchPolicy(value);
         }

         return true;
      }
   }

   public final Enumeration getInitParameterNames() {
      return (Enumeration)(this.initParams == null ? new EmptyEnumerator() : new IteratorEnumerator(this.initParams.keySet().iterator()));
   }

   public final String getInitParameter(String name) {
      return this.initParams == null ? null : (String)this.initParams.get(name);
   }

   public final String toString() {
      return super.toString() + " - " + this.getServletName() + " class: '" + this.className + "'";
   }

   public final boolean isDynamicallyGenerated() {
      return this.dynamicallyGenerated;
   }

   public final StubSecurityHelper getSecurityHelper() {
      return this.securityHelper;
   }

   public StubLifecycleHelper getLifecycleHelper() {
      return this.lifecycleHelper;
   }

   public WebAppServletContext getContext() {
      return this.context;
   }

   static ServletStubImpl getUnavailableStub(ServletStubImpl stub) {
      return new ServletStubImpl(stub.getServletName(), stub.getClassName(), (WebAppServletContext)stub.getServletContext()) {
         public void execute(ServletRequest req, ServletResponse rsp, FilterChainImpl chain) throws ServletException, IOException {
            throw new UnavailableException("Stub had been removed earlier due to UnavailableException with no timeout.");
         }
      };
   }

   static {
      _WLDF$INST_FLD_Servlet_Execute_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Execute_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletStubImpl.java", "weblogic.servlet.internal.ServletStubImpl", "execute", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Lweblogic/servlet/internal/FilterChainImpl;)V", 273, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Execute_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.ServletStubRenderer", false, true), (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Execute_Around_Medium};
   }
}
