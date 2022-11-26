package kodo.jdbc.conf.descriptor;

public interface SybaseDictionaryBean extends BuiltInDBDictionaryBean {
   String getBinaryTypeName();

   void setBinaryTypeName(String var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   String getConcatenateFunction();

   void setConcatenateFunction(String var1);

   boolean getUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getTrimLeadingFunction();

   void setTrimLeadingFunction(String var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   String getCrossJoinClause();

   void setCrossJoinClause(String var1);

   boolean getRequiresConditionForCrossJoin();

   void setRequiresConditionForCrossJoin(boolean var1);

   boolean getSupportsModOperator();

   void setSupportsModOperator(boolean var1);

   String getSchemaCase();

   void setSchemaCase(String var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   boolean getSupportsNullTableForGetColumns();

   void setSupportsNullTableForGetColumns(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   String getTimeTypeName();

   void setTimeTypeName(String var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   int getRangePosition();

   void setRangePosition(int var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   String getFloatTypeName();

   void setFloatTypeName(String var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getIntegerTypeName();

   void setIntegerTypeName(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   String getForUpdateClause();

   void setForUpdateClause(String var1);

   boolean getUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getIdentityColumnName();

   void setIdentityColumnName(String var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   String getTrimTrailingFunction();

   void setTrimTrailingFunction(String var1);

   String getCurrentTimestampFunction();

   void setCurrentTimestampFunction(String var1);

   String getCurrentTimeFunction();

   void setCurrentTimeFunction(String var1);

   boolean getCreateIdentityColumn();

   void setCreateIdentityColumn(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getCurrentDateFunction();

   void setCurrentDateFunction(String var1);

   String getTrimBothFunction();

   void setTrimBothFunction(String var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);

   String getDateTypeName();

   void setDateTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   boolean getRequiresAutoCommitForMetaData();

   void setRequiresAutoCommitForMetaData(boolean var1);
}
