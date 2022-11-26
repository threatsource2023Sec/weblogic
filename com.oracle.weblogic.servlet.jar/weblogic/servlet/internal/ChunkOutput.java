package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import weblogic.management.configuration.DomainMBean;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOOutputSink;
import weblogic.socket.WriteHandler;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;
import weblogic.utils.http.BytesToString;
import weblogic.utils.io.Chunk;

public class ChunkOutput {
   private static final boolean ASSERT = true;
   protected static final int CHUNK_HEADER_SIZE = 6;
   protected static final int CHUNK_TRAILER_SIZE = 2;
   protected static final int CHUNK_SIZE;
   protected static final int END_OFFSET;
   static final boolean USE_JDK_ENCODER_FOR_ASCII;
   protected static final byte[] DIGITS;
   protected static final int UNLIMITED_BUFFER = -1;
   protected static final int NO_BUFFER = 0;
   protected Chunk head;
   protected Chunk tail;
   protected long total;
   protected int count;
   protected int buflimit;
   protected OutputStream os;
   protected ServletOutputStreamImpl sos;
   protected boolean autoflush;
   protected boolean stickyBufferSize = false;
   protected boolean chunking;
   protected boolean alwaysFlush;
   protected boolean released;
   protected BufferFlushStrategy flushStrategy;
   private WriteListenerStateContext writeStateContext = null;
   private static CompleteMessageTimeoutTrigger trigger;

   protected ChunkOutput(int bs, boolean af, OutputStream os, ServletOutputStreamImpl sos) {
      this.setBufferSize(bs);
      this.autoflush = af;
      this.os = os;
      this.count = 0;
      this.total = 0L;
      this.head = this.tail = Chunk.getChunk();
      this.head.end = 6;
      this.sos = sos;
      if (os != null && sos == null) {
         this.alwaysFlush = true;
      }

      this.setBufferFlushStrategy();
   }

   protected ChunkOutput() {
   }

   protected ChunkOutput(ChunkOutput orig) {
      this.head = orig.head;
      this.tail = orig.tail;
      this.total = orig.total;
      this.count = orig.count;
      this.buflimit = orig.buflimit;
      this.os = orig.os;
      this.sos = orig.sos;
      this.autoflush = orig.autoflush;
      this.stickyBufferSize = orig.stickyBufferSize;
      this.chunking = orig.chunking;
      this.alwaysFlush = orig.alwaysFlush;
      orig.head = orig.tail = null;
      this.setBufferFlushStrategy();
   }

   public static ChunkOutput create(int bufsize, boolean autoflush, OutputStream os, ServletOutputStreamImpl sos) {
      if (os == null && bufsize != -1) {
         throw new IllegalArgumentException("cannot have no outputstream and a limited buffer size");
      } else if (USE_JDK_ENCODER_FOR_ASCII) {
         try {
            return (ChunkOutput)(ServletOutputStreamImpl.supportsGatheredWrites(os) ? new GatheringChunkOutput(createCharsetChunkOutput(bufsize, autoflush, os, sos, (String)null, (CharsetMap)null)) : createCharsetChunkOutput(bufsize, autoflush, os, sos, (String)null, (CharsetMap)null));
         } catch (UnsupportedEncodingException var5) {
            throw new IllegalArgumentException(var5.getMessage());
         }
      } else {
         return (ChunkOutput)(ServletOutputStreamImpl.supportsGatheredWrites(os) ? new GatheringChunkOutput(new ChunkOutput(bufsize, autoflush, os, sos)) : new ChunkOutput(bufsize, autoflush, os, sos));
      }
   }

