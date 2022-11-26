package weblogic.store.io.jdbc;

import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import weblogic.store.PersistentStoreException;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;
import weblogic.timers.Timer;

public final class JDBCStoreCursor extends ReservedConnection implements PersistentStoreIO.Cursor {
   private static int MAX_IDLE_MILLIS = getMaxIdleTime();
   private static final int MAX_IDLE_MILLIS_DEF = 300000;
   private long timestamp;
   private JDBCStoreIO jdbcStore;
   private ResultSet resultSet;
   private Statement statement;
   private int typeCode;
   private boolean isOpen;
   private boolean includeBlobs;
   private boolean inUse;
   private Object localLock = new Object();

   JDBCStoreCursor(JDBCStoreIO _jdbcStore, int _typeCode, boolean _includeBlobs, int _retryPeriodMillis, int _retryIntervalMillis) throws PersistentStoreException, SQLException {
      super(_jdbcStore, true, (String)null, _retryPeriodMillis, _retryIntervalMillis);
      this.lock();
      synchronized(this.localLock) {
         this.isOpen = true;
      }

      this.jdbcStore = _jdbcStore;
      this.typeCode = _typeCode;
      this.includeBlobs = _includeBlobs;

      try {
         this.getRows();
      } catch (SQLException var10) {
         this.close(true);
         throw var10;
      }

      synchronized(this.localLock) {
         this.timestamp = System.currentTimeMillis();
         this.inUse = false;
      }

      this.startPingTimer();
   }

   private void getRows() throws SQLException {
      StringBuffer sqlb = new StringBuffer(100);
      sqlb.append("SELECT ");
      sqlb.append("id");
      sqlb.append(", ").append("type");
      sqlb.append(", ").append("handle");
      if (this.includeBlobs) {
         sqlb.append(", ").append("record");
      }

      sqlb.append(" FROM ");
      sqlb.append(this.jdbcStore.getTableDMLIdentifier());
      if (this.typeCode != -4) {
         sqlb.append(" WHERE ");
         sqlb.append("type");
         if (this.typeCode == -5) {
            sqlb.append(" >= 0");
         } else {
            sqlb.append(" = ");
            sqlb.append(this.typeCode);
         }
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.debug("Cursor: " + sqlb);
      }

      try {
         this.statement = this.getConnection().createStatement();
         boolean hasResults = this.statement.execute(sqlb.toString());
         if (hasResults) {
            this.resultSet = this.statement.getResultSet();
         }

      } catch (RuntimeException var3) {
         throw new SQLExceptionWrapper(var3);
      }
   }

   private LocalIORecord getNextRecord() throws JDBCStoreException {
      boolean var21 = false;

      Object var1;
      label200: {
         LocalIORecord var31;
         label201: {
            try {
               var21 = true;
               synchronized(this.localLock) {
                  if (!this.isOpen) {
                     this.debugVerbose("connection is closed");
                     throw new JDBCStoreException(this.jdbcStore, "connection is closed");
                  }

                  this.inUse = true;
               }

               if (this.resultSet == null) {
                  this.close(false);
                  var1 = null;
                  var21 = false;
                  break label200;
               }

               try {
                  if (this.resultSet.next()) {
                     int row = this.resultSet.getInt(1);
                     int type = this.resultSet.getInt(2);
                     int handle = this.resultSet.getInt(3);
                     ByteBuffer bb = null;
                     byte[] b = null;
                     if (this.includeBlobs) {
                        if (this.jdbcStore.isOracleBlobRecord()) {
                           Blob blob = this.resultSet.getBlob(4);
                           if (blob != null) {
                              b = blob.getBytes(1L, (int)blob.length());
                           }
                        } else {
                           b = this.resultSet.getBytes(4);
                        }

                        if (b != null) {
                           bb = ByteBuffer.wrap(b);
                        }
                     }

                     if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                        this.debugVerbose("cursor read:  handle=" + handle + " row=" + row + " type=" + type + (b == null ? "" : " len=" + b.length));
                     }

                     var31 = new LocalIORecord(row, handle, type, bb);
                     var21 = false;
                     break label201;
                  }

                  this.close(false);
                  var1 = null;
                  var21 = false;
               } catch (SQLException var27) {
                  this.close(true);
                  throw new JDBCStoreException(this.jdbcStore, var27.toString(), var27);
               } catch (RuntimeException var28) {
                  this.close(true);
                  throw new JDBCStoreException(this.jdbcStore, var28.toString(), var28);
               }
            } finally {
               if (var21) {
                  synchronized(this.localLock) {
                     this.timestamp = System.currentTimeMillis();
                     this.inUse = false;
                  }
               }
            }

            synchronized(this.localLock) {
               this.timestamp = System.currentTimeMillis();
               this.inUse = false;
               return (LocalIORecord)var1;
            }
         }

         synchronized(this.localLock) {
            this.timestamp = System.currentTimeMillis();
            this.inUse = false;
            return var31;
         }
      }

      synchronized(this.localLock) {
         this.timestamp = System.currentTimeMillis();
         this.inUse = false;
         return (LocalIORecord)var1;
      }
   }

   public void timerExpired(Timer timer) {
      boolean debugOn = false;
      boolean debugVerboseOn = false;
      long currentTime = System.currentTimeMillis();
      if (StoreDebug.storeIOPhysical.isDebugEnabled() || StoreDebug.storeIOLogical.isDebugEnabled() || StoreDebug.storeIOLogicalBoot.isDebugEnabled()) {
         debugOn = true;
      }

      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         debugVerboseOn = true;
      }

      synchronized(this.localLock) {
         if (debugVerboseOn) {
            this.debugVerbose("StoreCursor.timerExpired() isOpen=" + this.isOpen + ", elapsed time=" + (currentTime - this.timestamp) + ", inUse=" + this.inUse + ", type=" + this.typeCode + ", maxIdleMillis=" + MAX_IDLE_MILLIS);
         }

         if (!this.isOpen || this.timestamp == 0L || this.inUse || currentTime - this.timestamp <= (long)MAX_IDLE_MILLIS) {
            return;
         }
      }

      if (debugOn || debugVerboseOn) {
         Exception e = new JDBCStoreException(this.jdbcStore, "Closing idle store cursor for connection type " + this.typeCode);
         this.debug(e.toString(), e);
      }

      this.close(false);
   }

   protected void close(boolean invalidateConnection) {
      synchronized(this.localLock) {
         this.isOpen = false;
      }

      JDBCHelper.close(this.resultSet);
      JDBCHelper.close(this.statement);
      super.close(invalidateConnection);
   }

   private static int getMaxIdleTime() {
      int maxIdleMillis = 300000;

      try {
         String propVal = System.getProperty("weblogic.store.jdbc.MaxIdleCursorMillis");
         if (propVal != null) {
            maxIdleMillis = Integer.parseInt(propVal);
            maxIdleMillis = Math.max(maxIdleMillis, 300000);
         }
      } catch (NumberFormatException var2) {
         var2.printStackTrace();
      }

      return maxIdleMillis;
   }

   public IORecord next() throws JDBCStoreException {
      return this.getNextRecord();
   }

   static class LocalIORecord extends IORecord {
      private int rowId;

      private LocalIORecord(int _row, int _handle, int _typeCode, ByteBuffer _data) {
         super(_handle, _typeCode, _data);
         this.rowId = _row;
      }

      int getRowId() {
         return this.rowId;
      }

      // $FF: synthetic method
      LocalIORecord(int x0, int x1, int x2, ByteBuffer x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
