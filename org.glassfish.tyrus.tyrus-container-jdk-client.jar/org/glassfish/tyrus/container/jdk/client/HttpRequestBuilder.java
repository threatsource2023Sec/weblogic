package org.glassfish.tyrus.container.jdk.client;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class HttpRequestBuilder {
   private static final String ENCODING = "ISO-8859-1";
   private static final String LINE_SEPARATOR = "\r\n";
   private static final String HTTP_VERSION = "HTTP/1.1";

   private static void appendUpgradeHeaders(StringBuilder request, JdkUpgradeRequest upgradeRequest) {
      Iterator var2 = upgradeRequest.getHeaders().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry header = (Map.Entry)var2.next();
         StringBuilder value = new StringBuilder();

         String valuePart;
         for(Iterator var5 = ((List)header.getValue()).iterator(); var5.hasNext(); value.append(valuePart)) {
            valuePart = (String)var5.next();
            if (value.length() != 0) {
               value.append(", ");
            }
         }

         appendHeader(request, (String)header.getKey(), value.toString());
      }

   }

   private static void appendHeader(StringBuilder request, String key, String value) {
      request.append(key);
      request.append(":");
      request.append(value);
      request.append("\r\n");
   }

   static ByteBuffer build(JdkUpgradeRequest upgradeRequest) {
      StringBuilder request = new StringBuilder();
      request.append(upgradeRequest.getHttpMethod());
      request.append(" ");
      request.append(upgradeRequest.getRequestUri());
      request.append(" ");
      request.append("HTTP/1.1");
      request.append("\r\n");
      appendUpgradeHeaders(request, upgradeRequest);
      request.append("\r\n");
      String requestStr = request.toString();
      byte[] bytes = requestStr.getBytes(Charset.forName("ISO-8859-1"));
      return ByteBuffer.wrap(bytes);
   }
}
