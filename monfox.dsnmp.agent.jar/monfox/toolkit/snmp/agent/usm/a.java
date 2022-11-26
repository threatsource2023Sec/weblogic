package monfox.toolkit.snmp.agent.usm;

class a implements Usm.Listener {
   protected final Usm.Listener a;
   protected final Usm.Listener b;
   private static final String c = "$Id: UsmMulticaster.java,v 1.1 2004/12/20 22:25:36 sking Exp $";

   protected a(Usm.Listener var1, Usm.Listener var2) {
      this.a = var1;
      this.b = var2;
   }

   public void handleEvent(Usm.Event var1) {
      this.a.handleEvent(var1);
      this.b.handleEvent(var1);
   }

   public static Usm.Listener add(Usm.Listener var0, Usm.Listener var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (Usm.Listener)(var1 == null ? var0 : new a(var0, var1));
      }
   }

   public static Usm.Listener remove(Usm.Listener var0, Usm.Listener var1) {
      if (var0 != var1 && var0 != null) {
         return !(var0 instanceof a) ? var0 : ((a)var0).remove(var1);
      } else {
         return null;
      }
   }

   public Usm.Listener remove(Usm.Listener var1) {
      if (var1 == this.a) {
         return this.b;
      } else if (var1 == this.b) {
         return this.a;
      } else {
         Usm.Listener var2 = remove(this.a, var1);
         Usm.Listener var3 = remove(this.b, var1);
         return (Usm.Listener)(var2 == this.a && var3 == this.b ? this : add(var2, var3));
      }
   }

   public static boolean contains(Usm.Listener var0, Usm.Listener var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return false;
      } else {
         return !(var0 instanceof a) ? false : ((a)var0).contains(var1);
      }
   }

   public boolean contains(Usm.Listener var1) {
      if (var1 == this.a) {
         return true;
      } else if (var1 == this.b) {
         return true;
      } else {
         return contains(this.a, var1) || contains(this.b, var1);
      }
   }
}
