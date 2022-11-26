package weblogic.servlet.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.StringUtils;
import weblogic.utils.http.HttpReasonPhraseCoder;
import weblogic.utils.http.HttpRequestParser;
import weblogic.utils.io.ChunkedDataOutputStream;

final class ResponseHeaders extends AbstractResponseHeaders {
   private static final String HTTP_VERSION_1_0 = "HTTP/1.0";
   private static final String HTTP_VERSION_1_1 = "HTTP/1.1";
   private static final String STATUS_OK_HEADER_1_0 = "HTTP/1.0 200 " + HttpReasonPhraseCoder.getReasonPhrase(200) + "\r\n";
   private static final String STATUS_OK_HEADER_1_1 = "HTTP/1.1 200 " + HttpReasonPhraseCoder.getReasonPhrase(200) + "\r\n";

   public ResponseHeaders(ServletResponseImpl response) {
      this.response = response;
      this.setEncoding(SYSTEM_ENCODING);
   }

   protected int writeHeaders(ServletOutputStreamImpl sos, String msg) throws IOException {
      return this.writeHeadersInternal(sos, this.buildFirstLine(msg));
   }

   private int writeHeadersInternal(ServletOutputStreamImpl sos, String firstLine) throws IOException {
      ChunkedDataOutputStream cdos = new ChunkedDataOutputStream() {
         protected void getBytes(String str, int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
            StringUtils.getBytes(str, srcBegin, srcEnd, dst, dstBegin);
         }
      };
      cdos.writeBytes(firstLine);

      int i;
      for(i = 0; i < 15; ++i) {
         Object value = this.values[i];
         if (value != null) {
            if (this.isDuplicateStdHeaderAllowed(i)) {
               this.writeStandardDuplicateHeader(cdos, i, value);
            } else {
               cdos.write(standardHeadersAsBytes[i]);
               if (i == 6) {
                  cdos.write(((String)value).getBytes(HttpRequestParser.getURIDecodeEncoding()));
                  cdos.write(CRLF);
               } else if (i == 12) {
                  this.writeContentDisposition(cdos, "Content-Disposition", value);
               } else {
                  cdos.writeBytes((String)value);
                  cdos.write(CRLF);
               }
            }
         }
      }

      if (this.genericHeaders != null) {
         Iterator i = this.genericHeaders.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            String name = (String)entry.getKey();
            cdos.writeBytes(name);
            cdos.write(COLON_SPACE);
            writeHeaderValue(cdos, name, entry.getValue());
         }
      }

      cdos.write(CRLF);
      i = cdos.getSize();
      sos.writeHeader(cdos);
      return i;
   }

   private void writeStandardDuplicateHeader(ChunkedDataOutputStream cdos, int offset, Object value) throws IOException {
      if (!(value instanceof String[])) {
         cdos.write(standardHeadersAsBytes[offset]);
         cdos.writeBytes(value.toString());
         cdos.write(CRLF);
      } else {
         String[] headerValues = (String[])((String[])value);

         for(int i = 0; i < headerValues.length; ++i) {
            cdos.write(standardHeadersAsBytes[offset]);
            cdos.writeBytes(headerValues[i]);
            cdos.write(CRLF);
         }

      }
   }

   private void writeContentDisposition(ChunkedDataOutputStream cdos, String name, Object value) throws IOException, UnsupportedEncodingException {
      HttpServer httpServer;
      if (this.response.getContext() == null) {
         httpServer = WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer();
      } else {
         httpServer = this.response.getContext().getServer();
      }

      if (httpServer.getMBean().isUseHeaderEncoding()) {
         if (value instanceof String) {
            this.writeEncodedString(cdos, (String)value);
            cdos.write(CRLF);
         } else {
            String[] vals = (String[])((String[])value);

            for(int j = 0; j < vals.length; ++j) {
               if (j > 0) {
                  cdos.writeBytes(name);
                  cdos.write(COLON_SPACE);
               }

               this.writeEncodedString(cdos, vals[j]);
               cdos.write(CRLF);
            }
         }
      } else {
         writeHeaderValue(cdos, name, value);
      }

   }

   private static void writeHeaderValue(ChunkedDataOutputStream cdos, String name, Object value) throws IOException {
      if (value == null) {
         cdos.write(CRLF);
      } else if (value instanceof String) {
         cdos.writeBytes((String)value);
         cdos.write(CRLF);
      } else {
         String[] vals = (String[])((String[])value);

         for(int j = 0; j < vals.length; ++j) {
            if (j > 0) {
               cdos.writeBytes(name);
               cdos.write(COLON_SPACE);
            }

            cdos.writeBytes(vals[j]);
            cdos.write(CRLF);
         }
      }

   }

   private void writeEncodedString(ChunkedDataOutputStream cdos, String s) throws UnsupportedEncodingException, IOException {
      if (this.encoding == null) {
         cdos.writeBytes(s);
      } else {
         cdos.write(s.getBytes(this.encoding));
      }

   }

   protected String getResponseInfo(String msg) {
      return this.buildFirstLine(msg);
   }

   private final String buildFirstLine(String statusMessage) {
      ServletRequestImpl request = this.response.getRequest();
      String firstLine;
      if (this.response.getStatus() == 200 && (statusMessage == null || "OK".equals(statusMessage))) {
         if (request != null && !request.getInputHelper().getRequestParser().isProtocolVersion_1_1() && !this.useHighestCompatibleHttpVersion()) {
            firstLine = STATUS_OK_HEADER_1_0;
         } else {
            firstLine = STATUS_OK_HEADER_1_1;
         }
      } else {
         String httpVersion;
         if (request != null && !request.getInputHelper().getRequestParser().isProtocolVersion_1_1() && !this.useHighestCompatibleHttpVersion()) {
            httpVersion = "HTTP/1.0";
         } else {
            httpVersion = "HTTP/1.1";
         }

         firstLine = httpVersion + ' ' + this.response.getStatus() + ' ' + statusMessage + "\r\n";
      }

      return firstLine;
   }

   private boolean useHighestCompatibleHttpVersion() {
      return this.response.getHttpServer().getMBean().isUseHighestCompatibleHTTPVersion();
   }
}
