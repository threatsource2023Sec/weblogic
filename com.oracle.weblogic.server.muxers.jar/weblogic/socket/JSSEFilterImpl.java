package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import weblogic.kernel.Kernel;
import weblogic.socket.utils.SSLConfigUtils;
import weblogic.utils.io.Chunk;
import weblogic.utils.net.SocketResetException;

public class JSSEFilterImpl implements MuxableSocket {
   protected SSLEngine sslEngine;
   protected volatile ByteBuffer readNWDataInBuf;
   protected volatile ByteBuffer writeNWDataOutBuf;
   protected volatile ByteBuffer clearTextBuf;
   private volatile boolean initialHSComplete;
   private volatile SSLEngineResult.HandshakeStatus handshakeStatus;
   private Socket sock;
   private MuxableSocket delegate;
   private SocketInfo sockInfo;
   private InputStream in;
   private OutputStream out;
   private Set handshakeCompletedListeners;
   private static final boolean BLOCKING_READ_ALLOWED = true;
   private static final boolean NO_BLOCKING_READ = false;
   private IOException cachedException;
   private Chunk chunkLinkedList;
   private ArrayList byteBufferList;
   private int messageByteProduced;
   private boolean isClientInitSecureRenegotiationAccepted;

   protected JSSEFilterImpl(Socket sock, boolean clientMode) throws IOException {
      this.handshakeCompletedListeners = new HashSet();
      this.cachedException = null;
      this.chunkLinkedList = null;
      this.byteBufferList = new ArrayList();
      this.isClientInitSecureRenegotiationAccepted = false;
      this.sock = sock;
      this.in = sock.getInputStream();
      this.out = sock.getOutputStream();
      this.handshakeStatus = clientMode ? HandshakeStatus.NEED_WRAP : HandshakeStatus.NEED_UNWRAP;
   }

   public JSSEFilterImpl(Socket sock, SSLEngine engine) throws IOException {
      this(sock, engine, false);
   }

   public JSSEFilterImpl(Socket sock, SSLEngine engine, boolean clientMode) throws IOException {
      this(sock, engine, clientMode, false);
   }

