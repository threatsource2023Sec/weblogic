package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.HTTPLogger;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOConnection;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketInfo;
import weblogic.socket.SocketMuxer;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.http.HttpChunkInputStream;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.NullInputStream;

public class MuxableSocketNonBlocking implements MuxableSocket, HttpSocket {
   private Chunk chunk = null;
   private ByteBuffer byteBuffer = null;
   private ChunkStatus chunkStatus;
   private int previousChunkLeft = 0;
   private UnsyncStringBuffer tempSizeString = new UnsyncStringBuffer();
   private int tmpChunkStartPos = 0;
   private int chunkStartPos = 0;
   private int chunkSize = -1;
   private ServerChannel channel;
   private HttpSocket httpSocket;
   protected MuxableSocket filter = null;
   protected SocketInfo info;
   private InputStream sis;
   private ByteBufferInputStream buffInputStream;
   protected HttpConnectionHandler connHandler;
   private ReadListenerStateContext readStateHandler;
   private boolean isChunk = false;
   private boolean isAsync = false;
   private long clen = -1L;
   protected int soTimeout;

   public MuxableSocketNonBlocking(HttpConnectionHandler connHandler, ReadListenerStateContext readStateHandler, InputStream inputStream, boolean isAsync, boolean isChunk, long clen) {
      this.chunk = Chunk.getChunk();
      this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.INIT;
      this.isAsync = isAsync;
      this.isChunk = isChunk;
      if (!isChunk) {
         this.clen = clen;
      }

      this.connHandler = connHandler;
      if (connHandler != null) {
         this.httpSocket = connHandler.getRawConnection();
         this.channel = connHandler.getChannel();
         this.sis = connHandler.getInputStream();
      }

      this.readStateHandler = readStateHandler;
      this.buffInputStream = new ByteBufferInputStream();
      this.buffInputStream.inheritOriginalInputStream(inputStream);
   }

   private void initBuf() {
      this.byteBuffer = this.chunk.getWriteByteBuffer();
      this.chunk.end = 0;
   }

   private void initChunk() {
      this.readChunks();
      this.byteBuffer.flip();
      this.chunk.end = 0;
      this.resetChunkPos();
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(512);
      buf.append("readStateHandler = " + this.readStateHandler);
      buf.append(", isAsync = " + this.isAsync);
      buf.append(", isChunk = " + this.isChunk);
      buf.append(", chunkStatus = " + this.chunkStatus);
      buf.append(", chunk.end = " + this.chunk.end);
      buf.append(", chunkStartPos = " + this.chunkStartPos);
      buf.append(", chunkSize = " + this.chunkSize);
      buf.append(", clen = " + this.clen);
      return buf.toString();
   }

   public void dispatch() {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("MuxableSocketNonBlocking dispatch: " + this);
      }

