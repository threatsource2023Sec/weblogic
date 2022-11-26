package monfox.toolkit.snmp.tc;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValueException;

public class SnmpDateAndTime {
   public static boolean a;

   public static Date toDate(SnmpString var0) throws SnmpValueException {
      boolean var17 = a;
      byte[] var1 = var0.toByteArray();
      if (var1.length != 11 && var1.length != 8) {
         throw new SnmpValueException(a("\u000e\u0005qJ#.\u000f'o.3\nFE+\u0013\u0002jNo!\u0004uF.3CkN! \u001fo\u0002ug") + var0.toHexString());
      } else {
         int var2 = var1[0] * 256 + (var1[1] & 255);
         int var3 = var1[2] - 1;
         byte var4 = var1[3];
         byte var5 = var1[4];
         byte var6 = var1[5];
         byte var7 = var1[6];
         byte var8 = var1[7];
         int var9 = 1;
         byte var10 = 0;
         byte var11 = 0;
         if (var1.length == 11) {
            var9 = var1[8] == 45 ? -1 : 1;
            var10 = var1[9];
            var11 = var1[10];
         }

         int var12 = var9 * (var10 * 3600 * 1000 + var11 * 60 * 1000);
         Calendar var13 = Calendar.getInstance(TimeZone.getTimeZone(a("\u0012?D")));
         var13.set(var2, var3, var4, var5, var6, var7);
         Date var14 = var13.getTime();
         long var15 = var14.getTime();
         var15 += (long)(var8 * 10);
         var15 += (long)(0 - var12);
         Date var10000 = new Date(var15);
         if (var17) {
            SnmpException.b = !SnmpException.b;
         }

         return var10000;
      }
   }

   public static SnmpString fromDate(Date var0, boolean var1) {
      Calendar var2 = Calendar.getInstance();
      var2.setTime(var0);
      return fromCalendar(var2, var1);
   }

   public static SnmpString fromCalendar(Calendar var0, boolean var1) {
      boolean var13 = a;
      int var2 = var0.get(1);
      int var3 = var0.get(2) + 1;
      int var4 = var0.get(5);
      int var5 = var0.get(11);
      int var6 = var0.get(12);
      int var7 = var0.get(13);
      int var8 = var0.get(14);
      Object var9 = null;
      byte[] var14;
      if (var1) {
         var14 = new byte[11];
      } else {
         var14 = new byte[8];
      }

      var14[0] = (byte)(var2 / 256);
      var14[1] = (byte)(var2 % 256 & 255);
      var14[2] = (byte)var3;
      var14[3] = (byte)var4;
      var14[4] = (byte)var5;
      var14[5] = (byte)var6;
      var14[6] = (byte)var7;
      var14[7] = (byte)(var8 / 10);
      if (var1) {
         int var10 = var0.getTimeZone().getRawOffset() / 1000;
         if (var0.getTimeZone().inDaylightTime(var0.getTime())) {
            var10 += 3600;
         }

         int var11 = Math.abs(var10);
         int var12 = var11 / 60;
         var14[8] = (byte)(var10 >= 0 ? 43 : 45);
         var14[9] = (byte)(var10 / 3600);
         var14[9] = (byte)(var12 / 60);
         var14[10] = (byte)(var12 % 60);
      }

      SnmpString var10000 = new SnmpString(var14);
      if (SnmpException.b) {
         a = !var13;
      }

      return var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 71;
               break;
            case 1:
               var10003 = 107;
               break;
            case 2:
               var10003 = 7;
               break;
            case 3:
               var10003 = 43;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
