package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import org.glassfish.grizzly.http.server.Request;

public class WebConnectionImpl implements WebConnection {
   private final ServletInputStream inputStream;
   private final ServletOutputStream outputStream;
   private final HttpServletRequestImpl request;
   private final AtomicBoolean isClosed = new AtomicBoolean();

   public WebConnectionImpl(HttpServletRequestImpl request, ServletInputStream inputStream, ServletOutputStream outputStream) {
      this.request = request;
      this.inputStream = inputStream;
      this.outputStream = outputStream;
   }

   public ServletInputStream getInputStream() throws IOException {
      return this.inputStream;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      return this.outputStream;
   }

   public void close() throws Exception {
      if (this.isClosed.compareAndSet(false, true)) {
         Request grizzlyRequest = this.request.getRequest();
         HttpUpgradeHandler httpUpgradeHandler = this.request.getHttpUpgradeHandler();

         try {
            httpUpgradeHandler.destroy();
         } finally {
            try {
               this.inputStream.close();
            } catch (Exception var12) {
            }

            try {
               this.outputStream.close();
            } catch (Exception var11) {
            }

            grizzlyRequest.getResponse().resume();
         }
      }

   }
}
