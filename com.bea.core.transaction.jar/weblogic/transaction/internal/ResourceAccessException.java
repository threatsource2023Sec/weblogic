package weblogic.transaction.internal;

import javax.transaction.xa.XAException;

public final class ResourceAccessException extends XAException {
   private static final long serialVersionUID = 7030927712655780833L;

   public ResourceAccessException() {
   }

   public ResourceAccessException(String msg) {
      super(msg);
   }
}
