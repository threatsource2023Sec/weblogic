package kodo.jdbc.sql;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.OracleDictionary;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class OracleAdvancedSQL extends AdvancedSQL {
   private static final Localizer _loc = Localizer.forPackage(OracleAdvancedSQL.class);
   private boolean _setDefaults = false;

   public OracleAdvancedSQL() {
      this.setSupportsUnionWithUnalignedOrdering(false);
   }

   public int getBatchLimit() {
      this.setDriverVendorDefaults();
      return super.getBatchLimit();
   }

   public boolean getSupportsUpdateCountsForBatch() {
      this.setDriverVendorDefaults();
      return super.getSupportsUpdateCountsForBatch();
   }

   public boolean getSupportsTotalCountsForBatch() {
      this.setDriverVendorDefaults();
      return super.getSupportsTotalCountsForBatch();
   }

   private void setDriverVendorDefaults() {
      if (!this._setDefaults) {
         this._setDefaults = true;
         ((OracleDictionary)this.dict).ensureDriverVendor();
         String driver = this.dict.driverVendor.toLowerCase();
         if (driver.startsWith("oracle")) {
            this.setSupportsUpdateCountsForBatch(false);
            this.setSupportsTotalCountsForBatch(true);
            if (driver.equals("oracle92")) {
               Log log = this.conf.getLog("openjpa.jdbc.JDBC");
               if (this.getBatchLimit() == -1) {
                  if (log.isWarnEnabled()) {
                     log.warn(_loc.get("oracle-batch-bug"));
                  }

                  this.setBatchLimit(0);
               } else if (this.getBatchLimit() > 0 && log.isInfoEnabled()) {
                  log.info(_loc.get("oracle-batch-override"));
               }
            }
         }

      }
   }

   public boolean canBatch(Column col) {
      switch (col.getType()) {
         case 91:
            return false;
         case 2004:
         case 2005:
            return false;
         default:
            return true;
      }
   }
}
