package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class KodoPersistenceMappingFactoryBeanImplBeanInfo extends MappingFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoPersistenceMappingFactoryBean.class;

   public KodoPersistenceMappingFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoPersistenceMappingFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.KodoPersistenceMappingFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.KodoPersistenceMappingFactoryBean");
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

         currentResult = new PropertyDescriptor("ClasspathScan", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ClasspathScan", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultAccessType")) {
         getterName = "getDefaultAccessType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultAccessType";
         }

         currentResult = new PropertyDescriptor("DefaultAccessType", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("DefaultAccessType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FieldOverride")) {
         getterName = "getFieldOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFieldOverride";
         }

         currentResult = new PropertyDescriptor("FieldOverride", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("FieldOverride", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Files")) {
         getterName = "getFiles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFiles";
         }

         currentResult = new PropertyDescriptor("Files", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Resources", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMode")) {
         getterName = "getStoreMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMode";
         }

         currentResult = new PropertyDescriptor("StoreMode", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Strict", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Types", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Types", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "null");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("URLs")) {
         getterName = "getURLs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setURLs";
         }

         currentResult = new PropertyDescriptor("URLs", KodoPersistenceMappingFactoryBean.class, getterName, setterName);
         descriptors.put("URLs", currentResult);
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
