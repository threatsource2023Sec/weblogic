package weblogic.jdbc.rmi;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transaction;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.StackTraceUtils;

public class SerialConnection extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = 7761262947309720591L;
   protected Set lobsets = Collections.synchronizedSet(new HashSet());
   private Connection rmi_conn;
   private Set stmts = Collections.synchronizedSet(new HashSet());
   private Throwable stackTraceSource = null;
   private String poolName = null;
   private boolean connectionClosed = false;
   private boolean createdInThisVM = true;
   private boolean inited = false;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, ret);
         return null;
      } else {
         try {
            if (ret instanceof CallableStatement) {
               CallableStatement serial_stmt = SerialCallableStatement.makeSerialCallableStatement((CallableStatement)ret, this);
               this.stmts.add(serial_stmt);
            } else if (ret instanceof PreparedStatement) {
               PreparedStatement serial_stmt = SerialPreparedStatement.makeSerialPreparedStatement((PreparedStatement)ret, this);
               this.stmts.add(serial_stmt);
            } else if (ret instanceof Statement) {
               Statement serial_stmt = SerialStatement.makeSerialStatement((Statement)ret, this);
               this.stmts.add(serial_stmt);
            }
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      if (!methodName.equals("close") && !methodName.equals("isClosed") && !methodName.equals("isValid") && this.connectionClosed) {
         throw new SQLException("Connection has already been closed.");
      }
   }

   public SerialConnection() {
   }

   public SerialConnection(Connection c) {
      this.init(c);
   }

   public void finalize() {
      if (!this.connectionClosed && !this.createdInThisVM && this.inited && this.stackTraceSource != null) {
         String stackTraceAtCreate = StackTraceUtils.throwable2StackTrace(this.stackTraceSource);

         try {
            String stringToRemove = ":";
            stackTraceAtCreate = stackTraceAtCreate.substring(stackTraceAtCreate.indexOf(stringToRemove) + stringToRemove.length());
         } catch (Exception var4) {
         }

         JDBCLogger.logConnectionLeakWarning(stackTraceAtCreate);

         try {
            this.close();
         } catch (Exception var3) {
         }
      }

   }

   public void init(Connection c) {
      this.rmi_conn = c;
      this.stackTraceSource = new Exception("rmi connection initialized:");
      this.inited = true;
   }

   public Object readResolve() throws ObjectStreamException {
      SerialConnection serConn = null;

      try {
         serConn = (SerialConnection)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialConnection", this.rmi_conn, false);
         serConn.init(this.rmi_conn);
         return (Connection)serConn;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.rmi_conn;
      }
   }

   public void addToLobSet(Object o) {
      this.lobsets.add(o);
   }

   public Connection getDelegate() {
      return this.rmi_conn;
   }

   void removeStatement(SerialStatement s) {
      this.stmts.remove(s);
   }

   public Statement createStatement() throws SQLException {
      Statement serial_stmt = null;
      String methodName = "createStatement";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         Statement s = this.rmi_conn.createStatement();
         if (s != null) {
            serial_stmt = SerialStatement.makeSerialStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return serial_stmt;
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      Statement serial_stmt = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         Statement s = this.rmi_conn.createStatement(resultSetType, resultSetConcurrency);
         if (s != null) {
            serial_stmt = SerialStatement.makeSerialStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return serial_stmt;
   }

   public PreparedStatement prepareStatement(String sql) throws SQLException {
      PreparedStatement serial_stmt = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         PreparedStatement s = this.rmi_conn.prepareStatement(sql);
         if (s != null) {
            serial_stmt = SerialPreparedStatement.makeSerialPreparedStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return serial_stmt;
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      PreparedStatement serial_stmt = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         PreparedStatement s = this.rmi_conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
         if (s != null) {
            serial_stmt = SerialPreparedStatement.makeSerialPreparedStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return serial_stmt;
   }

   public CallableStatement prepareCall(String sql) throws SQLException {
      CallableStatement serial_stmt = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         CallableStatement s = this.rmi_conn.prepareCall(sql);
         if (s != null) {
            serial_stmt = SerialCallableStatement.makeSerialCallableStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return serial_stmt;
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      CallableStatement serial_stmt = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency)};

      try {
         this.preInvocationHandler(methodName, params);
         CallableStatement s = this.rmi_conn.prepareCall(sql, resultSetType, resultSetConcurrency);
         if (s != null) {
            serial_stmt = SerialCallableStatement.makeSerialCallableStatement(s, this);
            this.stmts.add(serial_stmt);
         }

         this.postInvocationHandler(methodName, params, serial_stmt);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return serial_stmt;
   }

   public void close() throws SQLException {
      String methodName = "close";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (!this.connectionClosed) {
            this.connectionClosed = true;
            Exception first = null;

            try {
               this.closeAndClearAllLobs();
            } catch (Exception var7) {
               if (first == null) {
                  first = var7;
               }
            }

            try {
               this.closeAndClearAllStatements();
            } catch (Exception var6) {
               if (first == null) {
                  first = var6;
               }
            }

            try {
               this.rmi_conn.close();
            } catch (Exception var5) {
               if (first == null) {
                  first = var5;
               }
            }

            if (first != null) {
               throw first;
            }
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public boolean isClosed() throws SQLException {
      String methodName = "isClosed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         this.postInvocationHandler(methodName, params, new Boolean(this.connectionClosed));
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

      return this.connectionClosed;
   }

   public DatabaseMetaData getMetaData() throws SQLException {
      DatabaseMetaData ret = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         Transaction savetx = null;

         try {
            savetx = suspend();
            DatabaseMetaData dm = this.rmi_conn.getMetaData();
            if (dm != null) {
               SerialDatabaseMetaData rmi_dm = (SerialDatabaseMetaData)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialDatabaseMetaData", dm, false);
               ret = (DatabaseMetaData)rmi_dm;
            }
         } finally {
            if (savetx != null) {
               resume(savetx);
            }

         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var11) {
         this.invocationExceptionHandler(methodName, params, var11);
      }

      return ret;
   }

   public void setReadOnly(boolean readOnly) throws SQLException {
      Transaction savetx = null;
      String methodName = "setReadOnly";
      Object[] params = new Object[]{new Boolean(readOnly)};

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            this.rmi_conn.setReadOnly(readOnly);
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public boolean isReadOnly() throws SQLException {
      Transaction savetx = null;
      boolean ret = false;
      String methodName = "isReadOnly";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            ret = this.rmi_conn.isReadOnly();
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, new Boolean(ret));
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public void setCatalog(String catalog) throws SQLException {
      Transaction savetx = null;
      String methodName = "setCatalog";
      Object[] params = new Object[]{catalog};

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            this.rmi_conn.setCatalog(catalog);
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   public String getCatalog() throws SQLException {
      String ret = null;
      String methodName = "getCatalog";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         Transaction savetx = null;

         try {
            savetx = suspend();
            ret = this.rmi_conn.getCatalog();
         } finally {
            if (savetx != null) {
               resume(savetx);
            }

         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public SQLWarning getWarnings() throws SQLException {
      Transaction savetx = null;
      SQLWarning ret = null;
      String methodName = "getWarnings";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            ret = this.rmi_conn.getWarnings();
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public void clearWarnings() throws SQLException {
      Transaction savetx = null;
      String methodName = "clearWarnings";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            this.rmi_conn.clearWarnings();
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

   }

   public Map getTypeMap() throws SQLException {
      Transaction savetx = null;
      Map ret = null;
      String methodName = "getTypeMap";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            ret = this.rmi_conn.getTypeMap();
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public void setTypeMap(Map map) throws SQLException {
      Transaction savetx = null;
      String methodName = "setTypeMap";
      Object[] params = new Object[]{map};

      try {
         this.preInvocationHandler(methodName, params);

         try {
            savetx = suspend();
            this.rmi_conn.setTypeMap(map);
         } finally {
            resume(savetx);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

   }

   private static Transaction suspend() {
      return TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();
   }

   private static void resume(Transaction savetx) {
      TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(savetx);
   }

   private void closeAndClearAllStatements() {
      synchronized(this.stmts) {
         Iterator it = this.stmts.iterator();

         while(it.hasNext()) {
            SerialStatement stmt = (SerialStatement)it.next();

            try {
               stmt.close(false);
               it.remove();
            } catch (SQLException var6) {
            }
         }

      }
   }

   public void closeAndClearAllLobs() {
      if (this.lobsets != null && !this.lobsets.isEmpty()) {
         synchronized(this.lobsets) {
            for(Iterator it1 = this.lobsets.iterator(); it1.hasNext(); it1.remove()) {
               Object obj = it1.next();
               if (obj instanceof SerialArray) {
                  ((SerialArray)obj).internalClose();
               } else if (obj instanceof SerialOracleBlob) {
                  ((SerialOracleBlob)obj).internalClose();
               } else if (obj instanceof SerialOracleClob) {
                  ((SerialOracleClob)obj).internalClose();
               } else if (obj instanceof SerialSQLXML) {
                  ((SerialSQLXML)obj).internalClose();
               }
            }

         }
      }
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      Statement ret = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
         if (ret != null) {
            ret = SerialStatement.makeSerialStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      CallableStatement ret = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
         if (ret != null) {
            ret = SerialCallableStatement.makeSerialCallableStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(autoGeneratedKeys)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.prepareStatement(sql, autoGeneratedKeys);
         if (ret != null) {
            ret = SerialPreparedStatement.makeSerialPreparedStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, columnIndexes};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.prepareStatement(sql, columnIndexes);
         if (ret != null) {
            ret = SerialPreparedStatement.makeSerialPreparedStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, new Integer(resultSetType), new Integer(resultSetConcurrency), new Integer(resultSetHoldability)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
         if (ret != null) {
            ret = SerialPreparedStatement.makeSerialPreparedStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql, columnNames};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.prepareStatement(sql, columnNames);
         if (ret != null) {
            ret = SerialPreparedStatement.makeSerialPreparedStatement(ret, this);
            this.stmts.add(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      Array ret = null;
      String methodName = "createArrayOf";
      Object[] params = new Object[]{typeName, elements};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createArrayOf(typeName, elements);
         if (ret != null) {
            ret = SerialArray.makeSerialArrayFromStub(ret);
            this.addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Blob createBlob() throws SQLException {
      Blob ret = null;
      String methodName = "createBlob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createBlob();
         if (ret != null) {
            ret = SerialOracleBlob.makeSerialOracleBlob(ret);
            this.addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Clob createClob() throws SQLException {
      Clob ret = null;
      String methodName = "createClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createClob();
         if (ret != null) {
            ret = SerialOracleClob.makeSerialOracleClob(ret);
            this.addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public NClob createNClob() throws SQLException {
      NClob ret = null;
      String methodName = "createNClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createNClob();
         if (ret != null) {
            ret = SerialOracleNClob.makeSerialOracleNClob(ret);
            this.addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public SQLXML createSQLXML() throws SQLException {
      SQLXML ret = null;
      String methodName = "createSQLXML";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createSQLXML();
         if (ret != null) {
            ret = SerialSQLXML.makeSerialSQLXMLFromStub(ret);
            this.addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      Struct ret = null;
      String methodName = "createStruct";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_conn.createStruct(typeName, attributes);
         if (ret != null) {
            ret = SerialStruct.makeSerialStructFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public boolean isValid() throws SQLException {
      return this.isValid(15);
   }

   public boolean isValid(int timeout) throws SQLException {
      boolean ret = false;
      String methodName = "isValid";
      Object[] params = new Object[]{new Integer(timeout)};
      boolean connCheckOk = false;

      try {
         try {
            this.preInvocationHandler(methodName, params);
            connCheckOk = true;
         } catch (SQLException var8) {
         }

         if (timeout < 0) {
            throw new SQLException("timeout must not be less than 0");
         }

         if (connCheckOk && !this.connectionClosed) {
            try {
               ret = this.rmi_conn.isValid(timeout);
            } catch (Throwable var7) {
            }
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }
}
