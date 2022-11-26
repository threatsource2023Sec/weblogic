package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.WLConnection;
import weblogic.jdbc.jts.Driver;
import weblogic.transaction.CoordinatorService;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.XIDFactory;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.loggingresource.LoggingResourceException;
import weblogic.transaction.loggingresource.MigratableLoggingResource;
import weblogic.transaction.nonxa.NonXAException;
import weblogic.transaction.nonxa.NonXAResource;
import weblogic.utils.encoders.Base64Bytes;

public final class JTSLoggableResourceImpl implements LoggingResource, MigratableLoggingResource, NonXAResource, CoordinatorService {
   private static final String RESOURCE_NAME_PREFIX = "WL_JDBC_LLR_POOL.";
   private static final BackingTableMap backingTables = new BackingTableMap();
   private JTSConnection jtsConn;
   private String name;
   private String poolName;
   private String resourceName;
   private JTSLoggableResourceTable backingTable;
   private static TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();

   public JTSLoggableResourceImpl(JTSConnection aConnection) throws SQLException {
      this.jtsConn = aConnection;
      this.poolName = this.jtsConn.getPool();
      this.backingTable = backingTables.get(this.poolName);
      this.resourceName = "WL_JDBC_LLR_POOL." + this.poolName;
      if (this.backingTable == null) {
         throw new SQLException("No initialized logging last resource table found for pool '" + this.poolName + "'.  Logging last resource tables are initialized during server boot, and only supported for globally scoped JDBC pools.");
      }
   }

   public JTSLoggableResourceImpl(String connectionPoolMBeanName) {
      this.poolName = connectionPoolMBeanName;
      this.backingTable = backingTables.get(this.poolName);
      this.resourceName = "WL_JDBC_LLR_POOL." + this.poolName;
   }

   public int getRowCount() throws SQLException {
      return this.backingTable.getRowCount();
   }

   public int setMaxDeleteIntervalMillis(int millis) {
      return this.backingTable.setMaxDeleteIntervalMillis(millis);
   }

   public String toString() {
      return "JDBC LLR, pool=" + this.poolName + ", table=" + this.backingTable;
   }

   public String getName() {
      return this.resourceName;
   }

   public void writeXARecord(Xid xid, byte[] transactionRecord) throws LoggingResourceException {
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         this.trace(xid, "begin write XA record table=" + this.backingTable + " recLen=" + transactionRecord.length);
      }

