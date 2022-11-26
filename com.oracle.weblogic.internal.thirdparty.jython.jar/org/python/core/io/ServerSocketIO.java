package org.python.core.io;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import org.python.core.Py;

public class ServerSocketIO extends SocketIOBase {
   public ServerSocketIO(ServerSocketChannel socketChannel, String mode) {
      super(socketChannel, mode);
   }

   public int readinto(ByteBuffer buf) {
      this.checkClosed();
      this.checkReadable();
      throw Py.IOError((Constant)Errno.ENOTCONN);
   }

   public int write(ByteBuffer buf) {
      this.checkClosed();
      this.checkWritable();
      throw Py.IOError((Constant)Errno.EBADF);
   }
}
