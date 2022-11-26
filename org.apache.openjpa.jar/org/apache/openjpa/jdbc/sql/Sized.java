package org.apache.openjpa.jdbc.sql;

public class Sized {
   public final Object value;
   public final int size;

   public Sized(Object value, int size) {
      this.value = value;
      this.size = size;
   }
}
