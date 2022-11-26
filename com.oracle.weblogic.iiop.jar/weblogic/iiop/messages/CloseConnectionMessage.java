package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;

public final class CloseConnectionMessage extends Message {
   public CloseConnectionMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      this.unmarshal();
   }

   public CloseConnectionMessage(int minorVersion) {
      super(new MessageHeader(5, minorVersion));
   }
}
