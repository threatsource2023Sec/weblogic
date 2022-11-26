package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import javax.servlet.ServletOutputStream;
import weblogic.servlet.HTTPTextTextFormatter;
import weblogic.servlet.http2.HTTP2Exception;
import weblogic.servlet.http2.MuxableSocketHTTP2;
import weblogic.servlet.http2.Stream;
import weblogic.servlet.utils.Base64;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.utils.http.HttpChunkInputStream;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.HttpRequestParser;
import weblogic.utils.http.RequestParser;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.NullInputStream;
import weblogic.work.WorkManagerFactory;

public final class HttpConnectionHandler extends AbstractHttpConnectionHandler {
   private static final int MAX_PIPELINED_REQUESTS = 100;
   private byte[] buf = null;
   private int pos = 0;
   private int headerEndPos;
   private int pipelinedRequests;
   private boolean doNotPipeline = false;
   private long messagesReceived = 0L;

   public HttpConnectionHandler(HttpSocket httpSocket, Chunk head, boolean secure) throws MaxMessageSizeExceededException {
      super(httpSocket, secure);
      this.reuseRequestResponse = true;
      if (head.next == null) {
         this.buf = head.buf;
         this.incrementBufferOffset(head.end);
      } else {
         Chunk blowChunk = null;

         for(Chunk tmp = head; tmp != null; tmp = tmp.next) {
            int toCopy;
            for(int bytesCopied = 0; bytesCopied < tmp.end; bytesCopied += toCopy) {
               byte[] localBuf = this.getBuffer();
               int localOffset = this.getBufferOffset();
               toCopy = Math.min(localBuf.length - localOffset, tmp.end - bytesCopied);
               System.arraycopy(tmp.buf, bytesCopied, localBuf, localOffset, toCopy);
               this.incrementBufferOffset(toCopy);
            }

            if (blowChunk != null) {
               Chunk.releaseChunk(blowChunk);
            }

            blowChunk = tmp;
         }

         if (blowChunk != null) {
            Chunk.releaseChunk(blowChunk);
         }
      }

      this.request = new ServletRequestImpl(this);
      this.response = new ServletResponseImpl(this.request, httpSocket.getOutputStream());
   }

   public RequestParser createRequestParser() {
      HttpRequestParser parser = new HttpRequestParser();
      parser.setScheme(this.isSecure() ? "https" : "http");
      return parser;
   }

   public byte[] getBuffer() {
      if (this.buf != null) {
         if (this.pos < this.buf.length) {
            return this.buf;
         } else {
            byte[] tmp = new byte[this.pos << 1];
            System.arraycopy(this.buf, 0, tmp, 0, this.buf.length);
            this.buf = tmp;
            if (this.httpSocket.getHeadChunk() != null) {
               Chunk.releaseChunk(this.httpSocket.getHeadChunk());
               this.httpSocket.setHeadChunk((Chunk)null);
            }

            return this.buf;
         }
      } else {
         Chunk c = Chunk.getChunk();
         this.buf = c.buf;
         this.httpSocket.setHeadChunk(c);
         return this.buf;
      }
   }

   public boolean isMessageComplete() {
      if (this.buf == null) {
         return false;
      } else {
         boolean foundLF = false;

         for(int i = 0; i < this.pos; ++i) {
            switch (this.buf[i]) {
               case 10:
                  if (foundLF) {
                     this.headerEndPos = i;
                     return true;
                  }

                  foundLF = true;
               case 13:
                  break;
               default:
                  foundLF = false;
            }
         }

         return false;
      }
   }

   public int getBufferOffset() {
      return this.pos;
   }

   public void incrementBufferOffset(int i) throws MaxMessageSizeExceededException {
      this.incrementBufferOffset((Chunk)null, i);
   }

   public ByteBuffer getByteBuffer() {
      ByteBuffer tmp = ByteBuffer.wrap(this.getBuffer());
      tmp.position(this.getBufferOffset());
      return tmp;
   }

   public void incrementBufferOffset(Chunk c, int availBytes) throws MaxMessageSizeExceededException {
      this.pos += availBytes;
      int maxMessageSize = this.getChannel().getMaxMessageSize();
      if (maxMessageSize > -1 && this.pos > maxMessageSize) {
         if (HTTPDebugLogger.isEnabled()) {
            String messageStart = new String(this.buf, 0, 100);
            HTTPDebugLogger.debug("MaxMessageSizeExceeded from this message beginning: " + messageStart);
         }

         throw new MaxMessageSizeExceededException(this.pos, maxMessageSize, this.isSecure() ? "https" : "http");
      } else {
         if (c != null) {
            int offset = this.buf.length;
            byte[] buf = this.getBuffer();

            for(Chunk tmp = c; tmp != null; tmp = tmp.next) {
               System.arraycopy(tmp.buf, 0, buf, offset, tmp.end);
               offset += tmp.end;
            }

            if (offset != this.pos) {
               throw new IllegalArgumentException("Data was not correct!? offset: " + offset + " and availBytes: " + this.pos);
            }

            Chunk.releaseChunks(c);
         }

      }
   }

