package weblogic.ejb.container.interfaces;

import weblogic.utils.NestedException;

public final class NoSuchRoleException extends NestedException {
   private static final long serialVersionUID = 7729973007125942759L;

   public NoSuchRoleException() {
   }

   public NoSuchRoleException(String msg) {
      super(msg);
   }

   public NoSuchRoleException(Throwable th) {
      super(th);
   }

   public NoSuchRoleException(String msg, Throwable th) {
      super(msg, th);
   }
}
