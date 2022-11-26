package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDecorator {
   Connection decorate(Connection var1) throws SQLException;
}
