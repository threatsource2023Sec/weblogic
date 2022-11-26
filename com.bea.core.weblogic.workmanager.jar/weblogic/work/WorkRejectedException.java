package weblogic.work;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.kernel.QueueThrottleException;

public class WorkRejectedException extends RemoteException implements InteropWriteReplaceable {
   private static final long serialVersionUID = -5579748719979483383L;

   public WorkRejectedException() {
   }

   public WorkRejectedException(String msg) {
      super(msg);
   }

   public WorkRejectedException(String msg, Throwable throable) {
      super(msg, throable);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      return peerInfo.getMajor() < 9 ? new QueueThrottleException(this.getMessage(), this.getCause()) : this;
   }
}
