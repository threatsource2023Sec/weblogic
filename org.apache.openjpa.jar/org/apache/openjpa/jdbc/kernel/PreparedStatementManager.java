package org.apache.openjpa.jdbc.kernel;

import java.util.Collection;
import org.apache.openjpa.jdbc.sql.RowImpl;

public interface PreparedStatementManager {
   Collection getExceptions();

   void flush(RowImpl var1);

   void flush();
}
