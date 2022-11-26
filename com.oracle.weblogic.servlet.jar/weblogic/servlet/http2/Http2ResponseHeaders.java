package weblogic.servlet.http2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.servlet.http2.hpack.HeaderEntry;
import weblogic.servlet.internal.AbstractResponseHeaders;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.http.HttpRequestParser;

public class Http2ResponseHeaders extends AbstractResponseHeaders {
   private static final String[] STANDARD_HEADERS_LOWER = new String[]{"Cache-Control".toLowerCase(), "Connection".toLowerCase(), "Date".toLowerCase(), "Pragma".toLowerCase(), "Transfer-Encoding".toLowerCase(), "Accept-Ranges".toLowerCase(), "Location".toLowerCase(), "Server".toLowerCase(), "Content-Length".toLowerCase(), "Content-Type".toLowerCase(), "Expires".toLowerCase(), "Last-Modified".toLowerCase(), "Content-Disposition".toLowerCase(), "WL-Result".toLowerCase()};

   public Http2ResponseHeaders(ServletResponseImpl response) {
      this.response = response;
      response.disableKeepAlive();
      this.setEncoding(SYSTEM_ENCODING);
   }

   protected int writeHeaders(ServletOutputStreamImpl sos, String msg) throws IOException {
      Stream stream = this.response.getRequest().getConnection().getConnectionHandler().getStream();
      List headers = new ArrayList();
      headers.add(new HeaderEntry(":status", String.valueOf(this.response.getStatus()).getBytes()));

      Object value;
      for(int i = 0; i < 15; ++i) {
         value = this.values[i];
         if (value != null) {
            if (this.isDuplicateStdHeaderAllowed(i)) {
               this.writeStandardDuplicateHeader(headers, i, value);
            } else if (i == 6) {
               headers.add(new HeaderEntry(STANDARD_HEADERS_LOWER[i], ((String)value).getBytes(HttpRequestParser.getURIDecodeEncoding())));
            } else if (i == 12) {
               this.writeContentDisposition(headers, STANDARD_HEADERS_LOWER[i], value);
            } else {
               headers.add(new HeaderEntry(STANDARD_HEADERS_LOWER[i], ((String)value).getBytes()));
            }
         }
      }

      if (this.genericHeaders != null) {
         Iterator i = this.genericHeaders.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            writeHeaderValue(headers, ((String)entry.getKey()).toLowerCase(), entry.getValue());
         }
      }

      boolean endOfStream = false;
      value = this.values[8];
      if (value != null && Integer.parseInt((String)value) <= 0) {
         endOfStream = true;
      }

      return stream.sendHeaders(headers, endOfStream);
   }

   protected String getResponseInfo(String msg) {
      return "HTTP/2 " + this.response.getStatus() + (msg != null ? msg : "") + "\r\n";
   }

   private void writeStandardDuplicateHeader(List headers, int offset, Object value) throws IOException {
      if (value instanceof String[]) {
         String[] headerValues = (String[])((String[])value);

         for(int i = 0; i < headerValues.length; ++i) {
            headers.add(new HeaderEntry(STANDARD_HEADERS_LOWER[offset], headerValues[i].getBytes()));
         }
      } else {
         headers.add(new HeaderEntry(STANDARD_HEADERS_LOWER[offset], value.toString().getBytes()));
      }

   }

   private void writeContentDisposition(List headers, String name, Object value) throws UnsupportedEncodingException {
      HttpServer httpServer;
      if (this.response.getContext() == null) {
         httpServer = WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer();
      } else {
         httpServer = this.response.getContext().getServer();
      }

      if (httpServer.getMBean().isUseHeaderEncoding()) {
         if (value instanceof String) {
            headers.add(new HeaderEntry(name, this.getDecodedBytes((String)value)));
         } else {
            String[] vals = (String[])((String[])value);

            for(int j = 0; j < vals.length; ++j) {
               headers.add(new HeaderEntry(name, this.getDecodedBytes(vals[j])));
            }
         }
      } else {
         writeHeaderValue(headers, name, value);
      }

   }

   private byte[] getDecodedBytes(String s) throws UnsupportedEncodingException {
      return this.encoding == null ? s.getBytes() : s.getBytes(this.encoding);
   }

   private static void writeHeaderValue(List headers, String name, Object value) {
      if (value == null) {
         headers.add(new HeaderEntry(name, (byte[])null));
      } else if (value instanceof String) {
         headers.add(new HeaderEntry(name, ((String)value).getBytes()));
      } else {
         String[] vals = (String[])((String[])value);

         for(int j = 0; j < vals.length; ++j) {
            headers.add(new HeaderEntry(name, vals[j].getBytes()));
         }
      }

   }
}
