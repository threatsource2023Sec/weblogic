package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeGlobal;
import org.mozilla.javascript.ScriptRuntime;

class RENode {
   public static final int ANCHORED = 1;
   public static final int SINGLE = 2;
   public static final int NONEMPTY = 4;
   public static final int ISNEXT = 8;
   public static final int GOODNEXT = 16;
   public static final int ISJOIN = 32;
   public static final int REALLOK = 64;
   public static final int MINIMAL = 128;
   byte op;
   byte flags;
   short offset;
   RENode next;
   Object kid;
   int kid2;
   int num;
   char chr;
   short min;
   short max;
   short kidlen;
   short bmsize;
   char[] s;
   byte[] bitmap;

   RENode(CompilerState var1, byte var2, Object var3) {
      this.op = var2;
      this.kid = var3;
   }

   void buildBitmap(MatchState var1, char[] var2, boolean var3) {
      int var4 = (Integer)this.kid;
      int var5 = this.kid2;
      byte var6 = 0;
      boolean var10 = false;
      this.kid2 = 0;
      if (var2[var4] == '^') {
         var10 = true;
         this.kid2 = -1;
         ++var4;
      }

      this.calcBMSize(var2, var4, var5, var3);
      this.bitmap = new byte[this.bmsize];
      int var7;
      if (var10) {
         var6 = -1;

         for(var7 = 0; var7 < this.bmsize; ++var7) {
            this.bitmap[var7] = -1;
         }

         this.bitmap[0] = -2;
      }

      int var11 = this.bmsize * 8;
      char var12 = (char)var11;
      boolean var13 = false;

      while(true) {
         label188:
         while(var4 < var5) {
            char var14 = var2[var4++];
            if (var14 == '\\') {
               var14 = var2[var4++];
               int var8;
               switch (var14) {
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                     var8 = NativeRegExp.unDigit(var14);
                     int var9 = var4 - 2;
                     var14 = var2[var4];
                     if (var14 >= '0' && var14 <= '7') {
                        ++var4;
                        var8 = 8 * var8 + NativeRegExp.unDigit(var14);
                        var14 = var2[var4];
                        if (var14 >= '0' && var14 <= '7') {
                           ++var4;
                           var7 = 8 * var8 + NativeRegExp.unDigit(var14);
                           if (var7 <= 255) {
                              var8 = var7;
                           } else {
                              --var4;
                           }
                        }
                     }

                     var14 = (char)var8;
                     break;
                  case 'D':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;

                     for(var14 = 0; var14 < '0'; ++var14) {
                        this.matchBit(var14, var6);
                     }

                     var14 = ':';

                     while(true) {
                        if (var14 >= var11) {
                           continue label188;
                        }

                        this.matchBit(var14, var6);
                        ++var14;
                     }
                  case 'S':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;
                     var14 = 0;

                     while(true) {
                        if (var14 >= var11) {
                           continue label188;
                        }

                        if (!Character.isWhitespace(var14)) {
                           this.matchBit(var14, var6);
                        }

                        ++var14;
                     }
                  case 'W':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;
                     var14 = 0;

                     while(true) {
                        if (var14 >= var11) {
                           continue label188;
                        }

                        if (!NativeRegExp.isWord(var14)) {
                           this.matchBit(var14, var6);
                        }

                        ++var14;
                     }
                  case 'b':
                  case 'f':
                  case 'n':
                  case 'r':
                  case 't':
                  case 'v':
                     var14 = NativeRegExp.getEscape(var14);
                     break;
                  case 'c':
                     var14 = var2[var4++];
                     var14 = Character.toUpperCase(var14);
                     var14 = (char)(var14 ^ 64);
                     break;
                  case 'd':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;
                     var14 = '0';

                     while(true) {
                        if (var14 > '9') {
                           continue label188;
                        }

                        this.matchBit(var14, var6);
                        ++var14;
                     }
                  case 's':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;
                     var14 = 0;

                     while(true) {
                        if (var14 >= var11) {
                           continue label188;
                        }

                        if (Character.isWhitespace(var14)) {
                           this.matchBit(var14, var6);
                        }

                        ++var14;
                     }
                  case 'u':
                     if (var2.length > var4 + 3 && NativeRegExp.isHex(var2[var4]) && NativeRegExp.isHex(var2[var4 + 1]) && NativeRegExp.isHex(var2[var4 + 2]) && NativeRegExp.isHex(var2[var4 + 3])) {
                        var8 = (((NativeRegExp.unHex(var2[var4]) << 4) + NativeRegExp.unHex(var2[var4 + 1]) << 4) + NativeRegExp.unHex(var2[var4 + 2]) << 4) + NativeRegExp.unHex(var2[var4 + 3]);
                        var14 = (char)var8;
                        var4 += 4;
                     }
                     break;
                  case 'w':
                     if (var13) {
                        this.checkRange(var12, var6);
                     }

                     var12 = (char)var11;

                     for(var14 = 0; var14 < var11; ++var14) {
                        if (NativeRegExp.isWord(var14)) {
                           this.matchBit(var14, var6);
                        }
                     }
                     continue;
                  case 'x':
                     if (var4 < var2.length && NativeRegExp.isHex(var14 = var2[var4++])) {
                        var8 = NativeRegExp.unHex(var14);
                        if (var4 < var2.length && NativeRegExp.isHex(var14 = var2[var4++])) {
                           var8 <<= 4;
                           var8 += NativeRegExp.unHex(var14);
                        }
                     } else {
                        var4 = var4;
                        var8 = 120;
                     }

                     var14 = (char)var8;
               }
            }

            if (var13) {
               if (var12 > var14) {
                  throw NativeGlobal.constructError(Context.getCurrentContext(), "RangeError", ScriptRuntime.getMessage("msg.bad.range", (Object[])null), var1.scope);
               }

               var13 = false;
            } else {
               var12 = var14;
               if (var4 + 1 < var5 && var2[var4] == '-' && var2[var4 + 1] != ']') {
                  ++var4;
                  var13 = true;
                  continue;
               }
            }

            for(; var12 <= var14; ++var12) {
               this.matchBit(var12, var6);
               if (var3) {
                  char var15 = Character.toUpperCase(var12);
                  this.matchBit(var15, var6);
                  var15 = Character.toLowerCase(var15);
                  this.matchBit(var15, var6);
               }
            }

            var12 = var14;
         }

         return;
      }
   }

