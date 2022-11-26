package kodo.jdbc.sql;

public class InterbaseAdvancedSQL extends AdvancedSQL {
   public InterbaseAdvancedSQL() {
      this.setSupportsUnion(false);
   }
}
