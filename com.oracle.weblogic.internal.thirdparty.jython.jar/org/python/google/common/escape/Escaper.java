package org.python.google.common.escape;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;

@GwtCompatible
public abstract class Escaper {
   private final Function asFunction = new Function() {
      public String apply(String from) {
         return Escaper.this.escape(from);
      }
   };

   protected Escaper() {
   }

   public abstract String escape(String var1);

   public final Function asFunction() {
      return this.asFunction;
   }
}
