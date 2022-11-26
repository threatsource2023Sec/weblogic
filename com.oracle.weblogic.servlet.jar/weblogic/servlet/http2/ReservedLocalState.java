package weblogic.servlet.http2;

import java.util.Arrays;
import java.util.HashSet;
import weblogic.servlet.http2.frame.FrameType;

class ReservedLocalState extends StreamState {
   public ReservedLocalState() {
      super(new HashSet(Arrays.asList(FrameType.RST_STREAM, FrameType.PRIORITY, FrameType.WINDOW_UPDATE)), new HashSet(Arrays.asList(FrameType.HEADERS, FrameType.RST_STREAM, FrameType.PRIORITY)), 1);
   }

   protected void ChangeOnSending(FrameType frameType) {
   }

   protected void ChangeOnReceiving(FrameType frameType) {
   }
}
