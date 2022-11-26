package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DeprecatedJDOMappingDefaultsBeanImplBeanInfo extends MappingDefaultsBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeprecatedJDOMappingDefaultsBean.class;

   public DeprecatedJDOMappingDefaultsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeprecatedJDOMappingDefaultsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.DeprecatedJDOMappingDefaultsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String("Deprecated JDO mapping defaults. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.DeprecatedJDOMappingDefaultsBean");
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

         currentResult = new PropertyDescriptor("AddNullIndicator", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("AddNullIndicator", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseClassStrategy")) {
         getterName = "getBaseClassStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseClassStrategy";
         }

         currentResult = new PropertyDescriptor("BaseClassStrategy", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("BaseClassStrategy", currentResult);
         currentResult.setValue("description", "The base class strategy to use. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataStoreIdColumnName")) {
         getterName = "getDataStoreIdColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataStoreIdColumnName";
         }

         currentResult = new PropertyDescriptor("DataStoreIdColumnName", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("DataStoreIdColumnName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "jdoId");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMissingInfo")) {
         getterName = "getDefaultMissingInfo";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMissingInfo";
         }

         currentResult = new PropertyDescriptor("DefaultMissingInfo", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("DefaultMissingInfo", currentResult);
         currentResult.setValue("description", "Whether to populate missing info. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeferConstraints")) {
         getterName = "getDeferConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeferConstraints";
         }

         currentResult = new PropertyDescriptor("DeferConstraints", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DiscriminatorColumnName", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("DiscriminatorColumnName", currentResult);
         currentResult.setValue("description", "Default column name for class discriminator columns. ");
         setPropertyDescriptorDefault(currentResult, "jdoClass");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DiscriminatorStrategy")) {
         getterName = "getDiscriminatorStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDiscriminatorStrategy";
         }

         currentResult = new PropertyDescriptor("DiscriminatorStrategy", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("FieldStrategies", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("ForeignKeyDeleteAction", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("IndexDiscriminator", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("IndexLogicalForeignKeys", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("IndexLogicalForeignKeys", currentResult);
         currentResult.setValue("description", "Whether index logical foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexVersion")) {
         getterName = "getIndexVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIndexVersion";
         }

         currentResult = new PropertyDescriptor("IndexVersion", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("IndexVersion", currentResult);
         currentResult.setValue("description", "Whether to index version column. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinForeignKeyDeleteAction")) {
         getterName = "getJoinForeignKeyDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinForeignKeyDeleteAction";
         }

         currentResult = new PropertyDescriptor("JoinForeignKeyDeleteAction", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("NullIndicatorColumnName", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("NullIndicatorColumnName", currentResult);
         currentResult.setValue("description", "The name to use to indicate null. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrderColumnName")) {
         getterName = "getOrderColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrderColumnName";
         }

         currentResult = new PropertyDescriptor("OrderColumnName", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("OrderLists", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("StoreEnumOrdinal", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("StoreUnmappedObjectIdString", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SubclassStrategy", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("SubclassStrategy", currentResult);
         currentResult.setValue("description", "The subclass strategy to use. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseClassCriteria")) {
         getterName = "getUseClassCriteria";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseClassCriteria";
         }

         currentResult = new PropertyDescriptor("UseClassCriteria", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("UseClassCriteria", currentResult);
         currentResult.setValue("description", "Whether to use class criteria ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionColumnName")) {
         getterName = "getVersionColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionColumnName";
         }

         currentResult = new PropertyDescriptor("VersionColumnName", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("VersionStrategy", DeprecatedJDOMappingDefaultsBean.class, getterName, setterName);
         descriptors.put("VersionStrategy", currentResult);
         currentResult.setValue("description", "The default version strategy. ");
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
