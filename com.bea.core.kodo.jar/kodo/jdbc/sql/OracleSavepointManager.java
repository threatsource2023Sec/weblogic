package kodo.jdbc.sql;

import java.sql.Connection;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleSavepoint;
import org.apache.openjpa.jdbc.kernel.AbstractJDBCSavepointManager;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class OracleSavepointManager extends AbstractJDBCSavepointManager {
   private static final Localizer _loc = Localizer.forPackage(OracleSavepointManager.class);

   protected void rollbackDataStore(AbstractJDBCSavepointManager.ConnectionSavepoint savepoint) {
      OracleConnection conn = this.getOracleConnection(savepoint);
      OracleSavepoint save = (OracleSavepoint)savepoint.getDataStoreSavepoint();

      try {
         conn.oracleRollback(save);
      } catch (Throwable var5) {
         throw new UserException(_loc.get("oracle-rollback", savepoint.getName()), var5);
      }
   }

   protected void setDataStore(AbstractJDBCSavepointManager.ConnectionSavepoint savepoint) {
      OracleConnection conn = this.getOracleConnection(savepoint);

      try {
         savepoint.setDataStoreSavepoint(conn.oracleSetSavepoint(savepoint.getName()));
      } catch (Throwable var4) {
         throw new UserException(_loc.get("oracle-savepoint", savepoint.getName()), var4);
      }
   }

   private OracleConnection getOracleConnection(AbstractJDBCSavepointManager.ConnectionSavepoint savepoint) {
      Connection conn = savepoint.getConnection();
      if (conn instanceof DelegatingConnection) {
         conn = ((DelegatingConnection)conn).getInnermostDelegate();
      }

      if (!(conn instanceof OracleConnection)) {
         throw (new UserException(_loc.get("oracle-unknown-svpt", conn.getClass()))).setFatal(true);
      } else {
         return (OracleConnection)conn;
      }
   }
}
