package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class BESessionCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = 2830831915495832371L;
   private static final int EXTVERSION61 = 1;
   private static final int EXTVERSION92 = 2;
   private static final int VERSION_MASK = 255;
   private static final int HAS_ALLOW_DELAY_CLOSE = 256;
   private static final int EXTVERSION = 2;
   private long lastSequenceNumber;
   private boolean allowDelayClose;

   public BESessionCloseRequest(boolean allowDelayClose, JMSID sessionId, long lastSequenceNumber) {
      super(sessionId, 13328);
      this.lastSequenceNumber = lastSequenceNumber;
      this.allowDelayClose = allowDelayClose;
   }

   public final long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   boolean allowDelayClose() {
      return this.allowDelayClose;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BESessionCloseRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask;
      if (out instanceof PeerInfoable && ((PeerInfoable)out).getPeerInfo().compareTo(PeerInfo.VERSION_920) < 0) {
         mask = 1;
      } else {
         mask = 2;
         if (this.allowDelayClose) {
            mask |= 256;
         }
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(this.lastSequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version >= 1 && 2 >= version) {
         super.readExternal(in);
         this.lastSequenceNumber = in.readLong();
         this.allowDelayClose = (256 & mask) != 0;
      } else {
         throw JMSUtilities.versionIOException(version, 1, 2);
      }
   }
}
