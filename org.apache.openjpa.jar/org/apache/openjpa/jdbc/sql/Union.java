package org.apache.openjpa.jdbc.sql;

public interface Union extends SelectExecutor {
   Select[] getSelects();

   String getOrdering();

   boolean isUnion();

   void abortUnion();

   void select(Selector var1);

   public interface Selector {
      void select(Select var1, int var2);
   }
}
