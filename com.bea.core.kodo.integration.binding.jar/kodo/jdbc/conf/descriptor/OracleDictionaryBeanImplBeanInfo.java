package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class OracleDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = OracleDictionaryBean.class;

   public OracleDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OracleDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.OracleDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.OracleDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutoAssignSequenceName")) {
         getterName = "getAutoAssignSequenceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignSequenceName";
         }

         currentResult = new PropertyDescriptor("AutoAssignSequenceName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignSequenceName", currentResult);
         currentResult.setValue("description", "The global sequence name to use for auto-assign simulation. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BLOB");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DecimalTypeName")) {
         getterName = "getDecimalTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDecimalTypeName";
         }

         currentResult = new PropertyDescriptor("DecimalTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("DecimalTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinSyntax")) {
         getterName = "getJoinSyntax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinSyntax";
         }

         currentResult = new PropertyDescriptor("JoinSyntax", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("JoinSyntax", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "database");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BLOB");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONG");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", OracleDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEmbeddedBlobSize")) {
         getterName = "getMaxEmbeddedBlobSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEmbeddedBlobSize";
         }

         currentResult = new PropertyDescriptor("MaxEmbeddedBlobSize", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxEmbeddedBlobSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(4000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEmbeddedClobSize")) {
         getterName = "getMaxEmbeddedClobSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEmbeddedClobSize";
         }

         currentResult = new PropertyDescriptor("MaxEmbeddedClobSize", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxEmbeddedClobSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(4000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NextSequenceQuery")) {
         getterName = "getNextSequenceQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNextSequenceQuery";
         }

         currentResult = new PropertyDescriptor("NextSequenceQuery", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("NextSequenceQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT {0}.NEXTVAL FROM DUAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumericTypeName")) {
         getterName = "getNumericTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumericTypeName";
         }

         currentResult = new PropertyDescriptor("NumericTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("NumericTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Openjpa3GeneratedKeyNames")) {
         getterName = "getOpenjpa3GeneratedKeyNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOpenjpa3GeneratedKeyNames";
         }

         currentResult = new PropertyDescriptor("Openjpa3GeneratedKeyNames", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("Openjpa3GeneratedKeyNames", currentResult);
         currentResult.setValue("description", "When true, use Kodo 3.x style naming for auto assign sequence name and trigger name for backwards compatibility. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Oracle");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StringLengthFunction")) {
         getterName = "getStringLengthFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStringLengthFunction";
         }

         currentResult = new PropertyDescriptor("StringLengthFunction", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("StringLengthFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LENGTH({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDeferredConstraints")) {
         getterName = "getSupportsDeferredConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDeferredConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithDistinctClause")) {
         getterName = "getSupportsLockingWithDistinctClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithDistinctClause";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithDistinctClause", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithDistinctClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectEndIndex")) {
         getterName = "getSupportsSelectEndIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectEndIndex";
         }

         currentResult = new PropertyDescriptor("SupportsSelectEndIndex", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectEndIndex", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectStartIndex")) {
         getterName = "getSupportsSelectStartIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectStartIndex";
         }

         currentResult = new PropertyDescriptor("SupportsSelectStartIndex", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectStartIndex", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeTypeName")) {
         getterName = "getTimeTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeTypeName";
         }

         currentResult = new PropertyDescriptor("TimeTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("TimeTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DATE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMBER{0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetFormOfUseForUnicode")) {
         getterName = "getUseSetFormOfUseForUnicode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetFormOfUseForUnicode";
         }

         currentResult = new PropertyDescriptor("UseSetFormOfUseForUnicode", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetFormOfUseForUnicode", currentResult);
         currentResult.setValue("description", "When true, the dictionary will attempt to use the special OraclePreparedStatement.setFormOfUse method to configure statements that it detects are operating on unicode fields. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseTriggersForAutoAssign")) {
         getterName = "getUseTriggersForAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseTriggersForAutoAssign";
         }

         currentResult = new PropertyDescriptor("UseTriggersForAutoAssign", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("UseTriggersForAutoAssign", currentResult);
         currentResult.setValue("description", "When true, the dictionary uses a trigger to simulate auto-assigned columns.  The trigger uses a sequence. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationSQL")) {
         getterName = "getValidationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationSQL";
         }

         currentResult = new PropertyDescriptor("ValidationSQL", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT SYSDATE FROM DUAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarcharTypeName")) {
         getterName = "getVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("VarcharTypeName", OracleDictionaryBean.class, getterName, setterName);
         descriptors.put("VarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "VARCHAR2{0}");
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
