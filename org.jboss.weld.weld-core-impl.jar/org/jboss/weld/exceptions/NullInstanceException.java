package org.jboss.weld.exceptions;

public class NullInstanceException extends WeldException {
   private static final long serialVersionUID = 1L;

   public NullInstanceException(String message) {
      super(message);
   }
}
