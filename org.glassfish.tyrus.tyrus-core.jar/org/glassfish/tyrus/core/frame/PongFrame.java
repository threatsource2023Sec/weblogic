package org.glassfish.tyrus.core.frame;

import org.glassfish.tyrus.core.TyrusWebSocket;

public class PongFrame extends TyrusFrame {
   public PongFrame(Frame frame) {
      super(frame, TyrusFrame.FrameType.PONG);
   }

   public PongFrame(byte[] payload) {
      super(Frame.builder().fin(true).opcode((byte)10).payloadData(payload).build(), TyrusFrame.FrameType.PONG);
   }

   public void respond(TyrusWebSocket socket) {
      socket.onPong(this);
   }
}
