package weblogic.jdbc.rowset;

import java.sql.SQLException;

/** @deprecated */
@Deprecated
final class ParseException extends SQLException {
   public ParseException() {
   }

   public ParseException(String message) {
      super(message);
   }
}
