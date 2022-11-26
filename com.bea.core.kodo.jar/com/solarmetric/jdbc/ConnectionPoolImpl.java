package com.solarmetric.jdbc;

import com.solarmetric.manage.BucketStatistic;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;

public class ConnectionPoolImpl extends GenericKeyedObjectPool implements ConnectionPool, KeyedPoolableObjectFactory {
   public static final int ACTION_DESTROY = 0;
   public static final int ACTION_VALIDATE = 1;
   public static final int ACTION_NONE = 2;
   private static final Object MAKE_CONNECTION = new Object();
   private static final Object FAILED_CONNECTION = new Object();
   private static final Localizer _loc = Localizer.forPackage(ConnectionPoolImpl.class);
   private final ActiveSet _active = new ActiveSet();
   private final Collection _destroy = new ArrayList();
   private PoolingDataSource _ds = null;
   private String _validationSQL = null;
   private String _closePoolSQL = null;
   private int _validationTime = 300000;
   private int _queryTimeout = -1;
   private boolean _rollbackOnReturn = false;
   private boolean _delayClose = false;
   private int _exceptionAction = 0;
   private final BucketStatistic _numActive;
   private final BucketStatistic _numIdle;
   private final Collection _stats;

   public ConnectionPoolImpl() {
      super((KeyedPoolableObjectFactory)null);
      this.setFactory(this);
      this.setWhenExhaustedAction((byte)1);
      this.setMaxWait(3000L);
      this.setTestOnBorrow(true);
      this._numActive = new BucketStatistic("NumActive", "Active Connections", "# Connections", 1, 2, 5000L);
      this._numIdle = new BucketStatistic("NumIdle", "Idle Connections", "# Connections", 1, 2, 5000L);
      this._stats = Arrays.asList(this._numActive, this._numIdle);
   }

   public void setDataSource(PoolingDataSource ds) {
      this._ds = ds;
   }

   public PoolingDataSource getDataSource() {
      return this._ds;
   }

   public String getValidationSQL() {
      return this._validationSQL;
   }

   public void setValidationSQL(String sql) {
      this._validationSQL = sql;
   }

   public void setClosePoolSQL(String closePoolSQL) {
      this._closePoolSQL = closePoolSQL;
   }

   public String getClosePoolSQL() {
      return this._closePoolSQL;
   }

   public int getValidationTimeout() {
      return this._validationTime;
   }

   public void setValidationTimeout(int time) {
      this._validationTime = time;
   }

   public int getQueryTimeout() {
      return this._queryTimeout;
   }

   public void setQueryTimeout(int time) {
      this._queryTimeout = time;
   }

   public void setRollbackOnReturn(boolean rollbackOnReturn) {
      this._rollbackOnReturn = rollbackOnReturn;
   }

   public boolean getRollbackOnReturn() {
      return this._rollbackOnReturn;
   }

   public boolean getDelayConnectionClose() {
      return this._delayClose;
   }

   public void setDelayConnectionClose(boolean delay) {
      this._delayClose = delay;
   }

   public void setWhenExhaustedAction(String action) {
      if (action != null) {
         if ("block".equals(action)) {
            this.setWhenExhaustedAction((byte)1);
         } else if ("exception".equals(action)) {
            this.setWhenExhaustedAction((byte)0);
         } else if ("grow".equals(action)) {
            this.setWhenExhaustedAction((byte)2);
         } else {
            byte b;
            try {
               b = Byte.parseByte(action);
            } catch (RuntimeException var4) {
               throw new IllegalArgumentException(action);
            }

            this.setWhenExhaustedAction(b);
         }

      }
   }

   public void setExceptionAction(String action) {
      if (action != null) {
         if ("destroy".equals(action)) {
            this.setExceptionAction(0);
         } else if ("validate".equals(action)) {
            this.setExceptionAction(1);
         } else {
            if (!"none".equals(action)) {
               throw new IllegalArgumentException(action);
            }

            this.setExceptionAction(2);
         }

      }
   }

   public int getExceptionAction() {
      return this._exceptionAction;
   }

   public void setExceptionAction(int action) {
      this._exceptionAction = action;
   }

