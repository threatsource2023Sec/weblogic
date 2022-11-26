package com.kenai.jffi;

public final class LastError {
   private final Foreign foreign;

   private LastError() {
      this.foreign = Foreign.getInstance();
   }

   public static final LastError getInstance() {
      return LastError.SingletonHolder.INSTANCE;
   }

   /** @deprecated */
   @Deprecated
   public final int getError() {
      return Foreign.getLastError();
   }

   public final int get() {
      return Foreign.getLastError();
   }

   public final void set(int value) {
      Foreign.setLastError(value);
   }

   // $FF: synthetic method
   LastError(Object x0) {
      this();
   }

   private static final class SingletonHolder {
      static final LastError INSTANCE = new LastError();
   }
}
