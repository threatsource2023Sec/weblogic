package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Cacheable;

public abstract class HttpPacket implements Cacheable {
   public static boolean isHttp(Object packet) {
      return HttpPacket.class.isAssignableFrom(packet.getClass());
   }

   public abstract boolean isHeader();

   public abstract HttpHeader getHttpHeader();
}
