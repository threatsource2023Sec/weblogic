package weblogic.connector.exception;

import javax.resource.ResourceException;

public final class NoEnlistXAResourceException extends ResourceException {
   private static final long serialVersionUID = -4964377128937264002L;

   public NoEnlistXAResourceException(String reason, String errorCode) {
      super(reason, errorCode);
   }

   public NoEnlistXAResourceException(String reason) {
      super(reason);
   }
}
