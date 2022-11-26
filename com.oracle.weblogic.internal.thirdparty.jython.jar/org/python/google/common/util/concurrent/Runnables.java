package org.python.google.common.util.concurrent;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
public final class Runnables {
   private static final Runnable EMPTY_RUNNABLE = new Runnable() {
      public void run() {
      }
   };

   public static Runnable doNothing() {
      return EMPTY_RUNNABLE;
   }

   private Runnables() {
   }
}
