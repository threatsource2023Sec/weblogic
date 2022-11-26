package org.python.netty.handler.codec.compression;

final class FastLz {
   private static final int MAX_DISTANCE = 8191;
   private static final int MAX_FARDISTANCE = 73725;
   private static final int HASH_LOG = 13;
   private static final int HASH_SIZE = 8192;
   private static final int HASH_MASK = 8191;
   private static final int MAX_COPY = 32;
   private static final int MAX_LEN = 264;
   private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
   static final int MAGIC_NUMBER = 4607066;
   static final byte BLOCK_TYPE_NON_COMPRESSED = 0;
   static final byte BLOCK_TYPE_COMPRESSED = 1;
   static final byte BLOCK_WITHOUT_CHECKSUM = 0;
   static final byte BLOCK_WITH_CHECKSUM = 16;
   static final int OPTIONS_OFFSET = 3;
   static final int CHECKSUM_OFFSET = 4;
   static final int MAX_CHUNK_LENGTH = 65535;
   static final int MIN_LENGTH_TO_COMPRESSION = 32;
   static final int LEVEL_AUTO = 0;
   static final int LEVEL_1 = 1;
   static final int LEVEL_2 = 2;

   static int calculateOutputBufferLength(int inputLength) {
      int outputLength = (int)((double)inputLength * 1.06);
      return Math.max(outputLength, 66);
   }

