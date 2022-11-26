package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class ConnectionUnavailableSQLException extends SQLException {
   public ConnectionUnavailableSQLException(String msg) {
      super(msg);
   }
}
