package weblogic.scheduler;

final class MySQLHelperImpl extends GenericSQLHelperImpl {
   MySQLHelperImpl(String tableName, String domainName, String clusterName) {
      super(tableName, domainName, clusterName);
      this.advanceTimerSQL = "UPDATE " + this.TABLE_NAME + " SET START_TIME = ( ? + SCHEDULE_INTERVAL), LISTENER = ? WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
   }

   protected String getColumnNames() {
      return " ( TIMER_ID, LISTENER, START_TIME, SCHEDULE_INTERVAL, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }

   protected String getColumnNamesWithUserKey() {
      return " ( TIMER_ID, LISTENER, START_TIME, SCHEDULE_INTERVAL, USER_KEY, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }
}
