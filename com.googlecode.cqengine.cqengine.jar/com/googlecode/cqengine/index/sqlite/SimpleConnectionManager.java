package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.sql.Connection;

public class SimpleConnectionManager implements ConnectionManager {
   final Connection connection;

   public SimpleConnectionManager(Connection connection) {
      this.connection = connection;
   }

   public Connection getConnection(Index index, QueryOptions queryOptions) {
      return this.connection;
   }

   public boolean isApplyUpdateForIndexEnabled(Index index) {
      return true;
   }
}
