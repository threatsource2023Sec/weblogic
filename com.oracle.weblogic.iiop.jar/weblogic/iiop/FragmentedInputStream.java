package weblogic.iiop;

import java.io.InputStream;
import org.omg.CORBA.MARSHAL;
import weblogic.utils.Hex;
import weblogic.utils.io.Chunk;

class FragmentedInputStream extends InputStream {
   private static final WlsChunkManager WLS_CHUNK_MANAGER = new WlsChunkManager();
   static ChunkManager chunkManager;
   private Fragment fragmentHead;
   private Fragment fragmentTail;
   private Marker marker = new Marker();
   private Chunk head;
   private int streamPos = 0;
   private int chunkPos = 0;

   public static void setChunkManager(ChunkManager chunkManager) {
      FragmentedInputStream.chunkManager = chunkManager;
   }

   public static void resetChunkManager() {
      chunkManager = WLS_CHUNK_MANAGER;
   }

   FragmentedInputStream(Chunk head) {
      this.head = head;
      this.chunkPos = 0;
   }

   FragmentedInputStream(byte[] buf) {
      Chunk chunk = null;
      int len = buf.length;

      int copyLen;
      for(int off = 0; len > 0; chunk.end = copyLen) {
         Chunk tmp = chunk;
         chunk = chunkManager.allocateChunk();
         if (tmp != null) {
            tmp.next = chunk;
         } else {
            this.head = chunk;
         }

         copyLen = min(chunk.buf.length, len);
         System.arraycopy(buf, off, chunk.buf, 0, copyLen);
         off += copyLen;
         len -= copyLen;
      }

   }

   FragmentedInputStream() {
   }

   private static int min(int a, int b) {
      return a <= b ? a : b;
   }

   void addChunks(FragmentedInputStream stream) {
      Fragment fragment = this.fragmentTail;
      if (this.fragmentHead == null) {
         this.fragmentHead = this.fragmentTail = new Fragment(stream.head, stream.chunkPos);
      } else {
         this.fragmentTail = fragment.addFragment(new Fragment(stream.head, stream.chunkPos));
      }

   }

   public int pos() {
      return this.streamPos + this.chunkPos;
   }

   public boolean eof() {
      return this.atEndOfChunk() && this.noMoreChunks() && this.noMoreFragments();
   }

   String readUTF8String(int len) {
      char[] buf = new char[len];
      int remainingToCopy = len;

      int cpos;
      int pos;
      for(cpos = 0; remainingToCopy > 0; this.chunkPos = pos) {
         int copyLen;
         while(remainingToCopy > 0 && this.nextCharMightRequireNextChunk()) {
            copyLen = this.pos();
            buf[cpos++] = this.readUTF8wchar();
            remainingToCopy -= this.pos() - copyLen;
         }

         copyLen = min(this.getMaxToCopySafelyFromChunk(), remainingToCopy);

         int c;
         for(pos = this.chunkPos; pos < this.chunkPos + copyLen; buf[cpos++] = (char)c) {
            c = this.head.buf[pos++] & 255;
            if ((c & 128) != 0) {
               int c2;
               if ((c & 224) == 192) {
                  c2 = this.head.buf[pos++] & 255;
                  c = ((c & 31) << 6) + (c2 & 63);
               } else {
                  c2 = this.head.buf[pos++] & 255;
                  int c3 = this.head.buf[pos++] & 255;
                  c = ((c & 15) << 12) + ((c2 & 63) << 6) + (c3 & 63);
               }
            }
         }

         remainingToCopy -= pos - this.chunkPos;
      }

      return new String(buf, 0, cpos);
   }

   private int getMaxToCopySafelyFromChunk() {
      return this.head.end - this.chunkPos - 2;
   }

   private boolean nextCharMightRequireNextChunk() {
      return this.head.end - this.chunkPos < 3;
   }

