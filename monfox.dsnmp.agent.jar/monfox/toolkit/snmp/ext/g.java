package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.util.WorkItem;

class g extends WorkItem {
   private SnmpPollJob a = null;
   private static final String c = "$Id: SnmpPollJob.java,v 1.4 2002/02/04 17:13:42 sking Exp $";

   g(SnmpPollJob var1) {
      this.a = var1;
   }

   public void perform() {
      SnmpSession var1 = this.a.getSession();
      SnmpPollable var2 = this.a.getPollable();

      try {
         var2.perform(var1);
      } catch (Exception var4) {
         SnmpFramework.handleException(this, var4);
      }

   }
}
