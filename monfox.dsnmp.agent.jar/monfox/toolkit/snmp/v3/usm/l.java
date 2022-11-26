package monfox.toolkit.snmp.v3.usm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import monfox.log.Logger;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

class l extends USMPrivModule {
   static final String a = ")\u0003g4\u0001\biw\u0013&B\b[\u0001\u0004\t\"]?\u0002";
   static final String b = ")\u0003g4\u0001\b";
   private int c = 32;
   private static final byte[] d = new byte[]{1, 2, 3, 4, 5, 6, 6, 7};
   private Logger e = Logger.getInstance(b(")\u0015z\u001c5"), b(";u\u0019\u00046 "), b(")\u0003g\u000e0>\u000bd#\f\u001b\u000b[5\u0010\u0001#"));

   public l() {
      this.e.debug(b("9\u0002q\u0002:8\u0015y\u0001\u0017\u00040y>\u0001\u0018*Q"));
   }

   private Cipher c() {
      return this.a(b(")\u0003g4\u0001\biw\u0013&B\b[\u0001\u0004\t\"]?\u0002"));
   }

   public void encryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("\u0004(B0\t\u0004\"\u0014:\u0000\u0014fX4\u000b\n2\\kE") + var1.length + b("Mz\u0014") + this.c, false);
         } else {
            byte[] var6 = this.a((int)var2.getEngineBoots());
            byte[] var7 = new byte[8];

            for(int var8 = 0; var8 < 8; ++var8) {
               var7[var8] = (byte)(var1[24 + var8] ^ var6[var8]);
            }

            byte[] var18 = new byte[24];
            System.arraycopy(var1, 0, var18, 0, 24);
            Cipher var9 = this.c();
            if (var9 == null) {
               throw new SnmpSecurityCoderException(1, b("\u001d4]'\u0004\u000e?\u00140\t\n)F8\u0011\u0005+\u0014v!(\u0015Q5\u0000B\u0005v\u0012J#)d0\u0001\t/Z6BM([%E\u001e3D!\n\u001f2\u00143\u001cM\fb\u001cE\u001e#W$\u0017\u00042Mq\u0015\u001f)B8\u0001\b4"), false);
            } else {
               SecretKeySpec var10 = new SecretKeySpec(var18, 0, 24, b(")\u0003g4\u0001\b"));
               IvParameterSpec var11 = new IvParameterSpec(var7);
               var9.init(1, var10, var11);
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
               var5.data = var6;
               var5.offset = 0;
               var5.length = var6.length;
               var4.data = var16;
               var4.offset = 0;
               var4.length = var16.length;
            }
         }
      } catch (Exception var17) {
         this.e.error(b("\b4F>\u0017M/Zq\u0000\u0003%F(\u0015\u0019/Z6E=\u0002a"), var17);
         throw new SnmpSecurityCoderException(8, b("\b(W#\u001c\u001d2]>\u000bM#L2\u0000\u001d2]>\u000bWf") + var17, false);
      }
   }

   public void decryptData(byte[] var1, USMPrivModule.Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException {
      try {
         if (var1.length < this.c) {
            throw new SnmpSecurityCoderException(1, b("\u0004(B0\t\u0004\"\u0014:\u0000\u0014fX4\u000b\n2\\kE") + var1.length + b("Mz\u0014") + this.c, false);
         } else {
            byte[] var6 = var4.data;
            byte[] var7 = new byte[8];

            for(int var8 = 0; var8 < 8; ++var8) {
               var7[var8] = (byte)(var1[24 + var8] ^ var6[var8]);
            }

            Cipher var17 = this.c();
            if (var17 == null) {
               throw new SnmpSecurityCoderException(1, b("\u001d4]'\u0004\u000e?\u00140\t\n)F8\u0011\u0005+\u0014v!(\u0015Q5\u0000B\u0005v\u0012J#)d0\u0001\t/Z6BM([%E\u001e3D!\n\u001f2\u00143\u001cM\fb\u001cE\u001e#W$\u0017\u00042Mq\u0015\u001f)B8\u0001\b4"), false);
            } else {
               SecretKeySpec var9 = new SecretKeySpec(var1, 0, 24, b(")\u0003g4\u0001\b"));
               IvParameterSpec var10 = new IvParameterSpec(var7);
               var17.init(2, var9, var10);
               boolean var11 = false;
               boolean var12 = false;
               Object var13 = null;
               int var14 = 8 - var3.length % 8;
               byte[] var15;
               int var18;
               int var19;
               byte[] var20;
               if (var14 == 8) {
                  boolean var21 = false;
                  var20 = var3.data;
                  var18 = var3.offset;
                  var19 = var3.length;
               } else {
                  var15 = new byte[var3.length + var14];
                  System.arraycopy(var3.data, var3.offset, var15, 0, var3.length);
                  System.arraycopy(d, 0, var15, var3.length, var14);
                  var20 = var15;
                  var18 = 0;
                  var19 = var15.length;
               }

               var15 = var17.doFinal(var20, var18, var19);
               var5.data = var15;
               var5.offset = 0;
               var5.length = var15.length;
            }
         }
      } catch (Exception var16) {
         this.e.error(b("\b4F>\u0017M/Zq\u0001\b%F(\u0015\u0019/Z6E=\u0002a"), var16);
         throw new SnmpSecurityCoderException(7, b("\t#W#\u001c\u001d2]>\u000bM#L2\u0000\u001d2]>\u000bWf") + var16, false);
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
               var10003 = 109;
               break;
            case 1:
               var10003 = 70;
               break;
            case 2:
               var10003 = 52;
               break;
            case 3:
               var10003 = 81;
               break;
            default:
               var10003 = 101;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
