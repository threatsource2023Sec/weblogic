package monfox.toolkit.snmp.ber;

final class b {
   public static final int MAX_TAG_VALUE_LENGTH = 10;
   private static long[] a = new long[]{65408L, 16744448L, 4286578688L, 1097364144128L, 280925220896768L, 71916856549572608L, -36028797018963968L};
   private static long[] b = new long[]{65280L, 16711680L, 4278190080L, 1095216660480L, 280375465082880L, 71776119061217280L, -72057594037927936L};
   /** @deprecated */
   private static final String c = "$Id: BEREncoder.java,v 1.10 2003/09/23 21:06:55 sking Exp $";

   public static int longToCompactByteArray(BERBuffer var0, long var1, int var3, boolean var4) {
      int var7;
      int var10;
      int var10000;
      label68: {
         int var5;
         label72: {
            var10 = BERBuffer.j;
            var5 = 0;
            long[] var6 = var4 ? a : b;
            if (var1 < 0L && var4) {
               var7 = var3 - 2;

               while(var7 >= 0) {
                  long var11;
                  var10000 = (var11 = (var1 & var6[var7]) - var6[var7]) == 0L ? 0 : (var11 < 0L ? -1 : 1);
                  if (var10 != 0) {
                     break label68;
                  }

                  if (var10000 != 0) {
                     break;
                  }

                  ++var5;
                  --var7;
                  if (var10 != 0) {
                     break;
                  }
               }

               if (var10 == 0) {
                  break label72;
               }
            }

            var7 = var3 - 2;

            while(var7 >= 0) {
               long var12;
               var10000 = (var12 = (var1 & var6[var7]) - 0L) == 0L ? 0 : (var12 < 0L ? -1 : 1);
               if (var10 != 0) {
                  break label68;
               }

               if (var10000 != 0) {
                  break;
               }

               ++var5;
               --var7;
               if (var10 != 0) {
                  break;
               }
            }
         }

         var7 = 0;
         var10000 = var3 - var5;
      }

      int var8 = var10000;
      int var9 = 0;

      while(true) {
         if (var9 < var8) {
            var7 = (byte)((int)(255L & var1 >> 8 * var9));
            var0.a((byte)var7);
            ++var9;
            if (var10 != 0) {
               break;
            }

            if (var10 == 0) {
               continue;
            }
         }

         if (!var4 && (var7 & 128) == 128) {
            var0.a((byte)0);
            ++var8;
         }
         break;
      }

      return var8;
   }

   public static int intToCompactByteArray(BERBuffer var0, int var1) {
      int var2;
      int var5;
      label36: {
         var5 = BERBuffer.j;
         var2 = 0;
         if (var1 < 0) {
            if ((var1 & -8388608) != -8388608) {
               break label36;
            }

            ++var2;
            if ((var1 & 16744448) != 1046528) {
               break label36;
            }

            ++var2;
            if ((var1 & 'ﾀ') != 65408) {
               break label36;
            }

            ++var2;
            if (var5 == 0) {
               break label36;
            }
         }

         if ((var1 & -8388608) == 0) {
            ++var2;
            if ((var1 & 16744448) == 0) {
               ++var2;
               if ((var1 & 'ﾀ') == 0) {
                  ++var2;
               }
            }
         }
      }

      int var3 = 4 - var2;
      int var4 = 0;

      while(var4 < var3) {
         var0.a((byte)(255 & var1 >> 8 * var4));
         ++var4;
         if (var5 != 0) {
            break;
         }
      }

      return var3;
   }

