package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.util.SchedulerException;
import monfox.toolkit.snmp.util.WorkItem;

public final class SnmpWalkJob extends SnmpJob {
   private SnmpOid a = null;
   private s b = null;
   private boolean c = false;

   public SnmpWalkJob(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5, SnmpOid var6, boolean var7) throws SchedulerException {
      super(var1, var2, var3, var4, var5, (WorkItem)null);
      s var8 = new s(this);
      this.setWorkItem(var8);
      this.a = var6;
      this.c = var7;
   }

   public SnmpOid getTerminationOid() {
      return this.a;
   }

   public void showSteps(boolean var1) {
      this.c = var1;
   }

   public boolean showSteps() {
      return this.c;
   }
}
