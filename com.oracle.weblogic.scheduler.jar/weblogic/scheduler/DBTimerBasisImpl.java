package weblogic.scheduler;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import weblogic.cluster.ClusterLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.store.io.jdbc.JDBCHelper;
import weblogic.timers.TimerListener;
import weblogic.timers.internal.ScheduleExpressionWrapper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public final class DBTimerBasisImpl implements TimerBasis {
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();
   private final Object BLOB_MARKER = new Object();
   private DataSource ds;
   private final JDBCSystemResourceMBean jdbcResource;
   private final SQLHelper sqlHelper;
   private final String serverName;
   private final String domainID;
   long previousIdTime = -1L;
   int idSuffix = 0;
   private Connection connection;

   DBTimerBasisImpl(JDBCSystemResourceMBean jdbcResource, String tableName, String domainName, String clusterName, String serverName, String domainID) throws SQLException {
      this.jdbcResource = jdbcResource;
      this.serverName = serverName;
      this.domainID = domainID;
      Connection con = null;

      try {
         con = this.getJDBCConnection();
         DatabaseMetaData metaData = con.getMetaData();
         int dbmsType = JDBCHelper.getDBMSType(metaData, (String[])null);
         if (dbmsType == 1) {
            this.sqlHelper = new OracleSQLHelperImpl(tableName, domainName, clusterName);
         } else if (dbmsType == 9) {
            this.sqlHelper = new MySQLHelperImpl(tableName, domainName, clusterName);
         } else if (dbmsType == 5) {
            this.sqlHelper = new InformixSQLHelperImpl(tableName, domainName, clusterName);
         } else if (dbmsType == 7) {
            this.sqlHelper = new TimesTenSQLHelperImpl(tableName, domainName, clusterName);
         } else {
            this.sqlHelper = new GenericSQLHelperImpl(tableName, domainName, clusterName);
         }
      } finally {
         this.closeSQLConnection(con);
      }

   }

   public String createTimer(String tmName, TimerListener to, long initial, long interval, AuthenticatedSubject user) throws TimerException {
      return this.internalCreateTimer(tmName, to, System.currentTimeMillis() + initial, interval, (ScheduleExpressionWrapper)null, user, (String)null);
   }

   public String createCalendarTimer(String tmName, TimerListener to, long initialTimeout, ScheduleExpressionWrapper scheduleWrapper, AuthenticatedSubject user) throws TimerException {
      return this.internalCreateTimer(tmName, to, initialTimeout, -1L, scheduleWrapper, user, (String)null);
   }

   public String createCalendarTimer(String tmName, TimerListener to, long initialTimeout, ScheduleExpressionWrapper scheduleWrapper, AuthenticatedSubject user, String userKey) throws TimerException, TimerAlreadyExistsException {
      try {
         return this.internalCreateTimer(tmName, to, initialTimeout, -1L, scheduleWrapper, user, userKey);
      } catch (TimerException var10) {
         Throwable cause = var10.getCause();
         if (cause instanceof SQLException && this.isIntegrityConstraintViolationException((SQLException)cause)) {
            throw new TimerAlreadyExistsException(cause);
         } else {
            throw var10;
         }
      }
   }

   private boolean isIntegrityConstraintViolationException(SQLException sqle) {
      if (sqle instanceof SQLIntegrityConstraintViolationException) {
         return true;
      } else {
         return "23000".equals(sqle.getSQLState());
      }
   }

   private String internalCreateTimer(String tmName, TimerListener to, long initialTimeout, long interval, ScheduleExpressionWrapper scheduleWrapper, AuthenticatedSubject user, String userKey) throws TimerException {
      Transaction tx = null;
      TransactionManager tm = null;
      if (!(to instanceof TimerListenerExtension)) {
         tm = TxHelper.getTransactionManager();
         tx = tm.forceSuspend();
      }

      String sql;
      try {
         String id = this.getUniqueId(to);
         if (DEBUG) {
            debug("generated id " + id + " for listener " + to);
         }

         if (initialTimeout > -1L) {
            sql = userKey == null ? this.sqlHelper.getCreateTimerSQL() : this.sqlHelper.getCreateTimerSQLWithUserKey();
            if (DEBUG) {
               debug("executing sql: " + sql);
            }

            Serializable blobContent = (Serializable)to;
            if (scheduleWrapper != null) {
               blobContent = new BlobContent((Serializable)to, scheduleWrapper);
            }

            Object[] bindValues = userKey == null ? new Object[]{id, this.BLOB_MARKER, initialTimeout, interval, tmName} : new Object[]{id, this.BLOB_MARKER, initialTimeout, interval, userKey, tmName};
            this.writeListener(id, (Serializable)blobContent, sql, bindValues);
            ClusterLogger.logCreatedJob(id, to.toString());
         } else if (DEBUG) {
            debug("calendar timer with id " + id + " not persisted because it has no more timeouts");
         }

         sql = id;
      } catch (SQLException var19) {
         if (DEBUG) {
            var19.printStackTrace();
         }

         throw new TimerException("Unable to create timer", var19);
      } finally {
         if (tx != null) {
            tm.forceResume(tx);
         }

      }

      return sql;
   }

   public boolean cancelTimer(String id) throws TimerException {
      try {
         Object[] bindValues = new Object[]{id};
         int result = this.runUpdate(this.sqlHelper.getCancelTimerSQL(), bindValues);
         if (result > 0) {
            ClusterLogger.logCancelledJob(id);
            return true;
         } else {
            return false;
         }
      } catch (SQLException var4) {
         if (DEBUG) {
            var4.printStackTrace();
         }

         throw new TimerException("unable to cancel timer", var4);
      }
   }

   public synchronized void advanceIntervalTimer(String id, TimerListener listener) throws TimerException {
      TransactionManager tm = TxHelper.getTransactionManager();
      Transaction tx = tm.forceSuspend();

      try {
         Object[] bindValues = new Object[]{System.currentTimeMillis(), this.BLOB_MARKER, id};
         this.writeListener(id, (Serializable)listener, this.sqlHelper.getAdvanceTimerSQL(), bindValues);
      } catch (SQLException var9) {
         if (DEBUG) {
            var9.printStackTrace();
         }

         throw new TimerException("unable to timeout in database", var9);
      } finally {
         tm.forceResume(tx);
      }

   }

   public void advanceCalendarTimer(String id, TimerListener listener, ScheduleExpressionWrapper scheduleWrapper, long nextExpiration) throws NoSuchObjectLocalException, TimerException {
      TransactionManager tm = TxHelper.getTransactionManager();
      Transaction tx = tm.forceSuspend();

      try {
         Object[] bindValues = new Object[]{nextExpiration, this.BLOB_MARKER, id};
         BlobContent blobContent = new BlobContent((Serializable)listener, scheduleWrapper);
         this.writeListener(id, blobContent, this.sqlHelper.getUpdateStartTimeSQL(), bindValues);
      } catch (SQLException var13) {
         if (DEBUG) {
            var13.printStackTrace();
         }

         throw new TimerException("unable to timeout in database", var13);
      } finally {
         tm.forceResume(tx);
      }

   }

   public synchronized TimerState getTimerState(String id) throws TimerException {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_DB_TIMER_BASIS_IMPL_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      Object var57;
      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var8 = null;

         try {
            con = this.getJDBCConnection();
            String query = this.sqlHelper.getTimerStateSQL();
            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
               throw new TimerException("unable to get timerstate");
            }

            String timerId = rs.getString(1);
            Object blobObject = ObjectPersistenceHelper.getObject(con, rs, 2);
            long timeout = rs.getLong(3);
            long interval = rs.getLong(4);
            TimerListener timerListener = null;
            ScheduleExpressionWrapper scheduleWrapper = null;
            if (blobObject instanceof BlobContent) {
               BlobContent blobContent = (BlobContent)blobObject;
               scheduleWrapper = blobContent.getScheduleWrapper();
               timerListener = (TimerListener)blobContent.getTimerListener();
            } else {
               timerListener = (TimerListener)blobObject;
            }

            if (scheduleWrapper != null) {
               var57 = new CalendarTimerState(this, timerId, timerListener, timeout, scheduleWrapper, (AuthenticatedSubject)null, this.domainID);
               return (TimerState)var57;
            }

            var57 = new TimerState(this, timerId, timerListener, timeout, interval, (AuthenticatedSubject)null, this.domainID);
         } catch (Throwable var48) {
            var8 = var48;
            throw var48;
         } finally {
            if (mic != null) {
               if (var8 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var47) {
                     var8.addSuppressed(var47);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (SQLException var50) {
         if (DEBUG) {
            ClusterLogger.logInvalidTimerState(id, var50);
         }

         throw new TimerException("unable to get timer", var50);
      } catch (GlobalResourceGroupApplicationNotFoundException var51) {
         throw new TimerException("unable to read TimerListener", var51);
      } catch (ApplicationNotFoundException var52) {
         if (DEBUG) {
            ClusterLogger.logInvalidTimerState(id, var52);
         }

         throw new TimerException("unable to read TimerListener", var52);
      } catch (IOException var53) {
         if (DEBUG) {
            ClusterLogger.logInvalidTimerState(id, var53);
         }

         throw new TimerException("unable to read TimerListener", var53);
      } catch (RuntimeException var54) {
         ClusterLogger.logInvalidTimerState(id, var54);
         throw var54;
      } catch (Error var55) {
         ClusterLogger.logInvalidTimerState(id, var55);
         throw var55;
      } finally {
         this.closeResultSet(rs);
         this.closePreparedStatement(ps);
         this.closeSQLConnection(con);
      }

      return (TimerState)var57;
   }

   public synchronized List getReadyTimers(int eventHorizonSeconds) throws TimerException {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query = this.sqlHelper.getReadyTimersSQL();

      try {
         con = this.getJDBCConnection();
         ps = con.prepareStatement(query);
         long horizon = System.currentTimeMillis() + (long)(eventHorizonSeconds * 1000);
         ps.setLong(1, horizon);
         rs = ps.executeQuery();
         ArrayList list = new ArrayList();

         while(rs.next()) {
            list.add(rs.getString(1));
         }

         ArrayList var9 = list;
         return var9;
      } catch (SQLException var13) {
         if (DEBUG) {
            var13.printStackTrace();
         }

         throw new TimerException("SQLException while getting timers", var13);
      } finally {
         this.closeResultSet(rs);
         this.closePreparedStatement(ps);
         this.closeSQLConnection(con);
      }
   }

   private Timer[] getTimersHelper(String timerManagerName, String timerIdStartsWith, String userKey) throws TimerException {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         con = this.getJDBCConnection();
         String query = null;
         if (timerIdStartsWith != null) {
            query = this.sqlHelper.getTimersLikeIdSQL();
         } else if (userKey != null) {
            query = this.sqlHelper.getTimersByUserKey();
         } else {
            query = this.sqlHelper.getTimersSQL();
         }

         ps = con.prepareStatement(query);
         ps.setString(1, timerManagerName);
         if (timerIdStartsWith != null) {
            ps.setString(2, timerIdStartsWith + '%');
         } else if (userKey != null) {
            ps.setString(2, userKey);
         }

         rs = ps.executeQuery();
         ArrayList list = new ArrayList();

         while(rs.next()) {
            list.add(new TimerImpl(rs.getString(1), this));
         }

         Timer[] timers = new Timer[list.size()];
         list.toArray(timers);
         Timer[] var10 = timers;
         return var10;
      } catch (SQLException var14) {
         if (DEBUG) {
            var14.printStackTrace();
         }

         throw new TimerException("SQLException while getting timers", var14);
      } finally {
         this.closeResultSet(rs);
         this.closePreparedStatement(ps);
         this.closeSQLConnection(con);
      }
   }

   public Timer[] getTimers(String timerManagerName) throws TimerException {
      return this.getTimersHelper(timerManagerName, (String)null, (String)null);
   }

   public Timer[] getTimers(String timerManagerName, String TimerIdStartsWith) throws TimerException {
      return this.getTimersHelper(timerManagerName, TimerIdStartsWith, (String)null);
   }

   public Timer getTimerByUserKey(String timerManagerName, String userKey) throws TimerException {
      Timer[] timers = this.getTimersHelper(timerManagerName, (String)null, userKey);
      return timers.length > 0 ? timers[0] : null;
   }

   public void cancelTimers(String timerManagerName) throws TimerException {
      try {
         Object[] bindValues = new Object[]{timerManagerName};
         this.runUpdate(this.sqlHelper.getCancelTimersSQL(), bindValues);
      } catch (SQLException var3) {
         if (DEBUG) {
            var3.printStackTrace();
         }

         throw new TimerException(var3.toString());
      }
   }

   private synchronized String getUniqueId(TimerListener to) {
      long currentTime = System.currentTimeMillis();
      String key = this.serverName + "_" + currentTime;
      if (currentTime == this.previousIdTime) {
         ++this.idSuffix;
         key = key + "_" + this.idSuffix;
      } else {
         this.previousIdTime = currentTime;
         this.idSuffix = 0;
      }

      return to instanceof TimerCreationCallback ? ((TimerCreationCallback)to).getTimerId(key) : key;
   }

   private int runUpdate(String sqlQuery, Object[] bindValues) throws SQLException {
      Connection con = this.getJDBCConnection();
      PreparedStatement ps = null;

      int var5;
      try {
         ps = con.prepareStatement(sqlQuery);
         this.doBinds(ps, bindValues);
         var5 = ps.executeUpdate();
      } finally {
         this.closePreparedStatement(ps);
         this.closeSQLConnection(con);
      }

      return var5;
   }

   private void writeListener(String id, Serializable listener, String listenerSQL, Object[] bindValues) throws SQLException {
      Connection con = this.getJDBCConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      boolean autoCommit = con.getAutoCommit();
      if (autoCommit) {
         con.setAutoCommit(false);
      }

      try {
         ps = con.prepareStatement(listenerSQL);
         int blobBindPos = this.doBinds(ps, bindValues);
         if (ObjectPersistenceHelper.mustSelectForInsert(con)) {
            ps.setBlob(blobBindPos, con.createBlob());
            ps.executeUpdate();
            ps = con.prepareStatement(((OracleSQLHelperImpl)this.sqlHelper).getSelectForInsertSQL());
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
               throw new SQLException("unable to insert listener");
            }

            Blob blob = rs.getBlob(1);
            ObjectPersistenceHelper.writeToBlob(blob, listener);
         } else {
            ObjectPersistenceHelper helper = new ObjectPersistenceHelper(listener);
            ps.setBinaryStream(blobBindPos, helper.getBinaryStream(), (int)helper.length());
            ps.executeUpdate();
         }

         if (autoCommit) {
            con.commit();
         }
      } catch (IOException var14) {
         if (DEBUG) {
            var14.printStackTrace();
         }

         if (autoCommit) {
            con.rollback();
         }

         throw new SQLException("unable to convert Object to Blob. Reason: " + var14.getMessage());
      } finally {
         if (autoCommit) {
            con.setAutoCommit(autoCommit);
         }

         this.closeResultSet(rs);
         this.closePreparedStatement(ps);
         this.closeSQLConnection(con);
      }

   }

   private void closePreparedStatement(PreparedStatement ps) {
      if (ps != null) {
         try {
            ps.close();
         } catch (SQLException var3) {
            if (DEBUG) {
               var3.printStackTrace();
            }
         }
      }

   }

   private void closeSQLConnection(Connection con) {
      if (this.connection == null) {
         if (con != null) {
            try {
               con.close();
            } catch (SQLException var3) {
               if (DEBUG) {
                  var3.printStackTrace();
               }
            }
         }

      }
   }

   private void closeResultSet(ResultSet rs) {
      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var3) {
            if (DEBUG) {
               var3.printStackTrace();
            }
         }
      }

   }

   private Connection getJDBCConnection() throws SQLException {
      if (this.connection != null) {
         return this.connection;
      } else if (this.ds != null) {
         return this.ds.getConnection();
      } else {
         JDBCDataSourceParamsBean params = this.jdbcResource.getJDBCResource().getJDBCDataSourceParams();
         String[] dataSourceNames = params.getJNDINames();
         Context ctx = null;

         Connection var4;
         try {
            if (dataSourceNames == null || dataSourceNames.length == 0) {
               throw new SQLException("Job Scheduler data source is invalid !");
            }

            ctx = new InitialContext();
            this.ds = (DataSource)ctx.lookup(dataSourceNames[0]);
            var4 = this.ds.getConnection();
         } catch (NamingException var13) {
            throw new SQLException("Got a NamingException while looking up JobScheduler datasource\n" + StackTraceUtils.throwable2StackTrace(var13));
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var12) {
               }
            }

         }

         return var4;
      }
   }

   private int doBinds(PreparedStatement ps, Object[] bindValues) throws SQLException {
      if (bindValues != null && bindValues.length != 0) {
         int blobPos = -1;
         int pos = 1;
         if (bindValues != null) {
            Object[] var5 = bindValues;
            int var6 = bindValues.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Object thisBindValue = var5[var7];
               if (thisBindValue == this.BLOB_MARKER) {
                  blobPos = pos;
               } else if (thisBindValue instanceof String) {
                  ps.setString(pos, (String)thisBindValue);
               } else if (thisBindValue instanceof Long) {
                  ps.setLong(pos, (Long)thisBindValue);
               }

               ++pos;
            }
         }

         if (blobPos == -1) {
            blobPos = pos;
         }

         return blobPos;
      } else {
         return 1;
      }
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[DBTimerBasisImpl] " + s);
   }

   public static TimerBasis getDBTimerBasis(Connection connection, String tableName, String domainName, String clusterName, String serverName) throws SQLException {
      return new DBTimerBasisImpl(connection, tableName, domainName, clusterName, serverName);
   }

   private DBTimerBasisImpl(Connection connection, String tableName, String domainName, String clusterName, String serverName) throws SQLException {
      this.connection = connection;
      this.jdbcResource = null;
      this.serverName = serverName;
      this.domainID = null;
      if (JDBCHelper.getDBMSType(connection.getMetaData(), (String[])null) == 1) {
         this.sqlHelper = new OracleSQLHelperImpl(tableName, domainName, clusterName);
      } else {
         this.sqlHelper = new GenericSQLHelperImpl(tableName, domainName, clusterName);
      }

   }
}
