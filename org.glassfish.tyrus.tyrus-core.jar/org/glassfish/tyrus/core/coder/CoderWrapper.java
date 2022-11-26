package org.glassfish.tyrus.core.coder;

import javax.websocket.Decoder;
import javax.websocket.Encoder;

public class CoderWrapper extends CoderAdapter implements Decoder, Encoder {
   private final Class coderClass;
   private final Object coder;
   private final Class type;

   public CoderWrapper(Class coderClass, Class type) {
      this.coderClass = coderClass;
      this.coder = null;
      this.type = type;
   }

   public CoderWrapper(Object coder, Class type) {
      this.coder = coder;
      this.coderClass = coder.getClass();
      this.type = type;
   }

   public Class getType() {
      return this.type;
   }

   public Class getCoderClass() {
      return this.coderClass;
   }

   public Object getCoder() {
      return this.coder;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("CoderWrapper");
      sb.append("{coderClass=").append(this.coderClass);
      sb.append(", coder=").append(this.coder);
      sb.append(", type=").append(this.type);
      sb.append('}');
      return sb.toString();
   }
}
