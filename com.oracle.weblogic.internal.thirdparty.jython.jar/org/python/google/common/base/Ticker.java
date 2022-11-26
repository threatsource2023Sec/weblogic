package org.python.google.common.base;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public abstract class Ticker {
   private static final Ticker SYSTEM_TICKER = new Ticker() {
      public long read() {
         return Platform.systemNanoTime();
      }
   };

   protected Ticker() {
   }

   @CanIgnoreReturnValue
   public abstract long read();

   public static Ticker systemTicker() {
      return SYSTEM_TICKER;
   }
}
