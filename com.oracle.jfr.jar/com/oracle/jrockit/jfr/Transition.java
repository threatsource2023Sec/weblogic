package com.oracle.jrockit.jfr;

public enum Transition {
   None(0),
   From(1),
   To(2);

   private final int value;

   private Transition(int value) {
      this.value = value;
   }

   public int value() {
      return this.value;
   }
}
