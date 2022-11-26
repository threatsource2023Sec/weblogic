package kodo.jdbc.conf.descriptor;

public interface EmpressDictionaryBean extends BuiltInDBDictionaryBean {
   String getClobTypeName();

   void setClobTypeName(String var1);

   boolean getUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   int getMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   String getTimestampTypeName();

   void setTimestampTypeName(String var1);

   int getMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   String getDoubleTypeName();

   void setDoubleTypeName(String var1);

   boolean getUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   String getBitTypeName();

   void setBitTypeName(String var1);

   String getToUpperCaseFunction();

   void setToUpperCaseFunction(String var1);

   boolean getAllowConcurrentRead();

   void setAllowConcurrentRead(boolean var1);

   String getValidationSQL();

   void setValidationSQL(String var1);

   boolean getUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   String getBlobTypeName();

   void setBlobTypeName(String var1);

   boolean getUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   String getPlatform();

   void setPlatform(String var1);

   String getBigintTypeName();

   void setBigintTypeName(String var1);

   String getRealTypeName();

   void setRealTypeName(String var1);

   boolean getRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   String getSchemaCase();

   void setSchemaCase(String var1);

   int getMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   int getMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   String getJoinSyntax();

   void setJoinSyntax(String var1);

   String getToLowerCaseFunction();

   void setToLowerCaseFunction(String var1);

   String getTinyintTypeName();

   void setTinyintTypeName(String var1);

   boolean getSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);
}
