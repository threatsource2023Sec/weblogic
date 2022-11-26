package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MappingFileDeprecatedJDOMappingFactoryBeanImplBeanInfo extends MappingFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MappingFileDeprecatedJDOMappingFactoryBean.class;

   public MappingFileDeprecatedJDOMappingFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MappingFileDeprecatedJDOMappingFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClasspathScan")) {
         getterName = "getClasspathScan";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClasspathScan";
         }

         currentResult = new PropertyDescriptor("ClasspathScan", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ClasspathScan", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileName")) {
         getterName = "getFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileName";
         }

         currentResult = new PropertyDescriptor("FileName", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("FileName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "package.mapping");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Files")) {
         getterName = "getFiles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFiles";
         }

         currentResult = new PropertyDescriptor("Files", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Files", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resources")) {
         getterName = "getResources";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResources";
         }

         currentResult = new PropertyDescriptor("Resources", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ScanTopDown")) {
         getterName = "getScanTopDown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setScanTopDown";
         }

         currentResult = new PropertyDescriptor("ScanTopDown", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ScanTopDown", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleFile")) {
         getterName = "getSingleFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingleFile";
         }

         currentResult = new PropertyDescriptor("SingleFile", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("SingleFile", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMode")) {
         getterName = "getStoreMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMode";
         }

         currentResult = new PropertyDescriptor("StoreMode", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("StoreMode", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Strict")) {
         getterName = "getStrict";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStrict";
         }

         currentResult = new PropertyDescriptor("Strict", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Strict", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Types")) {
         getterName = "getTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTypes";
         }

         currentResult = new PropertyDescriptor("Types", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Types", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("URLs")) {
         getterName = "getURLs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setURLs";
         }

         currentResult = new PropertyDescriptor("URLs", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("URLs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSchemaValidation")) {
         getterName = "getUseSchemaValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSchemaValidation";
         }

         currentResult = new PropertyDescriptor("UseSchemaValidation", MappingFileDeprecatedJDOMappingFactoryBean.class, getterName, setterName);
         descriptors.put("UseSchemaValidation", currentResult);
         currentResult.setValue("description", " ");
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
