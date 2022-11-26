package weblogic.store.io.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import oracle.ucp.jdbc.HarvestableConnection;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.store.common.StoreDebug;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.io.ByteBufferInputStream;

class ReservedConnection implements NakedTimerListener {
   private static int PING_INTERVAL_MILLIS = getPingInterval();
   private static final int PING_INTERVAL_MILLIS_DEF = 30000;
   private static final int PING_INTERVAL_MILLIS_MIN = 10;
   private static final int UPDATE_TYPE_COL = 1;
   private static final int UPDATE_HANDLE_COL = 2;
   private static final int UPDATE_RECORD_COL = 3;
   private static final int UPDATE_ROWID_COL = 4;
   private static final int MAX_IDLE_MILLIS;
   static final String ORACLE_JDBC_AUTOCOMMIT_SPEC_COMPLIANT = "oracle.jdbc.autoCommitSpecCompliant";
   private Connection connection;
   private DataSource dataSource;
   private long timestamp;
   private int waiterCount;
   private boolean isOpen;
   private final JDBCStoreIO jdbcStore;
   private boolean connectionReserved;
   private final boolean autoCommit;
   private int maxDeleteCount;
   private Timer pingTimer;
   private TimerManager timerManager;
   private int connNum;
   private static int nextConnNum;
   private byte[] chunk;
   private int retryPeriodMilliseconds;
   private int retryIntervalMilliseconds;
   private PreparedStatement insertRowStatement;
   private boolean clearInsertRowStatement;
   private PreparedStatement threeStepInsertStatement;
   private boolean clearThreeStepInsertStatement;
   private PreparedStatement selectForUpdateStatement;
   private boolean clearSelectForUpdateStatement;
   private PreparedStatement readOneRowStatement;
   private boolean clearReadOneRowStatement;
   private PreparedStatement deleteStatement;
   private boolean clearDeleteStatement;
   private PreparedStatement updateStatement;
   private boolean clearUpdateStatement;
   private PreparedStatement rowCountStatement;
   private boolean clearRowCountStatement;
   private PreparedStatement rowStatement;
   private boolean clearRowStatement;
   private int pingIntervalMillis;
   private int maxIdleMillis;
   private boolean tableLockStampingEnabled;
   private static Class clsWLDataSource;
   private static Method methodCreateConnection;
   static Method methodCreateConnectionToInstance;
   private static Class clsWLConnection;
   private static Method getVendorConnection;
   private static Method clearPreparedStatement;
   private static Class clsOracleConnection;
   private static Method getServerSessionInfo;
   private int oracleIdealChunkSize;
   private String instance;
   private volatile int generation;
   private boolean usingGridLinkDS;
   private boolean allowPiggybackCommit;
   private boolean piggybackCommitted;
   private boolean getConnectionOnDemand;

   ReservedConnection(JDBCStoreIO _jdbcStore, boolean _autoCommit, String affinity, int _retryPeriodMilliseconds, int _retryIntervalMilliseconds) throws JDBCStoreException, SQLException {
      this(_jdbcStore, _autoCommit, affinity, _retryPeriodMilliseconds, _retryIntervalMilliseconds, false);
   }

   ReservedConnection(JDBCStoreIO _jdbcStore, boolean _autoCommit, String affinity, int _retryPeriodMilliseconds, int _retryIntervalMilliseconds, boolean _getConnectionOnDemand) throws JDBCStoreException, SQLException {
      this.chunk = new byte[0];
      this.clearInsertRowStatement = true;
      this.clearThreeStepInsertStatement = true;
      this.clearSelectForUpdateStatement = true;
      this.clearReadOneRowStatement = true;
      this.clearDeleteStatement = true;
      this.clearUpdateStatement = true;
      this.clearRowCountStatement = true;
      this.clearRowStatement = true;
      this.pingIntervalMillis = PING_INTERVAL_MILLIS;
      this.maxIdleMillis = MAX_IDLE_MILLIS;
      this.tableLockStampingEnabled = false;
      this.oracleIdealChunkSize = -1;
      this.usingGridLinkDS = false;
      this.allowPiggybackCommit = false;
      this.piggybackCommitted = false;
      this.getConnectionOnDemand = false;
      this.jdbcStore = _jdbcStore;
      this.autoCommit = _autoCommit;
      this.dataSource = this.jdbcStore.getDataSource();
      this.retryPeriodMilliseconds = Math.max(_retryPeriodMilliseconds, 200);
      this.retryPeriodMilliseconds = Math.min(this.retryPeriodMilliseconds, 300000);
      this.retryIntervalMilliseconds = Math.max(_retryIntervalMilliseconds, 100);
      this.retryIntervalMilliseconds = Math.min(this.retryIntervalMilliseconds, 10000);
      this.getConnectionOnDemand = _getConnectionOnDemand;
      Class var7 = ReservedConnection.class;
      synchronized(ReservedConnection.class) {
         this.connNum = nextConnNum++;
      }

      if (!this.getConnectionOnDemand) {
         this.createConnection(affinity, false);
      }

      this.isOpen = true;
      this.timestamp = System.currentTimeMillis();
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   }

