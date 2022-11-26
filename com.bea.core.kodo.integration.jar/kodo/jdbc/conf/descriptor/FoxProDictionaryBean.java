package kodo.jdbc.conf.descriptor;

public interface FoxProDictionaryBean extends BuiltInDBDictionaryBean {
   String getBinaryTypeName();

   void setBinaryTypeName(String var1);

   String getClobTypeName();

   void setClobTypeName(String var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   String getLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   String getVarcharTypeName();

   void setVarcharTypeName(String var1);

   String getDateTypeName();

   void setDateTypeName(String var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   String getDecimalTypeName();

   void setDecimalTypeName(String var1);

   String getSmallintTypeName();

   void setSmallintTypeName(String var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   String getTimeTypeName();

   void setTimeTypeName(String var1);

   String getFloatTypeName();

   void setFloatTypeName(String var1);

   String getNumericTypeName();

   void setNumericTypeName(String var1);

   String getIntegerTypeName();

   void setIntegerTypeName(String var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   boolean getSupportsForeignKeys();

   void setSupportsForeignKeys(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   String getRealTypeName();

   void setRealTypeName(String var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getCharacterColumnSize();

   void setCharacterColumnSize(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getJoinSyntax();

   void setJoinSyntax(String var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);
}