   public Connection getConnection(ConnectionRequestInfo cri) throws SQLException {
      try {
         if (this.getMaxActive() < 1) {
            return this.makeConnection(cri);
         } else {
            if (this._ds.getLogs().isJDBCEnabled()) {
               this._ds.getLogs().logJDBC(this.toString(), (Connection)null);
            }

            this._active.removeExpired();
            long start = System.currentTimeMillis();
            Object o = this.borrowObject(cri);
            long time = System.currentTimeMillis() - start;
            Connection conn;
            if (o != MAKE_CONNECTION && o != FAILED_CONNECTION) {
               conn = (Connection)o;
            } else {
               try {
                  conn = this.makeConnection(cri);
               } catch (SQLException var9) {
                  this.returnObject(cri, FAILED_CONNECTION);
                  throw var9;
               }
            }

            conn = this._active.add((PoolConnection)conn);
            if (this.getMaxWait() > 0L && time >= 500L) {
               Log log = this._ds.getLogs().getJDBCLog();
               if (log.isInfoEnabled()) {
                  log.info(_loc.get("wait-on-conn", String.valueOf(time), conn));
               }
            }

            this._ds.getLogs().logJDBC("checkout", conn);
            this.setStatistics();
            this.closeDestroyed();
            return conn;
         }
      } catch (SQLException var10) {
         throw var10;
      } catch (Exception var11) {
         boolean isExhausted = var11 instanceof NoSuchElementException;
         String msgText = var11.toString();
         Log log = this._ds.getLogs().getJDBCLog();
         if (log.isWarnEnabled()) {
            if (isExhausted) {
               Localizer.Message msg = _loc.get("conn-pool-exhausted", String.valueOf(this.getMaxActive()));
               log.warn(msg, var11);
               msgText = msg.getMessage();
            } else {
               log.warn(_loc.get("get-conn-exception", var11.getClass().getName()), var11);
            }
         }

         throw (SQLException)JavaVersions.initCause(new SQLException(msgText), var11);
      }
   }

   public void returnConnection(Connection conn) {
      while(!(conn instanceof PoolConnection)) {
         conn = ((DelegatingConnection)conn).getDelegate();
      }

      ConnectionRequestInfo cri = ((PoolConnection)conn).getRequestInfo();
      if (this.getMaxActive() < 1) {
         this.destroyObject(cri, conn);
      } else {
         this._ds.getLogs().logJDBC("return", conn);
         if (this._active.remove((PoolConnection)conn)) {
            try {
               this.returnObject(cri, conn);
            } catch (Exception var4) {
            }
         }

         this.setStatistics();
      }

      this.closeDestroyed();
   }

   public void evict() throws Exception {
      super.evict();
      this.closeDestroyed();
   }

