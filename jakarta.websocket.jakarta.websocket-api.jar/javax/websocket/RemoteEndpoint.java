package javax.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

public interface RemoteEndpoint {
   void setBatchingAllowed(boolean var1) throws IOException;

   boolean getBatchingAllowed();

   void flushBatch() throws IOException;

   void sendPing(ByteBuffer var1) throws IOException, IllegalArgumentException;

   void sendPong(ByteBuffer var1) throws IOException, IllegalArgumentException;

   public interface Basic extends RemoteEndpoint {
      void sendText(String var1) throws IOException;

      void sendBinary(ByteBuffer var1) throws IOException;

      void sendText(String var1, boolean var2) throws IOException;

      void sendBinary(ByteBuffer var1, boolean var2) throws IOException;

      OutputStream getSendStream() throws IOException;

      Writer getSendWriter() throws IOException;

      void sendObject(Object var1) throws IOException, EncodeException;
   }

   public interface Async extends RemoteEndpoint {
      long getSendTimeout();

      void setSendTimeout(long var1);

      void sendText(String var1, SendHandler var2);

      Future sendText(String var1);

      Future sendBinary(ByteBuffer var1);

      void sendBinary(ByteBuffer var1, SendHandler var2);

      Future sendObject(Object var1);

      void sendObject(Object var1, SendHandler var2);
   }
}
