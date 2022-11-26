package org.glassfish.grizzly.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.SingleThreadModel;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpUpgradeHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.SessionManager;
import org.glassfish.grizzly.http.server.StaticHttpHandlerBase;
import org.glassfish.grizzly.http.server.util.ClassLoaderUtil;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.Enumerator;
import org.glassfish.grizzly.http.server.util.Mapper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.MimeType;
import org.glassfish.grizzly.localization.LogMessages;

public class WebappContext implements ServletContext {
   private static final Logger LOGGER = Grizzly.logger(WebappContext.class);
   private static final Map DEPLOYED_APPS = new HashMap();
   private static final Set DEFAULT_SESSION_TRACKING_MODES;
   private static final int MAJOR_VERSION = 4;
   private static final int MINOR_VERSION = 0;
   private final String displayName;
   private final String contextPath;
   private final String basePath;
   private final Map contextInitParams;
   private final List securityRoles;
   protected final Map servletRegistrations;
   protected final Map filterRegistrations;
   protected final Map unmodifiableFilterRegistrations;
   private SessionManager sessionManager;
   private Set servletHandlers;
   private final Set eventListenerInstances;
   private EventListener[] eventListeners;
   protected boolean deployed;
   private final FilterChainFactory filterChainFactory;
   private final ConcurrentMap attributes;
   private volatile String serverInfo;
   private final ThreadLocal dispatchData;
   private DispatcherHelper dispatcherHelper;
   private ClassLoader webappClassLoader;
   int sessionTimeoutInSeconds;
   private String requestEncoding;
   private String responseEncoding;
   private javax.servlet.SessionCookieConfig sessionCookieConfig;
   private Set sessionTrackingModes;
   private final Runnable onDestroyListener;
   private final List filterMaps;

   protected WebappContext() {
      this.contextInitParams = new LinkedHashMap(8, 1.0F);
      this.securityRoles = new ArrayList();
      this.servletRegistrations = new HashMap(8, 1.0F);
      this.filterRegistrations = new LinkedHashMap(4, 1.0F);
      this.unmodifiableFilterRegistrations = Collections.unmodifiableMap(this.filterRegistrations);
      this.sessionManager = ServletSessionManager.instance();
      this.eventListenerInstances = new LinkedHashSet(4, 1.0F);
      this.eventListeners = new EventListener[0];
      this.attributes = new ConcurrentHashMap(16, 0.75F, 64);
      this.serverInfo = "grizzly/" + Grizzly.getDotedVersion();
      this.dispatchData = new ThreadLocal();
      this.onDestroyListener = new Runnable() {
         public void run() {
            if (WebappContext.this.deployed) {
               WebappContext.this.undeploy();
            }

         }
      };
      this.filterMaps = new ArrayList();
      this.displayName = "";
      this.contextPath = "";
      this.basePath = "";
      this.filterChainFactory = new FilterChainFactory(this);
   }

   public WebappContext(String displayName) {
      this(displayName, "");
   }

   public WebappContext(String displayName, String contextPath) {
      this(displayName, contextPath, ".");
   }

   public WebappContext(String displayName, String contextPath, String basePath) {
      this.contextInitParams = new LinkedHashMap(8, 1.0F);
      this.securityRoles = new ArrayList();
      this.servletRegistrations = new HashMap(8, 1.0F);
      this.filterRegistrations = new LinkedHashMap(4, 1.0F);
      this.unmodifiableFilterRegistrations = Collections.unmodifiableMap(this.filterRegistrations);
      this.sessionManager = ServletSessionManager.instance();
      this.eventListenerInstances = new LinkedHashSet(4, 1.0F);
      this.eventListeners = new EventListener[0];
      this.attributes = new ConcurrentHashMap(16, 0.75F, 64);
      this.serverInfo = "grizzly/" + Grizzly.getDotedVersion();
      this.dispatchData = new ThreadLocal();
      this.onDestroyListener = new Runnable() {
         public void run() {
            if (WebappContext.this.deployed) {
               WebappContext.this.undeploy();
            }

         }
      };
      this.filterMaps = new ArrayList();
      if (displayName != null && displayName.length() != 0) {
         if (contextPath == null) {
            throw new IllegalArgumentException("'contextPath' cannot be null");
         } else {
            if (contextPath.length() > 0) {
               if (contextPath.charAt(0) != '/') {
                  throw new IllegalArgumentException("'contextPath' must start with a forward slash");
               }

               if (!contextPath.equals("/") && contextPath.charAt(contextPath.length() - 1) == '/') {
                  throw new IllegalArgumentException("'contextPath' must not end with a forward slash");
               }
            }

            this.displayName = displayName;
            this.contextPath = contextPath;

            try {
               this.basePath = (new File(basePath)).getCanonicalPath();
            } catch (IOException var5) {
               throw new IllegalArgumentException("Unable to resolve path: " + basePath);
            }

            this.filterChainFactory = new FilterChainFactory(this);
            Mapper.setAllowReplacement(true);
         }
      } else {
         throw new IllegalArgumentException("'displayName' cannot be null or zero-length");
      }
   }

   public void setServerInfo(String serverInfo) {
      this.serverInfo = serverInfo;
   }

