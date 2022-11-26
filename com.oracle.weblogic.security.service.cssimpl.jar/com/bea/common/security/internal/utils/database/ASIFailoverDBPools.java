package com.bea.common.security.internal.utils.database;

import com.bea.common.logger.spi.LoggerSpi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import weblogic.management.utils.InvalidParameterException;

public class ASIFailoverDBPools {
   private LoggerSpi logger = null;
   private ASIDBPool _primary = null;
   private ASIDBPool[] _backups = null;
   private long _primaryRetryInterval = 100L;
   private boolean _enableAutomaticFailover = false;

   public ASIFailoverDBPools(LoggerSpi logger, ASIDBPool primary, ASIDBPool[] backups, long primaryRetryInterval, boolean enableAutomaticFailover) {
      this.logger = logger;
      boolean debug = logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + " constructor" : null;
      if (debug) {
         logger.debug(method);
      }

      this._primary = primary;
      this._backups = backups;
      this._enableAutomaticFailover = enableAutomaticFailover;
      this._primaryRetryInterval = primaryRetryInterval;
      if (this._primaryRetryInterval < 0L) {
         this._primaryRetryInterval = 100L;
      }

      if (debug) {
         logger.debug(method + " Constructed ASIFailoverDBPools");
      }

   }

   public void checkinConnection(ASIDBPoolConnection dbConn) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".checkinConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      dbConn.getPool().checkinConnection(dbConn);
   }

   public ASIDBPoolConnection checkoutConnection() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".checkoutConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      ASIDBPoolConnection dbConn = null;
      boolean livePool = false;
      if (this._primary.getPoolState() == 1) {
         livePool = true;
         if (debug) {
            this.logger.debug(method + " Checking out connection from a live primary pool");
         }

         dbConn = this._primary.checkoutConnection();
         if (dbConn != null) {
            return dbConn;
         }
      }

      if (System.currentTimeMillis() - this._primary.getPoolDeadTime() > this._primaryRetryInterval) {
         if (debug) {
            this.logger.debug(method + " Checking out connection from primary pool at the timeout of retry interval");
         }

         dbConn = this._primary.checkoutConnection();
         if (dbConn != null) {
            return dbConn;
         }

         if (debug) {
            this.logger.debug(method + " Primary database connection is still not available after failback time reached.");
         }

         this._primary.setPoolDeadTime();
      }

      int i;
      if (this._enableAutomaticFailover && this._backups != null) {
         for(i = 0; i < this._backups.length; ++i) {
            if (this._backups[i].getPoolState() == 1) {
               livePool = true;
               if (debug) {
                  this.logger.debug(method + " Checking out connection from live backup pool - number " + i);
               }

               dbConn = this._backups[i].checkoutConnection();
               if (dbConn != null) {
                  return dbConn;
               }
            }

            if (System.currentTimeMillis() - this._backups[i].getPoolDeadTime() > this._primaryRetryInterval) {
               if (debug) {
                  this.logger.debug(method + " Checking out connection at the timeout of retry interval from backup pool - number " + i);
               }

               dbConn = this._backups[i].checkoutConnection();
               if (dbConn != null) {
                  return dbConn;
               }

               if (debug) {
                  this.logger.debug(method + " Backup database connection is still not available");
               }

               this._backups[i].setPoolDeadTime();
            }
         }
      }

      if (livePool) {
         return null;
      } else {
         if (debug) {
            this.logger.debug(method + " Forcing checkout and establish connection from primary pool");
         }

         dbConn = this._primary.checkoutConnection(true);
         if (dbConn != null) {
            return dbConn;
         } else {
            if (debug) {
               this.logger.debug(method + " Failed to checkout and establish connection from primary pool");
            }

            if (this._enableAutomaticFailover && this._backups != null) {
               for(i = 0; i < this._backups.length; ++i) {
                  if (debug) {
                     this.logger.debug(method + " Forcing checkout and establish connection from backup pool - number " + i);
                  }

                  dbConn = this._backups[i].checkoutConnection(true);
                  if (dbConn != null) {
                     return dbConn;
                  }

                  if (debug) {
                     this.logger.debug(method + " Failed to checkout and establish connection from backup pool - number " + i);
                  }
               }
            }

            return null;
         }
      }
   }

   public ASIDBPoolConnection replaceConnection(ASIDBPoolConnection dbConn) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".replaceConnection" : null;
      if (debug) {
         this.logger.debug(method);
      }

      this.checkinConnection(dbConn);
      return this.checkoutConnection();
   }

   public void setPrimaryRetryInterval(long primaryRetryInterval) {
      if (primaryRetryInterval < 0L) {
         this._primaryRetryInterval = 100L;
      } else {
         this._primaryRetryInterval = primaryRetryInterval;
      }

   }

   public long getPrimaryRetryInterval() {
      return this._primaryRetryInterval;
   }

   public void setAutomaticFailover(boolean enableAutomaticFailover) {
      this._enableAutomaticFailover = enableAutomaticFailover;
   }

   public boolean isAutomaticFailover() {
      return this._enableAutomaticFailover;
   }

   public int getActivePoolNumber() {
      return this._enableAutomaticFailover && this._backups != null ? 1 + this._backups.length : 1;
   }

   public void shutdown() {
      this._primary.shutdown();
      if (this._backups != null && this._backups.length != 0) {
         for(int i = 0; i < this._backups.length; ++i) {
            this._backups[i].shutdown();
         }

      }
   }

   public Vector runStatement(String name, String[] bindValues, int columns, int maxRows) throws InvalidParameterException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".runStatement" : null;
      if (debug) {
         this.logger.debug(method);
      }

      Vector results = new Vector();
      ASIDBPoolConnection dbConn = this.checkoutConnection();
      if (dbConn == null) {
         return null;
      } else {
         ResultSet rs = null;
         boolean toRetryConnection = false;
         boolean retrySameConn = false;

         try {
            int reCheckoutCount = 0;
            int twicePoolNumber = 2 * this.getActivePoolNumber();

            while(reCheckoutCount < twicePoolNumber) {
               PreparedStatement stmt;
               if (reCheckoutCount == twicePoolNumber) {
                  stmt = null;
                  return stmt;
               }

               try {
                  if (retrySameConn) {
                     toRetryConnection = true;
                     dbConn.reInitialize();
                  } else {
                     if (toRetryConnection) {
                        ++reCheckoutCount;
                        dbConn = this.replaceConnection(dbConn);
                     }

                     toRetryConnection = true;
                  }

                  if (dbConn == null) {
                     stmt = null;
                     return stmt;
                  }

                  stmt = dbConn.getPreparedStmt(name);
                  if (stmt == null) {
                     throw new SQLException("Failed to obtain the query to perform");
                  }

                  if (debug) {
                     this.logger.debug(method + " Execute the " + name + " SQL: " + dbConn.getPreparedStmtSql(name));
                  }

                  int rowCount;
                  if (bindValues != null) {
                     for(int loop = 0; loop < bindValues.length; ++loop) {
                        rowCount = loop + 1;
                        String paramValue = bindValues[loop];
                        stmt.setString(rowCount, paramValue);
                        if (debug) {
                           this.logger.debug(method + " Param " + rowCount + " = " + paramValue);
                        }
                     }
                  }

                  boolean isResult = stmt.execute();
                  if (debug) {
                     this.logger.debug(method + " The " + name + " SQL has returned");
                  }

                  rs = stmt.getResultSet();
                  if (rs == null) {
                     Vector var35 = results;
                     return var35;
                  }

                  rowCount = 0;

                  while(true) {
                     Vector row;
                     do {
                        do {
                           do {
                              if (!rs.next()) {
                                 return results;
                              }
                           } while(maxRows != -1 && rowCount >= maxRows);

                           row = new Vector();

                           for(int cnt = 0; cnt < columns; ++cnt) {
                              String data = rs.getString(cnt + 1);
                              if (data != null) {
                                 row.add(data);
                                 ++rowCount;
                              }
                           }
                        } while(row.size() <= 0);

                        results.add(row);
                     } while(!debug);

                     StringBuffer logString = new StringBuffer();
                     logString.append("Row: ");
                     Iterator i = row.iterator();

                     while(i.hasNext()) {
                        logString.append("[" + (String)i.next() + "],");
                     }

                     if (debug) {
                        this.logger.debug(logString);
                     }
                  }
               } catch (SQLException var31) {
                  if (!dbConn.isConnectionError(var31)) {
                     if (debug) {
                        this.logger.debug(method + " Exception Received when trying to run SQL Prepared Statement: " + name, var31);
                     }

                     Vector var15 = results;
                     return var15;
                  }

                  if (dbConn.isConnectionErrorOnExceptionCount()) {
                     dbConn.getPool().setPoolState(0);
                     retrySameConn = false;
                  } else if (!retrySameConn) {
                     retrySameConn = true;
                  } else {
                     dbConn.getPool().setPoolState(0);
                     retrySameConn = false;
                  }
               }
            }

            return results;
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var30) {
               }
            }

            if (dbConn != null) {
               this.checkinConnection(dbConn);
            }

         }
      }
   }
}
