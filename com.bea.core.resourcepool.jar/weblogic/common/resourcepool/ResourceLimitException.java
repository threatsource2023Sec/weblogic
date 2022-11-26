package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceLimitException extends ResourceException {
   public ResourceLimitException(String s) {
      super(s);
   }

   public ResourceLimitException() {
      this((String)null);
   }

   public ResourceLimitException(String s, Throwable t) {
      super(s, t);
   }
}
