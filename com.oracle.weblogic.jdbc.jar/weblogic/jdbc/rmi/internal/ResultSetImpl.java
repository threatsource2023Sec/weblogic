package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.sql.NClob;
import java.sql.SQLException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class ResultSetImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private java.sql.ResultSet t2_rs = null;
   private BlockGetter bg = new BlockGetterImpl();
   private ReaderBlockGetter rbg = null;
   private RmiDriverSettings rmiSettings = null;
   private ResultSetMetaDataCache mdCache = null;
   private ResultSetRowCache nextRowCache = null;
   public static final int ASCII_STREAM = 1;
   public static final int UNICODE_STREAM = 2;
   public static final int BINARY_STREAM = 3;
   public static final int CHARACTER_STREAM = 4;
   public static final int NCHARACTER_STREAM = 5;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof java.sql.ResultSet) {
               ret = makeResultSetImpl((java.sql.ResultSet)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Blob) {
               ret = OracleTBlobImpl.makeOracleTBlobImpl((java.sql.Blob)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Clob) {
               ret = OracleTClobImpl.makeOracleTClobImpl((java.sql.Clob)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Struct) {
               ret = StructImpl.makeStructImpl((java.sql.Struct)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Ref) {
               ret = RefImpl.makeRefImpl((java.sql.Ref)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Array) {
               ret = ArrayImpl.makeArrayImpl((java.sql.Array)ret, this.rmiSettings);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
            throw var5;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void init(java.sql.ResultSet rs, RmiDriverSettings settings) {
      this.t2_rs = rs;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.ResultSet makeResultSetImpl(java.sql.ResultSet anResultSet, RmiDriverSettings rmiDriverSettings) {
      ResultSetImpl rmi_rs = (ResultSetImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ResultSetImpl", anResultSet, true);
      rmi_rs.init(anResultSet, rmiDriverSettings);
      return (java.sql.ResultSet)rmi_rs;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new ResultSetStub((ResultSet)stub, this.rmiSettings);
   }

   public boolean isRowCaching() throws SQLException {
      if (this.rmiSettings.getRowCacheSize() <= 1) {
         if (this.rmiSettings.isVerbose()) {
            JdbcDebug.JDBCRMIInternal.debug("rmiSettings.rowCacheSize is " + this.rmiSettings.getRowCacheSize() + " so returning false");
         }

         return false;
      } else {
         if (this.mdCache == null) {
            this.mdCache = new ResultSetMetaDataCache(this.t2_rs);
         }

         try {
            if (this.isResultSetCacheable()) {
               this.nextRowCache = new ResultSetRowCache(this.rmiSettings.getRowCacheSize(), this.t2_rs, this.mdCache);
            } else {
               this.rmiSettings.setRowCacheSize(0);
               if (this.rmiSettings.isVerbose()) {
                  JdbcDebug.JDBCRMIInternal.debug("Result set is not cacheable");
               }
            }
         } catch (Exception var2) {
            if (this.rmiSettings.isVerbose()) {
               JdbcDebug.JDBCRMIInternal.debug("Exception received: " + var2);
               JDBCLogger.logStackTrace(var2);
            }

            this.rmiSettings.setRowCacheSize(-1);
            if (var2 instanceof SQLException) {
               throw (SQLException)var2;
            }

            throw new SQLException(var2.toString());
         }

         if (this.rmiSettings.isVerbose()) {
            JdbcDebug.JDBCRMIInternal.debug("isRowCaching: rmiSettings.rowCacheSize is " + this.rmiSettings.getRowCacheSize());
         }

         return this.rmiSettings.getRowCacheSize() > 1;
      }
   }

   private boolean isResultSetCacheable() throws SQLException {
      try {
         if (this.t2_rs.getType() != 1003) {
            return false;
         }

         if (this.t2_rs.getConcurrency() != 1007) {
            return false;
         }
      } catch (SQLException var2) {
         if (this.rmiSettings.isVerbose()) {
            JdbcDebug.JDBCRMIInternal.debug("Trapped exception: " + var2);
            JDBCLogger.logStackTrace(var2);
         }
      }

      return ResultSetRowCache.isCacheable(this.mdCache);
   }

   public ResultSetMetaDataCache getMetaDataCache() throws SQLException {
      if (this.mdCache == null) {
         this.mdCache = new ResultSetMetaDataCache(this.t2_rs);
      }

      return this.mdCache;
   }

   public ResultSetRowCache getNextRowCache() throws SQLException {
      ResultSetRowCache curr = this.nextRowCache;
      if (!curr.isTrueSetFinished()) {
         this.nextRowCache = new ResultSetRowCache(this.rmiSettings.getRowCacheSize(), this.t2_rs, this.mdCache);
         if (this.nextRowCache.getRowCount() < 1) {
            curr.setTrueSetFinished(true);
         }
      }

      return curr;
   }

   public BlockGetter getBlockGetter() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getBlockGetter";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (this.bg == null) {
            this.bg = new BlockGetterImpl();
         }
      }

      return this.bg;
   }

   public ReaderBlockGetter getReaderBlockGetter() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getReaderBlockGetter";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (this.rbg == null) {
            this.rbg = new ReaderBlockGetterImpl();
         }
      }

      return this.rbg;
   }

   public int registerStream(int i, int streamType) throws SQLException {
      String rdr;
      if (this.rmiSettings.isVerbose()) {
         rdr = "time=" + System.currentTimeMillis() + " : registerStream";
         JdbcDebug.JDBCRMIInternal.debug(rdr);
      }

      if (streamType != 4 && streamType != 5) {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         InputStream is;
         if (streamType == 1) {
            is = this.t2_rs.getAsciiStream(i);
         } else if (streamType == 2) {
            is = this.t2_rs.getUnicodeStream(i);
         } else {
            if (streamType != 3) {
               throw new SQLException("Invalid stream type: " + streamType);
            }

            is = this.t2_rs.getBinaryStream(i);
         }

         if (is == null) {
            return -1;
         } else {
            int blockid = this.bg.register(is, this.rmiSettings.getChunkSize());
            return blockid;
         }
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         rdr = null;
         Reader rdr;
         if (streamType == 4) {
            rdr = this.t2_rs.getCharacterStream(i);
         } else {
            rdr = this.t2_rs.getNCharacterStream(i);
         }

         return rdr == null ? -1 : this.rbg.register(rdr, this.rmiSettings.getChunkSize());
      }
   }

   public int registerStream(String i, int streamType) throws SQLException {
      String rdr;
      if (this.rmiSettings.isVerbose()) {
         rdr = "time=" + System.currentTimeMillis() + " : registerStream";
         JdbcDebug.JDBCRMIInternal.debug(rdr);
      }

      synchronized(this) {
         if (this.bg == null) {
            this.bg = new BlockGetterImpl();
         }
      }

      if (streamType != 4 && streamType != 5) {
         InputStream is;
         if (streamType == 1) {
            is = this.t2_rs.getAsciiStream(i);
         } else if (streamType == 2) {
            is = this.t2_rs.getUnicodeStream(i);
         } else {
            if (streamType != 3) {
               throw new SQLException("Invalid stream type: " + streamType);
            }

            is = this.t2_rs.getBinaryStream(i);
         }

         if (is == null) {
            return -1;
         } else {
            int blockid = this.bg.register(is, this.rmiSettings.getChunkSize());
            return blockid;
         }
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         rdr = null;
         Reader rdr;
         if (streamType == 4) {
            rdr = this.t2_rs.getCharacterStream(i);
         } else {
            rdr = this.t2_rs.getNCharacterStream(i);
         }

         return rdr == null ? -1 : this.rbg.register(rdr, this.rmiSettings.getChunkSize());
      }
   }

   public void close() throws SQLException {
      this.t2_rs.close();
      if (this.bg != null) {
         this.bg.close();
      }

      if (this.rbg != null) {
         this.rbg.close();
      }

      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

   }

   public java.sql.Statement getStatement() throws SQLException {
      java.sql.Statement ret = null;
      String methodName = "getStatement";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_rs.getStatement();
         if (ret != null) {
            ret = StatementImpl.makeStatementImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.ResultSetMetaData getMetaData() throws SQLException {
      java.sql.ResultSetMetaData rsmd = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         rsmd = this.t2_rs.getMetaData();
         if (rsmd != null) {
            rsmd = new ResultSetMetaDataImpl((java.sql.ResultSetMetaData)rsmd);
         }

         this.postInvocationHandler(methodName, params, rsmd);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.ResultSetMetaData)rsmd;
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_rs.getNClob(columnIndex);
         if (ret != null) {
            ret = OracleTNClobImpl.makeOracleTNClobImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public NClob getNClob(String columnLabel) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_rs.getNClob(columnLabel);
         if (ret != null) {
            ret = OracleTNClobImpl.makeOracleTNClobImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.SQLXML getSQLXML(int columnIndex) throws SQLException {
      java.sql.SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{new Integer(columnIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_rs.getSQLXML(columnIndex);
         if (ret != null) {
            ret = SQLXMLImpl.makeSQLXMLImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.SQLXML getSQLXML(String columnLabel) throws SQLException {
      java.sql.SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{columnLabel};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_rs.getSQLXML(columnLabel);
         if (ret != null) {
            ret = SQLXMLImpl.makeSQLXMLImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public void updateNClob(int columnIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "updateNClob";
      Object[] params = new Object[]{columnIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateNClob(columnIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateNClob(int columnIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "updateNClob";
      Object[] params = new Object[]{columnIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateNClob(columnIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateClob(int columnIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "updateClob";
      Object[] params = new Object[]{columnIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateClob(columnIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateClob(int columnIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "updateClob";
      Object[] params = new Object[]{columnIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateClob(columnIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateCharacterStream(int columnIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "updateCharacterStream";
      Object[] params = new Object[]{columnIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateCharacterStream(columnIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateCharacterStream(int columnIndex, ReaderBlockGetter rbg, int blockid, int length) throws SQLException {
      String methodName = "updateCharacterStream";
      Object[] params = new Object[]{columnIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateCharacterStream(columnIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void updateCharacterStream(int columnIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "updateCharacterStream";
      Object[] params = new Object[]{columnIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateCharacterStream(columnIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateNCharacterStream(int columnIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "updateNCharacterStream";
      Object[] params = new Object[]{columnIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateNCharacterStream(columnIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateNCharacterStream(int columnIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "updateNCharacterStream";
      Object[] params = new Object[]{columnIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateNCharacterStream(columnIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateAsciiStream(int columnIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "updateAsciiStream";
      Object[] params = new Object[]{columnIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateAsciiStream(columnIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateAsciiStream(int columnIndex, BlockGetter bg, int blockid, int length) throws SQLException {
      String methodName = "updateAsciiStream";
      Object[] params = new Object[]{columnIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateAsciiStream(columnIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void updateAsciiStream(int columnIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "updateAsciiStream";
      Object[] params = new Object[]{columnIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateAsciiStream(columnIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateBinaryStream(int columnIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "updateBinaryStream";
      Object[] params = new Object[]{columnIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateBinaryStream(columnIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateBinaryStream(int columnIndex, BlockGetter bg, int blockid, int length) throws SQLException {
      String methodName = "updateBinaryStream";
      Object[] params = new Object[]{columnIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateBinaryStream(columnIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void updateBinaryStream(int columnIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "updateBinaryStream";
      Object[] params = new Object[]{columnIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateBinaryStream(columnIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateBlob(int columnIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "updateBlob";
      Object[] params = new Object[]{columnIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateBlob(columnIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateBlob(int columnIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "updateBlob";
      Object[] params = new Object[]{columnIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateBlob(columnIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void updateObject(int columnIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "updateObject";
      Object[] params = new Object[]{columnIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateObject(columnIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateObject(int columnIndex, ReaderBlockGetter rbg, int blockid, int scaleOrLength) throws SQLException {
      String methodName = "updateObject";
      Object[] params = new Object[]{columnIndex, rbg, blockid, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.t2_rs.updateObject(columnIndex, rh, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void updateObject(int columnIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "updateObject";
      Object[] params = new Object[]{columnIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateObject(columnIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void updateObject(int columnIndex, BlockGetter bg, int blockid, int scaleOrLength) throws SQLException {
      String methodName = "updateObject";
      Object[] params = new Object[]{columnIndex, bg, blockid, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.t2_rs.updateObject(columnIndex, ish, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }
}
