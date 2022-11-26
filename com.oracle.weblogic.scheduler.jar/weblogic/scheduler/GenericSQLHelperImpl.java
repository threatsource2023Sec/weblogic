package weblogic.scheduler;

class GenericSQLHelperImpl implements SQLHelper {
   protected String TABLE_NAME;
   protected String INSERT_VALUES;
   protected String WHERE_CLAUSE;
   protected String advanceTimerSQL;
   protected String cancelTimerSQL;
   protected String cancelTimersSQL;
   protected String createTimerSQL;
   protected String createTimerSQLWithUserKey;
   protected String getReadyTimersSQL;
   protected String getTimersSQL;
   protected String getTimersLikeIdSQL;
   protected String getTimersByUserKey;
   protected String getTimerStateSQL;
   protected String updateStartTimeSQL;

   GenericSQLHelperImpl(String tableName, String domainName, String clusterName) {
      this.TABLE_NAME = tableName;
      this.INSERT_VALUES = " , '" + domainName + "' , '" + clusterName + "'";
      this.WHERE_CLAUSE = " AND DOMAIN_NAME='" + domainName + "' AND CLUSTER_NAME='" + clusterName + "'";
      this.advanceTimerSQL = "UPDATE " + this.TABLE_NAME + " SET START_TIME = (? + INTERVAL), LISTENER = ? WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
      this.cancelTimerSQL = "DELETE FROM " + this.TABLE_NAME + " WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
      this.cancelTimersSQL = "DELETE FROM " + this.TABLE_NAME + " WHERE TIMER_MANAGER_NAME = ?" + this.WHERE_CLAUSE;
      this.createTimerSQL = "INSERT INTO " + this.TABLE_NAME + this.getColumnNames() + " VALUES ( ? , ? , ? , ? , ?" + this.INSERT_VALUES + " )";
      this.createTimerSQLWithUserKey = "INSERT INTO " + this.TABLE_NAME + this.getColumnNamesWithUserKey() + " VALUES ( ? , ? , ? , ? , ?, ?" + this.INSERT_VALUES + " )";
      this.getReadyTimersSQL = "SELECT TIMER_ID FROM " + this.TABLE_NAME + " WHERE START_TIME < ?" + this.WHERE_CLAUSE;
      this.getTimersSQL = "SELECT TIMER_ID FROM " + this.TABLE_NAME + " WHERE TIMER_MANAGER_NAME = ?" + this.WHERE_CLAUSE;
      this.getTimersLikeIdSQL = "SELECT TIMER_ID FROM " + this.TABLE_NAME + " WHERE TIMER_MANAGER_NAME = ? AND TIMER_ID LIKE ?" + this.WHERE_CLAUSE;
      this.getTimersByUserKey = "SELECT TIMER_ID FROM " + this.TABLE_NAME + " WHERE TIMER_MANAGER_NAME = ? AND USER_KEY = ?" + this.WHERE_CLAUSE;
      this.getTimerStateSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
      this.updateStartTimeSQL = "UPDATE " + this.TABLE_NAME + " SET START_TIME = ? , LISTENER = ? WHERE TIMER_ID = ?" + this.WHERE_CLAUSE;
   }

   protected String getColumnNames() {
      return " ( TIMER_ID, LISTENER, START_TIME, INTERVAL, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }

   protected String getColumnNamesWithUserKey() {
      return " ( TIMER_ID, LISTENER, START_TIME, INTERVAL, USER_KEY, TIMER_MANAGER_NAME, DOMAIN_NAME, CLUSTER_NAME )";
   }

   public String getCancelTimerSQL() {
      return this.cancelTimerSQL;
   }

   public String getAdvanceTimerSQL() {
      return this.advanceTimerSQL;
   }

   public String getUpdateStartTimeSQL() {
      return this.updateStartTimeSQL;
   }

   public String getCreateTimerSQL() {
      return this.createTimerSQL;
   }

   public String getCreateTimerSQLWithUserKey() {
      return this.createTimerSQLWithUserKey;
   }

   public String getTimerStateSQL() {
      return this.getTimerStateSQL;
   }

   public String getReadyTimersSQL() {
      return this.getReadyTimersSQL;
   }

   public String getTimersSQL() {
      return this.getTimersSQL;
   }

   public String getTimersLikeIdSQL() {
      return this.getTimersLikeIdSQL;
   }

   public String getTimersByUserKey() {
      return this.getTimersByUserKey;
   }

   public String getCancelTimersSQL() {
      return this.cancelTimersSQL;
   }
}