   public static ChunkOutput create(ChunkOutput orig, String encoding, CharsetMap csm) throws UnsupportedEncodingException {
      orig.flushBufferedDataToChunk();
      if (!USE_JDK_ENCODER_FOR_ASCII && BytesToString.is8BitUnicodeSubset(encoding)) {
         if (orig instanceof GatheringChunkOutput) {
            GatheringChunkOutput gco = (GatheringChunkOutput)orig;
            return new GatheringChunkOutput(gco, new ChunkOutput(gco.getDelegate()));
         } else {
            return new ChunkOutput(orig);
         }
      } else {
         Charset cs = getCharset(encoding, csm);
         ChunkOutput ret = null;
         if (orig instanceof GatheringChunkOutput) {
            GatheringChunkOutput gco = (GatheringChunkOutput)orig;
            ret = new GatheringChunkOutput(gco, new CharsetChunkOutput(gco.getDelegate(), cs, encoding == null));
            gco.getDelegate().head = gco.getDelegate().tail = null;
         } else {
            ret = new CharsetChunkOutput(orig, cs, encoding == null);
            orig.head = orig.tail = null;
         }

         return (ChunkOutput)ret;
      }
   }

   public static ChunkOutput create(int bufsize, boolean autoflush, OutputStream os, ServletOutputStreamImpl sos, String encoding, CharsetMap csm) throws UnsupportedEncodingException {
      if (!USE_JDK_ENCODER_FOR_ASCII && BytesToString.is8BitUnicodeSubset(encoding)) {
         return create(bufsize, autoflush, os, sos);
      } else {
         return (ChunkOutput)(ServletOutputStreamImpl.supportsGatheredWrites(os) ? new GatheringChunkOutput(createCharsetChunkOutput(bufsize, autoflush, os, sos, encoding, csm)) : createCharsetChunkOutput(bufsize, autoflush, os, sos, encoding, csm));
      }
   }

   private static CharsetChunkOutput createCharsetChunkOutput(int bufsize, boolean autoflush, OutputStream os, ServletOutputStreamImpl sos, String encoding, CharsetMap csm) throws UnsupportedEncodingException {
      Charset cs = getCharset(encoding, csm);
      return new CharsetChunkOutput(bufsize, autoflush, os, sos, cs, encoding == null);
   }

   private static Charset getCharset(String encoding, CharsetMap csm) throws UnsupportedEncodingException {
      encoding = encoding == null ? "ISO-8859-1" : encoding;
      String javaEnc = csm != null ? csm.getJavaCharset(encoding) : encoding;
      if (!Charset.isSupported(javaEnc)) {
         throw new UnsupportedEncodingException(javaEnc);
      } else {
         return Charset.forName(javaEnc);
      }
   }

   public String getEncoding() {
      return null;
   }

   public void reset() {
      this.clearBuffer();
      this.count = 0;
      this.total = 0L;
   }

   public void release() {
      Chunk.releaseChunks(this.head);
      this.head = this.tail = null;
      this.released = true;
   }

   public Chunk getHead() {
      return this.head;
   }

   public long getTotal() {
      return this.total;
   }

   public int getCount() {
      return this.count;
   }

   public OutputStream getOutput() {
      return this.os;
   }

   public int getBufferSize() {
      return this.buflimit;
   }

   public void setBufferSize(int bs) {
      if (!this.stickyBufferSize) {
         if (bs == -1) {
            this.buflimit = -1;
            this.setBufferFlushStrategy();
         } else if (bs == 0) {
            this.buflimit = 0;
            this.setBufferFlushStrategy();
         } else {
            int numBufs = bs / CHUNK_SIZE;
            if (bs % CHUNK_SIZE != 0) {
               ++numBufs;
            }

            this.buflimit = numBufs * CHUNK_SIZE;
            this.setBufferFlushStrategy();
         }
      }
   }

   public void setStickyBufferSize(boolean b) {
      this.stickyBufferSize = b;
   }

   public boolean isAutoFlush() {
      return this.autoflush;
   }

   public void setAutoFlush(boolean b) {
      if (this.os != null) {
         this.autoflush = b;
      } else {
         this.autoflush = false;
      }

      this.setBufferFlushStrategy();
   }

   public boolean isChunking() {
      return this.chunking;
   }

   public void setChunking(boolean b) {
      this.chunking = b;
   }