   private void calcBMSize(char[] var1, int var2, int var3, boolean var4) {
      char var5 = 0;

      while(true) {
         while(var2 < var3) {
            char var6 = var1[var2++];
            if (var6 == '\\') {
               if (var2 + 5 > var3 || var1[var2] != 'u' || !NativeRegExp.isHex(var1[var2 + 1]) || !NativeRegExp.isHex(var1[var2 + 2]) || !NativeRegExp.isHex(var1[var2 + 3]) || !NativeRegExp.isHex(var1[var2 + 4])) {
                  if (var5 < 255) {
                     var5 = 255;
                  }
                  continue;
               }

               int var7 = (((NativeRegExp.unHex(var1[var2]) << 4) + NativeRegExp.unHex(var1[var2 + 1]) << 4) + NativeRegExp.unHex(var1[var2 + 2]) << 4) + NativeRegExp.unHex(var1[var2 + 3]);
               var6 = (char)var7;
               var2 += 5;
            }

            if (var4) {
               char var8;
               if ((var8 = Character.toUpperCase(var6)) > var5) {
                  var5 = var8;
               }

               if ((var8 = Character.toLowerCase(var8)) > var5) {
                  var5 = var8;
               }
            }

            if (var6 > var5) {
               var5 = var6;
            }
         }

         this.bmsize = (short)((var5 + 8) / 8);
         return;
      }
   }

   private void checkRange(char var1, int var2) {
      this.matchBit(var1, var2);
      this.matchBit('-', var2);
   }

   private void matchBit(char var1, int var2) {
      int var3 = var1 >> 3;
      byte var4 = (byte)(var1 & 7);
      var4 = (byte)(1 << var4);
      byte[] var10000;
      if (var2 != 0) {
         var10000 = this.bitmap;
         var10000[var3] = (byte)(var10000[var3] & ~var4);
      } else {
         var10000 = this.bitmap;
         var10000[var3] |= var4;
      }

   }
}
