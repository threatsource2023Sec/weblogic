package monfox.toolkit.snmp.mgr.vacm;

class a extends Number {
   private int a;

   protected a(int var1) {
      this.a = var1;
   }

   public double doubleValue() {
      return (double)this.intValue();
   }

   public float floatValue() {
      return (float)this.intValue();
   }

   public long longValue() {
      return (long)this.intValue();
   }

   public short shortValue() {
      return (short)this.intValue();
   }

   public int intValue() {
      return this.a;
   }

   public boolean equals(Object var1) {
      try {
         Number var2 = (Number)var1;
         return this.intValue() == var2.intValue();
      } catch (Exception var3) {
         return false;
      }
   }
}
