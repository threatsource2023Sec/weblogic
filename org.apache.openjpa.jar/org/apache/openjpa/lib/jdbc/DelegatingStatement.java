package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import org.apache.openjpa.lib.util.Closeable;

public class DelegatingStatement implements Statement, Closeable {
   private final Statement _stmnt;
   private final DelegatingStatement _del;
   private final Connection _conn;

   public DelegatingStatement(Statement stmnt, Connection conn) {
      this._conn = conn;
      this._stmnt = stmnt;
      if (stmnt instanceof DelegatingStatement) {
         this._del = (DelegatingStatement)stmnt;
      } else {
         this._del = null;
      }

   }

   protected ResultSet wrapResult(ResultSet rs, boolean wrap) {
      return (ResultSet)(wrap && rs != null ? new DelegatingResultSet(rs, this) : rs);
   }

   public Statement getDelegate() {
      return this._stmnt;
   }

   public Statement getInnermostDelegate() {
      return this._del == null ? this._stmnt : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingStatement) {
            other = ((DelegatingStatement)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer("stmnt ")).append(this.hashCode());
      this.appendInfo(buf);
      return buf.toString();
   }

   protected void appendInfo(StringBuffer buf) {
      if (this._del != null) {
         this._del.appendInfo(buf);
      }

   }

   public ResultSet executeQuery(String str) throws SQLException {
      return this.executeQuery(str, true);
   }

   protected ResultSet executeQuery(String sql, boolean wrap) throws SQLException {
      ResultSet rs;
      if (this._del != null) {
         rs = this._del.executeQuery(sql, false);
      } else {
         rs = this._stmnt.executeQuery(sql);
      }

      return this.wrapResult(rs, wrap);
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

      return this.wrapResult(rs, wrap);
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
}
