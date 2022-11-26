package kodo.jdbc.conf.descriptor;

public interface DerbyDictionaryBean extends BuiltInDBDictionaryBean {
   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   String getSubstringFunctionName();

   void setSubstringFunctionName(String var1);

   boolean getUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getStringLengthFunction();

   void setStringLengthFunction(String var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getTrimLeadingFunction();

   void setTrimLeadingFunction(String var1);

   boolean getSupportsDefaultDeleteAction();

   void setSupportsDefaultDeleteAction(boolean var1);

   String getCrossJoinClause();

   void setCrossJoinClause(String var1);

   boolean getRequiresConditionForCrossJoin();

   void setRequiresConditionForCrossJoin(boolean var1);

   boolean getSupportsLockingWithMultipleTables();

   void setSupportsLockingWithMultipleTables(boolean var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   String getToUpperCaseFunction();

   void setToUpperCaseFunction(String var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   boolean getSupportsSelectForUpdate();

   void setSupportsSelectForUpdate(boolean var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getNumericTypeName();

   void setNumericTypeName(String var1);

   String getForUpdateClause();

   void setForUpdateClause(String var1);

   boolean getSupportsLockingWithOrderClause();

   void setSupportsLockingWithOrderClause(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   boolean getShutdownOnClose();

   void setShutdownOnClose(boolean var1);

   boolean getRequiresCastForComparisons();

   void setRequiresCastForComparisons(boolean var1);

   boolean getSupportsLockingWithOuterJoin();

   void setSupportsLockingWithOuterJoin(boolean var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   String getTrimTrailingFunction();

   void setTrimTrailingFunction(String var1);

   boolean getSupportsLockingWithSelectRange();

   void setSupportsLockingWithSelectRange(boolean var1);

   boolean getSupportsLockingWithInnerJoin();

   void setSupportsLockingWithInnerJoin(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   String getVarbinaryTypeName();

   void setVarbinaryTypeName(String var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getTrimBothFunction();

   void setTrimBothFunction(String var1);

   String getToLowerCaseFunction();

   void setToLowerCaseFunction(String var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   boolean getRequiresCastForMathFunctions();

   void setRequiresCastForMathFunctions(boolean var1);

   boolean getRequiresAutoCommitForMetaData();

   void setRequiresAutoCommitForMetaData(boolean var1);
}
