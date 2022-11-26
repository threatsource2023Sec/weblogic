package com.ning.compress.gzip;

import com.ning.compress.BufferRecycler;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class OptimizedGZIPInputStream extends InputStream {
   private static final int INPUT_BUFFER_SIZE = 16000;
   protected Inflater _inflater;
   protected final CRC32 _crc;
   protected final BufferRecycler _bufferRecycler;
   protected final GZIPRecycler _gzipRecycler;
   protected byte[] _buffer;
   protected int _bufferPtr;
   protected int _bufferEnd;
   protected byte[] _tmpBuffer;
   protected InputStream _rawInput;
   protected State _state;

   public OptimizedGZIPInputStream(InputStream in) throws IOException {
      this(in, BufferRecycler.instance(), GZIPRecycler.instance());
   }

   public OptimizedGZIPInputStream(InputStream in, BufferRecycler bufferRecycler, GZIPRecycler gzipRecycler) throws IOException {
      this._bufferRecycler = bufferRecycler;
      this._gzipRecycler = gzipRecycler;
      this._rawInput = in;
      this._buffer = bufferRecycler.allocInputBuffer(16000);
      this._bufferPtr = this._bufferEnd = 0;
      this._inflater = gzipRecycler.allocInflater();
      this._crc = new CRC32();
      this._readHeader();
      this._state = OptimizedGZIPInputStream.State.GZIP_CONTENT;
      this._crc.reset();
      if (this._bufferPtr >= this._bufferEnd) {
         this._loadMore();
      }

      this._inflater.setInput(this._buffer, this._bufferPtr, this._bufferEnd - this._bufferPtr);
   }

   public int available() {
      if (this._state == OptimizedGZIPInputStream.State.GZIP_COMPLETE) {
         return 0;
      } else {
         return this._inflater.finished() ? 0 : 1;
      }
   }

   public void close() throws IOException {
      this._state = OptimizedGZIPInputStream.State.GZIP_COMPLETE;
      if (this._rawInput != null) {
         this._rawInput.close();
         this._rawInput = null;
      }

      byte[] b = this._buffer;
      if (b != null) {
         this._buffer = null;
         this._bufferRecycler.releaseInputBuffer(b);
      }

      b = this._tmpBuffer;
      if (b != null) {
         this._tmpBuffer = null;
         this._bufferRecycler.releaseDecodeBuffer(b);
      }

      Inflater i = this._inflater;
      if (i != null) {
         this._inflater = null;
         this._gzipRecycler.releaseInflater(i);
      }

   }

   public void mark(int limit) {
   }

   public boolean markSupported() {
      return false;
   }

   public final int read() throws IOException {
      byte[] tmp = this._getTmpBuffer();
      int count = this.read(tmp, 0, 1);
      return count < 0 ? -1 : tmp[0] & 255;
   }

   public final int read(byte[] buf) throws IOException {
      return this.read(buf, 0, buf.length);
   }

   public final int read(byte[] buf, int offset, int len) throws IOException {
      if (buf == null) {
         throw new NullPointerException();
      } else if (offset >= 0 && len >= 0 && len <= buf.length - offset) {
         if (this._state == OptimizedGZIPInputStream.State.GZIP_COMPLETE) {
            return -1;
         } else if (len == 0) {
            return 0;
         } else {
            try {
               int count;
               while((count = this._inflater.inflate(buf, offset, len)) == 0) {
                  if (this._inflater.finished() || this._inflater.needsDictionary()) {
                     this._readTrailer();
                     this._state = OptimizedGZIPInputStream.State.GZIP_COMPLETE;
                     return -1;
                  }

                  if (this._inflater.needsInput()) {
                     this._loadMore();
                     this._inflater.setInput(this._buffer, this._bufferPtr, this._bufferEnd - this._bufferPtr);
                     this._bufferPtr = this._bufferEnd;
                  }
               }

               this._crc.update(buf, offset, count);
               return count;
            } catch (DataFormatException var6) {
               String s = var6.getMessage();
               throw new GZIPException(s != null ? s : "Invalid ZLIB data format");
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void reset() throws IOException {
      throw new IOException("mark/reset not supported");
   }

   public long skip(long n) throws IOException {
      if (n < 0L) {
         throw new IllegalArgumentException("negative skip length");
      } else {
         byte[] tmp = this._getTmpBuffer();
         long total = 0L;

         while(true) {
            int max = (int)(n - total);
            if (max == 0) {
               return total;
            }

            int count = this.read(tmp, 0, Math.min(max, tmp.length));
            total += (long)count;
         }
      }
   }

   protected byte[] _getTmpBuffer() {
      if (this._tmpBuffer == null) {
         this._tmpBuffer = this._bufferRecycler.allocDecodeBuffer(16000);
      }

      return this._tmpBuffer;
   }

   protected final void _readHeader() throws IOException {
      this._state = OptimizedGZIPInputStream.State.GZIP_HEADER;
      int sig = this._readShort();
      if (sig != 35615) {
         throw new GZIPException("Not in GZIP format (got 0x" + Integer.toHexString(sig) + ", should be 0x" + Integer.toHexString(35615) + ")");
      } else if (this._readByte() != 8) {
         throw new GZIPException("Unsupported compression method (only support Deflate, 8)");
      } else {
         int flg = this._readByte();
         this._skipBytes(6);
         if ((flg & 4) != 0) {
            this._skipBytes(this._readShort());
         }

         if ((flg & 8) != 0) {
            while(true) {
               if (this._readByte() != 0) {
                  continue;
               }
            }
         }

         if ((flg & 16) != 0) {
            while(true) {
               if (this._readByte() != 0) {
                  continue;
               }
            }
         }

         if ((flg & 2) != 0) {
            int act = (int)this._crc.getValue() & '\uffff';
            int exp = this._readShort();
            if (act != exp) {
               throw new GZIPException("Corrupt GZIP header (header CRC 0x" + Integer.toHexString(act) + ", expected 0x " + Integer.toHexString(exp));
            }
         }

      }
   }

   protected final void _readTrailer() throws IOException {
      int actCrc = (int)this._crc.getValue();
      int remains = this._inflater.getRemaining();
      if (remains > 0) {
         this._bufferPtr = this._bufferEnd - remains;
      } else {
         this._loadMore(8);
      }

      int expCrc = this._readInt();
      int expCount = this._readInt();
      int actCount32 = (int)this._inflater.getBytesWritten();
      if (actCount32 != expCount) {
         throw new GZIPException("Corrupt trailer: expected byte count " + expCount + ", read " + actCount32);
      } else if (expCrc != actCrc) {
         throw new GZIPException("Corrupt trailer: expected CRC " + Integer.toHexString(expCrc) + ", computed " + Integer.toHexString(actCrc));
      }
   }

   private final void _skipBytes(int count) throws IOException {
      while(true) {
         --count;
         if (count < 0) {
            return;
         }

         this._readByte();
      }
   }

   private final int _readByte() throws IOException {
      if (this._bufferPtr >= this._bufferEnd) {
         this._loadMore();
      }

      byte b = this._buffer[this._bufferPtr++];
      if (this._state == OptimizedGZIPInputStream.State.GZIP_HEADER) {
         this._crc.update(b);
      }

      return b & 255;
   }

   private final int _readShort() throws IOException {
      return this._readByte() | this._readByte() << 8;
   }

   private final int _readInt() throws IOException {
      return this._readByte() | this._readByte() << 8 | this._readByte() << 16 | this._readByte() << 24;
   }

   private final void _loadMore() throws IOException {
      this._loadMore(Math.min(this._buffer.length, 16000));
   }

   private final void _loadMore(int max) throws IOException {
      int count = this._rawInput.read(this._buffer, 0, max);
      if (count < 1) {
         String prob = count < 0 ? "Unexpected end of input" : "Strange underlying stream (returned 0 bytes for read)";
         throw new GZIPException(prob + " when reading " + this._state);
      } else {
         this._bufferPtr = 0;
         this._bufferEnd = count;
      }
   }

   static enum State {
      GZIP_HEADER,
      GZIP_CONTENT,
      GZIP_TRAILER,
      GZIP_COMPLETE;
   }
}
