package com.solarmetric.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.jdbc.DelegatingPreparedStatement;
import org.apache.openjpa.lib.jdbc.DelegatingStatement;
import org.apache.openjpa.lib.util.Localizer;

public class PoolConnection extends DelegatingConnection {
   private static final Localizer _loc = Localizer.forPackage(PoolConnection.class);
   private final ConnectionRequestInfo _cri;
   private ConnectionPool _source;
   private long _lastValidated = 0L;
   private boolean _transActive = false;
   private boolean _transDirty = false;
   private SQLException _freed = null;
   private int _errors = 0;
   private boolean _autoCommit = false;

   public PoolConnection(Connection conn, ConnectionRequestInfo cri, ConnectionPool source) throws SQLException {
      super(conn);
      this._cri = cri;
      this._source = source;
      this._lastValidated = System.currentTimeMillis();
      this._autoCommit = conn.getAutoCommit();
   }

   public void free() {
      this._source = null;
      this._freed = new SQLException(_loc.get("rsrc-closed", String.valueOf(this.hashCode())).getMessage());
   }

   public ConnectionPool getConnectionPool() {
      return this._source;
   }

   public ConnectionRequestInfo getRequestInfo() {
      return this._cri;
   }

   public long getLastValidatedTime() {
      return this._lastValidated;
   }

   public void setLastValidatedTime(long time) {
      this._lastValidated = time;
   }

   public int getExceptionCount() {
      return this._errors;
   }

   public void incrementExceptionCount(int num) {
      this._errors += num;
   }

   public boolean isTransactionActive() {
      return this._transActive;
   }

   public boolean isTransactionDirty() {
      return this._transDirty;
   }

   protected PreparedStatement prepareStatement(String sql, boolean wrap) throws SQLException {
      this.assertNotFreed();
      PreparedStatement stmnt = super.prepareStatement(sql, false);
      return new PoolPreparedStatement(stmnt, sql);
   }

   protected PreparedStatement prepareStatement(String sql, int rsType, int rsConcur, boolean wrap) throws SQLException {
      this.assertNotFreed();
      PreparedStatement stmnt = super.prepareStatement(sql, rsType, rsConcur, false);
      return new PoolPreparedStatement(stmnt, sql);
   }

   protected Statement createStatement(boolean wrap) throws SQLException {
      this.assertNotFreed();
      Statement stmnt = super.createStatement(false);
      return new PoolStatement(stmnt);
   }

   protected Statement createStatement(int type, int concur, boolean wrap) throws SQLException {
      this.assertNotFreed();
      Statement stmnt = super.createStatement(type, concur, false);
      return new PoolStatement(stmnt);
   }

   public boolean getAutoCommit() throws SQLException {
      this.assertNotFreed();
      return this._autoCommit;
   }

   public void setAutoCommit(boolean commit) throws SQLException {
      this.assertNotFreed();
      super.setAutoCommit(commit);
      this._autoCommit = commit;
   }

   public void commit() throws SQLException {
      this.assertNotFreed();
      this._transActive = false;
      this._transDirty = false;
      super.commit();
   }

   public void rollback() throws SQLException {
      this.assertNotFreed();
      this._transActive = false;
      this._transDirty = false;
      super.rollback();
   }

   public void close() throws SQLException {
      this.assertNotFreed();
      this._source.returnConnection(this);
   }

   protected void appendInfo(StringBuffer buf) {
      if (this._errors > 0) {
         buf.append(" (").append(this._errors).append(" errors)");
      }

   }

   private void transactionOp(boolean dirty) {
      if (!this._autoCommit) {
         this._transActive = true;
         this._transDirty |= dirty;
      }

   }

   private void assertNotFreed() throws SQLException {
      if (this._freed != null) {
         throw this._freed;
      }
   }

   private class PoolPreparedStatement extends DelegatingPreparedStatement {
      public PoolPreparedStatement(PreparedStatement stmnt, String sql) throws SQLException {
         super(stmnt, PoolConnection.this);
      }

      protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(false);

