package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class PoolPermissionsSQLException extends SQLException {
   public PoolPermissionsSQLException(String msg) {
      super(msg);
   }
}
