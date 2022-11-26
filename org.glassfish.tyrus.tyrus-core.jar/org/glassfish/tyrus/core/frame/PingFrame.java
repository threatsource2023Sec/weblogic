package org.glassfish.tyrus.core.frame;

import org.glassfish.tyrus.core.TyrusWebSocket;

public class PingFrame extends TyrusFrame {
   public PingFrame(Frame frame) {
      super(frame, TyrusFrame.FrameType.PING);
   }

   public PingFrame(byte[] payload) {
      super(Frame.builder().fin(true).opcode((byte)9).payloadData(payload).build(), TyrusFrame.FrameType.PING);
   }

   public void respond(TyrusWebSocket socket) {
      socket.onPing(this);
   }
}
