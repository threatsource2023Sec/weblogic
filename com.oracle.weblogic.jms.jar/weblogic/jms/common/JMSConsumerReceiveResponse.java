package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Response;

public class JMSConsumerReceiveResponse extends Response implements Externalizable {
   static final long serialVersionUID = -6762955361476059878L;
   private static final byte EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int MESSAGE_TYPE_MASK = 65280;
   private static final int MESSAGE_TYPE_SHIFT = 8;
   private static final int TRANSACTIONAL_FLAG = 65536;
   private static final int SEQUENCE_FLAG = 131072;
   private MessageImpl message;
   private long sequenceNumber;
   private boolean isTransactional;
   private int compressionThreshold = Integer.MAX_VALUE;

   public JMSConsumerReceiveResponse(MessageImpl message, long sequenceNumber, boolean isTransactional) {
      this.message = message;
      this.sequenceNumber = sequenceNumber;
      this.isTransactional = isTransactional;
   }

   public final void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }

   public final boolean isTransactional() {
      return this.isTransactional;
   }

   public final MessageImpl getMessage() {
      return this.message;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public JMSConsumerReceiveResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.isTransactional) {
         mask |= 65536;
      }

      if (this.sequenceNumber != 0L) {
         mask |= 131072;
      }

      if (this.message != null) {
         mask |= this.message.getType() << 8;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      if (this.message != null) {
         if (this.compressionThreshold == Integer.MAX_VALUE) {
            this.message.writeExternal(out);
         } else {
            this.message.writeExternal(MessageImpl.createJMSObjectOutputWrapper(out, this.compressionThreshold, true));
         }
      }

      if (this.sequenceNumber != 0L) {
         out.writeLong(this.sequenceNumber);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 65536) != 0) {
            this.isTransactional = true;
         }

         int messageType = (mask & '\uff00') >> 8;
         if (messageType != 0) {
            this.message = MessageImpl.createMessageImpl((byte)messageType);
            this.message.readExternal(in);
         }

         if ((mask & 131072) != 0) {
            this.sequenceNumber = in.readLong();
         }

      }
   }
}
