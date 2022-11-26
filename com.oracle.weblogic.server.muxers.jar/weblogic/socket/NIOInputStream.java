package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import weblogic.kernel.Kernel;

class NIOInputStream extends InputStream implements ScatteringByteChannel, NIOConnection {
   private NIOSocketMuxer nioSocketMuxer;
   private final SocketChannel sc;
   private ByteBuffer lastByteBuffer;
   private byte[] lastByteArray;
   private NetworkInterfaceInfo nwInfo;
   private ByteBuffer[] readByteBufs = new ByteBuffer[1];

   NIOInputStream(NIOSocketMuxer nioSocketMuxer, SocketChannel sc, NetworkInterfaceInfo info) {
      this.nioSocketMuxer = nioSocketMuxer;
      this.sc = sc;
      this.nwInfo = info;
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
         SocketLogger.logDebug("NIOInputStream created");
      }

   }

   public void close() throws IOException {
      if (this.sc.isConnected()) {
         this.sc.close();
      }

      this.lastByteBuffer = null;
      this.lastByteArray = null;
   }

   private ByteBuffer getByteBuffer(byte[] b, int off, int len) {
      if (b != this.lastByteArray) {
         this.lastByteArray = b;
         this.lastByteBuffer = ByteBuffer.wrap(b);
      }

      return (ByteBuffer)this.lastByteBuffer.position(off).limit(off + len);
   }

   public int read() throws IOException {
      byte[] b = new byte[1];
      int nRead = this.read((byte[])b, 0, 1);
      return nRead == 1 ? b[0] : nRead;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (len == 0) {
         return 0;
      } else {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail() && this.sc.isBlocking()) {
            SocketLogger.logDebugThrowable("Unexpected socket channel state", new Exception("The SocketChannel is in  blocking mode!"));
         }

         ByteBuffer buf = this.getByteBuffer(b, off, len);
         int nRead = this.read(buf);
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("NIOInputStream.read: expected to read " + len + " bytes, actually read " + nRead + " bytes");
         }

         return nRead;
      }
   }

   public int readNonBlocking(byte[] b, int off, int len) throws IOException {
      if (len == 0) {
         return 0;
      } else {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail() && this.sc.isBlocking()) {
            SocketLogger.logDebugThrowable("Unexpected socket channel state", new Exception("The SocketChannel is in  blocking mode!"));
         }

         ByteBuffer buf = this.getByteBuffer(b, off, len);
         int nRead = this.sc.read(buf);
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("NIOInputStream.read: expected to read " + len + " bytes, actually read " + nRead + " bytes");
         }

         return nRead;
      }
   }

   public int read(ByteBuffer dst) throws IOException {
      int nRead = this.sc.read(dst);
      if (nRead == 0) {
         this.readByteBufs[0] = dst;
         nRead = (int)this.readInternal(this.readByteBufs, 0, 1);
      }

      return nRead;
   }

   public long read(ByteBuffer[] dsts) throws IOException {
      return this.read((ByteBuffer[])dsts, 0, dsts.length);
   }

   public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
      long nRead = this.sc.read(dsts, offset, length);
      if (nRead == 0L) {
         nRead = this.readInternal(dsts, offset, length);
      }

      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
         SocketLogger.logDebug("NIOInputStream.read: " + nRead + " bytes");
      }

      return nRead;
   }

   private long readInternal(ByteBuffer[] bufs, int offset, int length) throws IOException {
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail() && this.sc.isBlocking()) {
         SocketLogger.logDebugThrowable("Unexpected socket channel state", new Exception("The SocketChannel is in blocking mode!"));
      }

      Selector readSelector = this.nioSocketMuxer.findOrCreateSelector();

      assert this.sc.keyFor(readSelector) == null;

      long timeout = (long)this.sc.socket().getSoTimeout();
      long to = timeout;
      long begin = System.currentTimeMillis();

      try {
         this.sc.register(readSelector, 1);

         while(true) {
            if (readSelector.select(to) > 0) {
               readSelector.selectedKeys().clear();
               long n;
               if ((n = this.sc.read(bufs, offset, length)) != 0L) {
                  long var15 = n;
                  return var15;
               }
            }

            if (timeout != 0L) {
               long end = System.currentTimeMillis();
               to -= end - begin;
               if (to <= 0L) {
                  throw new SocketTimeoutException("Read time out after " + timeout + " millis");
               }

               begin = end;
            }

            if (Thread.interrupted() && Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("NIOInputStream [" + this + "] has been interrupted.");
            }
         }
      } finally {
         this.nioSocketMuxer.release(readSelector);
      }
   }

   public boolean isOpen() {
      return this.sc.isOpen();
   }

   public InetAddress getLocalInetAddress() {
      return this.nwInfo.getLocalInetAddress();
   }

   public int getMTU() {
      return this.nwInfo.getMTU();
   }

   public int getOptimalNumberOfBuffers() {
      return this.nwInfo.getOptimalNumberOfBuffers();
   }

   public boolean supportsScatteredReads() {
      return this.nwInfo.supportsScatteredReads();
   }

   public ScatteringByteChannel getScatteringByteChannel() {
      return this;
   }

   public boolean supportsGatheredWrites() {
      return false;
   }

   public GatheringByteChannel getGatheringByteChannel() {
      throw new UnsupportedOperationException();
   }
}
