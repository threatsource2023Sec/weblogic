package org.apache.openjpa.lib.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.openjpa.lib.util.Closeable;

public class DelegatingDataSource implements DataSource, Closeable {
   private final DataSource _ds;
   private final DelegatingDataSource _del;

   public DelegatingDataSource(DataSource ds) {
      this._ds = ds;
      if (this._ds instanceof DelegatingDataSource) {
         this._del = (DelegatingDataSource)this._ds;
      } else {
         this._del = null;
      }

   }

   public DataSource getDelegate() {
      return this._ds;
   }

   public DataSource getInnermostDelegate() {
      return this._del == null ? this._ds : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingDataSource) {
            other = ((DelegatingDataSource)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   public String toString() {
      StringBuffer buf = (new StringBuffer("datasource ")).append(this.hashCode());
      this.appendInfo(buf);
      return buf.toString();
   }

   protected void appendInfo(StringBuffer buf) {
      if (this._del != null) {
         this._del.appendInfo(buf);
      }

   }

   public PrintWriter getLogWriter() throws SQLException {
      return this._ds.getLogWriter();
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
      this._ds.setLogWriter(out);
   }

   public int getLoginTimeout() throws SQLException {
      return this._ds.getLoginTimeout();
   }

   public void setLoginTimeout(int timeout) throws SQLException {
      this._ds.setLoginTimeout(timeout);
   }

   public Connection getConnection() throws SQLException {
      return this._ds.getConnection();
   }

   public Connection getConnection(String user, String pass) throws SQLException {
      return user == null && pass == null ? this._ds.getConnection() : this._ds.getConnection(user, pass);
   }

   public void close() throws Exception {
      if (this._ds instanceof Closeable) {
         ((Closeable)this._ds).close();
      }

   }
}
