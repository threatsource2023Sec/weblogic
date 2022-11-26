package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.VersionStrategy;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public abstract class AbstractVersionStrategy extends AbstractStrategy implements VersionStrategy {
   protected Version vers = null;

   public void setVersion(Version owner) {
      this.vers = owner;
   }

   public boolean select(Select sel, ClassMapping mapping) {
      return false;
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, Result res) throws SQLException {
   }

   public void afterLoad(OpenJPAStateManager sm, JDBCStore store) {
   }

   public boolean checkVersion(OpenJPAStateManager sm, JDBCStore store, boolean updateVersion) throws SQLException {
      return !updateVersion;
   }

   public int compareVersion(Object v1, Object v2) {
      return 3;
   }

   public Map getBulkUpdateValues() {
      return Collections.EMPTY_MAP;
   }
}
