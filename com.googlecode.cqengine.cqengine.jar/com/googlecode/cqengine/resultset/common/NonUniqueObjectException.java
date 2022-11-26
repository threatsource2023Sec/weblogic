package com.googlecode.cqengine.resultset.common;

public class NonUniqueObjectException extends RuntimeException {
   private static final long serialVersionUID = -5928615245205366453L;

   public NonUniqueObjectException() {
   }

   public NonUniqueObjectException(String s) {
      super(s);
   }
}