      if (this.isAsync() && this.isChunk()) {
         this.initChunk();
         if (this.chunkStatus == MuxableSocketNonBlocking.ChunkStatus.ZERO_CHUNK) {
            this.readStateHandler.setReadCompleteState();
         } else {
            this.readStateHandler.setReadReadyState();
         }
      } else {
         this.initBuf();
         if (!this.isAsync) {
            try {
               ((ServletOutputStreamImpl)this.connHandler.getServletResponse().getOutputStream()).setUpgradeMode(true);
            } catch (IOException var2) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("MuxableSocketNonBlocking dispatch() throws an exception when set UpgradeMode");
               }

               var2.printStackTrace();
            }
         }

         this.readStateHandler.setReadReadyState();
      }

      this.readStateHandler.scheduleProcess();
   }

   public int available() {
      try {
         return this.buffInputStream.available();
      } catch (IOException var2) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("An exception happened when invoke available() on buffInputStream");
         }

         var2.printStackTrace();
         return 0;
      }
   }

   public byte[] getBuffer() {
      return this.chunk.buf;
   }

   public ByteBuffer getByteBuffer() {
      return this.chunk.getReadByteBuffer();
   }

   public int getBufferOffset() {
      return this.chunk.end;
   }

   public void incrementBufferOffset(Chunk c, int availBytes) throws MaxMessageSizeExceededException {
      c.end += availBytes;
      int maxMessageSize = this.channel.getMaxMessageSize();
      if (maxMessageSize > -1 && c.end > maxMessageSize) {
         if (HTTPDebugLogger.isEnabled()) {
            String messageStart = new String(c.buf, 0, 100);
            HTTPDebugLogger.debug("MaxMessageSizeExceeded from this message beginning: " + messageStart);
         }

         throw new MaxMessageSizeExceededException(c.end, maxMessageSize, "http");
      }
   }

   public void incrementBufferOffset(int count) throws MaxMessageSizeExceededException {
      this.incrementBufferOffset(this.chunk, count);
   }

   public boolean isMessageComplete() {
      if (this.chunk.buf == null) {
         return false;
      } else if (this.isAsync() && this.isChunk()) {
         if (this.chunkStatus == MuxableSocketNonBlocking.ChunkStatus.PREV_SIZE_REMAIN) {
            this.chunkSize = this.previousChunkLeft;
            this.previousChunkLeft = 0;
         }

         if ((this.chunkSize > 0 || this.readChunkSize() >= 0) && this.chunkStartPos < this.chunk.end) {
            return true;
         } else {
            return this.chunkStatus == MuxableSocketNonBlocking.ChunkStatus.ZERO_CHUNK;
         }
      } else {
         return true;
      }
   }

   private void resetChunkPos() {
      this.chunkSize = -1;
      this.chunkStartPos = 0;
      this.tmpChunkStartPos = 0;
   }

   private void readChunks() {
      if (this.byteBuffer != null && this.byteBuffer.capacity() != 0) {
         this.byteBuffer.clear();
      } else {
         this.byteBuffer = ByteBuffer.allocate(Chunk.CHUNK_SIZE);
      }

      if (this.chunkStatus != MuxableSocketNonBlocking.ChunkStatus.ZERO_CHUNK) {
         do {
            this.readChunkData();
         } while(this.previousChunkLeft == 0 && this.readChunkSize() > 0);

         if (this.chunkStatus == MuxableSocketNonBlocking.ChunkStatus.ZERO_CHUNK) {
            this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.LAST_CHUNK;
         } else if (this.previousChunkLeft == 0) {
            this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.FULL_CHUNK;
         } else {
            this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.PREV_SIZE_REMAIN;
         }

      }
   }

   private int readChunkData() {
      int count = this.chunkSize;
      if (this.chunkStartPos + this.chunkSize > this.chunk.end) {
         count = this.chunk.end - this.chunkStartPos;
         this.previousChunkLeft = this.chunkStartPos + this.chunkSize - this.chunk.end;
      }

      this.byteBuffer.put(this.chunk.buf, this.chunkStartPos, count);
      this.tmpChunkStartPos = this.chunkStartPos + count;
      return count;
   }

   private int readChunkSize() throws NumberFormatException {
      this.consumeBodyEndCRLF();
      boolean sizeReadComplete = false;

      for(int i = this.tmpChunkStartPos; i < this.chunk.end; ++i) {
         if (i < this.chunk.end - 1 && this.chunk.buf[i] == 13 && this.chunk.buf[i + 1] == 10) {
            this.chunkStartPos = i + 2;
            sizeReadComplete = true;
            break;
         }

         this.tempSizeString.append((char)this.chunk.buf[i]);
      }

      if (!sizeReadComplete) {
         if (this.tempSizeString.length() != 0) {
            this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.PRATIAL_SIZE;
         }

         return -1;
      } else {
         try {
            this.chunkSize = Integer.parseInt(this.tempSizeString.toString(), 16);
            this.tempSizeString = new UnsyncStringBuffer();
            if (this.chunkSize == 0) {
               this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.ZERO_CHUNK;
            }
         } catch (NumberFormatException var3) {
            this.resetChunkPos();
            var3.printStackTrace();
            this.close();
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("readChunkSize get an error when parse size: " + this.chunkSize);
            }

            throw var3;
         }

         return this.chunkSize;
      }
   }

   private void consumeBodyEndCRLF() {
      if (this.tmpChunkStartPos >= 0 && this.chunk.buf[this.tmpChunkStartPos] == 13 && this.chunk.buf[this.tmpChunkStartPos + 1] == 10) {
         this.tmpChunkStartPos += 2;
      }

   }

   public Socket getSocket() {
      return this.httpSocket.getSocket();
   }

   public InputStream getSocketInputStream() {
      return this.sis;
   }

   public void setSoTimeout(int timeout) throws SocketException {
      if (timeout != this.soTimeout) {
         this.soTimeout = timeout;
         this.getSocket().setSoTimeout(timeout);
      }
   }

   public void hasException(Throwable t) {
      if (t instanceof MaxMessageSizeExceededException) {
         HTTPLogger.logConnectionFailure((MaxMessageSizeExceededException)t);
      } else if (t instanceof IOException) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("IO issue", (IOException)t);
         }
      } else {
         HTTPLogger.logConnectionFailure(t);
      }

      this.readStateHandler.setErrorState(t);
      this.readStateHandler.process();
   }

   public void endOfStream() {
      this.connHandler.blowAllChunks();
      if (this.chunk != null) {
         Chunk.releaseChunk(this.chunk);
      }

      this.chunk = null;
   }

   public boolean timeout() {
      SocketMuxer.getMuxer().deliverEndOfStream(this);
      this.close();
      return true;
   }

   public boolean requestTimeout() {
      return true;
   }

   public int getIdleTimeoutMillis() {
      return this.channel.getCompleteMessageTimeout() * 1000;
   }

   public int getCompleteMessageTimeoutMillis() {
      return this.channel.getCompleteMessageTimeout() * 1000;
   }

   public void setSocketFilter(MuxableSocket filter) {
      this.filter = filter;
   }

   public MuxableSocket getSocketFilter() {
      return this;
   }

   public void setSocketInfo(SocketInfo info) {
      this.info = info;
   }

   public SocketInfo getSocketInfo() {
      return this.info;
   }

   public boolean supportsScatteredRead() {
      return false;
   }

   public long read(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }

   public final void close() {
      this.buffInputStream = null;
      this.connHandler = null;
      if (this.readStateHandler != null) {
         this.readStateHandler.closeWebConnection();
      }

      this.byteBuffer = null;
      if (this.chunk != null) {
         Chunk.releaseChunk(this.chunk);
      }

      this.chunk = null;
   }

   public InputStream getBufferInputStream() {
      return this.buffInputStream;
   }

   private void setReayToRead() {
      if (this.readStateHandler.isReadReady()) {
         this.readStateHandler.setReadWaitState();
      }

   }

   protected boolean isAsync() {
      return this.isAsync;
   }

   protected boolean isChunk() {
      return this.isChunk;
   }

   public InputStream getInputStream() {
      return this.buffInputStream;
   }

   public OutputStream getOutputStream() {
      return null;
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }

   public void closeConnection(Throwable throwable) {
      if (throwable == null) {
         SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
      } else {
         SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), throwable);
      }

   }

   public boolean handleOnDemandContext(ServletRequestImpl request, String normalizedURI) throws IOException {
      return false;
   }

   public void setHeadChunk(Chunk c) {
   }

   public Chunk getHeadChunk() {
      return null;
   }

   public void registerForReadEvent() {
      if (this.getSocketFilter() instanceof SSLFilter && this.connHandler.isSecure()) {
         ((SSLFilter)this.getSocketFilter()).asyncOn();
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   public void setSocketReadTimeout(int timeout) throws SocketException {
      this.soTimeout = timeout;
   }

   public boolean closeSocketOnError() {
      return true;
   }

   public void requeue() {
   }

   public class ByteBufferInputStream extends InputStream {
      private long nread = 0L;

      private void inheritOriginalInputStream(InputStream inputStream) {
         if (inputStream instanceof NullInputStream) {
            MuxableSocketNonBlocking.this.byteBuffer = ByteBuffer.allocate(0);
         } else if (inputStream instanceof PostInputStream) {
            MuxableSocketNonBlocking.this.byteBuffer = ((PostInputStream)inputStream).buf;
            this.nread = ((PostInputStream)inputStream).nread;
         } else if (inputStream instanceof HttpChunkInputStream) {
            HttpChunkInputStream input = (HttpChunkInputStream)inputStream;

            try {
               if (!input.hasUnconsumedChunk()) {
                  MuxableSocketNonBlocking.this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.LAST_CHUNK;
                  MuxableSocketNonBlocking.this.readStateHandler.setReadWaitState();
               } else if (!input.currentChunkExhausts()) {
                  byte[] b = new byte[input.available()];
                  int pos = input.read(b);
                  if (pos <= 0) {
                     this.setPreviousChunkLeft((int)input.unConsumedChunkSize());
                     MuxableSocketNonBlocking.this.byteBuffer = ByteBuffer.allocate(0);
                  } else {
                     while(input.hasUnconsumedChunk() && input.available() > 0) {
                        if (b.length - pos < input.available()) {
                           byte[] destBuff = new byte[pos + input.available()];
                           System.arraycopy(b, 0, destBuff, 0, pos);
                           b = destBuff;
                        }

                        int readCount = input.read(b, pos, input.available());
                        if (readCount <= 0) {
                           break;
                        }

                        pos += readCount;
                     }

                     if (!input.currentChunkExhausts()) {
                        this.setPreviousChunkLeft((int)input.unConsumedChunkSize());
                     }

                     if (!input.hasUnconsumedChunk()) {
                        MuxableSocketNonBlocking.this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.LAST_CHUNK;
                     }

                     MuxableSocketNonBlocking.this.byteBuffer = ByteBuffer.allocate(Chunk.CHUNK_SIZE > pos ? Chunk.CHUNK_SIZE : pos);
                     if (pos < b.length - 1) {
                        MuxableSocketNonBlocking.this.byteBuffer.put(b, 0, pos);
                     } else {
                        MuxableSocketNonBlocking.this.byteBuffer.put(b);
                     }

                     MuxableSocketNonBlocking.this.byteBuffer.flip();
                  }
               } else {
                  MuxableSocketNonBlocking.this.byteBuffer = ByteBuffer.allocate(0);
               }
            } catch (IOException var6) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("ByteBufferInputStream inheritOriginalInputStream has an exception:" + var6);
               }
            }
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("ByteBufferInputStream inheritOriginalInputStream got a wrong type of stream: " + inputStream);
         }

         this.checkAvailable();
      }

      private void setPreviousChunkLeft(int unConsumedChunkSize) {
         MuxableSocketNonBlocking.this.chunkStatus = MuxableSocketNonBlocking.ChunkStatus.PREV_SIZE_REMAIN;
         MuxableSocketNonBlocking.this.previousChunkLeft = unConsumedChunkSize;
      }

      public int read() throws IOException {
         if (!this.isReady()) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("ByteBufferInputStream read() throws an exception: nread = " + this.nread);
            }

            MuxableSocketNonBlocking.this.setReayToRead();
            throw new IllegalStateException("Could not read on a not ready stream!");
         } else {
            int b = MuxableSocketNonBlocking.this.byteBuffer.get();
            ++this.nread;
            if (!MuxableSocketNonBlocking.this.byteBuffer.hasRemaining()) {
               MuxableSocketNonBlocking.this.setReayToRead();
            }

            if (this.shouldNotifyAllDataRead()) {
               MuxableSocketNonBlocking.this.readStateHandler.setReadCompleteState();
            }

            return b;
         }
      }

      private boolean shouldNotifyAllDataRead() {
         return this.endWithZeroChunk() || this.reachedContentLength();
      }

      private boolean endWithZeroChunk() {
         return MuxableSocketNonBlocking.this.isAsync() && MuxableSocketNonBlocking.this.isChunk() && !this.isReady() && MuxableSocketNonBlocking.this.chunkStatus == MuxableSocketNonBlocking.ChunkStatus.LAST_CHUNK;
      }

      private boolean reachedContentLength() {
         return MuxableSocketNonBlocking.this.isAsync() && !MuxableSocketNonBlocking.this.isChunk() && this.nread >= MuxableSocketNonBlocking.this.clen;
      }

      private boolean isReady() {
         return MuxableSocketNonBlocking.this.readStateHandler.isReadReady();
      }

      public int read(byte[] b, int off, int len) throws IOException {
         if (b == null) {
            throw new NullPointerException();
         } else if (off >= 0 && len >= 0 && len <= b.length - off) {
            if (len == 0) {
               return 0;
            } else if (!this.isReady()) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("ByteBufferInputStream read(byte b[], int off, int len) throws an exception. Could not read on a not ready stream : nread = " + this.nread);
               }

               MuxableSocketNonBlocking.this.setReayToRead();
               throw new IllegalStateException("Could not read on a not ready stream!");
            } else {
               int readLength = Math.min(this.available(), len);
               this.nread += (long)readLength;
               System.arraycopy(MuxableSocketNonBlocking.this.byteBuffer.array(), MuxableSocketNonBlocking.this.byteBuffer.position(), b, off, readLength);
               MuxableSocketNonBlocking.this.byteBuffer.position(MuxableSocketNonBlocking.this.byteBuffer.position() + readLength);
               if (!MuxableSocketNonBlocking.this.byteBuffer.hasRemaining()) {
                  MuxableSocketNonBlocking.this.setReayToRead();
               }

               if (this.shouldNotifyAllDataRead()) {
                  MuxableSocketNonBlocking.this.readStateHandler.setReadCompleteState();
               }

               return readLength;
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      private void checkAvailable() {
         if (MuxableSocketNonBlocking.this.byteBuffer == null || MuxableSocketNonBlocking.this.byteBuffer.limit() <= MuxableSocketNonBlocking.this.byteBuffer.position()) {
            if (this.shouldNotifyAllDataRead()) {
               MuxableSocketNonBlocking.this.readStateHandler.setReadCompleteState();
            } else {
               MuxableSocketNonBlocking.this.setReayToRead();
            }
         }

      }

      public int available() throws IOException {
         if (!this.isReady()) {
            return 0;
         } else {
            long avai = (long)(MuxableSocketNonBlocking.this.byteBuffer.limit() - MuxableSocketNonBlocking.this.byteBuffer.position());
            if (!MuxableSocketNonBlocking.this.isChunk()) {
               if (MuxableSocketNonBlocking.this.clen != -1L) {
                  avai = Math.min(MuxableSocketNonBlocking.this.clen - this.nread, avai);
               }

               if (avai < -1L) {
                  avai = -1L;
               }
            }

            if (avai <= 0L) {
               if (this.shouldNotifyAllDataRead()) {
                  MuxableSocketNonBlocking.this.readStateHandler.setReadCompleteState();
               } else {
                  MuxableSocketNonBlocking.this.setReayToRead();
               }
            }

            return (int)avai;
         }
      }
   }

   private static enum ChunkStatus {
      INIT,
      FULL_CHUNK,
      PRATIAL_SIZE,
      ZERO_CHUNK,
      PREV_SIZE_REMAIN,
      LAST_CHUNK;
   }
}
