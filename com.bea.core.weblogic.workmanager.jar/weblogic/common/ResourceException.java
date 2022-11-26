package weblogic.common;

import weblogic.utils.NestedException;

public class ResourceException extends NestedException {
   private static final long serialVersionUID = -7448966506307907440L;

   public ResourceException(String s) {
      super(s);
   }

   public ResourceException() {
      this((String)null, (Throwable)null);
   }

   public ResourceException(String s, Throwable t) {
      super(s, t);
   }

   public ResourceException(Throwable t) {
      super(t);
   }
}
