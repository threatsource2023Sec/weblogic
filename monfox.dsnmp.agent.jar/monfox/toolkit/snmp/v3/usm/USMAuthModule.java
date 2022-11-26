package monfox.toolkit.snmp.v3.usm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

public abstract class USMAuthModule {
   static final int a = 12;
   static final byte[] b = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   private String c;
   private int d;
   private Logger e;

   protected USMAuthModule(int var1) {
      this.c = null;
      this.d = 0;
      this.e = Logger.getInstance(a(")\u0016\u001d$q"), a(";v~<r "), a("8\u0016\u001e(T\u0019-\u001e\u0006E\u0018)6"));
      this.d = var1;
   }

   protected USMAuthModule(String var1, int var2) {
      this(var2);
      this.c = var1;
   }

   private MessageDigest a() throws SnmpSecurityCoderException {
      if (this.c != null) {
         try {
            return MessageDigest.getInstance(this.c);
         } catch (NoSuchAlgorithmException var2) {
            throw new SnmpSecurityCoderException(6, a("\f0'\u0001\u0001\u001d7<\u001dN\u000e*?IO\u00021s\u001aT\u001d5<\u001bU\b!iI") + this.c, false);
         }
      } else {
         throw new SnmpSecurityCoderException(6, a("\u0003*s\bT\u0019-s\u0019S\u00021<\nN\u0001e \fU"), false);
      }
   }

   public int getAuthParamLength() {
      return this.d;
   }

   public void authenticateOutgoing(byte[] var1, Context var2, int var3, SnmpBuffer var4) throws SnmpSecurityCoderException {
      boolean var11 = USMLocalizedUserData.k;
      this.e.debug(a("\f0'\u0001D\u00031:\n@\u0019 \u001c\u001cU\n*:\u0007F"));
      byte[] var5 = var2.getK1();
      byte[] var6 = var2.getK2();
      if (var5 == null || var6 == null) {
         var5 = new byte[64];
         var6 = new byte[64];
         int var7 = 0;

         label32:
         while(true) {
            int var10000;
            byte var10001;
            if (var7 < var1.length) {
               var10000 = var7;
               var10001 = 64;
               if (!var11) {
                  if (var7 < 64) {
                     var5[var7] = (byte)(var1[var7] ^ 54);
                     var6[var7] = (byte)(var1[var7] ^ 92);
                     ++var7;
                     if (!var11) {
                        continue;
                     }
                  }

                  var10000 = var7;
                  var10001 = 64;
               }
            } else {
               var10000 = var7;
               var10001 = 64;
            }

            while(true) {
               if (var10000 >= var10001) {
                  break label32;
               }

               var5[var7] = 54;
               var6[var7] = 92;
               ++var7;
               if (var11) {
                  break label32;
               }

               var10000 = var7;
               var10001 = 64;
            }
         }
      }

      MessageDigest var12 = this.a();
      var12.update(var5);
      var12.update(var4.data, var4.offset, var4.length);
      byte[] var8 = var12.digest();
      var12.reset();
      var12.update(var6);
      var12.update(var8);
      byte[] var9 = var12.digest();
      int var10 = var4.length - var3 + var4.offset;
      System.arraycopy(var9, 0, var4.data, var10, 12);
   }

   public boolean authenticateIncoming(byte[] var1, Context var2, int var3, SnmpBuffer var4) throws SnmpSecurityCoderException {
      boolean var12 = USMLocalizedUserData.k;
      this.e.debug(a("\f0'\u0001D\u00031:\n@\u0019 \u001a\u0007B\u0002(:\u0007F"));
      byte[] var5 = var2.getK1();
      byte[] var6 = var2.getK2();
      byte[] var7 = var2.getAuthParams();
      if (var5 != null && var6 != null) {
         if (var7 == null) {
            var7 = new byte[12];
            System.arraycopy(var4.data, var4.offset, var7, 0, 12);
         }

         System.arraycopy(b, 0, var4.data, var4.offset + var3, 12);
      } else {
         label77: {
            var5 = new byte[64];
            var6 = new byte[64];
            int var8 = 0;

            label69:
            while(true) {
               int var10000;
               byte var10001;
               if (var8 < var1.length) {
                  var10000 = var8;
                  var10001 = 64;
                  if (!var12) {
                     if (var8 < 64) {
                        var5[var8] = (byte)(var1[var8] ^ 54);
                        var6[var8] = (byte)(var1[var8] ^ 92);
                        ++var8;
                        if (!var12) {
                           continue;
                        }
                     }

                     var10000 = var8;
                     var10001 = 64;
                  }
               } else {
                  var10000 = var8;
                  var10001 = 64;
               }

               while(true) {
                  if (var10000 >= var10001) {
                     break label69;
                  }

                  var5[var8] = 54;
                  var6[var8] = 92;
                  ++var8;
                  if (var12) {
                     break label77;
                  }

                  if (var12) {
                     break label69;
                  }

                  var10000 = var8;
                  var10001 = 64;
               }
            }

            if (var7 == null) {
               var7 = new byte[12];
               System.arraycopy(var4.data, var4.offset, var7, 0, 12);
            }

            System.arraycopy(b, 0, var4.data, var4.offset + var3, 12);
         }
      }

      MessageDigest var13 = this.a();
      var13.update(var5);
      var13.update(var4.data, var4.offset, var4.length);
      byte[] var9 = var13.digest();
      var13.reset();
      var13.update(var6);
      var13.update(var9);
      byte[] var10 = var13.digest();
      int var11 = 0;

      byte var14;
      while(true) {
         if (var11 < 12) {
            var14 = var7[var11];
            if (var12) {
               break;
            }

            if (var14 != var10[var11]) {
               return false;
            }

            ++var11;
            if (!var12) {
               continue;
            }
         }

         var14 = 1;
         break;
      }

      return (boolean)var14;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 109;
               break;
            case 1:
               var10003 = 69;
               break;
            case 2:
               var10003 = 83;
               break;
            case 3:
               var10003 = 105;
               break;
            default:
               var10003 = 33;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class Context {
      private byte[] a = null;
      private byte[] b = null;
      private byte[] c = null;

      public void setAuthParams(byte[] var1) {
         this.c = var1;
      }

      public byte[] getAuthParams() {
         return this.c;
      }

      public void setK1(byte[] var1) {
         this.a = var1;
      }

      public byte[] getK1() {
         return this.a;
      }

      public void setK2(byte[] var1) {
         this.b = var1;
      }

      public byte[] getK2() {
         return this.b;
      }
   }
}
