package monfox.toolkit.snmp.ber;

import monfox.toolkit.snmp.SnmpOid;

final class e {
   private static final String a = "$Id: OID_BERCoder.java,v 1.9 2006/04/05 14:00:43 sking Exp $";

   long[] a(BERBuffer var1) throws BERException {
      int var12 = BERBuffer.j;
      int var2 = monfox.toolkit.snmp.ber.a.getLength(var1);
      if (var2 == 0) {
         return new long[0];
      } else if (var2 > var1.getRemainingSize()) {
         throw new BERException(a("n2\u0001$5U9\u0001?gh\u0015&k%^(\u0007k&U.\u00032gK9\f,3O|Xk") + var2 + ">" + var1.getRemainingSize());
      } else {
         int var3 = 0;
         long[] var4 = new long[var2 + 1];
         byte[] var5 = null;
         int var6 = 0;
         int var7 = var1.getIndex();
         long var8 = 0L;
         boolean var10 = false;
         int var11 = 0;

         while(var11 < var2) {
            label66: {
               byte var13 = var1.getByteAt(var7 + var11);
               if ((var13 & -128) == 0) {
                  label55: {
                     if (var6 == 0) {
                        var8 = (long)(var13 & 127);
                        if (var12 == 0) {
                           break label55;
                        }
                     }

                     var5[var6++] = var13;
                     var8 = this.a(var5, var6);
                     var6 = 0;
                  }

                  if (var3 == 0) {
                     if (var8 < 40L) {
                        var4[var3++] = 0L;
                        var4[var3++] = var8;
                        if (var12 == 0) {
                           break label66;
                        }
                     }

                     if (var8 < 80L) {
                        var4[var3++] = 1L;
                        var4[var3++] = var8 - 40L;
                        if (var12 == 0) {
                           break label66;
                        }
                     }

                     var4[var3++] = 2L;
                     var4[var3++] = var8 - 80L;
                     if (var12 == 0) {
                        break label66;
                     }
                  }

                  var4[var3++] = var8;
                  if (var12 == 0) {
                     break label66;
                  }
               }

               if (var5 == null) {
                  var5 = new byte[var2];
               }

               var5[var6++] = var13;
               if (var11 + 1 == var2) {
                  throw new BERException(a("i3B.)C5\f,gE%\u0016.gA3\u0010k\be\u0016'\b\u0013\u0007\u0015&\u000e\ts\u0015$\u0002\u0002u"));
               }
            }

            ++var11;
            if (var12 != 0) {
               break;
            }
         }

         long[] var14 = var4;
         if (var3 != var4.length) {
            var14 = new long[var3];
            System.arraycopy(var4, 0, var14, 0, var3);
         }

         var1.setRelativeIndex(var2);
         return var14;
      }
   }

   SnmpOid b(BERBuffer var1) throws BERException {
      int var9 = BERBuffer.j;
      int var2 = monfox.toolkit.snmp.ber.a.getLength(var1);
      if (var2 == 0) {
         return new SnmpOid();
      } else if (var2 > var1.getRemainingSize()) {
         throw new BERException(a("n2\u0001$5U9\u0001?gh\u0015&k%^(\u0007k&U.\u00032gK9\f,3Of") + var2 + ">" + var1.getRemainingSize());
      } else {
         int var3;
         long[] var4;
         long var5;
         byte var7;
         label42: {
            var3 = 0;
            var4 = new long[var2 + 1];
            var5 = 0L;
            var7 = var1.nextByte();
            if (var7 < 40) {
               var4[var3++] = 0L;
               var4[var3++] = (long)var7;
               if (var9 == 0) {
                  break label42;
               }
            }

            if (var7 < 80) {
               var4[var3++] = 1L;
               var4[var3++] = (long)(var7 - 40);
               if (var9 == 0) {
                  break label42;
               }
            }

            var4[var3++] = 2L;
            var4[var3++] = (long)(var7 - 80);
         }

         int var8 = 1;

         while(var8 < var2) {
            var7 = var1.nextByte();
            var5 <<= 7;
            var5 |= (long)(var7 & 127);
            if ((var7 & -128) == 0) {
               var4[var3++] = var5;
               var5 = 0L;
            }

            ++var8;
            if (var9 != 0) {
               break;
            }
         }

         return new SnmpOid(var3, var4, false);
      }
   }

   int a(BERBuffer var1, long[] var2) throws BERException {
      return this.a(var1, var2, var2.length);
   }

   int a(BERBuffer var1, long[] var2, int var3) throws BERException {
      int var10 = BERBuffer.j;
      if (var3 == 0) {
         var2 = new long[]{0L, 0L};
         var3 = 2;
      }

      if (var3 < 2) {
         throw new BERException(a("h\u001e(\u000e\u0004s|+\u000f\u0002i\b+\r\u000eb\u000eB<.S4B{gH.BzgB0\u0007&\"I("));
      } else {
         long var4 = var2[0];
         long var6 = var2[1];
         if (var4 != 0L && var4 != 1L && var4 != 2L) {
            throw new BERException(a("h\u001e(\u000e\u0004s|+\u000f\u0002i\b+\r\u000eb\u000eB\"4\u00075\f=&K5\u0006"));
         } else if (var6 >= 40L) {
            throw new BERException(a("h\u001e(\u000e\u0004s|+\u000f\u0002i\b+\r\u000eb\u000eB\"4\u00075\f=&K5\u0006"));
         } else {
            int var8 = 0;
            int var9 = var3 - 1;

            while(true) {
               if (var9 >= 2) {
                  var8 += this.a(var1, var2[var9]);
                  --var9;
                  if (var10 != 0) {
                     break;
                  }

                  if (var10 == 0) {
                     continue;
                  }
               }

               var8 += this.a(var1, var2[0] * 40L + var2[1]);
               var8 += b.encodeLength(var1, var8);
               break;
            }

            return var8;
         }
      }
   }

   private long a(byte[] var1, int var2) {
      int var6 = BERBuffer.j;
      long var3 = 0L;
      int var5 = 0;

      long var10000;
      while(true) {
         if (var5 < var2) {
            var3 <<= 7;
            var10000 = var3 | (long)(var1[var5] & 127);
            if (var6 != 0) {
               break;
            }

            var3 = var10000;
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

   private int a(BERBuffer var1, long var2) {
      if (var2 < 0L) {
         return b.significantBitEncoding(var1, var2, false);
      } else if (var2 < 128L) {
         var1.a((byte)((int)(var2 & 255L)));
         return 1;
      } else {
         return b.significantBitEncoding(var1, var2, false);
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 39;
               break;
            case 1:
               var10003 = 92;
               break;
            case 2:
               var10003 = 98;
               break;
            case 3:
               var10003 = 75;
               break;
            default:
               var10003 = 71;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
