package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.TypedValue;

public final class BooleanTypedValue extends TypedValue {
   public static final BooleanTypedValue TRUE = new BooleanTypedValue(true);
   public static final BooleanTypedValue FALSE = new BooleanTypedValue(false);

   private BooleanTypedValue(boolean b) {
      super(b);
   }

   public static BooleanTypedValue forValue(boolean b) {
      return b ? TRUE : FALSE;
   }
}
