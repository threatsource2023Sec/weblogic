package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;

public final class MessageErrorMessage extends Message {
   public MessageErrorMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
   }
}
