package monfox.toolkit.snmp.v3.usm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;

public class USMUtil {
   private static Logger a = null;

   public static byte[] generateKeyChangeFromPasswd(byte[] var0, String var1, String var2, int var3, boolean var4) throws NoSuchAlgorithmException {
      return generateKeyChangeFromPasswd(var0, var1.getBytes(), var2.getBytes(), var3, var4);
   }

   public static byte[] generateKeyChangeFromPasswd(byte[] var0, byte[] var1, byte[] var2, int var3, boolean var4) throws NoSuchAlgorithmException {
      byte[] var5 = generateKey(var1, var3);
      byte[] var6 = generateKey(var2, var3);
      return generateKeyChange(var0, var5, var6, var3, var4);
   }

   public static byte[] generateKeyChange(byte[] var0, byte[] var1, byte[] var2, int var3, boolean var4) throws NoSuchAlgorithmException {
      byte[] var5 = localizeKey(var1, var0, var3);
      byte[] var6 = localizeKey(var2, var0, var3);
      return generateKeyChangeFromLocalKey(var5, var6, var3, var4);
   }

   public static byte[] generateKeyChangeFromLocalKey(byte[] var0, byte[] var1, int var2, boolean var3) throws NoSuchAlgorithmException {
      boolean var11 = USMLocalizedUserData.k;
      MessageDigest var4 = a(var2);
      byte[] var6;
      if (var3) {
         byte[] var5 = new byte[16];
         System.arraycopy(var0, 0, var5, 0, var5.length);
         var6 = new byte[16];
         System.arraycopy(var1, 0, var6, 0, var6.length);
         var0 = var5;
         var1 = var6;
      }

      int var12 = var0.length;
      var6 = generateRandom(var12);
      var4.reset();
      var4.update(var0);
      var4.update(var6);
      byte[] var7 = var4.digest();
      byte[] var8 = new byte[var12];
      int var9 = 0;

      byte[] var10000;
      while(true) {
         if (var9 < var12) {
            var10000 = var8;
            if (var11) {
               break;
            }

            var8[var9] = (byte)(var7[var9] ^ var1[var9]);
            ++var9;
            if (!var11) {
               continue;
            }
         }

         var10000 = new byte[var12 * 2];
         break;
      }

      byte[] var13 = var10000;
      int var10 = 0;

      while(true) {
         if (var10 < var12) {
            var13[var10] = var6[var10];
            var10000 = var13;
            if (var11) {
               break;
            }

            var13[var10 + var12] = var8[var10];
            ++var10;
            if (!var11) {
               continue;
            }
         }

         var10000 = var13;
         break;
      }

      return var10000;
   }

   public static byte[] generateRandom(int var0) {
      boolean var3 = USMLocalizedUserData.k;
      byte[] var1 = new byte[var0];
      int var2 = 0;

      byte[] var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = var1;
            if (var3) {
               break;
            }

            var1[var2] = (byte)((byte)((int)(255.0 * Math.random())) & 255);
            ++var2;
            if (!var3) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      return var10000;
   }

   public static byte[] generateKey(byte[] var0, int var1) throws NoSuchAlgorithmException {
      boolean var8 = USMLocalizedUserData.k;
      int var2 = var0.length;
      int var3 = 1048576;
      int var4 = var3 / var2;
      int var5 = var3 % var2;
      MessageDigest var6 = a(var1);
      int var7 = 0;

      while(true) {
         if (var7 < var4) {
            var6.update(var0);
            ++var7;
            if (var8) {
               break;
            }

            if (!var8) {
               continue;
            }
         }

         var6.update(var0, 0, var5);
         break;
      }

      byte[] var9 = var6.digest();
      return var9;
   }

   public static byte[] localizeKey(byte[] var0, byte[] var1, int var2) throws NoSuchAlgorithmException {
      MessageDigest var3 = a(var2);
      var3.reset();
      var3.update(var0);
      var3.update(var1);
      var3.update(var0);
      byte[] var4 = var3.digest();
      return var4;
   }

   public static String authProtocolToString(int var0) {
      switch (var0) {
         case 0:
            return a("X\\\b");
         case 1:
            return a("FP|");
         default:
            return "" + var0;
      }
   }

   private static MessageDigest a(int var0) throws NoSuchAlgorithmException {
      String var1 = authProtocolToString(var0);
      String var2 = SnmpFramework.getSecurityProviderName();
      MessageDigest var3 = null;
      if (var2 != null) {
         try {
            var3 = MessageDigest.getInstance(var1, var2);
         } catch (Exception var5) {
            a.debug(a("x}NGRr}\u0010PZr}N@\t5hO[E||XF\u0013pjO[A/8") + var5);
         }
      }

      if (var3 == null) {
         var3 = MessageDigest.getInstance(var1);
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
               var10003 = 21;
               break;
            case 1:
               var10003 = 24;
               break;
            case 2:
               var10003 = 61;
               break;
            case 3:
               var10003 = 52;
               break;
            default:
               var10003 = 51;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
