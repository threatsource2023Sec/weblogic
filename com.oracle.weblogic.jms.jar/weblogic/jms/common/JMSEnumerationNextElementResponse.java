package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Response;

public final class JMSEnumerationNextElementResponse extends Response implements Externalizable {
   static final long serialVersionUID = -6840179225324539871L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int MESSAGE_MASK = 65280;
   private static final int MESSAGE_SHIFT = 8;
   private MessageImpl message;
   private int compressionThreshold = Integer.MAX_VALUE;

   public JMSEnumerationNextElementResponse(MessageImpl message) {
      this.message = message;
   }

   public final void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }

   public final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   public final MessageImpl getMessage() {
      return this.message;
   }

   public JMSEnumerationNextElementResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
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

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         byte type = (byte)((mask & '\uff00') >> 8);
         if (type != 0) {
            this.message = MessageImpl.createMessageImpl(type);
            this.message.readExternal(in);
         }

      }
   }
}