   public boolean isPipeliningDisabled() {
      return this.doNotPipeline;
   }

   public long getMessagesReceivedCount() {
      return this.messagesReceived;
   }

   public void incrementPipelinedRequests() {
      ++this.pipelinedRequests;
   }

   public void incrementMessagesReceivedCount() {
      ++this.messagesReceived;
   }

   public InputStream getInputStream() {
      return this.httpSocket.getInputStream();
   }

   public boolean isHttpMethodSafe() {
      return this.request.getInputHelper().getRequestParser().isMethodSafe();
   }

   public void prepareRequestForReuse() {
      if (this.reuseRequestResponse) {
         this.request.reset();
         this.response.init();
      } else {
         this.request.skipUnreadBody();
         this.request = new ServletRequestImpl(this);
         this.response = new ServletResponseImpl(this.request, this.httpSocket.getOutputStream());
      }

   }

   public int getKeepAliveSecs() {
      return this.isSecure() ? this.httpServer.getMBean().getHttpsKeepAliveSecs() * 1000 : this.httpServer.getMBean().getKeepAliveSecs() * 1000;
   }

   public void sendError(final ServletResponseImpl res, final int code) {
      Runnable runnable = new Runnable() {
         public void run() {
            try {
               try {
                  if (code == 413) {
                     HttpConnectionHandler.this.httpSocket.getInputStream().skip(HttpConnectionHandler.this.request.getContentLengthLong());
                  }
               } catch (Throwable var2) {
               }

               res.disableKeepAlive();
               res.sendError(code);
               HttpConnectionHandler.this.httpServer.getLogManager().log(HttpConnectionHandler.this.request, HttpConnectionHandler.this.response);
               HttpConnectionHandler.this.httpSocket.closeConnection((Throwable)null);
            } catch (Throwable var3) {
            }

         }
      };
      WorkManagerFactory.getInstance().getDefault().schedule(runnable);
   }

   protected void initInputStream() throws IOException {
      if (this.pipelinedRequests >= 100) {
         this.response.disableKeepAlive();
      }

      long clen = this.request.getContentLengthLong();
      boolean isChunked = this.request.getRequestHeaders().isChunked();
      if (!this.request.getInputHelper().getRequestParser().isMethodSafe()) {
         if (clen != -1L && isChunked) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Content-Length header ignored as the Chunked Transfer-Encoding header exists.");
            }

            clen = -1L;
            this.request.getRequestHeaders().ignoreContentLength();
         }

         if (clen == -1L) {
            if (!this.request.getInputHelper().getRequestParser().isMethodPost() && !this.request.getInputHelper().getRequestParser().isMethodOptions() && !this.request.getInputHelper().getRequestParser().isMethodDelete()) {
               this.response.disableKeepAlive();
            }

            if (!isChunked) {
               this.request.setInputStream((InputStream)NullInputStream.getInstance());
               return;
            }
         }

