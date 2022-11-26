package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class AuthenticatorMBeanImplBeanInfo extends AuthenticationProviderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AuthenticatorMBean.class;

   public AuthenticatorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AuthenticatorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.AuthenticatorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("The SSPI MBean that all Authentication providers with login services must extend. This MBean provides a ControlFlag to determine whether the Authentication provider is a REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL part of the login sequence. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.AuthenticatorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("ControlFlag")) {
         String getterName = "getControlFlag";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setControlFlag";
         }

         currentResult = new PropertyDescriptor("ControlFlag", AuthenticatorMBean.class, getterName, setterName);
         descriptors.put("ControlFlag", currentResult);
         currentResult.setValue("description", "<p>Returns how the login sequence uses the Authentication provider.</p>  <p>A <code>REQUIRED</code> value specifies this LoginModule must succeed. Even if it fails, authentication proceeds down the list of LoginModules for the configured Authentication providers. This setting is the default.</p>  <p>A <code>REQUISITE</code> value specifies this LoginModule must succeed. If other Authentication providers are configured and this LoginModule succeeds, authentication proceeds down the list of LoginModules. Otherwise, control is return to the application.</p>  <p>A <code>SUFFICIENT</code> value specifies this LoginModule need not succeed. If it does succeed, return control to the application. If it fails and other Authentication providers are configured, authentication proceeds down the LoginModule list.</p>  <p>An <code>OPTIONAL</code> value specifies this LoginModule need not succeed. Whether it succeeds or fails, authentication proceeds down the LoginModule list.</p> ");
         setPropertyDescriptorDefault(currentResult, "REQUIRED");
         currentResult.setValue("legalValues", new Object[]{"REQUIRED", "REQUISITE", "SUFFICIENT", "OPTIONAL"});
         currentResult.setValue("dynamic", Boolean.FALSE);
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
