package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourceDeadException extends ResourceException {
   public ResourceDeadException(String s) {
      super(s);
   }

   public ResourceDeadException() {
      this((String)null);
   }

   public ResourceDeadException(String s, Throwable t) {
      super(s, t);
   }
}
