package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class JDBC3SavepointManager extends AbstractJDBCSavepointManager {
   private static final Localizer _loc = Localizer.forPackage(JDBC3SavepointManager.class);

   protected void rollbackDataStore(AbstractJDBCSavepointManager.ConnectionSavepoint savepoint) {
      try {
         Connection conn = savepoint.getConnection();
         conn.rollback((Savepoint)savepoint.getDataStoreSavepoint());
      } catch (SQLException var3) {
         throw new UserException(_loc.get("error-rollback", (Object)savepoint.getName()), var3);
      }
   }

   protected void setDataStore(AbstractJDBCSavepointManager.ConnectionSavepoint savepoint) {
      try {
         Connection conn = savepoint.getConnection();
         savepoint.setDataStoreSavepoint(conn.setSavepoint(savepoint.getName()));
      } catch (SQLException var3) {
         throw new UserException(_loc.get("error-save", (Object)savepoint.getName()), var3);
      }
   }
}
