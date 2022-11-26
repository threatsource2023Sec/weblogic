package weblogic.iiop.messages;

import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class LocateRequestMessage extends SequencedRequestMessage implements MessageTypeConstants {
   private TargetAddress target;
   private byte[] objectKey;

   public LocateRequestMessage(IOR ior, int minorVersion, int requestID) {
      super(3, minorVersion, requestID);
      this.objectKey = ior.getProfile().getKey();
      this.target = new TargetAddress(this.objectKey);
   }

   public LocateRequestMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      this.read(is);
   }

   public byte[] getObjectKey() {
      return this.objectKey;
   }

   public void read(CorbaInputStream in) {
      switch (this.getMinorVersion()) {
         case 0:
         case 1:
            this.readRequestId(in);
            this.objectKey = in.read_octet_sequence(1048576);
            break;
         case 2:
            this.readRequestId(in);
            this.target = new TargetAddress(in);
            this.objectKey = this.target.getObjectKey();
      }

   }

   public void write(CorbaOutputStream out) {
      super.write(out);
      switch (this.getMinorVersion()) {
         case 0:
         case 1:
            this.writeRequestId(out);
            out.write_octet_sequence(this.objectKey);
            break;
         case 2:
            this.writeRequestId(out);
            this.target.write(out);
      }

   }
}
