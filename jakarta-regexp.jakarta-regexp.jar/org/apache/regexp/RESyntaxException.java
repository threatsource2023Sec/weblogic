package org.apache.regexp;

public class RESyntaxException extends RuntimeException {
   public RESyntaxException(String var1) {
      super("Syntax error: " + var1);
   }
}
