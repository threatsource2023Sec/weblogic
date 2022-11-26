package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class CallableStatementStub extends PreparedStatementStub {
   private static final long serialVersionUID = 8441497012896429985L;
   transient BlockGetter bg = null;
   private RmiDriverSettings rmiSettings = null;
   CallableStatement cstmt;

   public CallableStatementStub() {
   }

   public CallableStatementStub(CallableStatement cstmt, RmiDriverSettings settings) {
      this.init(cstmt, settings);
   }

   public void init(CallableStatement cstmt, RmiDriverSettings settings) {
      super.init(cstmt, settings);
      this.cstmt = cstmt;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      CallableStatementStub stub = null;

      try {
         stub = (CallableStatementStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.CallableStatementStub", this.cstmt, false);
         stub.init(this.cstmt, this.rmiSettings);
         return (java.sql.CallableStatement)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.cstmt;
      }
   }

   public CallableStatement getStubDelegateAsCS() {
      return (CallableStatement)this.getStubDelegate();
   }

   public InputStream getAsciiStream(int i) throws SQLException {
      InputStreamHandler ish = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getAsciiStream";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.getStubDelegateAsCS().getBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(i, 1);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ish;
   }

   public InputStream getBinaryStream(int i) throws SQLException {
      InputStreamHandler ish = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getBinaryStream";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.getStubDelegateAsCS().getBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(i, 3);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ish;
   }

   public InputStream getUnicodeStream(int i) throws SQLException {
      InputStreamHandler ish = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getUnicodeStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getUnicodeStream";
      Object[] params = new Object[]{new Integer(i)};

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.getStubDelegateAsCS().getBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(i, 2);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ish;
   }

   public void setClob(String parameterName, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setClob(parameterName, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setClob(parameterName, this.rbg, blockid);
      }

   }

   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setClob(parameterName, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setClob(parameterName, this.rbg, blockid, length);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setCharacterStream(parameterName, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setCharacterStream(parameterName, this.rbg, blockid);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setCharacterStream(parameterName, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setCharacterStream(parameterName, this.rbg, blockid, length);
      }

   }

   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setCharacterStream(parameterName, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setCharacterStream(parameterName, this.rbg, blockid, length);
      }

   }

   public void setNClob(String parameterName, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setNClob(parameterName, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setNClob(parameterName, this.rbg, blockid);
      }

   }

   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNClob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setNClob(parameterName, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setNClob(parameterName, this.rbg, blockid, length);
      }

   }

   public void setNCharacterStream(String parameterName, Reader reader) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setNCharacterStream(parameterName, reader);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setNCharacterStream(parameterName, this.rbg, blockid);
      }

   }

   public void setNCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (reader == null) {
         this.getStubDelegateAsCS().setNCharacterStream(parameterName, reader, length);
      } else {
         synchronized(this) {
            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }

         int blockid = this.rbg.register(reader, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setNCharacterStream(parameterName, this.rbg, blockid, length);
      }

   }

   public void setAsciiStream(String parameterName, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setAsciiStream(parameterName, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setAsciiStream(parameterName, this.bg, blockid);
      }

   }

   public void setAsciiStream(String parameterName, InputStream stream, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setAsciiStream(parameterName, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setAsciiStream(parameterName, this.bg, blockid, length);
      }

   }

   public void setAsciiStream(String parameterName, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setAsciiStream(parameterName, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setAsciiStream(parameterName, this.bg, blockid, length);
      }

   }

   public void setBinaryStream(String parameterName, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setBinaryStream(parameterName, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setBinaryStream(parameterName, this.bg, blockid);
      }

   }

   public void setBinaryStream(String parameterName, InputStream stream, int length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setBinaryStream(parameterName, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setBinaryStream(parameterName, this.bg, blockid, length);
      }

   }

   public void setBinaryStream(String parameterName, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setBinaryStream(parameterName, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setBinaryStream(parameterName, this.bg, blockid, length);
      }

   }

   public void setBlob(String parameterName, InputStream stream) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setBlob(parameterName, stream);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setBlob(parameterName, this.bg, blockid);
      }

   }

   public void setBlob(String parameterName, InputStream stream, long length) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : setBlob";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      if (stream == null) {
         this.getStubDelegateAsCS().setBlob(parameterName, stream, length);
      } else {
         synchronized(this) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         }

         int blockid = this.bg.register(stream, this.rmiSettings.getChunkSize());
         this.getStubDelegateAsCS().setBlob(parameterName, this.bg, blockid, length);
      }

   }

   public void setObject(String parameterName, Object x) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsCS().setObject(parameterName, x);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.rbg, blockid);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.bg, blockid);
         } else {
            this.getStubDelegateAsCS().setObject(parameterName, x);
         }
      }

   }

   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsCS().setObject(parameterName, (Object)x, targetSqlType);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.rbg, blockid, targetSqlType);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.bg, blockid, targetSqlType);
         } else {
            this.getStubDelegateAsCS().setObject(parameterName, (Object)x, targetSqlType);
         }
      }

   }

   public void setObject(String parameterName, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      if (x == null) {
         this.getStubDelegateAsCS().setObject(parameterName, (Object)x, targetSqlType, scaleOrLength);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.rbg, blockid, targetSqlType, scaleOrLength);
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
            this.getStubDelegateAsCS().setObject(parameterName, this.bg, blockid, targetSqlType, scaleOrLength);
         } else {
            this.getStubDelegateAsCS().setObject(parameterName, (Object)x, targetSqlType, scaleOrLength);
         }
      }

   }

   public Reader getCharacterStream(int columnIndex) throws SQLException {
      ReaderHandler rh = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getCharacterStream";
      Object[] params = new Object[]{columnIndex};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter bg = this.getStubDelegateAsCS().getReaderBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(columnIndex, 4);
         rh = new ReaderHandler();
         rh.setReaderBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, rh);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return rh;
   }

   public Reader getCharacterStream(String parameterName) throws SQLException {
      ReaderHandler rh = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getCharacterStream";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter bg = this.getStubDelegateAsCS().getReaderBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(parameterName, 4);
         rh = new ReaderHandler();
         rh.setReaderBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, rh);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return rh;
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      ReaderHandler rh = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getNCharacterStream";
      Object[] params = new Object[]{columnIndex};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter bg = this.getStubDelegateAsCS().getReaderBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(columnIndex, 5);
         rh = new ReaderHandler();
         rh.setReaderBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, rh);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return rh;
   }

   public Reader getNCharacterStream(String parameterName) throws SQLException {
      ReaderHandler rh = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getNCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getNCharacterStream";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter bg = this.getStubDelegateAsCS().getReaderBlockGetter();
         int blockid = this.getStubDelegateAsCS().registerStream(parameterName, 5);
         rh = new ReaderHandler();
         rh.setReaderBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, rh);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return rh;
   }
}
