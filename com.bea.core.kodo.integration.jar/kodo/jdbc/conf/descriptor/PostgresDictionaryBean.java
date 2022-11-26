package kodo.jdbc.conf.descriptor;

public interface PostgresDictionaryBean extends BuiltInDBDictionaryBean {
   String getBinaryTypeName();

   void setBinaryTypeName(String var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   boolean getUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getAllSequencesFromOneSchemaSQL();

   void setAllSequencesFromOneSchemaSQL(String var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getNextSequenceQuery();

   void setNextSequenceQuery(String var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   boolean getSupportsSetFetchSize();

   void setSupportsSetFetchSize(boolean var1);

   String getSchemaCase();

   void setSchemaCase(String var1);

   String getAllSequencesSQL();

   void setAllSequencesSQL(String var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   int getDatePrecision();

   void setDatePrecision(int var1);

   boolean getSupportsAlterTableWithDropColumn();

   void setSupportsAlterTableWithDropColumn(boolean var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   String getNamedSequencesFromAllSchemasSQL();

   void setNamedSequencesFromAllSchemasSQL(String var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   String getNamedSequenceFromOneSchemaSQL();

   void setNamedSequenceFromOneSchemaSQL(String var1);

   String getAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   int getMaxAutoAssignNameLength();

   void setMaxAutoAssignNameLength(int var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   String getVarcharTypeName();

   void setVarcharTypeName(String var1);

   int getRangePosition();

   void setRangePosition(int var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   boolean getUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   boolean getSupportsLockingWithOuterJoin();

   void setSupportsLockingWithOuterJoin(boolean var1);

   boolean getSupportsNullTableForGetImportedKeys();

   void setSupportsNullTableForGetImportedKeys(boolean var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   String getRealTypeName();

   void setRealTypeName(String var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getVarbinaryTypeName();

   void setVarbinaryTypeName(String var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   boolean getSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);
}
