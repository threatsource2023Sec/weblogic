package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;

public final class FragmentMessage extends SequencedMessage {
   public FragmentMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      this.read(is);
   }

   public void read(CorbaInputStream is) {
      this.readRequestId(is);
   }
}
