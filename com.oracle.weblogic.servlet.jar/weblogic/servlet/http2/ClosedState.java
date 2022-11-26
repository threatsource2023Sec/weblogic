package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class ClosedState extends StreamState {
   public ClosedState() {
      super(new HashSet(Arrays.asList(FrameType.PRIORITY)), new HashSet(Arrays.asList(FrameType.PRIORITY)), 5);
   }

   public ClosedState(HashSet validIncomingFrames, HashSet validOutcomeFrames, int error) {
      super(validIncomingFrames, validOutcomeFrames, error);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
