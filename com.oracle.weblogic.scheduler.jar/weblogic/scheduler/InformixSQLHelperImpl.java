package weblogic.scheduler;

final class InformixSQLHelperImpl extends GenericSQLHelperImpl {
   InformixSQLHelperImpl(String tableName, String domainName, String clusterName) {
      super(tableName, domainName, clusterName);
      this.advanceTimerSQL = "UPDATE " + this.TABLE_NAME + " SET START_TIME = ( ? + " + this.TABLE_NAME + ".INTERVAL), LISTENER = ? WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
   }

   protected String getColumnNames() {
      return " ( TIMER_ID, LISTENER, START_TIME, " + this.TABLE_NAME + ".INTERVAL, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }

   protected String getColumnNamesWithUserKey() {
      return " ( TIMER_ID, LISTENER, START_TIME, " + this.TABLE_NAME + ".INTERVAL, USER_KEY, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }
}
