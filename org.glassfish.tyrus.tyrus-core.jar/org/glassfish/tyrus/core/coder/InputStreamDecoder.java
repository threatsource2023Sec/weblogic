package org.glassfish.tyrus.core.coder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;

public class InputStreamDecoder extends CoderAdapter implements Decoder.Binary {
   public boolean willDecode(ByteBuffer bytes) {
      return true;
   }

   public InputStream decode(ByteBuffer bytes) throws DecodeException {
      return new ByteArrayInputStream(bytes.array());
   }
}
