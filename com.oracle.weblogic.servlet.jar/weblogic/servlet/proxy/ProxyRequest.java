package weblogic.servlet.proxy;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import weblogic.servlet.FutureServletResponse;
import weblogic.utils.Debug;
import weblogic.utils.io.DataIO;
import weblogic.work.WorkAdapter;

final class ProxyRequest extends WorkAdapter {
   private static final boolean DEBUG = true;
   private static final String CONTENT_LENGTH_HEADER = "Content-Length: ";
   private static final int CONTENT_LENGTH_HEADER_INDEX = "Content-Length: ".length();
   private final SocketConnResource con;
   private final HttpServletRequest request;
   private final FutureServletResponse response;
   private final byte[] bytes;
   private final PrintStream out;
   private final PushbackInputStream in;
   private final Cookie[] cookies;

   ProxyRequest(SocketConnResource con, HttpServletRequest req, FutureServletResponse response) throws IOException {
      this(con, req, response, (Cookie[])null);
   }

   ProxyRequest(SocketConnResource con, HttpServletRequest req, FutureServletResponse response, Cookie[] cookies) throws IOException {
      this.con = con;
      this.request = req;
      this.response = response;
      this.bytes = this.createRequest();
      this.out = con.getOutputStream();
      this.in = con.getInputStream();
      this.cookies = cookies;
   }

   byte[] getBackendRequest() {
      return this.bytes;
   }

   public void run() {
      this.out.write(this.bytes, 0, this.bytes.length);
      StringBuilder sb = new StringBuilder();
      String line = null;
      int contentLength = -1;
      boolean closeConnection = false;

      try {
         line = ProxyUtils.readHTTPHeader(this.in);

         while(line != null) {
            sb.append(line);
            if (line.length() == 0) {
               break;
            }

            if (contentLength == -1) {
               int index = line.indexOf("Content-Length: ");
               if (index > -1) {
                  contentLength = Integer.parseInt(line.substring(CONTENT_LENGTH_HEADER_INDEX).trim());
               }
            }
         }

         byte[] responseHdrs = sb.toString().getBytes();
         if (contentLength > 0) {
            this.sendResponseWithBody(contentLength, responseHdrs);
            return;
         }

         this.response.getOutputStream().write(responseHdrs);

         for(int chunkSize = ProxyUtils.readChunkSize(this.in); chunkSize > 0; chunkSize = ProxyUtils.readChunkSize(this.in)) {
            byte[] chunk = new byte[chunkSize + 2];
            this.in.read(chunk, 0, chunkSize + 2);
            this.sendResponse(chunk);
         }

         this.response.send();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }

   private void sendResponseWithBody(int contentLength, byte[] responseHdrs) throws IOException {
      int totalResponseSize = responseHdrs.length + contentLength;
      byte[] completeResponse = new byte[totalResponseSize];
      System.arraycopy(responseHdrs, 0, completeResponse, 0, responseHdrs.length);
      this.in.read(completeResponse, responseHdrs.length, contentLength);
      this.sendResponse(completeResponse);
      this.response.send();
   }

   private void sendResponse(byte[] b) {
      try {
         this.response.getOutputStream().write(b);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   private byte[] createRequest() {
      if (this.request.getMethod() == "GET") {
         return this.createGetRequest();
      } else if (this.request.getMethod() == "POST") {
         return this.createPostRequest();
      } else {
         return this.request.getMethod() == "HEAD" ? this.createHeadRequest() : null;
      }
   }

   private byte[] createHeadRequest() {
      StringBuilder sb = new StringBuilder();
      sb.append("HEAD ");
      sb.append(this.request.getRequestURI());
      sb.append("HTTP/1.1\r\n");
      sb.append("Content-Length: 0\r\n");
      sb.append("Connection: Keep-Alive\r\n");
      sb.append(this.con.getHost());
      sb.append("\r\n\r\n");
      Debug.say("HEAD REQUEST " + sb.toString());
      return sb.toString().getBytes();
   }

   private byte[] createGetRequest() {
      StringBuilder sb = new StringBuilder();
      sb.append("GET ");
      sb.append(this.request.getRequestURI());
      sb.append(" HTTP/1.1\r\n");
      sb.append(" Content-Length: 0\r\n");
      Cookie[] cookies = this.request.getCookies();
      if (cookies != null) {
         this.addCookies(sb, cookies);
      }

      sb.append("Connection: Keep-Alive\r\n");
      sb.append(this.con.getHost());
      sb.append("\r\n\r\n");
      Debug.say("GET REQUEST " + sb.toString());
      return sb.toString().getBytes();
   }

   private void addCookies(StringBuilder sb, Cookie[] cookies) {
      sb.append("Cookie: ");

      for(int i = 0; i < cookies.length; ++i) {
         sb.append(cookies[i].getName());
         sb.append("=");
         sb.append(cookies[i].getValue());
         sb.append(";");
      }

      sb.append("\r\n");
   }

   private byte[] createPostRequest() {
      int contentLength = this.request.getContentLength();
      StringBuilder sb = new StringBuilder();
      sb.append("POST ");
      sb.append(this.request.getRequestURI());
      sb.append(" HTTP/1.1\r\n");
      sb.append("Content-Length: ");
      sb.append(contentLength);
      sb.append("\r\n");
      Cookie[] cookies = this.request.getCookies();
      if (cookies != null) {
         this.addCookies(sb, cookies);
      }

      sb.append("Connection: Keep-Alive\r\n");
      sb.append(this.con.getHost());
      sb.append("\r\n\r\n");
      Debug.say("POST REQUEST" + sb.toString());
      byte[] requestHeaders = sb.toString().getBytes();
      byte[] completeRequest = new byte[contentLength + requestHeaders.length];
      System.arraycopy(requestHeaders, 0, completeRequest, 0, requestHeaders.length);

      try {
         DataIO.readFully(this.request.getInputStream(), completeRequest, requestHeaders.length, contentLength);
         return completeRequest;
      } catch (IOException var7) {
         throw new AssertionError("Unexpected exception");
      }
   }
}
