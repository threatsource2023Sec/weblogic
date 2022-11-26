package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class PreparedStatementStub extends StatementStub implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = -2902370428006989865L;
   transient BlockGetter bg = null;
   transient ReaderBlockGetter rbg = null;
   protected RmiDriverSettings rmiSettings = null;
   protected PreparedStatement pstmt;

   public PreparedStatementStub() {
   }

   public PreparedStatementStub(PreparedStatement pstmt, RmiDriverSettings settings) {
      this.init(pstmt, settings);
   }

   public void init(PreparedStatement pstmt, RmiDriverSettings settings) {
      super.init(pstmt, settings);
      this.pstmt = pstmt;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      PreparedStatementStub stub = null;

      try {
         stub = (PreparedStatementStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.PreparedStatementStub", this.pstmt, false);
         stub.init(this.pstmt, this.rmiSettings);
         return (java.sql.PreparedStatement)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.pstmt;
      }
   }

   public PreparedStatement getStubDelegateAsPS() {
      return (PreparedStatement)this.getStubDelegate();
   }

   public Object getStubDelegate() {
      return this.pstmt;
   }

   public int getRmiFetchSize() throws SQLException {
      return this.rmiSettings.getRowCacheSize();
   }

   public void setRmiFetchSize(int new_size) throws SQLException {
      this.pstmt.setRmiFetchSize(new_size);
      this.rmiSettings.setRowCacheSize(new_size);
   }

   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName = "setAsciiStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         if (this.rmiSettings.isVerbose()) {
            String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
            JdbcDebug.JDBCRMIInternal.debug(msg);
         }

         if (x != null && length > 0) {
            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            int blockid = this.bg.register(x, this.rmiSettings.getChunkSize());
            this.pstmt.setAsciiStream(parameterIndex, this.bg, blockid, length);
         } else {
            this.pstmt.setAsciiStream(parameterIndex, x, length);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : setUnicodeStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "setUnicodeStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         if (x != null && length > 0) {
            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            int blockid = this.bg.register(x, this.rmiSettings.getChunkSize());
            this.pstmt.setUnicodeStream(parameterIndex, this.bg, blockid, length);
         } else {
            this.pstmt.setUnicodeStream(parameterIndex, x, length);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "setBinaryStream";
      Object[] params = new Object[]{new Integer(parameterIndex), x, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         if (x != null && length > 0) {
            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            int blockid = this.bg.register(x, this.rmiSettings.getChunkSize());
            this.pstmt.setBinaryStream(parameterIndex, this.bg, blockid, length);
         } else {
            this.pstmt.setBinaryStream(parameterIndex, x, length);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "setCharacterStream";
      Object[] params = new Object[]{new Integer(parameterIndex), reader, new Integer(length)};

      try {
         this.preInvocationHandler(methodName, params);
         if (reader != null && length > 0) {
            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.pstmt.setCharacterStream(parameterIndex, this.rbg, blockid, length);
         } else {
            this.pstmt.setCharacterStream(parameterIndex, reader, length);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public java.sql.ResultSetMetaData getMetaData() throws SQLException {
      java.sql.ResultSetMetaData rsmd = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         rsmd = this.pstmt.getMetaData();
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
         pmd = this.pstmt.getParameterMetaData();
         if (pmd != null) {
            pmd = new ParameterMetaDataImpl((java.sql.ParameterMetaData)pmd);
         }

         this.postInvocationHandler(methodName, params, pmd);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.ParameterMetaData)pmd;
   }

   public void close() throws SQLException {
      super.close();
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

   public void setClob(int parameterIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setClob(parameterIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setClob(parameterIndex, this.rbg, blockid);
      }

   }

   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setClob(parameterIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setClob(parameterIndex, this.rbg, blockid, length);
      }

   }

   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setCharacterStream(parameterIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setCharacterStream(parameterIndex, this.rbg, blockid);
      }

   }

   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setCharacterStream(parameterIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setCharacterStream(parameterIndex, this.rbg, blockid, length);
      }

   }

   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setNClob(parameterIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setNClob(parameterIndex, this.rbg, blockid);
      }

   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setNClob(parameterIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setNClob(parameterIndex, this.rbg, blockid, length);
      }

   }

   public void setNCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setNCharacterStream(parameterIndex, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setNCharacterStream(parameterIndex, this.rbg, blockid);
      }

   }

   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsPS().setNCharacterStream(parameterIndex, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setNCharacterStream(parameterIndex, this.rbg, blockid, length);
      }

   }

   public void setAsciiStream(int parameterIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setAsciiStream(parameterIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setAsciiStream(parameterIndex, this.bg, blockid);
      }

   }

   public void setAsciiStream(int parameterIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setAsciiStream(parameterIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setAsciiStream(parameterIndex, this.bg, blockid, length);
      }

   }

   public void setBinaryStream(int parameterIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setBinaryStream(parameterIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setBinaryStream(parameterIndex, this.bg, blockid);
      }

   }

   public void setBinaryStream(int parameterIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setBinaryStream(parameterIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setBinaryStream(parameterIndex, this.bg, blockid, length);
      }

   }

   public void setBlob(int parameterIndex, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setBlob(parameterIndex, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setBlob(parameterIndex, this.bg, blockid);
      }

   }

   public void setBlob(int parameterIndex, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsPS().setBlob(parameterIndex, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsPS().setBlob(parameterIndex, this.bg, blockid, length);
      }

   }

   public void setObject(int parameterIndex, Object x) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsPS().setObject(parameterIndex, x);
      } else {
         String msg;
         int blockid;
         if (x instanceof Reader) {
            Reader reader = (Reader)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.rbg, blockid);
         } else if (x instanceof InputStream) {
            InputStream stream = (InputStream)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.bg, blockid);
         } else {
            this.getStubDelegateAsPS().setObject(parameterIndex, x);
         }
      }

   }

   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsPS().setObject(parameterIndex, (Object)x, targetSqlType);
      } else {
         String msg;
         int blockid;
         if (x instanceof Reader) {
            Reader reader = (Reader)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.rbg, blockid, targetSqlType);
         } else if (x instanceof InputStream) {
            InputStream stream = (InputStream)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.bg, blockid, targetSqlType);
         } else {
            this.getStubDelegateAsPS().setObject(parameterIndex, (Object)x, targetSqlType);
         }
      }

   }

   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsPS().setObject(parameterIndex, (Object)x, targetSqlType, scaleOrLength);
      } else {
         String msg;
         int blockid;
         if (x instanceof Reader) {
            Reader reader = (Reader)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.rbg == null) {
                  this.rbg = new ReaderBlockGetterImpl();
               }
            }

            blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.rbg, blockid, targetSqlType, scaleOrLength);
         } else if (x instanceof InputStream) {
            InputStream stream = (InputStream)x;
            if (this.rmiSettings.isVerbose()) {
               msg = "time=" + System.currentTimeMillis() + " : setObject";
               JdbcDebug.JDBCRMIInternal.debug(msg);
            }

            synchronized(this) {
               if (this.bg == null) {
                  this.bg = new BlockGetterImpl();
               }
            }

            blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
            this.getStubDelegateAsPS().setObject(parameterIndex, this.bg, blockid, targetSqlType, scaleOrLength);
         } else {
            this.getStubDelegateAsPS().setObject(parameterIndex, (Object)x, targetSqlType, scaleOrLength);
         }
      }

   }
}