   void writeByte(int i) throws IOException {
      this.write(i);
   }

   public void write(int i) throws IOException {
      if (!this.released) {
         this.flushStrategy.checkOverflow(1L);
         ++this.count;
         this.tail = ensureCapacity(this.tail);
         this.tail.buf[this.tail.end++] = (byte)i;
         this.flushStrategy.checkForFlush();
      }
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (!this.released) {
         while(len > 0) {
            this.tail = ensureCapacity(this.tail);
            int toCopy = Math.min(END_OFFSET - this.tail.end, len);
            this.flushStrategy.checkOverflow((long)toCopy);
            System.arraycopy(b, off, this.tail.buf, this.tail.end, toCopy);
            this.count += toCopy;
            Chunk var10000 = this.tail;
            var10000.end += toCopy;
            off += toCopy;
            len -= toCopy;
            this.flushStrategy.checkForFlush();
         }

      }
   }

   public void write(ByteBuffer buf) throws IOException {
      if (buf.hasArray()) {
         this.write(buf.array(), buf.position(), buf.limit() - buf.position());
      } else {
         byte[] bytes = new byte[buf.limit() - buf.position()];
         buf.get(bytes, 0, bytes.length);
         this.write((byte[])bytes, 0, bytes.length);
      }

   }

   public void write(char[] c, int off, int len) throws IOException {
      if (!this.released) {
         while(len > 0) {
            this.tail = ensureCapacity(this.tail);
            int toCopy = Math.min(END_OFFSET - this.tail.end, len);
            this.flushStrategy.checkOverflow((long)toCopy);

            for(int i = 0; i < toCopy; ++i) {
               this.tail.buf[this.tail.end++] = (byte)c[off++];
            }

            this.count += toCopy;
            len -= toCopy;
            this.flushStrategy.checkForFlush();
         }

      }
   }

   public void print(String s) throws IOException {
      if (!this.released) {
         if (s != null) {
            int len = s.length();
            int off = 0;
            this.flushStrategy.checkOverflow((long)len);

            while(len > 0) {
               this.tail = ensureCapacity(this.tail);
               int toCopy = Math.min(len, END_OFFSET - this.tail.end);
               StringUtils.getBytes(s, off, off + toCopy, this.tail.buf, this.tail.end);
               off += toCopy;
               Chunk var10000 = this.tail;
               var10000.end += toCopy;
               this.count += toCopy;
               len -= toCopy;
               this.flushStrategy.checkForFlush();
            }

         }
      }
   }

   public void println(String s) throws IOException {
      this.print(s);
      this.println();
   }

   public void println() throws IOException {
      this.print("\r\n");
   }

   public void commit() throws IOException {
      if (this.sos != null) {
         this.sos.commit();
      }

   }

   public synchronized void clearBuffer() {
      if (this.head != null) {
         Chunk.releaseChunks(this.head.next);
         this.tail = this.head;
         this.head.next = null;
         this.head.end = 6;
         this.count = 0;
      }
   }

   public void flush() throws IOException {
      if (!this.released) {
         if (this.sos != null && !this.sos.headersSent()) {
            this.sos.sendHeaders();
         }

         boolean useGzip = this.sos.useGzip();
         if (useGzip) {
            this.os = this.sos.createGzipOutput();
         } else {
            this.os = this.sos.getRawOutputStream();
         }

         if (this.os != null) {
            int chunks = writeChunks(this.head, this.os, this.chunking, useGzip);
            this.total += (long)this.count;
            if (this.chunking) {
               int tmpSize = 8 * chunks;
               this.total += (long)tmpSize;
            }

            this.clearBuffer();
            this.os.flush();
         }

      }
   }

