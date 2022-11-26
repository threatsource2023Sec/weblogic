package com.bea.wls.redef;

public class ClassRedefInitializationException extends Exception {
   static final long serialVersionUID = 669214325662698726L;

   public ClassRedefInitializationException(String msg, Throwable th) {
      super(msg, th);
   }

   public ClassRedefInitializationException(Throwable th) {
      super(th);
   }

   public ClassRedefInitializationException(String s) {
      super(s);
   }
}
