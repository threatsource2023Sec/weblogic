package kodo.jdbc.conf.descriptor;

public interface HSQLDictionaryBean extends BuiltInDBDictionaryBean {
   String getCrossJoinClause();

   void setCrossJoinClause(String var1);

   boolean getCacheTables();

   void setCacheTables(boolean var1);

   String getStringLengthFunction();

   void setStringLengthFunction(String var1);

   String getTrimLeadingFunction();

   void setTrimLeadingFunction(String var1);

   String getNextSequenceQuery();

   void setNextSequenceQuery(String var1);

   boolean getRequiresConditionForCrossJoin();

   void setRequiresConditionForCrossJoin(boolean var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getSupportsSelectForUpdate();

   void setSupportsSelectForUpdate(boolean var1);

   String getAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   boolean getUseGetObjectForBlobs();

   void setUseGetObjectForBlobs(boolean var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   int getRangePosition();

   void setRangePosition(int var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   String getPlatform();

   void setPlatform(String var1);

   boolean getRequiresCastForComparisons();

   void setRequiresCastForComparisons(boolean var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getUseSchemaName();

   void setUseSchemaName(boolean var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   boolean getSupportsNullTableForGetIndexInfo();

   void setSupportsNullTableForGetIndexInfo(boolean var1);

   String getTrimTrailingFunction();

   void setTrimTrailingFunction(String var1);

   String getClosePoolSQL();

   void setClosePoolSQL(String var1);

   String getTrimBothFunction();

   void setTrimBothFunction(String var1);

   boolean getSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   boolean getSupportsNullTableForGetPrimaryKeys();

   void setSupportsNullTableForGetPrimaryKeys(boolean var1);

   boolean getRequiresCastForMathFunctions();

   void setRequiresCastForMathFunctions(boolean var1);
}
