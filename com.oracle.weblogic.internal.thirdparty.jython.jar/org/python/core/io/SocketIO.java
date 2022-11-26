package org.python.core.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.python.core.Py;

public class SocketIO extends SocketIOBase {
   public SocketIO(SocketChannel socketChannel, String mode) {
      super(socketChannel, mode);
   }

   public int readinto(ByteBuffer buf) {
      this.checkClosed();
      this.checkReadable();

      try {
         return ((SocketChannel)this.socketChannel).read(buf);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public long readinto(ByteBuffer[] bufs) {
      this.checkClosed();
      this.checkReadable();

      try {
         return ((SocketChannel)this.socketChannel).read(bufs);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public int write(ByteBuffer buf) {
      this.checkClosed();
      this.checkWritable();

      try {
         return ((SocketChannel)this.socketChannel).write(buf);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public long write(ByteBuffer[] bufs) {
      this.checkClosed();
      this.checkWritable();

      try {
         return ((SocketChannel)this.socketChannel).write(bufs);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }
}
