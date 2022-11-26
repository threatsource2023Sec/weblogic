package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class HalfClosedLocalState extends StreamState {
   public HalfClosedLocalState() {
      super(new HashSet(Arrays.asList(FrameType.DATA, FrameType.HEADERS, FrameType.PRIORITY, FrameType.RST_STREAM, FrameType.PUSH_PROMISE, FrameType.WINDOW_UPDATE)), new HashSet(Arrays.asList(FrameType.WINDOW_UPDATE, FrameType.PRIORITY, FrameType.RST_STREAM)), 1);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
