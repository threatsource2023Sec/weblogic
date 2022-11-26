package weblogic.socket;

import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

final class NIOSocketInfo extends SocketInfo {
   private SocketChannel sc;
   private SelectionKey key;
   private int selectorIndex;

   NIOSocketInfo(MuxableSocket ms, int index) {
      super(ms);
      this.selectorIndex = index;
   }

   protected String fieldsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.fieldsToString()).append(", ").append("socketChannel = ").append(this.sc);
      return sb.toString();
   }

   SocketChannel getSocketChannel() {
      if (this.sc == null) {
         Socket sock = this.ms.getSocket();
         this.sc = sock.getChannel();
      }

      return this.sc;
   }

   SelectionKey getSelectionKey() {
      return this.key;
   }

   void setSelectionKey(SelectionKey key) {
      this.key = key;
   }

   int getSelectorIndex() {
      return this.selectorIndex;
   }
}
