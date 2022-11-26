package kodo.jdbc.sql;

public class DB2AdvancedSQL extends AdvancedSQL {
   public DB2AdvancedSQL() {
      this.setSupportsUnion(false);
   }
}
