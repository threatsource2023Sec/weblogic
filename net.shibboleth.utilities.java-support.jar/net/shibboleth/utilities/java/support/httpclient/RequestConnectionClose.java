package net.shibboleth.utilities.java.support.httpclient;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.protocol.HttpContext;

public class RequestConnectionClose implements HttpRequestInterceptor {
   public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
      if (HttpVersion.HTTP_1_1.equals(request.getProtocolVersion())) {
         request.addHeader("Connection", "close");
      }

   }
}
