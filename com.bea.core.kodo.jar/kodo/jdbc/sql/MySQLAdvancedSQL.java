package kodo.jdbc.sql;

public class MySQLAdvancedSQL extends AdvancedSQL {
   public MySQLAdvancedSQL() {
      this.setSupportsUnion(false);
   }
}
