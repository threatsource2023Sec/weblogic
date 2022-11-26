package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeSet;
import javax.transaction.xa.Xid;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.WLConnection;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.XIDFactory;

final class JTSLoggableResourceTable implements NakedTimerListener {
   private static final String TABLE_PREFIX = "WL_LLR_";
   private static final String XID_COL_NAME = "XIDSTR";
   private static final String POOL_COL_NAME = "POOLNAMESTR";
   private static final String DATA_COL_NAME = "RECORDSTR";
   private static final String VERSION_ROW = "JDBC LLR Version";
   private static final String VERSION_VALUE = "1.0";
   private static final String OWNER_ROW = "JDBC LLR Domain//Server";
   private static final String TEST_ROW = "JDBC LLR Test";
   private static final String TEST_ROW2 = "JDBC LLR Test 2";
   private static int DELETE_DELAY_MILLIS = 60000;
   private static final int TIMER_INTERVAL_MILLIS = 5000;
   private static final int MAX_BATCH_SIZE = 300;
   private static final int PIGGY_BACKING_THRESHOLD = 900;
   private static final int MAX_PIGGY_BACKING_BATCH_SIZE = 50;
   private static final int TIMER_TIMEOUT_MILLS = 30000;
   private boolean isDeleteBatchSupported = false;
   private String poolName;
   private String tableRef;
   private String dmlName;
   private String ddlName;
   private String domain;
   private String server;
   private String createSQL;
   private String insertSQL;
   private String deleteSQL;
   private String recoverSQL;
   private String sizeSQL;
   private String getSQL;
   private int dataColumnLength = 0;
   private int dbmsType;
   private CompletedXARecordSet completedXids = new CompletedXARecordSet();
   private Timer pingTimer;
   private TimerManager timerManager;
   private boolean isADMDDL;

   JTSLoggableResourceTable(java.sql.Connection conn, String poolName, String migratedServerName) throws SQLException {
      this.poolName = poolName;
      this.init(conn, migratedServerName);
   }

   JTSLoggableResourceTable(java.sql.Connection conn, String poolName) throws SQLException {
      this.poolName = poolName;
      String serverName = JDBCHelper.getHelper().getServerName();
      this.init(conn, serverName);
   }

   private void init(java.sql.Connection conn, String serverName) throws SQLException {
      String tableProperty = "weblogic.llr.table." + this.poolName;
      String table = System.getProperty(tableProperty);
      if (table != null) {
         this.tableRef = table;
         JDBCLogger.logLLRTablePropertyInfo(this.poolName, table);
      } else {
         this.tableRef = JDBCHelper.getHelper().getJDBCLLRTableName(serverName);
      }

      this.domain = JDBCHelper.getHelper().getDomainName();
      this.server = serverName;
      boolean useDefaultSchema = false;
      if (this.tableRef == null) {
         this.tableRef = ("WL_LLR_" + this.server).toUpperCase();
         this.tableRef = this.tableRef.replaceAll("[^a-zA-Z_0-9]", "_");
         useDefaultSchema = true;
      }

      this.isADMDDL = JDBCHelper.getHelper().isUseFusionForLLR(serverName);
      if (this.isADMDDL) {
         this.tableRef = "WLS_" + this.tableRef + "_DYD";
      }

      java.sql.DatabaseMetaData metaData = null;
      metaData = this.getMetaData(conn);
      String[] trace = new String[1];
      this.dbmsType = weblogic.jdbc.JDBCHelper.getDBMSType(metaData, trace);
      if (useDefaultSchema && this.dbmsType == 1) {
         this.tableRef = metaData.getUserName().toUpperCase() + "." + this.tableRef;
      }

      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         this.trace((Xid)null, "tableRef=" + this.tableRef + " " + trace[0]);
      }

      this.dmlName = weblogic.jdbc.JDBCHelper.getDMLIdentifier(metaData, this.tableRef, this.dbmsType);
      this.ddlName = weblogic.jdbc.JDBCHelper.getDDLIdentifier(metaData, this.tableRef, this.dbmsType);
      if (this.dbmsType == 6) {
         this.ddlName = weblogic.jdbc.JDBCHelper.getDb2LLRTableName(conn, metaData, this.tableRef);
         this.dmlName = this.ddlName;
      }

