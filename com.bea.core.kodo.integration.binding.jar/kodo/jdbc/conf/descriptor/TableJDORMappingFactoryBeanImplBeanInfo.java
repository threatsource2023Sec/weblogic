package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TableJDORMappingFactoryBeanImplBeanInfo extends MappingFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TableJDORMappingFactoryBean.class;

   public TableJDORMappingFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TableJDORMappingFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConstraintNames")) {
         getterName = "getConstraintNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConstraintNames";
         }

         currentResult = new PropertyDescriptor("ConstraintNames", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("ConstraintNames", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappingColumn")) {
         getterName = "getMappingColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMappingColumn";
         }

         currentResult = new PropertyDescriptor("MappingColumn", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("MappingColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "MAPPING_DEF");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NameColumn")) {
         getterName = "getNameColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNameColumn";
         }

         currentResult = new PropertyDescriptor("NameColumn", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("NameColumn", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMode")) {
         getterName = "getStoreMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMode";
         }

         currentResult = new PropertyDescriptor("StoreMode", TableJDORMappingFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Strict", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Strict", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Table")) {
         getterName = "getTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTable";
         }

         currentResult = new PropertyDescriptor("Table", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Table", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "KODO_JDO_MAPPINGS");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TypeColumn")) {
         getterName = "getTypeColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTypeColumn";
         }

         currentResult = new PropertyDescriptor("TypeColumn", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("TypeColumn", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Types")) {
         getterName = "getTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTypes";
         }

         currentResult = new PropertyDescriptor("Types", TableJDORMappingFactoryBean.class, getterName, setterName);
         descriptors.put("Types", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSchemaValidation")) {
         getterName = "getUseSchemaValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSchemaValidation";
         }

         currentResult = new PropertyDescriptor("UseSchemaValidation", TableJDORMappingFactoryBean.class, getterName, setterName);
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
