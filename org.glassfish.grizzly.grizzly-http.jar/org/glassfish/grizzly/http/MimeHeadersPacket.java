package org.glassfish.grizzly.http;

import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeHeaders;

public interface MimeHeadersPacket {
   MimeHeaders getHeaders();

   String getHeader(String var1);

   String getHeader(Header var1);

   void setHeader(String var1, String var2);

   void setHeader(String var1, HeaderValue var2);

   void setHeader(Header var1, String var2);

   void setHeader(Header var1, HeaderValue var2);

   void addHeader(String var1, String var2);

   void addHeader(String var1, HeaderValue var2);

   void addHeader(Header var1, String var2);

   void addHeader(Header var1, HeaderValue var2);

   boolean containsHeader(String var1);

   boolean containsHeader(Header var1);
}
