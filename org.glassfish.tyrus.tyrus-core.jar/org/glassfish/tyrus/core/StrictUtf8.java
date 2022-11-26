package org.glassfish.tyrus.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class StrictUtf8 extends Charset {
   public StrictUtf8() {
      super("StrictUtf8", new String[0]);
   }

   public CharsetDecoder newDecoder() {
      return new Decoder(this);
   }

   public CharsetEncoder newEncoder() {
      return new Encoder(this);
   }

   public boolean contains(Charset cs) {
      return "StrictUtf8".equals(cs.name());
   }

   private static boolean isSurrogate(char ch) {
      return ch >= '\ud800' && ch < '\ue000';
   }

   private static char highSurrogate(int codePoint) {
      return (char)((codePoint >>> 10) + 'ퟀ');
   }

   private static char lowSurrogate(int codePoint) {
      return (char)((codePoint & 1023) + '\udc00');
   }

   private static void updatePositions(ByteBuffer src, int sp, CharBuffer dst, int dp) {
      src.position(sp - src.arrayOffset());
      dst.position(dp - dst.arrayOffset());
   }

   private static void updatePositions(CharBuffer src, int sp, ByteBuffer dst, int dp) {
      src.position(sp - src.arrayOffset());
      dst.position(dp - dst.arrayOffset());
   }

   public static class Parser {
      private CoderResult error;

      public Parser() {
         this.error = CoderResult.UNDERFLOW;
      }

      public CoderResult error() {
         assert this.error != null;

         return this.error;
      }

      public int parse(char c, CharBuffer in) {
         if (Character.isHighSurrogate(c)) {
            if (!in.hasRemaining()) {
               this.error = CoderResult.UNDERFLOW;
               return -1;
            } else {
               char d = in.get();
               if (Character.isLowSurrogate(d)) {
                  int character = Character.toCodePoint(c, d);
                  this.error = null;
                  return character;
               } else {
                  this.error = CoderResult.malformedForLength(1);
                  return -1;
               }
            }
         } else if (Character.isLowSurrogate(c)) {
            this.error = CoderResult.malformedForLength(1);
            return -1;
         } else {
            this.error = null;
            return c;
         }
      }

      public int parse(char c, char[] ia, int ip, int il) {
         assert ia[ip] == c;

         if (Character.isHighSurrogate(c)) {
            if (il - ip < 2) {
               this.error = CoderResult.UNDERFLOW;
               return -1;
            } else {
               char d = ia[ip + 1];
               if (Character.isLowSurrogate(d)) {
                  int character = Character.toCodePoint(c, d);
                  this.error = null;
                  return character;
               } else {
                  this.error = CoderResult.malformedForLength(1);
                  return -1;
               }
            }
         } else if (Character.isLowSurrogate(c)) {
            this.error = CoderResult.malformedForLength(1);
            return -1;
         } else {
            this.error = null;
            return c;
         }
      }
   }

   static final class Encoder extends CharsetEncoder {
      private Parser sgp;

      private Encoder(Charset cs) {
         super(cs, 1.1F, 3.0F);
      }

      public boolean canEncode(char c) {
         return !StrictUtf8.isSurrogate(c);
      }

      public boolean isLegalReplacement(byte[] repl) {
         return repl.length == 1 && repl[0] >= 0 || super.isLegalReplacement(repl);
      }

      private static CoderResult overflow(CharBuffer src, int sp, ByteBuffer dst, int dp) {
         StrictUtf8.updatePositions(src, sp, dst, dp);
         return CoderResult.OVERFLOW;
      }

      private static CoderResult overflow(CharBuffer src, int mark) {
         src.position(mark);
         return CoderResult.OVERFLOW;
      }

      private CoderResult encodeArrayLoop(CharBuffer src, ByteBuffer dst) {
         char[] sa = src.array();
         int sp = src.arrayOffset() + src.position();
         int sl = src.arrayOffset() + src.limit();
         byte[] da = dst.array();
         int dp = dst.arrayOffset() + dst.position();
         int dl = dst.arrayOffset() + dst.limit();

         for(int dlASCII = dp + Math.min(sl - sp, dl - dp); dp < dlASCII && sa[sp] < 128; da[dp++] = (byte)sa[sp++]) {
         }

         for(; sp < sl; ++sp) {
            char c = sa[sp];
            if (c < 128) {
               if (dp >= dl) {
                  return overflow(src, sp, dst, dp);
               }

               da[dp++] = (byte)c;
            } else if (c < 2048) {
               if (dl - dp < 2) {
                  return overflow(src, sp, dst, dp);
               }

               da[dp++] = (byte)(192 | c >> 6);
               da[dp++] = (byte)(128 | c & 63);
            } else if (StrictUtf8.isSurrogate(c)) {
               if (this.sgp == null) {
                  this.sgp = new Parser();
               }

               int uc = this.sgp.parse(c, sa, sp, sl);
               if (uc < 0) {
                  StrictUtf8.updatePositions(src, sp, dst, dp);
                  return this.sgp.error();
               }

               if (dl - dp < 4) {
                  return overflow(src, sp, dst, dp);
               }

               da[dp++] = (byte)(240 | uc >> 18);
               da[dp++] = (byte)(128 | uc >> 12 & 63);
               da[dp++] = (byte)(128 | uc >> 6 & 63);
               da[dp++] = (byte)(128 | uc & 63);
               ++sp;
            } else {
               if (dl - dp < 3) {
                  return overflow(src, sp, dst, dp);
               }

               da[dp++] = (byte)(224 | c >> 12);
               da[dp++] = (byte)(128 | c >> 6 & 63);
               da[dp++] = (byte)(128 | c & 63);
            }
         }

         StrictUtf8.updatePositions(src, sp, dst, dp);
         return CoderResult.UNDERFLOW;
      }

      private CoderResult encodeBufferLoop(CharBuffer src, ByteBuffer dst) {
         int mark;
         for(mark = src.position(); src.hasRemaining(); ++mark) {
            char c = src.get();
            if (c < 128) {
               if (!dst.hasRemaining()) {
                  return overflow(src, mark);
               }

               dst.put((byte)c);
            } else if (c < 2048) {
               if (dst.remaining() < 2) {
                  return overflow(src, mark);
               }

               dst.put((byte)(192 | c >> 6));
               dst.put((byte)(128 | c & 63));
            } else if (StrictUtf8.isSurrogate(c)) {
               if (this.sgp == null) {
                  this.sgp = new Parser();
               }

               int uc = this.sgp.parse(c, src);
               if (uc < 0) {
                  src.position(mark);
                  return this.sgp.error();
               }

               if (dst.remaining() < 4) {
                  return overflow(src, mark);
               }

               dst.put((byte)(240 | uc >> 18));
               dst.put((byte)(128 | uc >> 12 & 63));
               dst.put((byte)(128 | uc >> 6 & 63));
               dst.put((byte)(128 | uc & 63));
               ++mark;
            } else {
               if (dst.remaining() < 3) {
                  return overflow(src, mark);
               }

               dst.put((byte)(224 | c >> 12));
               dst.put((byte)(128 | c >> 6 & 63));
               dst.put((byte)(128 | c & 63));
            }
         }

         src.position(mark);
         return CoderResult.UNDERFLOW;
      }

      protected final CoderResult encodeLoop(CharBuffer src, ByteBuffer dst) {
         return src.hasArray() && dst.hasArray() ? this.encodeArrayLoop(src, dst) : this.encodeBufferLoop(src, dst);
      }

      public int encode(char[] sa, int sp, int len, byte[] da) {
         int sl = sp + len;
         int dp = 0;

         for(int dlASCII = dp + Math.min(len, da.length); dp < dlASCII && sa[sp] < 128; da[dp++] = (byte)sa[sp++]) {
         }

         while(sp < sl) {
            char c = sa[sp++];
            if (c < 128) {
               da[dp++] = (byte)c;
            } else if (c < 2048) {
               da[dp++] = (byte)(192 | c >> 6);
               da[dp++] = (byte)(128 | c & 63);
            } else if (StrictUtf8.isSurrogate(c)) {
               if (this.sgp == null) {
                  this.sgp = new Parser();
               }

               int uc = this.sgp.parse(c, sa, sp - 1, sl);
               if (uc < 0) {
                  if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                     return -1;
                  }

                  da[dp++] = this.replacement()[0];
               } else {
                  da[dp++] = (byte)(240 | uc >> 18);
                  da[dp++] = (byte)(128 | uc >> 12 & 63);
                  da[dp++] = (byte)(128 | uc >> 6 & 63);
                  da[dp++] = (byte)(128 | uc & 63);
                  ++sp;
               }
            } else {
               da[dp++] = (byte)(224 | c >> 12);
               da[dp++] = (byte)(128 | c >> 6 & 63);
               da[dp++] = (byte)(128 | c & 63);
            }
         }

         return dp;
      }

      // $FF: synthetic method
      Encoder(Charset x0, Object x1) {
         this(x0);
      }
   }

   private static class Decoder extends CharsetDecoder {
      private Decoder(Charset cs) {
         super(cs, 1.0F, 1.0F);
      }

      private static boolean isNotContinuation(int b) {
         return (b & 192) != 128;
      }

      private static boolean isMalformed3(int b1, int b2, int b3) {
         return b1 == -32 && (b2 & 224) == 128 || (b2 & 192) != 128 || (b3 & 192) != 128;
      }

      private static boolean isMalformed3_2(int b1, int b2) {
         return b1 == -32 && (b2 & 224) == 128 || (b2 & 192) != 128;
      }

      private static boolean isMalformed4(int b2, int b3, int b4) {
         return (b2 & 192) != 128 || (b3 & 192) != 128 || (b4 & 192) != 128;
      }

      private static boolean isMalformed4_2(int b1, int b2) {
         return b1 == 240 && b2 == 144 || (b2 & 192) != 128;
      }

      private static boolean isMalformed4_3(int b3) {
         return (b3 & 192) != 128;
      }

      private static CoderResult malformedN(ByteBuffer src, int nb) {
         switch (nb) {
            case 1:
            case 2:
               return CoderResult.malformedForLength(1);
            case 3:
               int b1 = src.get();
               int b2 = src.get();
               return CoderResult.malformedForLength((b1 != -32 || (b2 & 224) != 128) && !isNotContinuation(b2) ? 2 : 1);
            case 4:
               int b1 = src.get() & 255;
               int b2 = src.get() & 255;
               if (b1 <= 244 && (b1 != 240 || b2 >= 144 && b2 <= 191) && (b1 != 244 || (b2 & 240) == 128) && !isNotContinuation(b2)) {
                  if (isNotContinuation(src.get())) {
                     return CoderResult.malformedForLength(2);
                  }

                  return CoderResult.malformedForLength(3);
               }

               return CoderResult.malformedForLength(1);
            default:
               assert false;

               return null;
         }
      }

      private static CoderResult malformed(ByteBuffer src, int sp, CharBuffer dst, int dp, int nb) {
         src.position(sp - src.arrayOffset());
         CoderResult cr = malformedN(src, nb);
         StrictUtf8.updatePositions(src, sp, dst, dp);
         return cr;
      }

      private static CoderResult malformed(ByteBuffer src, int mark, int nb) {
         src.position(mark);
         CoderResult cr = malformedN(src, nb);
         src.position(mark);
         return cr;
      }

      private static CoderResult malformedForLength(ByteBuffer src, int sp, CharBuffer dst, int dp, int malformedNB) {
         StrictUtf8.updatePositions(src, sp, dst, dp);
         return CoderResult.malformedForLength(malformedNB);
      }

      private static CoderResult malformedForLength(ByteBuffer src, int mark, int malformedNB) {
         src.position(mark);
         return CoderResult.malformedForLength(malformedNB);
      }

      private static CoderResult xflow(ByteBuffer src, int sp, int sl, CharBuffer dst, int dp, int nb) {
         StrictUtf8.updatePositions(src, sp, dst, dp);
         return nb != 0 && sl - sp >= nb ? CoderResult.OVERFLOW : CoderResult.UNDERFLOW;
      }

      private static CoderResult xflow(Buffer src, int mark, int nb) {
         src.position(mark);
         return nb != 0 && src.remaining() >= nb ? CoderResult.OVERFLOW : CoderResult.UNDERFLOW;
      }

      private CoderResult decodeArrayLoop(ByteBuffer src, CharBuffer dst) {
         byte[] sa = src.array();
         int sp = src.arrayOffset() + src.position();
         int sl = src.arrayOffset() + src.limit();
         char[] da = dst.array();
         int dp = dst.arrayOffset() + dst.position();
         int dl = dst.arrayOffset() + dst.limit();

         for(int dlASCII = dp + Math.min(sl - sp, dl - dp); dp < dlASCII && sa[sp] >= 0; da[dp++] = (char)sa[sp++]) {
         }

         while(true) {
            while(sp < sl) {
               int b1 = sa[sp];
               if (b1 < 0) {
                  if (b1 >> 5 == -2 && (b1 & 30) != 0) {
                     if (sl - sp < 2 || dp >= dl) {
                        return xflow(src, sp, sl, dst, dp, 2);
                     }

                     int b2 = sa[sp + 1];
                     if (isNotContinuation(b2)) {
                        return malformedForLength(src, sp, dst, dp, 1);
                     }

                     da[dp++] = (char)(b1 << 6 ^ b2 ^ 3968);
                     sp += 2;
                  } else {
                     int srcRemaining;
                     byte b2;
                     byte b3;
                     char c;
                     if (b1 >> 4 == -2) {
                        srcRemaining = sl - sp;
                        if (srcRemaining < 3 || dp >= dl) {
                           if (srcRemaining > 1 && isMalformed3_2(b1, sa[sp + 1])) {
                              return malformedForLength(src, sp, dst, dp, 1);
                           }

                           return xflow(src, sp, sl, dst, dp, 3);
                        }

                        b2 = sa[sp + 1];
                        b3 = sa[sp + 2];
                        if (isMalformed3(b1, b2, b3)) {
                           return malformed(src, sp, dst, dp, 3);
                        }

                        c = (char)(b1 << 12 ^ b2 << 6 ^ b3 ^ -123008);
                        if (StrictUtf8.isSurrogate(c)) {
                           return malformedForLength(src, sp, dst, dp, 3);
                        }

                        da[dp++] = c;
                        sp += 3;
                     } else {
                        if (b1 >> 3 != -2) {
                           return malformed(src, sp, dst, dp, 1);
                        }

                        srcRemaining = sl - sp;
                        if (srcRemaining >= 4 && dl - dp >= 2) {
                           b2 = sa[sp + 1];
                           b3 = sa[sp + 2];
                           c = (char)sa[sp + 3];
                           int uc = b1 << 18 ^ b2 << 12 ^ b3 << 6 ^ c ^ 3678080;
                           if (!isMalformed4(b2, b3, c) && Character.isSupplementaryCodePoint(uc)) {
                              da[dp++] = StrictUtf8.highSurrogate(uc);
                              da[dp++] = StrictUtf8.lowSurrogate(uc);
                              sp += 4;
                              continue;
                           }

                           return malformed(src, sp, dst, dp, 4);
                        }

                        if (srcRemaining > 1 && isMalformed4_2(b1, sa[sp + 1])) {
                           return malformedForLength(src, sp, dst, dp, 1);
                        }

                        if (srcRemaining > 2 && isMalformed4_3(sa[sp + 2])) {
                           return malformedForLength(src, sp, dst, dp, 2);
                        }

                        return xflow(src, sp, sl, dst, dp, 4);
                     }
                  }
               } else {
                  if (dp >= dl) {
                     return xflow(src, sp, sl, dst, dp, 1);
                  }

                  da[dp++] = (char)b1;
                  ++sp;
               }
            }

            return xflow(src, sp, sl, dst, dp, 0);
         }
      }

      private CoderResult decodeBufferLoop(ByteBuffer src, CharBuffer dst) {
         int mark = src.position();
         int limit = src.limit();

         while(true) {
            while(mark < limit) {
               int b1 = src.get();
               if (b1 < 0) {
                  if (b1 >> 5 == -2 && (b1 & 30) != 0) {
                     if (limit - mark < 2 || dst.remaining() < 1) {
                        return xflow(src, mark, 2);
                     }

                     int b2 = src.get();
                     if (isNotContinuation(b2)) {
                        return malformedForLength(src, mark, 1);
                     }

                     dst.put((char)(b1 << 6 ^ b2 ^ 3968));
                     mark += 2;
                  } else {
                     int srcRemaining;
                     byte b2;
                     byte b3;
                     char c;
                     if (b1 >> 4 == -2) {
                        srcRemaining = limit - mark;
                        if (srcRemaining < 3 || dst.remaining() < 1) {
                           if (srcRemaining > 1 && isMalformed3_2(b1, src.get())) {
                              return malformedForLength(src, mark, 1);
                           }

                           return xflow(src, mark, 3);
                        }

                        b2 = src.get();
                        b3 = src.get();
                        if (isMalformed3(b1, b2, b3)) {
                           return malformed(src, mark, 3);
                        }

                        c = (char)(b1 << 12 ^ b2 << 6 ^ b3 ^ -123008);
                        if (StrictUtf8.isSurrogate(c)) {
                           return malformedForLength(src, mark, 3);
                        }

                        dst.put(c);
                        mark += 3;
                     } else {
                        if (b1 >> 3 != -2) {
                           return malformed(src, mark, 1);
                        }

                        srcRemaining = limit - mark;
                        if (srcRemaining >= 4 && dst.remaining() >= 2) {
                           b2 = src.get();
                           b3 = src.get();
                           c = (char)src.get();
                           int uc = b1 << 18 ^ b2 << 12 ^ b3 << 6 ^ c ^ 3678080;
                           if (!isMalformed4(b2, b3, c) && Character.isSupplementaryCodePoint(uc)) {
                              dst.put(StrictUtf8.highSurrogate(uc));
                              dst.put(StrictUtf8.lowSurrogate(uc));
                              mark += 4;
                              continue;
                           }

                           return malformed(src, mark, 4);
                        }

                        if (srcRemaining > 1 && isMalformed4_2(b1, src.get())) {
                           return malformedForLength(src, mark, 1);
                        }

                        if (srcRemaining > 2 && isMalformed4_3(src.get())) {
                           return malformedForLength(src, mark, 2);
                        }

                        return xflow(src, mark, 4);
                     }
                  }
               } else {
                  if (dst.remaining() < 1) {
                     return xflow(src, mark, 1);
                  }

                  dst.put((char)b1);
                  ++mark;
               }
            }

            return xflow(src, mark, 0);
         }
      }

      protected CoderResult decodeLoop(ByteBuffer src, CharBuffer dst) {
         return src.hasArray() && dst.hasArray() ? this.decodeArrayLoop(src, dst) : this.decodeBufferLoop(src, dst);
      }

      private static ByteBuffer getByteBuffer(ByteBuffer bb, byte[] ba, int sp) {
         if (bb == null) {
            bb = ByteBuffer.wrap(ba);
         }

         bb.position(sp);
         return bb;
      }

      public int decode(byte[] sa, int sp, int len, char[] da) {
         int sl = sp + len;
         int dp = 0;
         int dlASCII = Math.min(len, da.length);

         ByteBuffer bb;
         for(bb = null; dp < dlASCII && sa[sp] >= 0; da[dp++] = (char)sa[sp++]) {
         }

         while(true) {
            while(true) {
               while(sp < sl) {
                  int b1 = sa[sp++];
                  if (b1 < 0) {
                     byte b2;
                     if (b1 >> 5 != -2 || (b1 & 30) == 0) {
                        byte b3;
                        char c;
                        if (b1 >> 4 == -2) {
                           if (sp + 1 < sl) {
                              b2 = sa[sp++];
                              b3 = sa[sp++];
                              if (isMalformed3(b1, b2, b3)) {
                                 if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                                    return -1;
                                 }

                                 da[dp++] = this.replacement().charAt(0);
                                 sp -= 3;
                                 bb = getByteBuffer(bb, sa, sp);
                                 sp += malformedN(bb, 3).length();
                              } else {
                                 c = (char)(b1 << 12 ^ b2 << 6 ^ b3 ^ -123008);
                                 if (StrictUtf8.isSurrogate(c)) {
                                    if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                                       return -1;
                                    }

                                    da[dp++] = this.replacement().charAt(0);
                                 } else {
                                    da[dp++] = c;
                                 }
                              }
                           } else {
                              if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                                 return -1;
                              }

                              if (sp >= sl || !isMalformed3_2(b1, sa[sp])) {
                                 da[dp++] = this.replacement().charAt(0);
                                 return dp;
                              }

                              da[dp++] = this.replacement().charAt(0);
                           }
                        } else if (b1 >> 3 == -2) {
                           if (sp + 2 < sl) {
                              b2 = sa[sp++];
                              b3 = sa[sp++];
                              c = (char)sa[sp++];
                              int uc = b1 << 18 ^ b2 << 12 ^ b3 << 6 ^ c ^ 3678080;
                              if (!isMalformed4(b2, b3, c) && Character.isSupplementaryCodePoint(uc)) {
                                 da[dp++] = StrictUtf8.highSurrogate(uc);
                                 da[dp++] = StrictUtf8.lowSurrogate(uc);
                              } else {
                                 if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                                    return -1;
                                 }

                                 da[dp++] = this.replacement().charAt(0);
                                 sp -= 4;
                                 bb = getByteBuffer(bb, sa, sp);
                                 sp += malformedN(bb, 4).length();
                              }
                           } else {
                              if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                                 return -1;
                              }

                              if (sp < sl && isMalformed4_2(b1, sa[sp])) {
                                 da[dp++] = this.replacement().charAt(0);
                              } else {
                                 ++sp;
                                 if (sp >= sl || !isMalformed4_3(sa[sp])) {
                                    da[dp++] = this.replacement().charAt(0);
                                    return dp;
                                 }

                                 da[dp++] = this.replacement().charAt(0);
                              }
                           }
                        } else {
                           if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                              return -1;
                           }

                           da[dp++] = this.replacement().charAt(0);
                        }
                     } else {
                        if (sp >= sl) {
                           if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                              return -1;
                           }

                           da[dp++] = this.replacement().charAt(0);
                           return dp;
                        }

                        b2 = sa[sp++];
                        if (isNotContinuation(b2)) {
                           if (this.malformedInputAction() != CodingErrorAction.REPLACE) {
                              return -1;
                           }

                           da[dp++] = this.replacement().charAt(0);
                           --sp;
                        } else {
                           da[dp++] = (char)(b1 << 6 ^ b2 ^ 3968);
                        }
                     }
                  } else {
                     da[dp++] = (char)b1;
                  }
               }

               return dp;
            }
         }
      }

      // $FF: synthetic method
      Decoder(Charset x0, Object x1) {
         this(x0);
      }
   }
}
