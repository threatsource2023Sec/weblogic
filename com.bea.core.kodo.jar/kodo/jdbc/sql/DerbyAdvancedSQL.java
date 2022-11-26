package kodo.jdbc.sql;

import org.apache.openjpa.jdbc.schema.Column;

public class DerbyAdvancedSQL extends AdvancedSQL {
   public DerbyAdvancedSQL() {
      this.setSupportsUnionWithUnalignedOrdering(false);
   }

   public boolean canBatch(Column col) {
      switch (col.getType()) {
         case 91:
         case 93:
         case 2004:
         case 2005:
            return false;
         default:
            return true;
      }
   }
}
