package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class PointbaseDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PointbaseDictionaryBean.class;

   public PointbaseDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PointbaseDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.PointbaseDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutoAssignTypeName")) {
         getterName = "getAutoAssignTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignTypeName";
         }

         currentResult = new PropertyDescriptor("AutoAssignTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BIGINT IDENTITY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BIGINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TINYINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BLOB(1M)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CharTypeName")) {
         getterName = "getCharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCharTypeName";
         }

         currentResult = new PropertyDescriptor("CharTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "CHARACTER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "CLOB(1M)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE PRECISION");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FloatTypeName")) {
         getterName = "getFloatTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFloatTypeName";
         }

         currentResult = new PropertyDescriptor("FloatTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("FloatTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FLOAT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT MAX({0}) FROM {1}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BLOB(1M)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Pointbase Embedded");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RealTypeName")) {
         getterName = "getRealTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealTypeName";
         }

         currentResult = new PropertyDescriptor("RealTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("RealTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "REAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAliasForSubselect")) {
         getterName = "getRequiresAliasForSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAliasForSubselect";
         }

         currentResult = new PropertyDescriptor("RequiresAliasForSubselect", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAliasForSubselect", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAutoAssign", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDeferredConstraints")) {
         getterName = "getSupportsDeferredConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDeferredConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithDistinctClause")) {
         getterName = "getSupportsLockingWithDistinctClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithDistinctClause";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithDistinctClause", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithDistinctClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithMultipleTables")) {
         getterName = "getSupportsLockingWithMultipleTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithMultipleTables";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithMultipleTables", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithMultipleTables", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsMultipleNontransactionalResultSets")) {
         getterName = "getSupportsMultipleNontransactionalResultSets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsMultipleNontransactionalResultSets";
         }

         currentResult = new PropertyDescriptor("SupportsMultipleNontransactionalResultSets", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsMultipleNontransactionalResultSets", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", PointbaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TINYINT");
         currentResult.setValue("configurable", Boolean.TRUE);
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
