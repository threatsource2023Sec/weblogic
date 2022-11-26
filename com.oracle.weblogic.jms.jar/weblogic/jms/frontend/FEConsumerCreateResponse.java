package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Response;

public final class FEConsumerCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = 4370603925624654360L;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte VERSION_MASK = 15;
   private static final byte _HAS_CONSUMER_RECON_INFO = 16;
   private JMSID consumerId;
   private String runtimeMBeanName;
   private ConsumerReconnectInfo consumerReconnectInfo;

   public FEConsumerCreateResponse(JMSID consumerId, String runtimeMBeanName, ConsumerReconnectInfo consumerReconnectInfo) {
      this.consumerId = consumerId;
      this.runtimeMBeanName = runtimeMBeanName;
      this.consumerReconnectInfo = consumerReconnectInfo;
   }

   public JMSID getConsumerId() {
      return this.consumerId;
   }

   public ConsumerReconnectInfo getConsumerReconnectInfo() {
      return this.consumerReconnectInfo;
   }

   public String getRuntimeMBeanName() {
      return this.runtimeMBeanName;
   }

   public FEConsumerCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      byte version;
      if (out instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
         if (peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_920) < 0) {
            if (peerInfo.compareTo(PeerInfo.VERSION_81) >= 0) {
               version = 2;
            } else {
               version = 1;
            }
         } else {
            version = 3;
         }
      } else {
         version = 3;
      }

      byte flags;
      if (version >= 3 && this.consumerReconnectInfo != null) {
         flags = (byte)(version | 16);
      } else {
         flags = version;
      }

      assert 3 == version;

      out.writeByte(flags);
      super.writeExternal(out);
      this.consumerId.writeExternal(out);
      if (version >= 2) {
         out.writeUTF(this.runtimeMBeanName);
      }

      if ((flags & 16) != 0) {
         this.consumerReconnectInfo.writeExternal(out);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte flags = in.readByte();
      byte version = (byte)(flags & 15);
      if (version >= 1 && 3 >= version) {
         super.readExternal(in);
         this.consumerId = new JMSID();
         this.consumerId.readExternal(in);
         if (version >= 2) {
            this.runtimeMBeanName = in.readUTF();
            if (version >= 3 && (flags & 16) != 0) {
               this.consumerReconnectInfo = new ConsumerReconnectInfo();
               this.consumerReconnectInfo.readExternal(in);
            }
         }

      } else {
         throw JMSUtilities.versionIOException(version, 1, 3);
      }
   }
}
