package weblogic.jdbc;

import java.sql.SQLException;

public class RollbackSQLException extends SQLException {
   public RollbackSQLException() {
   }

   public RollbackSQLException(String msg) {
      super(msg);
   }

   public RollbackSQLException(Throwable th) {
      super(th);
   }

   public RollbackSQLException(String msg, Throwable th) {
      super(msg, th);
   }
}
