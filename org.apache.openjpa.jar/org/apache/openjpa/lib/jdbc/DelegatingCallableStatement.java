package org.apache.openjpa.lib.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import org.apache.openjpa.lib.util.Closeable;

public class DelegatingCallableStatement implements CallableStatement, Closeable {
   private final CallableStatement _stmnt;
   private final DelegatingCallableStatement _del;
   private final Connection _conn;

   public DelegatingCallableStatement(CallableStatement stmnt, Connection conn) {
      this._conn = conn;
      this._stmnt = stmnt;
      if (this._stmnt instanceof DelegatingCallableStatement) {
         this._del = (DelegatingCallableStatement)this._stmnt;
      } else {
         this._del = null;
      }

   }

   private ResultSet wrapResult(boolean wrap, ResultSet rs) {
      if (!wrap) {
         return rs;
      } else {
         return rs == null ? null : new DelegatingResultSet(rs, this);
      }
   }

   public CallableStatement getDelegate() {
      return this._stmnt;
   }

   public CallableStatement getInnermostDelegate() {
      return this._del == null ? this._stmnt : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingCallableStatement) {
            other = ((DelegatingCallableStatement)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer("prepstmnt ")).append(this.hashCode());
      this.appendInfo(buf);
      return buf.toString();
   }

   protected void appendInfo(StringBuffer buf) {
      if (this._del != null) {
         this._del.appendInfo(buf);
      }

   }

   public ResultSet executeQuery(String str) throws SQLException {
      return this.executeQuery(true);
   }

   protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
      ResultSet rs;
      if (this._del != null) {
         rs = this._del.executeQuery(sql, false);
      } else {
         rs = this._stmnt.executeQuery(sql);
      }

