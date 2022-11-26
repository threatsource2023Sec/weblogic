package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class PoolUnavailableSQLException extends SQLException {
   public PoolUnavailableSQLException(String msg) {
      super(msg);
   }
}
