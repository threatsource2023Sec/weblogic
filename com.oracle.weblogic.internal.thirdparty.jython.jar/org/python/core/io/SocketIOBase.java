package org.python.core.io;

import java.io.IOException;
import java.nio.channels.Channel;
import org.python.core.Py;

public abstract class SocketIOBase extends RawIOBase {
   protected Channel socketChannel;
   private boolean readable = false;
   private boolean writable = false;

   public SocketIOBase(Channel socketChannel, String mode) {
      this.socketChannel = socketChannel;
      this.parseMode(mode);
   }

   protected void parseMode(String mode) {
      if (mode.equals("r")) {
         this.readable = true;
      } else if (mode.equals("w")) {
         this.writable = true;
      } else {
         if (!mode.equals("rw")) {
            throw Py.ValueError("invalid mode: '" + mode + "'");
         }

         this.readable = this.writable = true;
      }

   }

   public void close() {
      if (!this.closed()) {
         try {
            this.socketChannel.close();
         } catch (IOException var2) {
            throw Py.IOError(var2);
         }

         super.close();
      }
   }

   public Channel getChannel() {
      return this.socketChannel;
   }

   public boolean readable() {
      return this.readable;
   }

   public boolean writable() {
      return this.writable;
   }
}
