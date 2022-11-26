package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class IdleState extends StreamState {
   public IdleState() {
      super(new HashSet(Arrays.asList(FrameType.HEADERS, FrameType.PRIORITY)), new HashSet(Arrays.asList(FrameType.HEADERS)), 1);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
