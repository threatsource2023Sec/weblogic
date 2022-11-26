package weblogic.jdbc.common.internal;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.transaction.TransactionManager;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jdbc.jta.DataSource;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.internal.TransactionImpl;
import weblogic.transaction.internal.TxDebug;

public final class JdbcDebug {
   public static final DebugLogger JTAJDBC = DebugLogger.getDebugLogger("DebugJTAJDBC");
   public static final DebugLogger JDBCSQL = DebugLogger.getDebugLogger("DebugJDBCSQL");
   public static final DebugLogger JDBCRMI = DebugLogger.getDebugLogger("DebugJDBCRMI");
   public static final DebugLogger JDBCCONN = DebugLogger.getDebugLogger("DebugJDBCConn");
   public static final DebugLogger JDBCRAC = DebugLogger.getDebugLogger("DebugJDBCRAC");
   public static final DebugLogger JDBCONS = DebugLogger.getDebugLogger("DebugJDBCONS");
   public static final DebugLogger JDBCRMIInternal = DebugLogger.createUnregisteredDebugLogger("JDBCRMIInternal", true);
   private static Map dataSources = new Hashtable();
   private static Map numXAConn = new Hashtable();

   public static final void setDataSource(String poolName, DataSource ds) {
      getOrCreateDataSrcInfo(poolName).ds = ds;
   }

   public static final int getDebugLevel(Properties props) {
      if (props != null) {
         Object value = props.get("jdbcxaDebugLevel");
         if (value != null) {
            return Integer.parseInt((String)value);
         }
      }

      return 10;
   }

   public static final void setDebugLevel(String poolName, int debugLevel) {
      if (debugLevel > 0) {
         getOrCreateDataSrcInfo(poolName).debugLevel = debugLevel;
      }

   }

   public static boolean isEnabled(DataSource ds, int debugLevel) {
      if (ds == null) {
         return false;
      } else if (!JTAJDBC.isDebugEnabled()) {
         return false;
      } else if (ds.toString() == null) {
         return false;
      } else {
         return debugLevel <= ds.getDebugLevel();
      }
   }

   public static boolean isEnabled(String dsName, int debugLevel) {
      if (dsName == null) {
         return false;
      } else if (!JTAJDBC.isDebugEnabled()) {
         return false;
      } else {
         return debugLevel <= getOrCreateDataSrcInfo(dsName).debugLevel;
      }
   }

   public static final void enter(DataSource ds, String msg) {
      enter(ds, (Transaction)null, msg);
   }

   public static final void enter(DataSource ds, Transaction tx, String msg) {
      log((DataSource)ds, (Transaction)tx, "> " + msg, (Throwable)null);
   }

   public static final void enter(String dsName, String msg) {
      enter(dsName, (Transaction)null, msg);
   }

   public static final void enter(String dsName, Transaction tx, String msg) {
      log((String)dsName, (Transaction)tx, "> " + msg, (Throwable)null);
   }

   public static final void leave(DataSource ds, String msg) {
      leave(ds, (Transaction)null, msg);
   }

   public static final void leave(DataSource ds, Transaction tx, String msg) {
      log((DataSource)ds, (Transaction)tx, "< " + msg, (Throwable)null);
   }

   public static final void leave(String dsName, String msg) {
      leave(dsName, (Transaction)null, msg);
   }

   public static final void leave(String dsName, Transaction tx, String msg) {
      log((String)dsName, (Transaction)tx, "< " + msg, (Throwable)null);
   }

   public static final void log(DataSource ds, String msg) {
      log((DataSource)ds, (Transaction)((Transaction)null), msg, (Throwable)null);
   }

   public static final void log(DataSource ds, Transaction tx, String msg) {
      log((DataSource)ds, (Transaction)tx, msg, (Throwable)null);
   }

   public static final void log(String msg) {
      log((String)null, (Transaction)null, msg);
   }

   public static final void log(String msg, Throwable t) {
      log((String)null, (Transaction)null, msg, t);
   }

   public static final void log(String dsName, String msg) {
      log((String)dsName, (Transaction)((Transaction)null), msg, (Throwable)null);
   }

   public static void log(String poolName, Xid xid, String msg, Throwable t) {
      Transaction tx = null;
      TransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
      if (tm instanceof weblogic.transaction.TransactionManager) {
         tx = (Transaction)((weblogic.transaction.TransactionManager)tm).getTransaction(xid);
      }

      log(poolName, tx, msg, t);
   }

   public static final void log(String dsName, Transaction tx, String msg) {
      log((String)dsName, (Transaction)tx, msg, (Throwable)null);
   }

