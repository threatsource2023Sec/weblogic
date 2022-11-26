package kodo.jdbc.sql;

import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class AbstractSQLServerAdvancedSQL extends AdvancedSQL {
   private static final Localizer _loc = Localizer.forPackage(AbstractSQLServerAdvancedSQL.class);
   private boolean _warnedDriver = false;

   public AbstractSQLServerAdvancedSQL() {
      this.setSupportsUnionWithUnalignedOrdering(false);
   }

   public int getBatchLimit() {
      int limit = super.getBatchLimit();
      if (!this._warnedDriver) {
         this._warnedDriver = true;
         if (limit != 0 && "netdirect".equalsIgnoreCase(this.dict.driverVendor)) {
            Log log = this.conf.getLog("openjpa.jdbc.JDBC");
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("sqlserver-netdirect-batch"));
            }
         }
      }

      return limit;
   }
}
