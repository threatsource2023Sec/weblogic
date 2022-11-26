package org.apache.openjpa.jdbc.sql;

public class Raw {
   public final String sql;

   public Raw(String sql) {
      this.sql = sql;
   }

   public String toString() {
      return this.sql;
   }
}
