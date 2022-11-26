package weblogic.cluster.singleton;

import java.sql.SQLException;

public abstract class QueryHelper {
   public static String TIME_AS_FRACTION_OF_DAY = "";
   static final int RENEW_STATIC_QUERY_NUM_VARIABLES = 10;
   private String TABLE_NAME;
   private String COLUMN_NAMES;
   private String INSERT_VALUES;
   private String WHERE_CLAUSE;
   private String abdicateLeaseQuery;
   private String acquireLeaseQuery;
   private String assumeLeaseQuery;
   private String lockLeaseQuery;
   private String updateLeaseQuery;
   private String leaseOwnerQuery;
   private String previousLeaseOwnerQuery;
   private String renewAllLeasesQuery;
   private String renewLeaseQuery;
   private String renewLeasesQuery;
   private String unresponsiveMigratableServersQuery;
   private String dumpDBTableQuery;
   private int dbType;

   public QueryHelper(String tableName, String domainName, String clusterName, int dbType) {
      this.init(tableName, domainName, clusterName, dbType);
   }

   public QueryHelper() {
   }

   protected void init(String tableName, String domainName, String clusterName, int dbType) {
      this.dbType = dbType;
      this.TABLE_NAME = tableName;
      if (dbType == 1 || dbType == 7) {
         TIME_AS_FRACTION_OF_DAY = "/86400";
      }

      this.INSERT_VALUES = ", '" + domainName + "' , '" + clusterName + "' ";
      this.WHERE_CLAUSE = " AND DOMAINNAME='" + domainName + "' AND CLUSTERNAME='" + clusterName + "'";
      this.COLUMN_NAMES = " ( SERVER, INSTANCE, DOMAINNAME, CLUSTERNAME, TIMEOUT ) ";
      this.abdicateLeaseQuery = "DELETE FROM " + this.TABLE_NAME + " WHERE SERVER = ? AND INSTANCE = ?" + this.WHERE_CLAUSE;
      this.acquireLeaseQuery = "DELETE FROM " + this.TABLE_NAME + " WHERE (" + this.compareDates(this.getTimeFunction(), "TIMEOUT") + ") AND SERVER = ? " + this.WHERE_CLAUSE;
      this.assumeLeaseQuery = "INSERT INTO " + this.TABLE_NAME + this.COLUMN_NAMES + " VALUES ( ? , ? " + this.INSERT_VALUES + ", " + this.addToDate(this.getTimeFunction()) + " )";
      this.lockLeaseQuery = "SELECT INSTANCE FROM " + this.TABLE_NAME + " WHERE (" + this.compareDates(this.getTimeFunction(), "TIMEOUT") + ") AND SERVER = ? " + this.WHERE_CLAUSE + " FOR UPDATE NOWAIT";
      this.updateLeaseQuery = "UPDATE " + this.TABLE_NAME + " SET INSTANCE = ?, TIMEOUT = ( " + this.addToDate(this.getTimeFunction()) + ") WHERE (" + this.compareDates(this.getTimeFunction(), "TIMEOUT") + ") AND SERVER = ? " + this.WHERE_CLAUSE;
      this.leaseOwnerQuery = "SELECT INSTANCE FROM " + this.TABLE_NAME + " WHERE ( " + this.compareDates("TIMEOUT", this.getTimeFunction(), true) + ") AND SERVER = ? " + this.WHERE_CLAUSE;
      this.previousLeaseOwnerQuery = "SELECT INSTANCE FROM " + this.TABLE_NAME + " WHERE SERVER = ? " + this.WHERE_CLAUSE;
      this.renewAllLeasesQuery = "UPDATE " + this.TABLE_NAME + " SET TIMEOUT = ( " + this.addToDate(this.getTimeFunction()) + ") WHERE INSTANCE = ?" + this.WHERE_CLAUSE;
      this.renewLeaseQuery = "UPDATE " + this.TABLE_NAME + " SET TIMEOUT = ( " + this.addToDate(this.getTimeFunction()) + ") WHERE SERVER = ? AND INSTANCE = ? " + this.WHERE_CLAUSE;
      StringBuilder leaseListBuilder = new StringBuilder(" ? ");

      for(int i = 1; i < 10; ++i) {
         leaseListBuilder.append(", ? ");
      }

      this.renewLeasesQuery = "UPDATE " + this.TABLE_NAME + " SET TIMEOUT = ( " + this.addToDate(this.getTimeFunction()) + ") WHERE SERVER IN (" + leaseListBuilder.toString() + ") AND INSTANCE = ?" + this.WHERE_CLAUSE;
      this.unresponsiveMigratableServersQuery = "SELECT SERVER FROM " + this.TABLE_NAME + " WHERE ( " + this.compareDates(this.getTimeFunction(), "(" + this.addToDate("TIMEOUT") + "))") + this.WHERE_CLAUSE;
      this.dumpDBTableQuery = "SELECT * FROM " + this.TABLE_NAME + " WHERE DOMAINNAME='" + domainName + "' AND CLUSTERNAME='" + clusterName + "'";
   }

