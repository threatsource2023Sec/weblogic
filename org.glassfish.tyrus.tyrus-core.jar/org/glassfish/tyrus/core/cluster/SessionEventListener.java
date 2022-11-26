package org.glassfish.tyrus.core.cluster;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.websocket.CloseReason;
import javax.websocket.Session;

public class SessionEventListener {
   private final Session session;

   public SessionEventListener(Session session) {
      this.session = session;
   }

   public void onSendText(String message) throws IOException {
      this.session.getBasicRemote().sendText(message);
   }

   public void onSendText(String message, boolean isLast) throws IOException {
      this.session.getBasicRemote().sendText(message, isLast);
   }

   public void onSendBinary(byte[] message) throws IOException {
      this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
   }

   public void onSendBinary(byte[] message, boolean isLast) throws IOException {
      this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message), isLast);
   }

   public void onSendPing(byte[] payload) throws IOException {
      this.session.getBasicRemote().sendPing(ByteBuffer.wrap(payload));
   }

   public void onSendPong(byte[] payload) throws IOException {
      this.session.getBasicRemote().sendPong(ByteBuffer.wrap(payload));
   }

   public void onClose() throws IOException {
      this.session.close();
   }

   public void onClose(CloseReason closeReason) throws IOException {
      this.session.close(closeReason);
   }
}
