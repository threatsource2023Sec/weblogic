package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.security.IdentityDomainAwareProviderMBeanImplBeanInfo;

public class IdentityDomainAuthenticatorMBeanImplBeanInfo extends IdentityDomainAwareProviderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = IdentityDomainAuthenticatorMBean.class;

   public IdentityDomainAuthenticatorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public IdentityDomainAuthenticatorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.IdentityDomainAuthenticatorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Defines methods used to get/set the identity domain attribute used to determine the identity domain that an authenticator or identity asserter handles. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("IdentityDomain")) {
         String getterName = "getIdentityDomain";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityDomain";
         }

         currentResult = new PropertyDescriptor("IdentityDomain", IdentityDomainAuthenticatorMBean.class, getterName, setterName);
         descriptors.put("IdentityDomain", currentResult);
         currentResult.setValue("description", "Returns the name of the identity domain. ");
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
