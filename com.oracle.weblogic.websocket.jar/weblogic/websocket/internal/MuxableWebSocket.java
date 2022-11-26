package weblogic.websocket.internal;

import java.io.IOException;
import java.security.Principal;
import java.util.Random;
import javax.net.ssl.SSLSocket;
import javax.servlet.http.HttpServletRequest;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.servlet.internal.MuxableSocketHTTP;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.JSSEFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.io.Chunk;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketContext;
import weblogic.websocket.WebSocketListener;

public final class MuxableWebSocket extends AbstractMuxableSocket implements WebSocketConnection {
   static final Random rand = new Random(System.currentTimeMillis());
   private WebSocketContextImpl context;
   private int timeout = -1;
   private State readyState;
   private boolean isSecure;
   private boolean isSecureConnection;
   private int readPos = 0;
   private long length = 0L;
   private int headerSize = 0;
   private byte[] maskBytes = null;
   private WSChunkOutput out;
   private Utf8Utils.DecodingContext utf8DecodingContext;
   private boolean decodeContinuationFrame = false;
   private boolean inContinuationFrames = false;
   private String requestURI;
   private SubjectHandle authenticatedUser = null;
   private ServletResponseImpl httpResponseImpl = null;

   public MuxableWebSocket(MuxableSocketHTTP httpSocket, WebSocketContextImpl context, HttpServletRequest req) throws IOException {
      super(Chunk.getChunk(), httpSocket.getSocket(), httpSocket.getServerChannel());
      this.context = context;
      this.timeout = context.getTimeoutSecs();
      this.isSecure = req.isSecure();
      this.isSecureConnection = httpSocket.isSecure();
      this.requestURI = req.getRequestURI();
      this.readyState = MuxableWebSocket.State.CONNECTING;
      this.out = new WSChunkOutput(this.getSocket().getOutputStream());
      this.utf8DecodingContext = new Utf8Utils.DecodingContext();
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      this.httpResponseImpl = reqi != null ? reqi.getResponse() : null;
   }

   public void incrementBufferOffset(int availBytes) throws MaxMessageSizeExceededException {
      this.availBytes += availBytes;
      Chunk var10000 = this.tail;
      var10000.end += availBytes;
      int maxMessageSize = this.context.getMaxMessageSize();
      if (maxMessageSize > -1 && availBytes > maxMessageSize) {
         throw new MaxMessageSizeExceededException(availBytes, maxMessageSize, this.isSecure ? "wss" : "ws");
      }
   }

   public void hasException(Throwable t) {
      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug("WebSocket: " + t.getMessage(), t);
      }

      this.context.removeConnection(this);
      WebSocketListener listener = this.context.getWebSocketListener();
      if (listener != null) {
         PrivilegedCallbacks.onError(listener, this, t);
      }

      try {
         int statusCode = 1011;
         if (t instanceof MaxMessageSizeExceededException) {
            statusCode = 1009;
         } else if (t instanceof WebSocketMessageParsingException) {
            statusCode = ((WebSocketMessageParsingException)t).getStatusCode();
         }

         this.close(statusCode, t.getMessage());
      } catch (IOException var4) {
      }

