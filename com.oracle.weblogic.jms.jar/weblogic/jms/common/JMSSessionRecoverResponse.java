package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Response;

public final class JMSSessionRecoverResponse extends Response implements Externalizable {
   static final long serialVersionUID = -7983006155010336377L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private long sequenceNumber;

   public JMSSessionRecoverResponse(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public JMSSessionRecoverResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
      super.writeExternal(out);
      out.writeLong(this.sequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.sequenceNumber = in.readLong();
      }
   }
}
