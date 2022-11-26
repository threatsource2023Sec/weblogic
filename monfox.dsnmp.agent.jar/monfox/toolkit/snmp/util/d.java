package monfox.toolkit.snmp.util;

import java.util.Enumeration;
import java.util.Vector;

class d implements Runnable {
   private long a = 0L;
   private long b = 0L;
   private long c = 100L;
   private long d = 80L;
   private Vector e = new Vector();
   private static d f = null;
   private static final String g = "$Id: TimeController.java,v 1.3 2001/06/04 16:03:31 sking Exp $";

   public static void main(String[] var0) {
      long var1 = 0L;
      long var3 = 0L;
      a var5 = new a();
      addTimeControllerListener(var5);
      Thread var6 = new Thread(var5);
      var6.setDaemon(true);
      var6.start();

      while(true) {
         while(true) {
            try {
               var1 = currentTimeMillis();
               System.out.println(a("\u007fR\u0014pS\u000e") + var1 + ":" + (var1 - var3) + a("\bX\u0014pY"));
               var3 = var1;
               Thread.sleep(1000L);
            } catch (Exception var8) {
            }
         }
      }
   }

   d(long var1) {
      System.out.println(a("\u007fR\u0014z=0\u000f\u001e\u000e\u001a8\u001d}5\u001d!\nQ6\u001f0\n\u001epY\u007f"));
      this.c = var1;
      this.d = (long)((double)((float)var1) * 0.75);
      this.a = 0L;
      this.b = System.currentTimeMillis();
      Thread var3 = new Thread(this);
      var3.setDaemon(true);
      var3.start();
   }

   public static long currentTimeMillis() {
      return d().c();
   }

   public void run() {
      while(true) {
         this.a();
      }
   }

   public static void performTimeSync() {
      d().localPerformTimeSync();
   }

   public synchronized void localPerformTimeSync() {
      this.notifyAll();
   }

   private synchronized void a() {
      try {
         System.out.println(a("\"\u0019W.\u001a;\u001f\u0010t]"));
         this.wait(this.d);
         System.out.println(a("1\u0017P?]{V"));
         currentTimeMillis();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void a(e var1) {
      this.e.addElement(var1);
   }

   void b(e var1) {
      this.e.removeElement(var1);
   }

   void b() {
      Vector var1 = (Vector)((Vector)this.e.clone());
      Enumeration var2 = var1.elements();

      while(var2.hasMoreElements()) {
         e var3 = (e)var2.nextElement();

         try {
            System.out.println(a("\u001c6h\u00158\u001c6y"));
            var3.timeUpdated();
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         if (WorkItem.d != 0) {
            break;
         }
      }

   }

   synchronized long c() {
      long var1 = System.currentTimeMillis();
      long var3 = var1 + this.a;
      long var5 = var3 - this.b;
      if (var5 < 0L || var5 > this.c) {
         System.out.println(a("\u007fR\u0014z ,\u000bJ?\u001eu,W7\u0016u;V;\u001d2\u001dZzY\u007fR"));
         System.out.println(a("\u007fR\u0014z0 \nL`S") + var1);
         System.out.println(a("\u007fR\u0014z21\u0012\u001e`S") + var3);
         System.out.println(a("\u007fR\u0014z?4\u000bJ`S") + this.b);
         System.out.println(a("\u007fR\u0014z7<\u001eX`S") + var5);
         long var7 = var3 - (this.b + this.c);
         this.a -= var7;
         System.out.println(a("\u007fR\u0014z<3\u001eM?\u0007oX") + this.a);
         this.b = var1 + this.a;
         this.b();
         if (WorkItem.d == 0) {
            return this.b;
         }
      }

      this.b = var3;
      return this.b;
   }

   public static void addTimeControllerListener(e var0) {
      d().a(var0);
   }

   public static void removeTimeControllerListener(e var0) {
      d().b(var0);
   }

   static d d() {
      if (f == null) {
         System.out.println(a("s^\u0018"));
         long var0 = 500L;

         try {
            var0 = Long.getLong(a("\u0001\u0011S?0:\u0016J(\u001c9\u0014[(]<\u0016J?\u0001#\u0019R"), 500L);
         } catch (NumberFormatException var3) {
         }

         f = new d(var0);
         System.out.println(a("s^\u0018"));
      }

      return f;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 85;
               break;
            case 1:
               var10003 = 120;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 90;
               break;
            default:
               var10003 = 115;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
