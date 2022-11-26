package weblogic.jdbc.vendor.oracle;

import java.sql.SQLException;
import java.sql.Struct;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;

/** @deprecated */
@Deprecated
public interface OracleStruct extends Struct {
   StructDescriptor getDescriptor() throws SQLException;

   Datum[] getOracleAttributes() throws SQLException;

   OracleConnection getConnection() throws SQLException;

   oracle.jdbc.OracleConnection getOracleConnection() throws SQLException;

   void setAutoBuffering(boolean var1) throws SQLException;

   boolean getAutoBuffering() throws SQLException;
}
