package javax.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

public interface Encoder {
   void init(EndpointConfig var1);

   void destroy();

   public interface BinaryStream extends Encoder {
      void encode(Object var1, OutputStream var2) throws EncodeException, IOException;
   }

   public interface Binary extends Encoder {
      ByteBuffer encode(Object var1) throws EncodeException;
   }

   public interface TextStream extends Encoder {
      void encode(Object var1, Writer var2) throws EncodeException, IOException;
   }

   public interface Text extends Encoder {
      String encode(Object var1) throws EncodeException;
   }
}
