package org.glassfish.tyrus.core.coder;

import java.nio.ByteBuffer;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;

public class NoOpByteArrayCoder extends CoderAdapter implements Decoder.Binary, Encoder.Binary {
   public ByteBuffer encode(byte[] object) throws EncodeException {
      return ByteBuffer.wrap(object);
   }

   public boolean willDecode(ByteBuffer bytes) {
      return true;
   }

   public byte[] decode(ByteBuffer bytes) throws DecodeException {
      return bytes.array();
   }
}
