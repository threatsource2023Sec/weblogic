package monfox.toolkit.snmp.v3.usm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

class k extends USMPrivModule {
   static final String a = "M.Q\u001b\u001cK(-z0Y\nfP6g\f";
   static final String b = "M.Q";
   private int c = 8;
   private static final byte[] d = new byte[]{1, 2, 3, 4, 5, 6, 6, 7};
   private Logger e = Logger.getInstance(b("M8Ly\u000f"), b("_X/a\fD"), b("M.Qk\nZ&RF6\u007f&mP*e\u000e"));

   public k() {
      this.e.debug(b("M.Qk\nZ&RF6\u007f&mP*e\u000e"));
   }

   private Cipher c() {
      return this.a(b("M.Q\u001b\u001cK(-z0Y\nfP6g\f"));
   }

   public void encryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      boolean var17 = USMLocalizedUserData.k;

      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("`\u0005tU3`\u000f\"_:pKnQ1n\u001fj\u000e\u007f") + var1.length + b(")W\"") + this.c, false);
         }

         byte[] var6 = this.a((int)var2.getEngineBoots());
         byte[] var7 = new byte[8];

         for(int var8 = 0; var8 < 8; ++var8) {
            var7[var8] = (byte)(var1[8 + var8] ^ var6[var8]);
         }

         byte[] var19 = new byte[8];
         System.arraycopy(var1, 0, var19, 0, 8);
         Cipher var9 = this.c();
         if (var9 == null) {
            throw new SnmpSecurityCoderException(1, b("y\u0019kB>j\u0012\"U3n\u0004p]+a\u0006\"\u0013\u001bL8-w\u001dJDL[\u000fh\u000ff]1nL\"Z0}KqA/y\u0004p@\u007fk\u0012\"~\tDKqQ<|\u0019k@&)\u001bp[)`\u000fgF"), false);
         }

         SecretKeySpec var10 = new SecretKeySpec(var19, 0, this.c, b("M.Q"));
         IvParameterSpec var11 = new IvParameterSpec(var7);
         var9.init(1, var10, var11);
         boolean var12 = false;
         boolean var13 = false;
         Object var14 = null;
         int var15 = 8 - var3.length % 8;
         byte[] var16;
         int var20;
         int var21;
         byte[] var22;
         if (var15 == 8) {
            boolean var23 = false;
            var22 = var3.data;
            var20 = var3.offset;
            var21 = var3.length;
         } else {
            var16 = new byte[var3.length + var15];
            System.arraycopy(var3.data, var3.offset, var16, 0, var3.length);
            System.arraycopy(d, 0, var16, var3.length, var15);
            var22 = var16;
            var20 = 0;
            var21 = var16.length;
         }

         var16 = var9.doFinal(var22, var20, var21);
         var5.data = var6;
         var5.offset = 0;
         var5.length = var6.length;
         var4.data = var16;
         var4.offset = 0;
         var4.length = var16.length;
      } catch (Exception var18) {
         this.e.error(b("l\u0019p[-)\u0002l\u0014:g\bpM/}\u0002lS\u007fY/W"), var18);
         throw new SnmpSecurityCoderException(8, b("l\u0005aF&y\u001fk[1)\u000ezW:y\u001fk[13K") + var18, false);
      }

      if (SnmpException.b) {
         USMLocalizedUserData.k = !var17;
      }

   }

   public void decryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("`\u0005tU3`\u000f\"_:pKnQ1n\u001fj\u000e\u007f") + var1.length + b(")W\"") + this.c, false);
         } else {
            byte[] var6 = var4.data;
            byte[] var7 = new byte[8];

            for(int var8 = 0; var8 < 8; ++var8) {
               var7[var8] = (byte)(var1[8 + var8] ^ var6[var8]);
            }

            byte[] var18 = new byte[8];
            System.arraycopy(var1, 0, var18, 0, 8);
            Cipher var9 = this.c();
            if (var9 == null) {
               throw new SnmpSecurityCoderException(1, b("y\u0019kB>j\u0012\"U3n\u0004p]+a\u0006\"\u0013\u001bL8-w\u001dJDL[\u000fh\u000ff]1nL\"Z0}KqA/y\u0004p@\u007fk\u0012\"~\tDKqQ<|\u0019k@&)\u001bp[)`\u000fgF"), false);
            } else {
               SecretKeySpec var10 = new SecretKeySpec(var18, 0, this.c, b("M.Q"));
               IvParameterSpec var11 = new IvParameterSpec(var7);
               var9.init(2, var10, var11);
               boolean var12 = false;
               boolean var13 = false;
               Object var14 = null;
               int var15 = 8 - var3.length % 8;
               byte[] var16;
               int var19;
               int var20;
               byte[] var21;
               if (var15 == 8) {
                  boolean var22 = false;
                  var21 = var3.data;
                  var19 = var3.offset;
                  var20 = var3.length;
               } else {
                  var16 = new byte[var3.length + var15];
                  System.arraycopy(var3.data, var3.offset, var16, 0, var3.length);
                  System.arraycopy(d, 0, var16, var3.length, var15);
                  var21 = var16;
                  var19 = 0;
                  var20 = var16.length;
               }

               var16 = var9.doFinal(var21, var19, var20);
               var5.data = var16;
               var5.offset = 0;
               var5.length = var16.length;
            }
         }
      } catch (Exception var17) {
         this.e.error(b("l\u0019p[-)\u0002l\u0014;l\bpM/}\u0002lS\u007fY/W"), var17);
         throw new SnmpSecurityCoderException(7, b("m\u000eaF&y\u001fk[1)\u000ezW:y\u001fk[13K") + var17, false);
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
               var10003 = 107;
               break;
            case 2:
               var10003 = 2;
               break;
            case 3:
               var10003 = 52;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
