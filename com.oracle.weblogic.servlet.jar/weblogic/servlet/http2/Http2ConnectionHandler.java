package weblogic.servlet.http2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import weblogic.servlet.HTTPTextTextFormatter;
import weblogic.servlet.internal.AbstractHttpConnectionHandler;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.HttpSocket;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.utils.http.RequestParser;
import weblogic.utils.io.NullInputStream;
import weblogic.work.WorkManagerFactory;

public class Http2ConnectionHandler extends AbstractHttpConnectionHandler {
   private InputStream in;
   private OutputStream out;
   private StreamImpl stream;

   public Http2ConnectionHandler(HttpSocket httpSocket, OutputStream out, StreamImpl stream, boolean secure) {
      super(httpSocket, secure);
      this.request = new ServletRequestImpl(this);
      this.response = new ServletResponseImpl(this.request, out);
      this.reuseRequestResponse = false;
      this.out = out;
      this.stream = stream;
   }

   public RequestParser createRequestParser() {
      return new Http2RequestParser();
   }

   public InputStream getInputStream() {
      return this.in;
   }

   protected void initInputStream() throws IOException {
      if (this.request.getInputHelper().getRequestParser().isMethodSafe()) {
         this.request.setInputStream((InputStream)NullInputStream.getInstance());
      } else {
         this.request.setInputStream(this.stream.getInputStream());
      }
   }

   public void sendError(final ServletResponseImpl res, final int code) {
      Runnable runnable = new Runnable() {
         public void run() {
            try {
               res.sendError(code);
               Http2ConnectionHandler.this.httpServer.getLogManager().log(Http2ConnectionHandler.this.request, Http2ConnectionHandler.this.response);
               Http2ConnectionHandler.this.stream.reset(0);
            } catch (Throwable var2) {
            }

         }
      };
      WorkManagerFactory.getInstance().getDefault().schedule(runnable);
   }

   public Stream getStream() {
      return this.stream;
   }

   public void requeue() {
   }

   protected boolean needToUpgrade() {
      return false;
   }

   protected void send400Response(Exception ex) throws IOException {
   }

   public void sendRefreshPage(String uri, int progressIndicator) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Sending refresh screen for on demand deploy.");
      }

      char[] progress = new char[progressIndicator];

      for(int i = 0; i < progressIndicator; ++i) {
         progress[i] = '.';
      }

      HTTPTextTextFormatter formatter = new HTTPTextTextFormatter(this.request.getLocale());
      String refreshPage = formatter.getRefreshPageHTML(uri, new String(progress));
      ServletOutputStream out = this.response.getOutputStream();
      this.response.setContentLength(refreshPage.length());
      out.write(refreshPage.getBytes());
      out.flush();
   }
}
