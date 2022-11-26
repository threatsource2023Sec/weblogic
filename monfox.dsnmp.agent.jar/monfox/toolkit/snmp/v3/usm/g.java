package monfox.toolkit.snmp.v3.usm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import monfox.log.Logger;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

abstract class g extends USMPrivModule {
   static final String a = "H\u0006+\u007f\u0001O\u0001W\u001e-Y\"\u001c4+g$";
   static final String b = "H\u0006+";
   private int c = -1;
   private Logger d = Logger.getInstance(b("M\u00106\u001d\u0012"), b("_pU\u0005\u0011D"), b("H\u0006+\u000f\u0017Z\u000e(\"+\u007f\u000e\u001747e&"));

   public g(int var1) {
      this.d.detailed(b("H\u0006+\u000f\u0017Z\u000e(\"+\u007f\u000e\u001747e&"));
      this.c = var1;
   }

   private Cipher c() {
      return this.a(b("H\u0006+\u007f\u0001O\u0001W\u001e-Y\"\u001c4+g$"));
   }

   public void encryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("`-\u000e1.`'X;'pc\u00145,n7\u0010jb") + var1.length + b(")\u007fX") + this.c, false);
         } else {
            byte[] var6 = new byte[16];
            byte[] var7 = new byte[8];
            long var8 = this.b();
            long var10 = var2.getEngineBoots();
            long var12 = var2.getEngineTime();
            var6[0] = (byte)((int)(var10 >> 24 & 255L));

            int var14;
            for(var14 = 0; var14 < 4; ++var14) {
               var6[var14] = (byte)((int)(var10 >> (3 - var14) * 8 & 255L));
               var6[var14 + 4] = (byte)((int)(var12 >> (3 - var14) * 8 & 255L));
            }

            for(var14 = 0; var14 < 8; ++var14) {
               var7[var14] = (byte)((int)(var8 >> (7 - var14) * 8 & 255L));
               var6[var14 + 8] = var7[var14];
            }

            Cipher var22 = this.c();
            if (var22 == null) {
               throw new SnmpSecurityCoderException(1, b("y1\u0011&#j:X1.n,\n96a.Xw\u0003L\u0010W\u0013\u0004Kl6?\u0012h'\u001c9,ndX>-}c\u000b%2y,\n$bk:X\u001a\u0014Dc\u000b5!|1\u0011$;)3\n?4`'\u001d\""), false);
            } else {
               SecretKeySpec var15 = new SecretKeySpec(var1, 0, this.c, b("H\u0006+"));
               IvParameterSpec var16 = new IvParameterSpec(var6);
               var22.init(1, var15, var16);
               int var17 = var3.offset;
               int var18 = var3.length;
               byte[] var19 = var3.data;
               byte[] var20 = var22.doFinal(var3.data, var3.offset, var3.length);
               var5.data = var7;
               var5.offset = 0;
               var5.length = var7.length;
               var4.data = var20;
               var4.offset = 0;
               var4.length = var20.length;
            }
         }
      } catch (Exception var21) {
         this.d.error(b("l1\n?0)*\u0016p'g \n)2}*\u00167bY\u0007-"), var21);
         throw new SnmpSecurityCoderException(8, b("l-\u001b\";y7\u0011?,)&\u00003'y7\u0011?,3c") + var21, false);
      }
   }

   public void decryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("`-\u000e1.`'X;'pc\u00145,n7\u0010jb") + var1.length + b(")\u007fX") + this.c, false);
         } else {
            byte[] var6 = new byte[16];
            byte[] var7 = var4.data;
            long var8 = var2.getEngineBoots();
            long var10 = var2.getEngineTime();
            var6[0] = (byte)((int)(var8 >> 24 & 255L));

            int var12;
            for(var12 = 0; var12 < 4; ++var12) {
               var6[var12] = (byte)((int)(var8 >> (3 - var12) * 8 & 255L));
               var6[var12 + 4] = (byte)((int)(var10 >> (3 - var12) * 8 & 255L));
            }

            for(var12 = 0; var12 < 8; ++var12) {
               var6[var12 + 8] = var7[var12];
            }

            Cipher var17 = this.c();
            if (var17 == null) {
               throw new SnmpSecurityCoderException(1, b("y1\u0011&#j:X1.n,\n96a.Xw\u0003L\u0010W\u0013\u0004Kl6?\u0012h'\u001c9,ndX>-}c\u000b%2y,\n$bk:X\u001a\u0014Dc\u000b5!|1\u0011$;)3\n?4`'\u001d\""), false);
            } else {
               SecretKeySpec var13 = new SecretKeySpec(var1, 0, this.c, b("H\u0006+"));
               IvParameterSpec var14 = new IvParameterSpec(var6);
               var17.init(2, var13, var14);
               byte[] var15 = var17.doFinal(var3.data, var3.offset, var3.length);
               var5.data = var15;
               var5.offset = 0;
               var5.length = var15.length;
            }
         }
      } catch (Exception var16) {
         this.d.error(b("l1\n?0)*\u0016p'g \n)2}*\u00167bY\u0007-"), var16);
         throw new SnmpSecurityCoderException(7, b("l-\u001b\";y7\u0011?,)&\u00003'y7\u0011?,3c") + var16, false);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 9;
               break;
            case 1:
               var10003 = 67;
               break;
            case 2:
               var10003 = 120;
               break;
            case 3:
               var10003 = 80;
               break;
            default:
               var10003 = 66;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
