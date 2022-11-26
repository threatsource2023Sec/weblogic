package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;

public class CallableStatementImpl extends PreparedStatementImpl {
   private java.sql.CallableStatement t2_cstmt = null;
   private RmiDriverSettings rmiSettings = null;
   private BlockGetter bg = null;
   private ReaderBlockGetter rbg = null;
   public static final int ASCII_STREAM = 1;
   public static final int UNICODE_STREAM = 2;
   public static final int BINARY_STREAM = 3;
   public static final int CHARACTER_STREAM = 4;
   public static final int NCHARACTER_STREAM = 5;

   public CallableStatementImpl() {
   }

   public CallableStatementImpl(java.sql.CallableStatement s, RmiDriverSettings settings) {
      this.init(s, settings);
   }

   public void init(java.sql.CallableStatement s, RmiDriverSettings settings) {
      super.init(s, settings);
      this.t2_cstmt = s;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.CallableStatement makeCallableStatementImpl(java.sql.CallableStatement s, RmiDriverSettings settings) {
      if (s == null) {
         return null;
      } else {
         CallableStatementImpl rmi_stmt = (CallableStatementImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.CallableStatementImpl", s, true);
         rmi_stmt.init(s, settings);
         return (java.sql.CallableStatement)rmi_stmt;
      }
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new CallableStatementStub((CallableStatement)stub, this.rmiSettings);
   }

   public java.sql.CallableStatement getImplDelegateAsCS() {
      return (java.sql.CallableStatement)this.getImplDelegate();
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
            is = this.getAsciiStream(i);
         } else if (streamType == 2) {
            is = this.getUnicodeStream(i);
         } else {
            if (streamType != 3) {
               throw new SQLException("Invalid stream type: " + streamType);
            }

            is = this.getBinaryStream(i);
         }

         int blockid = this.bg.register(is, this.rmiSettings.getChunkSize());
         return blockid;
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         rdr = null;
         Reader rdr;
         if (streamType == 4) {
            rdr = this.getImplDelegateAsCS().getCharacterStream(i);
         } else {
            rdr = this.getImplDelegateAsCS().getNCharacterStream(i);
         }

         return rdr == null ? -1 : this.rbg.register(rdr, this.rmiSettings.getChunkSize());
      }
   }

   public int registerStream(String parameterName, int streamType) throws SQLException {
      String rdr;
      if (this.rmiSettings.isVerbose()) {
         rdr = "time=" + System.currentTimeMillis() + " : registerStream";
         JdbcDebug.JDBCRMIInternal.debug(rdr);
      }

      if (streamType != 4 && streamType != 5) {
         throw new SQLException("Invalid stream type: " + streamType);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         rdr = null;
         Reader rdr;
         if (streamType == 4) {
            rdr = this.getImplDelegateAsCS().getCharacterStream(parameterName);
         } else {
            rdr = this.getImplDelegateAsCS().getNCharacterStream(parameterName);
         }

         return rdr == null ? -1 : this.rbg.register(rdr, this.rmiSettings.getChunkSize());
      }
   }

   public void close() throws SQLException {
      super.close();
      if (this.bg != null) {
         this.bg.close();
      }

      if (this.rbg != null) {
         this.rbg.close();
      }

   }

   public InputStream getAsciiStream(int i) throws SQLException {
      throw new SQLException("This vendor feature is not supported");
   }

   public InputStream getBinaryStream(int i) throws SQLException {
      throw new SQLException("This vendor feature is not supported");
   }

   public InputStream getUnicodeStream(int i) throws SQLException {
      throw new SQLException("This vendor feature is not supported");
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

   public void setClob(String parameterName, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setClob";
      Object[] params = new Object[]{parameterName, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setClob(parameterName, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setClob(String parameterName, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setClob";
      Object[] params = new Object[]{parameterName, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setClob(parameterName, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setCharacterStream(String parameterName, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{parameterName, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setCharacterStream(parameterName, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setCharacterStream(String parameterName, ReaderBlockGetter rbg, int blockid, int length) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{parameterName, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setCharacterStream(parameterName, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setCharacterStream(String parameterName, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{parameterName, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setCharacterStream(parameterName, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setNClob(String parameterName, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setNClob";
      Object[] params = new Object[]{parameterName, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setNClob(parameterName, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setNClob(String parameterName, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setNClob";
      Object[] params = new Object[]{parameterName, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setNClob(parameterName, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setNCharacterStream(String parameterName, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setNCharacterStream";
      Object[] params = new Object[]{parameterName, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setNCharacterStream(parameterName, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setNCharacterStream(String parameterName, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setNCharacterStream";
      Object[] params = new Object[]{parameterName, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setNCharacterStream(parameterName, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setAsciiStream(String parameterName, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{parameterName, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setAsciiStream(parameterName, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setAsciiStream(String parameterName, BlockGetter bg, int blockid, int length) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{parameterName, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setAsciiStream(parameterName, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setAsciiStream(String parameterName, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{parameterName, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setAsciiStream(parameterName, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setBinaryStream(String parameterName, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{parameterName, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setBinaryStream(parameterName, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setBinaryStream(String parameterName, BlockGetter bg, int blockid, int length) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{parameterName, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setBinaryStream(parameterName, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setBinaryStream(String parameterName, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{parameterName, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setBinaryStream(parameterName, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setBlob(String parameterName, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setBlob";
      Object[] params = new Object[]{parameterName, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setBlob(parameterName, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setBlob(String parameterName, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setBlob";
      Object[] params = new Object[]{parameterName, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setBlob(parameterName, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setObject(String parameterName, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(String parameterName, ReaderBlockGetter rbg, int blockid, int targetSqlType) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, rbg, blockid, targetSqlType};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, rh, targetSqlType);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setObject(String parameterName, ReaderBlockGetter rbg, int blockid, int targetSqlType, int scaleOrLength) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, rbg, blockid, targetSqlType, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, rh, targetSqlType, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setObject(String parameterName, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(String parameterName, BlockGetter bg, int blockid, int targetSqlType) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, bg, blockid, targetSqlType};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, ish, targetSqlType);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setObject(String parameterName, BlockGetter bg, int blockid, int targetSqlType, int scaleOrLength) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterName, bg, blockid, targetSqlType, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsCS().setObject(parameterName, ish, targetSqlType, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }
}
