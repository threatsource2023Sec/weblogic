package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.RemoteHelper;

public class ResultSetStub extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = 3023484301546933309L;
   ResultSet remoteRs;
   private RmiDriverSettings rmiSettings;
   private transient ResultSetRowCache currRowCache;
   private transient BlockGetter bg = null;
   private transient ReaderBlockGetter rbg = null;

   public ResultSetStub() {
   }

   public ResultSetStub(ResultSet rs, RmiDriverSettings settings) {
      this.init(rs, settings);
   }

   public void init(ResultSet rs, RmiDriverSettings settings) {
      this.remoteRs = rs;
      this.rmiSettings = settings;
   }

   public Object readResolve() throws ObjectStreamException {
      ResultSetStub stub = null;

      try {
         stub = (ResultSetStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ResultSetStub", this.remoteRs, false);
         stub.init(this.remoteRs, this.rmiSettings);
         return (java.sql.ResultSet)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return stub;
      }
   }

   public Object getStub() {
      if (this.rmiSettings.isVerbose()) {
         JdbcDebug.JDBCRMIInternal.debug("getStub remoteRs: " + this.remoteRs);
      }

      return this.remoteRs;
   }

   public boolean isRowCaching() throws SQLException {
      if (RemoteHelper.isCollocated(this.remoteRs)) {
         this.rmiSettings.setRowCacheSize(0);
         return false;
      } else {
         return this.remoteRs.isRowCaching();
      }
   }

   public int getRowCacheSize() {
      return this.rmiSettings.getRowCacheSize();
   }

   public ResultSetRowCache getNextRowCache() throws SQLException {
      return this.remoteRs.getNextRowCache();
   }

   public ResultSetMetaDataCache getMetaDataCache() throws SQLException {
      return this.remoteRs.getMetaDataCache();
   }

   public java.sql.ResultSetMetaData getMetaData() throws SQLException {
      java.sql.ResultSetMetaData rsmd = this.remoteRs.getMetaData();
      return rsmd == null ? null : new ResultSetMetaDataImpl(rsmd);
   }

   public InputStream getAsciiStream(int columnIndex) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnIndex, 1);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public InputStream getAsciiStream(String columnName) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnName, 1);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getUnicodeStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnIndex, 2);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public InputStream getUnicodeStream(String columnName) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnName, 2);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public InputStream getBinaryStream(int columnIndex) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnIndex, 3);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public InputStream getBinaryStream(String columnName) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      BlockGetter bg = this.remoteRs.getBlockGetter();
      int blockid = this.remoteRs.registerStream(columnName, 3);
      if (blockid == -1) {
         return null;
      } else {
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         return ish;
      }
   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderBlockGetter rbg = this.remoteRs.getReaderBlockGetter();
      int blockid = this.remoteRs.registerStream(columnIndex, 4);
      if (blockid == -1) {
         return null;
      } else {
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         return rh;
      }
   }

   public Reader getCharacterStream(String columnName) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderBlockGetter rbg = this.remoteRs.getReaderBlockGetter();
      int blockid = this.remoteRs.registerStream(columnName, 4);
      if (blockid == -1) {
         return null;
      } else {
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         return rh;
      }
   }

   public void close() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : close";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      boolean var9 = false;

      try {
         var9 = true;
         this.remoteRs.close();
         var9 = false;
      } finally {
         if (var9) {
            synchronized(this) {
               if (this.bg != null) {
                  this.bg.close();
                  this.bg = null;
               }

               if (this.rbg != null) {
                  this.rbg.close();
                  this.rbg = null;
               }

            }
         }
      }

      synchronized(this) {
         if (this.bg != null) {
            this.bg.close();
            this.bg = null;
         }

         if (this.rbg != null) {
            this.rbg.close();
            this.rbg = null;
         }

      }
   }

   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateNClob(columnIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateNClob(columnIndex, this.rbg, blockid);
      }

   }

   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateNClob(columnIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateNClob(columnIndex, this.rbg, blockid, length);
      }

   }

   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      this.updateNClob(this.remoteRs.findColumn(columnLabel), reader);
   }

   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateNClob(this.remoteRs.findColumn(columnLabel), reader, length);
   }

   public void updateClob(int columnIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateClob(columnIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateClob(columnIndex, this.rbg, blockid);
      }

   }

   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateClob(columnIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateClob(columnIndex, this.rbg, blockid, length);
      }

   }

   public void updateClob(String columnLabel, Reader reader) throws SQLException {
      this.updateClob(this.remoteRs.findColumn(columnLabel), reader);
   }

   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateClob(this.remoteRs.findColumn(columnLabel), reader, length);
   }

   public void updateCharacterStream(int columnIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateCharacterStream(columnIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateCharacterStream(columnIndex, this.rbg, blockid);
      }

   }

   public void updateCharacterStream(int columnIndex, Reader reader, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateCharacterStream(columnIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateCharacterStream(columnIndex, this.rbg, blockid, length);
      }

   }

   public void updateCharacterStream(int columnIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateCharacterStream(columnIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateCharacterStream(columnIndex, this.rbg, blockid, length);
      }

   }

   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.updateCharacterStream(this.remoteRs.findColumn(columnLabel), reader);
   }

   public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
      this.updateCharacterStream(this.remoteRs.findColumn(columnLabel), reader, length);
   }

   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateCharacterStream(this.remoteRs.findColumn(columnLabel), reader, length);
   }

   public void updateNCharacterStream(int columnIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateNCharacterStream(columnIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateNCharacterStream(columnIndex, this.rbg, blockid);
      }

   }

   public void updateNCharacterStream(int columnIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.remoteRs.updateNCharacterStream(columnIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.remoteRs.updateNCharacterStream(columnIndex, this.rbg, blockid, length);
      }

   }

   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      this.updateNCharacterStream(this.remoteRs.findColumn(columnLabel), reader);
   }

   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      this.updateNCharacterStream(this.remoteRs.findColumn(columnLabel), reader, length);
   }

   public void updateAsciiStream(int columnIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateAsciiStream(columnIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateAsciiStream(columnIndex, this.bg, blockid);
      }

   }

   public void updateAsciiStream(int columnIndex, InputStream stream, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateAsciiStream(columnIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateAsciiStream(columnIndex, this.bg, blockid, length);
      }

   }

   public void updateAsciiStream(int columnIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateAsciiStream(columnIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateAsciiStream(columnIndex, this.bg, blockid, length);
      }

   }

   public void updateAsciiStream(String columnLabel, InputStream stream) throws SQLException {
      this.updateAsciiStream(this.remoteRs.findColumn(columnLabel), stream);
   }

   public void updateAsciiStream(String columnLabel, InputStream stream, int length) throws SQLException {
      this.updateAsciiStream(this.remoteRs.findColumn(columnLabel), stream, length);
   }

   public void updateAsciiStream(String columnLabel, InputStream stream, long length) throws SQLException {
      this.updateAsciiStream(this.remoteRs.findColumn(columnLabel), stream, length);
   }

   public void updateBinaryStream(int columnIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateBinaryStream(columnIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateBinaryStream(columnIndex, this.bg, blockid);
      }

   }

   public void updateBinaryStream(int columnIndex, InputStream stream, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateBinaryStream(columnIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateBinaryStream(columnIndex, this.bg, blockid, length);
      }

   }

   public void updateBinaryStream(int columnIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateBinaryStream(columnIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateBinaryStream(columnIndex, this.bg, blockid, length);
      }

   }

   public void updateBinaryStream(String columnLabel, InputStream stream) throws SQLException {
      this.updateBinaryStream(this.remoteRs.findColumn(columnLabel), stream);
   }

   public void updateBinaryStream(String columnLabel, InputStream stream, int length) throws SQLException {
      this.updateBinaryStream(this.remoteRs.findColumn(columnLabel), stream, length);
   }

   public void updateBinaryStream(String columnLabel, InputStream stream, long length) throws SQLException {
      this.updateBinaryStream(this.remoteRs.findColumn(columnLabel), stream, length);
   }

   public void updateBlob(int columnIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateBlob(columnIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateBlob(columnIndex, this.bg, blockid);
      }

   }

   public void updateBlob(int columnIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : updateBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.remoteRs.updateBlob(columnIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.remoteRs.updateBlob(columnIndex, this.bg, blockid, length);
      }

   }

   public void updateBlob(String columnLabel, InputStream stream) throws SQLException {
      this.updateBlob(this.remoteRs.findColumn(columnLabel), stream);
   }

   public void updateBlob(String columnLabel, InputStream stream, long length) throws SQLException {
      this.updateBlob(this.remoteRs.findColumn(columnLabel), stream, length);
   }

   public void updateObject(int columnIndex, Object x) throws SQLException {
      if (x == null) {
         this.remoteRs.updateObject(columnIndex, x);
      } else {
         String msg;
         int blockid;
         if (x instanceof Reader) {
            Reader reader = (Reader)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : updateObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.remoteRs.updateObject(columnIndex, this.rbg, blockid);
         } else if (x instanceof InputStream) {
            InputStream stream = (InputStream)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : updateObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
            this.remoteRs.updateObject(columnIndex, this.bg, blockid);
         } else {
            this.remoteRs.updateObject(columnIndex, x);
         }
      }

   }

   public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
      if (x == null) {
         this.remoteRs.updateObject(columnIndex, (Object)x, scaleOrLength);
      } else {
         String msg;
         int blockid;
         if (x instanceof Reader) {
            Reader reader = (Reader)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : updateObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.remoteRs.updateObject(columnIndex, this.rbg, blockid, scaleOrLength);
         } else if (x instanceof InputStream) {
            InputStream stream = (InputStream)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : updateObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
            this.remoteRs.updateObject(columnIndex, this.bg, blockid, scaleOrLength);
         } else {
            this.remoteRs.updateObject(columnIndex, (Object)x, scaleOrLength);
         }
      }

   }

   public void updateObject(String columnLabel, Object x) throws SQLException {
      if (x == null) {
         this.remoteRs.updateObject(columnLabel, x);
      } else if (!(x instanceof Reader) && !(x instanceof InputStream)) {
         this.remoteRs.updateObject(columnLabel, x);
      } else {
         this.updateObject(this.remoteRs.findColumn(columnLabel), x);
      }

   }

   public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
      if (x == null) {
         this.remoteRs.updateObject(columnLabel, x, scaleOrLength);
      } else if (!(x instanceof Reader) && !(x instanceof InputStream)) {
         this.remoteRs.updateObject(columnLabel, x, scaleOrLength);
      } else {
         this.updateObject(this.remoteRs.findColumn(columnLabel), x, scaleOrLength);
      }

   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderBlockGetter rbg = this.remoteRs.getReaderBlockGetter();
      int blockid = this.remoteRs.registerStream(columnIndex, 5);
      if (blockid == -1) {
         return null;
      } else {
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         return rh;
      }
   }

   public Reader getNCharacterStream(String columnName) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderBlockGetter rbg = this.remoteRs.getReaderBlockGetter();
      int blockid = this.remoteRs.registerStream(columnName, 5);
      if (blockid == -1) {
         return null;
      } else {
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         return rh;
      }
   }
}
