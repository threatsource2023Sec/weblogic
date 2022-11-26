package weblogic.management.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;

public class IdentityDomainAwareProviderMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = IdentityDomainAwareProviderMBean.class;

   public IdentityDomainAwareProviderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public IdentityDomainAwareProviderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.IdentityDomainAwareProviderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.security");
      String description = (new String("<p>The MBean interface must be implemented by any role mapping, authorization, credential mapping and audit providers MBean which should be aware of identity domain.</p>  <p>Certain WebLogic Security SPI (SSPI) providers must be identity-domain-aware to function correctly in a partitioned environment -- or, more precisely, in an environment where identity domains are configured into the system. For example, an authorization provider that doesn't understand identity domains cannot correctly distinguish between two users with the same name but different identity domains, and therefore can't make valid authorization decisions. When identity domains are in use, then all providers MBeans of the following types must be identity-domain-aware, and implement the interface:</p>  <ul> <li><code>Role Mapping</code></li> <li><code>Authorization</code></li> <li><code>Credential Mapping</code></li> <li><code>Audit</code></li> </ul>  <p>Along with implementing the marker interface, identity-domain-aware providers must handle user and group principals properly when they carry identity domain values, including accounting for identity domain when testing for equality, and constructing map keys that account for identity domain. Identity-domain-aware must also account for resource ownership properly.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.IdentityDomainAwareProviderMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
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
