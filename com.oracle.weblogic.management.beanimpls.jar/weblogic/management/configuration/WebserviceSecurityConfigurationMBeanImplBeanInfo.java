package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebserviceSecurityConfigurationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebserviceSecurityConfigurationMBean.class;

   public WebserviceSecurityConfigurationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebserviceSecurityConfigurationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebserviceSecurityConfigurationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Encapsulates information about a Web Service security configuration.</p>  <p>This information includes the list of tokens, credential providers, token handlers, and the timestamp.  After you have created a Web Service security configuration, you can associate it to a Web Service.  </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebserviceSecurityConfigurationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassName";
         }

         currentResult = new PropertyDescriptor("ClassName", WebserviceSecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements a particular credential provider or token handler.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConfigurationProperties")) {
         getterName = "getConfigurationProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationProperties", WebserviceSecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ConfigurationProperties", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of properties that are associated with this credential provider or token handler.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConfigurationProperty");
         currentResult.setValue("destroyer", "destroyConfigurationProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("TokenType")) {
         getterName = "getTokenType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTokenType";
         }

         currentResult = new PropertyDescriptor("TokenType", WebserviceSecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("TokenType", currentResult);
         currentResult.setValue("description", "<p>Specifies the type of token used for the particular credential provider or token handler.</p>  <p>WebLogic Server supports, by default, three types of tokens: X.509, UsernameToken, and SAML, as specified by the following WS-Security specifications:</p> <ul> <li>Web Services Security: Username Token Profile</li> <li>Web Services Security: X.509 Token Profile</li> <li>Web Services Security: SAML Token Profile</li> </ul> <p>To specify one of these out-of-the-box types, use these values respectively: \"ut\", \"x509\", or \"saml\". </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityConfigurationMBean.class.getMethod("createConfigurationProperty", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of ConfigurationProperty ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "create ConfigurationProperty object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConfigurationProperties");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityConfigurationMBean.class.getMethod("destroyConfigurationProperty", ConfigurationPropertyMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "ConfigurationProperty mbean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy ConfigurationProperty object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConfigurationProperties");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         Method mth = WebserviceSecurityConfigurationMBean.class.getMethod("lookupConfigurationProperty", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of ConfigurationProperty ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "look up ConfigurationProperty object ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ConfigurationProperties");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

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
