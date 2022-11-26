package com.ning.compress.lzf.impl;

import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.LZFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeChunkDecoder extends ChunkDecoder {
   private static final Unsafe unsafe;
   private static final long BYTE_ARRAY_OFFSET;

   public final int decodeChunk(InputStream is, byte[] inputBuffer, byte[] outputBuffer) throws IOException {
      int bytesRead = readHeader(is, inputBuffer);
      if (bytesRead < 5 || inputBuffer[0] != 90 || inputBuffer[1] != 86) {
         if (bytesRead == 0) {
            return -1;
         }

         this._reportCorruptHeader();
      }

      int type = inputBuffer[2];
      int compLen = uint16(inputBuffer, 3);
      if (type == 0) {
         readFully(is, false, outputBuffer, 0, compLen);
         return compLen;
      } else {
         readFully(is, true, inputBuffer, 0, 2 + compLen);
         int uncompLen = uint16(inputBuffer, 0);
         this.decodeChunk(inputBuffer, 2, outputBuffer, 0, uncompLen);
         return uncompLen;
      }
   }

   public final void decodeChunk(byte[] in, int inPos, byte[] out, int outPos, int outEnd) throws LZFException {
      int outputEnd8 = outEnd - 8;
      int outputEnd32 = outEnd - 32;

      label53:
      do {
         int ctrl;
         for(ctrl = in[inPos++] & 255; ctrl < 32; ctrl = in[inPos++] & 255) {
            if (outPos > outputEnd32) {
               System.arraycopy(in, inPos, out, outPos, ctrl + 1);
            } else {
               copyUpTo32(in, inPos, out, outPos, ctrl);
            }

            ++ctrl;
            inPos += ctrl;
            outPos += ctrl;
            if (outPos >= outEnd) {
               break label53;
            }
         }

         int len = ctrl >> 5;
         ctrl = -((ctrl & 31) << 8) - 1;
         if (len < 7) {
            ctrl -= in[inPos++] & 255;
            if (ctrl < -7 && outPos < outputEnd8) {
               long rawOffset = BYTE_ARRAY_OFFSET + (long)outPos;
               unsafe.putLong(out, rawOffset, unsafe.getLong(out, rawOffset + (long)ctrl));
               outPos += len + 2;
            } else {
               outPos = this.copyOverlappingShort(out, outPos, ctrl, len);
            }
         } else {
            len = (in[inPos++] & 255) + 9;
            ctrl -= in[inPos++] & 255;
            if (ctrl <= -9 && outPos <= outputEnd32) {
               if (len <= 32) {
                  copyUpTo32(out, outPos + ctrl, outPos, len - 1);
                  outPos += len;
               } else {
                  copyLong(out, outPos + ctrl, outPos, len, outputEnd32);
                  outPos += len;
               }
            } else {
               outPos = copyOverlappingLong(out, outPos, ctrl, len - 9);
            }
         }
      } while(outPos < outEnd);

      if (outPos != outEnd) {
         throw new LZFException("Corrupt data: overrun in decompress, input offset " + inPos + ", output offset " + outPos);
      }
   }

   public int skipOrDecodeChunk(InputStream is, byte[] inputBuffer, byte[] outputBuffer, long maxToSkip) throws IOException {
      int bytesRead = readHeader(is, inputBuffer);
      if (bytesRead < 5 || inputBuffer[0] != 90 || inputBuffer[1] != 86) {
         if (bytesRead == 0) {
            return -1;
         }

         this._reportCorruptHeader();
      }

      int type = inputBuffer[2];
      int compLen = uint16(inputBuffer, 3);
      if (type == 0) {
         if ((long)compLen <= maxToSkip) {
            skipFully(is, compLen);
            return compLen;
         } else {
            readFully(is, false, outputBuffer, 0, compLen);
            return -(compLen + 1);
         }
      } else {
         readFully(is, true, inputBuffer, 0, 2);
         int uncompLen = uint16(inputBuffer, 0);
         if ((long)uncompLen <= maxToSkip) {
            skipFully(is, compLen);
            return uncompLen;
         } else {
            readFully(is, true, inputBuffer, 2, compLen);
            this.decodeChunk(inputBuffer, 2, outputBuffer, 0, uncompLen);
            return -(uncompLen + 1);
         }
      }
   }

   private final int copyOverlappingShort(byte[] out, int outPos, int offset, int len) {
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      switch (len) {
         case 6:
            out[outPos] = out[outPos++ + offset];
         case 5:
            out[outPos] = out[outPos++ + offset];
         case 4:
            out[outPos] = out[outPos++ + offset];
         case 3:
            out[outPos] = out[outPos++ + offset];
         case 2:
            out[outPos] = out[outPos++ + offset];
         case 1:
            out[outPos] = out[outPos++ + offset];
         default:
            return outPos;
      }
   }

   private static final int copyOverlappingLong(byte[] out, int outPos, int offset, int len) {
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      out[outPos] = out[outPos++ + offset];
      len += outPos;

      for(int end = len - 3; outPos < end; out[outPos] = out[outPos++ + offset]) {
         out[outPos] = out[outPos++ + offset];
         out[outPos] = out[outPos++ + offset];
         out[outPos] = out[outPos++ + offset];
      }

      switch (len - outPos) {
         case 3:
            out[outPos] = out[outPos++ + offset];
         case 2:
            out[outPos] = out[outPos++ + offset];
         case 1:
            out[outPos] = out[outPos++ + offset];
         default:
            return outPos;
      }
   }

   private static final void copyUpTo32(byte[] buffer, int inputIndex, int outputIndex, int lengthMinusOne) {
      long inPtr = BYTE_ARRAY_OFFSET + (long)inputIndex;
      long outPtr = BYTE_ARRAY_OFFSET + (long)outputIndex;
      unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
      if (lengthMinusOne > 7) {
         inPtr += 8L;
         outPtr += 8L;
         unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
         if (lengthMinusOne > 15) {
            inPtr += 8L;
            outPtr += 8L;
            unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
            if (lengthMinusOne > 23) {
               inPtr += 8L;
               outPtr += 8L;
               unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
            }
         }
      }

   }

   private static final void copyUpTo32(byte[] in, int inputIndex, byte[] out, int outputIndex, int lengthMinusOne) {
      long inPtr = BYTE_ARRAY_OFFSET + (long)inputIndex;
      long outPtr = BYTE_ARRAY_OFFSET + (long)outputIndex;
      unsafe.putLong(out, outPtr, unsafe.getLong(in, inPtr));
      if (lengthMinusOne > 7) {
         inPtr += 8L;
         outPtr += 8L;
         unsafe.putLong(out, outPtr, unsafe.getLong(in, inPtr));
         if (lengthMinusOne > 15) {
            inPtr += 8L;
            outPtr += 8L;
            unsafe.putLong(out, outPtr, unsafe.getLong(in, inPtr));
            if (lengthMinusOne > 23) {
               inPtr += 8L;
               outPtr += 8L;
               unsafe.putLong(out, outPtr, unsafe.getLong(in, inPtr));
            }
         }
      }

   }

   private static final void copyLong(byte[] buffer, int inputIndex, int outputIndex, int length, int outputEnd8) {
      if (outputIndex + length > outputEnd8) {
         copyLongTail(buffer, inputIndex, outputIndex, length);
      } else {
         long inPtr = BYTE_ARRAY_OFFSET + (long)inputIndex;

         long outPtr;
         for(outPtr = BYTE_ARRAY_OFFSET + (long)outputIndex; length >= 8; length -= 8) {
            unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
            inPtr += 8L;
            outPtr += 8L;
         }

         if (length > 4) {
            unsafe.putLong(buffer, outPtr, unsafe.getLong(buffer, inPtr));
         } else if (length > 0) {
            unsafe.putInt(buffer, outPtr, unsafe.getInt(buffer, inPtr));
         }

      }
   }

   private static final void copyLongTail(byte[] buffer, int inputIndex, int outputIndex, int length) {
      for(int inEnd = inputIndex + length; inputIndex < inEnd; buffer[outputIndex++] = buffer[inputIndex++]) {
      }

   }

   static {
      try {
         Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
         theUnsafe.setAccessible(true);
         unsafe = (Unsafe)theUnsafe.get((Object)null);
      } catch (Exception var1) {
         throw new RuntimeException(var1);
      }

      BYTE_ARRAY_OFFSET = (long)unsafe.arrayBaseOffset(byte[].class);
   }
}