      JTSConnection jConn = this.jtsConn.getConnection(xid);
      String encodedStr;
      if (jConn == null) {
         encodedStr = "No connection associated with xid = " + xid;
         throw new LoggingResourceException(encodedStr);
      } else {
         encodedStr = this.encodeBuffer(transactionRecord);

         try {
            this.backingTable.insertRow((java.sql.Connection)jConn, XIDFactory.xidToString(xid, false), this.poolName, encodedStr);
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(xid, "after write XA record");
            }

         } catch (SQLException var6) {
            throw new LoggingResourceException(var6);
         }
      }
   }

   public void deleteXARecord(Xid xid) {
      this.backingTable.addCompleted(xid);
   }

   public void deleteXARecord(Xid xid, String server) {
      JTSLoggableResourceTable table = backingTables.get(this.getBackingTableKey(server));

      assert table != null;

      table.addCompleted(xid);
   }

   public byte[] getXARecord(Xid xid) throws LoggingResourceException {
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         this.trace(xid, "get XA record, begin");
      }

      java.sql.Connection conn = null;

      byte[] var5;
      try {
         conn = getNewJTSConnection(this.poolName);
         String xidStr = XIDFactory.xidToString(xid, false);
         String recStr = this.backingTable.getRow(conn, xidStr);
         if (recStr == null) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(xid, "get XA record, record does not exist");
            }

            Object var21 = null;
            return (byte[])var21;
         }

         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "get XA record, record found");
         }

         var5 = this.decodeString(recStr);
      } catch (SQLException var18) {
         try {
            if (conn != null) {
               ((WLConnection)conn).setFailed();
            }
         } catch (SQLException var17) {
         }

         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "get XA record, failed with exception: " + var18);
         }

         throw new LoggingResourceException(var18);
      } catch (LoggingResourceException var19) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "get XA record, failed with exception: " + var19);
         }

         throw var19;
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var16) {
            }
         }

      }

      return var5;
   }

   public byte[][] recoverXARecords() throws LoggingResourceException {
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         this.trace((Xid)null, "recovering XA records");
      }

      if (backingTables.get(this.poolName) != null) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace((Xid)null, "this pool already recovered");
         }

         return new byte[0][];
      } else {
         HashMap results = null;
         java.sql.Connection conn = null;

         try {
            conn = getNewJTSConnection(this.poolName);
            conn.setAutoCommit(true);
            this.backingTable = this.findOrCreateBackingTable(conn);
            backingTables.put(this.poolName, this.backingTable);
            results = this.backingTable.recover(conn);
         } catch (SQLException var13) {
            throw new LoggingResourceException(var13);
         } finally {
            if (conn != null) {
               try {
                  conn.close();
               } catch (SQLException var12) {
               }
            }

         }

         byte[][] arr = new byte[results.size()][];
         int pos = 0;
         Iterator resultKeys = results.keySet().iterator();

         while(resultKeys.hasNext()) {
            String xid = (String)resultKeys.next();
            String rec = (String)results.get(xid);
            arr[pos++] = this.decodeString(rec);
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace((Xid)null, "recovered XA record, xid=" + xid);
            }
         }

         return arr;
      }
   }

   private String getBackingTableKey(String serverName) {
      return this.poolName + "@" + serverName;
   }

   public byte[][] recoverXARecords(String serverName) throws LoggingResourceException {
      HashMap results = null;
      java.sql.Connection conn = null;

      try {
         conn = getNewJTSConnection(this.poolName);
         conn.setAutoCommit(true);
         JTSLoggableResourceTable table = this.findOrCreateBackingTable(serverName, conn);
         results = table.recover(conn);
      } catch (SQLException var14) {
         throw new LoggingResourceException(var14);
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var13) {
            }
         }

      }

      byte[][] arr = new byte[results.size()][];
      int pos = 0;
      Iterator resultKeys = results.keySet().iterator();

      while(resultKeys.hasNext()) {
         String xid = (String)resultKeys.next();
         String rec = (String)results.get(xid);
         arr[pos++] = this.decodeString(rec);
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace((Xid)null, "recovered XA record, xid=" + xid);
         }
      }

      return arr;
   }

   public void commit(Xid xid, boolean onePhase) throws NonXAException {
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
         this.trace(xid, "commit one-phase=" + onePhase);
      }

      JTSLoggableResourceTable.CompletedXARecord deleted = null;
      Exception sqe = null;

      try {
         JTSConnection localJTSConn = this.jtsConn.getConnection(xid);
         java.sql.Connection conn = (java.sql.Connection)localJTSConn;
         if (conn == null) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(xid, "commit failed, no connection");
            }

            throw new NonXAException("No connection associated with xid = " + xid);
         }

         deleted = this.backingTable.deleteYoungestCompleted(conn);
         localJTSConn.internalCommit();
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "commit complete");
         }
      } catch (RuntimeException var7) {
         sqe = var7;
      } catch (SQLException var8) {
         sqe = var8;
      }

      if (sqe != null) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "commit failed, " + sqe);
         }

         if (deleted != null) {
            this.backingTable.addCompleted(deleted);
         }

         NonXAException nxae = new NonXAException(((Exception)sqe).getMessage());
         nxae.initCause((Throwable)sqe);
         throw nxae;
      }
   }

   public void rollback(Xid xid) throws NonXAException {
      try {
         JTSConnection conn = this.jtsConn.getConnection(xid);
         if (conn == null) {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(xid, "rollback failed, no connection");
            }

            return;
         }

         conn.internalRollback();
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "rollback OK");
         }
      } catch (RuntimeException var3) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "rollback failed," + var3);
         }
      } catch (SQLException var4) {
         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            this.trace(xid, "rollback failed," + var4);
         }
      }

   }

   public boolean isSameRM(NonXAResource nxar) throws NonXAException {
      if (!(nxar instanceof JTSLoggableResourceImpl)) {
         return false;
      } else {
         JTSLoggableResourceImpl that = (JTSLoggableResourceImpl)nxar;
         return this.resourceName.equals(that.resourceName);
      }
   }

   public Object invokeCoordinatorService(String ServiceName, Object data) throws SystemException {
      Transaction tx = (Transaction)transactionHelper.getTransaction();
      if (tx == null) {
         throw new AssertionError("No transaction on current thread.");
      } else {
         SystemException se;
         try {
            if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
               this.trace(tx.getXid(), "lookup the local JDBC LLR connection for the current transaction");
            }

            java.sql.Connection c = Driver.createLocalConnection(this.poolName, (Properties)data);
            return c;
         } catch (RuntimeException var6) {
            se = new SystemException(var6.toString());
            se.initCause(var6);
            throw se;
         } catch (SQLException var7) {
            se = new SystemException(var7.toString());
            se.initCause(var7);
            throw se;
         }
      }
   }

   private byte[] decodeString(String str) throws LoggingResourceException {
      try {
         return Base64Bytes.base64ToByteArray(str, true);
      } catch (IllegalArgumentException var3) {
         throw new LoggingResourceException(var3);
      }
   }

   private String encodeBuffer(byte[] buf) {
      return Base64Bytes.byteArrayToBase64(buf, true);
   }

   static java.sql.Connection getNewJTSConnection(String poolName) throws SQLException {
      try {
         return (java.sql.Connection)Driver.newConnection(poolName, (Transaction)null, (String)null, (String)null, (String)null, (Properties)null);
      } catch (RuntimeException var3) {
         SQLException se = new SQLException(var3.toString());
         se.initCause(var3);
         throw var3;
      }
   }

   private JTSLoggableResourceTable findOrCreateBackingTable(java.sql.Connection conn) throws SQLException {
      JTSLoggableResourceTable table = new JTSLoggableResourceTable(conn, this.poolName);
      table.findOrCreateTable(conn);
      return table;
   }

   private JTSLoggableResourceTable findOrCreateBackingTable(String serverName, java.sql.Connection conn) throws SQLException {
      JTSLoggableResourceTable table = backingTables.get(this.getBackingTableKey(serverName));
      if (table != null) {
         return table;
      } else {
         table = new JTSLoggableResourceTable(conn, this.poolName, serverName);
         table.findOrCreateTable(conn);
         backingTables.put(this.getBackingTableKey(serverName), table);
         return table;
      }
   }

   private void trace(Xid xid, String s) {
      trace(this.poolName, xid, s, (Exception)null);
   }

   private void trace(Xid xid, String s, Exception e) {
      trace(this.poolName, xid, s, e);
   }

   static void trace(String poolName, Xid xid, String s, Exception e) {
      JTSLoggableResourceTable backingTable = poolName == null ? null : backingTables.get(poolName);
      StringBuffer msg = new StringBuffer();
      msg.append("JDBC LLR pool='").append(poolName).append("'");
      if (xid != null) {
         msg.append(" xid='").append(XIDFactory.xidToString(xid, false)).append("'");
      }

      if (backingTable != null) {
         msg.append(" tbl='").append(backingTable.toString()).append("'");
      }

      msg.append(": ").append(s);
      JdbcDebug.log((String)poolName, (Xid)xid, msg.toString(), e);
   }

   public void migratableActivate(String serverName) {
   }

   public void migratableDeactivate(String serverName) {
      backingTables.remove(this.getBackingTableKey(serverName));
   }

   private static class BackingTableMap {
      HashMap tables;

      private BackingTableMap() {
         this.tables = new HashMap();
      }

      synchronized JTSLoggableResourceTable get(String poolName) {
         return (JTSLoggableResourceTable)this.tables.get(poolName);
      }

      synchronized void put(String poolName, JTSLoggableResourceTable bt) {
         this.tables.put(poolName, bt);
      }

      synchronized JTSLoggableResourceTable remove(String poolName) {
         return (JTSLoggableResourceTable)this.tables.remove(poolName);
      }

      // $FF: synthetic method
      BackingTableMap(Object x0) {
         this();
      }
   }
}
