package org.jboss.classfilewriter;

public class InvalidBytecodeException extends RuntimeException {
   public InvalidBytecodeException(String messge) {
      super(messge);
   }
}
