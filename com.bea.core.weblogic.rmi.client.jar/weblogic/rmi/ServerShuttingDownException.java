package weblogic.rmi;

import java.io.IOException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;

public final class ServerShuttingDownException extends java.rmi.ConnectIOException implements InteropWriteReplaceable {
   private static final long serialVersionUID = -358206694774503090L;

   public ServerShuttingDownException(String s) {
      super(s);
   }

   public ServerShuttingDownException(String s, Exception e) {
      super(s, e);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      return !peerInfo.equals(PeerInfo.FOREIGN) && peerInfo.compareTo(PeerInfo.VERSION_920) > 0 ? this : new java.rmi.ConnectIOException(this.getMessage());
   }
}
