package monfox.toolkit.snmp.ext;

import java.util.Hashtable;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.util.Scheduler;
import monfox.toolkit.snmp.util.SchedulerException;
import monfox.toolkit.snmp.util.TimerItem;
import monfox.toolkit.snmp.util.TimerQueue;

public final class SnmpPollingEngine extends Scheduler {
   private static Logger a = Logger.getInstance(a("YXF\u000fd"), a("XS\\"), a("Nee2drgd+ZzNf%]sn"));
   private SnmpSession b;
   private Hashtable c;
   private Hashtable d;
   private TimerQueue e;
   private static final String f = "$Id: SnmpPollingEngine.java,v 1.11 2010/10/25 15:36:21 sking Exp $";

   public SnmpPollingEngine() throws SnmpException {
      this(new SnmpSession());
   }

   public SnmpPollingEngine(int var1, int var2) throws SnmpException {
      this(new SnmpSession(), var1, var2);
   }

   public SnmpPollingEngine(SnmpSession var1, int var2, int var3) {
      super(a("Nee2drgd+ZzNf%]sn"), var2, var3);
      this.b = null;
      this.c = new Hashtable();
      this.d = new Hashtable();
      this.e = null;
      this.b = var1;
      this.e = new TimerQueue(a("Nee2drgd+ZzZ}'Ax"), var2, var3);
   }

   public SnmpPollingEngine(SnmpSession var1) throws SnmpException {
      int var4 = SnmpObjectSet.q;
      super(a("Nee2drgd+ZzNf%]sn"), Integer.getInteger(a("mdd.]sl\\*Fxjl1"), new Integer(5)), Integer.getInteger(a("mdd.]slX0]rya6M"), new Integer(5)));
      this.b = null;
      this.c = new Hashtable();
      this.d = new Hashtable();
      this.e = null;
      this.b = var1;
      int var2 = Integer.getInteger(a("mdd.]sl\\*Fxjl1"), new Integer(5));
      int var3 = Integer.getInteger(a("mdd.]slX0]rya6M"), new Integer(5));
      this.e = new TimerQueue(a("Nee2drgd+ZzZ}'Ax"), var2, var3);
      if (SnmpException.b) {
         ++var4;
         SnmpObjectSet.q = var4;
      }

   }

   public SnmpPollJob add(SnmpPollable var1, String var2) throws SchedulerException {
      SnmpPollJob var3 = new SnmpPollJob(var2, this.b, var1);
      this.addJob(var3);
      this.c.put(var1, var3);
      return var3;
   }

   public void remove(SnmpPollable var1) {
      SnmpPollJob var2 = (SnmpPollJob)this.c.get(var1);
      if (var2 != null) {
         var2.cancel();
         this.c.remove(var1);
      }

      PollingItem var3 = (PollingItem)this.d.get(var1);
      if (var3 != null) {
         var3.cancel();
         this.d.remove(var1);
      }

   }

   public void add(SnmpPollable var1, int var2) {
      PollingItem var3 = new PollingItem(var1, this.b, var2, this.e);
      this.d.put(var1, var3);
      var3.arm();
   }

   public PollingItem getPollingItem(SnmpPollable var1) {
      return (PollingItem)this.d.get(var1);
   }

   public SnmpPollJob getPollJob(SnmpPollable var1) {
      return (SnmpPollJob)this.c.get(var1);
   }

   public void setSession(SnmpSession var1) {
      this.b = var1;
   }

   public SnmpSession getSession() {
      return this.b;
   }

   public void shutdown() {
      super.shutdown();

      try {
         this.e.shutdown();
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
               var10003 = 29;
               break;
            case 1:
               var10003 = 11;
               break;
            case 2:
               var10003 = 8;
               break;
            case 3:
               var10003 = 66;
               break;
            default:
               var10003 = 52;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class PollingItem extends TimerItem {
      private static Logger a = Logger.getInstance(a("9{\u0018\u0006["), a("8p\u0002"), a(".F;;[\u0012D:\"e\u001am8,b\u0013Mx\u001bd\u0011D?%l4\\3&"));
      private TimerQueue b = null;
      private SnmpPollable c = null;
      private SnmpSession d = null;
      private int e = -1;

      public PollingItem(SnmpPollable var1, SnmpSession var2, int var3, TimerQueue var4) {
         this.c = var1;
         this.d = var2;
         this.b = var4;
         this.e = var3;
      }

      public synchronized void arm() {
         if (this.b != null) {
            this.arm((long)(this.e * 1000) + System.currentTimeMillis());
            this.b.put(this);
         }

      }

      public void perform() {
         this.arm();
         if (this.c != null) {
            this.c.perform(this.d);
         }

      }

      public synchronized void cancel() {
         super.cancel();
         this.c = null;
         this.b = null;
         this.d = null;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 125;
                  break;
               case 1:
                  var10003 = 40;
                  break;
               case 2:
                  var10003 = 86;
                  break;
               case 3:
                  var10003 = 75;
                  break;
               default:
                  var10003 = 11;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
