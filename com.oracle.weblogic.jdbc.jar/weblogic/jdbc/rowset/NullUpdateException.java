package weblogic.jdbc.rowset;

import java.sql.SQLException;

/** @deprecated */
@Deprecated
public class NullUpdateException extends SQLException {
   private static String reason = "RowSet has pending changes but no change has been written back to the data source because writeTable was set and there is no column associated with it.";

   public NullUpdateException() {
      super(reason);
   }
}
