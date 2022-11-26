package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MappingDefaultsImplBeanImplBeanInfo extends MappingDefaultsBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MappingDefaultsImplBean.class;

   public MappingDefaultsImplBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MappingDefaultsImplBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.MappingDefaultsImplBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.MappingDefaultsImplBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AddNullIndicator")) {
         getterName = "getAddNullIndicator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAddNullIndicator";
         }

         currentResult = new PropertyDescriptor("AddNullIndicator", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("AddNullIndicator", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseClassStrategy")) {
         getterName = "getBaseClassStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseClassStrategy";
         }

         currentResult = new PropertyDescriptor("BaseClassStrategy", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("BaseClassStrategy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataStoreIdColumnName")) {
         getterName = "getDataStoreIdColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataStoreIdColumnName";
         }

         currentResult = new PropertyDescriptor("DataStoreIdColumnName", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("DataStoreIdColumnName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMissingInfo")) {
         getterName = "getDefaultMissingInfo";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMissingInfo";
         }

         currentResult = new PropertyDescriptor("DefaultMissingInfo", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("DefaultMissingInfo", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeferConstraints")) {
         getterName = "getDeferConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeferConstraints";
         }

         currentResult = new PropertyDescriptor("DeferConstraints", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("DeferConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DiscriminatorColumnName")) {
         getterName = "getDiscriminatorColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDiscriminatorColumnName";
         }

         currentResult = new PropertyDescriptor("DiscriminatorColumnName", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("DiscriminatorColumnName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DiscriminatorStrategy")) {
         getterName = "getDiscriminatorStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDiscriminatorStrategy";
         }

         currentResult = new PropertyDescriptor("DiscriminatorStrategy", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("DiscriminatorStrategy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FieldStrategies")) {
         getterName = "getFieldStrategies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFieldStrategies";
         }

         currentResult = new PropertyDescriptor("FieldStrategies", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("FieldStrategies", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForeignKeyDeleteAction")) {
         getterName = "getForeignKeyDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForeignKeyDeleteAction";
         }

         currentResult = new PropertyDescriptor("ForeignKeyDeleteAction", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("ForeignKeyDeleteAction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexDiscriminator")) {
         getterName = "getIndexDiscriminator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexDiscriminator";
         }

         currentResult = new PropertyDescriptor("IndexDiscriminator", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("IndexDiscriminator", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexLogicalForeignKeys")) {
         getterName = "getIndexLogicalForeignKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexLogicalForeignKeys";
         }

         currentResult = new PropertyDescriptor("IndexLogicalForeignKeys", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("IndexLogicalForeignKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexVersion")) {
         getterName = "getIndexVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexVersion";
         }

         currentResult = new PropertyDescriptor("IndexVersion", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("IndexVersion", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinForeignKeyDeleteAction")) {
         getterName = "getJoinForeignKeyDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinForeignKeyDeleteAction";
         }

         currentResult = new PropertyDescriptor("JoinForeignKeyDeleteAction", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("JoinForeignKeyDeleteAction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "1");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NullIndicatorColumnName")) {
         getterName = "getNullIndicatorColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNullIndicatorColumnName";
         }

         currentResult = new PropertyDescriptor("NullIndicatorColumnName", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("NullIndicatorColumnName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrderColumnName")) {
         getterName = "getOrderColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrderColumnName";
         }

         currentResult = new PropertyDescriptor("OrderColumnName", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("OrderColumnName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrderLists")) {
         getterName = "getOrderLists";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrderLists";
         }

         currentResult = new PropertyDescriptor("OrderLists", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("OrderLists", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreEnumOrdinal")) {
         getterName = "getStoreEnumOrdinal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreEnumOrdinal";
         }

         currentResult = new PropertyDescriptor("StoreEnumOrdinal", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("StoreEnumOrdinal", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreUnmappedObjectIdString")) {
         getterName = "getStoreUnmappedObjectIdString";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreUnmappedObjectIdString";
         }

         currentResult = new PropertyDescriptor("StoreUnmappedObjectIdString", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("StoreUnmappedObjectIdString", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubclassStrategy")) {
         getterName = "getSubclassStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubclassStrategy";
         }

         currentResult = new PropertyDescriptor("SubclassStrategy", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("SubclassStrategy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseClassCriteria")) {
         getterName = "getUseClassCriteria";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseClassCriteria";
         }

         currentResult = new PropertyDescriptor("UseClassCriteria", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("UseClassCriteria", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionColumnName")) {
         getterName = "getVersionColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionColumnName";
         }

         currentResult = new PropertyDescriptor("VersionColumnName", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("VersionColumnName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionStrategy")) {
         getterName = "getVersionStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionStrategy";
         }

         currentResult = new PropertyDescriptor("VersionStrategy", MappingDefaultsImplBean.class, getterName, setterName);
         descriptors.put("VersionStrategy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
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
