package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebAppComponentMBeanImplBeanInfo extends ComponentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebAppComponentMBean.class;

   public WebAppComponentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebAppComponentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebAppComponentMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 in favor of {@link AppDeploymentMBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Provides methods for configuring a Java EE web application that is deployed on a WebLogic Server instance. WebLogic Server instantiates this interface only when you deploy a web application. <p>This interface can configure web applications that are deployed as a WAR file or an exploded directory. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebAppComponentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ActivatedTargets")) {
         getterName = "getActivatedTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivatedTargets", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("ActivatedTargets", currentResult);
         currentResult.setValue("description", "<p>List of servers and clusters where this module is currently active. This attribute is valid only for modules deployed via the two phase protocol. Modules deployed with the WLS 6.x deployment protocol do not maintain this attribute.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ApplicationMBean#isTwoPhase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("Application")) {
         getterName = "getApplication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplication";
         }

         currentResult = new PropertyDescriptor("Application", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("Application", currentResult);
         currentResult.setValue("description", "<p>The application this component is a part of. This is guaranteed to never be null.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("AuthFilter")) {
         getterName = "getAuthFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthFilter";
         }

         currentResult = new PropertyDescriptor("AuthFilter", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("AuthFilter", currentResult);
         currentResult.setValue("description", "<p>Provides the name of the AuthFilter Servlet class, which will be called before and after all authentication and authorization checks in the Web Application.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "8.0.0.0 Use weblogic.xml. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthRealmName")) {
         getterName = "getAuthRealmName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthRealmName";
         }

         currentResult = new PropertyDescriptor("AuthRealmName", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("AuthRealmName", currentResult);
         currentResult.setValue("description", "<p>Provides the name of the Realm in the Basic Authentication HTTP dialog box, which pops up on the browsers. authRealmName is now set in weblogic.xml.</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic");
         currentResult.setValue("deprecated", "8.1.0.0 Use weblogic.xml. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ServletReloadCheckSecs")) {
         getterName = "getServletReloadCheckSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletReloadCheckSecs";
         }

         currentResult = new PropertyDescriptor("ServletReloadCheckSecs", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("ServletReloadCheckSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time (in seconds) that WebLogic Server waits to check if a servlet was modified and needs to be reloaded.</p> <p/> <p>How often WebLogic checks whether a servlet has been modified, and if so reloads it. When the value is set to -1, the servlet is never reloaded, and when the vlue is set to 0, the servlet is reloaded after each check.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("deprecated", "8.1.0.0 Use weblogic.xml or update using console. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleThreadedServletPoolSize")) {
         getterName = "getSingleThreadedServletPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingleThreadedServletPoolSize";
         }

         currentResult = new PropertyDescriptor("SingleThreadedServletPoolSize", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("SingleThreadedServletPoolSize", currentResult);
         currentResult.setValue("description", "<p>This provides size of the pool used for single threaded mode servlets. It</p> <p/> <p>defines the size of the pool used for SingleThreadedModel instance pools.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("deprecated", "8.1.0.0 Use weblogic.xml or update using console. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion) && !descriptors.containsKey("VirtualHosts")) {
         getterName = "getVirtualHosts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVirtualHosts";
         }

         currentResult = new PropertyDescriptor("VirtualHosts", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("VirtualHosts", currentResult);
         currentResult.setValue("description", "<p>Provides a means to target your deployments to specific virtual hosts.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeVirtualHost");
         currentResult.setValue("adder", "addVirtualHost");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "7.0.0.0.");
      }

      if (!descriptors.containsKey("WebServers")) {
         getterName = "getWebServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWebServers";
         }

         currentResult = new PropertyDescriptor("WebServers", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("WebServers", currentResult);
         currentResult.setValue("description", "<p>Returns a list of the targets on which this deployment is deployed.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addWebServer");
         currentResult.setValue("remover", "removeWebServer");
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute. To target  an actual web server, the ComponentMBean.Targets attribute should be used. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("IndexDirectoryEnabled")) {
         getterName = "isIndexDirectoryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexDirectoryEnabled";
         }

         currentResult = new PropertyDescriptor("IndexDirectoryEnabled", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("IndexDirectoryEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the target should automatically generate an HTML directory listing if no suitable index file is found.</p> <p/> <p>Indicates whether or not to automatically generate an HTML directory listing if no suitable index file is found.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", "8.1.0.0 Use weblogic.xml or update using console. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JaxRsMonitoringDefaultBehavior")) {
         getterName = "isJaxRsMonitoringDefaultBehavior";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJaxRsMonitoringDefaultBehavior";
         }

         currentResult = new PropertyDescriptor("JaxRsMonitoringDefaultBehavior", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("JaxRsMonitoringDefaultBehavior", currentResult);
         currentResult.setValue("description", "<p> Per-application property to determine the behavior of monitoring in JAX-RS applications. When the property is set to {@code true} the monitoring is turned on (if not overridden by properties set directly in application). If the property is set to {@code false} the monitoring for all JAX-RS applications is disabled. If the property is not set then the global property ({@link WebAppContainerMBean#isJaxRsMonitoringDefaultBehavior()}) is being considered. </p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PreferWebInfClasses")) {
         getterName = "isPreferWebInfClasses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreferWebInfClasses";
         }

         currentResult = new PropertyDescriptor("PreferWebInfClasses", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("PreferWebInfClasses", currentResult);
         currentResult.setValue("description", "<p>Specifies whether classes loaded in the WEB-INF directory will be loaded in preference to classes loaded in the application or system calssloader.</p> <p/> <p>Deprecated the setting from console beginning with version 8.1. You must now set this in weblogic.xml.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "8.0.0.0 Use weblogic.xml. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletExtensionCaseSensitive")) {
         getterName = "isServletExtensionCaseSensitive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletExtensionCaseSensitive";
         }

         currentResult = new PropertyDescriptor("ServletExtensionCaseSensitive", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("ServletExtensionCaseSensitive", currentResult);
         currentResult.setValue("description", "<p>Indicates whether servlet extensions should be treated as though they are lower case even if they are written in upper case.</p> <p/> <p>If True, the server treats all .extensions except .html as lower case. This is only necessary on WindowsNT. This property is being deprecated. The extension comparision will be case insensitive by default on Win32.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionMonitoringEnabled")) {
         getterName = "isSessionMonitoringEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionMonitoringEnabled";
         }

         currentResult = new PropertyDescriptor("SessionMonitoringEnabled", WebAppComponentMBean.class, getterName, setterName);
         descriptors.put("SessionMonitoringEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether runtime MBeans will be created for session monitoring.</p> <p/> <p>If true, then runtime MBeans will be created for sessions; otherwise, they will not.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "8.0.0.0 Use weblogic.xml or update using console. ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebAppComponentMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = WebAppComponentMBean.class.getMethod("addWebServer", WebServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the WebServer attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This adds a target to the list of web servers to which a deployment may be targeted.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "WebServers");
      }

      mth = WebAppComponentMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = WebAppComponentMBean.class.getMethod("removeWebServer", WebServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This removes a target from the list of web servers which may be targeted for deployments.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "WebServers");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion)) {
         mth = WebAppComponentMBean.class.getMethod("addVirtualHost", VirtualHostMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the VirtualHost attribute ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0.");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Used to add a virtual host to the list of virtual hosts to which deployments may be targeted.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "VirtualHosts");
            currentResult.setValue("since", "7.0.0.0.");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion)) {
         mth = WebAppComponentMBean.class.getMethod("removeVirtualHost", VirtualHostMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0.");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Used to remove a virtual host from the list of virtual hosts to which deployments may be targeted.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "VirtualHosts");
            currentResult.setValue("since", "7.0.0.0.");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebAppComponentMBean.class.getMethod("addTag", String.class);
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
         mth = WebAppComponentMBean.class.getMethod("removeTag", String.class);
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = WebAppComponentMBean.class.getMethod("activated", TargetMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Indicates whether component has been activated on a server</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      mth = WebAppComponentMBean.class.getMethod("refreshDDsIfNeeded", String[].class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebAppComponentMBean.class.getMethod("restoreDefaultValue", String.class);
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
