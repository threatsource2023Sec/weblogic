package org.apache.openjpa.lib.jdbc;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public class DelegatingConnection implements Connection, Closeable {
   private static final Object SET_HOLDABILITY = new Object();
   private static final Object GET_HOLDABILITY = new Object();
   private static final Object SET_SAVEPOINT_NONAME = new Object();
   private static final Object SET_SAVEPOINT = new Object();
   private static final Object ROLLBACK_SAVEPOINT = new Object();
   private static final Object RELEASE_SAVEPOINT = new Object();
   private static final Object CREATE_STATEMENT = new Object();
   private static final Object PREPARE_STATEMENT = new Object();
   private static final Object PREPARE_CALL = new Object();
   private static final Object PREPARE_WITH_KEYS = new Object();
   private static final Object PREPARE_WITH_INDEX = new Object();
   private static final Object PREPARE_WITH_NAMES = new Object();
   private static final Localizer _loc = Localizer.forPackage(DelegatingConnection.class);
   private static final Map _jdbc3;
   private final Connection _conn;
   private final DelegatingConnection _del;

   public DelegatingConnection(Connection conn) {
      this._conn = conn;
      if (conn instanceof DelegatingConnection) {
         this._del = (DelegatingConnection)this._conn;
      } else {
         this._del = null;
      }

   }

   public Connection getDelegate() {
      return this._conn;
   }

   public Connection getInnermostDelegate() {
      return this._del == null ? this._conn : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingConnection) {
            other = ((DelegatingConnection)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer("conn ")).append(this.hashCode());
      this.appendInfo(buf);
      return buf.toString();
   }

   protected void appendInfo(StringBuffer buf) {
      if (this._del != null) {
         this._del.appendInfo(buf);
      }

   }

   public Statement createStatement() throws SQLException {
      return this.createStatement(true);
   }

   protected Statement createStatement(boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.createStatement(false);
      } else {
         stmnt = this._conn.createStatement();
      }

      if (wrap) {
         stmnt = new DelegatingStatement((Statement)stmnt, this);
      }

      return (Statement)stmnt;
   }

   public PreparedStatement prepareStatement(String str) throws SQLException {
      return this.prepareStatement(str, true);
   }

   protected PreparedStatement prepareStatement(String str, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(str, false);
      } else {
         stmnt = this._conn.prepareStatement(str, 1003, 1007);
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   public CallableStatement prepareCall(String str) throws SQLException {
      return this.prepareCall(str, true);
   }

   protected CallableStatement prepareCall(String str, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareCall(str, false);
      } else {
         stmnt = this._conn.prepareCall(str);
      }

      if (wrap) {
         stmnt = new DelegatingCallableStatement((CallableStatement)stmnt, this);
      }

      return (CallableStatement)stmnt;
   }

   public String nativeSQL(String str) throws SQLException {
      return this._conn.nativeSQL(str);
   }

   public void setAutoCommit(boolean bool) throws SQLException {
      this._conn.setAutoCommit(bool);
   }

   public boolean getAutoCommit() throws SQLException {
      return this._conn.getAutoCommit();
   }

   public void commit() throws SQLException {
      this._conn.commit();
   }

   public void rollback() throws SQLException {
      this._conn.rollback();
   }

   public void close() throws SQLException {
      this._conn.close();
   }

   public boolean isClosed() throws SQLException {
      return this._conn.isClosed();
   }

   public DatabaseMetaData getMetaData() throws SQLException {
      return this.getMetaData(true);
   }

   protected DatabaseMetaData getMetaData(boolean wrap) throws SQLException {
      Object meta;
      if (this._del != null) {
         meta = this._del.getMetaData(false);
      } else {
         meta = this._conn.getMetaData();
      }

      if (wrap) {
         meta = new DelegatingDatabaseMetaData((DatabaseMetaData)meta, this);
      }

      return (DatabaseMetaData)meta;
   }

   public void setReadOnly(boolean bool) throws SQLException {
      this._conn.setReadOnly(bool);
   }

   public boolean isReadOnly() throws SQLException {
      return this._conn.isReadOnly();
   }

   public void setCatalog(String str) throws SQLException {
      this._conn.setCatalog(str);
   }

   public String getCatalog() throws SQLException {
      return this._conn.getCatalog();
   }

   public void setTransactionIsolation(int i) throws SQLException {
      this._conn.setTransactionIsolation(i);
   }

   public int getTransactionIsolation() throws SQLException {
      return this._conn.getTransactionIsolation();
   }

   public SQLWarning getWarnings() throws SQLException {
      return this._conn.getWarnings();
   }

   public void clearWarnings() throws SQLException {
      this._conn.clearWarnings();
   }

   public Statement createStatement(int type, int concur) throws SQLException {
      return this.createStatement(type, concur, true);
   }

   protected Statement createStatement(int type, int concur, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.createStatement(type, concur, false);
      } else {
         stmnt = this._conn.createStatement(type, concur);
      }

      if (wrap) {
         stmnt = new DelegatingStatement((Statement)stmnt, this);
      }

      return (Statement)stmnt;
   }

   public PreparedStatement prepareStatement(String str, int type, int concur) throws SQLException {
      return this.prepareStatement(str, type, concur, true);
   }

   protected PreparedStatement prepareStatement(String str, int type, int concur, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(str, type, concur, false);
      } else {
         stmnt = this._conn.prepareStatement(str, type, concur);
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   public CallableStatement prepareCall(String str, int type, int concur) throws SQLException {
      return this.prepareCall(str, type, concur, true);
   }

   protected CallableStatement prepareCall(String str, int type, int concur, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareCall(str, type, concur, false);
      } else {
         stmnt = this._conn.prepareCall(str, type, concur);
      }

      if (wrap) {
         stmnt = new DelegatingCallableStatement((CallableStatement)stmnt, this);
      }

      return (CallableStatement)stmnt;
   }

   public Map getTypeMap() throws SQLException {
      return this._conn.getTypeMap();
   }

   public void setTypeMap(Map map) throws SQLException {
      this._conn.setTypeMap(map);
   }

   public void setHoldability(int holdability) throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(SET_HOLDABILITY);
      if (m == null) {
         m = createJDBC3Method(SET_HOLDABILITY, "setHoldability", new Class[]{Integer.TYPE});
      }

      this.invokeJDBC3(m, new Object[]{Numbers.valueOf(holdability)});
   }

   public int getHoldability() throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(GET_HOLDABILITY);
      if (m == null) {
         m = createJDBC3Method(GET_HOLDABILITY, "getHoldability", (Class[])null);
      }

      return ((Number)this.invokeJDBC3(m, (Object[])null)).intValue();
   }

   public Savepoint setSavepoint() throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(SET_SAVEPOINT_NONAME);
      if (m == null) {
         m = createJDBC3Method(SET_SAVEPOINT_NONAME, "setSavepoint", (Class[])null);
      }

      return (Savepoint)this.invokeJDBC3(m, (Object[])null);
   }

   public Savepoint setSavepoint(String savepoint) throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(SET_SAVEPOINT);
      if (m == null) {
         m = createJDBC3Method(SET_SAVEPOINT, "setSavepoint", new Class[]{String.class});
      }

      return (Savepoint)this.invokeJDBC3(m, new Object[]{savepoint});
   }

   public void rollback(Savepoint savepoint) throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(ROLLBACK_SAVEPOINT);
      if (m == null) {
         m = createJDBC3Method(ROLLBACK_SAVEPOINT, "rollback", new Class[]{Savepoint.class});
      }

      this.invokeJDBC3(m, new Object[]{savepoint});
   }

   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      assertJDBC3();
      Method m = (Method)_jdbc3.get(RELEASE_SAVEPOINT);
      if (m == null) {
         m = createJDBC3Method(RELEASE_SAVEPOINT, "releaseSavepoint", new Class[]{Savepoint.class});
      }

      this.invokeJDBC3(m, new Object[]{savepoint});
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      assertJDBC3();
      return this.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability, true);
   }

   protected Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability, false);
      } else {
         Method m = (Method)_jdbc3.get(CREATE_STATEMENT);
         if (m == null) {
            m = createJDBC3Method(CREATE_STATEMENT, "createStatement", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
         }

         stmnt = (Statement)this.invokeJDBC3(m, new Object[]{Numbers.valueOf(resultSetType), Numbers.valueOf(resultSetConcurrency), Numbers.valueOf(resultSetHoldability)});
      }

      if (wrap) {
         stmnt = new DelegatingStatement((Statement)stmnt, this);
      }

      return (Statement)stmnt;
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      assertJDBC3();
      return this.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability, true);
   }

   protected PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability, false);
      } else {
         Method m = (Method)_jdbc3.get(PREPARE_STATEMENT);
         if (m == null) {
            m = createJDBC3Method(PREPARE_STATEMENT, "prepareStatement", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
         }

         stmnt = (PreparedStatement)this.invokeJDBC3(m, new Object[]{sql, Numbers.valueOf(resultSetType), Numbers.valueOf(resultSetConcurrency), Numbers.valueOf(resultSetHoldability)});
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      assertJDBC3();
      return this.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability, true);
   }

   protected CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability, false);
      } else {
         Method m = (Method)_jdbc3.get(PREPARE_CALL);
         if (m == null) {
            m = createJDBC3Method(PREPARE_CALL, "prepareCall", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
         }

         stmnt = (CallableStatement)this.invokeJDBC3(m, new Object[]{sql, Numbers.valueOf(resultSetType), Numbers.valueOf(resultSetConcurrency), Numbers.valueOf(resultSetHoldability)});
      }

      if (wrap) {
         stmnt = new DelegatingCallableStatement((CallableStatement)stmnt, this);
      }

      return (CallableStatement)stmnt;
   }

   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      assertJDBC3();
      return this.prepareStatement(sql, autoGeneratedKeys, true);
   }

   protected PreparedStatement prepareStatement(String sql, int autoGeneratedKeys, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(sql, autoGeneratedKeys);
      } else {
         Method m = (Method)_jdbc3.get(PREPARE_WITH_KEYS);
         if (m == null) {
            m = createJDBC3Method(PREPARE_WITH_KEYS, "prepareStatement", new Class[]{String.class, Integer.TYPE});
         }

         stmnt = (PreparedStatement)this.invokeJDBC3(m, new Object[]{sql, Numbers.valueOf(autoGeneratedKeys)});
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      assertJDBC3();
      return this.prepareStatement(sql, columnIndexes, true);
   }

   protected PreparedStatement prepareStatement(String sql, int[] columnIndexes, boolean wrap) throws SQLException {
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(sql, columnIndexes, wrap);
      } else {
         Method m = (Method)_jdbc3.get(PREPARE_WITH_INDEX);
         if (m == null) {
            m = createJDBC3Method(PREPARE_WITH_INDEX, "prepareStatement", new Class[]{String.class, int[].class});
         }

         stmnt = (PreparedStatement)this.invokeJDBC3(m, new Object[]{sql, columnIndexes});
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      assertJDBC3();
      return this.prepareStatement(sql, columnNames, true);
   }

   protected PreparedStatement prepareStatement(String sql, String[] columnNames, boolean wrap) throws SQLException {
      assertJDBC3();
      Object stmnt;
      if (this._del != null) {
         stmnt = this._del.prepareStatement(sql, columnNames, wrap);
      } else {
         Method m = (Method)_jdbc3.get(PREPARE_WITH_NAMES);
         if (m == null) {
            m = createJDBC3Method(PREPARE_WITH_NAMES, "prepareStatement", new Class[]{String.class, String[].class});
         }

         stmnt = (PreparedStatement)this.invokeJDBC3(m, new Object[]{sql, columnNames});
      }

      if (wrap) {
         stmnt = new DelegatingPreparedStatement((PreparedStatement)stmnt, this);
      }

      return (PreparedStatement)stmnt;
   }

   private static void assertJDBC3() {
      if (_jdbc3 == null) {
         throw new UnsupportedOperationException(_loc.get("not-jdbc3").getMessage());
      }
   }

   private Object invokeJDBC3(Method m, Object[] args) throws SQLException {
      try {
         return m.invoke(this._conn, args);
      } catch (Throwable var4) {
         if (var4 instanceof SQLException) {
            throw (SQLException)var4;
         } else {
            throw new NestableRuntimeException(_loc.get("invoke-jdbc3").getMessage(), var4);
         }
      }
   }

   private static Method createJDBC3Method(Object key, String name, Class[] args) {
      try {
         Method m = Connection.class.getMethod(name, args);
         _jdbc3.put(key, m);
         return m;
      } catch (Throwable var4) {
         throw new NestableRuntimeException(_loc.get("error-jdbc3").getMessage(), var4);
      }
   }

   static {
      boolean jdbc3 = false;
      Method m = null;

      try {
         m = Connection.class.getMethod("setSavepoint", String.class);
         jdbc3 = true;
      } catch (Throwable var3) {
      }

      if (jdbc3) {
         _jdbc3 = new HashMap();
         _jdbc3.put(SET_SAVEPOINT, m);
      } else {
         _jdbc3 = null;
      }

   }
}
