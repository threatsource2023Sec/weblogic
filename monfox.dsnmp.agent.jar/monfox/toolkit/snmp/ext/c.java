package monfox.toolkit.snmp.ext;

class c implements SnmpObjectSetListener {
   protected final SnmpObjectSetListener a;
   protected final SnmpObjectSetListener b;
   private static final String c = "$Id: SnmpObjectSetMulticaster.java,v 1.2 2001/01/15 21:33:25 sking Exp $";

   protected c(SnmpObjectSetListener var1, SnmpObjectSetListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleError(SnmpObjectSet var1, SnmpError var2) {
      this.a.handleError(var1, var2);
      this.b.handleError(var1, var2);
   }

   public void handleUpdated(SnmpObjectSet var1, int[] var2) {
      this.a.handleUpdated(var1, var2);
      this.b.handleUpdated(var1, var2);
   }

   public static SnmpObjectSetListener add(SnmpObjectSetListener var0, SnmpObjectSetListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpObjectSetListener)(var1 == null ? var0 : new c(var0, var1));
      }
   }

   public static SnmpObjectSetListener remove(SnmpObjectSetListener var0, SnmpObjectSetListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof c) ? var0 : ((c)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpObjectSetListener remove(SnmpObjectSetListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpObjectSetListener var2 = remove(this.a, var1);
         SnmpObjectSetListener var3 = remove(this.b, var1);
         return (SnmpObjectSetListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpObjectSetListener var0, SnmpObjectSetListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof c) ? false : ((c)var0).contains(var1);
      }
   }

   public boolean contains(SnmpObjectSetListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
