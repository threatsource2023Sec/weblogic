package kodo.jdbc.conf.descriptor;

public interface MySQLDictionaryBean extends BuiltInDBDictionaryBean {
   boolean getUseClobs();

   void setUseClobs(boolean var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   String getConcatenateFunction();

   void setConcatenateFunction(String var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   String getTableType();

   void setTableType(String var1);

   String getSchemaCase();

   void setSchemaCase(String var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   boolean getSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   String getConstraintNameMode();

   void setConstraintNameMode(String var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   String getDistinctCountColumnSeparator();

   void setDistinctCountColumnSeparator(String var1);

   boolean getSupportsSubselect();

   void setSupportsSubselect(boolean var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   boolean getSupportsMultipleNontransactionalResultSets();

   void setSupportsMultipleNontransactionalResultSets(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   int getMaxIndexesPerTable();

   void setMaxIndexesPerTable(int var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   boolean getSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   boolean getDriverDeserializesBlobs();

   void setDriverDeserializesBlobs(boolean var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);
}
