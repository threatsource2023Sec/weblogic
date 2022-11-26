package com.bea.wls.redef;

public class ClassRedefinitionException extends Exception {
   static final long serialVersionUID = 669214325662698726L;

   public ClassRedefinitionException(String msg, Throwable th) {
      super(msg, th);
   }

   public ClassRedefinitionException(Throwable th) {
      super(th);
   }

   public ClassRedefinitionException(String s) {
      super(s);
   }
}
