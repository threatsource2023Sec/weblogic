package monfox.toolkit.snmp.mgr;

import java.util.Date;

public class SnmpStats {
   private int a = 0;
   private int b = 0;
   private int c = 0;
   private int d = 0;
   private int e = 0;
   private int f = 0;
   private long g = -1L;
   private long h = -1L;

   public int getTimeoutCount() {
      return this.a;
   }

   public int getLateResponseCount() {
      return this.b;
   }

   public int getRetryCount() {
      return this.c;
   }

   public int getRequestCount() {
      return this.d;
   }

   public int getResponseCount() {
      return this.e;
   }

   public long getLastUpdateTime() {
      return this.h;
   }

   public long getAvgResponseTimeMillis() {
      return this.g;
   }

   void a() {
      ++this.a;
   }

   void b() {
      ++this.b;
   }

   void c() {
      ++this.c;
   }

   void d() {
      ++this.d;
   }

   void e() {
      ++this.e;
   }

   synchronized void f() {
      this.h = System.currentTimeMillis();
   }

   synchronized void a(long var1) {
      this.g = (this.g * (long)this.f + var1) / (long)(++this.f);
   }

   public synchronized void reset() {
      this.a = 0;
      this.b = 0;
      this.c = 0;
      this.d = 0;
      this.e = 0;
      this.g = -1L;
      this.f = 0;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("Oh\u0002eN\u0016+B5F1$[6Heh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8"));
      var1.append(a("Oe\u000feg 4Z f1\u0006@0{1e\u000fe5ee\u0012e")).append(this.d);
      var1.append(a("Oe\u000feg 1]<V*0A15ee\u000fe5ee\u0012e")).append(this.c);
      var1.append(a("Oe\u000feg 6_*{6 l*`+1\u000fe5ee\u0012e")).append(this.e);
      var1.append(a("Oe\u000fea,(J*`1\u0006@0{1e\u000fe5ee\u0012e")).append(this.a);
      var1.append(a("Oe\u000fey$1J\u0017p65@+f \u0006@0{1e\u0012e")).append(this.b);
      var1.append(a("Oe\u000fet3\"} f5*A6p\u0011,B 5ee\u0012e")).append(this.g).append(a("(6"));
      var1.append("\n");
      var1.append(a("Oe\u000fey$6[\u0010e!$[ qee\u000fe5ee\u0012e")).append(new Date(this.h));
      var1.append(a("Oh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8hh\u0002h8"));
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 69;
               break;
            case 1:
               var10003 = 69;
               break;
            case 2:
               var10003 = 47;
               break;
            case 3:
               var10003 = 69;
               break;
            default:
               var10003 = 21;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