      int xidSize = JDBCHelper.getHelper().getJDBCLLRTableXIDColumnSize(serverName);
      int poolSize = JDBCHelper.getHelper().getJDBCLLRTablePoolColumnSize(serverName);
      int recSize = JDBCHelper.getHelper().getJDBCLLRTableRecordColumnSize(serverName);
      String varchar;
      if (this.dbmsType == 4) {
         varchar = "NVARCHAR";
      } else if (this.dbmsType == 5) {
         varchar = "LVARCHAR";
      } else {
         varchar = "VARCHAR";
      }

      this.createSQL = "CREATE TABLE " + this.ddlName + " (" + "XIDSTR" + " " + varchar + "(" + xidSize + ") NOT NULL PRIMARY KEY, " + "POOLNAMESTR" + " " + varchar + "(" + poolSize + "), " + "RECORDSTR" + " " + varchar + "(" + recSize + "))";
      if (this.isADMDDL) {
         String sqlWithQuotes = "'" + this.createSQL + "'";
         this.createSQL = "{ call ADM_DDL.DO_DDL(" + sqlWithQuotes + ") }";
         JDBCLogger.logCreateSQLADMDDLInfo(this.createSQL);
      }

      this.insertSQL = "INSERT INTO " + this.dmlName + " (" + "XIDSTR" + "," + "POOLNAMESTR" + "," + "RECORDSTR" + ") VALUES (?,?,?)";
      this.deleteSQL = "DELETE FROM " + this.dmlName + " WHERE " + "XIDSTR" + "=?";
      this.recoverSQL = "SELECT XIDSTR,RECORDSTR FROM " + this.dmlName + " WHERE " + "POOLNAMESTR" + "='" + this.poolName + "'";
      this.getSQL = "SELECT RECORDSTR FROM " + this.dmlName + " WHERE " + "XIDSTR" + "=?";
      this.sizeSQL = "SELECT COUNT(*) FROM " + this.dmlName;
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      this.pingTimer = this.timerManager.schedule(this, 5000L);
   }

   private java.sql.DatabaseMetaData getMetaData(java.sql.Connection conn) throws SQLException, SQLRuntimeException {
      try {
         return conn.getMetaData();
      } catch (RuntimeException var3) {
         throw new SQLRuntimeException(var3);
      }
   }

   private void createTable(java.sql.Connection conn) throws SQLException {
      java.sql.Statement stmt = null;

      try {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace((Xid)null, "creating LLR table sql <<" + this.createSQL + ">>");
         }

         JDBCLogger.logCreatingLLRTable(this.poolName, this.createSQL);
         stmt = conn.createStatement();
         stmt.executeUpdate(this.createSQL);
      } catch (RuntimeException var7) {
         throw new SQLRuntimeException(var7);
      } finally {
         weblogic.jdbc.JDBCHelper.close(stmt);
      }

   }

   private SQLException newVerifyException(String row, String msg, Throwable cause) throws SQLException {
      if (row != null) {
         row = "row '" + row + "' record ";
      } else {
         row = "";
      }

      SQLException wrapped = new SQLException("JDBC LLR, table verify failed for table '" + this.dmlName + "', " + row + msg);
      if (cause != null) {
         wrapped.initCause(cause);
      }

      return wrapped;
   }

   void findOrCreateTable(java.sql.Connection conn) throws SQLException {
      if (!conn.getAutoCommit()) {
         throw new AssertionError();
      } else {
         boolean tableExists = false;

         try {
            tableExists = this.isADMDDL ? this.existsForADMDDL(conn) : weblogic.jdbc.JDBCHelper.tableExists(conn, conn.getMetaData(), this.tableRef);
         } catch (SQLException var15) {
            throw this.newVerifyException((String)null, "could not determine if table exists", var15);
         }

         try {
            if (!tableExists) {
               this.createTable(conn);
            }
         } catch (SQLException var16) {
            throw this.newVerifyException((String)null, "failed to create table", var16);
         }

         String testVal;
         String testVal2;
         try {
            this.deleteRow(conn, "JDBC LLR Test");
            this.deleteRow(conn, "JDBC LLR Test 2");
            this.insertRow(conn, "JDBC LLR Test", "JDBC LLR Test", "JDBC LLR Test");
            this.insertRow(conn, "JDBC LLR Test 2", "JDBC LLR Test 2", "JDBC LLR Test 2");
            testVal = this.getRow(conn, "JDBC LLR Test");
            testVal2 = this.getRow(conn, "JDBC LLR Test 2");
            this.detectAndDeleteRowsInBatch(conn, "JDBC LLR Test", "JDBC LLR Test 2");
         } catch (SQLException var14) {
            throw this.newVerifyException((String)null, "access failed", var14);
         }

         if (testVal != null && testVal.equals("JDBC LLR Test")) {
            if (testVal2 != null && testVal2.equals("JDBC LLR Test 2")) {
               String expectedOwner = this.domain + "//" + this.server;

               String owner;
               try {
                  owner = this.getRow(conn, "JDBC LLR Domain//Server");
                  if (owner == null) {
                     this.insertRow(conn, "JDBC LLR Domain//Server", "JDBC LLR Domain//Server", expectedOwner);
                     owner = this.getRow(conn, "JDBC LLR Domain//Server");
                  }
               } catch (SQLException var13) {
                  throw this.newVerifyException("JDBC LLR Domain//Server", "access failed", var13);
               }

               if (owner != null && owner.equals(expectedOwner)) {
                  String version;
                  try {
                     version = this.getRow(conn, "JDBC LLR Version");
                     if (version == null) {
                        this.insertRow(conn, "JDBC LLR Version", "JDBC LLR Version", "1.0");
                        version = this.getRow(conn, "JDBC LLR Version");
                     }
                  } catch (SQLException var12) {
                     throw this.newVerifyException("JDBC LLR Version", "access failed", var12);
                  }

                  if (version == null) {
                     throw this.newVerifyException("JDBC LLR Version", "had unexpected null value", (Throwable)null);
                  } else {
                     float expectedVersionF;
                     float versionF;
                     try {
                        expectedVersionF = Float.parseFloat("1.0");
                        versionF = Float.parseFloat(version);
                     } catch (NumberFormatException var11) {
                        throw this.newVerifyException("JDBC LLR Version", "failed to parse", var11);
                     }

                     if (versionF > expectedVersionF) {
                        throw this.newVerifyException("JDBC LLR Version", "This server only works with LLR table versions 1.0 and below but the the current LLR table is at version " + version + ".", (Throwable)null);
                     } else {
                        if (this.dbmsType == 3 || this.dbmsType == 5) {
                           this.detectDataColumnlength(conn, "JDBC LLR Version");
                        }

                     }
                  }
               } else {
                  throw this.newVerifyException("JDBC LLR Domain//Server", "had unexpected value '" + owner + "' expected '" + expectedOwner + "'*** ONLY the original domain and server that creates an LLR table may access it ***", (Throwable)null);
               }
            } else {
               throw this.newVerifyException("JDBC LLR Test 2", "had unexpected value '" + testVal2 + "' expected '" + "JDBC LLR Test 2" + "'", (Throwable)null);
            }
         } else {
            throw this.newVerifyException("JDBC LLR Test", "had unexpected value '" + testVal + "' expected '" + "JDBC LLR Test" + "'", (Throwable)null);
         }
      }
   }

   private boolean existsForADMDDL(java.sql.Connection conn) throws SQLException {
      boolean returnValue = false;

      try {
         this.getRowCount(conn);
         returnValue = true;
      } catch (SQLException var8) {
         if (var8 instanceof SQLRuntimeException) {
            JDBCLogger.logRTEADMDDLError(this.poolName, this.ddlName, var8.toString());
            throw var8;
         }

         JDBCLogger.logNoExistsForADMDDLInfo(this.poolName, this.ddlName);
      } catch (RuntimeException var9) {
         JDBCLogger.logRTEADMDDLError(this.poolName, this.ddlName, var9.toString());
         throw var9;
      } finally {
         weblogic.jdbc.JDBCHelper.close(conn);
      }

      JDBCLogger.logLeavingExistsForADMDDLInfo(returnValue);
      return returnValue;
   }

   int getRowCount(java.sql.Connection conn) throws SQLException {
      java.sql.Statement st = null;

      try {
         st = conn.createStatement();
         java.sql.ResultSet results = st.executeQuery(this.sizeSQL);
         if (results.next()) {
            int var4 = results.getInt(1);
            return var4;
         }
      } catch (RuntimeException var8) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            JdbcDebug.log((String)"JTSLoggableResourceTable.getRowCount catch RuntimeException.", (Throwable)var8);
         }

         throw new SQLRuntimeException(var8);
      } finally {
         weblogic.jdbc.JDBCHelper.close(st);
      }

      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         JdbcDebug.log("JTSLoggableResourceTable.getRowCount catch count failed.", new Throwable());
      }

      throw new SQLException("count failed");
   }

   int getRowCount() throws SQLException {
      java.sql.Connection conn = null;

      int var2;
      try {
         conn = JTSLoggableResourceImpl.getNewJTSConnection(this.poolName);
         conn.setAutoCommit(true);
         var2 = this.getRowCount(conn);
      } catch (RuntimeException var6) {
         throw new SQLRuntimeException(var6);
      } finally {
         weblogic.jdbc.JDBCHelper.close(conn);
      }

      return var2;
   }

   int setMaxDeleteIntervalMillis(int millis) {
      if (millis < 0) {
         throw new AssertionError();
      } else {
         int prev = DELETE_DELAY_MILLIS;
         DELETE_DELAY_MILLIS = millis;
         return prev;
      }
   }

   private void detectDataColumnlength(java.sql.Connection conn, String xidStr) throws SQLException {
      java.sql.PreparedStatement ps = null;
      java.sql.ResultSet results = null;

      try {
         ps = conn.prepareStatement(this.getSQL);
         ps.setString(1, xidStr);
         results = ps.executeQuery();
         this.dataColumnLength = results.getMetaData().getPrecision(1);
      } catch (RuntimeException var9) {
         this.dataColumnLength = 0;
      } finally {
         weblogic.jdbc.JDBCHelper.close(results);
         weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
      }

   }

   String getRow(java.sql.Connection conn, String xidStr) throws SQLException {
      java.sql.PreparedStatement ps = null;
      java.sql.ResultSet results = null;

      String var5;
      try {
         ps = conn.prepareStatement(this.getSQL);
         ps.setString(1, xidStr);
         results = ps.executeQuery();
         if (!results.next()) {
            return null;
         }

         var5 = results.getString(1);
      } catch (RuntimeException var9) {
         throw new SQLRuntimeException(var9);
      } finally {
         weblogic.jdbc.JDBCHelper.close(results);
         weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
      }

      return var5;
   }

   void insertRow(java.sql.Connection conn, String xidStr, String poolStr, String recStr) throws SQLException {
      if (this.dataColumnLength > 0 && recStr.length() > this.dataColumnLength) {
         throw new SQLException("The length of LLR record is " + recStr.length() + ". It exceeds column " + "RECORDSTR" + "'s length [" + this.dataColumnLength + "] of LLR table " + this.dmlName + ". Please contact administrator for how to increase the column length.");
      } else {
         java.sql.PreparedStatement ps = null;

         try {
            ps = conn.prepareStatement(this.insertSQL);
            ps.setString(1, xidStr);
            ps.setString(2, poolStr);
            ps.setString(3, recStr);
            ps.executeUpdate();
         } catch (RuntimeException var10) {
            throw new SQLRuntimeException(var10);
         } finally {
            weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
         }

      }
   }

   private void deleteRow(java.sql.Connection conn, String xidStr) throws SQLException {
      java.sql.PreparedStatement ps = null;

      try {
         ps = conn.prepareStatement(this.deleteSQL);
         ps.setString(1, xidStr);
         ps.executeUpdate();
      } catch (RuntimeException var8) {
         throw new SQLRuntimeException(var8);
      } finally {
         weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
      }

   }

   private void deleteRowsInBatch(java.sql.Connection conn, String xidStr, String xidStr2) throws SQLException {
      boolean originalMode = conn.getAutoCommit();
      java.sql.PreparedStatement ps = null;

      try {
         conn.setAutoCommit(false);
         ps = conn.prepareStatement(this.deleteSQL);
         ps.setString(1, xidStr);
         ps.addBatch();
         ps.setString(1, xidStr2);
         ps.addBatch();
         ps.executeBatch();
         conn.commit();
      } catch (RuntimeException var10) {
         throw new SQLRuntimeException(var10);
      } finally {
         weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
         conn.setAutoCommit(originalMode);
      }

   }

   private void detectAndDeleteRowsInBatch(java.sql.Connection conn, String xidStr, String xidStr2) throws SQLException {
      if (this.getMetaData(conn).supportsBatchUpdates()) {
         try {
            this.deleteRowsInBatch(conn, xidStr, xidStr2);
            this.isDeleteBatchSupported = true;
            return;
         } catch (Throwable var5) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               JdbcDebug.log("LLR resource " + this.poolName + " does not support delete batch.", var5);
            }
         }
      }

      this.deleteRow(conn, xidStr);
      this.deleteRow(conn, xidStr2);
   }

   void addCompleted(Xid xid) {
      this.completedXids.add(new CompletedXARecord(xid));
   }

   void addCompleted(CompletedXARecord first) {
      this.completedXids.add(first);
   }

   HashMap recover(java.sql.Connection conn) throws SQLException {
      HashMap ret = new HashMap();
      java.sql.Statement st = null;
      java.sql.ResultSet results = null;
      JTSConnection localJTSConn = null;

      try {
         st = conn.createStatement();
         results = st.executeQuery(this.recoverSQL);

         while(results.next()) {
            String xid = results.getString(1);
            String rec = results.getString(2);
            ret.put(xid, rec);
         }

         JDBCLogger.logLoadedLLRTable(this.poolName, ret.size(), this.dmlName);
         HashMap var13 = ret;
         return var13;
      } catch (RuntimeException var11) {
         throw new SQLRuntimeException(var11);
      } finally {
         weblogic.jdbc.JDBCHelper.close(results);
         weblogic.jdbc.JDBCHelper.close(st);
      }
   }

   public void timerExpired(Timer timer) {
      if (DELETE_DELAY_MILLIS == 0) {
         this.pingTimer = this.timerManager.schedule(this, 1000L);
      } else {
         boolean timedout = this.deleteCompletedOlderThanOrMoreThan((long)DELETE_DELAY_MILLIS, 300, 30000);
         if (timedout) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               JdbcDebug.log("Timer thread has executed 30 seconds, and will schedule another run immediately to process remaining records.");
            }

            this.pingTimer = this.timerManager.schedule(this, 1L);
         } else {
            this.pingTimer = this.timerManager.schedule(this, 5000L);
         }

      }
   }

   private void delete(java.sql.Connection conn, CompletedXARecord first, int batchSize) throws SQLException {
      if (first != null) {
         java.sql.PreparedStatement ps = null;

         try {
            int count = 0;
            ps = conn.prepareStatement(this.deleteSQL);

            for(CompletedXARecord rec = first; rec != null; rec = rec.next) {
               if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
                  this.trace(rec.xid, "deleting XA record, timestamp=" + rec.timestamp);
               }

               ps.setString(1, rec.xidStr);
               if (this.isDeleteBatchSupported) {
                  ps.addBatch();
                  ++count;
                  if (count == batchSize) {
                     ps.executeBatch();
                     count = 0;
                  }
               } else {
                  ps.executeUpdate();
               }
            }

            if (count > 0) {
               ps.executeBatch();
            }
         } catch (RuntimeException var10) {
            throw new SQLRuntimeException(var10);
         } finally {
            weblogic.jdbc.JDBCHelper.close((java.sql.Statement)ps);
         }

      }
   }

   private boolean deleteCompletedOlderThanOrMoreThan(long delayMillis, int max, int timeoutMills) {
      boolean timedout = false;
      java.sql.Connection conn = null;
      long startTime = System.currentTimeMillis();
      CompletedXARecord first = this.completedXids.removeOlderThanOrMoreThan(System.currentTimeMillis() - delayMillis, max);
      Exception e = null;
      if (first == null) {
         return false;
      } else {
         try {
            conn = JTSLoggableResourceImpl.getNewJTSConnection(this.poolName);
            conn.setAutoCommit(false);

            do {
               this.delete(conn, first, max);
               conn.commit();
               long curTime = System.currentTimeMillis();
               if (curTime - startTime > (long)timeoutMills) {
                  timedout = true;
               }

               first = this.completedXids.removeOlderThanOrMoreThan(System.currentTimeMillis() - delayMillis, max);
            } while(first != null);
         } catch (RuntimeException var28) {
            e = var28;
         } catch (SQLException var29) {
            e = var29;
         } finally {
            if (e != null) {
               if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
                  this.trace(first.xid, "failed to delete record(s), will retry", (Exception)e);
               }

               if (conn != null) {
                  try {
                     ((WLConnection)conn).setFailed();
                  } catch (SQLException var27) {
                  }

                  try {
                     conn.rollback();
                  } catch (Exception var26) {
                  }
               }

               this.completedXids.add(first);
            }

            weblogic.jdbc.JDBCHelper.close(conn);
            return timedout;
         }
      }
   }

   CompletedXARecord deleteYoungestCompleted(java.sql.Connection conn) {
      CompletedXARecord first = this.completedXids.removeYoungest(900, 50);
      if (first == null) {
         return null;
      } else {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(first.xid, "There are too many rows in queue; start piggy backing deletion.");
         }

         try {
            this.delete(conn, first, 50);
            return first;
         } catch (SQLException var4) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(first.xid, "failed to delete XA record(s), will retry later", var4);
            }

            this.completedXids.add(first);
            return null;
         }
      }
   }

   public String toString() {
      return this.dmlName;
   }

   private void trace(Xid xid, String s) {
      JTSLoggableResourceImpl.trace(this.poolName, xid, s, (Exception)null);
   }

   private void trace(Xid xid, String s, Exception e) {
      JTSLoggableResourceImpl.trace(this.poolName, xid, s, e);
   }

   static class SQLRuntimeException extends SQLException {
      public SQLRuntimeException(RuntimeException re) {
         super(re.toString());
         this.initCause(re);
      }
   }

   static class CompletedXARecord implements Comparable {
      private long timestamp;
      private Xid xid;
      private String xidStr;
      private CompletedXARecord next;

      private CompletedXARecord(Xid xid) {
         this.timestamp = System.currentTimeMillis();
         this.xid = xid;
         this.xidStr = XIDFactory.xidToString(xid, false);
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            CompletedXARecord other = (CompletedXARecord)o;
            return other.timestamp == this.timestamp && other.xidStr.equals(this.xidStr);
         }
      }

      public int hashCode() {
         return (int)this.timestamp ^ this.xidStr.hashCode();
      }

      public int compareTo(Object o) {
         CompletedXARecord other = (CompletedXARecord)o;
         return this.timestamp == other.timestamp ? this.xidStr.compareTo(other.xidStr) : (int)(other.timestamp - this.timestamp);
      }

      public String toString() {
         return this.xidStr;
      }

      // $FF: synthetic method
      CompletedXARecord(Xid x0, Object x1) {
         this(x0);
      }
   }

   private static class CompletedXARecordSet {
      private TreeSet tree;

      private CompletedXARecordSet() {
         this.tree = new TreeSet();
      }

      private synchronized void add(CompletedXARecord r) {
         while(r != null) {
            this.tree.add(r);
            r = r.next;
         }

      }

      private synchronized CompletedXARecord removeYoungest(int threshold, int max) {
         return this.tree.size() < threshold ? null : this.removeYoungestUnsync(max);
      }

      private CompletedXARecord removeYoungestUnsync(int max) {
         CompletedXARecord first = null;

         int oldSize;
         CompletedXARecord cur;
         for(oldSize = this.tree.size(); this.tree.size() > 0 && max-- > 0; first = cur) {
            cur = (CompletedXARecord)this.tree.first();
            this.tree.remove(cur);
            cur.next = first;
         }

         if (JdbcDebug.JTAJDBC.isDebugEnabled() && first != null) {
            JdbcDebug.log("found " + (oldSize - this.tree.size()) + " yongest records. Total records in  queue: " + oldSize);
         }

         return first;
      }

      private CompletedXARecord removeOlderThan(long timestamp, int max) {
         CompletedXARecord first = null;

         int oldSize;
         CompletedXARecord cur;
         for(oldSize = this.tree.size(); this.tree.size() > 0 && max-- > 0; first = cur) {
            cur = (CompletedXARecord)this.tree.last();
            if (cur.timestamp > timestamp) {
               break;
            }

            this.tree.remove(cur);
            cur.next = first;
         }

         if (JdbcDebug.JTAJDBC.isDebugEnabled() && first != null) {
            JdbcDebug.log("found " + (oldSize - this.tree.size()) + " records older than timestamp: " + timestamp + ". Total records in  queue: " + oldSize);
         }

         return first;
      }

      private synchronized CompletedXARecord removeOlderThanOrMoreThan(long timestamp, int max) {
         return this.tree.size() < max ? this.removeOlderThan(timestamp, max) : this.removeYoungestUnsync(max);
      }

      // $FF: synthetic method
      CompletedXARecordSet(Object x0) {
         this();
      }
   }
}
