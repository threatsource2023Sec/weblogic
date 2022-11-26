package net.shibboleth.utilities.java.support.logic;

public class ConstraintViolationException extends RuntimeException {
   private static final long serialVersionUID = -3994361273802830823L;

   public ConstraintViolationException(String message) {
      super(message);
   }
}
