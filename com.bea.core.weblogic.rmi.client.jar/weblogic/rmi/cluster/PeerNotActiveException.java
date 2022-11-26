package weblogic.rmi.cluster;

import java.rmi.server.ServerNotActiveException;

public final class PeerNotActiveException extends ServerNotActiveException {
   private static final long serialVersionUID = 7316387951257487863L;

   public PeerNotActiveException() {
   }

   public PeerNotActiveException(String msg) {
      super(msg);
   }
}
