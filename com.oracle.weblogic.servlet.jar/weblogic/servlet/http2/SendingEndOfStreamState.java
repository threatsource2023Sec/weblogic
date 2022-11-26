package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class SendingEndOfStreamState extends ClosedState {
   public SendingEndOfStreamState() {
      super(new HashSet(Arrays.asList(FrameType.WINDOW_UPDATE, FrameType.PRIORITY, FrameType.RST_STREAM)), new HashSet(0), 5);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
