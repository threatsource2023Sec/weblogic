package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebAppContainerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebAppContainerMBean.class;

   public WebAppContainerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebAppContainerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebAppContainerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean is used to specify domain-wide defaults for the WebApp container. In general, these properties can be overridden at the cluster level (in ClusterMBean, if the same property is present there), the server level (in ServerMBean, if the same property is present there) or for a specific Web application (in weblogic.xml).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebAppContainerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("GzipCompression")) {
         getterName = "getGzipCompression";
         setterName = null;
         currentResult = new PropertyDescriptor("GzipCompression", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("GzipCompression", currentResult);
         currentResult.setValue("description", "<p>Get the GzipCompressionMBean, which represents GZIP compression support configuration. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Http2Config")) {
         getterName = "getHttp2Config";
         setterName = null;
         currentResult = new PropertyDescriptor("Http2Config", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("Http2Config", currentResult);
         currentResult.setValue("description", "<p>Get the getHttp2Config from WebSerevrMBean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("MaxPostSize")) {
         getterName = "getMaxPostSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPostSize";
         }

         currentResult = new PropertyDescriptor("MaxPostSize", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("MaxPostSize", currentResult);
         currentResult.setValue("description", "<p>The maximum post size this server allows for reading HTTP POST data in a servlet request. A value less than 0 indicates an unlimited size.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxPostSize(int)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getMaxPostSize()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxPostTimeSecs")) {
         getterName = "getMaxPostTimeSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPostTimeSecs";
         }

         currentResult = new PropertyDescriptor("MaxPostTimeSecs", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("MaxPostTimeSecs", currentResult);
         currentResult.setValue("description", "<p>Maximum post time (in seconds) for reading HTTP POST data in a servlet request. MaxPostTime &lt; 0 means unlimited</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxPostTimeSecs(int)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getMaxPostTimeSecs()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRequestParameterCount")) {
         getterName = "getMaxRequestParameterCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestParameterCount";
         }

         currentResult = new PropertyDescriptor("MaxRequestParameterCount", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("MaxRequestParameterCount", currentResult);
         currentResult.setValue("description", "<p>The maximum request parameter count this server allows for reading maximum HTTP POST parameters count in a servlet request.</p> <p/> <p>Gets the maxRequestParameterCount attribute of the WebServerMBean object.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getMaxRequestParameterCount()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRequestParamterCount")) {
         getterName = "getMaxRequestParamterCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestParamterCount";
         }

         currentResult = new PropertyDescriptor("MaxRequestParamterCount", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("MaxRequestParamterCount", currentResult);
         currentResult.setValue("description", "<p>The maximum request parameter count this server allows for reading maximum HTTP POST parameters count in a servlet request.</p> <p/> <p>Gets the maxRequestParamterCount attribute of the WebServerMBean object.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getMaxRequestParamterCount()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("deprecated", "Use getMaxRequestParameterCount() ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MimeMappingFile")) {
         getterName = "getMimeMappingFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMimeMappingFile";
         }

         currentResult = new PropertyDescriptor("MimeMappingFile", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("MimeMappingFile", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the file containing mime-mappings for the domain.</p> <p>The Format of the file should be: extension=mime-type </p> <p> For Example:</p> <p>htm=text/html</p> <p>gif=image/gif</p> <p>jpg=image/jpeg</p> <p/> <p>If this file does not exist, WebLogic Server uses an implicit mime-mapping set of mappings defined in weblogic.utils.http.HttpConstants (DEFAULT_MIME_MAPPINGS). To remove a mapping defined in implicit map, set it to blank. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMimeMappingFile(String)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "./config/mimemappings.properties");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("P3PHeaderValue")) {
         getterName = "getP3PHeaderValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setP3PHeaderValue";
         }

         currentResult = new PropertyDescriptor("P3PHeaderValue", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("P3PHeaderValue", currentResult);
         currentResult.setValue("description", "<p> Returns the P3P header value that will be sent with all responses for HTTP requests (if non-null). The value of this header points to the location of the policy reference file for the website.</p> <p/> <p>Alternatively, a servlet filter can be used to set the P3P header.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setP3PHeaderValue(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostTimeoutSecs")) {
         getterName = "getPostTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("PostTimeoutSecs", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("PostTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time this server waits between receiving chunks of data in an HTTP POST data before it times out. (This is used to prevent denial-of-service attacks that attempt to overload the server with POST data.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setPostTimeoutSecs(int)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getPostTimeoutSecs()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("secureValue", new Integer(30));
         currentResult.setValue("legalMax", new Integer(120));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletReloadCheckSecs")) {
         getterName = "getServletReloadCheckSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletReloadCheckSecs";
         }

         currentResult = new PropertyDescriptor("ServletReloadCheckSecs", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ServletReloadCheckSecs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restProductionModeDefault", new Integer(-1));
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("XPoweredByHeaderLevel")) {
         getterName = "getXPoweredByHeaderLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXPoweredByHeaderLevel";
         }

         currentResult = new PropertyDescriptor("XPoweredByHeaderLevel", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("XPoweredByHeaderLevel", currentResult);
         currentResult.setValue("description", "<p> WebLogic Server uses the X-Powered-By HTTP header, as recommended by the Servlet 3.1 specification, to publish its implementation information.</p> <p/> <p> Following are the options: </p> <ul> <li>\"NONE\" (default):  X-Powered-By header will not be sent </li> <li>\"SHORT\":           \"Servlet/3.1 JSP/2.3\"  </li> <li>\"MEDIUM\":          \"Servlet/3.1 JSP/2.3 (WebLogic/12.2)\" </li> <li>\"FULL\":            \"Servlet/3.1 JSP/2.3 (WebLogic/12.2 JDK/1.8)\" </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setXPoweredByHeaderLevel(String)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "NONE");
         currentResult.setValue("legalValues", new Object[]{"NONE", "SHORT", "MEDIUM", "FULL"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowAllRoles")) {
         getterName = "isAllowAllRoles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowAllRoles";
         }

         currentResult = new PropertyDescriptor("AllowAllRoles", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("AllowAllRoles", currentResult);
         currentResult.setValue("description", "<p>In the security-constraints elements defined in a web application's web.xml deployment descriptor, the auth-constraint element indicates the user roles that should be permitted access to this resource collection. Here role-name = \"*\" is a compact syntax for indicating all roles in the Web application. In previous releases, role-name = \"*\" was treated as all users/roles defined in the realm.</p>  <p>This parameter is a backward-compatibility switch to restore old behavior. Default behavior is one required by the specification, meaning all roles defined in the web application.</p>  <p>If set, the value defined in weblogic.xml (container-descriptor -> allow-all-roles) takes precedence (if set) over this value. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setAllowAllRoles(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthCookieEnabled")) {
         getterName = "isAuthCookieEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthCookieEnabled";
         }

         currentResult = new PropertyDescriptor("AuthCookieEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("AuthCookieEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the AuthCookie feature is enabled or not.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setAuthCookieEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#isAuthCookieEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChangeSessionIDOnAuthentication")) {
         getterName = "isChangeSessionIDOnAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChangeSessionIDOnAuthentication";
         }

         currentResult = new PropertyDescriptor("ChangeSessionIDOnAuthentication", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ChangeSessionIDOnAuthentication", currentResult);
         currentResult.setValue("description", "<p>Global property to determine if we need to generate a new SessionID after authentication. When this property is set to \"false\", the previous sessionID will be retained even after authorization. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertProxyEnabled")) {
         getterName = "isClientCertProxyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertProxyEnabled";
         }

         currentResult = new PropertyDescriptor("ClientCertProxyEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ClientCertProxyEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not to honor the WL-Proxy-Client-Cert header coming with the request. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setClientCertProxyEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#isClientCertProxyEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isClientCertProxyEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("FilterDispatchedRequestsEnabled")) {
         getterName = "isFilterDispatchedRequestsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFilterDispatchedRequestsEnabled";
         }

         currentResult = new PropertyDescriptor("FilterDispatchedRequestsEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("FilterDispatchedRequestsEnabled", currentResult);
         currentResult.setValue("description", "<p> Indicates whether or not to apply filters to dispatched requests. This is a backward compatibility flag. Until version 8.1, WebLogic Server applied ServletFilters (if configured for the web application) on request dispatches (and includes/forwards). Servlet 2.4 has introduced the \"Dispatcher\" element to make this behavior explicit. The default value is Dispatcher=REQUEST. In order to be compliant with the Java EE specification, the default value for FilterDispatchedRequestsEnabled is false beginning with WebLogic Server 9.0. Note that if you are using old descriptors (meaning web.xml does not have version=2.4), then WebLogic Server automatically uses FilterDispatchedRequestsEnabled = true for the web applications, unless filter-dispatched-requests-enabled is explicitly set to false in weblogic.xml. This means that old applications will work fine without any modification. Additionally, during migration of old domains to the 9.0 domain, the migration plug-in automatically sets this flag to true. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setFilterDispatchedRequestsEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpTraceSupportEnabled")) {
         getterName = "isHttpTraceSupportEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpTraceSupportEnabled";
         }

         currentResult = new PropertyDescriptor("HttpTraceSupportEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("HttpTraceSupportEnabled", currentResult);
         currentResult.setValue("description", "<p> Returns the value of HttpTraceSupportEnabled. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setHttpTraceSupportEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#setHttpTraceSupportEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#setHttpTraceSupportEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSPCompilerBackwardsCompatible")) {
         getterName = "isJSPCompilerBackwardsCompatible";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJSPCompilerBackwardsCompatible";
         }

         currentResult = new PropertyDescriptor("JSPCompilerBackwardsCompatible", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("JSPCompilerBackwardsCompatible", currentResult);
         currentResult.setValue("description", "<p>Global property to determine the behavior of the JSP compiler. When this property set to \"true\", the JSP compiler throws a translation error for JSPs that do not conform to the JSP2.0 specification. This property exists for backward compatibility.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JaxRsMonitoringDefaultBehavior")) {
         getterName = "isJaxRsMonitoringDefaultBehavior";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJaxRsMonitoringDefaultBehavior";
         }

         currentResult = new PropertyDescriptor("JaxRsMonitoringDefaultBehavior", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("JaxRsMonitoringDefaultBehavior", currentResult);
         currentResult.setValue("description", "<p> Global property to determine the behavior of monitoring in JAX-RS applications. When the property is set to {@code true} (or not set) the monitoring is turned on (if not overridden by properties set directly in application). If the property is set to {@code false} the monitoring for all JAX-RS applications is disabled. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("OptimisticSerialization")) {
         getterName = "isOptimisticSerialization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimisticSerialization";
         }

         currentResult = new PropertyDescriptor("OptimisticSerialization", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("OptimisticSerialization", currentResult);
         currentResult.setValue("description", "<p> When OptimisticSerialization is turned on, WebLogic Server does not serialize-deserialize context and request attributes upon getAttribute(name) when a request gets dispatched across servlet contexts. This means you will need to make sure that the attributes common to web applications are scoped to a common parent classloader (they are application-scoped) or placed in the system classpath if the two web applications do not belong to the same application. When OptimisticSerialization is turned off (which is the default), WebLogic Server does serialize-deserialize context and request attributes upon getAttribute(name) to avoid the possibility of ClassCastExceptions. The value of OptimisticSerialization can also be overridden for specific web applications by setting the optimistic-serialization value in weblogic.xml. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isOptimisticSerialization()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OverloadProtectionEnabled")) {
         getterName = "isOverloadProtectionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverloadProtectionEnabled";
         }

         currentResult = new PropertyDescriptor("OverloadProtectionEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("OverloadProtectionEnabled", currentResult);
         currentResult.setValue("description", "<p>This parameter is used to enable overload protection in the web application container against low memory conditions. When a low memory situation occurs, new session creation attempts will result in weblogic.servlet.SessionCreationException. The application code needs to catch this exception and take proper action. Alternatively appropriate error-pages can be configured in web.xml against weblogic.servlet.SessionCreationException. This check is performed only on memory and replicated sessions. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.servlet.SessionCreationException"), BeanInfoHelper.encodeEntities("#setOverloadProtectionEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReloginEnabled")) {
         getterName = "isReloginEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReloginEnabled";
         }

         currentResult = new PropertyDescriptor("ReloginEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ReloginEnabled", currentResult);
         currentResult.setValue("description", "<p>Beginning with the 9.0 release, the FORM/BASIC authentication behavior has been modified to conform strictly to the Java EE Specification. If a user has logged-in but does not have privileges to access a resource, the 403 (FORBIDDEN) page will be returned. Turn this flag on to enable the old behavior, which was to return the user to the login form.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setReloginEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetainOriginalURL")) {
         getterName = "isRetainOriginalURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetainOriginalURL";
         }

         currentResult = new PropertyDescriptor("RetainOriginalURL", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("RetainOriginalURL", currentResult);
         currentResult.setValue("description", "<p> The retain-original-url property is used in FORM based authentication scenarios. When this property is set to true, after a successful authentication, WebLogic Server will redirect back to the web resource (page/servlet) retaining the protocol (http/https) used to access the protected resource in the original request. If set to false (which is the default value), WebLogic Server will redirect back to the protected resource using the current protocol. The retain-original-url value can also be specified at per web application level in weblogic.xml. The value in weblogic.xml, if specified, overrides the domain level value.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isRetainOriginalURL()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RtexprvalueJspParamName")) {
         getterName = "isRtexprvalueJspParamName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRtexprvalueJspParamName";
         }

         currentResult = new PropertyDescriptor("RtexprvalueJspParamName", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("RtexprvalueJspParamName", currentResult);
         currentResult.setValue("description", "<p>Global property which determines the behavior of the JSP compiler when a jsp:param attribute \"name\" has a request time value. Without this property set to \"true\", the JSP compiler throws an error for a JSP using a request time value for the \"name\" attribute as mandated by the JSP 2.0 specification. This property exists for backward compatibility.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setRtexprvalueJspParamName(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletAuthenticationFormURL")) {
         getterName = "isServletAuthenticationFormURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletAuthenticationFormURL";
         }

         currentResult = new PropertyDescriptor("ServletAuthenticationFormURL", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ServletAuthenticationFormURL", currentResult);
         currentResult.setValue("description", "<p> ServletAuthenticationFormURL is used for backward compatibility with previous releases of Weblogic Server. If ServletAuthenticationFormURL is set to true (default), then ServletAuthentication.getTargetURLForFormAuthentication() and HttpSession.getAttribute(AuthFilter.TARGET_URL) will return the URL of the protected target resource. If set to false, the above APIs will return the URI of the protected target resource. By default, the value is set to true.(new method added in 9.0.0.1) </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.servlet.security.ServletAuthentication#getTargetURLForFormAuthentication"), BeanInfoHelper.encodeEntities("weblogic.servlet.security.ServletAuthentication#getTargetURIForFormAuthentication"), BeanInfoHelper.encodeEntities("#isServletAuthenticationFormURL()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShowArchivedRealPathEnabled")) {
         getterName = "isShowArchivedRealPathEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShowArchivedRealPathEnabled";
         }

         currentResult = new PropertyDescriptor("ShowArchivedRealPathEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("ShowArchivedRealPathEnabled", currentResult);
         currentResult.setValue("description", "<p>Global property to determine the behavior of getRealPath() for archived web applications. When this property is set to \"true\", getRealPath() will return the canonical path of the resource files. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WAPEnabled")) {
         getterName = "isWAPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWAPEnabled";
         }

         currentResult = new PropertyDescriptor("WAPEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("WAPEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the session ID should include JVM information. (Checking this box may be necessary when using URL rewriting with WAP devices that limit the size of the URL to 128 characters, and may also affect the use of replicated sessions in a cluster.) When this box is selected, the default size of the URL will be set at 52 characters, and it will not contain any special characters.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setWAPEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#isWAPEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicPluginEnabled")) {
         getterName = "isWeblogicPluginEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWeblogicPluginEnabled";
         }

         currentResult = new PropertyDescriptor("WeblogicPluginEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("WeblogicPluginEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not the proprietary <code>WL-Proxy-Client-IP</code> header should be honored. (This is needed only when WebLogic Server plug-ins are configured.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setWeblogicPluginEnabled(boolean)"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#isWeblogicPluginEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isWeblogicPluginEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkContextPropagationEnabled")) {
         getterName = "isWorkContextPropagationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkContextPropagationEnabled";
         }

         currentResult = new PropertyDescriptor("WorkContextPropagationEnabled", WebAppContainerMBean.class, getterName, setterName);
         descriptors.put("WorkContextPropagationEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not WorkContextPropagation is enabled. By default, it is turned on. There is a little overhead involved in propagating WorkContexts. Therefore, if you don't want WorkContext propagation, turn this value off in production environments. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setWorkContextPropagationEnabled(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebAppContainerMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebAppContainerMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebAppContainerMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppContainerMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
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
