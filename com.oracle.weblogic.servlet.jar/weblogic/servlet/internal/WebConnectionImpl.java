package weblogic.servlet.internal;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;

public class WebConnectionImpl implements WebConnection {
   private ServletInputStreamImpl inputStream;
   private ServletOutputStreamImpl outputStream;
   private HttpUpgradeHandler httpUpgradeHandler;
   private final AtomicBoolean isClosed = new AtomicBoolean();

   public WebConnectionImpl(ServletInputStreamImpl inputStream, ServletOutputStreamImpl outputStream, HttpUpgradeHandler httpUpgradeHandler) {
      this.inputStream = inputStream;
      inputStream.setWebConnection(this);
      this.outputStream = outputStream;
      outputStream.setWebConnection(this);
      outputStream.setUpgradeMode(true);
      this.httpUpgradeHandler = httpUpgradeHandler;
   }

   public void close() throws Exception {
      if (this.isClosed.compareAndSet(false, true)) {
         this.httpUpgradeHandler.destroy();
         this.inputStream.close();
         this.outputStream.close();
         this.inputStream = null;
         this.outputStream = null;
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("WebConnectionImpl closed: " + this);
         }
      }

   }

   public ServletInputStream getInputStream() throws IOException {
      return this.inputStream;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      return this.outputStream;
   }
}
