package weblogic.jms.common;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import weblogic.utils.io.ByteBufferObjectOutputStream;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataInputStream;
import weblogic.utils.io.ChunkedDataOutputStream;

class PayloadChunkBase implements PayloadText, PayloadStream {
   private static int DOUBLE_CHUNK_SIZE;
   protected final Chunk chunk;

   PayloadChunkBase(Chunk chunk) {
      this.chunk = chunk;
   }

   private static Chunk safeShareChunks(Chunk source) {
      if (source.next == null && source.end != 0 && !source.isReadOnlySharedBuf()) {
         return source.getSharedBeforeTailCopy((Chunk)null);
      } else {
         Chunk firstDest = source.createOneSharedChunk();
         source = source.next;

         for(Chunk dest = firstDest; source != null; dest = dest.next) {
            if (source.end != 0) {
               if (source.next == null && !source.isReadOnlySharedBuf()) {
                  dest.next = source.getSharedBeforeTailCopy((Chunk)null);
                  return firstDest;
               }

               dest.next = source.createOneSharedChunk();
            }

            source = source.next;
         }

         return firstDest;
      }
   }

   private static Chunk sharedChunksForWrite(Chunk chunkArg) {
      Chunk sharedChunks = safeShareChunks(chunkArg);
      Chunk tail = Chunk.tail(sharedChunks);
      if (tail.isReadOnlySharedBuf()) {
         tail.next = Chunk.getChunk();
      }

      return sharedChunks;
   }

   public BufferInputStream getInputStream() throws IOException {
      Chunk localChunks = safeShareChunks(this.chunk);
      ChunkedDataInputStream cdis = new ChunkedDataInputStream(localChunks, 0);
      return new BufferInputStreamChunked((ObjectIOBypass)null, cdis);
   }

   public int getLength() {
      return Chunk.size(this.chunk);
   }

   public String readUTF8() throws IOException {
      int len = this.getLength();
      byte[] buf = new byte[4];
      int pos = 0;
      buf[pos++] = (byte)(len >>> 24);
      buf[pos++] = (byte)(len >>> 16);
      buf[pos++] = (byte)(len >>> 8);
      buf[pos++] = (byte)(len >>> 0);
      Chunk headerChunk = Chunk.createSharedChunk(buf, 4);
      headerChunk.next = safeShareChunks(this.chunk);
      ChunkedDataInputStream cdis = new ChunkedDataInputStream(headerChunk, 0);
      cdis.mark(0);
      return cdis.readUTF8();
   }

   public PayloadText copyPayloadWithoutSharedText() throws JMSException {
      return new PayloadChunkBase(copyWithoutSharedData(this.chunk));
   }

   public PayloadStream copyPayloadWithoutSharedStream() throws JMSException {
      return new PayloadChunkBase(copyWithoutSharedData(this.chunk));
   }

   public void writeTo(OutputStream out) throws IOException {
      for(Chunk current = this.chunk; current != null; current = current.next) {
         if (current.end > 0) {
            out.write(current.buf, 0, current.end);
         }
      }

   }

   public void writeLengthAndData(DataOutput out) throws IOException {
      internalWriteLengthAndData(out, this.chunk);
   }

   static Chunk copyWithoutSharedData(Chunk localChunk) throws JMSException {
      int len = 0;

      for(Chunk c = localChunk; c != null; c = c.next) {
         len += c.end;
      }

      ChunkedDataInputStream cdis = new ChunkedDataInputStream(localChunk, 0);
      cdis.mark(0);

      try {
         return copyIntoSharedChunks(cdis, len);
      } catch (IOException var4) {
         throw new JMSException(var4);
      }
   }