      return this.wrapResult(wrap, rs);
   }

   public int executeUpdate(String str) throws SQLException {
      return this._stmnt.executeUpdate(str);
   }

   public boolean execute(String str) throws SQLException {
      return this._stmnt.execute(str);
   }

   public void close() throws SQLException {
      this._stmnt.close();
   }

   public int getMaxFieldSize() throws SQLException {
      return this._stmnt.getMaxFieldSize();
   }

   public void setMaxFieldSize(int i) throws SQLException {
      this._stmnt.setMaxFieldSize(i);
   }

   public int getMaxRows() throws SQLException {
      return this._stmnt.getMaxRows();
   }

   public void setMaxRows(int i) throws SQLException {
      this._stmnt.setMaxRows(i);
   }

   public void setEscapeProcessing(boolean bool) throws SQLException {
      this._stmnt.setEscapeProcessing(bool);
   }

   public int getQueryTimeout() throws SQLException {
      return this._stmnt.getQueryTimeout();
   }

   public void setQueryTimeout(int i) throws SQLException {
      this._stmnt.setQueryTimeout(i);
   }

   public void cancel() throws SQLException {
      this._stmnt.cancel();
   }

   public SQLWarning getWarnings() throws SQLException {
      return this._stmnt.getWarnings();
   }

   public void clearWarnings() throws SQLException {
      this._stmnt.clearWarnings();
   }

   public void setCursorName(String str) throws SQLException {
      this._stmnt.setCursorName(str);
   }

   public ResultSet getResultSet() throws SQLException {
      return this.getResultSet(true);
   }

   protected ResultSet getResultSet(boolean wrap) throws SQLException {
      ResultSet rs;
      if (this._del != null) {
         rs = this._del.getResultSet(false);
      } else {
         rs = this._stmnt.getResultSet();
      }

      return this.wrapResult(wrap, rs);
   }

   public int getUpdateCount() throws SQLException {
      return this._stmnt.getUpdateCount();
   }

   public boolean getMoreResults() throws SQLException {
      return this._stmnt.getMoreResults();
   }

   public void setFetchDirection(int i) throws SQLException {
      this._stmnt.setFetchDirection(i);
   }

   public int getFetchDirection() throws SQLException {
      return this._stmnt.getFetchDirection();
   }

   public void setFetchSize(int i) throws SQLException {
      this._stmnt.setFetchSize(i);
   }

   public int getFetchSize() throws SQLException {
      return this._stmnt.getFetchSize();
   }

   public int getResultSetConcurrency() throws SQLException {
      return this._stmnt.getResultSetConcurrency();
   }

   public int getResultSetType() throws SQLException {
      return this._stmnt.getResultSetType();
   }

   public void addBatch(String str) throws SQLException {
      this._stmnt.addBatch(str);
   }

   public void clearBatch() throws SQLException {
      this._stmnt.clearBatch();
   }

   public int[] executeBatch() throws SQLException {
      return this._stmnt.executeBatch();
   }

   public Connection getConnection() throws SQLException {
      return this._conn;
   }

   public ResultSet executeQuery() throws SQLException {
      return this.executeQuery(true);
   }

   protected ResultSet executeQuery(boolean wrap) throws SQLException {
      ResultSet rs;
      if (this._del != null) {
         rs = this._del.executeQuery(false);
      } else {
         rs = this._stmnt.executeQuery();
      }

      return this.wrapResult(wrap, rs);
   }

   public int executeUpdate() throws SQLException {
      return this._stmnt.executeUpdate();
   }

   public void setNull(int i1, int i2) throws SQLException {
      this._stmnt.setNull(i1, i2);
   }

   public void setBoolean(int i, boolean b) throws SQLException {
      this._stmnt.setBoolean(i, b);
   }

   public void setByte(int i, byte b) throws SQLException {
      this._stmnt.setByte(i, b);
   }

   public void setShort(int i, short s) throws SQLException {
      this._stmnt.setShort(i, s);
   }

   public void setInt(int i1, int i2) throws SQLException {
      this._stmnt.setInt(i1, i2);
   }

   public void setLong(int i, long l) throws SQLException {
      this._stmnt.setLong(i, l);
   }

   public void setFloat(int i, float f) throws SQLException {
      this._stmnt.setFloat(i, f);
   }

   public void setDouble(int i, double d) throws SQLException {
      this._stmnt.setDouble(i, d);
   }

   public void setBigDecimal(int i, BigDecimal bd) throws SQLException {
      this._stmnt.setBigDecimal(i, bd);
   }

   public void setString(int i, String s) throws SQLException {
      this._stmnt.setString(i, s);
   }

   public void setBytes(int i, byte[] b) throws SQLException {
      this._stmnt.setBytes(i, b);
   }

   public void setDate(int i, Date d) throws SQLException {
      this._stmnt.setDate(i, d);
   }

   public void setTime(int i, Time t) throws SQLException {
      this._stmnt.setTime(i, t);
   }

   public void setTimestamp(int i, Timestamp t) throws SQLException {
      this._stmnt.setTimestamp(i, t);
   }

   public void setAsciiStream(int i1, InputStream is, int i2) throws SQLException {
      this._stmnt.setAsciiStream(i1, is, i2);
   }

   public void setUnicodeStream(int i1, InputStream is, int i2) throws SQLException {
      this._stmnt.setUnicodeStream(i1, is, i2);
   }

   public void setBinaryStream(int i1, InputStream is, int i2) throws SQLException {
      this._stmnt.setBinaryStream(i1, is, i2);
   }

   public void clearParameters() throws SQLException {
      this._stmnt.clearParameters();
   }

   public void setObject(int i1, Object o, int i2, int i3) throws SQLException {
      this._stmnt.setObject(i1, o, i2, i3);
   }

   public void setObject(int i1, Object o, int i2) throws SQLException {
      this._stmnt.setObject(i1, o, i2);
   }

   public void setObject(int i, Object o) throws SQLException {
      this._stmnt.setObject(i, o);
   }

   public boolean execute() throws SQLException {
      return this._stmnt.execute();
   }

   public void addBatch() throws SQLException {
      this._stmnt.addBatch();
   }

   public void setCharacterStream(int i1, Reader r, int i2) throws SQLException {
      this._stmnt.setCharacterStream(i1, r, i2);
   }

   public void setRef(int i, Ref r) throws SQLException {
      this._stmnt.setRef(i, r);
   }

   public void setBlob(int i, Blob b) throws SQLException {
      this._stmnt.setBlob(i, b);
   }

   public void setClob(int i, Clob c) throws SQLException {
      this._stmnt.setClob(i, c);
   }

   public void setArray(int i, Array a) throws SQLException {
      this._stmnt.setArray(i, a);
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      return this._stmnt.getMetaData();
   }

   public void setDate(int i, Date d, Calendar c) throws SQLException {
      this._stmnt.setDate(i, d, c);
   }

   public void setTime(int i, Time t, Calendar c) throws SQLException {
      this._stmnt.setTime(i, t, c);
   }

   public void setTimestamp(int i, Timestamp t, Calendar c) throws SQLException {
      this._stmnt.setTimestamp(i, t, c);
   }

   public void setNull(int i1, int i2, String s) throws SQLException {
      this._stmnt.setNull(i1, i2, s);
   }

   public boolean getMoreResults(int i) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public ResultSet getGeneratedKeys() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int executeUpdate(String s, int i) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int executeUpdate(String s, int[] ia) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int executeUpdate(String s, String[] sa) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean execute(String s, int i) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean execute(String s, int[] ia) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean execute(String s, String[] sa) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int getResultSetHoldability() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setURL(int i, URL url) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public ParameterMetaData getParameterMetaData() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void registerOutParameter(int i1, int i2) throws SQLException {
      this._stmnt.registerOutParameter(i1, i2);
   }

   public void registerOutParameter(int i1, int i2, int i3) throws SQLException {
      this._stmnt.registerOutParameter(i1, i2, i3);
   }

   public boolean wasNull() throws SQLException {
      return this._stmnt.wasNull();
   }

   public String getString(int i) throws SQLException {
      return this._stmnt.getString(i);
   }

   public boolean getBoolean(int i) throws SQLException {
      return this._stmnt.getBoolean(i);
   }

   public byte getByte(int i) throws SQLException {
      return this._stmnt.getByte(i);
   }

   public short getShort(int i) throws SQLException {
      return this._stmnt.getShort(i);
   }

   public int getInt(int i) throws SQLException {
      return this._stmnt.getInt(i);
   }

   public long getLong(int i) throws SQLException {
      return this._stmnt.getLong(i);
   }

   public float getFloat(int i) throws SQLException {
      return this._stmnt.getFloat(i);
   }

   public double getDouble(int i) throws SQLException {
      return this._stmnt.getDouble(i);
   }

   public BigDecimal getBigDecimal(int a, int b) throws SQLException {
      return this._stmnt.getBigDecimal(a, b);
   }

   public byte[] getBytes(int i) throws SQLException {
      return this._stmnt.getBytes(i);
   }

   public Date getDate(int i) throws SQLException {
      return this._stmnt.getDate(i);
   }

   public Time getTime(int i) throws SQLException {
      return this._stmnt.getTime(i);
   }

   public Timestamp getTimestamp(int i) throws SQLException {
      return this._stmnt.getTimestamp(i);
   }

   public Object getObject(int i) throws SQLException {
      return this._stmnt.getObject(i);
   }

   public BigDecimal getBigDecimal(int i) throws SQLException {
      return this._stmnt.getBigDecimal(i);
   }

   public Object getObject(int i, Map m) throws SQLException {
      return this._stmnt.getObject(i, m);
   }

   public Ref getRef(int i) throws SQLException {
      return this._stmnt.getRef(i);
   }

   public Blob getBlob(int i) throws SQLException {
      return this._stmnt.getBlob(i);
   }

   public Clob getClob(int i) throws SQLException {
      return this._stmnt.getClob(i);
   }

   public Array getArray(int i) throws SQLException {
      return this._stmnt.getArray(i);
   }

   public Date getDate(int i, Calendar c) throws SQLException {
      return this._stmnt.getDate(i, c);
   }

   public Time getTime(int i, Calendar c) throws SQLException {
      return this._stmnt.getTime(i, c);
   }

   public Timestamp getTimestamp(int i, Calendar c) throws SQLException {
      return this._stmnt.getTimestamp(i, c);
   }

   public void registerOutParameter(int i1, int i2, String s) throws SQLException {
      this._stmnt.registerOutParameter(i1, i2, s);
   }

   public void registerOutParameter(String s, int i) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void registerOutParameter(String s, int i1, int i2) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void registerOutParameter(String s1, int i, String s2) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public URL getURL(int i) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setURL(String a, URL b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public URL getURL(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setNull(String a, int b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setBoolean(String a, boolean b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setByte(String a, byte b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setShort(String a, short b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setInt(String a, int b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setLong(String a, long b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setFloat(String a, float b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setDouble(String a, double b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setBigDecimal(String a, BigDecimal b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setString(String a, String b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setBytes(String a, byte[] b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setDate(String a, Date b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setTime(String a, Time b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setTimestamp(String a, Timestamp b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setAsciiStream(String a, InputStream b, int c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setBinaryStream(String a, InputStream b, int c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setObject(String a, Object b, int c, int d) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setObject(String a, Object b, int c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setObject(String a, Object b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setCharacterStream(String a, Reader b, int c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setDate(String a, Date b, Calendar c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setTime(String a, Time b, Calendar c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setTimestamp(String a, Timestamp b, Calendar c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void setNull(String a, int b, String c) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public String getString(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean getBoolean(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public byte getByte(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public short getShort(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public int getInt(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public long getLong(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public float getFloat(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public double getDouble(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public byte[] getBytes(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Date getDate(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Time getTime(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Timestamp getTimestamp(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Object getObject(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public BigDecimal getBigDecimal(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Object getObject(String a, Map b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Ref getRef(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Blob getBlob(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Clob getClob(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Array getArray(String a) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Date getDate(String a, Calendar b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Time getTime(String a, Calendar b) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Timestamp getTimestamp(String a, Calendar b) throws SQLException {
      throw new UnsupportedOperationException();
   }
}
