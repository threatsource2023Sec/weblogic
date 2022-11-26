package org.glassfish.tyrus.core.coder;

import java.io.Reader;
import java.io.StringReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;

public class ReaderDecoder extends CoderAdapter implements Decoder.Text {
   public boolean willDecode(String s) {
      return true;
   }

   public Reader decode(String s) throws DecodeException {
      return new StringReader(s);
   }
}
