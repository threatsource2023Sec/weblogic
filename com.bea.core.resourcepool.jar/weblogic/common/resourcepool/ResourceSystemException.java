package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceSystemException extends ResourceException {
   public ResourceSystemException(String s) {
      super(s);
   }

   public ResourceSystemException() {
      this((String)null);
   }

   public ResourceSystemException(String s, Throwable t) {
      super(s, t);
   }
}
