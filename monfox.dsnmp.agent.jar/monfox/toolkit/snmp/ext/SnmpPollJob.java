package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.util.Job;
import monfox.toolkit.snmp.util.SchedulerException;
import monfox.toolkit.snmp.util.WorkItem;

public final class SnmpPollJob extends Job {
   private g a = null;
   private SnmpSession b = null;
   private SnmpPollable c = null;

   public SnmpPollJob(String var1, SnmpSession var2, SnmpPollable var3) throws SchedulerException {
      super(var1, "-", (WorkItem)null);
      this.c = var3;
      this.b = var2;
      this.a = new g(this);
      this.setWorkItem(this.a);
   }

   public SnmpSession getSession() {
      return this.b;
   }

   public SnmpPollable getPollable() {
      return this.c;
   }
}
