package weblogic.jdbc.common.rac.internal;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.replay.OracleDataSource;
import oracle.jdbc.replay.OracleDataSourceImpl;
import oracle.jdbc.replay.ReplayableConnection;
import oracle.jdbc.replay.driver.ReplayLoggerFactory;
import oracle.jdbc.replay.internal.ConnectionInitializationCallback;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnectionHelper;
import weblogic.common.ResourceException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.OracleHelper;
import weblogic.jdbc.common.internal.OraclePool;
import weblogic.jdbc.common.internal.ReplayStatisticsSnapshot;
import weblogic.utils.StackTraceUtils;

public class OracleHelperImpl implements OracleHelper, ConnectionInitializationCallback {
   OraclePool pool;
   static final DebugLogger JDBCREPLAY = DebugLogger.getDebugLogger("DebugJDBCReplay");
   private AtomicBoolean replayDriver;

   public OracleHelperImpl(OraclePool pool) throws ResourceException {
      this.pool = pool;
      this.replayDriver = new AtomicBoolean();
      this.checkForReplayDriver(pool);
   }

   private void checkForReplayDriver(OraclePool pool) {
      try {
         Class driverClass = pool.getDriverClass();
         if (driverClass == null || !OracleDataSourceImpl.class.isAssignableFrom(driverClass) && !driverClass.getName().startsWith("oracle.jdbc.replay")) {
            this.replayDriver = new AtomicBoolean(false);
         } else {
            this.replayDriver = new AtomicBoolean(true);
            if (JDBCREPLAY.isDebugEnabled()) {
               try {
                  JDBCREPLAY.debug("enabling Replay logging");
                  ReplayLoggerFactory.setLogLevel(Level.FINEST);
               } catch (Exception var4) {
                  JDBCREPLAY.debug(var4.toString());
               }
            }
         }
      } catch (ClassNotFoundException var5) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JDBCREPLAY.debug("error trying to determine replay driver: " + StackTraceUtils.throwable2StackTrace(var5));
         }
      }

   }

   public boolean isReplayDriver() {
      return this.replayDriver.get();
   }

   public void replayBeginRequest(ConnectionEnv rce, int replayInitiationTimeout) throws SQLException {
      if (rce != null) {
         rce.replayAttemptCount = 0;
      }

      Connection replayableConnection = rce.conn.jconn;
      if (replayableConnection == null || !(replayableConnection instanceof ReplayableConnection) && rce.conn.oracleBeginRequest == null) {
         if (JDBCREPLAY.isDebugEnabled()) {
            JDBCREPLAY.debug("NOT invoking beginRequest on connection " + replayableConnection);
         }

      } else {
         if (JDBCREPLAY.isDebugEnabled()) {
            JDBCREPLAY.debug("invoking beginRequest on connection " + replayableConnection + " with timeout " + replayInitiationTimeout);
         }

         try {
            if (replayableConnection instanceof ReplayableConnection) {
               ((oracle.jdbc.replay.internal.ReplayableConnection)replayableConnection).setReplayInitiationTimeout(replayInitiationTimeout);
            }

            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + replayableConnection + "] beginRequest()");
            }

            if (replayableConnection instanceof ReplayableConnection) {
               ((ReplayableConnection)replayableConnection).beginRequest();
            } else {
               ((OracleConnection)replayableConnection).beginRequest();
            }

            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + replayableConnection + "] beginRequest returns");
            }
         } catch (AbstractMethodError var5) {
            rce.conn.oracleBeginRequest = null;
         } catch (SQLException var6) {
            if (JDBCREPLAY.isDebugEnabled()) {
               JDBCREPLAY.debug("beginRequest failed: conn=" + replayableConnection + ", exception=" + StackTraceUtils.throwable2StackTrace(var6));
            }

            throw var6;
         }

      }
   }

   public void replayEndRequest(ConnectionEnv rce) throws SQLException {
      Connection replayableConnection = rce.conn.jconn;
      if (replayableConnection != null && (replayableConnection instanceof ReplayableConnection || rce.conn.oracleBeginRequest != null)) {
         if (JDBCREPLAY.isDebugEnabled()) {
            JDBCREPLAY.debug("invoking endRequest on connection " + replayableConnection);
         }

         try {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + replayableConnection + "] endRequest()");
            }

            if (replayableConnection instanceof ReplayableConnection) {
               rce.clearCache();
               ((ReplayableConnection)replayableConnection).endRequest();
            } else {
               ((OracleConnection)replayableConnection).endRequest();
            }

            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + replayableConnection + "] endRequest returns");
            }

         } catch (SQLException var4) {
            if (JDBCREPLAY.isDebugEnabled()) {
               JDBCREPLAY.debug("endRequest failed: conn=" + replayableConnection + ", exception=" + StackTraceUtils.throwable2StackTrace(var4));
            }

            throw var4;
         }
      } else {
         if (JDBCREPLAY.isDebugEnabled()) {
            JDBCREPLAY.debug("NOT invoking endRequest on connection " + replayableConnection);
         }

      }
   }

   public void initialize(Connection connection) throws SQLException {
      this.pool.replayInitialize(connection);
   }

   public Properties getConnectionInfo(ConnectionEnv ce) {
      Connection c = null;
      return ce.conn != null && ce.conn.jconn != null && ce.conn.jconn instanceof OracleConnection ? FailoverablePooledConnectionHelper.getSessionInfoOnOracleConnection(ce.conn.jconn) : null;
   }

   public ReplayStatisticsSnapshot getReplayStatistics(ConnectionEnv rce) {
      return OracleReplayStatistics.getReplayStatistics(rce);
   }

   public void clearReplayStatistics(ConnectionEnv rce) {
      OracleReplayStatistics.clearReplayStatistics(rce);
   }

   public void registerConnectionInitializationCallback(ConnectionEnv rce) throws SQLException {
      DataSource ds = rce.getDataSource();
      XADataSource xads = rce.getXADataSource();
      OracleDataSource rds = null;
      if (ds != null && ds instanceof OracleDataSource) {
         rds = (OracleDataSource)ds;
      } else if (xads != null && xads instanceof OracleDataSource) {
         rds = (OracleDataSource)xads;
      }

      if (rds != null && rds.getConnectionInitializationCallback() == null) {
         if (JDBCREPLAY.isDebugEnabled()) {
            JDBCREPLAY.debug("registering internal replay ConnectionInitializationCallback " + this);
         }

         if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
            JdbcDebug.JDBCSQL.debug("[" + rds + "] registerConnectionInitializationCallback()");
         }

         rds.registerConnectionInitializationCallback(this);
         if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
            JdbcDebug.JDBCSQL.debug("[" + rds + "] registerConnectionInitializationCallback returned");
         }
      }

   }

   public void setProxyObject(Object physical, ConnectionEnv rce) {
      if (rce != null && physical != null && physical instanceof oracle.jdbc.replay.internal.ReplayableConnection) {
         try {
            ((oracle.jdbc.replay.internal.ReplayableConnection)physical).setProxyObject(rce);
         } catch (SQLException var4) {
         }

      }
   }

   public ConnectionEnv getProxyObject(Object physical) {
      if (physical != null && physical instanceof oracle.jdbc.replay.internal.ReplayableConnection) {
         try {
            Object obj = ((oracle.jdbc.replay.internal.ReplayableConnection)physical).getProxyObject();
            return obj instanceof ConnectionEnv ? (ConnectionEnv)obj : null;
         } catch (SQLException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public String getInstanceName(ConnectionEnv ce) {
      Properties connectionInfo = this.getConnectionInfo(ce);
      return connectionInfo == null ? null : connectionInfo.getProperty("INSTANCE_NAME");
   }

   public String getPDBName(ConnectionEnv ce) {
      if (ce.conn != null && ce.conn.jconn != null) {
         Statement s = null;

         String var4;
         try {
            s = ce.conn.jconn.createStatement();
            ResultSet rs = s.executeQuery("select sys_context('userenv','con_name') from dual");
            if (rs.next()) {
               var4 = rs.getString(1);
               return var4;
            }

            var4 = null;
            return var4;
         } catch (SQLException var15) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("error obtaining current container name on connection " + ce, var15);
            }

            var4 = null;
         } finally {
            if (s != null) {
               try {
                  s.close();
               } catch (SQLException var14) {
               }
            }

         }

         return var4;
      } else {
         return null;
      }
   }

   public String getServiceName(ConnectionEnv ce) {
      Properties connectionInfo = this.getConnectionInfo(ce);
      return connectionInfo == null ? null : connectionInfo.getProperty("SERVICE_NAME");
   }

   public Object getLogicalTransactionId(Object connection) throws SQLException {
      try {
         Class classForName = Class.forName("oracle.jdbc.OracleConnection");
         Method method = classForName.getDeclaredMethod("getLogicalTransactionId");
         return method.invoke(connection);
      } catch (Throwable var4) {
         throw new SQLException(var4);
      }
   }
}
