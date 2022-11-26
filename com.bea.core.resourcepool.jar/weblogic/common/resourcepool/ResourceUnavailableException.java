package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceUnavailableException extends ResourceException {
   public ResourceUnavailableException(String s) {
      super(s);
   }

   public ResourceUnavailableException() {
      this((String)null);
   }

   public ResourceUnavailableException(String s, Throwable t) {
      super(s, t);
   }
}
