package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.support.DBUtils;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.persistence.composite.CompositePersistence;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.io.Closeable;
import java.sql.Connection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestScopeConnectionManager implements ConnectionManager, Closeable {
   final Persistence persistence;
   final ConcurrentMap openConnections = new ConcurrentHashMap(1, 1.0F, 1);

   public RequestScopeConnectionManager(Persistence persistence) {
      this.persistence = persistence;
   }

   public Connection getConnection(Index index, QueryOptions queryOptions) {
      index = index.getEffectiveIndex();
      SQLitePersistence persistence = this.getPersistenceForIndex(index);
      Connection connection = (Connection)this.openConnections.get(persistence);
      if (connection == null) {
         Connection newConnection = persistence.getConnection(index, queryOptions);
         Connection existingConnection = (Connection)this.openConnections.putIfAbsent(persistence, newConnection);
         if (existingConnection == null) {
            connection = newConnection;
            DBUtils.setAutoCommit(newConnection, false);
         } else {
            DBUtils.closeQuietly(newConnection);
            connection = existingConnection;
         }
      }

      return connection;
   }

   public void close() {
      Iterator iterator = this.openConnections.values().iterator();

      while(iterator.hasNext()) {
         Connection connection = (Connection)iterator.next();
         DBUtils.commit(connection);
         DBUtils.closeQuietly(connection);
         iterator.remove();
      }

   }

   SQLitePersistence getPersistenceForIndex(Index index) {
      if (this.persistence instanceof SQLitePersistence) {
         if (this.persistence.supportsIndex(index)) {
            return (SQLitePersistence)this.persistence;
         }
      } else if (this.persistence instanceof CompositePersistence) {
         CompositePersistence compositePersistence = (CompositePersistence)this.persistence;
         Persistence indexPersistence = compositePersistence.getPersistenceForIndex(index);
         if (indexPersistence instanceof SQLitePersistence) {
            return (SQLitePersistence)indexPersistence;
         }
      }

      throw new IllegalStateException("No configured Persistence implementation can support the given index: " + index);
   }

   public boolean isApplyUpdateForIndexEnabled(Index index) {
      return true;
   }
}
