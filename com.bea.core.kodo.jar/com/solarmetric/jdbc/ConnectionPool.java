package com.solarmetric.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.openjpa.lib.util.Closeable;

public interface ConnectionPool extends Closeable {
   void setDataSource(PoolingDataSource var1);

   PoolingDataSource getDataSource();

   Connection getConnection(ConnectionRequestInfo var1) throws SQLException;

   void returnConnection(Connection var1);

   void close();
}
