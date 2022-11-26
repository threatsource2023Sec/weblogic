package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DeployableCredentialMapperMBeanImplBeanInfo extends CredentialMapperMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeployableCredentialMapperMBean.class;

   public DeployableCredentialMapperMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeployableCredentialMapperMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.DeployableCredentialMapperMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("deprecated", "12.2.1.0.0 ");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("The SSPI MBean that must be extended by all Credential Mapper providers that can store credential maps created while deploying a component. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.DeployableCredentialMapperMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("CredentialMappingDeploymentEnabled")) {
         String getterName = "isCredentialMappingDeploymentEnabled";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialMappingDeploymentEnabled";
         }

         currentResult = new PropertyDescriptor("CredentialMappingDeploymentEnabled", DeployableCredentialMapperMBean.class, getterName, setterName);
         descriptors.put("CredentialMappingDeploymentEnabled", currentResult);
         currentResult.setValue("description", "Returns whether this Credential Mapping provider stores stores credential maps created when deploying a resource adapter. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
