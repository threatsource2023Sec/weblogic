package weblogic.websocket.tyrus;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.Writer;

public class TyrusServletWriter extends Writer {
   private final TyrusMuxableWebSocket socket;
   private final boolean isProtected;
   private final CloseListener closeListener;
   private Connection connection;
   private boolean closed = false;

   TyrusServletWriter(TyrusMuxableWebSocket socket, CloseListener closeListener, boolean isProtected) {
      this.socket = socket;
      this.closeListener = closeListener;
      this.isProtected = isProtected;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
      if (this.closed) {
         try {
            this.close();
         } catch (IOException var3) {
         }
      }

   }

   public void close() throws IOException {
      this.closed = true;
      if (this.connection != null) {
         if (this.closeListener != null) {
            this.closeListener.onClose(this.connection);
         }

         this.socket.shutdownSocket();
      }

   }

   public boolean isProtected() {
      return this.isProtected;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TyrusServletWriter that = (TyrusServletWriter)o;
         return this.socket.equals(that.socket);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.socket.hashCode();
   }

   public void write(ByteBuffer buffer, CompletionHandler completionHandler) {
      try {
         synchronized(this.socket) {
            this.socket.sendRawData(buffer.array());
         }

         if (completionHandler != null) {
            completionHandler.completed(buffer);
         }
      } catch (IOException var6) {
         if (completionHandler != null) {
            completionHandler.failed(var6);
         }

         if (this.closeListener != null) {
            this.closeListener.onClose(this.connection);
         }

         this.socket.shutdownSocket(var6);
      }

   }

   public interface CloseListener {
      void onClose(Connection var1);
   }
}
