package weblogic.cluster.singleton;

import java.sql.SQLException;

public class QueryHelperImpl extends QueryHelper {
   static final boolean DISABLE_ROW_LOCKING = Boolean.getBoolean("weblogic.dbleasing.disableRowLocking");

   public QueryHelperImpl(String tableName, String domainName, String clusterName, int dbtype) {
      super(tableName, domainName, clusterName, dbtype);
   }

   protected String addToDate(String column) {
      switch (this.getDBType()) {
         case 1:
         case 7:
            return "(" + column + " + ( ? " + TIME_AS_FRACTION_OF_DAY + "))";
         case 2:
         case 8:
         case 10:
         case 11:
         default:
            throw new AssertionError("Unsupported database driver");
         case 3:
         case 4:
            return "DATEADD(second, ? ," + column + ")";
         case 5:
            return column + " + ? UNITS SECOND";
         case 6:
            return column + " + ? SECONDS";
         case 9:
            return "ADDDATE(" + column + ", INTERVAL ? SECOND)";
         case 12:
            return "{fn TIMESTAMPADD(SQL_TSI_SECOND, ?, " + column + ")}";
      }
   }

   protected String compareDates(String date1, String date2) {
      return this.compareDates(date1, date2, false);
   }

   protected String compareDates(String date1, String date2, boolean includeEquality) {
      switch (this.getDBType()) {
         case 1:
         case 5:
         case 6:
         case 7:
         case 9:
            return includeEquality ? date1 + " >= " + date2 : date1 + " > " + date2;
         case 2:
         case 8:
         case 10:
         case 11:
         default:
            throw new AssertionError("Unsupported database driver");
         case 3:
         case 4:
            return includeEquality ? "DATEDIFF(second," + date1 + "," + date2 + ") <= 0" : "DATEDIFF(second," + date1 + "," + date2 + ") < 0";
         case 12:
            return includeEquality ? "{fn TIMESTAMPDIFF(SQL_TSI_SECOND, " + date1 + "," + date2 + ")} <= 0" : "{fn TIMESTAMPDIFF(SQL_TSI_SECOND, " + date1 + "," + date2 + ")} < 0";
      }
   }

   protected String getTimeFunction() {
      switch (this.getDBType()) {
         case 1:
            if (MigratableServerService.theOne() == null) {
               return "CURRENT_DATE";
            } else {
               if (MigratableServerService.theOne().isBEADriver()) {
                  return "CURRENT_DATE";
               }

               return "SYS_EXTRACT_UTC(SYSTIMESTAMP)";
            }
         case 2:
         case 8:
         case 10:
         case 11:
         default:
            throw new AssertionError("Unsupported database driver");
         case 3:
            return "GETDATE()";
         case 4:
            return "GETDATE()";
         case 5:
            return "(CURRENT YEAR TO SECOND)";
         case 6:
         case 12:
            return "CURRENT TIMESTAMP";
         case 7:
            return "SYSDATE";
         case 9:
            return "NOW()";
      }
   }

   protected boolean supportsTimeouts() {
      return this.getDBType() != 4 && this.getDBType() != 5;
   }

   protected boolean isLeaseRowLocked(SQLException sqlexception) {
      if (sqlexception instanceof SQLException && 1 == this.getDBType()) {
         return sqlexception.getErrorCode() == 54;
      } else {
         return false;
      }
   }

   protected boolean isLeaseRowConstraintViolated(SQLException sqlexception) {
      if (sqlexception instanceof SQLException && 1 == this.getDBType()) {
         return sqlexception.getErrorCode() == 1;
      } else {
         return false;
      }
   }

   protected boolean supportsRowLockingWithNoWait() {
      boolean result = 1 == this.getDBType();
      if (DISABLE_ROW_LOCKING) {
         result = false;
      }

      return result;
   }
}
