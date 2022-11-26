package monfox.toolkit.snmp.v3.usm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;

public abstract class USMPrivProtocolSpec {
   private static USMPrivProtocolSpec[] a = new USMPrivProtocolSpec[0];
   private static Logger b = Logger.getInstance(a("U$!e)"), a("GD@}*\\"), a("D$\"x\u000bx\u0001?Z\u0016e\u0018\fG\u0015B\u0007\nK"));

   public abstract int getKeyLength();

   public abstract int getPrivProtocol();

   public abstract SnmpOid getPrivProtocolOID();

   public abstract USMPrivModule newModule();

   public byte[] extendLocalizedKey(byte[] var1, byte[] var2, int var3) throws NoSuchAlgorithmException {
      boolean var10 = USMLocalizedUserData.k;
      if (this.getKeyLength() > var1.length) {
         MessageDigest var4 = MessageDigest.getInstance(var3 == 0 ? a("\\3Z") : a("B?."));
         byte[] var5 = new byte[this.getKeyLength()];
         System.arraycopy(var1, 0, var5, 0, var1.length);
         int var6 = var1.length;

         while(true) {
            label39:
            while(var6 < var5.length) {
               var4.reset();
               var4.update(var5, 0, var6);
               byte[] var7 = var4.digest();
               int var8 = var5.length - var6;
               if (var10) {
                  return var1;
               }

               if (var8 > var1.length) {
                  var8 = var1.length;
               }

               int var9 = 0;

               while(var9 < var8) {
                  var5[var9 + var6] = var7[var9];
                  ++var9;
                  if (var10 && var10) {
                     continue label39;
                  }
               }

               var6 += var8;
               if (var10) {
               }
            }

            var1 = var5;
            break;
         }
      }

      return var1;
   }

   public static synchronized void addSpec(USMPrivProtocolSpec var0) {
      boolean var3 = USMLocalizedUserData.k;
      USMPrivProtocolSpec[] var1 = new USMPrivProtocolSpec[a.length + 1];
      int var2 = 0;

      while(true) {
         if (var2 < a.length) {
            var1[var2] = a[var2];
            ++var2;
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1[var1.length - 1] = var0;
         a = var1;
         break;
      }

   }

   public static synchronized USMPrivProtocolSpec getSpec(int var0) {
      int var1 = 0;

      while(var1 < a.length) {
         if (a[var1].getPrivProtocol() == var0) {
            return a[var1];
         }

         ++var1;
         if (USMLocalizedUserData.k) {
            break;
         }
      }

      return null;
   }

   public static USMPrivProtocolSpec[] getSpecs() {
      return a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 17;
               break;
            case 1:
               var10003 = 119;
               break;
            case 2:
               var10003 = 111;
               break;
            case 3:
               var10003 = 40;
               break;
            default:
               var10003 = 121;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
