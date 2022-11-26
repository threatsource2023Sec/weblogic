package com.bea.core.repackaged.springframework.transaction.annotation;

public enum Propagation {
   REQUIRED(0),
   SUPPORTS(1),
   MANDATORY(2),
   REQUIRES_NEW(3),
   NOT_SUPPORTED(4),
   NEVER(5),
   NESTED(6);

   private final int value;

   private Propagation(int value) {
      this.value = value;
   }

   public int value() {
      return this.value;
   }
}
