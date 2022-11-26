package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFRESTNotificationBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFRESTNotificationBean.class;

   public WLDFRESTNotificationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFRESTNotificationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFRESTNotificationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Use this interface to define a REST action, which is sent when a diagnostic policy evaluates to <code>true</code>.</p> <p>Note: As of WebLogic Server 12.2.1, the terms <i>watch</i> and <i>notification</i> are replaced by <i>policy</i> and <i>action</i>, respectively. However, the definition of these terms has not changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFRESTNotificationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AcceptedResponseType")) {
         getterName = "getAcceptedResponseType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcceptedResponseType";
         }

         currentResult = new PropertyDescriptor("AcceptedResponseType", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("AcceptedResponseType", currentResult);
         currentResult.setValue("description", "<p>Configures the Accept header of the HTTP request to the REST end point. The response entity of the REST invocation is ignored by the server.</p> ");
         setPropertyDescriptorDefault(currentResult, "application/json");
         currentResult.setValue("legalValues", new Object[]{"application/json", "text/plain", "text/xml", "text/html"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomNotificationProperties")) {
         getterName = "getCustomNotificationProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomNotificationProperties";
         }

         currentResult = new PropertyDescriptor("CustomNotificationProperties", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("CustomNotificationProperties", currentResult);
         currentResult.setValue("description", "<p>Allows customizing the JSON object delivered to the REST end point.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EndpointURL")) {
         getterName = "getEndpointURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEndpointURL";
         }

         currentResult = new PropertyDescriptor("EndpointURL", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("EndpointURL", currentResult);
         currentResult.setValue("description", "<p>The REST end point URL which will be invoked with the action payload.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpAuthenticationMode")) {
         getterName = "getHttpAuthenticationMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpAuthenticationMode";
         }

         currentResult = new PropertyDescriptor("HttpAuthenticationMode", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("HttpAuthenticationMode", currentResult);
         currentResult.setValue("description", "<p>The HTTP authentication mode when invoking the REST end point URL.</p> ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalValues", new Object[]{"None", "Basic"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpAuthenticationPassword")) {
         getterName = "getHttpAuthenticationPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpAuthenticationPassword";
         }

         currentResult = new PropertyDescriptor("HttpAuthenticationPassword", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("HttpAuthenticationPassword", currentResult);
         currentResult.setValue("description", "<p>The HTTP authentication password when the REST end point is secured.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpAuthenticationPasswordEncrypted")) {
         getterName = "getHttpAuthenticationPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpAuthenticationPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("HttpAuthenticationPasswordEncrypted", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("HttpAuthenticationPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted HTTP authentication password when the REST end point is secured.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpAuthenticationUserName")) {
         getterName = "getHttpAuthenticationUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpAuthenticationUserName";
         }

         currentResult = new PropertyDescriptor("HttpAuthenticationUserName", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("HttpAuthenticationUserName", currentResult);
         currentResult.setValue("description", "<p>The HTTP authentication user name when the REST end point is secured.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestInvocationMethodType")) {
         getterName = "getRestInvocationMethodType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestInvocationMethodType";
         }

         currentResult = new PropertyDescriptor("RestInvocationMethodType", WLDFRESTNotificationBean.class, getterName, setterName);
         descriptors.put("RestInvocationMethodType", currentResult);
         currentResult.setValue("description", "<p>The REST method for invoking the end point.</p> ");
         setPropertyDescriptorDefault(currentResult, "PUT");
         currentResult.setValue("legalValues", new Object[]{"PUT", "POST"});
         currentResult.setValue("dynamic", Boolean.TRUE);
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
