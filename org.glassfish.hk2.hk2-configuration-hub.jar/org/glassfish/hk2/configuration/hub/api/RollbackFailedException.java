package org.glassfish.hk2.configuration.hub.api;

import org.glassfish.hk2.api.HK2RuntimeException;

public class RollbackFailedException extends HK2RuntimeException {
   private static final long serialVersionUID = -8491036705916859035L;

   public RollbackFailedException() {
   }

   public RollbackFailedException(Throwable cause) {
      super(cause);
   }
}
