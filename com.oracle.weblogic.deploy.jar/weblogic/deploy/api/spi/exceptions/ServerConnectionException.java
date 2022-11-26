package weblogic.deploy.api.spi.exceptions;

import weblogic.management.ManagementException;

public class ServerConnectionException extends IllegalStateException {
   public ServerConnectionException(String msg) {
      super(msg);
   }

   public ServerConnectionException(String msg, Throwable t) {
      super(msg);
      this.initCause(t);
   }

   public Throwable getRootCause() {
      return ManagementException.unWrapExceptions(this);
   }
}
