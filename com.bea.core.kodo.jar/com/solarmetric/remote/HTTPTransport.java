package com.solarmetric.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.openjpa.lib.util.Localizer;

public class HTTPTransport implements Transport {
   private static final Localizer _loc = Localizer.forPackage(HTTPTransport.class);
   private String _url = null;

   public String getURL() {
      return this._url;
   }

   public void setURL(String url) {
      this._url = url;
   }

   public Transport.Server getServer() throws Exception {
      return null;
   }

   public Transport.Channel getClientChannel() throws Exception {
      if (this._url == null) {
         throw new IllegalStateException(_loc.get("no-url").getMessage());
      } else {
         return new HTTPClientChannel(this._url);
      }
   }

   public Transport.Channel getServletChannel(HttpServletRequest req, HttpServletResponse resp) {
      return new ServletChannel(req, resp);
   }

   public void close() {
   }

   private static class HTTPClientChannel implements Transport.Channel {
      private final URL _url;
      private URLConnection _conn = null;

      public HTTPClientChannel(String url) throws Exception {
         this._url = new URL(url);
      }

      public OutputStream getOutput() throws Exception {
         try {
            this._conn = this._url.openConnection();
            this._conn.setDoOutput(true);
            return this._conn.getOutputStream();
         } catch (Exception var2) {
            throw new TransportException(HTTPTransport._loc.get("bad-http-output", this._url), var2);
         }
      }

      public InputStream getInput() throws Exception {
         try {
            return this._conn.getInputStream();
         } catch (Exception var2) {
            throw new TransportException(HTTPTransport._loc.get("bad-http-input", this._url), var2);
         }
      }

      public void error(IOException ioe) {
      }

      public void close() throws Exception {
      }
   }

   private static class ServletResponseOutputStream extends OutputStream {
      private final HttpServletResponse _resp;
      private final ByteArrayOutputStream _bytes;

      public ServletResponseOutputStream(HttpServletResponse resp, ByteArrayOutputStream bytes) throws IOException {
         this._resp = resp;
         this._bytes = bytes;
      }

      public void write(byte[] b) throws IOException {
         this._bytes.write(b);
      }

      public void write(byte[] b, int off, int len) throws IOException {
         this._bytes.write(b, off, len);
      }

      public void write(int b) throws IOException {
         this._bytes.write(b);
      }

      public void close() throws IOException {
         this._resp.setContentLength(this._bytes.size());
         OutputStream out = this._resp.getOutputStream();
         this._bytes.writeTo(out);
         out.flush();
      }
   }

   private static class ServletChannel implements Transport.Channel {
      private final HttpServletRequest _req;
      private final HttpServletResponse _resp;

      public ServletChannel(HttpServletRequest req, HttpServletResponse resp) {
         this._req = req;
         this._resp = resp;
      }

      public InputStream getInput() throws Exception {
         return this._req.getInputStream();
      }

      public OutputStream getOutput() throws Exception {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         return new ServletResponseOutputStream(this._resp, bytes);
      }

      public void error(IOException ioe) {
      }

      public void close() {
      }
   }
}
