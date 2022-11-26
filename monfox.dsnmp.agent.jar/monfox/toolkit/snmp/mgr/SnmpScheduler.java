package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.util.Scheduler;
import monfox.toolkit.snmp.util.SchedulerException;

public final class SnmpScheduler extends Scheduler {
   private SnmpSession a;
   private SnmpJobListener b;
   private static final String c = "$Id: SnmpScheduler.java,v 1.3 2001/05/21 20:11:32 sking Exp $";

   public SnmpScheduler() {
      super(a("bF12\u000bR@9&-]M."), Integer.getInteger(a("BK4'<DD90\fYZ9#<B"), new Integer(5)), Integer.getInteger(a("BK4'<DD90\bCA301EQ"), new Integer(5)));
      this.a = null;
      this.b = null;
   }

   public SnmpScheduler(SnmpSession var1, SnmpJobListener var2) {
      this();
      this.a = var1;
      this.b = var2;
   }

   public SnmpGetJob scheduleGet(String var1, SnmpVarBindList var2) throws SchedulerException {
      return this.scheduleGet(var1, (SnmpPeer)null, this.a, this.b, var2);
   }

   public SnmpGetJob scheduleGet(String var1, SnmpPeer var2, SnmpVarBindList var3) throws SchedulerException {
      return this.scheduleGet(var1, var2, this.a, this.b, var3);
   }

   public SnmpGetJob scheduleGet(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5) throws SchedulerException {
      SnmpGetJob var6 = new SnmpGetJob(var1, var2, var3, var4, var5);
      this.addJob(var6);
      return var6;
   }

   public SnmpGetNextJob scheduleGetNext(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5) throws SchedulerException {
      SnmpGetNextJob var6 = new SnmpGetNextJob(var1, var2, var3, var4, var5);
      this.addJob(var6);
      return var6;
   }

   public SnmpGetNextJob scheduleGetNext(String var1, SnmpPeer var2, SnmpVarBindList var3) throws SchedulerException {
      return this.scheduleGetNext(var1, var2, this.a, this.b, var3);
   }

   public SnmpGetNextJob scheduleGetNext(String var1, SnmpVarBindList var2) throws SchedulerException {
      return this.scheduleGetNext(var1, (SnmpPeer)null, this.a, this.b, var2);
   }

   public SnmpWalkJob scheduleWalk(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5, SnmpOid var6, boolean var7) throws SchedulerException {
      SnmpWalkJob var8 = new SnmpWalkJob(var1, var2, var3, var4, var5, var6, var7);
      this.addJob(var8);
      return var8;
   }

   public SnmpWalkJob scheduleWalk(String var1, SnmpPeer var2, SnmpVarBindList var3, SnmpOid var4, boolean var5) throws SchedulerException {
      return this.scheduleWalk(var1, var2, this.a, this.b, var3, var4, var5);
   }

   public void addJob(SnmpJob var1) throws SchedulerException {
      if (var1.getSession() == null) {
         var1.setSession(this.a);
      }

      if (var1.getJobListener() == null) {
         var1.setJobListener(this.b);
      }

      if (var1.getPeer() == null) {
         var1.setPeer(this.a.getDefaultPeer());
      }

      var1.validate();
      super.addJob(var1);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 49;
               break;
            case 1:
               var10003 = 40;
               break;
            case 2:
               var10003 = 92;
               break;
            case 3:
               var10003 = 66;
               break;
            default:
               var10003 = 88;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
