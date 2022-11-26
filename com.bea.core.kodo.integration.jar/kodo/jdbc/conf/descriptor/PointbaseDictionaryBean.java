package kodo.jdbc.conf.descriptor;

public interface PointbaseDictionaryBean extends BuiltInDBDictionaryBean {
   String getCharTypeName();

   void setCharTypeName(String var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   boolean getSupportsLockingWithMultipleTables();

   void setSupportsLockingWithMultipleTables(boolean var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   boolean getSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   String getAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   boolean getSupportsMultipleNontransactionalResultSets();

   void setSupportsMultipleNontransactionalResultSets(boolean var1);

   String getIntegerTypeName();

   void setIntegerTypeName(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   String getPlatform();

   void setPlatform(String var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   String getLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   String getRealTypeName();

   void setRealTypeName(String var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   String getFloatTypeName();

   void setFloatTypeName(String var1);
}
