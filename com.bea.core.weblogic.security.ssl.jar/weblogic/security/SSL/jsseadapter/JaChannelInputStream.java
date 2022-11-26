package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.text.MessageFormat;
import java.util.logging.Level;

final class JaChannelInputStream extends InputStream {
   private final ReadableByteChannel readableChannel;
   private AbstractSelectableChannel selectableChannel;
   private Selector selector;
   private final Object selectorLock = new Object();

   public int read() throws IOException {
      byte[] data = new byte[1];
      int result = this.read(data, 0, 1);
      if (result < 0) {
         return -1;
      } else if (0 == result) {
         throw new IllegalBlockingModeException();
      } else {
         return data[0];
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (null == b) {
         throw new NullPointerException("Expected non-null array.");
      } else if (off >= 0 && len >= 0 && off + len <= b.length) {
         if (0 == len) {
            return 0;
         } else {
            ByteBuffer buff = ByteBuffer.wrap(b);
            buff.position(off);
            buff.limit(off + len);
            int bytesRead = this.readableChannel.read(buff);
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: Given buffer length {2} bytes, actually read {3} bytes", this.getClass().getName(), this.hashCode(), len, bytesRead);
            }

            return bytesRead;
         }
      } else {
         String msg = MessageFormat.format("Offset={0}, Length={1}, ArrayLength={2}", off, len, b.length);
         throw new IndexOutOfBoundsException(msg);
      }
   }

   public int available() throws IOException {
      if (this.readableChannel instanceof JaApplicationReadableByteChannel) {
         JaApplicationReadableByteChannel appInChannel = (JaApplicationReadableByteChannel)this.readableChannel;
         return appInChannel.available();
      } else {
         if (this.readableChannel instanceof SocketChannel) {
            SocketChannel socketChannel = (SocketChannel)this.readableChannel;
            Socket socket = socketChannel.socket();
            if (null != socket) {
               InputStream is = socket.getInputStream();
               if (null != is) {
                  return is.available();
               }
            }
         }

         return 0;
      }
   }

   public void close() throws IOException {
      this.readableChannel.close();
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   JaChannelInputStream(ReadableByteChannel readableChannel) {
      if (null == readableChannel) {
         throw new IllegalArgumentException("Expected non-null ReadableByteChannel.");
      } else {
         this.readableChannel = readableChannel;
      }
   }

   void setSelectableChannel(AbstractSelectableChannel selectableChannel) {
      if (null == selectableChannel) {
         throw new IllegalArgumentException("Expected non-null AbstractSelectableChannel.");
      } else {
         this.selectableChannel = selectableChannel;
      }
   }
}
