package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;

public class NoneClassStrategy extends AbstractClassStrategy {
   public static final String ALIAS = "none";
   private static final NoneClassStrategy _instance = new NoneClassStrategy();
   private static final Localizer _loc = Localizer.forPackage(NoneClassStrategy.class);

   public static NoneClassStrategy getInstance() {
      return _instance;
   }

   private NoneClassStrategy() {
   }

   public String getAlias() {
      return "none";
   }

   public void setClassMapping(ClassMapping owner) {
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      throwFlushException(sm);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      throwFlushException(sm);
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      throwFlushException(sm);
   }

   private static void throwFlushException(OpenJPAStateManager sm) {
      throw (new InvalidStateException(_loc.get("flush-virtual", sm.getMetaData(), sm.getObjectId()))).setFailedObject(sm.getManagedInstance()).setFatal(true);
   }
}