   public static int intToByteArray(BERBuffer var0, int var1) {
      int var2;
      int var6;
      label40: {
         var6 = BERBuffer.j;
         var2 = 0;
         if (var1 < 0) {
            if ((var1 & -16777216) != -16777216) {
               break label40;
            }

            ++var2;
            if ((var1 & 16711680) != 1044480) {
               break label40;
            }

            ++var2;
            if ((var1 & '\uff00') != 65280) {
               break label40;
            }

            ++var2;
            if (var6 == 0) {
               break label40;
            }
         }

         if ((var1 & -16777216) == 0) {
            ++var2;
            if ((var1 & 16711680) == 0) {
               ++var2;
               if ((var1 & '\uff00') == 0) {
                  ++var2;
               }
            }
         }
      }

      int var3 = 4 - var2;
      int var4 = 0;

      int var10000;
      while(true) {
         if (var4 < var3) {
            var10000 = (byte)(255 & var1 >> 8 * var4);
            if (var6 != 0) {
               break;
            }

            int var5 = var10000;
            var0.a((byte)var5);
            ++var4;
            if (var6 == 0) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public static int lengthToByteArray(BERBuffer var0, int var1) {
      int var2;
      int var6;
      label40: {
         var6 = BERBuffer.j;
         var2 = 0;
         if (var1 < 0) {
            if ((var1 & -16777216) != -16777216) {
               break label40;
            }

            ++var2;
            if ((var1 & 16711680) != 1044480) {
               break label40;
            }

            ++var2;
            if ((var1 & '\uff00') != 65280) {
               break label40;
            }

            ++var2;
            if (var6 == 0) {
               break label40;
            }
         }

         if ((var1 & -16777216) == 0) {
            ++var2;
            if ((var1 & 16711680) == 0) {
               ++var2;
               if ((var1 & '\uff00') == 0) {
                  ++var2;
               }
            }
         }
      }

      int var3 = 4 - var2;
      int var4 = 0;

      while(true) {
         if (var4 < var3) {
            byte var5 = (byte)(255 & var1 >> 8 * var4);
            var0.a(var5);
            ++var4;
            if (var6 != 0) {
               break;
            }

            if (var6 == 0) {
               continue;
            }
         }

         byte var7 = (byte)(var3 | 128);
         var0.a(var7);
         break;
      }

      return var3 + 1;
   }

   public static int significantBitEncoding(BERBuffer var0, int var1) {
      int var2;
      int var6;
      label53: {
         var6 = BERBuffer.j;
         var2 = 0;
         if (var1 < 0) {
            if ((var1 & -268435456) != -268435456) {
               break label53;
            }

            ++var2;
            if ((var1 & 266338304) != 266338304) {
               break label53;
            }

            ++var2;
            if ((var1 & 2080768) != 2080768) {
               break label53;
            }

            ++var2;
            if ((var1 & 16256) != 261248) {
               break label53;
            }

            ++var2;
            if (var6 == 0) {
               break label53;
            }
         }

         if ((var1 & -268435456) == 0) {
            ++var2;
            if ((var1 & 266338304) == 0) {
               ++var2;
               if ((var1 & 2080768) == 0) {
                  ++var2;
                  if ((var1 & 16256) == 0) {
                     ++var2;
                  }
               }
            }
         }
      }

      int var3 = 5 - var2;
      boolean var4 = false;
      int var5 = 0;

      int var10000;
      while(true) {
         if (var5 < var3) {
            var4 = false;
            byte var7 = (byte)((int)(127L & (long)(var1 >> 7 * var5)));
            var10000 = var5;
            if (var6 != 0) {
               break;
            }

            label34: {
               if (var5 > 0) {
                  var7 |= -128;
                  if (var6 == 0) {
                     break label34;
                  }
               }

               var7 = (byte)(var7 & 127);
            }

            var0.a(var7);
            ++var5;
            if (var6 == 0) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public static int significantBitEncoding(BERBuffer var0, long var1) {
      return significantBitEncoding(var0, var1, true);
   }

   public static int significantBitEncoding(BERBuffer var0, long var1, boolean var3) {
      int var4;
      int var8;
      label80: {
         var8 = BERBuffer.j;
         var4 = 0;
         if (var1 < 0L && var3) {
            if ((var1 & Long.MIN_VALUE) != Long.MIN_VALUE) {
               break label80;
            }

            ++var4;
            if ((var1 & 9151314442816847872L) != 9151314442816847872L) {
               break label80;
            }

            ++var4;
            if ((var1 & 71494644084506624L) != 71494644084506624L) {
               break label80;
            }

            ++var4;
            if ((var1 & 558551906910208L) != 558551906910208L) {
               break label80;
            }

            ++var4;
            if ((var1 & 4363686772736L) != 4363686772736L) {
               break label80;
            }

            ++var4;
            if ((var1 & 34091302912L) != 34091302912L) {
               break label80;
            }

            ++var4;
            if ((var1 & 266338304L) != 266338304L) {
               break label80;
            }

            ++var4;
            if ((var1 & 2080768L) != 2080768L) {
               break label80;
            }

            ++var4;
            if ((var1 & 16256L) != 16256L) {
               break label80;
            }

            ++var4;
            if (var8 == 0) {
               break label80;
            }
         }

         if ((var1 & Long.MIN_VALUE) == 0L) {
            ++var4;
            if ((var1 & 9151314442816847872L) == 0L) {
               ++var4;
               if ((var1 & 71494644084506624L) == 0L) {
                  ++var4;
                  if ((var1 & 558551906910208L) == 0L) {
                     ++var4;
                     if ((var1 & 4363686772736L) == 0L) {
                        ++var4;
                        if ((var1 & 34091302912L) == 0L) {
                           ++var4;
                           if ((var1 & 266338304L) == 0L) {
                              ++var4;
                              if ((var1 & 2080768L) == 0L) {
                                 ++var4;
                                 if ((var1 & 16256L) == 0L) {
                                    ++var4;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      int var5 = 10 - var4;
      boolean var6 = false;
      int var7 = 0;

      int var10000;
      while(true) {
         if (var7 < var5) {
            var6 = false;
            byte var9 = (byte)((int)(127L & var1 >> 7 * var7));
            var10000 = var7;
            if (var8 != 0) {
               break;
            }

            label50: {
               if (var7 > 0) {
                  var9 |= -128;
                  if (var8 == 0) {
                     break label50;
                  }
               }

               var9 = (byte)(var9 & 127);
            }

            var0.a(var9);
            ++var7;
            if (var8 == 0) {
               continue;
            }
         }

         var10000 = var5;
         break;
      }

      return var10000;
   }

   public static int encodeLength(BERBuffer var0, int var1) throws NumberFormatException {
      if (var1 < 0) {
         throw new NumberFormatException();
      } else if (var1 < 128) {
         var0.a((byte)var1);
         return 1;
      } else {
         return lengthToByteArray(var0, var1);
      }
   }

   public static int encodeTagValue(BERBuffer var0, int var1) throws NumberFormatException {
      if (var1 < 0) {
         throw new NumberFormatException();
      } else if ((var1 & 31) < 31) {
         var0.a((byte)var1);
         return 1;
      } else {
         int var2 = significantBitEncoding(var0, var1);
         var0.a((byte)31);
         return var2 + 1;
      }
   }
}
