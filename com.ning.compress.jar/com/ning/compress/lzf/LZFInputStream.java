package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZFInputStream extends InputStream {
   protected final ChunkDecoder _decoder;
   protected final BufferRecycler _recycler;
   protected final InputStream _inputStream;
   protected boolean _inputStreamClosed;
   protected boolean _cfgFullReads;
   protected byte[] _inputBuffer;
   protected byte[] _decodedBytes;
   protected int _bufferPosition;
   protected int _bufferLength;

   public LZFInputStream(InputStream inputStream) throws IOException {
      this(inputStream, false);
   }

   public LZFInputStream(ChunkDecoder decoder, InputStream in) throws IOException {
      this(decoder, in, BufferRecycler.instance(), false);
   }

   public LZFInputStream(InputStream in, boolean fullReads) throws IOException {
      this(ChunkDecoderFactory.optimalInstance(), in, BufferRecycler.instance(), fullReads);
   }

   public LZFInputStream(ChunkDecoder decoder, InputStream in, boolean fullReads) throws IOException {
      this(decoder, in, BufferRecycler.instance(), fullReads);
   }

   public LZFInputStream(InputStream inputStream, BufferRecycler bufferRecycler) throws IOException {
      this(inputStream, bufferRecycler, false);
   }

   public LZFInputStream(InputStream in, BufferRecycler bufferRecycler, boolean fullReads) throws IOException {
      this(ChunkDecoderFactory.optimalInstance(), in, bufferRecycler, fullReads);
   }

   public LZFInputStream(ChunkDecoder decoder, InputStream in, BufferRecycler bufferRecycler, boolean fullReads) throws IOException {
      this._cfgFullReads = false;
      this._bufferPosition = 0;
      this._bufferLength = 0;
      this._decoder = decoder;
      this._recycler = bufferRecycler;
      this._inputStream = in;
      this._inputStreamClosed = false;
      this._cfgFullReads = fullReads;
      this._inputBuffer = bufferRecycler.allocInputBuffer(65535);
      this._decodedBytes = bufferRecycler.allocDecodeBuffer(65535);
   }

   public void setUseFullReads(boolean b) {
      this._cfgFullReads = b;
   }

   public int available() {
      if (this._inputStreamClosed) {
         return 0;
      } else {
         int left = this._bufferLength - this._bufferPosition;
         return left <= 0 ? 0 : left;
      }
   }

   public int read() throws IOException {
      return !this.readyBuffer() ? -1 : this._decodedBytes[this._bufferPosition++] & 255;
   }

   public int read(byte[] buffer) throws IOException {
      return this.read(buffer, 0, buffer.length);
   }

   public int read(byte[] buffer, int offset, int length) throws IOException {
      if (length < 1) {
         return 0;
      } else if (!this.readyBuffer()) {
         return -1;
      } else {
         int chunkLength = Math.min(this._bufferLength - this._bufferPosition, length);
         System.arraycopy(this._decodedBytes, this._bufferPosition, buffer, offset, chunkLength);
         this._bufferPosition += chunkLength;
         if (chunkLength != length && this._cfgFullReads) {
            int totalRead = chunkLength;

            do {
               offset += chunkLength;
               if (!this.readyBuffer()) {
                  break;
               }

               chunkLength = Math.min(this._bufferLength - this._bufferPosition, length - totalRead);
               System.arraycopy(this._decodedBytes, this._bufferPosition, buffer, offset, chunkLength);
               this._bufferPosition += chunkLength;
               totalRead += chunkLength;
            } while(totalRead < length);

            return totalRead;
         } else {
            return chunkLength;
         }
      }
   }

   public void close() throws IOException {
      this._bufferPosition = this._bufferLength = 0;
      byte[] buf = this._inputBuffer;
      if (buf != null) {
         this._inputBuffer = null;
         this._recycler.releaseInputBuffer(buf);
      }

      buf = this._decodedBytes;
      if (buf != null) {
         this._decodedBytes = null;
         this._recycler.releaseDecodeBuffer(buf);
      }

      if (!this._inputStreamClosed) {
         this._inputStreamClosed = true;
         this._inputStream.close();
      }

   }

   public long skip(long n) throws IOException {
      if (this._inputStreamClosed) {
         return -1L;
      } else if (n <= 0L) {
         return n;
      } else {
         long skipped;
         int amount;
         if (this._bufferPosition < this._bufferLength) {
            amount = this._bufferLength - this._bufferPosition;
            if (n <= (long)amount) {
               this._bufferPosition += (int)n;
               return n;
            }

            this._bufferPosition = this._bufferLength;
            skipped = (long)amount;
            n -= (long)amount;
         } else {
            skipped = 0L;
         }

         do {
            amount = this._decoder.skipOrDecodeChunk(this._inputStream, this._inputBuffer, this._decodedBytes, n);
            if (amount < 0) {
               if (amount == -1) {
                  this.close();
                  return skipped;
               }

               this._bufferLength = -(amount + 1);
               skipped += n;
               this._bufferPosition = (int)n;
               return skipped;
            }

            skipped += (long)amount;
            n -= (long)amount;
         } while(n > 0L);

         return skipped;
      }
   }

   public InputStream getUnderlyingInputStream() {
      return this._inputStream;
   }

   public void discardBuffered() {
      this._bufferPosition = this._bufferLength = 0;
   }

   public int readAndWrite(OutputStream out) throws IOException {
      int total;
      int avail;
      for(total = 0; this.readyBuffer(); total += avail) {
         avail = this._bufferLength - this._bufferPosition;
         out.write(this._decodedBytes, this._bufferPosition, avail);
         this._bufferPosition += avail;
      }

      return total;
   }

   protected boolean readyBuffer() throws IOException {
      if (this._bufferPosition < this._bufferLength) {
         return true;
      } else if (this._inputStreamClosed) {
         return false;
      } else {
         this._bufferLength = this._decoder.decodeChunk(this._inputStream, this._inputBuffer, this._decodedBytes);
         if (this._bufferLength < 0) {
            this.close();
            return false;
         } else {
            this._bufferPosition = 0;
            return this._bufferPosition < this._bufferLength;
         }
      }
   }
}
