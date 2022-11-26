package kodo.jdbc.sql;

public class InformixAdvancedSQL extends AdvancedSQL {
   public InformixAdvancedSQL() {
      this.setSupportsUnion(false);
   }
}
