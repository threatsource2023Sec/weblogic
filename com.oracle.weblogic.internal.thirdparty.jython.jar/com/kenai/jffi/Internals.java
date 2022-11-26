package com.kenai.jffi;

public final class Internals {
   private Internals() {
   }

   public static final long getErrnoSaveFunction() {
      return Foreign.getInstance().getSaveErrnoFunction();
   }
}
