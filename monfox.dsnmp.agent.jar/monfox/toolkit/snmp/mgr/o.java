package monfox.toolkit.snmp.mgr;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.util.WorkItem;

class o extends WorkItem {
   private static Logger a = null;
   private SnmpNotificationDispatcher b = null;
   private SnmpPDU c = null;
   private TransportEntity d = null;

   public o(SnmpNotificationDispatcher var1, SnmpPDU var2, TransportEntity var3) {
      this.c = var2;
      this.b = var1;
      this.d = var3;
      if (a == null) {
         a = Logger.getInstance(a("\u0018\u001dL\u0011z?\u0011Y\fu9\u001co\u0017n=\u0017J"));
      }

   }

   public void perform() {
      boolean var1 = SnmpSession.B;
      if (a.isDebugEnabled()) {
         a.debug(a("\u0006 w;Y\u0005!q6[v\"|-&vZ") + this.c + ")");
      }

      if (this.c.getType() == 164) {
         this.b.a((SnmpTrapPDU)this.c, this.d);
         if (!var1) {
            return;
         }
      }

      if (this.c.getType() == 167) {
         this.b.a((SnmpRequestPDU)this.c, this.d);
         if (!var1) {
            return;
         }
      }

      if (this.c.getType() == 166) {
         this.b.b((SnmpRequestPDU)this.c, this.d);
         if (!var1) {
            return;
         }
      }

      SnmpFramework.handleException(this, new SnmpException(a("\r!V\u0015l\u0018\u001dL\u0011z?\u0011Y\fu9\u001c|\u0011o&\u0013L\u001bt3\u0000eB<\u001f\u001cN\u0019p?\u0016\u0018(X\u0003H\u0018") + this.c));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 86;
               break;
            case 1:
               var10003 = 114;
               break;
            case 2:
               var10003 = 56;
               break;
            case 3:
               var10003 = 120;
               break;
            default:
               var10003 = 28;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
