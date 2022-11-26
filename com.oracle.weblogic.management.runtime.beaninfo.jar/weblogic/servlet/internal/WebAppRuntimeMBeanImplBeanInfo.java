package weblogic.servlet.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.ComponentConcurrentRuntimeMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;

public class WebAppRuntimeMBeanImplBeanInfo extends ComponentConcurrentRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebAppComponentRuntimeMBean.class;

   public WebAppRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebAppRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.WebAppRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("Describes a servlet component (servlet context). ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WebAppComponentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AllServletSessions")) {
         getterName = "getAllServletSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("AllServletSessions", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AllServletSessions", currentResult);
         currentResult.setValue("description", "<p>Returns a set of all current valid sessions as a list. If session monitoring is turned off, this method will return an empty list.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationIdentifier")) {
         getterName = "getApplicationIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationIdentifier", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationIdentifier", currentResult);
         currentResult.setValue("description", "<p>Provides the identifier of the application that contains the web module.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceClusterRuntime")) {
         getterName = "getCoherenceClusterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterRuntime", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceClusterRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the Coherence Cluster related runtime MBean for this component.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (!descriptors.containsKey("ComponentName")) {
         getterName = "getComponentName";
         setterName = null;
         currentResult = new PropertyDescriptor("ComponentName", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ComponentName", currentResult);
         currentResult.setValue("description", "<p>Provides the name of this component.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConfiguredContextRoot")) {
         getterName = "getConfiguredContextRoot";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredContextRoot", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredContextRoot", currentResult);
         currentResult.setValue("description", "<p> Returns the context root (context path) configured in weblogic-application.xml or web.xml file, or determined by name of the war file. </p>  <p> For web application deployed in partition scope, this configured context root does not include uriPrefix of the virtual target. The {@link #getContextRoot()} method returns value which includes the uriPrefix of the virtual target. </p>  <p> For web application deployed in domain scope, this method returns same value as {@link #getContextRoot()} method. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ContextRoot")) {
         getterName = "getContextRoot";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextRoot", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ContextRoot", currentResult);
         currentResult.setValue("description", "<p>Returns the context root (context path) for the web application.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("EJBRuntimes")) {
         getterName = "getEJBRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of EJBRuntimeMBean objects for this module. The EJBRuntimeMBean instances can be cast to their appropriate subclass (EntityEJBRuntimeMBean, StatelessEJBRuntimeMBean, StatefulEJBRuntimeMBean or MessageDrivenEJBRuntimeMBean) to access additional runtime information for the particular EJB.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPCompileCommand")) {
         getterName = "getJSPCompileCommand";
         setterName = null;
         currentResult = new PropertyDescriptor("JSPCompileCommand", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JSPCompileCommand", currentResult);
         currentResult.setValue("description", "<p>Provides the JSP's compileCommand value as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPPageCheckSecs")) {
         getterName = "getJSPPageCheckSecs";
         setterName = null;
         currentResult = new PropertyDescriptor("JSPPageCheckSecs", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JSPPageCheckSecs", currentResult);
         currentResult.setValue("description", "<p>Provides the JSP's PageCheckSecs value as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JaxRsApplications")) {
         getterName = "getJaxRsApplications";
         setterName = null;
         currentResult = new PropertyDescriptor("JaxRsApplications", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JaxRsApplications", currentResult);
         currentResult.setValue("description", "<p>Provides an array of JaxRsApplicationMBeans associated with this module.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoPersistenceUnitRuntimes")) {
         getterName = "getKodoPersistenceUnitRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPersistenceUnitRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("KodoPersistenceUnitRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of KodoPersistenceUnitRuntimeMBean objects for this web application module. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "As of 11.1.2.0, use getPersistenceUnitRuntimes instead ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryRuntimes")) {
         getterName = "getLibraryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of library runtime instances for each Java EE library that is contained in this enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFilename")) {
         getterName = "getLogFilename";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFilename", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogFilename", currentResult);
         currentResult.setValue("description", "Returns the log filename as configured in the \"logging/log-filename\" element in weblogic.xml. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the log runtime associated with the Java EE web application log. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedExecutorServiceRuntimes")) {
         getterName = "getManagedExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServiceRuntimes")) {
         getterName = "getManagedScheduledExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedScheduledExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedScheduledExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ManagedThreadFactoryRuntimes")) {
         getterName = "getManagedThreadFactoryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedThreadFactoryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedThreadFactorys defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ModuleURI")) {
         getterName = "getModuleURI";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleURI", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleURI", currentResult);
         currentResult.setValue("description", "<p>Returns the web URI as configured in application.xml for the web application. For a standalone WAR, it will return the docroot (if exploded) or the name of the WAR file (if archived).</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>Provides the name of this MBean.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OpenSessionsCurrentCount")) {
         getterName = "getOpenSessionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OpenSessionsCurrentCount", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OpenSessionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the current total number of open sessions in this module.</p>  <p>Returns the current total number of open sessions in this component.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OpenSessionsHighCount")) {
         getterName = "getOpenSessionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OpenSessionsHighCount", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OpenSessionsHighCount", currentResult);
         currentResult.setValue("description", "<p>Provides the high water mark of the total number of open sessions in this server. The count starts at zero each time the server is activated. Note that this is an optimization method for a highly useful statistic that could be implemented less efficiently using change notification.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PageFlows")) {
         getterName = "getPageFlows";
         setterName = null;
         currentResult = new PropertyDescriptor("PageFlows", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PageFlows", currentResult);
         currentResult.setValue("description", "<p>Provides a hook for getting Beehive runtime metrics for the current module.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitRuntimes")) {
         getterName = "getPersistenceUnitRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PersistenceUnitRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of PersistenceUnitRuntimeMBean objects for this web application module. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletReloadCheckSecs")) {
         getterName = "getServletReloadCheckSecs";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletReloadCheckSecs", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletReloadCheckSecs", currentResult);
         currentResult.setValue("description", "<p>Provides the servlet reload check seconds as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletSessions")) {
         getterName = "getServletSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletSessions", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletSessions", currentResult);
         currentResult.setValue("description", "<p>Provides an array of ServletSessionRuntimeMBeans associated with this component. This operation should only be done by explicit poll request (no real-time monitoring). This method will return a non-empty array only when session-monitoring has been turned on in weblogic.xml.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServletSessionsMonitoringIds()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "as of WebLogic 9.0, use getServletSessionsMonitoringTags() ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletSessionsMonitoringIds")) {
         getterName = "getServletSessionsMonitoringIds";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletSessionsMonitoringIds", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletSessionsMonitoringIds", currentResult);
         currentResult.setValue("description", "<p> This method returns an array of monitoring IDs for HTTP sessions. By default, the monitoring ID for a given HTTP session is a random string (not the same as session ID for security reasons). If the value of the element monitoring-attribute-name in session-descriptor of weblogic.xml is set, the monitoring ID will be the toString() of the attribute value in the session, using monitoring-attribute-name as the key. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Servlets")) {
         getterName = "getServlets";
         setterName = null;
         currentResult = new PropertyDescriptor("Servlets", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Servlets", currentResult);
         currentResult.setValue("description", "<p>Provides an array of ServletRuntimeMBeans associated with this module.</p>  <p>Returns an array of ServletRuntimeMBeans associated with this component.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionCookieComment")) {
         getterName = "getSessionCookieComment";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionCookieComment", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionCookieComment", currentResult);
         currentResult.setValue("description", "<p>By default, all applications on WebLogic Server specify \"WebLogic Session Tracking Cookie\" as the cookie comment. To provide a more specific comment, edit your application's <code>weblogic.xml</code> deployment descriptor.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionCookieDomain")) {
         getterName = "getSessionCookieDomain";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionCookieDomain", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionCookieDomain", currentResult);
         currentResult.setValue("description", "<p>By default, clients can return cookies only to the server that issued the cookie. You can change this default behavior by editing your application's <code>weblogic.xml</code> deployment descriptor.</p>  <p>For more information, see the servlet specification.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionCookieMaxAgeSecs")) {
         getterName = "getSessionCookieMaxAgeSecs";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionCookieMaxAgeSecs", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionCookieMaxAgeSecs", currentResult);
         currentResult.setValue("description", "<p>Provides the life span of the session cookie, in seconds, after which it expires on the client. If the value is 0, the cookie expires immediately. If set to -1, the cookie expires when the user exits the browser.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionCookieName")) {
         getterName = "getSessionCookieName";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionCookieName", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionCookieName", currentResult);
         currentResult.setValue("description", "<p>By default, all applications on WebLogic Server specify \"JSESSIONID\" as the cookie name. To provide a more specific name, edit your application's <code>weblogic.xml</code> deployment descriptor.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionCookiePath")) {
         getterName = "getSessionCookiePath";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionCookiePath", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionCookiePath", currentResult);
         currentResult.setValue("description", "<p>Provides the path name to which clients send cookies.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionIDLength")) {
         getterName = "getSessionIDLength";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionIDLength", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionIDLength", currentResult);
         currentResult.setValue("description", "<p>Provides the session ID length configured for HTTP sessions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionInvalidationIntervalSecs")) {
         getterName = "getSessionInvalidationIntervalSecs";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionInvalidationIntervalSecs", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionInvalidationIntervalSecs", currentResult);
         currentResult.setValue("description", "<p>Provides the invalidation check timer interval configured for HTTP sessions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionTimeoutSecs")) {
         getterName = "getSessionTimeoutSecs";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionTimeoutSecs", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>Provides the timeout configured for HTTP sessions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionsOpenedTotalCount")) {
         getterName = "getSessionsOpenedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionsOpenedTotalCount", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionsOpenedTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of sessions opened.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleThreadedServletPoolSize")) {
         getterName = "getSingleThreadedServletPoolSize";
         setterName = null;
         currentResult = new PropertyDescriptor("SingleThreadedServletPoolSize", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SingleThreadedServletPoolSize", currentResult);
         currentResult.setValue("description", "<p>Provides the single threaded servlet pool size as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceInfo")) {
         getterName = "getSourceInfo";
         setterName = null;
         currentResult = new PropertyDescriptor("SourceInfo", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SourceInfo", currentResult);
         currentResult.setValue("description", "<p>Provides an informative string about the module's source.</p>  <p>Returns an informative string about the component's source.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpringRuntimeMBean")) {
         getterName = "getSpringRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringRuntimeMBean", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringRuntimeMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "<p>Provides the status of the component.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebPubSubRuntime")) {
         getterName = "getWebPubSubRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WebPubSubRuntime", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WebPubSubRuntime", currentResult);
         currentResult.setValue("description", "Get HTTP pub-sub server runtime of this web application. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebsocketApplicationRuntimeMBean")) {
         getterName = "getWebsocketApplicationRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("WebsocketApplicationRuntimeMBean", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WebsocketApplicationRuntimeMBean", currentResult);
         currentResult.setValue("description", "<p>Provides a WebsocketApplicationRuntimeMBean associated with this module.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("WseeClientConfigurationRuntimes")) {
         getterName = "getWseeClientConfigurationRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClientConfigurationRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeClientConfigurationRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service client reference configuration runtime instances that are contained in this web application within an enterprise application.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WseeClientRuntimes")) {
         getterName = "getWseeClientRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClientRuntimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeClientRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service client runtime instances that are contained in this Enterprise JavaBean component. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WseeV2Runtimes")) {
         getterName = "getWseeV2Runtimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeV2Runtimes", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeV2Runtimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service runtime instances that are contained in this web application within an enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FilterDispatchedRequestsEnabled")) {
         getterName = "isFilterDispatchedRequestsEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("FilterDispatchedRequestsEnabled", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FilterDispatchedRequestsEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the dispatched requests are filtered as configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexDirectoryEnabled")) {
         getterName = "isIndexDirectoryEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("IndexDirectoryEnabled", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IndexDirectoryEnabled", currentResult);
         currentResult.setValue("description", "<p>Provides the directory indexing indicator as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPDebug")) {
         getterName = "isJSPDebug";
         setterName = null;
         currentResult = new PropertyDescriptor("JSPDebug", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JSPDebug", currentResult);
         currentResult.setValue("description", "<p>Provides the JSP's debug/linenumbers parameter value as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPKeepGenerated")) {
         getterName = "isJSPKeepGenerated";
         setterName = null;
         currentResult = new PropertyDescriptor("JSPKeepGenerated", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JSPKeepGenerated", currentResult);
         currentResult.setValue("description", "<p>Provides the JSP's KeepGenerated parameter value as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPVerbose")) {
         getterName = "isJSPVerbose";
         setterName = null;
         currentResult = new PropertyDescriptor("JSPVerbose", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JSPVerbose", currentResult);
         currentResult.setValue("description", "<p>Provides the JSP's verbose parameter value as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionMonitoringEnabled")) {
         getterName = "isSessionMonitoringEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionMonitoringEnabled", WebAppComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionMonitoringEnabled", currentResult);
         currentResult.setValue("description", "<p>Provides the session monitoring indicator as it is configured in weblogic.xml.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebAppComponentRuntimeMBean.class.getMethod("lookupJaxRsApplication", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the runtime MBean ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Finds the JAX-RS application identified by name that is nothing but the corresponding servlet name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JaxRsApplications");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("lookupWseeClientRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("rawClientId", "The raw client ID of the client to lookup. This ID does not contain the application/component qualifiers that are prepended to the full client ID for the client. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service client runtime instance that is contained in this Enterprise JavaBean component. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeClientRuntimes");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("lookupWseeV2Runtime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The web service description name of the web service to look up. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service runtime instance that is contained in this web application within an enterprise application. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeV2Runtimes");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("lookupWseeClientConfigurationRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The web service client reference name to look up. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service client reference configuration runtime instance that is contained in this web application within an enterprise application.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeClientConfigurationRuntimes");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebAppComponentRuntimeMBean.class.getMethod("getEJBRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("ejbName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the EJBRuntimeMBean for the EJB with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getServletSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sessionID", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "as of WebLogic 9.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the servlet session by its session ID.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("invalidateServletSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("monitoringId", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalStateException if sessions has been invalidated already")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Invalidates this session for a given monitoring ID.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServletSessionsMonitoringIds()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getSessionLastAccessedTime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("monitoringId", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalStateException if sessions has been invalidated already")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides a record of the last time this session was accessed. You need to pass the string returned by getServletSessionsMonitoringIds().</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServletSessionsMonitoringIds()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getSessionMaxInactiveInterval", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("monitoringId", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalStateException if sessions have been invalidated already")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns the timeout (seconds) for the session corresponding to the given monitoring ID.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getServletSessionsMonitoringIds()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getMonitoringId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sessionId", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalStateException if sessions have been invalidated already")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the monitoring ID for a session for a given session ID.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("deleteInvalidSessions");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Invalidates expired sessions. This is useful for cleanup if the session invalidation trigger is too large.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("registerServlet", String.class, String.class, String[].class, Map.class, Integer.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<servlet> and <servlet-mapping> If load-on-startup has been specified and this method is invoked after the server has started, the container will preload the servlet immediately. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getKodoPersistenceUnitRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("unitName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "As of 11.1.2.0, use getPersistenceUnitRuntime instead ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the KodoPersistenceUnitRuntimeMBean for the web application with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentRuntimeMBean.class.getMethod("getPersistenceUnitRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("unitName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the PersistenceUnitRuntimeMBean for the web application with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
