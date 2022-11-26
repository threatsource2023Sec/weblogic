package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class FoxProDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = FoxProDictionaryBean.class;

   public FoxProDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FoxProDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.FoxProDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.FoxProDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GENERAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMERIC(1)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GENERAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CharacterColumnSize")) {
         getterName = "getCharacterColumnSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCharacterColumnSize";
         }

         currentResult = new PropertyDescriptor("CharacterColumnSize", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("CharacterColumnSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(240));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "MEMO");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DateTypeName")) {
         getterName = "getDateTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDateTypeName";
         }

         currentResult = new PropertyDescriptor("DateTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("DateTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TIMESTAMP");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DecimalTypeName")) {
         getterName = "getDecimalTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDecimalTypeName";
         }

         currentResult = new PropertyDescriptor("DecimalTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("DecimalTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FloatTypeName")) {
         getterName = "getFloatTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFloatTypeName";
         }

         currentResult = new PropertyDescriptor("FloatTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("FloatTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMERIC(19,16)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinSyntax")) {
         getterName = "getJoinSyntax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinSyntax";
         }

         currentResult = new PropertyDescriptor("JoinSyntax", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("JoinSyntax", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "traditional");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GENERAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "MEMO");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxColumnNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConstraintNameLength")) {
         getterName = "getMaxConstraintNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConstraintNameLength";
         }

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(8));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(8));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumericTypeName")) {
         getterName = "getNumericTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumericTypeName";
         }

         currentResult = new PropertyDescriptor("NumericTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("NumericTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Visual FoxPro");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RealTypeName")) {
         getterName = "getRealTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealTypeName";
         }

         currentResult = new PropertyDescriptor("RealTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("RealTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDeferredConstraints")) {
         getterName = "getSupportsDeferredConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDeferredConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsForeignKeys")) {
         getterName = "getSupportsForeignKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsForeignKeys";
         }

         currentResult = new PropertyDescriptor("SupportsForeignKeys", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsForeignKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeTypeName")) {
         getterName = "getTimeTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeTypeName";
         }

         currentResult = new PropertyDescriptor("TimeTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("TimeTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TIMESTAMP");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarcharTypeName")) {
         getterName = "getVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("VarcharTypeName", FoxProDictionaryBean.class, getterName, setterName);
         descriptors.put("VarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "CHARACTER{0}");
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
