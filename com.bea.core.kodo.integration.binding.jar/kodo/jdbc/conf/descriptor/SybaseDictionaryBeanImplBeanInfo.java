package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SybaseDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SybaseDictionaryBean.class;

   public SybaseDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SybaseDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.SybaseDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.SybaseDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllowsAliasInBulkClause")) {
         getterName = "getAllowsAliasInBulkClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowsAliasInBulkClause";
         }

         currentResult = new PropertyDescriptor("AllowsAliasInBulkClause", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("AllowsAliasInBulkClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoAssignClause")) {
         getterName = "getAutoAssignClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignClause";
         }

         currentResult = new PropertyDescriptor("AutoAssignClause", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "IDENTITY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NUMERIC(38)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", SybaseDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("BlobTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "IMAGE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TEXT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConcatenateFunction")) {
         getterName = "getConcatenateFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcatenateFunction";
         }

         currentResult = new PropertyDescriptor("ConcatenateFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("ConcatenateFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "({0}+{1})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreateIdentityColumn")) {
         getterName = "getCreateIdentityColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreateIdentityColumn";
         }

         currentResult = new PropertyDescriptor("CreateIdentityColumn", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CreateIdentityColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossJoinClause")) {
         getterName = "getCrossJoinClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossJoinClause";
         }

         currentResult = new PropertyDescriptor("CrossJoinClause", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CrossJoinClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "JOIN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentDateFunction")) {
         getterName = "getCurrentDateFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentDateFunction";
         }

         currentResult = new PropertyDescriptor("CurrentDateFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentDateFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GETDATE()");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentTimeFunction")) {
         getterName = "getCurrentTimeFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentTimeFunction";
         }

         currentResult = new PropertyDescriptor("CurrentTimeFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentTimeFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GETDATE()");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentTimestampFunction")) {
         getterName = "getCurrentTimestampFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentTimestampFunction";
         }

         currentResult = new PropertyDescriptor("CurrentTimestampFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentTimestampFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "GETDATE()");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DateTypeName")) {
         getterName = "getDateTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDateTypeName";
         }

         currentResult = new PropertyDescriptor("DateTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("DateTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DATETIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FLOAT(32)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FloatTypeName")) {
         getterName = "getFloatTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFloatTypeName";
         }

         currentResult = new PropertyDescriptor("FloatTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("FloatTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FLOAT(16)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForUpdateClause")) {
         getterName = "getForUpdateClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForUpdateClause";
         }

         currentResult = new PropertyDescriptor("ForUpdateClause", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("ForUpdateClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FOR UPDATE AT ISOLATION SERIALIZABLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityColumnName")) {
         getterName = "getIdentityColumnName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityColumnName";
         }

         currentResult = new PropertyDescriptor("IdentityColumnName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("IdentityColumnName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "UNQ_INDEX");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT @@IDENTITY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "IMAGE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TEXT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", SybaseDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", SybaseDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MaxTableNameLength", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Sybase");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RangePosition")) {
         getterName = "getRangePosition";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRangePosition";
         }

         currentResult = new PropertyDescriptor("RangePosition", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("RangePosition", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAutoCommitForMetaData")) {
         getterName = "getRequiresAutoCommitForMetaData";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAutoCommitForMetaData";
         }

         currentResult = new PropertyDescriptor("RequiresAutoCommitForMetaData", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAutoCommitForMetaData", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresConditionForCrossJoin")) {
         getterName = "getRequiresConditionForCrossJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresConditionForCrossJoin";
         }

         currentResult = new PropertyDescriptor("RequiresConditionForCrossJoin", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresConditionForCrossJoin", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaCase")) {
         getterName = "getSchemaCase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaCase";
         }

         currentResult = new PropertyDescriptor("SchemaCase", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SchemaCase", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "preserve");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", SybaseDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", SybaseDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsLockingWithDistinctClause", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithDistinctClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsModOperator")) {
         getterName = "getSupportsModOperator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsModOperator";
         }

         currentResult = new PropertyDescriptor("SupportsModOperator", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsModOperator", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetColumns")) {
         getterName = "getSupportsNullTableForGetColumns";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetColumns";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetColumns", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetColumns", currentResult);
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

         currentResult = new PropertyDescriptor("SupportsSelectEndIndex", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectEndIndex", currentResult);
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

         currentResult = new PropertyDescriptor("TimeTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TimeTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DATETIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimestampTypeName")) {
         getterName = "getTimestampTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimestampTypeName";
         }

         currentResult = new PropertyDescriptor("TimestampTypeName", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TimestampTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DATETIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimBothFunction")) {
         getterName = "getTrimBothFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimBothFunction";
         }

         currentResult = new PropertyDescriptor("TrimBothFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimBothFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LTRIM(RTRIM({0}))");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimLeadingFunction")) {
         getterName = "getTrimLeadingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimLeadingFunction";
         }

         currentResult = new PropertyDescriptor("TrimLeadingFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimLeadingFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LTRIM({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimTrailingFunction")) {
         getterName = "getTrimTrailingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimTrailingFunction";
         }

         currentResult = new PropertyDescriptor("TrimTrailingFunction", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimTrailingFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "RTRIM({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBytesForBlobs")) {
         getterName = "getUseGetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetBytesForBlobs", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBytesForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetStringForClobs")) {
         getterName = "getUseGetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseGetStringForClobs", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetStringForClobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetBytesForBlobs")) {
         getterName = "getUseSetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseSetBytesForBlobs", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetBytesForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetStringForClobs")) {
         getterName = "getUseSetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseSetStringForClobs", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetStringForClobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationSQL")) {
         getterName = "getValidationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationSQL";
         }

         currentResult = new PropertyDescriptor("ValidationSQL", SybaseDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT GETDATE()");
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
