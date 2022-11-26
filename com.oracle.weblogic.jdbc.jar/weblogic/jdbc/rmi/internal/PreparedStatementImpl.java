package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.rmi.Remote;
import java.sql.NClob;
import java.sql.SQLException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JDBCWrapperImpl;
import weblogic.rmi.extensions.StubFactory;

public class PreparedStatementImpl extends StatementImpl implements InteropWriteReplaceable {
   protected java.sql.PreparedStatement t2_pstmt = null;
   protected RmiDriverSettings rmiSettings = null;

   public PreparedStatementImpl() {
   }

   public PreparedStatementImpl(java.sql.PreparedStatement s, RmiDriverSettings settings) {
      this.init(s, settings);
   }

   public void init(java.sql.PreparedStatement s, RmiDriverSettings settings) {
      super.init(s, settings);
      this.t2_pstmt = s;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.PreparedStatement makePreparedStatementImpl(java.sql.PreparedStatement s, RmiDriverSettings settings) {
      if (s == null) {
         return null;
      } else {
         PreparedStatementImpl rmi_stmt = (PreparedStatementImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.PreparedStatementImpl", s, true);
         rmi_stmt.init(s, settings);
         return (java.sql.PreparedStatement)rmi_stmt;
      }
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new PreparedStatementStub((PreparedStatement)stub, this.rmiSettings);
   }

   public java.sql.PreparedStatement getImplDelegateAsPS() {
      return (java.sql.PreparedStatement)this.getImplDelegate();
   }

   public void setAsciiStream(int parameterIndex, BlockGetter bg, int blockId, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      InputStreamHandler ish = new InputStreamHandler();
      ish.setBlockGetter(bg, blockId);
      this.setAsciiStream(parameterIndex, (InputStream)ish, length);
   }

   public void setUnicodeStream(int parameterIndex, BlockGetter bg, int blockId, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setUnicodeStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      InputStreamHandler ish = new InputStreamHandler();
      ish.setBlockGetter(bg, blockId);
      this.setUnicodeStream(parameterIndex, ish, length);
   }

   public void setBinaryStream(int parameterIndex, BlockGetter bg, int blockId, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      InputStreamHandler ish = new InputStreamHandler();
      ish.setBlockGetter(bg, blockId);
      this.setBinaryStream(parameterIndex, (InputStream)ish, length);
   }

   public void setCharacterStream(int parameterIndex, ReaderBlockGetter rbg, int blockId, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderHandler rh = new ReaderHandler();
      rh.setReaderBlockGetter(rbg, blockId);
      this.setCharacterStream(parameterIndex, (Reader)rh, length);
   }

   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         this.t2_pstmt.setAsciiStream(parameterIndex, x, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName = "setUnicodeStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         this.t2_pstmt.setUnicodeStream(parameterIndex, x, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         this.t2_pstmt.setBinaryStream(parameterIndex, x, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{new Integer(parameterIndex), reader, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         this.t2_pstmt.setCharacterStream(parameterIndex, reader, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(targetSqlType), new Integer(scale)};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setObject(parameterIndex, ((JDBCWrapperImpl)x).getVendorObj(), targetSqlType, scale);
         } else {
            this.t2_pstmt.setObject(parameterIndex, x, targetSqlType, scale);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(targetSqlType)};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setObject(parameterIndex, ((JDBCWrapperImpl)x).getVendorObj(), targetSqlType);
         } else {
            this.t2_pstmt.setObject(parameterIndex, x, targetSqlType);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(int parameterIndex, Object x) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{new Integer(parameterIndex), x};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setObject(parameterIndex, ((JDBCWrapperImpl)x).getVendorObj());
         } else {
            this.t2_pstmt.setObject(parameterIndex, x);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setRef(int i, java.sql.Ref x) throws SQLException {
      String methodName = "setRef";
      Object[] params = new Object[]{new Integer(i), x};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setRef(i, (java.sql.Ref)((JDBCWrapperImpl)x).getVendorObj());
         } else {
            this.t2_pstmt.setRef(i, x);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setBlob(int i, java.sql.Blob x) throws SQLException {
      String methodName = "setBlob";
      Object[] params = new Object[]{new Integer(i), x};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setBlob(i, (java.sql.Blob)((JDBCWrapperImpl)x).getVendorObj());
         } else {
            this.t2_pstmt.setBlob(i, x);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setClob(int i, java.sql.Clob x) throws SQLException {
      String methodName = "setClob";
      Object[] params = new Object[]{new Integer(i), x};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setClob(i, (java.sql.Clob)((JDBCWrapperImpl)x).getVendorObj());
         } else {
            this.t2_pstmt.setClob(i, x);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setArray(int i, java.sql.Array x) throws SQLException {
      String methodName = "setArray";
      Object[] params = new Object[]{new Integer(i), x};

      try {
         this.preInvocationHandler(methodName, params);
         if (x instanceof JDBCWrapperImpl) {
            this.t2_pstmt.setArray(i, (java.sql.Array)((JDBCWrapperImpl)x).getVendorObj());
         } else {
            this.t2_pstmt.setArray(i, x);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public java.sql.ResultSetMetaData getMetaData() throws SQLException {
      java.sql.ResultSetMetaData rsmd = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         rsmd = this.t2_pstmt.getMetaData();
         if (rsmd != null) {
            rsmd = new ResultSetMetaDataImpl((java.sql.ResultSetMetaData)rsmd);
         }

         this.postInvocationHandler(methodName, params, rsmd);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.ResultSetMetaData)rsmd;
   }

   public java.sql.ParameterMetaData getParameterMetaData() throws SQLException {
      java.sql.ParameterMetaData pmd = null;
      String methodName = "getParameterMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         pmd = this.t2_pstmt.getParameterMetaData();
         if (pmd != null) {
            pmd = new ParameterMetaDataImpl((java.sql.ParameterMetaData)pmd);
         }

         this.postInvocationHandler(methodName, params, pmd);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.ParameterMetaData)pmd;
   }

   public java.sql.ResultSet executeQuery() throws SQLException {
      java.sql.ResultSet ret = null;
      String methodName = "executeQuery";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.getImplDelegateAsPS().executeQuery();
         if (ret != null) {
            ret = ResultSetImpl.makeResultSetImpl(ret, this.rmiSettings);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setNClob(int parameterIndex, NClob value) throws SQLException {
      String methodName = "setNClob";
      Object[] params = new Object[]{new Integer(parameterIndex), value};

      try {
         this.preInvocationHandler(methodName, params);
         if (value instanceof JDBCWrapperImpl) {
            this.getImplDelegateAsPS().setNClob(parameterIndex, (NClob)((JDBCWrapperImpl)value).getVendorObj());
         } else {
            this.getImplDelegateAsPS().setNClob(parameterIndex, value);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setSQLXML(int parameterIndex, java.sql.SQLXML xmlObject) throws SQLException {
      String methodName = "setSQLXML";
      Object[] params = new Object[]{new Integer(parameterIndex), xmlObject};

      try {
         this.preInvocationHandler(methodName, params);
         if (xmlObject instanceof JDBCWrapperImpl) {
            this.getImplDelegateAsPS().setSQLXML(parameterIndex, (java.sql.SQLXML)((JDBCWrapperImpl)xmlObject).getVendorObj());
         } else {
            this.getImplDelegateAsPS().setSQLXML(parameterIndex, xmlObject);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void setClob(int parameterIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setClob";
      Object[] params = new Object[]{parameterIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setClob(parameterIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setClob(int parameterIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setClob";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setClob(parameterIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setCharacterStream(int parameterIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{parameterIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setCharacterStream(parameterIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setCharacterStream(int parameterIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setCharacterStream";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setCharacterStream(parameterIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setNClob(int parameterIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setNClob";
      Object[] params = new Object[]{parameterIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setNClob(parameterIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setNClob(int parameterIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setNClob";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setNClob(parameterIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setNCharacterStream(int parameterIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setNCharacterStream";
      Object[] params = new Object[]{parameterIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setNCharacterStream(parameterIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setNCharacterStream(int parameterIndex, ReaderBlockGetter rbg, int blockid, long length) throws SQLException {
      String methodName = "setNCharacterStream";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setNCharacterStream(parameterIndex, rh, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setAsciiStream(int parameterIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{parameterIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setAsciiStream(parameterIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setAsciiStream(int parameterIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{parameterIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setAsciiStream(parameterIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setBinaryStream(int parameterIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{parameterIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setBinaryStream(parameterIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setBinaryStream(int parameterIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setBinaryStream";
      Object[] params = new Object[]{parameterIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setBinaryStream(parameterIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setBlob(int parameterIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setBlob";
      Object[] params = new Object[]{parameterIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setBlob(parameterIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setBlob(int parameterIndex, BlockGetter bg, int blockid, long length) throws SQLException {
      String methodName = "setBlob";
      Object[] params = new Object[]{parameterIndex, bg, blockid, length};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setBlob(parameterIndex, ish, length);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setObject(int parameterIndex, ReaderBlockGetter rbg, int blockid) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, rbg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, rh);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(int parameterIndex, ReaderBlockGetter rbg, int blockid, int targetSqlType) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, targetSqlType};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, rh, targetSqlType);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setObject(int parameterIndex, ReaderBlockGetter rbg, int blockid, int targetSqlType, int scaleOrLength) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, rbg, blockid, targetSqlType, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderHandler rh = new ReaderHandler();
         rh.setReaderBlockGetter(rbg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, rh, targetSqlType, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setObject(int parameterIndex, BlockGetter bg, int blockid) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, bg, blockid};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, ish);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void setObject(int parameterIndex, BlockGetter bg, int blockid, int targetSqlType) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, bg, blockid, targetSqlType};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, ish, targetSqlType);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public void setObject(int parameterIndex, BlockGetter bg, int blockid, int targetSqlType, int scaleOrLength) throws SQLException {
      String methodName = "setObject";
      Object[] params = new Object[]{parameterIndex, bg, blockid, targetSqlType, scaleOrLength};

      try {
         this.preInvocationHandler(methodName, params);
         InputStreamHandler ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.getImplDelegateAsPS().setObject(parameterIndex, ish, targetSqlType, scaleOrLength);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }
}
