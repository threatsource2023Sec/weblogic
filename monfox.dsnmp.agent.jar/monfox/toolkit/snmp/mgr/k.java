package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;

class k implements SnmpNotificationListener {
   protected final SnmpNotificationListener a;
   protected final SnmpNotificationListener b;
   private static final String c = "$Id: SnmpNotificationMulticaster.java,v 1.2 2001/05/21 20:11:32 sking Exp $";

   protected k(SnmpNotificationListener var1, SnmpNotificationListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleTrapV1(SnmpNotificationDispatcher var1, SnmpTrapPDU var2, TransportEntity var3) {
      this.a.handleTrapV1(var1, var2, var3);
      this.b.handleTrapV1(var1, var2, var3);
   }

   public void handleTrapV2(SnmpNotificationDispatcher var1, SnmpRequestPDU var2, TransportEntity var3) {
      this.a.handleTrapV2(var1, var2, var3);
      this.b.handleTrapV2(var1, var2, var3);
   }

   public void handleInform(SnmpNotificationDispatcher var1, SnmpRequestPDU var2, TransportEntity var3) {
      this.a.handleInform(var1, var2, var3);
      this.b.handleInform(var1, var2, var3);
   }

   public static SnmpNotificationListener add(SnmpNotificationListener var0, SnmpNotificationListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpNotificationListener)(var1 == null ? var0 : new k(var0, var1));
      }
   }

   public static SnmpNotificationListener remove(SnmpNotificationListener var0, SnmpNotificationListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof k) ? var0 : ((k)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpNotificationListener remove(SnmpNotificationListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpNotificationListener var2 = remove(this.a, var1);
         SnmpNotificationListener var3 = remove(this.b, var1);
         return (SnmpNotificationListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpNotificationListener var0, SnmpNotificationListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof k) ? false : ((k)var0).contains(var1);
      }
   }

   public boolean contains(SnmpNotificationListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
