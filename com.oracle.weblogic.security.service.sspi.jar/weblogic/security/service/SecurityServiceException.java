package weblogic.security.service;

import weblogic.utils.NestedException;

public class SecurityServiceException extends NestedException {
   public SecurityServiceException() {
   }

   public SecurityServiceException(String msg) {
      super(msg);
   }

   public SecurityServiceException(Throwable nested) {
      super(nested);
   }

   public SecurityServiceException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