   public void close() {
      Log log = null;
      if (this._ds != null) {
         log = this._ds.getLogs().getJDBCLog();
         if (log.isInfoEnabled()) {
            log.info(_loc.get("close-pool"));
         }
      }

      if (this._ds != null && this._closePoolSQL != null && this._closePoolSQL.length() > 0) {
         Connection conn = null;
         Statement stmnt = null;

         try {
            conn = this._ds.getConnection();
            stmnt = conn.createStatement();
            this._ds.getLogs().logSQL(this._closePoolSQL, conn);
            stmnt.execute(this._closePoolSQL);
         } catch (SQLException var19) {
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("close-pool-fail", this._closePoolSQL), var19);
            }
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var17) {
               }
            }

            if (conn != null) {
               try {
                  conn.close();
               } catch (SQLException var16) {
               }
            }

         }
      }

      try {
         super.close();
      } catch (Exception var18) {
      }

   }

   public Object makeObject(Object key) throws SQLException {
      return MAKE_CONNECTION;
   }

   public Connection makeConnection(Object key) throws SQLException {
      ConnectionRequestInfo cri = (ConnectionRequestInfo)key;
      Connection conn = this.wrapConnection(this._ds.newConnection(cri));
      PoolConnection poolConn = new PoolConnection(conn, cri, this);
      if (this._ds.getLogs().isJDBCEnabled()) {
         this._ds.getLogs().logJDBC("open: " + this._ds.getConnectionURL() + " (" + cri.getUsername() + ")", poolConn);
      }

      return poolConn;
   }

   public void destroyObject(Object key, Object obj) {
      if (obj != MAKE_CONNECTION && obj != FAILED_CONNECTION) {
         PoolConnection conn = (PoolConnection)obj;
         this._ds.getLogs().logJDBC("free", conn);
         conn.free();
         if (this._delayClose) {
            this._destroy.add(conn.getDelegate());
         } else {
            try {
               conn.getDelegate().close();
            } catch (SQLException var5) {
            }
         }

      }
   }

   private void closeDestroyed() {
      if (!this._destroy.isEmpty()) {
         Connection[] copy;
         synchronized(this) {
            copy = (Connection[])((Connection[])this._destroy.toArray(new Connection[this._destroy.size()]));
            this._destroy.clear();
         }

         for(int i = 0; i < copy.length; ++i) {
            try {
               copy[i].close();
            } catch (SQLException var4) {
            }
         }

      }
   }

   public boolean validateObject(Object key, Object obj) {
      if (obj == MAKE_CONNECTION) {
         return true;
      } else if (obj == FAILED_CONNECTION) {
         return false;
      } else {
         Statement stmnt = null;
         ResultSet rs = null;
         PoolConnection conn = (PoolConnection)obj;

         boolean var6;
         try {
            boolean var7;
            try {
               if (!conn.isClosed()) {
                  int errs = conn.getExceptionCount();
                  if (errs > 0 && this._exceptionAction == 0) {
                     var7 = false;
                     return var7;
                  }

                  if (this._validationSQL != null && this._validationSQL.length() > 0 && (errs > 0 && this._exceptionAction == 1 || this._validationTime <= 0 || conn.getLastValidatedTime() + (long)this._validationTime <= System.currentTimeMillis())) {
                     stmnt = conn.createStatement();
                     if (this._queryTimeout != -1) {
                        stmnt.setQueryTimeout(this._queryTimeout);
                     }

                     stmnt.execute(this._validationSQL);
                     rs = stmnt.getResultSet();
                     if (rs != null) {
                        rs.next();
                     }

                     if (this._validationTime > 0) {
                        conn.setLastValidatedTime(System.currentTimeMillis());
                     }
                  }

                  conn.incrementExceptionCount(-errs);
                  var7 = true;
                  return var7;
               }

               var6 = false;
            } catch (SQLException var25) {
               this._ds.getLogs().logJDBC("validation failed", conn);
               var7 = false;
               return var7;
            }
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var24) {
               }
            }

            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var23) {
               }
            }

         }

         return var6;
      }
   }

   public void activateObject(Object key, Object value) {
   }

   public void passivateObject(Object key, Object value) {
      if (value != MAKE_CONNECTION && value != FAILED_CONNECTION) {
         PoolConnection conn = (PoolConnection)value;
         if (!this.getRollbackOnReturn() && !conn.isTransactionDirty()) {
            if (conn.isTransactionActive()) {
               try {
                  conn.commit();
               } catch (SQLException var5) {
               }
            }
         } else {
            try {
               conn.rollback();
            } catch (SQLException var6) {
            }
         }

      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("connection pool: active=").append(this.getNumActive()).append(", idle=").append(this.getNumIdle());
      this.appendInfo(buf);
      return buf.toString();
   }

   protected void appendInfo(StringBuffer buf) {
   }

   protected Connection wrapConnection(Connection dataStoreConn) throws SQLException {
      return dataStoreConn;
   }

   private void setStatistics() {
      this._numActive.setValue((double)this.getNumActive());
      this._numIdle.setValue((double)this.getNumIdle());
   }

   public Collection getStatistics() {
      return this._stats;
   }

   private class ActiveSet extends ConcurrentReferenceHashMap {
      public ActiveSet() {
         super(0, 1);
      }

      public Connection add(PoolConnection conn) {
         DelegatingConnection dc = new DelegatingConnection(conn);
         this.put(conn, dc);
         return dc;
      }

      public boolean remove(PoolConnection conn) {
         return super.remove(conn) != null;
      }

      public void valueExpired(Object key) {
         PoolConnection conn = (PoolConnection)key;
         ConnectionPoolImpl.this._ds.getLogs().logJDBC("reclaiming", conn);

         try {
            ConnectionPoolImpl.this.returnObject(conn.getRequestInfo(), conn);
         } catch (Exception var4) {
         }

      }
   }
}
