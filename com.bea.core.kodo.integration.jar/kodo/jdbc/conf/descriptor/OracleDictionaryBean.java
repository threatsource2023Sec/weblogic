package kodo.jdbc.conf.descriptor;

public interface OracleDictionaryBean extends BuiltInDBDictionaryBean {
   String getBinaryTypeName();

   void setBinaryTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getStringLengthFunction();

   void setStringLengthFunction(String var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getNextSequenceQuery();

   void setNextSequenceQuery(String var1);

   boolean getUseSetFormOfUseForUnicode();

   void setUseSetFormOfUseForUnicode(boolean var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   int getMaxEmbeddedClobSize();

   void setMaxEmbeddedClobSize(int var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   String getDecimalTypeName();

   void setDecimalTypeName(String var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   String getTimeTypeName();

   void setTimeTypeName(String var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   String getVarcharTypeName();

   void setVarcharTypeName(String var1);

   boolean getUseTriggersForAutoAssign();

   void setUseTriggersForAutoAssign(boolean var1);

   String getNumericTypeName();

   void setNumericTypeName(String var1);

   String getIntegerTypeName();

   void setIntegerTypeName(String var1);

   String getPlatform();

   void setPlatform(String var1);

   boolean getOpenjpa3GeneratedKeyNames();

   void setOpenjpa3GeneratedKeyNames(boolean var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   String getJoinSyntax();

   void setJoinSyntax(String var1);

   int getMaxEmbeddedBlobSize();

   void setMaxEmbeddedBlobSize(int var1);

   String getAutoAssignSequenceName();

   void setAutoAssignSequenceName(String var1);

   boolean getSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);
}
