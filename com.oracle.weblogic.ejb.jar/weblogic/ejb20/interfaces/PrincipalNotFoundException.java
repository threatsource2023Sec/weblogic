package weblogic.ejb20.interfaces;

import weblogic.utils.NestedException;

public final class PrincipalNotFoundException extends NestedException {
   private static final long serialVersionUID = 8767040552998421219L;

   public PrincipalNotFoundException() {
   }

   public PrincipalNotFoundException(String msg) {
      super(msg);
   }

   public PrincipalNotFoundException(Throwable th) {
      super(th);
   }

   public PrincipalNotFoundException(String msg, Throwable th) {
      super(msg, th);
   }
}
