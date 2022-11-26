package com.ning.compress.lzf;

import com.ning.compress.BufferRecycler;
import com.ning.compress.DataHandler;
import com.ning.compress.Uncompressor;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import java.io.IOException;

public class LZFUncompressor extends Uncompressor {
   protected static final int STATE_INITIAL = 0;
   protected static final int STATE_HEADER_Z_GOTTEN = 1;
   protected static final int STATE_HEADER_ZV_GOTTEN = 2;
   protected static final int STATE_HEADER_COMPRESSED_0 = 3;
   protected static final int STATE_HEADER_COMPRESSED_1 = 4;
   protected static final int STATE_HEADER_COMPRESSED_2 = 5;
   protected static final int STATE_HEADER_COMPRESSED_3 = 6;
   protected static final int STATE_HEADER_COMPRESSED_BUFFERING = 7;
   protected static final int STATE_HEADER_UNCOMPRESSED_0 = 8;
   protected static final int STATE_HEADER_UNCOMPRESSED_1 = 9;
   protected static final int STATE_HEADER_UNCOMPRESSED_STREAMING = 10;
   protected final DataHandler _handler;
   protected final ChunkDecoder _decoder;
   protected final BufferRecycler _recycler;
   protected int _state;
   protected boolean _terminated;
   protected int _compressedLength;
   protected int _uncompressedLength;
   protected byte[] _inputBuffer;
   protected byte[] _decodeBuffer;
   protected int _bytesReadFromBlock;

   public LZFUncompressor(DataHandler handler) {
      this(handler, ChunkDecoderFactory.optimalInstance(), BufferRecycler.instance());
   }

   public LZFUncompressor(DataHandler handler, BufferRecycler bufferRecycler) {
      this(handler, ChunkDecoderFactory.optimalInstance(), bufferRecycler);
   }

   public LZFUncompressor(DataHandler handler, ChunkDecoder dec) {
      this(handler, dec, BufferRecycler.instance());
   }

   public LZFUncompressor(DataHandler handler, ChunkDecoder dec, BufferRecycler bufferRecycler) {
      this._state = 0;
      this._handler = handler;
      this._decoder = dec;
      this._recycler = bufferRecycler;
   }