      super.hasException(t);
   }

   public void endOfStream() {
      if (this.readyState != MuxableWebSocket.State.CLOSED && this.readyState != MuxableWebSocket.State.CLOSING) {
         this.context.removeConnection(this);
         final WebSocketListener listener = this.context.getWebSocketListener();
         if (listener != null) {
            Runnable runnable = new Runnable() {
               public void run() {
                  Thread thread = Thread.currentThread();
                  WebAppServletContext ctx = (WebAppServletContext)MuxableWebSocket.this.context.getServletContext();
                  ClassLoader cl = ctx.pushEnvironment(thread);

                  try {
                     PrivilegedCallbacks.onClose(listener, MuxableWebSocket.this, new ClosingMessageImpl(1001));
                  } catch (Throwable var8) {
                  } finally {
                     WebAppServletContext.popEnvironment(thread, cl);
                     MuxableWebSocket.super.endOfStream();
                  }

               }
            };
            this.context.getWorkManager().schedule(runnable);
         } else {
            super.endOfStream();
         }
      } else {
         super.endOfStream();
      }

   }

   public boolean timeout() {
      this.syncHttpSessionIfAny();
      this.context.removeConnection(this);
      final WebSocketListener listener = this.context.getWebSocketListener();
      if (listener != null) {
         Runnable runnable = new Runnable() {
            public void run() {
               Thread thread = Thread.currentThread();
               WebAppServletContext ctx = (WebAppServletContext)MuxableWebSocket.this.context.getServletContext();
               ClassLoader cl = ctx.pushEnvironment(thread);

               try {
                  PrivilegedCallbacks.onTimeout(listener, MuxableWebSocket.this);
               } catch (Throwable var8) {
               } finally {
                  WebAppServletContext.popEnvironment(thread, cl);
                  SocketMuxer.getMuxer().finishExceptionHandling(MuxableWebSocket.this);
               }

            }
         };
         this.context.getWorkManager().schedule(runnable);
         return false;
      } else {
         return super.timeout();
      }
   }

   public boolean isMessageComplete() {
      if (this.availBytes <= 1) {
         return false;
      } else {
         byte lengthHintByte = this.getLengthHintByte();
         boolean masked = this.isMasked();
         this.headerSize = this.getHeaderSize(lengthHintByte, masked);
         if (this.availBytes < this.headerSize) {
            return false;
         } else {
            this.length = this.readLength(lengthHintByte);
            if (masked) {
               this.maskBytes = this.readMaskBytes(lengthHintByte);
            }

            return (long)this.availBytes > this.length + (long)this.headerSize - 1L;
         }
      }
   }

   public String getRequestURI() {
      return this.requestURI;
   }

   public Principal getUserPrincipal() {
      return this.authenticatedUser == null ? null : this.authenticatedUser.getPrincipal();
   }

   void setAuthenticatedUser(SubjectHandle subject) {
      this.authenticatedUser = subject;
   }

   SubjectHandle getAuthenticatedUser() {
      return this.authenticatedUser;
   }

   private boolean isMasked() {
      return (this.getByte(1) & 128) == 128;
   }

   private byte getLengthHintByte() {
      return (byte)(this.getByte(1) & 127);
   }

   private long readLength(byte lengthHintByte) {
      if (lengthHintByte < 126) {
         return (long)lengthHintByte;
      } else {
         int lengthBytesSize = lengthHintByte == 126 ? 2 : 8;
         long length = 0L;

         for(int offset = 2; offset < 2 + lengthBytesSize; ++offset) {
            length <<= 8;
            length ^= (long)this.getByte(offset) & 255L;
         }

         return length;
      }
   }

   private int getHeaderSize(byte lengthHintByte, boolean masked) {
      if (lengthHintByte < 126) {
         return (masked ? 4 : 0) + 2;
      } else {
         return lengthHintByte == 126 ? (masked ? 4 : 0) + 4 : (masked ? 4 : 0) + 10;
      }
   }

   private byte[] readMaskBytes(byte lengthHintByte) {
      byte[] maskKeys = new byte[4];
      int maskOffset = 2;
      if (lengthHintByte == 126) {
         maskOffset = 4;
      }

      if (lengthHintByte == 127) {
         maskOffset = 10;
      }

      int i = 0;

      for(int j = maskOffset; i < 4; ++j) {
         maskKeys[i] = this.getByte(j);
         ++i;
      }

      return maskKeys;
   }

   private int getDataOffset(byte lengthHintByte, boolean masked) {
      if (lengthHintByte < 126) {
         return masked ? 6 : 2;
      } else if (lengthHintByte == 126) {
         return masked ? 8 : 4;
      } else {
         return masked ? 14 : 10;
      }
   }

   public int getIdleTimeoutMillis() {
      return this.timeout == -1 ? Integer.MAX_VALUE : this.timeout * 1000;
   }

   public void dispatch() {
      if (this.isSecureConnection && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOff();
      }

      byte opcode = (byte)(this.head.buf[this.readPos] & 15);
      int data = this.getDataOffset(this.getLengthHintByte(), this.maskBytes != null);
      boolean finalFragment = (this.head.buf[this.readPos] & 128) == 128;
      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug("WebSocket: Received DataFrame: ", this.head, data + (int)this.length, true);
      }

      if ((this.head.buf[this.readPos] & 112) > 0) {
         try {
            this.close(1002, "No Extension Negotiated");
         } catch (IOException var7) {
            if (WebSocketDebugLogger.isEnabled()) {
               WebSocketDebugLogger.debug("Got IOException when sending close message", var7);
            }
         }

      } else {
         byte[] payload = new byte[(int)this.length];
         this.copyDataTo(payload, data, (int)this.length);

         try {
            AbstractWebSocketMessage message = WebSocketMessageFactory.getInstance(payload, this.maskBytes, opcode, finalFragment, this.decodeContinuationFrame, this.inContinuationFrames, this.utf8DecodingContext);
            this.checkShouldSetDecodeContinuationFrame(opcode, finalFragment);
            this.checkShouldResetDecodeContinuationFrame(opcode, finalFragment);
            this.toggleEncounteredContinuationFrame(opcode, finalFragment);
            this.context.dispatch(this, message);
         } catch (Exception var6) {
            this.hasException(var6);
            this.shutdownSocket();
         }

      }
   }

   private byte getByte(int offset) {
      int pos = this.readPos + offset;
      Chunk c = this.head;
      int i = 0;

      for(int j = pos / Chunk.CHUNK_SIZE; i < j; ++i) {
         c = c.next;
      }

      return c.buf[pos % Chunk.CHUNK_SIZE];
   }

   private void copyDataTo(byte[] payload, int offset, int len) {
      int readPosInChunk = this.readPos;
      int writePos = 0;
      int readCount = 0;
      Chunk current = null;
      if (readPosInChunk + offset > Chunk.CHUNK_SIZE) {
         current = this.head.next;
         readPosInChunk = readPosInChunk + offset - Chunk.CHUNK_SIZE;
      } else {
         current = this.head;
         readPosInChunk += offset;
      }

      while(readCount < len && current != null) {
         int bytesNeedToCopy = len - readCount;
         int bytesInChunk = Chunk.CHUNK_SIZE - readPosInChunk;
         int copyLen = bytesNeedToCopy <= bytesInChunk ? bytesNeedToCopy : bytesInChunk;
         System.arraycopy(current.buf, readPosInChunk, payload, writePos, copyLen);
         writePos += copyLen;
         readCount += copyLen;
         readPosInChunk = 0;
         current = current.next;
      }

   }

   private void checkShouldSetDecodeContinuationFrame(byte opcode, boolean finalFragment) {
      if (!finalFragment && !this.decodeContinuationFrame) {
         this.decodeContinuationFrame = WebSocketMessageFactory.getFrameType(opcode) == WebSocketMessage.Type.TEXT;
      }

   }

   private void checkShouldResetDecodeContinuationFrame(byte opcode, boolean finalFragment) {
      if (this.decodeContinuationFrame && finalFragment && WebSocketMessageFactory.getFrameType(opcode) == WebSocketMessage.Type.CONTINUATION) {
         this.decodeContinuationFrame = false;
      }

   }

   private void toggleEncounteredContinuationFrame(byte opcode, boolean finalFragment) {
      WebSocketMessage.Type type = WebSocketMessageFactory.getFrameType(opcode);
      if (!finalFragment && (type == WebSocketMessage.Type.TEXT || type == WebSocketMessage.Type.BINARY)) {
         this.inContinuationFrames = true;
      }

      if (finalFragment && type == WebSocketMessage.Type.CONTINUATION) {
         this.inContinuationFrames = false;
      }

   }

   public void send(String message) throws IOException {
      if (this.readyState != MuxableWebSocket.State.OPEN) {
         throw new IllegalStateException("Web socket is closed!");
      } else {
         this.out.sendTextMessage(message);
      }
   }

   public void send(byte[] message) throws IOException {
      if (this.readyState != MuxableWebSocket.State.OPEN) {
         throw new IllegalStateException("Web socket is closed!");
      } else {
         this.out.sendBinaryMessage(message);
      }
   }

   public void stream(boolean last, String fragment) throws IOException, IllegalStateException {
      if (this.readyState != MuxableWebSocket.State.OPEN) {
         throw new IllegalStateException("Web socket is closed!");
      } else {
         this.out.sendFragment(last, fragment);
      }
   }

   public void stream(boolean last, byte[] fragment, int off, int length) throws IOException, IllegalStateException {
      if (this.readyState != MuxableWebSocket.State.OPEN) {
         throw new IllegalStateException("Web socket is closed!");
      } else {
         this.out.sendFragment(last, fragment, off, length);
      }
   }

   public void sendPing(byte[] message) throws IOException {
      if (this.readyState != MuxableWebSocket.State.OPEN) {
         throw new IllegalStateException("Web socket is closed!");
      } else {
         this.out.sendPingMessage(message);
      }
   }

   public void sendPong(byte[] message) throws IOException {
      this.out.sendPongMessage(message);
   }

   public void close(int code) throws IOException {
      this.close(code, (String)null);
   }

   public void close(int code, String reason) throws IOException {
      this.syncHttpSessionIfAny();
      if (this.readyState != MuxableWebSocket.State.CLOSING && this.readyState != MuxableWebSocket.State.CLOSED) {
         if (this.closeLatch.tryLock()) {
            this.readyState = MuxableWebSocket.State.CLOSING;
            this.context.removeConnection(this);
            this.out.sendCloseMessage(code, reason);
            this.shutdownSocket();
         }

      }
   }

   private void syncHttpSessionIfAny() {
      if (this.httpResponseImpl != null) {
         try {
            this.httpResponseImpl.syncSession();
         } catch (IOException var2) {
            if (WebSocketDebugLogger.isEnabled()) {
               WebSocketDebugLogger.debug("Got IOException for http syncSession() when closing the websocket connection.", var2);
            }
         }

      }
   }

   private void shutdownSocket() {
      if (this.readyState != MuxableWebSocket.State.CLOSED) {
         this.readyState = MuxableWebSocket.State.CLOSED;
         SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
         this.cleanup();
      }
   }

   protected void cleanup() {
      super.cleanup();
      this.authenticatedUser = null;
   }

   public boolean isOpen() {
      return this.readyState == MuxableWebSocket.State.OPEN;
   }

   public boolean isSecure() {
      return this.isSecure;
   }

   public String getRemoteUser() {
      return this.authenticatedUser == null ? null : this.authenticatedUser.getUsername();
   }

   public WebSocketContext getWebSocketContext() {
      return this.context;
   }

   static byte[] getRandomBytes(int count) {
      byte[] b = new byte[count];

      for(int i = 0; i < count; ++i) {
         rand.nextBytes(b);
      }

      return b;
   }

   void requeue() {
      long preDataFrameLen = this.length + (long)this.headerSize;
      if ((long)this.availBytes > preDataFrameLen) {
         this.availBytes = (int)((long)this.availBytes - preDataFrameLen);
         this.releaseChunks((int)preDataFrameLen);
         if (this.isMessageComplete()) {
            this.dispatch();
            return;
         }
      } else {
         this.reset();
      }

      this.registerForReadEvent();
   }

   private void releaseChunks(int preDataFrameLen) {
      int len = this.readPos + preDataFrameLen;
      int i = 0;

      for(int j = len / Chunk.CHUNK_SIZE; i < j; ++i) {
         Chunk temp = this.head.next;
         Chunk.releaseChunk(this.head);
         this.head = temp;
      }

      this.readPos = len % Chunk.CHUNK_SIZE;
   }

   void registerForReadEvent() {
      if (this.isSecureConnection && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOn();
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   private void reset() {
      this.headerSize = 0;
      this.length = 0L;
      this.maskBytes = null;
      this.readPos = 0;
      Chunk.releaseChunks(this.head);
      this.resetData();
   }

   void upgrade(MuxableSocket httpSocket) throws IOException {
      if (this.isSecureConnection) {
         SSLSocket sslSock = (SSLSocket)((SSLSocket)httpSocket.getSocket());
         JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSock);
         if (jsseSock != null) {
            JSSEFilterImpl filter = (JSSEFilterImpl)httpSocket.getSocketFilter();
            this.setSocketFilter(filter);
            filter.setDelegate(this);
         } else {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSock);
            if (sslIOCtx == null) {
               throw new IOException("SSL transport layer closed the socket!");
            }

            SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
            this.setSocketFilter(sslf);
            sslf.setDelegate(this);
            sslf.activateNoRegister();
         }
      } else {
         SocketMuxer.getMuxer().reRegister(httpSocket, this);
      }

      this.readyState = MuxableWebSocket.State.OPEN;
   }

   private static enum State {
      CONNECTING,
      OPEN,
      CLOSING,
      CLOSED;
   }
}
