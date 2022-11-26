package monfox.toolkit.snmp.engine;

import java.io.Serializable;

public final class SnmpBuffer implements Serializable {
   static final long serialVersionUID = 452520251835025418L;
   public int length;
   public int offset;
   public byte[] data;
   public long timestamp;
   private static char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final String b = "$Id: SnmpBuffer.java,v 1.2 2004/12/20 22:25:36 sking Exp $";

   public SnmpBuffer() {
      this.length = 0;
      this.offset = 0;
      this.data = null;
      this.timestamp = 0L;
   }

   public SnmpBuffer(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public SnmpBuffer(byte[] var1, int var2, int var3) {
      this.length = 0;
      this.offset = 0;
      this.data = null;
      this.timestamp = 0L;
      this.data = var1;
      this.offset = var2;
      this.length = var3;
   }

   public String toString() {
      return BytesToString(this.data, this.offset, this.length);
   }

   public static String BytesToString(byte[] var0) {
      return BytesToString(var0, 0, var0.length);
   }

   public static String BytesToString(byte[] var0, int var1, int var2) {
      boolean var6 = SnmpPDU.i;
      if (var0 == null) {
         return a("zF,Z");
      } else {
         StringBuffer var3 = new StringBuffer();
         int var4 = var1 + var2;
         if (var4 > var0.length) {
            var4 = var0.length;
         }

         int var5 = var1;

         StringBuffer var10000;
         while(true) {
            if (var5 < var4) {
               var3.append(a[var0[var5] >> 4 & 15]);
               var3.append(a[var0[var5] & 15]);
               var10000 = var3.append(" ");
               if (var6) {
                  break;
               }

               ++var5;
               if (!var6) {
                  continue;
               }
            }

            var10000 = var3;
            break;
         }

         return var10000.toString();
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
               var10003 = 20;
               break;
            case 1:
               var10003 = 51;
               break;
            case 2:
               var10003 = 64;
               break;
            case 3:
               var10003 = 54;
               break;
            default:
               var10003 = 71;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
