package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceUnusableException extends ResourceException {
   public ResourceUnusableException(String s) {
      super(s);
   }

   public ResourceUnusableException() {
      this((String)null);
   }

   public ResourceUnusableException(String s, Throwable t) {
      super(s, t);
   }
}