   private char readUTF8wchar() {
      int c = this.read() & 255;
      if ((c & 128) != 0) {
         int c2;
         if ((c & 224) == 192) {
            c2 = this.read() & 255;
            c = ((c & 31) << 6) + (c2 & 63);
         } else {
            c2 = this.read() & 255;
            int c3 = this.read() & 255;
            c = ((c & 15) << 12) + ((c2 & 63) << 6) + (c3 & 63);
         }
      }

      return (char)c;
   }

   int readLong(boolean littleEndian) {
      int b1;
      int b2;
      int b3;
      int b4;
      if (this.isRoomInHeadChunk(4)) {
         b1 = this.head.buf[this.chunkPos++] & 255;
         b2 = this.head.buf[this.chunkPos++] & 255;
         b3 = this.head.buf[this.chunkPos++] & 255;
         b4 = this.head.buf[this.chunkPos++] & 255;
      } else {
         this.advanceIfReachedToTheEnd();
         b1 = this.head.buf[this.chunkPos++] & 255;
         this.advanceIfReachedToTheEnd();
         b2 = this.head.buf[this.chunkPos++] & 255;
         this.advanceIfReachedToTheEnd();
         b3 = this.head.buf[this.chunkPos++] & 255;
         this.advanceIfReachedToTheEnd();
         b4 = this.head.buf[this.chunkPos++] & 255;
      }

      return toLong(littleEndian, b1, b2, b3, b4);
   }

   private boolean isRoomInHeadChunk(int bytesNeeded) {
      return this.chunkPos + bytesNeeded <= this.head.end;
   }

   private static int toLong(boolean littleEndian, int b1, int b2, int b3, int b4) {
      return littleEndian ? b4 << 24 | b3 << 16 | b2 << 8 | b1 : b1 << 24 | b2 << 16 | b3 << 8 | b4;
   }

   long readLongLong(boolean littleEndian) {
      long b8;
      long b1;
      long b2;
      long b3;
      long b4;
      long b5;
      long b6;
      long b7;
      if (this.isRoomInHeadChunk(8)) {
         b1 = (long)(this.head.buf[this.chunkPos++] & 255);
         b2 = (long)(this.head.buf[this.chunkPos++] & 255);
         b3 = (long)(this.head.buf[this.chunkPos++] & 255);
         b4 = (long)(this.head.buf[this.chunkPos++] & 255);
         b5 = (long)(this.head.buf[this.chunkPos++] & 255);
         b6 = (long)(this.head.buf[this.chunkPos++] & 255);
         b7 = (long)(this.head.buf[this.chunkPos++] & 255);
         b8 = (long)(this.head.buf[this.chunkPos++] & 255);
      } else {
         this.advanceIfReachedToTheEnd();
         b1 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b2 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b3 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b4 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b5 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b6 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b7 = (long)(this.head.buf[this.chunkPos++] & 255);
         this.advanceIfReachedToTheEnd();
         b8 = (long)(this.head.buf[this.chunkPos++] & 255);
      }

      long l;
      if (littleEndian) {
         l = b8 << 56 | b7 << 48 | b6 << 40 | b5 << 32 | b4 << 24 | b3 << 16 | b2 << 8 | b1;
      } else {
         l = b1 << 56 | b2 << 48 | b3 << 40 | b4 << 32 | b5 << 24 | b6 << 16 | b7 << 8 | b8;
      }

      return l;
   }

   public int available() {
      int result = Chunk.size(this.head) - this.chunkPos;

      for(Fragment f = this.fragmentHead; f != null; f = f.getNext()) {
         result += Chunk.size(f.chunk) - f.start;
      }

      return result;
   }

   public void close() {
      this.fragmentTail = null;
      this.clearMark();
      releaseChunks(this.head);

      Fragment tmp;
      for(this.head = null; this.fragmentHead != null; this.fragmentHead = tmp) {
         tmp = this.fragmentHead.getNext();
         releaseChunks(this.fragmentHead.chunk);
      }

   }

