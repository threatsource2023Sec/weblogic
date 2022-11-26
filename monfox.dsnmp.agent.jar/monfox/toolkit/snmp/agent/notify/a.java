package monfox.toolkit.snmp.agent.notify;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;

class a implements SnmpNotifier.Listener {
   protected final SnmpNotifier.Listener a;
   protected final SnmpNotifier.Listener b;
   private static final String c = "$Id: SnmpNotifierMulticaster.java,v 1.2 2006/08/01 20:13:01 sking Exp $";

   protected a(SnmpNotifier.Listener var1, SnmpNotifier.Listener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleNotification(String var1, SnmpOid var2, SnmpVarBindList var3, String var4) {
      this.a.handleNotification(var1, var2, var3, var4);
      this.b.handleNotification(var1, var2, var3, var4);
   }

   public static SnmpNotifier.Listener add(SnmpNotifier.Listener var0, SnmpNotifier.Listener var1) {
      if (contains(var0, var1)) {
         return var0;
      } else if (var0 == null) {
         return var1;
      } else {
         return (SnmpNotifier.Listener)(var1 == null ? var0 : new a(var0, var1));
      }
   }

   public static SnmpNotifier.Listener remove(SnmpNotifier.Listener var0, SnmpNotifier.Listener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof a) ? var0 : ((a)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpNotifier.Listener remove(SnmpNotifier.Listener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpNotifier.Listener var2 = remove(this.a, var1);
         SnmpNotifier.Listener var3 = remove(this.b, var1);
         return (SnmpNotifier.Listener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpNotifier.Listener var0, SnmpNotifier.Listener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof a) ? false : ((a)var0).contains(var1);
      }
   }

   public boolean contains(SnmpNotifier.Listener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
