package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceDisabledException extends ResourceException {
   public ResourceDisabledException(String s) {
      super(s);
   }

   public ResourceDisabledException() {
      this((String)null);
   }

   public ResourceDisabledException(String s, Throwable t) {
      super(s, t);
   }
}
