package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sql.DataSource;

public class DecoratingDataSource extends DelegatingDataSource {
   private List _decorators = new CopyOnWriteArrayList();

   public DecoratingDataSource(DataSource ds) {
      super(ds);
   }

   public Collection getDecorators() {
      return Collections.unmodifiableCollection(this._decorators);
   }

   public void addDecorator(ConnectionDecorator decorator) {
      if (decorator != null) {
         this._decorators.add(decorator);
      }

   }

   public void addDecorators(Collection decorators) {
      if (decorators != null) {
         this._decorators.addAll(decorators);
      }

   }

   public boolean removeDecorator(ConnectionDecorator decorator) {
      return this._decorators.remove(decorator);
   }

   public void clearDecorators() {
      this._decorators.clear();
   }

   public Connection getConnection() throws SQLException {
      Connection conn = super.getConnection();
      return this.decorate(conn);
   }

   public Connection getConnection(String user, String pass) throws SQLException {
      Connection conn = super.getConnection(user, pass);
      return this.decorate(conn);
   }

   private Connection decorate(Connection conn) throws SQLException {
      if (!this._decorators.isEmpty()) {
         for(Iterator itr = this._decorators.iterator(); itr.hasNext(); conn = ((ConnectionDecorator)itr.next()).decorate(conn)) {
         }
      }

      return conn;
   }
}
