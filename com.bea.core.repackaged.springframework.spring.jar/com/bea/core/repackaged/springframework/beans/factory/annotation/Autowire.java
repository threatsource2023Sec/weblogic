package com.bea.core.repackaged.springframework.beans.factory.annotation;

public enum Autowire {
   NO(0),
   BY_NAME(1),
   BY_TYPE(2);

   private final int value;

   private Autowire(int value) {
      this.value = value;
   }

   public int value() {
      return this.value;
   }

   public boolean isAutowire() {
      return this == BY_NAME || this == BY_TYPE;
   }
}
