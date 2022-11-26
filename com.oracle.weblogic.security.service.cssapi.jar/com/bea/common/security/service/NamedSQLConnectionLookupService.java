package com.bea.common.security.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface NamedSQLConnectionLookupService {
   Connection getConnection(String var1) throws SQLException, NamedSQLConnectionNotFoundException;

   void releaseConnection(Connection var1) throws SQLException;
}
