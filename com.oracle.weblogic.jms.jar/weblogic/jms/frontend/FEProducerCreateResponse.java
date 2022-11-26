package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Response;

public final class FEProducerCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = -6258736813443927954L;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private JMSID producerId;
   private String runtimeMBeanName;

   public FEProducerCreateResponse(JMSID producerId, String runtimeMBeanName) {
      this.producerId = producerId;
      this.runtimeMBeanName = runtimeMBeanName;
   }

   public JMSID getProducerId() {
      return this.producerId;
   }

   public String getRuntimeMBeanName() {
      return this.runtimeMBeanName;
   }

   public FEProducerCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      PeerInfo peerInfo = null;
      if (out instanceof PeerInfoable) {
         peerInfo = ((PeerInfoable)out).getPeerInfo();
      }

      byte version;
      if (peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_81) < 0) {
         version = 1;
      } else {
         version = 2;
      }

      out.writeByte(version);
      super.writeExternal(out);
      this.producerId.writeExternal(out);
      if (version >= 2) {
         out.writeUTF(this.runtimeMBeanName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte version = in.readByte();
      if (version != 1 && version != 2) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         this.producerId = new JMSID();
         this.producerId.readExternal(in);
         if (version >= 2) {
            this.runtimeMBeanName = in.readUTF();
         }

      }
   }
}
