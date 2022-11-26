package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class ReceivingEndOfStreamState extends ClosedState {
   public ReceivingEndOfStreamState() {
      super(new HashSet(Arrays.asList(FrameType.PRIORITY)), new HashSet(0), 5);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
