package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Type;

public class Local {
   private Type type;
   private int index;

   public Local(int index, Type type) {
      this.type = type;
      this.index = index;
   }

   public int getIndex() {
      return this.index;
   }

   public Type getType() {
      return this.type;
   }
}
