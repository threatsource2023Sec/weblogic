package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.util.WorkItem;

class s extends WorkItem {
   private SnmpWalkJob a = null;
   private static final String c = "$Id: SnmpWalkJob.java,v 1.2 2001/01/15 21:33:26 sking Exp $";

   s(SnmpWalkJob var1) {
      this.a = var1;
   }

   public void perform() {
      SnmpSession var1 = this.a.getSession();
      SnmpPeer var2 = this.a.getPeer();
      SnmpJobListener var3 = this.a.getJobListener();

      try {
         SnmpPendingRequest var4 = var1.startWalk(this.a.getPeer(), this.a.getResponseListener(), this.a.getVarBindList(), this.a.getTerminationOid(), this.a.showSteps());
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
