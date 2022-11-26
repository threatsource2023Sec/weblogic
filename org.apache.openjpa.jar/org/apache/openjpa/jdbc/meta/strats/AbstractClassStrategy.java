package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassStrategy;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.rop.ResultObjectProvider;

public abstract class AbstractClassStrategy extends AbstractStrategy implements ClassStrategy {
   protected ClassMapping cls = null;

   public void setClassMapping(ClassMapping owner) {
      this.cls = owner;
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return false;
   }

   public Joins joinSuperclass(Joins joins, boolean toThis) {
      return joins;
   }

   public boolean supportsEagerSelect(Select sel, OpenJPAStateManager sm, JDBCStore store, ClassMapping base, JDBCFetchConfiguration fetch) {
      return true;
   }

   public ResultObjectProvider customLoad(JDBCStore store, boolean subclasses, JDBCFetchConfiguration fetch, long startIdx, long endIdx) throws SQLException {
      return null;
   }

   public boolean customLoad(OpenJPAStateManager sm, JDBCStore store, PCState state, JDBCFetchConfiguration fetch) throws SQLException, ClassNotFoundException {
      return false;
   }

   public boolean customLoad(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result result) throws SQLException {
      return false;
   }
}
