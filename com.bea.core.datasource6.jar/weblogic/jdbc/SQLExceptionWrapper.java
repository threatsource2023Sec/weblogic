package weblogic.jdbc;

import java.sql.SQLException;

public class SQLExceptionWrapper extends SQLException {
   public SQLExceptionWrapper(RuntimeException re) {
      super(re.toString());
      this.initCause(re);
   }
}
