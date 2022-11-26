package monfox.toolkit.snmp.engine;

class b implements SnmpErrorListener {
   protected final SnmpErrorListener a;
   protected final SnmpErrorListener b;
   private static final String c = "$Id: SnmpErrorMulticaster.java,v 1.2 2003/02/04 20:18:47 sking Exp $";

   protected b(SnmpErrorListener var1, SnmpErrorListener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleError(SnmpBuffer var1, TransportEntity var2, int var3, int var4, SnmpCoderException var5) {
      this.a.handleError(var1, var2, var3, var4, var5);
      this.b.handleError(var1, var2, var3, var4, var5);
   }

   public static SnmpErrorListener add(SnmpErrorListener var0, SnmpErrorListener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (SnmpErrorListener)(var1 == null ? var0 : new b(var0, var1));
      }
   }

   public static SnmpErrorListener remove(SnmpErrorListener var0, SnmpErrorListener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof b) ? var0 : ((b)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpErrorListener remove(SnmpErrorListener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpErrorListener var2 = remove(this.a, var1);
         SnmpErrorListener var3 = remove(this.b, var1);
         return (SnmpErrorListener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpErrorListener var0, SnmpErrorListener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof b) ? false : ((b)var0).contains(var1);
      }
   }

   public boolean contains(SnmpErrorListener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
