package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.MetaDataRepositoryBeanImplBeanInfo;

public class KodoMappingRepositoryBeanImplBeanInfo extends MetaDataRepositoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoMappingRepositoryBean.class;

   public KodoMappingRepositoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoMappingRepositoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.KodoMappingRepositoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.KodoMappingRepositoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Resolve")) {
         getterName = "getResolve";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResolve";
         }

         currentResult = new PropertyDescriptor("Resolve", KodoMappingRepositoryBean.class, getterName, setterName);
         descriptors.put("Resolve", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceMode")) {
         getterName = "getSourceMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceMode";
         }

         currentResult = new PropertyDescriptor("SourceMode", KodoMappingRepositoryBean.class, getterName, setterName);
         descriptors.put("SourceMode", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Validate")) {
         getterName = "getValidate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidate";
         }

         currentResult = new PropertyDescriptor("Validate", KodoMappingRepositoryBean.class, getterName, setterName);
         descriptors.put("Validate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
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
