package com.oracle.pitchfork.interfaces;

public class LifecycleCallbackException extends RuntimeException {
   private static final long serialVersionUID = 1505749224864792843L;

   public LifecycleCallbackException() {
   }

   public LifecycleCallbackException(String msg) {
      super(msg);
   }

   public LifecycleCallbackException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
