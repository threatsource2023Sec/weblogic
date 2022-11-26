package com.ning.compress.lzf.impl;

import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.LZFException;
import java.io.IOException;
import java.io.InputStream;

public class VanillaChunkDecoder extends ChunkDecoder {
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
      do {
         int ctrl = in[inPos++] & 255;
         if (ctrl < 32) {
            switch (ctrl) {
               case 31:
                  out[outPos++] = in[inPos++];
               case 30:
                  out[outPos++] = in[inPos++];
               case 29:
                  out[outPos++] = in[inPos++];
               case 28:
                  out[outPos++] = in[inPos++];
               case 27:
                  out[outPos++] = in[inPos++];
               case 26:
                  out[outPos++] = in[inPos++];
               case 25:
                  out[outPos++] = in[inPos++];
               case 24:
                  out[outPos++] = in[inPos++];
               case 23:
                  out[outPos++] = in[inPos++];
               case 22:
                  out[outPos++] = in[inPos++];
               case 21:
                  out[outPos++] = in[inPos++];
               case 20:
                  out[outPos++] = in[inPos++];
               case 19:
                  out[outPos++] = in[inPos++];
               case 18:
                  out[outPos++] = in[inPos++];
               case 17:
                  out[outPos++] = in[inPos++];
               case 16:
                  out[outPos++] = in[inPos++];
               case 15:
                  out[outPos++] = in[inPos++];
               case 14:
                  out[outPos++] = in[inPos++];
               case 13:
                  out[outPos++] = in[inPos++];
               case 12:
                  out[outPos++] = in[inPos++];
               case 11:
                  out[outPos++] = in[inPos++];
               case 10:
                  out[outPos++] = in[inPos++];
               case 9:
                  out[outPos++] = in[inPos++];
               case 8:
                  out[outPos++] = in[inPos++];
               case 7:
                  out[outPos++] = in[inPos++];
               case 6:
                  out[outPos++] = in[inPos++];
               case 5:
                  out[outPos++] = in[inPos++];
               case 4:
                  out[outPos++] = in[inPos++];
               case 3:
                  out[outPos++] = in[inPos++];
               case 2:
                  out[outPos++] = in[inPos++];
               case 1:
                  out[outPos++] = in[inPos++];
               case 0:
                  out[outPos++] = in[inPos++];
            }
         } else {
            int len = ctrl >> 5;
            ctrl = -((ctrl & 31) << 8) - 1;
            if (len < 7) {
               ctrl -= in[inPos++] & 255;
               out[outPos] = out[outPos++ + ctrl];
               out[outPos] = out[outPos++ + ctrl];
               switch (len) {
                  case 6:
                     out[outPos] = out[outPos++ + ctrl];
                  case 5:
                     out[outPos] = out[outPos++ + ctrl];
                  case 4:
                     out[outPos] = out[outPos++ + ctrl];
                  case 3:
                     out[outPos] = out[outPos++ + ctrl];
                  case 2:
                     out[outPos] = out[outPos++ + ctrl];
                  case 1:
                     out[outPos] = out[outPos++ + ctrl];
               }
            } else {
               len = in[inPos++] & 255;
               ctrl -= in[inPos++] & 255;
               if (ctrl + len < -9) {
                  len += 9;
                  if (len <= 32) {
                     copyUpTo32WithSwitch(out, outPos + ctrl, out, outPos, len - 1);
                  } else {
                     System.arraycopy(out, outPos + ctrl, out, outPos, len);
                  }

                  outPos += len;
               } else {
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  out[outPos] = out[outPos++ + ctrl];
                  len += outPos;

                  for(int end = len - 3; outPos < end; out[outPos] = out[outPos++ + ctrl]) {
                     out[outPos] = out[outPos++ + ctrl];
                     out[outPos] = out[outPos++ + ctrl];
                     out[outPos] = out[outPos++ + ctrl];
                  }

                  switch (len - outPos) {
                     case 3:
                        out[outPos] = out[outPos++ + ctrl];
                     case 2:
                        out[outPos] = out[outPos++ + ctrl];
                     case 1:
                        out[outPos] = out[outPos++ + ctrl];
                  }
               }
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

   protected static final void copyUpTo32WithSwitch(byte[] in, int inPos, byte[] out, int outPos, int lengthMinusOne) {
      switch (lengthMinusOne) {
         case 31:
            out[outPos++] = in[inPos++];
         case 30:
            out[outPos++] = in[inPos++];
         case 29:
            out[outPos++] = in[inPos++];
         case 28:
            out[outPos++] = in[inPos++];
         case 27:
            out[outPos++] = in[inPos++];
         case 26:
            out[outPos++] = in[inPos++];
         case 25:
            out[outPos++] = in[inPos++];
         case 24:
            out[outPos++] = in[inPos++];
         case 23:
            out[outPos++] = in[inPos++];
         case 22:
            out[outPos++] = in[inPos++];
         case 21:
            out[outPos++] = in[inPos++];
         case 20:
            out[outPos++] = in[inPos++];
         case 19:
            out[outPos++] = in[inPos++];
         case 18:
            out[outPos++] = in[inPos++];
         case 17:
            out[outPos++] = in[inPos++];
         case 16:
            out[outPos++] = in[inPos++];
         case 15:
            out[outPos++] = in[inPos++];
         case 14:
            out[outPos++] = in[inPos++];
         case 13:
            out[outPos++] = in[inPos++];
         case 12:
            out[outPos++] = in[inPos++];
         case 11:
            out[outPos++] = in[inPos++];
         case 10:
            out[outPos++] = in[inPos++];
         case 9:
            out[outPos++] = in[inPos++];
         case 8:
            out[outPos++] = in[inPos++];
         case 7:
            out[outPos++] = in[inPos++];
         case 6:
            out[outPos++] = in[inPos++];
         case 5:
            out[outPos++] = in[inPos++];
         case 4:
            out[outPos++] = in[inPos++];
         case 3:
            out[outPos++] = in[inPos++];
         case 2:
            out[outPos++] = in[inPos++];
         case 1:
            out[outPos++] = in[inPos++];
         case 0:
            out[outPos++] = in[inPos++];
         default:
      }
   }
}
