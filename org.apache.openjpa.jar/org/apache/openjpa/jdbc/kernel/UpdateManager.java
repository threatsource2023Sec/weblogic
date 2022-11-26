package org.apache.openjpa.jdbc.kernel;

import java.util.Collection;

public interface UpdateManager {
   boolean orderDirty();

   Collection flush(Collection var1, JDBCStore var2);
}
