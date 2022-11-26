package javax.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

public interface Decoder {
   void init(EndpointConfig var1);

   void destroy();

   public interface TextStream extends Decoder {
      Object decode(Reader var1) throws DecodeException, IOException;
   }

   public interface Text extends Decoder {
      Object decode(String var1) throws DecodeException;

      boolean willDecode(String var1);
   }

   public interface BinaryStream extends Decoder {
      Object decode(InputStream var1) throws DecodeException, IOException;
   }

   public interface Binary extends Decoder {
      Object decode(ByteBuffer var1) throws DecodeException;

      boolean willDecode(ByteBuffer var1);
   }
}
