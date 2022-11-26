package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class BuiltInDBDictionaryBeanImplBeanInfo extends DBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = BuiltInDBDictionaryBean.class;

   public BuiltInDBDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BuiltInDBDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.BuiltInDBDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.BuiltInDBDictionaryBean");
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

         currentResult = new PropertyDescriptor("AllowsAliasInBulkClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("AllowsAliasInBulkClause", currentResult);
         currentResult.setValue("description", "When true, SQL delete and update statements may use table aliases. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ArrayTypeName")) {
         getterName = "getArrayTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArrayTypeName";
         }

         currentResult = new PropertyDescriptor("ArrayTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ArrayTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.ARRAY. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "ARRAY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoAssignClause")) {
         getterName = "getAutoAssignClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignClause";
         }

         currentResult = new PropertyDescriptor("AutoAssignClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignClause", currentResult);
         currentResult.setValue("description", "The magic word(s) to append to the column definition of a SQL statement that creates an auto-assignment column. For example, \"AUTO_INCREMENT\" for MySQL. Used only when the mapping tool generates a schema. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoAssignTypeName")) {
         getterName = "getAutoAssignTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignTypeName";
         }

         currentResult = new PropertyDescriptor("AutoAssignTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type used for auto-increment columns. For example, \"BIGSERIAL\" for PostgreSQL. The mapping tool uses the name when it generates a schema. ");
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

         currentResult = new PropertyDescriptor("BigintTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.BIGINT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "BIGINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.BINARY. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "BINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitLengthFunction")) {
         getterName = "getBitLengthFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitLengthFunction";
         }

         currentResult = new PropertyDescriptor("BitLengthFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BitLengthFunction", currentResult);
         currentResult.setValue("description", "This property is not used. ");
         setPropertyDescriptorDefault(currentResult, "(OCTET_LENGTH({0}) * 8)");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.BIT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "BIT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.BLOB. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "BLOB");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BooleanTypeName")) {
         getterName = "getBooleanTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBooleanTypeName";
         }

         currentResult = new PropertyDescriptor("BooleanTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("BooleanTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.BOOLEAN. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "BOOLEAN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CastFunction")) {
         getterName = "getCastFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCastFunction";
         }

         currentResult = new PropertyDescriptor("CastFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CastFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to cast a value to another SQL type. Use the tokens {0} and {1} to represent the two arguments.  The result of the function is convert the {0} value to a {1} type. ");
         setPropertyDescriptorDefault(currentResult, "CAST({0} AS {1})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CatalogSeparator")) {
         getterName = "getCatalogSeparator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCatalogSeparator";
         }

         currentResult = new PropertyDescriptor("CatalogSeparator", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CatalogSeparator", currentResult);
         currentResult.setValue("description", "The string the database uses to delimit between the schema name and the table name. ");
         setPropertyDescriptorDefault(currentResult, ".");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CharTypeName")) {
         getterName = "getCharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCharTypeName";
         }

         currentResult = new PropertyDescriptor("CharTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CharTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.CHAR. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "CHAR");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CharacterColumnSize")) {
         getterName = "getCharacterColumnSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCharacterColumnSize";
         }

         currentResult = new PropertyDescriptor("CharacterColumnSize", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CharacterColumnSize", currentResult);
         currentResult.setValue("description", "The default size of VARCHAR and CHAR columns, typically 255. ");
         setPropertyDescriptorDefault(currentResult, new Integer(255));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.CLOB. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "CLOB");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClosePoolSQL")) {
         getterName = "getClosePoolSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClosePoolSQL";
         }

         currentResult = new PropertyDescriptor("ClosePoolSQL", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ClosePoolSQL", currentResult);
         currentResult.setValue("description", "A special command to issue to the database when shutting down the pool. <p> Usually the pool of connections to the database is closed when the application is ending.  For embedded databases, whose lifecycle is coterminous with the application, there may be a special command, usually \"SHUTDOWN\", that will cause the database to close cleanly. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConcatenateDelimiter")) {
         getterName = "getConcatenateDelimiter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcatenateDelimiter";
         }

         currentResult = new PropertyDescriptor("ConcatenateDelimiter", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ConcatenateDelimiter", currentResult);
         currentResult.setValue("description", "This property is not used. ");
         setPropertyDescriptorDefault(currentResult, "'OPENJPATOKEN'");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConcatenateFunction")) {
         getterName = "getConcatenateFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcatenateFunction";
         }

         currentResult = new PropertyDescriptor("ConcatenateFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ConcatenateFunction", currentResult);
         currentResult.setValue("description", "The SQL function call or operation to concatenate two strings. Use the tokens {0} and {1} to represent the two arguments.  The result of the function or operation is to concatenate the {1} string to the end of the {0} string. ");
         setPropertyDescriptorDefault(currentResult, "({0}||{1})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConstraintNameMode")) {
         getterName = "getConstraintNameMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConstraintNameMode";
         }

         currentResult = new PropertyDescriptor("ConstraintNameMode", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ConstraintNameMode", currentResult);
         currentResult.setValue("description", "When creating constraints, determines where to put the constraint name. <ul> <li>before - put name before the constraint's definition</li> <li>mid - put name just after the constraint's type name</li> <li>after - put name after the constraint's definition</li> <item> </ul> ");
         setPropertyDescriptorDefault(currentResult, "before");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreatePrimaryKeys")) {
         getterName = "getCreatePrimaryKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreatePrimaryKeys";
         }

         currentResult = new PropertyDescriptor("CreatePrimaryKeys", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CreatePrimaryKeys", currentResult);
         currentResult.setValue("description", "When true, create database primary keys for identifiers. ");
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

         currentResult = new PropertyDescriptor("CrossJoinClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CrossJoinClause", currentResult);
         currentResult.setValue("description", "The SQL clause to express a cross join (cartesian product). ");
         setPropertyDescriptorDefault(currentResult, "CROSS JOIN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentDateFunction")) {
         getterName = "getCurrentDateFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentDateFunction";
         }

         currentResult = new PropertyDescriptor("CurrentDateFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentDateFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to obtain the current date from the database. ");
         setPropertyDescriptorDefault(currentResult, "CURRENT_DATE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentTimeFunction")) {
         getterName = "getCurrentTimeFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentTimeFunction";
         }

         currentResult = new PropertyDescriptor("CurrentTimeFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentTimeFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to obtain the current time from the database. ");
         setPropertyDescriptorDefault(currentResult, "CURRENT_TIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentTimestampFunction")) {
         getterName = "getCurrentTimestampFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentTimestampFunction";
         }

         currentResult = new PropertyDescriptor("CurrentTimestampFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("CurrentTimestampFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to obtain the current timestamp from the database. ");
         setPropertyDescriptorDefault(currentResult, "CURRENT_TIMESTAMP");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatePrecision")) {
         getterName = "getDatePrecision";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatePrecision";
         }

         currentResult = new PropertyDescriptor("DatePrecision", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DatePrecision", currentResult);
         currentResult.setValue("description", "The database is able to store time values to this degree of precision, which is expressed in nanoseconds. <p> This value is usually one million, meaning that the database is able to store time values with a precision of one millisecond.  Particular databases may have more or less precision. <p> Kodo and OpenJPA will round all time values to this degree of precision before storing them in the database. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DateTypeName")) {
         getterName = "getDateTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDateTypeName";
         }

         currentResult = new PropertyDescriptor("DateTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DateTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.DATE. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "DATE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DecimalTypeName")) {
         getterName = "getDecimalTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDecimalTypeName";
         }

         currentResult = new PropertyDescriptor("DecimalTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DecimalTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.DECIMAL. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "DECIMAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistinctCountColumnSeparator")) {
         getterName = "getDistinctCountColumnSeparator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistinctCountColumnSeparator";
         }

         currentResult = new PropertyDescriptor("DistinctCountColumnSeparator", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DistinctCountColumnSeparator", currentResult);
         currentResult.setValue("description", "The string the database uses to delimit between column expressions in a SELECT COUNT(DISTINCT column-list) clause. Defaults to null for most databases, meaning that they do not support multiple columns in a distinct COUNT clause. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistinctTypeName")) {
         getterName = "getDistinctTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistinctTypeName";
         }

         currentResult = new PropertyDescriptor("DistinctTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DistinctTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.DISTINCT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "DISTINCT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.DOUBLE. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DriverVendor")) {
         getterName = "getDriverVendor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDriverVendor";
         }

         currentResult = new PropertyDescriptor("DriverVendor", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DriverVendor", currentResult);
         currentResult.setValue("description", "Some dictionaries must alter their behavior depending on the driver's vendor.  When used, the dictionary often sets this property using its own logic.  To override this logic, see the VENDOR_XXX constants, if any, defined in the dictionary's Javadoc. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DropTableSQL")) {
         getterName = "getDropTableSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDropTableSQL";
         }

         currentResult = new PropertyDescriptor("DropTableSQL", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("DropTableSQL", currentResult);
         currentResult.setValue("description", "The SQL statement used to drop a table.  Use the token {0} as the argument for the table name. ");
         setPropertyDescriptorDefault(currentResult, "DROP TABLE {0}");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FixedSizeTypeNames")) {
         getterName = "getFixedSizeTypeNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFixedSizeTypeNames";
         }

         currentResult = new PropertyDescriptor("FixedSizeTypeNames", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("FixedSizeTypeNames", currentResult);
         currentResult.setValue("description", "A comma separated list of additional database types that have a size defined by the database.  In other words, when a column of a fixed size type is declared, its size cannot be defined by the user.  Common examples would be DATE, FLOAT, and INTEGER. <p> Each database dictionary has its own internal set of fixed size type names (fixedSizeTypeNameSet) that include the names mentioned above and many others. <p> Names added to this property are added to the dictionary's internal set. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FloatTypeName")) {
         getterName = "getFloatTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFloatTypeName";
         }

         currentResult = new PropertyDescriptor("FloatTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("FloatTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.FLOAT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "FLOAT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForUpdateClause")) {
         getterName = "getForUpdateClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForUpdateClause";
         }

         currentResult = new PropertyDescriptor("ForUpdateClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ForUpdateClause", currentResult);
         currentResult.setValue("description", "The clause to append to SQL select statements to issue queries that obtain pessimistic locks.  The clause is usually FOR UPDATE. ");
         setPropertyDescriptorDefault(currentResult, "FOR UPDATE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitializationSQL")) {
         getterName = "getInitializationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitializationSQL";
         }

         currentResult = new PropertyDescriptor("InitializationSQL", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("InitializationSQL", currentResult);
         currentResult.setValue("description", "A SQL statement to initialize a connection after obtaining it from the DataSource.  The dictionary normally uses this to work around any JDBC bugs. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InnerJoinClause")) {
         getterName = "getInnerJoinClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInnerJoinClause";
         }

         currentResult = new PropertyDescriptor("InnerJoinClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("InnerJoinClause", currentResult);
         currentResult.setValue("description", "The SQL clause to express an inner join. ");
         setPropertyDescriptorDefault(currentResult, "INNER JOIN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.INTEGER. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaObjectTypeName")) {
         getterName = "getJavaObjectTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaObjectTypeName";
         }

         currentResult = new PropertyDescriptor("JavaObjectTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("JavaObjectTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.JAVAOBJECT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "JAVA_OBJECT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinSyntax")) {
         getterName = "getJoinSyntax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinSyntax";
         }

         currentResult = new PropertyDescriptor("JoinSyntax", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("JoinSyntax", currentResult);
         currentResult.setValue("description", "The style of SQL join syntax to use in select statements. <ul> <li>sql92 - ANSI SQL92 join syntax where joins are expressed in the SQL FROM clause. Outer joins are supported. Not all databases support this syntax.</li> <li>traditional - Traditional SQL join syntax where joins are expressed in the SQL WHERE clause.  Outer joins are not supported.</li> <li>database - Join syntax is encoded to some extent within the dictionary class for the database.  Outer joins may be supported.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "sql92");
         currentResult.setValue("legalValues", new Object[]{"sql92", "traditional", "database"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", "The query to issue to obtain the last automatically generated key for an auto-increment column. For example, \"SELECT LAST_INSERT_ID()\" for MySQL. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.LONGVARBINARY. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "LONGVARBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.LONGVARCHAR. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "LONGVARCHAR");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxAutoAssignNameLength")) {
         getterName = "getMaxAutoAssignNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxAutoAssignNameLength";
         }

         currentResult = new PropertyDescriptor("MaxAutoAssignNameLength", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxAutoAssignNameLength", currentResult);
         currentResult.setValue("description", "The maximum number of characters in the name of a sequence used for auto-increment columns.  Any name provided that is longer than this value is truncated. ");
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

         currentResult = new PropertyDescriptor("MaxColumnNameLength", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxColumnNameLength", currentResult);
         currentResult.setValue("description", "The maximum number of characters in a column name. ");
         setPropertyDescriptorDefault(currentResult, new Integer(128));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConstraintNameLength")) {
         getterName = "getMaxConstraintNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConstraintNameLength";
         }

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", "The maximum number of characters in a constraint name. ");
         setPropertyDescriptorDefault(currentResult, new Integer(128));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEmbeddedBlobSize")) {
         getterName = "getMaxEmbeddedBlobSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEmbeddedBlobSize";
         }

         currentResult = new PropertyDescriptor("MaxEmbeddedBlobSize", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxEmbeddedBlobSize", currentResult);
         currentResult.setValue("description", "When greater than -1, the maximum size of a BLOB value that can be sent directly to the database within an insert or update statement.  Values whose size is greater than MaxEmbeddedBlobSize force OpenJPA to work around this limitation.  A value of -1 means that there is no limitation. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEmbeddedClobSize")) {
         getterName = "getMaxEmbeddedClobSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEmbeddedClobSize";
         }

         currentResult = new PropertyDescriptor("MaxEmbeddedClobSize", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxEmbeddedClobSize", currentResult);
         currentResult.setValue("description", "When greater than -1, the maximum size of a CLOB value that can be sent directly to the database within an insert or update statement.  Values whose size is greater than MaxEmbeddedClobSize force OpenJPA to work around this limitation.  A value of -1 means that there is no limitation. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", "The maximum number of characters in an index name. ");
         setPropertyDescriptorDefault(currentResult, new Integer(128));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexesPerTable")) {
         getterName = "getMaxIndexesPerTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexesPerTable";
         }

         currentResult = new PropertyDescriptor("MaxIndexesPerTable", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexesPerTable", currentResult);
         currentResult.setValue("description", "The maximum number of indexes that can be placed on a single table. ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", "The maximum number of characters in a table name. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NextSequenceQuery")) {
         getterName = "getNextSequenceQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNextSequenceQuery";
         }

         currentResult = new PropertyDescriptor("NextSequenceQuery", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("NextSequenceQuery", currentResult);
         currentResult.setValue("description", "A SQL string for obtaining a native sequence value. May use a placeholder of {0} for the sequence name. For example, \"SELECT {0}.NEXTVAL FROM DUAL\" for Oracle. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NullTypeName")) {
         getterName = "getNullTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNullTypeName";
         }

         currentResult = new PropertyDescriptor("NullTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("NullTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.NULL. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "NULL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumericTypeName")) {
         getterName = "getNumericTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumericTypeName";
         }

         currentResult = new PropertyDescriptor("NumericTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("NumericTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.NUMERIC. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "NUMERIC");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OtherTypeName")) {
         getterName = "getOtherTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOtherTypeName";
         }

         currentResult = new PropertyDescriptor("OtherTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("OtherTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.OTHER. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "OTHER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OuterJoinClause")) {
         getterName = "getOuterJoinClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOuterJoinClause";
         }

         currentResult = new PropertyDescriptor("OuterJoinClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("OuterJoinClause", currentResult);
         currentResult.setValue("description", "The SQL clause to express a left outer join. ");
         setPropertyDescriptorDefault(currentResult, "LEFT OUTER JOIN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", "The name of the database that this dictionary targets. ");
         setPropertyDescriptorDefault(currentResult, "Generic");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RangePosition")) {
         getterName = "getRangePosition";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRangePosition";
         }

         currentResult = new PropertyDescriptor("RangePosition", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RangePosition", currentResult);
         currentResult.setValue("description", "Indicates where to specify in the SQL select statement the range, if any, of the result rows to be returned. <p> When limiting the number of returned result rows to a subset of all those that satisfy the query's conditions, the position of the range clause varies by database. <p> Examine the source code of the org.apache.openjpa.jdbc.sql.DBDictionary class to view the possible values and their effects. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RealTypeName")) {
         getterName = "getRealTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealTypeName";
         }

         currentResult = new PropertyDescriptor("RealTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RealTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.REAL. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "REAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefTypeName")) {
         getterName = "getRefTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefTypeName";
         }

         currentResult = new PropertyDescriptor("RefTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RefTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.REF. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "REF");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAliasForSubselect")) {
         getterName = "getRequiresAliasForSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAliasForSubselect";
         }

         currentResult = new PropertyDescriptor("RequiresAliasForSubselect", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAliasForSubselect", currentResult);
         currentResult.setValue("description", "When true, the database requires that subselects in a FROM clause be assigned an alias. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAutoCommitForMetaData")) {
         getterName = "getRequiresAutoCommitForMetaData";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAutoCommitForMetaData";
         }

         currentResult = new PropertyDescriptor("RequiresAutoCommitForMetaData", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAutoCommitForMetaData", currentResult);
         currentResult.setValue("description", "When true, the JDBC driver requires that autocommit be enabled before any schema interrogation operations can take place. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresCastForComparisons")) {
         getterName = "getRequiresCastForComparisons";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresCastForComparisons";
         }

         currentResult = new PropertyDescriptor("RequiresCastForComparisons", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresCastForComparisons", currentResult);
         currentResult.setValue("description", "When true, comparisons of two values of different types or of two literals requires a cast in the generated SQL. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresCastForMathFunctions")) {
         getterName = "getRequiresCastForMathFunctions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresCastForMathFunctions";
         }

         currentResult = new PropertyDescriptor("RequiresCastForMathFunctions", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresCastForMathFunctions", currentResult);
         currentResult.setValue("description", "When true, math operations on two values of different types or on two literals requires a cast in the generated SQL. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresConditionForCrossJoin")) {
         getterName = "getRequiresConditionForCrossJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresConditionForCrossJoin";
         }

         currentResult = new PropertyDescriptor("RequiresConditionForCrossJoin", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresConditionForCrossJoin", currentResult);
         currentResult.setValue("description", "When true, the database requires that there always be a conditional clause in any SQL statement expressing a cross join. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReservedWords")) {
         getterName = "getReservedWords";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReservedWords";
         }

         currentResult = new PropertyDescriptor("ReservedWords", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ReservedWords", currentResult);
         currentResult.setValue("description", "A comma-separated list of reserved words for the database, beyond the standard SQL92 keywords. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaCase")) {
         getterName = "getSchemaCase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaCase";
         }

         currentResult = new PropertyDescriptor("SchemaCase", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SchemaCase", currentResult);
         currentResult.setValue("description", "The case to use when querying the database metadata about schema components. Available values are: upper, lower, and preserve. ");
         setPropertyDescriptorDefault(currentResult, "upper");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SearchStringEscape")) {
         getterName = "getSearchStringEscape";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSearchStringEscape";
         }

         currentResult = new PropertyDescriptor("SearchStringEscape", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SearchStringEscape", currentResult);
         currentResult.setValue("description", "The default escape character used when generating SQL LIKE clauses.  The escape character is used to escape the wildcard meaning of the _ and % characters. <p> Note: since JPQL provides the ability to define the escape character in the query, this setting is primarily used when translating JDOQL matches expressions. ");
         setPropertyDescriptorDefault(currentResult, "\\");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SimulateLocking")) {
         getterName = "getSimulateLocking";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSimulateLocking";
         }

         currentResult = new PropertyDescriptor("SimulateLocking", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SimulateLocking", currentResult);
         currentResult.setValue("description", "Setting this property to true bypasses Kodo's attempt, when executing a transaction with the pessimistic lock manager, to lock records in the database upon load. <p> Because some databases do not support pessimistic locking, attempting a pessimistic transaction within Kodo will result in an exception with these databases unless this property is set to true.  At the same time, setting this property to true means that the semantics of a pessimistic transaction with the database are not obtained. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.SMALLINT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StorageLimitationsFatal")) {
         getterName = "getStorageLimitationsFatal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStorageLimitationsFatal";
         }

         currentResult = new PropertyDescriptor("StorageLimitationsFatal", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("StorageLimitationsFatal", currentResult);
         currentResult.setValue("description", "When true, then any data truncation or rounding that is performed by the dictionary in order to store a value in the database will be treated as a fatal error.  Otherwise, the dictionary issues a warning. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreCharsAsNumbers")) {
         getterName = "getStoreCharsAsNumbers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreCharsAsNumbers";
         }

         currentResult = new PropertyDescriptor("StoreCharsAsNumbers", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("StoreCharsAsNumbers", currentResult);
         currentResult.setValue("description", "When true, the dictionary prefers to store Java char fields as numbers rather than as CHAR values. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreLargeNumbersAsStrings")) {
         getterName = "getStoreLargeNumbersAsStrings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreLargeNumbersAsStrings";
         }

         currentResult = new PropertyDescriptor("StoreLargeNumbersAsStrings", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("StoreLargeNumbersAsStrings", currentResult);
         currentResult.setValue("description", "When true, the dictionary prefers to store large numbers (Java fields of type BigInteger and BigDecimal) as string values in the database.  Likewise, the dictionary will instruct the mapping tool to map the BigInteger or BigDecimal fields to character columns. <p> Because many databases have limitations on the number of digits that can be stored in a numeric column (for example, Oracle can only store 38 digits), this option may be necessary for some applications. <p> Note that this option may prevent Kodo from executing meaningful numeric queries against the columns. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StringLengthFunction")) {
         getterName = "getStringLengthFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStringLengthFunction";
         }

         currentResult = new PropertyDescriptor("StringLengthFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("StringLengthFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to get the length of a string. Use the token {0} to represent the argument. ");
         setPropertyDescriptorDefault(currentResult, "CHAR_LENGTH({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StructTypeName")) {
         getterName = "getStructTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStructTypeName";
         }

         currentResult = new PropertyDescriptor("StructTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("StructTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.STRUCT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "STRUCT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubstringFunctionName")) {
         getterName = "getSubstringFunctionName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubstringFunctionName";
         }

         currentResult = new PropertyDescriptor("SubstringFunctionName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SubstringFunctionName", currentResult);
         currentResult.setValue("description", "The name of the SQL function for getting the substring of a string. Arguments are not represented. ");
         setPropertyDescriptorDefault(currentResult, "SUBSTRING");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAlterTableWithAddColumn")) {
         getterName = "getSupportsAlterTableWithAddColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAlterTableWithAddColumn";
         }

         currentResult = new PropertyDescriptor("SupportsAlterTableWithAddColumn", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAlterTableWithAddColumn", currentResult);
         currentResult.setValue("description", "When true, the database supports adding a new column in an ALTER TABLE statement. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAlterTableWithDropColumn")) {
         getterName = "getSupportsAlterTableWithDropColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAlterTableWithDropColumn";
         }

         currentResult = new PropertyDescriptor("SupportsAlterTableWithDropColumn", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAlterTableWithDropColumn", currentResult);
         currentResult.setValue("description", "When true, the database supports dropping a column in an ALTER TABLE statement. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAutoAssign", currentResult);
         currentResult.setValue("description", "When true, the database supports auto-assign columns where the value of column is assigned upon insertion of the row into the database. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsCascadeDeleteAction")) {
         getterName = "getSupportsCascadeDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsCascadeDeleteAction";
         }

         currentResult = new PropertyDescriptor("SupportsCascadeDeleteAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsCascadeDeleteAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the CASCADE delete action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsCascadeUpdateAction")) {
         getterName = "getSupportsCascadeUpdateAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsCascadeUpdateAction";
         }

         currentResult = new PropertyDescriptor("SupportsCascadeUpdateAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsCascadeUpdateAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the CASCADE update action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsCorrelatedSubselect")) {
         getterName = "getSupportsCorrelatedSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsCorrelatedSubselect";
         }

         currentResult = new PropertyDescriptor("SupportsCorrelatedSubselect", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsCorrelatedSubselect", currentResult);
         currentResult.setValue("description", "When true, the database supports correlated subselects.  Correlated subselects are select statements nested within select statements that refers to a column in the outer select statement.  For performance reasons, correlated subselects are generally a last resort. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDefaultDeleteAction")) {
         getterName = "getSupportsDefaultDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDefaultDeleteAction";
         }

         currentResult = new PropertyDescriptor("SupportsDefaultDeleteAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDefaultDeleteAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the SET DEFAULT delete action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDefaultUpdateAction")) {
         getterName = "getSupportsDefaultUpdateAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDefaultUpdateAction";
         }

         currentResult = new PropertyDescriptor("SupportsDefaultUpdateAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDefaultUpdateAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the SET DEFAULT update action on foreign keys. ");
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

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", "When true, the database supports deferred constraints.  The database supports deferred constraints by checking for constraint violations when the transaction commits, rather than checking for violations immediately after receiving each SQL statement within the transaction. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsForeignKeys")) {
         getterName = "getSupportsForeignKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsForeignKeys";
         }

         currentResult = new PropertyDescriptor("SupportsForeignKeys", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsForeignKeys", currentResult);
         currentResult.setValue("description", "When true, the database supports foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsHaving")) {
         getterName = "getSupportsHaving";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsHaving";
         }

         currentResult = new PropertyDescriptor("SupportsHaving", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsHaving", currentResult);
         currentResult.setValue("description", "When true, the database supports HAVING clauses in SQL select statements. ");
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

         currentResult = new PropertyDescriptor("SupportsLockingWithDistinctClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithDistinctClause", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements with DISTINCT clauses. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithInnerJoin")) {
         getterName = "getSupportsLockingWithInnerJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithInnerJoin";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithInnerJoin", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithInnerJoin", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements with inner join clauses. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithMultipleTables")) {
         getterName = "getSupportsLockingWithMultipleTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithMultipleTables";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithMultipleTables", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithMultipleTables", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements that select from multiple tables. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithOrderClause")) {
         getterName = "getSupportsLockingWithOrderClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithOrderClause";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithOrderClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithOrderClause", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements with ORDER BY clauses. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithOuterJoin")) {
         getterName = "getSupportsLockingWithOuterJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithOuterJoin";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithOuterJoin", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithOuterJoin", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements with outer join clauses. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsLockingWithSelectRange")) {
         getterName = "getSupportsLockingWithSelectRange";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsLockingWithSelectRange";
         }

         currentResult = new PropertyDescriptor("SupportsLockingWithSelectRange", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsLockingWithSelectRange", currentResult);
         currentResult.setValue("description", "When true, the database supports FOR UPDATE clauses in SQL select statements select a range of data using LIMIT, TOP or equivalent database specific clause. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsModOperator")) {
         getterName = "getSupportsModOperator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsModOperator";
         }

         currentResult = new PropertyDescriptor("SupportsModOperator", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsModOperator", currentResult);
         currentResult.setValue("description", "When true, the database supports the modulus operator (%) instead of the MOD function. ");
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

         currentResult = new PropertyDescriptor("SupportsMultipleNontransactionalResultSets", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsMultipleNontransactionalResultSets", currentResult);
         currentResult.setValue("description", "When true, a nontransactional JDBC connection to the database is capable of having multiple open ResultSet instances. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullDeleteAction")) {
         getterName = "getSupportsNullDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullDeleteAction";
         }

         currentResult = new PropertyDescriptor("SupportsNullDeleteAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullDeleteAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the SET NULL delete action on foreign keys. ");
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

         currentResult = new PropertyDescriptor("SupportsNullTableForGetColumns", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetColumns", currentResult);
         currentResult.setValue("description", "When true, the database supports passing a null parameter to DatabaseMetaData.getColumns as an optimization to get information on all tables. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetImportedKeys")) {
         getterName = "getSupportsNullTableForGetImportedKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetImportedKeys";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetImportedKeys", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetImportedKeys", currentResult);
         currentResult.setValue("description", "When true, the database supports passing a null parameter to DatabaseMetaData.getImportedKeys as an optimization to get information on all tables. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetIndexInfo")) {
         getterName = "getSupportsNullTableForGetIndexInfo";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetIndexInfo";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetIndexInfo", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetIndexInfo", currentResult);
         currentResult.setValue("description", "When true, the database supports passing a null parameter to DatabaseMetaData.getIndexInfo as an optimization to get information on all tables. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetPrimaryKeys")) {
         getterName = "getSupportsNullTableForGetPrimaryKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetPrimaryKeys";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetPrimaryKeys", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetPrimaryKeys", currentResult);
         currentResult.setValue("description", "When true, the database supports passing a null parameter to DatabaseMetaData.getPrimaryKeys as an optimization to get information on all tables. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullUpdateAction")) {
         getterName = "getSupportsNullUpdateAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullUpdateAction";
         }

         currentResult = new PropertyDescriptor("SupportsNullUpdateAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullUpdateAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the SET NULL update action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsQueryTimeout")) {
         getterName = "getSupportsQueryTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsQueryTimeout";
         }

         currentResult = new PropertyDescriptor("SupportsQueryTimeout", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsQueryTimeout", currentResult);
         currentResult.setValue("description", "When true, the JDBC driver supports calls to java.sql.Statement.setQueryTimeout. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsRestrictDeleteAction")) {
         getterName = "getSupportsRestrictDeleteAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsRestrictDeleteAction";
         }

         currentResult = new PropertyDescriptor("SupportsRestrictDeleteAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsRestrictDeleteAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the RESTRICT delete action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsRestrictUpdateAction")) {
         getterName = "getSupportsRestrictUpdateAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsRestrictUpdateAction";
         }

         currentResult = new PropertyDescriptor("SupportsRestrictUpdateAction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsRestrictUpdateAction", currentResult);
         currentResult.setValue("description", "When true, the database supports the RESTRICT update action on foreign keys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSchemaForGetColumns")) {
         getterName = "getSupportsSchemaForGetColumns";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSchemaForGetColumns";
         }

         currentResult = new PropertyDescriptor("SupportsSchemaForGetColumns", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSchemaForGetColumns", currentResult);
         currentResult.setValue("description", "When true, the database driver supports using the schema name for schema reflection on column names. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSchemaForGetTables")) {
         getterName = "getSupportsSchemaForGetTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSchemaForGetTables";
         }

         currentResult = new PropertyDescriptor("SupportsSchemaForGetTables", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSchemaForGetTables", currentResult);
         currentResult.setValue("description", "When true, the database driver supports using the schema name for schema reflection on table names. ");
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

         currentResult = new PropertyDescriptor("SupportsSelectEndIndex", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectEndIndex", currentResult);
         currentResult.setValue("description", "When true, the database supports SQL select statements that do not return more than the first N results. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectForUpdate")) {
         getterName = "getSupportsSelectForUpdate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectForUpdate";
         }

         currentResult = new PropertyDescriptor("SupportsSelectForUpdate", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectForUpdate", currentResult);
         currentResult.setValue("description", "When true, the database supports SQL select statements with a pessimistic locking (FOR UPDATE) clause. ");
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

         currentResult = new PropertyDescriptor("SupportsSelectStartIndex", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectStartIndex", currentResult);
         currentResult.setValue("description", "When true, the database supports SQL select statements that skip the first N results. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSubselect")) {
         getterName = "getSupportsSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSubselect";
         }

         currentResult = new PropertyDescriptor("SupportsSubselect", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSubselect", currentResult);
         currentResult.setValue("description", "When true, the database supports SQL select statements with subselect clauses. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsTimestampNanos")) {
         getterName = "getSupportsTimestampNanos";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsTimestampNanos";
         }

         currentResult = new PropertyDescriptor("SupportsTimestampNanos", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsTimestampNanos", currentResult);
         currentResult.setValue("description", "When true, the database supports nanoseconds in TIMESTAMP columns. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsUniqueConstraints")) {
         getterName = "getSupportsUniqueConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsUniqueConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsUniqueConstraints", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsUniqueConstraints", currentResult);
         currentResult.setValue("description", "When true, the database supports unique constraints. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemSchemas")) {
         getterName = "getSystemSchemas";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemSchemas";
         }

         currentResult = new PropertyDescriptor("SystemSchemas", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SystemSchemas", currentResult);
         currentResult.setValue("description", "A comma-separated list of schema names that should be ignored. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemTables")) {
         getterName = "getSystemTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemTables";
         }

         currentResult = new PropertyDescriptor("SystemTables", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("SystemTables", currentResult);
         currentResult.setValue("description", "A comma-separated list of table names that the dictionary should ignored. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableForUpdateClause")) {
         getterName = "getTableForUpdateClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableForUpdateClause";
         }

         currentResult = new PropertyDescriptor("TableForUpdateClause", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TableForUpdateClause", currentResult);
         currentResult.setValue("description", "The clause to append to the end of each table alias in queries that obtain pessimistic locks. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableTypes")) {
         getterName = "getTableTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableTypes";
         }

         currentResult = new PropertyDescriptor("TableTypes", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TableTypes", currentResult);
         currentResult.setValue("description", "Comma-separated list of table types to use when looking for tables during schema reflection, as defined in the JDBC method java.sql.DatabaseMetaData.getTableInfo. Examples are \"TABLE\" and \"TABLE,VIEW,ALIAS\". ");
         setPropertyDescriptorDefault(currentResult, "TABLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeTypeName")) {
         getterName = "getTimeTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeTypeName";
         }

         currentResult = new PropertyDescriptor("TimeTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TimeTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.TIME. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "TIME");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimestampTypeName")) {
         getterName = "getTimestampTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimestampTypeName";
         }

         currentResult = new PropertyDescriptor("TimestampTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TimestampTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.TIMESTAMP. The mapping tool uses the name when it generates a schema. ");
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

         currentResult = new PropertyDescriptor("TinyintTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.TINYINT. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "TINYINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ToLowerCaseFunction")) {
         getterName = "getToLowerCaseFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setToLowerCaseFunction";
         }

         currentResult = new PropertyDescriptor("ToLowerCaseFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ToLowerCaseFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to convert a string to lower case. Use the token {0} to represent the argument. ");
         setPropertyDescriptorDefault(currentResult, "LOWER({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ToUpperCaseFunction")) {
         getterName = "getToUpperCaseFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setToUpperCaseFunction";
         }

         currentResult = new PropertyDescriptor("ToUpperCaseFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ToUpperCaseFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to convert a string to upper case. Use the token {0} to represent the argument. ");
         setPropertyDescriptorDefault(currentResult, "UPPER({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimBothFunction")) {
         getterName = "getTrimBothFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimBothFunction";
         }

         currentResult = new PropertyDescriptor("TrimBothFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimBothFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to trim any number of a particular character from both the start and end of a string. <p> Note: some databases do not support specifying the character in which case only spaces or whitespace can be trimmed. <p> Use the token {1} when possible to represent the character, and the token {0} to represent the string. ");
         setPropertyDescriptorDefault(currentResult, "TRIM(BOTH {1} FROM {0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimLeadingFunction")) {
         getterName = "getTrimLeadingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimLeadingFunction";
         }

         currentResult = new PropertyDescriptor("TrimLeadingFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimLeadingFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to trim any number of a particular character from the start of a string. <p> Note: some databases do not support specifying the character in which case only spaces or whitespace can be trimmed. <p> Use the token {1} when possible to represent the character, and the token {0} to represent the string. ");
         setPropertyDescriptorDefault(currentResult, "TRIM(LEADING {1} FROM {0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimTrailingFunction")) {
         getterName = "getTrimTrailingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimTrailingFunction";
         }

         currentResult = new PropertyDescriptor("TrimTrailingFunction", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimTrailingFunction", currentResult);
         currentResult.setValue("description", "The SQL function call to trim any number of a particular character from the end of a string. <p> Note: some databases do not support specifying the character in which case only spaces or whitespace can be trimmed. <p> Use the token {1} when possible to represent the character, and the token {0} to represent the string. ");
         setPropertyDescriptorDefault(currentResult, "TRIM(TRAILING {1} FROM {0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBestRowIdentifierForPrimaryKeys")) {
         getterName = "getUseGetBestRowIdentifierForPrimaryKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBestRowIdentifierForPrimaryKeys";
         }

         currentResult = new PropertyDescriptor("UseGetBestRowIdentifierForPrimaryKeys", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBestRowIdentifierForPrimaryKeys", currentResult);
         currentResult.setValue("description", "When true, metadata queries will use DatabaseMetaData.getBestRowIdentifier to obtain information about primary keys, rather than DatabaseMetaData.getPrimaryKeys. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBytesForBlobs")) {
         getterName = "getUseGetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetBytesForBlobs", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBytesForBlobs", currentResult);
         currentResult.setValue("description", "When true, the dictionary will use ResultSet.getBytes to obtain blob data rather than ResultSet.getBinaryStream. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetObjectForBlobs")) {
         getterName = "getUseGetObjectForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetObjectForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetObjectForBlobs", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetObjectForBlobs", currentResult);
         currentResult.setValue("description", "When true, the dictionary will use ResultSet.getObject to obtain blob data rather than ResultSet.getBinaryStream. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetStringForClobs")) {
         getterName = "getUseGetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseGetStringForClobs", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetStringForClobs", currentResult);
         currentResult.setValue("description", "When true, the dictionary will use ResultSet.getString to obtain clob data rather than ResultSet.getCharacterStream. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSchemaName")) {
         getterName = "getUseSchemaName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSchemaName";
         }

         currentResult = new PropertyDescriptor("UseSchemaName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSchemaName", currentResult);
         currentResult.setValue("description", "When true, the dictionary generates SQL that uses the schema name with the table name. ");
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

         currentResult = new PropertyDescriptor("UseSetBytesForBlobs", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetBytesForBlobs", currentResult);
         currentResult.setValue("description", "When true, the dictionary will use PreparedStatement.setBytes to set blob data, rather than PreparedStatement.setBinaryStream. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetStringForClobs")) {
         getterName = "getUseSetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseSetStringForClobs", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetStringForClobs", currentResult);
         currentResult.setValue("description", "When true, the dictionary will use PreparedStatement.setString to set clob data, rather than PreparedStatement.setCharacterStream. ");
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

         currentResult = new PropertyDescriptor("ValidationSQL", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", "The SQL used to validate that a connection to the database is still in working order.  The SQL statement should impose minimum load on the database. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarbinaryTypeName")) {
         getterName = "getVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("VarbinaryTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("VarbinaryTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.VARBINARY. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "VARBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VarcharTypeName")) {
         getterName = "getVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("VarcharTypeName", BuiltInDBDictionaryBean.class, getterName, setterName);
         descriptors.put("VarcharTypeName", currentResult);
         currentResult.setValue("description", "The default name for the column type indicated by java.sql.Types.VARCHAR. The mapping tool uses the name when it generates a schema. ");
         setPropertyDescriptorDefault(currentResult, "VARCHAR");
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
