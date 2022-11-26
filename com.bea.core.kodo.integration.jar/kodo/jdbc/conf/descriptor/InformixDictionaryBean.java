package kodo.jdbc.conf.descriptor;

public interface InformixDictionaryBean extends BuiltInDBDictionaryBean {
   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   boolean getSupportsQueryTimeout();

   void setSupportsQueryTimeout(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   int getLockWaitSeconds();

   void setLockWaitSeconds(int var1);

   String getDateTypeName();

   void setDateTypeName(String var1);

   boolean getSupportsSchemaForGetTables();

   void setSupportsSchemaForGetTables(boolean var1);

   String getCatalogSeparator();

   void setCatalogSeparator(String var1);

   boolean getSupportsLockingWithMultipleTables();

   void setSupportsLockingWithMultipleTables(boolean var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   boolean getLockModeEnabled();

   void setLockModeEnabled(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   String getConstraintNameMode();

   void setConstraintNameMode(String var1);

   String getAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   boolean getSupportsMultipleNontransactionalResultSets();

   void setSupportsMultipleNontransactionalResultSets(boolean var1);

   String getFloatTypeName();

   void setFloatTypeName(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   boolean getSupportsLockingWithOrderClause();

   void setSupportsLockingWithOrderClause(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   boolean getSwapSchemaAndCatalog();

   void setSwapSchemaAndCatalog(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   boolean getSupportsSchemaForGetColumns();

   void setSupportsSchemaForGetColumns(boolean var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);
}
