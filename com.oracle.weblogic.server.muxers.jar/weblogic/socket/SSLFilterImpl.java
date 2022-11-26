package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSocket;
import weblogic.security.utils.SSLSetupLogging;
import weblogic.utils.AssertionError;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;

public final class SSLFilterImpl implements SSLFilter {
   private static final boolean ASSERT = true;
   private static final boolean DEBUG = false;
   private boolean activated = false;
   private final InputStream in;
   private Chunk head;
   private Chunk tail;
   private int pos;
   private int availBytes;
   private MuxableSocket delegate;
   private final SSLSocket sslSocket;
   private InputStream clearText;
   private SocketInfo sockInfo;
   private ChunkedInputStream cis = null;

   public SSLFilterImpl(InputStream is, SSLSocket ssl) throws IOException {
      this.in = is;
      this.sslSocket = ssl;
      this.head = this.tail = Chunk.getChunk();
      SSLSetupLogging.info("Filtering JSSE SSLSocket");
   }

   public String toString() {
      return "SSLFilterImpl[" + this.getDelegate() + "]";
   }

   public MuxableSocket getDelegate() {
      return this.delegate;
   }

   public void setDelegate(MuxableSocket mx) {
      this.delegate = mx;
   }

   public void asyncOn() {
      this.activated = true;
   }

   public void asyncOff() {
      this.activated = false;
   }

   public boolean isActivated() {
      return this.activated;
   }

   public ChunkedInputStream getInputStream() throws IOException {
      if (this.cis == null) {
         synchronized(this) {
            if (this.cis == null) {
               this.cis = new ChunkedInputStream(this.head, 0, this.in);
            }

            synchronized(this.cis) {
               ;
            }
         }
      }

      return this.cis;
   }

   public int available() throws IOException {
      return !this.activated ? this.in.available() : this.availBytes;
   }

   public void activate() throws IOException {
      SSLSetupLogging.info("SSLFilteriImpl.activate()");
      this.activateNoRegister();
      if (this.delegate.isMessageComplete()) {
         SocketMuxer.getMuxer().register((MuxableSocket)this);
         this.delegate.dispatch();
      } else {
         SocketMuxer.getMuxer().register((MuxableSocket)this);
         SocketMuxer.getMuxer().read((MuxableSocket)this);
      }

   }

   public void activateNoRegister() throws IOException {
      SSLSetupLogging.info("activateNoRegister()");
      this.clearText = this.sslSocket.getInputStream();
      int clearTextAvail = 0;

      try {
         int nread = false;

         while(clearTextAvail > 0) {
            SSLSetupLogging.info("clearTextAvail = " + clearTextAvail);
            byte[] buf = this.delegate.getBuffer();
            int off = this.delegate.getBufferOffset();
            int nread = this.clearText.read(buf, off, Math.min(buf.length - off, clearTextAvail));
            clearTextAvail -= nread;
            this.delegate.incrementBufferOffset(nread);
         }
      } catch (InterruptedIOException var5) {
      }

      this.activated = true;
      SSLSetupLogging.info("SSLFilterImpl.activate(): activated: " + this.in.hashCode() + " " + this.clearText.hashCode());
   }

   public int getIdleTimeoutMillis() {
      return this.delegate.getIdleTimeoutMillis();
   }

   public byte[] getBuffer() {
      if (this.tail.end == this.tail.buf.length) {
         this.tail.next = Chunk.getChunk();
         this.tail = this.tail.next;
      }

      return this.tail.buf;
   }

   public int getBufferOffset() {
      return this.tail.end;
   }

   private static void p(String s) {
      System.out.println("SSLFilterImpl: " + s);
   }

   public void incrementBufferOffset(int i) throws MaxMessageSizeExceededException {
      this.availBytes += i;
      Chunk var10000 = this.tail;
      var10000.end += i;
   }

   public boolean isMessageComplete() {
      while(true) {
         byte[] b = this.delegate.getBuffer();
         int off = this.delegate.getBufferOffset();

         try {
            int nread = this.clearText.read(b, off, b.length - off);
            if (nread != -1) {
               this.delegate.incrementBufferOffset(nread);
               continue;
            }

            try {
               SocketMuxer.getMuxer().deliverEndOfStream(this);
            } catch (Throwable var5) {
               if (!(var5 instanceof SocketException)) {
                  SocketLogger.logDebugThrowable("isMessageComplete", var5);
               }
            }
         } catch (InterruptedIOException var6) {
         } catch (MaxMessageSizeExceededException var7) {
            SocketMuxer.getMuxer().deliverHasException(this, var7);
         } catch (IOException var8) {
            if (!(var8 instanceof SocketException)) {
               SocketLogger.logDebugThrowable("isMessageComplete", var8);
            }

            SocketMuxer.getMuxer().deliverHasException(this, var8);
            continue;
         } catch (Throwable var9) {
            SocketLogger.logDebugThrowable("isMessageComplete", var9);
            SocketMuxer.getMuxer().deliverHasException(this, var9);
            continue;
         }

         boolean result = this.delegate.isMessageComplete();
         return result;
      }
   }

   public void dispatch() {
      this.delegate.dispatch();
   }

   public InputStream getSocketInputStream() {
      return this.in;
   }

   public void setSoTimeout(int to) throws SocketException {
      this.delegate.setSoTimeout(to);
   }

   public Socket getSocket() {
      return this.sslSocket;
   }

   public boolean closeSocketOnError() {
      return true;
   }

   public void hasException(Throwable t) {
      if (!(t instanceof SocketException)) {
         SSLSetupLogging.debug(3, t, "hasException");
      }

      this.delegate.hasException(t);
   }

   public void endOfStream() {
      this.delegate.endOfStream();
   }

   public boolean timeout() {
      return this.delegate.timeout();
   }

   public boolean requestTimeout() {
      return this.delegate.requestTimeout();
   }

   public int getCompleteMessageTimeoutMillis() {
      return this.delegate.getCompleteMessageTimeoutMillis();
   }

   public void setSocketFilter(MuxableSocket remx) {
      throw new AssertionError("Re-register Muxer not allowed on SSLFilterImpl");
   }

   public MuxableSocket getSocketFilter() {
      return this;
   }

   public void setSocketInfo(SocketInfo info) {
      this.sockInfo = info;
   }

   public SocketInfo getSocketInfo() {
      return this.sockInfo;
   }

   public void ensureForceClose() {
      try {
         this.sslSocket.getOutputStream().close();
      } catch (IOException var2) {
      }

   }

   public boolean supportsScatteredRead() {
      return false;
   }

   public long read(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void incrementBufferOffset(Chunk c, int availBytes) throws MaxMessageSizeExceededException {
      throw new UnsupportedOperationException();
   }

   public ByteBuffer getByteBuffer() {
      return this.delegate.getByteBuffer();
   }
}
