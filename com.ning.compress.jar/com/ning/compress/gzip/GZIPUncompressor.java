package com.ning.compress.gzip;

import com.ning.compress.BufferRecycler;
import com.ning.compress.DataHandler;
import com.ning.compress.Uncompressor;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class GZIPUncompressor extends Uncompressor {
   protected static final int GZIP_MAGIC = 35615;
   protected static final byte GZIP_MAGIC_0 = 31;
   protected static final byte GZIP_MAGIC_1 = -117;
   protected static final int FHCRC = 2;
   protected static final int FEXTRA = 4;
   protected static final int FNAME = 8;
   protected static final int FCOMMENT = 16;
   protected static final int DEFAULT_CHUNK_SIZE = 4096;
   protected static final int DECODE_BUFFER_SIZE = 65535;
   protected static final int STATE_INITIAL = 0;
   protected static final int STATE_HEADER_SIG1 = 1;
   protected static final int STATE_HEADER_COMP_TYPE = 2;
   protected static final int STATE_HEADER_FLAGS = 3;
   protected static final int STATE_HEADER_SKIP = 4;
   protected static final int STATE_HEADER_EXTRA0 = 5;
   protected static final int STATE_HEADER_EXTRA1 = 6;
   protected static final int STATE_HEADER_FNAME = 7;
   protected static final int STATE_HEADER_COMMENT = 8;
   protected static final int STATE_HEADER_CRC0 = 9;
   protected static final int STATE_HEADER_CRC1 = 10;
   protected static final int STATE_TRAILER_INITIAL = 11;
   protected static final int STATE_TRAILER_CRC1 = 12;
   protected static final int STATE_TRAILER_CRC2 = 13;
   protected static final int STATE_TRAILER_CRC3 = 14;
   protected static final int STATE_TRAILER_LEN0 = 15;
   protected static final int STATE_TRAILER_LEN1 = 16;
   protected static final int STATE_TRAILER_LEN2 = 17;
   protected static final int STATE_TRAILER_LEN3 = 18;
   protected static final int STATE_BODY = 20;
   protected final DataHandler _handler;
   protected final BufferRecycler _recycler;
   protected final GZIPRecycler _gzipRecycler;
   protected Inflater _inflater;
   protected final CRC32 _crc;
   protected final int _inputChunkLength;
   protected byte[] _decodeBuffer;
   protected int _state;
   protected boolean _terminated;
   protected int _flags;
   protected int _headerCRC;
   protected int _skippedBytes;
   protected int _trailerCRC;
   protected int _trailerCount;

   public GZIPUncompressor(DataHandler h) {
      this(h, 4096, BufferRecycler.instance(), GZIPRecycler.instance());
   }

   public GZIPUncompressor(DataHandler h, int inputChunkLength) {
      this(h, inputChunkLength, BufferRecycler.instance(), GZIPRecycler.instance());
   }

   public GZIPUncompressor(DataHandler h, int inputChunkLength, BufferRecycler bufferRecycler, GZIPRecycler gzipRecycler) {
      this._state = 0;
      this._inputChunkLength = inputChunkLength;
      this._handler = h;
      this._recycler = bufferRecycler;
      this._decodeBuffer = bufferRecycler.allocDecodeBuffer(65535);
      this._gzipRecycler = gzipRecycler;
      this._inflater = gzipRecycler.allocInflater();
      this._crc = new CRC32();
   }

   public boolean feedCompressedData(byte[] comp, int offset, int len) throws IOException {
      if (this._terminated) {
         return false;
      } else {
         int end = offset + len;
         if (this._state != 20) {
            if (this._state >= 11) {
               offset = this._handleTrailer(comp, offset, end);
               if (offset < end) {
                  this._throwInternal();
               }

               return true;
            }

            offset = this._handleHeader(comp, offset, end);
            if (offset >= end) {
               return true;
            }
         }

         int remains;
         label67:
         do {
            if (this._inflater.needsInput()) {
               remains = end - offset;
               if (remains < 1) {
                  return true;
               }

               int amount = Math.min(remains, this._inputChunkLength);
               this._inflater.setInput(comp, offset, amount);
               offset += amount;
            }

            do {
               try {
                  remains = this._inflater.inflate(this._decodeBuffer);
               } catch (DataFormatException var7) {
                  throw new GZIPException("Problems inflating gzip data: " + var7.getMessage(), var7);
               }

               if (remains == 0) {
                  continue label67;
               }

               this._crc.update(this._decodeBuffer, 0, remains);
            } while(this._handler.handleData(this._decodeBuffer, 0, remains));

            this._terminated = true;
            return false;
         } while(!this._inflater.finished() && !this._inflater.needsDictionary());

         this._state = 11;
         remains = this._inflater.getRemaining();
         if (remains > 0) {
            offset -= remains;
         }

         offset = this._handleTrailer(comp, offset, end);
         if (offset < end) {
            this._throwInternal();
         }

         return !this._terminated;
      }
   }

   public void complete() throws IOException {
      byte[] b = this._decodeBuffer;
      if (b != null) {
         this._decodeBuffer = null;
         this._recycler.releaseDecodeBuffer(b);
      }

      Inflater i = this._inflater;
      if (i != null) {
         this._inflater = null;
         this._gzipRecycler.releaseInflater(i);
      }

      this._handler.allDataHandled();
      if (!this._terminated && this._state != 0) {
         if (this._state >= 11) {
            if (this._state == 20) {
               throw new GZIPException("Invalid GZIP stream: end-of-input in the middle of compressed data");
            } else {
               throw new GZIPException("Invalid GZIP stream: end-of-input in the trailer (state: " + this._state + ")");
            }
         } else {
            throw new GZIPException("Invalid GZIP stream: end-of-input in header (state: " + this._state + ")");
         }
      }
   }

   protected final boolean _hasFlag(int flag) {
      return (this._flags & flag) == flag;
   }

   private final int _handleHeader(byte[] comp, int offset, int end) throws IOException {
      while(true) {
         while(true) {
            label137:
            while(offset < end) {
               byte b = comp[offset++];
               this._crc.update(b);
               switch (this._state) {
                  case 0:
                     if (b != 31) {
                        this._reportBadHeader(comp, offset, end, 0);
                     }

                     if (offset >= end) {
                        this._state = 1;
                        break;
                     } else {
                        b = comp[offset++];
                        this._crc.update(b);
                     }
                  case 1:
                     if (b != -117) {
                        this._reportBadHeader(comp, offset, end, 1);
                     }

                     if (offset >= end) {
                        this._state = 2;
                        break;
                     } else {
                        b = comp[offset++];
                        this._crc.update(b);
                     }
                  case 2:
                     if (b != 8) {
                        this._reportBadHeader(comp, offset, end, 1);
                     }

                     if (offset >= end) {
                        this._state = 3;
                        break;
                     } else {
                        b = comp[offset++];
                        this._crc.update(b);
                     }
                  case 3:
                     this._flags = b;
                     this._skippedBytes = 0;
                     this._state = 4;
                     if (offset >= end) {
                        break;
                     }

                     b = comp[offset++];
                     this._crc.update(b);
                  case 4:
                     while(++this._skippedBytes < 6) {
                        if (offset >= end) {
                           break label137;
                        }

                        b = comp[offset++];
                        this._crc.update(b);
                     }

                     if (this._hasFlag(4)) {
                        this._state = 5;
                     } else if (this._hasFlag(8)) {
                        this._state = 7;
                     } else if (this._hasFlag(16)) {
                        this._state = 8;
                     } else {
                        if (this._hasFlag(2)) {
                           this._state = 9;
                           continue;
                        }

                        this._state = 20;
                        break label137;
                     }
                     break;
                  case 5:
                     this._state = 6;
                     break;
                  case 6:
                     if (this._hasFlag(8)) {
                        this._state = 7;
                     } else if (this._hasFlag(16)) {
                        this._state = 8;
                     } else if (this._hasFlag(2)) {
                        this._state = 9;
                     } else {
                        this._state = 20;
                        break label137;
                     }
                     break;
                  case 7:
                     while(b != 0) {
                        if (offset >= end) {
                           break label137;
                        }

                        b = comp[offset++];
                        this._crc.update(b);
                     }

                     if (this._hasFlag(16)) {
                        this._state = 8;
                        break;
                     } else {
                        if (this._hasFlag(2)) {
                           this._state = 9;
                           break;
                        }

                        this._state = 20;
                        break label137;
                     }
                  case 8:
                     while(b != 0) {
                        if (offset >= end) {
                           break label137;
                        }

                        b = comp[offset++];
                     }

                     if (this._hasFlag(2)) {
                        this._state = 9;
                        break;
                     }

                     this._state = 20;
                     break label137;
                  case 9:
                     this._headerCRC = b & 255;
                     if (offset >= end) {
                        this._state = 10;
                        break;
                     } else {
                        b = comp[offset++];
                        this._crc.update(b);
                     }
                  case 10:
                     this._headerCRC += (b & 255) << 8;
                     int act = (int)this._crc.getValue() & '\uffff';
                     if (act != this._headerCRC) {
                        throw new GZIPException("Corrupt GZIP header: header CRC 0x" + Integer.toHexString(act) + ", expected 0x " + Integer.toHexString(this._headerCRC));
                     }

                     this._state = 20;
                     break label137;
                  default:
                     this._throwInternal("Unknown header state: " + this._state);
               }
            }

            if (this._state == 20) {
               this._crc.reset();
            }

            return offset;
         }
      }
   }

   private final int _handleTrailer(byte[] comp, int offset, int end) throws IOException {
      while(offset < end) {
         byte b = comp[offset++];
         switch (this._state) {
            case 11:
               this._trailerCRC = b & 255;
               this._state = 12;
               break;
            case 12:
               this._trailerCRC += (b & 255) << 8;
               this._state = 13;
               break;
            case 13:
               this._trailerCRC += (b & 255) << 16;
               this._state = 14;
               break;
            case 14:
               this._trailerCRC += (b & 255) << 24;
               int actCRC = (int)this._crc.getValue();
               if (this._trailerCRC != actCRC) {
                  throw new GZIPException("Corrupt block or trailer: expected CRC " + Integer.toHexString(this._trailerCRC) + ", computed " + Integer.toHexString(actCRC));
               }

               this._state = 15;
               break;
            case 15:
               this._trailerCount = b & 255;
               this._state = 16;
               break;
            case 16:
               this._trailerCount += (b & 255) << 8;
               this._state = 17;
               break;
            case 17:
               this._trailerCount += (b & 255) << 16;
               this._state = 18;
               break;
            case 18:
               this._trailerCount += (b & 255) << 24;
               this._state = 0;
               int actCount32 = (int)this._inflater.getBytesWritten();
               if (actCount32 == this._trailerCount) {
                  break;
               }

               throw new GZIPException("Corrupt block or trailed: expected byte count " + this._trailerCount + ", read " + actCount32);
            default:
               this._throwInternal("Unknown trailer state: " + this._state);
         }
      }

      return offset;
   }

   protected void _throwInternal() throws GZIPException {
      throw new GZIPException("Internal error");
   }

   protected void _throwInternal(String msg) throws GZIPException {
      throw new GZIPException("Internal error: " + msg);
   }

   protected void _reportBadHeader(byte[] comp, int nextOffset, int end, int relative) throws GZIPException {
      String byteStr = "0x" + Integer.toHexString(comp[nextOffset] & 255);
      if (relative <= 1) {
         int exp = relative == 0 ? 31 : 139;
         --nextOffset;
         throw new GZIPException("Bad GZIP stream: byte #" + relative + " of header not '" + exp + "' (0x" + Integer.toHexString(exp) + ") but " + byteStr);
      } else if (relative == 2) {
         throw new GZIPException("Bad GZIP stream: byte #2 of header invalid: type " + byteStr + " not supported, 0x" + Integer.toHexString(8) + " expected");
      } else {
         throw new GZIPException("Bad GZIP stream: byte #" + relative + " of header invalid: " + byteStr);
      }
   }
}
