package org.python.netty.handler.codec.base64;

import java.nio.ByteOrder;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.ByteProcessor;
import org.python.netty.util.internal.PlatformDependent;

public final class Base64 {
   private static final int MAX_LINE_LENGTH = 76;
   private static final byte EQUALS_SIGN = 61;
   private static final byte NEW_LINE = 10;
   private static final byte WHITE_SPACE_ENC = -5;
   private static final byte EQUALS_SIGN_ENC = -1;

   private static byte[] alphabet(Base64Dialect dialect) {
      if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         return dialect.alphabet;
      }
   }

   private static byte[] decodabet(Base64Dialect dialect) {
      if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         return dialect.decodabet;
      }
   }

   private static boolean breakLines(Base64Dialect dialect) {
      if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         return dialect.breakLinesByDefault;
      }
   }

   public static ByteBuf encode(ByteBuf src) {
      return encode(src, Base64Dialect.STANDARD);
   }

   public static ByteBuf encode(ByteBuf src, Base64Dialect dialect) {
      return encode(src, breakLines(dialect), dialect);
   }

   public static ByteBuf encode(ByteBuf src, boolean breakLines) {
      return encode(src, breakLines, Base64Dialect.STANDARD);
   }

   public static ByteBuf encode(ByteBuf src, boolean breakLines, Base64Dialect dialect) {
      if (src == null) {
         throw new NullPointerException("src");
      } else {
         ByteBuf dest = encode(src, src.readerIndex(), src.readableBytes(), breakLines, dialect);
         src.readerIndex(src.writerIndex());
         return dest;
      }
   }

   public static ByteBuf encode(ByteBuf src, int off, int len) {
      return encode(src, off, len, Base64Dialect.STANDARD);
   }

   public static ByteBuf encode(ByteBuf src, int off, int len, Base64Dialect dialect) {
      return encode(src, off, len, breakLines(dialect), dialect);
   }

   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines) {
      return encode(src, off, len, breakLines, Base64Dialect.STANDARD);
   }

   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect) {
      return encode(src, off, len, breakLines, dialect, src.alloc());
   }

   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect, ByteBufAllocator allocator) {
      if (src == null) {
         throw new NullPointerException("src");
      } else if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         ByteBuf dest = allocator.buffer(encodedBufferSize(len, breakLines)).order(src.order());
         byte[] alphabet = alphabet(dialect);
         int d = 0;
         int e = 0;
         int len2 = len - 2;

         for(int lineLength = 0; d < len2; e += 4) {
            encode3to4(src, d + off, 3, dest, e, alphabet);
            lineLength += 4;
            if (breakLines && lineLength == 76) {
               dest.setByte(e + 4, 10);
               ++e;
               lineLength = 0;
            }

            d += 3;
         }

         if (d < len) {
            encode3to4(src, d + off, len - d, dest, e, alphabet);
            e += 4;
         }

         if (e > 1 && dest.getByte(e - 1) == 10) {
            --e;
         }

         return dest.slice(0, e);
      }
   }

   private static void encode3to4(ByteBuf src, int srcOffset, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
      int inBuff;
      if (src.order() == ByteOrder.BIG_ENDIAN) {
         switch (numSigBytes) {
            case 1:
               inBuff = toInt(src.getByte(srcOffset));
               break;
            case 2:
               inBuff = toIntBE(src.getShort(srcOffset));
               break;
            default:
               inBuff = numSigBytes <= 0 ? 0 : toIntBE(src.getMedium(srcOffset));
         }

         encode3to4BigEndian(inBuff, numSigBytes, dest, destOffset, alphabet);
      } else {
         switch (numSigBytes) {
            case 1:
               inBuff = toInt(src.getByte(srcOffset));
               break;
            case 2:
               inBuff = toIntLE(src.getShort(srcOffset));
               break;
            default:
               inBuff = numSigBytes <= 0 ? 0 : toIntLE(src.getMedium(srcOffset));
         }

         encode3to4LittleEndian(inBuff, numSigBytes, dest, destOffset, alphabet);
      }

   }

   static int encodedBufferSize(int len, boolean breakLines) {
      long len43 = ((long)len << 2) / 3L;
      long ret = len43 + 3L & -4L;
      if (breakLines) {
         ret += len43 / 76L;
      }

      return ret < 2147483647L ? (int)ret : Integer.MAX_VALUE;
   }

   private static int toInt(byte value) {
      return (value & 255) << 16;
   }

   private static int toIntBE(short value) {
      return (value & '\uff00') << 8 | (value & 255) << 8;
   }

   private static int toIntLE(short value) {
      return (value & 255) << 16 | value & '\uff00';
   }

   private static int toIntBE(int mediumValue) {
      return mediumValue & 16711680 | mediumValue & '\uff00' | mediumValue & 255;
   }

   private static int toIntLE(int mediumValue) {
      return (mediumValue & 255) << 16 | mediumValue & '\uff00' | (mediumValue & 16711680) >>> 16;
   }

   private static void encode3to4BigEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
      switch (numSigBytes) {
         case 1:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 63] << 16 | 15616 | 61);
            break;
         case 2:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 63] << 16 | alphabet[inBuff >>> 6 & 63] << 8 | 61);
            break;
         case 3:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 63] << 16 | alphabet[inBuff >>> 6 & 63] << 8 | alphabet[inBuff & 63]);
      }

   }

   private static void encode3to4LittleEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet) {
      switch (numSigBytes) {
         case 1:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 63] << 8 | 3997696 | 1023410176);
            break;
         case 2:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 63] << 8 | alphabet[inBuff >>> 6 & 63] << 16 | 1023410176);
            break;
         case 3:
            dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 63] << 8 | alphabet[inBuff >>> 6 & 63] << 16 | alphabet[inBuff & 63] << 24);
      }

   }

   public static ByteBuf decode(ByteBuf src) {
      return decode(src, Base64Dialect.STANDARD);
   }

   public static ByteBuf decode(ByteBuf src, Base64Dialect dialect) {
      if (src == null) {
         throw new NullPointerException("src");
      } else {
         ByteBuf dest = decode(src, src.readerIndex(), src.readableBytes(), dialect);
         src.readerIndex(src.writerIndex());
         return dest;
      }
   }

   public static ByteBuf decode(ByteBuf src, int off, int len) {
      return decode(src, off, len, Base64Dialect.STANDARD);
   }

   public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect) {
      return decode(src, off, len, dialect, src.alloc());
   }

   public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect, ByteBufAllocator allocator) {
      if (src == null) {
         throw new NullPointerException("src");
      } else if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         return (new Decoder()).decode(src, off, len, allocator, dialect);
      }
   }

   static int decodedBufferSize(int len) {
      return len - (len >>> 2);
   }

   private Base64() {
   }

   private static final class Decoder implements ByteProcessor {
      private final byte[] b4;
      private int b4Posn;
      private byte sbiCrop;
      private byte sbiDecode;
      private byte[] decodabet;
      private int outBuffPosn;
      private ByteBuf dest;

      private Decoder() {
         this.b4 = new byte[4];
      }

      ByteBuf decode(ByteBuf src, int off, int len, ByteBufAllocator allocator, Base64Dialect dialect) {
         this.dest = allocator.buffer(Base64.decodedBufferSize(len)).order(src.order());
         this.decodabet = Base64.decodabet(dialect);

         try {
            src.forEachByte(off, len, this);
            return this.dest.slice(0, this.outBuffPosn);
         } catch (Throwable var7) {
            this.dest.release();
            PlatformDependent.throwException(var7);
            return null;
         }
      }

      public boolean process(byte value) throws Exception {
         this.sbiCrop = (byte)(value & 127);
         this.sbiDecode = this.decodabet[this.sbiCrop];
         if (this.sbiDecode >= -5) {
            if (this.sbiDecode >= -1) {
               this.b4[this.b4Posn++] = this.sbiCrop;
               if (this.b4Posn > 3) {
                  this.outBuffPosn += decode4to3(this.b4, this.dest, this.outBuffPosn, this.decodabet);
                  this.b4Posn = 0;
                  if (this.sbiCrop == 61) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            throw new IllegalArgumentException("invalid bad Base64 input character: " + (short)(value & 255) + " (decimal)");
         }
      }

      private static int decode4to3(byte[] src, ByteBuf dest, int destOffset, byte[] decodabet) {
         byte src0 = src[0];
         byte src1 = src[1];
         byte src2 = src[2];
         int decodedValue;
         if (src2 == 61) {
            try {
               decodedValue = (decodabet[src0] & 255) << 2 | (decodabet[src1] & 255) >>> 4;
            } catch (IndexOutOfBoundsException var11) {
               throw new IllegalArgumentException("not encoded in Base64");
            }

            dest.setByte(destOffset, decodedValue);
            return 1;
         } else {
            byte src3 = src[3];
            byte b1;
            if (src3 == 61) {
               b1 = decodabet[src1];

               try {
                  if (dest.order() == ByteOrder.BIG_ENDIAN) {
                     decodedValue = ((decodabet[src0] & 63) << 2 | (b1 & 240) >> 4) << 8 | (b1 & 15) << 4 | (decodabet[src2] & 252) >>> 2;
                  } else {
                     decodedValue = (decodabet[src0] & 63) << 2 | (b1 & 240) >> 4 | ((b1 & 15) << 4 | (decodabet[src2] & 252) >>> 2) << 8;
                  }
               } catch (IndexOutOfBoundsException var12) {
                  throw new IllegalArgumentException("not encoded in Base64");
               }

               dest.setShort(destOffset, decodedValue);
               return 2;
            } else {
               try {
                  if (dest.order() == ByteOrder.BIG_ENDIAN) {
                     decodedValue = (decodabet[src0] & 63) << 18 | (decodabet[src1] & 255) << 12 | (decodabet[src2] & 255) << 6 | decodabet[src3] & 255;
                  } else {
                     b1 = decodabet[src1];
                     byte b2 = decodabet[src2];
                     decodedValue = (decodabet[src0] & 63) << 2 | (b1 & 15) << 12 | (b1 & 240) >>> 4 | (b2 & 3) << 22 | (b2 & 252) << 6 | (decodabet[src3] & 255) << 16;
                  }
               } catch (IndexOutOfBoundsException var13) {
                  throw new IllegalArgumentException("not encoded in Base64");
               }

               dest.setMedium(destOffset, decodedValue);
               return 3;
            }
         }
      }

      // $FF: synthetic method
      Decoder(Object x0) {
         this();
      }
   }
}
