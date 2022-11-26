package com.googlecode.cqengine.resultset.common;

public class NoSuchObjectException extends RuntimeException {
   private static final long serialVersionUID = 7269599398244063814L;

   public NoSuchObjectException() {
   }

   public NoSuchObjectException(String s) {
      super(s);
   }
}