   public static final void err(DataSource ds, String msg, Throwable t) {
      err(ds, (Transaction)null, msg, t);
   }

   public static final void err(DataSource ds, Transaction tx, String msg, Throwable t) {
      log(ds, tx, "< " + msg, t);
   }

   public static final void err(String dsName, String msg, Throwable t) {
      err(dsName, (Transaction)null, msg, t);
   }

   public static final void err(String dsName, Transaction tx, String msg, Throwable t) {
      log(dsName, tx, "< " + msg, t);
   }

   public static final int incNumXAConn(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return ++dsInfo.numXAConn;
         }
      } else {
         return 0;
      }
   }

   public static final int decNumXAConn(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return --dsInfo.numXAConn;
         }
      } else {
         return 0;
      }
   }

   public static final int getNumXAConn(String poolName) {
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      return dsInfo != null ? dsInfo.numXAConn : 0;
   }

   public static final int incNumConn(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return ++dsInfo.numConn;
         }
      } else {
         return 0;
      }
   }

   public static final int decNumConn(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return --dsInfo.numConn;
         }
      } else {
         return 0;
      }
   }

   public static final int getNumConn(String poolName) {
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      return dsInfo != null ? dsInfo.numConn : 0;
   }

   public static final int incNumStmt(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return ++dsInfo.numStmt;
         }
      } else {
         return 0;
      }
   }

   public static final int decNumStmt(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return --dsInfo.numStmt;
         }
      } else {
         return 0;
      }
   }

   public static final int getNumStmt(String poolName) {
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      return dsInfo != null ? dsInfo.numStmt : 0;
   }

   public static final int incNumRS(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return ++dsInfo.numRS;
         }
      } else {
         return 0;
      }
   }

   public static final int decNumRS(DataSource ds) {
      String poolName = ds.toString();
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      if (dsInfo != null) {
         synchronized(dsInfo) {
            return --dsInfo.numRS;
         }
      } else {
         return 0;
      }
   }

   public static final int getNumRS(String poolName) {
      DataSrcInfo dsInfo = (DataSrcInfo)dataSources.get(poolName);
      return dsInfo != null ? dsInfo.numRS : 0;
   }

   private static final String getTxString() {
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      return getTxString(tx);
   }

   private static final String getTxString(Transaction tx) {
      String txName = "null";
      if (tx != null) {
         txName = tx.getName();
         if (txName == null || txName.equals("")) {
            txName = tx.toString();
         }
      }

      return " -tx:" + txName + "-";
   }

   private static void log(DataSource ds, Transaction tx, String msg, Throwable t) {
      String poolName = ds == null ? "" : ds.toString();
      log(poolName, tx, msg, t);
   }

   private static void log(String poolName, Transaction tx, String msg, Throwable t) {
      String debugMsg = getTxString(tx) + " -pool:" + poolName + "- " + msg;
      if (t == null) {
         TxDebug.txdebug(TxDebug.JTAJDBC, (TransactionImpl)tx, debugMsg);
      } else {
         TxDebug.txdebug(TxDebug.JTAJDBC, (TransactionImpl)tx, debugMsg, t);
      }

   }

   private static DataSrcInfo getOrCreateDataSrcInfo(String poolName) {
      DataSrcInfo dsInfo = null;
      if (poolName != null) {
         dsInfo = (DataSrcInfo)dataSources.get(poolName);
         if (dsInfo == null) {
            dsInfo = new DataSrcInfo();
            dsInfo.debugLevel = 10;
            dataSources.put(poolName, dsInfo);
         }
      }

      return dsInfo;
   }

   public static String txIsolationToString(int isolation) {
      switch (isolation) {
         case 0:
            return "TRANSACTION_NONE";
         case 1:
            return "TRANSACTION_READ_UNCOMMITTED";
         case 2:
            return "TRANSACTION_READ_COMMITTED";
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return "UNKNOWN";
         case 4:
            return "TRANSACTION_REPEATABLE_READ";
         case 8:
            return "TRANSACTION_SERIALIZABLE";
      }
   }

   private static class DataSrcInfo {
      public DataSource ds;
      public int debugLevel;
      public int numXAConn;
      public int numConn;
      public int numStmt;
      public int numRS;

      private DataSrcInfo() {
         this.debugLevel = 10;
         this.numXAConn = 0;
         this.numConn = 0;
         this.numStmt = 0;
         this.numRS = 0;
      }

      // $FF: synthetic method
      DataSrcInfo(Object x0) {
         this();
      }
   }
}
