package monfox.toolkit.snmp.ber;

import monfox.toolkit.snmp.SnmpException;

final class a {
   private static final int a = 4;
   private static final int[] b = new int[]{1, 256, 65536, 16777216};
   private static final String c = "$Id: BERDecoder.java,v 1.10 2008/12/04 17:21:22 sking Exp $";

   /** @deprecated */
   static boolean a(BERBuffer var0, byte var1) {
      if (var0.getByteAt(var0.getIndex()) == var1) {
         var0.setIndex(var0.getIndex() + 1);
         return true;
      } else {
         return false;
      }
   }

   static int a(byte[] var0, int var1, int var2) throws BERException {
      return (int)b(var0, var1, var2);
   }

   static long b(byte[] var0, int var1, int var2) throws BERException {
      int var9 = BERBuffer.j;
      long var3 = 0L;

      try {
         long var5 = 0L;
         int var7 = var1 + var2 - 1;
         int var8 = var1;

         while(var8 <= var7) {
            var5 = (long)var0[var8] & 255L;
            var3 |= var5 << 8 * (var7 - var8);
            ++var8;
            if (var9 != 0 || var9 != 0) {
               break;
            }
         }

         return var3;
      } catch (Exception var10) {
         throw new BERException();
      }
   }

   static long a(BERBuffer var0, int var1) throws BERException {
      int var8 = BERBuffer.j;
      if (var1 == 0) {
         return 0L;
      } else {
         long var2 = 0L;

         try {
            label41: {
               long var10000;
               if ((var0.atIndex() & 128) == 128) {
                  int var4 = 7;

                  while(var4 >= var1) {
                     var10000 = var2 | 255L << var4 * 8;
                     if (var8 != 0) {
                        break label41;
                     }

                     var2 = var10000;
                     --var4;
                     if (var8 != 0) {
                        break;
                     }
                  }
               }

               var10000 = 0L;
            }

            int var6 = var1 - 1;
            int var7 = 0;

            while(var7 <= var6) {
               long var10 = (long)var0.nextByte() & 255L;
               var2 |= var10 << 8 * (var6 - var7);
               ++var7;
               if (var8 != 0 || var8 != 0) {
                  break;
               }
            }

            return var2;
         } catch (Exception var9) {
            throw new BERException();
         }
      }
   }

   static long b(BERBuffer var0, int var1) throws BERException {
      int var8 = BERBuffer.j;
      if (var1 == 0) {
         return 0L;
      } else {
         long var2 = 0L;

         try {
            long var4 = 0L;
            int var6 = var1 - 1;
            int var7 = 0;

            while(var7 <= var6) {
               var4 = (long)var0.nextByte() & 255L;
               var2 |= var4 << 8 * (var6 - var7);
               ++var7;
               if (var8 != 0 || var8 != 0) {
                  break;
               }
            }

            return var2;
         } catch (Exception var9) {
            throw new BERException();
         }
      }
   }

   public static int getLength(BERBuffer var0) throws BERException {
      int var4 = BERBuffer.j;

      try {
         int var1 = var0.nextByte();
         int var10000;
         if (((byte)var1 & -128) == -128) {
            int var2 = var1 & 127;
            var1 = 0;
            int var3 = 0;

            while(var3 < var2) {
               var10000 = var1 + (var0.nextByte() & 255) * b[var2 - var3 - 1];
               if (var4 != 0) {
                  return var10000;
               }

               var1 = var10000;
               ++var3;
               if (var4 != 0) {
                  break;
               }
            }
         }

         var10000 = var1;
         return var10000;
      } catch (Exception var5) {
         throw new BERException(a("F^SH`}USS2mIDB2nBBFk/\\UIu{X\n") + var5.getMessage());
      }
   }

   public static BERBuffer getEntireEncoding(BERBuffer var0) throws BERException {
      int var7 = BERBuffer.j;
      int var1 = var0.getIndex();
      int var2 = var0.getIndex();
      if (var2 >= var0.getLength()) {
         throw new BERException(a("J^T\u0007}i\u0010rRtiUB"));
      } else {
         int var10;
         label63: {
            try {
               boolean var3 = false;
               if (((var10 = var0.getByteAt(var2)) & 31) == 31) {
                  ++var2;

                  while((var0.getByteAt(var2) & 128) == 128) {
                     ++var2;
                     if (var7 != 0) {
                        SnmpException.b = !SnmpException.b;
                        if (var7 != 0) {
                           break label63;
                        }
                     }
                  }
               } else {
                  ++var2;
               }
            } catch (Exception var9) {
               throw new BERException();
            }

            var10 = 0;
         }

         int var4;
         label50: {
            try {
               label48: {
                  if ((var0.getByteAt(var2) & -128) != -128) {
                     var10 = var0.getByteAt(var2);
                     ++var2;
                     if (var7 == 0) {
                        break label48;
                     }
                  }

                  if ((var0.getByteAt(var2) & -128) == -128) {
                     var4 = var0.getByteAt(var2) & 127;
                     ++var2;
                     int var5 = 0;

                     while(var5 < var4) {
                        int var6 = var0.getByteAt(var2) & 255;
                        var10 |= var6 << 8 * (var4 - var5 - 1);
                        ++var2;
                        ++var5;
                        if (var7 != 0) {
                           break label50;
                        }

                        if (var7 != 0) {
                           break;
                        }
                     }
                  }
               }
            } catch (Exception var8) {
               throw new BERException(a("F^SH`}USS2mIDB2nBBFk/\\UI<"));
            }

            var4 = var2 - var1 + var10;
         }

         BERBuffer var11 = new BERBuffer(var0, var1 + var4);
         var0.setIndex(var1 + var4);
         return var11;
      }
   }

