package org.glassfish.tyrus.core.frame;

import org.glassfish.tyrus.core.TyrusWebSocket;

public class BinaryFrame extends TyrusFrame {
   private final boolean continuation;

   public BinaryFrame(Frame frame) {
      super(frame, TyrusFrame.FrameType.BINARY);
      this.continuation = false;
   }

   public BinaryFrame(Frame frame, boolean continuation) {
      super(frame, continuation ? TyrusFrame.FrameType.BINARY_CONTINUATION : TyrusFrame.FrameType.BINARY);
      this.continuation = continuation;
   }

   public BinaryFrame(byte[] payload, boolean continuation, boolean fin) {
      super(Frame.builder().payloadData(payload).opcode((byte)(continuation ? 0 : 2)).fin(fin).build(), continuation ? TyrusFrame.FrameType.BINARY_CONTINUATION : TyrusFrame.FrameType.BINARY);
      this.continuation = continuation;
   }

   public void respond(TyrusWebSocket socket) {
      if (this.continuation) {
         socket.onFragment(this, this.isFin());
      } else if (this.isFin()) {
         socket.onMessage(this);
      } else {
         socket.onFragment(this, false);
      }

   }
}
