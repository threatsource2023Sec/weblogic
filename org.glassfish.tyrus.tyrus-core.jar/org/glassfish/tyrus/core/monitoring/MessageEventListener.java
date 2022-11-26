package org.glassfish.tyrus.core.monitoring;

import org.glassfish.tyrus.core.Beta;
import org.glassfish.tyrus.core.frame.TyrusFrame;

@Beta
public interface MessageEventListener {
   MessageEventListener NO_OP = new MessageEventListener() {
      public void onFrameSent(TyrusFrame.FrameType frameType, long payloadLength) {
      }

      public void onFrameReceived(TyrusFrame.FrameType frameType, long payloadLength) {
      }
   };

   void onFrameSent(TyrusFrame.FrameType var1, long var2);

   void onFrameReceived(TyrusFrame.FrameType var1, long var2);
}
