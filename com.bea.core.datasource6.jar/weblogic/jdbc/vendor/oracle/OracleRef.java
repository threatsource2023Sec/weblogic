package weblogic.jdbc.vendor.oracle;

import java.sql.Ref;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

/** @deprecated */
@Deprecated
public interface OracleRef extends Ref {
   OracleConnection getConnection() throws SQLException;

   oracle.jdbc.OracleConnection getOracleConnection() throws SQLException;

   StructDescriptor getDescriptor() throws SQLException;

   STRUCT getSTRUCT() throws SQLException;

   Object getValue() throws SQLException;

   Object getValue(Map var1) throws SQLException;

   void setValue(Object var1) throws SQLException;
}
