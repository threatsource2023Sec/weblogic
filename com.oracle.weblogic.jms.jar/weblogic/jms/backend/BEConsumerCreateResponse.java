package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.JMSUtilities;
import weblogic.messaging.dispatcher.Response;

public final class BEConsumerCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = -2336586559673535552L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int _HAS_CONSUMER_RECON_INFO = 256;
   private ConsumerReconnectInfo consumerReconnectInfo;

   public BEConsumerCreateResponse(ConsumerReconnectInfo consumerReconnectInfo) {
      this.consumerReconnectInfo = consumerReconnectInfo;
   }

   public ConsumerReconnectInfo getConsumerReconnectInfo() {
      return this.consumerReconnectInfo;
   }

   public BEConsumerCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      if (out instanceof PeerInfoable && ((PeerInfoable)out).getPeerInfo().compareTo(PeerInfo.VERSION_920) < 0) {
         throw JMSUtilities.versionIOException(0, 1, 1);
      } else {
         int flags = 1;
         if (this.consumerReconnectInfo != null) {
            flags |= 256;
         }

         out.writeInt(flags);
         super.writeExternal(out);
         if ((flags & 256) != 0) {
            this.consumerReconnectInfo.writeExternal(out);
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((flags & 256) != 0) {
            this.consumerReconnectInfo = new ConsumerReconnectInfo();
            this.consumerReconnectInfo.readExternal(in);
         }

      }
   }
}
