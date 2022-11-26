package monfox.toolkit.snmp.util;

import java.util.Enumeration;
import java.util.Vector;

public class Scheduler {
   private boolean a = false;
   private Vector b = new Vector();
   private String c = a("5=\u007fy/\u00132rn");
   private TimerQueue d = null;

   public Scheduler(String var1, int var2, int var3) {
      this.c = var1;
      this.d = new TimerQueue(this.c + a(",1uM>\u0003+r"), var2, var3);
   }

   public Scheduler(String var1) {
      this.c = var1;
      int var2 = Integer.getInteger(a("\u0015=\u007fy/\u00132rn\u001f\u000e,r}/\u0015"), new Integer(5));
      int var3 = Integer.getInteger(a("\u0015=\u007fy/\u00132rn\u001b\u00147xn\"\u0012'"), new Integer(5));
      this.d = new TimerQueue(a(",1uM>\u0003+r"), var2, var3);
   }

   public String getName() {
      return this.c;
   }

   public Enumeration getJobs() {
      return this.b.elements();
   }

   public Job addJob(String var1, WorkItem var2) throws SchedulerException {
      Job var3 = new Job(var1, "-", var2);
      this.b.addElement(var3);
      var3.start();
      new f(this.d, var3);
      return var3;
   }

   public Job addJob(String var1, Runnable var2) throws SchedulerException {
      Job var3 = new Job(var1, "-", new RunnableWorkItem(var2));
      this.b.addElement(var3);
      var3.start();
      new f(this.d, var3);
      return var3;
   }

   public Job addJob(Job var1) {
      this.b.addElement(var1);
      new f(this.d, var1);
      var1.start();
      return var1;
   }

   public void removeJob(Job var1) {
      this.b.removeElement(var1);

      try {
         var1.cancel();
      } catch (Exception var3) {
      }

   }

   protected void cancelJob(Job var1) {
      this.b.removeElement(var1);
   }

   public void shutdown() {
      try {
         this.d.shutdown();
      } catch (Exception var2) {
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
               var10003 = 102;
               break;
            case 1:
               var10003 = 94;
               break;
            case 2:
               var10003 = 23;
               break;
            case 3:
               var10003 = 28;
               break;
            default:
               var10003 = 75;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   static class RunnableWorkItem extends WorkItem {
      private Runnable a;

      RunnableWorkItem(Runnable var1) {
         this.a = var1;
      }

      public void perform() {
         this.a.run();
      }
   }
}