   private Connection invokeCreateConnectionToInstance(String instance, Properties additionalProperties) throws SQLException {
      try {
         return (Connection)methodCreateConnectionToInstance.invoke(this.dataSource, instance, additionalProperties);
      } catch (IllegalAccessException var5) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("invokeCreateConnectionToInstance failed", var5);
         }

         throw new RuntimeException("program error", var5);
      } catch (IllegalArgumentException var6) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("invokeCreateConnectionToInstance failed", var6);
         }

         throw new RuntimeException("program error", var6);
      } catch (InvocationTargetException var7) {
         for(Throwable cause = var7; cause != null; cause = ((Throwable)cause).getCause()) {
            if (cause instanceof SQLFeatureNotSupportedException || ((Throwable)cause).toString().contains("FeatureNotSupportedException")) {
               return null;
            }
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("invokeCreateConnectionToInstance failed", var7);
         }

         throw new SQLException("Error invoking WLDataSource.createConnectionToInstance", var7.getCause());
      }
   }

   private Connection invokeCreateConnection(Properties additionalProperties) throws SQLException {
      Connection conn = null;

      try {
         conn = (Connection)methodCreateConnection.invoke(this.dataSource, additionalProperties);
      } catch (IllegalAccessException var4) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("invokeCreateConnection failed", var4);
         }

         throw new RuntimeException("program error", var4);
      } catch (IllegalArgumentException var5) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("invokeCreateConnection failed", var5);
         }

         throw new RuntimeException("program error", var5);
      } catch (InvocationTargetException var6) {
         if (!(var6.getCause() instanceof SQLFeatureNotSupportedException)) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("invokeCreateConnection failed", var6);
            }

            throw new SQLException("Error invoking WLDataSource.createConnection", var6.getCause());
         }

         conn = null;
      }

      return conn;
   }

   int getRetryPeriodMillis() {
      return this.retryPeriodMilliseconds;
   }

   void createConnection(String affinity, boolean test) throws JDBCStoreException, SQLException {
      boolean creationSuccessful = false;
      Exception createException = null;
      Properties additionalProps = null;
      if (this.jdbcStore.isOraclePiggybackCommitEnabled) {
         additionalProps = new Properties();
         additionalProps.put("oracle.jdbc.autoCommitSpecCompliant", "false");
      }

      long startTimeMillis = System.currentTimeMillis();

      do {
         try {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("retrieving connection from data source, store=" + this.jdbcStore.getStoreName() + " table=" + this.jdbcStore.getTableRef(), (Throwable)null);
            }

            if (!clsWLDataSource.isInstance(this.dataSource)) {
               this.connection = this.dataSource.getConnection();
            } else {
               if (affinity != null) {
                  this.connection = this.invokeCreateConnectionToInstance(affinity, additionalProps);
                  if (this.connection != null) {
                     this.usingGridLinkDS = true;
                     if (additionalProps != null && !this.autoCommit) {
                        this.allowPiggybackCommit = true;
                     }
                  }
               }

               if (this.connection == null && additionalProps != null) {
                  this.connection = this.invokeCreateConnection(additionalProps);
                  if (this.connection != null && !this.autoCommit) {
                     this.allowPiggybackCommit = true;
                  }
               }

               if (this.connection == null) {
                  this.connection = this.dataSource.getConnection();
               }
            }

            if (this.connection == null) {
               throw new SQLException("SQL connection is null : possible mismatch of driver and url");
            }

            if (this.connection.getAutoCommit() != this.autoCommit) {
               this.connection.setAutoCommit(this.autoCommit);
            }

            if (this.connection instanceof HarvestableConnection) {
               HarvestableConnection hc = (HarvestableConnection)this.connection;
               if (hc.isConnectionHarvestable()) {
                  hc.setConnectionHarvestable(false);
               }
            }

            if (!this.getConnectionOnDemand) {
               this.queryInstance(affinity);
            }

            if (test) {
               this.pingDatabase();
            }

            creationSuccessful = true;
            createException = null;
         } catch (SQLException var15) {
            createException = var15;
         } catch (RuntimeException var16) {
            createException = new SQLExceptionWrapper(var16);
         } catch (Throwable var17) {
            createException = new SQLExceptionWrapper(new RuntimeException(var17));
         } finally {
            if (!creationSuccessful) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  this.debug("connection retrieval from data source failed, store=" + this.jdbcStore.getStoreName() + " table=" + this.jdbcStore.getTableRef(), (Throwable)createException);
               }

               if (this.connection != null) {
                  this.closeInner(true);
               }
            }

         }

         if (this.connection == null) {
            if ((long)this.retryIntervalMilliseconds + (System.currentTimeMillis() - startTimeMillis) >= (long)this.retryPeriodMilliseconds) {
               break;
            }

            try {
               Thread.sleep((long)this.retryIntervalMilliseconds);
            } catch (InterruptedException var18) {
            }
         }
      } while(!creationSuccessful && System.currentTimeMillis() - startTimeMillis < (long)this.retryPeriodMilliseconds);

      if (createException != null) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("connection creation failed, store=" + this.jdbcStore.getStoreName() + " table=" + this.jdbcStore.getTableRef(), (Throwable)createException);
         }

         if (createException instanceof JDBCStoreException) {
            throw (JDBCStoreException)createException;
         } else {
            throw (SQLException)createException;
         }
      } else {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Created new connection: ");
            if (this.usingGridLinkDS) {
               sb.append(" RAC instance:").append(this.getInstance());
            }

            sb.append(" allowPiggybackCommit: ").append(this.allowPiggybackCommit);
            sb.append(" autoCommit: ").append(this.autoCommit);
            sb.append(" table: ").append(this.jdbcStore.getTableRef());
            this.debug(sb.toString());
         }

      }
   }

   private void queryInstance(String affinity) throws SQLException {
      if (clsOracleConnection != null && clsOracleConnection.isAssignableFrom(this.connection.getClass())) {
         try {
            Properties props = (Properties)getServerSessionInfo.invoke(this.connection);
            this.instance = props == null ? null : props.getProperty("INSTANCE_NAME");
            if (StoreDebug.storeIOPhysical.isDebugEnabled() && this.usingGridLinkDS) {
               this.debug("#!JDBC STORE RAC AFFINITY DEBUG!# expectedInstance=" + affinity + ", actualInstance=" + this.instance);
            }
         } catch (IllegalAccessException var3) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("getInstance failed", var3);
            }

            throw new RuntimeException("program error", var3);
         } catch (IllegalArgumentException var4) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("getInstance failed", var4);
            }

            throw new RuntimeException("program error", var4);
         } catch (InvocationTargetException var5) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("getInstance failed", var5);
            }

            throw new SQLException("Error invoking OracleConnection.getServerSessionInfo", var5.getCause());
         }
      }

   }

   String getInstance() {
      return this.instance;
   }

   boolean isUsingGridLinkDS() {
      return this.usingGridLinkDS;
   }

   void setUsingGridLinkDS(boolean usingGridLinkDS) {
      this.usingGridLinkDS = usingGridLinkDS;
   }

   int getGeneration() {
      return this.generation;
   }

   void setGeneration(int generation) {
      this.generation = generation;
   }

   void commit() throws SQLException {
      synchronized(this) {
         if (!this.connectionReserved) {
            throw new AssertionError();
         }
      }

      if (this.autoCommit) {
         throw new AssertionError();
      } else if (this.piggybackCommitted) {
         this.piggybackCommitted = false;
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            this.debugVerbose("local commit skipped, piggyback-committed");
         }

      } else {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            this.debugVerbose("local commit");
         }

         try {
            this.connection.commit();
         } catch (RuntimeException var3) {
            throw new SQLExceptionWrapper(var3);
         } catch (Throwable var4) {
            throw new SQLExceptionWrapper(new RuntimeException(var4));
         }
      }
   }

   boolean isAutoCommit() {
      return this.autoCommit;
   }

   boolean isAllowPiggybackCommit() {
      return this.allowPiggybackCommit;
   }

   DatabaseMetaData getMetaData() throws SQLException {
      synchronized(this) {
         if (!this.connectionReserved) {
            throw new AssertionError();
         }
      }

      try {
         return this.connection.getMetaData();
      } catch (RuntimeException var3) {
         throw new SQLExceptionWrapper(var3);
      } catch (Throwable var4) {
         throw new SQLExceptionWrapper(new RuntimeException(var4));
      }
   }

   void debug(DebugLogger dl, String s, Throwable t) {
      dl.debug("JDBC " + this.jdbcStore.getStoreName() + (this.autoCommit ? " autoOn  " : " autoOff ") + "conn" + this.connNum + ": " + s, t);
   }

   void debug(String s, Throwable t) {
      this.debug(StoreDebug.storeIOPhysical, s, t);
   }

   void debug(String s) {
      this.debug(StoreDebug.storeIOPhysical, s, (Throwable)null);
   }

   void debugVerbose(String s) {
      this.debugVerbose(s, (Throwable)null);
   }

   void debugVerbose(String s, Throwable t) {
      this.debug(StoreDebug.storeIOPhysicalVerbose, s, (Throwable)null);
   }

   synchronized void startPingTimer() {
      this.pingTimer = this.timerManager.schedule(this, (long)this.pingIntervalMillis);
   }

   synchronized void startPingTimer(boolean tableLockStampingEnabled, int pingIntervalMillis) {
      this.tableLockStampingEnabled = tableLockStampingEnabled;
      this.pingIntervalMillis = pingIntervalMillis;
      this.maxIdleMillis = pingIntervalMillis / 2 + 1;
      this.pingTimer = this.timerManager.schedule(this, (long)pingIntervalMillis);
   }

   void setMaxDeleteCount(int i) {
      if (this.maxDeleteCount != 0) {
         throw new AssertionError();
      } else {
         this.maxDeleteCount = i;
      }
   }

   private void checkOpen() throws JDBCStoreException {
      if (!this.isOpen) {
         throw new JDBCStoreException(this.jdbcStore, "connection is closed");
      }
   }

   Object lock() throws JDBCStoreException {
      return this.lock(false, false);
   }

   Object lock(boolean ignoreIfActive, boolean skipPing) throws JDBCStoreException {
      boolean doPing = false;
      synchronized(this) {
         this.checkOpen();
         if (ignoreIfActive && this.connectionReserved) {
            return null;
         }

         this.innerLock(false);
         if (!skipPing) {
            doPing = !this.getConnectionOnDemand && (this.connection == null || System.currentTimeMillis() - this.timestamp >= (long)this.maxIdleMillis);
         }
      }

      boolean doCleanup;
      if (this.getConnectionOnDemand && this.connection == null) {
         doCleanup = true;
         boolean var26 = false;

         try {
            var26 = true;
            if (StoreDebug.storeConnectionCaching.isDebugEnabled()) {
               StringBuilder sb = new StringBuilder();
               sb.append("Retrieving db connection for store \"");
               sb.append(this.jdbcStore.getStoreShortName()).append("\" from the pool.");
               StoreDebug.storeConnectionCaching.debug(sb.toString());
            }

            this.createConnection((String)null, false);
            doCleanup = false;
            var26 = false;
         } catch (SQLException | JDBCStoreException var31) {
            if (var31 instanceof JDBCStoreException) {
               throw (JDBCStoreException)var31;
            }

            throw new JDBCStoreException(this.jdbcStore, "Failed to open connection", var31);
         } finally {
            if (var26) {
               if (doCleanup) {
                  synchronized(this) {
                     this.unlock(this);
                  }
               }

            }
         }

         if (doCleanup) {
            synchronized(this) {
               this.unlock(this);
            }
         }
      }

      if (!skipPing) {
         doCleanup = true;

         try {
            if (this.usingGridLinkDS && this.jdbcStore.getRACState().needToResync(this.generation)) {
               this.closeInner(false);
               this.resetConnection();
            } else if (doPing) {
               this.testConnection();
            }

            doCleanup = false;
         } finally {
            if (doCleanup) {
               this.unlock(this);
            }

         }
      }

      synchronized(this) {
         this.timestamp = System.currentTimeMillis();
         return this;
      }
   }

   synchronized ReservedConnection innerLock(boolean updateTimeStamp) throws JDBCStoreException {
      boolean doCleanup = true;

      try {
         ++this.waiterCount;

         while(true) {
            if (!this.connectionReserved) {
               this.checkOpen();
               this.connectionReserved = true;
               doCleanup = false;
               break;
            }

            try {
               this.wait(100L);
            } catch (InterruptedException var7) {
            }

            this.checkOpen();
         }
      } finally {
         if (doCleanup) {
            --this.waiterCount;
         }

      }

      if (updateTimeStamp) {
         this.timestamp = System.currentTimeMillis();
      }

      return this;
   }

   private void testConnection() throws JDBCStoreException {
      if (this.connection == null) {
         this.resetConnection();
      } else {
         try {
            this.pingDatabase();
         } catch (SQLException var2) {
            this.closeInner(true);
            this.resetConnection();
         }

      }
   }

   private void resetConnection() throws JDBCStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.debug("attempting reset connection");
      }

      try {
         Thread.sleep((long)this.retryIntervalMilliseconds);
      } catch (InterruptedException var2) {
      }

      if (this.usingGridLinkDS) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("attempting to reset RAC connection");
         }

         try {
            this.jdbcStore.getRACState().failover(this);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("reset RAC connection OK");
            }
         } catch (SQLException var3) {
            if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
               this.debugVerbose("reset RAC connection failed", var3);
            } else if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("reset RAC connection failed");
            }

            throw new JDBCStoreException(this.jdbcStore, "reset RAC connection failed", var3);
         }
      } else {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("attempting to reset Non-RAC connection");
         }

         try {
            this.createConnection((String)null, true);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("reset Non-RAC connection OK");
            }
         } catch (SQLException var4) {
            if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
               this.debugVerbose("reset Non-RAC connection failed", var4);
            } else if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.debug("reset Non-RAC connection failed");
            }

            this.closeInner(true);
            throw new JDBCStoreException(this.jdbcStore, "reset Non-RAC connection failed", var4);
         }
      }

   }

   protected synchronized Connection getConnection() {
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         return this.connection;
      }
   }

   void unlock(Object conn, boolean invalidate) {
      synchronized(this) {
         if (conn != this) {
            throw new AssertionError("Incorrect ReservedConnection specified.");
         }

         if (this.waiterCount == 0) {
            throw new AssertionError("No waiters waiting.");
         }
      }

      StringBuilder sb;
      if (invalidate) {
         if (StoreDebug.storeConnectionCaching.isDebugEnabled()) {
            sb = new StringBuilder();
            sb.append("Invalidating db connection for store \"");
            sb.append(this.jdbcStore.getStoreName());
            StoreDebug.storeConnectionCaching.debug(sb.toString());
         }

         this.closeInner(true);
      } else if (this.getConnectionOnDemand) {
         if (StoreDebug.storeConnectionCaching.isDebugEnabled()) {
            sb = new StringBuilder();
            sb.append("Releasing db connection for store \"");
            sb.append(this.jdbcStore.getStoreShortName());
            sb.append("\" back to the pool.");
            StoreDebug.storeConnectionCaching.debug(sb.toString());
         }

         this.closeInner(false);
      }

      synchronized(this) {
         --this.waiterCount;
         this.timestamp = System.currentTimeMillis();
         this.connectionReserved = false;
         if (this.waiterCount > 0) {
            this.notify();
         }

      }
   }

   synchronized void unlock(Object conn) {
      this.unlock(conn, false);
   }

   private final void closeInner(boolean invalidateConnection) {
      if (invalidateConnection && this.connection != null) {
         try {
            this.connection.rollback();
         } catch (Throwable var6) {
         }

         if (clsWLConnection != null && clsWLConnection.isInstance(this.connection)) {
            try {
               getVendorConnection.invoke(this.connection, (Object[])null);
            } catch (Exception var5) {
            }
         }

         this.clearDeleteStatement = true;
         this.clearInsertRowStatement = true;
         this.clearReadOneRowStatement = true;
         this.clearRowCountStatement = true;
         this.clearRowStatement = true;
         this.clearSelectForUpdateStatement = true;
         this.clearThreeStepInsertStatement = true;
         this.clearUpdateStatement = true;
      }

      JDBCHelper.close((Statement)this.insertRowStatement);
      JDBCHelper.close((Statement)this.updateStatement);
      JDBCHelper.close((Statement)this.readOneRowStatement);
      JDBCHelper.close((Statement)this.deleteStatement);
      JDBCHelper.close((Statement)this.rowCountStatement);
      JDBCHelper.close((Statement)this.selectForUpdateStatement);
      JDBCHelper.close((Statement)this.rowStatement);
      JDBCHelper.close((Statement)this.threeStepInsertStatement);
      JDBCHelper.close(this.connection);
      synchronized(this) {
         this.insertRowStatement = null;
         this.updateStatement = null;
         this.readOneRowStatement = null;
         this.deleteStatement = null;
         this.rowCountStatement = null;
         this.rowStatement = null;
         this.selectForUpdateStatement = null;
         this.threeStepInsertStatement = null;
         this.connection = null;
         this.instance = null;
      }
   }

   void close(boolean invalidateConnection) {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.debug("close");
      }

      synchronized(this) {
         this.isOpen = false;
         if (this.pingTimer != null) {
            this.pingTimer.cancel();
         }

         this.pingTimer = null;
         this.closeInner(invalidateConnection);
      }
   }

   public void timerExpired(Timer timer) {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         this.debugVerbose("timerExpired is called");
      }

      if (this.tableLockStampingEnabled) {
         synchronized(this) {
            if (!this.isOpen) {
               return;
            }
         }

         this.jdbcStore.updateTableOwnershipFromTimer();
      }

      synchronized(this) {
         if (!this.isOpen) {
            return;
         }

         if (this.connectionReserved || System.currentTimeMillis() - this.timestamp < (long)this.maxIdleMillis) {
            this.pingTimer = this.timerManager.schedule(this, (long)this.pingIntervalMillis);
            return;
         }
      }

      try {
         Object connLock = this.lock(true, false);
         if (connLock != null) {
            this.unlock(connLock);
         }
      } catch (Throwable var7) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("In timerExpired method, connection locking failed", var7);
         }
      }

      synchronized(this) {
         if (this.isOpen) {
            this.pingTimer = this.timerManager.schedule(this, (long)this.pingIntervalMillis);
         }
      }
   }

   void failbackToConnection(String originalInstance, Connection conn, int newGeneration) {
      this.closeInner(false);
      synchronized(this) {
         this.instance = originalInstance;
         this.connection = conn;
         this.timestamp = System.currentTimeMillis();
         this.generation = newGeneration;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.debug("failback to connection with instance " + this.instance);
      }

   }

   PreparedStatement prepareStatement(String sql) throws SQLException {
      SQLException sqlExp = null;

      PreparedStatement var3;
      try {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("creating prepared statement <" + sql + ">");
         }

         var3 = this.connection.prepareStatement(sql);
      } catch (SQLException var9) {
         sqlExp = var9;
         throw var9;
      } catch (RuntimeException var10) {
         sqlExp = new SQLExceptionWrapper(var10);
         throw sqlExp;
      } catch (Throwable var11) {
         sqlExp = new SQLExceptionWrapper(new RuntimeException(var11));
         throw sqlExp;
      } finally {
         if (sqlExp != null && StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("prepare statement failed for SQL: " + sql, (Throwable)sqlExp);
         }

      }

      return var3;
   }

   private void clearStatementCacheFor(String sql) {
      if (clsWLConnection != null && clsWLConnection.isInstance(this.connection)) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Clearing the statement cache for SQL: " + sql);
         }

         try {
            Object[] args = new Object[]{sql};
            clearPreparedStatement.invoke(this.connection, args);
         } catch (Exception var3) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("Prepared statement cache clear for \"" + sql + "\" failed.", var3);
            }
         }
      }

   }

   synchronized PreparedStatement getInsertRowStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.insertRowStatement == null) {
            String sql = "INSERT INTO " + this.jdbcStore.getTableDMLIdentifier() + " (" + "id,type,handle,record" + ") VALUES (?,?,?,?)";
            if (this.clearInsertRowStatement) {
               this.clearStatementCacheFor(sql);
               this.clearInsertRowStatement = !this.getConnectionOnDemand;
            }

            this.insertRowStatement = this.prepareStatement(sql);
         }

         return this.insertRowStatement;
      }
   }

   synchronized PreparedStatement getThreeStepInsertStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.threeStepInsertStatement == null) {
            String sql = "INSERT INTO " + this.jdbcStore.getTableDMLIdentifier() + " (" + "id,type,handle,record" + ") VALUES (?,?,?,EMPTY_BLOB())";
            if (this.clearThreeStepInsertStatement) {
               this.clearStatementCacheFor(sql);
               this.clearThreeStepInsertStatement = !this.getConnectionOnDemand;
            }

            this.threeStepInsertStatement = this.prepareStatement(sql);
         }

         return this.threeStepInsertStatement;
      }
   }

   private synchronized PreparedStatement getSelectForUpdateStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.selectForUpdateStatement == null) {
            String sql = "SELECT record FROM " + this.jdbcStore.getTableDMLIdentifier() + " WHERE " + "id" + " = ? FOR UPDATE";
            if (this.clearSelectForUpdateStatement) {
               this.clearStatementCacheFor(sql);
               this.clearSelectForUpdateStatement = !this.getConnectionOnDemand;
            }

            this.selectForUpdateStatement = this.prepareStatement(sql);
         }

         return this.selectForUpdateStatement;
      }
   }

   synchronized PreparedStatement getReadOneRowStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.readOneRowStatement == null) {
            String sql = "SELECT id,type,handle,record FROM " + this.jdbcStore.getTableDMLIdentifier() + " WHERE " + "id" + " = ?";
            if (this.clearReadOneRowStatement) {
               this.clearStatementCacheFor(sql);
               this.clearReadOneRowStatement = !this.getConnectionOnDemand;
            }

            this.readOneRowStatement = this.prepareStatement(sql);
         }

         return this.readOneRowStatement;
      }
   }

   synchronized PreparedStatement getDeleteStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.deleteStatement == null) {
            String deleteSuffix = " WHERE id IN ( ?";

            for(int i = 1; i < this.maxDeleteCount; ++i) {
               deleteSuffix = deleteSuffix + " , ?";
            }

            deleteSuffix = deleteSuffix + " )";
            String sql = "DELETE FROM " + this.jdbcStore.getTableDMLIdentifier() + deleteSuffix;
            if (this.clearDeleteStatement) {
               this.clearStatementCacheFor(sql);
               this.clearDeleteStatement = !this.getConnectionOnDemand;
            }

            this.deleteStatement = this.prepareStatement(sql);
         }

         return this.deleteStatement;
      }
   }

   synchronized PreparedStatement getUpdateStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.updateStatement == null) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(this.jdbcStore.getTableDMLIdentifier()).append(" SET ");
            sql.append("type").append(" = ? , ");
            sql.append("handle").append(" = ? , ");
            sql.append("record").append(" = ? ");
            sql.append("WHERE ").append("id").append(" = ?");
            if (this.clearUpdateStatement) {
               this.clearStatementCacheFor(sql.toString());
               this.clearUpdateStatement = !this.getConnectionOnDemand;
            }

            this.updateStatement = this.prepareStatement(sql.toString());
         }

         return this.updateStatement;
      }
   }

   private synchronized PreparedStatement getRowCountStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError();
      } else {
         if (this.rowCountStatement == null) {
            String sql = "SELECT COUNT(*) FROM " + this.jdbcStore.getTableDMLIdentifier();
            if (this.clearRowCountStatement) {
               this.clearStatementCacheFor(sql);
               this.clearRowCountStatement = !this.getConnectionOnDemand;
            }

            this.rowCountStatement = this.prepareStatement(sql);
         }

         return this.rowCountStatement;
      }
   }

   private synchronized PreparedStatement getRowStatement() throws JDBCStoreException, SQLException {
      this.checkOpen();
      if (!this.connectionReserved) {
         throw new AssertionError("Not reserved");
      } else {
         if (this.rowStatement == null) {
            String sql = "SELECT id FROM " + this.jdbcStore.getTableDMLIdentifier() + " where id = -1";
            if (this.clearRowStatement) {
               this.clearStatementCacheFor(sql);
               this.clearRowStatement = !this.getConnectionOnDemand;
            }

            this.rowStatement = this.prepareStatement(sql);
         }

         return this.rowStatement;
      }
   }

   int getRowCount() throws JDBCStoreException, SQLException {
      PreparedStatement st = null;
      ResultSet results = null;

      int var4;
      try {
         st = this.getRowCountStatement();
         results = st.executeQuery();
         if (!results.next()) {
            throw new SQLException("count failed");
         }

         int ret = results.getInt(1);
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            this.debugVerbose("ping row count=" + ret);
         }

         var4 = ret;
      } catch (RuntimeException var9) {
         throw new SQLExceptionWrapper(var9);
      } catch (Throwable var10) {
         throw new SQLExceptionWrapper(new RuntimeException(var10));
      } finally {
         JDBCHelper.close(results);
      }

      return var4;
   }

   private void pingDatabase() throws SQLException {
      SQLException sqlEx = null;
      boolean pingOK = false;
      PreparedStatement st = null;
      ResultSet results = null;

      try {
         st = this.getRowStatement();
         results = st.executeQuery();
         if (results == null) {
            if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
               this.debugVerbose("database ping results were null; jdbc driver out of spec");
            }

            throw new SQLException("database ping failed");
         }

         pingOK = true;
      } catch (SQLException var11) {
         sqlEx = var11;
      } catch (RuntimeException var12) {
         sqlEx = new SQLExceptionWrapper(var12);
      } catch (Throwable var13) {
         sqlEx = new SQLExceptionWrapper(new RuntimeException(var13));
      } finally {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("database ping " + (pingOK ? "succeeded" : "failed"), (Throwable)sqlEx);
         }

         JDBCHelper.close(results);
         if (sqlEx != null) {
            throw sqlEx;
         }

      }

   }

   void fillInsertStatement(int row, int type, int handle, ByteBuffer[] bb, boolean forceExecuteUpdate, boolean piggybackCommitIfPossible) throws SQLException, JDBCStoreException {
      if (this.insertRowStatement == null) {
         this.getInsertRowStatement();
      }

      try {
         this.insertRowStatement.setInt(1, row);
         this.insertRowStatement.setInt(2, type);
         this.insertRowStatement.setInt(3, handle);
         if (bb == null || bb.length == 0) {
            bb = new ByteBuffer[]{ByteBuffer.allocate(1)};
            bb[0].position(0);
         }

         int totalBytes = 0;

         for(int i = 0; i < bb.length; ++i) {
            totalBytes += bb[i].remaining();
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.debug("insert  r=" + row + " t=" + type + " h=" + handle + " b=" + totalBytes);
         }

         ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
         this.insertRowStatement.setBinaryStream(4, bbis, totalBytes);
         if (forceExecuteUpdate) {
            this.executeUpdateForStatement(this.insertRowStatement, piggybackCommitIfPossible);
         }

      } catch (RuntimeException var9) {
         throw new SQLExceptionWrapper(var9);
      } catch (Throwable var10) {
         throw new SQLExceptionWrapper(new RuntimeException(var10));
      }
   }

   final void fillUpdateStatement(int row, int type, int handle, ByteBuffer[] bb) throws SQLException, JDBCStoreException {
      if (this.updateStatement == null) {
         this.getUpdateStatement();
      }

      try {
         this.updateStatement.clearParameters();
         this.updateStatement.setInt(1, type);
         this.updateStatement.setInt(2, handle);
         if (bb == null || bb.length == 0) {
            bb = new ByteBuffer[]{ByteBuffer.allocate(1)};
            bb[0].position(0);
         }

         int totalBytes = 0;

         for(int i = 0; i < bb.length; ++i) {
            totalBytes += bb[i].remaining();
         }

         ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("update  r=").append(row);
            sb.append(" t=").append(type);
            sb.append(" h=").append(handle);
            sb.append(" b=").append(totalBytes);
            this.debug(sb.toString());
         }

         this.updateStatement.setBinaryStream(3, bbis, totalBytes);
         this.updateStatement.setInt(4, row);
      } catch (RuntimeException var8) {
         throw new SQLExceptionWrapper(var8);
      } catch (Throwable var9) {
         throw new SQLExceptionWrapper(new RuntimeException(var9));
      }
   }

   final void executeUpdateForStatement(PreparedStatement pStmt, boolean piggybackCommitIfPossible) throws SQLException {
      if (this.allowPiggybackCommit && piggybackCommitIfPossible) {
         if (this.piggybackCommitted) {
            throw new AssertionError();
         }

         this.connection.setAutoCommit(true);
         pStmt.executeUpdate();
         this.connection.setAutoCommit(false);
         this.piggybackCommitted = true;
      } else {
         pStmt.executeUpdate();
      }

   }

   final void executeBatchForStatement(PreparedStatement pStmt, boolean piggybackCommitIfPossible) throws SQLException {
      if (this.allowPiggybackCommit && piggybackCommitIfPossible) {
         if (this.piggybackCommitted) {
            throw new AssertionError();
         }

         this.connection.setAutoCommit(true);
         pStmt.executeBatch();
         this.connection.setAutoCommit(false);
         this.piggybackCommitted = true;
      } else {
         pStmt.executeBatch();
      }

   }

   void threeStepInsert(int row, int type, int handle, ByteBuffer[] bb) throws SQLException, JDBCStoreException {
      if (this.threeStepInsertStatement == null) {
         this.getThreeStepInsertStatement();
      }

      try {
         this.threeStepInsertStatement.setInt(1, row);
         this.threeStepInsertStatement.setInt(2, type);
         this.threeStepInsertStatement.setInt(3, handle);
         this.threeStepInsertStatement.executeUpdate();
         if (this.selectForUpdateStatement == null) {
            this.getSelectForUpdateStatement();
         }

         this.selectForUpdateStatement.setInt(1, row);
         Blob blob = null;
         ResultSet rs = this.selectForUpdateStatement.executeQuery();

         try {
            if (!rs.next()) {
               throw new SQLException("error, row " + row + " not found");
            }

            blob = rs.getBlob(1);
         } finally {
            rs.close();
         }

         if (this.oracleIdealChunkSize == -1) {
            this.oracleIdealChunkSize = 32768;
         }

         if (this.chunk.length < this.oracleIdealChunkSize) {
            this.chunk = new byte[this.oracleIdealChunkSize];
         }

         ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
         OutputStream os = blob.setBinaryStream(1L);

         try {
            int length;
            try {
               while((length = bbis.read(this.chunk, 0, this.oracleIdealChunkSize)) != -1) {
                  os.write(this.chunk, 0, length);
               }
            } catch (IOException var27) {
               SQLException sqle = new SQLException(var27.toString());
               sqle.initCause(var27);
               throw sqle;
            }
         } finally {
            try {
               os.close();
            } catch (IOException var26) {
               throw new JDBCStoreException(this.jdbcStore, var26.toString(), var26);
            }
         }

      } catch (RuntimeException var30) {
         throw new SQLExceptionWrapper(var30);
      } catch (Throwable var31) {
         throw new SQLExceptionWrapper(new RuntimeException(var31));
      }
   }

   void executeDDLStream(InputStream is) throws SQLException, IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      String line;
      while((line = gatherLine(reader)) != null) {
         this.executeDDLString(line);
      }

   }

   void executeDDLString(String line) throws SQLException {
      synchronized(this) {
         if (!this.connectionReserved) {
            throw new AssertionError();
         }
      }

      line = line.replaceAll("\\$INDEX", this.jdbcStore.getIndexDDLIdentifier());
      line = line.replaceAll("\\$TABLE", this.jdbcStore.getTableDDLIdentifier());
      line = line.replaceAll("\\s+", " ");
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.debug("exec DDL <" + line + ">");
      }

      Statement statement = null;

      try {
         statement = this.connection.createStatement();
         statement.execute(line);
      } catch (RuntimeException var9) {
         throw new SQLExceptionWrapper(var9);
      } catch (Throwable var10) {
         throw new SQLExceptionWrapper(new RuntimeException(var10));
      } finally {
         JDBCHelper.close(statement);
      }

   }

   private static String gatherLine(BufferedReader br) throws IOException {
      StringBuffer result = null;
      char terminator = 59;

      String line;
      while((line = br.readLine()) != null) {
         if (line.trim().length() > 0 && !line.startsWith("#")) {
            char lastChar = line.charAt(line.length() - 1);
            boolean termination = lastChar == terminator;
            if (result == null) {
               result = new StringBuffer("");
            }

            result.append(termination ? line.substring(0, line.length() - 1) : line + " ");
            if (termination) {
               break;
            }
         }
      }

      return result != null ? result.toString() : null;
   }

   private static int getPingInterval() {
      int minRefreshMillis = 30000;

      try {
         String propVal = System.getProperty("weblogic.store.jdbc.RefreshMillis");
         if (propVal != null) {
            minRefreshMillis = Integer.parseInt(propVal);
            minRefreshMillis = Math.max(minRefreshMillis, 10);
         }
      } catch (NumberFormatException var2) {
         var2.printStackTrace();
      }

      return minRefreshMillis;
   }

   static {
      MAX_IDLE_MILLIS = PING_INTERVAL_MILLIS / 3 + 1;
      nextConnNum = 1;
      clsWLDataSource = null;
      methodCreateConnection = null;
      methodCreateConnectionToInstance = null;
      clsWLConnection = null;
      getVendorConnection = null;
      clearPreparedStatement = null;
      clsOracleConnection = null;
      getServerSessionInfo = null;

      try {
         clsWLDataSource = Class.forName("weblogic.jdbc.extensions.WLDataSource");
         methodCreateConnectionToInstance = clsWLDataSource.getMethod("createConnectionToInstance", String.class, Properties.class);
         methodCreateConnection = clsWLDataSource.getMethod("createConnection", Properties.class);
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException("program error", var5);
      } catch (NoSuchMethodException var6) {
         throw new RuntimeException("program error", var6);
      }

      try {
         clsWLConnection = Class.forName("weblogic.jdbc.extensions.WLConnection");
         getVendorConnection = clsWLConnection.getMethod("getVendorConnection", (Class[])null);
         Class[] partypes = new Class[]{String.class};
         clearPreparedStatement = clsWLConnection.getMethod("clearPreparedStatement", partypes);
      } catch (ClassNotFoundException var3) {
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException("program error", var4);
      }

      try {
         clsOracleConnection = Class.forName("oracle.jdbc.internal.OracleConnection");
         getServerSessionInfo = clsOracleConnection.getMethod("getServerSessionInfo");
      } catch (ClassNotFoundException var1) {
      } catch (NoSuchMethodException var2) {
         throw new RuntimeException("program error", var2);
      }

   }
}
