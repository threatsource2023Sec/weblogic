package monfox.toolkit.snmp.engine;

class c implements SnmpMessageListener {
   protected final SnmpMessageListener a;
   protected final SnmpMessageListener b;
   private static final String c = "$Id: SnmpMessageMulticaster.java,v 1.1 2001/05/21 20:09:47 sking Exp $";

   protected c(SnmpMessageListener var1, SnmpMessageListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleMessage(SnmpMessage var1, TransportEntity var2) {
      this.a.handleMessage(var1, var2);
      this.b.handleMessage(var1, var2);
   }

   public static SnmpMessageListener add(SnmpMessageListener var0, SnmpMessageListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpMessageListener)(var1 == null ? var0 : new c(var0, var1));
      }
   }

   public static SnmpMessageListener remove(SnmpMessageListener var0, SnmpMessageListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof c) ? var0 : ((c)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpMessageListener remove(SnmpMessageListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpMessageListener var2 = remove(this.a, var1);
         SnmpMessageListener var3 = remove(this.b, var1);
         return (SnmpMessageListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpMessageListener var0, SnmpMessageListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof c) ? false : ((c)var0).contains(var1);
      }
   }

   public boolean contains(SnmpMessageListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
