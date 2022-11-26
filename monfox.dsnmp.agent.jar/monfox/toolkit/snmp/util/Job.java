package monfox.toolkit.snmp.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class Job {
   public static final int UNINITIALIZED = -1;
   public static final int STOPPED = 0;
   public static final int RUNNING = 1;
   public static final int CANCELLED = 2;
   protected WorkItem _workItem;
   private int a;
   private Vector b;
   private Vector c;
   private int d;
   private Scheduler e;
   private String f;
   private static final String g = "$Id: Job.java,v 1.6 2010/06/24 10:38:18 sking Exp $";

   public Job(String var1, String var2) throws SchedulerException {
      this(var1, var2, (WorkItem)null);
   }

   public Job(String var1, String var2, WorkItem var3) throws SchedulerException {
      this._workItem = null;
      this.a = -1;
      this.b = new Vector();
      this.c = new Vector();
      this.d = -1;
      this.e = null;
      this.f = a("2_\u0006");
      if (var2 != null) {
         this.addBlackout(var2);
      }

      this._workItem = var3;
      this.addSchedule(var1);
      this.d = 1;
   }

   public void setWorkItem(WorkItem var1) {
      this._workItem = var1;
   }

   public synchronized void removeBlackout(CronTime var1) {
      this.b.removeElement(var1);
   }

   public synchronized void removeSchedule(CronTime var1) {
      this.c.removeElement(var1);
   }

   public Vector getBlackouts() {
      return this.b;
   }

   public synchronized Vector getSchedules() {
      return this.c;
   }

   public synchronized void addBlackout(CronTime var1) {
      this.b.addElement(var1);
   }

   public CronTime addBlackout(String var1) throws SchedulerException {
      if (var1 == null) {
         throw new NullPointerException(a("6E\bh]\u001a\\\u0005g\u0016\u0017E\u0010"));
      } else {
         CronTime var2 = new CronTime(var1);
         this.addBlackout(var2);
         return var2;
      }
   }

   public synchronized void addSchedule(CronTime var1) {
      this.c.addElement(var1);
   }

   public CronTime addSchedule(String var1) throws SchedulerException {
      if (var1 == null) {
         throw new NullPointerException(a("6E\bh]\u000bS\fa\u0019\r\\\u0001"));
      } else {
         CronTime var2 = new CronTime(var1);
         this.addSchedule(var2);
         return var2;
      }
   }

   public boolean isCancelled() {
      return this.d == 2;
   }

   public synchronized void start() {
      this.d = 1;
   }

   public synchronized void stop() {
      this.d = 0;
   }

   public synchronized void cancel() {
      this.d = 2;
      if (this.e != null) {
         this.e.cancelJob(this);
      }

   }

   public synchronized Calendar getNextRunTime(Calendar var1) {
      int var6 = WorkItem.d;
      if (this.d == 2) {
         return null;
      } else {
         Calendar var2 = null;
         Enumeration var3 = this.c.elements();

         Calendar var10000;
         while(true) {
            if (var3.hasMoreElements()) {
               try {
                  CronTime var4 = (CronTime)var3.nextElement();
                  Calendar var5 = var4.nextTime(var1);
                  var10000 = var2;
                  if (var6 != 0 || var6 != 0) {
                     break;
                  }

                  if (var2 == null || var5.before(var2)) {
                     var2 = var5;
                  }
                  continue;
               } catch (SchedulerException var7) {
                  if (var6 == 0) {
                     continue;
                  }
               }
            }

            var10000 = var2;
            break;
         }

         return var10000;
      }
   }

   public void execute(long var1) {
      Calendar var3 = Calendar.getInstance();
      var3.setTime(new Date(var1));
      int var4 = var3.get(12);
      if (this.d == 1) {
         boolean var5 = this.a(var3, this.c);
         if (var5) {
            var5 = !this.a(var3, this.b);
         }

         if (var5 && this._workItem != null) {
            this._workItem.perform();
         }
      }

   }

   private boolean a(Calendar var1, Vector var2) {
      int var5 = WorkItem.d;
      Enumeration var3 = var2.elements();

      boolean var10000;
      while(true) {
         if (var3.hasMoreElements()) {
            CronTime var4 = (CronTime)var3.nextElement();
            var10000 = var4.matches(var1);
            if (var5 != 0) {
               break;
            }

            if (var10000) {
               return true;
            }

            if (var5 == 0) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   public String getString() {
      return this.toString();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      int var5 = WorkItem.d;
      var1.append(a("#z+FGX"));
      var1.append(a("#c0E)=\nD"));
      switch (this.d) {
         case 1:
            var1.append(a("*m"));
            if (var5 == 0) {
               break;
            }
         case 0:
            var1.append(a("+m"));
            if (var5 == 0) {
               break;
            }
         case 2:
            var1.append(a(";m"));
            if (var5 == 0) {
               break;
            }
         case -1:
            var1.append(a("-m"));
      }

      var1.append(a("#c^"));
      Enumeration var2 = this.c.elements();

      while(true) {
         if (var2.hasMoreElements()) {
            CronTime var3 = (CronTime)var2.nextElement();
            var1.append(var3);
            if (var5 != 0) {
               break;
            }

            if (var5 == 0) {
               continue;
            }
         }

         var1.append(a("%k&>"));
         break;
      }

      Enumeration var6 = this.b.elements();

      while(true) {
         if (var6.hasMoreElements()) {
            CronTime var4 = (CronTime)var6.nextElement();
            var1.append(var4);
            if (var5 == 0 || var5 == 0) {
               continue;
            }
            break;
         }

         var1.append("]");
         break;
      }

   }

   public String getName() {
      return this.f;
   }

   public void setName(String var1) {
      this.f = var1;
   }

   public int getState() {
      return this.d;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 120;
               break;
            case 1:
               var10003 = 48;
               break;
            case 2:
               var10003 = 100;
               break;
            case 3:
               var10003 = 4;
               break;
            default:
               var10003 = 125;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