   static int compress(byte[] input, int inOffset, int inLength, byte[] output, int outOffset, int proposedLevel) {
      int level;
      if (proposedLevel == 0) {
         level = inLength < 65536 ? 1 : 2;
      } else {
         level = proposedLevel;
      }

      int ip = 0;
      int ipBound = ip + inLength - 2;
      int ipLimit = ip + inLength - 12;
      int op = 0;
      int[] htab = new int[8192];
      if (inLength < 4) {
         if (inLength == 0) {
            return 0;
         } else {
            output[outOffset + op++] = (byte)(inLength - 1);
            ++ipBound;

            while(ip <= ipBound) {
               output[outOffset + op++] = input[inOffset + ip++];
            }

            return inLength + 1;
         }
      } else {
         for(int hslot = 0; hslot < 8192; ++hslot) {
            htab[hslot] = ip;
         }

         int copy = 2;
         output[outOffset + op++] = 31;
         output[outOffset + op++] = input[inOffset + ip++];
         output[outOffset + op++] = input[inOffset + ip++];

         while(true) {
            while(ip < ipLimit) {
               int ref = 0;
               long distance = 0L;
               int len = 3;
               int anchor = ip;
               boolean matchLabel = false;
               if (level == 2 && input[inOffset + ip] == input[inOffset + ip - 1] && readU16(input, inOffset + ip - 1) == readU16(input, inOffset + ip + 1)) {
                  distance = 1L;
                  ip += 3;
                  ref = anchor - 1 + 3;
                  matchLabel = true;
               }

               label223: {
                  int hval;
                  if (!matchLabel) {
                     hval = hashFunction(input, inOffset + ip);
                     ref = htab[hval];
                     distance = (long)(anchor - ref);
                     htab[hval] = anchor;
                     if (distance == 0L) {
                        break label223;
                     }

                     if (level == 1) {
                        if (distance >= 8191L) {
                           break label223;
                        }
                     } else if (distance >= 73725L) {
                        break label223;
                     }

                     if (input[inOffset + ref++] != input[inOffset + ip++] || input[inOffset + ref++] != input[inOffset + ip++] || input[inOffset + ref++] != input[inOffset + ip++]) {
                        break label223;
                     }

                     if (level == 2 && distance >= 8191L) {
                        if (input[inOffset + ip++] != input[inOffset + ref++] || input[inOffset + ip++] != input[inOffset + ref++]) {
                           output[outOffset + op++] = input[inOffset + anchor++];
                           ip = anchor;
                           ++copy;
                           if (copy == 32) {
                              copy = 0;
                              output[outOffset + op++] = 31;
                           }
                           continue;
                        }

                        len += 2;
                     }
                  }

                  ip += len;
                  --distance;
                  if (distance == 0L) {
                     for(byte x = input[inOffset + ip - 1]; ip < ipBound && input[inOffset + ref++] == x; ++ip) {
                     }
                  } else if (input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++] && input[inOffset + ref++] == input[inOffset + ip++]) {
                     while(ip < ipBound && input[inOffset + ref++] == input[inOffset + ip++]) {
                     }
                  }

                  if (copy != 0) {
                     output[outOffset + op - copy - 1] = (byte)(copy - 1);
                  } else {
                     --op;
                  }

                  copy = 0;
                  ip -= 3;
                  len = ip - anchor;
                  if (level == 2) {
                     if (distance < 8191L) {
                        if (len < 7) {
                           output[outOffset + op++] = (byte)((int)((long)(len << 5) + (distance >>> 8)));
                           output[outOffset + op++] = (byte)((int)(distance & 255L));
                        } else {
                           output[outOffset + op++] = (byte)((int)(224L + (distance >>> 8)));

                           for(len -= 7; len >= 255; len -= 255) {
                              output[outOffset + op++] = -1;
                           }

                           output[outOffset + op++] = (byte)len;
                           output[outOffset + op++] = (byte)((int)(distance & 255L));
                        }
                     } else if (len < 7) {
                        distance -= 8191L;
                        output[outOffset + op++] = (byte)((len << 5) + 31);
                        output[outOffset + op++] = -1;
                        output[outOffset + op++] = (byte)((int)(distance >>> 8));
                        output[outOffset + op++] = (byte)((int)(distance & 255L));
                     } else {
                        distance -= 8191L;
                        output[outOffset + op++] = -1;

                        for(len -= 7; len >= 255; len -= 255) {
                           output[outOffset + op++] = -1;
                        }

                        output[outOffset + op++] = (byte)len;
                        output[outOffset + op++] = -1;
                        output[outOffset + op++] = (byte)((int)(distance >>> 8));
                        output[outOffset + op++] = (byte)((int)(distance & 255L));
                     }
                  } else {
                     if (len > 262) {
                        while(len > 262) {
                           output[outOffset + op++] = (byte)((int)(224L + (distance >>> 8)));
                           output[outOffset + op++] = -3;
                           output[outOffset + op++] = (byte)((int)(distance & 255L));
                           len -= 262;
                        }
                     }

                     if (len < 7) {
                        output[outOffset + op++] = (byte)((int)((long)(len << 5) + (distance >>> 8)));
                        output[outOffset + op++] = (byte)((int)(distance & 255L));
                     } else {
                        output[outOffset + op++] = (byte)((int)(224L + (distance >>> 8)));
                        output[outOffset + op++] = (byte)(len - 7);
                        output[outOffset + op++] = (byte)((int)(distance & 255L));
                     }
                  }

                  hval = hashFunction(input, inOffset + ip);
                  htab[hval] = ip++;
                  hval = hashFunction(input, inOffset + ip);
                  htab[hval] = ip++;
                  output[outOffset + op++] = 31;
                  continue;
               }

               output[outOffset + op++] = input[inOffset + anchor++];
               ip = anchor;
               ++copy;
               if (copy == 32) {
                  copy = 0;
                  output[outOffset + op++] = 31;
               }
            }

            ++ipBound;

            while(ip <= ipBound) {
               output[outOffset + op++] = input[inOffset + ip++];
               ++copy;
               if (copy == 32) {
                  copy = 0;
                  output[outOffset + op++] = 31;
               }
            }

            if (copy != 0) {
               output[outOffset + op - copy - 1] = (byte)(copy - 1);
            } else {
               --op;
            }

            if (level == 2) {
               output[outOffset] = (byte)(output[outOffset] | 32);
            }

            return op;
         }
      }
   }

   static int decompress(byte[] input, int inOffset, int inLength, byte[] output, int outOffset, int outLength) {
      int level = (input[inOffset] >> 5) + 1;
      if (level != 1 && level != 2) {
         throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", level, 1, 2));
      } else {
         int ip = 0;
         int op = 0;
         long ctrl = (long)(input[inOffset + ip++] & 31);
         int loop = true;

         do {
            long len = ctrl >> 5;
            long ofs = (ctrl & 31L) << 8;
            if (ctrl >= 32L) {
               --len;
               int ref = (int)((long)op - ofs);
               int code;
               if (len == 6L) {
                  if (level == 1) {
                     len += (long)(input[inOffset + ip++] & 255);
                  } else {
                     do {
                        code = input[inOffset + ip++] & 255;
                        len += (long)code;
                     } while(code == 255);
                  }
               }

               if (level == 1) {
                  ref -= input[inOffset + ip++] & 255;
               } else {
                  code = input[inOffset + ip++] & 255;
                  ref -= code;
                  if (code == 255 && ofs == 7936L) {
                     ofs = (long)((input[inOffset + ip++] & 255) << 8);
                     ofs += (long)(input[inOffset + ip++] & 255);
                     ref = (int)((long)op - ofs - 8191L);
                  }
               }

               if ((long)op + len + 3L > (long)outLength) {
                  return 0;
               }

               if (ref - 1 < 0) {
                  return 0;
               }

               if (ip < inLength) {
                  ctrl = (long)(input[inOffset + ip++] & 255);
               } else {
                  loop = false;
               }

               if (ref == op) {
                  byte b = output[outOffset + ref - 1];
                  output[outOffset + op++] = b;
                  output[outOffset + op++] = b;

                  for(output[outOffset + op++] = b; len != 0L; --len) {
                     output[outOffset + op++] = b;
                  }
               } else {
                  --ref;
                  output[outOffset + op++] = output[outOffset + ref++];
                  output[outOffset + op++] = output[outOffset + ref++];

                  for(output[outOffset + op++] = output[outOffset + ref++]; len != 0L; --len) {
                     output[outOffset + op++] = output[outOffset + ref++];
                  }
               }
            } else {
               ++ctrl;
               if ((long)op + ctrl > (long)outLength) {
                  return 0;
               }

               if ((long)ip + ctrl > (long)inLength) {
                  return 0;
               }

               output[outOffset + op++] = input[inOffset + ip++];
               --ctrl;

               while(ctrl != 0L) {
                  output[outOffset + op++] = input[inOffset + ip++];
                  --ctrl;
               }

               loop = ip < inLength;
               if (loop) {
                  ctrl = (long)(input[inOffset + ip++] & 255);
               }
            }
         } while(loop);

         return op;
      }
   }

   private static int hashFunction(byte[] p, int offset) {
      int v = readU16(p, offset);
      v ^= readU16(p, offset + 1) ^ v >> 3;
      v &= 8191;
      return v;
   }

   private static int readU16(byte[] data, int offset) {
      return offset + 1 >= data.length ? data[offset] & 255 : (data[offset + 1] & 255) << 8 | data[offset] & 255;
   }

   private FastLz() {
   }
}
