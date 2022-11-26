package weblogic.servlet.http2;

import java.util.Set;
import weblogic.servlet.http2.frame.FrameType;

abstract class StreamState {
   private final Set framesAllowedToReceive;
   private final Set framesAllowedToSend;
   private final int errorOnReceivingInvalidFrame;

   protected StreamState(Set framesAllowedToReceive, Set framesAllowedToSend, int ec) {
      this.framesAllowedToReceive = framesAllowedToReceive;
      this.framesAllowedToSend = framesAllowedToSend;
      this.errorOnReceivingInvalidFrame = ec;
   }

   protected boolean isFrameTypeAllowedToReceive(FrameType frameType) {
      return this.framesAllowedToReceive.contains(frameType);
   }

   protected boolean isFrameTypeAllowedToSend(FrameType frameType) {
      return this.framesAllowedToSend.contains(frameType);
   }

   protected int getErrorCode() {
      return this.errorOnReceivingInvalidFrame;
   }

   protected abstract void ChangeOnSending(FrameType var1);

   protected abstract void ChangeOnReceiving(FrameType var1);
}
