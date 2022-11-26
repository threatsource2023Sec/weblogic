package org.glassfish.tyrus.core.coder;

import java.nio.ByteBuffer;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;

public class NoOpByteBufferCoder extends CoderAdapter implements Decoder.Binary, Encoder.Binary {
   public boolean willDecode(ByteBuffer bytes) {
      return true;
   }

   public ByteBuffer decode(ByteBuffer bytes) throws DecodeException {
      return bytes;
   }

   public ByteBuffer encode(ByteBuffer object) throws EncodeException {
      return object;
   }
}
