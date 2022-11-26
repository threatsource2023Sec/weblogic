package weblogic.jaxrs.monitoring.impl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import javax.servlet.FilterRegistration;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jaxrs.integration.internal.JAXRSIntegrationLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.JaxRsExceptionMapperStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceConfigTypeRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceRuntimeMBean;
import weblogic.management.runtime.JaxRsResponseStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsUriRuntimeMBean;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.servlet.internal.WebAppRuntimeMBeanImpl;
import weblogic.servlet.internal.WebAppServletContext;

public final class JaxRsApplicationMBeanImpl extends JaxRsMonitoringInfoMBeanImpl implements JaxRsApplicationRuntimeMBean {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugRestJersey2Integration");
   private final String applicationName;
   private final String applicationClass;
   private final Map configurationProperties;
   private final Set providers;
   private final Set registeredClasses;
   private final Set registeredInstances;
   private final ServletRuntimeMBean servlet;
   private final String wadlUrl;
   private final String rootPath;
   private final Map inflectorCache = new WeakHashMap();
   private JaxRsExecutionStatisticsMBeanImpl requestStats;
   private JaxRsResponseStatisticsMBeanImpl responseStats;
   private JaxRsExceptionMapperStatisticsMBeanImpl exceptionStats;
   private final Map classToResource = new HashMap();
   private final Map classResources = new TreeMap();
   private final Map uriResources = new TreeMap();
   private volatile String resourcePattern;
   private volatile WadlApplicationContext wadlContext;
   private final boolean extendedEnabled;
   private volatile JaxRsResourceConfigTypeRuntimeMBean resourceConfigMBean;
   private static final HashMap EMPTY_RESPONSE_CODE_COUNTS = new HashMap(0);
   private static final HashMap EMPTY_HTTP_METHOD_COUNTS = new HashMap(0);

   public JaxRsApplicationMBeanImpl(String name, WebAppServletContext context, WebAppRuntimeMBeanImpl parent, WadlApplicationContext wadlContext, ApplicationEvent applicationEvent, boolean extendedEnabled) throws ManagementException {
      super(name, parent);
      this.resourceConfigMBean = new JaxRsResourceConfigTypeMBeanImpl(this, applicationEvent.getResourceConfig());
      ResourceConfig config = applicationEvent.getResourceConfig();
      this.wadlContext = wadlContext;
      this.extendedEnabled = extendedEnabled;
      this.servlet = findServlet(name, parent.getServlets());
      if (this.servlet != null) {
         this.wadlUrl = this.getServletWadlUrl(this.servlet);
         this.rootPath = getServletRootPath(this.servlet);
      } else {
         this.wadlUrl = this.getFilterWadlUrl(name, context, parent.getServlets());
         this.rootPath = this.getFilterRootPath(name, context, parent.getServlets());
      }

      this.providers = new HashSet();
      this.registeredClasses = new HashSet();
      this.registeredInstances = new HashSet();
      Iterator var8 = applicationEvent.getProviders().iterator();

      Class registeredClass;
      while(var8.hasNext()) {
         registeredClass = (Class)var8.next();
         this.providers.add(registeredClass.getName());
      }

      var8 = applicationEvent.getRegisteredClasses().iterator();

      while(var8.hasNext()) {
         registeredClass = (Class)var8.next();
         this.registeredClasses.add(registeredClass.getName());
      }

      var8 = applicationEvent.getRegisteredInstances().iterator();

      while(var8.hasNext()) {
         Object registeredInstance = var8.next();
         this.registeredInstances.add(registeredInstance.getClass().getName());
      }

      this.applicationName = config.getApplicationName();
      this.applicationClass = unwrapApplicationClassName(config);
      this.configurationProperties = new HashMap();

      String stringValue;
      Map.Entry entry;
      for(var8 = config.getProperties().entrySet().iterator(); var8.hasNext(); this.configurationProperties.put(entry.getKey(), stringValue)) {
         entry = (Map.Entry)var8.next();
         Object value = entry.getValue();

         try {
            stringValue = value == null ? "[null]" : value.toString();
         } catch (Exception var13) {
            stringValue = LocalizationMessages.PROPERTY_VALUE_TOSTRING_THROWS_EXCEPTION(var13.getClass().getName(), var13.getMessage());
         }
      }

      var8 = applicationEvent.getResourceModel().getRootResources().iterator();

      while(var8.hasNext()) {
         Resource resource = (Resource)var8.next();
         Iterator var17 = resource.getResourceMethods().iterator();

         while(var17.hasNext()) {
            ResourceMethod method = (ResourceMethod)var17.next();
            this.classToResource.put(method.getInvocable().getHandler().getHandlerClass(), resource);
         }
      }

   }

