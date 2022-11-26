package weblogic.deploy.service.internal.transport;

import java.io.ObjectStreamException;
import java.rmi.RemoteException;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;

public final class UnreachableHostException extends RemoteException {
   private final String unreachableMessage;
   private final Exception internalCause;

   public UnreachableHostException(String server) {
      this(server, (Exception)null);
   }

   public UnreachableHostException(String server, Exception e) {
      this.unreachableMessage = DeployerRuntimeLogger.serverUnreachable(server);
      this.internalCause = e;
   }

   public String getMessage() {
      return this.internalCause == null ? this.unreachableMessage : this.unreachableMessage + "; nested exception is: \n\t" + this.internalCause.toString();
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   private Object writeReplace() throws ObjectStreamException {
      return new RemoteException(this.unreachableMessage);
   }
}
