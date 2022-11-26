package org.apache.openjpa.jdbc.schema;

public class Unique extends LocalConstraint {
   public Unique() {
   }

   public Unique(String name, Table table) {
      super(name, table);
   }

   public boolean isLogical() {
      return false;
   }

   public boolean equalsUnique(Unique unq) {
      return this.equalsLocalConstraint(unq);
   }
}
