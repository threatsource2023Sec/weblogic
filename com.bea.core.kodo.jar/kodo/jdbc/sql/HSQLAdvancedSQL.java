package kodo.jdbc.sql;

public class HSQLAdvancedSQL extends AdvancedSQL {
   public HSQLAdvancedSQL() {
      this.setBatchLimit(0);
      this.setSupportsUnionWithUnalignedOrdering(false);
   }
}
