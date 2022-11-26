package weblogic.scheduler;

final class OracleSQLHelperImpl extends GenericSQLHelperImpl {
   protected String selectForInsertSQL;

   OracleSQLHelperImpl(String tableName, String domainName, String clusterName) {
      super(tableName, domainName, clusterName);
      this.selectForInsertSQL = "SELECT listener FROM " + this.TABLE_NAME + " WHERE timer_id = ?" + this.WHERE_CLAUSE + " FOR UPDATE";
   }

   public String getSelectForInsertSQL() {
      return this.selectForInsertSQL;
   }
}
