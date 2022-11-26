package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.MetaDataFactoryBeanImplBeanInfo;

public class JDORMappingFactoryBeanImplBeanInfo extends MetaDataFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDORMappingFactoryBean.class;

   public JDORMappingFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDORMappingFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.JDORMappingFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.JDORMappingFactoryBean");
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

         currentResult = new PropertyDescriptor("ClasspathScan", JDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ClasspathScan", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConstraintNames")) {
         getterName = "getConstraintNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConstraintNames";
         }

         currentResult = new PropertyDescriptor("ConstraintNames", JDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ConstraintNames", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Files")) {
         getterName = "getFiles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFiles";
         }

         currentResult = new PropertyDescriptor("Files", JDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Resources", JDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("ScanTopDown", JDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ScanTopDown", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMode")) {
         getterName = "getStoreMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMode";
         }

         currentResult = new PropertyDescriptor("StoreMode", JDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Strict", JDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Strict", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Types")) {
         getterName = "getTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTypes";
         }

         currentResult = new PropertyDescriptor("Types", JDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("URLs", JDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("UseSchemaValidation", JDORMappingFactoryBean.class, getterName, setterName);
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
