package org.glassfish.tyrus.core.coder;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;

public class NoOpTextCoder extends CoderAdapter implements Decoder.Text, Encoder.Text {
   public boolean willDecode(String s) {
      return true;
   }

   public String decode(String s) throws DecodeException {
      return s;
   }

   public String encode(String object) throws EncodeException {
      return object;
   }
}