   protected abstract String addToDate(String var1);

   protected abstract String compareDates(String var1, String var2);

   protected abstract String compareDates(String var1, String var2, boolean var3);

   protected abstract String getTimeFunction();

   protected abstract boolean supportsTimeouts();

   protected abstract boolean isLeaseRowLocked(SQLException var1);

   protected abstract boolean isLeaseRowConstraintViolated(SQLException var1);

   protected abstract boolean supportsRowLockingWithNoWait();

   protected int getDBType() {
      return this.dbType;
   }

   public String getLeaseOwnerQuery() {
      return this.leaseOwnerQuery;
   }

   public String getPreviousLeaseOwnerQuery() {
      return this.previousLeaseOwnerQuery;
   }

   public String getAcquireLeaseQuery() {
      return this.acquireLeaseQuery;
   }

   public String getAssumeLeaseQuery() {
      return this.assumeLeaseQuery;
   }

   public String getLockLeaseQuery() {
      return this.lockLeaseQuery;
   }

   public String getUpdateLeaseQuery() {
      return this.updateLeaseQuery;
   }

   public String getRenewLeaseQuery() {
      return this.renewLeaseQuery;
   }

   public String getRenewLeasesQuery(int numLeases) {
      StringBuilder leaseListBuilder = new StringBuilder(" ? ");

      for(int i = 1; i < numLeases; ++i) {
         leaseListBuilder.append(", ? ");
      }

      return "UPDATE " + this.TABLE_NAME + " SET TIMEOUT = ( " + this.addToDate(this.getTimeFunction()) + ") WHERE SERVER IN (" + leaseListBuilder.toString() + ") AND INSTANCE = ?" + this.WHERE_CLAUSE;
   }

   public String getRenewLeasesQuery() {
      return this.renewLeasesQuery;
   }

   public boolean useStaticRenewLeasesQuery(int numValues) {
      return numValues <= 10;
   }

   public String getRenewAllLeasesQuery() {
      return this.renewAllLeasesQuery;
   }

   public String getAbdicateLeaseQuery() {
      return this.abdicateLeaseQuery;
   }

   public String getUnresponsiveMigratableServersQuery() {
      return this.unresponsiveMigratableServersQuery;
   }

   public String getInsertMachineQuery(String serverName, String hostMachine, String machineTableName) {
      return "INSERT INTO " + machineTableName + " VALUES ('" + serverName + "','" + hostMachine + "'" + this.INSERT_VALUES + ")";
   }

   public String getDeleteMachineQuery(String serverName, String machineTableName) {
      return "DELETE FROM " + machineTableName + " WHERE SERVER = '" + serverName + "'" + this.WHERE_CLAUSE;
   }

   public String getRetrieveMachineQuery(String serverName, String machineTableName) {
      return "SELECT HOSTMACHINE FROM " + machineTableName + " WHERE SERVER='" + serverName + "'" + this.WHERE_CLAUSE;
   }

   public String getDumpDBTableQuery() {
      return this.dumpDBTableQuery;
   }
}