   public JSSEFilterImpl(Socket sock, SSLEngine engine, boolean clientMode, boolean isClientInitSecureRenegotiationAccepted) throws IOException {
      this(sock, clientMode);
      this.isClientInitSecureRenegotiationAccepted = isClientInitSecureRenegotiationAccepted;
      this.sslEngine = engine;
      this.sslEngine.setUseClientMode(clientMode);
      this.readNWDataInBuf = ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize() + 50);
      this.writeNWDataOutBuf = ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize() + 50);
      this.clearTextBuf = ByteBuffer.allocate(this.sslEngine.getSession().getApplicationBufferSize() + 50);
   }

   public synchronized boolean doHandshake() throws IOException {
      this.initialHSComplete = false;
      this.handshakeStatus = HandshakeStatus.NEED_WRAP;
      return this.doHandshake((ByteBuffer)null, (MuxableSocket)null, true);
   }

   private synchronized boolean doHandshake(ByteBuffer appInBuf, MuxableSocket ms, boolean blockingNWReadAllowed) throws IOException {
      SSLEngineResult result = null;
      if (this.initialHSComplete) {
         return this.initialHSComplete;
      } else {
         while(this.handshakeStatus != HandshakeStatus.FINISHED && this.handshakeStatus != HandshakeStatus.NOT_HANDSHAKING) {
            switch (this.handshakeStatus) {
               case NEED_UNWRAP:
                  result = this.unwrapAndHandleResults(appInBuf, ms, blockingNWReadAllowed);
                  this.handshakeStatus = result.getHandshakeStatus();
                  if (!blockingNWReadAllowed && result.getStatus() == Status.BUFFER_UNDERFLOW) {
                     return false;
                  }
                  break;
               case NEED_WRAP:
                  result = this.wrapAndWrite(ByteBuffer.allocate(0), blockingNWReadAllowed);
                  this.handshakeStatus = result.getHandshakeStatus();
                  break;
               case NEED_TASK:
                  this.handshakeStatus = this.doTasks();
                  break;
               default:
                  throw new RuntimeException("Invalid Handshaking State" + this.handshakeStatus);
            }

            if (result != null && result.getStatus() == Status.CLOSED) {
               return false;
            }
         }

         this.initialHSComplete = true;
         if (!this.sslEngine.getUseClientMode()) {
            SSLConfigUtils.configureClientInitSecureRenegotiation(this.sslEngine, this.isClientInitSecureRenegotiationAccepted);
         }

         this.notifyHandshakeComplete();
         return this.initialHSComplete;
      }
   }

   private void notifyHandshakeComplete() {
      if (!this.handshakeCompletedListeners.isEmpty()) {
         Iterator var1 = this.handshakeCompletedListeners.iterator();

         while(var1.hasNext()) {
            HandshakeListener listener = (HandshakeListener)var1.next();
            listener.handshakeDone(this.sslEngine.getSession());
         }

      }
   }

   public ByteBuffer getClearTextBuf() {
      return this.clearTextBuf;
   }

   public void addHandshakeCompletedListener(HandshakeListener listener) {
      if (null == listener) {
         throw new IllegalArgumentException("Non-null HandshakeCompletedListener expected.");
      } else {
         this.handshakeCompletedListeners.add(listener);
         if (isLogDebug() && (null == this.delegate || null == this.delegate.getSocket())) {
            SocketLogger.logDebug("No SSLSocket when adding HandshakeCompletedListener: class=" + listener.getClass().getName() + ", instance=" + listener + ", on " + this + ". An associated SSLSocket is required.");
         }

         if (isLogDebug()) {
            SocketLogger.logDebug("Added HandshakeCompletedListener: class=" + listener.getClass().getName() + ", instance=" + listener + ", on " + this + " .");
         }

      }
   }

   public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
      if (null == listener) {
         throw new IllegalArgumentException("Non-null HandshakeCompletedListener expected.");
      } else if (!this.handshakeCompletedListeners.remove(listener)) {
         String msg = SocketLogger.logUnregisteredHandshakeCompletedListener(listener.getClass().getName(), listener.toString());
         throw new IllegalArgumentException(msg);
      } else {
         if (isLogDebug()) {
            SocketLogger.logDebug("Removed HandshakeCompletedListener: class=" + listener.getClass().getName() + ", instance=" + listener + ".");
         }

      }
   }

   private SSLEngineResult.HandshakeStatus doTasks() {
      Runnable runnable;
      while((runnable = this.sslEngine.getDelegatedTask()) != null) {
         runnable.run();
      }

      return this.sslEngine.getHandshakeStatus();
   }

   public boolean closeSocketOnError() {
      return true;
   }

   public void dispatch() {
      this.delegate.dispatch();
   }

   public void endOfStream() {
      this.delegate.endOfStream();
   }

   public byte[] getBuffer() {
      this.growReadInBufAsNeeded();
      return this.readNWDataInBuf.array();
   }

   public int getBufferOffset() {
      return this.readNWDataInBuf.position();
   }

   public int getCompleteMessageTimeoutMillis() {
      return this.delegate.getCompleteMessageTimeoutMillis();
   }

   public int getIdleTimeoutMillis() {
      return this.delegate.getIdleTimeoutMillis();
   }

   public Socket getSocket() {
      return this.sock;
   }

   public MuxableSocket getSocketFilter() {
      return this;
   }

   public SocketInfo getSocketInfo() {
      return this.sockInfo;
   }

   public InputStream getSocketInputStream() {
      return this.in;
   }

   public void hasException(Throwable t) {
      this.delegate.hasException(t);
   }

   public void incrementBufferOffset(int count) throws MaxMessageSizeExceededException {
      this.readNWDataInBuf.position(this.readNWDataInBuf.position() + count);
   }

   public int getAvailableBytes() {
      return this.getClearTextBuf().position();
   }

   private boolean isRehandshakeNeeded(SSLEngineResult result) throws IOException {
      return this.initialHSComplete && result.getStatus() == Status.OK && result.getHandshakeStatus() != HandshakeStatus.NOT_HANDSHAKING && result.getHandshakeStatus() != HandshakeStatus.FINISHED;
   }

   private void adjustBufferOffset(MuxableSocket ms, int bytesProduced, int existingSpace) throws MaxMessageSizeExceededException {
      if (bytesProduced > existingSpace) {
         int remaining = bytesProduced - existingSpace;

         Chunk c;
         for(c = this.chunkLinkedList; remaining > Chunk.CHUNK_SIZE; c = c.next) {
            c.end = Chunk.CHUNK_SIZE;
            remaining -= Chunk.CHUNK_SIZE;
         }

         c.end = remaining;
         if (c.next != null) {
            Chunk.releaseChunks(c.next);
            c.next = null;
         }

         ms.incrementBufferOffset(this.chunkLinkedList, bytesProduced);
      } else {
         Chunk.releaseChunks(this.chunkLinkedList);
         ms.incrementBufferOffset(bytesProduced);
      }

   }

   public boolean isMessageComplete() {
      if (this.delegate.isMessageComplete()) {
         return true;
      } else {
         try {
            synchronized(this) {
               while(this.getAvailableBytes() > 0) {
                  ByteBuffer byteBuffer = this.delegate.getByteBuffer();
                  int filled = this.fillAppBuf(byteBuffer);
                  this.delegate.incrementBufferOffset(filled);
                  this.chunkLinkedList = null;
                  this.byteBufferList.clear();
               }
            }

            int existingSpace = this.delegate.getByteBuffer().remaining();
            this.messageByteProduced = 0;
            if (!this.initialHSComplete && !this.doHandshake((ByteBuffer)null, this.delegate, false)) {
               if (this.messageByteProduced > 0) {
                  this.adjustBufferOffset(this.delegate, this.messageByteProduced, existingSpace);
                  this.chunkLinkedList = null;
                  this.byteBufferList.clear();
               }

               return this.delegate.isMessageComplete();
            } else {
               SSLEngineResult result;
               do {
                  result = this.unwrapAndHandleResults(this.delegate, false);
               } while(this.readNWDataInBuf.position() > 0 && result.getStatus() == Status.OK);

               if (this.messageByteProduced > 0) {
                  this.adjustBufferOffset(this.delegate, this.messageByteProduced, existingSpace);
               }

               this.chunkLinkedList = null;
               this.byteBufferList.clear();
               return this.delegate.isMessageComplete();
            }
         } catch (Exception var6) {
            if (isLogDebug()) {
               SocketLogger.logDebug("Caught sslException: " + var6 + " returning false for isMessageComplete");
            }

            SocketMuxer.getMuxer().deliverHasException(this, var6);
            return false;
         }
      }
   }

   public boolean requestTimeout() {
      return this.delegate.requestTimeout();
   }

   public void setSoTimeout(int timeout) throws SocketException {
      this.delegate.setSoTimeout(timeout);
   }

   public void setSocketFilter(MuxableSocket filter) {
      throw new UnsupportedOperationException("Re-register Muxer not allowed on JSSEFilterImpl");
   }

   public void setSocketInfo(SocketInfo info) {
      this.sockInfo = info;
   }

   public boolean timeout() {
      return this.delegate.timeout();
   }

   public MuxableSocket getDelegate() {
      return this.delegate;
   }

   public void setDelegate(MuxableSocket mx) {
      this.delegate = mx;
   }

   public int read(byte[] buf, int offset, int length) throws IOException {
      if (this.sslEngine.isInboundDone()) {
         this.checkCauseSSLEngineClosed();
      }

      if (length == 0) {
         return 0;
      } else {
         ByteBuffer tmp = ByteBuffer.wrap(buf, offset, length);
         if (this.getAvailableBytes() > 0) {
            return this.fillAppBuf(tmp);
         } else {
            boolean handshakeUnfinished = false;
            if (!this.initialHSComplete && !this.doHandshake(tmp, (MuxableSocket)null, true)) {
               handshakeUnfinished = true;
            }

            int read;
            if (tmp.position() > offset) {
               read = tmp.position() - offset;
               return read;
            } else if (handshakeUnfinished) {
               return 0;
            } else {
               if (this.getAvailableBytes(this.readNWDataInBuf) == 0) {
                  read = this.readFromNetwork();
                  if (read <= -1) {
                     return read;
                  }
               }

               this.unwrapAndHandleResults(tmp, (MuxableSocket)null, true);
               return tmp.position() - offset;
            }
         }
      }
   }

   private void checkCauseSSLEngineClosed() throws IOException {
      if (this.cachedException != null) {
         throw this.cachedException;
      } else {
         throw new SocketResetException("SSLEngine is closed");
      }
   }

   private int getAvailableBytes(ByteBuffer dataBuf) {
      return dataBuf.position();
   }

   public void write(byte[] buf, int off, int len) throws IOException {
      if (this.sslEngine.isOutboundDone()) {
         this.checkCauseSSLEngineClosed();
      }

      if (!this.initialHSComplete && !this.doHandshake()) {
         throw new IOException("Could not complete handshake on engine: " + this.sslEngine);
      } else {
         ByteBuffer tmp = ByteBuffer.wrap(buf, off, len);

         while(tmp.position() < tmp.limit()) {
            this.wrapAndWrite(tmp, true);
         }

      }
   }

   private int readFromNetwork() throws IOException {
      int read = this.in.read(this.readNWDataInBuf.array(), this.readNWDataInBuf.position(), this.readNWDataInBuf.remaining());
      if (read > 0) {
         this.incrementBufferOffset(read);
      }

      return read;
   }

   private SSLEngineResult unwrapAndHandleResults(MuxableSocket ms, boolean blockingReadAllowed) throws IOException {
      return this.unwrapAndHandleResults((ByteBuffer)null, ms, blockingReadAllowed);
   }

   private SSLEngineResult unwrapAndHandleResults(ByteBuffer appInBuf, MuxableSocket ms, boolean blockingNWReadAllowed) throws IOException {
      int size = this.sslEngine.getSession().getApplicationBufferSize();
      int increment = size;
      ByteBuffer[] buffers = this.createBuffers(appInBuf, ms, size);
      SSLEngineResult result = this.unwrap(buffers);

      int bytesProduced;
      for(bytesProduced = result.bytesProduced(); result.getStatus() == Status.BUFFER_OVERFLOW; bytesProduced = result.bytesProduced()) {
         size += increment;
         buffers = this.createBuffers(appInBuf, ms, size);
         result = this.unwrap(buffers);
      }

      if (bytesProduced > 0 && ms != null) {
         this.messageByteProduced += bytesProduced;
      }

      this.handleUnwrapResults(result, appInBuf, ms, blockingNWReadAllowed);
      return result;
   }

   private ByteBuffer[] createBuffers(ByteBuffer appInBuf, MuxableSocket ms, int size) throws IOException {
      ByteBuffer[] buffers;
      if (ms == null) {
         buffers = this.getBufferArray(appInBuf, size);
      } else {
         buffers = this.getAvailableBufferofSize(ms, size);
      }

      return buffers;
   }

   private ByteBuffer[] getAvailableBufferofSize(MuxableSocket ms, int size) {
      int remaining = 0;
      ByteBuffer tmp;
      if (!this.byteBufferList.isEmpty()) {
         for(Iterator var4 = this.byteBufferList.iterator(); var4.hasNext(); remaining += tmp.remaining()) {
            tmp = (ByteBuffer)var4.next();
         }
      } else {
         ByteBuffer byteBuffer = ms.getByteBuffer();
         remaining += byteBuffer.remaining();
         this.byteBufferList.add(byteBuffer);
      }

      while(remaining < size) {
         Chunk tail = this.chunkLinkedList;
         if (this.chunkLinkedList != null) {
            tail = Chunk.tail(tail);
         }

         Chunk c = Chunk.getChunk();
         ByteBuffer byteBuf = c.getReadByteBuffer();
         this.byteBufferList.add(byteBuf);
         remaining += byteBuf.remaining();
         if (this.chunkLinkedList == null) {
            this.chunkLinkedList = c;
         } else {
            tail.next = c;
            tail = tail.next;
         }
      }

      return (ByteBuffer[])this.byteBufferList.toArray(new ByteBuffer[this.byteBufferList.size()]);
   }

   public void incrementBufferOffset(Chunk c, int availBytes) throws MaxMessageSizeExceededException {
      throw new UnsupportedOperationException();
   }

   public ByteBuffer getByteBuffer() {
      this.growReadInBufAsNeeded();
      return this.readNWDataInBuf;
   }

   private ByteBuffer growClearTextBuf(int size) {
      ByteBuffer clearText = this.getClearTextBuf();
      if (clearText.position() == 0) {
         this.clearTextBuf = ByteBuffer.allocate(size);
      } else {
         ByteBuffer tmp = ByteBuffer.allocate(clearText.position() + size);
         clearText.flip();
         this.clearTextBuf = tmp.put(clearText);
      }

      return this.clearTextBuf;
   }

   private ByteBuffer[] getBufferArray(ByteBuffer appInBuf, int size) {
      ByteBuffer clearText = this.getClearTextBuf();
      if (clearText.remaining() < size) {
         clearText = this.growClearTextBuf(size);
      }

      return appInBuf != null ? new ByteBuffer[]{appInBuf, clearText} : new ByteBuffer[]{clearText};
   }

   private SSLEngineResult unwrap(ByteBuffer[] dstBufs) throws IOException {
      this.readNWDataInBuf.flip();

      SSLEngineResult result;
      try {
         result = this.sslEngine.unwrap(this.readNWDataInBuf, dstBufs);
      } catch (SSLException var7) {
         this.cachedException = var7;
         this.cleanupSSLEngine();
         throw var7;
      } finally {
         this.readNWDataInBuf.compact();
      }

      return result;
   }

   private void handleUnwrapResults(SSLEngineResult result, ByteBuffer appInBuf, MuxableSocket ms, boolean blockingNWReadAllowed) throws IOException {
      if (result.getStatus() == Status.BUFFER_UNDERFLOW) {
         if (blockingNWReadAllowed) {
            int read = this.readFromNetwork();
            if (read <= -1) {
               SocketLogger.logDebug("read EOF on socket");
               this.cachedException = new IOException("Connection closed, EOF detected");
               if (!this.sslEngine.isInboundDone()) {
                  this.cleanupSSLEngine();
               }

               throw this.cachedException;
            }
         }
      } else {
         this.handleResultsCommonly(result, appInBuf, ms, blockingNWReadAllowed);
      }

   }

   private void cleanupSSLEngine() {
      this.sslEngine.closeOutbound();

      try {
         this.writeToNetwork();
         this.writeNWDataOutBuf.clear();
         SSLEngineResult result = null;

         while(!this.sslEngine.isOutboundDone()) {
            result = this.sslEngine.wrap(ByteBuffer.allocate(0), this.writeNWDataOutBuf);
            if (result.getStatus() == Status.BUFFER_OVERFLOW) {
               this.writeToNetwork();
            }
         }

         if (result == null || result.getStatus() == Status.OK || result.getStatus() == Status.CLOSED) {
            this.writeToNetwork();
         }
      } catch (IOException var2) {
         if (isLogDebug()) {
            SocketLogger.logDebug("CleanupSSLEngine caught IOExcpetion: " + var2);
         }
      }

   }

   private int fillAppBuf(ByteBuffer dst) {
      int dataRead = false;
      ByteBuffer clearText = this.getClearTextBuf();
      clearText.flip();

      int dataRead;
      try {
         int availSpace = dst.remaining();
         if (availSpace == 0) {
            byte var5 = 0;
            return var5;
         }

         dataRead = Math.min(clearText.remaining(), availSpace);
         dst.put(clearText.array(), clearText.arrayOffset(), dataRead);
         clearText.position(clearText.position() + dataRead);
      } finally {
         clearText.compact();
      }

      return dataRead;
   }

   private SSLEngineResult wrapAndWrite(ByteBuffer appOut, boolean blockingNWReadAllowed) throws IOException {
      SSLEngineResult result;
      try {
         result = this.sslEngine.wrap(appOut, this.writeNWDataOutBuf);
      } catch (SSLException var5) {
         this.cachedException = var5;
         this.cleanupSSLEngine();
         throw var5;
      }

      if (result.getStatus() == Status.BUFFER_UNDERFLOW) {
         throw new IOException("SSLException not enough data in the buffer: " + appOut + " to encrypt!?! Results: " + result);
      } else if (result.getStatus() == Status.BUFFER_OVERFLOW && this.getAvailableBytes(this.writeNWDataOutBuf) == 0) {
         throw new IOException("SSLException, writeNWBuf: " + this.writeNWDataOutBuf + " is not large enough for SSLEngine.unwrapAndHandleResults, results: " + result);
      } else {
         this.writeToNetwork();
         this.handleResultsCommonly(result, (ByteBuffer)null, (MuxableSocket)null, blockingNWReadAllowed);
         return result;
      }
   }

   private void handleResultsCommonly(SSLEngineResult result, ByteBuffer appInBuf, MuxableSocket ms, boolean blockingNWReadAllowed) throws IOException {
      if (result.getStatus() == Status.CLOSED) {
         this.cleanupSSLEngine();
         this.checkCauseSSLEngineClosed();
      } else if (result.getStatus() == Status.OK && this.isRehandshakeNeeded(result)) {
         this.initialHSComplete = false;
         this.handshakeStatus = result.getHandshakeStatus();
         if (!this.doHandshake(appInBuf, ms, blockingNWReadAllowed) && blockingNWReadAllowed) {
            throw new IOException("Requested re-handshake failed: " + result);
         }
      }

   }

   private void writeToNetwork() throws IOException {
      this.writeNWDataOutBuf.flip();

      try {
         if (this.writeNWDataOutBuf.hasRemaining()) {
            this.out.write(this.writeNWDataOutBuf.array(), this.writeNWDataOutBuf.arrayOffset(), this.writeNWDataOutBuf.limit());
         }
      } finally {
         this.writeNWDataOutBuf.clear();
      }

   }

   private void growReadInBufAsNeeded() {
      if (this.readNWDataInBuf.remaining() == 0) {
         ByteBuffer tmp = ByteBuffer.allocate(this.readNWDataInBuf.limit() + Chunk.CHUNK_SIZE);
         this.readNWDataInBuf.flip();
         tmp.put(this.readNWDataInBuf);
         this.readNWDataInBuf = tmp;
      }

   }

   public SSLEngine getSSLEngine() {
      return this.sslEngine;
   }

   public long read(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }

   public boolean supportsScatteredRead() {
      return false;
   }

   public String toString() {
      return super.toString() + " with delegate: " + this.delegate;
   }

   protected static boolean isLogDebug() {
      return Kernel.DEBUG && Kernel.getDebug().getDebugMuxer();
   }

   public interface HandshakeListener {
      void handshakeDone(SSLSession var1);
   }
}
