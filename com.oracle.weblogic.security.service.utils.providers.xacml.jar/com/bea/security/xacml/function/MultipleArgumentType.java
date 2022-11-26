package com.bea.security.xacml.function;

import com.bea.common.security.xacml.Type;

public class MultipleArgumentType extends Type {
   private int minCount;

   public MultipleArgumentType(Type argType, int minCount) {
      super(argType);
      this.minCount = minCount;
   }

   public int getMinCount() {
      return this.minCount;
   }
}
