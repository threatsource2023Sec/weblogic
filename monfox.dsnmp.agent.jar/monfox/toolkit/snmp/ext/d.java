package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.mgr.SnmpSession;

class d implements SnmpPollable {
   protected final SnmpPollable a;
   protected final SnmpPollable b;
   private static final String c = "$Id: SnmpPollableMulticaster.java,v 1.2 2001/01/15 21:33:25 sking Exp $";

   protected d(SnmpPollable var1, SnmpPollable var2) {
      this.a = var1;
      this.b = var2;
   }

   public void perform(SnmpSession var1) {
      this.a.perform(var1);
      this.b.perform(var1);
   }

   public static SnmpPollable add(SnmpPollable var0, SnmpPollable var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpPollable)(var1 == null ? var0 : new d(var0, var1));
      }
   }

   public static SnmpPollable remove(SnmpPollable var0, SnmpPollable var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof d) ? var0 : ((d)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpPollable remove(SnmpPollable var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpPollable var2 = remove(this.a, var1);
         SnmpPollable var3 = remove(this.b, var1);
         return (SnmpPollable)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpPollable var0, SnmpPollable var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof d) ? false : ((d)var0).contains(var1);
      }
   }

   public boolean contains(SnmpPollable var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
