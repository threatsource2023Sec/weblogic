package com.bea.common.security.internal.utils.database;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ASIDBPool extends LIFOSimplePool {
   private LoggerSpi logger;
   private static int MAX_CAPACITY_DEFAULT = 5;
   private static long CONNECTION_TIMEOUT_DEFAULT = 10000L;
   private ArrayList _connections;
   private String _jdbcURL;
   private Properties _connProperties;
   private long _lastConnectionLostTime;
   private HashMap _preparedStmtSqls;
   public static final int READY = 1;
   public static final int DEAD = 0;
   private int _state;
   private int _maxCapacity;
   private long _waitTime;

   public ASIDBPool(LoggerSpi logger, String jdbcClassName, String jdbcURL, Properties connProperties, int startNumber) throws RuntimeException {
      this(logger, jdbcClassName, jdbcURL, connProperties, startNumber, MAX_CAPACITY_DEFAULT, CONNECTION_TIMEOUT_DEFAULT);
   }

   public ASIDBPool(LoggerSpi logger, String jdbcClassName, String jdbcURL, Properties connProperties, int startNumber, int maxSize, long waitTime) throws RuntimeException {
      this.logger = null;
      this._lastConnectionLostTime = 0L;
      this._state = 1;
      this.logger = logger;
      boolean debug = logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + " constructor" : null;
      if (debug) {
         logger.debug(method);
      }

      if (debug) {
         logger.debug(method + " driver class: " + jdbcClassName);
         logger.debug(method + " URL: " + jdbcURL);
         logger.debug(method + " initial connections: " + startNumber);
         logger.debug(method + " maxSize: " + maxSize);
         logger.debug(method + " waitTime: " + waitTime);
         String propName = null;
         if (connProperties != null) {
            Enumeration e = connProperties.propertyNames();

            while(e.hasMoreElements()) {
               propName = (String)e.nextElement();
               if (propName != null && !propName.equals("password")) {
                  logger.debug(method + " primary property: " + propName + " = " + connProperties.getProperty(propName));
               }
            }
         } else {
            logger.debug(method + " null connection properties");
         }
      }

      this._preparedStmtSqls = new HashMap();
      this._waitTime = waitTime;
      this._connProperties = connProperties;
      this._jdbcURL = jdbcURL;
      if (maxSize < 1) {
         this._maxCapacity = 1;
      } else {
         this._maxCapacity = maxSize;
      }

      this._connections = new ArrayList(this._maxCapacity);
      if (startNumber > this._maxCapacity) {
         startNumber = this._maxCapacity;
      } else if (startNumber < 0) {
         startNumber = 0;
      }

      try {
         int i = 0;

         while(true) {
            if (i >= this._maxCapacity) {
               this.fill(this._connections);
               break;
            }

            ASIDBPoolConnection dbConn;
            if (i < startNumber) {
               dbConn = new ASIDBPoolConnection(logger, this, true);
               this._connections.add(dbConn);
            } else {
               dbConn = new ASIDBPoolConnection(logger, this, false);
               this._connections.add(dbConn);
            }

            ++i;
         }
      } catch (SQLException var13) {
         throw new RuntimeException(ServiceLogger.getDBConnectionNotAvailable(), var13);
      }

      if (debug) {
         logger.debug(method + " size is " + this.getMaxCapacity() + ", starting with " + startNumber + " connected connections ");
      }

   }

   public void setPoolState(int state) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".setPoolState" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (this._state == state) {
         if (debug) {
            this.logger.debug("No state change needed already in: " + state);
         }

      } else {
         if (debug) {
            this.logger.debug("Setting the pool to state: " + state);
         }

         this._state = state;
         if (state == 0) {
            this.setPoolDeadTime();
         } else if (state == 1) {
            this._lastConnectionLostTime = 0L;
         }

      }
   }

   public int getPoolState() {
      return this._state;
   }

   public long getPoolDeadTime() {
      return this._lastConnectionLostTime;
   }

   public void setPoolDeadTime() {
      this._lastConnectionLostTime = System.currentTimeMillis();
   }

   public void checkinConnection(ASIDBPoolConnection dbConn) {
      this.checkin(dbConn);
   }

   public ASIDBPoolConnection checkoutConnection() {
      return this.checkoutConnection(false);
   }

   public ASIDBPoolConnection checkoutConnection(boolean initConnection) {
      ASIDBPoolConnection dbConn = null;

      try {
         dbConn = (ASIDBPoolConnection)this.checkout(this._waitTime);
         if (dbConn == null) {
            return null;
         } else {
            if (!dbConn.isInitialized() || initConnection) {
               dbConn.initialize();
            }

            return dbConn;
         }
      } catch (InterruptedException var4) {
         this.logger.error(ServiceLogger.getDBConnectionNotAvailable(), var4);
         return null;
      } catch (SQLException var5) {
         if (dbConn != null) {
            this.checkin(dbConn);
         }

         this.logger.error(ServiceLogger.getDBConnectionNotAvailable(), var5);
         return null;
      }
   }

   public long getWaitTime() {
      return this._waitTime;
   }

   public String getConnectionURL() {
      return this._jdbcURL;
   }

   public Properties getConnectionProperties() {
      return this._connProperties;
   }

   public void setPreparedStmtSqls(HashMap namedSqls) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".setPreparedStmtSqls" : null;
      if (debug) {
         this.logger.debug(method + " Setting PreparedStatements text to the pool " + this);
      }

      this._preparedStmtSqls = namedSqls;
      Set keys = this._preparedStmtSqls.keySet();
      Iterator iter = keys.iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();

         for(int i = 0; i < this._connections.size(); ++i) {
            ASIDBPoolConnection dbConn = (ASIDBPoolConnection)this._connections.get(i);
            if (dbConn.isInitialized()) {
               try {
                  dbConn.addPreparedStmt(key, (String)this._preparedStmtSqls.get(key));
               } catch (SQLException var10) {
                  if (debug) {
                     this.logger.debug(method + " Unable to add Prepared Statement " + key + " at this time to connection " + dbConn);
                  }
               }
            }
         }
      }

   }

   public HashMap getPreparedStmtSqls() {
      return this._preparedStmtSqls;
   }

   public void addPreparedStmtSql(String name, String sqlText) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".addPreparedStmtSql" : null;
      if (debug) {
         this.logger.debug(method + " Adding PreparedStatement text to the pool " + this);
      }

      for(int i = 0; i < this._connections.size(); ++i) {
         ASIDBPoolConnection dbConn = (ASIDBPoolConnection)this._connections.get(i);
         if (dbConn.isInitialized()) {
            try {
               dbConn.addPreparedStmt(name, sqlText);
            } catch (SQLException var8) {
               if (debug) {
                  this.logger.debug(method + " Unable to add Prepared Statement " + name + " at this time to connection " + dbConn);
               }
            }
         }
      }

      this._preparedStmtSqls.put(name, sqlText);
   }

   public String getPreparedStmtSql(String name) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getPreparedStmtSql" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (name == null) {
         return null;
      } else {
         String sql = (String)this._preparedStmtSqls.get(name);
         if (sql == null) {
            if (debug) {
               this.logger.debug(method + " no SQL string found for " + name + ", returning null");
            }

            return null;
         } else {
            return sql;
         }
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + " constructor" : null;
      if (debug) {
         this.logger.debug(method + " shutting down Pool " + this);
      }

      this.drain();

      for(int i = 0; i < this._connections.size(); ++i) {
         ASIDBPoolConnection dbConn = (ASIDBPoolConnection)this._connections.get(i);
         if (dbConn.isInitialized()) {
            dbConn.close();
         }
      }

      this._connections.clear();
      this._connections = null;
   }

   public static Properties convertArrayToProperties(LoggerSpi logger, String[] propertiesArray) {
      boolean debug = logger.isDebugEnabled();
      String method = debug ? ASIDBPool.class.getName() + ".convertArrayToProperties" : null;
      if (debug) {
         logger.debug(method);
      }

      Properties prop = new Properties();
      if (debug) {
         logger.debug("Array contains " + propertiesArray.length + " items");
      }

      for(int i = 0; i < propertiesArray.length; ++i) {
         if (propertiesArray[i].length() >= 1) {
            String[] nv = propertiesArray[i].split("=", 2);
            if (nv.length != 2) {
               logger.warn(ServiceLogger.getDBPoolPropertySkipped(propertiesArray[i]));
            } else {
               prop.put(nv[0].trim(), nv[1].trim());
               if (debug) {
                  logger.debug("Adding to property object: name = " + nv[0].trim() + ", value = " + nv[1].trim());
               }
            }
         }
      }

      return prop;
   }
}