   private static String unwrapApplicationClassName(ResourceConfig config) {
      Application app;
      ResourceConfig unwrapped;
      for(app = config.getApplication(); app instanceof ResourceConfig; app = unwrapped.getApplication()) {
         unwrapped = (ResourceConfig)app;
         if (unwrapped.getApplication() == unwrapped) {
            break;
         }
      }

      return app.getClass().getName();
   }

   private static ServletRuntimeMBean findServlet(String name, ServletRuntimeMBean[] servlets) {
      ServletRuntimeMBean[] var2 = servlets;
      int var3 = servlets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServletRuntimeMBean servlet = var2[var4];
         if (servlet.getServletName().equals(name)) {
            return servlet;
         }
      }

      return null;
   }

   private static ServletRuntimeMBean findServlet(Collection mappings, ServletRuntimeMBean[] servlets) {
      if (!mappings.isEmpty()) {
         Iterator var2 = mappings.iterator();

         while(var2.hasNext()) {
            String servletName = (String)var2.next();
            ServletRuntimeMBean servlet = findServlet(servletName, servlets);
            if (servlet != null) {
               return servlet;
            }
         }
      }

      return null;
   }

   public void initMonitoringStatistics(MonitoringStatistics stats) throws ManagementException {
      this.requestStats = new JaxRsExecutionStatisticsMBeanImpl(this.name + "_RequestStatistics", this, stats.getRequestStatistics());
      this.responseStats = new JaxRsResponseStatisticsMBeanImpl(this.name + "_ResponseStatistics", this, stats.getResponseStatistics());
      this.exceptionStats = new JaxRsExceptionMapperStatisticsMBeanImpl(this.name + "_ExceptionMapperStatistics", this, stats.getExceptionMapperStatistics());
      this.update(stats);
   }

   public void update(MonitoringStatistics stats) throws ManagementException {
      this.requestStats.update(stats.getRequestStatistics());
      this.responseStats.update(stats.getResponseStatistics());
      this.exceptionStats.update(stats.getExceptionMapperStatistics());
      this.updateClassResources(this.classResources, stats.getResourceClassStatistics());
      this.updateUriResources(this.uriResources, stats.getUriStatistics());
   }

   private Map updateClassResources(Map resources, Map stats) throws ManagementException {
      Iterator var3 = stats.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         Class key = (Class)entry.getKey();
         String clazz = key.getName();
         if (!resources.containsKey(clazz)) {
            Resource resource;
            if (!this.classToResource.containsKey(key)) {
               resource = Resource.from(key, true);
               if (resource != null) {
                  this.classToResource.put(key, resource);
               }
            }

            resource = (Resource)this.classToResource.get(key);
            String resourcePath = "";
            boolean extended = true;
            if (resource != null) {
               resourcePath = resource.getPath();
               extended = isExtended(key, resource);
            } else if (!this.isInflector(key)) {
               JAXRSIntegrationLogger.logCannotFindUriForResourceClass(key.getName());
            }

            if (this.extendedEnabled || !extended) {
               resources.put(clazz, new JaxRsResourceMBeanImpl(this.name + "_" + clazz, this, resourcePath, key, (ResourceStatistics)entry.getValue(), extended, this.extendedEnabled));
            }
         }

         JaxRsResourceMBeanImpl mbean = (JaxRsResourceMBeanImpl)resources.get(clazz);
         if (mbean != null) {
            mbean.update((ResourceStatistics)entry.getValue());
         }
      }

      return resources;
   }

   private static boolean isExtended(Class handlerClass, Resource resource) {
      Iterator var2 = resource.getResourceMethods().iterator();

      ResourceMethod method;
      do {
         if (!var2.hasNext()) {
            return resource.isExtended();
         }

         method = (ResourceMethod)var2.next();
      } while(!handlerClass.equals(method.getInvocable().getHandler().getHandlerClass()));

      return method.isExtended();
   }

   private boolean isInflector(Class key) {
      Boolean isInflector = (Boolean)this.inflectorCache.get(key);
      if (isInflector == null) {
         boolean assignableFrom = Inflector.class.isAssignableFrom(key);
         this.inflectorCache.put(key, assignableFrom);
         return assignableFrom;
      } else {
         return isInflector;
      }
   }

   private Map updateUriResources(Map resources, Map stats) throws ManagementException {
      Iterator var3 = stats.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String path = (String)entry.getKey();
         if (!resources.containsKey(path)) {
            boolean extended = true;

            ResourceMethod method;
            for(Iterator var7 = ((ResourceStatistics)entry.getValue()).getResourceMethodStatistics().keySet().iterator(); var7.hasNext(); extended &= method.isExtended()) {
               method = (ResourceMethod)var7.next();
            }

            if (this.extendedEnabled || !extended) {
               resources.put(path, new JaxRsUriMBeanImpl(this.name + "_" + path, this, path, (ResourceStatistics)entry.getValue(), extended, this.extendedEnabled));
            }
         }

         JaxRsUriMBeanImpl mbean = (JaxRsUriMBeanImpl)resources.get(path);
         if (mbean != null) {
            mbean.update((ResourceStatistics)entry.getValue());
         }
      }

      return resources;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getApplicationClass() {
      return this.applicationClass;
   }

   public Map getProperties() {
      return this.configurationProperties;
   }

   public Set getRegisteredClasses() {
      return this.registeredClasses;
   }

   public Set getRegisteredInstances() {
      return this.registeredInstances;
   }

   public Set getAllRegisteredClasses() {
      return this.providers;
   }

   public JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics() {
      return this.requestStats;
   }

   public JaxRsResponseStatisticsRuntimeMBean getResponseStatistics() {
      return this.responseStats;
   }

   public JaxRsExceptionMapperStatisticsRuntimeMBean getExceptionMapperStatistics() {
      return this.exceptionStats;
   }

   public JaxRsResourceRuntimeMBean[] getRootResourcesByClass() {
      return (JaxRsResourceRuntimeMBean[])this.classResources.values().toArray(new JaxRsResourceRuntimeMBean[this.classResources.size()]);
   }

   public JaxRsResourceRuntimeMBean lookupRootResourcesByClass(String clazz) {
      return (JaxRsResourceRuntimeMBean)this.classResources.get(clazz);
   }

   public JaxRsUriRuntimeMBean[] getRootResourcesByUri() {
      return (JaxRsUriRuntimeMBean[])this.uriResources.values().toArray(new JaxRsUriRuntimeMBean[this.uriResources.size()]);
   }

   public JaxRsUriRuntimeMBean lookupRootResourcesByUri(String uri) {
      return (JaxRsUriRuntimeMBean)this.uriResources.get(uri);
   }

   public ServletRuntimeMBean getServlet() {
      return this.servlet;
   }

   public boolean isWadlGenerationEnabled() {
      return this.wadlContext == null ? false : this.wadlContext.isWadlGenerationEnabled();
   }

   public void setWadlGenerationEnabled(boolean wadlGenEnabled) {
      if (this.wadlContext != null) {
         this.wadlContext.setWadlGenerationEnabled(wadlGenEnabled);
      }

   }

   public String getResourcePattern() {
      return this.resourcePattern;
   }

   public void setResourcePattern(String resPattern) {
      this.resourcePattern = resPattern;
   }

   public String getWadlUrl() {
      return this.wadlUrl;
   }

   private String getFilterWadlUrl(String name, WebAppServletContext context, ServletRuntimeMBean[] servlets) {
      FilterRegistration registration = context.getFilterRegistration(name);
      if (registration != null) {
         ServletRuntimeMBean servlet = findServlet(registration.getServletNameMappings(), servlets);
         if (servlet != null) {
            return this.getServletWadlUrl(servlet);
         }

         String rootPath = getFilterRootPath(context, registration);
         if (rootPath != null) {
            String partialUrl = "http:";
            if (servlets[0] != null) {
               try {
                  URL servletUrl = new URL(servlets[0].getURL());
                  partialUrl = (new URL(servletUrl.getProtocol(), servletUrl.getHost(), servletUrl.getPort(), "")).toString();
               } catch (MalformedURLException var9) {
                  LOGGER.debug("Unable to compute WADL URL, falling back to the default one.", var9);
               }
            }

            return this.computeWadlUrl(partialUrl, rootPath);
         }
      }

      return null;
   }

   private String getServletWadlUrl(ServletRuntimeMBean servlet) {
      return this.computeWadlUrl(servlet.getURL());
   }

   private String computeWadlUrl(String partialUrl) {
      return this.computeWadlUrl(partialUrl, (String)null);
   }

   private String computeWadlUrl(String partialUrl, String rootPath) {
      if (partialUrl == null) {
         return null;
      } else {
         String fullUrl = partialUrl;

         try {
            URL url = new URL(partialUrl);
            if (url.getHost() == null || "".equals(url.getHost())) {
               InetAddress locHost = InetAddress.getLocalHost();
               if (locHost != null) {
                  fullUrl = url.getProtocol() + "://" + locHost.getCanonicalHostName() + (url.getPort() >= 0 ? ":" + url.getPort() : "") + (rootPath == null ? url.getPath() : rootPath);
               }
            }
         } catch (UnknownHostException | MalformedURLException var6) {
            LOGGER.debug("Unable to compute WADL URL, falling back to the default one.", var6);
         }

         if (fullUrl.endsWith("/*")) {
            fullUrl = fullUrl.substring(0, fullUrl.length() - 1);
         }

         if (!fullUrl.endsWith("/")) {
            fullUrl = fullUrl + "/";
         }

         return fullUrl + "application.wadl";
      }
   }

   public String getRootPath() {
      return this.rootPath;
   }

   private static String getServletRootPath(ServletRuntimeMBean servlet) {
      return computeRootPath(servlet.getContextPath(), servlet.getServletPath());
   }

   private String getFilterRootPath(String name, WebAppServletContext context, ServletRuntimeMBean[] servlets) {
      FilterRegistration registration = context.getFilterRegistration(name);
      if (registration != null) {
         ServletRuntimeMBean servlet = findServlet(registration.getServletNameMappings(), servlets);
         return servlet != null ? getServletRootPath(servlet) : getFilterRootPath(context, registration);
      } else {
         return null;
      }
   }

   private static String getFilterRootPath(WebAppServletContext context, FilterRegistration registration) {
      Collection mappings = registration.getUrlPatternMappings();
      return !mappings.isEmpty() ? computeRootPath(context.getContextPath(), (String)mappings.iterator().next()) : null;
   }

   static String computeRootPath(String contextPath, String applicationPath) {
      if (contextPath == null) {
         contextPath = "";
      }

      if (applicationPath == null) {
         applicationPath = "";
      }

      if (contextPath.isEmpty() && applicationPath.isEmpty()) {
         return "";
      } else {
         StringBuilder rootPathBuilder = new StringBuilder();
         boolean endsWithSlash = false;
         if (!contextPath.isEmpty()) {
            if (!contextPath.startsWith("/")) {
               rootPathBuilder.append('/');
            }

            rootPathBuilder.append(contextPath);
            endsWithSlash = contextPath.endsWith("/");
         }

         if (!applicationPath.isEmpty()) {
            if (applicationPath.startsWith("/")) {
               if (endsWithSlash) {
                  rootPathBuilder.deleteCharAt(rootPathBuilder.length() - 1);
               }
            } else if (!endsWithSlash) {
               rootPathBuilder.append("/");
            }

            rootPathBuilder.append(applicationPath);
         }

         char[] var4 = "*/".toCharArray();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            char removeChar = var4[var6];
            if (rootPathBuilder.charAt(rootPathBuilder.length() - 1) == removeChar) {
               rootPathBuilder.deleteCharAt(rootPathBuilder.length() - 1);
            }
         }

         return rootPathBuilder.toString();
      }
   }

   public JaxRsResourceConfigTypeRuntimeMBean getResourceConfig() {
      return this.resourceConfigMBean;
   }

   public JaxRsResourceRuntimeMBean[] getRootResources() {
      Collection values = this.classResources.values();
      return (JaxRsResourceRuntimeMBean[])values.toArray(new JaxRsResourceRuntimeMBean[values.size()]);
   }

   public JaxRsResourceRuntimeMBean lookupRootResource(String name) {
      return (JaxRsResourceRuntimeMBean)this.classResources.get(name);
   }

   public long getErrorCount() {
      return this.exceptionStats != null ? this.exceptionStats.getUnsuccessfulMappings() : -1L;
   }

   public HashMap getResponseCodeCounts() {
      return this.responseStats != null ? new HashMap(this.responseStats.getResponseCodes()) : EMPTY_RESPONSE_CODE_COUNTS;
   }

   public int getLastResponseCode() {
      Integer lastResponseCode = null;
      if (this.responseStats != null) {
         lastResponseCode = this.responseStats.getLastResponseCode();
      }

      return lastResponseCode != null ? lastResponseCode : -1;
   }

   public boolean isApplicationEnabled() {
      return true;
   }

   public void setApplicationEnabled(boolean applicationEnabled) {
      JAXRSIntegrationLogger.logDoNotCallJaxRsApplicationRuntimeMBeanSetApplicationEnabled();
      if (!applicationEnabled && LOGGER.isDebugEnabled()) {
         LOGGER.debug("Exception created (but not thrown) just to record caller stack trace.", new RuntimeException("Who calls JaxRsApplicationRuntimeMBean.setApplicationEnabled(boolean)?"));
      }

   }

   public String[] getLastErrorDetails() {
      return null;
   }

   public long getLastErrorTime() {
      return -1L;
   }

   public String getLastErrorMapper() {
      return null;
   }

   public String getLastHttpMethod() {
      return null;
   }

   public HashMap getHttpMethodCounts() {
      return EMPTY_HTTP_METHOD_COUNTS;
   }
}
