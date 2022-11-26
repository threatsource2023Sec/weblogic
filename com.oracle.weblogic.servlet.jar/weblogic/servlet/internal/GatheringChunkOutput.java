package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import weblogic.socket.NIOConnection;
import weblogic.utils.io.Chunk;

public final class GatheringChunkOutput extends ChunkOutput {
   private static final byte[] CRCF = "\r\n".getBytes();
   private LinkedList buffers;
   private ChunkOutput delegate;
   private Chunk currentChunk;
   private Chunk httpHeaders;
   private int currentChunkOffset;

   GatheringChunkOutput(ChunkOutput out) {
      this.delegate = out;
      this.currentChunk = this.delegate.head;
      this.currentChunkOffset = 6;
      this.buffers = new LinkedList();
      this.internalSetFlushStrategy();
   }

   GatheringChunkOutput(GatheringChunkOutput out, ChunkOutput newDelegate) {
      this.delegate = newDelegate;
      this.currentChunk = out.currentChunk;
      this.currentChunkOffset = out.currentChunkOffset;
      this.buffers = out.buffers;
      this.internalSetFlushStrategy();
   }

   public ChunkOutput getDelegate() {
      return this.delegate;
   }

   public void reset() {
      this.delegate.reset();
      this.internalClear();
   }

   public void release() {
      this.delegate.release();
      this.internalClear();
      this.internalRelease();
   }

   public void clearBuffer() {
      this.delegate.clearBuffer();
      this.internalClear();
   }

   public void setBufferSize(int bs) {
      this.delegate.setBufferSize(bs);
      this.internalSetFlushStrategy();
   }

   public void setAutoFlush(boolean b) {
      this.delegate.setAutoFlush(b);
      this.internalSetFlushStrategy();
   }

   protected void flushBufferedDataToChunk() {
      this.delegate.flushBufferedDataToChunk();
      this.flushChunksToByteBuffers();
   }

   public void write(ByteBuffer buf) throws IOException {
      if (!this.delegate.released) {
         this.delegate.flushStrategy.checkOverflow((long)buf.limit());
         this.delegate.flushBufferedDataToChunk();
         this.flushChunksToByteBuffers();
         buf.rewind();
         this.buffers.addLast(buf.duplicate());
         ChunkOutput var10000 = this.delegate;
         var10000.count += buf.limit();
         this.delegate.flushStrategy.checkForFlush();
      }
   }

   public void flush() throws IOException {
      if (!this.delegate.released) {
         this.delegate.flushBufferedDataToChunk();
         if (!this.isHeaderSent()) {
            this.delegate.sos.sendHeaders();
         }

         if (this.delegate.os != null) {
            this.flushChunksToByteBuffers();
            int chunks = this.flushBuffer();
            ChunkOutput var10000 = this.delegate;
            var10000.total += (long)this.delegate.count;
            if (this.delegate.chunking) {
               var10000 = this.delegate;
               var10000.total += (long)(8 * chunks);
            }

            if (this.httpHeaders != null) {
               this.releaseHeaders();
            }

            this.clearBuffer();
         }

         this.delegate.postFlush();
      }
   }

   protected void setHttpHeaders(Chunk httpHeaders) {
      this.httpHeaders = httpHeaders;
   }

   private int flushBuffer() throws IOException {
      int chunkHeaders = this.addChunkingHeaderAndTail();
      if (this.httpHeaders != null) {
         this.flushHeaderToByteBuffers();
      }

      if (this.buffers.isEmpty()) {
         return 0;
      } else {
         int var2;
         try {
            registerToTrigger(this.delegate.os);
            this.writeChunks();
            var2 = chunkHeaders;
         } finally {
            unregisterFromTrigger(this.delegate.os);
         }

         return var2;
      }
   }

   private boolean isHeaderSent() {
      return this.delegate.sos != null && this.delegate.sos.headersSent();
   }

   private int addChunkingHeaderAndTail() {
      if (this.delegate.chunking && !this.buffers.isEmpty()) {
         this.buffers.addFirst(getChunkHeader(this.delegate.count));
         this.buffers.addLast(ByteBuffer.wrap(CRCF));
         return 1;
      } else {
         return 0;
      }
   }