   public synchronized void deploy(HttpServer targetServer) {
      if (!this.deployed) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting application [{0}] ...", this.displayName);
         }

         boolean error = false;

         try {
            this.webappClassLoader = ClassLoaderUtil.createURLClassLoader((new File(this.getBasePath())).getCanonicalPath());
            if (this.getSessionCookieConfig().getName() == null) {
               SessionManager manager = targetServer.getServerConfiguration().getSessionManager();
               if (manager != null) {
                  this.getSessionCookieConfig().setName(manager.getSessionCookieName());
               }
            }

            String serverName = targetServer.getServerConfiguration().getHttpServerName();
            if (serverName != null) {
               String serverVersion = targetServer.getServerConfiguration().getHttpServerVersion();
               if (serverVersion != null) {
                  serverName = serverName + '/' + serverVersion;
               }
            }

            this.setServerInfo(serverName);
            this.sessionTimeoutInSeconds = targetServer.getServerConfiguration().getSessionTimeoutSeconds();
            this.initializeListeners();
            this.contextInitialized();
            this.initServlets(targetServer);
            this.initFilters();
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "Application [{0}] is ready to service requests.  Root: [{1}].", new Object[]{this.displayName, this.contextPath});
            }

            DEPLOYED_APPS.put(this, targetServer);
            this.deployed = true;
         } catch (Exception var8) {
            error = true;
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "[" + this.displayName + "] Exception deploying application.  See stack trace for details.", var8);
            }
         } finally {
            if (error) {
               this.undeploy();
            }

         }
      }

   }

   public synchronized void undeploy() {
      try {
         if (this.deployed) {
            this.deployed = false;
            HttpServer server = (HttpServer)DEPLOYED_APPS.remove(this);
            this.destoryServlets(server);
            this.destroyFilters();
            this.contextDestroyed();
         }
      } catch (Exception var2) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "[" + this.displayName + "] Exception undeploying application.  See stack trace for details.", var2);
         }
      }

   }

   public void addContextInitParameter(String name, String value) {
      if (!this.deployed) {
         this.contextInitParams.put(name, value);
      }

   }

   public void removeContextInitParameter(String name) {
      if (!this.deployed) {
         this.contextInitParams.remove(name);
      }

   }

   public void clearContextInitParameters() {
      if (!this.deployed) {
         this.contextInitParams.clear();
      }

   }

   public FilterRegistration addFilter(String filterName, Class filterClass) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (filterName == null) {
         throw new IllegalArgumentException("'filterName' cannot be null");
      } else if (filterClass == null) {
         throw new IllegalArgumentException("'filterClass' cannot be null");
      } else {
         FilterRegistration registration = (FilterRegistration)this.filterRegistrations.get(filterName);
         if (registration == null) {
            registration = new FilterRegistration(this, filterName, filterClass);
            this.filterRegistrations.put(filterName, registration);
         } else if (registration.filterClass != filterClass) {
            registration.filter = null;
            registration.filterClass = filterClass;
            registration.className = filterClass.getName();
         }

         return registration;
      }
   }

   public FilterRegistration addFilter(String filterName, Filter filter) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (filterName == null) {
         throw new IllegalArgumentException("'filterName' cannot be null");
      } else if (filter == null) {
         throw new IllegalArgumentException("'filter' cannot be null");
      } else {
         FilterRegistration registration = (FilterRegistration)this.filterRegistrations.get(filterName);
         if (registration == null) {
            registration = new FilterRegistration(this, filterName, filter);
            this.filterRegistrations.put(filterName, registration);
         } else if (registration.filter != filter) {
            registration.filter = filter;
            registration.filterClass = filter.getClass();
            registration.className = filter.getClass().getName();
         }

         return registration;
      }
   }

   public FilterRegistration addFilter(String filterName, String className) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (filterName == null) {
         throw new IllegalArgumentException("'filterName' cannot be null");
      } else if (className == null) {
         throw new IllegalArgumentException("'className' cannot be null");
      } else {
         FilterRegistration registration = (FilterRegistration)this.filterRegistrations.get(filterName);
         if (registration == null) {
            registration = new FilterRegistration(this, filterName, className);
            this.filterRegistrations.put(filterName, registration);
         } else if (!registration.className.equals(className)) {
            registration.className = className;
            registration.filterClass = null;
            registration.filter = null;
         }

         return registration;
      }
   }

   public ServletRegistration addServlet(String servletName, Class servletClass) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (servletName == null) {
         throw new IllegalArgumentException("'servletName' cannot be null");
      } else if (servletClass == null) {
         throw new IllegalArgumentException("'servletClass' cannot be null");
      } else {
         ServletRegistration registration = (ServletRegistration)this.servletRegistrations.get(servletName);
         if (registration == null) {
            registration = new ServletRegistration(this, servletName, servletClass);
            this.servletRegistrations.put(servletName, registration);
         } else if (registration.servletClass != servletClass) {
            registration.servlet = null;
            registration.servletClass = servletClass;
            registration.className = servletClass.getName();
         }

         return registration;
      }
   }

   public ServletRegistration addServlet(String servletName, Servlet servlet) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (servletName == null) {
         throw new IllegalArgumentException("'servletName' cannot be null");
      } else if (servlet == null) {
         throw new IllegalArgumentException("'servlet' cannot be null");
      } else if (servlet instanceof SingleThreadModel) {
         throw new IllegalArgumentException("SingleThreadModel Servlet instances are not allowed.");
      } else {
         ServletRegistration registration = (ServletRegistration)this.servletRegistrations.get(servletName);
         if (registration == null) {
            registration = new ServletRegistration(this, servletName, servlet);
            this.servletRegistrations.put(servletName, registration);
         } else if (registration.servlet != servlet) {
            registration.servlet = servlet;
            registration.servletClass = servlet.getClass();
            registration.className = servlet.getClass().getName();
         }

         return registration;
      }
   }

   public ServletRegistration addServlet(String servletName, String className) {
      if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else if (servletName == null) {
         throw new IllegalArgumentException("'servletName' cannot be null");
      } else if (className == null) {
         throw new IllegalArgumentException("'className' cannot be null");
      } else {
         ServletRegistration registration = (ServletRegistration)this.servletRegistrations.get(servletName);
         if (registration == null) {
            registration = new ServletRegistration(this, servletName, className);
            this.servletRegistrations.put(servletName, registration);
         } else if (!registration.className.equals(className)) {
            registration.servlet = null;
            registration.servletClass = null;
            registration.className = className;
         }

         return registration;
      }
   }

   public FilterRegistration getFilterRegistration(String name) {
      return name == null ? null : (FilterRegistration)this.filterRegistrations.get(name);
   }

   public Map getFilterRegistrations() {
      return this.unmodifiableFilterRegistrations;
   }

   public ServletRegistration getServletRegistration(String name) {
      return name == null ? null : (ServletRegistration)this.servletRegistrations.get(name);
   }

   public Map getServletRegistrations() {
      return Collections.unmodifiableMap(this.servletRegistrations);
   }

   public void addListener(Class listenerClass) {
      if (this.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (listenerClass == null) {
         throw new IllegalArgumentException("'listener' cannot be null");
      } else {
         try {
            this.addListener(this.createEventListenerInstance(listenerClass));
         } catch (Exception var3) {
            throw new IllegalStateException(var3);
         }
      }
   }

   public void addListener(String className) {
      if (this.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (className == null) {
         throw new IllegalArgumentException("'className' cannot be null");
      } else {
         try {
            this.addListener(this.createEventListenerInstance(className));
         } catch (Exception var3) {
            throw new IllegalStateException(var3);
         }
      }
   }

   public void addListener(EventListener eventListener) {
      if (this.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else {
         this.eventListenerInstances.add(eventListener);
      }
   }

   public Servlet createServlet(Class clazz) throws ServletException {
      try {
         return this.createServletInstance(clazz);
      } catch (Exception var3) {
         throw new ServletException(var3);
      }
   }

   public Filter createFilter(Class clazz) throws ServletException {
      try {
         return this.createFilterInstance(clazz);
      } catch (Exception var3) {
         throw new ServletException(var3);
      }
   }

   public EventListener createListener(Class clazz) throws ServletException {
      try {
         return this.createEventListenerInstance(clazz);
      } catch (Exception var3) {
         throw new ServletException(var3);
      }
   }

   public void declareRoles(String... roleNames) {
      if (this.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else {
         this.securityRoles.addAll(Arrays.asList(roleNames));
      }
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public ServletContext getContext(String uri) {
      if (uri != null && uri.startsWith("/")) {
         if (this.dispatcherHelper == null) {
            return null;
         } else {
            DispatchData dd = (DispatchData)this.dispatchData.get();
            if (dd == null) {
               dd = new DispatchData();
               this.dispatchData.set(dd);
            } else {
               dd.recycle();
            }

            DataChunk uriDC = dd.uriDC;
            MappingData mappingData = dd.mappingData;

            try {
               uriDC.setString(uri);
               this.dispatcherHelper.mapPath((HttpRequestPacket)null, uriDC, mappingData);
               if (mappingData.context == null) {
                  return null;
               }
            } catch (Exception var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "Error during mapping", var6);
               }

               return null;
            }

            if (!(mappingData.context instanceof ServletHandler)) {
               return null;
            } else {
               ServletHandler context = (ServletHandler)mappingData.context;
               return context.getServletCtx();
            }
         }
      } else {
         return null;
      }
   }

   public int getMajorVersion() {
      return 4;
   }

   public int getMinorVersion() {
      return 0;
   }

   public int getEffectiveMajorVersion() {
      return 4;
   }

   public int getEffectiveMinorVersion() {
      return 0;
   }

   public String getMimeType(String file) {
      if (file == null) {
         return null;
      } else {
         int period = file.lastIndexOf(".");
         if (period < 0) {
            return null;
         } else {
            String extension = file.substring(period + 1);
            return extension.length() < 1 ? null : MimeType.get(extension);
         }
      }
   }

   public Set getResourcePaths(String path) {
      if (path == null) {
         return null;
      } else if (!path.startsWith("/")) {
         throw new IllegalArgumentException(path);
      } else {
         path = this.normalize(path);
         if (path == null) {
            return null;
         } else {
            File[] files = (new File(this.basePath, path)).listFiles();
            Set set = Collections.emptySet();
            if (files != null) {
               set = new HashSet(files.length);
               File[] var4 = files;
               int var5 = files.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  File f = var4[var6];

                  try {
                     String canonicalPath = f.getCanonicalPath();
                     if (f.isDirectory()) {
                        canonicalPath = canonicalPath + "/";
                     }

                     canonicalPath = canonicalPath.substring(canonicalPath.indexOf(this.basePath) + this.basePath.length());
                     ((Set)set).add(canonicalPath.replace("\\", "/"));
                  } catch (IOException var9) {
                     throw new RuntimeException(var9);
                  }
               }
            }

            return (Set)set;
         }
      }
   }

   public URL getResource(String path) throws MalformedURLException {
      if (path != null && path.startsWith("/")) {
         path = this.normalize(path);
         if (path == null) {
            return null;
         } else {
            if (path.length() > 1) {
               path = path.substring(1);
            }

            return Thread.currentThread().getContextClassLoader().getResource(path);
         }
      } else {
         throw new MalformedURLException(path);
      }
   }

   public InputStream getResourceAsStream(String path) {
      String pathLocal = this.normalize(path);
      if (pathLocal == null) {
         return null;
      } else {
         if (pathLocal.length() > 1) {
            pathLocal = pathLocal.substring(1);
         }

         return Thread.currentThread().getContextClassLoader().getResourceAsStream(pathLocal);
      }
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      if (path == null) {
         return null;
      } else if (this.dispatcherHelper == null) {
         return null;
      } else if (!path.startsWith("/") && !path.isEmpty()) {
         throw new IllegalArgumentException("Path " + path + " does not start with ''/'' and is not empty");
      } else {
         String queryString = null;
         int pos = path.indexOf(63);
         if (pos >= 0) {
            queryString = path.substring(pos + 1);
            path = path.substring(0, pos);
         }

         path = this.normalize(path);
         if (path == null) {
            return null;
         } else {
            DispatchData dd = (DispatchData)this.dispatchData.get();
            if (dd == null) {
               dd = new DispatchData();
               this.dispatchData.set(dd);
            } else {
               dd.recycle();
            }

            DataChunk uriDC = dd.uriDC;
            MappingData mappingData = dd.mappingData;

            try {
               if (this.contextPath.length() == 1 && this.contextPath.charAt(0) == '/') {
                  uriDC.setString(path);
               } else {
                  uriDC.setString(this.contextPath + path);
               }

               this.dispatcherHelper.mapPath((HttpRequestPacket)null, uriDC, mappingData);
               if (mappingData.wrapper == null) {
                  return null;
               }
            } catch (Exception var10) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "Error during mapping", var10);
               }

               return null;
            }

            if (!(mappingData.wrapper instanceof ServletHandler)) {
               return null;
            } else {
               ServletHandler wrapper = (ServletHandler)mappingData.wrapper;
               String wrapperPath = mappingData.wrapperPath.toString();
               String pathInfo = mappingData.pathInfo.toString();
               return new ApplicationDispatcher(wrapper, uriDC.toString(), wrapperPath, pathInfo, queryString, (String)null);
            }
         }
      }
   }

   public RequestDispatcher getNamedDispatcher(String name) {
      if (name == null) {
         return null;
      } else if (this.dispatcherHelper == null) {
         return null;
      } else {
         DispatchData dd = (DispatchData)this.dispatchData.get();
         if (dd == null) {
            dd = new DispatchData();
            this.dispatchData.set(dd);
         } else {
            dd.recycle();
         }

         DataChunk servletNameDC = dd.servletNameDC;
         MappingData mappingData = dd.mappingData;
         servletNameDC.setString(name);

         ServletHandler wrapper;
         try {
            this.dispatcherHelper.mapName(servletNameDC, mappingData);
            if (!(mappingData.wrapper instanceof ServletHandler)) {
               return null;
            }

            wrapper = (ServletHandler)mappingData.wrapper;
            if (!this.contextPath.equals(wrapper.getContextPath())) {
               return null;
            }
         } catch (Exception var6) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Error during mapping", var6);
            }

            return null;
         }

         wrapper = (ServletHandler)mappingData.wrapper;
         return new ApplicationDispatcher(wrapper, (String)null, (String)null, (String)null, (String)null, name);
      }
   }

   public javax.servlet.ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
      if (this.deployed) {
         throw new IllegalStateException();
      } else if (servletName == null) {
         throw new IllegalArgumentException();
      } else {
         return null;
      }
   }

   public int getSessionTimeout() {
      return (int)TimeUnit.MINUTES.convert((long)this.sessionTimeoutInSeconds, TimeUnit.SECONDS);
   }

   public void setSessionTimeout(int sessionTimeout) {
      if (this.deployed) {
         throw new IllegalStateException();
      } else {
         this.sessionTimeoutInSeconds = (int)TimeUnit.SECONDS.convert((long)sessionTimeout, TimeUnit.MINUTES);
      }
   }

   public String getRequestCharacterEncoding() {
      return this.requestEncoding;
   }

   public void setRequestCharacterEncoding(String requestEncoding) {
      if (this.deployed) {
         throw new IllegalStateException();
      } else {
         this.requestEncoding = requestEncoding;
      }
   }

   public String getResponseCharacterEncoding() {
      return this.responseEncoding;
   }

   public void setResponseCharacterEncoding(String responseEncoding) {
      if (this.deployed) {
         throw new IllegalStateException();
      } else {
         this.responseEncoding = responseEncoding;
      }
   }

   /** @deprecated */
   @Deprecated
   public Servlet getServlet(String name) throws ServletException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Enumeration getServlets() {
      return new Enumerator(Collections.emptyList());
   }

   /** @deprecated */
   @Deprecated
   public Enumeration getServletNames() {
      return new Enumerator(Collections.emptyList());
   }

   public void log(String message) {
      LOGGER.log(Level.INFO, String.format("[%s] %s", this.displayName, message));
   }

   /** @deprecated */
   @Deprecated
   public void log(Exception e, String message) {
      this.log((String)message, (Throwable)e);
   }

   public void log(String message, Throwable throwable) {
      LOGGER.log(Level.INFO, String.format("[%s] %s", this.displayName, message), throwable);
   }

   public String getRealPath(String path) {
      return path == null ? null : (new File(this.basePath, path)).getAbsolutePath();
   }

   public String getVirtualServerName() {
      return "server";
   }

   public String getServerInfo() {
      return this.serverInfo;
   }

   public String getInitParameter(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return (String)this.contextInitParams.get(name);
      }
   }

   public Enumeration getInitParameterNames() {
      return new Enumerator(this.contextInitParams.keySet());
   }

   public boolean setInitParameter(String name, String value) {
      if (name == null) {
         throw new NullPointerException();
      } else if (!this.deployed) {
         this.contextInitParams.put(name, value);
         return true;
      } else {
         return false;
      }
   }

   public Object getAttribute(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return this.attributes.get(name);
      }
   }

   public Enumeration getAttributeNames() {
      return new Enumerator(this.attributes.keySet());
   }

   public void setAttribute(String name, Object value) {
      if (name == null) {
         throw new NullPointerException();
      } else if (value == null) {
         this.removeAttribute(name);
      } else {
         Object oldValue = this.attributes.put(name, value);
         ServletContextAttributeEvent event = null;
         int i = 0;

         for(int len = this.eventListeners.length; i < len; ++i) {
            if (this.eventListeners[i] instanceof ServletContextAttributeListener) {
               ServletContextAttributeListener listener = (ServletContextAttributeListener)this.eventListeners[i];

               try {
                  if (event == null) {
                     if (oldValue != null) {
                        event = new ServletContextAttributeEvent(this, name, oldValue);
                     } else {
                        event = new ServletContextAttributeEvent(this, name, value);
                     }
                  }

                  if (oldValue != null) {
                     listener.attributeReplaced(event);
                  } else {
                     listener.attributeAdded(event);
                  }
               } catch (Throwable var9) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR("ServletContextAttributeListener", listener.getClass().getName()), var9);
                  }
               }
            }
         }

      }
   }

   public void removeAttribute(String name) {
      Object value = this.attributes.remove(name);
      if (value != null) {
         ServletContextAttributeEvent event = null;
         int i = 0;

         for(int len = this.eventListeners.length; i < len; ++i) {
            if (this.eventListeners[i] instanceof ServletContextAttributeListener) {
               ServletContextAttributeListener listener = (ServletContextAttributeListener)this.eventListeners[i];

               try {
                  if (event == null) {
                     event = new ServletContextAttributeEvent(this, name, value);
                  }

                  listener.attributeRemoved(event);
               } catch (Throwable var8) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR("ServletContextAttributeListener", listener.getClass().getName()), var8);
                  }
               }
            }
         }

      }
   }

   public String getServletContextName() {
      return this.displayName;
   }

   public javax.servlet.SessionCookieConfig getSessionCookieConfig() {
      if (this.sessionCookieConfig == null) {
         this.sessionCookieConfig = new SessionCookieConfig(this);
      }

      return this.sessionCookieConfig;
   }

   public void setSessionTrackingModes(Set sessionTrackingModes) {
      if (sessionTrackingModes.contains(SessionTrackingMode.SSL)) {
         throw new IllegalArgumentException("SSL tracking mode is not supported");
      } else if (this.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.sessionTrackingModes = Collections.unmodifiableSet(sessionTrackingModes);
      }
   }

   public Set getDefaultSessionTrackingModes() {
      return DEFAULT_SESSION_TRACKING_MODES;
   }

   public Set getEffectiveSessionTrackingModes() {
      return this.sessionTrackingModes != null ? this.sessionTrackingModes : DEFAULT_SESSION_TRACKING_MODES;
   }

   public JspConfigDescriptor getJspConfigDescriptor() {
      return null;
   }

   public ClassLoader getClassLoader() {
      return null;
   }

   protected String normalize(String path) {
      if (path == null) {
         return null;
      } else {
         String normalized = path;
         if (path.indexOf(92) >= 0) {
            normalized = path.replace('\\', '/');
         }

         while(true) {
            int index = normalized.indexOf("/../");
            if (index < 0) {
               return normalized;
            }

            if (index == 0) {
               return null;
            }

            int index2 = normalized.lastIndexOf(47, index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
         }
      }
   }

   protected String getBasePath() {
      return this.basePath;
   }

   protected void setDispatcherHelper(DispatcherHelper dispatcherHelper) {
      this.dispatcherHelper = dispatcherHelper;
   }

   public void setSessionManager(SessionManager sessionManager) {
      this.sessionManager = sessionManager;
   }

   protected EventListener[] getEventListeners() {
      return this.eventListeners;
   }

   protected void addFilterMap(FilterMap filterMap, boolean isMatchAfter) {
      String filterName = filterMap.getFilterName();
      String servletName = filterMap.getServletName();
      String urlPattern = filterMap.getURLPattern();
      if (null == this.filterRegistrations.get(filterName)) {
         throw new IllegalArgumentException("Filter mapping specifies an unknown filter name: " + filterName);
      } else if (servletName == null && urlPattern == null) {
         throw new IllegalArgumentException("Filter mapping must specify either a <url-pattern> or a <servlet-name>");
      } else if (servletName != null && urlPattern != null) {
         throw new IllegalArgumentException("Filter mapping must specify either a <url-pattern> or a <servlet-name>");
      } else if (urlPattern != null && !this.validateURLPattern(urlPattern)) {
         throw new IllegalArgumentException("Invalid <url-pattern> {0} in filter mapping: " + urlPattern);
      } else {
         if (isMatchAfter) {
            this.filterMaps.add(filterMap);
         } else {
            this.filterMaps.add(0, filterMap);
         }

      }
   }

   protected List getFilterMaps() {
      return this.filterMaps;
   }

   protected void removeFilterMaps() {
      this.filterMaps.clear();
   }

   protected Collection getServletNameFilterMappings(String filterName) {
      HashSet mappings = new HashSet();
      synchronized(this.filterMaps) {
         Iterator var4 = this.filterMaps.iterator();

         while(var4.hasNext()) {
            FilterMap fm = (FilterMap)var4.next();
            if (filterName.equals(fm.getFilterName()) && fm.getServletName() != null) {
               mappings.add(fm.getServletName());
            }
         }

         return mappings;
      }
   }

   protected Collection getUrlPatternFilterMappings(String filterName) {
      HashSet mappings = new HashSet();
      synchronized(this.filterMaps) {
         Iterator var4 = this.filterMaps.iterator();

         while(var4.hasNext()) {
            FilterMap fm = (FilterMap)var4.next();
            if (filterName.equals(fm.getFilterName()) && fm.getURLPattern() != null) {
               mappings.add(fm.getURLPattern());
            }
         }

         return mappings;
      }
   }

   protected FilterChainFactory getFilterChainFactory() {
      return this.filterChainFactory;
   }

   protected void unregisterFilter(Filter f) {
      synchronized(this.filterRegistrations) {
         Iterator i = this.filterRegistrations.values().iterator();

         while(true) {
            FilterRegistration registration;
            do {
               if (!i.hasNext()) {
                  return;
               }

               registration = (FilterRegistration)i.next();
            } while(registration.filter != f);

            Iterator fmi = this.filterMaps.iterator();

            while(fmi.hasNext()) {
               FilterMap fm = (FilterMap)fmi.next();
               if (fm.getFilterName().equals(registration.name)) {
                  fmi.remove();
               }
            }

            f.destroy();
            i.remove();
         }
      }
   }

   protected void unregisterAllFilters() {
      this.destroyFilters();
   }

   protected void destroyFilters() {
      Iterator var1 = this.filterRegistrations.values().iterator();

      while(var1.hasNext()) {
         FilterRegistration registration = (FilterRegistration)var1.next();
         registration.filter.destroy();
      }

      this.removeFilterMaps();
   }

   private void destoryServlets(HttpServer server) {
      if (this.servletHandlers != null && !this.servletHandlers.isEmpty()) {
         ServerConfiguration config = server.getServerConfiguration();
         Iterator var3 = this.servletHandlers.iterator();

         while(var3.hasNext()) {
            ServletHandler handler = (ServletHandler)var3.next();
            config.removeHttpHandler(handler);
         }
      }

   }

   private void initializeListeners() throws ServletException {
      if (!this.eventListenerInstances.isEmpty()) {
         this.eventListeners = (EventListener[])this.eventListenerInstances.toArray(new EventListener[this.eventListenerInstances.size()]);
      }

   }

   private void initServlets(HttpServer server) throws ServletException {
      boolean defaultMappingAdded = false;
      if (!this.servletRegistrations.isEmpty()) {
         ServerConfiguration serverConfig = server.getServerConfiguration();
         this.servletHandlers = new LinkedHashSet(this.servletRegistrations.size(), 1.0F);
         LinkedList sortedRegistrations = new LinkedList(this.servletRegistrations.values());
         Collections.sort(sortedRegistrations);
         Iterator var5 = sortedRegistrations.iterator();

         while(var5.hasNext()) {
            ServletRegistration registration = (ServletRegistration)var5.next();
            ServletConfigImpl sConfig = this.createServletConfig(registration);
            if (registration.servlet != null) {
               try {
                  registration.servlet.init(sConfig);
               } catch (Exception var14) {
                  throw new RuntimeException(var14);
               }
            } else if (registration.loadOnStartup >= 0) {
               try {
                  Servlet servletInstance = this.createServletInstance(registration);
                  LOGGER.log(Level.INFO, "Loading Servlet: {0}", servletInstance.getClass().getName());
                  servletInstance.init(sConfig);
               } catch (Exception var13) {
                  throw new RuntimeException(var13);
               }
            }

            ServletHandler servletHandler = new ServletHandler(sConfig);
            servletHandler.setServletInstance(registration.servlet);
            servletHandler.setServletClass(registration.servletClass);
            servletHandler.setServletClassName(registration.className);
            servletHandler.setSessionManager(this.sessionManager);
            servletHandler.setContextPath(this.contextPath);
            servletHandler.setFilterChainFactory(this.filterChainFactory);
            servletHandler.setExpectationHandler(registration.expectationHandler);
            servletHandler.addOnDestroyListener(this.onDestroyListener);
            servletHandler.setClassLoader(this.webappClassLoader);
            String[] patterns = (String[])registration.urlPatterns.getArray();
            if (patterns != null && patterns.length > 0) {
               String[] mappings = new String[patterns.length];
               int i = 0;

               while(true) {
                  if (i >= patterns.length) {
                     serverConfig.addHttpHandler(servletHandler, mappings);
                     break;
                  }

                  String pattern = patterns[i];
                  if (pattern.length() == 0 || "/".equals(pattern)) {
                     defaultMappingAdded = true;
                  }

                  mappings[i] = updateMappings(servletHandler, pattern);
                  ++i;
               }
            } else {
               serverConfig.addHttpHandler(servletHandler, new String[]{updateMappings(servletHandler, "")});
            }

            this.servletHandlers.add(servletHandler);
            if (LOGGER.isLoggable(Level.INFO)) {
               String p = patterns == null ? "" : Arrays.toString(patterns);
               LOGGER.log(Level.INFO, "[{0}] Servlet [{1}] registered for url pattern(s) [{2}].", new Object[]{this.displayName, registration.className, p});
            }
         }
      }

      if (!defaultMappingAdded) {
         this.registerDefaultServlet(server);
      }

   }

   private void registerDefaultServlet(HttpServer server) {
      ServerConfiguration serverConfig = server.getServerConfiguration();
      Map handlers = serverConfig.getHttpHandlers();
      Iterator var4 = handlers.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         HttpHandler h;
         do {
            if (!var4.hasNext()) {
               return;
            }

            entry = (Map.Entry)var4.next();
            h = (HttpHandler)entry.getKey();
         } while(!(h instanceof StaticHttpHandlerBase));

         String[] mappings = (String[])entry.getValue();
         String[] var8 = mappings;
         int var9 = mappings.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String mapping = var8[var10];
            if ("/".equals(mapping)) {
               DefaultServlet s = new DefaultServlet((StaticHttpHandlerBase)h);
               ServletRegistration registration = this.addServlet("DefaultServlet", (Servlet)s);
               registration.addMapping("/");
               ServletConfigImpl sConfig = this.createServletConfig(registration);

               try {
                  s.init(sConfig);
               } catch (ServletException var16) {
               }

               ServletHandler servletHandler = new ServletHandler(sConfig);
               servletHandler.setServletInstance(registration.servlet);
               servletHandler.setServletClass(registration.servletClass);
               servletHandler.setServletClassName(registration.className);
               servletHandler.setContextPath(this.contextPath);
               servletHandler.setFilterChainFactory(this.filterChainFactory);
               servletHandler.setExpectationHandler(registration.expectationHandler);
               servletHandler.addOnDestroyListener(this.onDestroyListener);
               serverConfig.addHttpHandler(servletHandler, new String[]{updateMappings(servletHandler, "/")});
               if (this.servletHandlers == null) {
                  this.servletHandlers = new LinkedHashSet(1, 1.0F);
               }

               this.servletHandlers.add(servletHandler);
            }
         }
      }
   }

   private void initFilters() {
      if (!this.filterRegistrations.isEmpty()) {
         Iterator var1 = this.filterRegistrations.values().iterator();

         while(var1.hasNext()) {
            FilterRegistration registration = (FilterRegistration)var1.next();

            try {
               Filter f = registration.filter;
               if (f == null) {
                  f = this.createFilterInstance(registration);
               }

               FilterConfigImpl filterConfig = this.createFilterConfig(registration);
               registration.filter = f;
               f.init(filterConfig);
               if (LOGGER.isLoggable(Level.INFO)) {
                  LOGGER.log(Level.INFO, "[{0}] Filter [{1}] registered for url pattern(s) [{2}] and servlet name(s) [{3}]", new Object[]{this.displayName, registration.className, registration.getUrlPatternMappings(), registration.getServletNameMappings()});
               }
            } catch (Exception var5) {
               throw new RuntimeException(var5);
            }
         }
      }

   }

   private static String updateMappings(ServletHandler handler, String mapping) {
      String mappingLocal = mapping;
      if (mapping.length() == 0) {
         mappingLocal = "/";
      } else {
         if (mapping.charAt(0) == '*') {
            mappingLocal = "/" + mapping;
         }

         if (mappingLocal.indexOf("//", 1) != -1) {
            mappingLocal = mappingLocal.replaceAll("//", "/");
         }
      }

      String contextPath = handler.getContextPath();
      contextPath = contextPath.length() == 0 ? "/" : contextPath;
      return contextPath + mappingLocal;
   }

   private FilterConfigImpl createFilterConfig(FilterRegistration registration) {
      FilterConfigImpl fConfig = new FilterConfigImpl(this);
      fConfig.setFilterName(registration.getName());
      if (!registration.initParameters.isEmpty()) {
         fConfig.setInitParameters(registration.initParameters);
      }

      return fConfig;
   }

   private ServletConfigImpl createServletConfig(ServletRegistration registration) {
      ServletConfigImpl sConfig = new ServletConfigImpl(this);
      sConfig.setServletName(registration.getName());
      if (!registration.initParameters.isEmpty()) {
         sConfig.setInitParameters(registration.initParameters);
      }

      return sConfig;
   }

   private void contextInitialized() {
      ServletContextEvent event = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.webappClassLoader);
      int i = 0;

      for(int len = this.eventListeners.length; i < len; ++i) {
         if (this.eventListeners[i] instanceof ServletContextListener) {
            ServletContextListener listener = (ServletContextListener)this.eventListeners[i];
            if (event == null) {
               event = new ServletContextEvent(this);
            }

            try {
               listener.contextInitialized(event);
            } catch (Throwable var7) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR("contextInitialized", "ServletContextListener", listener.getClass().getName()), var7);
               }
            }
         }
      }

      Thread.currentThread().setContextClassLoader(loader);
   }

   private void contextDestroyed() {
      ServletContextEvent event = null;
      int i = 0;

      for(int len = this.eventListeners.length; i < len; ++i) {
         if (this.eventListeners[i] instanceof ServletContextListener) {
            ServletContextListener listener = (ServletContextListener)this.eventListeners[i];
            if (event == null) {
               event = new ServletContextEvent(this);
            }

            try {
               listener.contextDestroyed(event);
            } catch (Throwable var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR("contextDestroyed", "ServletContextListener", listener.getClass().getName()), var6);
               }
            }
         }
      }

   }

   protected Servlet createServletInstance(ServletRegistration registration) throws Exception {
      String servletClassName = registration.className;
      Class servletClass = registration.servletClass;
      return servletClassName != null ? (Servlet)ClassLoaderUtil.load(servletClassName) : this.createServletInstance(servletClass);
   }

   protected Servlet createServletInstance(Class servletClass) throws Exception {
      return (Servlet)servletClass.newInstance();
   }

   protected Filter createFilterInstance(FilterRegistration registration) throws Exception {
      String filterClassName = registration.className;
      Class filterClass = registration.filterClass;
      return filterClassName != null ? (Filter)ClassLoaderUtil.load(filterClassName) : this.createFilterInstance(filterClass);
   }

   protected Filter createFilterInstance(Class filterClass) throws Exception {
      return (Filter)filterClass.newInstance();
   }

   protected EventListener createEventListenerInstance(Class eventListenerClass) throws Exception {
      return (EventListener)eventListenerClass.newInstance();
   }

   protected EventListener createEventListenerInstance(String eventListenerClassname) throws Exception {
      return (EventListener)ClassLoaderUtil.load(eventListenerClassname);
   }

   public HttpUpgradeHandler createHttpUpgradeHandlerInstance(Class clazz) throws Exception {
      return (HttpUpgradeHandler)clazz.newInstance();
   }

   protected boolean validateURLPattern(String urlPattern) {
      if (urlPattern == null) {
         return false;
      } else if (urlPattern.isEmpty()) {
         return true;
      } else if (urlPattern.indexOf(10) < 0 && urlPattern.indexOf(13) < 0) {
         if (urlPattern.startsWith("*.")) {
            if (urlPattern.indexOf(47) < 0) {
               this.checkUnusualURLPattern(urlPattern);
               return true;
            } else {
               return false;
            }
         } else if (urlPattern.startsWith("/") && !urlPattern.contains("*.")) {
            this.checkUnusualURLPattern(urlPattern);
            return true;
         } else {
            return false;
         }
      } else {
         LOGGER.log(Level.WARNING, "The URL pattern ''{0}'' contains a CR or LF and so can never be matched", urlPattern);
         return false;
      }
   }

   private void checkUnusualURLPattern(String urlPattern) {
      if (LOGGER.isLoggable(Level.INFO) && urlPattern.endsWith("*") && (urlPattern.length() < 2 || urlPattern.charAt(urlPattern.length() - 2) != '/')) {
         LOGGER.log(Level.INFO, "Suspicious url pattern: \"{0}\" in context - see section SRV.11.2 of the Servlet specification", urlPattern);
      }

   }

   static {
      DEFAULT_SESSION_TRACKING_MODES = EnumSet.of(SessionTrackingMode.COOKIE);
   }

   private static final class DispatchData {
      public final DataChunk uriDC = DataChunk.newInstance();
      public final DataChunk servletNameDC = DataChunk.newInstance();
      public final MappingData mappingData = new MappingData();

      public DispatchData() {
      }

      public void recycle() {
         this.uriDC.recycle();
         this.servletNameDC.recycle();
         this.mappingData.recycle();
      }
   }
}
