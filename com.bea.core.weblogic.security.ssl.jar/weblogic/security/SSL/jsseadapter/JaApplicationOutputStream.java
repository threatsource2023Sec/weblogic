package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.text.MessageFormat;
import java.util.logging.Level;

final class JaApplicationOutputStream extends OutputStream {
   private final JaAbstractSSLSocket socket;

   public void write(int b) throws IOException {
      byte[] data = new byte[]{(byte)b};
      this.write(data, 0, 1);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (null == b) {
         throw new NullPointerException("Expected non-null array.");
      } else if (off >= 0 && len >= 0 && off + len <= b.length) {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: Given {2} bytes to write.", this.getClass().getName(), this.hashCode(), len);
         }

         if (0 != len) {
            ByteBuffer buff = ByteBuffer.wrap(b, off, len);
            this.socket.getSslEngineRunnerContext().getSync().lock(JaSSLEngineSynchronizer.LockState.OUTBOUND);

            int cumulativeBytesWritten;
            try {
               WritableByteChannel appWriteChannel = this.socket.getWritableByteChannel();
               int bytesWritten = appWriteChannel.write(buff);
               cumulativeBytesWritten = bytesWritten;
               if (buff.hasRemaining()) {
                  if (JaLogger.isLoggable(Level.FINEST)) {
                     JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: Last write(1): Written bytes={2}, Remaining bytes={3}.", this.getClass().getName(), this.hashCode(), bytesWritten, buff.remaining());
                  }

                  while(buff.hasRemaining()) {
                     Thread.currentThread();
                     Thread.yield();
                     bytesWritten = appWriteChannel.write(buff);
                     cumulativeBytesWritten += bytesWritten;
                     if (JaLogger.isLoggable(Level.FINEST)) {
                        JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: Last write(2): Written bytes={2}, Remaining bytes={3}.", this.getClass().getName(), this.hashCode(), bytesWritten, buff.remaining());
                     }
                  }
               }
            } finally {
               this.socket.getSslEngineRunnerContext().getSync().unlock();
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: All bytes written: Total written bytes={2}, Remaining bytes={3}", this.getClass().getName(), this.hashCode(), cumulativeBytesWritten, buff.remaining());
            }

         }
      } else {
         String msg = MessageFormat.format("Offset={0}, Length={1}, ArrayLength={2}", off, len, b.length);
         throw new IndexOutOfBoundsException(msg);
      }
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLSOCKET, "{0}[{1}]: close() called.", this.getClass().getName(), this.hashCode());
      }

      this.socket.shutdownOutput();
   }

   JaApplicationOutputStream(JaAbstractSSLSocket socket) {
      if (null == socket) {
         throw new IllegalArgumentException("Expected non-null JaAbstractSSLSocket.");
      } else {
         this.socket = socket;
      }
   }
}
