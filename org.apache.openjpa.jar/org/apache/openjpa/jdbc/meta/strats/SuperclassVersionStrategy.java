package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class SuperclassVersionStrategy extends AbstractVersionStrategy {
   public void afterLoad(OpenJPAStateManager sm, JDBCStore store) {
      this.vers.getClassMapping().getPCSuperclassMapping().getVersion().afterLoad(sm, store);
   }

   public boolean checkVersion(OpenJPAStateManager sm, JDBCStore store, boolean updateVersion) throws SQLException {
      return this.vers.getClassMapping().getPCSuperclassMapping().getVersion().checkVersion(sm, store, updateVersion);
   }

   public int compareVersion(Object v1, Object v2) {
      return this.vers.getClassMapping().getPCSuperclassMapping().getVersion().compareVersion(v1, v2);
   }

   public Map getBulkUpdateValues() {
      return this.vers.getClassMapping().getPCSuperclassMapping().getVersion().getBulkUpdateValues();
   }
}
