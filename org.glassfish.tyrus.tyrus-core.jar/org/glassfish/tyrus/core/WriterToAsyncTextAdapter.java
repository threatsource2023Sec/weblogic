package org.glassfish.tyrus.core;

import java.io.IOException;
import java.io.Writer;

class WriterToAsyncTextAdapter extends Writer {
   private final TyrusWebSocket socket;
   private String buffer = null;

   public WriterToAsyncTextAdapter(TyrusWebSocket socket) {
      this.socket = socket;
   }

   private void sendBuffer(boolean last) {
      this.socket.sendText(this.buffer, last);
   }

   public void write(char[] chars, int index, int len) throws IOException {
      if (this.buffer != null) {
         this.sendBuffer(false);
      }

      this.buffer = (new String(chars)).substring(index, index + len);
   }

   public void flush() throws IOException {
      if (this.buffer != null) {
         this.sendBuffer(false);
      }

      this.buffer = null;
   }

   public void close() throws IOException {
      this.sendBuffer(true);
   }
}
