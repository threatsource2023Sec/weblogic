package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public final class ResourcePermissionsException extends ResourceException {
   public ResourcePermissionsException(String s) {
      super(s);
   }

   public ResourcePermissionsException() {
      this((String)null);
   }

   public ResourcePermissionsException(String s, Throwable t) {
      super(s, t);
   }
}
