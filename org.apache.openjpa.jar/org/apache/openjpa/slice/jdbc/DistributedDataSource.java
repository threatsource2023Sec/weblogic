package org.apache.openjpa.slice.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import org.apache.openjpa.lib.jdbc.DecoratingDataSource;

class DistributedDataSource extends DecoratingDataSource implements Iterable {
   private List real = new ArrayList();
   private DataSource master;

   public DistributedDataSource(List dataSources) {
      super((DataSource)dataSources.get(0));
      this.real = dataSources;
      this.master = (DataSource)dataSources.get(0);
   }

   Connection getConnection(DataSource ds) throws SQLException {
      if (ds instanceof DecoratingDataSource) {
         return this.getConnection(((DecoratingDataSource)ds).getInnermostDelegate());
      } else {
         return ds instanceof XADataSource ? ((XADataSource)ds).getXAConnection().getConnection() : ds.getConnection();
      }
   }

   Connection getConnection(DataSource ds, String user, String pwd) throws SQLException {
      if (ds instanceof DecoratingDataSource) {
         return this.getConnection(((DecoratingDataSource)ds).getInnermostDelegate(), user, pwd);
      } else {
         return ds instanceof XADataSource ? ((XADataSource)ds).getXAConnection(user, pwd).getConnection() : ds.getConnection(user, pwd);
      }
   }

   public Iterator iterator() {
      return this.real.iterator();
   }

   public Connection getConnection() throws SQLException {
      List c = new ArrayList();
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         DataSource ds = (DataSource)i$.next();
         c.add(ds.getConnection());
      }

      return new DistributedConnection(c);
   }

   public Connection getConnection(String username, String password) throws SQLException {
      List c = new ArrayList();
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         DataSource ds = (DataSource)i$.next();
         c.add(ds.getConnection(username, password));
      }

      return new DistributedConnection(c);
   }

   public PrintWriter getLogWriter() throws SQLException {
      return this.master.getLogWriter();
   }

   public int getLoginTimeout() throws SQLException {
      return this.master.getLoginTimeout();
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         DataSource ds = (DataSource)i$.next();
         ds.setLogWriter(out);
      }

   }

   public void setLoginTimeout(int seconds) throws SQLException {
      Iterator i$ = this.real.iterator();

      while(i$.hasNext()) {
         DataSource ds = (DataSource)i$.next();
         ds.setLoginTimeout(seconds);
      }

   }
}
