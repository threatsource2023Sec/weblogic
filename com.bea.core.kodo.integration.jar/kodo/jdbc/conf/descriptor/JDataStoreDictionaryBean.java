package kodo.jdbc.conf.descriptor;

public interface JDataStoreDictionaryBean extends BuiltInDBDictionaryBean {
   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   boolean getSupportsQueryTimeout();

   void setSupportsQueryTimeout(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getSearchStringEscape();

   void setSearchStringEscape(String var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   boolean getAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   String getAutoAssignClause();

   void setAutoAssignClause(String var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   boolean getUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getJoinSyntax();

   void setJoinSyntax(String var1);
}
