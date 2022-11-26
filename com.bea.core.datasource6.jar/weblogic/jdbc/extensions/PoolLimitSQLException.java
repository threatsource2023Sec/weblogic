package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class PoolLimitSQLException extends SQLException {
   public PoolLimitSQLException(String msg) {
      super(msg);
   }
}
