package weblogic.servlet.internal;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.utils.collections.PartitionedStackPool;
import weblogic.utils.collections.Pool;

public class CharChunk {
   public static final int CHUNK_SIZE = getIntegerProperty("weblogic.servlet.internal.CharChunk.ChunkSize", 8192);
   public static final int JSPCHUNK_SIZE = getIntegerProperty("weblogic.servlet.internal.CharChunk.JspChunkSize", 8192);
   private static final int TOTAL_CHUNKS = getIntegerProperty("weblogic.servlet.internal.CharChunk.TotalChunks", 256);
   private static final int CHUNK_PARTITIONS = getIntegerProperty("weblogic.servlet.internal.CharChunk.ChunkPartitions", 4);
   private static final Pool chunkPool;
   public final char[] buf;
   public int end;
   public CharChunk next;
   private CharBuffer charBuffer;

   public static CharChunk getChunk() {
      CharChunk chunk = null;
      chunk = (CharChunk)chunkPool.remove();
      return chunk == null ? new CharChunk() : chunk;
   }

   public static CharChunk getJspCharChunk() {
      CharChunk chunk = (CharChunk)CharChunk.JspChunkPoolHolder.chunkPool.remove();
      return chunk == null ? new CharChunk(JSPCHUNK_SIZE) : chunk;
   }

   public static void releaseChunk(CharChunk chunk) {
      chunk.end = 0;
      chunk.next = null;
      chunk.charBuffer.clear();
      chunkPool.add(chunk);
   }

   public static void releaseChunks(CharChunk chunk) {
      while(chunk != null) {
         CharChunk temp = chunk.next;
         releaseChunk(chunk);
         chunk = temp;
      }

   }

   public static void releaseJspChunk(CharChunk chunk) {
      chunk.end = 0;
      chunk.next = null;
      chunk.charBuffer.clear();
      CharChunk.JspChunkPoolHolder.chunkPool.add(chunk);
   }

   public static void releaseJspChunks(CharChunk chunk) {
      while(chunk != null) {
         CharChunk temp = chunk.next;
         releaseJspChunk(chunk);
         chunk = temp;
      }

   }

   public static CharChunk tail(CharChunk chunk) {
      CharChunk tmp;
      for(tmp = chunk; tmp.next != null; tmp = tmp.next) {
      }

      return tmp;
   }

   public static CharChunk ensureCapacity(CharChunk chunk) {
      if (CHUNK_SIZE == chunk.end) {
         chunk.next = getChunk();
         return chunk.next;
      } else {
         return chunk;
      }
   }

   public static int chunkFully(CharChunk c, Reader r) throws IOException {
      CharChunk tail = tail(c);
      int totalRead = 0;

      while(true) {
         tail = ensureCapacity(tail);
         int read = r.read(tail.buf, tail.end, CHUNK_SIZE - tail.end);
         if (read == -1) {
            return totalRead;
         }

         tail.end += read;
         totalRead += read;
      }
   }

   public static int chunk(CharChunk c, Reader in, int len) throws IOException {
      CharChunk tail = tail(c);

      int requested;
      int read;
      for(requested = len; len > 0; len -= read) {
         tail = ensureCapacity(tail);
         int toRead = Math.min(len, CHUNK_SIZE - tail.end);
         read = in.read(tail.buf, tail.end, toRead);
         if (read == -1) {
            return requested - len;
         }

         tail.end += read;
      }

      return requested;
   }

   private static int getIntegerProperty(final String property, int defaultValue) {
      Integer i = (Integer)AccessController.doPrivileged(new PrivilegedAction() {
         public Integer run() {
            return Integer.getInteger(property);
         }
      });
      return i == null ? defaultValue : i;
   }

   private CharChunk() {
      this(CHUNK_SIZE);
   }

   private CharChunk(int size) {
      this.buf = new char[size];
      this.end = 0;
      this.next = null;
      this.charBuffer = CharBuffer.wrap(this.buf);
   }

   public CharBuffer getWriteCharBuffer() {
      this.charBuffer.position(0);
      this.charBuffer.limit(this.end);
      return this.charBuffer;
   }

   static {
      chunkPool = new PartitionedStackPool(TOTAL_CHUNKS, CHUNK_PARTITIONS);
   }

   private static class JspChunkPoolHolder {
      private static final Pool chunkPool;

      static {
         chunkPool = new PartitionedStackPool(CharChunk.TOTAL_CHUNKS, CharChunk.CHUNK_PARTITIONS);
      }
   }
}
