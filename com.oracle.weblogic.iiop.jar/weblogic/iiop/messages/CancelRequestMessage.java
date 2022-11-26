package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;

public final class CancelRequestMessage extends Message {
   public CancelRequestMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr);
   }
}
