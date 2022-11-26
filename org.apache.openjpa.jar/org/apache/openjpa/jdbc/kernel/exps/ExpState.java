package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.Joins;

public class ExpState {
   public static final ExpState NULL = new ExpState();
   public Joins joins;

   public ExpState() {
   }

   public ExpState(Joins joins) {
      this.joins = joins;
   }
}