   private void writeChunks() throws IOException {
      if (this.buffers.size() == 1) {
         ByteBuffer buf = (ByteBuffer)this.buffers.getFirst();
         if (buf.hasArray()) {
            this.delegate.os.write(buf.array(), buf.position(), buf.limit() - buf.position());
         } else {
            byte[] bytes = new byte[buf.limit() - buf.position()];
            buf.get(bytes, 0, bytes.length);
            this.delegate.os.write(bytes, 0, bytes.length);
         }
      } else {
         GatheringByteChannel channel = ((NIOConnection)this.delegate.os).getGatheringByteChannel();
         ByteBuffer[] arr = (ByteBuffer[])this.buffers.toArray(new ByteBuffer[this.buffers.size()]);
         channel.write(arr);
      }

      this.buffers.clear();
   }

   private static ByteBuffer getChunkHeader(int size) {
      int maxDigits = size > 65535 ? 8 : 4;
      byte[] headerBuf = new byte[maxDigits + 2];
      int pos = maxDigits;

      do {
         --pos;
         headerBuf[pos] = DIGITS[size & 15];
         size >>>= 4;
      } while(size != 0);

      for(int j = 0; j < pos; ++j) {
         headerBuf[j] = 48;
      }

      headerBuf[maxDigits] = 13;
      headerBuf[maxDigits + 1] = 10;
      return ByteBuffer.wrap(headerBuf);
   }

   private void internalClear() {
      this.currentChunk = this.delegate.head;
      this.buffers.clear();
      this.currentChunkOffset = 6;
      this.releaseHeaders();
   }

   private void releaseHeaders() {
      if (this.httpHeaders != null) {
         Chunk.releaseChunks(this.httpHeaders);
         this.httpHeaders = null;
      }

   }

   private void internalRelease() {
      this.currentChunk = null;
      this.buffers = null;
      this.delegate = null;
   }

   private void internalSetFlushStrategy() {
      if (this.delegate.flushStrategy != null) {
         this.delegate.flushStrategy.setChunkOutput(this);
      }

   }

   private void flushChunksToByteBuffers() {
      while(this.currentChunk != null && this.currentChunk.end > this.currentChunkOffset) {
         this.buffers.addLast(ByteBuffer.wrap(this.currentChunk.buf, this.currentChunkOffset, this.currentChunk.end - this.currentChunkOffset));
         if (this.currentChunk.end == END_OFFSET) {
            this.currentChunkOffset = 6;
            this.currentChunk = this.currentChunk.next;
         } else {
            this.currentChunkOffset = this.currentChunk.end;
         }
      }

   }

   private void flushHeaderToByteBuffers() {
      Chunk header = this.httpHeaders;

      for(int index = 0; header != null; header = header.next) {
         ByteBuffer buf = header.getWriteByteBuffer();
         buf.position(0);
         this.buffers.add(index++, buf);
      }

   }

   public String getEncoding() {
      return this.delegate.getEncoding();
   }

   public Chunk getHead() {
      return this.delegate.getHead();
   }

   public long getTotal() {
      return this.delegate.getTotal();
   }

   public int getCount() {
      return this.delegate.getCount();
   }

   public int getBufferSize() {
      return this.delegate.getBufferSize();
   }

   public void setStickyBufferSize(boolean b) {
      this.delegate.setStickyBufferSize(b);
   }

   public boolean isAutoFlush() {
      return this.delegate.isAutoFlush();
   }

   public boolean isChunking() {
      return this.delegate.isChunking();
   }

   public void setChunking(boolean b) {
      this.delegate.setChunking(b);
   }

   void writeByte(int i) throws IOException {
      this.delegate.writeByte(i);
   }

   public void write(int i) throws IOException {
      this.delegate.write(i);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.delegate.write(b, off, len);
   }

   public void write(char[] c, int off, int len) throws IOException {
      this.delegate.write(c, off, len);
   }

   public void print(String s) throws IOException {
      this.delegate.print(s);
   }

   public void println(String s) throws IOException {
      this.delegate.println(s);
   }

   public void println() throws IOException {
      this.delegate.println();
   }

   public void commit() throws IOException {
      this.delegate.commit();
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
      this.delegate.writeStream(is, len, clen);
   }

   protected int getCountForCheckOverflow() {
      return this.delegate.getCountForCheckOverflow();
   }
}
