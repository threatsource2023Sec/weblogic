package weblogic.jdbc.rowset;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class RowSetNClob extends RowSetClob implements NClob {
   private static final long serialVersionUID = -1366136586508870902L;

   public RowSetNClob() {
   }

   public RowSetNClob(String s) {
      super(s);
   }

   public RowSetNClob(char[] data) {
      super(data);
   }

   public RowSetNClob(NClob nclob) throws SQLException {
      super((Clob)nclob);
   }

   protected Object update(Connection con, ResultSet rs, int i, RowSetLob.UpdateHelper helper) throws SQLException {
      return helper.update(con, rs.getNClob(i), this.data);
   }
}
