package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.Message;

public final class HdrMessageImpl extends MessageImpl implements Externalizable {
   private static final byte EXTVERSION = 1;
   static final long serialVersionUID = -5400333814519213733L;
   private int controlOpcode;

   public HdrMessageImpl() {
   }

   public HdrMessageImpl(Message message) throws javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public void nullBody() {
   }

   public HdrMessageImpl(Message message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      super(message, destination, replyDestination);
   }

   public byte getType() {
      return 2;
   }

   public final int getControlOpcode() {
      return this.controlOpcode;
   }

   public final void setControlOpcode(int opcode) {
      assert (opcode & -16711681) == 0;

      this.controlOpcode = opcode;
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
      } else {
         out = tOut;
      }

      out.writeByte(1);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      }
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      HdrMessageImpl hmi = new HdrMessageImpl();
      this.copy(hmi);
      return hmi;
   }

   public long getPayloadSize() {
      return 0L;
   }

   public void decompressMessageBody() {
   }
}
