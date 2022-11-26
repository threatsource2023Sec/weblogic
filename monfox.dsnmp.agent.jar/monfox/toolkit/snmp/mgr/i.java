package monfox.toolkit.snmp.mgr;

class i implements SnmpInformListener {
   protected final SnmpInformListener a;
   protected final SnmpInformListener b;
   private static final String c = "$Id: SnmpInformMulticaster.java,v 1.4 2002/02/05 23:17:28 sking Exp $";

   protected i(SnmpInformListener var1, SnmpInformListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleInform(SnmpPendingInform var1) {
      this.a.handleInform(var1);
      this.b.handleInform(var1);
   }

   public static SnmpInformListener add(SnmpInformListener var0, SnmpInformListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpInformListener)(var1 == null ? var0 : new i(var0, var1));
      }
   }

   public static SnmpInformListener remove(SnmpInformListener var0, SnmpInformListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof i) ? var0 : ((i)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpInformListener remove(SnmpInformListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpInformListener var2 = remove(this.a, var1);
         SnmpInformListener var3 = remove(this.b, var1);
         return (SnmpInformListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpInformListener var0, SnmpInformListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof i) ? false : ((i)var0).contains(var1);
      }
   }

   public boolean contains(SnmpInformListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
