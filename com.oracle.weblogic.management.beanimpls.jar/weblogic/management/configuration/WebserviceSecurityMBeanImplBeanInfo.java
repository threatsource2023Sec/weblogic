package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebserviceSecurityMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebserviceSecurityMBean.class;

   public WebserviceSecurityMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebserviceSecurityMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebserviceSecurityMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Encapsulates information about a Web Service security configuration.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebserviceSecurityMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompatibilityOrderingPreference")) {
         getterName = "getCompatibilityOrderingPreference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompatibilityOrderingPreference";
         }

         currentResult = new PropertyDescriptor("CompatibilityOrderingPreference", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("CompatibilityOrderingPreference", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the compatiblityOrderingPreference attribute.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompatibilityPreference")) {
         getterName = "getCompatibilityPreference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompatibilityPreference";
         }

         currentResult = new PropertyDescriptor("CompatibilityPreference", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("CompatibilityPreference", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the compatiblityPreference attribute.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DefaultCredentialProviderSTSURI")) {
         getterName = "getDefaultCredentialProviderSTSURI";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultCredentialProviderSTSURI";
         }

         currentResult = new PropertyDescriptor("DefaultCredentialProviderSTSURI", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("DefaultCredentialProviderSTSURI", currentResult);
         currentResult.setValue("description", "<p>Gets the default STS endpoint URL for all WS-Trust enabled credential providers of this Web Service security configuration.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (!descriptors.containsKey("PolicySelectionPreference")) {
         getterName = "getPolicySelectionPreference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPolicySelectionPreference";
         }

         currentResult = new PropertyDescriptor("PolicySelectionPreference", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("PolicySelectionPreference", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the PolicySelectionPreference attribute. </p>  <p>The preference value can be one of the following:</p> <ul> <li>NONE</li> <li>SCP</li> <li>SPC</li> <li>CSP</li> <li>CPS</li> <li>PCS</li> <li>PSC</li> </ul>  <p>Where: S: Security or functionality; C: Compatibility or Interoperability; P: Performance</p>  <p>If NONE is specified, no preference is applied - the first policy alternative is always chosen, and optional policy assertions are ignored.</p> ");
         setPropertyDescriptorDefault(currentResult, "NONE");
         currentResult.setValue("legalValues", new Object[]{"NONE", "SCP", "SPC", "CSP", "CPS", "PCS", "PSC"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceCredentialProviders")) {
         getterName = "getWebserviceCredentialProviders";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceCredentialProviders", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("WebserviceCredentialProviders", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of credential providers that have been configured for this Web Service security configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWebserviceCredentialProvider");
         currentResult.setValue("destroyer", "destroyWebserviceCredentialProvider");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceSecurityTokens")) {
         getterName = "getWebserviceSecurityTokens";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceSecurityTokens", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("WebserviceSecurityTokens", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of tokens that have been configured for this Web Service security configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWebserviceSecurityToken");
         currentResult.setValue("destroyer", "destroyWebserviceSecurityToken");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceTimestamp")) {
         getterName = "getWebserviceTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceTimestamp", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("WebserviceTimestamp", currentResult);
         currentResult.setValue("description", "<p>Specifies the timestamp information that has been configured for this Web Service security configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceTokenHandlers")) {
         getterName = "getWebserviceTokenHandlers";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceTokenHandlers", WebserviceSecurityMBean.class, getterName, setterName);
         descriptors.put("WebserviceTokenHandlers", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of token handlers that have been configured for this Web Service security configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWebserviceTokenHandler");
         currentResult.setValue("creator", "createWebserviceTokenHandler");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("createWebserviceTokenHandler", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WebserviceTokenHandler ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "create WebserviceTokenHandler object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceTokenHandlers");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("destroyWebserviceTokenHandler", WebserviceTokenHandlerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsth", "WebserviceTokenHandler mbean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy WebserviceTokenHandler object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceTokenHandlers");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("createWebserviceCredentialProvider", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WebserviceCredentialProvider ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "create WebserviceCredentialProvider object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceCredentialProviders");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("destroyWebserviceCredentialProvider", WebserviceCredentialProviderMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wcp", "WebserviceCredentialProvider mbean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy WebserviceCredentialProvider object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceCredentialProviders");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("createWebserviceSecurityToken", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WebserviceSecurityToken ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "create WebserviceSecurityToken object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceSecurityTokens");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("destroyWebserviceSecurityToken", WebserviceSecurityTokenMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wcp", "WebserviceSecurityToken mbean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy WebserviceSecurityToken object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceSecurityTokens");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("lookupWebserviceTokenHandler", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of WebserviceSecurity ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "look up WebserviceSecurity object ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WebserviceTokenHandlers");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("lookupWebserviceCredentialProvider", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of WebserviceSecurity ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "look up WebserviceSecurity object ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WebserviceCredentialProviders");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = WebserviceSecurityMBean.class.getMethod("lookupWebserviceSecurityToken", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of WebserviceSecurityToken ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "look up WebserviceSecurityToken object ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WebserviceSecurityTokens");
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
