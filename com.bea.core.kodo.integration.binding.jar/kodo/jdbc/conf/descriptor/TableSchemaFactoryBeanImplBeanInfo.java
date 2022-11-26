package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TableSchemaFactoryBeanImplBeanInfo extends SchemaFactoryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TableSchemaFactoryBean.class;

   public TableSchemaFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TableSchemaFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.TableSchemaFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.TableSchemaFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("PrimaryKeyColumn")) {
         getterName = "getPrimaryKeyColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryKeyColumn";
         }

         currentResult = new PropertyDescriptor("PrimaryKeyColumn", TableSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("PrimaryKeyColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "ID");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaColumn")) {
         getterName = "getSchemaColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaColumn";
         }

         currentResult = new PropertyDescriptor("SchemaColumn", TableSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("SchemaColumn", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Table")) {
         getterName = "getTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTable";
         }

         currentResult = new PropertyDescriptor("Table", TableSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("Table", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "OPENJPA_SCHEMA");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", TableSchemaFactoryBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
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
