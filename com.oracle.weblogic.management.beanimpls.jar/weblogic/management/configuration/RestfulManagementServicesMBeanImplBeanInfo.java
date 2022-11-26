package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class RestfulManagementServicesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RestfulManagementServicesMBean.class;

   public RestfulManagementServicesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RestfulManagementServicesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.RestfulManagementServicesMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.6.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Controls the configuration of the RESTful Management Services interfaces to WebLogic Server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.RestfulManagementServicesMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CORSAllowedHeaders")) {
         getterName = "getCORSAllowedHeaders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSAllowedHeaders";
         }

         currentResult = new PropertyDescriptor("CORSAllowedHeaders", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSAllowedHeaders", currentResult);
         currentResult.setValue("description", "<p>Determines the value of allowed HTTP headers for CORS requests.</p>  <p>The allowed headers value is a string that contains a comma separated list of HTTP header names. The default setting allows all headers.</p>  <p>When the value is empty or not specified, the response will not include the corresponding CORS HTTP header.</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CORSAllowedMethods")) {
         getterName = "getCORSAllowedMethods";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSAllowedMethods";
         }

         currentResult = new PropertyDescriptor("CORSAllowedMethods", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSAllowedMethods", currentResult);
         currentResult.setValue("description", "<p>Determines the value of allowed HTTP methods for CORS requests.</p>  <p>The allowed methods value is a string that contains a comma separated list of HTTP method names. The default setting allows all methods.</p>  <p>When the value is empty or not specified, the response will not include the corresponding CORS HTTP header.</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CORSAllowedOrigins")) {
         getterName = "getCORSAllowedOrigins";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSAllowedOrigins";
         }

         currentResult = new PropertyDescriptor("CORSAllowedOrigins", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSAllowedOrigins", currentResult);
         currentResult.setValue("description", "<p>Determines the list of allowed origins for CORS requests. When the list is empty or not specified and CORS support is enabled then all origins are accepted. When specified the HTTP Origin header must match exactly one of the values configured as allowed.</p>  <p>CORS origin values include protocol and domain name, they may also contain port numbers. Multiple variants of the allowed origin may need to be specified based on the origin used by the browser.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CORSExposedHeaders")) {
         getterName = "getCORSExposedHeaders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSExposedHeaders";
         }

         currentResult = new PropertyDescriptor("CORSExposedHeaders", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSExposedHeaders", currentResult);
         currentResult.setValue("description", "<p>Determines the value of exposed HTTP headers for CORS requests.</p>  <p>The exposed headers value is a string that contains a comma separated list of HTTP header names. The default setting does not specify any specific headers.</p>  <p>When the value is empty or not specified, the response will not include the corresponding CORS HTTP header.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CORSMaxAge")) {
         getterName = "getCORSMaxAge";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSMaxAge";
         }

         currentResult = new PropertyDescriptor("CORSMaxAge", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSMaxAge", currentResult);
         currentResult.setValue("description", "<p>Determines the number of seconds for a browser cache of CORS preflight requests.</p>  <p>The max age value is a string that contains a numeric value with the number of seconds used for the browser cache. The default setting does not specify any value.</p>  <p>When the value is empty or not specified, the response will not include the corresponding CORS HTTP header.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DelegatedRequestConnectTimeoutMillis")) {
         getterName = "getDelegatedRequestConnectTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelegatedRequestConnectTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("DelegatedRequestConnectTimeoutMillis", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("DelegatedRequestConnectTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>Determines the connection timeout, in milliseconds, when the Management Services Web application propagates an idempotent REST request from the admin server to a managed server.</p> <p> If DelegatedRequestConnectTimeouttMillis is zero, then the default underlying JAXRS client api connection timeout will be used.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getFannedOutRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestReadTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(30000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DelegatedRequestMaxWaitMillis")) {
         getterName = "getDelegatedRequestMaxWaitMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelegatedRequestMaxWaitMillis";
         }

         currentResult = new PropertyDescriptor("DelegatedRequestMaxWaitMillis", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("DelegatedRequestMaxWaitMillis", currentResult);
         currentResult.setValue("description", "<p>Determines how long the Management Services Web application will wait, in milliseconds, when it propagates an idempotent REST request from the admin server to a managed server.  If zero, then it will try to propagate the request once. If greater than zero, then it will retry the request until it finishes or until it detects that the time limit has been exceeded.</p>. <p>A request can override this value by specifying the requestMaxWaitMillis query parameter.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getFannedOutRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestConnectTimeoutMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestReadTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DelegatedRequestMinThreads")) {
         getterName = "getDelegatedRequestMinThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelegatedRequestMinThreads";
         }

         currentResult = new PropertyDescriptor("DelegatedRequestMinThreads", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("DelegatedRequestMinThreads", currentResult);
         currentResult.setValue("description", "<p>Specifies the minimum number of threads that should be dedicated to the Management Services Web application when interacting with managed servers in parallel for improved responsiveness in large domains.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(25));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DelegatedRequestReadTimeoutMillis")) {
         getterName = "getDelegatedRequestReadTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelegatedRequestReadTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("DelegatedRequestReadTimeoutMillis", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("DelegatedRequestReadTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>Determines the read timeout, in milliseconds, when the Management Services Web application propagates an idempotent REST request from the admin server to a managed server.</p> <p> If DelegatedRequestReadTimeoutMillis is zero, then the default underlying JAXRS client api read timeout will be used.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getFannedOutRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestConnectTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("FannedOutRequestMaxWaitMillis")) {
         getterName = "getFannedOutRequestMaxWaitMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFannedOutRequestMaxWaitMillis";
         }

         currentResult = new PropertyDescriptor("FannedOutRequestMaxWaitMillis", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("FannedOutRequestMaxWaitMillis", currentResult);
         currentResult.setValue("description", "<p>Determines how long the Management Services Web application will wait, in milliseconds, when it fans out an idempotent REST request from the admin server to all the managed servers.  If zero, then it will choose an appropriate timeout. </p>. <p>A request can override this value by specifying the requestMaxWaitMillis query parameter.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDelegatedRequestMaxWaitMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestConnectTimeoutMillis"), BeanInfoHelper.encodeEntities("#getDelegatedRequestReadTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(180000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("CORSAllowedCredentials")) {
         getterName = "isCORSAllowedCredentials";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSAllowedCredentials";
         }

         currentResult = new PropertyDescriptor("CORSAllowedCredentials", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSAllowedCredentials", currentResult);
         currentResult.setValue("description", "<p>Determines if credentials are allowed for CORS requests.</p>  <p>When the value is false, the response will not include the corresponding CORS HTTP header.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CORSEnabled")) {
         getterName = "isCORSEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCORSEnabled";
         }

         currentResult = new PropertyDescriptor("CORSEnabled", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("CORSEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the support of CORS (Cross-Origin Resource Sharing) processing in the RESTful Management Services Web application.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Enables the monitoring of this WebLogic Server domain through the RESTful Management Services Web application.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaServiceResourcesEnabled")) {
         getterName = "isJavaServiceResourcesEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaServiceResourcesEnabled";
         }

         currentResult = new PropertyDescriptor("JavaServiceResourcesEnabled", RestfulManagementServicesMBean.class, getterName, setterName);
         descriptors.put("JavaServiceResourcesEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the REST resource features for administering WebLogic Server as a Java Service within a cloud environment should be enabled in this domain.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
