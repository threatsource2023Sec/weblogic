package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class FileSchemaFactoryBeanImplBeanInfo extends SchemaFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = FileSchemaFactoryBean.class;

   public FileSchemaFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FileSchemaFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.FileSchemaFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.FileSchemaFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("File")) {
         getterName = "getFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFile";
         }

         currentResult = new PropertyDescriptor("File", FileSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("File", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "package.schema");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileName")) {
         getterName = "getFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileName";
         }

         currentResult = new PropertyDescriptor("FileName", FileSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("FileName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "package.schema");
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