         try {
            return super.executeQuery(sql, wrap);
         } catch (SQLException var4) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var4;
         }
      }

      public int executeUpdate(String sql) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.executeUpdate(sql);
         } catch (SQLException var3) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var3;
         }
      }

      public boolean execute(String sql) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.execute(sql);
         } catch (SQLException var3) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var3;
         }
      }

      protected ResultSet executeQuery(boolean wrap) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(false);

         try {
            return super.executeQuery(wrap);
         } catch (SQLException var3) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var3;
         }
      }

      public int executeUpdate() throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.executeUpdate();
         } catch (SQLException var2) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var2;
         }
      }

      public int[] executeBatch() throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.executeBatch();
         } catch (SQLException var2) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var2;
         }
      }

      public boolean execute() throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.execute();
         } catch (SQLException var2) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var2;
         }
      }

      public void setNull(int i1, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setNull(i1, i2);
      }

      public void setBoolean(int i, boolean b) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setBoolean(i, b);
      }

      public void setByte(int i, byte b) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setByte(i, b);
      }

      public void setShort(int i, short s) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setShort(i, s);
      }

      public void setInt(int i1, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setInt(i1, i2);
      }

      public void setLong(int i, long l) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setLong(i, l);
      }

      public void setFloat(int i, float f) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setFloat(i, f);
      }

      public void setDouble(int i, double d) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setDouble(i, d);
      }

      public void setBigDecimal(int i, BigDecimal bd) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setBigDecimal(i, bd);
      }

      public void setString(int i, String s) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setString(i, s);
      }

      public void setBytes(int i, byte[] b) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setBytes(i, b);
      }

      public void setDate(int i, Date d) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setDate(i, d);
      }

      public void setTime(int i, Time t) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setTime(i, t);
      }

      public void setTimestamp(int i, Timestamp t) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setTimestamp(i, t);
      }

      public void setAsciiStream(int i1, InputStream is, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setAsciiStream(i1, is, i2);
      }

      public void setUnicodeStream(int i1, InputStream is, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setUnicodeStream(i2, is, i2);
      }

      public void setBinaryStream(int i1, InputStream is, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setBinaryStream(i1, is, i2);
      }

      public void clearParameters() throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.clearParameters();
      }

      public void setObject(int i1, Object o, int i2, int i3) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setObject(i1, o, i2, i3);
      }

      public void setObject(int i1, Object o, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setObject(i1, o, i2);
      }

      public void setObject(int i, Object o) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setObject(i, o);
      }

      public void addBatch() throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.addBatch();
      }

      public void setCharacterStream(int i1, Reader r, int i2) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setCharacterStream(i1, r, i2);
      }

      public void setRef(int i, Ref r) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setRef(i, r);
      }

      public void setBlob(int i, Blob b) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setBlob(i, b);
      }

      public void setClob(int i, Clob c) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setClob(i, c);
      }

      public void setArray(int i, Array a) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setArray(i, a);
      }

      public ResultSetMetaData getMetaData() throws SQLException {
         PoolConnection.this.assertNotFreed();
         return super.getMetaData();
      }

      public void setDate(int i, Date d, Calendar c) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setDate(i, d, c);
      }

      public void setTime(int i, Time t, Calendar c) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setTime(i, t, c);
      }

      public void setTimestamp(int i, Timestamp t, Calendar c) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setTimestamp(i, t, c);
      }

      public void setNull(int i1, int i2, String s) throws SQLException {
         PoolConnection.this.assertNotFreed();
         super.setNull(i1, i2, s);
      }
   }

   private class PoolStatement extends DelegatingStatement {
      public PoolStatement(Statement stmnt) throws SQLException {
         super(stmnt, PoolConnection.this);
      }

      protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(false);

         try {
            return super.executeQuery(sql, wrap);
         } catch (SQLException var4) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var4;
         }
      }

      public int executeUpdate(String sql) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.executeUpdate(sql);
         } catch (SQLException var3) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var3;
         }
      }

      public boolean execute(String sql) throws SQLException {
         PoolConnection.this.assertNotFreed();
         PoolConnection.this.transactionOp(true);

         try {
            return super.execute(sql);
         } catch (SQLException var3) {
            PoolConnection.this.incrementExceptionCount(1);
            throw var3;
         }
      }
   }
}
