package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.util.Assert;

public class NamedInheritableThreadLocal extends InheritableThreadLocal {
   private final String name;

   public NamedInheritableThreadLocal(String name) {
      Assert.hasText(name, "Name must not be empty");
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}
