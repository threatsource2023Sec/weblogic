package weblogic.jdbc.rowset;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class RowSetLob {
   abstract Object update(Connection var1, ResultSet var2, int var3, UpdateHelper var4) throws SQLException;

   abstract static class UpdateHelper {
      abstract Object update(Connection var1, Clob var2, char[] var3) throws SQLException;

      abstract Object update(Connection var1, NClob var2, char[] var3) throws SQLException;

      abstract Object update(Connection var1, Blob var2, byte[] var3) throws SQLException;
   }
}
