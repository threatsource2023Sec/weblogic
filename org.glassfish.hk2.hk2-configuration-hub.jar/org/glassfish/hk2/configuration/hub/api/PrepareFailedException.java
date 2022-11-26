package org.glassfish.hk2.configuration.hub.api;

import org.glassfish.hk2.api.HK2RuntimeException;

public class PrepareFailedException extends HK2RuntimeException {
   private static final long serialVersionUID = 6554163616313792146L;

   public PrepareFailedException() {
   }

   public PrepareFailedException(Throwable th) {
      super(th);
   }
}
