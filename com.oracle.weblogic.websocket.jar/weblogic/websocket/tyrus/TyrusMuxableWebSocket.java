package weblogic.websocket.tyrus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.PrivilegedAction;
import javax.net.ssl.SSLSocket;
import javax.servlet.ServletContext;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.spi.Connection;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.servlet.internal.MuxableSocketHTTP;
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
import weblogic.websocket.internal.WSChunkOutput;
import weblogic.websocket.internal.WebSocketDebugLogger;

public final class TyrusMuxableWebSocket extends AbstractMuxableSocket {
   private final boolean isSecureConnection;
   private final WSChunkOutput out;
   private final CoherenceServletFilterService coherenceService;
   private final SubjectHandle subject;
   private State readyState;
   private int readPos = 0;
   private long length = 0L;
   private int headerSize = 0;
   private byte[] maskBytes = null;
   private volatile Connection connection;
   private volatile WebAppServletContext servletContext;

   public TyrusMuxableWebSocket(MuxableSocketHTTP httpSocket, CoherenceServletFilterService coherenceService, SubjectHandle subject) throws IOException {
      super(Chunk.getChunk(), httpSocket.getSocket(), httpSocket.getServerChannel());
      this.isSecureConnection = httpSocket.isSecure();
      this.readyState = TyrusMuxableWebSocket.State.CONNECTING;
      this.out = new WSChunkOutput(this.getSocket().getOutputStream());
      this.coherenceService = coherenceService;
      this.subject = subject;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public void incrementBufferOffset(int availBytes) throws MaxMessageSizeExceededException {
      this.availBytes += availBytes;
      Chunk var10000 = this.tail;
      var10000.end += availBytes;
   }

   public void hasException(final Throwable t) {
      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug("WebSocket: " + t.getMessage(), t);
      }

      this.execute(new Runnable() {
         public void run() {
            if (TyrusMuxableWebSocket.this.connection != null) {
               TyrusMuxableWebSocket.this.connection.close(new CloseReason(CloseCodes.CLOSED_ABNORMALLY, "Exception occurs: " + t.getMessage()));
            }

         }
      }, false);
      super.hasException(t);
   }

   public void endOfStream() {
      if (this.readyState != TyrusMuxableWebSocket.State.CLOSED && this.readyState != TyrusMuxableWebSocket.State.CLOSING) {
         this.execute(new Runnable() {
            public void run() {
               if (TyrusMuxableWebSocket.this.connection != null) {
                  TyrusMuxableWebSocket.this.connection.close(new CloseReason(CloseCodes.CLOSED_ABNORMALLY, "Client closed."));
               }

            }
         }, false);
      }

      super.endOfStream();
   }

   public void dispatch() {
      if (this.isSecureConnection && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOff();
      }

      final int dataOffset = this.getDataOffset(this.getLengthHintByte(), this.maskBytes != null);
      if (WebSocketDebugLogger.isEnabled()) {
         WebSocketDebugLogger.debug("WebSocket: Received DataFrame: ", this.head, dataOffset + (int)this.length, true);
      }

      this.execute(new Runnable() {
         public void run() {
            ByteBuffer buf = ByteBuffer.wrap(TyrusMuxableWebSocket.this.getRawDataFrame(dataOffset));
            if (TyrusMuxableWebSocket.this.connection != null) {
               TyrusMuxableWebSocket.this.connection.getReadHandler().handle(buf);
            }

         }
      }, true);
   }

   public int getIdleTimeoutMillis() {
      return -1;
   }

   public boolean timeout() {
      this.connection.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Connection timed out"));
      return super.timeout();
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

   public void sendRawData(byte[] bytes) throws IOException {
      this.out.sendRawData(bytes);
   }

   public void shutdownSocket(IOException e) {
      if (this.readyState != TyrusMuxableWebSocket.State.CLOSED) {
         this.readyState = TyrusMuxableWebSocket.State.CLOSED;
         if (e == null) {
            this.connection.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, (String)null));
         } else {
            this.connection.close(new CloseReason(CloseCodes.CLOSED_ABNORMALLY, "Exception occurs: " + e.getMessage()));
         }

         SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
         this.cleanup();
         this.connection = null;
      }
   }

   public void shutdownSocket() {
      this.shutdownSocket((IOException)null);
   }

   public void registerForReadEvent() {
      if (this.isSecureConnection && this.getSocketFilter() instanceof SSLFilter) {
         ((SSLFilter)this.getSocketFilter()).asyncOn();
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   public void upgrade(MuxableSocket httpSocket, ServletContext context) throws IOException {
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

      this.servletContext = (WebAppServletContext)context;
      this.readyState = TyrusMuxableWebSocket.State.OPEN;
   }

   private void execute(final Runnable runnable, final boolean requeue) {
      this.servletContext.getConfigManager().getWorkManager().schedule(new Runnable() {
         public void run() {
            Thread thread = Thread.currentThread();
            ClassLoader contextClassLoader = null;
            ClassLoader cl = TyrusMuxableWebSocket.this.servletContext.pushEnvironment(thread);

            try {
               if (TyrusMuxableWebSocket.this.coherenceService != null) {
                  contextClassLoader = TyrusMuxableWebSocket.this.coherenceService.setContextClassLoader(thread);
               }

               TyrusMuxableWebSocket.this.subject.run(new PrivilegedAction() {
                  public Void run() {
                     runnable.run();
                     return null;
                  }
               });
            } finally {
               if (contextClassLoader != null) {
                  thread.setContextClassLoader(contextClassLoader);
               }

               WebAppServletContext.popEnvironment(thread, cl);
            }

            if (requeue && TyrusMuxableWebSocket.this.isOpen()) {
               TyrusMuxableWebSocket.this.requeue();
            }

         }
      });
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

   private boolean isOpen() {
      return this.readyState == TyrusMuxableWebSocket.State.OPEN;
   }

   private void requeue() {
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

   private void reset() {
      this.headerSize = 0;
      this.length = 0L;
      this.maskBytes = null;
      this.readPos = 0;
      Chunk.releaseChunks(this.head);
      this.resetData();
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

   private byte getByte(int offset) {
      int pos = this.readPos + offset;
      Chunk c = this.head;
      int i = 0;

      for(int j = pos / Chunk.CHUNK_SIZE; i < j; ++i) {
         c = c.next;
      }

      return c.buf[pos % Chunk.CHUNK_SIZE];
   }

   private byte[] getRawDataFrame(int dataOffset) {
      int frameLen = dataOffset + (int)this.length;
      byte[] frame = new byte[frameLen];
      this.copyDataTo(frame, 0, frameLen);
      return frame;
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

   private static enum State {
      CONNECTING,
      OPEN,
      CLOSING,
      CLOSED;
   }
}
