package weblogic.iiop.messages;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class LocateReplyMessage extends SequencedMessage implements MessageTypeConstants {
   private int locate_status;
   private IOR ior;

   public LocateReplyMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      this.read(is);
   }

   public static LocateReplyMessage createReply(LocateRequestMessage req, int locate_status) {
      return new LocateReplyMessage(req.getRequestID(), req.getMinorVersion(), locate_status, (IOR)null);
   }

   private LocateReplyMessage(int requestId, int minorVersion, int locate_status, IOR ior) {
      super(4, minorVersion, requestId);
      this.locate_status = locate_status;
      this.ior = ior;
   }

   public static LocateReplyMessage createForwardReply(LocateRequestMessage req, IOR ior) {
      return new LocateReplyMessage(req.getRequestID(), req.getMinorVersion(), 2, ior);
   }

   public IOR needsForwarding() {
      if (this.locate_status != 2 && this.locate_status != 3) {
         if (this.locate_status == 0) {
            throw new OBJECT_NOT_EXIST("Unknown object in LOCATE_REQUEST");
         } else {
            return null;
         }
      } else {
         return this.ior;
      }
   }

   public IOR getIOR() {
      return this.ior;
   }

   public void read(CorbaInputStream in) {
      this.readRequestId(in);
      this.locate_status = in.read_long();
      switch (this.locate_status) {
         case 2:
         case 3:
            this.ior = new IOR(in, true);
            break;
         case 4:
            SystemExceptionReplyBody exReply = new SystemExceptionReplyBody();
            exReply.read(in);
            break;
         case 5:
            short var3 = in.read_short();
      }

   }

   public void write(CorbaOutputStream out) {
      super.write(out);
      this.writeRequestId(out);
      out.write_long(this.locate_status);
      if (this.locate_status == 2 && this.ior != null) {
         this.ior.write(out);
      }

   }
}