   void postFlush() {
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
      if (!this.released) {
         if (clen == -1L && len == -1L) {
            this.writeStream(is);
         } else {
            if (len == -1L) {
               len = clen;
            }

            while(len > 0L) {
               this.tail = ensureCapacity(this.tail);
               int toRead = (int)Math.min(len, (long)(END_OFFSET - this.tail.end));
               if (clen != -1L && this.total + (long)this.count + (long)toRead > clen) {
                  throw new ProtocolException("Exceeded stated content length of " + clen);
               }

               this.flushStrategy.checkOverflow((long)toRead);
               int read = is.read(this.tail.buf, this.tail.end, toRead);
               if (read == -1) {
                  throw new IOException("failed to read '" + toRead + "' bytes from InputStream; clen: " + clen + " remaining: " + len + " count: " + this.count);
               }

               this.count += read;
               Chunk var10000 = this.tail;
               var10000.end += read;
               this.flushStrategy.checkForFlush();
               len -= (long)read;
            }

         }
      }
   }

   protected void flushBufferedDataToChunk() {
   }

   protected void setHttpHeaders(Chunk httpHeaders) {
   }

   private void writeStream(InputStream is) throws IOException {
      while(true) {
         this.tail = ensureCapacity(this.tail);
         int toRead = END_OFFSET - this.tail.end;
         this.flushStrategy.checkOverflow((long)toRead);
         int read = is.read(this.tail.buf, this.tail.end, toRead);
         if (read == -1) {
            return;
         }

         Chunk var10000 = this.tail;
         var10000.end += read;
         this.count += read;
         this.flushStrategy.checkForFlush();
      }
   }

   private static void writeChunkHeader(byte[] b, int i) {
      Debug.assertion(i <= CHUNK_SIZE);
      int pos = 4;

      do {
         --pos;
         b[pos] = DIGITS[i & 15];
         i >>>= 4;
      } while(i != 0);

      for(int j = 0; j < pos; ++j) {
         b[j] = 48;
      }

      b[4] = 13;
      b[5] = 10;
   }

   protected static int writeChunks(Chunk c, OutputStream os, boolean chunkXfer, boolean useGzip) throws IOException {
      int var4;
      try {
         registerToTrigger(os);
         if (!chunkXfer) {
            var4 = writeChunkNoTransfer(c, os);
            return var4;
         }

         var4 = writeChunkTransfer(c, os, useGzip);
      } finally {
         unregisterFromTrigger(os);
      }

      return var4;
   }

   protected static void registerToTrigger(OutputStream os) {
      trigger.register(os);
   }

   protected static void unregisterFromTrigger(OutputStream os) {
      trigger.unregister(os);
   }

   private static boolean isUseJdkEncoderForAscii() {
      DomainMBean domain = WebServerRegistry.getInstance().getManagementProvider().getDomainMBean();
      boolean useJDKEncoderForASCII = Boolean.getBoolean("weblogic.servlet.useJDKEncoderForASCII");
      return domain.isExalogicOptimizationsEnabled() && useJDKEncoderForASCII;
   }

   private static int writeChunkTransfer(Chunk c, OutputStream os, boolean useGzip) throws IOException {
      int chunks = 0;

      while(c != null) {
         int chunkSize = c.end - 6;
         if (chunkSize <= 0) {
            c = c.next;
         } else {
            if (useGzip) {
               os.write(c.buf, 6, chunkSize);
            } else {
               writeChunkHeader(c.buf, chunkSize);
               c.buf[c.end] = 13;
               c.buf[c.end + 1] = 10;
               os.write(c.buf, 0, c.end + 2);
            }

            Debug.assertion(c != c.next);
            c = c.next;
            ++chunks;
         }
      }

      return chunks;
   }

   private static int writeChunkNoTransfer(Chunk c, OutputStream os) throws IOException {
      int chunks;
      for(chunks = 0; c != null; ++chunks) {
         int toWrite;
         for(int nwrote = 6; nwrote < c.end; nwrote += toWrite) {
            toWrite = Math.min(Chunk.CHUNK_SIZE, c.end - nwrote);
            Debug.assertion(toWrite >= 0);
            os.write(c.buf, nwrote, toWrite);
         }

         c = c.next;
      }

      return chunks;
   }

