package monfox.toolkit.snmp.ext;

class f implements SnmpTableListener {
   protected final SnmpTableListener a;
   protected final SnmpTableListener b;
   private static final String c = "$Id: SnmpTableMulticaster.java,v 1.3 2001/08/28 14:07:12 cbritton Exp $";

   protected f(SnmpTableListener var1, SnmpTableListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleCreated(SnmpTable var1, SnmpRow var2, int var3) {
      this.a.handleCreated(var1, var2, var3);
      this.b.handleCreated(var1, var2, var3);
   }

   public void handleDeleted(SnmpTable var1, SnmpRow var2, int var3) {
      this.a.handleDeleted(var1, var2, var3);
      this.b.handleDeleted(var1, var2, var3);
   }

   public void handleUpdated(SnmpTable var1, SnmpRow var2, int[] var3, int var4) {
      this.a.handleUpdated(var1, var2, var3, var4);
      this.b.handleUpdated(var1, var2, var3, var4);
   }

   public void handleError(SnmpTable var1, SnmpError var2) {
      this.a.handleError(var1, var2);
      this.b.handleError(var1, var2);
   }

   public static SnmpTableListener add(SnmpTableListener var0, SnmpTableListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpTableListener)(var1 == null ? var0 : new f(var0, var1));
      }
   }

   public static SnmpTableListener remove(SnmpTableListener var0, SnmpTableListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof f) ? var0 : ((f)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpTableListener remove(SnmpTableListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpTableListener var2 = remove(this.a, var1);
         SnmpTableListener var3 = remove(this.b, var1);
         return (SnmpTableListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpTableListener var0, SnmpTableListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof f) ? false : ((f)var0).contains(var1);
      }
   }

   public boolean contains(SnmpTableListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
