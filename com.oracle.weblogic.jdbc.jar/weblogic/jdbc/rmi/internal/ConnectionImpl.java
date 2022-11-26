package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.server.Unreferenced;
import java.sql.NClob;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.WLDataSourceImpl;
import weblogic.jdbc.rmi.RMIWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rjvm.RJVM;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.server.UnicastRemoteObject;
import weblogic.rmi.spi.EndPoint;
import weblogic.utils.StackTraceUtils;

public class ConnectionImpl extends RMISkelWrapperImpl implements Unreferenced, PeerGoneListener, InteropWriteReplaceable {
   private java.sql.Connection t2_conn = null;
   private RmiDriverSettings rmiSettings = null;
   private boolean isPeerGoneListener = false;
   public static final String CHUNK_SIZE = "weblogic.jdbc.stream_chunk_size";
   public static final String VERBOSE = "weblogic.jdbc.verbose";
   public static final String CACHE_ROWS = "weblogic.jdbc.rmi.cacheRows";
   private Throwable stackTraceSource = null;
   private boolean isClosed = false;
   private String poolName = null;
   private static final boolean DEBUGROUTING = false;
   private transient boolean createdInThisVM = true;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof java.sql.CallableStatement) {
               ret = CallableStatementImpl.makeCallableStatementImpl((java.sql.CallableStatement)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.PreparedStatement) {
               ret = PreparedStatementImpl.makePreparedStatementImpl((java.sql.PreparedStatement)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Statement) {
               ret = StatementImpl.makeStatementImpl((java.sql.Statement)ret, this.rmiSettings);
            } else if (ret instanceof Savepoint) {
               ret = SavepointImpl.makeSavepointImpl((Savepoint)ret);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
            throw var5;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void init(weblogic.jdbc.wrapper.Connection conn) throws SQLException {
      this.t2_conn = (java.sql.Connection)conn;
      WLDataSourceImpl ds = conn.getRMIDataSource();
      if (ds != null) {
         this.rmiSettings = new RmiDriverSettings(ds.getDriverSettings());
         this.setup(ds.getDriverProperties());
      } else {
         this.rmiSettings = new RmiDriverSettings();
      }

      this.poolName = conn.getPoolName();
      if (this.rmiSettings.isVerbose()) {
         JdbcDebug.JDBCRMIInternal.debug("rmi settings: " + this.rmiSettings);
      }

   }

   public void finalize() {
      if (!this.isClosed && !this.createdInThisVM) {
         try {
            if (!this.t2_conn.getAutoCommit()) {
               this.t2_conn.rollback();
            }
         } catch (Exception var2) {
         }

         JdbcDebug.JDBCRMIInternal.debug("Detected Connection Leak!!!!! : " + StackTraceUtils.throwable2StackTrace(this.stackTraceSource));
      }

   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      return this;
   }

   public void peerGone(PeerGoneEvent e) {
      if (this.rmiSettings.isVerbose()) {
         JdbcDebug.JDBCRMIInternal.debug("Client JVM died: " + e);
      }

      try {
         if (!this.isClosed && !this.createdInThisVM) {
            String stackTraceAtCreate = StackTraceUtils.throwable2StackTrace(this.stackTraceSource);

            try {
               String stringToRemove = ":";
               stackTraceAtCreate = stackTraceAtCreate.substring(stackTraceAtCreate.indexOf(stringToRemove) + stringToRemove.length());
            } catch (Exception var4) {
            }

            JDBCLogger.logConnectionLeakWarning(stackTraceAtCreate);
            if (!this.t2_conn.getAutoCommit()) {
               this.t2_conn.rollback();
            }
         }

         this.close();
      } catch (SQLException var5) {
      }

      this.t2_conn = null;
   }

   public void addPeerGoneListener() {
      if (!this.isPeerGoneListener) {
         this.isPeerGoneListener = true;
         EndPoint endPoint = ServerHelper.getClientEndPointInternal();
         if (endPoint != null && endPoint instanceof RJVM) {
            ((RJVM)endPoint).addPeerGoneListener(this);
         }
      }

   }

   private void setup(Properties props) throws SQLException {
      String prop = (String)props.get("weblogic.jdbc.stream_chunk_size");
      String err;
      if (prop != null) {
         try {
            this.rmiSettings.setChunkSize(Integer.parseInt(prop));
         } catch (Exception var7) {
            err = "The Property weblogic.jdbc.stream_chunk_size must be a number";
            throw new SQLException(err);
         }
      }

      prop = (String)props.get("weblogic.jdbc.rmi.cacheRows");
      if (prop != null) {
         try {
            this.rmiSettings.setRowCacheSize(Integer.parseInt(prop));
         } catch (Exception var6) {
            err = "The Property weblogic.jdbc.rmi.cacheRows must be a number";
            throw new SQLException(err);
         }
      }

      prop = (String)props.get("weblogic.jdbc.verbose");
      if (prop != null) {
         try {
            this.rmiSettings.setVerbose(Boolean.valueOf(prop));
         } catch (Exception var5) {
            err = "The Property weblogic.jdbc.verbose must be a true or false.";
            throw new SQLException(err);
         }
      }

   }

   public void unreferenced() {
      try {
         if (!this.isClosed && !this.createdInThisVM) {
            String stackTraceAtCreate = StackTraceUtils.throwable2StackTrace(this.stackTraceSource);

            try {
               String stringToRemove = ":";
               stackTraceAtCreate = stackTraceAtCreate.substring(stackTraceAtCreate.indexOf(stringToRemove) + stringToRemove.length());
            } catch (Exception var7) {
            }

            JDBCLogger.logConnectionLeakWarning(stackTraceAtCreate);
         }

         this.close();
      } catch (SQLException var8) {
         JDBCLogger.logStackTrace(var8);
      } finally {
         this.t2_conn = null;
      }

   }

   public java.sql.Statement createStatement() throws SQLException {
      java.sql.Statement ret = null;
      String methodName = "createStatement";
      Object[] params = new Object[0];

      try {
         try {
            this.preInvocationHandler(methodName, params);
            ret = StatementImpl.makeStatementImpl(this.t2_conn.createStatement(), this.rmiSettings);
            this.postInvocationHandler(methodName, params, ret);
         } catch (Exception var8) {
            this.invocationExceptionHandler(methodName, params, var8);
         }

         return ret;
      } finally {
         ;
      }
   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.Statement ret = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = StatementImpl.makeStatementImpl(this.t2_conn.createStatement(resultSetType, resultSetConcurrency), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql, resultSetType, resultSetConcurrency), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
      java.sql.CallableStatement ret = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         ret = CallableStatementImpl.makeCallableStatementImpl(this.t2_conn.prepareCall(sql), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.CallableStatement ret = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = CallableStatementImpl.makeCallableStatementImpl(this.t2_conn.prepareCall(sql, resultSetType, resultSetConcurrency), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public void rollback(Savepoint sp) throws SQLException {
      this.t2_conn.rollback((Savepoint)((RMIWrapperImpl)sp).getVendorObj());
   }

   public void releaseSavepoint(Savepoint sp) throws SQLException {
      this.t2_conn.releaseSavepoint((Savepoint)((RMIWrapperImpl)sp).getVendorObj());
      ((SavepointImpl)sp).close();
   }

   public void close() throws SQLException {
      String methodName = "close";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (this.t2_conn != null) {
            this.t2_conn.close();
            this.t2_conn = null;
            if (this.isPeerGoneListener) {
               this.isPeerGoneListener = false;
               EndPoint endPoint = ServerHelper.getClientEndPointInternal();
               if (endPoint != null && endPoint instanceof RJVM) {
                  ((RJVM)endPoint).removePeerGoneListener(this);
               }

               try {
                  UnicastRemoteObject.unexportObject(this, true);
               } catch (Exception var5) {
               }
            }
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public boolean isClosed() throws SQLException {
      boolean ret = false;
      String methodName = "isClosed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (this.t2_conn == null) {
            ret = true;
         } else {
            ret = this.t2_conn.isClosed();
         }

         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.DatabaseMetaData getMetaData() throws SQLException {
      DatabaseMetaDataImpl rmi_dm = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.DatabaseMetaData dm = this.t2_conn.getMetaData();
         rmi_dm = (DatabaseMetaDataImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.DatabaseMetaDataImpl", dm, true);
         rmi_dm.init(dm, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_dm);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.DatabaseMetaData)rmi_dm;
   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      java.sql.Statement ret = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = StatementImpl.makeStatementImpl(this.t2_conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      java.sql.CallableStatement ret = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = CallableStatementImpl.makeCallableStatementImpl(this.t2_conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(autoGeneratedKeys)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql, autoGeneratedKeys), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, columnIndexes};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql, columnIndexes), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, columnNames};

      try {
         this.preInvocationHandler(methodName, params);
         ret = PreparedStatementImpl.makePreparedStatementImpl(this.t2_conn.prepareStatement(sql, columnNames), this.rmiSettings);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      ArrayImpl rmi_array = null;
      String methodName = "createArrayOf";
      Object[] params = new Object[]{typeName, elements};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Array array = this.t2_conn.createArrayOf(typeName, elements);
         rmi_array = (ArrayImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ArrayImpl", array, true);
         rmi_array.init(array, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_array);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return (java.sql.Array)rmi_array;
   }

   public java.sql.Blob createBlob() throws SQLException {
      OracleTBlobImpl rmi_blob = null;
      String methodName = "createBlob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Blob blob = this.t2_conn.createBlob();
         rmi_blob = (OracleTBlobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTBlobImpl", blob, true);
         rmi_blob.init(blob, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_blob);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.Blob)rmi_blob;
   }

   public java.sql.Clob createClob() throws SQLException {
      OracleTClobImpl rmi_clob = null;
      String methodName = "createClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Clob clob = this.t2_conn.createClob();
         rmi_clob = (OracleTClobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTClobImpl", clob, true);
         rmi_clob.init(clob, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_clob);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.Clob)rmi_clob;
   }

   public NClob createNClob() throws SQLException {
      OracleTNClobImpl rmi_nclob = null;
      String methodName = "createNClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         NClob nclob = this.t2_conn.createNClob();
         rmi_nclob = (OracleTNClobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTNClobImpl", nclob, true);
         rmi_nclob.init(nclob, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_nclob);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (NClob)rmi_nclob;
   }

   public java.sql.SQLXML createSQLXML() throws SQLException {
      SQLXMLImpl rmi_sqlxml = null;
      String methodName = "createSQLXML";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.SQLXML sqlxml = this.t2_conn.createSQLXML();
         rmi_sqlxml = (SQLXMLImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.SQLXMLImpl", sqlxml, true);
         rmi_sqlxml.init(sqlxml, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_sqlxml);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return (java.sql.SQLXML)rmi_sqlxml;
   }

   public java.sql.Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      StructImpl rmi_struct = null;
      String methodName = "createStruct";
      Object[] params = new Object[]{typeName, attributes};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Struct struct = this.t2_conn.createStruct(typeName, attributes);
         rmi_struct = (StructImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.StructImpl", struct, true);
         rmi_struct.init(struct, this.rmiSettings);
         this.postInvocationHandler(methodName, params, rmi_struct);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return (java.sql.Struct)rmi_struct;
   }

   public boolean isValid(int timeout) throws SQLException {
      boolean ret = false;
      String methodName = "isValid";
      Object[] params = new Object[]{new Integer(timeout)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.t2_conn.isValid(timeout);
         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }
}