   public static int lookEntireTagValue(BERBuffer var0) throws BERException {
      int var6 = BERBuffer.j;
      int var1 = var0.getIndex();
      if (var1 >= var0.getLength()) {
         return Integer.MAX_VALUE;
      } else {
         boolean var2 = false;

         try {
            int var8;
            if (((var8 = var0.getByteAt(var1)) & 31) == 31) {
               int var4 = var1 + 1;
               int var5 = 0;

               while((var0.getByteAt(var4 + var5) & 128) == 128) {
                  ++var5;
                  if (var6 != 0) {
                     return var8;
                  }

                  if (var6 != 0) {
                     break;
                  }
               }

               var8 = a(var0.getData(), var1, var5 + 1);
               if (var6 != 0) {
               }
            } else {
               var8 &= 255;
            }

            return var8;
         } catch (Exception var7) {
            throw new BERException();
         }
      }
   }

   public static int getEntireTagValue(BERBuffer var0) throws BERException {
      try {
         return var0.nextByte() & 255;
      } catch (Exception var2) {
         throw new BERException(var2.getMessage());
      }
   }

   /** @deprecated */
   public static int lookTagValue(BERBuffer var0) {
      int var8 = BERBuffer.j;
      int var1 = var0.getIndex();
      boolean var2 = false;
      int var9;
      if (((var9 = var0.getByteAt(var1)) & 31) == 31) {
         int var3 = var1 + 1;
         boolean var4 = false;
         byte[] var5 = new byte[4];
         var9 = 0;
         int var6 = 0;

         int var7;
         while((var0.getByteAt(var3 + var6) & 128) == 128) {
            var7 = (var0.getByteAt(var0.getIndex() + var6) & 127) << 7 * var6;
            var9 |= var7;
            ++var6;
            if (var8 != 0) {
               return var9;
            }

            if (var8 != 0) {
               break;
            }
         }

         var7 = (var0.getByteAt(var0.getIndex() + var6) & 127) << 7 * var6;
         var9 |= var7;
         if (var8 != 0) {
         }
      } else {
         var9 &= 255;
      }

      return var9;
   }

   /** @deprecated */
   public static int getTagValue(BERBuffer var0) {
      int var8 = BERBuffer.j;
      int var1 = var0.getIndex();
      boolean var2 = false;
      int var9;
      if (((var9 = var0.getByteAt(var1)) & 31) == 31) {
         int var3 = var1 + 1;
         boolean var4 = false;
         byte[] var5 = new byte[4];
         var9 = 0;
         int var6 = 0;

         int var7;
         while((var0.getByteAt(var3 + var6) & 128) == 128) {
            var7 = (var0.getByteAt(var0.getIndex() + var6) & 127) << 7 * var6;
            var9 |= var7;
            ++var6;
            if (var8 != 0) {
               return var9;
            }

            if (var8 != 0) {
               break;
            }
         }

         var7 = (var0.getByteAt(var0.getIndex() + var6) & 127) << 7 * var6;
         var9 |= var7;
         var0.setIndex(var1 + var6 + 1);
         if (var8 != 0) {
         }
      } else {
         var9 &= 255;
         var0.setIndex(var1 + 1);
      }

      return var9;
   }

   public static int byteArrayToInt_2sCompliment(byte[] var0, int var1, int var2) throws BERException {
      int var4 = BERBuffer.j;
      int var3 = (int)b(var0, var1, var2);
      if ((var0[var1] & -128) == -128) {
         switch (var2) {
            case 1:
               var3 |= -256;
               if (var4 == 0) {
                  break;
               }
            case 2:
               var3 |= -65536;
               if (var4 == 0) {
                  break;
               }
            case 3:
               var3 |= -16777216;
         }
      }

      return var3;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 15;
               break;
            case 1:
               var10003 = 48;
               break;
            case 2:
               var10003 = 48;
               break;
            case 3:
               var10003 = 39;
               break;
            default:
               var10003 = 18;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