   static void internalWriteLengthAndData(DataOutput out, Chunk chunkArg) throws IOException {
      int len = 0;

      Chunk sharedChunks;
      for(sharedChunks = chunkArg; sharedChunks != null; sharedChunks = sharedChunks.next) {
         len += sharedChunks.end;
      }

      if (len > PayloadFactoryImpl.CHUNK_LINK_THRESHOLD) {
         if (out instanceof ChunkedDataOutputStream) {
            sharedChunks = sharedChunksForWrite(chunkArg);
            ((ChunkedDataOutputStream)out).writeChunks(sharedChunks);
            return;
         }

         if (out instanceof ByteBufferObjectOutputStream) {
            sharedChunks = safeShareChunks(chunkArg);
            writeLengthLinkByteBuffers((ByteBufferObjectOutputStream)out, sharedChunks);
            return;
         }
      }

      out.writeInt(len);

      for(; chunkArg != null; chunkArg = chunkArg.next) {
         if (chunkArg.end != 0) {
            out.write(chunkArg.buf, 0, chunkArg.end);
         }
      }

   }

   private static void writeLengthLinkByteBuffers(ByteBufferObjectOutputStream out, Chunk sharedChunks) throws IOException {
      int len = 0;
      int index = 0;

      for(Chunk c = sharedChunks; c != null; c = c.next) {
         if (c.end > 0) {
            len += c.end;
            ++index;
         }
      }

      out.writeInt(len);
      if (len != 0) {
         ByteBuffer[] readOnlyBB = new ByteBuffer[index];
         index = 0;

         for(Chunk c = sharedChunks; c != null; c = c.next) {
            if (c.end > 0) {
               ByteBuffer b = c.wrapAsReadOnlyByteBuffer();
               b.position(c.end);
               readOnlyBB[index++] = b;
            }
         }

         out.addReadOnlyBuffers(readOnlyBB);
      }
   }

   static final Chunk linkAndCopyChunksWithoutWastedMemory(ChunkedDataInputStream cdis, int len) throws IOException {
      Chunk head = cdis.getChunks();
      int chunkPos = cdis.getChunkPos();
      Chunk first;
      if (chunkPos != 0) {
         int fromHead = head.end - chunkPos;
         len -= fromHead;
         first = copyIntoSharedChunks(cdis, fromHead);
      } else {
         first = null;
      }

      Chunk tail = first;
      head = cdis.getChunks();
      chunkPos = cdis.getChunkPos();

      while(true) {
         while(len > 0) {
            if (head.end == chunkPos) {
               head = head.next;
               chunkPos = 0;
            } else {
               assert chunkPos == 0;

               Chunk chunk;
               Chunk newTail;
               if (len >= head.end && head.end == head.buf.length) {
                  newTail = chunk = head.createOneSharedChunk();
                  len -= chunk.end;
                  cdis.skip((long)chunk.end);
               } else {
                  int size = Math.min(len, head.end);
                  len -= size;
                  chunk = copyIntoSharedChunks(cdis, size);
                  newTail = Chunk.tail(chunk);
               }

               if (first == null) {
                  first = chunk;
               } else {
                  tail.next = chunk;
               }

               tail = newTail;
               head = cdis.getChunks();
               chunkPos = cdis.getChunkPos();
            }
         }

         if (first == null) {
            first = Chunk.createOneSharedChunk(cdis, 0);
         }

         return first;
      }
   }

   static Chunk copyIntoSharedChunks(InputStream inputStream, int len) throws IOException {
      int leftover = len % Chunk.CHUNK_SIZE;
      int toRead = Math.min(len, Chunk.CHUNK_SIZE + leftover);
      len -= toRead;
      Chunk first = Chunk.createOneSharedChunk(inputStream, toRead);

      for(Chunk tail = first; len > 0; tail = tail.next) {
         toRead = Math.min(len, Chunk.CHUNK_SIZE);
         len -= toRead;
         tail.next = Chunk.createOneSharedChunk(inputStream, toRead);
      }

      return first;
   }

   static {
      DOUBLE_CHUNK_SIZE = Chunk.CHUNK_SIZE + Chunk.CHUNK_SIZE;
   }
}