         PostInputStream pis = new PostInputStream(this, clen, this.buf, this.headerEndPos + 1, this.pos);
         this.httpSocket.setSocketReadTimeout(this.getHttpServer().getPostTimeoutSecs() * 1000);
         if (isChunked) {
            if (this.request.getRequestHeaders().hasTrailer()) {
               this.request.setInputStream((InputStream)(new HttpChunkInputStream(pis, this.httpServer.getMaxPostSize(), this.request.getInputHelper().getRequestParser(), this.request.getRequestHeaders().getTrailerHeaders(), this.request.getInputEncoding())));
            } else {
               this.request.setInputStream((InputStream)(new HttpChunkInputStream(pis, this.httpServer.getMaxPostSize(), this.request.getInputHelper().getRequestParser())));
            }
         } else {
            this.request.setInputStream((InputStream)pis);
         }

      } else {
         this.request.setInputStream((InputStream)NullInputStream.getInstance());
         if (clen > 0L || isChunked) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug(this.request.getInputHelper().getRequestParser().getMethod() + " request has Content-Length or Chunked Transfer-Encoding. Pipelining is turned off for this request.");
            }

            this.response.disableKeepAlive();
            this.doNotPipeline = true;
         }

      }
   }

   protected void send400Response(Exception ex) throws IOException {
      this.response.setStatus(400);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Request parsing failed.", ex);
      }

      String protocol = this.request.getProtocol();
      if (protocol == null && ex instanceof HttpRequestParseException) {
         protocol = ((HttpRequestParseException)ex).getProtocol();
      }

      if (protocol == null) {
         protocol = "HTTP/1.1";
      }

      String message = protocol + " " + 400 + " Bad Request\r\nConnection: close\r\n\r\n";

      try {
         this.httpSocket.getOutputStream().write(message.getBytes());
      } finally {
         this.httpServer.getLogManager().log(this.request, this.response);
      }

      this.httpSocket.closeConnection((Throwable)null);
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
      this.response.setHeader("Connection", "close");
      out.write(refreshPage.getBytes());
      out.flush();
      this.httpSocket.closeConnection((Throwable)null);
   }

   protected void parseHeaders(RequestParser parser) throws HttpRequestParseException {
      parser.parse(this.buf, this.pos);
   }

   protected void IncrementCount() {
      this.incrementMessagesReceivedCount();
      this.incrementBytesReceivedCount((long)(this.headerEndPos + 1));
   }

   protected boolean needToUpgrade() {
      return this.isHTTP2Upgrade();
   }

   protected void processUpgrade() throws IOException {
      try {
         String httpSettings = this.getHTTP2Settings();
         if (httpSettings != null) {
            MuxableSocketHTTP httpSocket = (MuxableSocketHTTP)this.request.getConnection().getConnectionHandler().getRawConnection();
            MuxableSocketHTTP2 http2 = new MuxableSocketHTTP2(Chunk.getChunk(), httpSocket.getSocket(), httpSocket.getServerChannel(), this.request, Base64.decode(httpSettings), false);
            http2.upgrade(httpSocket);
            this.completeHTTP2Upgrade();
            http2.registerForReadEvent();
         }

      } catch (HTTP2Exception var4) {
         throw new IOException(var4);
      }
   }

   private void completeHTTP2Upgrade() throws IOException {
      this.response.setStatus(101);
      this.response.setHeader("Upgrade", "h2c");
      this.response.setHeader("Content-Length", "0");
      this.response.addHeader("Connection", "Upgrade");
      this.response.flushBuffer();
   }

   private boolean isHTTP2Upgrade() {
      String[] connectionHeader = this.getConnectionHeader();
      if (connectionHeader == null) {
         return false;
      } else if (!this.isExisted(connectionHeader, "Upgrade")) {
         return false;
      } else if (!this.isExisted(connectionHeader, "HTTP2-Settings")) {
         return false;
      } else {
         Enumeration enumeration = this.request.getHeaders("HTTP2-Settings");
         int settingCount = 0;

         while(enumeration.hasMoreElements()) {
            ++settingCount;
            enumeration.nextElement();
         }

         if (settingCount != 1) {
            return false;
         } else {
            String upgradeHeader = this.request.getHeader("Upgrade");
            return upgradeHeader != null && "h2c".equalsIgnoreCase(upgradeHeader);
         }
      }
   }

   private String getHTTP2Settings() {
      Enumeration enumeration = this.request.getHeaders("HTTP2-Settings");

      String settings;
      for(settings = null; enumeration.hasMoreElements(); settings = (String)enumeration.nextElement()) {
         if (settings != null) {
            return null;
         }
      }

      return settings;
   }

   private boolean isExisted(String[] strings, String str) {
      String[] var3 = strings;
      int var4 = strings.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         if (str.equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   private String[] getConnectionHeader() {
      String connection = this.request.getHeader("Connection");
      if (connection == null) {
         return null;
      } else {
         String[] connectionHeader = connection.split(",");

         for(int i = 0; i < connectionHeader.length; ++i) {
            connectionHeader[i] = connectionHeader[i].trim();
         }

         return connectionHeader;
      }
   }

   public void requeue() {
      if (this.getSocket().isClosed()) {
         this.blowAllChunks();
         this.closeConnection((IOException)null);
      } else {
         boolean isMethodSafe = this.isHttpMethodSafe();
         this.prepareRequestForReuse();
         int requestEnd = this.headerEndPos + 1;
         this.headerEndPos = 0;
         if (isMethodSafe && requestEnd < this.pos) {
            if (this.isPipeliningDisabled()) {
               this.blowAllChunks();
               this.closeConnection((IOException)null);
               return;
            }

            byte[] tmp = new byte[this.buf.length];
            System.arraycopy(this.buf, requestEnd, tmp, 0, this.pos - requestEnd);
            this.buf = tmp;
            this.pos -= requestEnd;
            if (this.isMessageComplete()) {
               this.incrementPipelinedRequests();
               this.dispatch();
               return;
            }
         } else {
            this.blowAllChunks();
         }

         this.httpSocket.requeue();
      }
   }

   public synchronized void blowAllChunks() {
      Chunk c = this.httpSocket.getHeadChunk();
      if (c != null) {
         Chunk.releaseChunks(c);
         this.buf = null;
         this.httpSocket.setHeadChunk((Chunk)null);
      }

      this.pos = 0;
   }

   public Stream getStream() {
      return null;
   }
}
