package kodo.jdbc.sql;

public class SybaseAdvancedSQL extends AdvancedSQL {
   public SybaseAdvancedSQL() {
      this.setBatchParameterLimit(1000);
   }
}
