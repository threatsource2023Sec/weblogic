package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.util.Assert;

public class NamedThreadLocal extends ThreadLocal {
   private final String name;

   public NamedThreadLocal(String name) {
      Assert.hasText(name, "Name must not be empty");
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}