   protected static Chunk ensureCapacity(Chunk c) {
      if (c.end == END_OFFSET) {
         c.next = Chunk.getChunk();
         c.next.end = 6;
         return c.next;
      } else {
         return c;
      }
   }

   protected void switchToNonBlockingStrategy() {
      this.flushStrategy = new BufferFlushStrategy(this) {
         public final void checkOverflow(long len) throws IOException {
         }

         public final void checkForFlush() throws IOException {
            if (ChunkOutput.this.getCountForCheckOverflow() >= ChunkOutput.this.buflimit && ChunkOutput.this.writeStateContext.isWriteReady()) {
               ChunkOutput.this.writeStateContext.setWriteWaitState();
            }

         }
      };
      if (this.getCountForCheckOverflow() >= this.buflimit && this.writeStateContext.isWriteReady()) {
         this.writeStateContext.setWriteWaitState();
      }

   }

   void setWriteStateContext(WriteListenerStateContext writeStateContext, MuxableSocket ms) {
      this.writeStateContext = writeStateContext;
      if (writeStateContext != null) {
         this.switchToNonBlockingStrategy();
         this.configureNonBlocking(ms);
      } else {
         this.configureBlocking();
      }

   }

   private void configureNonBlocking(MuxableSocket ms) {
      ((NIOOutputSink)this.os).configureNonBlocking(ms);
   }

   private void configureBlocking() {
      try {
         ((NIOOutputSink)this.os).configureBlocking();
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   boolean canWrite() {
      return ((NIOOutputSink)this.os).canWrite();
   }

   public void notifyWritePossible(WriteHandler writeHandler) {
      ((NIOOutputSink)this.os).notifyWritePossible(this.writeStateContext);
   }

   protected void setBufferFlushStrategy() {
      if (this.buflimit == -1) {
         this.flushStrategy = new BufferFlushStrategy(this) {
            public final void checkOverflow(long len) throws IOException {
            }

            public final void checkForFlush() throws IOException {
            }
         };
      } else if (this.autoflush) {
         this.flushStrategy = new BufferFlushStrategy(this) {
            public final void checkOverflow(long len) throws IOException {
            }

            public final void checkForFlush() throws IOException {
               if (ChunkOutput.this.getCountForCheckOverflow() >= ChunkOutput.this.buflimit) {
                  this.out.flush();
               }

            }
         };
      } else if (!this.alwaysFlush && this.buflimit != 0) {
         this.flushStrategy = new BufferFlushStrategy(this) {
            public final void checkOverflow(long len) throws IOException {
               if ((long)ChunkOutput.this.getCountForCheckOverflow() + len > (long)ChunkOutput.this.buflimit) {
                  throw new IOException("Exceeded buffer size of: '" + ChunkOutput.this.buflimit + "', and autoflush is: 'false'");
               }
            }

            public final void checkForFlush() throws IOException {
            }
         };
      } else {
         this.flushStrategy = new BufferFlushStrategy(this) {
            public final void checkOverflow(long len) throws IOException {
            }

            public final void checkForFlush() throws IOException {
               this.out.flush();
            }
         };
      }
   }

   protected int getCountForCheckOverflow() {
      return this.count;
   }

   static {
      CHUNK_SIZE = Chunk.CHUNK_SIZE - 6 - 2;
      END_OFFSET = Chunk.CHUNK_SIZE - 2;
      USE_JDK_ENCODER_FOR_ASCII = isUseJdkEncoderForAscii();
      DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
      trigger = null;
      trigger = new CompleteMessageTimeoutTrigger();
   }

   protected abstract class BufferFlushStrategy {
      ChunkOutput out;

      public BufferFlushStrategy(ChunkOutput co) {
         this.out = co;
      }

      void setChunkOutput(ChunkOutput co) {
         this.out = co;
      }

      abstract void checkOverflow(long var1) throws IOException;

      abstract void checkForFlush() throws IOException;
   }
}
