package kodo.jdbc.conf.descriptor;

public interface BuiltInDBDictionaryBean extends DBDictionaryBean {
   String getCharTypeName();

   void setCharTypeName(String var1);

   String getOuterJoinClause();

   void setOuterJoinClause(String var1);

   String getBinaryTypeName();

   void setBinaryTypeName(String var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   boolean getSimulateLocking();

   void setSimulateLocking(boolean var1);

   String getSystemTables();

   void setSystemTables(String var1);

   String getConcatenateFunction();

   void setConcatenateFunction(String var1);

   String getSubstringFunctionName();

   void setSubstringFunctionName(String var1);

   boolean getSupportsQueryTimeout();

   void setSupportsQueryTimeout(boolean var1);

   boolean getUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getSearchStringEscape();

   void setSearchStringEscape(String var1);

   boolean getSupportsCascadeUpdateAction();

   void setSupportsCascadeUpdateAction(boolean var1);

   String getStringLengthFunction();

   void setStringLengthFunction(String var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   boolean getSupportsUniqueConstraints();

   void setSupportsUniqueConstraints(boolean var1);

   boolean getSupportsRestrictDeleteAction();

   void setSupportsRestrictDeleteAction(boolean var1);

   String getTrimLeadingFunction();

   void setTrimLeadingFunction(String var1);

   boolean getSupportsDefaultDeleteAction();

   void setSupportsDefaultDeleteAction(boolean var1);

   String getNextSequenceQuery();

   void setNextSequenceQuery(String var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   String getCrossJoinClause();

   void setCrossJoinClause(String var1);

   int getMaxEmbeddedClobSize();

   void setMaxEmbeddedClobSize(int var1);

   String getDateTypeName();

   void setDateTypeName(String var1);

   boolean getSupportsSchemaForGetTables();

   void setSupportsSchemaForGetTables(boolean var1);

   boolean getSupportsAlterTableWithDropColumn();

   void setSupportsAlterTableWithDropColumn(boolean var1);

   String getCurrentTimeFunction();

   void setCurrentTimeFunction(String var1);

   boolean getRequiresConditionForCrossJoin();

   void setRequiresConditionForCrossJoin(boolean var1);

   String getRefTypeName();

   void setRefTypeName(String var1);

   String getConcatenateDelimiter();

   void setConcatenateDelimiter(String var1);

   String getCatalogSeparator();

   void setCatalogSeparator(String var1);

   boolean getSupportsModOperator();

   void setSupportsModOperator(boolean var1);

   String getSchemaCase();

   void setSchemaCase(String var1);

   String getJavaObjectTypeName();

   void setJavaObjectTypeName(String var1);

   String getDriverVendor();

   void setDriverVendor(String var1);

   boolean getSupportsLockingWithMultipleTables();

   void setSupportsLockingWithMultipleTables(boolean var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   String getDecimalTypeName();

   void setDecimalTypeName(String var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   int getDatePrecision();

   void setDatePrecision(int var1);

   boolean getSupportsAlterTableWithAddColumn();

   void setSupportsAlterTableWithAddColumn(boolean var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   boolean getSupportsNullTableForGetColumns();

   void setSupportsNullTableForGetColumns(boolean var1);

   String getToUpperCaseFunction();

   void setToUpperCaseFunction(String var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getStoreLargeNumbersAsStrings();

   void setStoreLargeNumbersAsStrings(boolean var1);

   String getConstraintNameMode();

   void setConstraintNameMode(String var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   boolean getSupportsSelectForUpdate();

   void setSupportsSelectForUpdate(boolean var1);

   String getDistinctCountColumnSeparator();

   void setDistinctCountColumnSeparator(String var1);

   boolean getSupportsSubselect();

   void setSupportsSubselect(boolean var1);

   String getTimeTypeName();

   void setTimeTypeName(String var1);

   String getAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   boolean getUseGetObjectForBlobs();

   void setUseGetObjectForBlobs(boolean var1);

   int getMaxAutoAssignNameLength();

   void setMaxAutoAssignNameLength(int var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   String getStructTypeName();

   void setStructTypeName(String var1);

   String getVarcharTypeName();

   void setVarcharTypeName(String var1);

   int getRangePosition();

   void setRangePosition(int var1);

   boolean getSupportsRestrictUpdateAction();

   void setSupportsRestrictUpdateAction(boolean var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   boolean getSupportsMultipleNontransactionalResultSets();

   void setSupportsMultipleNontransactionalResultSets(boolean var1);

   String getBitLengthFunction();

   void setBitLengthFunction(String var1);

   boolean getCreatePrimaryKeys();

   void setCreatePrimaryKeys(boolean var1);

   String getNullTypeName();

   void setNullTypeName(String var1);

   String getFloatTypeName();

   void setFloatTypeName(String var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getTableTypes();

   void setTableTypes(String var1);

   String getNumericTypeName();

   void setNumericTypeName(String var1);

   String getTableForUpdateClause();

   void setTableForUpdateClause(String var1);

   String getIntegerTypeName();

   void setIntegerTypeName(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   String getForUpdateClause();

   void setForUpdateClause(String var1);

   String getBooleanTypeName();

   void setBooleanTypeName(String var1);

   boolean getUseGetBestRowIdentifierForPrimaryKeys();

   void setUseGetBestRowIdentifierForPrimaryKeys(boolean var1);

   boolean getSupportsForeignKeys();

   void setSupportsForeignKeys(boolean var1);

   String getDropTableSQL();

   void setDropTableSQL(String var1);

   boolean getUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   boolean getSupportsLockingWithOrderClause();

   void setSupportsLockingWithOrderClause(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getFixedSizeTypeNames();

   void setFixedSizeTypeNames(String var1);

   boolean getStoreCharsAsNumbers();

   void setStoreCharsAsNumbers(boolean var1);

   int getMaxIndexesPerTable();

   void setMaxIndexesPerTable(int var1);

   boolean getRequiresCastForComparisons();

   void setRequiresCastForComparisons(boolean var1);

   boolean getSupportsHaving();

   void setSupportsHaving(boolean var1);

   boolean getSupportsLockingWithOuterJoin();

   void setSupportsLockingWithOuterJoin(boolean var1);

   boolean getSupportsCorrelatedSubselect();

   void setSupportsCorrelatedSubselect(boolean var1);

   boolean getSupportsNullTableForGetImportedKeys();

   void setSupportsNullTableForGetImportedKeys(boolean var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   String getReservedWords();

   void setReservedWords(String var1);

   boolean getSupportsNullUpdateAction();

   void setSupportsNullUpdateAction(boolean var1);

   boolean getUseSchemaName();

   void setUseSchemaName(boolean var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   String getRealTypeName();

   void setRealTypeName(String var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   boolean getSupportsNullTableForGetIndexInfo();

   void setSupportsNullTableForGetIndexInfo(boolean var1);

   String getTrimTrailingFunction();

   void setTrimTrailingFunction(String var1);

   boolean getSupportsLockingWithSelectRange();

   void setSupportsLockingWithSelectRange(boolean var1);

   boolean getStorageLimitationsFatal();

   void setStorageLimitationsFatal(boolean var1);

   boolean getSupportsLockingWithInnerJoin();

   void setSupportsLockingWithInnerJoin(boolean var1);

   String getCurrentTimestampFunction();

   void setCurrentTimestampFunction(String var1);

   String getCastFunction();

   void setCastFunction(String var1);

   String getOtherTypeName();

   void setOtherTypeName(String var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   String getDistinctTypeName();

   void setDistinctTypeName(String var1);

   int getCharacterColumnSize();

   void setCharacterColumnSize(int var1);

   String getVarbinaryTypeName();

   void setVarbinaryTypeName(String var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getClosePoolSQL();

   void setClosePoolSQL(String var1);

   String getCurrentDateFunction();

   void setCurrentDateFunction(String var1);

   String getJoinSyntax();

   void setJoinSyntax(String var1);

   int getMaxEmbeddedBlobSize();

   void setMaxEmbeddedBlobSize(int var1);

   String getTrimBothFunction();

   void setTrimBothFunction(String var1);

   boolean getSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   String getToLowerCaseFunction();

   void setToLowerCaseFunction(String var1);

   String getArrayTypeName();

   void setArrayTypeName(String var1);

   String getInnerJoinClause();

   void setInnerJoinClause(String var1);

   boolean getSupportsDefaultUpdateAction();

   void setSupportsDefaultUpdateAction(boolean var1);

   boolean getSupportsSchemaForGetColumns();

   void setSupportsSchemaForGetColumns(boolean var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   boolean getSupportsNullTableForGetPrimaryKeys();

   void setSupportsNullTableForGetPrimaryKeys(boolean var1);

   String getSystemSchemas();

   void setSystemSchemas(String var1);

   boolean getRequiresCastForMathFunctions();

   void setRequiresCastForMathFunctions(boolean var1);

   boolean getSupportsNullDeleteAction();

   void setSupportsNullDeleteAction(boolean var1);

   boolean getRequiresAutoCommitForMetaData();

   void setRequiresAutoCommitForMetaData(boolean var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);

   String getInitializationSQL();

   void setInitializationSQL(String var1);

   boolean getSupportsCascadeDeleteAction();

   void setSupportsCascadeDeleteAction(boolean var1);

   boolean getSupportsTimestampNanos();

   void setSupportsTimestampNanos(boolean var1);
}
