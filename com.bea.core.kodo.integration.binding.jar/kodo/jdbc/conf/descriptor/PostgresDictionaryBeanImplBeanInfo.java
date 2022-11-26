package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class PostgresDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PostgresDictionaryBean.class;

   public PostgresDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PostgresDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.PostgresDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.PostgresDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllSequencesFromOneSchemaSQL")) {
         getterName = "getAllSequencesFromOneSchemaSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllSequencesFromOneSchemaSQL";
         }

         currentResult = new PropertyDescriptor("AllSequencesFromOneSchemaSQL", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("AllSequencesFromOneSchemaSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND nspname = ?");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllSequencesSQL")) {
         getterName = "getAllSequencesSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllSequencesSQL";
         }

         currentResult = new PropertyDescriptor("AllSequencesSQL", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("AllSequencesSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S'");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowsAliasInBulkClause")) {
         getterName = "getAllowsAliasInBulkClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowsAliasInBulkClause";
         }

         currentResult = new PropertyDescriptor("AllowsAliasInBulkClause", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("AllowsAliasInBulkClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoAssignTypeName")) {
         getterName = "getAutoAssignTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignTypeName";
         }

         currentResult = new PropertyDescriptor("AutoAssignTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BIGSERIAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BYTEA");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BOOL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BYTEA");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TEXT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatePrecision")) {
         getterName = "getDatePrecision";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatePrecision";
         }

         currentResult = new PropertyDescriptor("DatePrecision", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("DatePrecision", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE PRECISION");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT CURRVAL(''{2}'')");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BYTEA");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TEXT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxAutoAssignNameLength")) {
         getterName = "getMaxAutoAssignNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxAutoAssignNameLength";
         }

         currentResult = new PropertyDescriptor("MaxAutoAssignNameLength", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxAutoAssignNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(31));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxColumnNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(31));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConstraintNameLength")) {
         getterName = "getMaxConstraintNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConstraintNameLength";
         }

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(31));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(31));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(31));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NamedSequenceFromOneSchemaSQL")) {
         getterName = "getNamedSequenceFromOneSchemaSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNamedSequenceFromOneSchemaSQL";
         }

         currentResult = new PropertyDescriptor("NamedSequenceFromOneSchemaSQL", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("NamedSequenceFromOneSchemaSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class, pg_namespace WHERE relkind='S' AND pg_class.relnamespace = pg_namespace.oid AND relname = ? AND nspname = ?");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NamedSequencesFromAllSchemasSQL")) {
         getterName = "getNamedSequencesFromAllSchemasSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNamedSequencesFromAllSchemasSQL";
         }

         currentResult = new PropertyDescriptor("NamedSequencesFromAllSchemasSQL", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("NamedSequencesFromAllSchemasSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NULL AS SEQUENCE_SCHEMA, relname AS SEQUENCE_NAME FROM pg_class WHERE relkind='S' AND relname = ?");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NextSequenceQuery")) {
         getterName = "getNextSequenceQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNextSequenceQuery";
         }

         currentResult = new PropertyDescriptor("NextSequenceQuery", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("NextSequenceQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NEXTVAL(''{0}'')");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "PostgreSQL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RangePosition")) {
         getterName = "getRangePosition";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRangePosition";
         }

         currentResult = new PropertyDescriptor("RangePosition", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("RangePosition", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RealTypeName")) {
         getterName = "getRealTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealTypeName";
         }

         currentResult = new PropertyDescriptor("RealTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("RealTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FLOAT8");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAliasForSubselect")) {
         getterName = "getRequiresAliasForSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAliasForSubselect";
         }

         currentResult = new PropertyDescriptor("RequiresAliasForSubselect", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAliasForSubselect", currentResult);
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

         currentResult = new PropertyDescriptor("SchemaCase", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SchemaCase", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "lower");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAlterTableWithDropColumn")) {
         getterName = "getSupportsAlterTableWithDropColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAlterTableWithDropColumn";
         }

         currentResult = new PropertyDescriptor("SupportsAlterTableWithDropColumn", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAlterTableWithDropColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsLockingWithDistinctClause", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithDistinctClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithOuterJoin")) {
         getterName = "getSupportsLockingWithOuterJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithOuterJoin";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithOuterJoin", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithOuterJoin", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetImportedKeys")) {
         getterName = "getSupportsNullTableForGetImportedKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetImportedKeys";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetImportedKeys", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetImportedKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectEndIndex")) {
         getterName = "getSupportsSelectEndIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectEndIndex";
         }

         currentResult = new PropertyDescriptor("SupportsSelectEndIndex", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsSelectStartIndex", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectStartIndex", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSetFetchSize")) {
         getterName = "getSupportsSetFetchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSetFetchSize";
         }

         currentResult = new PropertyDescriptor("SupportsSetFetchSize", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSetFetchSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimestampTypeName")) {
         getterName = "getTimestampTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimestampTypeName";
         }

         currentResult = new PropertyDescriptor("TimestampTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("TimestampTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "ABSTIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBytesForBlobs")) {
         getterName = "getUseGetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetBytesForBlobs", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("UseGetStringForClobs", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("UseSetBytesForBlobs", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("UseSetStringForClobs", PostgresDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("ValidationSQL", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NOW()");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarbinaryTypeName")) {
         getterName = "getVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("VarbinaryTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("VarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BYTEA");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarcharTypeName")) {
         getterName = "getVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("VarcharTypeName", PostgresDictionaryBean.class, getterName, setterName);
         descriptors.put("VarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "VARCHAR{0}");
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
