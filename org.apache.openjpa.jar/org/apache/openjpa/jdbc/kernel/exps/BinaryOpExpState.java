package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.Joins;

class BinaryOpExpState extends ExpState {
   public ExpState state1;
   public ExpState state2;

   public BinaryOpExpState() {
   }

   public BinaryOpExpState(Joins joins, ExpState state1, ExpState state2) {
      super(joins);
      this.state1 = state1;
      this.state2 = state2;
   }
}