   public boolean feedCompressedData(byte[] comp, int offset, int len) throws IOException {
      int end = offset + len;

      while(true) {
         while(offset < end) {
            byte b = comp[offset++];
            switch (this._state) {
               case 0:
                  if (b != 90) {
                     this._reportBadHeader(comp, offset, len, 0);
                  }

                  if (offset >= end) {
                     this._state = 1;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 1:
                  if (b != 86) {
                     this._reportBadHeader(comp, offset, len, 1);
                  }

                  if (offset >= end) {
                     this._state = 2;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 2:
                  this._bytesReadFromBlock = 0;
                  int type = b & 255;
                  if (type != 1) {
                     if (type == 0) {
                        this._state = 8;
                        break;
                     }

                     this._reportBadBlockType(comp, offset, len, type);
                  }

                  this._state = 3;
                  if (offset >= end) {
                     break;
                  }

                  b = comp[offset++];
               case 3:
                  this._compressedLength = b & 255;
                  if (offset >= end) {
                     this._state = 4;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 4:
                  this._compressedLength = (this._compressedLength << 8) + (b & 255);
                  if (offset >= end) {
                     this._state = 5;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 5:
                  this._uncompressedLength = b & 255;
                  if (offset >= end) {
                     this._state = 6;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 6:
                  this._uncompressedLength = (this._uncompressedLength << 8) + (b & 255);
                  this._state = 7;
                  if (offset >= end) {
                     break;
                  }

                  b = comp[offset++];
               case 7:
                  --offset;
                  offset = this._handleCompressed(comp, offset, end);
                  break;
               case 8:
                  this._uncompressedLength = b & 255;
                  if (offset >= end) {
                     this._state = 9;
                     break;
                  } else {
                     b = comp[offset++];
                  }
               case 9:
                  this._uncompressedLength = (this._uncompressedLength << 8) + (b & 255);
                  this._state = 10;
                  if (offset >= end) {
                     break;
                  }

                  b = comp[offset++];
               case 10:
                  --offset;
                  offset = this._handleUncompressed(comp, offset, end);
                  if (!this._terminated && this._bytesReadFromBlock == this._uncompressedLength) {
                     this._state = 0;
                  }
            }
         }

         return !this._terminated;
      }
   }

   public void complete() throws IOException {
      byte[] b = this._inputBuffer;
      if (b != null) {
         this._inputBuffer = null;
         this._recycler.releaseInputBuffer(b);
      }

      b = this._decodeBuffer;
      if (b != null) {
         this._decodeBuffer = null;
         this._recycler.releaseDecodeBuffer(b);
      }

      this._handler.allDataHandled();
      if (!this._terminated && this._state != 0) {
         if (this._state == 7) {
            throw new LZFException("Incomplete compressed LZF block; only got " + this._bytesReadFromBlock + " bytes, needed " + this._compressedLength);
         } else if (this._state == 10) {
            throw new LZFException("Incomplete uncompressed LZF block; only got " + this._bytesReadFromBlock + " bytes, needed " + this._uncompressedLength);
         } else {
            throw new LZFException("Incomplete LZF block; decoding state = " + this._state);
         }
      }
   }

   private final int _handleUncompressed(byte[] comp, int offset, int end) throws IOException {
      int amount = Math.min(end - offset, this._uncompressedLength - this._bytesReadFromBlock);
      if (!this._handler.handleData(comp, offset, amount)) {
         this._terminated = true;
      }

      this._bytesReadFromBlock += amount;
      return offset + amount;
   }

   private final int _handleCompressed(byte[] comp, int offset, int end) throws IOException {
      int available = end - offset;
      if (this._bytesReadFromBlock == 0 && available >= this._compressedLength) {
         this._uncompress(comp, offset, this._compressedLength);
         offset += this._compressedLength;
         this._state = 0;
         return offset;
      } else {
         if (this._inputBuffer == null) {
            this._inputBuffer = this._recycler.allocInputBuffer(65535);
         }

         int amount = Math.min(available, this._compressedLength - this._bytesReadFromBlock);
         System.arraycopy(comp, offset, this._inputBuffer, this._bytesReadFromBlock, amount);
         offset += amount;
         this._bytesReadFromBlock += amount;
         if (this._bytesReadFromBlock == this._compressedLength) {
            this._uncompress(this._inputBuffer, 0, this._compressedLength);
            this._state = 0;
         }

         return offset;
      }
   }

   private final void _uncompress(byte[] src, int srcOffset, int len) throws IOException {
      if (this._decodeBuffer == null) {
         this._decodeBuffer = this._recycler.allocDecodeBuffer(65535);
      }

      this._decoder.decodeChunk(src, srcOffset, this._decodeBuffer, 0, this._uncompressedLength);
      this._handler.handleData(this._decodeBuffer, 0, this._uncompressedLength);
   }

   protected void _reportBadHeader(byte[] comp, int nextOffset, int len, int relative) throws IOException {
      char exp = relative == 0 ? 90 : 86;
      --nextOffset;
      throw new LZFException("Bad block: byte #" + relative + " of block header not '" + exp + "' (0x" + Integer.toHexString(exp) + ") but 0x" + Integer.toHexString(comp[nextOffset] & 255) + " (at " + (nextOffset - 1) + "/" + len + ")");
   }

   protected void _reportBadBlockType(byte[] comp, int nextOffset, int len, int type) throws IOException {
      throw new LZFException("Bad block: unrecognized type 0x" + Integer.toHexString(type & 255) + " (at " + (nextOffset - 1) + "/" + len + ")");
   }
}
