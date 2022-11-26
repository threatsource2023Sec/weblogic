package com.bea.common.security.internal.utils.database;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ASIDBPoolConnection {
   private LoggerSpi logger = null;
   private static int MAX_RECONNECTION_TIMES = 2;
   private static int SLEEP_TIME_INTERVAL = 100;
   private ASIDBPool _myPool = null;
   private Connection _connection = null;
   private HashMap _prepStmts = null;
   private int _exceptionCount = 0;
   private boolean _isReadOnly = true;
   private int _numSQLExceptionsAsConnErr = 10;

   public ASIDBPoolConnection(LoggerSpi logger, ASIDBPool pool, boolean init) throws SQLException {
      this.logger = logger;
      boolean debug = logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + " constructor" : null;
      if (debug) {
         logger.debug(method + " Constructing an ASIDBPoolConnection, initialization is set to " + init);
      }

      this._myPool = pool;
      this._prepStmts = new HashMap();
      this._connection = null;
      if (init) {
         try {
            this.initialize();
         } catch (SQLException var7) {
            logger.error(ServiceLogger.getDBConnectionNotAvailable(), var7);
            throw var7;
         }
      }

      if (debug) {
         logger.debug(method + " Constructed ASIDBPoolConnection");
      }

   }

   public ASIDBPool getPool() {
      return this._myPool;
   }

   public Connection initialize() throws SQLException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".initialize" : null;
      if (debug) {
         this.logger.debug(method + " initialize connection " + this);
         this.logger.debug(method + "Retry is set to " + MAX_RECONNECTION_TIMES + " times, each waiting for " + SLEEP_TIME_INTERVAL + "ms");
      }

      if (this._connection != null) {
         this.close();
      }

      int retryTimes = 0;

      while(retryTimes < MAX_RECONNECTION_TIMES) {
         ++retryTimes;

         try {
            if (debug) {
               this.logger.debug(method + " getConnection to " + this._myPool.getConnectionURL() + " opened");
            }

            this._connection = DriverManager.getConnection(this._myPool.getConnectionURL(), this._myPool.getConnectionProperties());
            if (debug) {
               this.logger.debug(method + "Connection to " + this._myPool.getConnectionURL() + " opened");
            }

            this.initReadOnyState();
            this._prepStmts.clear();
            Set keys = this._myPool.getPreparedStmtSqls().keySet();
            Iterator iter = keys.iterator();

            while(iter.hasNext()) {
               String key = (String)iter.next();
               this.addPreparedStmt(key, (String)this._myPool.getPreparedStmtSqls().get(key));
            }

            this._myPool.setPoolState(1);
            this._exceptionCount = 0;
            break;
         } catch (SQLException var9) {
            this.logger.debug(var9);
            if (!this.isConnectionError(var9)) {
               throw var9;
            }

            try {
               if (debug) {
                  this.logger.debug(method + " Unable to connect to the database. Waiting to retry");
               }

               Thread.sleep((long)SLEEP_TIME_INTERVAL);
            } catch (InterruptedException var8) {
            }

            if (retryTimes == MAX_RECONNECTION_TIMES) {
               this._myPool.setPoolState(0);
               if (debug) {
                  this.logger.debug(method + " Maximum number of retries exhausted. Cannot connect to database with URL " + this._myPool.getConnectionURL(), var9);
               }

               this._prepStmts.clear();
               this._connection = null;
               throw var9;
            }
         }
      }

      if (debug) {
         this.logger.debug(method + "Initialized the connection " + this);
      }

      this._exceptionCount = 0;
      return this._connection;
   }

   public void reInitialize() throws SQLException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".initialize" : null;
      if (debug) {
         this.logger.debug(method + " To re-initialize the connection " + this);
      }

      this.initialize();
   }

   public boolean isInitialized() {
      return this._connection != null;
   }

   public void close() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".close" : null;
      if (debug) {
         this.logger.debug(method);
      }

      this._prepStmts.clear();

      try {
         this._connection.close();
      } catch (SQLException var4) {
         if (debug) {
            this.logger.debug(method + " SQL Exception when closing database connection", var4);
         }
      }

      this._connection = null;
   }

   public void addPreparedStmt(String name, String sql) throws SQLException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".initialize" : null;
      if (debug) {
         this.logger.debug(method + " Adding a PreparedStatement " + name + " to connection " + this);
      }

      if (sql != null && sql.length() != 0) {
         if (this.isInitialized()) {
            while(true) {
               try {
                  PreparedStatement statement = this._connection.prepareStatement(sql, 1004, 1007);
                  this._prepStmts.put(name, statement);
                  break;
               } catch (SQLException var8) {
                  if (this.isConnectionError(var8)) {
                     try {
                        this.initialize();
                        continue;
                     } catch (SQLException var7) {
                        if (this.isConnectionError(var7)) {
                           this._myPool.setPoolState(0);
                           this.logger.error("Database connection is lost", var7);
                           throw var7;
                        }
                     }
                  }

                  if (debug) {
                     this.logger.debug(method + " SQL exception trying to prepare statement");
                  }

                  throw var8;
               }
            }

            if (debug) {
               this.logger.debug(method + " PreparedStatement " + name + " added to connection " + this);
            }

         }
      } else {
         if (debug) {
            this.logger.debug(method + " SQL Statement not prepared for " + name);
         }

      }
   }

   public PreparedStatement getPreparedStmt(String name) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getPreparedStmt" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (name == null) {
         return null;
      } else {
         PreparedStatement stmt = (PreparedStatement)this._prepStmts.get(name);
         if (stmt == null) {
            if (debug) {
               this.logger.debug(method + "No PreparedStatement found for " + name + " in connection " + this + ", returning null");
            }

            return null;
         } else {
            try {
               stmt.clearParameters();
            } catch (SQLException var6) {
               if (debug) {
                  this.logger.debug(method + "Cannot clear parameters from statement", var6);
               }
            }

            return stmt;
         }
      }
   }

   public String getPreparedStmtSql(String name) {
      return name == null ? null : this._myPool.getPreparedStmtSql(name);
   }

   public Connection getConnection() throws SQLException {
      if (!this.isInitialized()) {
         this.initialize();
      }

      return this._connection;
   }

   public boolean setReadOnly(boolean readOnly) {
      if (this._isReadOnly == readOnly) {
         return true;
      } else {
         this._isReadOnly = readOnly;
         return this.initReadOnyState();
      }
   }

   private boolean initReadOnyState() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".initialize" : null;
      if (this._connection == null) {
         return true;
      } else {
         try {
            this._connection.setReadOnly(this._isReadOnly);
            if (debug) {
               this.logger.debug(method + " Connection read only is set to: " + this._isReadOnly);
            }

            return true;
         } catch (SQLException var4) {
            this._isReadOnly = false;
            if (debug) {
               this.logger.debug(method + " Read only database connection not supported");
            }

            return false;
         }
      }
   }

   public boolean isReadOnly() {
      return this._connection == null ? false : this._isReadOnly;
   }

   public void setNumberSQLExceptionsAsConnectionError(int numberSQLException) {
      if (numberSQLException < 1) {
         this._numSQLExceptionsAsConnErr = 1;
      } else {
         this._numSQLExceptionsAsConnErr = numberSQLException;
      }

   }

   public boolean isConnectionError(SQLException sqlEx) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isConnectionError" : null;
      if (debug) {
         this.logger.debug(method);
      }

      String sqlState = sqlEx.getSQLState();
      int errCode = sqlEx.getErrorCode();
      boolean connError = false;
      if (debug) {
         this.logger.debug(method + " Caught SQLException - SQLState: " + sqlState + ", ErrorCode: " + errCode + ", Message: " + sqlEx.getMessage());
      }

      if (sqlState != null) {
         if (sqlState.equals("61000") || sqlState.equals("JZ006") || sqlState.equals("72000")) {
            this._exceptionCount = 0;
            connError = true;
         }
      } else if (errCode != 17002 && errCode != 17410 && errCode != 17001 && errCode != 17008 && (errCode != 0 || !sqlEx.getMessage().startsWith("Connection refused"))) {
         ++this._exceptionCount;
      } else {
         this._exceptionCount = 0;
         connError = true;
      }

      if (this._exceptionCount >= this._numSQLExceptionsAsConnErr) {
         connError = true;
         this.close();
      }

      if (connError) {
         if (debug) {
            this.logger.debug(method + " Lost connection " + this);
         }

         this._connection = null;
      }

      return connError;
   }

   public boolean isConnectionErrorOnExceptionCount() {
      return this._exceptionCount >= this._numSQLExceptionsAsConnErr;
   }
}