   static void releaseChunks(Chunk head) {
      while(head != null) {
         Chunk tmp = head.next;
         chunkManager.releaseChunk(head);
         head = tmp;
      }

   }

   public void mark(int readlimit) {
      this.marker.mark(this);
   }

   public void mark(Marker marker) {
      if (!this.marker.isSet()) {
         this.mark(0);
      }

      marker.mark(this);
   }

   public void reset() {
      if (!this.marker.isSet()) {
         throw new IllegalStateException("Mark must be called before reset");
      } else {
         this.resetTo(this.marker);
         this.marker.clear();
      }
   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      this.advanceIfReachedToTheEnd();
      return this.head.buf[this.chunkPos++];
   }

   public int read(byte[] b, int off, int len) {
      assert b != null : "output buffer may not be null";

      assert off + len <= b.length : "offset and length out of bounds";

      int copyLen;
      for(int remainingToCopy = len; remainingToCopy > 0; remainingToCopy -= copyLen) {
         this.advanceIfReachedToTheEnd();
         copyLen = min(this.bytesLeftInCurrentChunk(), remainingToCopy);
         System.arraycopy(this.head.buf, this.chunkPos, b, off, copyLen);
         this.chunkPos += copyLen;
         off += copyLen;
      }

      return len;
   }

   public long skip(long bytesToSkip) {
      while(bytesToSkip > 0L) {
         this.advanceIfReachedToTheEnd();
         int skipLen = min(this.bytesLeftInCurrentChunk(), (int)bytesToSkip);
         this.chunkPos += skipLen;
         bytesToSkip -= (long)skipLen;
      }

      return bytesToSkip;
   }

   private int bytesLeftInCurrentChunk() {
      return this.head.end - this.chunkPos;
   }

   void resetTo(Marker marker) {
      this.streamPos = marker.streamPos;
      this.chunkPos = marker.chunkPos;
      this.head = marker.head;
      this.fragmentHead = marker.fragmentHead;
   }

   private void advance() {
      this.streamPos += this.head.end;
      this.chunkPos = 0;
      Chunk tmp = this.head.next;
      if (this.mayReleaseChunks()) {
         chunkManager.releaseChunk(this.head);
      }

      this.head = tmp;
      if (this.head == null) {
         if (this.fragmentHead == null) {
            throw new MARSHAL("EOF at " + this.pos());
         }

         this.streamPos -= this.fragmentHead.start;
         this.head = this.fragmentHead.chunk;
         this.chunkPos = this.fragmentHead.start;
         this.fragmentHead = this.fragmentHead.getNext();
      }

   }

   private boolean mayReleaseChunks() {
      return !this.marker.isSet();
   }

   void clearMark() {
      if (this.marker.isSet()) {
         releaseChunks(this.marker.head, this.head);
         this.releaseFragments(this.marker.fragmentHead, this.head, this.fragmentHead);
         this.marker.clear();
      }
   }

   private void releaseFragments(Fragment fragment, Chunk endChunk, Fragment endFragment) {
      while(fragment != null && fragment != endFragment) {
         Fragment tmp = fragment.getNext();
         releaseChunks(fragment.chunk, endChunk);
         fragment = tmp;
      }

   }

   private static void releaseChunks(Chunk startChunk, Chunk endChunk) {
      while(startChunk != null && startChunk != endChunk) {
         Chunk nextChunk = startChunk.next;
         chunkManager.releaseChunk(startChunk);
         startChunk = nextChunk;
      }

   }

   private boolean noMoreFragments() {
      return this.fragmentHead == null;
   }

   private boolean noMoreChunks() {
      return this.head.next == null;
   }

   private boolean atEndOfChunk() {
      return this.chunkPos == this.head.end;
   }

   private void advanceIfReachedToTheEnd() {
      while(this.atEndOfChunk()) {
         this.advance();
      }

   }

