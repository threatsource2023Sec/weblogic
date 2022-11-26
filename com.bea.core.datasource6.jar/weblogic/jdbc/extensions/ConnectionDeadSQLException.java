package weblogic.jdbc.extensions;

import java.sql.SQLException;

public final class ConnectionDeadSQLException extends SQLException {
   public ConnectionDeadSQLException(String msg) {
      super(msg);
   }
}
