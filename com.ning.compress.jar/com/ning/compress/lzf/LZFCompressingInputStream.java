package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import java.io.IOException;
import java.io.InputStream;

public class LZFCompressingInputStream extends InputStream {
   private final BufferRecycler _recycler;
   private ChunkEncoder _encoder;
   protected final InputStream _inputStream;
   protected boolean _inputStreamClosed;
   protected boolean _cfgFullReads;
   protected byte[] _inputBuffer;
   protected byte[] _encodedBytes;
   protected int _bufferPosition;
   protected int _bufferLength;
   protected int _readCount;

   public LZFCompressingInputStream(InputStream in) {
      this((ChunkEncoder)null, in, BufferRecycler.instance());
   }

   public LZFCompressingInputStream(ChunkEncoder encoder, InputStream in) {
      this(encoder, in, (BufferRecycler)null);
   }

   public LZFCompressingInputStream(ChunkEncoder encoder, InputStream in, BufferRecycler bufferRecycler) {
      this._cfgFullReads = false;
      this._bufferPosition = 0;
      this._bufferLength = 0;
      this._readCount = 0;
      this._encoder = encoder;
      this._inputStream = in;
      if (bufferRecycler == null) {
         bufferRecycler = encoder != null ? this._encoder._recycler : BufferRecycler.instance();
      }

      this._recycler = bufferRecycler;
      this._inputBuffer = bufferRecycler.allocInputBuffer(65535);
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
      return !this.readyBuffer() ? -1 : this._encodedBytes[this._bufferPosition++] & 255;
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
         System.arraycopy(this._encodedBytes, this._bufferPosition, buffer, offset, chunkLength);
         this._bufferPosition += chunkLength;
         if (chunkLength != length && this._cfgFullReads) {
            int totalRead = chunkLength;

            do {
               offset += chunkLength;
               if (!this.readyBuffer()) {
                  break;
               }

               chunkLength = Math.min(this._bufferLength - this._bufferPosition, length - totalRead);
               System.arraycopy(this._encodedBytes, this._bufferPosition, buffer, offset, chunkLength);
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
      byte[] buf = this._encodedBytes;
      if (buf != null) {
         this._encodedBytes = null;
         this._recycler.releaseEncodeBuffer(buf);
      }

      if (this._encoder != null) {
         this._encoder.close();
      }

      this._closeInput();
   }

   private void _closeInput() throws IOException {
      byte[] buf = this._inputBuffer;
      if (buf != null) {
         this._inputBuffer = null;
         this._recycler.releaseInputBuffer(buf);
      }

      if (!this._inputStreamClosed) {
         this._inputStreamClosed = true;
         this._inputStream.close();
      }

   }

   public long skip(long n) throws IOException {
      if (this._inputStreamClosed) {
         return -1L;
      } else {
         int left = this._bufferLength - this._bufferPosition;
         if (left <= 0) {
            int b = this.read();
            if (b < 0) {
               return -1L;
            }

            --this._bufferPosition;
            left = this._bufferLength - this._bufferPosition;
         }

         if ((long)left > n) {
            left = (int)n;
         }

         this._bufferPosition += left;
         return (long)left;
      }
   }

   protected boolean readyBuffer() throws IOException {
      if (this._bufferPosition < this._bufferLength) {
         return true;
      } else if (this._inputStreamClosed) {
         return false;
      } else {
         int count = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
         if (count < 0) {
            this._closeInput();
            return false;
         } else {
            int chunkLength = count;
            int left = this._inputBuffer.length - count;

            while((count = this._inputStream.read(this._inputBuffer, chunkLength, left)) > 0) {
               chunkLength += count;
               left -= count;
               if (left < 1) {
                  break;
               }
            }

            this._bufferPosition = 0;
            int encodeEnd;
            if (this._encoder == null) {
               encodeEnd = chunkLength + (chunkLength + 31 >> 5) + 7;
               this._encoder = ChunkEncoderFactory.optimalNonAllocatingInstance(encodeEnd, this._recycler);
            }

            if (this._encodedBytes == null) {
               encodeEnd = chunkLength + (chunkLength + 31 >> 5) + 7;
               this._encodedBytes = this._recycler.allocEncodingBuffer(encodeEnd);
            }

            encodeEnd = this._encoder.tryCompress(this._inputBuffer, 0, chunkLength, this._encodedBytes, 7);
            if (encodeEnd < chunkLength + 5) {
               LZFChunk.appendCompressedHeader(chunkLength, encodeEnd - 7, this._encodedBytes, 0);
               this._bufferLength = encodeEnd;
            } else {
               int ptr = LZFChunk.appendNonCompressedHeader(chunkLength, this._encodedBytes, 0);
               System.arraycopy(this._inputBuffer, 0, this._encodedBytes, ptr, chunkLength);
               this._bufferLength = ptr + chunkLength;
            }

            if (count < 0) {
               this._closeInput();
            }

            return true;
         }
      }
   }
}