   void peek_octet_array(byte[] b, int off, int len) {
      int i = this.chunkPos;
      Chunk current = this.head;

      int copyLen;
      for(Fragment nextFragment = this.fragmentHead; len > 0; len -= copyLen) {
         if (i == current.end) {
            i = 0;
            current = current.next;
            if (current == null) {
               if (this.fragmentHead == null) {
                  throw new MARSHAL("EOF looking ahead " + len + " bytes at " + this.pos());
               }

               current = nextFragment.chunk;
               i = nextFragment.start;
               nextFragment = nextFragment.getNext();
            }
         }

         copyLen = min(current.end - i, len);
         System.arraycopy(current.buf, i, b, off, copyLen);
         i += copyLen;
         off += copyLen;
      }

   }

   String dumpBuf() {
      return this.dumpBuf(Integer.MAX_VALUE);
   }

   String dumpBuf(int count) {
      return this.dumpBuf(this.chunkPos, count);
   }

   String dumpBuf(int start, int count) {
      StringBuilder buf = new StringBuilder();

      for(Chunk chunk = this.head; count > 0 && chunk != null; chunk = chunk.next) {
         int bufCount = min(chunk.end - start, count);
         buf.append("\n").append(Hex.dump(chunk.buf, start, bufCount));
         count -= bufCount;
         start = 0;
      }

      return buf.toString();
   }

   public int peekLong(int offset, boolean littleEndian) {
      if (this.isRoomInHeadChunk(4 + offset)) {
         int b1 = this.head.buf[this.chunkPos + offset] & 255;
         int b2 = this.head.buf[this.chunkPos + offset + 1] & 255;
         int b3 = this.head.buf[this.chunkPos + offset + 2] & 255;
         int b4 = this.head.buf[this.chunkPos + offset + 3] & 255;
         return toLong(littleEndian, b1, b2, b3, b4);
      } else {
         int var7;
         try {
            this.mark(0);
            this.skip((long)offset);
            var7 = this.readLong(littleEndian);
         } finally {
            this.reset();
         }

         return var7;
      }
   }

   static {
      chunkManager = WLS_CHUNK_MANAGER;
   }

   private static class WlsChunkManager implements ChunkManager {
      private WlsChunkManager() {
      }

      public Chunk allocateChunk() {
         return Chunk.getChunk();
      }

      public void releaseChunk(Chunk head) {
         Chunk.releaseChunk(head);
      }

      // $FF: synthetic method
      WlsChunkManager(Object x0) {
         this();
      }
   }

   interface ChunkManager {
      Chunk allocateChunk();

      void releaseChunk(Chunk var1);
   }

   static class Marker {
      private int streamPos;
      private int chunkPos;
      private Chunk head;
      private Fragment fragmentHead;

      void mark(FragmentedInputStream stream) {
         this.streamPos = stream.streamPos;
         this.chunkPos = stream.chunkPos;
         this.head = stream.head;
         this.fragmentHead = stream.fragmentHead;
      }

      void copyFrom(Marker marker) {
         this.streamPos = marker.streamPos;
         this.chunkPos = marker.chunkPos;
         this.head = marker.head;
         this.fragmentHead = marker.fragmentHead;
      }

      private boolean isSet() {
         return this.head != null || this.fragmentHead != null;
      }

      private void clear() {
         this.head = null;
         this.fragmentHead = null;
      }
   }

   private static class Fragment {
      private Fragment next;
      private final int start;
      private final Chunk chunk;

      private Fragment(Chunk chunk, int chunkPos) {
         this.chunk = chunk;
         this.start = chunkPos;
      }

      private Fragment addFragment(Fragment fragment) {
         this.next = fragment;
         return fragment;
      }

      private Fragment getNext() {
         return this.next;
      }

      // $FF: synthetic method
      Fragment(Chunk x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
