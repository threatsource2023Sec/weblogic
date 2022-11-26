package org.glassfish.tyrus.spi;

import java.util.List;
import javax.websocket.HandshakeResponse;

public abstract class UpgradeResponse implements HandshakeResponse {
   public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
   public static final String LOCATION = "Location";
   public static final String RETRY_AFTER = "Retry-After";
   public static final String TRACING_HEADER_PREFIX = "X-Tyrus-Tracing-";

   public abstract int getStatus();

   public abstract void setStatus(int var1);

   public abstract void setReasonPhrase(String var1);

   public final String getFirstHeaderValue(String name) {
      List stringList = (List)this.getHeaders().get(name);
      return stringList == null ? null : (stringList.size() > 0 ? (String)stringList.get(0) : null);
   }
}
