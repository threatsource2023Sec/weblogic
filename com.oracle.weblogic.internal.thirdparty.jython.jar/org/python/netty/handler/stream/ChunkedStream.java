package org.python.netty.handler.stream;

import java.io.InputStream;
import java.io.PushbackInputStream;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelHandlerContext;

public class ChunkedStream implements ChunkedInput {
   static final int DEFAULT_CHUNK_SIZE = 8192;
   private final PushbackInputStream in;
   private final int chunkSize;
   private long offset;
   private boolean closed;

   public ChunkedStream(InputStream in) {
      this(in, 8192);
   }

   public ChunkedStream(InputStream in, int chunkSize) {
      if (in == null) {
         throw new NullPointerException("in");
      } else if (chunkSize <= 0) {
         throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
      } else {
         if (in instanceof PushbackInputStream) {
            this.in = (PushbackInputStream)in;
         } else {
            this.in = new PushbackInputStream(in);
         }

         this.chunkSize = chunkSize;
      }
   }

   public long transferredBytes() {
      return this.offset;
   }

   public boolean isEndOfInput() throws Exception {
      if (this.closed) {
         return true;
      } else {
         int b = this.in.read();
         if (b < 0) {
            return true;
         } else {
            this.in.unread(b);
            return false;
         }
      }
   }

   public void close() throws Exception {
      this.closed = true;
      this.in.close();
   }

   /** @deprecated */
   @Deprecated
   public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
      return this.readChunk(ctx.alloc());
   }

   public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
      if (this.isEndOfInput()) {
         return null;
      } else {
         int availableBytes = this.in.available();
         int chunkSize;
         if (availableBytes <= 0) {
            chunkSize = this.chunkSize;
         } else {
            chunkSize = Math.min(this.chunkSize, this.in.available());
         }

         boolean release = true;
         ByteBuf buffer = allocator.buffer(chunkSize);

         ByteBuf var6;
         try {
            this.offset += (long)buffer.writeBytes((InputStream)this.in, chunkSize);
            release = false;
            var6 = buffer;
         } finally {
            if (release) {
               buffer.release();
            }

         }

         return var6;
      }
   }

   public long length() {
      return -1L;
   }

   public long progress() {
      return this.offset;
   }
}
