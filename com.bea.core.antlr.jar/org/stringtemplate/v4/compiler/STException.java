package org.stringtemplate.v4.compiler;

public class STException extends RuntimeException {
   public STException() {
   }

   public STException(String msg, Exception cause) {
      super(msg, cause);
   }
}
