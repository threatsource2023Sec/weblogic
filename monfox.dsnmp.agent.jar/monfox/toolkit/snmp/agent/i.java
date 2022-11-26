package monfox.toolkit.snmp.agent;

class i implements SnmpRequestProcessor {
   protected final SnmpRequestProcessor a;
   protected final SnmpRequestProcessor b;
   private static final String c = "$Id: SnmpRequestProcessorMulticaster.java,v 1.1 2006/08/01 20:27:23 sking Exp $";

   protected i(SnmpRequestProcessor var1, SnmpRequestProcessor var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleRequest(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3) {
      this.a.handleRequest(var1, var2, var3);
      this.b.handleRequest(var1, var2, var3);
   }

   public static SnmpRequestProcessor add(SnmpRequestProcessor var0, SnmpRequestProcessor var1) {
      if (contains(var0, var1)) {
         return var0;
      } else if (var0 == null) {
         return var1;
      } else {
         return (SnmpRequestProcessor)(var1 == null ? var0 : new i(var0, var1));
      }
   }

   public static SnmpRequestProcessor remove(SnmpRequestProcessor var0, SnmpRequestProcessor var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof i) ? var0 : ((i)var0).remove(var1);
      } else {
         return null;
      }
   }

   public SnmpRequestProcessor remove(SnmpRequestProcessor var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         SnmpRequestProcessor var2 = remove(this.a, var1);
         SnmpRequestProcessor var3 = remove(this.b, var1);
         return (SnmpRequestProcessor)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(SnmpRequestProcessor var0, SnmpRequestProcessor var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof i) ? false : ((i)var0).contains(var1);
      }
   }

   public boolean contains(SnmpRequestProcessor var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
