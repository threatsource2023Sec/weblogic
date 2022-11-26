package com.asn1c.core;

import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class UTCTime extends GregorianCalendar implements ASN1Object, Comparable {
   public UTCTime() {
   }

   public UTCTime(TimeZone var1) {
      super(var1, Locale.getDefault());
   }

   public UTCTime(Locale var1) {
      super(TimeZone.getDefault(), var1);
   }

   public UTCTime(TimeZone var1, Locale var2) {
      super(var1, var2);
   }

   public UTCTime(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.set(var1, var2, var3, var4, var5, var6);
      if (var7) {
         this.set(15, 0);
         this.set(16, 0);
         this.setTimeZone(TimeZone.getTimeZone("UTC"));
      }

      this.complete();
   }

   public UTCTime(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.set(var1, var2, var3, var4, var5, var6);
      this.complete();
   }

   public UTCTime(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      StringBuffer var8 = new StringBuffer();
      this.set(var1, var2, var3, var4, var5, var6);
      this.set(15, var7);
      this.set(16, 0);
      if (var7 >= 0) {
         var8.append("GMT+");
      } else {
         var7 = -var7;
         var8.append("GMT-");
      }

      int var9 = (var7 + 30000) / 3600000;
      int var10 = (var7 + 30000 - 3600000 * var9) / '\uea60';
      var8.append(Integer.toString(var9 / 10) + Integer.toString(var9 % 10));
      if (var10 > 0) {
         var8.append(":" + Integer.toString(var10 / 10) + Integer.toString(var10 % 10));
      }

      this.setTimeZone(TimeZone.getTimeZone(var8.toString()));
      this.complete();
   }

   public UTCTime(GregorianCalendar var1) {
      this.set(var1.get(1), var1.get(2), var1.get(5), var1.get(11), var1.get(12), var1.get(13));
      this.set(15, var1.get(15));
      this.set(16, var1.get(16));
      this.setTimeZone(var1.getTimeZone());
      this.complete();
   }

   public void setValue() {
      this.setTimeZone(TimeZone.getDefault());
      this.setTime(new Date());
   }

   public void setValue(TimeZone var1) {
      this.setTimeZone(var1);
      this.setTime(new Date());
   }

   public void setValue(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.setTimeZone(TimeZone.getDefault());
      this.set(var1, var2, var3, var4, var5, var6);
      if (var7) {
         this.set(15, 0);
         this.set(16, 0);
         this.setTimeZone(TimeZone.getTimeZone("UTC"));
      }

      this.complete();
   }

   public void setValue(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.setTimeZone(TimeZone.getDefault());
      this.set(var1, var2, var3, var4, var5, var6);
      this.complete();
   }

   public void setValue(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      StringBuffer var8 = new StringBuffer();
      this.set(var1, var2, var3, var4, var5, var6);
      this.set(15, var7);
      this.set(16, 0);
      if (var7 >= 0) {
         var8.append("GMT+");
      } else {
         var7 = -var7;
         var8.append("GMT-");
      }

      int var9 = (var7 + 30000) / 3600000;
      int var10 = (var7 + 30000 - 3600000 * var9) / '\uea60';
      var8.append(Integer.toString(var9 / 10) + Integer.toString(var9 % 10));
      if (var10 > 0) {
         var8.append(":" + Integer.toString(var10 / 10) + Integer.toString(var10 % 10));
      }

      this.setTimeZone(TimeZone.getTimeZone(var8.toString()));
      this.complete();
   }

   public void setValue(GregorianCalendar var1) {
      this.set(var1.get(1), var1.get(2), var1.get(5), var1.get(11), var1.get(12), var1.get(13));
      this.set(15, var1.get(15));
      this.set(16, var1.get(16));
      this.setTimeZone(var1.getTimeZone());
      this.complete();
   }

   private static void formatDecimal(int var0, StringBuffer var1, int var2, int var3) {
      for(int var4 = 0; var4 < var3; ++var4) {
         var1.setCharAt(var3 - var4 - 1 + var2, (char)(var0 % 10 + 48));
         var0 /= 10;
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(17);
      var1.setLength(17);
      formatDecimal(this.get(1) % 100, var1, 0, 2);
      formatDecimal(this.get(2) + 1, var1, 2, 2);
      formatDecimal(this.get(5), var1, 4, 2);
      formatDecimal(this.get(11), var1, 6, 2);
      formatDecimal(this.get(12), var1, 8, 2);
      int var3 = this.get(13);
      int var2;
      if (var3 != 0) {
         formatDecimal(var3, var1, 10, 2);
         var2 = 12;
      } else {
         var2 = 10;
      }

      int var4 = this.get(15) / '\uea60';
      if (var4 == 0) {
         var1.setCharAt(var2++, 'Z');
      } else {
         if (var4 < 0) {
            var1.setCharAt(var2++, '-');
            var4 = -var4;
         } else {
            var1.setCharAt(var2++, '+');
         }

         formatDecimal(var4 / 60, var1, var2, 2);
         var2 += 2;
         if (var4 % 60 != 0) {
            formatDecimal(var4 % 60, var1, var2, 2);
            var2 += 2;
         }
      }

      var1.setLength(var2);
      return "\"" + var1.toString() + "\"";
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + this.toString() + var4);
   }

   public int compareTo(Object var1) {
      if (this.after(var1)) {
         return 1;
      } else {
         return this.before(var1) ? -1 : 0;
      }
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }
}
