package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.util.SchedulerException;
import monfox.toolkit.snmp.util.WorkItem;

public final class SnmpGetJob extends SnmpJob {
   private r a = null;

   public SnmpGetJob(String var1, SnmpPeer var2, SnmpSession var3, SnmpJobListener var4, SnmpVarBindList var5) throws SchedulerException {
      super(var1, var2, var3, var4, var5, (WorkItem)null);
      r var6 = new r(this);
      this.setWorkItem(var6);
   }
}
