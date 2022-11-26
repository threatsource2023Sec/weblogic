package monfox.toolkit.snmp.mgr;

class m implements SnmpTrapListener {
   protected final SnmpTrapListener a;
   protected final SnmpTrapListener b;
   private static final String c = "$Id: SnmpTrapMulticaster.java,v 1.4 2002/02/05 23:17:29 sking Exp $";

   protected m(SnmpTrapListener var1, SnmpTrapListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleTrap(SnmpTrap var1) {
      this.a.handleTrap(var1);
      this.b.handleTrap(var1);
   }

   public static SnmpTrapListener add(SnmpTrapListener var0, SnmpTrapListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpTrapListener)(var1 == null ? var0 : new m(var0, var1));
      }
   }

   public static SnmpTrapListener remove(SnmpTrapListener var0, SnmpTrapListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof m) ? var0 : ((m)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpTrapListener remove(SnmpTrapListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpTrapListener var2 = remove(this.a, var1);
         SnmpTrapListener var3 = remove(this.b, var1);
         return (SnmpTrapListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpTrapListener var0, SnmpTrapListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof m) ? false : ((m)var0).contains(var1);
      }
   }

   public boolean contains(SnmpTrapListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
