package org.glassfish.hk2.configuration.hub.api;

import org.glassfish.hk2.api.HK2RuntimeException;

public class CommitFailedException extends HK2RuntimeException {
   private static final long serialVersionUID = -7714473563491187847L;

   public CommitFailedException() {
   }

   public CommitFailedException(Throwable cause) {
      super(cause);
   }
}
