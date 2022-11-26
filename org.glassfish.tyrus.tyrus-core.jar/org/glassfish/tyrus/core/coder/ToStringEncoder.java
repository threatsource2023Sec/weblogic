package org.glassfish.tyrus.core.coder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;

public class ToStringEncoder extends CoderAdapter implements Encoder.Text {
   public String encode(Object object) throws EncodeException {
      return object.toString();
   }
}
