package monfox.toolkit.snmp.agent.x.pdu;

class a {
   private long[] a;
   private boolean b = true;

   public a(long[] var1, boolean var2) {
      this.a = var1;
      this.b = var2;
   }

   public long[] getValue() {
      return this.a;
   }

   public boolean isInclude() {
      return this.b;
   }
}
