package org.apache.openjpa.jdbc.kernel;

import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface JDBCLockManager extends LockManager {
   boolean selectForUpdate(Select var1, int var2);

   void loadedForUpdate(OpenJPAStateManager var1);
}
