package org.apache.openjpa.jdbc.sql;

public class CacheDictionary extends DBDictionary {
   public CacheDictionary() {
      this.platform = "Intersystems Cache";
      this.supportsDeferredConstraints = false;
      this.supportsSelectForUpdate = true;
      this.validationSQL = "SET OPTION DUMMY=DUMMY";
      this.bigintTypeName = "NUMERIC";
      this.numericTypeName = "NUMERIC";
      this.clobTypeName = "LONGVARCHAR";
      this.blobTypeName = "LONGVARBINARY";
      this.autoAssignClause = "DEFAULT OBJECTSCRIPT '$INCREMENT(^LogNumber)'";
      this.lastGeneratedKeyQuery = "SELECT MAX({0}) FROM {1}";
   }
}
