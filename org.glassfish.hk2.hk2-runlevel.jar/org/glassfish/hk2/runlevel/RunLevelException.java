package org.glassfish.hk2.runlevel;

import org.glassfish.hk2.api.HK2RuntimeException;

public class RunLevelException extends HK2RuntimeException {
   private static final long serialVersionUID = 1514027985824049713L;

   public RunLevelException() {
   }

   public RunLevelException(String message) {
      super(message);
   }

   public RunLevelException(Throwable origin) {
      super(origin);
   }

   public RunLevelException(String message, Throwable origin) {
      super(message, origin);
   }
}
