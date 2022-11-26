package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.util.WorkItem;

class r extends WorkItem {
   private SnmpJob a = null;
   private static final String c = "$Id: SnmpGetJob.java,v 1.3 2001/05/21 20:11:31 sking Exp $";

   r(SnmpGetJob var1) {
      this.a = var1;
   }

   public void perform() {
      SnmpSession var1 = this.a.getSession();
      SnmpPeer var2 = this.a.getPeer();
      SnmpJobListener var3 = this.a.getJobListener();

      try {
         SnmpPendingRequest var4 = var1.startGet(this.a.getPeer(), this.a.getResponseListener(), this.a.getVarBindList());
         if (var3 != null) {
            var3.handleStart(this.a, var4);
         }
      } catch (SnmpException var5) {
         if (var3 != null) {
            var3.handleException(this.a, (SnmpPendingRequest)null, var5);
         }
      }

   }
}
