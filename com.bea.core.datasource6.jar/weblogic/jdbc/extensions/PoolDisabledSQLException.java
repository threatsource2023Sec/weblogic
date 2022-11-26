package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class PoolDisabledSQLException extends SQLException {
   public PoolDisabledSQLException(String msg) {
      super(msg);
   }
}
