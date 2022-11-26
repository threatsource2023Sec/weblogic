package monfox.toolkit.snmp.agent;

class f implements SnmpMibTableListener {
   private final SnmpMibTableListener a;
   private final SnmpMibTableListener b;

   public f(SnmpMibTableListener var1, SnmpMibTableListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void rowInit(SnmpMibTableRow var1) {
      this.a.rowInit(var1);
      this.b.rowInit(var1);
   }

   public void rowAdded(SnmpMibTableRow var1) {
      this.a.rowAdded(var1);
      this.b.rowAdded(var1);
   }

   public void rowActivated(SnmpMibTableRow var1) {
      this.a.rowActivated(var1);
      this.b.rowActivated(var1);
   }

   public void rowDeactivated(SnmpMibTableRow var1) {
      this.a.rowDeactivated(var1);
      this.b.rowDeactivated(var1);
   }

   public void rowRemoved(SnmpMibTableRow var1) {
      this.a.rowRemoved(var1);
      this.b.rowRemoved(var1);
   }

   public static SnmpMibTableListener add(SnmpMibTableListener var0, SnmpMibTableListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpMibTableListener)(var1 == null ? var0 : new f(var0, var1));
      }
   }

   public static SnmpMibTableListener remove(SnmpMibTableListener var0, SnmpMibTableListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof f) ? var0 : ((f)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpMibTableListener remove(SnmpMibTableListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpMibTableListener var2 = remove(this.a, var1);
         SnmpMibTableListener var3 = remove(this.b, var1);
         return (SnmpMibTableListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpMibTableListener var0, SnmpMibTableListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof f) ? false : ((f)var0).contains(var1);
      }
   }

   public boolean contains(SnmpMibTableListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
