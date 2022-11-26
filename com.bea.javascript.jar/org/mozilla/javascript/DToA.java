package org.mozilla.javascript;

import java.math.BigInteger;

class DToA {
   static final int DTOBASESTR_BUFFER_SIZE = 1078;
   static final int DTOSTR_STANDARD = 0;
   static final int DTOSTR_STANDARD_EXPONENTIAL = 1;
   static final int DTOSTR_FIXED = 2;
   static final int DTOSTR_EXPONENTIAL = 3;
   static final int DTOSTR_PRECISION = 4;
   static final int Frac_mask = 1048575;
   static final int Exp_shift = 20;
   static final int Exp_msk1 = 1048576;
   static final int Bias = 1023;
   static final int P = 53;
   static final int Exp_shift1 = 20;
   static final int Exp_mask = 2146435072;
   static final int Bndry_mask = 1048575;
   static final int Log2P = 1;
   static final int Sign_bit = Integer.MIN_VALUE;
   static final int Exp_11 = 1072693248;
   static final int Ten_pmax = 22;
   static final int Quick_max = 14;
   static final int Bletch = 16;
   static final int Frac_mask1 = 1048575;
   static final int Int_max = 14;
   static final int n_bigtens = 5;
   static final double[] tens = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21, 1.0E22};
   static final double[] bigtens = new double[]{1.0E16, 1.0E32, 1.0E64, 1.0E128, 1.0E256};
   private static final int[] dtoaModes = new int[]{0, 0, 3, 2, 2};

   static char BASEDIGIT(int var0) {
      return (char)(var0 >= 10 ? 87 + var0 : 48 + var0);
   }

   static int JS_dtoa(double var0, int var2, boolean var3, int var4, boolean[] var5, StringBuffer var6) {
      int[] var33 = new int[1];
      int[] var34 = new int[1];
      if ((word0(var0) & Integer.MIN_VALUE) != 0) {
         var5[0] = true;
         var0 = setWord0(var0, word0(var0) & Integer.MAX_VALUE);
      } else {
         var5[0] = false;
      }

      if ((word0(var0) & 2146435072) == 2146435072) {
         var6.append(word1(var0) == 0 && (word0(var0) & 1048575) == 0 ? "Infinity" : "NaN");
         return 9999;
      } else if (var0 == 0.0) {
         var6.setLength(0);
         var6.append('0');
         return 1;
      } else {
         BigInteger var27 = d2b(var0, var33, var34);
         int var9;
         double var35;
         boolean var42;
         if ((var9 = word0(var0) >>> 20 & 2047) != 0) {
            var35 = setWord0(var0, word0(var0) & 1048575 | 1072693248);
            var9 -= 1023;
            var42 = false;
         } else {
            var9 = var34[0] + var33[0] + 1074;
            long var25 = (long)(var9 > 32 ? word0(var0) << 64 - var9 | word1(var0) >>> var9 - 32 : word1(var0) << 32 - var9);
            var35 = setWord0((double)var25, word0((double)var25) - 32505856);
            var9 -= 1075;
            var42 = true;
         }

         double var37 = (var35 - 1.5) * 0.289529654602168 + 0.1760912590558 + (double)var9 * 0.301029995663981;
         int var16 = (int)var37;
         if (var37 < 0.0 && var37 != (double)var16) {
            --var16;
         }

         boolean var43 = true;
         if (var16 >= 0 && var16 <= 22) {
            if (var0 < tens[var16]) {
               --var16;
            }

            var43 = false;
         }

         int var14 = var34[0] - var9 - 1;
         int var7;
         int var20;
         if (var14 >= 0) {
            var7 = 0;
            var20 = var14;
         } else {
            var7 = -var14;
            var20 = 0;
         }

         int var8;
         int var21;
         if (var16 >= 0) {
            var8 = 0;
            var21 = var16;
            var20 += var16;
         } else {
            var7 -= var16;
            var8 = -var16;
            var21 = 0;
         }

         if (var2 < 0 || var2 > 9) {
            var2 = 0;
         }

         boolean var44 = true;
         if (var2 > 5) {
            var2 -= 4;
            var44 = false;
         }

         boolean var45 = true;
         int var13 = 0;
         int var11 = 0;
         boolean var51;
         switch (var2) {
            case 0:
            case 1:
               var13 = -1;
               var11 = -1;
               var51 = true;
               var4 = 0;
               break;
            case 2:
               var45 = false;
            case 4:
               if (var4 <= 0) {
                  var4 = 1;
               }

               var13 = var4;
               var11 = var4;
               break;
            case 3:
               var45 = false;
            case 5:
               var9 = var4 + var16 + 1;
               var11 = var9;
               var13 = var9 - 1;
               if (var9 <= 0) {
                  var51 = true;
               }
         }

         boolean var46 = false;
         int var15;
         long var23;
         BigInteger var31;
         BigInteger var32;
         char var47;
         if (var11 >= 0 && var11 <= 14 && var44) {
            var9 = 0;
            var35 = var0;
            int var12 = var11;
            int var10 = 2;
            if (var16 <= 0) {
               if ((var15 = -var16) != 0) {
                  var0 *= tens[var15 & 15];

                  for(var14 = var15 >> 4; var14 != 0; ++var9) {
                     if ((var14 & 1) != 0) {
                        ++var10;
                        var0 *= bigtens[var9];
                     }

                     var14 >>= 1;
                  }
               }
            } else {
               var37 = tens[var16 & 15];
               var14 = var16 >> 4;
               if ((var14 & 16) != 0) {
                  var14 &= 15;
                  var0 /= bigtens[4];
                  ++var10;
               }

               while(var14 != 0) {
                  if ((var14 & 1) != 0) {
                     ++var10;
                     var37 *= bigtens[var9];
                  }

                  var14 >>= 1;
                  ++var9;
               }

               var0 /= var37;
            }

            if (var43 && var0 < 1.0 && var11 > 0) {
               if (var13 <= 0) {
                  var46 = true;
               } else {
                  var11 = var13;
                  --var16;
                  var0 *= 10.0;
                  ++var10;
               }
            }

            double var39 = (double)var10 * var0 + 7.0;
            var39 = setWord0(var39, word0(var39) - 54525952);
            if (var11 == 0) {
               var31 = null;
               var32 = null;
               var0 -= 5.0;
               if (var0 > var39) {
                  var6.append('1');
                  ++var16;
                  return var16 + 1;
               }

               if (var0 < -var39) {
                  var6.setLength(0);
                  var6.append('0');
                  return 1;
               }

               var46 = true;
            }

            if (!var46) {
               var46 = true;
               if (var45) {
                  var39 = 0.5 / tens[var11 - 1] - var39;
                  var9 = 0;

                  while(true) {
                     var23 = (long)var0;
                     var0 -= (double)var23;
                     var6.append((char)((int)(48L + var23)));
                     if (var0 < var39) {
                        return var16 + 1;
                     }

                     if (1.0 - var0 < var39) {
                        while(true) {
                           var47 = var6.charAt(var6.length() - 1);
                           var6.setLength(var6.length() - 1);
                           if (var47 != '9') {
                              break;
                           }

                           if (var6.length() == 0) {
                              ++var16;
                              var47 = '0';
                              break;
                           }
                        }

                        var6.append((char)(var47 + 1));
                        return var16 + 1;
                     }

                     ++var9;
                     if (var9 >= var11) {
                        break;
                     }

                     var39 *= 10.0;
                     var0 *= 10.0;
                  }
               } else {
                  var39 *= tens[var11 - 1];
                  var9 = 1;

                  while(true) {
                     var23 = (long)var0;
                     var0 -= (double)var23;
                     var6.append((char)((int)(48L + var23)));
                     if (var9 == var11) {
                        if (var0 > 0.5 + var39) {
                           while(true) {
                              var47 = var6.charAt(var6.length() - 1);
                              var6.setLength(var6.length() - 1);
                              if (var47 != '9') {
                                 break;
                              }

                              if (var6.length() == 0) {
                                 ++var16;
                                 var47 = '0';
                                 break;
                              }
                           }

                           var6.append((char)(var47 + 1));
                           return var16 + 1;
                        }

                        if (var0 < 0.5 - var39) {
                           while(var6.charAt(var6.length() - 1) == '0') {
                              var6.setLength(var6.length() - 1);
                           }

                           return var16 + 1;
                        }
                        break;
                     }

                     ++var9;
                     var0 *= 10.0;
                  }
               }
            }

            if (var46) {
               var6.setLength(0);
               var0 = var35;
               var16 = var16;
               var11 = var12;
            }
         }

         if (var33[0] >= 0 && var16 <= 14) {
            var37 = tens[var16];
            if (var4 < 0 && var11 <= 0) {
               var31 = null;
               var32 = null;
               if (var11 < 0 || var0 < 5.0 * var37 || !var3 && var0 == 5.0 * var37) {
                  var6.setLength(0);
                  var6.append('0');
                  return 1;
               } else {
                  var6.append('1');
                  ++var16;
                  return var16 + 1;
               }
            } else {
               var9 = 1;

               while(true) {
                  var23 = (long)(var0 / var37);
                  var0 -= (double)var23 * var37;
                  var6.append((char)((int)(48L + var23)));
                  if (var9 == var11) {
                     var0 += var0;
                     if (var0 > var37 || var0 == var37 && ((var23 & 1L) != 0L || var3)) {
                        while(true) {
                           var47 = var6.charAt(var6.length() - 1);
                           var6.setLength(var6.length() - 1);
                           if (var47 != '9') {
                              break;
                           }

                           if (var6.length() == 0) {
                              ++var16;
                              var47 = '0';
                              break;
                           }
                        }

                        var6.append((char)(var47 + 1));
                     }
                     break;
                  }

                  var0 *= 10.0;
                  if (var0 == 0.0) {
                     break;
                  }

                  ++var9;
               }

               return var16 + 1;
            }
         } else {
            int var18 = var7;
            int var19 = var8;
            BigInteger var30 = null;
            var31 = null;
            if (var45) {
               if (var2 < 2) {
                  var9 = var42 ? var33[0] + 1075 : 54 - var34[0];
               } else {
                  var14 = var11 - 1;
                  if (var8 >= var14) {
                     var19 = var8 - var14;
                  } else {
                     var21 += var14 -= var8;
                     var8 += var14;
                     var19 = 0;
                  }

                  var9 = var11;
                  if (var11 < 0) {
                     var18 = var7 - var11;
                     var9 = 0;
                  }
               }

               var7 += var9;
               var20 += var9;
               var31 = BigInteger.valueOf(1L);
            }

            if (var18 > 0 && var20 > 0) {
               var9 = var18 < var20 ? var18 : var20;
               var7 -= var9;
               var18 -= var9;
               var20 -= var9;
            }

            if (var8 > 0) {
               if (var45) {
                  if (var19 > 0) {
                     var31 = pow5mult(var31, var19);
                     BigInteger var28 = var31.multiply(var27);
                     var27 = var28;
                  }

                  if ((var14 = var8 - var19) != 0) {
                     var27 = pow5mult(var27, var14);
                  }
               } else {
                  var27 = pow5mult(var27, var8);
               }
            }

            var32 = BigInteger.valueOf(1L);
            if (var21 > 0) {
               var32 = pow5mult(var32, var21);
            }

            boolean var41 = false;
            if (var2 < 2 && word1(var0) == 0 && (word0(var0) & 1048575) == 0 && (word0(var0) & 2145386496) != 0) {
               ++var7;
               ++var20;
               var41 = true;
            }

            byte[] var52 = var32.toByteArray();
            int var48 = 0;

            for(int var49 = 0; var49 < 4; ++var49) {
               var48 <<= 8;
               if (var49 < var52.length) {
                  var48 |= var52[var49] & 255;
               }
            }

            if ((var9 = (var21 != 0 ? 32 - hi0bits(var48) : 1) + var20 & 31) != 0) {
               var9 = 32 - var9;
            }

            if (var9 > 4) {
               var9 -= 4;
               var7 += var9;
               var18 += var9;
               var20 += var9;
            } else if (var9 < 4) {
               var9 += 28;
               var7 += var9;
               var18 += var9;
               var20 += var9;
            }

            if (var7 > 0) {
               var27 = var27.shiftLeft(var7);
            }

            if (var20 > 0) {
               var32 = var32.shiftLeft(var20);
            }

            if (var43 && var27.compareTo(var32) < 0) {
               --var16;
               var27 = var27.multiply(BigInteger.valueOf(10L));
               if (var45) {
                  var31 = var31.multiply(BigInteger.valueOf(10L));
               }

               var11 = var13;
            }

            if (var11 <= 0 && var2 > 2) {
               if (var11 >= 0 && (var9 = var27.compareTo(var32.multiply(BigInteger.valueOf(5L)))) >= 0 && (var9 != 0 || var3)) {
                  var6.append('1');
                  ++var16;
                  return var16 + 1;
               } else {
                  var6.setLength(0);
                  var6.append('0');
                  return 1;
               }
            } else {
               char var22;
               BigInteger[] var50;
               if (!var45) {
                  var9 = 1;

                  while(true) {
                     var50 = var27.divideAndRemainder(var32);
                     var27 = var50[1];
                     var22 = (char)(var50[0].intValue() + 48);
                     var6.append(var22);
                     if (var9 >= var11) {
                        break;
                     }

                     var27 = var27.multiply(BigInteger.valueOf(10L));
                     ++var9;
                  }
               } else {
                  if (var18 > 0) {
                     var31 = var31.shiftLeft(var18);
                  }

                  var30 = var31;
                  if (var41) {
                     var31 = var31.shiftLeft(1);
                  }

                  var9 = 1;

                  while(true) {
                     var50 = var27.divideAndRemainder(var32);
                     var27 = var50[1];
                     var22 = (char)(var50[0].intValue() + 48);
                     var14 = var27.compareTo(var30);
                     BigInteger var29 = var32.subtract(var31);
                     var15 = var29.signum() <= 0 ? 1 : var27.compareTo(var29);
                     if (var15 == 0 && var2 == 0 && (word1(var0) & 1) == 0) {
                        if (var22 == '9') {
                           var6.append('9');
                           if (roundOff(var6)) {
                              ++var16;
                              var6.append('1');
                           }

                           return var16 + 1;
                        }

                        if (var14 > 0) {
                           ++var22;
                        }

                        var6.append(var22);
                        return var16 + 1;
                     }

                     if (var14 < 0 || var14 == 0 && var2 == 0 && (word1(var0) & 1) == 0) {
                        if (var15 > 0) {
                           var27 = var27.shiftLeft(1);
                           var15 = var27.compareTo(var32);
                           if ((var15 > 0 || var15 == 0 && ((var22 & 1) == 1 || var3)) && var22++ == '9') {
                              var6.append('9');
                              if (roundOff(var6)) {
                                 ++var16;
                                 var6.append('1');
                              }

                              return var16 + 1;
                           }
                        }

                        var6.append(var22);
                        return var16 + 1;
                     }

                     if (var15 > 0) {
                        if (var22 == '9') {
                           var6.append('9');
                           if (roundOff(var6)) {
                              ++var16;
                              var6.append('1');
                           }

                           return var16 + 1;
                        }

                        var6.append((char)(var22 + 1));
                        return var16 + 1;
                     }

                     var6.append(var22);
                     if (var9 == var11) {
                        break;
                     }

                     var27 = var27.multiply(BigInteger.valueOf(10L));
                     if (var30 == var31) {
                        var30 = var31 = var31.multiply(BigInteger.valueOf(10L));
                     } else {
                        var30 = var30.multiply(BigInteger.valueOf(10L));
                        var31 = var31.multiply(BigInteger.valueOf(10L));
                     }

                     ++var9;
                  }
               }

               var27 = var27.shiftLeft(1);
               var14 = var27.compareTo(var32);
               if (var14 <= 0 && (var14 != 0 || (var22 & 1) != 1 && !var3)) {
                  while(var6.charAt(var6.length() - 1) == '0') {
                     var6.setLength(var6.length() - 1);
                  }
               } else if (roundOff(var6)) {
                  ++var16;
                  var6.append('1');
                  return var16 + 1;
               }

               return var16 + 1;
            }
         }
      }
   }

   public static String JS_dtobasestr(int var0, double var1) {
      char[] var3 = new char[1078];
      int var4 = 0;
      if (var1 < 0.0) {
         var3[var4++] = '-';
         var1 = -var1;
      }

      if (Double.isNaN(var1)) {
         return "NaN";
      } else if (Double.isInfinite(var1)) {
         return "Infinity";
      } else {
         double var8 = (double)((int)var1);
         BigInteger var12 = BigInteger.valueOf((long)((int)var8));
         String var13 = var12.toString(var0);
         var13.getChars(0, var13.length(), var3, var4);
         var4 += var13.length();
         double var10 = var1 - var8;
         if (var10 != 0.0) {
            var3[var4++] = '.';
            long var14 = Double.doubleToLongBits(var1);
            int var16 = (int)(var14 >> 32);
            int var17 = (int)var14;
            int[] var18 = new int[1];
            int[] var19 = new int[1];
            var12 = d2b(var10, var18, var19);
            int var20 = -(var16 >>> 20 & 2047);
            if (var20 == 0) {
               var20 = -1;
            }

            var20 += 1076;
            BigInteger var21 = BigInteger.valueOf(1L);
            BigInteger var22 = var21;
            if (var17 == 0 && (var16 & 1048575) == 0 && (var16 & 2145386496) != 0) {
               ++var20;
               var22 = BigInteger.valueOf(2L);
            }

            var12 = var12.shiftLeft(var18[0] + var20);
            BigInteger var23 = BigInteger.valueOf(1L);
            var23 = var23.shiftLeft(var20);
            BigInteger var24 = BigInteger.valueOf((long)var0);
            boolean var25 = false;

            do {
               var12 = var12.multiply(var24);
               BigInteger[] var26 = var12.divideAndRemainder(var23);
               var12 = var26[1];
               int var7 = (char)var26[0].intValue();
               if (var21 == var22) {
                  var21 = var22 = var21.multiply(var24);
               } else {
                  var21 = var21.multiply(var24);
                  var22 = var22.multiply(var24);
               }

               int var27 = var12.compareTo(var21);
               BigInteger var28 = var23.subtract(var22);
               int var29 = var28.signum() <= 0 ? 1 : var12.compareTo(var28);
               if (var29 == 0 && (var17 & 1) == 0) {
                  if (var27 > 0) {
                     ++var7;
                  }

                  var25 = true;
               } else if (var27 < 0 || var27 == 0 && (var17 & 1) == 0) {
                  if (var29 > 0) {
                     var12 = var12.shiftLeft(1);
                     var29 = var12.compareTo(var23);
                     if (var29 > 0) {
                        ++var7;
                     }
                  }

                  var25 = true;
               } else if (var29 > 0) {
                  ++var7;
                  var25 = true;
               }

               var3[var4++] = BASEDIGIT(var7);
            } while(!var25);
         }

         return new String(var3, 0, var4);
      }
   }

   static void JS_dtostr(StringBuffer var0, int var1, int var2, double var3) {
      boolean[] var6 = new boolean[1];
      if (var1 == 2 && (var3 >= 1.0E21 || var3 <= -1.0E21)) {
         var1 = 0;
      }

      int var5 = JS_dtoa(var3, dtoaModes[var1], var1 >= 2, var2, var6, var0);
      int var7 = var0.length();
      if (var5 != 9999) {
         boolean var8 = false;
         int var9 = 0;
         switch (var1) {
            case 0:
               if (var5 >= -5 && var5 <= 21) {
                  var9 = var5;
                  break;
               }

               var8 = true;
               break;
            case 2:
               if (var2 >= 0) {
                  var9 = var5 + var2;
               } else {
                  var9 = var5;
               }
               break;
            case 3:
               var9 = var2;
            case 1:
               var8 = true;
               break;
            case 4:
               var9 = var2;
               if (var5 < -5 || var5 > var2) {
                  var8 = true;
               }
         }

         if (var7 < var9) {
            int var10 = var9;
            var7 = var9;

            do {
               var0.append('0');
            } while(var0.length() != var10);
         }

         if (var8) {
            if (var7 != 1) {
               var0.insert(1, '.');
            }

            var0.append('e');
            if (var5 - 1 >= 0) {
               var0.append('+');
            }

            var0.append(var5 - 1);
         } else if (var5 != var7) {
            if (var5 > 0) {
               var0.insert(var5, '.');
            } else {
               for(int var12 = 0; var12 < 1 - var5; ++var12) {
                  var0.insert(0, '0');
               }

               var0.insert(1, '.');
            }
         }
      }

      if (var6[0] && (word0(var3) != Integer.MIN_VALUE || word1(var3) != 0) && ((word0(var3) & 2146435072) != 2146435072 || word1(var3) == 0 && (word0(var3) & 1048575) == 0)) {
         var0.insert(0, '-');
      }

   }

   static BigInteger d2b(double var0, int[] var2, int[] var3) {
      long var10 = Double.doubleToLongBits(var0);
      int var12 = (int)(var10 >>> 32);
      int var13 = (int)var10;
      int var8 = var12 & 1048575;
      var12 &= Integer.MAX_VALUE;
      int var9;
      if ((var9 = var12 >>> 20) != 0) {
         var8 |= 1048576;
      }

      byte[] var4;
      int var5;
      int var6;
      if (var13 != 0) {
         var4 = new byte[8];
         var6 = lo0bits(var13);
         int var7 = var13 >>> var6;
         if (var6 != 0) {
            stuffBits(var4, 4, var7 | var8 << 32 - var6);
            var8 >>= var6;
         } else {
            stuffBits(var4, 4, var7);
         }

         stuffBits(var4, 0, var8);
         var5 = var8 != 0 ? 2 : 1;
      } else {
         var4 = new byte[4];
         var6 = lo0bits(var8);
         var8 >>>= var6;
         stuffBits(var4, 0, var8);
         var6 += 32;
         var5 = 1;
      }

      if (var9 != 0) {
         var2[0] = var9 - 1023 - 52 + var6;
         var3[0] = 53 - var6;
      } else {
         var2[0] = var9 - 1023 - 52 + 1 + var6;
         var3[0] = 32 * var5 - hi0bits(var8);
      }

      return new BigInteger(var4);
   }

   static int hi0bits(int var0) {
      int var1 = 0;
      if ((var0 & -65536) == 0) {
         var1 = 16;
         var0 <<= 16;
      }

      if ((var0 & -16777216) == 0) {
         var1 += 8;
         var0 <<= 8;
      }

      if ((var0 & -268435456) == 0) {
         var1 += 4;
         var0 <<= 4;
      }

      if ((var0 & -1073741824) == 0) {
         var1 += 2;
         var0 <<= 2;
      }

      if ((var0 & Integer.MIN_VALUE) == 0) {
         ++var1;
         if ((var0 & 1073741824) == 0) {
            return 32;
         }
      }

      return var1;
   }

   static int lo0bits(int var0) {
      int var2 = var0;
      if ((var0 & 7) != 0) {
         if ((var0 & 1) != 0) {
            return 0;
         } else {
            return (var0 & 2) != 0 ? 1 : 2;
         }
      } else {
         int var1 = 0;
         if ((var0 & '\uffff') == 0) {
            var1 = 16;
            var2 = var0 >>> 16;
         }

         if ((var2 & 255) == 0) {
            var1 += 8;
            var2 >>>= 8;
         }

         if ((var2 & 15) == 0) {
            var1 += 4;
            var2 >>>= 4;
         }

         if ((var2 & 3) == 0) {
            var1 += 2;
            var2 >>>= 2;
         }

         if ((var2 & 1) == 0) {
            ++var1;
            var2 >>>= 1;
            if ((var2 & 1) == 0) {
               return 32;
            }
         }

         return var1;
      }
   }

   static BigInteger pow5mult(BigInteger var0, int var1) {
      return var0.multiply(BigInteger.valueOf(5L).pow(var1));
   }

   static boolean roundOff(StringBuffer var0) {
      do {
         char var1;
         if ((var1 = var0.charAt(var0.length() - 1)) != '9') {
            var0.append((char)(var1 + 1));
            return false;
         }

         var0.setLength(var0.length() - 1);
      } while(var0.length() != 0);

      return true;
   }

   static double setWord0(double var0, int var2) {
      long var3 = Double.doubleToLongBits(var0);
      var3 = (long)var2 << 32 | var3 & 4294967295L;
      return Double.longBitsToDouble(var3);
   }

   static void stuffBits(byte[] var0, int var1, int var2) {
      var0[var1] = (byte)(var2 >> 24);
      var0[var1 + 1] = (byte)(var2 >> 16);
      var0[var1 + 2] = (byte)(var2 >> 8);
      var0[var1 + 3] = (byte)var2;
   }

   static int word0(double var0) {
      long var2 = Double.doubleToLongBits(var0);
      return (int)(var2 >> 32);
   }

   static int word1(double var0) {
      long var2 = Double.doubleToLongBits(var0);
      return (int)var2;
   }
}
