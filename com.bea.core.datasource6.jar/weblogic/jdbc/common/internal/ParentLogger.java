package weblogic.jdbc.common.internal;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ParentLogger {
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }
}
